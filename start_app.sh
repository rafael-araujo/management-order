#!/bin/bash

# Nome da imagem da sua aplicação
IMAGE_NAME="order-management-app"

# Comando para construir a imagem Docker
echo "Construindo a imagem Docker: $IMAGE_NAME"
docker build -t "$IMAGE_NAME" .

# Verifica se a construção da imagem foi bem-sucedida
if [ $? -ne 0 ]; then
  echo "Erro ao construir a imagem Docker. Saindo..."
  exit 1
fi

echo "Imagem Docker construída com sucesso."

# Comando para subir os containers com Docker Compose
echo "Iniciando os containers com Docker Compose..."
docker compose up -d

# Verifica se o Docker Compose subiu os containers sem erros
if [ $? -ne 0 ]; then
  echo "Erro ao iniciar os containers com Docker Compose. Verifique os logs."
  exit 1
fi

echo "Containers iniciados com sucesso!"