---
sidebar_position: 38
sidebar_class_name: new-content
title: App badges
description: >-
  Paint notification badges onto the operating system app icon and the browser
  tab favicon.
_i18n_hash: ff5a388432db849aa6d7b7ac1f48aa89
---
# Insignias de la aplicación <DocChip chip='since' label='26.01' />

webforJ expone dos API de insignias complementarias. <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>`.setBadge` pinta el ícono de la aplicación del sistema operativo que se muestra en el dock, la barra de tareas o la pantalla de inicio. <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink>`.setIconBadge` pinta el favicon del documento que se muestra en la banda de pestañas del navegador. Apuntan a diferentes superficies y tienen diferentes prerrequisitos, por lo que la mayoría de las aplicaciones llaman a ambas para una visibilidad más amplia.

<!-- INTRO_END -->

## Insignia del ícono de la aplicación {#app-icon-badge}

`App.setBadge` renderiza la insignia en el ícono que el sistema operativo utiliza para la aplicación: el dock de macOS, la barra de tareas de Windows, la estantería de Chrome OS o la pantalla de inicio de Android.

![Insignia del ícono de la aplicación en el dock de macOS](/img/app-badges/app-badge.png)

### Prerrequisitos {#app-prerequisites}

La insignia es visible solo cuando se cumple lo siguiente:

- El navegador admite el dibujo de insignias en íconos de aplicaciones.
- La página se sirve desde un contexto seguro (HTTPS o `http://localhost` durante el desarrollo). Los orígenes de HTTP simples rechazan la llamada.
- La aplicación ha sido instalada en el dispositivo. El flujo de instalación varía según el navegador: los navegadores Chromium ofrecen un aviso de instalación para cualquier página que envíe un manifiesto, Safari en macOS utiliza **Archivo → Agregar al Dock**, y Safari en iOS utiliza **Compartir → Agregar a la Pantalla de Inicio**.

Para que la aplicación sea instalable al ejecutarse bajo Spring Boot o un servidor webforJ independiente, anote la subclase de <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> con <JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" code='true'>AppProfile</JavadocLink>. El procesador de anotaciones emite el manifiesto, las etiquetas de enlace del ícono de la aplicación y las etiquetas meta que el navegador necesita para ofrecer el aviso de instalación.

```java
@AppProfile(name = "Inbox", shortName = "Inbox")
public class Application extends App {}
```

Consulte la página de [Aplicaciones Instalables](../configuration/installable-apps) para obtener la lista completa de miembros de `@AppProfile`, tamaño de íconos y orientación específica de la plataforma sobre el flujo de instalación.

### Soporte del navegador {#app-browser-support}

Después de la instalación, el renderizado de la insignia depende del navegador. El soporte de instalación en sí se trata en la página de [Aplicaciones Instalables](../configuration/installable-apps#browser-support).

| Navegador | Insignia renderizada después de la instalación |
| --- | --- |
| Chrome, Edge, Opera y otros navegadores Chromium (escritorio y Android) | Sí |
| Safari en macOS Sonoma (Safari 17) y posteriores | Sí |
| Safari en iOS 16.4 y posteriores | Sí |
| Firefox (todas las plataformas) | No. La llamada se devuelve sin renderizar. |

### Configuración y limpieza de la insignia {#app-setting-clearing}

Pase un número entero positivo para mostrar una insignia numérica. Pase `null` o `0` para limpiarla. Llame a la sobrecarga sin argumentos para mostrar el indicador de bandera (un punto pequeño, el visual exacto es definido por la plataforma).

```java
App.setBadge(5);     // insignia numérica
App.setBadge();      // indicador de bandera sin un número
App.setBadge(0);     // limpiar
App.setBadge(null);  // limpiar
```

`App.setBadge` devuelve inmediatamente. El navegador escribe la insignia en la superficie del sistema operativo de forma asincrónica, y el cambio no se informa de vuelta a la aplicación.

## Insignia del ícono de la pestaña del navegador {#browser-tab-icon-badge}

`Page.setIconBadge` pinta el conteo en el favicon del documento. Funciona en cualquier pestaña sin necesidad de instalación y no requiere un manifiesto. La insignia es visible en la banda de pestañas del navegador y en cualquier otra ubicación que renderice el favicon, como marcadores o vistas de páginas recientes.

![Favicon de la pestaña del navegador con una superposición de insignia numérica](/img/app-badges/icon-badge.png)

### Configuración y limpieza de la insignia {#tab-setting-clearing}

```java
Page page = Page.getCurrent();

page.setIconBadge(5);     // insignia numérica
page.setIconBadge();      // indicador de bandera sin un número
page.setIconBadge(0);     // limpiar
page.setIconBadge(null);  // limpiar
```

Limpiar la insignia restaura el favicon original.

:::info Ejecución con `BBjServices`
Cuando la aplicación es servida por `BBjServices`, el favicon es la **Imagen de acceso directo** configurada para la aplicación en el Administrador de Empresas. La insignia se pinta en cualquier ícono que el Administrador de Empresas sirva. Si no se configura una imagen de acceso directo, `Page.setIconBadge` no tiene favicon para superponer y no hace nada silenciosamente.
:::

### Estilizando la insignia {#styling-the-badge}

Pase un <JavadocLink type="foundation" location="com/webforj/IconBadgeOptions" code='true'>IconBadgeOptions</JavadocLink> para controlar el color, la forma y el tamaño:

```java
IconBadgeOptions options = new IconBadgeOptions()
    .setColor(new Color(0x2e, 0x7d, 0x32))
    .setShape(IconBadgeOptions.Shape.SQUARE)
    .setSize(1.25);

Page.getCurrent().setIconBadge(5, options);
```

El objeto de opciones es un portador de valores. Todos los métodos `set` devuelven `this` para que las llamadas se puedan encadenar.

| Opción | Tipo | Predeterminado | Notas |
|---|---|---|---|
| `color` | `java.awt.Color` | `#e53935` | Color de fondo de la insignia. El color del texto se deriva automáticamente para contraste, por lo que los dígitos permanecen legibles en cualquier color elegido. |
| `shape` | `Shape` | `CIRCLE` | `CIRCLE` o `SQUARE`. |
| `size` | `double` | `1.0` | Tamaño relativo. `0.5` es la mitad del diámetro predeterminado; `1.5` es un 50% más grande. La insignia se ajusta para encajar dentro del lienzo del favicon. |

### Advertencia del navegador {#browser-caveat}

Safari no actualiza el favicon después de la carga inicial de la página. Las llamadas a `Page.setIconBadge` se completan sin errores, pero Safari continúa mostrando el ícono original. Use `Page.setTitle` para también anteponer el conteo al título del documento si necesita una pista visible en Safari.

```java
int unread = 5;
Page page = Page.getCurrent();
page.setIconBadge(unread);
page.setTitle("(" + unread + ") Inbox");
```

## Elegir entre los dos {#choosing-between-the-two}

| Superficie | API | Requiere instalación | Visible en Safari |
|---|---|---|---|
| Ícono de aplicación del sistema operativo | `App.setBadge` | Sí | Sí (macOS Sonoma / iOS 16.4 y posteriores) |
| Favicon de la pestaña del navegador | `Page.setIconBadge` | No | No. La llamada se completa sin errores, pero la banda de pestañas no se actualiza. |

La mayoría de las aplicaciones llaman a ambos para que la insignia sea visible ya sea que el usuario esté dentro de una ventana instalada o en una pestaña de navegador regular.
