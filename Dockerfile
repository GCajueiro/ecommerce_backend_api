FROM openjdk:21-jdk

COPY target/ecommerce-backend-0.0.1-SNAPSHOT.jar /app/app.jar

#LABEL authors="GabrielCajueiro"

ENTRYPOINT ["java","-jar", "/app/app.jar"]

CMD []