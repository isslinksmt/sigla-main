FROM amazoncorretto:8-alpine
LABEL MAINTAINER Gabriele Arena <gabriele.arena@linksmt.it>

RUN apk update && apk add --no-cache tzdata python3 openssl

# Scarica e importa il certificato del server Keycloak nel truststore Java
RUN openssl s_client \
    -connect devops-roma-k8s-dev-net.linksmt.it:443 \
    -showcerts \
    </dev/null 2>/dev/null \
    | openssl x509 -outform PEM > /tmp/keycloak.crt \
    && keytool -import \
    -noprompt \
    -trustcacerts \
    -alias keycloak-linksmt \
    -file /tmp/keycloak.crt \
    -keystore /usr/lib/jvm/default-jvm/jre/lib/security/cacerts \
    -storepass changeit \
    && rm /tmp/keycloak.crt

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