---
title: Toolbar
sidebar_position: 145
_i18n_hash: e3329a54fd8feccd96e15e1b19c3c97d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Las barras de herramientas ofrecen a los usuarios un acceso rápido a acciones fundamentales y elementos de navegación. El componente webforJ `Toolbar` es un contenedor horizontal que puede contener un conjunto de botones de acción, íconos u otros componentes. Es ideal para gestionar controles de página y albergar funciones clave como una barra de búsqueda o un botón de notificación.

<!-- INTRO_END -->

## Organizando el contenido de la barra de herramientas {#organizing-toolbar-content}

La `Toolbar` organiza componentes esenciales en un diseño fácilmente accesible y consistente. Por defecto, ocupa el ancho completo de su elemento padre y proporciona cuatro áreas de colocación, o _slots_, para organizar componentes:

- **Inicio**: Por lo general, contiene un <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> o un botón de inicio.
- **Título**: Usado para nombres de aplicaciones o logotipos.
- **Contenido**: Para acciones de alta atención como búsqueda o navegación.
- **Fin**: Acciones menos frecuentes, como perfil de usuario o ayuda.

Cada slot tiene un método para agregar componentes: `addToStart()`, `addToTitle()`, `addToContent()`, y `addToEnd()`.

La siguiente demostración muestra cómo agregar una `Toolbar` a un [AppLayout](./app-layout) y utilizar todos los slots soportados de manera efectiva.
Para leer más sobre cómo implementar barras de herramientas dentro de un `AppLayout`, consulta [Barras de herramientas adhesivas](./app-layout#sticky-toolbars) y [Diseño de navegación móvil](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Modo compacto {#compact-mode}

Utiliza `setCompact(true)` para reducir el padding alrededor de una `Toolbar`. Esto es útil cuando necesitas ajustar más contenido en pantalla, especialmente en aplicaciones con barras de herramientas apiladas o espacio limitado. La barra de herramientas sigue comportándose de la misma manera; solo se reduce la altura. Este modo se utiliza comúnmente en encabezados, barras laterales o diseños donde el espacio es reducido.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` en las barras de herramientas {#progressbar-in-toolbars}

Un `ProgressBar` sirve como un indicador visual para procesos en curso, como cargar datos, subir archivos o completar pasos en un flujo. Cuando se coloca dentro de una `Toolbar`, el `ProgressBar` se alinea de manera ordenada a lo largo del borde inferior, haciéndolo discreto mientras comunica claramente el progreso a los usuarios.

Puedes combinarlo con otros componentes en la barra de herramientas, como botones o etiquetas, sin interrumpir el diseño.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Estilo {#styling}

### Temas {#themes}

Los componentes `Toolbar` incluyen <JavadocLink type="foundation" location="com/webforj/component/Theme">siete temas integrados</JavadocLink> para una rápida personalización visual:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
