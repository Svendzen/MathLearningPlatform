FROM amazoncorretto:21
COPY ./target/progressservice-0.0.1-SNAPSHOT.jar /app/progressservice.jar
WORKDIR /app
EXPOSE 8083
CMD ["java", "-jar", "progressservice.jar"]