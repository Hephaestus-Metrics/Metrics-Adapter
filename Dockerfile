FROM openjdk:16
COPY ./target /opt
WORKDIR /opt
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./DroolsPrototype-0.0.1-SNAPSHOT.jar"]