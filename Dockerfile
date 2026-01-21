FROM eclipse-temurin:17-jdk-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} store.jar
LABEL authors="PumkinStars"

ENTRYPOINT ["java", "-jar", "store.jar"]