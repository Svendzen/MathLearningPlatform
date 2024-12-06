FROM amazoncorretto:21
COPY ./target/gateway-0.0.1-SNAPSHOT.jar /app/gateway.jar
WORKDIR /app
EXPOSE 8000
CMD ["java", "-jar", "gateway.jar"]