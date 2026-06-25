---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: >-
  Rebuild frontend sources while a webforJ app runs and refresh the browser,
  with stylesheet and image changes applied in place and script changes
  reloading the view.
_i18n_hash: efb22f8bcac71567979d21178e62ba7c
---
El watch del frontend reconstruye tus fuentes en `src/main/frontend` mientras la aplicación está en ejecución y actualiza el navegador, de modo que un cambio en el frontend aparece sin necesidad de una construcción manual o un reinicio del servidor. Es el lado de desarrollo del [bundler del frontend](/docs/managing-resources/bundler/overview).

## Ejecutando el watch {#running-the-watch}

Ejecuta esto junto a tu servidor, ya que es el objetivo anterior al que inicia la aplicación. Un proyecto de Spring establece esto como su objetivo predeterminado, por lo que `mvn` sin argumentos inicia ambos:

```bash
mvn compile webforj:watch spring-boot:run
```

Contra el [plugin Maven Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin), inícialo de la misma manera:

```bash
mvn compile webforj:watch jetty:run
```

Para el watch como un paso de construcción, consulta [Construcción y pruebas](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## Qué sucede en un cambio {#what-happens-on-a-change}

Cuando guardas una fuente, el watch reconstruye y envía los archivos modificados al navegador. Lo que hace el navegador depende de la salida, no de la fuente que editaste:

- Un **stylesheet** se parchea en su lugar, sin recarga, por lo que los datos del formulario y la posición de desplazamiento permanecen. Una edición de `.css`, `.scss`, `.sass`, o `.less` entra aquí.
- Una **imagen** se intercambia en su lugar, sin recarga.
- **Cualquier otra cosa** recarga la vista. Una edición de `.ts`, `.tsx`, o `.js` entra aquí, ya que un nuevo comportamiento necesita una página fresca.

Si una reconstrucción toca varios archivos y todos pueden parchearse en su lugar, el navegador permanece idéntico. Si incluso uno no puede, se recarga, por lo que nunca ves un cambio a medias.

## A través de un reinicio del servidor {#across-a-server-restart}

Un cambio en Java reinicia el servidor, a través de [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools), el [plugin Maven Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin), o [JRebel](/docs/configuration/deploy-reload/jrebel). El watch mantiene el frontend sincronizado con él:

- **Tus estilos permanecen aplicados** a través del reinicio, en lugar de parpadear sin estilo.
- **La página se recarga una vez que la aplicación está lista**, no antes, para que no te enfrentes a un error por recargar demasiado pronto. Un pequeño indicador aparece mientras el servidor se reinicia; una recarga manual no muestra nada.
- **Agregar o eliminar un `@BundleEntry` tiene efecto en el próximo reinicio.**

## Configuración {#configuration}

La recarga en vivo es una configuración de webforJ, por lo que se aplica a cualquier servidor que ejecutes. Una aplicación de Spring lee estas claves de `application.properties`; un servidor independiente, como el [plugin Maven Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin), lee las mismas claves de `webforj.conf`.

```ini title="application.properties"
# Refrescar el navegador en un cambio
webforj.devtools.livereload.enabled=true

# Parchear stylesheets e imágenes en su lugar en lugar de recargar (predeterminado: true)
webforj.devtools.livereload.static-resources-enabled=true

# Puerto y ruta a la que se conecta el navegador (predeterminado: 35730, /webforj-devtools-ws)
webforj.devtools.livereload.websocket-port=35730
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Intervalo de latido en milisegundos (predeterminado: 30000)
webforj.devtools.livereload.heartbeat-interval=30000
```
