---
sidebar_position: 2
title: Testaaminen Playwrightilla
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: 3f815af0ad7916fdd5a53d6178846c6d
---
Tässä dokumentaatiossa kuvataan webforJ-sovellusten testaaminen Playwrightilla. Esimerkissä keskitytään `webforj-archetype-hello-world`-projektin `HelloWorldView`-näkymään.

:::info Sovelluksen perusteet
Lisätietoja `webforj-archetype-hello-world`-projektista on [Sovelluksen perusteet -johdanto](../../introduction/basics) -osiossa.
:::

## Edellytykset {#prerequisites}

Ennen Playwright-testien kirjoittamista ja suorittamista varmista, että:
- webforJ-sovellus on määritetty oikein ja käynnissä paikallisella palvelimellasi.
- Olet asentanut seuraavat:
  - Playwrightin Java-sidokset.
  - Yhteensopivan selaimen (Playwright voi asentaa selaimet automaattisesti määrityksen aikana).
  - Mavenin projektin riippuvuuksia varten.

## Maven-määritys {#maven-configuration}

Lisää Playwrightin tarvittavat riippuvuudet `pom.xml`-tiedostoon:

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

Seuraava koodi näyttää Playwrightilla toteutetun testin `HelloWorldView`-komponentille.

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

### Tärkeimmät vaiheet {#key-steps}

1. **Alusta Playwright**:
   - Luo `Playwright`-instanssi.
   - Käynnistä selaininstanssi komennolla `playwright.chromium().launch()`.

2. **Määritä testiympäristö**:
   - Avaa uusi selainikkuna komennolla `browser.newPage()`.
   - Siirry `HelloWorldView`-sivulle `navigate`-metodilla.

3. **Käsittele elementtejä**:
   - Käytä [Playwrightin paikantimia](https://playwright.dev/java/docs/api/class-locator) DOM-elementtien käsittelyyn.
   - Täytä syötekentät komennolla `locator("input").fill()` ja käynnistä toiminto komennolla `getByText("Say Hello").click()`.

4. **Väitteet**:
   - Varmista näytettävä ilmoitusviesti komennolla `PlaywrightAssertions.assertThat()`.

5. **Siivous**:
   - Playwright huolehtii selaimen siivouksesta automaattisesti testin päättyessä. Voit sulkea selaimen myös itse komennolla `browser.close()`.

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

- Kun siirryt osoitteeseen `http://localhost:<port>/`, `HelloWorldView`-sivu latautuu.
- Kirjoita tekstikenttään webforJ ja napsauta `Say Hello` -painiketta.
- Näkyviin tulee ilmoitusviesti, jonka teksti on `Welcome to webforJ Starter webforJ!`.
