#!/bin/env bash

java -jar /api-service.jar --mongodb.biocontainers.db.database=$MONGO_DB \
  --mongodb.biocontainers.db.user=$MONGODB_USER \
  --mongodb.biocontainers.db.password=$MONGODB_PASS \
  --mongodb.biocontainers.db.authenticationDatabase= \
  --mongodb.biocontainers.db.port= \
  --mongodb.biocontainers.db.host=$MONGODB_HOST \
