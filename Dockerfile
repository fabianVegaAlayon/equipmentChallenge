# Usar imagen base con Java 21 (Amazon Corretto 21)
FROM amazoncorretto:21

WORKDIR /app
# Copiar el JAR generado al contenedor
COPY target/equipmentinventory-0.0.1-SNAPSHOT.jar equipmentinventory.jar

# Exponer el puerto que usa Spring Boot (por defecto 8080)
EXPOSE 8080

# Comando para ejecutar la app Spring Boot
ENTRYPOINT ["java", "-jar", "equipmentinventory.jar"]