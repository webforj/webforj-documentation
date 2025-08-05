---
title: Toolbar
sidebar_position: 145
_i18n_hash: 171c46f92903112a08194d130d89f2c7
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Las barras de herramientas ofrecen a los usuarios acceso rápido a acciones fundamentales y elementos de navegación. El componente webforJ `Toolbar` es un contenedor horizontal que puede contener un conjunto de botones de acción, íconos u otros componentes. Es ideal para gestionar controles de página y albergar funciones clave como una barra de búsqueda o un botón de notificación.

## Organizando el contenido de la barra de herramientas {#organizing-toolbar-content}

La `Toolbar` organiza componentes esenciales en un diseño accesible y consistente. Por defecto, ocupa todo el ancho de su elemento padre y proporciona cuatro áreas de colocación, o _slots_, para organizar componentes:

- **Inicio**: Generalmente contiene un <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> o un botón de inicio.
- **Título**: Utilizado para nombres de aplicaciones o logotipos.
- **Contenido**: Para acciones de alta atención como búsqueda o navegación.
- **Final**: Acciones menos frecuentes, como perfil de usuario o ayuda.

Cada slot tiene un método para añadir componentes: `addToStart()`, `addToTitle()`, `addToContent()`, y `addToEnd()`.

La siguiente demostración muestra cómo añadir una `Toolbar` a un [AppLayout](./app-layout) y utilizar eficazmente todos los slots soportados. Para leer más sobre cómo implementar barras de herramientas dentro de un `AppLayout`, consulte [Barras de herramientas fijas](./app-layout#sticky-toolbars) y [Diseño de navegación móvil](./app-layout#mobile-navigation-layout).

<AppLayoutViewer
path='/webforj/toolbarslots?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java'
height='300px'
/>

## Modo compacto {#compact-mode}

Use `setCompact(true)` para reducir el relleno alrededor de una `Toolbar`. Esto es útil cuando necesita ajustar más contenido en la pantalla, especialmente en aplicaciones con barras de herramientas apiladas o espacio limitado. La barra de herramientas sigue comportándose igual: solo se reduce la altura. Este modo se utiliza comúnmente en encabezados, barras laterales o diseños donde el espacio es limitado.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<AppLayoutViewer path='/webforj/toolbarcompact?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java'
/>

## `ProgressBar` en barras de herramientas {#progressbar-in-toolbars}

Un `ProgressBar` sirve como un indicador visual para procesos en curso, como cargar datos, subir archivos o completar pasos en un flujo. Cuando se coloca dentro de una `Toolbar`, el `ProgressBar` se alinea perfectamente a lo largo del borde inferior, haciéndolo poco intrusivo mientras comunica claramente el progreso a los usuarios.

Puede combinarlo con otros componentes en la barra de herramientas, como botones o etiquetas, sin interrumpir el diseño.

<AppLayoutViewer path='/webforj/toolbarprogressbar?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java'
/>

## Estilo {#styling}

### Temas {#themes}

Los componentes de `Toolbar` incluyen <JavadocLink type="foundation" location="com/webforj/component/Theme">siete temas integrados</JavadocLink> para una rápida personalización visual:

<ComponentDemo 
path='/webforj/toolbartheme?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java' 
height = '475px'
/>

<TableBuilder name="Toolbar" />
