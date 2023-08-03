/**
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
**/