# SoftwareIoT-Aeropuerto

Desarrollar un sistema de seguimiento del nivel de ruido de un aeropuerto haciendo uso de
 tecnología Arduino (real y simulada) y considerando la arquitectura de 4 capas de IoT. El sistema de seguimiento debe ser implementado usando el microframework Spark de Java en conjunto con la librería weka y el gestor de bases de datos MongoDB. El sistema debe permitir realizar las siguientes operaciones:

• Obtener en tiempo real el valor del ruido en decibeles a partir de 5 tarjetas (1 sensor por tarjeta) distribuidas en las principales salas del aeropuerto.

• A medida que se vaya obteniendo los valores del ruido ir clasificando dichos valores según las escalas convencionales.

• Presentar las gráficas en tiempo real con los valores que van tomando el sensor en las diferentes salas (Haga uso de librerías de javascript como Canvas.js, Chart.js, etc.).

• Consultar el histórico de los datos obtenidos por cada sala.

• Obtener y graficar la regresión lineal de los datos históricos de cada sala a través del uso de weka.

• Aplicar el algoritmo de SimpleKMeans y determinar los cluster y/o centroides a partir del histórico de los datos y presentarlos en pantallas a través de tablas html.

• Escoger algún otro algoritmo de weka (Cluster, Asociación, Clasificación, etc) sobre el histórico de los datos y presentar los resultados en pantalla.