FROM openjdk:17-jdk
ARG JAR_FILE=./build/libs/*SNAPSHOT.jar
EXPOSE 8004
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]