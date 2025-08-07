---
sidebar_position: 2
title: Testing with Playwright
_i18n_hash: 43cec2eab876a8dc170f4fb69aaa8214
---
Tämä asiakirja kuvaa prosessin webforJ-sovellusten testaamiselle Playwrightilla, erityisesti keskittyen `HelloWorldView`-komponenttiin `webforj-archetype-hello-world`-projektista.

:::info Sovelluksen perusteet
Jos haluat tietää lisää `webforj-archetype-hello-world`:ista, katso [Sovelluksen perusteet -esittely](../../introduction/basics) -osio.
:::

## Esivaatimukset {#prerequisites}

Ennen Playwright-testien kirjoittamista ja suorittamista varmista seuraavat:
- webforJ-sovellus on oikein asetettu ja toimii paikallisella palvelimellasi.
- Olet asentanut:
  - Playwright Java -sidokset.
  - Yhteensopivan selaimen (Playwright voi asentaa selaimet automaattisesti asetuksen aikana).
  - Maven projektiavusteita varten.

## Maven-konfiguraatio {#maven-configuration}

Lisää tarvittavat riippuvuudet `pom.xml`-tiedostoosi Playwrightia varten:

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

## Testausesimerkki: `HelloWorldView` {#testing-example-helloworldview}

Seuraava koodi osoittaa Playwright-pohjaisen testin `HelloWorldView`-komponentille.

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

### Keskeiset vaihe(et) {#key-steps}

1. **Alusta Playwright**:
   - Luo `Playwright`-instanssi.
   - Käynnistä selaininstanssi `playwright.chromium().launch()`-käskyllä.

2. **Aseta testausympäristö**:
   - Avaa uusi selainvälilehti `browser.newPage()`-käskyllä.
   - Siirry `HelloWorldView`-sivulle `navigate`-menetelmällä.

3. **Vuorovaikutus elementtien kanssa**:
   - Käytä [Playwrightin paikantimia](https://playwright.dev/java/docs/api/class-locator) vuorovaikutukseen DOM-elementtien kanssa.
   - Täytä syötekentät `locator("input").fill()`-käskyllä ja laukaise toimintoja `getByText("Say Hello").click()`-käskyllä.

4. **Väitteet**:
   - Vahvista näytettävä toast-viesti `PlaywrightAssertions.assertThat()`-käskyllä.

5. **Siivous**:
   - Playwright hoitaa selaimen siivouksen automaattisesti testin päätyttyä. Manuaalista siivousta varten voit sulkea selaimen `browser.close()`-käskyllä.

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
- Syötä webforJ tekstikenttään ja napsauta `Say Hello`-painiketta.
- Toast-viestin pitäisi ilmestyä tekstillä: `Welcome to webforJ Starter webforJ!`.
