# Estágio 1: Build da aplicação (Multi-stage build)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia apenas o arquivo de declaração de dependências primeiro
# Isso otimiza o cache de camadas do Docker: se o pom.xml não mudar, o Docker usa o cache
COPY pom.xml .

# Baixa as dependências (go-offline) para mantê-las em cache
RUN mvn dependency:go-offline -B

# Copia todo o código fonte
COPY src ./src

# Compila o projeto e empacota em um JAR, ignorando a execução de testes no build da imagem
RUN mvn clean package -DskipTests

# Estágio 2: Criação da imagem final de execução, enxuta
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Cria um usuário não-root por razões de segurança (evita rodar a aplicação como root no container)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser:appgroup

# Informa qual porta será exposta pelo container
EXPOSE 8080

# Copia o arquivo JAR compilado (do estágio 'builder') para o contêiner atual
COPY --from=builder /app/target/*.jar app.jar

# Define o ponto de entrada da nossa aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
