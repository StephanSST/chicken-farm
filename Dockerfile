FROM eclipse-temurin:17-jdk-jammy

# install Tinkerforge Brick Daemon

RUN wget https://download.tinkerforge.com/apt/raspbian/tinkerforge.gpg -q -O - | tee /etc/apt/trusted.gpg.d/tinkerforge.gpg > /dev/null
RUN echo "deb https://download.tinkerforge.com/apt/raspbian bullseye main" | tee /etc/apt/sources.list.d/tinkerforge.list
RUN apt update
RUN apt install -y brickd
RUN apt install libtinkerforge-java

#RUN apt-get -q update && apt-get install -y wget pm-utils libusb-1.0-0
#RUN wget http://download.tinkerforge.com/tools/brickd/linux/brickd_linux_latest_armhf.deb
#RUN sudo dpkg -i brickd_linux_latest_armhf.deb

#RUN apt-get install libusb-1.0-0 libudev1 procps
#RUN wget --backups=1 https://download.tinkerforge.com/tools/brickd/linux/brickd_linux_latest_armhf.deb
#RUN dpkg -i brickd_linux_latest_armhf.deb

#RUN apt install brickd

EXPOSE 8080
WORKDIR /app
COPY staging/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]