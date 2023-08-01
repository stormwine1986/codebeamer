// Auto Set Review Role
// $CB_HOME/tomcat/webapps/cb/config/script/workflow/AutoSetReviewRole.groovy

//if(user.name!="bond") return

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
def TRACKER_TYPE = "AREA"		// Type of Tracker named "项目属性"
def FIELD_ID_ROLEMAPPING = 1		// Field Id of "缺陷审核小组配置" in "项目属性"

def trackers = trackerMgr.findTrackers(user, subject.project.id, "AREA")
def tracker = null
trackers.each{
	it ->
	if(it.keyName==TRACKER_KEY_NAME) tracker = it
}

def scope = subject.getChoiceList(15)[0]
def level = subject.getChoiceList(13)[0]
def phase = subject.getChoiceList(8)[0]
def reviewRole = subject.getChoiceList(9)

if(tracker==null&&(reviewRole==null || reviewRole.isEmpty())) throw new VetoException("不满足约束：缺陷审核小组必须设置。")

def trackerItems = trackerItemMgr.findByTracker(user, [tracker], null)

if(trackerItems.isEmpty()&&(reviewRole==null || reviewRole.isEmpty())) throw new VetoException("不满足约束：缺陷审核小组必须设置。")

def configHolder = trackerItems.get(0)
logger.info("configObj = $configHolder")
def rolemapping = configHolder.getTable(FIELD_ID_ROLEMAPPING)
logger.info("rolemapping = $rolemapping")

//if((phase.name=="TR1"||phase.name=="TR2")&&reviewRole.isEmpty()) throw new VetoException("不满足约束：缺陷审核小组必须设置。")

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
	subject.setChoiceList(9, roles)
}

//throw new VetoException("see log")
