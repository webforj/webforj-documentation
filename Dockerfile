# Use Playwright's official Java image
FROM mcr.microsoft.com/playwright/java:v1.50.0-noble

# Set working directory
WORKDIR /app

# Copy everything from the host project into that folder
COPY . /app

# Set environment variable (from workflow)
ENV CI=true

# Configure Maven to use custom directory for repository
ENV MAVEN_CONFIG=/var/maven/.m2
RUN mkdir -p /var/maven/.m2

# Default command to run tests
CMD ["mvn", "verify"]
