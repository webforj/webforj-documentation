---
sidebar_position: 2
title: Testing with Playwright
_i18n_hash: 43cec2eab876a8dc170f4fb69aaa8214
---
Diese Dokumentation beschreibt den Prozess zum Testen von webforJ-Anwendungen mit Playwright, wobei speziell der `HelloWorldView` aus dem `webforj-archetype-hello-world` im Fokus steht.

:::info App-Grundlagen
Um mehr über das `webforj-archetype-hello-world` zu erfahren, siehe den Abschnitt [App-Grundlagen Einführung](../../introduction/basics).
:::

## Voraussetzungen {#prerequisites}

Bevor Sie die Playwright-Tests schreiben und ausführen, stellen Sie sicher, dass Folgendes erfüllt ist:
- Die webforJ-App ist korrekt eingerichtet und läuft auf Ihrem lokalen Server.
- Sie haben Folgendes installiert:
  - Playwright Java-Bindings.
  - Einen kompatiblen Browser (Playwright kann Browser während der Einrichtung automatisch installieren).
  - Maven für Projektabhängigkeiten.

## Maven-Konfiguration {#maven-configuration}

Fügen Sie die erforderlichen Abhängigkeiten in Ihrer `pom.xml` für Playwright hinzu:

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

Der folgende Code zeigt einen auf Playwright basierenden Test für die Komponente `HelloWorldView`.

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

### Schlüssel Schritte {#key-steps}

1. **Playwright initialisieren**:
   - Erstellen Sie eine `Playwright`-Instanz.
   - Starten Sie eine Browser-Instanz mit `playwright.chromium().launch()`.

2. **Testumgebung einrichten**:
   - Öffnen Sie eine neue Browserseite mit `browser.newPage()`.
   - Navigieren Sie zur Seite `HelloWorldView` mit der Methode `navigate`.

3. **Mit Elementen interagieren**:
   - Verwenden Sie [Playwrights Locator](https://playwright.dev/java/docs/api/class-locator), um mit DOM-Elementen zu interagieren.
   - Füllen Sie Eingabefelder mit `locator("input").fill()` und lösen Sie Aktionen mit `getByText("Say Hello").click()` aus.

4. **Assertions**:
   - Überprüfen Sie die angezeigte Toast-Nachricht mit `PlaywrightAssertions.assertThat()`.

5. **Teardown**:
   - Playwright kümmert sich automatisch um die Bereinigung des Browsers, wenn der Test abgeschlossen ist. Für manuelle Bereinigung können Sie den Browser mit `browser.close()` schließen.

### Tests ausführen {#running-tests}

1. Starten Sie den webforJ-Server:
   ```bash
   mvn jetty:run
   ```

2. Führen Sie die Testfälle aus:
   ```bash
   mvn test
   ```

## Erwartetes Verhalten {#expected-behavior}

- Beim Besuch von `http://localhost:<port>/` wird die Seite `HelloWorldView` geladen.
- Geben Sie webforJ in das Textfeld ein und klicken Sie auf die Schaltfläche `Say Hello`.
- Eine Toast-Nachricht sollte erscheinen mit dem Text: `Willkommen bei webforJ Starter webforJ!`.
