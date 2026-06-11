# =====================================================================
# ETAPA 1: Compilación interna en Docker usando Maven y JDK 17
# =====================================================================
FROM maven:3.8.8-eclipse-temurin-17 AS builder
WORKDIR /build

# 1. Copiar el archivo de configuración de dependencias
COPY pom.xml .

# 2. Copiar toda la carpeta de código fuente (src)
COPY src ./src

# 3. Compilar el proyecto dentro de Docker (genera el Fat JAR gracias al plugin)
RUN mvn clean package -DskipTests


# =====================================================================
# ETAPA 2: Imagen final optimizada para la ejecución de Pekko / Java
# =====================================================================
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiar el archivo .jar generado en la Etapa 1 directamente a esta nueva imagen
# Nota: Usamos "lab7-0.0.1-SNAPSHOT.jar" o "*.jar" para asegurar que capture el Fat JAR unificado.
COPY --from=builder /build/target/lab7-0.0.1-SNAPSHOT.jar app.jar

# El comando por defecto (se sobreescribirá con los "command" del docker-compose.yml)
CMD ["java", "-jar", "app.jar"]