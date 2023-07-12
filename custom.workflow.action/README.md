# About

this is some examples for custom workflow action written by groovy script.

# How to deploy

copy files to $CB_HOME/tomcat/webapps/ROOT/config/scripts/workflow

```bash
## with docker
docker cp . docker-codebeamer-app-1:/home/appuser/codebeamer/tomcat/webapps/ROOT/config/scripts/workflow
```

# How to use

see more: https://codebeamer.com/cb/wiki/649989

# How to find log

```bash
## with docker
docker exec -it docker-codebeamer-app-1 bash
[appuser@9deb8508b5e9 ~]$ tail -f codebeamer/tomcat/logs/cb.txt | grep event.impl.CustomScriptExecutor
```
if you want to see other more logs, run command instead

```bash
tail -f codebeamer/tomcat/logs/cb.txt
```