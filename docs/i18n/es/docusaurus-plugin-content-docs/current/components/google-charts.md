---
title: Google Charts
sidebar_position: 50
_i18n_hash: b477c90cfb24a59329f3047d7ae7d24c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="google-chart" exclude= 'true' />
<DocChip chip='since' label='23.06' />
<JavadocLink type="googlecharts" location="com/webforj/component/googlecharts/GoogleChart" top='true'/>

<!-- Breve descripción del componente y lo que hace -->

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

La clase `GoogleChart` es una solución integral para incrustar gráficos ricos e interactivos dentro de aplicaciones web. Esta clase actúa como un puente hacia la biblioteca [Google Charts](https://developers.google.com/chart), ofreciendo una amplia variedad de tipos de gráficos adecuados para cualquier tarea de visualización de datos.

<ComponentDemo 
path='/webforj/chart?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartView.java'
cssURL='/css/googlecharts/chart.css'
height='300px'
/>


## Tipos de gráficos {#chart-types}

El complemento `GoogleChart` ofrece una amplia gama de tipos de gráficos para satisfacer diversos requisitos de visualización de datos. Seleccionar el tipo de gráfico apropiado es esencial para comunicar eficazmente la historia de los datos. Consulta la galería a continuación para ver ejemplos de gráficos comunes que se pueden utilizar en una aplicación webforJ.

<ComponentDemo 
path='/webforj/chartgallery?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartGalleryView.java'
cssURL='/css/googlecharts/chartGallery.css'
height='600px'
/>

## Opciones {#options}

El complemento `GoogleChart` permite una extensa personalización a través de una variedad de opciones. Estas opciones te permiten ajustar la apariencia y funcionalidad de tus gráficos para adaptarse a las necesidades de tu aplicación. Las opciones se pasan como un `Map<String, Object>` al método `setOptions()` del gráfico. 

Aquí hay un ejemplo para establecer las opciones de un gráfico:

```java
Map<String, Object> options = new HashMap<>();
options.put("title", "Ingresos Mensuales");
options.put("backgroundColor", "#EFEFEF");

// Aplicar las opciones al gráfico
chart.setOptions(options);
```

Para más información sobre las opciones disponibles para gráficos específicos, consulta la [referencia de la API de Visualización de Google (Galería de Gráficos)](https://developers.google.com/chart/interactive/docs/gallery).

## Estableciendo datos {#setting-data}

Visualizar datos con `GoogleChart` requiere estructurar y establecer correctamente los datos. Esta guía te llevará a través de la preparación de tus datos y su aplicación a tus gráficos.

### Configuración básica de datos {#basic-data-setup}

La forma más sencilla de definir los datos es utilizando `List<Object>`, donde cada fila es una lista de valores.

```java
List<Object> data = new ArrayList<>();
data.add(Arrays.asList("Tarea", "Horas por Día"));
data.add(Arrays.asList("Trabajo", 11));
data.add(Arrays.asList("Comer", 2));
data.add(Arrays.asList("Desplazamiento", 2));
data.add(Arrays.asList("Ver TV", 2));
data.add(Arrays.asList("Dormir", 7));
chart.setData(data);
```

### Usando mapas para estructuras más complejas {#using-maps-for-more-complex-structures}

Para estructuras de datos más complejas, puedes usar mapas para representar filas y luego convertirlas en el formato requerido.

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
path='/webforj/chartsettingdata?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartSettingDataView.java'
height='300px'
/>

<!-- tabs={['ChartDemoSettingData.java']} -->

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

## Actualizando los visuales del gráfico {#updating-chart-visuals}

Actualizar o refrescar la apariencia de tus gráficos en respuesta a cambios en los datos, interacciones del usuario o ajustes en las opciones visuales es sencillo con el método `redraw()`. Este método garantiza que tus gráficos se mantengan precisos y visualmente alineados con los datos subyacentes o cualquier modificación en sus configuraciones.

Invoca `redraw()` en situaciones como:

- **Después de modificaciones en los datos**: Asegura que el gráfico refleje cualquier actualización en su fuente de datos.
- **Al cambiar opciones**: Aplica nuevos estilos o cambios en la configuración al gráfico.
- **Para ajustes responsivos**: Ajusta el diseño o tamaño del gráfico cuando cambian las dimensiones del contenedor, garantizando una visualización óptima en diferentes dispositivos.

<ComponentDemo 
path='/webforj/chartredraw?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/googlecharts/ChartRedrawView.java'
cssURL='/css/googlecharts/chartRedraw.css'
height='650px'
/>

<!-- tabs={['ChartDemoRedraw.java', 'redrawchart_styles.css']} -->

## Exportando gráficos como imágenes {#exporting-charts-as-images}

El método `getImageUri()` proporciona una forma de exportar tus gráficos de Google como imágenes PNG codificadas en base64. Este método es particularmente útil para compartir gráficos fuera del entorno web, incrustándolos en correos electrónicos o documentos, o simplemente para fines de archivo.

Llama a `getImageUri()` en tu instancia de gráfico después de que el gráfico se haya renderizado completamente. Típicamente, este método se usa dentro de un oyente de eventos "listo" para asegurar que el gráfico esté listo para ser exportado:

```java
chart.addReadyListener(e -> {
    String imageUri = chart.getImageUri();
    // Ahora puedes usar imageUri, por ejemplo, como el atributo src de una etiqueta img
});
```

## `GoogleChartSelectedEvent` {#googlechartselectedevent}

El `GoogleChartSelectedEvent` se activa cada vez que un usuario selecciona un punto de datos o segmento en un componente de Google Chart. Este evento permite interactuar con los datos seleccionados del gráfico, proporcionando detalles sobre lo que se seleccionó. El evento se puede escuchar utilizando el método `addSelectedListener()` en la instancia de `GoogleChart`.

El `GoogleChartSelectedEvent` es útil en aplicaciones donde la interacción del usuario con el gráfico es necesaria. 

```java
GoogleChart chart = new GoogleChart(GoogleChart.Type.BAR);

// Agregar el oyente de selección al gráfico
chart.addSelectedListener(event -> {
    // Obtener la selección
    List<Object> selection = chart.getSelection();
    
    // Manejar el evento seleccionado
    if (!selection.isEmpty()) {
        System.out.println("Fila Seleccionada: " + selection.get(0));
        // Procesamiento adicional basado en la fila/columna de la selección
    }
});
```

### Payload {#payload}
El `GoogleChartSelectedEvent` proporciona acceso a los datos de selección, que se pueden recuperar utilizando el método `getSelection()` en el objeto gráfico. Este método devuelve una lista de objetos, donde cada objeto contiene las siguientes propiedades:

- **row**: El índice de la fila en la tabla de datos del gráfico que fue seleccionada.
- **column**: El índice de la columna en la tabla de datos, que es opcional y se aplica a gráficos que permiten la selección de celdas individuales, como un gráfico de tabla.
  
Para gráficos como gráficos de torta o de barras, generalmente solo se proporciona `row`, que indica el punto de datos seleccionado.

Aquí tienes un ejemplo de payload:
```java
[
  {
    "row": 3,  // El índice de la fila seleccionada en los datos
    "column": 2  // (Opcional) El índice de la columna seleccionada
  }
]
```

:::info Seleccionando Múltiples Puntos de Datos
Si el usuario selecciona múltiples puntos de datos, el método `getSelection()` devolverá un arreglo de objetos, cada uno representando un elemento seleccionado. El payload puede variar según el tipo de gráfico y la interacción que realice el usuario.
:::
