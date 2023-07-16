#FROM arm32v7/openjdk:11-jdk
#FROM amd64/openjdk:11-jdk
FROM eclipse-temurin:11-jdk-jammy

EXPOSE 8080
WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]