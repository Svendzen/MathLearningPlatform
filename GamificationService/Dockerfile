FROM amazoncorretto:21
COPY ./target/gamificationservice-0.0.1-SNAPSHOT.jar /app/gamificationservice.jar
WORKDIR /app
EXPOSE 8084
CMD ["java", "-jar", "gamificationservice.jar"]