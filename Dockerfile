FROM openjdk:16
COPY ./target/MetricsAdapter-0.0.1-SNAPSHOT.jar /opt
WORKDIR /opt
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./MetricsAdapter-0.0.1-SNAPSHOT.jar"]