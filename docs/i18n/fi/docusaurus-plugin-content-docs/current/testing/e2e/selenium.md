---
sidebar_position: 3
title: Testaus Seleniumilla
description: >-
  Drive a webforJ app from JUnit with Selenium WebDriver and WebDriverManager to
  simulate user interactions and assert UI state.
_i18n_hash: 482f683ba0db2228dfbfb9fdbea794e7
---
Tässä dokumentaatiossa kuvataan webforJ-sovellusten testaaminen Seleniumilla. Esimerkissä keskitytään `webforj-archetype-hello-world`-sovelluksen `HelloWorldView`-näkymään.

:::info Sovelluksen perusteet
Lisätietoja `webforj-archetype-hello-world`-sovelluksesta on kohdassa [Sovelluksen perusteiden esittely](../../introduction/basics).
:::

## Edellytykset {#prerequisites}

Varmista ennen Selenium-testien suorittamista seuraavat asiat:
- webforJ-sovellus on määritetty oikein ja käynnissä paikallisella palvelimellasi.
- Olet asentanut seuraavat:
  - Seleniumin Java-sidokset.
  - Selaimellesi yhteensopivan WebDriverin.
  - Mavenin projektin riippuvuuksien hallintaa varten.

## Maven-määritys {#maven-configuration}

Lisää Seleniumin ja muiden testikirjastojen tarvittavat riippuvuudet tiedostoon `pom.xml`:

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

Seuraava koodi näyttää Selenium-pohjaisen testin `HelloWorldView`-komponentille.

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
   - Käytä [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager)-kirjastoa selaimen ajuriohjelman automaattiseen hallintaan.

2. **Määritä testiympäristö**:
   - Käynnistä testipalvelin osoitteessa `http://localhost:<port>/`.
   - Odota, kunnes sivun otsikko vastaa odotettua arvoa `webforJ Hello World`.

3. **Käsittele elementtejä**:
   - Etsi elementtejä käyttämällä `By.tagName`-, `By.id`- tai muita Seleniumin paikantimia.
   - Varmista odotettu toiminta, kuten painikkeen napsauttaminen tai tekstin muuttuminen.

  :::info
  Koska webforJ tuottaa yksisivuisen verkkosovelluksen, Selenium ei havaitse DOM-manipulaatiota sivun ensimmäisen latauksen jälkeen. Voit odottaa DOMin kääntämistä Seleniumin [WebDriverWait-rajapinnalla](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html).
  :::

4. **Siivoa testin jälkeen**:
   - Sulje WebDriver-istunto vapauttaaksesi resurssit.

### Testien suorittaminen {#running-tests}

1. Käynnistä webforJ-palvelin:
   ```bash
   mvn
   ```

2. Suorita testitapaukset:
   ```bash
   mvn test
   ```

## Odotettu toiminta {#expected-behavior}

- Kun avaat osoitteen `http://localhost:<port>/`, `HelloWorldView`-sivu latautuu.
- `dwc-button`-elementin, jonka teksti on `Say Hello`, pitäisi olla näkyvissä.
