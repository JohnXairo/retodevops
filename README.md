# retodevops

Microservicio REST construido con Java 17 + Spring Boot 3.

## Arquitectura
Cliente → Nginx (API Gateway / Balanceador de Carga) → app1 / app2 (Spring Boot)

### Gestión de API
Nginx actúa como API Gateway gestionando:
- Punto único de entrada para todo el tráfico
- Balanceo de carga entre dos nodos de aplicación
- Enrutamiento de peticiones al endpoint /DevOps

### Seguridad
Spring Security gestiona la autenticación:
- Validación de API Key mediante header `X-Parse-REST-API-Key`
- Generación y validación de JWT mediante header `X-JWT-KWY`
- Autenticación stateless por transacción

## Stack
- Java 17 + Spring Boot 3
- Spring Security + JJWT
- Docker + Docker Compose
- Nginx (API Gateway + Balanceador de Carga)
- GitHub Actions (CI/CD)
- JaCoCo (cobertura de código >80%)
- Checkstyle (análisis estático)

## Ejecutar localmente
```bash
docker-compose up --build -d --scale app=2
```

## Generar JWT
```bash
./generate_jwt.sh
```

## Prueba del endpoint
```bash
curl -X POST \
  -H "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
  -H "X-JWT-KWY: ${JWT}" \
  -H "Content-Type: application/json" \
  -d '{ "message": "This is a test", "to": "Juan Perez", "from": "Rita Asturia", "timeToLifeSec": 45 }' \
  https://${HOST}/DevOps
```

## Respuesta esperada
```json
{"message": "Hello Juan Perez your message will be sent"}
```

## Escalabilidad dinámica
```bash
# Escalar a 4 nodos
docker-compose up -d --scale app=4

# Reducir a 2 nodos
docker-compose up -d --scale app=2
```

## Demo completo
```bash
./demo.sh
```

## CI/CD
- Push a cualquier rama → CI Pipeline (build + test + análisis estático)
- Merge a main → Deploy Pipeline (automático)
- Tag vX.Y.Z → Deploy por versión (bajo demanda)
