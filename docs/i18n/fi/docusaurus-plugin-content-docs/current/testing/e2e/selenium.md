---
sidebar_position: 3
title: Testing with Selenium
_i18n_hash: 5d2e4b04f794236d9a8ea2a32d50579b
---
Tämä dokumentaatio kuvaa prosessin webforJ-sovellusten testaamiseksi Seleniumilla, erityisesti keskittyen `HelloWorldView`-komponenttiin, joka tulee `webforj-archetype-hello-world`-projektista.

:::info Sovelluksen perusteet
Jos haluat tietää lisää `webforj-archetype-hello-world`-sovelluksesta, tutustu kohtaan [Sovelluksen perusteet -esittely](../../introduction/basics).
:::

## Vaatimukset {#prerequisites}

Ennen Selenium-testien suorittamista varmista seuraavat asiat:
- webforJ-sovellus on asetettu oikein ja toimii paikallisella palvelimellasi.
- Olet asentanut:
  - Seleniumin Java-sidonnat.
  - Yhteensopivan WebDriverin selaimellesi.
  - Mavenin projektiriippuvuuksia varten.

## Maven-konfigurointi {#maven-configuration}

Lisää tarvittavat riippuvuudet `pom.xml`-tiedostoosi Seleniumille ja muille testauskirjastoille:

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

Seuraava koodi kuvaa Selenium-pohjaista testiä `HelloWorldView`-komponentille.

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
   - Käytä [`WebDriverManager`](https://github.com/bonigarcia/webdrivermanager) -koulukunta automaattisesti hallitsemaan selaimen ajurin suoritettavaa tiedostoa.

2. **Testiympäristön asetukset**:
   - Käynnistä testipalvelin `http://localhost:<port>/`-osoitteessa.
   - Odota, kunnes sivun otsikko vastaa odotettua `webforJ Hello World`.

3. **Vuorovaikutus elementtien kanssa**:
   - Hae elementtejä käyttäen `By.tagName`, `By.id` tai muita Seleniumin hakijoita.
   - Vahvista odotettu käyttäytyminen, kuten painikkeen napsautukset tai tekstin muutokset.
    
  :::info
  Koska webforJ tuottaa yhden sivun verkkosovelluksen, Selenium ei ole tietoinen DOM-manipulaatioista sen jälkeen, kun alkuperäinen sivu on ladattu. Voit käyttää Seleniumin [WebDriverWait APIa](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/WebDriverWait.html) odottaaksesi, kunnes DOM on koottu.
  :::

4. **Puhdistus**:
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
- `dwc-button`-elementin, jossa on teksti `Say Hello`, tulisi olla läsnä.
