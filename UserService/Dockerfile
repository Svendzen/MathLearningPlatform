FROM amazoncorretto:21
COPY ./target/userservice-0.0.1-SNAPSHOT.jar /app/userservice.jar
WORKDIR /app
EXPOSE 8082
CMD ["java", "-jar", "userservice.jar"]