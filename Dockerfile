FROM openjdk:17-jdk-slim

# Install netcat
RUN apt-get update && apt-get install -y netcat

COPY target/throttlex-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-redis.sh wait-for-redis.sh
RUN chmod +x wait-for-redis.sh

ENTRYPOINT ["./wait-for-redis.sh", "redis", "java", "-jar", "app.jar"]

