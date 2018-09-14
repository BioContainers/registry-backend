#!/bin/env bash

#   --mongodb.biocontainers.db.authenticationDatabase= \
#  --mongodb.biocontainers.db.port= \

mongo --host $MONGODB_HOST -u root -p $MONGO_ROOT_PASS --authenticationDatabase $MONGODB_ADMIN_DB --eval "db.grantRolesToUser( $MONGODB_USER , [ { 'role' : 'readWrite', 'db' : $BIOCONT_DB_NAME } ] )"

java -jar /api-service.jar --mongodb.biocontainers.db.database=$BIOCONT_DB_NAME \
  --mongodb.biocontainers.db.user=$MONGODB_USER \
  --mongodb.biocontainers.db.password=$MONGODB_PASS \
  --mongodb.biocontainers.db.authenticationDatabase=$MONGODB_ADMIN_DB \
  --mongodb.biocontainers.db.host=$MONGODB_HOST \
