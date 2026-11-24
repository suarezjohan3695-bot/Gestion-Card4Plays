
FROM eclipse-temurin:17

WORKDIR /app

COPY target/gestionCards4play-1.0-jar-with-dependencies.jar app.jar

RUN mkdir -p data && touch Datos/usuarios.json

EXPOSE 4567

CMD ["java", "-jar", "app.jar"]

