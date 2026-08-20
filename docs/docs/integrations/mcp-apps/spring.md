---
title: Spring Boot MCP Apps
sidebar_position: 5
description: Build and publish a routed webforJ view as an MCP App with Spring Boot and Spring AI.
---

The Spring Boot integration publishes routed webforJ views through a Spring AI MCP server. Use it when a Spring Boot app needs to expose an interactive view to an MCP client, the AI app that connects to the server.

:::tip[Not using Spring Boot?]

For a servlet-based webforJ app, use the [standard webforJ setup](./without-spring) to assemble and register the MCP server yourself.
:::

## Add the dependencies {#add-the-dependencies}

Import the webforJ bill of materials so the webforJ modules use one version. Then add the Spring Boot starter, MCP Apps module, and Spring AI WebMVC MCP server starter.

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

## Configure the MCP endpoint {#configure-the-mcp-endpoint}

Set Spring AI to use Streamable HTTP in `src/main/resources/application.properties`:

```Ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
```

Spring AI serves the transport at `/mcp` by default. webforJ leaves that path and the OAuth 2.0 discovery paths to Spring, instead of treating them as UI routes. The local origin lets the generated app resource and component URLs point back to the running app. See [MCP App configuration](./configuration) when a proxy or tunnel gives the app a different public origin.

## Add a routed app {#add-a-routed-app}

Create a normal routed view and add `@McpApp`. The description helps the AI determine when the tool is useful. The explicit name keeps the tool name stable if the route changes.

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
    description = "Shows the current inventory for a warehouse.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.add(new H1("Inventory"));
  }
}
```

The view still works at `/inventory` in a browser. Spring auto-configuration publishes an `inventory` MCP tool and a UI resource that opens this route, and installs the servlet filters and session settings needed to embed webforJ.

## Start the server {#start-the-server}

Start the server using the project's normal run workflow. With the default port, the MCP endpoint is:

```text
http://localhost:8080/mcp
```

[Test the minimal published view](./testing) with a local or remote client. See [opening input](./opening-apps) for structured arguments and [MCP App configuration](./configuration) for public origins and external resources.
