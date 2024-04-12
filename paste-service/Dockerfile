FROM eclipse-temurin:22-alpine
LABEL authors="Nikita"

COPY target/paste-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]