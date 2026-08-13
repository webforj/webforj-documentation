---
sidebar_position: 2
title: Testing with Playwright
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: d0b58780be88b22c15eef134bbd4755a
---
Tämä dokumentaatio kuvaa prosessin webforJ-sovellusten testaamiseksi Playwrightilla, erityisesti keskittyen `HelloWorldView`:iin `webforj-archetype-hello-world`-projektista.

:::info Sovelluksen perusasiat
Lisätietoja `webforj-archetype-hello-world`:ista löytyy [Sovelluksen perusasiat -esittely](../../introduction/basics) -osiosta.
:::

## Edellytykset {#prerequisites}

Ennen Playwright-testien kirjoittamista ja suorittamista varmista seuraavat asiat:
- webforJ-sovellus on oikein asetettu ja käynnissä paikallisella palvelimellasi.
- Olet asentanut:
  - Playwright Java -siteet.
  - Yhteensopivan selaimen (Playwright voi automaattisesti asentaa selaimia asetusten aikana).
  - Maven-projektiriippuvuuksille.

## Maven-konfiguraatio {#maven-configuration}

Lisää tarvittavat riippuvuudet `pom.xml`:ään Playwrightia varten:

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

Seuraava koodi näyttää Playwright-pohjaisen testin `HelloWorldView`-komponentille.

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

1. **Alusta Playwright**:
   - Luo `Playwright`-instanssi.
   - Käynnistä selaininstanssi `playwright.chromium().launch()`-metodilla.

2. **Aseta testausympäristö**:
   - Avaa uusi selainikkuna `browser.newPage()`-metodilla.
   - Siirry `HelloWorldView`-sivulle `navigate`-menetelmällä.

3. **Vuorovaikuta elementtien kanssa**:
   - Käytä [Playwrightin lokalisaattoreita](https://playwright.dev/java/docs/api/class-locator) vuorovaikutukseen DOM-elementtien kanssa.
   - Täytä syöttökentät `locator("input").fill()`-metodilla ja laukaise toimintoja `getByText("Say Hello").click()`-menetelmällä.

4. **Vaatimukset**:
   - Vahvista näytetty toast-viesti `PlaywrightAssertions.assertThat()`-menetelmällä.

5. **Puhdistus**:
   - Playwright hoitaa selaimen puhdistuksen automaattisesti testin päättyessä. Manuaalista puhdistusta varten voit sulkea selaimen käyttämällä `browser.close()`.

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

- Vieraillessasi `http://localhost:<port>/`, `HelloWorldView`-sivu latautuu.
- Syötä webforJ tekstikenttään ja napsauta `Say Hello`-painiketta.
- Toast-viestin pitäisi ilmestyä tekstillä: `Welcome to webforJ Starter webforJ!`.
