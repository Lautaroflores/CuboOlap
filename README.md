# Enunciado

Se desea construir una librería que permita manipular y analizar datos multidimensionales
construyendo un cubo OLAP (https://es.wikipedia.org/wiki/Cubo_OLAP) para el lenguaje
Java. Deberá ofrecer estructuras de datos y operaciones que soporten la funcionalidad
solicitada, como así también contemple posibles extensiones futuras, minimizando el
impacto ante alguna modificación.
En principio no es necesario hacer foco en la eficiencia de las operaciones, pero sería
deseable disponer también de algún mecanismo que nos permita cuantificar, al menos en
tiempo, el costo de su ejecución.
Es posible apoyarse en estructuras existentes de forma nativa en el lenguaje Java para
construir las estructuras de datos, pero **no se deben utilizar librerías externas**

# Funcionalidad principal de la librería

En este apartado se describe la funcionalidad mínima que se requiere de la librería. Toda
información adicional que se necesite para clarificar funcionalidad se deberá consultar en
clase ejercitando el proceso de relevamiento de requerimientos con el equipo docente. Es
importante tener bien definidos los requerimientos del producto a construir y que serán parte
de la documentación de análisis.
# La estructura del cubo
La estructura multidimensional se creará a partir de datos externos en un formato dado. En
principio se solicita generar una estructura de 3 dimensiones, pero debería contemplarse su
extensión a futuro.
Se recomienda pensar la estructura del cubo como **inmutable**.

## Dimensiones

Una dimensión es un conjunto de términos relacionados con los cuales nos interesa
observar ciertas medidas de registros. Por ejemplo, la dimensión Tiempo podría tener
miembros a cada año (2022, 2023, 2024, etc). A su vez, una dimensión puede tener uno o
más niveles que organizan sus miembros de forma jerárquica. En el ejemplo de Tiempo,
podríamos tener un nivel para años, luego otro para cuatrimestres (Q1, Q2, Q3, Q4), otro
para meses (enero, febrero, marzo, etc), otro para días, etc.
En general, las dimensiones suelen corresponder a variables categóricas de un dataset.
Podemos pensar que existe un nivel máximo de abstracción denominado “Todos”, donde se
agrupan todos los miembros de la dimensión. Sería una agrupación o **reducción total de la
dimensión.**

## Hechos y Medidas

Un hecho (fact) es una observación numérica asociada a cierta combinación única de
miembros de las dimensiones. Por ejemplo, podría ser la cantidad de unidades vendidas de
cierto producto (dimensión Producto), en cierto local (dimensión Ubicación), en cierta fecha
(dimensión Tiempo).
Una medida es una función numérica aplicada sobre los hechos. Si no aplicamos ninguna
operación al cubo, la medida suele ser el mismo valor que cada hecho (observación). Si
aplicamos alguna operación de reducción de dimensiones, la medida es el valor en cada
celda agrupada resultante de esas reducciones. Por lo tanto, un cubo tiene celdas que
representan medidas sobre los hechos que correspondan a esa agrupación de
observaciones (si las hay).
Las medidas a computar en cada celda por el momento son:

● count: contar la cantidad de hechos

● min: el valor de hecho mínimo

● max: el valor de hecho máximo

● sum: la suma de los valores de los hechos

## Creación del cubo

Limitaremos la creación del cubo a partir de un único formato: archivos csv. Pero se debe
contemplar que en el futuro existan nuevas formas de creación (extensibilidad). Serán
provistos archivos para cada dimensión y para los hechos.

## Archivos de dimensión

Describen en cada línea a un miembro de la dimensión al máximo nivel de detalle, por lo
cual pueden incluir columnas que representen niveles de la jerarquía. Cada miembro tiene
asociado un identificador único que servirá como clave para referenciar en el archivo de
hechos.
Cada dimensión se describe en su propio archivo csv, representa la tabla de dimensión.
Al momento de crear una dimensión se debe proveer un nombre de dimensión, un archivo
csv desde donde obtener los miembros y determinar las columnas que definen cada nivel
de la jerarquía.
Es necesario generar las dimensiones del cubo antes de cargar los hechos.
## Archivo de hechos

Describe en cada línea a una observación que se compone por el identificador de cada
dimensión y uno o más hechos numéricos, representa la tabla de hechos.
Al momento de cargar los hechos se debe validar que las dimensiones estén pregeneradas
y que existan los miembros referenciados, de lo contrario arrojar excepción.

## Información básica del cubo

Se debe poder conocer la siguiente información sobre una instancia de cubo dada:

● Nombre del cubo

● Cantidad y nombres de dimensiones

● Información de cada dimensión:

  ○ Niveles jerárquicos
  
  ○ Miembros (elementos) de la dimensión en formato tabular
  
● Listado de hechos contenidos en cada celda (ventas, costo, unidades, etc)

● Medidas representadas en cada celda

## Visualización

Definiremos tres formas de visualizar información del cubo que dependen de cuántas
dimensiones se especifican para mostrar.

● 0 dimensiones: Se muestra una única celda donde se agrupan (reducen)
totalmente todas las dimensiones existentes.

● 1 dimensión: Se muestra una tabla con dos columnas, la primera corresponde al
valor (miembro) de la dimensión, y la segunda a la celda asociada con mediciones
calculadas con la agrupación (reducción) total de las otras dimensiones del cubo.

● 2 dimensiones: Se muestra una tabla A x B, donde A corresponde a los miembros
de la primera dimensión y B a los miembros de la segunda dimensión, es decir, las
etiquetas de filas son los miembros de A y las etiquetas de columnas son los
miembros de B. Las celdas muestran las medidas correspondientes de esas
dimensiones, junto con la agrupación (reducción) total de la dimensión restante del
cubo.

Por ejemplo, si pedimos visualizar Productos y Tiempo, las filas serán de cada
producto y las columnas representan cada día/mes/año (según el nivel de
agrupación de ese momento). En cada celda se verán las medidas de cada
combinación de producto en ese tiempo, reduciendo completamente la dimensión
Ubicación.

# Operaciones soportadas

Se deberán soportar las siguientes operaciones para poder explorar y visualizar un cubo.

## Roll-up

Se aplica sobre una dimensión con el objetivo de subir en un nivel de jerarquía. De esta
forma, se reduce la dimensión a menos miembros que agrupan a otros del nivel más bajo.
Se sube el nivel de abstracción. Por ejemplo, en Tiempo podemos hacer un rollup desde
días a meses.
Cuando se efectúa un rollup, la dimensión presenta información sumarizada a través de las
medidas sobre los hechos agrupados.
No se puede hacer un rollup más allá de la agrupación o reducción total (concepto de
“Todos”).

## Drill-down

Es la operación inversa a Rollup, también aplicada en una dimensión para bajar en un nivel
de jerarquía. En este caso se desagrupan celdas respecto al nivel superior porque se
muestran más miembros en la dimensión. Por ejemplo, en Ubicación podemos hacer un
drill-down de provincias a municipios.
No se puede hacer un drill-down más allá del nivel más detallado de la dimensión, es decir,
se puede hacer hasta llegar a hechos sin agrupar para esa dimensión.

## Slice

Dada una dimensión y un valor específico, se genera un subcubo con el resto de las
dimensiones filtrando los hechos correspondientes a la dimensión y valor de corte.
Por ejemplo, si hacemos un slice sobre Tiempo definiendo el año 2024 como valor, se
obtiene un nuevo “cubo” con dimensiones Producto y Ubicación pero sólo con ventas
realizadas en 2024.
Si trabajamos con cubos de 3 dimensiones, esta operación genera una matriz (2
dimensiones) y es análoga a la forma de visualización de 2 dimensiones solicitada
previamente.

## Dice

Es similar a la operación de slice, pero permite seleccionar varias dimensiones de corte y
además un conjunto de valores a filtrar en cada una. En este caso, se devuelve un subcubo
con la misma cantidad de dimensiones pero filtrando los hechos correspondientes a los
solicitados.
Por ejemplo, si hacemos un dice con Tiempo entre 2020 y 2024, y Ubicación de Córdoba o
Salta, obtendremos un cubo con los hechos de esos años y esas provincias únicamente.

# Fuentes de datos

Se proveen las siguientes fuentes de datos para construir el cubo. Todas en formato csv.

## Dimensiones

Cada fuente de dimensión provee sus miembros en su nivel más fino, pero incluye
columnas que permiten identificar los niveles en esa dimensión de cada miembro. En su
nivel más bajo, el miembro de la dimensión se identifica por su campo descriptivo.

● Productos: Descripción de cada producto.

  ○ Archivo: productos.csv
  
  ○ Niveles: Categoría -> Subcategoría -> Producto
  
  ○ Campo descriptivo: producto
  
● Fechas: Descripción de cada fecha.

  ○ Archivo: fechas.csv
  
  ○ Niveles: Año -> Quarter -> Mes -> Día
  
  ○ Campo descriptivo: fecha
  
● Puntos de Venta: Descripción de cada punto de venta.

  ○ Archivo: puntos_venta.csv
  
  ○ Niveles: Región -> País -> Provincia -> Ciudad
  
  ○ Campo descriptivo: punto_venta
  
## Hechos

Representa la venta de cierta cantidad de:

● un mismo producto

● en un punto de venta

● en una fecha (día)

Archivo: ventas.csv

Los campos de la fuente de datos de hechos son:

● id_producto: Key asociada a la dimensión Producto.

● id_fecha: Key asociada a la dimensión Fecha.

● id_punto_venta: Key asociada a la dimensión Punto de Venta.

● cantidad: productos vendidos
● valor_unitario: importe unitario del producto vendido
● valor_total: importe total de la venta
● costo: costo asumido
