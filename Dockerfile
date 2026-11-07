# --- Estágio 1: Build da aplicação ---
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
  
  # Copia apenas os arquivos de definição do Maven para aproveitar o cache de camadas
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
  
  # Baixa as dependências do projeto (se o pom.xml não mudar, esta camada é cacheada)
RUN ./mvnw dependency:go-offline -B
  
  # Copia o código-fonte e compila gerando o JAR compilado
COPY src src
RUN ./mvnw package -DskipTests
  
  # Extrai o JAR em camadas utilizando a ferramenta oficial do Spring Boot
RUN java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted
  
  # --- Estágio 2: Imagem final de execução (Leve e Segura) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
  
  # Cria um grupo e usuário dedicados para não rodar a aplicação como root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
  
  # Copia as camadas extraídas do estágio de build na ordem correta (da que menos muda para a que mais muda)
COPY --from=builder /app/target/extracted/dependencies/ ./
COPY --from=builder /app/target/extracted/spring-boot-loader/ ./
COPY --from=builder /app/target/extracted/snapshot-dependencies/ ./
COPY --from=builder /app/target/extracted/application/ ./
  
  # Boas práticas para produção: Expor a porta e configurar limites de memória saudáveis para a JVM
EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "org.springframework.boot.loader.launch.JarLauncher"]
