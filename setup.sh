#!/bin/bash
echo "Generando certificado SSL autofirmado..."
mkdir -p nginx/ssl
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout nginx/ssl/nginx.key \
  -out nginx/ssl/nginx.crt \
  -subj "/CN=localhost"
echo "Certificado generado en nginx/ssl/"
echo "Ejecuta: docker-compose up --build -d --scale app=2"
