---
sidebar_position: 3
title: Testing with Selenium
_i18n_hash: fe85942b4638ef9828b334ef986b4436
---
Tämä dokumentaatio kuvaa prosessin webforJ-sovellusten testaamiseksi Seleniumilla, keskittyen erityisesti `HelloWorldView`-komponenttiin `webforj-archetype-hello-world`-projektista.

:::info Sovelluksen perusteet
Jos haluat tietää lisää `webforj-archetype-hello-world`-projektista, viittaa [Sovelluksen perusteet -esittelyyn](../../introduction/basics).
:::

## Edellytykset {#prerequisites}

Ennen Selenium-testien suorittamista varmista seuraavat asiat:
- webforJ-sovellus on oikein asetettu ja toimii paikallisella palvelimellasi.
- Olet asentanut:
  - Selenium Java -sidokset.
  - Yhteensopivan WebDriverin selaimellesi.
  - Maven projektiriippuvuuksia varten.

## Maven-konfiguraatio {#maven-configuration}

Lisää tarvittavat riippuvuudet `pom.xml`-tiedostoosi Seleniumia ja muita testauskirjastoja varten:

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

## Testausesimerkki: `HelloWorldView` {#testing-example-helloworldview}

Seuraava koodi esittelee Selenium-pohjaisen testin `HelloWorldView`-komponentille.

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

### Keskeiset askeleet {#key-steps}

1. **Alusta WebDriver**:
   - Käytä [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) -työkalua hallitsemaan selaimen ajurin suoritettavaa automaattisesti.

2. **Aseta testausympäristö**:
   - Käynnistä testipalvelin `http://localhost:<port>/`-osoitteeseen.
   - Odota, että sivun otsikko vastaa odotettua `webforJ Hello World`.

3. **Vuorovaikutus elementtien kanssa**:
   - Etsi elementtejä käyttäen `By.tagName`, `By.id` tai muita Seleniumin etsintätapoja.
   - Vahvista odotettuja käyttäytymisiä, kuten nappien klikkaukset tai tekstimuutokset.

  :::info
  Koska webforJ tuottaa yksisivuisen verkkosovelluksen, Selenium ei tunne DOM-manipulaatiota sen jälkeen, kun alkuperäinen sivu on ladattu. Voit käyttää Seleniumin [WebDriverWait APIa](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) odottaaksesi, kunnes DOM on käännetty.
  :::

4. **Tehostus**:
   - Sulje WebDriver-istunto vapauttaaksesi resurssit.

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

- Kun vierailet `http://localhost:<port>/`, `HelloWorldView`-sivu latautuu.
- `dwc-button`-elementin, jonka teksti on `Say Hello`, tulisi olla läsnä.
