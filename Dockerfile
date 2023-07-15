FROM openjdk:11

EXPOSE 8080
WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]