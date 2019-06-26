FROM maven:3.5-jdk-8-alpine as build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN mvn install -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
CMD ["java -jar app.jar"]