FROM openjdk:17

# Install netcat for the wait script
RUN apt-get update && apt-get install -y netcat-openbsd

WORKDIR /app
COPY target/*.jar app.jar

# Script to wait for MySQL
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 9090

# Use wait-for-it to wait for MySQL before starting the application
ENTRYPOINT ["/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]