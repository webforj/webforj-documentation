---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 8ebb2cb8863f97fcddea40fac13f71ce
---
Assets-annotaatiot tarjoavat deklaratiivisen lähestymistavan ulkoisten ja sisäisten resurssien, kuten JavaScriptin ja CSS:n, upottamiseen sovellukseen staattisesti. Nämä annotaatiot yksinkertaistavat resurssien hallintaa varmistamalla, että riippuvuudet ladataan sopivassa suoritusaikavaiheessa, vähentäen manuaalista konfigurointia ja parantaen ylläpidettävyyttä.

:::tip Pakettityökalu on oletusarvo npm:lle ja kehyksille
Resurssiannotaatiot liittävät jo olemassa olevan skriptin tai tyylitiedoston ilman rakennusvaihetta. Npm-pakettien tuomiseen, komponenttikehyksen, kuten Reactin, tai tyylitiedostokielen, kuten SCSS:n, käyttöön, käytä [frontend-pakettityökalua](/docs/managing-resources/bundler/overview). Se on oletuspolku tähän työhön, ja se tekee kaiken, mitä annotaatiot tekevät.
:::

## JavaScript-tiedostojen tuominen {#importing-javascript-files}

Deklaratiivinen JavaScriptin sisällyttäminen on tuettu `@JavaScript`-annotaation avulla, joka mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatio voidaan soveltaa sekä komponentin että sovelluksen tasolla.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Annotaatio hyväksyy suhteellisen tai täysin määritellyn polun, joka ladataan sovellukseen. Tämä lisätään DOM:iin [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) -täginä. Lisäksi annotaatio tukee seuraavia ominaisuuksia:

| Ominaisuus   | Tyyppi   | Kuvaus                                                                                                                                       | Oletus  |
| ------------ | -------  | --------------------------------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean  | Määrittelee, tuleeko skripti liittää ylimpään ikkunaan                                                                                        | `false` |
| `attributes` | Object   | Sarja <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attribuutteja</JavadocLink>, jotka sovelletaan skriptiin. | `{}`    |

#### Esimerkki: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Tiedostot ladataan vain, kun annotaatiota declaring komponentti on liitetty konttinaan. Jos useat komponentit lataavat saman tiedoston, tiedosto liitetään vain kerran.
:::

## JavaScriptin injektoiminen {#injecting-javascript}

Jossain tapauksessa saatat haluta injektoida JavaScript-koodia suoraan DOM:iin sen sijaan, että tarjoaisit JavaScript-polun. `InlineJavaScript`-annotaatio mahdollistaa JavaScript-sisällön injektoimisen.

```java
@InlineJavaScript("alert('Olen inline-skripti!');")
@JavaScript("context://js/app.js")
```

| Ominaisuus   | Tyyppi   | Kuvaus                                                               | Oletus  |
| ------------ | -------  | --------------------------------------------------------------------- | ------- |
| `top`        | `Boolean`| Määrittelee, tuleeko skripti liittää ylimpään ikkunaan               | `false` |
| `attributes` | `Object` | Attribuutit, jotka sovelletaan skriptiin                             | `{}`    |
| `id`         | `String` | Yksilöllinen resurssi-ID, joka varmistaa yksittäisen injektoinnin    | `""`    |

:::warning
Skriptejä voidaan injektoida useaan kertaan käyttämällä `InlineJavaScript`, ellei erityistä ID:tä ole määritetty `id`-ominaisuuden avulla.
:::

## CSS-tiedostojen tuominen {#importing-css-files}

Deklaratiivinen CSS:n sisällyttäminen on tuettu `@StyleSheet`-annotaation avulla, joka mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatio voidaan soveltaa sekä komponentin että sovelluksen tasolla.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Ominaisuus   | Tyyppi   | Kuvaus                                                                   | Oletus  |
| ------------ | -------  | ------------------------------------------------------------------------- | ------- |
| `top`        | Boolean  | Määrittelee, tuleeko tyylitiedoston liittää ylimpään ikkunaan           | `false` |
| `attributes` | Object   | Attribuutit, jotka sovelletaan tyylitiedostoon                           | `{}`    |

#### Esimerkki: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Tiedostot ladataan vain, kun annotaatiota declaring komponentti on liitetty konttinaan. Jokainen tiedosto ladataan vain kerran.
:::

## CSS:n injektoiminen {#injecting-css}

`InlineStyleSheet`-annotaatio mahdollistaa CSS-sisällön injektoimisen suoraan verkkosivulle sekä komponentin että sovelluksen tasolla.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Ominaisuus   | Tyyppi   | Kuvaus                                                                                                               | Oletus  |
| ------------ | -------  | ------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean  | Määrittelee, tuleeko tyylitiedoston liittää sivun ylimpään ikkunaan.                                               | `false` |
| `attributes` | Object   | Sarja [attribuutteja](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), jotka sovelletaan tyylielementtiin. | `{}`    |
| `id`         | String   | Yksilöllinen resurssi-ID. Jos useilla resursseilla on sama ID, ne yhdistetään yhteen tyylielementtiin.             | `""`    |
| `once`       | Boolean  | Määrittää, tullaanko tyylitiedoston injektoimaan sivulle vain kerran, riippumatta useista komponenttinstansseista. | `true`  |

:::tip 
Parempaa syntaksin korostusta inline-CSS:n kirjoittamiseen komponentteillesi voit käyttää webforJ VS Code -laajennusta: [Java HTML CSS Syntaksin Korostus](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynaamiset resurssit suoritusaikana {#dynamic-assets-at-runtime}

Dynaaminen resurssien hallinta on mahdollista JavaScriptin ja CSS:n ohjelmallisella injektoinnilla suoritusaikana. Voit ladata tai injektoida resursseja suoritusaikayhteyden perusteella.

### JavaScriptin lataaminen ja injektoiminen {#loading-and-injecting-javascript}

Lataa tai injektoi JavaScriptia dynaamisesti suoritusaikana käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>:ta. Tämä mahdollistaa skriptien lataamisen URL-osoitteista tai inline-skriptien injektoimisen suoraan DOM:iin.

```java
Page page = Page.getCurrent();

// JavaScript-tiedostojen lataaminen
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline-JavaScriptin injektoiminen
page.addInlineJavaScript("console.log('Suoritusaikainen injektio');");
page.addInlineJavaScript("alert('Tämä skripti toimii inline');");
```

| Parametri    | Kuvaus                                                                                                             |
| ------------ | ------------------------------------------------------------------------------------------------------------------ |
| `script`     | URL tai inline-skripti, joka injektoidaan. URL-osoitteet, jotka alkavat `context://`, ratkaisevat sovelluksen juuriresurssikansioon. |
| `top`        | Määrittää, tuleeko skripti injektoida sivun ylimpään osaan.                                                        |
| `attributes` | Aseta skriptille attribuuttikartta.                                                                                |

### CSS:n lataaminen ja injektoiminen {#loading-and-injecting-css}

Lataa tai injektoi CSS:ää dynaamisesti suoritusaikana käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>:ta. Tämä mahdollistaa tyylitiedostojen lataamisen URL-osoitteista tai inline-tyylien injektoimisen suoraan DOM:iin.

```java
Page page = Page.getCurrent();

// CSS-tiedostojen lataaminen
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inline-CSS:n injektoiminen
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parametri    | Kuvaus                                                                                                                  |
| ------------ | ------------------------------------------------------------------------------------------------------------------------ |
| `stylesheet` | URL tai inline-tyylitiedosto, joka injektoidaan. URL-osoitteet, jotka alkavat `context://`, ratkaisevat sovelluksen juuriresurssikansioon. |
| `top`        | Määrittää, tuleeko tyylitiedoston injektoida sivun ylimpään osaan.                                                      |
| `attributes` | Aseta tyylitiedostolle attribuuttikartta.                                                                                 |
