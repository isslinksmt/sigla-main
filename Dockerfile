# DOCKER-VERSION 17.10.0-ce
FROM eclipse-temurin:8-jdk-alpine
LABEL MAINTAINER Gabriele Arena <gabriele.arena@linksmt.it>

RUN apk update
RUN apk add tzdata

COPY sigla-web/target/sigla-thorntail.jar /opt/sigla-thorntail.jar

ENV ESERCIZIO=2024

EXPOSE 8080

CMD java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dliquibase.bootstrap.esercizio=$ESERCIZIO -Djava.security.egd=file:/dev/./urandom -jar /opt/sigla-thorntail.jar