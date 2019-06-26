FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY server/mvnw .
COPY server/.mvn .mvn
COPY server/pom.xml .
COPY server/src src

RUN ./server/mvnw install -DskipTests=true

CMD ["java", "-jar", "server/target/notification-service-0.0.1-SNAPSHOT.jar"]