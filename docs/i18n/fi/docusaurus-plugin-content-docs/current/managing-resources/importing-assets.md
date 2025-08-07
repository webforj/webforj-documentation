---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 356ee4ac3242f607ead40f76311ead79
---
Assets-annotaatiot tarjoavat deklaratiivisen lähestymistavan ulkoisten ja sisäisten resurssien, kuten JavaScriptin ja CSS:n, upottamiseen sovellukseen staattisesti. Nämä annotaatiot yksinkertaistavat resurssien hallintaa varmistamalla, että riippuvuudet ladataan oikeassa suoritusvaiheessa, mikä vähentää manuaalista konfigurointia ja parantaa ylläpidettävyyttä.

## JavaScript-tiedostojen tuonti {#importing-javascript-files}

Deklaratiivinen JavaScriptin sisällyttäminen on tuettu `@JavaScript`-annotaatiolla, joka mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatio voidaan soveltaa sekä komponenttitasolla että sovellustasolla.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Annotaatio hyväksyy suhteellisen tai täydellisen polun, joka ladataan sovellukseen. Tämä lisätään DOM:iin [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script)-tagina. Lisäksi annotaatio tukee seuraavia ominaisuuksia:

| Ominaisuus   | Tyyppi   | Kuvaus                                                                                                                                       | Oletus  |
| ------------ | -------  | --------------------------------------------------------------------------------------------------------------------------------------------| ------- |
| `top`        | Boolean  | Määrittää, tuleeko skripti injektoida pääikkunaan                                                                                         | `false` |
| `attributes` | Object   | Kokoelma <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attribuutteja</JavadocLink>, jotka sovelletaan skriptiin. | `{}`    |

#### Esimerkki: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Tiedostot ladataan vain silloin, kun annotaatiota ilmoittava komponentti on liitetty säiliöön. Jos useat komponentit lataavat saman tiedoston, tiedosto injektoidaan vain kerran.
:::

## JavaScriptin injektointi {#injecting-javascript}

Joissakin tapauksissa saatat haluta injektoida JavaScript-koodia suoraan DOM:iin sen sijaan, että tarjoat JavaScript-polun. `InlineJavaScript`-annotaatio mahdollistaa JavaScript-sisällön injektoinnin.

```java
@InlineJavaScript("alert('Olen inline-skripti!');")
@JavaScript("context://js/app.js")
```

| Ominaisuus   | Tyyppi    | Kuvaus                                                               | Oletus  |
| ------------ | -------   | ---------------------------------------------------------------------| ------- |
| `top`        | `Boolean` | Määrittää, tuleeko skripti injektoida pääikkunaan                   | `false` |
| `attributes` | `Object`  | Attribuutit, joita sovelletaan skriptiin                             | `{}`    |
| `id`         | `String`  | Yksilöllinen resurssi-ID varmistaakseen yhden injektoinnin           | `""`    |

:::warning
Skriptejä voidaan injektoida useita kertoja käyttämällä `InlineJavaScript`-annotaatiota, ellei tunnista aseteta erityistä ID:tä `id`-ominaisuudella.
:::

## CSS-tiedostojen tuonti {#importing-css-files}

Deklaratiivinen CSS:n sisällyttäminen on tuettu `@StyleSheet`-annotaatiolla, joka mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatio voidaan soveltaa sekä komponenttitasolla että sovellustasolla.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Ominaisuus   | Tyyppi   | Kuvaus                                                                   | Oletus  |
| ------------ | -------  | -------------------------------------------------------------------------| ------- |
| `top`        | Boolean  | Määrittää, tuleeko tyylitiedosto injektoida pääikkunaan                 | `false` |
| `attributes` | Object   | Attribuutit, joita sovelletaan tyylitiedostoon                          | `{}`    |

#### Esimerkki: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Tiedostot ladataan vain silloin, kun annotaatiota ilmoittava komponentti on liitetty säiliöön. Kukin tiedosto ladataan vain kerran.
:::

## CSS:n injektointi {#injecting-css}

`InlineStyleSheet`-annotaatio mahdollistaa CSS-sisällön injektoimisen suoraan verkkosivulle sekä komponenttitasolla että sovellustasolla.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Ominaisuus   | Tyyppi   | Kuvaus                                                                                                               | Oletus  |
| ------------ | -------  | -------------------------------------------------------------------------------------------------------------------| ------- |
| `top`        | Boolean  | Määrittää, tuleeko tyylitiedoston injektoida verkkosivun pääikkunaan.                                             | `false` |
| `attributes` | Object   | Kokoelma [attribuutteja](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), jotka sovelletaan tyyli-elementtiin. | `{}`    |
| `id`         | String   | Yksilöllinen resurssi-ID. Jos useilla resursseilla on sama ID, ne yhdistetään yhteen tyyli-elementtiin.             | `""`    |
| `once`       | Boolean  | Määrittää, tuleeko tyylitiedoston injektoida sivulle vain kerran riippumatta useista komponentti-instansseista.   | `true`  |

:::tip 
Parempaa syntaksin korostamista inline-CSS:llä komponentteja kirjoitettaessa voit käyttää webforJ VS Code -laajennusta: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynaamiset resurssit ajonaikana {#dynamic-assets-at-runtime}

Dynaaminen resurssien hallinta on mahdollista ohjelmallisen JavaScriptin ja CSS:n injektoinnin kautta ajonaikana. Voit ladata tai injektoida resursseja ajonaikaisen kontekstin perusteella.

### JavaScriptin lataaminen ja injektointi {#loading-and-injecting-javascript}

Lataa tai injektoi JavaScriptia dynaamisesti ajonaikana käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Tämä mahdollistaa skriptien lataamisen URL-osoitteista tai inline-skriptien injektoimisen suoraan DOM:iin.

```java
Page page = Page.getCurrent();

// JavaScript-tiedostojen lataaminen
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline-JavaScriptin injektointi
page.addInlineJavaScript("console.log('Ajonaikainen injektointi');");
page.addInlineJavaScript("alert('Tämä skripti suoritetaan inline');");
```

| Parametri    | Kuvaus                                                                                                             |
| ------------ | ----------------------------------------------------------------------------------------------------------------- |
| `script`     | URL tai inline-skripti, joka injektoidaan. URL-osoitteet, jotka alkavat `context://`, viittaavat sovelluksen juuritiedostoihin. |
| `top`        | Määrittää, tuleeko skripti injektoida sivun yläosaan.                                                             |
| `attributes` | Ominaisuuksia mapina, jotka asetetaan skriptille.                                                                  |

### CSS:n lataaminen ja injektointi {#loading-and-injecting-css}

Lataa tai injektoi CSS dynaamisesti ajonaikana käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Tämä mahdollistaa tyylitiedostojen lataamisen URL-osoitteista tai inline-tyylien injektoimisen suoraan DOM:iin.

```java
Page page = Page.getCurrent();

// CSS-tiedostojen lataaminen
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inline-CSS:n injektointi
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parametri    | Kuvaus                                                                                                                 |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | URL tai inline-tyylitiedoston sisältö, joka injektoidaan. URL-osoitteet, jotka alkavat `context://`, viittaavat sovelluksen juuritiedostoihin. |
| `top`        | Määrittää, tuleeko tyylitiedosto injektoida sivun yläosaan.                                                          |
| `attributes` | Ominaisuuksia mapina, jotka asetetaan tyylitiedostolle.                                                                |
