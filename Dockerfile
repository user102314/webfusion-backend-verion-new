# Étape 1 : Build du JAR avec Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape 2 : Exécution de l'application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Utilisation de la variable PORT imposée par Render
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]