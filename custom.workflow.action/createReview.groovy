
if (!beforeEvent) {
    return;    // do NOTHING on after-event, everything is already handled in the before-event!
}

import com.intland.codebeamer.event.util.VetoException
import com.intland.codebeamer.persistence.dto.ReviewDto
import com.intland.codebeamer.controller.support.review.ReviewSupport
import com.intland.codebeamer.persistence.dao.TrackerDao

logger.info("-------------------------------------")
logger.info("subject = $subject")

def trackerDao = applicationContext.getBean(TrackerDao.class);
def reviewSupport = applicationContext.getBean(ReviewSupport.class);


def users = [user]

def reviewDto = new ReviewDto()
reviewDto.setName("Auto Created Review")
reviewDto.setDescription("--")
reviewDto.setStartDate(new Date())
reviewDto.setCreatedFromGuard(false)
reviewDto.setIsMergeRequest(false)
// reviewDto.setEndDate(new Date())
reviewDto.setIsPublicReview(true)
reviewDto.setReviewers(users)
reviewDto.setModerators(users)
reviewDto.setViewers(users)
reviewDto.setIsNecessaryBaselineSignature(false)
reviewDto.setNotifyModerators(false)
reviewDto.setNotifyReviewers(false)
reviewDto.setNotifyOnItemUpdate(false)
reviewDto.setMinimumSignaturesRequired(0)
reviewDto.setRequiresSignatureFromReviewers(false)



reviewSupport.createReview(reviewDto, subject.getSubjects(), user, event.getRequest(), null, false)




throw new VetoException("see log")