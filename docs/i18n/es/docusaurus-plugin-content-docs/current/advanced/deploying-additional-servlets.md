---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# Desplegando Servlets Adicionales <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ enruta todas las solicitudes a través de `WebforjServlet`, que está mapeado a `/*` en web.xml por defecto. Este servlet gestiona el ciclo de vida del componente, la ruta y las actualizaciones de la interfaz de usuario que impulsan tu aplicación webforJ.

En algunos escenarios, puede que necesites desplegar servlets adicionales junto a tu aplicación webforJ:
- Integrando bibliotecas de terceros que proporcionan sus propios servlets
- Implementando API REST o webhooks
- Manejando cargas de archivos con procesamiento personalizado
- Soportando código legado basado en servlets

webforJ proporciona dos enfoques para desplegar servlets personalizados junto a tu aplicación:

## Enfoque 1: Remapeo de `WebforjServlet` {#approach-1-remapping-webforjservlet}

Este enfoque remapea el `WebforjServlet` de `/*` a una ruta específica como `/ui/*`, liberando el espacio de nombres de URL para servlets personalizados. Aunque esto requiere modificar `web.xml`, permite a los servlets personalizados acceso directo a sus patrones de URL sin ningún tipo de sobrecarga de proxy.

```xml
<web-app>
  <!-- WebforjServlet remapeado para manejar solo /ui/* -->
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
- El servlet personalizado maneja las solicitudes a `/hello-world`
- No hay mecanismo de proxy involucrado - enrutamiento directo del contenedor de servlets

:::tip Configuración de Spring Boot
Al usar webforJ con Spring Boot, no hay un archivo `web.xml`. En su lugar, configura el mapeo del servlet en `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

Esta propiedad remapea `WebforjServlet` del predeterminado `/*` a `/ui/*`, liberando el espacio de nombres de URL para tus servlets personalizados. No incluyas comillas alrededor del valor, ya que se interpretarán como parte del patrón de URL.
:::

## Enfoque 2: Configuración de proxy de `WebforjServlet` {#approach-2-webforjservlet-proxy-configuration}

Este enfoque mantiene `WebforjServlet` en `/*` y configura servlets personalizados en `webforJ.conf`. El `WebforjServlet` intercepta todas las solicitudes y envía a sus servlets personalizados según los patrones coincidentes.

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
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

Con esta configuración:
- `WebforjServlet` maneja todas las solicitudes
- Las solicitudes a `/hello-world` son enviadas al `HelloWorldServlet`
