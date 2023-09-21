// copyItem.groovy

if(beforeEvent) return

import com.intland.codebeamer.controller.TrackerItemController
import com.fasterxml.jackson.databind.ObjectMapper

def controller = applicationContext.getBean(TrackerItemController.class)

def jsonStr = """
{"copy":true,"origin":{"id":3384,"project":{"id":3}},"items":[1016],"destination":{"id":null,"tracker":{"id":3384},"position":null},"association":{"type":{"id":9,"name":"copy of"},"description":"","propagatingSuspects":true},"mapping":{},"clearClipboard":true}
"""
def mapper = new ObjectMapper()
def json = mapper.readTree(jsonStr)

def result = controller.copyTrackerItems(event.request, 3384, json)

logger.info("result = $result")

def items = result["items"]
items.each{
  it ->
  logger.info("old.id = ${it.old.id} new.id = ${it.new.id}")
}
