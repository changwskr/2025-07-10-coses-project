# Multi-stage build for KDB Oversea Banking Application
FROM maven:3.9.5-openjdk-17 AS builder

# Set working directory
WORKDIR /app

# Copy parent pom.xml
COPY pom.xml ./

# Copy all module pom.xml files
COPY kdb-oversea-foundation/pom.xml ./kdb-oversea-foundation/
COPY kdb-oversea-framework/pom.xml ./kdb-oversea-framework/
COPY kdb-oversea-eplaton/pom.xml ./kdb-oversea-eplaton/
COPY kdb-oversea-common/pom.xml ./kdb-oversea-common/
COPY kdb-oversea-cashcard/pom.xml ./kdb-oversea-cashcard/
COPY kdb-oversea-deposit/pom.xml ./kdb-oversea-deposit/
COPY kdb-oversea-teller/pom.xml ./kdb-oversea-teller/
COPY kdb-oversea-app/pom.xml ./kdb-oversea-app/

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-jre-slim

# Set working directory
WORKDIR /app

# Create non-root user
RUN groupadd -r kdbuser && useradd -r -g kdbuser kdbuser

# Copy built JAR from builder stage
COPY --from=builder /app/kdb-oversea-app/target/kdb-oversea-app-*.jar app.jar

# Create logs directory
RUN mkdir -p /app/logs && chown -R kdbuser:kdbuser /app

# Switch to non-root user
USER kdbuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM options for production
ENV JAVA_OPTS="-Xms512m -Xmx2g -XX:+UseG1GC -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 