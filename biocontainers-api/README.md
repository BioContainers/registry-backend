# BioContainers API 

The **biocontainers** API provides the all biocontainers tools and containers store and developed by [Biocontainers Community ](biocontainers.pro).
The biocontainers API implements the [TRS specification from GA4GH community](https://github.com/ga4gh/tool-registry-service-schemas).

# How to run it. 

```bash 
java -jar biocontainers-api-{version}.jar --varialble="variable-value"
``` 

## Configuration Variables

To run the service different variables can be provided: 

### API url variables: 

```yaml

server.port    The server API port where the service will run, default: server.port=9999
biocontainers.api.base-path   The url of the API entry point, default: biocontainers.api.base-path=/api/v2
springfox.documentation.swagger.v2.path The url of the documentation of the API, default: springfox.documentation.swagger.v2.path=/api/v2/api-docs

```

### MongoDB backend Variables: 

MongoDB where the data is store variables: 

```yaml

mongodb.biocontainers.db.database    BioContainers Database, default: mongodb.biocontainers.db.database=bioconDB
mongodb.biocontainers.db.user        BioContainers user, default: mongodb.biocontainers.db.user=biocon_admin
mongodb.biocontainers.db.password    BioContainers user password, default: mongodb.biocontainers.db.password= 12345
mongodb.biocontainers.db.authenticationDatabase   BioContainers user login database, default: mongodb.biocontainers.db.authenticationDatabase= admin
mongodb.biocontainers.db.port        BioContainers Database port: mongodb.biocontainers.db.port= 27017
mongodb.biocontainers.db.host        BioContainers Database url (machine), default: mongodb.biocontainers.db.host= localhost

```






