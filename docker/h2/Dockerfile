FROM alpine:3.19.1

ARG h2_version=2.2.224

WORKDIR /root/

# system config
RUN apk update
RUN apk upgrade
RUN apk add curl

# java install
RUN apk add openjdk17-jre

# h2 install
RUN curl -L -o h2.jar https://github.com/h2database/h2database/releases/download/version-${h2_version}/h2-${h2_version}.jar
RUN chmod +x ./h2.jar

# cleaning up
RUN apk del curl

ENTRYPOINT java -cp /root/h2.jar -Dh2.bindAddress=0.0.0.0 org.h2.tools.Server                                    \
                                                                              -web -webPort 8082 -webAllowOthers \
                                                                              -tcp -tcpPort 9092 -tcpAllowOthers \
                                                                              -ifNotExists -baseDir /root/data/

EXPOSE 8082
EXPOSE 9092
VOLUME /root/data/