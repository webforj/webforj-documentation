---
sidebar_position: 25
title: Deploying Additional Servlets
description: >-
  Host REST endpoints and third-party servlets alongside a webforJ app by
  remapping WebforjServlet or proxying through webforj.conf.
_i18n_hash: 1e25ddc6c7e56063c26b9f911c0be5d2
---
<!-- vale off -->
# Despliegue de Servlets Adicionales <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ enruta todas las solicitudes a través de `WebforjServlet`, que está mapeado a `/*` en web.xml por defecto. Este servlet gestiona el ciclo de vida del componente, el enrutamiento y las actualizaciones de la interfaz de usuario que impulsan tu aplicación webforJ.

En algunos escenarios, es posible que necesites desplegar servlets adicionales junto con tu aplicación webforJ:
- Integración de bibliotecas de terceros que proporcionan sus propios servlets
- Implementación de APIs REST o webhooks
- Manejo de subidas de archivos con procesamiento personalizado
- Soporte de código basado en servlets heredados

webforJ proporciona dos enfoques para desplegar servlets personalizados junto con tu aplicación:

<AISkillTip skill="webforj-adding-servlets" />

## Enfoque 1: Reasignación de `WebforjServlet` {#approach-1-remapping-webforjservlet}

Este enfoque reasigna el `WebforjServlet` de `/*` a una ruta específica como `/ui/*`, liberando el espacio de nombres de URL para servlets personalizados. Aunque esto requiere modificar `web.xml`, proporciona a los servlets personalizados acceso directo a sus patrones de URL sin sobrecarga de proxy.

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
- No se implica un mecanismo de proxy: enrutamiento directo del contenedor de servlets

:::tip Configuración de Spring Boot
Al usar webforJ con Spring Boot, no hay un archivo `web.xml`. En su lugar, configura el mapeo del servlet en `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Esta propiedad reasigna `WebforjServlet` de la configuración predeterminada `/*` a `/ui/*`, liberando el espacio de nombres de URL para tus servlets personalizados. No incluyas comillas alrededor del valor: se interpretarán como parte del patrón de URL.
:::

## Enfoque 2: Configuración de proxy de `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Este enfoque mantiene `WebforjServlet` en `/*` y configura servlets personalizados en `webforj.conf`. El `WebforjServlet` intercepta todas las solicitudes y envía los patrones coincidentes a tus servlets personalizados.

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
- La clave opcional `config` proporciona pares de nombre/valor como parámetros de inicialización para el servlet
