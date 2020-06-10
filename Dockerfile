FROM alpine:edge

RUN apk add --no-cache openjdk11

RUN mkdir /opt/enigma

COPY target/enigma-0.0.1-SNAPSHOT.jar /opt/enigma

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "/opt/enigma/enigma-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080