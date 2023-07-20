// the script will be executed when review create event rise.
// subject is class com.intland.codebeamer.persistence.dto.ReviewDto

def USER_GROUP_REFERENCE_ID = 1001

import com.intland.codebeamer.event.util.VetoException
import java.util.List
import com.intland.codebeamer.persistence.dao.ArtifactDao
import org.springframework.webflow.execution.RequestContextHolder

def artifactDao = applicationContext.getBean(ArtifactDao.class)

if (!beforeEvent) {
    return    // do NOTHING on after-event, everything is already handled in the before-event!
}

logger.info("the script will be executed when review create event rise.")
logger.info("subject = $subject")

def usergroup = artifactDao.findById(USER_GROUP_REFERENCE_ID)
logger.info("usergroup = $usergroup")

// A context for a single request to manipulate a flow execution. 
// Allows Web Flow users to access contextual information about the executing request, 
// as well as the governing active flow execution. 
// see also: https://docs.spring.io/spring-webflow/docs/current/api/org/springframework/webflow/execution/RequestContext.html
// 
// def rc = RequestContextHolder.getRequestContext()

// Provides contextual information about a flow execution. 
// A flow execution is an runnable instance of a FlowDefinition.
// see also: https://docs.spring.io/spring-webflow/docs/current/api/org/springframework/webflow/execution/FlowExecutionContext.html
// 
// def ec = rc.getFlowExecutionContext()

// A single, local instantiation of a flow definition launched within an overall flow execution. 
// see also: https://docs.spring.io/spring-webflow/docs/current/api/org/springframework/webflow/execution/FlowSession.html
//
// def session = ec.getActiveSession()

//
// fetch review subject's project
// 
// def scope = session.getScope()
// def reviewSubjects = scope.get("reviewSubjects")
// def roTrackerDto = reviewSubjects.get(0)
// def projectDto = roTrackerDto.getProject()
// logger.info("projectDto = $projectDto")


// set default user group to viewers
// subject.setViewers([usergroup])

