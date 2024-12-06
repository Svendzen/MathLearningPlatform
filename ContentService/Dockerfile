FROM amazoncorretto:21
COPY ./target/contentservice-0.0.1-SNAPSHOT.jar /app/contentservice.jar
WORKDIR /app
EXPOSE 8081
CMD ["java", "-jar", "contentservice.jar"]
