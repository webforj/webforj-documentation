---
title: Google Charts
sidebar_position: 50
description: >-
  Render bar, line, pie, geo, and other Google Charts in webforJ using the
  GoogleChart component with a typed Java options map and data API.
_i18n_hash: 8e1916e526ef45c55ca75c6cd32a4e7f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude='true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

El componente `GoogleChart` integra la biblioteca de [Google Charts](https://developers.google.com/chart) en webforJ, dándote acceso a tipos de gráficos como barras, líneas, pasteles, geográficos, y más. Los gráficos se configuran con Java utilizando un tipo, un conjunto de datos y un mapa de opciones que controla la apariencia y el comportamiento.

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

Para crear un gráfico, especifica un tipo de gráfico, configura sus opciones visuales y proporciona los datos a mostrar.

Este ejemplo crea un gráfico geográfico que representa los datos de ingresos en diferentes países, con colores personalizados, posicionamiento de leyenda y tamaño del área del gráfico:

<ComponentDemo
path='/webforj/chart'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartView.java',
  'src/main/frontend/css/googlecharts/chart.css',
]}
height='300px'
/>

## Tipos de gráficos {#chart-types}

El complemento `GoogleChart` ofrece una amplia variedad de tipos de gráficos para satisfacer diversos requisitos de visualización de datos. Seleccionar el tipo de gráfico apropiado es esencial para comunicar efectivamente la historia de los datos. Consulta la galería a continuación para ejemplos de gráficos comunes que se pueden utilizar en una aplicación webforJ.

<ComponentDemo
path='/webforj/chartgallery'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java',
  'src/main/frontend/css/googlecharts/chartGallery.css',
]}
height='600px'
/>

## Opciones {#options}

El complemento `GoogleChart` permite una extensa personalización a través de una variedad de opciones. Estas opciones te permiten adaptar la apariencia y funcionalidad de tus gráficos para ajustarse a las necesidades de tu aplicación. Las opciones se pasan como un `Map<String, Object>` al método `setOptions()` del gráfico.

Aquí tienes un ejemplo de cómo establecer las opciones de un gráfico:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Ingresos Mensuales");
options.put("backgroundColor", "#EFEFEF");

// Aplicar las opciones al gráfico
chart.setOptions(options);
```

Para más información sobre las opciones disponibles para gráficos específicos, consulta la [referencia de API de Visualización de Google (Galería de Gráficos)](https://developers.google.com/chart/interactive/docs/gallery).

## Estableciendo datos {#setting-data}

Visualizar datos con `GoogleChart` requiere estructurar y establecer correctamente los datos. Esta guía te mostrará cómo preparar tus datos y aplicarlos a tus gráficos.

### Configuración básica de datos {#basic-data-setup}

La forma más sencilla de definir los datos es utilizando `List<Object>`, donde cada fila es una lista de valores.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tarea", "Horas por Día"));
data.add(Arrays.asList("Trabajo", 11));
data.add(Arrays.asList("Comer", 2));
data.add(Arrays.asList("Transporte", 2));
data.add(Arrays.asList("Ver TV", 2));
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
Map<String, Object> row1 = Map.of("Country", "Alemania", "Revenue", 1000);
Map<String, Object> row2 = Map.of("Country", "Estados Unidos", "Revenue", 1170);
Map<String, Object> row3 = Map.of("Country", "Brasil", "Revenue", 660);

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

También puedes cargar datos y opciones desde archivos JSON utilizando Gson para facilitar la gestión. Este enfoque ayuda a mantener tus datos y opciones organizados y fáciles de actualizar.

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

## Actualizando visuales del gráfico {#updating-chart-visuals}

El `GoogleChart` se actualiza automáticamente cuando sus datos, opciones, tipo o selección cambian. No necesitas llamar a `redraw()` después de usar `setData()`, `setOptions()`, `setType()` o `setSelection()`.

El componente también escucha eventos de redimensionamiento de la ventana del navegador y se redibuja automáticamente para ajustarse a las nuevas dimensiones.

Llama a `redraw()` para cambios en el tamaño del contenedor que no activan un evento de redimensionamiento de la ventana. Ejemplos incluyen expandir un panel y redimensionar una barra lateral. Llámalo después de que se hayan aplicado las nuevas dimensiones del contenedor.

La siguiente demostración actualiza los datos del gráfico con `setData()`. El gráfico se redibuja automáticamente cuando se aplican los nuevos datos.

<ComponentDemo
path='/webforj/chartredraw'
files={[
  'src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java',
  'src/main/frontend/css/googlecharts/chartRedraw.css',
]}
height='650px'
/>

## Exportando gráficos como imágenes {#exporting-charts-as-images}

El método `getImageUri()` proporciona una forma de exportar tus Gráficos de Google como imágenes PNG codificadas en base64. Este método es particularmente útil para compartir gráficos fuera del entorno web, incrustarlos en correos electrónicos o documentos, o simplemente con fines de archivo.

Llama a `getImageUri()` en tu instancia de gráfico después de que el gráfico se haya renderizado completamente. Típicamente, este método se usa dentro de un oyente de evento "listo" para asegurar que el gráfico esté listo para la exportación:

```java
chart.addReadyListener(e -> {
  String imageUri = chart.getImageUri();
  // Ahora puedes usar el imageUri, por ejemplo, como el atributo src de una etiqueta img
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

El `GoogleChartSelectedEvent` se activa siempre que un usuario selecciona un punto de datos o segmento en un componente Google Chart. Este evento permite la interacción con los datos seleccionados del gráfico, proporcionando detalles sobre lo que se seleccionó. Se puede escuchar el evento utilizando el método `addSelectedListener()` en la instancia de `GoogleChart`.

El `GoogleChartSelectedEvent` es útil en aplicaciones donde la interacción del usuario con el gráfico es necesaria.

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Agrega el oyente de selección al gráfico
chart.addSelectedListener(event -> {
  // Obtener la selección
  List<Object> selection = chart.getSelection();

  // Manejar el evento seleccionado
  if (!selection.isEmpty()) {
    System.out.println("Fila seleccionada: " + selection.get(0));
    // Procesamiento adicional basado en la fila/columna de la selección
  }
});
```

### Carga útil {#payload}
El `GoogleChartSelectedEvent` proporciona acceso a los datos de selección, que se pueden recuperar utilizando el método `getSelection()` en el objeto gráfico. Este método devuelve una lista de objetos, donde cada objeto contiene las siguientes propiedades:

- **row**: El índice de la fila en la tabla de datos del gráfico que fue seleccionada.
- **column**: El índice de la columna en la tabla de datos, que es opcional y se aplica a gráficos que permiten la selección de celdas individuales, como un gráfico de tabla.

Para gráficos como gráficos de pastel o gráficos de barras, generalmente solo se proporciona `row`, indicando el punto de datos seleccionado.

Aquí tienes un ejemplo de carga útil:
```java
[
  {
    "row": 3,  // El índice de fila seleccionada en los datos
    "column": 2  // (Opcional) El índice de columna seleccionada
  }
]
```

:::info Seleccionando múltiples puntos de datos
Si el usuario selecciona múltiples puntos de datos, el método `getSelection()` devolverá un array de objetos, cada uno representando un elemento seleccionado. La carga útil puede variar según el tipo de gráfico y la interacción realizada por el usuario.
:::
