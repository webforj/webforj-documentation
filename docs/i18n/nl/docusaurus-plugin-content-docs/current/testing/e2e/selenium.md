---
sidebar_position: 3
title: Testen met Selenium
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 482f683ba0db2228dfbfb9fdbea794e7
---
Deze documentatie beschrijft hoe je een webforJ-app test met Selenium, met specifieke aandacht voor de `HelloWorldView` uit
`webforj-archetype-hello-world`.

:::info Basisprincipes van apps
Lees het gedeelte [Introductie van de basisprincipes van apps](../../introduction/basics) voor meer informatie over `webforj-archetype-hello-world`.
:::

## Vereisten {#prerequisites}

Zorg voordat je de Selenium-tests uitvoert voor het volgende:
- De webforJ-app is correct geconfigureerd en draait op je lokale server.
- Je hebt het volgende geïnstalleerd:
  - Selenium Java-bindings.
  - Een compatibele WebDriver voor je browser.
  - Maven voor projectafhankelijkheden.

## Maven-configuratie {#maven-configuration}

Voeg de benodigde afhankelijkheden voor Selenium en andere testbibliotheken toe aan je `pom.xml`:

```xml title="pom.xml"
<dependencies>
  <dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.27.0</version>
  </dependency>
  <dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.9.2</version>
  </dependency>
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

## Testvoorbeeld: `HelloWorldView` {#testing-example-helloworldview}

De volgende code toont een op Selenium gebaseerde test voor de `HelloWorldView`-component.

```java title="HelloWorldViewTest.java"
package com.example.views;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import io.github.bonigarcia.wdm.WebDriverManager;

class HelloWorldViewTest {

  private WebDriver driver;
  private static final String PORT = System.getProperty("server.port", "8080");

  @BeforeAll
  static void setupAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  void setup() {
    driver = new ChromeDriver();
    driver.get("http://localhost:" + PORT + "/");
    new WebDriverWait(driver, ofSeconds(30))
            .until(titleIs("webforJ Hello World"));
  }

  @AfterEach
  void teardown() {
    if (driver != null) {
        driver.quit();
    }
  }

  @Test
  void shouldClickButton() {
    WebElement button = driver.findElement(By.tagName("dwc-button"));
    assertEquals("Say Hello", button.getText(), "Button text mismatch!");
  }
}
```

### Belangrijke stappen {#key-steps}

1. **WebDriver initialiseren**:
   - Gebruik [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) om het uitvoerbare stuurprogramma voor de browser automatisch te beheren.

2. **Testomgeving instellen**:
   - Start de testserver op `http://localhost:<port>/`.
   - Wacht totdat de paginatitel overeenkomt met de verwachte titel `webforJ Hello World`.

3. **Interactie met elementen**:
   - Zoek elementen met `By.tagName`, `By.id` of andere Selenium-locators.
   - Controleer het verwachte gedrag, zoals klikken op knoppen of tekstwijzigingen.

  :::info
  Omdat webforJ een webapp met één pagina genereert, is Selenium niet op de hoogte van DOM-manipulaties nadat de pagina voor het eerst is geladen. Je kunt Seleniums [WebDriverWait API](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) gebruiken om te wachten totdat de DOM is opgebouwd.
  :::

4. **Opruimen**:
   - Beëindig de WebDriver-sessie om resources vrij te maken.

### Tests uitvoeren {#running-tests}

1. Start de webforJ-server:
   ```bash
   mvn
   ```

2. Voer de testcases uit:
   ```bash
   mvn test
   ```

## Verwacht gedrag {#expected-behavior}

- Wanneer je `http://localhost:<port>/` bezoekt, wordt de pagina `HelloWorldView` geladen.
- Het element `dwc-button` met de tekst `Say Hello` moet aanwezig zijn.
