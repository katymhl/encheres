# ----------------------------
# Étape 1 : Builder le projet avec Gradle
# ----------------------------
FROM gradle:8.3-jdk17 AS builder
WORKDIR /app
COPY . /app
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# ----------------------------
# Étape 2 : Image runtime légère
# ----------------------------
FROM openjdk:17-jdk-slim
COPY --from=builder /app/build/libs/*.jar encheres.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/encheres.jar"]
