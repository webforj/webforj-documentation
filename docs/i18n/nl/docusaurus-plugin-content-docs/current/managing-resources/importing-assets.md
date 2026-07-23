---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 4343173cf0cbef440f24c05cf9ee3fbd
---
Annotaties voor activa bieden een declaratieve benadering om externe en inline bronnen zoals JavaScript en CSS statisch binnen een app in te voegen. Deze annotaties stroomlijnen het resourcebeheer door ervoor te zorgen dat afhankelijkheden worden geladen in de juiste uitvoeringsfase, waardoor handmatige configuratie wordt verminderd en de onderhoudbaarheid wordt verbeterd.

:::tip De bundelaar is de standaard voor npm en frameworks
De asset-annotaties koppelen een script of stylesheet die je al hebt, zonder een build-stap. Om npm-pakketten, een componentframework zoals React, of een stylesheet-taal zoals SCSS in te voeren, gebruik je de [frontend bundler](/docs/managing-resources/bundler/overview). Dit is het standaardpad voor dat werk en het doet alles wat de annotaties doen.
:::

## Importeren van JavaScript-bestanden {#importing-javascript-files}

Declaratieve JavaScript-inclusie wordt ondersteund via de `@JavaScript` annotatie, die automatische afhankelijkheid loading mogelijk maakt. De annotatie kan zowel op componentniveau als op app-niveau worden toegepast.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

De annotatie accepteert een relatieve of volledige pad dat in de app moet worden geladen. Dit wordt in de DOM ingevoegd als een [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) tag. Bovendien ondersteunt de annotatie de volgende eigenschappen:

| Eigenschap   | Type    | Beschrijving                                                                                                                                  | Standaard |
| ------------ | ------- | --------------------------------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of het script in het bovenste niveau venster moet worden geïnjecteerd                                                              | `false`   |
| `attributes` | Object  | Een set van <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attributen</JavadocLink> die op het script moeten worden toegepast. | `{}`      |

#### Voorbeeld: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie declareert is gekoppeld aan een container. Als meerdere componenten hetzelfde bestand laden, wordt het bestand slechts eenmaal geïnjecteerd.
:::

## Injecteren van JavaScript {#injecting-javascript}

In sommige gevallen wil je misschien JavaScript-code direct in de DOM injecteren in plaats van een JavaScript-pad op te geven. De `InlineJavaScript` annotatie stelt je in staat JavaScript-inhoud in te voegen.

```java
@InlineJavaScript("alert('Ik ben een inline script!');")
@JavaScript("context://js/app.js")
```

| Eigenschap   | Type    | Beschrijving                                                                | Standaard |
| ------------ | ------- | -------------------------------------------------------------------------- | --------- |
| `top`        | `Boolean` | Geeft aan of het script in het bovenste niveau venster moet worden geïnjecteerd | `false`   |
| `attributes` | `Object`  | Attributen die op het script moeten worden toegepast                    | `{}`      |
| `id`         | `String`  | Een unieke resource-ID om een enkele injectie te waarborgen                | `""`      |

:::warning
Scripts kunnen meerdere keren worden geïnjecteerd met `InlineJavaScript`, tenzij er een specifieke ID wordt toegewezen met behulp van de `id` eigenschap.
:::

## Importeren van CSS-bestanden {#importing-css-files}

Declaratieve CSS-inclusie wordt ondersteund via de `@StyleSheet` annotatie, die automatische afhankelijkheid loading mogelijk maakt. De annotatie kan zowel op componentniveau als op app-niveau worden toegepast.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschap   | Type    | Beschrijving                                                                    | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------------ | --------- |
| `top`        | Boolean | Geeft aan of de stylesheet in het bovenste niveau venster moet worden geïnjecteerd | `false`   |
| `attributes` | Object  | Attributen die op de stylesheet moeten worden toegepast                          | `{}`      |

#### Voorbeeld: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie declareert is gekoppeld aan een container. Elk bestand wordt slechts eenmaal geladen.
:::

## Injecteren van CSS {#injecting-css}

De `InlineStyleSheet` annotatie stelt je in staat om CSS-inhoud rechtstreeks in een webpagina in te voegen op zowel componentniveau als app-niveau.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschap   | Type    | Beschrijving                                                                                                           | Standaard |
| ------------ | ------- | --------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of de stylesheet in het bovenste niveau venster van de pagina moet worden geïnjecteerd.                     | `false`   |
| `attributes` | Object  | Een set van [attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) die op het style-element moeten worden toegepast. | `{}`      |
| `id`         | String  | Een unieke resource-ID. Als meerdere resources dezelfde ID hebben, worden ze samengevoegd in een enkel style-element. | `""`      |
| `once`       | Boolean | Bepaalt of de stylesheet slechts één keer in de pagina moet worden geïnjecteerd, ongeacht meerdere componentinstanties. | `true`    |

:::tip
Voor betere syntaxismarkering bij het schrijven van inline CSS voor je componenten, kun je de webforJ VS Code-extensie gebruiken: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische activa tijdens runtime {#dynamic-assets-at-runtime}

Dynamisch resourcebeheer is mogelijk door programmatische injectie van JavaScript en CSS tijdens runtime. Je kunt bronnen laden of injecteren op basis van de runtime-context.

### Laden en injecteren van JavaScript {#loading-and-injecting-javascript}

Laad of injecteer dynamisch JavaScript tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dit stelt je in staat om scripts vanuit URL's te laden of inline scripts rechtstreeks in de DOM te injecteren.

```java
Page page = Page.getCurrent();

// JavaScript-bestanden laden
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline JavaScript injecteren
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('Dit script wordt inline uitgevoerd');");
```

| Parameter    | Beschrijving                                                                                                             |
| ------------ | ----------------------------------------------------------------------------------------------------------------------- |
| `script`     | De URL of inline script-inhoud die moet worden geïnjecteerd. URLs die beginnen met `context://` verwijzen naar de hoofdbronnenmap van de app. |
| `top`        | Bepaalt of het script aan de bovenkant van de pagina moet worden geïnjecteerd.                                         |
| `attributes` | Een map van attributen die voor het script moeten worden ingesteld.                                                      |

### Laden en injecteren van CSS {#loading-and-injecting-css}

Laad of injecteer dynamisch CSS tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dit stelt je in staat om stylesheets vanuit URL's te laden of inline stijlen rechtstreeks in de DOM te injecteren.

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
| `stylesheet` | De URL of inline StyleSheet-inhoud die moet worden geïnjecteerd. URLs die beginnen met `context://` verwijzen naar de hoofdbronnenmap van de app. |
| `top`        | Bepaalt of de stylesheet aan de bovenkant van de pagina moet worden geïnjecteerd.                                          |
| `attributes` | Een map van attributen die voor de stylesheet moeten worden ingesteld.                                                       |
