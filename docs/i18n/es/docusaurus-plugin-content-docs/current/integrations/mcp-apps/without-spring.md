---
title: Configuración estándar de webforJ
sidebar_position: 35
description: >-
  Assemble an MCP server and register webforJ MCP Apps in a standard
  servlet-based webforJ application.
_i18n_hash: 0d4b2a9da02c480934e5527e0c6d4a44
---
La configuración estándar crea el servidor MCP, registra su servlet e instala el soporte de webforJ.

:::tip[Cuándo usar esta configuración]

Utiliza la configuración estándar del servlet cuando la aplicación no usa Spring Boot. Para aplicaciones de Spring Boot, utiliza la [configuración de Spring Boot](./spring), que publica vistas enrutadas a través de Spring AI automáticamente.
:::

:::warning[BBj Services no es compatible]

Las aplicaciones MCP necesitan control sobre el contexto del servlet de la aplicación para instalar el filtro de origen cruzado, la configuración de cookies, el manejo de descubrimiento de OAuth 2.0 y el otro soporte embebido que necesita el host. Una aplicación desplegada a través de BBj Services no puede iniciar esa configuración de servlet. Despliega la aplicación en un contenedor de servlet que la aplicación controle en su lugar.
:::

## Agregar las dependencias del servidor MCP {#add-the-dependencies}

Agrega el módulo de aplicaciones MCP junto a la dependencia existente de webforJ. Proporciona la contribución de webforJ y el SDK de MCP utilizado para ensamblar el servidor.

El inicializador también implementa `ServletContainerInitializer` y utiliza otros tipos de Jakarta Servlet. Agrega la API Servlet con el alcance `provided` para que esos tipos estén disponibles en tiempo de compilación sin empaquetar una segunda implementación de servlet. El contenedor de servlets, como Jetty, los proporciona en tiempo de ejecución.

```xml
<properties>
  <webforj.version>26.02</webforj.version>
</properties>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-bom</artifactId>
      <version>${webforj.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-mcp-apps</artifactId>
  </dependency>
  <dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.1.0</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

## Agregar el inicializador {#add-the-initializer}

El inicializador escanea el paquete que contiene las vistas enrutadas, contribuye sus herramientas y recursos de UI a un servidor MCP, y monta Streamable HTTP en `/mcp`.

```java
package com.example.inventory;

import com.webforj.mcp.McpAppContribution;
import com.webforj.mcp.McpAppOptions;
import com.webforj.mcp.McpAppServletPath;
import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import java.util.Set;

public class InventoryMcpServerInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(Set<Class<?>> classes, ServletContext context) {
    McpAppOptions options = McpAppOptions.load();
    McpAppContribution contribution = McpAppContribution.ofPackages(
        new String[] {"com.example.inventory"}, McpAppServletPath.of(context));
    contribution.getOrigin().configure(options.getOrigin());

    HttpServletStreamableServerTransportProvider transport =
        HttpServletStreamableServerTransportProvider.builder().mcpEndpoint("/mcp")
            .contextExtractor(request -> {
              contribution.getOrigin().observe(request);
              return McpTransportContext.EMPTY;
            }).build();

    McpSyncServer server = McpServer.sync(transport)
        .serverInfo("inventory", "1.0.0")
        .capabilities(ServerCapabilities.builder().tools(true).resources(false, true).build())
        .tools(contribution.getToolSpecifications())
        .resources(contribution.getResourceSpecifications())
        .build();

    ServletRegistration.Dynamic registration = context.addServlet("mcpServlet", transport);
    registration.setAsyncSupported(true);
    registration.addMapping("/mcp/*");

    context.addListener(new ServletContextListener() {
      @Override
      public void contextDestroyed(ServletContextEvent event) {
        server.close();
      }
    });

    contribution.install(context, options);
  }
}
```

`McpAppContribution.ofPackages` crea ambas colecciones registradas en el servidor. `getToolSpecifications()` contiene las herramientas generadas a partir de las vistas enrutadas `@McpApp`. `getResourceSpecifications()` contiene los recursos generados `ui://webforj/...` que los clientes MCP leen para renderizar esas vistas. Registrar las herramientas sin sus recursos de UI expone llamadas que el cliente no puede mostrar.

ElExtractor de contexto de solicitud permite que webforJ observe el origen público cuando `webforj.origin` no está configurado. La llamada final `install` agrega la política de recurso de la aplicación, el manejo de origen cruzado, la configuración de cookies de sesión, el manejo de descubrimiento de OAuth 2.0 y el soporte de favicon. Un servidor que publica las herramientas pero omite esta llamada puede exponer un recurso que el cliente no puede ejecutar correctamente.

## Registrar el inicializador {#register-the-initializer}

Registra el inicializador con el cargador de servicios de Java en `src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer`:

```text
com.example.inventory.InventoryMcpServerInitializer
```

El contenedor de servlet carga esta clase durante el inicio de la aplicación. Mantén el nombre de la clase completamente calificada en el archivo de servicio sincronizado con el paquete del inicializador.

## Configurar el despliegue {#configure-the-deployment}

Los despliegues estándar leen configuraciones de la aplicación MCP desde `webforj.conf`. Por ejemplo:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
```

El servidor mínimo ahora se puede verificar con cualquiera de los clientes descritos en [Probar una aplicación MCP](./testing). La [configuración de la aplicación MCP](./configuration) explica los orígenes públicos y de cliente cuando el despliegue necesita más que los valores predeterminados locales.
