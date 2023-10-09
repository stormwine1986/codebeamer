// updateOtherItem.groovy
// update other item with script

if(beforeEvent) return

// only subject is created, the other item could link the subject as upstream 
// so this code must execute after event

logger.info("==================> debug start")

import com.intland.codebeamer.manager.TrackerItemManager
import com.intland.codebeamer.persistence.dto.TrackerItemDto
import com.intland.codebeamer.persistence.dto.base.NamedDto
import com.intland.codebeamer.persistence.dao.TrackerItemDao

def trackerItemMgr = applicationContext.getBean(TrackerItemManager.class)
def trackerItemDao = applicationContext.getBean(TrackerItemDao.class)

def sr = trackerItemMgr.findById(user, 1004)
logger.info("sr = $sr")

// modify other item
def delta = sr.clone() 
def status = new NamedDto()
status.id = 4
delta.setStatus(status)

// add downstream for subject
delta.setSubjects([subject])

// save other item
trackerItemDao.update(delta)

logger.info("===============> debug end")
