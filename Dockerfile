FROM openjdk:17-oracle
LABEL authors="dharshet"
MAINTAINER Harshet
COPY target/serviceManagement-0.0.1-SNAPSHOT.jar services-1.0.0.jar
ENTRYPOINT ["java","-jar","services-1.0.0.jar"]