# Use the official Maven image for building the application
FROM maven:latest AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and download the dependencies
COPY pom.xml .
COPY src ./src

# Build the application (skip tests to speed up the build)
RUN mvn clean package -DskipTests

# Use the official OpenJDK image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port (default for Spring Boot is 8080)
EXPOSE 8400

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
