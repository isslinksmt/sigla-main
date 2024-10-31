# DOCKER-VERSION 17.10.0-ce
FROM openjdk:8-jdk-alpine
MAINTAINER Marco Spasiano <marco.spasiano@cnr.it>

COPY sigla-web/target/sigla-thorntail.jar /opt/sigla-thorntail.jar

ENV ESERCIZIO=2024

EXPOSE 8080

CMD java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dliquibase.bootstrap.esercizio=$ESERCIZIO -Djava.security.egd=file:/dev/./urandom -Dremote.maven.repo=https://repository.jboss.org/nexus/content/groups/public/,https://maven.repository.redhat.com/earlyaccess/all -jar /opt/sigla-thorntail.jar