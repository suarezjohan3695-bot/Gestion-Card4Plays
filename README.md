Intengrantes: Johan Esteven Suarez Aristizabal - Caren Mayerli Astudillo
Contruir imagen desde la carpeta raiz : docker build -t cards4play .
Contruir volumen: docker volume create persistencia-cards4play
ejecutar contenedor: docker run --rm -p 8080:8080 -e PORT=8080 -e TASA_DEFECTO=3500 cards4play
