---
title: Spring Boot MCP Apps
sidebar_position: 5
description: >-
  Build and publish a routed webforJ view as an MCP App with Spring Boot and
  Spring AI.
_i18n_hash: c8aedcd8d0981805ce4fce3b338542c0
---
Die Spring Boot-Integration veröffentlicht geroutete webforJ-Ansichten über einen Spring AI MCP-Server. Verwenden Sie es, wenn eine Spring Boot-Anwendung eine interaktive Ansicht für einen MCP-Client, die KI-Anwendung, die sich mit dem Server verbindet, bereitstellen muss.

:::tip[Nicht mit Spring Boot?]

Für eine servletbasierte webforJ-Anwendung verwenden Sie das [Standard-webforJ-Setup](./without-spring), um den MCP-Server selbst zusammenzustellen und zu registrieren.
:::

## Abhängigkeiten hinzufügen {#add-the-dependencies}

Importieren Sie das webforJ-Bill of Materials, damit die webforJ-Module eine Version verwenden. Fügen Sie dann den Spring Boot-Starter, das MCP Apps-Modul und den Spring AI WebMVC MCP-Server-Starter hinzu.

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

## Konfigurieren Sie den MCP-Endpunkt {#configure-the-mcp-endpoint}

Stellen Sie Spring AI so ein, dass es Streamable HTTP in `src/main/resources/application.properties` verwendet:

```Ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
```

Spring AI dient den Transport standardmäßig unter `/mcp`. webforJ überlässt diesen Pfad und die OAuth 2.0-Discovery-Pfade Spring, anstatt sie als UI-Routen zu behandeln. Der lokale Ursprung lässt die generierten Anwendungsressourcen- und Komponenten-URLs auf die laufende Anwendung verweisen. Weitere Informationen finden Sie in der [MCP App-Konfiguration](./configuration), wenn ein Proxy oder Tunnel der Anwendung einen anderen öffentlichen Ursprung gibt.

## Fügen Sie eine geroutete Anwendung hinzu {#add-a-routed-app}

Erstellen Sie eine normale geroutete Ansicht und fügen Sie `@McpApp` hinzu. Die Beschreibung hilft der KI dabei zu bestimmen, wann das Tool nützlich ist. Der explizite Name hält den Toolnamen stabil, falls sich die Route ändert.

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
    description = "Zeigt den aktuellen Bestand für ein Lager an.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.add(new H1("Bestand"));
  }
}
```

Die Ansicht funktioniert weiterhin unter `/inventory` in einem Browser. Die automatische Konfiguration von Spring veröffentlicht ein `inventory` MCP-Tool und eine UI-Ressource, die diese Route öffnet, und installiert die Servlet-Filter und Sitzungseinstellungen, die benötigt werden, um webforJ einzubetten.

## Starten Sie den Server {#start-the-server}

Starten Sie den Server mit dem normalen Ausführungsablauf des Projekts. Bei dem Standardport ist der MCP-Endpunkt:

```text
http://localhost:8080/mcp
```

[Testen Sie die minimal veröffentlichte Ansicht](./testing) mit einem lokalen oder entfernten Client. Weitere Informationen finden Sie unter [Eingaben öffnen](./opening-apps) für strukturierte Argumente und [MCP App-Konfiguration](./configuration) für öffentliche Ursprünge und externe Ressourcen.
