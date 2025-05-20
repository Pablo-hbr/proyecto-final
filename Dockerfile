# Usa la imagen base de Java 17
FROM eclipse-temurin:17-jdk

LABEL authors="lamdi"

# Define el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el contenido del proyecto al contenedor
COPY . .

#  DA PERMISO DE EJECUCIÓN AL MAVEN WRAPPER
RUN chmod +x ./mvnw

#  COMPILA LA APP
RUN ./mvnw clean package -DskipTests



# Render asigna el puerto mediante variable $PORT
ENV PORT=8080
EXPOSE 8080

# Ejecuta el JAR generado (ajusta si el nombre cambia)
CMD ["java", "-jar", "target/proyectofinal-0.0.1-SNAPSHOT.jar"]

