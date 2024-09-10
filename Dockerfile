# Use Maven image with OpenJDK 21 to build the app
FROM maven:3.8.5-openjdk-21 AS build
WORKDIR /app

# Copy the pom.xml file and download project dependencies (this will cache dependencies if unchanged)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code to the Docker image
COPY src ./src

# Build the project and package the Spring Boot app (skip tests for faster builds)
RUN mvn package -DskipTests

# Use a lightweight JDK 21 image to run the app
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the built jar file from the Maven image
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on (8080 by default)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
