---
title: Standard webforJ setup
sidebar_position: 35
description: Assemble an MCP server and register webforJ MCP Apps in a standard servlet-based webforJ application.
---

The standard setup creates the MCP server, registers its servlet, and installs the webforJ support itself.

:::tip[When to use this setup]

Use the standard servlet setup when the app doesn't use Spring Boot. For Spring Boot apps, use the [Spring Boot setup](./spring), which publishes routed views through Spring AI automatically.
:::


:::warning[BBj Services isn't supported]

MCP Apps need control of the app's servlet context to install the cross-origin filter, cookie settings, OAuth 2.0 discovery handling, and the other embed support the host needs. An app deployed through BBj Services can't initiate that servlet setup. Deploy the app in a servlet container that the app controls instead.
:::

## Add the MCP server dependencies {#add-the-dependencies}

Add the MCP Apps module beside the existing webforJ dependency. It provides the webforJ contribution and the MCP SDK used to assemble the server.

The initializer also implements `ServletContainerInitializer` and uses other Jakarta Servlet types. Add the Servlet API with `provided` scope so those types are available at compile time without packaging a second servlet implementation. The servlet container, such as Jetty, supplies them at runtime.

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

## Add the initializer {#add-the-initializer}

The initializer scans the package containing the routed views, contributes their tools and UI resources to an MCP server, and mounts Streamable HTTP at `/mcp`.

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

`McpAppContribution.ofPackages` creates both collections registered on the server. `getToolSpecifications()` contains the tools generated from the routed `@McpApp` views. `getResourceSpecifications()` contains the generated `ui://webforj/...` resources that MCP clients read to render those views. Registering the tools without their UI resources exposes calls that the client can't display.

The request context extractor lets webforJ observe the public origin when `webforj.origin` isn't set. The final `install` call adds the app-resource policy, cross-origin handling, session-cookie settings, OAuth 2.0 discovery handling, and favicon support. A server that publishes the tools but skips this call can expose a resource that the client can't run correctly.

## Register the initializer {#register-the-initializer}

Register the initializer with Java's service loader in `src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer`:

```text
com.example.inventory.InventoryMcpServerInitializer
```

The servlet container loads this class during app startup. Keep the fully qualified class name in the service file synchronized with the initializer package.

## Configure the deployment {#configure-the-deployment}

Standard deployments read MCP App settings from `webforj.conf`. For example:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
```

The minimal server can now be checked with any of the clients described in [Test an MCP App](./testing). [MCP App configuration](./configuration) explains public and client origins when the deployment needs more than the local defaults.
