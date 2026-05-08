#!/bin/bash

echo "================================================"
echo "   RETO DEVOPS - DEMO DE VALIDACION"
echo "================================================"

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m'

echo ""
echo -e "${BLUE}[1] Verificando contenedores activos...${NC}"
docker-compose ps

echo ""
echo -e "${BLUE}[2] Generando JWT unico para esta transaccion...${NC}"
JWT=$(python3 generate_jwt.py)
echo -e "JWT generado: ${GREEN}$JWT${NC}"

echo ""
echo -e "${BLUE}[3] Probando endpoint POST /DevOps (request valido)...${NC}"
RESPONSE=$(curl -s -X POST \
  --header "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
  --header "X-JWT-KWY: $JWT" \
  --header "Content-Type: application/json" \
  --data '{"message":"This is a test","to":"Juan Perez","from":"Rita Asturia","timeToLifeSec":45}' \
  http://localhost/DevOps)
echo -e "Respuesta: ${GREEN}$RESPONSE${NC}"

echo ""
echo -e "${BLUE}[4] Probando seguridad - API Key invalida (debe dar 403)...${NC}"
RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" -X POST \
  --header "X-Parse-REST-API-Key: invalid-key" \
  --header "X-JWT-KWY: $JWT" \
  --header "Content-Type: application/json" \
  --data '{"message":"This is a test","to":"Juan Perez","from":"Rita Asturia","timeToLifeSec":45}' \
  http://localhost/DevOps)
echo -e "HTTP Status: ${GREEN}$RESPONSE${NC}"

echo ""
echo -e "${BLUE}[5] Probando metodo GET (debe retornar ERROR)...${NC}"
RESPONSE=$(curl -s \
  --header "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
  --header "X-JWT-KWY: $JWT" \
  http://localhost/DevOps)
echo -e "Respuesta: ${GREEN}$RESPONSE${NC}"

echo ""
echo -e "${BLUE}[6] Demostrando escalabilidad dinamica...${NC}"
echo "Escalando a 4 nodos..."
docker compose up -d --scale app=4 2>/dev/null
sleep 3
docker compose ps
echo ""
echo "Reduciendo a 2 nodos..."
docker compose up -d --scale app=2 2>/dev/null
sleep 3
docker compose ps

echo ""
echo -e "${BLUE}[7] Verificando balanceo de carga - 4 requests consecutivos...${NC}"
for i in 1 2 3 4; do
  JWT=$(python3 generate_jwt.py)
  RESPONSE=$(curl -s -X POST \
    --header "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
    --header "X-JWT-KWY: $JWT" \
    --header "Content-Type: application/json" \
    --data '{"message":"This is a test","to":"Juan Perez","from":"Rita Asturia","timeToLifeSec":45}' \
    http://localhost/DevOps)
  echo -e "Request $i: ${GREEN}$RESPONSE${NC}"
done

echo ""
echo -e "${BLUE}[8] Reporte de cobertura de codigo...${NC}"
cd app && mvn jacoco:report -q 2>/dev/null
echo -e "${GREEN}Reporte generado en: app/target/site/jacoco/index.html${NC}"
cd ..

echo ""
echo "================================================"
echo -e "${GREEN}   DEMO COMPLETADO EXITOSAMENTE${NC}"
echo "================================================"
