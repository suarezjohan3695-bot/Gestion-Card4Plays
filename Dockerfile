
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY ["Source packages", "./Source packages"]
RUN mvn -q clean package
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/target/gestionCards4play-1.0-jar-with-dependencies.jar app.jar
RUN mkdir -p data && touch data/usuarios.json data/productos.json data/torneos.json data/compras.json
EXPOSE 4567
CMD ["java", "-jar", "app.jar"]

