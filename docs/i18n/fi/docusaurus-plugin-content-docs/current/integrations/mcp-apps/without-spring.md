---
title: Standardi webforJ-asennus
sidebar_position: 35
description: >-
  Assemble an MCP server and register webforJ MCP Apps in a standard
  servlet-based webforJ application.
_i18n_hash: 0d4b2a9da02c480934e5527e0c6d4a44
---
Tavanomainen asennus luo MCP-palvelimen, rekisteröi sen servletin ja asentaa webforJ-tuen itsessään.

:::tip[Milloin käyttää tätä asetusta]

Käytä tavanomaista servlet-asennusta, kun sovellus ei käytä Spring Bootia. Spring Boot -sovelluksille käytä [Spring Boot -asetusta](./spring), joka julkaisee reititetyt näkymät Spring AI:n kautta automaattisesti.
:::


:::warning[BBj-palveluja ei tueta]

MCP-sovellukset tarvitsevat kontrollia sovelluksen servlet-kontekstiin asentaakseen ristiin alkuperäisen suodattimen, evästeasetukset, OAuth 2.0 -tunnistautumisen käsittelyn ja muut isäntätyypit tarvitsevat sisällytyksen tuen. BBj-palvelujen kautta käyttöön otettu sovellus ei voi aloittaa tätä servlet-asennusta. Ota sovellus käyttöön servlet-kontainerissa, jota sovellus hallitsee.
:::

## Lisää MCP-palvelimen riippuvuudet {#add-the-dependencies}

Lisää MCP Apps -moduuli olemassa olevaan webforJ-riippuvuuteen. Se tarjoaa webforJ:in kontribuution ja MCP SDK:n, jota käytetään palvelimen kokoamiseen.

Alustaja toteuttaa myös `ServletContainerInitializer`-liittymän ja käyttää muita Jakarta Servlet -tyyppejä. Lisää Servlet API `provided`-alueella, jotta nämä tyypit ovat käytettävissä käännösaikana ilman toisen servlet-toteutuksen pakkaamista. Servlet-kontaineri, kuten Jetty, tarjoaa ne ajonaikana.

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

## Lisää alustaja {#add-the-initializer}

Alustaja skannaa reititettyjen näkymien sisältävän paketin, tuo niiden työkalut ja UI-resurssit MCP-palvelimelle ja asettaa Streamable HTTP:n kohtaan `/mcp`.

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

`McpAppContribution.ofPackages` luo molemmat kokoelmat, jotka on rekisteröity palvelimelle. `getToolSpecifications()` sisältää työkalut, jotka on luotu reititetystä `@McpApp`-näkymistä. `getResourceSpecifications()` sisältää luodut `ui://webforj/...` resurssit, joita MCP-asiakkaat lukevat niiden näkymien renderöimiseksi. Työkalujen rekisteröinti ilman niiden UI-resursseja altistaa kutsuja, jota asiakas ei voi näyttää.

Pyyntökonteksti-tiedonkeräin antaa webforJ:n tarkkailla julkista alkuperää, kun `webforj.origin` ei ole asetettu. Viimeinen `install`-kutsu lisää sovellusresurssipolitiikan, ristiin alkuperäisen käsittelyn, istunto-evästeasetukset, OAuth 2.0 -tunnistautumisen käsittelyn ja favicon-tuen. Palvelin, joka julkaisee työkalut, mutta ohittaa tämän kutsun, voi altistaa resurssin, jota asiakas ei voi suorittaa oikein.

## Rekisteröi alustaja {#register-the-initializer}

Rekisteröi alustaja Java:n palvelu-luetteloon tiedostoon `src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer`:

```text
com.example.inventory.InventoryMcpServerInitializer
```

Servlet-kontaineri lataa tämän luokan sovelluksen käynnistyksen aikana. Pidä täysin määritellyn luokan nimen palvelutiedostossa synkronoituna alustajan paketin kanssa.

## Määritä käyttöönotto {#configure-the-deployment}

Tavanomaiset käyttöönotot lukevat MCP-sovelluksen asetuksia tiedostosta `webforj.conf`. Esimerkiksi:

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
```

Minimaalista palvelinta voidaan nyt tarkistaa millä tahansa yllä kuvatuista asiakkaista [Testaa MCP-sovellus](./testing). [MCP-sovelluksen kokoonpano](./configuration) selittää julkiset ja asiakasalkuperät, kun käyttöönotto tarvitsee enemmän kuin paikalliset oletukset.
