FROM amazoncorretto:8-alpine
LABEL MAINTAINER Gabriele Arena <gabriele.arena@linksmt.it>

RUN apk update && apk add --no-cache tzdata python3

COPY sigla-web/target/sigla-thorntail.jar /opt/sigla-thorntail.jar
COPY patch_xsd.py /opt/patch_xsd.py

RUN python3 /opt/patch_xsd.py

ENV ESERCIZIO=2024
EXPOSE 8080

CMD java \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
    -Djavax.xml.accessExternalSchema=all \
    -Djavax.xml.accessExternalDTD=all \
    -Dliquibase.bootstrap.esercizio=$ESERCIZIO \
    -Djava.security.egd=file:/dev/./urandom \
    -jar /opt/sigla-thorntail.jar