FROM eclipse-temurin:17-jdk-jammy

# install Tinkerforge Brick Daemon

#RUN wget https://download.tinkerforge.com/apt/raspbian/tinkerforge.gpg -q -O - | tee /etc/apt/trusted.gpg.d/tinkerforge.gpg > /dev/null
#RUN echo "deb https://download.tinkerforge.com/apt/raspbian bullseye main" | tee /etc/apt/sources.list.d/tinkerforge.list
#RUN apt update
#RUN apt install -y brickd
#RUN apt install libtinkerforge-java

EXPOSE 8080
WORKDIR /app
COPY staging/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]