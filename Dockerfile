FROM openjdk:17-alpine
ADD /target/shippingService-0.0.1.jar shippingService.jar
ENTRYPOINT ["java", "-jar", "shippingService.jar"]
EXPOSE 8080