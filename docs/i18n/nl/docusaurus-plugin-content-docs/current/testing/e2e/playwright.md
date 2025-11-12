---
sidebar_position: 2
title: Testen met Playwright
_i18n_hash: dffe640edd9d7918a3c8bace8cf0bbe8
---
Deze documentatie beschrijft het proces voor het testen van webforJ-toepassingen met behulp van Playwright, met een specifieke focus op de `HelloWorldView` van de `webforj-archetype-hello-world`.

:::info Basis van de app
Om meer te leren over de `webforj-archetype-hello-world`, raadpleeg de sectie [Basis van de app](/docs/introduction/basics).
:::

## Vereisten {#prerequisites}

Voordat je de Playwright-tests schrijft en uitvoert, zorg voor het volgende:
- De webforJ-app is correct ingesteld en draait op je lokale server.
- Je hebt geïnstalleerd:
  - Playwright Java bindings.
  - Een compatibele browser (Playwright kan browsers automatisch installeren tijdens de setup).
  - Maven voor projectafhankelijkheden.

## Maven-configuratie {#maven-configuration}

Voeg de noodzakelijke afhankelijkheden toe in je `pom.xml` voor Playwright:

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

De volgende code demonstreert een Playwright gebaseerde test voor de `HelloWorldView` component.

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

1. **Initialiseer Playwright**:
   - Maak een `Playwright` instance.
   - Lanceer een browser instance met `playwright.chromium().launch()`.

2. **Stel Testomgeving in**:
   - Open een nieuwe browserpagina met `browser.newPage()`.
   - Navigeer naar de `HelloWorldView` pagina met de `navigate` methode.

3. **Interactie met Elementen**:
   - Gebruik [Playwright's locators](https://playwright.dev/java/docs/api/class-locator) om met DOM-elementen te interageren.
   - Vul invoervelden in met `locator("input").fill()` en trigger acties met `getByText("Say Hello").click()`.

4. **Assertions**:
   - Verifieer de weergegeven toastmelding met `PlaywrightAssertions.assertThat()`.

5. **Opruimen**:
   - Playwright beheert automatisch de opruiming van de browser wanneer de test is afgelopen. Voor handmatige opruiming kun je de browser sluiten met `browser.close()`.

### Tests uitvoeren {#running-tests}

1. Start de webforJ-server:
   ```bash
   mvn jetty:run
   ```

2. Voer de testgevallen uit:
   ```bash
   mvn test
   ```

## Verwacht gedrag {#expected-behavior}

- Bij het bezoeken van `http://localhost:<port>/` wordt de `HelloWorldView` pagina geladen.
- Vul webforJ in het tekstveld in en klik op de knop `Say Hello`.
- Een toastmelding zou moeten verschijnen met de tekst: `Welcome to webforJ Starter webforJ!`.
