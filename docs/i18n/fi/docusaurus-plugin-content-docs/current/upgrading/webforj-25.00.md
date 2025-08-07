---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 2553d37a63c097b7520f2989849f016b
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämiseen versiosta 24.00 versioon 25.00. 
Tässä ovat muutokset, jotka on tehtävä olemassa oleville sovelluksille, jotta ne toimisivat edelleen sujuvasti. 
Kuten aina, katso [GitHubin julkaisuyleiskatsaus](https://github.com/webforj/webforj/releases) saadaksesi kattavamman luettelon muutoksista eri versioiden välillä.

## Jetty 12 -web-palvelimet {#jetty-12-web-servers}

webforJ 25.00 ja uudemmat käyttävät Jetty 12:ta, joka perustuu Jakarta EE10 -servlet-arkkitehtuuriin. Jos käytät Jetty Maven -pluginia kehityksessä, siirry Jakarta EE8:sta Jakarta EE10:een. Tämä päivitys vaatii myös kaiken, joka on riippuvainen `javax.servlet`-paketista, korvaamista `Jakarta.servlet`-paketilla.

### POM-tiedoston muutokset {#pom-file-changes}

**Ennen**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee8</groupId>
  <artifactId>jetty-ee8-maven-plugin</artifactId>
  <version>10.x.xx</version>
```
**Jälkeen**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee10</groupId>
  <artifactId>jetty-ee10-maven-plugin</artifactId>
  <version>12.x.xx</version>
```

## API-muutokset `App`-luokassa {#api-changes-for-the-app-class}

Useita vanhentuneita `App`-menetelmiä on poistettu versiossa 25.00. Seuraavat osiot kuvaavat, mitkä menetelmät on korvattu ja suositellut korvaukset.

### Konsolilokit {#console-logging}

Apuluokka [`BrowserConsole`](../advanced/browser-console.md), joka on omistettu muotoiltujen lokien luomiseen selainkonsoliin, korvasi `consoleLog()`- ja `consoleError()`-menetelmät. Hanki `BrowserConsole` käyttämällä `console()`-menetelmää:

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("Lokiviesti");
    console().error("Virheviesti");
  }
}
```

### Verkkovarasto {#web-storage}

Versioissa ennen webforJ 25.00 `App`-luokassa on menetelmät `getLocalStorage()`, `getSessionStorage()`, ja `getCookieStorage()`, joilla saadaan `LocalStorage`, `SessionStorage` ja `CookieStorage` -luokkien instanssit. Jatkossa jokaisella luokalla on `getCurrent()`-menetelmä.

Katso [Verkkovarasto](../advanced/web-storage.md) lisätietoja varten.

### `Request`-luokka {#request-class}

`Request`-luokka on nyt vastuussa sovelluksen URL-osoitteen, portin, isäntäosoitteen ja protokollan hankkimisesta. Joten käytä `App.getCurrent().getUrl()` sen sijaan, että käyttäisit `App.getUrl()`. `getCurrent()`-menetelmä korvasi myös `getRequest()`-menetelmän saadakseen `Request`-luokan instanssin.

:::info
`Request`-luokasta on myös poistettu menetelmiä, siirry [`Request`](#request-changes) nähdäksesi ne.
:::

### `Page`-luokka {#page-class}

`getPage()`-menetelmä on korvattu `Page.getCurrent()`-menetelmällä, jolla saadaan nykyinen sivuinstanssi.

### Valikkodialogit {#option-dialogs}

Sen sijaan, että käyttäisit `msgbox()`-menetelmää, käytä [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message), jotta voit luoda viestidialogejä.

### Sovelluksen lopettaminen {#app-termination}

`cleanup()`-menetelmä on poistettu. Nyt on kaksi menetelmää lopettamiseen: `onWillTerminate()` ja `onDidTerminate()`.

Katso [Lopettamisen koukut](../advanced/terminate-and-error-actions.md#hooks-for-termination) lisätietoja varten.

## Taulukon lajittelu {#table-sorting}

Versioissa webforJ 25.00 ja uudemmat taulukot käyttävät oletuksena yksittäistä sarakelajittelua. Sarakkeita lajitellaan vain viimeksi valitun sarakeotsikon mukaan. Jotta taulukko käyttäisi monisarakelajittelua, kutsu [`setMultiSorting()`](../components/table/sorting#multi-sorting) -menetelmää:

```java
table.setMultiSorting(true);
```

## Piilotettu `TabbedPane`-runko {#hidden-tabbedpane-body}

`hideBody()`-menetelmä on korvattu `setBodyHidden()`-menetelmällä, jotta menetelmien nimien yhdenmukaisuus säilyy.

## HTML:n renderöinti komponenttien sisällä {#rendering-html-inside-components}

Versioissa webforJ 25.00 ja uudemmat on olemassa `setHtml()`-menetelmä, joka auttaa erottamaan komponentin sisällä olevan kirjaimellisten ja HTML-tekstin asettamisen. HTML:n asettaminen `setText()`-menetelmällä on edelleen mahdollista, mutta se vaatii nyt selkeästi ympäröimään sen `<html>`-tageilla.

```java
// Voimassa olevat käyttötapaukset setText() ja setHtml()
Button home = new Button();

home.setText(""" 
  <html>
    <h1>Kotisivu</h1>
  </html>
""");

home.setHtml("<h1>Kotisivu</h1>");

home.setText("Kotisivu");
```

```java
// Virheelliset käyttötavat setText() ja setHtml()
Button home = new Button();
home.setText("<h1>Kotisivu</h1>");
```

## HTML-kontainerit {#html-containers}

`com.webforj.component.htmlcontainer`-paketti ei ole enää webforJ:ssä. Käytä sen sijaan monipuolisempaa `com.webforj.component.element`-pakettia. WebforJ:n luokkien luettelo vakiomuotoisille HTML-elementeille löytyy [HTML Element Components](../building-ui/web-components/html-elements.md).

## `Request`-muutokset {#request-changes}

- Aivan kuten `getCookieStorage()`-menetelmän poistaminen `App`-luokasta, `Request`-luokalla ei enää ole `getCookie()`-menetelmää. Tämä vahvistaa `CookieStorage.getCurrent()`-menetelmän käyttöä saadakseen `CookieStorage`-luokan instanssin.

- `getQueryParam()`-menetelmä on nyt nimeltään `getQueryParameter()`.

## `WebforjBBjBridge`-muutokset {#webforjbbjbridge-changes}

### `WebforjBBjBridge`-instanssin saaminen {#getting-an-instance-of-webforjbbjbridge}

`Environment`-luokassa ei enää ole `getWebforjHelper()`-menetelmää, joten käytä sen sijaan `getBridge()`-menetelmää.

### `ConfirmDialog`-komponentin käyttäminen `msgbox()`-menetelmässä {#using-the-confirmdialog-component-for-the-msgbox-method}

Aikaisemmissa webforJ-versioissa käytettiin merkkijonoja ja kokonaislukuja suoraan `WebforjBBjBridge`- `msgbox()`-menetelmässä. Kuitenkin viestit `WebforjBBjBridge`:ssä versiossa webforJ 25.00 ja uudemmat käyttävät [`ConfirmDialog`](../components/option-dialogs/confirm.md) -komponenttia. Tämä antaa enemmän hallintaa sen suhteen, mitkä painikkeet näkyvät ja minkä tyyppinen viesti on.

**Ennen**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("Oletko varma, että haluat poistaa tämän tiedoston?", 1, "Poisto");
```

**Jälkeen**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
      "Oletko varma, että haluat poistaa tämän tiedoston?", "Poisto",
      ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

## `PasswordMediation`-typo-korjaus {#passwordmediation-typo-correction}

Enum-luokka `PasswordMediation`, jota käytetään osoittamaan, onko käyttäjältä vaadittu kirjautuminen joka kerta, kun he vierailevat sovelluksessa, jossa on `Login`-komponentti, on aiemmissa webforJ-versioissa virheellinen. `SILENT` korvasi virheen `SILIENT` versiossa webforJ 25.00 ja uudemmissa.

## Automaattisen tarkennuksen menetelmät {#auto-focusing-methods}

Jotta webforJ olisi johdonmukainen, menetelmät kuten `setAutofocus()` ja `isAutofocus()` ovat nyt yhdenmukaisessa suureksi kirjoituksessa, kuten HasAutoFocus-rajapinnassa. Joten komponentit kuten `Dialog` ja `Drawer` käyttävät `setAutoFocus()` ja `isAutoFocus()` versiossa 25.00 ja uudemmissa.

## `BBjWindowAdapter` ja `Panel` merkitty `final` {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter`- ja `Panel`-luokat on nyt ilmoitettu `final`, mikä tarkoittaa, että niitä ei voi enää aliluokittaa. Tämä muutos parantaa vakautta ja tuo esiin johdonmukaisia käyttömallia.
