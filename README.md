# Prerequesites
- runtime : jre 21
- docker installed

# Run
launch 
```
./start.sh .env
```
APi is accessible on http://localhost:8080/ 

NB : a _.env_ file has been exceptionally commited to ease the start up  

# TODO (fix bug & enhancements)

- api infra layer: 
  - add pagination on api player & ranking
  - add validation
  - add applicative exceptions / http code mapping
  - fill openapi definition
- database infra layer : 
  - set up transaction between player & point creation
  - enhance DAO mapper (refacto usinf JPA-like Exposed "Entity" ?)
  - understand the Exposed subquery behaviour to fix ranking player feature !
- domain : 
  - nickname to be UNIQUE
- hexa archi 
  - dispatch _plugins_ directory files in right packages



# Prod ready ? 
- security : 
  - api : set up authN/authZ on routes (eg : JWT)
  - database : set up user & roles
- subscribe to devops process by adding CI scripts
- add database schema versionning (flyway / Liquibase / ...)
- build prod ready docker image
- refacto code for better scalability (pagination, isolate bounded context?, ...) 

