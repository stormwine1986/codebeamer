# Collections for Codebeamer

## About codebeamer

codebeamer is an Agile Application Lifecycle Management (ALM) solution for distributed development.
codebeamer provides Project-, Task-, Requirement-, Test-, Change-, Configuration-, Build-, Knowledge- and Document management in a single, secure environment. It enables software and hardware development to be more collaborative, transparent and productive.

For more information see: <https://codebeamer.com/cb/project/CB>

## About the resposity

the resposity that store something about codebeamer custom component samples which be mantained by a couple of programmer who interested in codebeamer.

## Directory

|Name                   |Description                                                            |
|-----------------------|-----------------------------------------------------------------------|
|widget.iframe          |widget for show external site pages in wiki                            |
|custom.workflow.action |Groovy scripts sample                                                  |
|listener.wrapper       |use groovy scripts extends listener                                    |
|gerrit                 |gerrit data collector and rest api                                     |
|jenkins                |pipeline sample                                                        |

## Tools Integration Reference

|Tool                   |Reference Link                                                             |
|-----------------------|---------------------------------------------------------------------------|
|Parasoft               |<https://docs.parasoft.com/display/DTP543/Integrating+with+CodeBeamer+ALM> |
|vectorCast             |<https://www.vector.com/int/en/download/vectorcast-codebeamer-integration/>|

## How to deploy custom workflow action

copy groovy files to /home/appuser/codebeamer/tomcat/webapps/ROOT/config/scripts/workflow
see more: <https://codebeamer.com/cb/wiki/649989>

## How to review log

```bash
tail -f /home/appuser/codebeamer/tomcat/logs/cb.txt
```

if you want to review the log about custom workflow action script, you can use command below:

```bash
tail -f /home/appuser/codebeamer/tomcat/logs/cb.txt | grep CustomScript
```

