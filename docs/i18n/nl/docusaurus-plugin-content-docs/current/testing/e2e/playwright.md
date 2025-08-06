---
sidebar_position: 2
title: Testing with Playwright
_i18n_hash: 43cec2eab876a8dc170f4fb69aaa8214
---
Deze documentatie beschrijft het proces voor het testen van webforJ-toepassingen met Playwright, met specifieke focus op de `HelloWorldView` van de `webforj-archetype-hello-world`.

:::info App Basics
Om meer te leren over de `webforj-archetype-hello-world`, verwijzen we naar de [App Basics Introduction](../../introduction/basics) sectie.
:::

## Vereisten {#prerequisites}

Voordat je de Playwright-tests schrijft en uitvoert, zorg ervoor dat het volgende is geregeld:
- De webforJ-app is correct ingesteld en draait op je lokale server.
- Je hebt het volgende geïnstalleerd:
  - Playwright Java bindings.
  - Een compatibele browser (Playwright kan automatisch browsers installeren tijdens de configuratie).
  - Maven voor projectafhankelijkheden.

## Maven configuratie {#maven-configuration}

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

De volgende code demonstreert een Playwright-gebaseerde test voor de `HelloWorldView` component.

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
   - Maak een `Playwright` instantie aan.
   - Start een browserinstantie met `playwright.chromium().launch()`.

2. **Stel de testomgeving in**:
   - Open een nieuwe browserpagina met `browser.newPage()`.
   - Navigeer naar de `HelloWorldView` pagina met de `navigate` methode.

3. **Interactie met elementen**:
   - Gebruik [Playwright's locators](https://playwright.dev/java/docs/api/class-locator) om interactie te hebben met DOM-elementen.
   - Vul invoervelden in met `locator("input").fill()` en voer acties uit met `getByText("Say Hello").click()`.

4. **Asserties**:
   - Verifieer de weergegeven toastboodschap met `PlaywrightAssertions.assertThat()`.

5. **Opruimen**:
   - Playwright beheert automatisch de browseropruiming wanneer de test is voltooid. Voor handmatige opruiming kun je de browser sluiten met `browser.close()`.

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
- Vul webforJ in het tekstveld en klik op de knop `Say Hello`.
- Een toastboodschap zou moeten verschijnen met de tekst: `Welcome to webforJ Starter webforJ!`.
