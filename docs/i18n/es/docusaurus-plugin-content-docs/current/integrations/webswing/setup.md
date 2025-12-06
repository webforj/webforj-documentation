---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: e3af6f7983bbd6ed7db57428412466c8
---
Integrar Webswing con webforJ implica dos componentes: el servidor Webswing que aloja tu aplicación Swing y el componente `WebswingConnector` en tu aplicación webforJ que lo incrusta.

## Prerrequisitos {#prerequisites}

Antes de comenzar, asegúrate de tener los siguientes prerrequisitos:

- **Aplicación de escritorio Java**: una aplicación Swing, JavaFX o SWT empaquetada como un archivo JAR
- **Servidor Webswing**: descarga desde [webswing.org](https://webswing.org)
- **versión webforJ `25.10` o superior**: requerida para el soporte de `WebswingConnector`

## Resumen de la arquitectura {#architecture-overview}

La arquitectura de integración consta de:

1. **Servidor Webswing**: ejecuta tu aplicación Swing, captura el renderizado de la GUI y maneja la entrada del usuario
2. **Aplicación webforJ**: aloja tu aplicación web con el `WebswingConnector` incrustado
3. **Cliente del navegador**: muestra tanto la UI de webforJ como la aplicación Swing incrustada

:::important Configuración de puertos
Webswing y webforJ deben ejecutarse en puertos diferentes para evitar conflictos. Tanto webforJ como Webswing normalmente se ejecutan en el puerto `8080`. Debes cambiar el puerto de Webswing o el puerto de webforJ.
:::

## Configuración del servidor Webswing {#webswing-server-setup}

### Instalación y puesta en marcha {#installation-and-startup}

1. **Descargar Webswing** desde el [sitio web oficial](https://www.webswing.org/en/downloads)
2. **Extraer el archivo** a tu ubicación preferida (por ejemplo, `/opt/webswing` o `C:\webswing`)
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

### Configuración de la aplicación {#application-configuration}

Una vez que el servidor esté en funcionamiento, accede a la consola de administración en `http://localhost:8080/admin` para agregar y configurar tu aplicación Swing.

En la consola de administración, configura:

- **Nombre de la aplicación**: forma parte de la ruta de la URL (por ejemplo, `myapp` → `http://localhost:8080/myapp/`)
- **Clase principal**: el punto de entrada de tu aplicación Swing
- **Classpath**: ruta a tu archivo JAR de la aplicación y dependencias
- **Argumentos de la JVM**: configuraciones de memoria, propiedades del sistema y otras opciones de la JVM
- **Directorio de inicio**: directorio de trabajo para la aplicación

Después de la configuración, tu aplicación Swing estará accesible en `http://localhost:8080/[nombre-de-la-aplicación]/`

### Configuración de CORS {#cors-configuration}

Al incrustar Webswing en una aplicación webforJ que se ejecute en un puerto o dominio diferente, debes configurar el Intercambio de Recursos de Origen Cruzado (CORS) en Webswing. Esto permite que el navegador cargue contenido de Webswing desde dentro de tu página webforJ.

En la consola de administración de Webswing, navega a la configuración de tu aplicación y establece:

- **Orígenes permitidos**: Agrega el origen de tu aplicación webforJ (por ejemplo, `http://localhost:8090` o `*` para desarrollo)

Esta configuración corresponde a la opción `allowedCorsOrigins` en la configuración de la aplicación de Webswing.


## Integración con webforJ {#webforj-integration}

Una vez que tu servidor Webswing esté en funcionamiento con tu aplicación Swing configurada y CORS habilitado, puedes integrarlo en tu aplicación webforJ.

### Agregar dependencia {#add-dependency}

Agrega el módulo de integración de Webswing a tu proyecto webforJ. Esto proporciona el componente `WebswingConnector` y clases relacionadas.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### Implementación básica {#basic-implementation}

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
    // Inicializar el conector con la URL de tu aplicación Webswing
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Establecer las dimensiones de visualización
    connector.setSize("100%", "600px");

    // Agregar al contenedor de la vista
    getBoundComponent().add(connector);
  }
}
```

El conector establece automáticamente una conexión con el servidor Webswing cuando se agrega al DOM. La UI de la aplicación Swing se renderiza dentro del componente conector.

## Opciones de configuración {#configuration-options}

La clase `WebswingOptions` te permite personalizar el comportamiento del conector. Por defecto, el conector se inicia automáticamente cuando se crea y usa configuraciones de conexión estándar. Puedes modificar este comportamiento creando una instancia de `WebswingOptions` y aplicándola al conector.

Por ejemplo, para ocultar el botón de cierre de sesión en un entorno de producción donde gestionas la autenticación a través de tu aplicación webforJ:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Ocultar el botón de cierre de sesión

connector.setOptions(options);
```

O si necesitas control manual sobre cuándo empieza la conexión:

```java
// Crear conector sin inicio automático
WebswingConnector connector = new WebswingConnector(url, false);

// Configurar y empezar cuando esté listo
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

Las opciones cubren la gestión de conexiones, autenticación, depuración y monitoreo.
