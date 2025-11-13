---
title: Configuración y Preparativos
sidebar_position: 2
_i18n_hash: 5d819b2a84de98748b48e7b3b1c9ab66
---
Integrar Webswing con webforJ implica dos componentes: el servidor Webswing que alberga tu aplicación Swing y el componente `WebswingConnector` en tu aplicación webforJ que lo incrusta.

## Prerrequisitos

Antes de comenzar, asegúrate de tener los siguientes prerrequisitos:

- **Aplicación de escritorio Java**: una aplicación Swing, JavaFX o SWT empaquetada como un archivo JAR
- **Servidor Webswing**: descargar desde [webswing.org](https://webswing.org)
- **Versión de webforJ `25.10` o posterior**: requerida para el soporte de `WebswingConnector`

## Resumen de la arquitectura

La arquitectura de integración consta de:

1. **Servidor Webswing**: ejecuta tu aplicación Swing, captura la representación de la GUI y maneja la entrada del usuario
2. **Aplicación webforJ**: aloja tu aplicación web con el `WebswingConnector` incrustado
3. **Cliente del navegador**: muestra tanto la UI de webforJ como la aplicación Swing incrustada

:::important Configuración del puerto
Webswing y webforJ deben ejecutarse en puertos diferentes para evitar conflictos. Normalmente, tanto webforJ como Webswing se ejecutan en el puerto `8080`. Debes cambiar el puerto de Webswing o el puerto de webforJ.
:::

## Configuración del servidor Webswing

### Instalación y puesta en marcha

1. **Descargar Webswing** desde el [sitio web oficial](https://www.webswing.org/en/downloads)
2. **Extraer el archivo** en tu ubicación preferida (por ejemplo, `/opt/webswing` o `C:\webswing`)
3. **Iniciar el servidor** utilizando los scripts específicos de la plataforma:

<Tabs>
      <TabItem value="Linux" label="Linux" default>
        ```bash
        webswing.sh
        ```
      </TabItem>
      <TabItem value="macOS" label="macOS">
        ```bash
        webswing.command
        ```
      </TabItem>
      <TabItem value="Windows" label="Windows">
        ```bash
        webswing.bat
        ```
      </TabItem>
</Tabs>

4. **Verificar que el servidor está en funcionamiento** accediendo a `http://localhost:8080`

### Configuración de la aplicación

Una vez que el servidor esté en funcionamiento, accede a la consola de administración en `http://localhost:8080/admin` para agregar y configurar tu aplicación Swing.

En la consola de administración, configura:

- **Nombre de la aplicación**: formará parte de la ruta de URL (por ejemplo, `myapp` → `http://localhost:8080/myapp/`)
- **Clase principal**: el punto de entrada de tu aplicación Swing
- **Classpath**: ruta a tu JAR de aplicación y dependencias
- **Argumentos de JVM**: configuraciones de memoria, propiedades del sistema y otras opciones de JVM
- **Directorio de inicio**: directorio de trabajo para la aplicación

Después de la configuración, tu aplicación Swing será accesible en `http://localhost:8080/[nombre-aplicación]/`

## Integración con webforJ

Una vez que tu servidor Webswing esté en funcionamiento con tu aplicación Swing configurada, puedes integrarlo en tu aplicación webforJ. Esto implica agregar la dependencia, configurar el uso compartido de recursos de origen cruzado (CORS) y crear una vista con el componente `WebswingConnector`.

### Agregar dependencia

Agrega el módulo de integración de Webswing a tu proyecto webforJ. Esto proporciona el componente `WebswingConnector` y clases relacionadas.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### Configuración de CORS

La configuración de Cross-Origin Resource Sharing (CORS) es necesaria cuando Webswing y webforJ se ejecutan en diferentes puertos o dominios. La política de mismo origen del navegador bloquea las solicitudes entre diferentes orígenes sin los encabezados CORS adecuados.

Crea un filtro de servlet para agregar encabezados CORS a tu aplicación webforJ:

```java title="CorsFilter.java"
package com.example.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // sin operaciones
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (response instanceof HttpServletResponse) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setHeader("Access-Control-Allow-Origin", "*");
      httpResponse.setHeader("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      httpResponse.setHeader("Access-Control-Allow-Headers",
          "Content-Type, Authorization, X-Requested-With");
      httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // sin operaciones
  }
}
```

Registra el filtro en tu `web.xml`:

```xml
<filter>
  <filter-name>CorsFilter</filter-name>
  <filter-class>com.example.config.CorsFilter</filter-class>
</filter>

<filter-mapping>
  <filter-name>CorsFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

:::important Acceso en entornos de producción
Para entornos de producción, reemplaza el comodín (`*`) en `Access-Control-Allow-Origin` con la URL específica de tu servidor Webswing por motivos de seguridad.
:::

### Implementación básica

Crea una vista que incruste tu aplicación Swing utilizando el `WebswingConnector`:

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private WebswingConnector connector;

  public SwingAppView() {
    // Inicializa el conector con la URL de tu aplicación Webswing
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Establece las dimensiones de visualización
    connector.setSize("100%", "600px");

    // Agrega al contenedor de la vista
    getBoundComponent().add(connector);
  }
}
```

El conector establece automáticamente una conexión con el servidor Webswing cuando se agrega al DOM. La interfaz de usuario de la aplicación Swing se renderiza dentro del componente conector.

## Opciones de configuración

La clase `WebswingOptions` te permite personalizar el comportamiento del conector. Por defecto, el conector se inicia automáticamente cuando se crea y utiliza configuraciones de conexión estándar. Puedes modificar este comportamiento creando una instancia de `WebswingOptions` y aplicándola al conector.

Por ejemplo, para ocultar el botón de cierre de sesión en un entorno de producción donde gestionas la autenticación a través de tu aplicación webforJ:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Ocultar el botón de cierre de sesión

connector.setOptions(options);
```

O si necesitas control manual sobre cuándo se inicia la conexión:

```java
// Crear conector sin auto-inicio
WebswingConnector connector = new WebswingConnector(url, false);

// Configurar e iniciar cuando esté listo
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Las opciones cubren la gestión de conexiones, autenticación, depuración y monitoreo.
