FROM alpine:3.19.1

ARG wildfly_version=29.0.1.Final

WORKDIR /root/

# system config
RUN apk update
RUN apk upgrade
RUN apk add curl

# java install
RUN apk add openjdk17-jdk openjdk17-jre

# wildfly install
RUN curl -L -o wildfly-${wildfly_version}.tar.gz https://github.com/wildfly/wildfly/releases/download/${wildfly_version}/wildfly-${wildfly_version}.tar.gz
RUN tar -xf ./wildfly-${wildfly_version}.tar.gz
RUN mv ./wildfly-${wildfly_version}/ ./wildfly/
RUN chmod +x ./wildfly/bin/*.sh

# cleaning up
RUN rm -f ./wildfly-${wildfly_version}.tar.gz
RUN apk del curl

ENTRYPOINT /root/wildfly/bin/standalone.sh

EXPOSE 8080
EXPOSE 9990