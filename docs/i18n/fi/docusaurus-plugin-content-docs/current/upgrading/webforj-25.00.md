---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: cbb2bd70fa3e51df1096018ff2519878
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämiseen versiosta 24.00 versioon 25.00. Tässä ovat muutokset, joita tarvitaan, jotta olemassa olevat sovellukset voivat jatkaa sujuvaa toimintaa. Kuten aina, katso [GitHubin julkaisun yleiskatsaus](https://github.com/webforj/webforj/releases) saadaksesi kattavamman luettelon muutoksista eri julkaisujen välillä.


## Jetty 12 -verkkopalvelimet {#jetty-12-web-servers}

webforJ 25.00 ja korkeammat versiot käyttävät Jetty 12:ta, joka perustuu Jakarta EE10 -servlet-arkkitehtuuriin. Jos käytät Jetty Maven -lisäosaa kehityksessä, siirry Jakarta EE8:sta Jakarta EE10:een. Tämä päivitys vaatii myös kaikkien `javax.servlet`-pakettiin tukeutuvien osien korvaamisen `Jakarta.servlet`-paketilla.

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

Useita poistettuja `App`-menetelmiä on poistettu versiosta 25.00. Seuraavissa osioissa kuvataan, mitkä menetelmät on korvattu ja suositellut vaihtoehdot.

### Konsoliloki {#console-logging}

Apuohjelmaluokka [`BrowserConsole`](/docs/advanced/browser-console), joka on omistettu tyyliteltyjen lokien luomiseen selainkonsoliin, korvasi `consoleLog()`- ja `consoleError()`-menetelmät. Hanki `BrowserConsole` käyttämällä `console()`-menetelmää:

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

Versioissa ennen webforJ 25.00 `App`-luokassa on metodit `getLocalStorage()`, `getSessionStorage()` ja `getCookieStorage()`, joilla saadaan `LocalStorage`, `SessionStorage` ja `CookieStorage` -luokkien instanssit. Jatkossa jokaisella luokalla on `getCurrent()`-menetelmä.

Katso [Web-tilavarasto](/docs/advanced/web-storage) saadaksesi lisätietoja.

### `Request`-luokka {#request-class}

`Request`-luokka on nyt vastuussa sovelluksen URL-osoitteen, portin, isännän ja protokollan saamista. Joten käytä `App.getCurrent().getUrl()` sen sijaan, että käyttäisit `App.getUrl()`. `getCurrent()`-menetelmä korvasi myös `getRequest()`-menetelmän saadaksesi `Request`-luokan instanssin.

:::info
`Request`-luokassa on myös poistettuja menetelmiä, siirry [`Request`](#request-changes) nähdäksesi ne.
:::

### `Page`-luokka {#page-class}

`getPage()`-menetelmä on korvattu `Page.getCurrent()`-menetelmällä saadaksesi nykyisen sivun instanssin.

### Valintaikkunat {#option-dialogs}

Käytä `msgbox()`-menetelmän sijaan [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) -menetelmää luodaksesi viesti-ikkunoita.

### Sovelluksen lopettaminen {#app-termination}

`cleanup()`-menetelmä on poistettu. Nyt on kaksi menetelmää lopettamiseen, `onWillTerminate()` ja `onDidTerminate()`.

Katso [Hooks for termination](/docs/advanced/terminate-and-error-actions#hooks-for-termination) saadaksesi lisätietoja.

## Taulukon lajittelu {#table-sorting}

Versioissa webforJ 25.00 ja korkeammissa taulukot käyttävät oletusarvoisesti yhden sarakkeen lajittelua. Sarakkeita lajitellaan vain viimeksi valitun sarakeotsikon mukaan. Jotta taulukko käyttäisi monisarakkeista lajittelua, kutsu [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) -menetelmää:

```java
table.setMultiSorting(true);
```

## Piilotettu `TabbedPane`-keho {#hidden-tabbedpane-body}

`hideBody()`-menetelmä on korvattu `setBodyHidden()`-menetelmällä suunnitelmallisen nimeämiskäytännön säilyttämiseksi.

## HTML:n renderöinti komponenttien sisällä {#rendering-html-inside-components}

Versioissa webforJ 25.00 ja korkeammissa on `setHtml()`-menetelmä, joka auttaa erottamaan litteän ja HTML-tekstin asettamisen komponentin sisällä. HTML:n asettaminen `setText()`-menetelmällä on edelleen mahdollista, mutta se vaatii nyt nimenomaisesti käärittäväksi `<html>`-tageihin.

```java
// Voimassa olevat käytöt setText() ja setHtml()
Button home = new Button();

home.setText("""
  <html>
    <h1>Aloitus</h1>
  </html>
""");

home.setHtml("<h1>Aloitus</h1>");

home.setText("Aloitus");
```

```java
// Voimattomat käytöt setText() ja setHtml()
Button home = new Button();
home.setText("<h1>Aloitus</h1>");
```

## HTML-kontit {#html-containers}

`com.webforj.component.htmlcontainer`-pakettia ei enää ole webforJ:ssä. Käytä sen sijaan monipuolisempaa `com.webforj.component.element`-pakettia. Saadaksesi luettelon webforJ-luokista standardeille HTML-elementeille, siirry [HTML Element Components](/docs/components/html-elements).

## `Request`-muutokset {#request-changes}

- Aivan kuten `getCookieStorage()`-menetelmän poisto `App`-luokasta, `Request`-luokassa ei enää ole `getCookie()`-menetelmää. Tämä vahvistaa `CookieStorgage.getCurrent()`-menetelmän käyttöä saadaksesi `CookieStorage`-luokan instanssin.

- `getQueryParam()`-menetelmä on nyt `getQueryParameter()`.

## `WebforjBBjBridge`-muutokset {#webforjbbjbridge-changes}

### `WebforjBBjBridge`-instanssin saaminen {#getting-an-instance-of-webforjbbjbridge}

`Environment`-luokassa ei enää ole `getWebforjHelper()`-menetelmää, joten käytä sen sijaan `getBridge()`-menetelmää.

### `ConfirmDialog`-komponentin käyttäminen `msgbox()`-menetelmälle {#using-the-confirmdialog-component-for-the-msgbox-method}

Aiemmat webforJ-versiot käyttivät merkkijonoja ja kokonaislukuja suoraan `WebforjBBjBridge`- `msgbox()`-menetelmälle. Kuitenkin viestit `WebforjBBjBridge`-sovelluksessa versiosta 25.00 ja korkeammissa käyttävät [`ConfirmDialog`](/docs/components/option-dialogs/confirm) -komponenttia. Tämä antaa enemmän hallintaa näytettävien painikkeiden ja viestityypin suhteen.


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

<!-- ## Environment.logError removed -->

## `PasswordMediation`-kirjoitusvirheen korjaus {#passwordmediation-typo-correction}

Enum-luokka `PasswordMediation`, jota käytetään osoittamaan, onko käyttäjän pakko kirjautua sisään joka käynnistämällä sovellusta, sisältää kirjoitusvirheen aiemmissa webforJ-versioissa. `SILENT` korvasi kirjoitusvirheen `SILIENT` webforJ 25.00 ja korkeammissa versioissa.

## Itsestään keskittyvät menetelmät {#auto-focusing-methods}

Säilyttääksesi webforJ:n johdonmukaisuuden, menetelmiä kuten `setAutofocus()` ja `isAutofocus()` on nyt yhdenmukaiset suurten alkukirjainten kanssa kuten HasAutoFocus-rajapinta. Joten komponentit kuten `Dialog` ja `Drawer` käyttävät `setAutoFocus()` ja `isAutoFocus()` versiossa 25.00 ja korkeammissa.

## `BBjWindowAdapter` ja `Panel` merkitty `final` {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter`- ja `Panel`-luokat on nyt määritelty `final` -avainsanalla, mikä tarkoittaa, että niitä ei voi enää aliluokitella. Tämä muutos parantaa vakautta ja pakottaa johdonmukaiset käyttötavat.
