FROM alpine:3.19.1

ARG wildfly_version=29.0.1.Final

WORKDIR /root/

# system configuring
RUN apk update
RUN apk upgrade
RUN apk add curl

# java installing
RUN apk add openjdk17-jdk openjdk17-jre

# wildfly installing
RUN curl -L -o wildfly-${wildfly_version}.tar.gz https://github.com/wildfly/wildfly/releases/download/${wildfly_version}/wildfly-${wildfly_version}.tar.gz
RUN tar -xf ./wildfly-${wildfly_version}.tar.gz
RUN mv ./wildfly-${wildfly_version}/ ./wildfly/
RUN chmod +x ./wildfly/bin/*.sh

#wildfly configuring
COPY ./mgmt-groups.properties /root/wildfly/standalone/configuration/mgmt-groups.properties
COPY ./mgmt-users.properties /root/wildfly/standalone/configuration/mgmt-users.properties
COPY ./mgmt-groups.properties /root/wildfly/domain/configuration/mgmt-groups.properties
COPY ./mgmt-users.properties /root/wildfly/domain/configuration/mgmt-users.properties

# cleaning up
RUN rm -f ./wildfly-${wildfly_version}.tar.gz
RUN apk del curl

ENV JAVA_OPTS="-Dprogram.name=wildfly-standalone-server -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9989"

ENTRYPOINT /root/wildfly/bin/standalone.sh

EXPOSE 8080
EXPOSE 9989
EXPOSE 9990