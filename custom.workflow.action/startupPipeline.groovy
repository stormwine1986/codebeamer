// startupPipeline
// codebeamer build task startup a jenkins pipline with some parameters 

if(beforeEvent) {
  // generate report info link
  subject.setCustomField(1, "[show|http://alm.cb.test:8080/cb/demo/index.html?taskId=${subject.id}]")
  return
}

logger.info("====> startupPipeline.groovy")

import java.net.URL
import java.net.HttpURLConnection
import java.io.DataOutputStream
import java.util.LinkedHashMap
import java.lang.StringBuffer
import java.net.URLEncoder
import java.lang.String
import com.intland.codebeamer.event.util.VetoException
import java.util.Base64

logger.info("param1 = ${subject.getCustomField(2)}")
logger.info("param2 = ${subject.getCustomField(3)}")
logger.info("param3 = ${subject.getChoiceList(0).get(0).name}")
logger.info("param4 = ${subject.getCustomField(4)}")

// set parameters for pipeline
def params = new LinkedHashMap()
params.put("param1",subject.getCustomField(2))
params.put("param2",subject.getCustomField(3))
params.put("param3",subject.getChoiceList(0).get(0).name)
params.put("param4",subject.getCustomField(4))
params.put("taskId",subject.id)

def postData = new StringBuffer()
params.each{
  it ->
  if(postData.length() != 0) postData.append('&')

  postData.append(URLEncoder.encode(it.getKey(), "UTF-8"))
  postData.append('=')
  postData.append(URLEncoder.encode(String.valueOf(it.getValue()), "UTF-8"))
}
def postDataBytes = postData.toString().getBytes("UTF-8")

// send request to jenkins
def url = new URL("http://alm.cb.test:8090/job/codebeamer/buildWithParameters?token=codebeamer")
def conn = (HttpURLConnection) url.openConnection()
conn.setRequestMethod("POST")
conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
conn.setRequestProperty("Authorization", "Basic " + new String(Base64.getEncoder().encode("jenkins:11aaf5e696aafa790c15e5d6dc0efde6c5".getBytes("UTF-8"))))
conn.setDoOutput(true)
conn.getOutputStream().write(postDataBytes)

// show jenkins response code
def statusCode = conn.getResponseCode()
logger.info("status code = $statusCode ")

logger.info("====> startupPipeline.groovy end")

