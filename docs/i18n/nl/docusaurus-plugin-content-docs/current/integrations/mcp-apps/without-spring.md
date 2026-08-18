---
title: Standard webforJ setup
sidebar_position: 35
description: >-
  Assemble an MCP server and register webforJ MCP Apps in a standard
  servlet-based webforJ application.
_i18n_hash: 0d4b2a9da02c480934e5527e0c6d4a44
---
De standaardconfiguratie maakt de MCP-server, registreert zijn servlet en installeert de webforJ-ondersteuning zelf.

:::tip[Wanneer deze configuratie te gebruiken]

Gebruik de standaard servlet-configuratie wanneer de app geen gebruik maakt van Spring Boot. Voor Spring Boot-apps, gebruik de [Spring Boot configuratie](./spring), die automatisch gerouteerde weergaven publiceert via Spring AI.
:::


:::warning[BBj Services wordt niet ondersteund]

MCP-apps hebben controle over de servletcontext van de app nodig om de cross-origin filter, cookie-instellingen, OAuth 2.0 discover-handling en de andere embed-ondersteuning te installeren die de host nodig heeft. Een app die wordt gedeployd via BBj Services kan die servlet-configuratie niet initiëren. Deploy de app in een servletcontainer die de app controleert in plaats daarvan.
:::

## Voeg de MCP-server afhankelijkheden toe {#add-the-dependencies}

Voeg de MCP Apps module toe naast de bestaande webforJ afhankelijkheid. Het biedt de webforJ-bijdrage en de MCP SDK die wordt gebruikt om de server samen te stellen.

De initializer implementeert ook `ServletContainerInitializer` en gebruikt andere Jakarta Servlet-typen. Voeg de Servlet API toe met `provided` scope zodat die typen beschikbaar zijn tijdens de compileertijd zonder een tweede servletimplementatie te verpakken. De servletcontainer, zoals Jetty, levert ze tijdens runtime.

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

## Voeg de initializer toe {#add-the-initializer}

De initializer scant het pakket met gerouteerde weergaven, draagt hun tools en UI-bronnen bij aan een MCP-server en monteert Streamable HTTP op `/mcp`.

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

`McpAppContribution.ofPackages` maakt beide collecties die op de server zijn geregistreerd. `getToolSpecifications()` bevat de tools die zijn gegenereerd vanuit de gerouteerde `@McpApp`-weergaven. `getResourceSpecifications()` bevat de gegenereerde `ui://webforj/...` bronnen die MCP-clients lezen om die weergaven weer te geven. Registreren van de tools zonder hun UI-bronnen blootlegt oproepen die de client niet kan weergeven.

De request context extractor laat webforJ de openbare oorsprong observeren wanneer `webforj.origin` niet is ingesteld. De laatste `install`-aanroep voegt het app-resourcebeleid, cross-origin handling, sessie-cookie-instellingen, OAuth 2.0 discover-handling, en favicon-ondersteuning toe. Een server die de tools publiceert maar deze aanroep overslaat, kan een bron blootleggen die de client niet correct kan uitvoeren.

## Registreer de initializer {#register-the-initializer}

Registreer de initializer met de service loader van Java in `src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer`:

```text
com.example.inventory.InventoryMcpServerInitializer
```

De servletcontainer laadt deze klasse tijdens de opstart van de app. Houd de volledig gekwalificeerde klasnaam in het servicebestand gesynchroniseerd met het initializer-pakket.

## Configureer de implementatie {#configure-the-deployment}

Standaardimplementaties lezen MCP App-instellingen vanuit `webforj.conf`. Bijvoorbeeld:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
```

De minimale server kan nu worden gecontroleerd met een van de clients die zijn beschreven in [Test een MCP App](./testing). [MCP App configuratie](./configuration) legt publieke en cliënt-oorsprongen uit wanneer de implementatie meer nodig heeft dan de lokale standaardinstellingen.
