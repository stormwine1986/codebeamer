# gerrit_data_collector.py

import sys, getopt
import requests
import json
import oracledb
import numpy as np
from datetime import datetime
import yaml
import logging
import smtplib
from email.mime.text import MIMEText
from email.header import Header
import os

COLLECTOR_NAME = "GDC"

def main(argv):
    ## build origin data
    changeComments = {}
    url = gerrit_url + "/changes/?q=" + query + "&o=ALL_REVISIONS&o=ALL_FILES&o=DETAILED_ACCOUNTS"
    response = requests.get(url)
    logging.info("GET {} response.status_code = {}".format(url, str(response.status_code)))
    if response.status_code == 200:
        fetchDatetime = datetime.now()
        changes = json.loads(response.text.splitlines()[1])
        for change in changes:
            comments = getComments(gerrit_url, change["id"])
            changeComments[change["id"]] = comments
    else:
        raise Exception("Gerrit response code " + str(response.status_code))
 
    
    logging.info("Data fetched at {}".format(fetchDatetime.strftime("%d/%m/%Y, %H:%M:%S")))

    con = oracledb.connect(user=oracle_user, password=oracle_password, dsn=oracle_dsn)
    logging.info("Print database version: {}".format(con.version))

    if len(changes) == 0:
        logging.info("Nothing to refresh, COMPLETED")
    else:
        ## update database
        for change in changes:
            refreshDatabase(change, changeComments[change["id"]], con)
    
    ## if no need to trace last success updated datatime, skipped
    if not no_trace:
        confirmUpdated(con, fetchDatetime)
        logging.info("Last Updated Datetime marked as {}".format(fetchDatetime.strftime("%d/%m/%Y, %H:%M:%S")))
    
    con.commit()
    con.close()
    return

## get comments for each change
def getComments(gerrit_url, masterId):
    url = gerrit_url + "/changes/" + masterId + "/comments"
    response = requests.get(url)
    logging.info("GET {} response.status_code = {}".format(url, str(response.status_code)))
    if response.status_code == 200:
        comments = json.loads(response.text.splitlines()[1])
        return comments
    else:
        raise Exception("Gerrit response code " + str(response.status_code))

## refresh database
def refreshDatabase(change, comments, con):
    cur = con.cursor()
    ## delete exsisting
    logging.info("Deleting change [{}]".format(change["change_id"]))
    cur.execute("delete from CHANGE_COMMENTS where CHANGE_ID = :id", id = change["change_id"])
    cur.execute("delete from CHANGE_FILES where CHANGE_ID = :id", id = change["change_id"])
    cur.execute("delete from CHANGE_MASTER where ID = :id", id = change["change_id"])
    ## insert new
    logging.info("Inserting change [{}]".format(change["change_id"]))
    sql = """
    INSERT INTO CHANGE_MASTER
        (ID, PROJECT, BRANCH, "NUMBER", SUBJECT, OWNER, FEATURE_GROUP, DEPARTMENT, CREATED, UPDATED, 
        STATUS, INSERTIONS, DELETIONS, RESOURCE_GROUP, TOTAL_COMMENT_COUNT, UNRESOLVED_COMMENT_COUNT, 
        COMMIT_TYPE, ORIGIN_MASTER, ORIGIN_COMMENTS)
    VALUES
        (:1, :2, :3, :4, :5, :6, :7, :8, :9, :10, :11, :12, :13, :14, :15, :16, :17, :18, :19)
    """
    cur.execute(sql,
               (change["change_id"],
                change["project"],
                change["branch"],
                change["_number"],
                change["subject"],
                change["owner"]["name"],
                getFeatureGroupFromCB(change["owner"]["name"]),
                getDepartmentFromCB(change["owner"]["name"]),
                toTimestamp(change["created"]),
                toTimestamp(change["updated"]),
                change["status"],
                change["insertions"],
                change["deletions"],
                getResourceGroupFromCB(change["owner"]["name"]),
                change["total_comment_count"],
                change["unresolved_comment_count"],
                getCommitType(change["subject"]),
                json.dumps(change),
                json.dumps(comments)))
    
    sql = """
    INSERT INTO CHANGE_COMMENTS
        (ID, CHANGE_ID, "FILE", LINE, AUTHOR, UPDATED, MESSAGE, "TYPE", SEVERITY, UNRESOLVED, IN_REPLY_TO)
    VALUES(:1, :2, :3, :4, :5, :6, :7, :8, :9, :10, :11)
    """

    values = []
    for file in comments.keys():
        list = comments[file]
        for comment in list:
            typeAndSeverity = getCommentTypeAndSeverity(comment["message"])
            values.append((
                comment["id"],
                change["change_id"],
                file,
                comment["line"],
                comment["author"]["name"],
                toTimestamp(comment["updated"]),
                comment["message"],
                typeAndSeverity[0],
                typeAndSeverity[1],
                comment["unresolved"],
                getValue(comment, "in_reply_to", "")))

    if len(values) > 0:
        cur.executemany(sql, values)
    
    sql = """
    INSERT INTO CHANGE_FILES
        (ID, CHANGE_ID, "FILE", STATUS, LINES_INSERTED, LINES_DELETED, CREATED)
    VALUES(CHANGE_FILES_ID.nextval, :1, :2, :3, :4, :5, :6)
    """

    values = []
    for revision in change["revisions"].values():
        created = revision["created"]
        files = revision["files"]
        for file in files.keys():
            info = files[file]
            values.append((
                change["change_id"],
                file,
                getValue(info, "status", "M"),
                getValue(info, "lines_inserted", 0),
                getValue(info, "lines_deleted", 0),
                toTimestamp(created)
            ))

    cur.executemany(sql, values)

    cur.close()
    return

## nano seconds to datatime
def toTimestamp(str):
    dt = np.datetime64(str)
    ts = (dt - np.datetime64('1970-01-01T00:00:00')) / np.timedelta64(1, 's')
    return datetime.fromtimestamp(ts)

## get dict value safety
def getValue(dict, key, default):
    if key in dict:
        return dict[key]
    else:
        return default

## fetch user's feature group from codebeamer
def getFeatureGroupFromCB(user):
    ## TODO
    return ""

## fetch user's department from codebeamer
def getDepartmentFromCB(user):
    ## TODO
    return ""

## fetch resource group from codebeamer
def getResourceGroupFromCB(user):
    ## TODO
    return ""

## extract commit type from subject
def getCommitType(subject):
    commit_type = ""
    if subject.startswith("feat"):
        commit_type = "FEAT"
    if subject.startswith("fix"):
        commit_type = "FIX"
    if subject.startswith("perf"):
        commit_type = "PERF"
    if subject.startswith("merge"):
        commit_type = "MERGE"
    if subject.startswith("sync"):
        commit_type = "SYNC"
    if subject.startswith("style"):
        commit_type = "STYLE"
    if subject.startswith("refactor"):
        commit_type = "REFACTOR"
    if subject.startswith("revert"):
        commit_type = "REVERT"
    if subject.startswith("test"):
        commit_type = "TEST"
    if subject.startswith("docs"):
        commit_type = "DOCS"
    if subject.startswith("chore"):
        commit_type = "CHORE"
    
    return commit_type

## extract comment type and severity from message
def getCommentTypeAndSeverity(message):
    strip = message.strip()
    if strip.startswith("["):
        end = strip.find("]")
        if end == -1:
            return ("","")
        
        split = strip[1:end].split(",")
        return (split[0], getArrayValue(split, 1, ""))
    else:
        return ("","")

## after FAILED notice administrator
def notificationWhenFailed():
    ## TODO
    return

## get array value safety
def getArrayValue(arr, index, default):
    if index > len(arr) - 1:
        return default
    else:
        return arr[index]
    
## record last updated datetime into db
def confirmUpdated(con, updated):
    cur = con.cursor()
    sql = """
    DELETE FROM LAST_UPDATED_DATETIME WHERE ID=:1
    """
    cur.execute(sql, [collector_id])
    sql = """
    INSERT INTO LAST_UPDATED_DATETIME
        (ID, UPDATED)
    VALUES(:1, :2)
    """
    cur.execute(sql, [collector_id, updated])

## notifaction to admin when updated failed
def notifationIfFailed():
    try:
        message = MIMEText('GDC Collector Update Failed', 'plain', 'utf-8')
        message['Subject'] = Header("GDC Collector Update Failed", 'utf-8')
        smtpObj = smtplib.SMTP(notification_host, notification_port)
        smtpObj.sendmail(notification_from, [notification_to], message.as_string())
    except smtplib.SMTPException as e:
        logging.error(e, exc_info=True)


## main
if __name__ == "__main__":
    ## get option
    opts,_ = getopt.getopt(sys.argv[1:], "-c:-q:-n", ["config=","query=","notrace"])
    
    config = ""
    query = ""
    no_trace = False
    for opt_name, opt_value in opts:
        if opt_name in ('-c','--config'):
            config = opt_value
        if opt_name in ('-q','--query'):
            query = opt_value
        if opt_name in ('-n','--notrace'):
            no_trace = True

    ## check option
    if config == "":
        raise Exception("OPTION '-c' or '--config' MUST exists")
    
    if query == "":
        raise Exception("OPTION '-q' or '--query' MUST exists")
    
    ## setting logging
    dirname, filename = os.path.split(config)
    collector_id = "{}_{}".format(COLLECTOR_NAME, filename[:filename.find('.')])
    if dirname != "":
        dirname = dirname + "/"
    log_file = "{}{}.log".format(dirname, collector_id)
    print("log file location: " + log_file)
    logging.basicConfig(
        filename=log_file,
        filemode="a",
        format="%(asctime)s %(name)s %(levelname)s %(funcName)s %(lineno)d > %(message)s",
        level=logging.INFO)
    console = logging.StreamHandler()
    console.setLevel(logging.INFO)
    console.setFormatter(logging.Formatter("%(asctime)s %(name)s %(levelname)s %(funcName)s %(lineno)d > %(message)s"))
    logging.getLogger("").addHandler(console)
    logging.info("Collector STARTUP with config '{}' and query '{}'".format(config, query))

    ## load config
    with open(config,encoding='utf-8') as file:
        configData = yaml.load(file, Loader=yaml.FullLoader)
        gerrit_url = configData["gerrit"]["url"]
        oracle_dsn = configData["oracle"]["dsn"]
        oracle_user = configData["oracle"]["user"]
        oracle_password = configData["oracle"]["password"]
        notification_host = configData["notification"]["host"]
        notification_port = configData["notification"]["port"]
        notification_from = configData["notification"]["from"]
        notification_to = configData["notification"]["to"]

    try:
        main(sys.argv[1:])
    except Exception as e:
        logging.error(e,exc_info=True)
        notifationIfFailed()
        exit(128)

