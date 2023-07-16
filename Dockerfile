FROM amd64/openjdk:11-jdk

EXPOSE 8080
WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]