#!/bin/bash

# Nome da imagem da sua aplicação
IMAGE_NAME="order-management-app"

# Comando para construir a imagem Docker SEM CACHE
echo "Construindo a imagem Docker (sem cache): $IMAGE_NAME"
docker build --no-cache -t "$IMAGE_NAME" .

# Verifica se a construção da imagem foi bem-sucedida
if [ $? -ne 0 ]; then
  echo "Erro ao construir a imagem Docker. Saindo..."
  exit 1
fi

echo "Imagem Docker construída com sucesso."

# Comando para subir os containers com Docker Compose, forçando rebuild da imagem
echo "Iniciando os containers com Docker Compose (forçando rebuild)..."
docker compose up --build -d

# Verifica se o Docker Compose subiu os containers sem erros
if [ $? -ne 0 ]; then
  echo "Erro ao iniciar os containers com Docker Compose. Verifique os logs."
  exit 1
fi

echo "Containers iniciados com sucesso!"


# Aguarda alguns segundos para o banco de dados PostgreSQL iniciar completamente
echo "Aguardando 10 segundos para o banco de dados iniciar..."
sleep 10

# Comando para executar o script init.sql dentro do container do PostgreSQL
echo "Executando script de inicialização do banco de dados..."
docker exec -it $(docker compose ps -q db) psql -U postgres -d order_database -f /docker-entrypoint-initdb.d/init.sql

# Verifica se a execução do script foi bem-sucedida
if [ $? -ne 0 ]; then
  echo "Erro ao executar o script de inicialização do banco de dados. Verifique os logs do container 'db'."
fi

echo "Script de inicialização do banco de dados executado (se não houve erros)."
