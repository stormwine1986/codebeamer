# About

Capture a specific event of reviewhub and hand them off to the workflow custom scripts for processing.
e.g.
|event|script|
|-----|------|
|review create event|review_created.groovy|

## How to package

run maven command as below to package jarfile:

```bash
/opt/maven/bin/mvn clean package
```

then, you can find jarfile in "target" directory named "listener.reviewhub-2.0.0.0.jar".

## How to deploy

copy listener.reviewhub-2.0.0.0.jar to $CB_HOME/tomcat/webapps/ROOT/WEB-INF/lib, then restart service.

```bash
## with docker
docker cp ./target/listener.reviewhub-2.0.0.0.jar docker-codebeamer-app-1:/home/appuser/codebeamer/tomcat/webapps/ROOT/WEB-INF/lib
```

## How to debug

run this command to see the log:

```bash
## with docker
docker exec -it docker-codebeamer-app-1 bash
tail -f codebeamer/tomcat/logs/cb.txt | grep ReviewhubListener
```
