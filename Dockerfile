FROM openjdk:8-jre-slim

WORKDIR /app

COPY target/*.jar /app/assetmanager-api.jar

EXPOSE 8081

CMD ["java", "-jar", "assetmanager-api.jar"]