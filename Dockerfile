# Étape 1 : Builder le projet avec Gradle
FROM gradle:8.3-jdk17 AS builder

# Définir le répertoire de travail
WORKDIR /app

# Copier tout le code source
COPY . /app

# Lancer la compilation avec Gradle Wrapper
RUN ./gradlew clean build --no-daemon

# Étape 2 : image runtime légère
FROM openjdk:17-jdk-slim

# Copier le jar généré depuis l'image builder
# Remplacer par le nom exact si nécessaire
COPY --from=builder /app/build/libs/encheres-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port utilisé par Spring Boot
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]