// JUST for Debug

if (!beforeEvent) {
    return    // do NOTHING on after-event, everything is already handled in the before-event!
}

import com.intland.codebeamer.manager.TrackerManager
import com.intland.codebeamer.manager.TrackerItemManager
import com.intland.codebeamer.event.util.VetoException

trackerMgr = applicationContext.getBean(TrackerManager.class)
trackerItemMgr = applicationContext.getBean(TrackerItemManager.class)

def trackerItem = findFirstTrackerItem("CRS")
logger.info("trackerItem = $trackerItem")

def findFirstTrackerItem(keyname){
    // keyname string

    def result = null
    def tracker = null

    def trackerDtos = trackerMgr.findTrackers(user, subject.getProject().getId(), "REQUIREMENT,USERSTORY")
    trackerDtos.each{
        it ->
        logger.info("it.getKeyName() = ${it.getKeyName()}")
        if(keyname == it.getKeyName()){
            tracker = it
        }
    }
    if(tracker!=null){
        def trackerItems = trackerItemMgr.findByTracker(user, [tracker], null)
        if(trackerItems.size()>0){
            result = trackerItems.get(0)
        }
    }

    // TrackerItemDto
    return result
}

throw new VetoException("debug")