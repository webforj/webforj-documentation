---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: cd2108b15ca8583794ddb366329c3638
---
Integrar Webswing con webforJ implica dos componentes: el servidor Webswing que aloja tu aplicación Swing y el componente `WebswingConnector` en tu aplicación webforJ que lo embebe.

## Prerrequisitos {#prerequisites}

Antes de comenzar, asegúrate de tener los siguientes prerrequisitos:

- **Aplicación de escritorio Java**: una aplicación Swing, JavaFX, o SWT empaquetada como un archivo JAR.
- **Servidor Webswing**: descarga desde [webswing.org](https://webswing.org).
- **Versión de webforJ `25.10` o posterior**: requerida para el soporte de `WebswingConnector`.

## Visión general de la arquitectura {#architecture-overview}

La arquitectura de integración consiste en:

1. **Servidor Webswing**: ejecuta tu aplicación Swing, captura el renderizado de la GUI y maneja la entrada del usuario.
2. **Aplicación webforJ**: aloja tu aplicación web con el `WebswingConnector` embebido.
3. **Cliente del navegador**: muestra tanto la interfaz de usuario de webforJ como la aplicación Swing embebida.

:::important Configuración del puerto
Webswing y webforJ deben ejecutarse en puertos diferentes para evitar conflictos. Ambos, webforJ y Webswing, suelen ejecutarse en el puerto `8080`. Debes cambiar el puerto de Webswing o el puerto de webforJ.
:::

## Configuración del servidor Webswing {#webswing-server-setup}

### Instalación y arranque {#installation-and-startup}

1. **Descarga Webswing** desde el [sitio web oficial](https://www.webswing.org/en/downloads).
2. **Extrae el archivo** en la ubicación que prefieras (por ejemplo, `/opt/webswing` o `C:\webswing`).
3. **Inicia el servidor** utilizando los scripts específicos de la plataforma:

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

4. **Verifica que el servidor esté en funcionamiento** accediendo a `http://localhost:8080`.

### Configuración de la aplicación {#application-configuration}

Una vez que el servidor esté en funcionamiento, accede a la consola de administración en `http://localhost:8080/admin` para agregar y configurar tu aplicación Swing.

En la consola de administración, configura:

- **Nombre de la aplicación**: forma parte de la ruta de la URL (por ejemplo, `myapp` → `http://localhost:8080/myapp/`).
- **Clase principal**: el punto de entrada de tu aplicación Swing.
- **Classpath**: ruta a tu JAR de aplicación y dependencias.
- **Argumentos JVM**: configuraciones de memoria, propiedades del sistema y otras opciones JVM.
- **Directorio de inicio**: directorio de trabajo para la aplicación.

Después de la configuración, tu aplicación Swing será accesible en `http://localhost:8080/[app-name]/`.

### Configuración de CORS {#cors-configuration}

Al embeber Webswing en una aplicación webforJ que se ejecuta en un puerto o dominio diferente, debes configurar el intercambio de recursos de origen cruzado (CORS) en Webswing. Esto permite que el navegador cargue contenido de Webswing desde dentro de tu página webforJ.

En la consola de administración de Webswing, navega a la configuración de tu aplicación y establece:

- **Orígenes permitidos**: agrega el origen de tu aplicación webforJ (por ejemplo, `http://localhost:8090` o `*` para desarrollo).

Esta configuración corresponde a la opción `allowedCorsOrigins` en la configuración de la aplicación de Webswing.

## Integración con webforJ {#webforj-integration}

Una vez que tu servidor Webswing esté en funcionamiento con tu aplicación Swing configurada y CORS habilitado, puedes integrarlo en tu aplicación webforJ.

### Agregar dependencia {#add-dependency}

La integración de Webswing depende del módulo de integración Webswing de webforJ, que proporciona el componente `WebswingConnector` y clases relacionadas. Agrega lo siguiente a tu archivo `pom.xml`:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### Implementación básica {#basic-implementation}

Crea una vista que embeba tu aplicación Swing usando el `WebswingConnector`:

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private WebswingConnector connector;

  public SwingAppView() {
    // Inicializa el conector con la URL de tu aplicación Webswing
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Establece las dimensiones de visualización
    connector.setSize("100%", "600px");

    // Agrega al contenedor de vista
    self.add(connector);
  }
}
```

El conector establece automáticamente una conexión con el servidor Webswing cuando se agrega al DOM. La interfaz de usuario de la aplicación Swing se renderiza dentro del componente del conector.

## Opciones de configuración {#configuration-options}

La clase `WebswingOptions` te permite personalizar el comportamiento del conector. Por defecto, el conector comienza automáticamente cuando se crea y utiliza configuraciones de conexión estándar. Puedes modificar este comportamiento creando una instancia de `WebswingOptions` y aplicándola al conector.

Por ejemplo, para ocultar el botón de cierre de sesión en un entorno de producción donde gestionas la autenticación a través de tu aplicación webforJ:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Oculta el botón de cerrar sesión

connector.setOptions(options);
```

O si necesitas control manual sobre cuándo se inicia la conexión:

```java
// Crea el conector sin auto-inicio
WebswingConnector connector = new WebswingConnector(url, false);

// Configura e inicia cuando estés listo
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Las opciones cubren la gestión de conexiones, autenticación, depuración y monitoreo.
