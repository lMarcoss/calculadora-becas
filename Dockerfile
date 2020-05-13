#
# Build stage
#
FROM maven:3.6.2-jdk-11-slim AS build
COPY src /home/unsis-app/src
COPY pom.xml /home/unsis-app
RUN mvn -f /home/unsis-app/pom.xml clean package


FROM openjdk:8u181-jre-slim-stretch
COPY --from=build /home/unsis-app/target/becas-1.0.0.jar /opt/becas-1.0.0.jar
#COPY application.properties /application.properties
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.awt.headless=true", "-Xms512m", "-Xmx512m", "-jar", "/opt/becas-1.0.0.jar"]
