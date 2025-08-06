---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 356ee4ac3242f607ead40f76311ead79
---
Assets annotaties bieden een declaratieve benadering voor het insluiten van externe en inline bronnen zoals JavaScript en CSS binnen een app op een statische manier. Deze annotaties stroomlijnen het beheer van bronnen door te zorgen dat afhankelijkheden op het juiste moment worden geladen, waardoor handmatige configuratie wordt verminderd en de onderhoudbaarheid wordt verbeterd.

## Importeren van JavaScript-bestanden {#importing-javascript-files}

Declaratieve JavaScript-inclusie wordt ondersteund door de `@JavaScript` annotatie, waarmee automatische afhankelijkheidsladen mogelijk is. De annotatie kan zowel op componentniveau als op app-niveau worden toegepast.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

De annotatie accepteert een relatieve of volledige pad dat in de app moet worden geladen. Dit zal worden ingevoegd in de DOM als een [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) tag. Daarnaast ondersteunt de annotatie de volgende eigenschappen:

| Eigenschap   | Type    | Beschrijving                                                                                                                                | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Specificeert of het script in de bovenste venster moet worden geïnjecteerd                                                                | `false`   |
| `attributes` | Object  | Een set van <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attributen</JavadocLink> om op het script toe te passen. | `{}`      |

#### Voorbeeld: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie verklaart aan een container is gehecht. Als meerdere componenten hetzelfde bestand laden, wordt het bestand slechts één keer geïnjecteerd.
:::

## Injecteren van JavaScript {#injecting-javascript}

In sommige gevallen wilt u misschien JavaScript-code rechtstreeks in de DOM injecteren in plaats van een JavaScript-pad op te geven. De `InlineJavaScript` annotatie stelt u in staat om JavaScript-inhoud te injecteren.

```java
@InlineJavaScript("alert('Ik ben een inline script!');")
@JavaScript("context://js/app.js")
```

| Eigenschap   | Type    | Beschrijving                                                              | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------ | --------- |
| `top`        | `Boolean` | Specificeert of het script in het bovenste venster moet worden geïnjecteerd | `false`   |
| `attributes` | `Object`  | Attributen om op het script toe te passen                                  | `{}`      |
| `id`         | `String`  | Een unieke resource-ID voor een enkele injectie                            | `""`      |

:::warning
Scripts kunnen meerdere keren worden geïnjecteerd met `InlineJavaScript`, tenzij er een specifieke ID wordt toegewezen met de `id` eigenschap.
:::

## Importeren van CSS-bestanden {#importing-css-files}

Declaratieve CSS-inclusie wordt ondersteund door de `@StyleSheet` annotatie, waarmee automatische afhankelijkheidsladen mogelijk is. De annotatie kan zowel op componentniveau als op app-niveau worden toegepast.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschap   | Type    | Beschrijving                                                                   | Standaard |
| ------------ | ------- | ----------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Specificeert of de StyleSheet in het bovenste venster moet worden geïnjecteerd | `false`   |
| `attributes` | Object  | Attributen om op de StyleSheet toe te passen                                   | `{}`      |

#### Voorbeeld: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie verklaart aan een container is gehecht. Elk bestand wordt slechts één keer geladen.
:::

## Injecteren van CSS {#injecting-css}

De `InlineStyleSheet` annotatie stelt u in staat om CSS-inhoud rechtstreeks in een webpagina op zowel componentniveau als app-niveau te injecteren.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschap   | Type    | Beschrijving                                                                                                               | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Specificeert of de StyleSheet in het bovenste venster van de pagina moet worden geïnjecteerd.                           | `false`   |
| `attributes` | Object  | Een set van [attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) om op het stijlelement toe te passen. | `{}`      |
| `id`         | String  | Een unieke resource-ID. Als meerdere bronnen dezelfde ID hebben, worden ze samengevoegd in één stijlelement.             | `""`      |
| `once`       | Boolean | Bepaalt of de StyleSheet alleen één keer in de pagina moet worden geïnjecteerd, ongeacht meerdere componentinstanties.    | `true`    |

:::tip 
Voor betere syntaxisaccentuering bij het schrijven van inline CSS voor uw componenten, kunt u de webforJ VS Code-extensie gebruiken: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische activa tijdens runtime {#dynamic-assets-at-runtime}

Dynamisch resourcebeheer is mogelijk via programmatische injectie van JavaScript en CSS tijdens runtime. U kunt bronnen laden of injecteren op basis van de runtime-context.

### Laden en injecteren van JavaScript {#loading-and-injecting-javascript}

Laad of injecteer dynamisch JavaScript tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dit stelt u in staat om scripts van URL's te laden of inline scripts rechtstreeks in de DOM te injecteren.

```java
Page page = Page.getCurrent();

// Laden van JavaScript-bestanden
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injecteren van inline JavaScript
page.addInlineJavaScript("console.log('Runtime Injectie');");
page.addInlineJavaScript("alert('Dit script wordt inline uitgevoerd');");
```

| Parameter    | Beschrijving                                                                                                              |
| ------------ | ------------------------------------------------------------------------------------------------------------------------ |
| `script`     | De URL of inline scriptinhoud om te injecteren. URL's die beginnen met `context://` worden omgezet naar de root resources map van de app. |
| `top`        | Bepaalt of het script aan de bovenkant van de pagina moet worden geïnjecteerd.                                          |
| `attributes` | Een map van attributen om in te stellen voor het script.                                                                 |

### Laden en Injecteren van CSS {#loading-and-injecting-css}

Laad of injecteer dynamisch CSS tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dit stelt u in staat om stijlen van URL's te laden of inline stijlen rechtstreeks in de DOM te injecteren.

```java
Page page = Page.getCurrent();

// Laden van CSS-bestanden
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Injecteren van inline CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parameter    | Beschrijving                                                                                                                 |
| ------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet` | De URL of inline StyleSheet-inhoud om te injecteren. URL's die beginnen met `context://` worden omgezet naar de root resources map van de app. |
| `top`        | Bepaalt of de StyleSheet aan de bovenkant van de pagina moet worden geïnjecteerd.                                          |
| `attributes` | Een map van attributen om in te stellen voor de StyleSheet.                                                                  |
