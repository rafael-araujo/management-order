# Nome da imagem da sua aplicação
$ImageName = "order-management-app"

# Comando para construir a imagem Docker SEM CACHE
Write-Host "Construindo a imagem Docker (sem cache): $ImageName"
docker build --no-cache -t $ImageName .

# Verifica se a construção da imagem foi bem-sucedida
if ($LASTEXITCODE -ne 0) {
    Write-Error "Erro ao construir a imagem Docker. Saindo..."
    exit 1
}

Write-Host "Imagem Docker construída com sucesso."

# Comando para subir os containers com Docker Compose, forçando rebuild da imagem
Write-Host "Iniciando os containers com Docker Compose (forçando rebuild)..."
docker compose up --build -d

# Verifica se o Docker Compose subiu os containers sem erros
if ($LASTEXITCODE -ne 0) {
    Write-Error "Erro ao iniciar os containers com Docker Compose. Verifique os logs."
    exit 1
}

Write-Host "Containers iniciados com sucesso!"