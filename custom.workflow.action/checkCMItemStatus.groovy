// Groovy script implements function that check 'CM Item' status which related the subject.
//
// Data Structure
//   Tracker'CM Item' with key named 'CM-ITEM'
//     Has Status 'BASELINED' which id is 3
//     Has Field 'CB Tracker' which is choice list and id is 2, this field use to link a Tracker such as Tracker'System Requirement'
//
// Scene
//   When the CM item's status is baselined, we CANNOT create a new system requirement item. other else we can create a new one.
//
// the Script MUST be installed at the right place, for example the transition from '--' to 'New', and MUST set 'Veto on exception' is TRUE.
// 
// Adjust paramezers below and copy to <CBInstall>/tomcat/webapps/ROOT/config/scripts/workflow
def TRACKER_KEY = "CM-ITEM"
def CB_TRACKER_CHOICE_LIST_IDX = 2
def BASELINED_STATUS_IDX = 3

import com.intland.codebeamer.manager.CbQLManager
import com.intland.codebeamer.manager.CbQLManager.PagingParams
import com.intland.codebeamer.manager.TrackerManager
import com.intland.codebeamer.event.util.VetoException

cbqlMgr = applicationContext.getBean(CbQLManager.class)
trackerMgr = applicationContext.getBean(TrackerManager.class)

if (!beforeEvent) {
    return    // do NOTHING on after-event, everything is already handled in the before-event!
}

logger.info("--------------------------------------")

logger.info("subject = $subject")

def projectDto = subject.getProject()
def subjectTrackerDto = subject.getTracker()
def trackerDto = lookupTrackerDto(projectDto, TRACKER_KEY)

logger.info("project = $projectDto")
logger.info("subjectTrackerDto = $subjectTrackerDto")
logger.info("trackerDto = $trackerDto")

// no CM-ITEM tracker means no more check
if(trackerDto == null){
    return
}

def binding = ["projectId":projectDto.getId(),"trackerId":trackerDto.getId(),"fieldIdx":CB_TRACKER_CHOICE_LIST_IDX,"subjectTrackerId":subjectTrackerDto.getId()]
def cbQl = 'project.id IN ($projectId) AND tracker.id IN ($trackerId) AND \'${trackerId}.choiceList[$fieldIdx]\' IN ($subjectTrackerId)'
def engine = new groovy.text.SimpleTemplateEngine()
def template = engine.createTemplate(cbQl).make(binding)

logger.info("template = ${template.toString()}")

def result = cbqlMgr.runQuery(event.getRequest(), user, template.toString(), new PagingParams(1000, 0)).getTrackerItems().getList()
logger.info("result = $result")

// no related cm item means no more check
if(result.size() == 0){
    return
}

def trackerItemDto = result.get(0)
def statusNamedDto = trackerItemDto.getStatus()

logger.info("statusNamedDto = $statusNamedDto")

if(statusNamedDto.getId() == BASELINED_STATUS_IDX){
    throw new VetoException("Tracker is locked, because related CM Item status is BASELINED.");
}
1

// Lookup TrackerDto By key in the scope of projectDto
def lookupTrackerDto(projectDto, key) {
    def result = null
    def trackerDtos = trackerMgr.findByProject(user, projectDto)
    trackerDtos.each{it ->
        if(it.getKeyName() == key){
            result = it
        }
    }
    return result
}