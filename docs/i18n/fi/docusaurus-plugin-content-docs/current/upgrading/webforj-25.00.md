---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
_i18n_hash: 71f184a02c2552f5af34bfc3ec47c385
---
Tämä dokumentaatio toimii oppaana webforJ-sovellusten päivittämiseksi versiosta 24.00 versioon 25.00. Tässä ovat tarvittavat muutokset, jotta olemassa olevat sovellukset voivat toimia sujuvasti. Kuten aina, katso [GitHub julkaisun yhteenveto](https://github.com/webforj/webforj/releases) saadaksesi kattavamman listan muutoksista julkaisujen välillä.

## Jetty 12 web-palvelimet {#jetty-12-web-servers}

webforJ 25.00 ja uudemmat versiot hyödyntävät Jetty 12:ta, joka käyttää Jakarta EE10 servlet-arkkitehtuuria. Jos käytät Jetty Maven -pluginia kehityksessä, siirry Jakarta EE8:sta Jakarta EE10:een. Tämä päivitys vaatii myös kaikki `javax.servlet`-pakettiin perustuvat asiat korvattavaksi `Jakarta.servlet` -paketilla.

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

Useita vanhentuneita `App`-metodeja on poistettu versiossa 25.00. Seuraavat osiot ohjaavat, mitkä metodit on korvattu ja mitkä korvaukset suositellaan.

### Konsolilokitus {#console-logging}

Apuluokka [`BrowserConsole`](../advanced/browser-console.md), joka on omistettu tyyliteltyjen lokien luomiseen selainkonsoliin, korvasi `consoleLog()` ja `consoleError()` -metodit. Hanki `BrowserConsole` käyttämällä `console()`-metodia:

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

Versioissa ennen webforJ 25.00 `App`-luokalla on metodit `getLocalStorage()`, `getSessionStorage()` ja `getCookieStorage()`, joilla voi saada instansseja `LocalStorage`, `SessionStorage` ja `CookieStorage` -luokista. Jatkossa jokaisella luokalla on `getCurrent()`-metodi.

Katso [Web Storage](../advanced/web-storage.md) saadaksesi lisätietoja.

### `Request`-luokka {#request-class}

`Request`-luokka on nyt vastuussa sovelluksen URL-osoitteen, portin, isäntänimen ja protokollan hakemisesta. Joten käytä `App.getCurrent().getUrl()` sen sijaan, että käyttäisit `App.getUrl()`. `getCurrent()`-metodi korvasi myös `getRequest()`-metodin `Request`-luokan instanssin saamista varten.

:::info
`Request`-luokalta on myös poistettu metodeja, hyppää [`Request`](#request-changes) nähdäksesi ne.
:::

### `Page`-luokka {#page-class}

`getPage()`-metodi on korvattu `Page.getCurrent()`-metodilla, jolla saadaan nykyisen sivun instanssi.

### Valikkodialogit {#option-dialogs}

Käytä [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message) luodaksesi viestidialogeja sen sijaan, että käyttäisit `msgbox()`-metodia.

### Sovelluksen päättäminen {#app-termination}

`cleanup()`-metodia on poistettu. Nyt on kaksi metodia päättämiseen: `onWillTerminate()` ja `onDidTerminate()`.

Katso [Hooks for termination](../advanced/terminate-and-error-actions.md#hooks-for-termination) saadaksesi lisätietoja.

## Taulukoiden lajittelu {#table-sorting}

webforJ 25.00 ja uudemmat versiot käyttävät oletuksena yksittäis-sarakelajittelua. Sarakkeet lajitellaan vain viimeksi valitun sarakeotsikon mukaan. Jos haluat taulukon käyttävän monisarakkeista lajittelua, kutsu [`setMultiSorting()`](../components/table/sorting#multi-sorting) -metodia:

```java
table.setMultiSorting(true);
```

## Piilotettu `TabbedPane`-runko {#hidden-tabbedpane-body}

`hideBody()`-metodi on korvattu `setBodyHidden()`-metodilla, jotta metodien nimivihjesäännöt pysyvät johdonmukaisina.

## HTML:n renderöinti komponenttien sisällä {#rendering-html-inside-components}

webforJ 25.00 ja uudemmat versiot sisältävät `setHtml()`-metodin, joka auttaa erottamaan, milloin asetetaan kirjaimellista ja milloin HTML-tekstiä komponenttiin. HTML:n asettaminen `setText()`-metodilla on edelleen mahdollista, mutta se vaatii nyt, että se on nimenomaan kääritty `<html>`-tageihin.

```java
// Validoitu käyttö setText() ja setHtml() -metodeista
Button home = new Button();

home.setText("""
  <html>
    <h1>Etusivu</h1>
  </html>
""");

home.setHtml("<h1>Etusivu</h1>");

home.setText("Etusivu");
```

```java
// Epävalidit käytöt setText() ja setHtml()
Button home = new Button();
home.setText("<h1>Etusivu</h1>");
```

## HTML-kontit {#html-containers}

`com.webforj.component.htmlcontainer` -paketti ei ole enää käytössä webforJ:ssä. Käytä sen sijaan monipuolisempaa `com.webforj.component.element` -pakettia. Saadaksesi luettelon webforJ-luokista standardille HTML-elementille, siirry [HTML Element Components](../building-ui/web-components/html-elements.md).

## `Request`-muutokset {#request-changes}

- Aivan kuten `getCookieStorage()`-metodin poistaminen `App`-luokasta, `Request`-luokalla ei enää ole `getCookie()` -metodia. Tämä vahvistaa käytön `CookieStorgage.getCurrent()` saadaksesi instanssin `CookieStorage` -luokasta.

- `getQueryParam()`-metodin nimi on nyt `getQueryParameter()`.

## `WebforjBBjBridge`-muutokset {#webforjbbjbridge-changes}

### Instanssin saaminen `WebforjBBjBridge` {#getting-an-instance-of-webforjbbjbridge}

`Environment`-luokalla ei enää ole `getWebforjHelper()` -metodia, joten käytä `getBridge()` sen sijaan.

### `ConfirmDialog`-komponentin käyttö `msgbox()`-metodin sijasta {#using-the-confirmdialog-component-for-the-msgbox-method}

WebforJ:n aikaisemmissa versioissa käytettiin suoraan merkkijonoja ja kokonaislukuja `WebforjBBjBridge` `msgbox()`-metodissa. Kuitenkin, webforJ 25.00 ja uudemmat versiot käyttävät [`ConfirmDialog`](../components/option-dialogs/confirm.md) komponenttia `WebforjBBjBridge`:lla. Tämä antaa enemmän hallintaa siitä, mitä painikkeita näytetään ja minkä tyyppisiä viestejä käytetään.

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

## `PasswordMediation`-typo korjaus {#passwordmediation-typo-correction}

Enum-luokassa `PasswordMediation`, jota käytetään ilmoittamaan, tarvitseeko käyttäjän kirjautua sisään joka vierailulla sovelluksessa, jossa on `Login`-komponentti, on aikaisemmissa webforJ-versioissa kirjoitusvirhe. `SILENT` korvasi virheellisen `SILIENT` webforJ 25.00 ja uudemmissa versioissa.

## Automaattinen fokusointi {#auto-focusing-methods}

Käyttääkseen webforJ:ta johdonmukaisesti, kuten `setAutofocus()` ja `isAutofocus()` -metodit, nyt on yksittäinen päättely kuten `HasAutoFocus`-rajapinta. Joten komponentit kuten `Dialog` ja `Drawer` käyttävät `setAutoFocus()` ja `isAutoFocus()` versiossa 25.00 ja uudemmissa.

## `BBjWindowAdapter` ja `Panel` merkitty `final` {#bbjwindowadapter-and-panel-marked-as-final}

`BBjWindowAdapter` ja `Panel`-luokat on nyt määritelty `final`, mikä tarkoittaa, että niitä ei voi enää aliluokitella. Tämä muutos parantaa vakautta ja vahvistaa johdonmukaisia käyttökuvioita.
