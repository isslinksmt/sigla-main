FROM amazoncorretto:8-alpine
LABEL MAINTAINER Gabriele Arena <gabriele.arena@linksmt.it>

RUN apk update && apk add --no-cache tzdata python3

# Importa tutti i certificati della chain nel truststore Java
COPY cert*.crt /tmp/
RUN CACERTS=$(find /usr/lib/jvm -name "cacerts" 2>/dev/null | head -1) && \
    i=1 && \
    for cert in /tmp/cert*.crt; do \
        keytool -import -noprompt -trustcacerts \
            -alias keycloak-linksmt-$i \
            -file "$cert" \
            -keystore "$CACERTS" \
            -storepass changeit && \
        i=$((i+1)); \
    done && \
    rm /tmp/cert*.crt

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