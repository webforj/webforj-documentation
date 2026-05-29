---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 53afcc2a74e5569086bcf7daeb6582d7
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämiseksi versiosta 24.00 versioon 25.00. Tässä ovat tarpeelliset muutokset, jotta olemassa olevat sovellukset voivat toimia sujuvasti. Kuten aina, katso [GitHubin julkaisun yleiskatsaus](https://github.com/webforj/webforj/releases) saadaksesi kattavamman listan muutoksista versioiden välillä.

## Jetty 12 web-palvelimet {#jetty-12-web-servers}

webforJ 25.00 ja korkeammilla versioilla käytetään Jetty 12:ta, joka hyödyntää Jakarta EE10 -servlettiarkkitehtuuria. Jos käytät Jetty Maven -liitintä kehityksessä, siirry Jakarta EE8:sta Jakarta EE10:ään. Tämä päivitys vaatii myös kaiken korvaamista, mikä on riippuvainen `javax.servlet`-paketista `Jakarta.servlet`-pakettiin.

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

Useita vanhentuneita `App`-menetelmiä on poistettu versiossa 25.00. Seuraavat osiot selittävät, mitkä menetelmät on korvattu ja mitä suositeltuja korvauksia on.

### Konsoli lokitus {#console-logging}

Utiliteetti luokka [`BrowserConsole`](/docs/advanced/browser-console), joka on omistettu tyyliteltyjen lokien luomiseen selaimen konsoliin, korvasi `consoleLog()` ja `consoleError()` menetelmät. Hanki `BrowserConsole` käyttäen `console()` menetelmää:

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("Lokiviesti");
    console().error("Virhesanoma");
  }
}
```

### Web-tallennus {#web-storage}

Versionssa webforJ 25.00 ja sitä aikaisemmin `App`-luokassa oli menetelmät `getLocalStorage()`, `getSessionStorage()` ja `getCookieStorage()` saadaksesi instansseja `LocalStorage`, `SessionStorage` ja `CookieStorage` luokista vastaavasti. Jatkossa jokaisella luokalla on `getCurrent()`-metodi.

Katso [Web Storage](/docs/advanced/web-storage) saadaksesi lisätietoja.

### `Request`-luokka {#request-class}

`Request`-luokka vastaa nyt sovelluksen URL-osoitteen, portin, isännän ja protokollan hakemisesta. Joten sen sijaan, että käyttäisit `App.getUrl()`, käytä `App.getCurrent().getUrl()`. `getCurrent()`-metodi korvasi myös `getRequest()`-metodin saadaksesi instanssin `Request`-luokasta.

:::info
`Request`-luokassa on myös poistettuja menetelmiä, siirry [`Request`](#request-changes) nähdäksesi ne.
:::

### `Page`-luokka {#page-class}

`getPage()`-metodi on korvattu `Page.getCurrent()`-menetelmällä saadaksesi nykyisen sivuinan.

### Valinta dialogit {#option-dialogs}

Sen sijaan, että käyttäisit `msgbox()`-metodia, käytä [`OptionDialog.showMessageDialog()`](/docs/components/option-dialogs/message) luodaksesi viestidialogeja.

### Sovelluksen lopettaminen {#app-termination}

`cleanup()`-metodi on poistettu. Nyt on kaksi menetelmää lopettamiseen, `onWillTerminate()` ja `onDidTerminate()`.

Katso [Hooks for termination](/docs/advanced/terminate-and-error-actions#hooks-for-termination) saadaksesi lisätietoja.

## Taulukon lajittelu {#table-sorting}

Versioissa webforJ 25.00 ja korkeammalla taulukot käyttävät oletuksena yhden sarakkeen lajittelua. Sarakkeita lajitellaan vain äskettäin valitun sarakeotsikon mukaan. Jotta taulukko käyttää monisarakelajittelua, kutsu [`setMultiSorting()`](/docs/components/table/sorting#multi-sorting) -metodia:

```java
table.setMultiSorting(true);
```

## Piilotettu `TabbedPane`-runko {#hidden-tabbedpane-body}

`hideBody()`-metodi on korvattu `setBodyHidden()`-metodilla, jotta menetelmiin saadaan yhtenäinen nimeämiskäytäntö.

## HTML:n renderöinti komponenteissa {#rendering-html-inside-components}

Versioissa webforJ 25.00 ja korkeammalla on `setHtml()`-metodi erottamaan literaalin ja HTML-tekstin asettaminen komponenttiin. HTML:n asettaminen `setText()`-metodin avulla on edelleen mahdollista, mutta se vaatii nyt sen erottavan `<html>`-tageilla.

```java
// Voimassa olevat käytännöt setText() ja setHtml()
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
// Virheelliset käytännöt setText() ja setHtml()
Button home = new Button();
home.setText("<h1>Koti</h1>");
```

## HTML-säiliöt {#html-containers}

Pakettia `com.webforj.component.htmlcontainer` ei ole enää webforJ:ssa. Käytä sen sijaan monipuolisempaa pakettia `com.webforj.component.element`. Listan webforJ-luokista standardeille HTML-elementeille löydät kohdasta [HTML Element Components](/docs/components/html-elements).

## `Request`-muutokset {#request-changes}

- Juuri kuten `getCookieStorage()`-metodin poistamisessa `App`-luokasta, `Request`-luokalla ei ole enää `getCookie()`-metodia. Tämä vahvistaa `CookieStorgage.getCurrent()`-menetelmän käyttöä saadaksesi instanssin `CookieStorage`-luokasta.

- `getQueryParam()`-metodi on nyt `getQueryParameter()`.

## `WebforjBBjBridge`-muutokset {#webforjbbjbridge-changes}

### `WebforjBBjBridge`-instanssin saaminen {#getting-an-instance-of-webforjbbjbridge}

`Environment`-luokalla ei ole enää `getWebforjHelper()`-metodia, joten käytä sen sijaan `getBridge()`.

### `ConfirmDialog`-komponentin käyttäminen `msgbox()`-metodille {#using-the-confirmdialog-component-for-the-msgbox-method}

Aiemmat webforJ-versiot käyttivät suoraan merkkijonoja ja kokonaislukuja `WebforjBBjBridge` `msgbox()`-metodissa. Kuitenkin, viestit `WebforjBBjBridge`-sovelluksessa webforJ 25.00 ja korkeammalla käyttävät [`ConfirmDialog`](/docs/components/option-dialogs/confirm) komponenttia. Tämä antaa enemmän hallintaa näyttävien painikkeiden ja viestityypin suhteen.


**Ennen**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("Haluatko varmasti poistaa tämän tiedoston?", 1, "Poisto");
```

**Jälkeen**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
  "Haluatko varmasti poistaa tämän tiedoston?", "Poisto",
  ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

## `PasswordMediation`-typo korjaus {#passwordmediation-typo-correction}

`PasswordMediation`-enum-luokka, jota käytetään osoittamaan, onko käyttäjän kirjauduttava sisään joka vierailulla sovelluksessa, jossa on `Login`-komponentti, sisältää typon aikaisemmissa webforJ-versioissa. `SILENT` korvasi kirjoitusvirheen `SILIENT` webforJ 25.00 ja korkeammissa versioissa.

## Automaattisesti kohdistavat menetelmät {#auto-focusing-methods}

Pitääkseen webforJ:n johdonmukaisena, menetelmät kuten `setAutofocus()` ja `isAutofocus()` saavat nyt yhtenäisen ison alkukirjaimen, kuten `HasAutoFocus`-rajapinta. Joten komponentit kuten `Dialog` ja `Drawer` käyttävät `setAutoFocus()` ja `isAutoFocus()` versioissa 25.00 ja korkeammalla.

## `BBjWindowAdapter` ja `Panel` merkitty `final`-nä {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter` ja `Panel` -luokat on nyt määritelty `final`, mikä tarkoittaa, että niitä ei voi enää aliluokkaistaa. Tämä muutos parantaa vakautta ja pakottaa yhtenäisiä käyttökuvioita.
