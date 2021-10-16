# Subway Challenge

### Errores detectados en el archivo Metro_CDMX.kml
- **Falta Centro Médico en la Línea 3**: Sí existe la estación y está asociada a la Línea 9 pero no existen las coordenadas en el listado de la Línea 3 (ni una cercana).
- **Hay varios puntos que no coinciden exactamente**: Pero sí tienen su correspondiente punto cercano. Se implementó un mecanísmo para calcular un márgen de tolerancia basado en la distancia euclidiana. [Ver mapa](https://www.google.com/maps/d/u/0/edit?mid=1lZmHGontCqxqGFpb6p1KxlZKCUCFXCGO&usp=sharing) y [Reporte](ImpreciseStations-Report.html)
- **Hay un punto entre Indios Verdes y Deportivo 18 de Marzo que no corresponde a ninguna estación**: Simplemente se ignoró. El punto es `-99.1209751134766,19.4906856228133,0`.

### Otras notas
- Se agrega el json respuesta del endpoint http://localhost:8080/subway/api/v1/map/list con todas las líneas y todas las estaciones. [JSON con el listado de la red del Metro](metro_map.json)

https://jgrapht.org/guide/UserOverview#hello-jgrapht
https://jgrapht.org/javadoc/org.jgrapht.core/org/jgrapht/graph/AsUnweightedGraph.html

SimpleWeightedGraph	undirected	no	no	yes  
SimpleGraph	undirected	no	no	no
