// updateOtherItem.groovy
// update other item with script

if(!beforeEvent) return

logger.info("==================> debug start")

import com.intland.codebeamer.manager.TrackerItemManager
import com.intland.codebeamer.persistence.dto.TrackerItemDto
import com.intland.codebeamer.persistence.dto.base.NamedDto
import com.intland.codebeamer.persistence.dao.TrackerItemDao

def trackerItemMgr = applicationContext.getBean(TrackerItemManager.class)
def trackerItemDao = applicationContext.getBean(TrackerItemDao.class)

def trackerItemDto = trackerItemMgr.findById(user, 1003)
logger.info("trackerItemDto = $trackerItemDto")

def newOne = trackerItemDto.clone() 
def status = new NamedDto()
status.id = 4
newOne.setStatus(status)
trackerItemDao.update(newOne)

logger.info("===============> debug end")
