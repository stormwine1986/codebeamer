# About

## How to package

run maven command as below to package jarfile:

```bash
/opt/maven/bin/mvn clean package
```

then, you can find jarfile in "target" directory named "listener.wrapper-2.0.0.0.jar".

## How to deploy

copy listener.wrapper-2.0.0.0.jar to $CB_HOME/tomcat/webapps/ROOT/WEB-INF/lib, then restart service.

```bash
## with docker
docker cp ./target/listener.wrapper-2.0.0.0.jar docker-codebeamer-app-1:/home/appuser/codebeamer/tomcat/webapps/ROOT/WEB-INF/lib

docker cp ./listener/ docker-codebeamer-app-1:/home/appuser/codebeamer/tomcat/webapps/ROOT/config/scripts/
```

## How to debug

run this command to see the log:

```bash
## with docker
docker exec -it docker-codebeamer-app-1 bash
tail -f codebeamer/tomcat/logs/cb.txt | grep TrackerItemListenerScriptWrapper
```
