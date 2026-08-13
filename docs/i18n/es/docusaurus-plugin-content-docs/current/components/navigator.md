---
title: Navigator
sidebar_position: 75
description: >-
  Add pagination controls with the Navigator component, binding to a Paginator
  or Repository to drive page size, navigation, and labels.
_i18n_hash: 1223e167b76000411cd73c4bbbbda3d5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-navigator" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="foundation" location="com/webforj/component/navigator/Navigator" top='true'/>

El componente `Navigator` agrega controles de paginación para navegar a través de conjuntos de datos. Puede mostrar botones de primero, último, siguiente y anterior junto con números de página o un campo de salto rápido, y desactiva automáticamente los controles cuando no son aplicables. Se vincula a una instancia de `Paginator` para gestionar la lógica de paginación subyacente.

<!-- INTRO_END -->

## Vinculando a repositorios {#binding-to-repositories}

A menudo, un componente `Navigator` muestra información encontrada en un `Repository` vinculado. Esta vinculación permite que el `Navigator` pagine automáticamente los datos gestionados por el repositorio y actualice otros componentes vinculables, como tablas, en función de los datos navegado.

Para hacerlo, simplemente pasa el objeto `Repository` deseado al constructor de un objeto `Navigator` aplicable:

<ComponentDemo
path='/webforj/navigatortable'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorTableView.java']}
height='475px'
/>

Este ejemplo crea el `Navigator` y [`Table`](table/overview) con la misma instancia de `Repository`. Esto significa que cuando se navega a una nueva página con el `Navigator`, la [`Table`](table/overview) reconoce este cambio y se vuelve a renderizar.

## Paginación {#pagination}

El componente `Navigator` está estrechamente vinculado con la clase de modelo `Paginator`, calcula metadatos de paginación como el número total de páginas, índices de inicio/fin de los elementos en la página actual y una matriz de números de página para la navegación.

Si bien no es absolutamente necesario, utilizar el `Paginator` permite la lógica detrás de la navegación. Al integrarse con un `Paginator`, el `navigator` responde a cualquier cambio dentro del `Paginator`. Los objetos `Navigator` tienen acceso a un `Paginator` incorporado mediante el uso del método `getPaginator()`. También puede aceptar una instancia de `Paginator` a través del método `setPaginator()`, o mediante la utilización de uno de los constructores aplicables.

Esta sección incluye fragmentos de código prácticos para ilustrar cómo funciona esta integración en la práctica.

### Elementos {#items}

El término "elementos" denota los elementos individuales paginados o entradas de datos. Estos podrían ser registros, entradas o cualquier unidad discreta dentro de un conjunto de datos. Puedes establecer el número total de elementos usando el método `setTotalItems()`.

```java
navigator.getPaginator().setTotalItems(totalItems);
```

:::info
Un repositorio asociado con la instancia de `Paginator` tiene el número total de elementos gestionados directamente por el repositorio y no se puede establecer directamente.
:::

### Páginas máximas {#maximum-pages}

El método `setMax()` te permite definir el número máximo de enlaces de página que se mostrarán en la navegación de paginación. Esto es particularmente útil cuando se trata de un gran número de páginas, ya que controla el número de enlaces de página visibles para el usuario en cualquier momento.

```java
navigator.getPaginator().setMax(maxPages);
```

<ComponentDemo
path='/webforj/navigatorpages'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorPagesView.java']}
height='125px'
/>

Este programa muestra un máximo de cinco páginas en el `Navigator` al mismo tiempo utilizando el método `getPaginator()` para recuperar el `Paginator` asociado con el objeto `Navigator`, y luego utilizando el método `setMax()` para especificar un número deseado de páginas máximas mostradas.

### Tamaño de página {#page-size}

El método `setSize()` te permite especificar el número de elementos que se mostrarán en cada página de la paginación. Cuando llamas a este método y proporcionas un nuevo tamaño de página, ajusta la paginación en consecuencia.

```java
navigator.getPaginator().setSize(pageSize);
```

## Personalizando botones, texto y tooltips {#customizing-buttons-text-and-tooltips}

El componente `Navigator` proporciona amplias opciones de personalización para botones, texto y tooltips. Para cambiar el texto mostrado en el componente `Navigator`, utiliza el método `setText()`. Este método toma texto, así como la `Part` deseada del `Navigator`.

En el siguiente ejemplo, el método `setText()` muestra un valor numérico al usuario. Al hacer clic en los botones se activa el método `onChange` del `Navigator`, que viene con un valor de `Direction` del botón clicado.

<ComponentDemo
path='/webforj/navigatorbasic'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorBasicView.java']}
height='100px'
/>

### Botones y texto del componente {#buttons-and-component-text}

El método `setText()` evalúa el parámetro de texto como una expresión de JavaScript utilizando los siguientes parámetros:

- `page` - el número de la página actual
- `current` - el número de página actualmente seleccionado
- `x` - un alias para la página actual
- `startIndex` - El índice de inicio de la página actual.
- `endIndex` - El índice de fin de la página actual.
- `totalItems` - El número total de elementos.
- `startPage` - El número de la página de inicio.
- `endPage` - El número de la última página.
- `component` - El componente cliente de Navigator.

<!-- vale off -->
Por ejemplo, para establecer el texto del botón de la última página en un `Navigator` con 10 páginas a "Ir a la página 10", utiliza el siguiente fragmento de código:
<!-- vale on -->

```java
navigator.setText("'Ir a la página ' + endPage", Navigator.Part.LAST_BUTTON);
```

### Texto del tooltip {#tooltip-text}

Puedes personalizar tooltips para varias partes del componente `Navigator` utilizando el método `setTooltipText()`. Los tooltips proporcionan consejos útiles a los usuarios cuando pasan el mouse sobre los elementos de navegación.

:::info
El texto del tooltip no se evalúa en JavaScript, a diferencia del texto utilizado por el método `setText()`
:::

<!-- vale off -->
Por ejemplo, para establecer el texto del tooltip del botón de la última página en un `Navigator` a "Ir a la última página", utiliza el siguiente fragmento de código:
<!-- vale on -->

```java
navigator.setTooltipText("Ir a la última página", Navigator.Part.LAST_BUTTON);
```

## Diseños {#layouts}

Existen varias opciones de diseño para el componente `Navigator` para proporcionar flexibilidad en la visualización de los controles de paginación. Para acceder a estos diseños, utiliza los valores del enum `Navigator.Layout`. Las opciones son las siguientes:

<ComponentDemo
path='/webforj/navigatorlayout'
files={['src/main/java/com/webforj/samples/views/navigator/NavigatorLayoutView.java']}
height='200px'
/>

### 1. Diseño sin {#1-none-layout}

El diseño `NONE` no renderiza texto dentro del `Navigator`, mostrando solo los botones de navegación sin una visualización de texto predeterminada. Para activar este diseño, utiliza:

```java
navigator.setLayout(Navigator.Layout.NONE);
```

### 2. Diseño numerado {#2-numbered-layout}

El diseño numerado muestra chips numerados correspondientes a cada página dentro del área de visualización del `Navigator`. Utilizar este diseño es ideal para escenarios donde los usuarios prefieren la navegación directa a páginas específicas. Para activar este diseño, utiliza:

```java
navigator.setLayout(Navigator.Layout.PAGES);
```

### 3. Diseño de vista previa {#3-preview-layout}

El diseño de vista previa muestra el número de la página actual y el número total de páginas, y es adecuado para interfaces de paginación compactas con espacio limitado.

:::info
La vista previa es el diseño predeterminado del `Navigator`.
:::

Para activar este diseño, utiliza:

```java
navigator.setLayout(Navigator.Layout.PREVIEW);
```

### 4. Diseño de salto rápido {#4-quick-jump-layout}

El diseño de salto rápido proporciona un [NumberField](./fields/number-field.md) para que los usuarios ingresen un número de página para la navegación rápida. Esto es útil cuando los usuarios necesitan navegar a una página específica rápidamente, especialmente para grandes conjuntos de datos. Para activar este diseño, utiliza:

```java
navigator.setLayout(Navigator.Layout.QUICK_JUMP);
```

## Estilo {#styling}

<TableBuilder name="Navigator" />

## Mejores prácticas {#best-practices}

Para asegurar una experiencia óptima del usuario al utilizar el componente `Navigator`, considera las siguientes mejores prácticas:

- **Entender el conjunto de datos**: Antes de integrar un componente `Navigator` en tu aplicación, entiende a fondo los requisitos de navegación de datos de tus usuarios. Considera factores como el tamaño del conjunto de datos, las interacciones típicas de los usuarios y los patrones de navegación preferidos.

- **Elegir un diseño apropiado**: Selecciona un diseño para el componente `Navigator` que esté alineado con los objetivos de experiencia del usuario y el espacio en la pantalla disponible.

- **Personalizar texto y tooltips**: Personaliza el texto y los tooltips del componente `Navigator` para coincidir con el lenguaje y la terminología utilizados en tu aplicación. Proporciona etiquetas descriptivas y sugerencias útiles para ayudar a los usuarios a navegar eficazmente por el conjunto de datos.
