// Auto Set Review Role
// $CB_HOME/tomcat/webapps/cb/config/script/workflow/AutoSetReviewRole.groovy

//if(user.name!="bond") return // just for gray release

if(!beforeEvent) return

import com.intland.codebeamer.event.util.VetoException
import com.intland.codebeamer.manager.ProjectManager
import com.intland.codebeamer.persistence.dto.ProjectRoleReferenceDto
import com.intland.codebeamer.manager.RoleManager
import com.intland.codebeamer.manager.TrackerManager
import com.intland.codebeamer.manager.TrackerItemManager

projectMgr = applicationContext.getBean(ProjectManager.class)
roleMgr = applicationContext.getBean(RoleManager.class)
trackerMgr = applicationContext.getBean(TrackerManager.class)
trackerItemMgr = applicationContext.getBean(TrackerItemManager.class)

// Defined Constant
def TRACKER_KEY_NAME = "PROJECT"	// Key name of Tracker named "项目属性"
def TRACKER_TYPE = "AREA"		    // Type of Tracker named "项目属性"
def FIELD_ID_ROLEMAPPING = 1		// Field Id of "缺陷审核小组配置" in "项目属性"
def FIELD_ID_SCOPE = 15             // Field Id of "Defect Scope" in "Defect"
def FIELD_ID_LEVEL = 13             // Field Id of "Defect Level" in "Defect"
def FIELD_ID_PHASE = 8              // Field Id of "Project Phase" in "Defect"
def FIELD_ID_REVIEW_ROLE = 9        // Field Id of "Defect Review Group" in "Defect"

def trackers = trackerMgr.findTrackers(user, subject.project.id, "AREA")
def tracker = null
trackers.each{
	it ->
	if(it.keyName==TRACKER_KEY_NAME) tracker = it
}

def scope = subject.getChoiceList(FIELD_ID_SCOPE)[0]
def level = subject.getChoiceList(FIELD_ID_LEVEL)[0]
def phase = subject.getChoiceList(FIELD_ID_PHASE)[0]
def reviewRole = subject.getChoiceList(FIELD_ID_REVIEW_ROLE)

if(tracker==null&&(reviewRole==null || reviewRole.isEmpty())) throw new VetoException("不满足约束：缺陷审核小组必须设置。")

def trackerItems = trackerItemMgr.findByTracker(user, [tracker], null)

if(trackerItems.isEmpty()&&(reviewRole==null || reviewRole.isEmpty())) throw new VetoException("不满足约束：缺陷审核小组必须设置。")

def configHolder = trackerItems.get(0)
logger.info("configObj = $configHolder")
def rolemapping = configHolder.getTable(FIELD_ID_ROLEMAPPING)
logger.info("rolemapping = $rolemapping")

logger.info("scope = ${scope} , level = ${level} , phase = ${phase}")

def apply = null
rolemapping.each{
	it ->
	if(it.keySet().size() == 4){
		if(it.get(0) == "${scope.id}" && it.get(1) == "${level.id}" && it.get(2) == "${phase.id}") {
			apply = it
			setRole(it.get(3))
		}
	}
}

logger.info("apply = $apply")
if(apply == null && (reviewRole==null || reviewRole.isEmpty())) throw new VetoException("不满足约束：缺陷审核小组必须设置。")

def setRole(roleId){
	logger.info("role id = $roleId")
	def ids = roleId.split(",").collect{
		it -> return it.split("-")[1]
	}
	logger.info("ids = $ids")
	def roles = roleMgr.findById(user, ids)
	logger.info("roles = $roles")
	subject.setChoiceList(FIELD_ID_REVIEW_ROLE, roles)
}

//throw new VetoException("see log") // just for debug
