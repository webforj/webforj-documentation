---
title: Spring Boot MCP-apps
sidebar_position: 5
description: >-
  Build and publish a routed webforJ view as an MCP App with Spring Boot and
  Spring AI.
_i18n_hash: c8aedcd8d0981805ce4fce3b338542c0
---
De Spring Boot-integratie publiceert gerouteerde webforJ-weergaven via een Spring AI MCP-server. Gebruik het wanneer een Spring Boot-app een interactieve weergave moet blootstellen aan een MCP-client, de AI-app die verbinding maakt met de server.

:::tip[Niet met Spring Boot?]

Voor een servlet-gebaseerde webforJ-app, gebruik de [standaard webforJ-configuratie](./without-spring) om de MCP-server zelf samen te stellen en te registreren.
:::

## Voeg de afhankelijkheden toe {#add-the-dependencies}

Importeer de webforJ-bill of materials zodat de webforJ-modules één versie gebruiken. Voeg vervolgens de Spring Boot-starter, MCP Apps-module en Spring AI WebMVC MCP-serverstarter toe.

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

## Configureer de MCP-eindpunt {#configure-the-mcp-endpoint}

Stel Spring AI in om Streamable HTTP te gebruiken in `src/main/resources/application.properties`:

```Ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
```

Spring AI bedient het transport standaard op `/mcp`. webforJ laat dat pad en de OAuth 2.0-discoverypaden aan Spring over, in plaats van ze als UI-routes te behandelen. De lokale oorsprong stelt de gegenereerde app-bron- en component-URL's in staat om terug te wijzen naar de draaiende app. Zie [MCP App-configuratie](./configuration) wanneer een proxy of tunnel de app een andere publieke oorsprong geeft.

## Voeg een gerouteerde app toe {#add-a-routed-app}

Maak een normale gerouteerde weergave en voeg `@McpApp` toe. De beschrijving helpt de AI om te bepalen wanneer de tool nuttig is. De expliciete naam houdt de naam van de tool stabiel als de route verandert.

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
    description = "Toont de huidige inventaris voor een magazijn.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.add(new H1("Inventaris"));
  }
}
```

De weergave werkt nog steeds op `/inventory` in een browser. Spring-autoconfiguratie publiceert een `inventory` MCP-tool en een UI-resource die deze route opent, en installeert de servletfilters en sessie-instellingen die nodig zijn om webforJ in te sluiten.

## Start de server {#start-the-server}

Start de server met de normale workflow van het project. Met de standaardpoort is het MCP-eindpunt:

```text
http://localhost:8080/mcp
```

[Test de minimaal gepubliceerde weergave](./testing) met een lokale of externe client. Zie [invoer openen](./opening-apps) voor gestructureerde argumenten en [MCP App-configuratie](./configuration) voor publieke oorsprongen en externe bronnen.
