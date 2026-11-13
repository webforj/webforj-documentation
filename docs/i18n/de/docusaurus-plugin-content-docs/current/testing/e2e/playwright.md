---
sidebar_position: 2
title: Testen mit Playwright
description: >-
  Drive a webforJ app from JUnit using the Playwright Java bindings to fill
  fields, click buttons, and assert rendered output in the browser.
_i18n_hash: 3f815af0ad7916fdd5a53d6178846c6d
---
Diese Dokumentation beschreibt, wie webforJ-Anwendungen mit Playwright getestet werden. Im Mittelpunkt steht dabei `HelloWorldView` aus `webforj-archetype-hello-world`.

:::info App-Grundlagen
Weitere Informationen zu `webforj-archetype-hello-world` finden Sie im Abschnitt [Einführung in die App-Grundlagen](../../introduction/basics).
:::

## Voraussetzungen {#prerequisites}

Bevor Sie die Playwright-Tests schreiben und ausführen, stellen Sie Folgendes sicher:
- Die webforJ-App ist korrekt eingerichtet und läuft auf Ihrem lokalen Server.
- Sie haben Folgendes installiert:
  - Playwright-Java-Bindings.
  - Einen kompatiblen Browser (Playwright kann Browser während der Einrichtung automatisch installieren).
  - Maven für Projektabhängigkeiten.

## Maven-Konfiguration {#maven-configuration}

Fügen Sie die erforderlichen Abhängigkeiten für Playwright in Ihre `pom.xml` ein:

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

## Testbeispiel: `HelloWorldView` {#testing-example-helloworldview}

Der folgende Code zeigt einen Playwright-basierten Test für die Komponente `HelloWorldView`.

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

### Wichtige Schritte {#key-steps}

1. **Playwright initialisieren**:
   - Erstellen Sie eine `Playwright`-Instanz.
   - Starten Sie eine Browserinstanz mit `playwright.chromium().launch()`.

2. **Testumgebung einrichten**:
   - Öffnen Sie mit `browser.newPage()` eine neue Browserseite.
   - Rufen Sie die Seite `HelloWorldView` mit der Methode `navigate` auf.

3. **Mit Elementen interagieren**:
   - Verwenden Sie [Playwright-Locators](https://playwright.dev/java/docs/api/class-locator), um mit DOM-Elementen zu interagieren.
   - Füllen Sie Eingabefelder mit `locator("input").fill()` aus und lösen Sie Aktionen mit `getByText("Say Hello").click()` aus.

4. **Assertions**:
   - Überprüfen Sie die angezeigte Toast-Nachricht mit `PlaywrightAssertions.assertThat()`.

5. **Aufräumen**:
   - Playwright kümmert sich nach Abschluss des Tests automatisch um das Schließen des Browsers. Zum manuellen Aufräumen können Sie den Browser mit `browser.close()` schließen.

### Tests ausführen {#running-tests}

1. Starten Sie den webforJ-Server:
   ```bash
   mvn
   ```

2. Führen Sie die Testfälle aus:
   ```bash
   mvn test
   ```

## Erwartetes Verhalten {#expected-behavior}

- Beim Aufrufen von `http://localhost:<port>/` wird die Seite `HelloWorldView` geladen.
- Geben Sie webforJ in das Textfeld ein und klicken Sie auf die Schaltfläche `Say Hello`.
- Es sollte eine Toast-Nachricht mit folgendem Text erscheinen: `Welcome to webforJ Starter webforJ!`.
