---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 0256f553c96c490015f9e19b1eeea533
---
Assets annotations tarjoavat deklaratiivisen lähestymistavan ulkoisten ja sisäisten resurssien, kuten JavaScriptin ja CSS:n, upottamiseen sovellukseen staattisesti. Nämä annotaatiot virtaviivaistavat resurssien hallintaa varmistamalla, että riippuvuudet ladataan asianmukaisessa suoritusvaiheessa, mikä vähentää manuaalista konfigurointia ja parantaa ylläpidettävyyttä.

## JavaScript-tiedostojen tuominen {#importing-javascript-files}

Deklaratiivinen JavaScriptin sisällyttäminen on tuettu `@JavaScript`-annotaation kautta, mikä mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatio voidaan soveltaa sekä komponenttitasolla että sovellustasolla.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Annotaatio hyväksyy suhteellisen tai täydellisen polun, joka ladataan sovellukseen. Tämä lisätään DOM:iin [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) -tagina. Lisäksi annotaatio tukee seuraavia ominaisuuksia:

| Ominaisuus   | Tyyppi   | Kuvaus                                                                                                                                       | Oletus  |
| ------------ | -------  | ---------------------------------------------------------------------------------------------------------------------------------------------| ------- |
| `top`        | Boolean  | Määrittää, tulisiko skripti injektoida ylimmälle tasolle                                                                                   | `false` |
| `attributes` | Object   | Setti <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>atribuutteja</JavadocLink>, jotka sovelletaan skriptiin. | `{}`    |

#### Esimerkki: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Tiedostot ladataan vain, kun komponentti, joka määrittelee annotaation, on liitetty säiliöön. Jos useat komponentit lataavat saman tiedoston, tiedosto injektoidaan vain kerran.
:::

## JavaScriptin injektoiminen {#injecting-javascript}

Jossakin tapauksessa saatat haluta injektoida JavaScript-koodia suoraan DOM:iin sen sijaan, että tarjoaisit JavaScript-polun. `InlineJavaScript` -annotaatio mahdollistaa JavaScript-sisällön injektoimisen.

```java
@InlineJavaScript("alert('Olen sisäinen skripti!');")
@JavaScript("context://js/app.js")
```

| Ominaisuus   | Tyyppi    | Kuvaus                                                               | Oletus  |
| ------------ | -------   | ---------------------------------------------------------------------| -------  |
| `top`        | `Boolean` | Määrittää, tulisiko skripti injektoida ylimmälle tasolle           | `false` |
| `attributes` | `Object`  | Atribuutit, jotka sovelletaan skriptiin                              | `{}`    |
| `id`         | `String`  | Ainulaatuinen resurssi-ID, joka varmistaa yhden injektion            | `""`    |

:::warning
Skriptejä voidaan injektoida useita kertoja `InlineJavaScript`-annotaation avulla, ellei erityistä ID:tä ole määritetty käyttämällä `id`-ominaisuutta.
:::

## CSS-tiedostojen tuominen {#importing-css-files}

Deklaratiivinen CSS:n sisällyttäminen on tuettu `@StyleSheet` -annotaation kautta, mikä mahdollistaa automaattisen riippuvuuksien lataamisen. Annotaatio voidaan soveltaa sekä komponenttitasolla että sovellustasolla.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Ominaisuus   | Tyyppi   | Kuvaus                                                                   | Oletus  |
| ------------ | -------  | ------------------------------------------------------------------------- | ------- |
| `top`        | Boolean  | Määrittää, tulisiko StyleSheet injektoida ylimmälle tasolle            | `false` |
| `attributes` | Object   | Atribuutit, jotka sovelletaan StyleSheetille                             | `{}`    |

#### Esimerkki: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Tiedostot ladataan vain, kun komponentti, joka määrittelee annotaation, on liitetty säiliöön. Jokainen tiedosto ladataan vain kerran.
:::

## CSS:n injektoiminen {#injecting-css}

`InlineStyleSheet` -annotaatio mahdollistaa CSS-sisällön injektoimisen suoraan verkkosivulle sekä komponenttitasolla että sovellustasolla.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Ominaisuus   | Tyyppi   | Kuvaus                                                                                                               | Oletus  |
| ------------ | -------  | --------------------------------------------------------------------------------------------------------------------- | ------- |
| `top`        | Boolean  | Määrittää, tulisiko StyleSheet injektoida verkkosivun ylimmälle tasolle.                                            | `false` |
| `attributes` | Object   | Setti [attribuutteja](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), jotka sovelletaan tyyli elementtiin. | `{}`    |
| `id`         | String   | Ainulaatuinen resurssi-ID. Jos useat resurssit sisältävät saman ID:n, ne yhdistetään yhteen tyyli elementtiin.       | `""`    |
| `once`       | Boolean  | Määrittää, injektoidaanko StyleSheet sivulle vain kerran, riippumatta useista komponenttiinstansseista.             | `true`  |

:::tip 
Parempaa syntaksin korostusta inline-CSS:n kirjoittamiselle komponenteillesi varten voit käyttää webforJ VS Code -laajennusta: [Java HTML CSS Syntaksin korostus](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynaamiset resurssit ajon aikana {#dynamic-assets-at-runtime}

Dynaaminen resurssien hallinta on mahdollista JavaScriptin ja CSS:n ohjelmallisen injektoinnin avulla ajon aikana. Voit ladata tai injektoida resursseja ajon aikaisesta kontekstista riippuen.

### JavaScriptin lataaminen ja injektoiminen {#loading-and-injecting-javascript}

Lataa tai injektoi JavaScript ajon aikana käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>:ta. Tämä mahdollistaa skriptien lataamisen URL-osoitteista tai inline-skriptien injektoimisen suoraan DOM:iin.

```java
Page page = Page.getCurrent();

// Lataaminen JavaScript-tiedostoja
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline JavaScriptin injektoiminen
page.addInlineJavaScript("console.log('Ajon aikainen injektio');");
page.addInlineJavaScript("alert('Tämä skripti suoritetaan inline');");
```

| Parametri    | Kuvaus                                                                                                               |
| ------------ | --------------------------------------------------------------------------------------------------------------------- |
| `script`     | URL-osoite tai inline-skripti-in sisältö, joka injektoidaan. URL-osoitteet, jotka alkavat `context://`, viittaavat sovelluksen juuresresurssikansioon. |
| `top`        | Määrittää, tulisiko skripti injektoida sivun yläosaan.                                                              |
| `attributes` | Map-a attribuuteista, jotka asetetaan skriptille.                                                                    |

### CSS:n lataaminen ja injektoiminen {#loading-and-injecting-css}

Lataa tai injektoi CSS ajon aikana käyttämällä <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>:ta. Tämä mahdollistaa tyylitiedostojen lataamisen URL-osoitteista tai inline-tyylien injektoimisen suoraan DOM:iin.

```java
Page page = Page.getCurrent();

// Lataaminen CSS-tiedostoja
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inline CSS:n injektoiminen
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parametri    | Kuvaus                                                                                                                 |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | URL-osoite tai inline StyleSheet -sisältö, joka injektoidaan. URL-osoitteet, jotka alkavat `context://`, viittaavat sovelluksen juuresresurssikansioon. |
| `top`        | Määrittää, tulisiko StyleSheet injektoida sivun yläosaan.                                                              |
| `attributes` | Map-a attribuuteista, jotka asetetaan StyleSheetille.                                                                  |
