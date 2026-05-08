FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build
COPY app/pom.xml .
COPY app/checkstyle.xml .
RUN mvn dependency:go-offline -q
COPY app/src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY --from=builder /build/target/reto-1.0.0.jar app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
