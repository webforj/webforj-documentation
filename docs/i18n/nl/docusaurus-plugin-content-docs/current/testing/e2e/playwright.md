---
sidebar_position: 2
title: Testen met Playwright
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: 3f815af0ad7916fdd5a53d6178846c6d
---
Deze documentatie beschrijft het proces voor het testen van webforJ-applicaties met Playwright, met specifieke aandacht voor `HelloWorldView` uit `webforj-archetype-hello-world`.

:::info Basisprincipes van de app
Lees het gedeelte [Inleiding tot de basisprincipes van de app](../../introduction/basics) voor meer informatie over `webforj-archetype-hello-world`.
:::

## Vereisten {#prerequisites}

Zorg voordat je de Playwright-tests schrijft en uitvoert voor het volgende:
- De webforJ-app is correct ingesteld en draait op je lokale server.
- Je hebt het volgende geïnstalleerd:
  - Playwright Java-bindings.
  - Een compatibele browser (Playwright kan tijdens de installatie automatisch browsers installeren).
  - Maven voor projectafhankelijkheden.

## Maven-configuratie {#maven-configuration}

Voeg de benodigde afhankelijkheden voor Playwright toe aan je `pom.xml`:

```xml title="pom.xml"
<dependencies>
  <dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.49.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Testvoorbeeld: `HelloWorldView` {#testing-example-helloworldview}

De volgende code toont een test op basis van Playwright voor de component `HelloWorldView`.

```java title="HelloWorldViewTest.java"
package com.example.views;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

class HelloWorldViewTest {

  static Playwright playwright = Playwright.create();
  Browser browser;
  Page page;
  String port = System.getProperty("server.port", "8080");

  @BeforeEach
  void setUp() {
    browser = playwright.chromium().launch();
    page = browser.newPage();
    page.navigate("http://localhost:" + port + "/");
  }

  @Test
  void shouldClickButton() {
    page.locator("input").fill("webforJ");
    page.getByText("Say Hello").click();

    assertThat(page.locator("dwc-toast").first())
        .containsText("Welcome to webforJ Starter webforJ!");
  }
}
```

### Belangrijke stappen {#key-steps}

1. **Playwright initialiseren**:
   - Maak een `Playwright`-instantie aan.
   - Start een browserinstantie met `playwright.chromium().launch()`.

2. **De testomgeving instellen**:
   - Open een nieuwe browserpagina met `browser.newPage()`.
   - Navigeer met de methode `navigate` naar de pagina `HelloWorldView`.

3. **Interactie met elementen**:
   - Gebruik [locators van Playwright](https://playwright.dev/java/docs/api/class-locator) om met DOM-elementen te werken.
   - Vul invoervelden in met `locator("input").fill()` en voer acties uit met `getByText("Say Hello").click()`.

4. **Controles**:
   - Controleer het weergegeven toastbericht met `PlaywrightAssertions.assertThat()`.

5. **Opruimen**:
   - Playwright ruimt de browser automatisch op wanneer de test is voltooid. Je kunt de browser ook handmatig sluiten met `browser.close()`.

### Tests uitvoeren {#running-tests}

1. Start de webforJ-server:
   ```bash
   mvn
   ```

2. Voer de testgevallen uit:
   ```bash
   mvn test
   ```

## Verwacht gedrag {#expected-behavior}

- Wanneer je `http://localhost:<port>/` bezoekt, wordt de pagina `HelloWorldView` geladen.
- Voer webforJ in het tekstveld in en klik op de knop `Say Hello`.
- Er verschijnt een toastbericht met de tekst: `Welcome to webforJ Starter webforJ!`.
