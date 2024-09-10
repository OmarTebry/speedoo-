# Use an official OpenJDK 21 image as a base
FROM eclipse-temurin:21-jdk AS build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Package the application (skip tests to speed up the build process)
RUN mvn -B -DskipTests clean package

# Stage 2: Create a smaller image to run the app
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
