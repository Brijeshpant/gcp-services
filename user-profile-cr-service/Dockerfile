# Use a base image with Java 21
FROM us-central1-docker.pkg.dev/serverless-runtimes/google-22-full/runtimes/java21:java21_20250609_21_0_RC00

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY build/libs/*.jar app.jar

# Expose the port your Spring Boot app listens on (usually 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]