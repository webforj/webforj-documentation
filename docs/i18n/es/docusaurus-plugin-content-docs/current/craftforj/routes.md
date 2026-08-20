---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: 8a8c4099d3bd0d4ff988038cee6a5c15
---
La pestaña Rutas muestra la tabla de enrutamiento de la aplicación en ejecución en la [jerarquía](/docs/routing/route-hierarchy/overview) que el enrutador mantiene, con la ruta activa marcada. Las rutas registradas [dinámicamente](/docs/routing/routes-registration) aparecen junto a las anotadas.

![El árbol de rutas con la ruta activa marcada](/img/craftforj/routes/tree.png#rounded-border)

## Detalles de la ruta {#route-details}

Seleccionar una ruta muestra lo que el enrutador sabe sobre ella, incluyendo su ruta, la clase detrás de ella, los observadores del ciclo de vida adjuntos a ella y su configuración. Desde aquí puedes abrir esa clase en el visor de código fuente.

## Navegando desde craftforJ {#navigating-from-craftforj}

Puedes navegar a cualquier ruta directamente desde craftforJ. Las rutas que toman parámetros ofrecen un campo para cada uno y resuelven la ruta a medida que los completas, para que puedas confirmar dónde aterrizarás antes de ir.

Navegar de esta manera es una navegación real, por lo que los [observadores del ciclo de vida](/docs/routing/navigation-lifecycle/observers) de tu aplicación se ejecutan exactamente como lo harían para un usuario. El árbol también sigue a la aplicación, por lo que navegar en la propia aplicación mueve el marcador.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4" type="video/mp4" />
  </video>
</div>

## Reglas de acceso {#access-rules}

Cada ruta lleva una insignia de la [anotación de seguridad](/docs/security/annotations) declarada en ella, y puedes limitar el árbol a rutas públicas o protegidas desde la barra de herramientas.

Solo `@RolesAllowed` y `@DenyAll` cuentan como protegidas. `@PermitAll` no nombre roles y requiere solo que alguien haya iniciado sesión, por lo que el filtro la trata como pública. Ten eso en cuenta al verificar qué rutas restringen el acceso por rol.

![El árbol de rutas con una insignia de acceso en cada ruta](/img/craftforj/routes/access-badge.png#rounded-border)

También puedes cambiar la regla de acceso de una ruta desde craftforJ. craftforJ escribe la anotación en la clase de la ruta y la aplicación se reinicia, por lo que el cambio pasa por la misma revisión que cualquier otro [cambio en el código fuente](/docs/craftforj/source-changes). La opción no está disponible cuando no se permite que craftforJ escriba en Java.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4" type="video/mp4" />
  </video>
</div>
