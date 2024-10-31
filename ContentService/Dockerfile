FROM amazoncorretto:21
COPY ./target/contentservice-0.0.1-SNAPSHOT.jar /usr/src/contentservice/
WORKDIR /usr/src/contentservice/
EXPOSE 8000
CMD ["java", "-jar", "contentservice-0.0.1-SNAPSHOT.jar"]