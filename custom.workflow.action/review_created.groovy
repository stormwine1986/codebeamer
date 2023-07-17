// the script will be executed when review create event rise.
// subject is class com.intland.codebeamer.persistence.dto.ReviewDto

def USER_GROUP_REFERENCE_ID = 1001

import com.intland.codebeamer.event.util.VetoException
import java.util.List
import com.intland.codebeamer.persistence.dao.ArtifactDao

def artifactDao = applicationContext.getBean(ArtifactDao.class)

if (!beforeEvent) {
    return    // do NOTHING on after-event, everything is already handled in the before-event!
}

logger.info("the script will be executed when review create event rise.")
logger.info("subject = $subject")

def usergroup = artifactDao.findById(USER_GROUP_REFERENCE_ID)
logger.info("usergroup = $usergroup")

// set default user group to viewers
subject.setViewers([usergroup])

