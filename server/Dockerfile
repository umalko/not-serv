FROM maven:3-jdk-8

ENV HOME=/usr/src/app

RUN mkdir -p $HOME

WORKDIR $HOME

# 1, add pom.xml only here, 2, and start downloading dependencies

ADD pom.xml $HOME

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

# 3, now we can add all source code and start compiling

ADD . $HOME

RUN ["mvn", "package", "-DskipTests=true"]

CMD ["java", "-jar", "./target/notification-service-0.0.1-SNAPSHOT.jar"]