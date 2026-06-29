---
sidebar_position: 1
title: Importing Assets
description: >-
  Attach JavaScript and CSS to webforJ components or the app using JavaScript,
  InlineJavaScript, StyleSheet, and InlineStyleSheet annotations.
_i18n_hash: 8ebb2cb8863f97fcddea40fac13f71ce
---
Die Asset-Anmerkungen bieten einen deklarativen Ansatz zum Einbetten externer und integrierter Ressourcen wie JavaScript und CSS innerhalb einer App auf statische Weise. Diese Anmerkungen vereinfachen die Ressourcenverwaltung, indem sie sicherstellen, dass Abhängigkeiten zur entsprechenden Ausführungsphase geladen werden, was die manuelle Konfiguration reduziert und die Wartbarkeit verbessert.

:::tip Der Bundler ist die Standardlösung für npm und Frameworks
Die Asset-Anmerkungen fügen ein Skript oder Stylesheet hinzu, das Sie bereits haben, ohne dass ein Build-Schritt erforderlich ist. Um npm-Pakete, ein Komponenten-Framework wie React oder eine Stylesheet-Sprache wie SCSS einzubinden, verwenden Sie den [Frontend-Bundler](/docs/managing-resources/bundler/overview). Es ist der Standardweg für diese Arbeiten und führt alles aus, was die Anmerkungen tun.
:::

## Importieren von JavaScript-Dateien {#importing-javascript-files}

Die deklarative JavaScript-Einbindung wird durch die `@JavaScript`-Anmerkung unterstützt, die das automatische Laden von Abhängigkeiten ermöglicht. Die Anmerkung kann sowohl auf Komponenten- als auch auf App-Ebene angewendet werden.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Die Anmerkung akzeptiert einen relativen oder vollständigen Pfad, der in der App geladen werden soll. Dies wird als [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script)-Tag in das DOM eingefügt. Darüber hinaus unterstützt die Anmerkung die folgenden Eigenschaften:

| Eigenschaft   | Typ      | Beschreibung                                                                                                                                  | Standard |
| ------------- | -------  | ---------------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`         | Boolean  | Gibt an, ob das Skript in das Top-Level-Fenster injiziert werden soll                                                                        | `false`  |
| `attributes`  | Objekt   | Eine Menge von <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>Attributen</JavadocLink>, die auf das Skript angewendet werden. | `{}`     |

#### Beispiel: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Anmerkung erklärt, an einen Container angehängt ist. Wenn mehrere Komponenten dieselbe Datei laden, wird die Datei nur einmal injiziert.
:::

## Injizieren von JavaScript {#injecting-javascript}

In einigen Fällen möchten Sie möglicherweise JavaScript-Code direkt in das DOM injizieren, anstatt einen JavaScript-Pfad bereitzustellen. Die `InlineJavaScript`-Anmerkung ermöglicht es Ihnen, JavaScript-Inhalte zu injizieren.

```java
@InlineJavaScript("alert('Ich bin ein Inline-Skript!');")
@JavaScript("context://js/app.js")
```

| Eigenschaft   | Typ      | Beschreibung                                                               | Standard |
| ------------- | -------  | ------------------------------------------------------------------------- | -------- |
| `top`         | `Boolean`| Gibt an, ob das Skript in das Top-Level-Fenster injiziert werden soll   | `false`  |
| `attributes`  | `Objekt` | Attribute, die auf das Skript angewendet werden                          | `{}`     |
| `id`          | `String` | Eine eindeutige Ressourcen-ID, um eine einmalige Injektion sicherzustellen | `""`     |

:::warning
Skripte können mehrere Male unter Verwendung von `InlineJavaScript` injiziert werden, es sei denn, eine spezifische ID wird mit der `id`-Eigenschaft zugewiesen.
:::

## Importieren von CSS-Dateien {#importing-css-files}

Die deklarative CSS-Einbindung wird durch die `@StyleSheet`-Anmerkung unterstützt, die das automatische Laden von Abhängigkeiten ermöglicht. Die Anmerkung kann sowohl auf Komponenten- als auch auf App-Ebene angewendet werden.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschaft   | Typ      | Beschreibung                                                                   | Standard |
| ------------- | -------  | ----------------------------------------------------------------------------- | -------- |
| `top`         | Boolean  | Gibt an, ob das Stylesheet in das Top-Level-Fenster injiziert werden soll    | `false`  |
| `attributes`  | Objekt   | Attribute, die auf das Stylesheet angewendet werden                          | `{}`     |

#### Beispiel: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Anmerkung erklärt, an einen Container angehängt ist. Jede Datei wird nur einmal geladen.
:::

## Injizieren von CSS {#injecting-css}

Die `InlineStyleSheet`-Anmerkung ermöglicht es Ihnen, CSS-Inhalte direkt in eine Webseite sowohl auf Komponenten- als auch auf App-Ebene zu injizieren.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschaft   | Typ      | Beschreibung                                                                                                               | Standard |
| ------------- | -------  | ------------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`         | Boolean  | Gibt an, ob das Stylesheet in das Top-Level-Fenster der Seite injiziert werden soll.                                     | `false`  |
| `attributes`  | Objekt   | Eine Menge von [Attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), die auf das Style-Element angewendet werden. | `{}`     |
| `id`          | String   | Eine eindeutige Ressourcen-ID. Wenn mehrere Ressourcen dieselbe ID haben, werden sie in einem einzigen Style-Element zusammengefasst. | `""`     |
| `once`        | Boolean  | Bestimmt, ob das Stylesheet nur einmal in die Seite injiziert werden soll, unabhängig von mehreren Instanzen der Komponente. | `true`   |

:::tip 
Für eine bessere Syntax-Hervorhebung beim Schreiben von Inline-CSS für Ihre Komponenten können Sie die webforJ VS Code-Erweiterung verwenden: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische Assets zur Laufzeit {#dynamic-assets-at-runtime}

Das Management dynamischer Ressourcen ist durch programmatische Injektion von JavaScript und CSS zur Laufzeit möglich. Sie können Ressourcen basierend auf dem Laufzeitkontext laden oder injizieren.

### Laden und Injizieren von JavaScript {#loading-and-injecting-javascript}

Laden oder injizieren Sie JavaScript dynamisch zur Laufzeit mit der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page-API</JavadocLink>. Dies ermöglicht es Ihnen, Skripte von URLs zu laden oder Inline-Skripte direkt in das DOM zu injizieren.

```java
Page page = Page.getCurrent();

// Laden von JavaScript-Dateien
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injizieren von Inline-JavaScript
page.addInlineJavaScript("console.log('Laufzeitinjektion');");
page.addInlineJavaScript("alert('Dieses Skript wird inline ausgeführt');");
```

| Parameter     | Beschreibung                                                                                                             |
| --------------| ----------------------------------------------------------------------------------------------------------------------- |
| `script`      | Die URL oder den Inline-Skriptinhalt, der injiziert werden soll. URLs, die mit `context://` beginnen, verweisen auf den Stammordner der App-Ressourcen. |
| `top`         | Bestimmt, ob das Skript am oberen Rand der Seite injiziert werden soll.                                               |
| `attributes`  | Eine Karte von Attributen, die für das Skript festgelegt werden sollen.                                              |

### Laden und Injizieren von CSS {#loading-and-injecting-css}

Laden oder injizieren Sie CSS dynamisch zur Laufzeit mit der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page-API</JavadocLink>. Dies ermöglicht es Ihnen, Stylesheets von URLs zu laden oder Inline-Styles direkt in das DOM zu injizieren.

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
| --------------| --------------------------------------------------------------------------------------------------------------------------- |
| `stylesheet`  | Die URL oder den Inline-StyleSheet-Inhalt, der injiziert werden soll. URLs, die mit `context://` beginnen, verweisen auf den Stammordner der App-Ressourcen. |
| `top`         | Bestimmt, ob das Stylesheet am oberen Rand der Seite injiziert werden soll.                                               |
| `attributes`  | Eine Karte von Attributen, die für das Stylesheet festgelegt werden sollen.                                              |
