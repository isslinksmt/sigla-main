FROM amazoncorretto:8-alpine
LABEL MAINTAINER Gabriele Arena <gabriele.arena@linksmt.it>

RUN apk update && apk add --no-cache tzdata python3

COPY cert1.crt /tmp/cert1.crt
COPY cert2.crt /tmp/cert2.crt

RUN CACERTS=$(find /usr/lib/jvm -name "cacerts" 2>/dev/null | head -1) && \
    echo "Trovato cacerts in: $CACERTS" && \
    keytool -import -noprompt -trustcacerts \
        -alias keycloak-leaf \
        -file /tmp/cert1.crt \
        -keystore "$CACERTS" \
        -storepass changeit && \
    keytool -import -noprompt -trustcacerts \
        -alias keycloak-ca \
        -file /tmp/cert2.crt \
        -keystore "$CACERTS" \
        -storepass changeit && \
    echo "=== Verifica import ===" && \
    keytool -list -keystore "$CACERTS" -storepass changeit | grep keycloak && \
    rm /tmp/cert1.crt /tmp/cert2.crt

COPY sigla-web/target/sigla-thorntail.jar /opt/sigla-thorntail.jar
COPY patch_xsd.py /opt/patch_xsd.py

RUN python3 /opt/patch_xsd.py

ENV ESERCIZIO=2024
EXPOSE 8080

CMD java \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 \
    -Djavax.net.ssl.trustStore=/usr/lib/jvm/java-8-amazon-corretto/lib/security/cacerts \
    -Djavax.net.ssl.trustStorePassword=changeit \
    -Djavax.xml.accessExternalSchema=all \
    -Djavax.xml.accessExternalDTD=all \
    -Dliquibase.bootstrap.esercizio=$ESERCIZIO \
    -Djava.security.egd=file:/dev/./urandom \
    -jar /opt/sigla-thorntail.jar