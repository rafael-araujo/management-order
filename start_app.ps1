# Nome da imagem da sua aplicação
$ImageName = "management-order-app"

# Comando para construir a imagem Docker
Write-Host "Construindo a imagem Docker: $ImageName"
docker build -t $ImageName .

# Verifica se a construção da imagem foi bem-sucedida
if ($LASTEXITCODE -ne 0) {
    Write-Error "Erro ao construir a imagem Docker. Saindo..."
    exit 1
}

Write-Host "Imagem Docker construída com sucesso."

# Comando para subir os containers com Docker Compose
Write-Host "Iniciando os containers com Docker Compose..."
docker-compose up -d

# Verifica se o Docker Compose subiu os containers sem erros
if ($LASTEXITCODE -ne 0) {
    Write-Error "Erro ao iniciar os containers com Docker Compose. Verifique os logs."
    exit 1
}

Write-Host "Containers iniciados com sucesso!"