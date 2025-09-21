FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/client-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9080
ENTRYPOINT ["java", "-jar", "app.jar"]