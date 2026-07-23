---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
sidebar_position: 30
_i18n_hash: 6fdaf15e67e0015f7319572200ccc353
---
Deze documentatie dient als gids voor het upgraden van webforJ-apps van 24.00 naar 25.00. Hier zijn de wijzigingen die nodig zijn voor bestaande apps om soepel te blijven draaien. Zoals altijd, zie de [GitHub release-overzicht](https://github.com/webforj/webforj/releases) voor een meer uitgebreide lijst van wijzigingen tussen releases.

## Jetty 12 webservers {#jetty-12-web-servers}

webforJ 25.00 en hoger maakt gebruik van Jetty 12, met de Jakarta EE10 servlet-architectuur. Als je de Jetty Maven-plugin voor ontwikkeling gebruikt, migreer dan van Jakarta EE8 naar Jakarta EE10. Deze upgrade vereist ook dat je alles vervangt dat afhankelijk was van het `javax.servlet`-pakket door het `Jakarta.servlet`-pakket.

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

## API-wijzigingen voor de `App`-klasse {#api-changes-for-the-app-class}

Verschillende verouderde `App`-methoden zijn verwijderd in 25.00. De volgende secties schetsen welke methoden zijn vervangen en de aanbevolen vervangingen.

### Console logging {#console-logging}

De utilityklasse [`BrowserConsole`](/docs/advanced/browser-console), die is gewijd aan het creëren van gestyleerde logs naar de browserconsole, vervangt de `consoleLog()` en `consoleError()`-methoden. Verkrijg de `BrowserConsole` door gebruik te maken van de `console()`-methode:

```java
public class Application extends App{

  @Override
  public void run() throws WebforjException {
    console().log("Logbericht");
    console().error("Foutbericht");
  }
}
```

### Webopslag {#web-storage}

Voor versies voor webforJ 25.00 heeft de `App`-klasse de methoden `getLocalStorage()`, `getSessionStorage()` en `getCookieStorage()` om instanties van de `LocalStorage`, `SessionStorage` en `CookieStorage` klassen te verkrijgen. Voortaan heeft elke klasse een `getCurrent()`-methode.

Zie [Webopslag](/docs/advanced/web-storage) voor meer informatie.

### `Request`-klasse {#request-class}

De `Request`-klasse is nu verantwoordelijk voor het verkrijgen van de URL, poort, host en protocol van een app. Dus in plaats van `App.getUrl()` te gebruiken, gebruik `App.getCurrent().getUrl()`. De `getCurrent()`-methode vervangt ook de `getRequest()`-methode om een instantie van de `Request`-klasse te verkrijgen.

:::info
De `Request`-klasse heeft ook verwijderde methoden, ga naar [`Request`](#request-changes) om ze te zien.
:::

### `Page`-klasse {#page-class}

De `getPage()`-methode is vervangen door `Page.getCurrent()` om de huidige pagina-instantie te verkrijgen.

### Keuzedialogen {#option-dialogs}

In plaats van de `msgbox()`-methode te gebruiken, gebruik [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) om berichtendialogen te creëren.

### App-beëindiging {#app-termination}

De `cleanup()`-methode is verwijderd. Er zijn nu twee methoden voor beëindigingen, `onWillTerminate()` en `onDidTerminate()`.

Zie [Hooks voor beëindiging](/docs/advanced/terminate-and-error-actions#hooks-for-termination) voor meer informatie.

## Tabelsortering {#table-sorting}

Voor webforJ 25.00 en hoger gebruiken tabellen standaard sortering op één kolom. Kolommen worden alleen gesorteerd op de meest recent geselecteerde kolomkop. Om een tabel meerdere kolomsortering te laten gebruiken, roep de [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) methode aan:

```java
table.setMultiSorting(true);
```

## Verborgen `TabbedPane`-body {#hidden-tabbedpane-body}

De `hideBody()`-methode is vervangen door `setBodyHidden()` om een consistente naamgevingsconventie voor methoden te behouden.

## HTML renderen binnen componenten {#rendering-html-inside-components}

In webforJ 25.00 en hoger is er een `setHtml()`-methode om onderscheid te maken tussen het instellen van letterlijke en HTML-tekst binnen een component. HTML instellen met de `setText()`-methode is nog steeds mogelijk, maar vereist nu expliciet dat het wordt omgeven met `<html>`-tags.

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

Het `com.webforj.component.htmlcontainer`-pakket is niet langer aanwezig in webforJ. Gebruik in plaats daarvan het meer uitgebreide `com.webforj.component.element`-pakket. Voor een lijst van webforJ-klassen voor standaard HTML-elementen, ga naar [HTML Element Componenten](/docs/components/html-elements).

## `Request` wijzigingen {#request-changes}

- Net als de verwijdering van de `getCookieStorage()`-methode voor de `App`-klasse, heeft `Request` niet langer de `getCookie()`-methode. Dit versterkt het gebruik van `CookieStorgage.getCurrent()` om een instantie van de `CookieStorage`-klasse te verkrijgen.

- De `getQueryParam()`-methode is nu `getQueryParameter()`.

## `WebforjBBjBridge` wijzigingen {#webforjbbjbridge-changes}

### Het verkrijgen van een instantie van `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

De `Environment`-klasse heeft niet langer de `getWebforjHelper()`-methode, dus gebruik in plaats daarvan `getBridge()`.

### De `ConfirmDialog`-component gebruiken voor de `msgbox()`-methode {#using-the-confirmdialog-component-for-the-msgbox-method}

Eerdere versies van webforJ gebruikten direct strings en gehele getallen voor de `WebforjBBjBridge` `msgbox()`-methode. Echter, berichten voor `WebforjBBjBridge` in webforJ 25.00 en hoger gebruiken de [`ConfirmDialog`](/docs/components/option-dialogs/confirm) component. Dit biedt meer controle over welke knoppen worden weergegeven en het berichttype.

**Voor**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("Weet je zeker dat je dit bestand wilt verwijderen?", 1, "Verwijdering");
```

**Na**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
  "Weet je zeker dat je dit bestand wilt verwijderen?", "Verwijdering",
  ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

<!-- ## Environment.logError verwijderd -->

## Typefoutcorrectie `PasswordMediation` {#passwordmediation-typo-correction}

De enum-klasse `PasswordMediation`, gebruikt om aan te geven of een gebruiker bij elke bezoek aan een app met een `Login`-component moet inloggen, heeft een typefout in eerdere versies van webforJ. `SILENT` vervangt de typefout `SILIENT` voor webforJ 25.00 en hoger.

## Auto-focussen methoden {#auto-focusing-methods}

Om webforJ consistent te houden, hebben methoden zoals `setAutofocus()` en `isAutofocus()` nu een uniforme kapitalisatie zoals de HasAutoFocus-interface. Dus componenten zoals `Dialog` en `Drawer` gebruiken `setAutoFocus()` en `isAutoFocus()` voor 25.00 en hoger.

## `BBjWindowAdapter` en `Panel` gemarkeerd als `final` {#bbjwindowadapter-and-panel-marked-as-final}

De `BBjWindowAdapter` en `Panel` klassen zijn nu gemarkeerd als `final`, wat betekent dat ze niet langer kunnen worden onderverdeeld. Deze wijziging verbetert de stabiliteit en handhaaft consistente gebruikspatronen.
