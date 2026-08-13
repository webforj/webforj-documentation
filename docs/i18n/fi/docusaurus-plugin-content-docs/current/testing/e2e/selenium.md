---
sidebar_position: 3
title: Testing with Selenium
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 865e29cc6d656fb60a0a3332a805c5f4
---
Tämä dokumentaatio kuvaa prosessia webforJ-sovellusten testaamiseen Seleniumin avulla, keskittyen erityisesti `HelloWorldView`:hin `webforj-archetype-hello-world`-projektista.

:::info Sovelluksen perusteet
Lisätietoja `webforj-archetype-hello-world`-projektista saat [Sovelluksen perusteet -esittely](../../introduction/basics) -osiosta.
:::

## Esivaatimukset {#prerequisites}

Ennen Selenium-testien suorittamista varmista, että:
- webforJ-sovellus on oikein asetettu ja toimii paikallisella palvelimellasi.
- Olet asentanut:
  - Selenium Java -kirjastot.
  - Yhteensopivan WebDriverin selaimellesi.
  - Maven projektin riippuvuuksia varten.

## Maven-konfiguraatio {#maven-configuration}

Lisää tarvittavat riippuvuudet `pom.xml`-tiedostoon Seleniumille ja muille testauskirjastoille:

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

## Testiesimerkki: `HelloWorldView` {#testing-example-helloworldview}

Seuraava koodi esittää Selenium-pohjaisen testin `HelloWorldView`-komponentille.

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

### Keskeiset vaiheet {#key-steps}

1. **Alusta WebDriver**:
   - Käytä [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) -työkalua hallitaksesi selaimen ajurin suoritettavaa tiedostoa automaattisesti.

2. **Aseta testausympäristö**:
   - Käynnistä testipalvelin `http://localhost:<port>/`.
   - Odota, kunnes sivun otsikko vastaa odotettua `webforJ Hello World`.

3. **Vuorovaikuta elementtien kanssa**:
   - Etsi elementtejä käyttäen `By.tagName`, `By.id` tai muita Selenium-lokattoreita.
   - Vahvista odotettu käyttäytyminen, kuten painikkeiden napsautukset tai tekstin muutokset.

  :::info
  Koska webforJ tuottaa yksisivuisen verkkosovelluksen, Selenium ei ole tietoinen DOM-muokkauksista sen jälkeen, kun alkuperäinen sivu on ladattu. Voit käyttää Seleniumin [WebDriverWait API:a](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) odottaaksesi, kunnes DOM on käännetty.
  :::

4. **Siivous**:
   - Lopeta WebDriver-istunto vapauttaaksesi resurssit.

### Testien suorittaminen {#running-tests}

1. Käynnistä webforJ-palvelin:
   ```bash
   mvn jetty:run
   ```

2. Suorita testitapaukset:
   ```bash
   mvn test
   ```

## Odotettu käyttäytyminen {#expected-behavior}

- Käyttäessäsi `http://localhost:<port>/`, `HelloWorldView`-sivu latautuu.
- `dwc-button`-elementin, jonka teksti on `Say Hello`, pitäisi olla läsnä.
