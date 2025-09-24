# ----------------------------
# Étape 1 : Builder le projet avec Gradle
# ----------------------------
FROM gradle:8.3-jdk17 AS builder

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier tout le projet dans le conteneur
COPY . /app

RUN chmod +x gradlew
# Lancer la compilation avec le Gradle Wrapper
RUN ./gradlew clean build --no-daemon

# ----------------------------
# Étape 2 : Image runtime légère
# ----------------------------
FROM openjdk:17-jdk-slim

# Copier le jar Spring Boot (non plain) depuis l'image builder
COPY --from=builder /app/build/libs/encheres-0.0.1-SNAPSHOT.jar encheres.jar

# Exposer le port par défaut de Spring Boot
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "/encheres.jar"]