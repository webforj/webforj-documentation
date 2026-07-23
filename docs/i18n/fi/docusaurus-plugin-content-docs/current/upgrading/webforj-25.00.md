---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
sidebar_position: 30
_i18n_hash: 6fdaf15e67e0015f7319572200ccc353
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämiseksi versiosta 24.00 versioon 25.00. Tässä ovat vaatimukset olemassa oleville sovelluksille, jotta ne voivat jatkaa sujuvaa toimintaa. Kuten aina, katso [GitHubin julkaisu-yhteenveto](https://github.com/webforj/webforj/releases) saadaksesi kattavamman listan muutoksista versioiden välillä.

## Jetty 12 web-palvelimet {#jetty-12-web-servers}

webforJ 25.00 ja korkeammat versiot käyttävät Jetty 12:ta, joka perustuu Jakarta EE10 servlet-arkkitehtuuriin. Jos käytät Jetty Maven -lisäosaa kehityksessä, siirry Jakarta EE8:sta Jakarta EE10:een. Tämä päivitys vaatii myös kaiken, mikä tuki `javax.servlet`-pakettia, korvaamisen `Jakarta.servlet`-paketilla.

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

Useita vanhentuneita `App`-menetelmiä on poistettu versiossa 25.00. Seuraavat osiot kuvaavat, mitä menetelmiä on korvattu ja suositellut korvaukset.

### Konsolilokitus {#console-logging}

Apuluokka [`BrowserConsole`](/docs/advanced/browser-console), joka on omistettu tyylikkäiden lokien luomiseen selainkonsoliin, korvasi `consoleLog()` ja `consoleError()` -menetelmät. Hanki `BrowserConsole` käyttämällä `console()`-menetelmää:

```java
public class Application extends App{

  @Override
  public void run() throws WebforjException {
    console().log("Lokiviesti");
    console().error("Virheviesti");
  }
}
```

### Web-tallennus {#web-storage}

Versioissa, ennen webforJ 25.00, `App`-luokassa oli menetelmät `getLocalStorage()`, `getSessionStorage()`, ja `getCookieStorage()` saadakseen instanssit `LocalStorage`, `SessionStorage` ja `CookieStorage` -luokista. Tulevaisuudessa jokaisella luokalla on `getCurrent()`-menetelmä.

Katso [Web Tallennus](/docs/advanced/web-storage) lisätietoja varten.

### `Request`-luokka {#request-class}

`Request`-luokka on nyt vastuussa sovelluksen URL-osoitteen, portin, hostin ja protokollan hankkimisesta. Joten käyttäessäsi `App.getUrl()`, käytä `App.getCurrent().getUrl()`. `getCurrent()`-menetelmä korvasi myös `getRequest()`-menetelmän saadakseen instanssin `Request`-luokasta.

:::info
`Request`-luokassa on myös poistettuja metodeja, siirry [`Request`](#request-changes) nähdäksesi ne.
:::

### `Page`-luokka {#page-class}

`getPage()`-menetelmä on korvattu `Page.getCurrent()`-menetelmällä saadakseen nykyinen sivu.

### Valinta-dialiit {#option-dialogs}

Sen sijaan, että käyttäisit `msgbox()`-menetelmää, käytä [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) luodaksesi viestidialoja.

### Sovelluksen lopetus {#app-termination}

`cleanup()`-menetelmä on poistettu. Nyt on kaksi menetelmää lopetuksille, `onWillTerminate()` ja `onDidTerminate()`.

Katso [Hooks for termination](/docs/advanced/terminate-and-error-actions#hooks-for-termination) lisätietoja varten.

## Taulukon lajittelu {#table-sorting}

webforJ 25.00 ja korkeammat versiot käyttävät oletuksena yhden sarakkeen lajittelua taulukoissa. Sarakkeet lajitellaan vain viimeksi valitun sarakeotsikon mukaan. Jotta taulukko käyttäisi monisarakkeista lajittelua, kutsu [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) -menetelmää:

```java
table.setMultiSorting(true);
```

## Piilotettu `TabbedPane`-keho {#hidden-tabbedpane-body}

`hideBody()`-menetelmä on korvattu `setBodyHidden()`-menetelmällä, jotta menetelmien nimikäytännöt pysyvät johdonmukaisina.

## HTML:n renderöinti komponenteissa {#rendering-html-inside-components}

webforJ 25.00 ja korkeammat versiot sisältävät `setHtml()`-menetelmän, joka auttaa erottamaan komponentin sisällä asetettavan kirjaimellisen ja HTML-tekstin. HTML:n asettaminen `setText()`-menetelmällä on edelleen mahdollista, mutta nyt se vaatii erikseen `<html>`-tageihin kääretyn tekstin.

```java
// Kelvolliset käytöt setText() ja setHtml()
Button home = new Button();

home.setText("""
  <html>
    <h1>Koti</h1>
  </html>
""");

home.setHtml("<h1>Koti</h1>");

home.setText("Koti");
```

```java
// Kelvottomat käytöt setText() ja setHtml()
Button home = new Button();
home.setText("<h1>Koti</h1>");
```

## HTML-kontit {#html-containers}

`com.webforj.component.htmlcontainer`-pakettia ei enää ole webforJ:ssä. Käytä sen sijaan monipuolisempaa `com.webforj.component.element`-pakettia. Lista webforJ-luokista standardeille HTML-Elementeille löytyy [HTML Element Components](/docs/components/html-elements).

## `Request` muutokset {#request-changes}

- Aivan kuten `getCookieStorage()`-menetelmän poistaminen `App`-luokasta, `Request`-luokalla ei enää ole `getCookie()`-menetelmää. Tämä vahvistaa `CookieStorgage.getCurrent()`-menetelmän käyttöä saadakseen instanssin `CookieStorage`-luokasta.

- `getQueryParam()`-menetelmä on nyt `getQueryParameter()`.

## `WebforjBBjBridge` muutokset {#webforjbbjbridge-changes}

### `WebforjBBjBridge`-instanssin hankkiminen {#getting-an-instance-of-webforjbbjbridge}

`Environment`-luokassa ei enää ole `getWebforjHelper()`-menetelmää, joten käytä `getBridge()` sen sijaan.

### `ConfirmDialog` komponentin käyttäminen `msgbox()`-menetelmälle {#using-the-confirmdialog-component-for-the-msgbox-method}

Aikaisemmissa webforJ-versioissa käytettiin suoraan merkkijonoja ja kokonaislukuja `WebforjBBjBridge`- `msgbox()`-menetelmälle. Kuitenkin viestit `WebforjBBjBridge`-sovelluksessa webforJ 25.00 ja korkeammissa versioissa käyttävät [`ConfirmDialog`](/docs/components/option-dialogs/confirm) komponenttia. Tämä antaa enemmän hallintaa siitä, mitkä painikkeet näytetään ja millaista viestiä käytetään.

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

<!-- ## Environment.logError poistettu -->

## `PasswordMediation` kirjoitusvirheen korjaus {#passwordmediation-typo-correction}

Enum-luokka `PasswordMediation`, jota käytetään osoittamaan, vaaditaanko käyttäjältä kirjautuminen joka käynnistyksellä sovelluksessa, jossa on `Login`-komponentti, sisältää kirjoitusvirheen aiemmissa webforJ-versioissa. `SILENT` korvasi kirjoitusvirheen `SILIENT` webforJ 25.00 ja korkeammissa versioissa.

## Itsestään keskittymisen menetelmät {#auto-focusing-methods}

WebforJ:n johdonmukaisuuden säilyttämiseksi menetelmät kuten `setAutofocus()` ja `isAutofocus()` saavat nyt yhdenmukaisen päättämisen kuten HasAutoFocus -rajapinta. Joten komponentit kuten `Dialog` ja `Drawer` käyttävät `setAutoFocus()` ja `isAutoFocus()` versioissa 25.00 ja korkeammissa.

## `BBjWindowAdapter` ja `Panel` merkitty `final` {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter` ja `Panel` -luokat on nyt merkitty `final`, mikä tarkoittaa, etteivät niitä voi enää periyttää. Tämä muutos parantaa vakautta ja vahvistaa johdonmukaisia käyttökaavoja.
