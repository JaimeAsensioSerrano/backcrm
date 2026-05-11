# Paso 1: Compilar con Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Ejecutar con el JRE de Java 21
FROM eclipse-temurin:21-jre-jammy
# Copiamos el archivo .jar generado en el paso anterior
COPY --from=build /target/*.jar app.jar
# Exponemos el puerto 8080 (el que usa Spring Boot por defecto)
EXPOSE 8080
# Comando para arrancar la app
ENTRYPOINT ["java","-jar","/app.jar"]