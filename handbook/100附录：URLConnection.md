# 附录：URLConnection

## 在 groovy 脚本中发起 POST 请求

groovy

```groovy
import java.net.URL
import java.net.HttpURLConnection
import java.util.Base64
import java.io.OutputStreamWriter
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.IOUtils;

if(!beforeEvent) return

def url = new URL("http://localhost:8080/rest/item")
def conn = (HttpURLConnection)url.openConnection()
conn.setRequestMethod("POST")
logger.info("${conn.requestMethod} $url")

// 有请求体的必须设置为true
conn.setDoOutput(true)

// Request Headers
conn.setRequestProperty("Content-Type","application/json;charset=UTF-8")
def base64 = Base64.getEncoder().encode(("bond:007").getBytes())
conn.setRequestProperty("Authorization","Basic " + new String(base64))

// Request Body
def requestBody = new JSONObject();
requestBody.put("tracker", "/project/3/tracker/3383");
requestBody.put("type", "Folder");
requestBody.put("name", "文件夹");
requestBody.put("description", "--");
logger.info("requestBody = $requestBody")

def writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")
writer.write(requestBody.toString())
writer.flush()
writer.close()

logger.info("http.status = ${conn.responseCode}")
// Response Body
def responseBody = IOUtils.toString(conn.getInputStream(), "UTF-8")

conn.disconnect()
```



## 在 groovy 脚本中发起 GET 请求

groovy

```groovy
import java.net.URL
import java.net.HttpURLConnection
import java.util.Base64
import java.io.OutputStreamWriter
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.IOUtils;

if(!beforeEvent) return

def url = new URL("http://localhost:8080/rest/item/1002")
def conn = (HttpURLConnection)url.openConnection()
// Request Method
conn.setRequestMethod("GET")
logger.info("GET $url")

// Request Headers
def base64 = Base64.getEncoder().encode(("bond:007").getBytes())
conn.setRequestProperty("Authorization","Basic " + new String(base64))

logger.info("http.status = ${conn.responseCode}")
// Response Body
def responseBody = IOUtils.toString(conn.getInputStream(), "UTF-8")

conn.disconnect()
```



## 在 groovy 脚本中发起 PUT 请求

groovy

```groovy
import java.net.URL
import java.net.HttpURLConnection
import java.util.Base64
import java.io.OutputStreamWriter
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.IOUtils;

if(!beforeEvent) return

def url = new URL("http://localhost:8080/rest/item")
def conn = (HttpURLConnection)url.openConnection()
conn.setRequestMethod("PUT")
logger.info("${conn.requestMethod} $url")

// 有请求体的必须设置为true
conn.setDoOutput(true)

// Request Headers
def base64 = Base64.getEncoder().encode(("bond:007").getBytes())
conn.setRequestProperty("Authorization","Basic " + new String(base64))
conn.setRequestProperty("Content-Type","application/json;charset=UTF-8")

// Request Body
def requestBody = new JSONObject();
requestBody.put("uri", "/item/1002");
requestBody.put("name", "重命名");
logger.info("requestBody = $requestBody")

def writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")
writer.write(requestBody.toString())
writer.flush()
writer.close()

logger.info("http.status = ${conn.responseCode}")
// Response Body
def responseBody = IOUtils.toString(conn.getInputStream(), "UTF-8")

conn.disconnect()
```



## 在 groovy 脚本中发起 DELETE 请求

groovy

```groovy
import java.net.URL
import java.net.HttpURLConnection
import java.util.Base64
import java.io.OutputStreamWriter
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.IOUtils;

if(!beforeEvent) return

def url = new URL("http://localhost:8080/rest/item/1002")
def conn = (HttpURLConnection)url.openConnection()
conn.setRequestMethod("DELETE")
logger.info("${conn.requestMethod} $url")

// Request Headers
def base64 = Base64.getEncoder().encode(("bond:007").getBytes())
conn.setRequestProperty("Authorization","Basic " + new String(base64))

logger.info("http.status = ${conn.responseCode}")
// Response Body
def responseBody = IOUtils.toString(conn.getInputStream(), "UTF-8")

conn.disconnect()
```

## 在 groovy 脚本中发起 multipart 请求

groovy

```groovy
import java.net.URL
import java.net.HttpURLConnection
import java.util.Base64
import java.io.OutputStreamWriter
import org.json.JSONObject;
import org.json.JSONArray;
import org.apache.commons.io.IOUtils;
import java.util.UUID;

if(!beforeEvent) return

def url = new URL("http://localhost:8080/rest/file")
def conn = (HttpURLConnection)url.openConnection()
conn.setRequestMethod("POST")
logger.info("${conn.requestMethod} $url")

conn.setDoOutput(true);
conn.setDoInput(true);

// 1. 设置请求头
def base64 = Base64.getEncoder().encode(("bond:007").getBytes())
conn.setRequestProperty("Authorization","Basic " + new String(base64))
def boundary = UUID.randomUUID().toString()
conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

def writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8")
// 2. 添加 json body
def body = new JSONObject()
body.put("name","README.md")
body.put("project","/project/3")

writer.append("--" + boundary).append("\r\n");
writer.append("Content-Disposition: form-data; name=\"body\"").append("\r\n");
writer.append("Content-Type: application/json; charset=UTF-8").append("\r\n");
writer.append("\r\n");
writer.append(body.toString()).append("\r\n");
writer.flush();

// 3. 添加文件
writer.append("--" + boundary).append("\r\n");
writer.append("Content-Disposition: form-data; name=\"README.md\"; filename=\"README.md\"").append("\r\n");
writer.append("Content-Type: text/plain").append("\r\n");
writer.append("\r\n");
writer.flush();
writer.append("#Hello World");
writer.append("\r\n");
writer.flush();

// 4. 结束 multipart
writer.append("--" + boundary + "--").append("\r\n");
writer.close();

// 4. 检查响应
logger.info("http.status = ${conn.responseCode}")
conn.disconnect()
```

