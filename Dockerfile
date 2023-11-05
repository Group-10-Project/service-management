# Use a base image that includes Maven
FROM maven:3.8.4-openjdk-17-slim as mavenBuild
# Run the Maven build command
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:17-oracle
LABEL authors="dharshet"
MAINTAINER Harshet
WORKDIR /app
COPY --from=mavenBuild  /app/target/serviceManagement-0.0.1-SNAPSHOT.jar services-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","services-1.0.0.jar"]