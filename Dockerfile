# Use uma imagem base com Java 21 (JDK)
FROM eclipse-temurin:21

# Defina um diretório de trabalho dentro do container
WORKDIR /app

# Instale o Git
RUN apt-get update && apt-get install -y git

# Defina as variáveis de ambiente para o GitHub (opcional, mas recomendado para acesso privado)
#ARG GITHUB_USERNAME
#ARG GITHUB_TOKEN

# Clone o repositório do GitHub (use as variáveis de ambiente se fornecidas)
# RUN if [ -n "$GITHUB_USERNAME" ] && [ -n "$GITHUB_TOKEN" ]; then \
#     git clone https://$GITHUB_USERNAME:$GITHUB_TOKEN@github.com/rafael-araujo/management-order.git .; \
#   else \
    git clone https://github.com/rafael-araujo/management-order.git .; \
#   fi

# Limpe o cache do apt após a instalação do git
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/log/*

# Execute o build da aplicação usando Maven
RUN ./mvnw clean package -DskipTests

# Defina a variável de ambiente para o perfil do Spring (opcional)
ENV SPRING_PROFILES_ACTIVE=default

# Exponha a porta em que a aplicação Spring Boot estará rodando (geralmente 8080)
EXPOSE 8080

# Comando para executar a aplicação Spring Boot
CMD ["java", "-jar", "target/*.jar"]