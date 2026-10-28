---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: >-
  Apply code changes to a running webforJ app during development, on the server
  through hotswap or a restart, and in the browser through live reload.
_i18n_hash: 243a55fe41bec8aeb4dfc9f70561d80c
---
Durante el desarrollo, webforJ aplica los cambios guardados a la aplicación en ejecución y actualiza el navegador. Los cambios en las clases alcanzan la aplicación a través de una [herramienta de hotswap](/docs/configuration/deploy-reload/hotswap) o mediante un reinicio. La recarga en vivo actualiza el navegador después de cualquiera de estas acciones.

Los proyectos creados a partir de un [arquetipo](/docs/introduction/getting-started) vienen configurados. Para un proyecto existente, sigue [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) o [BBjServices](/docs/configuration/deploy-reload/bbjservices).

## Cómo se aplica cada cambio {#how-each-change-applies}

| Cambio | Resultado | Referencia |
|---|---|---|
| Clase Java, herramienta de hotswap adjunta | La clase se actualiza en la aplicación en ejecución. La parte afectada de la página se reconstruye y el estado de la aplicación se mantiene. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Clase Java, sin herramienta de hotswap | La aplicación se reinicia. El navegador se recarga cuando la aplicación está lista. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Clase Java en BBjServices, sin herramienta de hotswap | El cambio no se aplica hasta que el watch comienza nuevamente. | [BBjServices](/docs/configuration/deploy-reload/bbjservices) |
| Hoja de estilo o imagen | La página lo aplica en su lugar, sin recarga. | [Settings](#settings) |
| Código fuente bajo `src/main/frontend` | El watch lo reconstruye y actualiza el navegador. | [Frontend watch](/docs/configuration/deploy-reload/frontend-watch) |

## Configuraciones {#settings}

Estas configuraciones controlan la recarga en vivo durante el desarrollo:

| Propiedad | Predeterminado | Descripción |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Activa la recarga en vivo para ejecuciones de desarrollo. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Puerto para la conexión del navegador. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Ruta para la conexión del navegador. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Aplica cambios en hojas de estilo e imágenes en su lugar en lugar de recargar la página. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Intervalo en milisegundos para las verificaciones de conexión que detectan un servidor en reinicio. |

Las claves no tienen efecto en una aplicación empaquetada. Las aplicaciones empaquetadas no contienen herramientas de desarrollo.

## Temas {#topics}

<DocCardList className="topics-section" />
