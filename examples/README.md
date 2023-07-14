# Examples

## Get user schema

```bash
curl -X GET -u bond:007 http://localhost:8080/rest/user/schema -i
```

## Create new user

```bash
curl -X POST -u bond:007 http://localhost:8080/rest/user -i \
    -H 'Content-Type:application/json;charset=UTF-8' \
    -d '{
        "name":"alice",
        "email":"alice@cb.com",
        "firstName": "alice",
        "lastName": "alice"
    }'
```

## Update an existing user

```bash
curl -X PUT -u bond:007 http://127.0.0.1:8080/rest/user -i \
    -H 'Content-Type:application/json;charset=UTF-8' \
    -d '{
            "uri": "/user/alice",
            "status": "Disabled"
        }'
```

## Get the project role schema

```bash
curl -X GET -u bond:007 http://127.0.0.1:8080/rest/project/role/schema -i
```

## Get a project definition

```bash
curl -X GET -u bond:007 "http://127.0.0.1:8080/rest/project/Intland%20Software's%20Default%20Template" -i
```

## Define a new role stereotype

```bash
curl -X POST -u bond:007 http://127.0.0.1:8080/rest/role -i \
    -H 'Content-Type:application/json;charset=UTF-8' \
    -d '{
            "name"        : "开发工程师",
            "description" : "开发工程师"
        }'
```

## Define a new project role (instantiate a role stereotype on a project)

```bash
curl -X POST -u bond:007 "http://127.0.0.1:8080/rest/project/Intland%20Software's%20Default%20Template/role/Developer" -i \
    -H 'Content-Type:application/json;charset=UTF-8' \
    -d '[
            "Wiki Space - View",
            "Document - View",
            "Trackers - View",
            "CMDB - View"
        ]'
```

## Grant a project role to a user

```bash
curl -X PUT -u bond:007 "http://127.0.0.1:8080/rest/project/Intland%20Software's%20Default%20Template/role/Developer/user/bond" -i \
    -H 'Content-Type:application/json;charset=UTF-8'
```

## Get roles

```bash
curl -X GET -u bond:007 http://localhost:8080/api/v3/roles -i
```

## Get basic tracker item

```bash
curl -X GET -u bond:007 http://localhost:8080/api/v3/items/1021 -i
```

## Update fields of a tracker item

```bash
curl -X PUT -u bond:007 http://localhost:8080/api/v3/items/1021/fields -i \
    -H 'Content-Type:application/json;charset=UTF-8' \
    -d '{
            "fieldValues": [
                {
                    "fieldId": 1002,
                    "type": "RoleReference",
                    "value": [
                        {
                            "id": 2
                        }
                    ]
                }
            ]
        }'
```

## Get fields of tracker

```bash
curl -X GET -u bond:007 http://localhost:8080/api/v3/trackers/2047/fields -i
```

## Get field of tracker

```bash
curl -X GET -u bond:007 http://localhost:8080/api/v3/trackers/2047/fields/1002 -i
```

## Find all reviews

```bash
curl -X POST -u bond:007 http://127.0.0.1:8080/api/v3/items/query -i \
    -H 'Content-Type:application/json;charset=UTF-8' \
    -d '{
            "page": 1,
            "pageSize": 2,
            "queryString": "tracker.id in (2047)"
        }'
```

## Submit timekeeping

```bash
```

## Submit baseline

```bash
```
