FROM eclipse-temurin:22-alpine
LABEL authors="Nikita"

COPY out/eureka-server.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]