---
title: Rutas
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: ec7a4521cd9c4328f9cd6550ebc9d90b
---
La pestaña Rutas muestra la tabla de enrutamiento de la aplicación en ejecución, en la [jerarquía](/docs/routing/route-hierarchy/overview) en la que la mantiene el enrutador, y marca la ruta activa. Las rutas registradas [dinámicamente](/docs/routing/routes-registration) aparecen junto a las anotadas.

![El árbol de rutas con la ruta activa marcada](/img/craftforj/routes/tree.png#rounded-border)

## Detalles de la ruta {#route-details}

Al seleccionar una ruta, se muestra lo que el enrutador sabe sobre ella, incluidos su path, la clase que la respalda, los observadores del ciclo de vida asociados y su configuración. Desde aquí puedes abrir esa clase en el visor de código fuente.

## Navegar desde craftforJ {#navigating-from-craftforj}

Puedes navegar directamente a cualquier ruta desde craftforJ. Las rutas que aceptan parámetros ofrecen un campo para cada uno y resuelven el path a medida que los completas, para que puedas confirmar adónde llegarás antes de navegar.

Navegar de esta manera es una navegación real, así que los [observadores del ciclo de vida](/docs/routing/navigation-lifecycle/observers) de tu aplicación se ejecutan exactamente igual que para un usuario. El árbol también sigue el estado de la aplicación, por lo que navegar en la propia aplicación mueve el marcador.

<VideoPlayer video="craftforj/route-navigate" width={1920} height={1000} title="Route navigate" />

## Reglas de acceso {#access-rules}

Cada ruta muestra una insignia con la [anotación de seguridad](/docs/security/annotations) declarada en ella, y puedes filtrar el árbol para mostrar rutas públicas o protegidas desde la barra de herramientas.

Solo `@RolesAllowed` y `@DenyAll` cuentan como protegidas. `@PermitAll` no especifica roles y solo requiere que alguien haya iniciado sesión, por lo que el filtro la trata como pública. Tenlo en cuenta al comprobar qué rutas restringen el acceso según el rol.

![El árbol de rutas con una insignia de acceso en cada ruta](/img/craftforj/routes/access-badge.png#rounded-border)

También puedes cambiar la regla de acceso de una ruta desde craftforJ. craftforJ escribe la anotación en la clase de la ruta y la aplicación se reinicia, por lo que el cambio pasa por la misma revisión que cualquier otro [cambio en el código fuente](/docs/craftforj/source-changes). Esta opción no está disponible cuando craftforJ no tiene permiso para escribir código Java.

<VideoPlayer video="craftforj/route-security" width={1920} height={1000} title="Route security" />
