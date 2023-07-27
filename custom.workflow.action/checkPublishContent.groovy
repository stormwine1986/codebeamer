// check publish content
//	1. ALM Tracher Item MUST NOT be "In progress"
//	2. Auto create review for non-ALM contents
//	3. if all content is ALM content and all items MUST NOT be "In progess", workflow entry "Build Version"
//	4. if any non-ALM content exsists and all alm content MUST NOT be "In progess", workflow entry "Pending Review"
//	5. if any alm item at "In progress", workflow SHOULD NOT be start

if(!beforeEvent) return

if(user.getName() != "bond") return // just for gray release

import com.intland.codebeamer.event.util.VetoException
import com.intland.codebeamer.manager.TrackerItemManager
import com.intland.codebeamer.persistence.dto.TrackerItemDto
import com.intland.codebeamer.persistence.util.*
import com.intland.codebeamer.persistence.dto.ReviewDto
import com.intland.codebeamer.controller.support.review.ReviewSupport

// Defined Constant
def FIELD_ID_CONTENT_TYPE = 7 // Field Id of "Content Type" which in "Publish Content", values e.g ALM,GIT,SVN
def FIELD_ID_RELATED_TRACKER = 6 // Field Id of "Related Tracker" which in "Publish Content". 

def trackerItemMgr = applicationContext.getBean(TrackerItemManager.class)
def reviewSupport = applicationContext.getBean(ReviewSupport.class)
def flags = [TrackerItemDto.Flag.InProgress] as Set
def criteria = new Criteria()
criteria.add(TrackerItemRestrictions.getFlagsCriterion(flags,true,false))

def subjects = subject.getSubjects()

// pcs: publish cotents
def pcs = trackerItemMgr.findById(user,subjects)
logger.info("pcs = $pcs")

def alm = [] // alm content, such as ALM
def nonalm = [] // non-alm content, such as SVN,GIT
def reviewers = [] // reviewers for non-alm content review
pcs.each{
	it ->
	def options = it.getChoiceList(FIELD_ID_CONTENT_TYPE)
	if(options[0].getName()=="ALM"){
		alm.add(it)
	}else{
		nonalm.add(it)
		reviewers.addAll(it.getAssignedTo())
	}
}
logger.info("alm = $alm")
logger.info("nonalm = $nonalm")

// check akm content
//	1. ALM Tracher Item MUST NOT be "In progress"
def nosatifies = []
alm.each{
	it ->
	def options = it.getChoiceList(FIELD_ID_RELATED_TRACKER)
	logger.info("options = $options")
	// query tracker items which status in progress
	def finds = trackerItemMgr.findByTracker(user,options,null,criteria)
	logger.info("finds=$finds")
	if(!finds.isEmpty()) nosatifies.addAll(options) 	
}
logger.info("nosatifies=$nosatifies")
def tips = nosatifies.collect{it -> return it.getName()}
if(!nosatifies.isEmpty()) throw new VetoException("$tips 不满足发布条件。")

// auto create review for non alm content
if(!nonalm.isEmpty()){
	def review = new ReviewDto()
	review.setName(subject.getName())
	review.setDescription(subject.getDescription())
	review.setStartDate(new Date())
	review.setCreatedFromGuard(false)
	review.setIsMergeRequest(false)
	// current user is moderators
	review.setModerators([user])
	// content owner is reviewers
	review.setReviewers(reviewers)
	review.setViewers(reviewers)
	review.setIsNecessaryBaselineSignature(false)
	review.setNotifyModerators(false)
	review.setNotifyReviewers(false)
	review.setNotifyOnItemUpdate(false)
	review.setMinimumSignaturesRequired(0)
	review.setRequiresSignatureFromReviewers(false)
	reviewSupport.createReview(review, nonalm, user, event.getRequest(), null, false)
}

throw new VetoException("just for debug") // just for debug
