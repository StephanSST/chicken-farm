FROM eclipse-temurin:17-jdk-jammy

EXPOSE 8080
WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]