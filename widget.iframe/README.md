# About

use iframe to load external site pages

## How to package

First, download cb-2.0.0.0.jar and cb-jsp-wiki-2.0.0.0.jar, then run maven command as below:

```bash
## install cb-2.0.0.0.jar to local
/opt/maven/bin/mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file  -Dfile=cb-2.0.0.0.jar -DgroupId=com.intland -DartifactId=cb-core -Dversion=2.0.0.0 -Dpackaging=jar
## install cb-jsp-wiki-2.0.0.0 to local
/opt/maven/bin/mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file  -Dfile=cb-jsp-wiki-2.0.0.0.jar -DgroupId=com.intland -DartifactId=cb-jsp-wiki -Dversion=2.0.0.0 -Dpackaging=jar
```

Second, run maven command as below to package jarfile:

```bash
## package widget.iframe-2.0.0.0.jar
/opt/maven/bin/mvn clean package
```

Finally, you can find jarfile in "target" directory named widget.iframe-2.0.0.0.jar.

## How to depoy

copy ./target/widget.iframe-2.0.0.0.jar to $CB_HOME/tomcat/webapps/ROOT/WEB-INF/lib, then restart service.

```bash
## with docker
docker cp ./target/widget.iframe-2.0.0.0.jar docker-codebeamer-app-1:/home/appuser/codebeamer/tomcat/webapps/ROOT/WEB-INF/lib
```
