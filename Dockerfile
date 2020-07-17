##
## Build stage
##
#FROM maven:3.6.2-jdk-11-slim AS build
#COPY src /home/unsis-app/src
#COPY pom.xml /home/unsis-app
#RUN mvn -f /home/unsis-app/pom.xml clean package
#
#
#FROM openjdk:8u181-jre-slim-stretch
#COPY --from=build /home/unsis-app/target/becas-1.0.0.jar /opt/becas-1.0.0.jar
##COPY application.properties /application.properties
#EXPOSE 8080
#ENTRYPOINT ["java", "-Djava.awt.headless=true", "-Xms512m", "-Xmx512m", "-jar", "/opt/becas-1.0.0.jar"]

# Build stage
FROM openjdk:8-jdk-alpine as build
WORKDIR /app

#copia los archivos necesarios para instalar
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# permios
RUN chmod +x ./mvnw
#Descarga dependencias
RUN ./mvnw dependency:go-offline -B

COPY src src

#Construye el proyecto
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#stage produccion
FROM openjdk:8-jre-alpine as production
ARG DEPENDENCY=/app/target/dependency

# copia las dependencias
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Run the Spring boot application
ENTRYPOINT ["java", "-cp", "app:app/lib/*","edu.calc.becas.BecasApplication"]