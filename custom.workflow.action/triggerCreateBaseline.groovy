// Groovy script implements function that invoke baseline creation service.
//
// Data Structure
//   'Baseline Action' -- subjects --> 'CM Item'
//   'CM Item' has fields:
//     id: Tracker Item id
//     SCM Type: Choice, 'Type for SCM, such as GIT,SVN,CB'
//     SCM Path: Text, 'if SCM type is GIT or SVN, SCM Path is path for config item'
//     CB Tracker: Choice, 'if SCM type is CB, CB Tracker related to a Tracker'
//
// Scenes:
//   when the 'Baseline Action' entering state thar named 'Baselined', the trigger will inovke external baseline creation service to create baseline
//   for related 'CM Item'. the external baseline creation service need kown where the config item is. these information as below SHOULD be submitted:
//     Project's Id: used for create CB baseline
//     CM Item's Id: uesd for write back status for 'CM Item'
//     CM Item's ScmType: which type of scm the config item stored
//     CM Item's ScmPath: if config item stored external, the path show the location
//     CM Item's CB Tracker: if config item stored in CB, this is the related Tracker
//   if external service done succefully, return status code with 200, other else trigger will throw a VetoException then rollback transaction.
//
// Adjust paramezers below and copy to <CBInstall>/tomcat/webapps/ROOT/config/scripts/workflow

if (!beforeEvent) {
    return;    // do NOTHING on after-event, everything is already handled in the before-event!
}

if(user.name!="bond") return  // for gray release

// Defined Constant
def serviceUrl = "http://172.18.1.126:9000"     // target service root
def servicepath = "/codebeamerapi/tags/create"  // target service path
def serviceUser = "ALM"                         // service auth user
def servicePass = "123456"                      // service token
def BASELINE_NAME_CUSTOM_FIELD_IDX = 0          // Field ID of "Baseline Name" on Tracker named "Baseline Action"
def SCM_TYPE_CHOICE_LIST_IDX = 7                // Field ID of "SCM Type" on Tracker named "CM Item"
def SCM_PATH_CUSTOM_FIELD_IDX = 1               // Field ID of "SCM Path" on Tracker named "CM Item"
def CB_TRACKER_CHOICE_LIST_IDX = 6              // Field ID of "Tracker" on Tracker named "CM Item"

import com.intland.codebeamer.event.util.VetoException;
import org.json.JSONObject;
import org.json.JSONArray;
import com.intland.codebeamer.manager.TrackerItemManager;
import java.net.URL
import java.net.HttpURLConnection
import java.util.Base64
import java.io.OutputStreamWriter

def trackerItemMgr = applicationContext.getBean(TrackerItemManager.class);

logger.info("--------------------------------------");

logger.info("subject = ${subject}");

def upstreamNamedDtos = subject.getSubjects();
def projectDto = subject.getProject();
def baselineName = subject.getCustomField(BASELINE_NAME_CUSTOM_FIELD_IDX)

logger.info("upstreamNamedDtos = ${upstreamNamedDtos}");
logger.info("projectDto = ${projectDto}");

if(upstreamNamedDtos != null){

    def requestBody = new JSONObject();

    requestBody.put("projectId", projectDto.getId());
    requestBody.put("baselineName", baselineName);
    requestBody.put("items", new JSONArray());

    upstreamNamedDtos.each{it ->
        def item = new JSONObject();
        def trackItemDto = trackerItemMgr.findById(user, it.getId());
        item.put("id", trackItemDto.getId());
        item.put("scmType", trackItemDto.getChoiceList(SCM_TYPE_CHOICE_LIST_IDX)?.get(0)?.getName());
	      item.put("scmPath", (trackItemDto.getCustomField(SCM_PATH_CUSTOM_FIELD_IDX)==null)?JSONObject.NULL:trackItemDto.getCustomField(SCM_PATH_CUSTOM_FIELD_IDX));
        item.put("cbTracker", (trackItemDto.getChoiceList(CB_TRACKER_CHOICE_LIST_IDX)?.get(0)?.getId()==null)?JSONObject.NULL:trackItemDto.getChoiceList(CB_TRACKER_CHOICE_LIST_IDX)?.get(0)?.getId());
        requestBody.getJSONArray("items").put(item);
    }

    logger.info("requestBody = ${requestBody.toString()}");

    def url = new URL(serviceUrl+servicepath)
    def conn = (HttpURLConnection)url.openConnection()
    conn.setDoOutput(true)
    conn.setRequestMethod("POST")
    conn.setRequestProperty("Content-Type","application/json;charset=UTF-8")
    def base64 = Base64.getEncoder().encode((serviceUser+":"+servicePass).getBytes())
    conn.setRequestProperty("Authorization","Basic " + new String(base64))
    def writer = new OutputStreamWriter(conn.getOutputStream())
    writer.write(requestBody.toString())
    writer.flush()
    writer.close()
    logger.info("http.status = ${conn.responseCode}")

    if(conn.responseCode!=200) throw new VetoException("target service is not work, see logs for more.")
    
}

//throw new VetoException("see log")
