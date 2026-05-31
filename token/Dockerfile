FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/quarkus-app/ /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "quarkus-run.jar"]
