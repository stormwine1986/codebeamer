// JUST for Debug

if (!beforeEvent) {
    return    // do NOTHING on after-event, everything is already handled in the before-event!
}

import com.intland.codebeamer.controller.rest.v2.TrackerItemReferenceRestController
import com.intland.codebeamer.event.util.VetoException

def controller = applicationContext.getBean(TrackerItemReferenceRestController.class)

def result = controller.getBaselineTrackerItemRelations(user,subject.id,null,1,500)

result.downstreamReferences.each{
    it -> logger.info("downstreams.*.id = ${it.itemRevision.id}")
}
throw new VetoException("debug")
