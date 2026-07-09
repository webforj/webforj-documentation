---
sidebar_position: 15
title: Browser Console
description: >-
  Log messages from Java to the browser console with typed levels and styled
  output using the BrowserConsole utility.
_i18n_hash: 5900eaf4e7be19839d40784d6532bff1
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Die Verwendung der Konsole des Browsers, um Programminformationen auszugeben, ist ein integraler Bestandteil des Entwicklungsprozesses. Die <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink>-Hilfsklasse bietet Funktionen, die die Protokollierungsfähigkeiten durch Protokolltypen und -stil verbessern.

## Instanz {#instance}

Erhalten Sie eine Instanz von `BrowserConsole` mit der `App.console()`-Methode. Drucken Sie jedes gewünschte `Object` als einen der fünf Protokolltypen: log, info, warn, error oder debug.

```java
import static com.webforj.App.console;
// Typen
console().log("Protokollnachricht");
console().info("Infomeldung");
console().warn("Warnmeldung");
console().error("Fehlermeldung");
console().debug("Debug-Nachricht");
```

## Styling {#styling}

Verwenden Sie die Builder-Methoden, um das Erscheinungsbild der Protokollnachricht festzulegen. Jeder Builder hat Optionen, um eine bestimmte Eigenschaft zu ändern. Es ist auch möglich, [mehrere Stile zu mischen](#mixing-styles). Sobald eine Konsolennachricht druckt, wird jeder angewandte Stil nicht auf nachfolgende Nachrichten übertragen, es sei denn, er wird *explizit* neu definiert.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Verwenden Sie die Methode `setStyle`, um die Eigenschaften des `BrowserConsole`-Protokolls zu ändern, die nicht von den Buildern festgelegt werden.
:::

### Hintergrundfarbe {#background-color}

Setzen Sie die Hintergrundfarbe mit der Methode `background()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> zurückgibt. Verwenden Sie Methoden, die nach der Farbe benannt sind, wie `blue()`, oder wählen Sie einen spezifischen Wert mit `colored(String color)`.

```java
// Hintergrund Beispiele
console().background().blue().log("Blaue Hintergrundfarbe");
console().background().colored("#031f8f").log("Benutzerdefinierte blaue Hintergrundfarbe");
```

### Textfarbe {#text-color}

Setzen Sie die Textfarbe mit der Methode `color()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> zurückgibt. Verwenden Sie Methoden, die nach der Farbe benannt sind, wie `red()`, oder wählen Sie einen spezifischen Wert mit `colored(String color)`.

```java
// Farb Beispiele
console().background().red().log("Roter Text");
console().color().colored("#becad2").log("Benutzerdefinierter hellbläulicher grauer Text");
```

### Schriftgröße {#font-size}

Setzen Sie die Schriftgröße mit der Methode `size()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> zurückgibt. Verwenden Sie Methoden, die nach einer Größe benannt sind, wie `small()`, oder wählen Sie einen spezifischen Wert mit `from(String value)`.

```java
// Größen Beispiele
console().size().small().log("Kleine Schrift");
console().size().from("30px").log("30px Schriftgröße");
```
:::tip
Die Methode `from(String value)` kann andere Schriftgrößenwerte wie rem und vw annehmen.
:::

### Schriftstil {#font-style}

Setzen Sie den Schriftstil mit der Methode `style()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> zurückgibt. Verwenden Sie zum Beispiel die Methode `italic()`, um das Konsolenprotokoll kursiv zu machen.

```java
// Stil Beispiele
console().style().italic().log("Kursive Schrift");
console().style().normal().log("Normale Schrift");
```

### Texttransformation {#text-transformation}

Steuern Sie die Großschreibung der Zeichen in einer Nachricht mit der Methode `transform()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> zurückgibt. Verwenden Sie zum Beispiel die Methode `capitalize()`, um den ersten Buchstaben jedes Wortes in Großbuchstaben zu verwandeln.

```java
// Transform Beispiele
// Großschreibung Texttransformation 
console().transform().capitalize().log("Großschreibung Texttransformation");
// GROßBUCHSTABEN TEXTTRANSFORMATION
console().transform().uppercase().log("Großbuchstaben Texttransformation");
```

### Schriftgewicht {#font-weight}

Legen Sie fest, wie dick der Text ist, mit der Methode `weight()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> zurückgibt. Verwenden Sie zum Beispiel die Methode `ligther()`, um die Schrift leichter als normal zu machen.

```java
// Gewicht Beispiele
console().weight().bold().log("Fette Schrift");
console().weight().lighter().log("Leichtere Schrift");
```

## Stile mischen {#mixing-styles}
Es ist möglich, Methoden zu kombinieren und anzupassen, um eine benutzerdefinierte Protokollanzeige zu erstellen.

```java
// Eine Vielzahl von Optionen für benutzerdefinierte Protokollanzeige
console()
  .weight().bolder()
  .size().larger()
  .color().gray()
  .style().italic()
  .transform().uppercase()
  .background().blue()
  .warn("Stile mischen");
```
