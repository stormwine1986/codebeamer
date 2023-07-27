// Auto Set Review Role
// When defect entry "Review for Close", codebeamer will autoset review role according to "scope","level" and "phase".

if(user.name!="bond") return // for gray release

if(!beforeEvent) return

import com.intland.codebeamer.event.util.VetoException
import com.intland.codebeamer.manager.ProjectManager
import com.intland.codebeamer.persistence.dto.ProjectRoleReferenceDto
import com.intland.codebeamer.manager.RoleManager

projectMgr = applicationContext.getBean(ProjectManager.class)
roleMgr = applicationContext.getBean(RoleManager.class)

// Defined Constant
def ROLE_ID_CHIP_SE = [64] 				// 芯片SE
def ROLE_ID_PM = [74]					// 项目经理
def ROLE_ID_MASTER = [74]				// 总工
def ROLE_ID_PQE = [34]					// PQE
def ROLE_ID_D_MGR = [52,47,33,67,70]	// 开发经理
def ROLE_ID_CHIP_RR = [69]				// 芯片研发代表
def ROLE_ID_SE_MASTER = [35]			// SE组长
def FIELD_ID_REVIEW_ROLE = 9			// Field Id of "缺陷审核小组" which in "Defect"

def scope = subject.getChoiceList(15)[0] 	// scope that defect located
def level = subject.getChoiceList(13)[0] 	// severity
def phase = subject.getChoiceList(8)[0]		// project phrase that defect found in
def reviewRole = subject.getChoiceList(9)	// current value of review role

// when phase is TR1 or TR2, review role MUST be filled by human 
if((phase.name=="TR1"||phase.name=="TR2")&&reviewRole.isEmpty()) throw new VetoException("不满足约束：缺陷审核小组必须设置。")

logger.info("scope.name = ${scope.name} , level.name = ${level.name} , phase.name = ${phase.name}")

// Rule Mapping
if(scope.name=="模块缺陷"&&level.name=="致命"&&phase.name=="TR4"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="模块缺陷"&&level.name=="致命"&&phase.name=="TR5"){setRole(ROLE_ID_PM)}
if(scope.name=="模块缺陷"&&level.name=="致命"&&phase.name=="TR6"){setRole(ROLE_ID_MASTER)}
if(scope.name=="模块缺陷"&&level.name=="致命"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="模块缺陷"&&level.name=="严重"&&phase.name=="TR4"){setRole(ROLE_ID_D_MGR)}
if(scope.name=="模块缺陷"&&level.name=="严重"&&phase.name=="TR5"){setRole(ROLE_ID_CHIP_RR)}
if(scope.name=="模块缺陷"&&level.name=="严重"&&phase.name=="TR6"){setRole(ROLE_ID_PM)}
if(scope.name=="模块缺陷"&&level.name=="严重"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="模块缺陷"&&level.name=="一般"&&phase.name=="TR4"){setRole(ROLE_ID_D_MGR)}
if(scope.name=="模块缺陷"&&level.name=="一般"&&phase.name=="TR5"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="模块缺陷"&&level.name=="一般"&&phase.name=="TR6"){setRole(ROLE_ID_CHIP_RR)}
if(scope.name=="模块缺陷"&&level.name=="一般"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="模块缺陷"&&level.name=="提示"&&phase.name=="TR4"){setRole(ROLE_ID_D_MGR)}
if(scope.name=="模块缺陷"&&level.name=="提示"&&phase.name=="TR5"){setRole(ROLE_ID_D_MGR)}
if(scope.name=="模块缺陷"&&level.name=="提示"&&phase.name=="TR6"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="模块缺陷"&&level.name=="提示"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="芯片缺陷"&&level.name=="致命"&&phase.name=="TR4"){setRole(ROLE_ID_CHIP_RR)}
if(scope.name=="芯片缺陷"&&level.name=="致命"&&phase.name=="TR5"){setRole(ROLE_ID_PM)}
if(scope.name=="芯片缺陷"&&level.name=="致命"&&phase.name=="TR6"){setRole(ROLE_ID_PM)}
if(scope.name=="芯片缺陷"&&level.name=="致命"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="芯片缺陷"&&level.name=="严重"&&phase.name=="TR4"){setRole(ROLE_ID_SE_MASTER)}
if(scope.name=="芯片缺陷"&&level.name=="严重"&&phase.name=="TR5"){setRole(ROLE_ID_CHIP_RR)}
if(scope.name=="芯片缺陷"&&level.name=="严重"&&phase.name=="TR6"){setRole(ROLE_ID_CHIP_RR)}
if(scope.name=="芯片缺陷"&&level.name=="严重"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="芯片缺陷"&&level.name=="一般"&&phase.name=="TR4"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="芯片缺陷"&&level.name=="一般"&&phase.name=="TR5"){setRole(ROLE_ID_SE_MASTER)}
if(scope.name=="芯片缺陷"&&level.name=="一般"&&phase.name=="TR6"){setRole(ROLE_ID_SE_MASTER)}
if(scope.name=="芯片缺陷"&&level.name=="一般"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="芯片缺陷"&&level.name=="提示"&&phase.name=="TR4"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="芯片缺陷"&&level.name=="提示"&&phase.name=="TR5"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="芯片缺陷"&&level.name=="提示"&&phase.name=="TR6"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="芯片缺陷"&&level.name=="提示"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="系统缺陷"&&level.name=="致命"&&phase.name=="TR4"){setRole(ROLE_ID_PM)}
if(scope.name=="系统缺陷"&&level.name=="致命"&&phase.name=="TR5"){setRole(ROLE_ID_PM)}
if(scope.name=="系统缺陷"&&level.name=="致命"&&phase.name=="TR6"){setRole(ROLE_ID_PM)}
if(scope.name=="系统缺陷"&&level.name=="致命"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="系统缺陷"&&level.name=="严重"&&phase.name=="TR4"){setRole(ROLE_ID_CHIP_RR)}
if(scope.name=="系统缺陷"&&level.name=="严重"&&phase.name=="TR5"){setRole(ROLE_ID_CHIP_RR)}
if(scope.name=="系统缺陷"&&level.name=="严重"&&phase.name=="TR6"){setRole(ROLE_ID_CHIP_RR)}
if(scope.name=="系统缺陷"&&level.name=="严重"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="系统缺陷"&&level.name=="一般"&&phase.name=="TR4"){setRole(ROLE_ID_SE_MASTER)}
if(scope.name=="系统缺陷"&&level.name=="一般"&&phase.name=="TR5"){setRole(ROLE_ID_SE_MASTER)}
if(scope.name=="系统缺陷"&&level.name=="一般"&&phase.name=="TR6"){setRole(ROLE_ID_SE_MASTER)}
if(scope.name=="系统缺陷"&&level.name=="一般"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

if(scope.name=="系统缺陷"&&level.name=="提示"&&phase.name=="TR4"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="系统缺陷"&&level.name=="提示"&&phase.name=="TR5"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="系统缺陷"&&level.name=="提示"&&phase.name=="TR6"){setRole(ROLE_ID_CHIP_SE)}
if(scope.name=="系统缺陷"&&level.name=="提示"&&phase.name=="GA"){setRole(ROLE_ID_PQE)}

def setRole(roleId){
	logger.info("role id = $roleId")
	def roles = roleMgr.findById(user, roleId)
	logger.info("roles = $roles")
	subject.setChoiceList(FIELD_ID_REVIEW_ROLE, roles)
}

//throw new VetoException("see log")
