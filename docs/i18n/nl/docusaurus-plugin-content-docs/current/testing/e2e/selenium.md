---
sidebar_position: 3
title: Testing met Selenium
_i18n_hash: fe85942b4638ef9828b334ef986b4436
---
Deze documentatie beschrijft het proces voor het testen van webforJ-apps met Selenium, met een specifieke focus op de `HelloWorldView` van de `webforj-archetype-hello-world`.

:::info App Basis
Om meer te leren over de `webforj-archetype-hello-world`, raadpleeg de [App Basis Introductie](../../introduction/basics) sectie.
:::

## Vereisten {#prerequisites}

Voordat je de Selenium-tests uitvoert, zorg ervoor dat aan het volgende is voldaan:
- De webforJ-app is correct ingesteld en draait op je lokale server.
- Je hebt geïnstalleerd:
  - Selenium Java bindings.
  - Een compatibele WebDriver voor je browser.
  - Maven voor projectafhankelijkheden.

## Maven configuratie {#maven-configuration}

Voeg de noodzakelijke afhankelijkheden toe in je `pom.xml` voor Selenium en andere testbibliotheken:

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

De volgende code demonstreert een op Selenium gebaseerd test voor de `HelloWorldView` component.

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
   - Wacht totdat de paginatitel overeenkomt met de verwachte `webforJ Hello World`.

3. **Interactie met Elementen**:
   - Zoek elementen met behulp van `By.tagName`, `By.id`, of andere Selenium-locators.
   - Controleer verwachte gedragingen zoals knopklikken of tekstveranderingen.
    
  :::info
  Omdat webforJ een single-page webapp produceert, is Selenium zich niet bewust van DOM-manipulatie nadat de initiële pagina is geladen. Je kunt de [WebDriverWait API](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) van Selenium gebruiken om te wachten totdat de DOM is samengesteld.
  :::

4. **Opruimen**:
   - Sluit de WebDriver-sessie om de middelen vrij te geven.

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

- Bij het bezoeken van `http://localhost:<port>/`, laadt de `HelloWorldView` pagina.
- Het `dwc-button` element met de tekst `Say Hello` zou aanwezig moeten zijn.
