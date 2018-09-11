
################## BASE IMAGE ######################

FROM biocontainers/biocontainers:debian-stretch-backports

################## METADATA ######################

LABEL base_image="biocontainers:debian-stretch-backports"
LABEL version="1"
LABEL software="biocontainers-api"
LABEL software.version="1.0.0"
LABEL about.summary="The biocontainers API provides an entry point to retrieve all containers"
LABEL about.home="https://biocontainers.pro"
LABEL about.license="SPDX:Apache-2"

################## MAINTAINER ######################

MAINTAINER Yasset Perez-Riverol <ypriverol@gmail.com>

################## INSTALLATION ######################


RUN apt-get clean all && \
    apt-get update && \
    apt-get upgrade -y && \
    apt-get -y install software-properties-common && \
    add-apt-repository -y ppa:webupd8team/java  && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
    echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections && \
    apt-get update && \
    apt-get install -y --force-yes oracle-java8-installer maven


ADD ../../biocontainers-api/target/biocontainers-api-1.0-SNAPSHOT.jar /api-service.jar
ADD run.sh /usr/local/bin/run.sh
RUN chmod u+x /usr/local/bin/run.sh
ENTRYPOINT ["run.sh"]
