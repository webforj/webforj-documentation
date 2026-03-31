---
title: AppLayout
sidebar_position: 5
_i18n_hash: 8b9351e865e2651e84f0ae16ef5efc21
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-app-layout" />
<DocChip chip='since' label='23.06' />
<JavadocLink type="applayout" location="com/webforj/component/layout/applayout/AppLayout" top='true'/>

El componente `AppLayout` te proporciona una estructura de página lista para usar con un encabezado y pie de página fijos, un cajón que se desliza hacia dentro y fuera, y un área de contenido desplazable. Juntas, estas secciones cubren las necesidades de diseño de tableros, paneles de administración y la mayoría de las interfaces de múltiples secciones.

<!-- INTRO_END -->

## Características {#features}

El diseño de aplicaciones webforJ es un componente que permite construir diseños comunes de aplicaciones.

<ul>
    <li>Fácil de usar y personalizar</li>
    <li>Diseño responsivo</li>
    <li>Múltiples opciones de diseño</li>
    <li>Funciona con el modo oscuro de webforJ</li>
</ul>

Proporciona un encabezado, pie de página, cajón y sección de contenido, todo integrado en un componente responsivo que se puede personalizar fácilmente para construir rápidamente diseños comunes de aplicaciones como un tablero. El encabezado y el pie de página son fijos, el cajón se desliza dentro y fuera del área de visualización, y el contenido es desplazable.

Cada parte del diseño es un `Div`, que puede contener cualquier control válido de webforJ. Para obtener los mejores resultados, la aplicación debe incluir una etiqueta meta de viewport que contenga viewport-fit=cover. La etiqueta meta hace que el viewport se escale para llenar la pantalla del dispositivo.

```java
@AppMeta(name = "viewport", content = "width=device-width, initial-scale=1.0, viewport-fit=cover, user-scalable=no")
```

## Resumen {#overview}

El siguiente ejemplo de código resultará en una aplicación con una barra lateral colapsable que contiene un logotipo y pestañas para varias opciones de contenido y un encabezado. La demostración utiliza el componente web dwc-icon-button para crear un botón de alternancia del cajón. El botón tiene el atributo data-drawer-toggle que instruye al DwcAppLayout a escuchar eventos de clic provenientes de ese componente para alternar el estado del cajón.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayout/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayout/AppLayoutView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Barra de navegación de ancho completo {#full-width-navbar}

Por defecto, el AppLayout renderiza el encabezado y el pie de página en modo fuera de pantalla. El modo fuera de pantalla significa que la posición del encabezado y el pie de página se desplazará para ajustarse junto al cajón abierto. Desactivar este modo hará que el encabezado y el pie de página ocupen todo el espacio disponible y desplazarán la posición superior e inferior del cajón para ajustarse con el encabezado y el pie de página.

```java showLineNumbers
AppLayout myApp = new AppLayout();

myApp.setHeaderOffscreen(false);
myApp.setFooterOffscreen(false);
```

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutfullnavbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/fullnavbar/AppLayoutFullNavbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Barras de herramientas múltiples {#multiple-toolbars}

La barra de navegación no tiene límite en la cantidad de barras de herramientas que puedes agregar. Una `Toolbar` es un componente contenedor horizontal que alberga un conjunto de botones de acción, íconos u otros controles. Para agregar una barra de herramientas adicional, simplemente utiliza el método `addToHeader()` para agregar otro componente `Toolbar`.

La siguiente demostración muestra cómo usar dos barras de herramientas. La primera alberga el botón de alternancia del cajón y el título de la aplicación. La segunda barra de herramientas alberga un menú de navegación secundario.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmultipleheaders/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeadersView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/multipleheaders/AppLayoutMultipleHeaderContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Barras de herramientas adhesivas {#sticky-toolbars}

Una barra de herramientas adhesiva es una barra de herramientas que permanece visible en la parte superior de la página cuando el usuario se desplaza hacia abajo, pero la altura de la barra de navegación se colapsa para hacer más espacio disponible para el contenido de la página. Normalmente, este tipo de barra de herramientas contiene un menú de navegación fijo que es relevante para la página actual.

Es posible crear barras de herramientas adhesivas utilizando la propiedad CSS personalizada `--dwc-app-layout-header-collapse-height` y la opción `AppLayout.setHeaderReveal()`.

Cuando se llama a `AppLayout.setHeaderReveal(true)`, el encabezado será visible en el primer renderizado y luego se ocultará cuando el usuario comience a desplazarse hacia abajo. Una vez que el usuario comience a desplazarse hacia arriba nuevamente, el encabezado será revelado.

Con la ayuda de la propiedad CSS personalizada `--dwc-app-layout-header-collapse-height`, es posible controlar cuánto del encabezado de la barra de navegación será ocultado.

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutstickytoolbar/content/Dashboard?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/stickytoolbar/AppLayoutStickyToolbarContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Diseño de navegación móvil {#mobile-navigation-layout}

La barra de navegación inferior se puede usar para proporcionar una versión diferente de la navegación en la parte inferior de la aplicación. Este tipo de navegación es particularmente popular en aplicaciones móviles.

Observa cómo el cajón está oculto en la siguiente demostración. El widget AppLayout soporta tres posiciones del cajón: `DrawerPlacement.LEFT`, `DrawerPlacement.RIGHT` y `DrawerPlacement.HIDDEN`.

Al igual que `AppLayout.setHeaderReveal()`, se soporta `AppLayout.setFooterReveal()`. Cuando se llama a `AppLayout.setFooterReveal(true)`, el pie de página será visible en el primer renderizado y luego se ocultará cuando el usuario comience a desplazarse hacia arriba. Una vez que el usuario comience a desplazarse hacia abajo nuevamente, el pie de página será revelado.

Por defecto, cuando el ancho de la pantalla es de 800px o menos, el cajón se cambiará a modo popover. Esto se llama el punto de quiebre. El modo popover significa que el cajón aparecerá sobre el área de contenido con una superposición. Es posible configurar el punto de quiebre utilizando el método `setDrawerBreakpoint()` y el punto de quiebre debe ser una consulta de medios válida [media query](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries).

<!--vale off-->
<AppLayoutViewer path='/webforj/applayoutmobiledrawer/?' mobile='true'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/mobiledrawer/AppLayoutMobileDrawerContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->

## Utilidades del cajón {#drawer-utilities}

Las utilidades del cajón `AppLayout` están diseñadas para navegación integrada y menús contextuales dentro del diseño principal de la aplicación, mientras que los componentes de cajón [`Drawer`](https://docs.webforj.com/docs/components/drawer) independientes ofrecen paneles deslizantes flexibles que se pueden usar en cualquier lugar de tu aplicación para contenido adicional, filtros o notificaciones. Esta sección se centra en las características y utilidades incorporadas del cajón proporcionadas por AppLayout.

### Punto de quiebre del cajón {#drawer-breakpoint}

Por defecto, cuando el ancho de la pantalla es de 800px o menos, el cajón se cambiará a modo popover. Esto se llama el punto de quiebre. El modo popover significa que el cajón aparecerá sobre el área de contenido con una superposición. Es posible configurar el punto de quiebre utilizando el método `setDrawerBreakpoint()` y el punto de quiebre debe ser una consulta de medios válida.

Por ejemplo, en el siguiente ejemplo, el punto de quiebre del cajón se configura para ser de 500px o menos.

```java
AppLayout demo = new AppLayout();
demo.setDrawerBreakpoint("(max-width:500px)");
```

### Título del cajón {#drawer-title}

El componente `AppLayout` proporciona un método `addToDrawerTitle()` para definir un título personalizado que se mostrará en el encabezado del cajón.

```java
layout.addToDrawerTitle(new Div("Menu"));
```

### Acciones del cajón {#drawer-actions}

El componente `AppLayout` te permite colocar componentes personalizados como botones o íconos en el área de acciones del encabezado del cajón utilizando el método `addToDrawerHeaderActions()`.

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
<AppLayoutViewer path='/webforj/applayoutdrawerutility/content/Dashboard/?' mobile='false'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityView.java'
cssURL='/css/applayout/applayout.css'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/DrawerLogo.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/applayoutdrawerutility/AppLayoutDrawerUtilityContentView.java',
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/applayout/AbstractContentView.java']}/>
<!--vale on-->


## `AppDrawerToggle` <DocChip chip='since' label='24.12' /> {#appdrawertoggle-docchip-chipsince-label2412-}

El componente [`AppDrawerToggle`](https://javadoc.io/doc/com.webforj/webforj-applayout/latest/com/webforj/component/layout/applayout/AppDrawerToggle.html) es una clase de servidor webforJ que representa un botón utilizado para alternar la visibilidad de un cajón de navegación en un `AppLayout`. Se mapea al elemento del cliente `<dwc-app-drawer-toggle>` y está estilizado por defecto para comportarse como un ícono de menú hamburguesa tradicional, este comportamiento puede ser personalizado.

### Resumen {#overview-1}

El `AppDrawerToggle` extiende `IconButton` y utiliza el ícono "menu-2" del conjunto de íconos Tabler por defecto. Aplicando automáticamente el atributo `data-drawer-toggle` para integrarse con el comportamiento del cajón en el lado del cliente.

```java
// No se requiere registro de eventos:
AppLayout layout = new AppLayout();
layout.addToHeader(new AppDrawerToggle());
// El toggle del cajón funcionará directamente—no se necesitan oyentes de eventos manuales.
```
## Estilización {#styling}

<TableBuilder name="AppLayout" />
