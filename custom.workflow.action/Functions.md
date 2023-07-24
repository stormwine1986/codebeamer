# Functions

## find Tracker by key name

```groovy
import com.intland.codebeamer.manager.TrackerManager

trackerMgr = applicationContext.getBean(TrackerManager.class)

def findTrackerByKeyname(keyname){
    // keyname string

    def result = null
    def trackerDtos = trackerMgr.findTrackers(user, subject.getProject().getId(), "REQUIREMENT,USERSTORY")
    trackerDtos.each{
        it ->
        if(keyname == it.getKeyName()){
            result = it
        }
    }

    // TrackerDto or null
    return result
}
```

## find first Tracker Item by key name

```groovy
import com.intland.codebeamer.manager.TrackerManager
import com.intland.codebeamer.manager.TrackerItemManager

trackerMgr = applicationContext.getBean(TrackerManager.class)
trackerItemMgr = applicationContext.getBean(TrackerItemManager.class)

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

    // TrackerItemDto or null
    return result
}
```
