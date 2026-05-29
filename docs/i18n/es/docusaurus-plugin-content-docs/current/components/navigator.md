---
title: Navigator
sidebar_position: 75
_i18n_hash: db351d8f9fdf344a571d374e8d373f22
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

El componente `Navigator` añade controles de paginación para navegar a través de conjuntos de datos. Puede mostrar botones de primero, último, siguiente y anterior junto con números de página o un campo de salto rápido, y deshabilita automáticamente los controles cuando no son aplicables. Se vincula a una instancia de `Paginator` para gestionar la lógica de paginación subyacente.

<!-- INTRO_END -->

## Vinculación a repositorios {#binding-to-repositories}

A menudo, un componente `Navigator` muestra información encontrada en un `Repository` vinculado. Esta vinculación permite que el `Navigator` pagine automáticamente los datos gestionados por el repositorio y actualice otros componentes vinculables, como tablas, en función de los datos navegados.

Para hacerlo, simplemente pasa el objeto `Repository` deseado al constructor del objeto `Navigator` aplicable:

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Este ejemplo crea el `Navigator` y el [`Table`](table/overview) con la misma instancia de `Repository`. Esto significa que al navegar a una nueva página con el `Navigator`, el [`Table`](table/overview) reconoce este cambio y se vuelve a renderizar.

## Paginación {#pagination}

El componente `Navigator` está estrechamente vinculado con la clase de modelo `Paginator`, calcula metadatos de paginación como el número total de páginas, índices de inicio/fin de los elementos en la página actual y una matriz de números de página para la navegación.

Si bien no es estrictamente necesario, utilizar el `Paginator` habilita la lógica detrás de la navegación. Al integrarse con un `Paginator`, el `navigator` responde a cualquier cambio dentro del `Paginator`. Los objetos `Navigator` tienen acceso a un `Paginator` incorporado a través del método `getPaginator()`. También puede aceptar una instancia de `Paginator` a través del método `setPaginator()`, o utilizando uno de los constructores aplicables.

Esta sección incluye fragmentos de código prácticos para ilustrar cómo funciona esta integración en la práctica.

### Elementos {#items}

El término "elementos" denota los elementos individuales paginados o entradas de datos. Estos pueden ser registros, entradas o cualquier unidad discreta dentro de un conjunto de datos. Puede establecer el número total de elementos usando el método `setTotalItems()`.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Un repositorio asociado con la instancia de `Paginator` tiene el número total de elementos gestionados directamente por el repositorio y no puede ser establecido directamente.
:::

### Páginas máximas {#maximum-pages}

El método `setMax()` te permite definir el número máximo de enlaces de página a mostrar en la navegación de paginación. Esto es particularmente útil cuando se trata de un gran número de páginas, ya que controla el número de enlaces visibles para el usuario en cualquier momento.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Este programa muestra un máximo de cinco páginas en el `Navigator` al mismo tiempo utilizando el método `getPaginator()` para recuperar el `Paginator` asociado con el objeto `Navigator`, y luego utilizando el método `setMax()` para especificar un número deseado de páginas máximas a mostrar.

### Tamaño de página {#page-size}

El método `setSize()` te permite especificar el número de elementos a mostrar en cada página de la paginación. Cuando llamas a este método y proporcionas un nuevo tamaño de página, ajusta la paginación en consecuencia.

```java
navigator.getPaginator().setSize(pageSize);
```

## Personalizando botones, texto y descripciones emergentes {#customizing-buttons-text-and-tooltips}

El componente `Navigator` proporciona amplias opciones de personalización para botones, texto y descripciones emergentes. Para cambiar el texto mostrado en el componente `Navigator`, utiliza el método `setText()`. Este método toma texto, así como la `Part` deseada del `Navigator`.

En el siguiente ejemplo, el método `setText()` muestra un valor numérico al usuario. Al hacer clic en los botones se activa el método `onChange` del `Navigator`, que viene con un valor de `Direction` del botón que fue clicado.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Botones y texto del componente {#buttons-and-component-text}

El método `setText()` evalúa el parámetro de texto como una expresión de JavaScript utilizando los siguientes parámetros:

- `page` - el número de la página actual
- `current` - el número de la página actualmente seleccionada
- `x` - un alias para la página actual
- `startIndex` - El índice de inicio de la página actual.
- `endIndex` - El índice de fin de la página actual.
- `totalItems` - El número total de elementos.
- `startPage` - El número de la página de inicio.
- `endPage` - El número de la última página.
- `component` - El componente cliente del Navigator.

<!-- vale off -->
Por ejemplo, para establecer el texto del botón de la última página en un `Navigator` con 10 páginas a "Ir a la página 10", utiliza el siguiente fragmento de código: 
<!-- vale on -->

```java
navigator.setText("'Ir a la página ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Texto de descripción emergente {#tooltip-text}

Puedes personalizar descripciones emergentes para varias partes del componente `Navigator` utilizando el método `setTooltipText()`. Las descripciones emergentes proporcionan sugerencias útiles a los usuarios cuando pasan el mouse sobre los elementos de navegación.

:::info
El texto de la descripción emergente no se evalúa como Javascript, a diferencia del texto utilizado por el método `setText()`.
:::

<!-- vale off -->
Por ejemplo, para establecer el texto de la descripción emergente del botón de la última página en un `Navigator` como "Ir a la última página", utiliza el siguiente fragmento de código:
<!-- vale on -->

```java
navigator.setTooltipText("Ir a la última página", Navigator.Part.LAST_BUTTON);
```

## Diseños {#layouts}

Existen varias opciones de diseño para el componente `Navigator` para proporcionar flexibilidad en la visualización de controles de paginación. Para acceder a estos diseños, utiliza los valores del enum `Navigator.Layout`. Las opciones son las siguientes:

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Diseño ninguno {#1-none-layout}

El diseño `NONE` no renderiza texto dentro del `Navigator`, mostrando solo los botones de navegación sin una visualización textual predeterminada. Para activar este diseño, utiliza:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Diseño numerado {#2-numbered-layout}

El diseño numerado muestra chips numerados correspondientes a cada página dentro del área de visualización del `Navigator`. Utilizar este diseño es ideal para escenarios donde los usuarios prefieren la navegación directa a páginas específicas. Para activar este diseño, utiliza:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Diseño de vista previa {#3-preview-layout}

El diseño de vista previa muestra el número de página actual y el número total de páginas, y es adecuado para interfaces de paginación compactas con espacio limitado.

:::info
La vista previa es el diseño `Navigator` predeterminado.
:::

Para activar este diseño, utiliza:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Diseño de salto rápido {#4-quick-jump-layout}

El diseño de salto rápido proporciona un [NumberField](./fields/number-field.md) para que los usuarios ingresen un número de página para una navegación rápida. Esto es útil cuando los usuarios necesitan navegar a una página específica rápidamente, especialmente para grandes conjuntos de datos. Para activar este diseño, utiliza:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Estilizando {#styling}

<TableBuilder name="Navigator" />

## Mejores prácticas {#best-practices}

Para garantizar una experiencia óptima del usuario al utilizar el componente `Navigator`, considera las siguientes mejores prácticas: 

- **Entender el conjunto de datos**: Antes de integrar un componente `Navigator` en tu aplicación, comprende a fondo los requisitos de navegación de datos de tus usuarios. Considera factores tales como el tamaño del conjunto de datos, interacciones típicas del usuario y patrones de navegación preferidos.

- **Elegir el diseño apropiado**: Selecciona un diseño para el componente `Navigator` que se alinee con los objetivos de experiencia del usuario y el espacio disponible en la pantalla.

- **Personalizar texto y descripciones emergentes**: Personaliza el texto y las descripciones emergentes del componente `Navigator` para que coincidan con el idioma y la terminología utilizada en tu aplicación. Proporciona etiquetas descriptivas y sugerencias útiles para ayudar a los usuarios a navegar efectivamente por el conjunto de datos.
