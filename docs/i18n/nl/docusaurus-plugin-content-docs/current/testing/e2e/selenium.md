---
sidebar_position: 3
title: Testing with Selenium
_i18n_hash: 5d2e4b04f794236d9a8ea2a32d50579b
---
Deze documentatie beschrijft het proces voor het testen van webforJ-apps met Selenium, met een specifieke focus op de `HelloWorldView` van de `webforj-archetype-hello-world`.

:::info App Basics
Om meer te leren over de `webforj-archetype-hello-world`, raadpleeg de sectie [App Basics Introduction](../../introduction/basics).
:::

## Voorwaarden {#prerequisites}

Voordat je de Selenium-tests uitvoert, zorg ervoor dat het volgende is geregeld:
- webforJ-app is correct ingesteld en draait op je lokale server.
- Je hebt geïnstalleerd:
  - Selenium Java bindmiddelen.
  - Een compatibele WebDriver voor je browser.
  - Maven voor projectafhankelijkheden.

## Maven-configuratie {#maven-configuration}

Voeg de benodigde afhankelijkheden toe in je `pom.xml` voor Selenium en andere testbibliotheken:

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

De volgende code demonstreert een test op basis van Selenium voor de `HelloWorldView`-component.

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

1. **Initialiseer WebDriver**:
   - Gebruik [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) om automatisch de driver-executable voor de browser te beheren.

2. **Stel Testomgeving in**:
   - Start de testserver op `http://localhost:<port>/`.
   - Wacht tot de paginatitel overeenkomt met de verwachte `webforJ Hello World`.

3. **Interactie met Elementen**:
   - Lokaliseer elementen met `By.tagName`, `By.id` of andere Selenium-locators.
   - Verifieer verwachte gedragingen zoals knopklikken of tekstwijzigingen.
    
  :::info
  Omdat webforJ een single-page webapp maakt, is Selenium zich niet bewust van DOM-manipulatie nadat de initiële pagina is geladen. Je kunt de [WebDriverWait API](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) van Selenium gebruiken om te wachten tot de DOM is gecompileerd.
  :::

4. **Afsluiten**:
   - Stop de WebDriver-sessie om bronnen vrij te geven.

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

- Bij het bezoeken van `http://localhost:<port>/` wordt de `HelloWorldView`-pagina geladen.
- Het `dwc-button`-element met de tekst `Say Hello` moet aanwezig zijn.
