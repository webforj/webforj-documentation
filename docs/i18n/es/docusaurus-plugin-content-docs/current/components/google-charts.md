---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: a733a52b4d9ffb87eae039e9729b9cb9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude='true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

El componente `GoogleChart` integra la biblioteca de [Google Charts](https://developers.google.com/chart) en webforJ, brindándote acceso a tipos de gráficos como de barras, líneas, tarta, geográficos y más. Los gráficos se configuran con Java utilizando un tipo, un conjunto de datos y un mapa de opciones que controla la apariencia y el comportamiento.

<!-- INTRO_END -->

## Creando un gráfico {#creating-a-chart}

:::info Importando Google Charts
Para usar la clase `GoogleChart` en tu aplicación, utiliza el siguiente XML en tu archivo POM:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-googlecharts</artifactId>
  <version>${webforj.version}</version>
</dependency>
```
:::

Para crear un gráfico, especifica un tipo de gráfico, configura sus opciones visuales y proporciona los datos para mostrar.

Este ejemplo crea un gráfico geográfico que mapea datos de ingresos a través de diferentes países, con colores personalizados, posicionamiento de leyendas y tamaños del área del gráfico:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Tipos de gráficos {#chart-types}

El complemento `GoogleChart` ofrece una amplia variedad de tipos de gráficos para satisfacer diversos requisitos de visualización de datos. Seleccionar el tipo de gráfico adecuado es esencial para comunicar efectivamente la historia de los datos. Consulta la galería a continuación para ver ejemplos de gráficos comunes que se pueden usar en una aplicación webforJ.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Opciones {#options}

El complemento `GoogleChart` permite una amplia personalización a través de una variedad de opciones. Estas opciones te permiten adaptar la apariencia y funcionalidad de tus gráficos para ajustarse a las necesidades de tu aplicación. Las opciones se pasan como un `Map<String, Object>` al método `setOptions()` del gráfico.

Aquí hay un ejemplo para establecer las opciones de un gráfico:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Ingresos Mensuales");
options.put("backgroundColor", "#EFEFEF");

// Aplicar las opciones al gráfico
chart.setOptions(options);
```

Para obtener más información sobre las opciones disponibles para gráficos específicos, consulta la [referencia de la API de Google Visualization (Galería de Gráficos)](https://developers.google.com/chart/interactive/docs/gallery).

## Estableciendo datos {#setting-data}

Visualizar datos con `GoogleChart` requiere estructurar y establecer correctamente los datos. Esta guía te llevará a través de la preparación de tus datos y su aplicación a tus gráficos.

### Configuración básica de datos {#basic-data-setup}

La forma más sencilla de definir los datos es utilizando `List<Object>`, donde cada fila es una lista de valores.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tarea", "Horas por Día"));
data.add(Arrays.asList("Trabajo", 11));
data.add(Arrays.asList("Comer", 2));
data.add(Arrays.asList("Transporte", 2));
data.add(Arrays.asList("Ver televisión", 2));
data.add(Arrays.asList("Dormir", 7));
chart.setData(data);
```

### Usando mapas para estructuras más complejas {#using-maps-for-more-complex-structures}

Para estructuras de datos más complejas, puedes usar mapas para representar filas y luego convertirlas al formato requerido.

```java
List<Object> data = new ArrayList<>();

// Fila de encabezado
data.add(Arrays.asList("País", "Ingresos"));

// Filas de datos
Map<String, Object> row1 = Map.of("País", "Alemania", "Ingresos", 1000);
Map<String, Object> row2 = Map.of("País", "Estados Unidos", "Ingresos", 1170);
Map<String, Object> row3 = Map.of("País", "Brasil", "Ingresos", 660);

data.add(new ArrayList<>(row1.values()));
data.add(new ArrayList<>(row2.values()));
data.add(new ArrayList<>(row3.values()));

chart.setData(data);
```

Una vez que los datos están preparados, se pueden aplicar al GoogleChart utilizando el método setData.

<ComponentDemo
path='/webforj/chartsettingdata'
files={['src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java']}
height='300px'
/>

### Cargando datos y opciones desde JSON {#loading-data-and-options-from-json}

También puedes cargar datos y opciones desde archivos JSON utilizando Gson para una gestión más fácil. Este enfoque ayuda a mantener tus datos y opciones organizados y fáciles de actualizar.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Año", "Ventas", "Gastos"));
data.add(Arrays.asList("2013", 1000, 400));
data.add(Arrays.asList("2014", 1170, 460));
data.add(Arrays.asList("2015", 660, null));
data.add(Arrays.asList("2016", 1030, 540));
chart.setData(data);

Map<String, Object> options = new Gson().fromJson(
  Assets.contentOf("options.json"),
  new TypeToken<Map<String, Object>>() {}.getType()
);
chart.setOptions(options);
```

## Actualizando visuales de gráficos {#updating-chart-visuals}

Actualizar o refrescar la apariencia de tus gráficos en respuesta a cambios de datos, interacciones del usuario o ajustes de opciones visuales es sencillo con el método `redraw()`. Este método asegura que tus gráficos sigan siendo precisos y visualmente alineados con los datos subyacentes o cualquier modificación a su configuración.

Invoca `redraw()` en escenarios como:

- **Después de Modificaciones de Datos**: Asegura que el gráfico refleje cualquier actualización a su fuente de datos.
- **Al Cambiar Opciones**: Aplica nuevos estilos o cambios de configuración al gráfico.
- **Para Ajustes Responsivos**: Ajusta el diseño o tamaño del gráfico cuando cambien las dimensiones del contenedor, asegurando una visualización óptima en todos los dispositivos.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Exportando gráficos como imágenes {#exporting-charts-as-images}

El método `getImageUri()` proporciona una forma de exportar tus Google Charts como imágenes PNG codificadas en base64. Este método es particularmente útil para compartir gráficos fuera del entorno web, incrustándolos en correos electrónicos o documentos, o simplemente para propósitos de archivo.

Llama a `getImageUri()` en tu instancia de gráfico después de que el gráfico se haya renderizado completamente. Típicamente, este método se usa dentro de un oyente de eventos "listo" para asegurar que el gráfico esté listo para exportar:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Ahora puedes usar el imageUri, por ejemplo, como el atributo src de una etiqueta img
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

El `GoogleChartSelectedEvent` se activa cada vez que un usuario selecciona un punto de datos o un segmento en un componente de Google Chart. Este evento permite la interacción con los datos seleccionados del gráfico, proporcionando detalles sobre lo que se seleccionó. El evento se puede escuchar utilizando el método `addSelectedListener()` en la instancia de `GoogleChart`.

El `GoogleChartSelectedEvent` es útil en aplicaciones donde la interacción del usuario con el gráfico es necesaria.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Agregar el oyente seleccionado al gráfico
chart.addSelectedListener(event -> {
  // Obtener la selección
  List<Object> selection = chart.getSelection();

  // Manejar el evento seleccionado
  if (!selection.isEmpty()) {
    System.out.println("Fila Seleccionada: " + selection.get(0));
    // Procesamiento adicional basado en la fila/columna de selección
  }
});
```

### Carga útil {#payload}
El `GoogleChartSelectedEvent` proporciona acceso a los datos de selección, que se pueden recuperar utilizando el método `getSelection()` en el objeto gráfico. Este método devuelve una lista de objetos, donde cada objeto contiene las siguientes propiedades:

- **row**: El índice de la fila en la tabla de datos del gráfico que fue seleccionada.
- **column**: El índice de la columna en la tabla de datos, que es opcional y se aplica a gráficos que permiten la selección de celdas individuales, como un gráfico de tabla.

Para gráficos como gráficos de tarta o gráficos de barras, solo se proporciona típicamente el `row`, indicando el punto de datos seleccionado.

Aquí hay un ejemplo de carga útil:
```java
[
  {
    "row": 3,  // El índice de fila seleccionada en los datos
    "column": 2  // (Opcional) El índice de columna seleccionada
  }
]
```

:::info Seleccionando Múltiples Puntos de Datos
Si el usuario selecciona múltiples puntos de datos, el método `getSelection()` devolverá un arreglo de objetos, cada uno representando un elemento seleccionado. La carga útil puede variar según el tipo de gráfico y la interacción que realice el usuario.
:::
