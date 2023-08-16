// Groovy script implements a Jenkins-build
// Adjust paramezers below and copy to <CBInstall>/tomcat/webapps/ROOT/config/scripts/workflow

// Defined Constant
def jenkinsUrl = "http://jenkins:8080/" // Jenkins server
def jenkinsUser = "admin"               // User used to connect to Jenkins
def jenkinsPassword = "admin"           // Either password or better an access token of the user
def jenkinsBuild = "codebeamer"         // Name of the build to trigger
def jenkinsBuildToken = "codebeamer"    // Token configure for remote triggering of the build.
def linkField = "cIBuild"               // the field used to show the link to build detail page
def runningStatus = 3                   // status index value for "In Progress"
def successStatus = 7                   // status index value for "Completed"


import com.intland.codebeamer.persistence.dto.*;
import com.intland.codebeamer.persistence.dto.base.*;
import com.intland.codebeamer.persistence.dao.*;
import com.intland.codebeamer.manager.*;
import com.intland.codebeamer.controller.importexport.*;
import org.apache.commons.lang3.*
import org.springframework.http.HttpMethod
import org.springframework.web.client.ResponseExtractor;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.intland.codebeamer.security.util.RemoteConnection;
import com.intland.codebeamer.security.util.RestHttpClient;
import com.intland.codebeamer.security.realm.token.Token;
import com.intland.codebeamer.security.realm.token.TokenDef;
import java.util.concurrent.TimeUnit;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;


def token = new Token(user)
token.getDef().onlyMatchUrlStartingWith("/rest/item").setExpiry(1, TimeUnit.HOURS)
def tokenString = "CB_TOKEN" + token.encodeToToken();

if (!beforeEvent) {
    return;    // do NOTHING on after-event, everything is already handled in the before-event!
}

logger.info("-------------------------------------");
logger.info("Executing Jenkins build:" + subject);

def baseUrl = com.intland.codebeamer.Config.getLocationURL().getCodeBeamerBaseUrl(true)
def client = new RestHttpClient(new RemoteConnection(jenkinsUrl,jenkinsUser,jenkinsPassword));

def parameters = "cbUrl=http://codebeamer:8090/cb&cbId=${subject.id}&cbLinkField=${linkField}&cbRunningStatus=${runningStatus}&cbSuccessStatus=${successStatus}&cbToken=${tokenString}"
client.postForEntity("/view/all/job/${jenkinsBuild}/buildWithParameters?token=${jenkinsBuildToken}&${parameters}","",Void.class);
