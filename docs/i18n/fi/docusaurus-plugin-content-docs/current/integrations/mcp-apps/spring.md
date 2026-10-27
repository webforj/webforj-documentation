---
title: Spring Boot MCP-sovellukset
sidebar_position: 5
description: >-
  Build and publish a routed webforJ view as an MCP App with Spring Boot and
  Spring AI.
_i18n_hash: c8aedcd8d0981805ce4fce3b338542c0
---
Spring Boot -integraatio julkaisee reititettyjä webforJ-näkymiä Spring AI MCP -palvelimen kautta. Käytä tätä, kun Spring Boot -sovelluksen tarvitsee altistaa interaktiivinen näkymä MCP-asiakkaalle, AI-sovellukselle, joka yhdistää palvelimeen.

:::tip[Etkö käytä Spring Bootia?]

Servlet-pohjaisessa webforJ-sovelluksessa käytä [vakiota webforJ-asetusta](./without-spring) kootaaksesi ja rekisteröidäksesi MCP-palvelimen itse.
:::

## Lisää riippuvuudet {#add-the-dependencies}

Tuo webforJ:n materiaaliluettelo niin, että webforJ-moduulit käyttävät yhtä versiota. Lisää sitten Spring Boot -aloitus, MCP Apps -moduuli ja Spring AI WebMVC MCP -palvelimen aloitus.

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

## Määritä MCP-päätepiste {#configure-the-mcp-endpoint}

Aseta Spring AI käyttämään Streamable HTTP:tä tiedostossa `src/main/resources/application.properties`:

```Ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
```

Spring AI palvelee siirtoa oletusarvoisesti polussa `/mcp`. webforJ jättää tämän polun ja OAuth 2.0 -löydöspolut Springin hoidettaviksi sen sijaan, että käsittelisi niitä UI-reitteinä. Paikallinen alkuperä mahdollistaa, että luotu sovellusresurssi ja komponenttien URL-osoitteet osoittavat takaisin käynnissä olevaan sovellukseen. Katso [MCP App -asetukset](./configuration), kun proxyn tai tunnelin avulla sovellukselle annetaan erilainen julkinen alkuperä.

## Lisää reititetty sovellus {#add-a-routed-app}

Luo normaali reititetty näkymä ja lisää `@McpApp`. Kuvaus auttaa AI:ta määrittämään, milloin työkalu on hyödyllinen. Ilmaistu nimi pitää työkalun nimen vakaana, jos reitti muuttuu.

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
    description = "Näyttää varaston nykyisen inventaarion.",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.add(new H1("Inventaario"));
  }
}
```

Näkymä toimii edelleen polussa `/inventory` selaimessa. Springin automaattinen konfigurointi julkaisee `inventory` MCP -työkalun ja UI-resurssin, joka avaa tämän reitin, ja asentaa tarvittavat servlet-suodattimet ja istuntoasetukset webforJ:n upottamiseksi.

## Käynnistä palvelin {#start-the-server}

Käynnistä palvelin käyttämällä projektin normaalia ajotyöskentelyä. Oletusportilla MCP-päätepiste on:

```text
http://localhost:8080/mcp
```

[testaa minimaalinen julkaistu näkymä](./testing) paikallisella tai etäasiakkaalla. Katso [syötteen avaaminen](./opening-apps) rakenteellisia argumentteja varten ja [MCP App -asetukset](./configuration) julkisille alkuperille ja ulkoisille resursseille.
