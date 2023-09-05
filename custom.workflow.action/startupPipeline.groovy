// codebeamer build task startup a jenkins pipline with some parameters 

if(beforeEvent) return

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

logger.info("bool = ${subject.getCustomField(3)}")
logger.info("choice = ${subject.getChoiceList(1).get(0).name}")
logger.info("multiline = ${subject.getCustomField(4)}")
logger.info("string = ${subject.getCustomField(5)}")

// set parameters for pipeline
def params = new LinkedHashMap()
params.put("bool",subject.getCustomField(3))
params.put("choice",subject.getChoiceList(1).get(0).name)
params.put("multiline",subject.getCustomField(4))
params.put("string",subject.getCustomField(5))
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
def url = new URL("http://docker-jenkins-1:8080/job/codebeamer/buildWithParameters?token=codebeamer")
def conn = (HttpURLConnection) url.openConnection()
conn.setRequestMethod("POST")
conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
conn.setRequestProperty("Authorization", "Basic " + new String(Base64.getEncoder().encode("bond:116c7fdcfa3a65ed91c7a0e1ff46e0c953".getBytes("UTF-8"))))
conn.setDoOutput(true)
conn.getOutputStream().write(postDataBytes)

// show jenkins response code
def statusCode = conn.getResponseCode()
logger.info("status code = $statusCode ")

logger.info("====> startupPipeline.groovy end")

