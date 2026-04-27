---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 0256f553c96c490015f9e19b1eeea533
---
Assets-Anmerkungen bieten einen erklärenden Ansatz, um externe und integrierte Ressourcen wie JavaScript und CSS statisch innerhalb einer Anwendung einzubetten. Diese Anmerkungen optimieren das Ressourcenmanagement, indem sie sicherstellen, dass Abhängigkeiten in der richtigen Ausführungsphase geladen werden, was die manuelle Konfiguration reduziert und die Wartbarkeit verbessert.

## Importieren von JavaScript-Dateien {#importing-javascript-files}

Die erklärende JavaScript-Einbindung wird durch die `@JavaScript`-Anmerkung unterstützt, die ein automatisches Laden von Abhängigkeiten ermöglicht. Die Anmerkung kann sowohl auf Komponentenebene als auch auf Anwendungsebene angewendet werden.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Die Anmerkung akzeptiert einen relativen oder vollständigen Pfad, der in die Anwendung geladen werden soll. Dies wird als [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) Tag in das DOM eingefügt. Darüber hinaus unterstützt die Anmerkung die folgenden Eigenschaften:

| Eigenschaft   | Typ     | Beschreibung                                                                                                                                  | Standard |
| ------------- | ------- | ---------------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Skript in das oberste Fenster eingefügt werden soll                                                                          | `false`  |
| `attributes` | Object  | Eine Menge von <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>Attributen</JavadocLink>, die auf das Skript angewendet werden sollen. | `{}`     |

#### Beispiel: {#example}

```java
@JavaScript(value = "ws://my-component.js",
  attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Anmerkung deklariert, an einen Container angehängt ist. Wenn mehrere Komponenten dieselbe Datei laden, wird die Datei nur einmal eingefügt.
:::

## Injizieren von JavaScript {#injecting-javascript}

In einigen Fällen möchten Sie möglicherweise JavaScript-Code direkt in das DOM injizieren, anstatt einen JavaScript-Pfad bereitzustellen. Die `InlineJavaScript`-Anmerkung ermöglicht es Ihnen, JavaScript-Inhalte zu injizieren.

```java
@InlineJavaScript("alert('Ich bin ein Inline-Script!');")
@JavaScript("context://js/app.js")
```

| Eigenschaft   | Typ     | Beschreibung                                                               | Standard |
| ------------- | ------- | ------------------------------------------------------------------------- | -------- |
| `top`        | `Boolean` | Gibt an, ob das Skript in das oberste Fenster eingefügt werden soll     | `false`  |
| `attributes` | `Object`  | Attribute, die auf das Skript angewendet werden sollen                  | `{}`     |
| `id`         | `String`  | Eine eindeutige Ressourcen-ID, um eine einzelne Injektion zu gewährleisten | `""`     |

:::warning
Skripte können mehrfach mit `InlineJavaScript` injiziert werden, es sei denn, eine spezifische ID wird mit der `id`-Eigenschaft zugewiesen.
:::

## Importieren von CSS-Dateien {#importing-css-files}

Die erklärende CSS-Einbindung wird durch die `@StyleSheet`-Anmerkung unterstützt, die ein automatisches Laden von Abhängigkeiten ermöglicht. Die Anmerkung kann sowohl auf Komponentenebene als auch auf Anwendungsebene angewendet werden.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschaft   | Typ     | Beschreibung                                                                   | Standard |
| ------------- | ------- | ----------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Stylesheet in das oberste Fenster eingefügt werden soll      | `false`  |
| `attributes` | Object  | Attribute, die auf das Stylesheet angewendet werden sollen                    | `{}`     |

#### Beispiel: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
  attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Anmerkung deklariert, an einen Container angehängt ist. Jede Datei wird nur einmal geladen.
:::

## Injizieren von CSS {#injecting-css}

Die `InlineStyleSheet`-Anmerkung ermöglicht es Ihnen, CSS-Inhalte direkt in eine Webseite auf Komponentenebene und Anwendungsebene einzufügen.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschaft   | Typ     | Beschreibung                                                                                                               | Standard |
| ------------- | ------- | ------------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Stylesheet in das oberste Fenster der Seite eingefügt werden soll.                                       | `false`  |
| `attributes` | Object  | Eine Menge von [Attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), die auf das Stilelement angewendet werden sollen. | `{}`     |
| `id`         | String  | Eine eindeutige Ressourcen-ID. Wenn mehrere Ressourcen dieselbe ID haben, werden sie in einem einzigen Stilelement zusammengeführt. | `""`     |
| `once`       | Boolean | Bestimmt, ob das Stylesheet nur einmal in die Seite eingefügt werden soll, unabhängig von mehreren Instanzen der Komponente. | `true`   |

:::tip 
Für eine bessere Syntaxhervorhebung beim Schreiben von Inline-CSS für Ihre Komponenten können Sie die webforJ VS Code-Erweiterung verwenden: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische Assets zur Laufzeit {#dynamic-assets-at-runtime}

Das dynamische Ressourcenmanagement ist durch programmatische Injektion von JavaScript und CSS zur Laufzeit möglich. Sie können Ressourcen basierend auf dem Kontext zur Laufzeit laden oder injizieren.

### Laden und Injizieren von JavaScript {#loading-and-injecting-javascript}

Laden oder injizieren Sie JavaScript dynamisch zur Laufzeit unter Verwendung der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dies ermöglicht es Ihnen, Skripte von URLs zu laden oder Inline-Skripte direkt in das DOM zu injizieren.

```java
Page page = Page.getCurrent();

// Laden von JavaScript-Dateien
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injizieren von Inline-JavaScript
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('Dieses Skript läuft inline');");
```

| Parameter     | Beschreibung                                                                                                             |
| --------------| ----------------------------------------------------------------------------------------------------------------------- |
| `script`      | Die URL oder der Inline-Skriptinhalt, der injiziert werden soll. URLs, die mit `context://` beginnen, verweisen auf den Ressourcenordner der App. |
| `top`        | Bestimmt, ob das Skript oben auf der Seite injiziert werden soll.                                                      |
| `attributes`  | Eine Map von Attributen, die für das Skript gesetzt werden sollen.                                                     |

### Laden und Injizieren von CSS {#loading-and-injecting-css}

Laden oder injizieren Sie CSS dynamisch zur Laufzeit unter Verwendung der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dies ermöglicht es Ihnen, Stylesheets von URLs zu laden oder Inline-Stile direkt in das DOM zu injizieren.

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
| `stylesheet`  | Die URL oder der Inline-Stylesheetinhalt, der injiziert werden soll. URLs, die mit `context://` beginnen, verweisen auf den Ressourcenordner der App. |
| `top`        | Bestimmt, ob das Stylesheet oben auf der Seite injiziert werden soll.                                                      |
| `attributes`  | Eine Map von Attributen, die für das Stylesheet gesetzt werden sollen.                                                     |
