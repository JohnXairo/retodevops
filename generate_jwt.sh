#!/bin/bash

SECRET="devops-reto-secret-key-2024-super-secure"

HEADER=$(echo -n '{"alg":"HS256","typ":"JWT"}' | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
PAYLOAD=$(echo -n "{\"iss\":\"devops-consumer-key\",\"exp\":$(($(date +%s) + 3600))}" | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
SIGNATURE=$(echo -n "${HEADER}.${PAYLOAD}" | openssl dgst -sha256 -hmac "${SECRET}" -binary | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')

echo "${HEADER}.${PAYLOAD}.${SIGNATURE}"
