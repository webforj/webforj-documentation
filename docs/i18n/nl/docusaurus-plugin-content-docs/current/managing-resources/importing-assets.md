---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 0256f553c96c490015f9e19b1eeea533
---
Annotaties voor assets bieden een declaratieve aanpak voor het statisch inbedden van externe en inline bronnen zoals JavaScript en CSS binnen een app. Deze annotaties vereenvoudigen het beheer van hulpbronnen door ervoor te zorgen dat afhankelijkheden op het juiste moment worden geladen, waardoor handmatige configuratie wordt verminderd en de onderhoudbaarheid wordt verbeterd.

## JavaScript-bestanden importeren {#importing-javascript-files}

Declaratieve JavaScript-inclusie wordt ondersteund via de `@JavaScript` annotatie, die automatisch laden van afhankelijkheden mogelijk maakt. De annotatie kan op zowel componentniveau als app-niveau worden toegepast.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

De annotatie accepteert een relatieve of volledige pad dat in de app geladen moet worden. Dit zal in de DOM worden ingevoegd als een [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) tag. Bovendien ondersteunt de annotatie de volgende eigenschappen:

| Eigenschap   | Type    | Beschrijving                                                                                                                                       | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of het script in het bovenste venster moet worden geïnjecteerd                                                                         | `false`   |
| `attributes` | Object  | Een set van <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attributen</JavadocLink> die op het script moeten worden toegepast. | `{}`      |

#### Voorbeeld: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie declareert aan een container is gekoppeld. Als meerdere componenten hetzelfde bestand laden, wordt het bestand slechts één keer geïnjecteerd.
:::

## JavaScript injecteren {#injecting-javascript}

In sommige gevallen wilt u mogelijk JavaScript-code rechtstreeks in de DOM injecteren in plaats van een JavaScript-pad op te geven. De `InlineJavaScript` annotatie stelt u in staat om JavaScript-inhoud te injecteren.

```java
@InlineJavaScript("alert('Ik ben een inline script!');")
@JavaScript("context://js/app.js")
```

| Eigenschap   | Type    | Beschrijving                                                               | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------- | --------- |
| `top`        | `Boolean` | Geeft aan of het script in het bovenste venster moet worden geïnjecteerd | `false`   |
| `attributes` | `Object`  | Attributen die op het script moeten worden toegepast                     | `{}`      |
| `id`         | `String`  | Een unieke resource-ID om een enkele injectie te waarborgen              | `""`      |

:::warning
Scripts kunnen meerdere keren worden geïnjecteerd met `InlineJavaScript`, tenzij een specifieke ID wordt toegewezen met de `id`-eigenschap.
:::

## CSS-bestanden importeren {#importing-css-files}

Declaratieve CSS-inclusie wordt ondersteund via de `@StyleSheet` annotatie, die automatisch laden van afhankelijkheden mogelijk maakt. De annotatie kan op zowel componentniveau als app-niveau worden toegepast.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschap   | Type    | Beschrijving                                                                   | Standaard |
| ------------ | ------- | ----------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of de StyleSheet in het bovenste venster moet worden geïnjecteerd | `false`   |
| `attributes` | Object  | Attributen die op de StyleSheet moeten worden toegepast                      | `{}`      |

#### Voorbeeld: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie declareert aan een container is gekoppeld. Elk bestand wordt slechts één keer geladen.
:::

## CSS injecteren {#injecting-css}

De `InlineStyleSheet` annotatie stelt u in staat om CSS-inhoud rechtstreeks in een webpagina te injecteren op zowel componentniveau als app-niveau.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschap   | Type    | Beschrijving                                                                                                               | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of de StyleSheet in het bovenste venster van de pagina moet worden geïnjecteerd.                               | `false`   |
| `attributes` | Object  | Een set van [attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) die op het style-element moeten worden toegepast.     | `{}`      |
| `id`         | String  | Een unieke resource-ID. Als meerdere bronnen dezelfde ID hebben, worden ze samengevoegd in één style-element.             | `""`      |
| `once`       | Boolean | Bepaalt of de StyleSheet slechts één keer in de pagina moet worden geïnjecteerd, ongeacht meerdere instantie van de component. | `true`    |

:::tip 
Voor betere syntaxismarkering bij het schrijven van inline CSS voor uw componenten, kunt u de webforJ VS Code-extensie gebruiken: [Java HTML CSS Syntaxismarkering](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische assets tijdens runtime {#dynamic-assets-at-runtime}

Dynamisch beheer van hulpbronnen is mogelijk door programmatic injectie van JavaScript en CSS tijdens runtime. U kunt bronnen laden of injecteren op basis van de runtime-context.

### JavaScript laden en injecteren {#loading-and-injecting-javascript}

Laad of injecteer JavaScript dynamisch tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dit stelt u in staat om scripts van URL's te laden of inline scripts rechtstreeks in de DOM te injecteren.

```java
Page page = Page.getCurrent();

// JavaScript-bestanden laden
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline JavaScript injecteren
page.addInlineJavaScript("console.log('Runtime Injectie');");
page.addInlineJavaScript("alert('Dit script draait inline');");
```

| Parameter     | Beschrijving                                                                                                             |
| ------------- | ----------------------------------------------------------------------------------------------------------------------- |
| `script`      | De URL of inline scriptinhoud om te injecteren. URL's die beginnen met `context://` verwijzen naar de hoofdbronnenmap van de app. |
| `top`         | Bepaalt of het script aan de bovenkant van de pagina moet worden geïnjecteerd.                                          |
| `attributes`  | Een kaart van attributen die voor het script moeten worden ingesteld.                                                    |

### CSS laden en injecteren {#loading-and-injecting-css}

Laad of injecteer CSS dynamisch tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dit stelt u in staat om stylesheets van URL's te laden of inline stijlen rechtstreeks in de DOM te injecteren.

```java
Page page = Page.getCurrent();

// CSS-bestanden laden
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inline CSS injecteren
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parameter     | Beschrijving                                                                                                                 |
| ------------- | --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet`  | De URL of inline StyleSheet-inhoud om te injecteren. URL's die beginnen met `context://` verwijzen naar de hoofdbronnenmap van de app. |
| `top`         | Bepaalt of de StyleSheet aan de bovenkant van de pagina moet worden geïnjecteerd.                                          |
| `attributes`   | Een kaart van attributen die voor de StyleSheet moeten worden ingesteld.                                                      |
