---
title: Standard webforJ setup
sidebar_position: 35
description: >-
  Assemble an MCP server and register webforJ MCP Apps in a standard
  servlet-based webforJ application.
_i18n_hash: 0d4b2a9da02c480934e5527e0c6d4a44
---
Die Standardkonfiguration erstellt den MCP-Server, registriert seinen Servlet und installiert die Unterstützung für webforJ selbst.

:::tip[Wann diese Konfiguration verwenden]

Verwenden Sie die Standard-Servlet-Konfiguration, wenn die App kein Spring Boot verwendet. Für Spring Boot-Apps verwenden Sie die [Spring Boot-Konfiguration](./spring), die geroutete Ansichten automatisch über Spring AI veröffentlicht.
:::

:::warning[BBj-Dienste werden nicht unterstützt]

MCP-Apps benötigen Kontrolle über den Servlet-Kontext der App, um den Cross-Origin-Filter, Cookie-Einstellungen, die Handhabung der OAuth 2.0-Discovery und die andere Unterstützung zu installieren, die der Host benötigt. Eine App, die über BBj-Dienste bereitgestellt wird, kann diese Servlet-Konfiguration nicht initiieren. Stellen Sie die App stattdessen in einem Servlet-Container bereit, den die App kontrolliert.
:::

## Fügen Sie die MCP-Server-Abhängigkeiten hinzu {#add-the-dependencies}

Fügen Sie das MCP Apps-Modul neben der vorhandenen webforJ-Abhängigkeit hinzu. Es bietet den webforJ-Beitrag und das MCP SDK, das verwendet wird, um den Server zusammenzustellen.

Der Initialisierer implementiert auch `ServletContainerInitializer` und verwendet andere Jakarta Servlet-Typen. Fügen Sie die Servlet-API mit dem `provided`-Scope hinzu, damit diese Typen zur Compile-Zeit verfügbar sind, ohne eine zweite Servlet-Implementierung zu paketieren. Der Servlet-Container, wie Jetty, stellt sie zur Laufzeit bereit.

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

## Fügen Sie den Initialisierer hinzu {#add-the-initializer}

Der Initialisierer scannt das Paket, das die gerouteten Ansichten enthält, trägt deren Werkzeuge und UI-Ressourcen zu einem MCP-Server bei und montiert Streamable HTTP unter `/mcp`.

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

`McpAppContribution.ofPackages` erstellt beide Sammlungen, die auf dem Server registriert sind. `getToolSpecifications()` enthält die Werkzeuge, die aus den gerouteten `@McpApp`-Ansichten generiert wurden. `getResourceSpecifications()` enthält die generierten `ui://webforj/...`-Ressourcen, die von MCP-Clients gelesen werden, um diese Ansichten darzustellen. Das Registrieren der Werkzeuge ohne deren UI-Ressourcen offenbart Aufrufe, die der Client nicht anzeigen kann.

Der Anfragekontext-Extractor lässt webforJ die öffentliche Ursprung beobachten, wenn `webforj.origin` nicht gesetzt ist. Der letzte `install`-Aufruf fügt die App-Ressourcenrichtlinie, die Handhabung von Cross-Origin, die Sitzungs-Cookie-Einstellungen, die Handhabung von OAuth 2.0-Discovery und die Favicon-Unterstützung hinzu. Ein Server, der die Werkzeuge veröffentlicht, aber diesen Aufruf überspringt, kann eine Ressource offenbaren, die der Client nicht korrekt ausführen kann.

## Registrieren Sie den Initialisierer {#register-the-initializer}

Registrieren Sie den Initialisierer mit Java's Service-Loader in `src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer`:

```text
com.example.inventory.InventoryMcpServerInitializer
```

Der Servlet-Container lädt diese Klasse während des App-Starts. Halten Sie den vollqualifizierten Klassennamen in der Servicedatei mit dem Initialisierer-Paket synchronisiert.

## Konfigurieren Sie die Bereitstellung {#configure-the-deployment}

Standardbereitstellungen lesen die MCP-App-Einstellungen aus `webforj.conf`. Zum Beispiel:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
```

Der minimale Server kann jetzt mit einem der in [Test einer MCP-App](./testing) beschriebenen Clients überprüft werden. Die [MCP-App-Konfiguration](./configuration) erklärt öffentliche und Client-Ursprünge, wenn die Bereitstellung mehr als die lokalen Standardeinstellungen benötigt.
