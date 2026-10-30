---
title: AppLayout
sidebar_position: 5
description: >-
  Build dashboards and admin shells with the AppLayout component, providing a
  fixed header, footer, sliding drawer, and scrollable content area.
_i18n_hash: 4e36be1fd55d6c17c810ace6ef3f1fd8
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

El componente `AppLayout` te proporciona una estructura de página lista para usar con un encabezado y pie de página fijos, un cajón que se desliza dentro y fuera, y un área de contenido desplazable. Juntas, estas secciones cubren las necesidades de diseño de paneles de control, paneles de administración y la mayoría de las interfaces de varias secciones.

<!-- INTRO_END -->

## Características {#features}

El diseño de aplicaciones webforJ es un componente que permite construir diseños de aplicaciones comunes.

<ul>
    <li>Fácil de usar y personalizar</li>
    <li>Diseño receptivo</li>
    <li>Múltiples opciones de diseño</li>
    <li>Funciona con el modo oscuro de webforJ</li>
</ul>

Proporciona un encabezado, pie de página, cajón y sección de contenido, todo integrado en un componente receptivo que se puede personalizar fácilmente para construir rápidamente diseños de aplicaciones comunes, como un panel de control. El encabezado y el pie de página son fijos, el cajón se desliza dentro y fuera del área de vista, y el contenido es desplazable.

Cada parte del diseño es un `Div`, que puede contener cualquier control válido de webforJ. Para obtener los mejores resultados, la aplicación debe incluir una etiqueta meta de viewport que contenga viewport-fit=cover. La etiqueta meta hace que el viewport se escale para llenar la pantalla del dispositivo.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Resumen {#overview}

El siguiente ejemplo de código dará como resultado una aplicación con una barra lateral colapsable que contiene un logotipo y pestañas para varias opciones de contenido y un encabezado. La demostración utiliza el componente web dwc-icon-button para crear un botón de alternar cajón. El botón tiene el atributo data-drawer-toggle que instruye a DwcAppLayout a escuchar eventos de clic provenientes de ese componente para alternar el estado del cajón.

<!--vale off-->
<ComponentDemo
path='/webforj/applayout/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Barra de navegación de ancho completo {#full-width-navbar}

Por defecto, el AppLayout renderiza el encabezado y el pie de página en modo fuera de pantalla. El modo fuera de pantalla significa que la posición del encabezado y del pie de página se desplaza para ajustarse al lado del cajón abierto. Desactivar este modo hará que el encabezado y el pie de página ocupen todo el espacio disponible y que la posición del cajón se ajuste por encima y por debajo para encajar con el encabezado y el pie de página.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutfullnavbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Múltiples barras de herramientas {#multiple-toolbars}

La barra de navegación no tiene límite en la cantidad de barras de herramientas que puedes agregar. Una `Toolbar` es un contenedor horizontal que alberga un conjunto de botones de acción, íconos u otros controles. Para agregar una barra de herramientas adicional, simplemente utiliza el método `addToHeader()` para agregar otro componente `Toolbar`.

La siguiente demostración muestra cómo usar dos barras de herramientas. La primera alberga el botón de alternar del cajón y el título de la aplicación. La segunda barra de herramientas alberga un menú de navegación secundario.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmultipleheaders/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Barras de herramientas fijas {#sticky-toolbars}

Una barra de herramientas fija es una barra de herramientas que permanece visible en la parte superior de la página cuando el usuario se desplaza hacia abajo, pero la altura de la barra de navegación se colapsa para liberar más espacio para el contenido de la página. Normalmente, este tipo de barra de herramientas contiene un menú de navegación fijo que es relevante para la página actual.

Es posible crear barras de herramientas fijas utilizando la propiedad CSS personalizada `--dwc-app-layout-header-collapse-height` y la opción `AppLayout.setHeaderReveal()`.

Cuando se llama a `AppLayout.setHeaderReveal(true)`, el encabezado será visible en el primer renderizado y luego se ocultará cuando el usuario comience a desplazarse hacia abajo. Una vez que el usuario comience a desplazarse hacia arriba nuevamente, el encabezado será revelado.

Con la ayuda de la propiedad CSS personalizada `--dwc-app-layout-header-collapse-height`, es posible controlar cuánto del encabezado de la barra de navegación estará oculto.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutstickytoolbar/content/Dashboard'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Diseño de navegación móvil {#mobile-navigation-layout}

La barra de navegación inferior se puede utilizar para proporcionar una versión diferente de la navegación en la parte inferior de la aplicación. Este tipo de navegación es especialmente popular en aplicaciones móviles.

Observe cómo el cajón está oculto en la siguiente demostración. El widget AppLayout admite tres posiciones de cajón: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` y `DrawerPlacement.HIDDEN`.

Al igual que `AppLayout.setHeaderReveal()`, se admite `AppLayout.setFooterReveal()`. Cuando se llama a `AppLayout.setFooterReveal(true)`, el pie de página será visible en el primer renderizado y luego se ocultará cuando el usuario comience a desplazarse hacia arriba. Una vez que el usuario comience a desplazarse hacia abajo nuevamente, el pie de página será revelado.

Por defecto, cuando el ancho de la pantalla es de 800px o menos, el cajón se cambiará al modo popover. Esto se llama el punto de interrupción. El modo popover significa que el cajón se superpondrá al área de contenido con una superposición. Es posible configurar el punto de interrupción utilizando el método `setDrawerBreakpoint()` y el punto de interrupción debe ser una consulta de medios válida [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutmobiledrawer/'
frame='mobile'
files={[
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->

## Utilidades del cajón {#drawer-utilities}

Las utilidades del cajón de `AppLayout` están diseñadas para navegación integrada y menús contextuales dentro del diseño principal de la aplicación, mientras que los componentes [`Drawer`](https://docs.webforj.com/docs/components/drawer) independientes ofrecen paneles deslizables flexibles que pueden usarse en cualquier lugar de tu aplicación para contenido adicional, filtros o notificaciones. Esta sección se centra en las características y utilidades del cajón integradas que proporciona AppLayout.

### Punto de interrupción del cajón {#drawer-breakpoint}

Por defecto, cuando el ancho de la pantalla es de 800px o menos, el cajón se cambiará al modo popover. Esto se llama el punto de interrupción. El modo popover significa que el cajón se superpondrá al área de contenido con una superposición. Es posible configurar el punto de interrupción utilizando el método `setDrawerBreakpoint()` y el punto de interrupción debe ser una consulta de medios válida.

Por ejemplo, en el siguiente ejemplo, el punto de interrupción del cajón se configura para ser de 500px o menos.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Título del cajón {#drawer-title}

El componente `AppLayout` proporciona un método `addToDrawerTitle()` para definir un título personalizado que se mostrará en el encabezado del cajón.

```java
layout.addToDrawerTitle(new Div("Menú"));
```

### Acciones del cajón {#drawer-actions}

El componente `AppLayout` te permite colocar componentes personalizados, como botones o íconos, en el área de **acciones del encabezado del cajón** utilizando el método `addToDrawerHeaderActions()`.

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
);
```

Es posible pasar múltiples componentes como argumentos:

```java
layout.addToDrawerHeaderActions(
  new IconButton(TablerIcon.create("bell")),
  new Button("Perfil")
);
```

Las acciones del cajón aparecen en la **sección alineada a la derecha** del encabezado del cajón.

<!--vale off-->
<ComponentDemo
path='/webforj/applayoutdrawerutility/content/Dashboard/'
frame='desktop'
files={[
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java',
  'src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
  'src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
  'src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java',
  'src/main/frontend/css/applayout/applayout.css',
]}
/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='name' label="dwc-icon-button" /> <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

El componente [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) es una clase de servidor de webforJ que representa un botón utilizado para alternar la visibilidad de un cajón de navegación en un `AppLayout`. Se mapea al elemento del lado del cliente `<dwc-app-drawer-toggle>` y está estilizado por defecto para comportarse como un ícono tradicional de menú hamburguesa; este comportamiento se puede personalizar.

### Resumen {#overview-1}

El `AppDrawerToggle` extiende `IconButton` y utiliza el ícono "menu-2" del conjunto de íconos Tabler por defecto. Se aplica automáticamente el atributo `data-drawer-toggle` para integrarse con el comportamiento del cajón del lado del cliente.

```java
// No se requiere registro de eventos:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// El interruptor del cajón funcionará sin problemas, sin necesidad de oyentes de eventos manuales.
```
## Estilo {#styling}

<TableBuilder name="AppLayout" />
