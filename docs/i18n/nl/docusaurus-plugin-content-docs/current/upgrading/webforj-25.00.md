---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: cbb2bd70fa3e51df1096018ff2519878
---
Deze documentatie dient als een gids om webforJ-apps te upgraden van 24.00 naar 25.00. Hier zijn de wijzigingen die nodig zijn voor bestaande apps om soepel te blijven draaien. Zoals altijd, zie de [GitHub releaseoverzicht](https://github.com/webforj/webforj/releases) voor een meer uitgebreide lijst van wijzigingen tussen releases.

## Jetty 12 webservers {#jetty-12-web-servers}

webforJ 25.00 en hoger maakt gebruik van Jetty 12, met de Jakarta EE10 servlet architectuur. Als je de Jetty Maven plugin voor ontwikkeling gebruikt, migreer dan van Jakarta EE8 naar Jakarta EE10. Deze upgrade vereist ook het vervangen van alles wat afhankelijk is van het `javax.servlet` pakket door het `Jakarta.servlet` pakket.

### POM-bestand wijzigingen {#pom-file-changes}

**Voor**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee8</groupId>
  <artifactId>jetty-ee8-maven-plugin</artifactId>
  <version>10.x.xx</version>
```
**Na**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee10</groupId>
  <artifactId>jetty-ee10-maven-plugin</artifactId>
  <version>12.x.xx</version>
```

## API-wijzigingen voor de `App` klasse {#api-changes-for-the-app-class}

Verscheidene verouderde `App`-methoden zijn verwijderd in 25.00. De volgende secties beschrijven welke methoden zijn vervangen en de aanbevolen vervangingen.

### Console logging {#console-logging}

De hulpprogrammaklasse [`BrowserConsole`](/docs/advanced/browser-console), die is gewijd aan het maken van gestileerde logs naar de browserconsole, vervangt de `consoleLog()` en `consoleError()` methoden. Verkrijg de `BrowserConsole` door de `console()` methode te gebruiken:

```java
public class Application extends App {
  
  @Override
  public void run() throws WebforjException {
    console().log("Logbericht");
    console().error("Foutbericht");
  }
}
```

### Webopslag {#web-storage}

Voor versies vóór webforJ 25.00 heeft de `App` klasse de methoden `getLocalStorage()`, `getSessionStorage()`, en `getCookieStorage()` om instanties van de klassen `LocalStorage`, `SessionStorage` en `CookieStorage` respectievelijk te verkrijgen. Voortaan heeft elke klasse een `getCurrent()` methode.

Zie [Webopslag](/docs/advanced/web-storage) voor meer informatie.

### `Request` klasse {#request-class}

De `Request` klasse is nu verantwoordelijk voor het verkrijgen van de URL, poort, host en protocol van een app. Dus in plaats van `App.getUrl()` te gebruiken, gebruik je `App.getCurrent().getUrl()`. De `getCurrent()` methode vervangt ook de `getRequest()` methode om een instantie van de `Request` klasse te verkrijgen.

:::info
De `Request` klasse heeft ook verwijderde methoden, spring naar [`Request`](#request-changes) om ze te zien.
:::

### `Page` klasse {#page-class}

De `getPage()` methode is vervangen door `Page.getCurrent()` om de huidige pagina-instantie te verkrijgen.

### Optie dialogen {#option-dialogs}

In plaats van de `msgbox()` methode te gebruiken, gebruik je [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) om berichtdialogen te maken.

### App beëindiging {#app-termination}

De `cleanup()` methode is verwijderd. Er zijn nu twee methoden voor beëindiging: `onWillTerminate()` en `onDidTerminate()`.

Zie [Hooks voor beëindiging](/docs/advanced/terminate-and-error-actions#hooks-for-termination) voor meer informatie.

## Tabel sorteren {#table-sorting}

Voor webforJ 25.00 en hoger gebruiken tabellen standaard sorteren op één kolom. Kolommen zullen alleen worden gesorteerd op de meest recent geselecteerde kolomkop. Om een tabel multi-kolom sorteren te laten gebruiken, roep je de [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) methode aan:

```java
table.setMultiSorting(true);
```

## Verborgen `TabbedPane` body {#hidden-tabbedpane-body}

De `hideBody()` methode is vervangen door `setBodyHidden()` om een consistente naamgevingsconventie voor methoden te behouden.

## HTML weergeven binnen componenten {#rendering-html-inside-components}

In webforJ 25.00 en hoger is er een `setHtml()` methode om onderscheid te maken tussen het instellen van letterlijke en HTML-tekst binnen een component. Het instellen van HTML met de `setText()` methode is nog steeds mogelijk, maar vereist nu expliciet om het met `<html>` tags te omhullen.

```java
// Geldige gebruiken van setText() en setHtml()
Button home = new Button();

home.setText("""
  <html>
    <h1>Home</h1>
  </html>
""");

home.setHtml("<h1>Home</h1>");

home.setText("Home");
```

```java
// Ongeldige gebruiken van setText() en setHtml()
Button home = new Button();
home.setText("<h1>Home</h1>");
```

## HTML-containers {#html-containers}

Het `com.webforj.component.htmlcontainer` pakket is niet langer beschikbaar in webforJ. Gebruik in plaats daarvan het rijkere `com.webforj.component.element` pakket. Voor een lijst van webforJ klassen voor standaard HTML-elementen, ga naar [HTML Element Components](/docs/components/html-elements).

## `Request` wijzigingen {#request-changes}

- Net als de verwijdering van de `getCookieStorage()` methode voor de `App` klasse, heeft `Request` niet langer de `getCookie()` methode. Dit versterkt het gebruik van `CookieStorgage.getCurrent()` om een instantie van de `CookieStorage` klasse te verkrijgen.

- De `getQueryParam()` methode is nu `getQueryParameter()`.

## `WebforjBBjBridge` wijzigingen {#webforjbbjbridge-changes}

### Een instantie van `WebforjBBjBridge` verkrijgen {#getting-an-instance-of-webforjbbjbridge}

De `Environment` klasse heeft niet langer de `getWebforjHelper()` methode, gebruik daarom in plaats daarvan `getBridge()`.

### De `ConfirmDialog` component gebruiken voor de `msgbox()` methode {#using-the-confirmdialog-component-for-the-msgbox-method}

Eerdere versies van webforJ gebruikten direct strings en integers voor de `WebforjBBjBridge` `msgbox()` methode. Berichten voor `WebforjBBjBridge` in webforJ 25.00 en hoger gebruiken de [`ConfirmDialog`](/docs/components/option-dialogs/confirm) component. Dit geeft meer controle over welke knoppen worden weergegeven en het type bericht.

**Voor**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("Weet je zeker dat je dit bestand wilt verwijderen?", 1, "Verwijderen");
```

**Na**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
      "Weet je zeker dat je dit bestand wilt verwijderen?", "Verwijderen",
      ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

<!-- ## Environment.logError verwijderd -->

## `PasswordMediation` typefoutcorrectie {#passwordmediation-typo-correction}

De enum klasse `PasswordMediation`, gebruikt om aan te geven of een gebruiker bij elke bezoek aan een app met een `Login` component moet inloggen, heeft een typefout in eerdere webforJ-versies. `SILENT` vervangt de typefout `SILIENT` voor webforJ 25.00 en hoger.

## Auto-focussen methoden {#auto-focusing-methods}

Om webforJ consistent te houden, hebben methoden zoals `setAutofocus()` en `isAutofocus()` nu een uniforme kapitalisatie, vergelijkbaar met de HasAutoFocus interface. Dus componenten zoals `Dialog` en `Drawer` gebruiken `setAutoFocus()` en `isAutoFocus()` voor 25.00 en hoger.

## `BBjWindowAdapter` en `Panel` gemarkeerd als `final` {#bbjwindowadapter-and-panel-marked-as-final}

De `BBjWindowAdapter` en `Panel` klassen zijn nu als `final` gedeclareerd, wat betekent dat ze niet langer kunnen worden onderverdeeld. Deze wijziging verbetert de stabiliteit en handhaaft consistente gebruikspatronen.
