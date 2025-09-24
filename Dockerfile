# Étape 1 : Builder le projet avec Gradle
FROM gradle:8.3-jdk17 AS builder

# Copier le code source dans le conteneur
WORKDIR /app
COPY . /app

# Lancer la compilation et générer le jar
RUN gradle clean build --no-daemon

# Étape 2 : image runtime légère
FROM openjdk:17-jdk-slim

# Copier le jar depuis l'image builder
COPY --from=builder /encheres/build/libs/encheres-0.0.1-SNAPSHOT.jar encheres.jar

# Exposer le port de l'application Spring Boot
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "/app.jar"]