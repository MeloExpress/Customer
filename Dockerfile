FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} customerapplication.jar
EXPOSE 5432
ENTRYPOINT ["java","-jar","/customerapplication.jar"]