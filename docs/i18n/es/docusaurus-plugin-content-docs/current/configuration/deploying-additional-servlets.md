---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 95695a68854d595e78a58904d7214208
---
<!-- vale off -->
# Desplegando Servlets Adicionales <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ enruta todas las solicitudes a través de `WebforjServlet`, que está mapeado a `/*` en web.xml por defecto. Este servlet gestiona el ciclo de vida del componente, el enrutamiento y las actualizaciones de la interfaz de usuario que alimentan tu aplicación webforJ.

En algunos escenarios, es posible que necesites desplegar servlets adicionales junto con tu aplicación webforJ:
- Integrar bibliotecas de terceros que proporcionen sus propios servlets
- Implementar APIs REST o webhooks
- Manejar cargas de archivos con procesamiento personalizado
- Soportar código heredado basado en servlets

webforJ ofrece dos enfoques para desplegar servlets personalizados junto con tu aplicación:

## Enfoque 1: Reasignar `WebforjServlet` {#approach-1-remapping-webforjservlet}

Este enfoque reasigna el `WebforjServlet` de `/*` a una ruta específica como `/ui/*`, liberando el espacio de nombres de URL para servlets personalizados. Aunque esto requiere modificar `web.xml`, da acceso directo a los patrones de URL de los servlets personalizados sin ninguna sobrecarga de proxy.

```xml
<web-app>
  <!-- WebforjServlet reasignado para manejar solo /ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Servlet personalizado con su propio patrón de URL -->
  <servlet>
    <servlet-name>HelloWorldServlet</servlet-name>
    <servlet-class>com.example.HelloWorldServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>HelloWorldServlet</servlet-name>
    <url-pattern>/hello-world</url-pattern>
  </servlet-mapping>
</web-app>
```

Con esta configuración:
- Los componentes de webforJ son accesibles bajo `/ui/`
- El servlet personalizado maneja solicitudes a `/hello-world`
- No hay mecanismo de proxy involucrado - enrutamiento directo del contenedor de servlets

:::tip Configuración de Spring Boot
Cuando uses webforJ con Spring Boot, no hay un archivo `web.xml`. En su lugar, configura el mapeo de servlets en `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Esta propiedad reasigna `WebforjServlet` del `/*` por defecto a `/ui/*`, liberando el espacio de nombres de URL para tus servlets personalizados. No incluyas comillas alrededor del valor; se interpretarán como parte del patrón de URL.
:::

## Enfoque 2: Configuración de proxy de `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Este enfoque mantiene `WebforjServlet` en `/*` y configura servlets personalizados en `webforj.conf`. El `WebforjServlet` intercepta todas las solicitudes y envía las que coinciden con patrones a tus servlets personalizados.

### Configuración estándar de web.xml {#standard-webxml-configuration}

```xml
<servlet>
  <servlet-name>WebforjServlet</servlet-name>
  <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
  <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
  <servlet-name>WebforjServlet</servlet-name>
  <url-pattern>/*</url-pattern>
</servlet-mapping>

<!-- Servlet personalizado con su propio patrón de URL -->
<servlet>
  <servlet-name>HelloWorldServlet</servlet-name>
  <servlet-class>com.example.HelloWorldServlet</servlet-class>
</servlet>
<servlet-mapping>
  <servlet-name>HelloWorldServlet</servlet-name>
  <url-pattern>/hello-world</url-pattern>
</servlet-mapping>
</web-app>
```

### Configuración de webforJ.conf {#webforjconf-configuration}

```hocon
servlets: [
  {
    class: "com.example.HelloWorldServlet",
    name: "hello-world",
    config: {
      foo: "bar",
      baz: "bang"
    }
  }
]
```

Con esta configuración:
- `WebforjServlet` maneja todas las solicitudes
- Las solicitudes a `/hello-world` se envían al `HelloWorldServlet`
- La clave opcional `config` proporciona pares nombre/valor como parámetros de inicialización para el servlet
