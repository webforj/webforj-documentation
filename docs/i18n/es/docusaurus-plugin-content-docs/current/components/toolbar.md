---
title: Toolbar
sidebar_position: 145
description: >-
  Lay out action controls with the Toolbar component, placing components into
  Start, Title, Content, and End slots with compact mode.
_i18n_hash: 8dcb4d5bcecce36e656de87218bd3359
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toolbar" />
<DocChip chip='since' label='24.12' />
<JavadocLink type="toolbar" location="com/webforj/component/layout/toolbar/Toolbar" top='true'/>

Las barras de herramientas ofrecen a los usuarios acceso rápido a acciones básicas y elementos de navegación. El componente webforJ `Toolbar` es un contenedor horizontal que puede albergar un conjunto de botones de acción, íconos u otros componentes. Es ideal para gestionar controles de página y contener funciones clave como una barra de búsqueda o un botón de notificación.

<!-- INTRO_END -->

## Organizándose el contenido de la barra de herramientas {#organizing-toolbar-content}

La `Toolbar` organiza componentes esenciales en un diseño fácilmente accesible y consistente. Por defecto, ocupa todo el ancho de su elemento padre y proporciona cuatro áreas de colocación, o _slots_, para organizar componentes:

- **Inicio**: Generalmente contiene un <JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppDrawerToggle" code='true'>AppDrawerToggle</JavadocLink> o un botón de inicio.
- **Título**: Utilizado para nombres o logotipos de aplicaciones.
- **Contenido**: Para acciones de alta atención como búsqueda o navegación.
- **Fin**: Acciones menos frecuentes, como perfil de usuario o ayuda.

Cada slot tiene un método para añadir componentes: `addToStart()`, `addToTitle()`, `addToContent()`, y `addToEnd()`.

La siguiente demostración muestra cómo agregar una `Toolbar` a un [AppLayout](./app-layout) y utilizar todos los slots compatibles de manera efectiva. Para leer más sobre la implementación de barras de herramientas dentro de un `AppLayout`, consulta [Barras de herramientas fijas](./app-layout#sticky-toolbars) y [Diseño de navegación móvil](./app-layout#mobile-navigation-layout).

<ComponentDemo
path='/webforj/toolbarslots'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/toolbar/ToolbarSlotsView.java',
  'src/main/frontend/css/toolbar/toolbar-slots-view.css',
]}
/>

## Modo compacto {#compact-mode}

Utiliza `setCompact(true)` para reducir el relleno alrededor de una `Toolbar`. Esto es útil cuando necesitas ajustar más contenido en la pantalla, especialmente en aplicaciones con barras de herramientas apiladas o espacio limitado. La barra de herramientas sigue comportándose de la misma manera; solo se reduce la altura. Este modo se usa comúnmente en encabezados, barras laterales o diseños donde el espacio es estrecho.

```java
Toolbar toolbar = new Toolbar();
toolbar.setCompact(true);
```

<ComponentDemo
path='/webforj/toolbarcompact'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarCompactView.java']}
/>

## `ProgressBar` en barras de herramientas {#progressbar-in-toolbars}

Un `ProgressBar` sirve como un indicador visual para procesos en curso, tales como cargar datos, subir archivos o completar pasos en un flujo. Cuando se coloca dentro de una `Toolbar`, el `ProgressBar` se alinea perfectamente a lo largo del borde inferior, lo que lo hace poco intrusivo mientras comunica claramente el progreso a los usuarios.

Puedes combinarlo con otros componentes en la barra de herramientas, como botones o etiquetas, sin interrumpir el diseño.

<ComponentDemo
path='/webforj/toolbarprogressbar'
frame='desktop'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarProgressbarView.java']}
/>

## Estilo {#styling}

### Temas {#themes}

Los componentes `Toolbar` incluyen <JavadocLink type="foundation" location="com/webforj/component/Theme">siete temas incorporados</JavadocLink> para una personalización visual rápida:

<ComponentDemo
path='/webforj/toolbartheme'
files={['src/main/java/com/webforj/samples/views/toolbar/ToolbarThemeView.java']}
height='590px'
/>

<TableBuilder name="Toolbar" />
