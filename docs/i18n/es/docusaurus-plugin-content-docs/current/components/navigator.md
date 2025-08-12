---
title: Navigator
sidebar_position: 75
_i18n_hash: 920c1d604673e69a32f58161e3fd4e14
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

El componente `Navigator` es un componente de paginación personalizable diseñado para navegar a través de conjuntos de datos, soportando múltiples diseños. Puedes configurarlo para mostrar varios controles de navegación, como botones de inicio, fin, siguiente y anterior, junto con números de página o un campo de salto rápido según la configuración del diseño.

Soporta la desactivación automática de botones de navegación en función de la página actual y el total de elementos, y ofrece opciones de personalización para texto y tooltips para diferentes partes del navegador. Además, puedes vincularlo a una instancia de `Paginator` para gestionar la lógica de paginación del conjunto de datos y reflejar los cambios en los controles de navegación.

## Vinculación a repositorios {#binding-to-repositories}

A menudo, un componente `Navigator` muestra información encontrada en un `Repository` vinculado. Esta vinculación permite que el `Navigator` pagine automáticamente los datos gestionados por el repositorio y actualice otros componentes vinculados, como las tablas, en función de los datos navegados.

Para hacerlo, simplemente pasa el objeto `Repository` deseado al constructor de un objeto `Navigator` aplicable:

<ComponentDemo 
path='/webforj/navigatortable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java'
height='475px'
/>

Este ejemplo crea el `Navigator` y la [`Table`](table/overview) con la misma instancia de `Repository`. Esto significa que cuando navegas a una nueva página con el `Navigator`, la [`Table`](table/overview) reconoce este cambio y se vuelve a renderizar.

## Paginación {#pagination}

El componente `Navigator` está estrechamente relacionado con la clase modelo `Paginator`, calcula metadatos de paginación, como el número total de páginas, índices de inicio y fin de los elementos en la página actual, y una matriz de números de página para navegación.

Si bien no es absolutamente necesario, utilizar el `Paginator` permite la lógica detrás de la navegación. Al integrarse con un `Paginator`, el navegador responde a cualquier cambio dentro del `Paginator`. Los objetos `Navigator` tienen acceso a un `Paginator` incorporado mediante el uso del método `getPaginator()`. También puede aceptar una instancia de `Paginator` a través del método `setPaginator()`, o la utilización de uno de los constructores aplicables.

Esta sección incluye fragmentos de código prácticos para ilustrar cómo funciona esta integración en la práctica.

### Elementos {#items}

El término "elementos" denota los elementos individuales paginados o entradas de datos. Estos podrían ser registros, entradas o cualquier unidad discreta dentro de un conjunto de datos. Puedes establecer el número total de elementos utilizando el método `setTotalItems()`.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Un repositorio asociado con la instancia de `Paginator` tiene el número total de elementos directamente gestionados por el repositorio y no puede ser establecido directamente.
:::

### Páginas máximas {#maximum-pages}

El método `setMax()` te permite definir el número máximo de enlaces de página a mostrar en la navegación de paginación. Esto es particularmente útil al tratar con un gran número de páginas, ya que controla el número de enlaces de página visibles para el usuario en cualquier momento dado.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo 
path='/webforj/navigatorpages?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java'
height='125px'
/>

Este programa muestra un máximo de cinco páginas en el `Navigator` a la vez usando el método `getPaginator()` para recuperar el `Paginator` asociado con el objeto `Navigator`, y luego usando el método `setMax()` para especificar un número deseado de páginas máximas mostradas.

### Tamaño de página {#page-size}

El método `setSize()` te permite especificar el número de elementos a mostrar en cada página de la paginación. Cuando llamas a este método y proporcionas un nuevo tamaño de página, ajusta la paginación en consecuencia.

```java
navigator.getPaginator().setSize(pageSize);
```

## Personalizando botones, texto y tooltips {#customizing-buttons-text-and-tooltips}

El componente `Navigator` proporciona amplias opciones de personalización para botones, texto y tooltips. Para cambiar el texto mostrado en el componente `Navigator`, usa el método `setText()`. Este método toma texto, así como la `Part` deseada del `Navigator`.

En el siguiente ejemplo, el método `setText()` muestra un valor numérico al usuario. Al hacer clic en los botones se activa el método `onChange` del `Navigator`, que viene con un valor `Direction` del botón que se hizo clic.

<ComponentDemo 
path='/webforj/navigatorbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java'
height='100px'
/>

### Botones y texto del componente {#buttons-and-component-text}

El método `setText()` evalúa el parámetro de texto como una expresión de JavaScript utilizando los siguientes parámetros:

- `page` - el número de página actual
- `current` - el número de página actualmente seleccionada
- `x` - un alias para la página actual
- `startIndex` - El índice de inicio de la página actual.
- `endIndex` - El índice final de la página actual.
- `totalItems` - El número total de elementos.
- `startPage` - El número de página de inicio.
- `endPage` - El número de página final.
- `component` - El componente cliente del Navigator.

<!-- vale off -->
Por ejemplo, para establecer el texto del botón de la última página en un `Navigator` con 10 páginas a "Ir a la página 10", usa el siguiente fragmento de código: 
<!-- vale on -->

```java
navigator.setText("'Ir a la página ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Texto del tooltip {#tooltip-text}

Puedes personalizar tooltips para varias partes del componente `Navigator` utilizando el método `setTooltipText()`. Los tooltips proporcionan pistas útiles a los usuarios cuando pasan el cursor sobre los elementos de navegación.

:::info
El texto del tooltip no se evalúa como Javascript, a diferencia del texto utilizado por el método `setText()`
:::

<!-- vale off -->
Por ejemplo, para establecer el texto del tooltip del botón de la última página en un `Navigator` a "Ir a la última página", utiliza el siguiente fragmento de código:
<!-- vale on -->

```java
navigator.setTooltipText("Ir a la última página", Navigator.Part.LAST_BUTTON);
```

## Diseños {#layouts}

Existen varias opciones de diseño para el componente `Navigator` que ofrecen flexibilidad en la presentación de controles de paginación. Para acceder a estos diseños, utiliza los valores del enum `Navigator.Layout`. Las opciones son las siguientes:

<ComponentDemo 
path='/webforj/navigatorlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java'
height='200px'
/>

### 1. Diseño ninguno {#1-none-layout}

El diseño `NONE` no renderiza texto dentro del `Navigator`, mostrando solo los botones de navegación sin una visualización textual por defecto. Para activar este diseño, usa:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Diseño numerado {#2-numbered-layout}

El diseño numerado muestra chips numerados correspondientes a cada página dentro del área de visualización del `Navigator`. Usar este diseño es ideal para escenarios donde los usuarios prefieren la navegación directa a páginas específicas. Para activar este diseño, usa:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Diseño de vista previa {#3-preview-layout}

El diseño de vista previa muestra el número de página actual y el total de páginas, y es adecuado para interfaces de paginación compactas con espacio limitado.

:::info
La vista previa es el diseño `Navigator` por defecto.
:::

Para activar este diseño, usa:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Diseño de salto rápido {#4-quick-jump-layout}

El diseño de salto rápido proporciona un [NumberField](./fields/number-field.md) para que los usuarios ingresen un número de página para una navegación rápida. Esto es útil cuando los usuarios necesitan navegar rápidamente a una página específica, especialmente para conjuntos de datos grandes. Para activar este diseño, usa:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Estilo {#styling}

<TableBuilder name="Navigator" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima para el usuario al utilizar el componente `Navigator`, considera las siguientes mejores prácticas:

- **Entender el conjunto de datos**: Antes de integrar un componente `Navigator` en tu aplicación, comprende a fondo los requisitos de navegación de datos de tus usuarios. Considera factores como el tamaño del conjunto de datos, las interacciones típicas de los usuarios y los patrones de navegación preferidos.

- **Elegir el diseño apropiado**: Selecciona un diseño para el componente `Navigator` que esté alineado con los objetivos de experiencia del usuario y el espacio disponible en pantalla.

- **Personalizar texto y tooltips**: Personaliza el texto y los tooltips del componente `Navigator` para que coincidan con el idioma y la terminología utilizada en tu aplicación. Proporciona etiquetas descriptivas y pistas útiles para ayudar a los usuarios a navegar el conjunto de datos de manera efectiva.
