FROM openjdk:11.0-jre
VOLUME /tmp

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar","--spring.config.location=application-prod.yml"]
