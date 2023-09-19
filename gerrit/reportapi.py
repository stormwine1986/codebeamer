# reportapi.py

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import uvicorn
import oracledb
from datetime import datetime

app = FastAPI()

origins = ['*']

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/gerrit/data/personal")
def getGerritData(
        start_time: str,
        end_time: str,
        project:str = ""):
    # datetime format 2023-09-23 10:58:00
    cur = con.cursor()

    sql = """
    SELECT 
        OWNER,
        RESOURCE_GROUP,
        sum(cm.INSERTIONS) AS INSERTIONS, 
        sum(cm.DELETIONS) AS DELETIONS, 
        sum(cm.INSERTIONS) + sum(cm.DELETIONS) AS TOTAL_CHANGED,
        sum(1) AS PATCH_COUNT
    FROM CHANGE_MASTER cm 
    WHERE
        cm.CREATED BETWEEN to_timestamp(:1, 'yyyy-mm-dd hh24:mi:ss') AND to_timestamp(:2, 'yyyy-mm-dd hh24:mi:ss')
        {}
    GROUP BY (OWNER,RESOURCE_GROUP)
    """

    params = [start_time, end_time]
    
    if project != "":
        sql = sql.format("AND cm.PROJECT = :3")
        params.append(project)
    else:
        sql = sql.format("")


    results = []
    for row in cur.execute(sql, params):
        results.append(
            {
                "username": row[0],
                "resource_group": row[1],
                "insertions": row[2],
                "deletions": row[3],
                "total": row[4],
                "patch_count": row[5]
            }
        )

    cur.close()
    return results

if __name__ == '__main__':
    con = oracledb.connect(dsn="10.125.1.81/ALMCBTEST",user="gerrit",password="gerrit")
    print("Database Version: " + con.version)
    uvicorn.run(app=app,host="0.0.0.0",port=9000)
