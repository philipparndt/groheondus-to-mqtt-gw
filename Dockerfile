FROM openjdk:8-jdk-alpine

LABEL maintainer="Philipp Arndt <2f.mail@gmx.de>"
LABEL version="1.0"
LABEL description="grohe ondus to mqtt gateway"


ENV LANG en_US.UTF-8
ENV TERM xterm

WORKDIR /opt/groheondus-to-mqtt-gw

RUN apk update --no-cache && apk add --no-cache maven

COPY src /opt/groheondus-to-mqtt-gw

RUN mvn install assembly:single
RUN cp ./de.rnd7.groheondustomqtt/target/groheondus-to-mqtt-gw.jar ./groheondus-to-mqtt-gw.jar

CMD java -jar groheondus-to-mqtt-gw.jar /var/lib/groheondus-to-mqtt-gw/config.json
