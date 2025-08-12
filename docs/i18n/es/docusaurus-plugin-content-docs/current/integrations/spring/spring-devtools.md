---
title: Spring DevTools
sidebar_position: 30
_i18n_hash: 5401d3aa92e9230c4f26c827dcf83162
---
Spring DevTools proporciona reinicios automáticos de la aplicación cuando hay cambios en el código. webforJ DevTools añade una actualización automática del navegador: cuando Spring reinicia tu aplicación, el navegador se actualiza automáticamente a través del servidor LiveReload de webforJ.

Diferentes tipos de archivos activan diferentes comportamientos de recarga. Los cambios en el código Java provocan un reinicio completo de Spring y una actualización del navegador. Los cambios en CSS e imágenes se actualizan sin recargar la página, preservando los datos del formulario y el estado de la aplicación.

## Entendiendo webforJ DevTools {#understanding-webforj-devtools}

webforJ amplía Spring DevTools con sincronización del navegador. Cuando Spring detecta cambios en los archivos y reinicia, webforJ DevTools actualiza automáticamente tu navegador.

### Comportamiento de recarga {#reload-behavior}

Diferentes tipos de archivos activan diferentes estrategias de recarga:

- **Archivos Java** - Recarga completa de la página del navegador después del reinicio de Spring
- **Archivos CSS** - Actualizaciones de estilo sin recarga de página  
- **Archivos JavaScript** - Recarga completa de la página del navegador después del reinicio de Spring
- **Imágenes** - Actualización en su lugar sin recarga de página

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

Habilita webforJ DevTools en las propiedades de tu aplicación:

```Ini title="application.properties"
# Habilitar la recarga automática del navegador webforJ
webforj.devtools.livereload.enabled=true

# Habilitar el apagado inmediato para reinicios más rápidos
server.shutdown=immediate
```

### Configuración avanzada {#advanced-configuration}

Configura la conexión WebSocket y el comportamiento de recarga:

```Ini title="application.properties"
# Puerto del servidor WebSocket (por defecto: 35730)
webforj.devtools.livereload.websocket-port=35730

# Ruta del endpoint WebSocket (por defecto: /webforj-devtools-ws)
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Intervalo de latido en milisegundos (por defecto: 30000)
webforj.devtools.livereload.heartbeat-interval=30000

# Habilitar recarga en caliente para recursos estáticos (por defecto: true)
webforj.devtools.livereload.static-resources-enabled=true
```
