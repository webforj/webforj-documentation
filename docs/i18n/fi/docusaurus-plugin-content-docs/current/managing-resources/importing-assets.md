---
sidebar_position: 1
title: Importing Assets
_i18n_hash: a2aecab2ea12370f1e494703c2ec05af
---
Asset-annotaatioilla on julkinen lähestymistapa upottaa ulkoisia ja sisäisiä resursseja, kuten JavaScriptiä ja CSS:ää, applikaatioon statisesti. Nämä annotaatiot tehostavat resurssien hallintaa varmistaen, että riippuvuudet ladataan oikeassa suoritusvaiheessa, mikä vähentää manuaalista konfigurointia ja parantaa kunnossapidettävyyttä.

## JavaScript-tiedostojen tuonti {#importing-javascript-files}

Deklaratiivinen JavaScriptin sisällyttäminen on mahdollista `@JavaScript`-annotaation kautta, joka mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatioita voidaan käyttää sekä komponenttitasolla että sovelluskohtaisesti.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Annotaatio hyväksyy suhteellisen tai täydellisen polun, joka ladataan applikaatioon. Tämä lisätään DOM:iin [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script)-tagina. Lisäksi annotaatio tukee seuraavia ominaisuuksia:

| Ominaisuus   | Tyyppi   | Kuvaus                                                                                                                                       | Oletus  |
| ------------ | -------  | ------------------------------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean  | Määrittää, tuleeko skriptin olla injektoitu ylä-tason ikkunaan                                                                              | `false` |
| `attributes` | Object   | Aseta <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attribuutit</JavadocLink> skriptille.         | `{}`    |

#### Esimerkki: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Tiedostot ladataan vain silloin, kun annotaatiota julistava komponentti on liitetty konttiin. Jos useat komponentit lataavat saman tiedoston, tiedosto injektoidaan vain kerran.
:::

## JavaScriptin injektointi {#injecting-javascript}

Joissakin tapauksissa saatat haluta injektoida JavaScript-koodia suoraan DOM:iin sen sijaan, että tarjoaisit JavaScript-polun. `InlineJavaScript`-annotaatio sallii JavaScript-sisällön injektoimisen.

```java
@InlineJavaScript("alert('Olen inline-skripti!');")
@JavaScript("context://js/app.js")
```

| Ominaisuus   | Tyyppi    | Kuvaus                                                                | Oletus  |
| ------------ | -------   | ----------------------------------------------------------------------| ------- |
| `top`        | `Boolean` | Määrittää, tuleeko skriptin olla injektoitu ylä-tason ikkunaan      | `false` |
| `attributes` | `Object`  | Attribuutit, joita sovelletaan skriptiin                              | `{}`    |
| `id`         | `String`  | Yksilöllinen resurssi-ID varmistaaksesi ainutlaatuisen injektoinnin  | `""`    |

:::warning
Skriptejä voidaan injektoida useita kertoja `InlineJavaScript`:n avulla, ellei erityistä ID:tä ole annettu `id`-ominaisuuden avulla.
:::

## CSS-tiedostojen tuonti {#importing-css-files}

Deklaratiivinen CSS:n sisällyttäminen on mahdollista `@StyleSheet`-annotaation kautta, joka mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatioita voidaan käyttää sekä komponenttitasolla että sovelluskohtaisesti.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Ominaisuus   | Tyyppi    | Kuvaus                                                                     | Oletus  |
| ------------ | -------   |---------------------------------------------------------------------------| ------- |
| `top`        | Boolean   | Määrittää, tuleeko StyleSheetin olla injektoitu ylä-tason ikkunaan      | `false` |
| `attributes` | Object    | Attribuutit, joita sovelletaan StyleSheetiin                              | `{}`    |

#### Esimerkki: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Tiedostot ladataan vain silloin, kun annotaatiota julistava komponentti on liitetty konttiin. Jokainen tiedosto ladataan vain kerran.
:::

## CSS:n injektointi {#injecting-css}

`InlineStyleSheet`-annotaatio mahdollistaa CSS-sisällön suoran injektoimisen verkkosivulle sekä komponenttitasolla että sovelluskohtaisesti.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Ominaisuus   | Tyyppi    | Kuvaus                                                                                                             | Oletus  |
| ------------ | -------   |-------------------------------------------------------------------------------------------------------------------| ------- |
| `top`        | Boolean   | Määrittää, tuleeko StyleSheetin olla injektoitu sivun ylä-tason ikkunaan.                                       | `false` |
| `attributes` | Object    | Aseta [attribuutit](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), jotka sovelletaan tyyli-elementtiin. | `{}`    |
| `id`         | String    | Yksilöllinen resurssi-ID. Jos useilla resursseilla on sama ID, ne yhdistetään yhteen tyyli-elementtiin.            | `""`    |
| `once`       | Boolean   | Määrittää, tuleeko StyleSheetin olla injektoitu sivulle vain kerran riippumatta useista komponentti-instansseista. | `true`  |

:::tip 
Parhaan syntaksin korostuksen saavuttamiseksi inline CSS:ää kirjoitettaessa komponenttisi varten voit käyttää webforJ VS Code -laajennusta: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynaamiset resurssit ajonaikana {#dynamic-assets-at-runtime}

Dynaaminen resurssien hallinta on mahdollista ohjelmallisen JavaScriptin ja CSS:n injektoinnin avulla ajonaikana. Voit ladata tai injektoida resursseja ajonaikaisen kontekstin mukaan.

### JavaScriptin lataaminen ja injektointi {#loading-and-injecting-javascript}

Lataa tai injektoi JavaScriptiä dynaamisesti ajonaikana käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>:tä. Tämä mahdollistaa skriptien lataamisen URL-osoitteista tai inline-skriptien injektoimisen suoraan DOM:iin.

```java
Page page = Page.getCurrent();

// JavaScript-tiedostojen lataaminen
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline JavaScriptin injektointi
page.addInlineJavaScript("console.log('Ajon aikana injektointi');");
page.addInlineJavaScript("alert('Tämä skripti toimii inline');");
```

| Parametri    | Kuvaus                                                                                                             |
| ------------ | ----------------------------------------------------------------------------------------------------------------- |
| `script`     | URL-osoite tai inline-skripti, jota injektoidaan. URL-osoitteet, jotka alkavat `context://`, viittaavat applikaation juuresresurssikansioon. |
| `top`        | Määrittää, tuleeko skriptin olla injektoitu sivun ylle.                                                             |
| `attributes` | Aseta attribuuttien kartta skriptille.                                                                              |

### CSS:n lataaminen ja injektointi {#loading-and-injecting-css}

Lataa tai injektoi CSS:ää dynaamisesti ajonaikana käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>:tä. Tämä mahdollistaa tyylitiedostojen lataamisen URL-osoitteista tai inline-tyylien injektoimisen suoraan DOM:iin.

```java
Page page = Page.getCurrent();

// CSS-tiedostojen lataaminen
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inline CSS:n injektointi
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parametri    | Kuvaus                                                                                                                 |
| ------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | URL-osoite tai inline-StyleSheetin sisältö, jota injektoidaan. URL-osoitteet, jotka alkavat `context://`, viittaavat applikaation juuresresurssikansioon. |
| `top`        | Määrittää, tuleeko StyleSheetin olla injektoitu sivun ylle.                                                                |
| `attributes` | Aseta attribuuttien kartta StyleSheetille.                                                                               |
