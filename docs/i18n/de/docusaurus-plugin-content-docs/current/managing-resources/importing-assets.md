---
sidebar_position: 1
title: Importing Assets
_i18n_hash: 356ee4ac3242f607ead40f76311ead79
---
Assets-Annotationen bieten einen deklarativen Ansatz zum Einbetten externer und interner Ressourcen wie JavaScript und CSS innerhalb einer App. Diese Annotationen erleichtern das Ressourcenmanagement, indem sie sicherstellen, dass Abhängigkeiten zur richtigen Ausführungsphase geladen werden, die manuelle Konfiguration reduzieren und die Wartbarkeit verbessern.

## Importieren von JavaScript-Dateien {#importing-javascript-files}

Die deklarative Einbindung von JavaScript wird durch die Annotation `@JavaScript` unterstützt, die ein automatisches Laden von Abhängigkeiten ermöglicht. Die Annotation kann sowohl auf Komponenten- als auch auf App-Ebene angewendet werden.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Die Annotation akzeptiert einen relativen oder vollständigen Pfad, der in der App geladen werden soll. Dies wird als [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script) Tag in das DOM eingefügt. Darüber hinaus unterstützt die Annotation die folgenden Eigenschaften:

| Eigenschaft   | Typ     | Beschreibung                                                                                                                                         | Standard |
| ------------- | ------- | --------------------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Skript im obersten Fenster eingebettet werden soll                                                                                 | `false`  |
| `attributes` | Object  | Eine Menge von <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>Attributen</JavadocLink>, die auf das Skript angewendet werden sollen. | `{}`     |

#### Beispiel: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Annotation deklariert, an einen Container angehängt ist. Wenn mehrere Komponenten dieselbe Datei laden, wird die Datei nur einmal eingefügt.
:::

## Injizieren von JavaScript {#injecting-javascript}

In einigen Fällen möchten Sie möglicherweise JavaScript-Code direkt in das DOM einfügen, anstatt einen JavaScript-Pfad bereitzustellen. Die Annotation `InlineJavaScript` ermöglicht es Ihnen, JavaScript-Inhalte einzufügen.

```java
@InlineJavaScript("alert('Ich bin ein Inline-Skript!');")
@JavaScript("context://js/app.js")
```

| Eigenschaft   | Typ      | Beschreibung                                                              | Standard |
| ------------- | -------- | ------------------------------------------------------------------------ | -------- |
| `top`        | `Boolean` | Gibt an, ob das Skript im obersten Fenster eingebettet werden soll      | `false`  |
| `attributes` | `Object`  | Attribute, die auf das Skript angewendet werden sollen                  | `{}`     |
| `id`         | `String`  | Eine eindeutige Ressourcen-ID, um eine einzige Injektion sicherzustellen | `""`     |

:::warning
Skripte können mit `InlineJavaScript` mehrmals injiziert werden, es sei denn, eine spezifische ID wird mit der `id`-Eigenschaft zugewiesen.
:::

## Importieren von CSS-Dateien {#importing-css-files}

Die deklarative Einbindung von CSS wird durch die Annotation `@StyleSheet` unterstützt, die ein automatisches Laden von Abhängigkeiten ermöglicht. Die Annotation kann sowohl auf Komponenten- als auch auf App-Ebene angewendet werden.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschaft   | Typ      | Beschreibung                                                                    | Standard |
| ------------- | -------- | ------------------------------------------------------------------------------ | -------- |
| `top`        | Boolean  | Gibt an, ob das Stylesheet im obersten Fenster eingebettet werden soll        | `false`  |
| `attributes` | Object   | Attribute, die auf das Stylesheet angewendet werden sollen                     | `{}`     |

#### Beispiel: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Annotation deklariert, an einen Container angehängt ist. Jede Datei wird nur einmal geladen.
:::

## Injizieren von CSS {#injecting-css}

Die Annotation `InlineStyleSheet` ermöglicht es Ihnen, CSS-Inhalte direkt auf einer Webseite sowohl auf Komponenten- als auch auf App-Ebene einzufügen.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschaft   | Typ      | Beschreibung                                                                                                            | Standard |
| ------------- | -------- | ---------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`        | Boolean  | Gibt an, ob das Stylesheet im obersten Fenster der Seite eingebettet werden soll                                      | `false`  |
| `attributes` | Object   | Eine Menge von [Attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), die auf das Style-Element angewendet werden sollen. | `{}`     |
| `id`         | String   | Eine eindeutige Ressourcen-ID. Wenn mehrere Ressourcen dieselbe ID haben, werden sie in einem einzigen Style-Element zusammengefasst. | `""`     |
| `once`       | Boolean  | Bestimmt, ob das Stylesheet nur einmal in die Seite eingefügt werden soll, unabhängig von mehreren Komponenteninstanzen. | `true`   |

:::tip 
Für eine bessere Syntaxhervorhebung beim Schreiben von Inline-CSS für Ihre Komponenten können Sie die webforJ VS Code-Erweiterung verwenden: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische Assets zur Laufzeit {#dynamic-assets-at-runtime}

Ein dynamisches Ressourcenmanagement ist durch die programmgesteuerte Injektion von JavaScript und CSS zur Laufzeit möglich. Sie können Ressourcen basierend auf dem Laufzeitkontext laden oder injizieren.

### Laden und Injizieren von JavaScript {#loading-and-injecting-javascript}

Laden oder injizieren Sie JavaScript dynamisch zur Laufzeit mit der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dies ermöglicht es Ihnen, Skripte von URLs zu laden oder Inline-Skripte direkt in das DOM einzufügen.

```java
Page page = Page.getCurrent();

// Laden von JavaScript-Dateien
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Injektieren von Inline-JavaScript
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('Dieses Skript läuft inline');");
```

| Parameter     | Beschreibung                                                                                                       |
| --------------| ------------------------------------------------------------------------------------------------------------------ |
| `script`      | Die URL oder den Inhalt des Inline-Skripts, der injiziert werden soll. URLs, die mit `context://` beginnen, werden im Wurzelressourcenordner der App aufgelöst. |
| `top`         | Bestimmt, ob das Skript oben auf der Seite injiziert werden soll.                                                |
| `attributes`  | Eine Map von Attributen, die für das Skript festgelegt werden sollen.                                           |

### Laden und Injizieren von CSS {#loading-and-injecting-css}

Laden oder injizieren Sie CSS dynamisch zur Laufzeit mit der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dies ermöglicht es Ihnen, Stylesheets von URLs zu laden oder Inline-Stile direkt in das DOM einzufügen.

```java
Page page = Page.getCurrent();

// Laden von CSS-Dateien
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Injektieren von Inline-CSS
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parameter     | Beschreibung                                                                                                            |
| --------------| ---------------------------------------------------------------------------------------------------------------------- |
| `stylesheet`  | Die URL oder den Inhalt des Inline-Stylesheets, der injiziert werden soll. URLs, die mit `context://` beginnen, werden im Wurzelressourcenordner der App aufgelöst. |
| `top`         | Bestimmt, ob das Stylesheet oben auf der Seite injiziert werden soll.                                                |
| `attributes`  | Eine Map von Attributen, die für das Stylesheet festgelegt werden sollen.                                           |
