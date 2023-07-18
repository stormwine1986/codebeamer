# About

suport some cli tools for codebeamer

## How to Build

```bash
go build
```

## How to Use

```bash
cb-cli -h
```

## Exmaples

```bash
# Grant a project role to a user
cb-cli grantrole -p "Project X" -r "Developer" -m "bond"
CB_CLI_SERVERURL=http://localhost:8080 CB_CLI_USER=bond CB_CLI_PASSWORD=007 cb-cli grantrole -p "Project X" -r "Developer" -m "bond"

# Make a user a member of a group
cb-cli addgroup -g "SuperMan" -m "bond"
CB_CLI_SERVERURL=http://localhost:8080 CB_CLI_USER=bond CB_CLI_PASSWORD=007 cb-cli addgroup -g "SuperMan" -m "bond"
```
