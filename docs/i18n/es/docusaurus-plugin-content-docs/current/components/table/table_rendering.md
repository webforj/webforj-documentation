---
sidebar_position: 20
title: Rendering
slug: rendering
_i18n_hash: 12791f1583592c6f000a45b74e52ba65
---
# Contenido rico y renderizado del lado del cliente

Las tablas en webforJ también son configurables utilizando las siguientes herramientas para mostrar contenido rico dentro de las celdas. Esto incluye componentes interactivos o datos formateados dentro de las celdas de la tabla.

Estos elementos se renderizan del lado del cliente, lo que significa que el proceso de generación y visualización de contenido rico se realiza directamente en el navegador, utilizando JavaScript solo cuando es necesario, aumentando el rendimiento de las aplicaciones que utilizan el `Table`.

## Renderizadores de Lodash {#lodash-renderers}

Los renderizadores ofrecen un mecanismo poderoso para personalizar la forma en que se muestra la información dentro de un `Table`. La clase principal, `Renderer`, está diseñada para ser extendida para crear renderizadores personalizados basados en plantillas de lodash, lo que permite el renderizado de contenido dinámico e interactivo.

Las plantillas de Lodash permiten la inserción de HTML directamente en las celdas de la tabla, lo que las hace altamente efectivas para renderizar datos complejos en una celda de un `Table`. Este enfoque permite la generación dinámica de HTML basada en los datos de las celdas, facilitando así contenido rico e interactivo en las celdas de la tabla.

### Sintaxis de Lodash {#lodash-syntax}

La siguiente sección describe lo básico de la sintaxis de Lodash. Si bien no es una visión exhaustiva o completa, se puede utilizar para ayudar a comenzar a usar Lodash dentro del componente `Table`.

#### Descripción general de la sintaxis para plantillas de lodash: {#syntax-overview-for-lodash-templates}

- `<%= ... %>` - Interpola valores, insertando el resultado del código JavaScript en la plantilla.
- `<% ... %>` - Ejecuta código JavaScript, permitiendo bucles, condicionales y más.
- `<%- ... %>` - Escapa el contenido HTML, asegurando que los datos interpolados estén a salvo de ataques de inyección HTML.

#### Ejemplos utilizando datos de celdas: {#examples-using-cell-data}

**1. Interpolación de valor simple**: Mostrar directamente el valor de la celda.

`<%= cell.value %>`

**2. Renderizado condicional**: Usar lógica de JavaScript para renderizar contenido condicionalmente.

`<% if (cell.value > 100) { %> 'Alto' <% } else { %> 'Normal' <% } %>`

**3. Combinando campos de datos**: Renderizar contenido utilizando múltiples campos de datos de la celda.

`<%= cell.row.getValue('firstName') + ' ' + cell.row.getValue('lastName') %>`

**4. Escapando contenido HTML**: Renderizar de forma segura contenido generado por el usuario.

El renderizador tiene acceso a propiedades detalladas de celdas, filas y columnas en el lado del cliente:

**Propiedades de TableCell:**

|Propiedad	|Tipo	|Descripción|
|-|-|-|
|column|`TableColumn`|El objeto de columna asociado.|
|first|`boolean`|Indica si la celda es la primera en la fila.|
|id|`String`|La ID de la celda.|
|index|`int`|El índice de la celda dentro de su fila.|
|last|`boolean`|Indica si la celda es la última en la fila.|
|row|`TableRow`|El objeto de fila asociado con la celda.|
|value|`Object`|El valor bruto de la celda, directamente de la fuente de datos.|

**Propiedades de TableRow:**

|Propiedad|Tipo|Descripción|
|-|-|-|
|cells|`TableCell[]`|Las celdas dentro de la fila.|
|data|`Object`|Los datos proporcionados por la aplicación para la fila.|
|even|`boolean`|Indica si la fila es de número par (para propósitos de estilo).|
|first|`boolean`|Indica si la fila es la primera en la tabla.|
|id|`String`|ID único para la fila.|
|index|`int`|El índice de la fila.|
|last|`boolean`|Indica si la fila es la última en la tabla.|
|odd|`boolean`|Indica si la fila es de número impar (para propósitos de estilo).|

**Propiedades de TableColumn:**

|Propiedad	|Tipo	|Descripción|
|-|-|-|
|align|ColumnAlignment|La alineación de la columna (izquierda, centro, derecha).|
|id|String|El campo del objeto de fila del que obtener los datos de la celda.|
|label|String|El nombre a renderizar en el encabezado de la columna.|
|pinned|ColumnPinDirection|La dirección en la que se fija la columna (izquierda, derecha, automática).|
|sortable|boolean|Si es verdadero, la columna puede ser ordenada.|
|sort|SortDirection|El orden de clasificación de la columna.|
|type|ColumnType|El tipo de la columna (texto, número, booleano, etc.).|
|minWidth|number|El ancho mínimo de la columna en píxeles.|

## Renderizadores disponibles {#available-renderers}

Si bien se pueden crear renderizadores personalizados, hay múltiples renderizadores preconfigurados disponibles para su uso dentro de un `Table`. Los siguientes están disponibles para que los desarrolladores los utilicen directamente sin la necesidad de crear un renderizador personalizado:

>- `ButtonRenderer` - Renderer para un botón de webforJ.
>- `NativeButtonRenderer` - Renderer para un botón HTML nativo.
>- `ElementRenderer` - La clase base para todos los renderizadores que renderizan una etiqueta HTML **con** contenido.
>- `VoidElementRenderer` - La clase base para todos los renderizadores que renderizan un elemento vacío, o una etiqueta HTML **sin** contenido.
>- `IconRenderer` - Renderer para un ícono - **[ver este](../../components/icon)** artículo para más información sobre íconos.

Los renderizadores también permiten que se escriban eventos personalizados extendiendo cualquiera de los renderizadores base soportados. Actualmente, los renderizadores vienen con un `RendererClickEvent` disponible para su uso por los desarrolladores.

A continuación se muestra un ejemplo de un `Table` que utiliza renderizadores para mostrar contenido rico:

<ComponentDemo 
path='/webforj/tablerichcontent?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableRichContentView.java'
cssURL='/css/table/tableRichContent.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
