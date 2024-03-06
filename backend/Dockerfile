FROM ubuntu:latest

FROM maven:latest AS builder

WORKDIR /app

COPY . .

RUN mvn package

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/tags-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]