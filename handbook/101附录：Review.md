# 附录：Review

groovy：根据条目是否关联未解决的问题设置条目的状态

```groovy
import com.intland.codebeamer.controller.QueriesController
import com.intland.codebeamer.event.util.VetoException
import com.intland.codebeamer.controller.TrackerItemController
import com.intland.codebeamer.persistence.dto.base.NamedDto

if(!beforeEvent) return
// 评审即将完成时，根据评审条目是否关联未解决的问题设置条目的状态

queries = applicationContext.getBean(QueriesController.class)
controller  = applicationContext.getBean(TrackerItemController.class)

def draft = new NamedDto(2)
def accepted = new NamedDto(5)

def trackerItemDtos = []

// subject 是评审对象
subject.subjects.each{ s -> 
  // 1.获取条目对象
  def trackerItemDto = controller.getTrackerItem(event.request, s.id)
  trackerItemDto = trackerItemDto.clone()
  trackerItemDtos << trackerItemDto
  // 2.检查条目是否关联未解决的问题
  if(hasUnresolvedIssue(s.id)){
    // 2.1 有未解决的问题，条目状态提交到草稿
    logger.info("submit ${s.id} to draft")
    trackerItemDto.status = draft
  }else{
    // 2.2 问题已经全部解决，条目状态提交到接受
    logger.info("submit ${s.id} to accepted")
    trackerItemDto.status = accepted
  }
}

// 3. 提交修改
controller.updateTrackerItems(event.request, trackerItemDtos)

def hasUnresolvedIssue(reqId){
    // 针对条目，查询处于'新建','已分派'状态下的问题
    def /*QueriesResult*/ result = queries.executeQueryByQueryString(event.request, /*pageNo*/ 1, /*pageSize*/ 1, /*queryString*/ "tracker.id IN (4771) AND 'status' IN ('新建','已分派') AND SubjectID IN ($reqId)")
    return result.trackerItems.fullListSize > 0
}
```

groovy: 创建关联条目的问题时，默认关联评审对象，默认设置问题解决人

```groovy
import com.intland.codebeamer.controller.TrackerItemController
import com.intland.codebeamer.controller.QueriesController
import com.intland.codebeamer.event.util.VetoException

if(!beforeEvent) return

def controller = applicationContext.getBean(TrackerItemController.class)
def queries = applicationContext.getBean(QueriesController.class)

// 1. 获取问题关联的条目
def item = subject.subjects[0]
item = controller.getTrackerItem(event.request, item.id)

// 2. 从问题关联的条目上获取评审对象
def /*QueriesResult*/ result = queries.executeQueryByQueryString(event.request, /*pageNo*/ 1, /*pagesize*/ 1, /*queryString*/ "tracker.id IN (4424) AND 'status' IN ('评委初审','主审人终审') AND SubjectID IN (${item.id})")

if(result.trackerItems.fullListSize == 0) throw new VetoException("不存在正在进行中的评审，拒绝创建问题。") 

def /*TrackerItemDto*/ review = result.trackerItems.typedList[0]

// 3. 为当前问题设置评审对象
subject.setChoiceList(0, [review])

// 4. 为当前问题设置解决人
subject.assignedTo = item.assignedTo
```

