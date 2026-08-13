---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 4343173cf0cbef440f24c05cf9ee3fbd
---
Assets-annotaatiot tarjoavat deklaratiivisen lähestymistavan ulkoisten ja inline-resurssien, kuten JavaScriptin ja CSS:n, upottamiseen sovellukseen staattisesti. Nämä annotaatiot yksinkertaistavat resurssien hallintaa takaamalla, että riippuvuudet ladataan oikeassa suoritusvaiheessa, vähentäen manuaalista konfigurointia ja parantaen ylläpidettävyyttä.

:::tip Bundler on oletusarvo npm:lle ja kehyksille
Asset-annotaatiot liittävät skriptin tai tyylitiedoston, jonka sinulla jo on, ilman rakennusvaihetta. Tuodaksesi npm-paketteja, komponenttikehyksia, kuten React, tai tyylitiedostokieltä, kuten SCSS, käytä [frontend-bundleria](/docs/managing-resources/bundler/overview). Se on oletuspolku tälle työlle, ja se tekee kaiken, mitä annotaatiotkin tekevät.
:::

## JavaScript-tiedostojen tuominen {#importing-javascript-files}

Deklaratiivinen JavaScriptin sisällyttäminen tuetaan `@JavaScript`-annotaation avulla, joka mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatio voidaan soveltaa sekä komponentitason että sovellustason.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Annotaatio hyväksyy suhteellisen tai täydellisen polun, joka ladataan sovellukseen. Tämä lisätään DOM:iin [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) -tagina. Lisäksi annotaatio tukee seuraavia ominaisuuksia:

| Ominaisuus    | Tyyppi    | Kuvaus                                                                                                                                       | Oletus  |
| ------------- | --------- | ------------------------------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`         | Boolean   | Määrittelee, tulisiko skripti injektoida ylimmälle tasolle                                                                               | `false` |
| `attributes`  | Object    | Koko joukko <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attribuutteja</JavadocLink>, joita sovelletaan skriptiin. | `{}`    |

#### Esimerkki: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Tiedostot ladataan vain, kun annotaatiota declaring komponentti on liitetty säiliöön. Jos useat komponentit lataavat saman tiedoston, tiedosto injektoidaan vain kerran.
:::

## JavaScriptin injektointi {#injecting-javascript}

Jotkut tapaukset saattavat vaatia, että haluat injektoida JavaScript-koodia suoraan DOM:iin sen sijaan, että tarjoaisit JavaScript-polun. `InlineJavaScript`-annotaatio sallii JavaScript-sisällön injektoinnin.

```java
@InlineJavaScript("alert('Olen inline-skripti!');")
@JavaScript("context://js/app.js")
```

| Ominaisuus    | Tyyppi    | Kuvaus                                                               | Oletus  |
| ------------- | --------- | --------------------------------------------------------------------- | ------- |
| `top`         | `Boolean` | Määrittelee, tulisiko skripti injektoida ylimmälle tasolle          | `false` |
| `attributes`  | `Object`  | Attribuutit, joita sovelletaan skriptiin                             | `{}`    |
| `id`          | `String`  | Yksilöllinen resurssitunnus, joka varmistaa yksittäisen injektoinnin | `""`    |

:::warning
Skriptejä voidaan injektoida useita kertoja käyttämällä `InlineJavaScript`-annotaatiota, ellei `id`-ominaisuudelle ole annettu erityistä ID:tä.
:::

## CSS-tiedostojen tuominen {#importing-css-files}

Deklaratiivinen CSS:n sisällyttäminen tuetaan `@StyleSheet`-annotaation avulla, joka mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatio voidaan soveltaa sekä komponentitason että sovellustason.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Ominaisuus    | Tyyppi    | Kuvaus                                                                  | Oletus  |
| ------------- | --------- | ----------------------------------------------------------------------- | ------- |
| `top`         | Boolean   | Määrittelee, tulisiko tyylitiedosto injektoida ylimmälle tasolle       | `false` |
| `attributes`  | Object    | Attribuutit, joita sovelletaan tyylitiedostoon                          | `{}`    |

#### Esimerkki: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Tiedostot ladataan vain, kun annotaatiota declaring komponentti on liitetty säiliöön. Jokainen tiedosto ladataan vain kerran.
:::

## CSS:n injektointi {#injecting-css}

`InlineStyleSheet`-annotaatio mahdollistaa CSS-sisällön injektoimisen suoraan verkkosivulle sekä komponenttitasolla että sovellustasolla.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Ominaisuus    | Tyyppi    | Kuvaus                                                                                                               | Oletus  |
| ------------- | --------- | --------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`         | Boolean   | Määrittelee, tulisiko tyylitiedoston injektoida sivun ylimmälle tasolle.                                            | `false` |
| `attributes`  | Object    | Koko joukko [attribuutteja](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), joita sovelletaan tyylielementtiin. | `{}`    |
| `id`          | String    | Yksilöllinen resurssitunnus. Jos useilla resursseilla on sama ID, ne ryhmitellään yhteen tyylielementtiin.            | `""`    |
| `once`        | Boolean   | Määrittelee, tuleeko tyylitiedoston injektoida sivulle vain kerran, riippumatta useista komponenttihakemista.      | `true`  |

:::tip
Parempaa syntaksin korostusta inline CSS:lle komponenttisi kirjoittamisessa voit käyttää webforJ VS Code -laajennusta: [Java HTML CSS Syntaksin korostaminen](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynaamisten resurssien hallinta ajonaikaisesti {#dynamic-assets-at-runtime}

Dynaaminen resurssien hallinta on mahdollista ohjelmallisen JavaScriptin ja CSS:n injektoinnin kautta ajonaikaisesti. Voit ladata tai injektoida resursseja ajonaikaisen kontekstin perusteella.

### JavaScriptin lataaminen ja injektointi {#loading-and-injecting-javascript}

Lataa tai injektoi JavaScript ajonaikaisesti käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink> -rajapintaa. Tämä mahdollistaa skriptien lataamisen URL-osoitteista tai inline-skriptien suoran injektoimisen DOM:iin.

```java
Page page = Page.getCurrent();

// Ladataan JavaScript-tiedostot
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injektoidaan inline JavaScript
page.addInlineJavaScript("console.log('Ajonaikainen injektio');");
page.addInlineJavaScript("alert('Tämä skripti ajetaan inline');");
```

| Parametri     | Kuvaus                                                                                                                    |
| ------------- | -------------------------------------------------------------------------------------------------------------------------- |
| `script`      | URL-osoite tai inline-skripti, joka injektoidaan. URL-osoitteet, jotka alkavat `context://`, liitetään sovelluksen juurivaraston kansioon. |
| `top`         | Määrittelee, tuleeko skripti injektoida sivun ylle.                                                                       |
| `attributes`  | Ominaisuusavain, jota asetetaan skriptille.                                                                              |

### CSS:n lataaminen ja injektointi {#loading-and-injecting-css}

Lataa tai injektoi CSS ajonaikaisesti käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink> -rajapintaa. Tämä mahdollistaa tyylitiedostojen lataamisen URL-osoitteista tai inline-tyylin suoran injektoimisen DOM:iin.

```java
Page page = Page.getCurrent();

// Ladataan CSS-tiedostot
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Injektoidaan inline CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parametri     | Kuvaus                                                                                                                     |
| ------------- | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet`  | URL-osoite tai inline-tyylitiedoston sisältö, joka injektoidaan. URL-osoitteet, jotka alkavat `context://`, liitetään sovelluksen juurivaraston kansioon. |
| `top`         | Määrittelee, tuleeko tyylitiedosto injektoida sivun ylle.                                                                   |
| `attributes`  | Ominaisuusavain, jota asetetaan tyylitiedostolle.                                                                          |
