---
sidebar_position: 2
title: Testing with Playwright
_i18n_hash: dffe640edd9d7918a3c8bace8cf0bbe8
---
Tämä dokumentaatio kuvaa prosessia webforJ-sovellusten testaamiseksi Playwrightin avulla, keskittyen erityisesti `HelloWorldView`-komponenttiin `webforj-archetype-hello-world`-projektista.

:::info Sovelluksen perusteet
Jos haluat oppia lisää `webforj-archetype-hello-world`-projektista, katso [Sovelluksen perusteet -esittely](../../introduction/basics) -osan.
:::

## Edellytykset {#prerequisites}

Ennen Playwright-testausten kirjoittamista ja suorittamista varmista, että:
- webforJ-sovellus on oikein asetettu ja käynnissä paikallisella palvelimellasi.
- Olet asentanut:
  - Playwright Java -sitojat.
  - Yhteensopivan selaimen (Playwright voi automaattisesti asentaa selaimia asetusten aikana).
  - Maven projektiriippuvuuksia varten.

## Maven-konfigurointi {#maven-configuration}

Lisää tarvittavat riippuvuudet `pom.xml`-tiedostoosi Playwrightille:

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

## Testiesimerkki: `HelloWorldView` {#testing-example-helloworldview}

Seuraava koodi havainnollistaa Playwright-pohjaista testiä `HelloWorldView`-komponentille.

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

### Avainvaiheet {#key-steps}

1. **Alustaminen Playwrightissa**:
   - Luo `Playwright`-instanssi.
   - Käynnistä selaininstanssi käyttäen `playwright.chromium().launch()`.

2. **Testiympäristön asettaminen**:
   - Avaa uusi selainvälilehti käyttäen `browser.newPage()`.
   - Siirrä `HelloWorldView`-sivulle `navigate`-metodin avulla.

3. **Vuorovaikutus Elementtien kanssa**:
   - Käytä [Playwrightin sijoittimia](https://playwright.dev/java/docs/api/class-locator) vuorovaikutukseen DOM-elementtien kanssa.
   - Täytä syöttökentät `locator("input").fill()` ja laukaise toimenpiteitä käyttäen `getByText("Say Hello").click()`.

4. **Väittämiä**:
   - Vahvista näytettävä toast-viesti `PlaywrightAssertions.assertThat()`-metodilla.

5. **Purku**:
   - Playwright käsittelee selaimen puhdistuksen automaattisesti testin päätyttyä. Manuaalista puhdistusta varten voit sulkea selaimen käyttäen `browser.close()`.

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
- Syötä webforJ tekstikenttään ja napsauta `Say Hello`-painiketta.
- Toast-viestin pitäisi ilmestyä, jossa on teksti: `Welcome to webforJ Starter webforJ!`.
