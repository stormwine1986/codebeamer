logger.info("beforeEvent = $beforeEvent") // beforeEvent: java.lang.Boolean
logger.info("script = ${script.name}") // script: java.io.File
logger.info("user = $user") // user: com.intland.codebeamer.persistence.dto.UserDto
logger.info("applicationContext = $applicationContext") // applicationContext: org.springframework.context.ApplicationContext
logger.info("listener = $listener") // listener: com.intland.codebeamer.event.impl.custom.TrackerItemListenerScriptWrapper
logger.info("event = $event") // event: com.intland.codebeamer.event.BaseEvent
logger.info("event.request = ${event.request}") // event.request: javax.servlet.http.HttpServletRequest
logger.info("event.data = ${event.data}") // event.data: com.intland.codebeamer.manager.util.ActionData
logger.info("event.source = ${event.source}") // event.source: com.intland.codebeamer.persistence.util.TrackerItemAttachmentGroup
logger.info("event.secondarySource = ${event.secondarySource}") // event.secondarySource: [com.intland.codebeamer.persistence.dto.AccessPermissionDto]

logger.info("AttachmentArtifacts: ")
event.source.attachmentArtifacts.each{
    it -> 
    logger.info("it = $it")
    logger.info("path = ${it.path}")
    logger.info("map = ${it.attributes}")
}

logger.info("ActionData: ")
logger.info("data.attachmentName = ${event.data.attachmentName}")
logger.info("data.input = ${event.data.input}")
event.data.uploads.each{
    it ->
    logger.info("filename ${it.fileName} file ${it.file}")
}

/**
logger.info("HTTP Request: ")
if(!beforeEvent){
    event.request.parts.each{
        it ->
        logger.info("name ${it.name} size ${it.size} stream ${it.inputStream}")
    }
}
**/

// Attacher