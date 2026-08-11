---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 4343173cf0cbef440f24c05cf9ee3fbd
---
Assets annotations bieten einen deklarativen Ansatz zur Einbettung externer und interner Ressourcen wie JavaScript und CSS innerhalb einer App statisch. Diese Annotationen vereinfachen das Ressourcenmanagement, indem sie sicherstellen, dass Abhängigkeiten zur richtigen Ausführungsphase geladen werden, was die manuelle Konfiguration verringert und die Wartbarkeit verbessert.

:::tip Der Bundler ist der Standard für npm und Frameworks
Die Asset-Annotationen fügen ein Skript oder Stylesheet hinzu, das Sie bereits haben, ohne Build-Schritt. Um npm-Pakete, ein Komponentenframework wie React oder eine Stylesheet-Sprache wie SCSS einzubringen, verwenden Sie den [Frontend-Bundler](/docs/managing-resources/bundler/overview). Es ist der Standardweg für diese Arbeit und erledigt alles, was die Annotationen tun.
:::

## Importieren von JavaScript-Dateien {#importing-javascript-files}

Die deklarative JavaScript-Einbindung wird durch die `@JavaScript`-Annotation unterstützt, die ein automatisches Laden der Abhängigkeiten ermöglicht. Die Annotation kann sowohl auf Komponenten- als auch auf App-Ebene angewendet werden.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Die Annotation akzeptiert einen relativen oder vollständigen Pfad, der in die App geladen werden soll. Dies wird als [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) Tag in das DOM eingefügt. Darüber hinaus unterstützt die Annotation die folgenden Eigenschaften:

| Eigenschaft   | Typ     | Beschreibung                                                                                                                                       | Standard |
| ------------- | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Skript in das oberste Fenster injiziert werden soll                                                                             | `false`  |
| `attributes` | Object  | Eine Menge von <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>Attributen</JavadocLink>, die auf das Skript angewendet werden sollen. | `{}`     |

#### Beispiel: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Annotation deklariert, an einen Container angehängt wird. Wenn mehrere Komponenten dieselbe Datei laden, wird die Datei nur einmal injiziert.
:::

## Injizieren von JavaScript {#injecting-javascript}

In einigen Fällen möchten Sie möglicherweise JavaScript-Code direkt in das DOM injizieren, anstatt einen JavaScript-Pfad bereitzustellen. Die Annotation `InlineJavaScript` ermöglicht es Ihnen, JavaScript-Inhalt zu injizieren.

```java
@InlineJavaScript("alert('Ich bin ein Inline-Skript!');")
@JavaScript("context://js/app.js")
```

| Eigenschaft   | Typ      | Beschreibung                                                               | Standard |
| ------------- | -------- | ------------------------------------------------------------------------- | -------- |
| `top`        | `Boolean` | Gibt an, ob das Skript in das oberste Fenster injiziert werden soll      | `false`  |
| `attributes` | `Object`  | Attribute, die auf das Skript angewendet werden sollen                   | `{}`     |
| `id`         | `String`  | Eine eindeutige Ressourcen-ID, um eine einzelne Injektion sicherzustellen | `""`     |

:::warning
Skripte können mit `InlineJavaScript` mehrmals injiziert werden, es sei denn, es wird eine spezifische ID mit der `id`-Eigenschaft zugewiesen.
:::

## Importieren von CSS-Dateien {#importing-css-files}

Die deklarative CSS-Einbindung wird durch die `@StyleSheet`-Annotation unterstützt, die ein automatisches Laden der Abhängigkeiten ermöglicht. Die Annotation kann sowohl auf Komponenten- als auch auf App-Ebene angewendet werden.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschaft   | Typ     | Beschreibung                                                                   | Standard |
| ------------- | ------- | ----------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Stylesheet in das oberste Fenster injiziert werden soll      | `false`  |
| `attributes` | Object  | Attribute, die auf das Stylesheet angewendet werden sollen                   | `{}`     |

#### Beispiel: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Annotation deklariert, an einen Container angehängt wird. Jede Datei wird nur einmal geladen.
:::

## Injizieren von CSS {#injecting-css}

Die Annotation `InlineStyleSheet` ermöglicht es Ihnen, CSS-Inhalt direkt in eine Webseite sowohl auf Komponenten- als auch auf App-Ebene zu injizieren.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschaft   | Typ     | Beschreibung                                                                                                               | Standard |
| ------------- | ------- | ------------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Stylesheet in das oberste Fenster der Seite injiziert werden soll                                        | `false`  |
| `attributes` | Object  | Eine Menge von [Attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), die auf das Style-Element angewendet werden sollen.     | `{}`     |
| `id`         | String  | Eine eindeutige Ressourcen-ID. Wenn mehrere Ressourcen dieselbe ID haben, werden sie in einem einzelnen Style-Element zusammengefasst. | `""`     |
| `once`       | Boolean | Bestimmt, ob das Stylesheet nur einmal in die Seite injiziert werden soll, unabhängig von mehreren Instanzen der Komponenten. | `true`   |

:::tip
Für eine bessere Syntax-Hervorhebung beim Schreiben von Inline-CSS für Ihre Komponenten können Sie die webforJ VS Code-Erweiterung verwenden: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische Assets zur Laufzeit {#dynamic-assets-at-runtime}

Das dynamische Ressourcenmanagement ist möglich durch programmgesteuertes Injizieren von JavaScript und CSS zur Laufzeit. Sie können Ressourcen basierend auf dem Kontext zur Laufzeit laden oder injizieren.

### Laden und Injizieren von JavaScript {#loading-and-injecting-javascript}

Laden oder injizieren Sie JavaScript zur Laufzeit mit der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dies ermöglicht es Ihnen, Skripte von URLs zu laden oder Inline-Skripte direkt in das DOM zu injizieren.

```java
Page page = Page.getCurrent();

// Laden von JavaScript-Dateien
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injizieren von Inline-JavaScript
page.addInlineJavaScript("console.log('Laufzeit-Injektion');");
page.addInlineJavaScript("alert('Dieses Skript läuft inline');");
```

| Parameter     | Beschreibung                                                                                                             |
| --------------|-------------------------------------------------------------------------------------------------------------------------|
| `script`      | Die URL oder der Inline-Skriptinhalt, der injiziert werden soll. URLs, die mit `context://` beginnen, werden im Stammordner der App aufgelöst. |
| `top`        | Bestimmt, ob das Skript oben auf der Seite injiziert werden soll.                                                        |
| `attributes`  | Eine Map von Attributen, die für das Skript gesetzt werden sollen.                                                        |

### Laden und Injizieren von CSS {#loading-and-injecting-css}

Laden oder injizieren Sie CSS zur Laufzeit mit der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dies ermöglicht es Ihnen, Stylesheets von URLs zu laden oder Inline-Stile direkt in das DOM zu injizieren.

```java
Page page = Page.getCurrent();

// Laden von CSS-Dateien
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Injizieren von Inline-CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parameter     | Beschreibung                                                                                                                 |
| --------------|-----------------------------------------------------------------------------------------------------------------------------|
| `stylesheet`  | Die URL oder der Inline-Stylesheet-Inhalt, der injiziert werden soll. URLs, die mit `context://` beginnen, werden im Stammordner der App aufgelöst. |
| `top`        | Bestimmt, ob das Stylesheet oben auf der Seite injiziert werden soll.                                                        |
| `attributes`  | Eine Map von Attributen, die für das Stylesheet gesetzt werden sollen.                                                        |
