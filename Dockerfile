FROM openjdk:17-jdk

WORKDIR /app

COPY target/shippingService-0.0.1.jar app/shippingService.jar

EXPOSE 8080

CMD ["java", "-jar", "shippingService.jar"]