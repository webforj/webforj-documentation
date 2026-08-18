---
title: Spring Boot MCP Apps
sidebar_position: 5
description: >-
  Build and publish a routed webforJ view as an MCP App with Spring Boot and
  Spring AI.
_i18n_hash: c8aedcd8d0981805ce4fce3b338542c0
---
La integración de Spring Boot publica vistas de webforJ enrutadas a través de un servidor Spring AI MCP. Úsalo cuando una aplicación de Spring Boot necesite exponer una vista interactiva a un cliente MCP, la aplicación de IA que se conecta al servidor.

:::tip[¿No estás usando Spring Boot?]

Para una aplicación webforJ basada en servlets, utiliza la [configuración estándar de webforJ](./without-spring) para ensamblar y registrar el servidor MCP tú mismo.
:::

## Agregar las dependencias {#add-the-dependencies}

Importa el bill of materials de webforJ para que los módulos de webforJ usen una versión. Luego, añade el iniciador de Spring Boot, el módulo de MCP Apps y el iniciador de servidor Spring AI WebMVC MCP.

```xml
<properties>
  <maven.compiler.release>21</maven.compiler.release>
  <webforj.version>26.02</webforj.version>
  <spring-ai.version>2.0.0</spring-ai.version>
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
    <artifactId>webforj-spring-boot-starter</artifactId>
  </dependency>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-mcp-apps</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
    <version>${spring-ai.version}</version>
  </dependency>
</dependencies>
```

## Configurar el punto final de MCP {#configure-the-mcp-endpoint}

Configura Spring AI para usar HTTP Streamable en `src/main/resources/application.properties`:

```Ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
```

Spring AI sirve el transporte en `/mcp` por defecto. webforJ deja esa ruta y las rutas de descubrimiento de OAuth 2.0 a Spring, en lugar de tratarlas como rutas de interfaz de usuario. El origen local permite que las URL de recursos y componentes generados apunten de regreso a la aplicación en ejecución. Consulta la [configuración de la aplicación MCP](./configuration) cuando un proxy o túnel le dé a la aplicación un origen público diferente.

## Agregar una aplicación enrutada {#add-a-routed-app}

Crea una vista enrutada normal y añade `@McpApp`. La descripción ayuda a la IA a determinar cuándo la herramienta es útil. El nombre explícito mantiene el nombre de la herramienta estable si la ruta cambia.

```java
package com.example.inventory;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.mcp.annotation.McpApp;
import com.webforj.router.annotation.Route;

@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "Muestra el inventario actual de un almacén.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.add(new H1("Inventario"));
  }
}
```

La vista aún funciona en `/inventory` en un navegador. La auto-configuración de Spring publica una herramienta MCP `inventory` y un recurso de interfaz de usuario que abre esta ruta, e instala los filtros de servlet y la configuración de sesión necesarios para integrar webforJ.

## Iniciar el servidor {#start-the-server}

Inicia el servidor utilizando el flujo de trabajo normal de ejecución del proyecto. Con el puerto por defecto, el punto final de MCP es:

```text
http://localhost:8080/mcp
```

[Prueba la vista mínima publicada](./testing) con un cliente local o remoto. Consulta [abrir entradas](./opening-apps) para argumentos estructurados y [configuración de la aplicación MCP](./configuration) para orígenes públicos y recursos externos.
