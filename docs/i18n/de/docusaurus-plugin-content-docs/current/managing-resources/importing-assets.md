---
sidebar_position: 1
title: Assets importieren
_i18n_hash: a2aecab2ea12370f1e494703c2ec05af
---
Assets-Anmerkungen bieten einen deklarativen Ansatz zur Einbettung externer und interner Ressourcen wie JavaScript und CSS innerhalb einer App auf statische Weise. Diese Anmerkungen optimieren das Ressourcenmanagement, indem sie sicherstellen, dass Abhängigkeiten zur entsprechenden Ausführungsphase geladen werden, was die manuelle Konfiguration reduziert und die Wartbarkeit erhöht.

## Importieren von JavaScript-Dateien {#importing-javascript-files}

Deklarative JavaScript-Einbindung wird durch die `@JavaScript`-Anmerkung unterstützt, die das automatische Laden von Abhängigkeiten ermöglicht. Die Anmerkung kann sowohl auf Komponenten- als auch auf App-Ebene angewendet werden.

```java
@JavaScript("ws://js/app.js")
@JavaScript("https://cdn.example.com/library.js")
```

Die Anmerkung akzeptiert einen relativen oder vollständigen Pfad, der in der App geladen werden soll. Dies wird in das DOM als [`<script>`](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/script)-Tag eingefügt. Darüber hinaus unterstützt die Anmerkung die folgenden Eigenschaften:

| Eigenschaft   | Typ     | Beschreibung                                                                                                                                       | Standard |
| ------------- | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Skript in das oberste Fenster eingefügt werden soll                                                                                   | `false`  |
| `attributes` | Objekt  | Eine Menge von <JavadocLink type="foundation" location="com/webforj/annotation/Attribute" code='true'>Attributen</JavadocLink>, die auf das Skript angewendet werden. | `{}`     |

#### Beispiel: {#example}

```java
@JavaScript(value = "ws://my-component.js",
    attributes = {@Attribute(name = "type", value = "module")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Anmerkung deklariert, an einen Container angehängt ist. Wenn mehrere Komponenten dieselbe Datei laden, wird die Datei nur einmal eingefügt.
:::

## Injizieren von JavaScript {#injecting-javascript}

In einigen Fällen möchten Sie möglicherweise JavaScript-Code direkt in das DOM injizieren, anstatt einen JavaScript-Pfad anzugeben. Die Anmerkung `InlineJavaScript` ermöglicht Ihnen das Injizieren von JavaScript-Inhalten.

```java
@InlineJavaScript("alert('Ich bin ein Inline-Skript!');")
@JavaScript("context://js/app.js")
```

| Eigenschaft   | Typ     | Beschreibung                                                                 | Standard |
| ------------- | ------- |--------------------------------------------------------------------------- | -------- |
| `top`        | `Boolean` | Gibt an, ob das Skript in das oberste Fenster eingefügt werden soll       | `false`  |
| `attributes` | `Objekt`  | Attribute, die auf das Skript angewendet werden                          | `{}`     |
| `id`         | `String`  | Eine eindeutige Ressourcen-ID, um eine einzige Injektion sicherzustellen | `""`     |

:::warning
Skripte können mehrfach mit `InlineJavaScript` injiziert werden, es sei denn, es wird eine bestimmte ID mit der `id`-Eigenschaft zugewiesen.
:::

## Importieren von CSS-Dateien {#importing-css-files}

Die deklarative CSS-Einbindung wird durch die `@StyleSheet`-Anmerkung unterstützt, die das automatische Laden von Abhängigkeiten ermöglicht. Die Anmerkung kann sowohl auf Komponenten- als auch auf App-Ebene angewendet werden.

```java
@StyleSheet("ws://css/app.css")
@StyleSheet("https://cdn.example.com/library.css")
```

| Eigenschaft   | Typ     | Beschreibung                                                                   | Standard |
| ------------- | ------- |----------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Stylesheet in das oberste Fenster eingefügt werden soll    | `false`  |
| `attributes` | Objekt  | Attribute, die auf das Stylesheet angewendet werden                         | `{}`     |

#### Beispiel: {#example-1}

```java
@StyleSheet(value = "ws://my-component.css",
    attributes = {@Attribute(name = "media", value = "screen")})
```

:::info
Dateien werden nur geladen, wenn die Komponente, die die Anmerkung deklariert, an einen Container angehängt ist. Jede Datei wird nur einmal geladen.
:::

## Injizieren von CSS {#injecting-css}

Die Anmerkung `InlineStyleSheet` ermöglicht es Ihnen, CSS-Inhalte direkt in eine Webseite auf Komponenten- und App-Ebene einzufügen.

```java
@InlineStyleSheet("body { background-color: lightblue; }")
@InlineStyleSheet(value = "h1 { color: red; }", id = "headingStyles", once = true)
```

| Eigenschaft   | Typ     | Beschreibung                                                                                                           | Standard |
| ------------- | ------- | --------------------------------------------------------------------------------------------------------------------- | -------- |
| `top`        | Boolean | Gibt an, ob das Stylesheet in das oberste Fenster der Seite eingefügt werden soll                                    | `false`  |
| `attributes` | Objekt  | Eine Menge von [Attributen](https://developer.mozilla.org/en-US/docs/Web/HTML/Element/style), die auf das Style-Element angewendet werden. | `{}`     |
| `id`         | String  | Eine eindeutige Ressourcen-ID. Wenn mehrere Ressourcen dieselbe ID haben, werden sie in einem einzigen Style-Element zusammengefasst. | `""`     |
| `once`       | Boolean | Bestimmt, ob das Stylesheet nur einmal in die Seite injiziert werden soll, unabhängig von mehreren Komponenteninstanzen. | `true`   |

:::tip 
Für eine bessere Syntax-Hervorhebung beim Schreiben von Inline-CSS für Ihre Komponenten können Sie die webforJ VS Code-Erweiterung verwenden: [Java HTML CSS Syntax Highlighting](https://marketplace.visualstudio.com/items?itemName=BEU.vscode-java-html).
:::

## Dynamische Ressourcen zur Laufzeit {#dynamic-assets-at-runtime}

Dynamisches Ressourcenmanagement ist durch programmgesteuertes Injizieren von JavaScript und CSS zur Laufzeit möglich. Sie können Ressourcen basierend auf dem Laufzeitkontext laden oder injizieren.

### Laden und Injizieren von JavaScript {#loading-and-injecting-javascript}

Laden oder injizieren Sie dynamisch JavaScript zur Laufzeit mithilfe der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dies ermöglicht es Ihnen, Skripte aus URLs zu laden oder Inline-Skripte direkt in das DOM zu injizieren.

```java
Page page = Page.getCurrent();

// JavaScript-Dateien laden
page.addJavaScript("ws://js/app.js");
page.addJavaScript("https://cdn.example.com/library.js");

// Inline-JavaScript injizieren
page.addInlineJavaScript("console.log('Runtime Injection');");
page.addInlineJavaScript("alert('Dieses Skript wird inline ausgeführt');");
```

| Parameter     | Beschreibung                                                                                                             |
| --------------|-------------------------------------------------------------------------------------------------------------------------|
| `script`      | Die URL oder der Inline-Skriptinhalt, der injiziert werden soll. URLs, die mit `context://` beginnen, lösen sich im Ressourcenordner der App auf. |
| `top`        | Bestimmt, ob das Skript oben auf der Seite injiziert werden soll.                                                       |
| `attributes`  | Eine Karte von Attributen, die für das Skript festgelegt werden sollen.                                               |

### Laden und Injizieren von CSS {#loading-and-injecting-css}

Laden oder injizieren Sie dynamisch CSS zur Laufzeit mithilfe der <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page API</JavadocLink>. Dies ermöglicht es Ihnen, Stylesheets aus URLs zu laden oder Inline-Stile direkt in das DOM zu injizieren.

```java
Page page = Page.getCurrent();

// CSS-Dateien laden
page.addStyleSheet("ws://css/app.css");
page.addStyleSheet("https://cdn.example.com/library.css");

// Inline-CSS injizieren
page.addInlineStyleSheet("body { background-color: lightblue; }");
page.addInlineStyleSheet("h1 { font-size: 24px; color: navy; }");
```

| Parameter     | Beschreibung                                                                                                                 |
| --------------|-----------------------------------------------------------------------------------------------------------------------------|
| `stylesheet`  | Die URL oder der Inline-Stylesheet-Inhalt, der injiziert werden soll. URLs, die mit `context://` beginnen, lösen sich im Ressourcenordner der App auf. |
| `top`        | Bestimmt, ob das Stylesheet oben auf der Seite injiziert werden soll.                                                       |
| `attributes`  | Eine Karte von Attributen, die für das Stylesheet festgelegt werden sollen.                                               |
