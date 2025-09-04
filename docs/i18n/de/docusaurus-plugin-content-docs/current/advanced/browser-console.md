---
sidebar_position: 5
title: Browser Console
_i18n_hash: 340e3d6f1d09c67ecc3d2d93bcd23b28
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Die Verwendung der Konsole des Browsers, um wertvolle Programminformationen auszugeben, ist ein integraler Bestandteil des Entwicklungsprozesses. Die <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink>-Utility-Klasse bietet zahlreiche Funktionen zur Verbesserung der Protokollierungsfähigkeiten.

## Instanz {#instance}

Holen Sie sich eine Instanz von `BrowserConsole` mit der Methode `App.console()`. Drucken Sie beliebige `Objekte` als einen der fünf Protokolltypen: log, info, warn, error oder debug.

```java
import static com.webforj.App.console;
// Typen
console().log("Protokollnachricht");
console().info("Infomeldung");
console().warn("Warnmeldung");
console().error("Fehlermeldung");
console().debug("Debug-Nachricht");
```

## Stil {#styling}

Verwenden Sie die Builder-Methoden, um das Erscheinungsbild der Protokollnachricht festzulegen. Jeder Builder hat Optionen, um eine bestimmte Eigenschaft zu ändern. Es ist auch möglich, [mehrere Stile zu mischen](#mixing-styles).
Sobald eine Konsolennachricht ausgegeben wird, werden alle angewandten Stile bei nachfolgenden Nachrichten nicht übernommen, es sei denn, sie werden *ausdrücklich* neu definiert.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Verwenden Sie die Methode `setStyle`, um die Eigenschaften des `BrowserConsole`-Protokolls zu ändern, die von den Buildern nicht angegeben sind.
:::

### Hintergrundfarbe {#background-color}

Setzen Sie die Hintergrundfarbe mit der Methode `background()`, die das <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach Farbe benannt sind, wie `blue()`, oder wählen Sie einen spezifischen Wert mit `colored(String color)`.

```java
// Hintergrundbeispiele
console().background().blue().log("Blauer Hintergrund");
console().background().colored("#031f8f").log("Benutzerdefinierter blauer Hintergrund");
```

### Textfarbe {#text-color}

Setzen Sie die Textfarbe mit der Methode `color()`, die das <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach Farbe benannt sind, wie `red()`, oder wählen Sie einen spezifischen Wert mit `colored(String color)`.

```java
// Farbbeispiele
console().background().red().log("Roter Text");
console().color().colored("#becad2").log("Benutzerdefinierter hellbläulich-grauer Text");
```

### Schriftgröße {#font-size}

Setzen Sie die Schriftgröße mit der Methode `size()`, die das <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach einer Größe benannt sind, wie `small()`, oder wählen Sie einen spezifischen Wert mit `from(String value)`.

```java
// Größenbeispiele
console().size().small().log("Kleine Schrift");
console().size().from("30px").log("30px Schrift");
```
:::tip
Die Methode `from(String value)` kann auch andere Schriftgrößenwerte wie rem und vw annehmen.
:::

### Schriftstil {#font-style}

Setzen Sie den Schriftstil mit der Methode `style()`, die das <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `italic()`, um das Konsolenprotokoll kursiv darzustellen.

```java
// Stilbeispiele
console().style().italic().log("Kursive Schrift");
console().style().normal().log("Normale Schrift");
```

### Texttransformation {#text-transformation}

Steuern Sie die Großschreibung der Zeichen in einer Nachricht mit der Methode `transform()`, die das <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `capitalize()`, um den ersten Buchstaben jedes Wortes in Großbuchstaben zu transformieren.

```java
// Transformationsbeispiele
// Großschreibung Texttransformation
console().transform().capitalize().log("Großschreibung Texttransformation");
// GROSSBUCHSTABEN TEXTTRANSFORMATION 
console().transform().uppercase().log("GROSSBUCHSTABEN Texttransformation");
```

### Schriftgewicht {#font-weight}

Bestimmen Sie, wie dick der Text ist, mit der Methode `weight()`, die das <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `lighter()`, um die Schrift leichter als normal zu machen.

```java
// Gewichtbeispiele
console().weight().bold().log("Fette Schrift");
console().weight().lighter().log("Leichtere Schrift");
```

## Stile mischen {#mixing-styles}
Es ist möglich, Methoden zu mischen und anzupassen, um eine benutzerdefinierte Protokollanzeige zu erstellen.

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
