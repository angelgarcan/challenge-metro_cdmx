# Mexico City Metro Challenge

El reto consiste en crear un servicio para usar el sistema de transporte metro dentro de la CDMX.

Se compone de 4 etapas sucesivas.

## Etapa 1

A partir el archivo .kml proporcionado, obtener la descripción de la todas las líneas del metro. Cada línea tiene un nombre y una lista de estaciones. Cada estación tiene un nombre y unas coordenadas geográficas (latitud y longitud). 

**SOLUCIÓN:** [**/map/list**](http://localhost:8080/subway/api/v1/map/list)

## Etapa 2

Basándose en la etapa anterior, crear un programa que a partir de los nombres de un par de estaciones, te de instrucciones precisas para trasladarte de una estación a otra, incluyendo todos los detalles necesarios para cada segmento de la ruta y los transbordes que hay que realizar en caso de que la ruta se componga de varios segmentos.

Para cada segmento debe indicar:

- estación de origen.
- estación destino.
- dirección en la que hay que abordar.
- número de estaciones que hay que viajar.

Los transbordos deben indicar a qué línea se debe dirigir para el nuevo segmento.

**SOLUCIÓN:** [**/itinerary/pretty**](http://localhost:8080/subway/api/v1/itinerary/pretty?start=Iztapalapa&end=La%20Raza)

## Etapa 3

Exponer la funcionalidad anterior como un API REST

**SOLUCIÓN:** [**Swagger Documentation**](http://localhost:8080/subway/api/v1/swagger-ui.html#/main-controller) 

## Etapa 4

Crear una interfaz de usuario que permita utilizar este servicio via el api REST.
