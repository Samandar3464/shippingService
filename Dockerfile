FROM openjdk:17-alpine
ADD /target/shippingService-0.0.1.jar shippingservice.jar
ENTRYPOINT ["java", "-jar", "shippingservice.jar"]
EXPOSE 8080