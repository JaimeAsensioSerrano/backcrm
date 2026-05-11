# Etapa 1: Construcción (Build)
FROM eclipse-temurin:21-jdk-jammy AS build
# Instalamos Maven manualmente para asegurar compatibilidad
RUN apt-get update && apt-get install -y maven
COPY . .
# Damos permisos de ejecución al wrapper y construimos
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copiamos el archivo generado (el asterisco ayuda si el nombre varía)
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]