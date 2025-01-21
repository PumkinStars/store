FROM openjdk:17-jdk
FROM maven:3.9.0-eclipse-temurin-17 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY ./src/main/resources/application.properties ./src/main/resources/application.properties
RUN mvn clean package -DskipTests

WORKDIR /app

COPY target/store-0.0.1-SNAPSHOT.jar /app/store.jar

EXPOSE 8090

CMD ["java", "-jar", "store.jar"]