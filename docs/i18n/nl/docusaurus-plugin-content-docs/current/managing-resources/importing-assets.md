---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 8ebb2cb8863f97fcddea40fac13f71ce
---
Assets annotaties bieden een declaratieve benadering voor het statisch inbedden van externe en inline resources zoals JavaScript en CSS binnen een app. Deze annotaties stroomlijnen het resourcebeheer door ervoor te zorgen dat afhankelijkheden worden geladen in de juiste uitvoeringsfase, wat de handmatige configuratie vermindert en de onderhoudbaarheid verbetert.

:::tip De bundler is de standaard voor npm en frameworks
De asset annotaties koppelen een script of stylesheet die je al hebt, zonder build-stap. Om npm-pakketten, een componentenframework zoals React, of een stylesheet-taal zoals SCSS te gebruiken, volg je het [frontend bundler](/docs/managing-resources/bundler/overview). Het is het standaard pad voor dat werk en het doet alles wat de annotaties doen.
:::

## Importeren van JavaScript-bestanden {#importing-javascript-files}

Declaratieve JavaScript-inclusie wordt ondersteund via de `@JavaScript` annotatie, waarmee automatische afhankelijkheidslading mogelijk is. De annotatie kan zowel op componentniveau als op app-niveau worden toegepast.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

De annotatie accepteert een relatieve of volledige pad die in de app moet worden geladen. Dit zal in de DOM worden ingevoegd als een [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) tag. Bovendien ondersteunt de annotatie de volgende eigenschappen:

| Eigenschap   | Type    | Beschrijving                                                                                                                                       | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of het script in het top-level venster moet worden geïnjecteerd                                                                         | `false`   |
| `attributes` | Object  | Een set van <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attributen</JavadocLink> die op het script moeten worden toegepast. | `{}`      |

#### Voorbeeld: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie declareert aan een container is bevestigd. Als meerdere componenten hetzelfde bestand laden, wordt het bestand slechts eenmaal geïnjecteerd.
:::

## Injecteren van JavaScript {#injecting-javascript}

In sommige gevallen wil je misschien JavaScript-code rechtstreeks in de DOM injecteren in plaats van een JavaScript-pad op te geven. De `InlineJavaScript` annotatie stelt je in staat om JavaScript-inhoud te injecteren.

```java
@InlineJavaScript("alert('Ik ben een inline script!');")
@JavaScript("context://js/app.js")
```

| Eigenschap   | Type    | Beschrijving                                                               | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------- | --------- |
| `top`        | `Boolean` | Geeft aan of het script in het top-level venster moet worden geïnjecteerd | `false`   |
| `attributes` | `Object`  | Attributen die op het script moeten worden toegepast                     | `{}`      |
| `id`         | `String`  | Een unieke resource-ID om een enkele injectie te garanderen              | `""`      |

:::waarschuwing
Scripts kunnen meerdere keren worden geïnjecteerd met `InlineJavaScript`, tenzij een specifieke ID is toegewezen met de `id` eigenschap.
:::

## Importeren van CSS-bestanden {#importing-css-files}

Declaratieve CSS-inclusie wordt ondersteund via de `@StyleSheet` annotatie, waarmee automatische afhankelijkheidslading mogelijk is. De annotatie kan zowel op componentniveau als op app-niveau worden toegepast.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschap   | Type    | Beschrijving                                                                   | Standaard |
| ------------ | ------- | ----------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of de StyleSheet in het top-level venster moet worden geïnjecteerd | `false`   |
| `attributes` | Object  | Attributen die op de StyleSheet moeten worden toegepast                      | `{}`      |

#### Voorbeeld: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie declareert aan een container is bevestigd. Elk bestand wordt slechts eenmaal geladen.
:::

## Injecteren van CSS {#injecting-css}

De `InlineStyleSheet` annotatie stelt je in staat om CSS-inhoud rechtstreeks in een webpagina te injecteren op zowel componentniveau als app-niveau.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschap   | Type    | Beschrijving                                                                                                               | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of de StyleSheet in het top-level venster van de pagina moet worden geïnjecteerd.                             | `false`   |
| `attributes` | Object  | Een set van [attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) die op het stijl element moeten worden toegepast. | `{}`      |
| `id`         | String  | Een unieke resource-ID. Als meerdere resources dezelfde ID hebben, worden ze samengevoegd in één stijl element.         | `""`      |
| `once`       | Boolean | Bepaalt of de StyleSheet slechts één keer in de pagina moet worden geïnjecteerd, ongeacht meerdere componentinstanties. | `true`    |

:::tip 
Voor betere syntaxisverlichting bij het schrijven van inline CSS voor je componenten, kun je de webforJ VS Code extensie gebruiken: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische assets tijdens runtime {#dynamic-assets-at-runtime}

Dynamisch resourcebeheer is mogelijk door middel van programmatical injectie van JavaScript en CSS tijdens runtime. Je kunt resources laden of injecteren op basis van de runtime-context.

### Laden en injecteren van JavaScript {#loading-and-injecting-javascript}

Laad of injecteer JavaScript dynamisch tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Hiermee kun je scripts vanaf URL's laden of inline scripts rechtstreeks in de DOM injecteren.

```java
Page page = Page.getCurrent();

// JavaScript-bestanden laden
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline JavaScript injecteren
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('Dit script draait inline');");
```

| Parameter    | Beschrijving                                                                                                             |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| `script`     | De URL of inline script inhoud die moet worden geïnjecteerd. URL's die beginnen met `context://` lossen op naar de rootresourcesmap van de app. |
| `top`        | Bepaalt of het script aan de bovenkant van de pagina moet worden geïnjecteerd.                                           |
| `attributes` | Een kaart van attributen om voor het script in te stellen.                                                              |

### Laden en injecteren van CSS {#loading-and-injecting-css}

Laad of injecteer CSS dynamisch tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Hiermee kun je stijlen vanaf URL's laden of inline stijlen rechtstreeks in de DOM injecteren.

```java
Page page = Page.getCurrent();

// CSS-bestanden laden
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inline CSS injecteren
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parameter    | Beschrijving                                                                                                                 |
| ------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | De URL of inline StyleSheet inhoud die moet worden geïnjecteerd. URL's die beginnen met `context://` lossen op naar de rootresourcesmap van de app. |
| `top`        | Bepaalt of de StyleSheet aan de bovenkant van de pagina moet worden geïnjecteerd.                                          |
| `attributes` | Een kaart van attributen om voor de StyleSheet in te stellen.                                                              |
