# Spring Boot App - Docker y Kubernetes

## Requisitos

- Docker instalado
- Kubernetes (minikube, kind, o cluster real)
- (Opcional) Cuenta en Docker Hub

---

## Uso en ambiente local con Docker

1. Construir la imagen (si no esta construida):
docker build -t equipment-inventory-springboot:3.4.5-java21 .

2. Ejecutar el contenedor:
docker run -p 8080:8080 equipment-inventory-springboot:3.4.5-java21

3. Acceder a la aplicacion en: `http://localhost:8080/swagger-ui/index.html`

## Subir la imagen a Docker Hub (opcional, para Kubernetes remoto)

1. Iniciar sesion en Docker Hub:
docker login

2. Etiquetar la imagen:
docker tag equipment-inventory-springboot:3.4.5-java21 tuusuario/equipment-inventory-springboot:3.4.5-java21

3. Subir la imagen:
docker push tuusuario/equipment-inventory-springboot:3.4.5-java21

