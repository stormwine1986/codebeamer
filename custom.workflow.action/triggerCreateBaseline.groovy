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
def serviceUrl = "http://localhost:8080"
def servicepath = "/path/to/service"
def serviceUser = "user"
def servicePass = "pass"
def SCM_TYPE_CHOICE_LIST_IDX = 2
def SCM_PATH_CUSTOM_FIELD_IDX = 1
def CB_TRACKER_CHOICE_LIST_IDX = 3


import com.intland.codebeamer.security.util.RemoteConnection;
import com.intland.codebeamer.security.util.RestHttpClient;
import com.intland.codebeamer.event.util.VetoException;
import org.json.JSONObject;
import org.json.JSONArray;
import com.intland.codebeamer.manager.TrackerItemManager;

def trackerItemMgr = applicationContext.getBean(TrackerItemManager.class);

if (!beforeEvent) {
    return;    // do NOTHING on after-event, everything is already handled in the before-event!
}

logger.info("--------------------------------------");

logger.info("subject = ${subject}");

def upstreamNamedDtos = subject.getSubjects();
def projectDto = subject.getProject();

logger.info("upstreamNamedDtos = ${upstreamNamedDtos}");
logger.info("projectDto = ${projectDto}");

if(upstreamNamedDtos != null){

    def requestBody = new JSONObject();

    requestBody.put("projectId", projectDto.getId());
    requestBody.put("items", new JSONArray());

    upstreamNamedDtos.each{it ->
        def item = new JSONObject();
        def trackItemDto = trackerItemMgr.findById(user, it.getId());
        item.put("id", trackItemDto.getId());
        item.put("scmType", trackItemDto.getChoiceList(SCM_TYPE_CHOICE_LIST_IDX)?.get(0).getName());
        item.put("scmPath", trackItemDto.getCustomField(SCM_PATH_CUSTOM_FIELD_IDX));
        item.put("cbTracker", trackItemDto.getChoiceList(CB_TRACKER_CHOICE_LIST_IDX)?.get(0).getId());
        requestBody.getJSONArray("items").put(item);
    }

    logger.info("requestBody = ${requestBody}");

    def client = new RestHttpClient(new RemoteConnection(serviceUrl,serviceUser,servicePass));
    def responseEntity = client.postForEntity(servicepath,requestBody.toString(),JSONObject.class);
    def statusCode = responseEntity.getStatusCode().value();
    def responseBody = responseEntity.getBody();
    logger.info("statusCode = ${statusCode}, responseBody = ${responseBody}");

    if(statusCode!=200){
        throw new VetoException("target service is not work, see logs for more.");
    }
}
