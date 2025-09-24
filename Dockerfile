FROM gradle:8.3-jdk17 AS builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim
COPY --from=builder /app/build/libs/encheres-0.0.1-SNAPSHOT.jar encheres.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/encheres.jar"]
