---
sidebar_position: 1
title: Importing Assets
_i18n_hash: a2aecab2ea12370f1e494703c2ec05af
---
Assets annotations bieden een declaratieve benadering om externe en inline bronnen zoals JavaScript en CSS statisch in een app in te sluiten. Deze annotaties stroomlijnen het beheer van bronnen door ervoor te zorgen dat afhankelijkheden op het juiste moment van uitvoering worden geladen, wat handmatige configuratie vermindert en de onderhoudbaarheid verbetert.

## Importeren van JavaScript-bestanden {#importing-javascript-files}

Declaratieve JavaScript-inclusie wordt ondersteund via de `@JavaScript` annotatie, die automatische afhankelijkheidslading mogelijk maakt. De annotatie kan zowel op componentniveau als op app-niveau worden toegepast.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

De annotatie accepteert een relatief of volledig pad dat in de app moet worden geladen. Dit wordt in de DOM ingevoegd als een [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) tag. Daarnaast ondersteunt de annotatie de volgende eigenschappen:

| Eigenschap   | Type    | Beschrijving                                                                                                                                       | Standaard |
| ------------ | ------- | --------------------------------------------------------------------------------------------------------------------------------------------------- | --------- |
| `top`        | Boolean | Geeft aan of het script in het bovenste venster moet worden ingevoegd                                                                           | `false`   |
| `attributes` | Object  | Een set van <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>attributen</JavadocLink> die op het script moeten worden toegepast. | `{}`      |

#### Voorbeeld: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie declareert is aangemaakt in een container. Als meerdere componenten hetzelfde bestand laden, wordt het bestand maar één keer ingevoegd.
:::

## Injecteren van JavaScript {#injecting-javascript}

In sommige gevallen wilt u mogelijk JavaScript-code rechtstreeks in de DOM injecteren in plaats van een JavaScript-pad op te geven. De `InlineJavaScript` annotatie stelt u in staat om JavaScript-inhoud te injecteren.

```java
@InlineJavaScript("alert('Ik ben een inline script!');")
@JavaScript("context://js/app.js")
```

| Eigenschap   | Type    | Beschrijving                                                                | Standaard |
| ------------ | ------- | -------------------------------------------------------------------------- | --------- |
| `top`        | `Boolean` | Geeft aan of het script in het bovenste venster moet worden ingevoegd | `false`   |
| `attributes` | `Object`  | Attributen die op het script moeten worden toegepast                     | `{}`      |
| `id`         | `String`  | Een unieke resource-ID om een enkele injectie te waarborgen              | `""`      |

:::warning
Scripts kunnen meerdere keren worden ingevoegd met `InlineJavaScript`, tenzij een specifieke ID wordt toegewezen met de `id`-eigenschap.
:::

## Importeren van CSS-bestanden {#importing-css-files}

Declaratieve CSS-inclusie wordt ondersteund via de `@StyleSheet` annotatie, die automatische afhankelijkheidslading mogelijk maakt. De annotatie kan zowel op componentniveau als op app-niveau worden toegepast.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschap   | Type    | Beschrijving                                                                    | Standaard |
| ------------ | ------- | ------------------------------------------------------------------------------ | --------- |
| `top`        | Boolean | Geeft aan of de StyleSheet in het bovenste venster moet worden ingevoegd     | `false`   |
| `attributes` | Object  | Attributen die op de StyleSheet moeten worden toegepast                       | `{}`      |

#### Voorbeeld: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Bestanden worden alleen geladen wanneer de component die de annotatie declareert is aangemaakt in een container. Elk bestand wordt slechts één keer geladen.
:::

## Injecteren van CSS {#injecting-css}

De `InlineStyleSheet` annotatie stelt u in staat om CSS-inhoud rechtstreeks in een webpagina te injecteren op zowel componentniveau als app-niveau.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschap   | Type    | Beschrijving                                                                                                                  | Standaard |
| ------------ | ------- |------------------------------------------------------------------------------------------------------------------------------| --------- |
| `top`        | Boolean | Geeft aan of de StyleSheet in het bovenste venster van de pagina moet worden ingevoegd.                                     | `false`   |
| `attributes` | Object  | Een set van [attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style) die op het style-element moeten worden toegepast. | `{}`      |
| `id`         | String  | Een unieke resource-ID. Als meerdere resources dezelfde ID hebben, worden ze samengevoegd in een enkel style-element.       | `""`      |
| `once`       | Boolean | Bepaalt of de StyleSheet slechts eenmaal in de pagina moet worden ingevoegd, ongeacht meerdere componentinstanties.        | `true`    |

:::tip 
Voor betere syntaxis-markering bij het schrijven van inline CSS voor uw componenten, kunt u de webforJ VS Code-extensie gebruiken: [Java HTML CSS Syntaxismarkering](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische bronnen tijdens runtime {#dynamic-assets-at-runtime}

Dynamisch resourcebeheer is mogelijk via programatische injectie van JavaScript en CSS tijdens runtime. U kunt bronnen laden of injecteren op basis van de runtime-context.

### Laden en injecteren van JavaScript {#loading-and-injecting-javascript}

Laad of injecteer dynamisch JavaScript tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Hiermee kunt u scripts laden van URL's of inline scripts rechtstreeks in de DOM injecteren.

```java
Page page = Page.getCurrent();

// JavaScript-bestanden laden
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline JavaScript injecteren
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('Dit script draait inline');");
```

| Parameter    | Beschrijving                                                                                                              |
| ------------ | ------------------------------------------------------------------------------------------------------------------------ |
| `script`     | De URL of inline scriptinhoud om in te voegen. URL's die beginnen met `context://` verwijzen naar de root resources map van de app. |
| `top`        | Bepaalt of het script aan de bovenkant van de pagina moet worden ingevoegd.                                            |
| `attributes` | Een kaart van attributen om voor het script in te stellen.                                                               |

### Laden en injecteren van CSS {#loading-and-injecting-css}

Laad of injecteer dynamisch CSS tijdens runtime met behulp van de <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Hiermee kunt u stylesheets laden van URL's of inline stijlen rechtstreeks in de DOM injecteren.

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
| `stylesheet` | De URL of inline StyleSheet-inhoud om in te voegen. URL's die beginnen met `context://` verwijzen naar de root resources map van de app. |
| `top`        | Bepaalt of de StyleSheet aan de bovenkant van de pagina moet worden ingevoegd.                                            |
| `attributes` | Een kaart van attributen om voor de StyleSheet in te stellen.                                                               |
