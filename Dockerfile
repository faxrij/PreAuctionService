FROM maven:3.6.3-openjdk-17-slim as builder

WORKDIR /usr/local/app

COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY ./src ./src

RUN mvn -Dmaven.test.skip=true package

FROM openjdk:17-alpine

WORKDIR /usr/local/app

COPY --from=builder /usr/local/app/target/PreAuctionService-0.0.1-SNAPSHOT.jar ./server.jar

EXPOSE 8080

CMD ["java", "-jar", "server.jar"]