---
title: Spring DevTools
sidebar_position: 30
description: >-
  Pair Spring DevTools with webforJ DevTools to auto-restart the app and refresh
  the browser when Java, CSS, or asset files change.
_i18n_hash: 183c4eb42a93904e03dff44faf2118e7
---
Spring DevTools proporciona reinicios automáticos de la aplicación cuando hay cambios en el código. webforJ DevTools añade la actualización automática del navegador: cuando Spring reinicia tu aplicación, el navegador se actualiza automáticamente a través del servidor LiveReload de webforJ.

Diferentes tipos de archivos desencadenan diferentes comportamientos de recarga. Los cambios en el código Java provocan un reinicio completo de Spring y una actualización del navegador. Las modificaciones en CSS e imágenes se actualizan sin recargar la página, preservando los datos del formulario y el estado de la aplicación.

:::tip Cambios en el frontend
Los cambios en `src/main/frontend` son gestionados por el [frontend watch](/docs/configuration/deploy-reload/frontend-watch), que los recompila y actualiza el navegador junto con el servidor.
:::

<!-- vale off -->
## Comprendiendo webforJ DevTools {#understanding-webforj-devtools}
<!-- vale on -->

webforJ extiende Spring DevTools con sincronización del navegador. Cuando Spring detecta cambios en los archivos y reinicia, webforJ DevTools actualiza automáticamente tu navegador.

### Comportamiento de recarga {#reload-behavior}

Diferentes tipos de archivos desencadenan diferentes estrategias de recarga:

- **Archivos Java**: Recarga completa de la página del navegador después del reinicio de Spring
- **Archivos JavaScript**: Recarga completa de la página del navegador después del reinicio de Spring
- **Archivos CSS**: Actualizaciones de estilo sin recarga de la página
- **Imágenes**: Actualización en su lugar sin recarga de la página

## Dependencias {#dependencies}

Agrega tanto Spring DevTools como webforJ DevTools a tu proyecto:

```xml title="pom.xml"
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>

<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-spring-devtools</artifactId>
  <version>${webforj.version}</version>
  <optional>true</optional>
</dependency>
```

## Configuración {#configuration}

Habilita webforJ DevTools en tu archivo `application.properties`:

```Ini title="application.properties"
# Habilitar auto-reload del navegador webforJ
webforj.devtools.livereload.enabled=true

# Habilitar apagado inmediato para reinicios más rápidos
server.shutdown=immediate
```

### Configuración avanzada {#advanced-configuration}

Configura la conexión WebSocket y el comportamiento de recarga:

```Ini title="application.properties"
# Puerto del servidor WebSocket (default: 35730)
webforj.devtools.livereload.websocket-port=35730

# Ruta del endpoint WebSocket (default: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Intervalo de heartbeat en milisegundos (default: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Habilitar recarga en caliente para recursos estáticos (default: true)
webforj.devtools.livereload.static-resources-enabled=true
```

<DocChip chip='since' label='25.03' /> Configurar la apertura del navegador al iniciar la aplicación:

```Ini title="application.properties"
# Habilitar apertura del navegador (default: false)
webforj.devtools.browser.open=true

# localhost, nombre de host o dirección IP (default: localhost)
webforj.devtools.browser.host=localhost
```
