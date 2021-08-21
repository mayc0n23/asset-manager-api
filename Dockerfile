FROM openjdk:8-jre-slim

RUN cd / && mkdir Arquivos && chmod 777 -R Arquivos/

WORKDIR /app

COPY target/*.jar /app/assetmanager-api.jar

EXPOSE 8081

CMD ["java", "-jar", "assetmanager-api.jar"]