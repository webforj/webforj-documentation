---
sidebar_position: 15
title: Browser Console
_i18n_hash: fd0e46761a5fd8b887a39b7a51e9b66b
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Die Verwendung der Konsole des Browsers zum Drucken von Programminformationen ist ein integraler Bestandteil des Entwicklungsprozesses. 
Die <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink>-Hilfsklasse bietet Funktionen, die die Protokollierungsfähigkeiten durch Protokoltypen und Styling verbessern.

## Instanz {#instance}

Holen Sie sich eine Instanz von `BrowserConsole` mit der Methode `App.console()`. Drucken Sie jedes gewünschte `Object` als einen der fünf Protokoltypen: log, info, warn, error oder debug.

```java
import static com.webforj.App.console;
// Typen
console().log("Protokollnachricht");
console().info("Infomeldung");
console().warn("Warnmeldung");
console().error("Fehlermeldung");
console().debug("Debugnachricht");
```

## Styling {#styling}

Verwenden Sie die Builder-Methoden, um das Erscheinungsbild der Protokollnachricht festzulegen. Jeder Builder hat Optionen, um eine bestimmte Eigenschaft zu ändern. Es ist auch möglich, [mehrere Stile zu mischen](#mixing-styles).
Sobald eine Konsolenmeldung ausgegeben wird, werden alle angewendeten Stile nicht auf nachfolgende Nachrichten übertragen, es sei denn, sie werden *ausdrücklich* neu definiert.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Verwenden Sie die Methode `setStyle`, um die Eigenschaften des `BrowserConsole`-Protokolls zu ändern, die nicht von den Buildern festgelegt wurden.
:::

### Hintergrundfarbe {#background-color}

Stellen Sie die Hintergrundfarbe mit der Methode `background()` ein, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach Farben benannt sind, wie `blue()`, oder wählen Sie einen bestimmten Wert mit `colored(String color)`.

```java
// Beispiele für den Hintergrund
console().background().blue().log("Blauer Hintergrund");
console().background().colored("#031f8f").log("Benutzerdefinierter blauer Hintergrund");
```

### Textfarbe {#text-color}

Stellen Sie die Textfarbe mit der Methode `color()` ein, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach Farben benannt sind, wie `red()`, oder wählen Sie einen bestimmten Wert mit `colored(String color)`.

```java
// Beispiele für Farben
console().background().red().log("Roter Text");
console().color().colored("#becad2").log("Benutzerdefinierter hellbläulicher grauer Text");
```

### Schriftgröße {#font-size}

Stellen Sie die Schriftgröße mit der Methode `size()` ein, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach einer Größe benannt sind, wie `small()`, oder wählen Sie einen bestimmten Wert mit `from(String value)`.

```java
// Beispiele für Größen
console().size().small().log("Kleine Schrift");
console().size().from("30px").log("30px Schrift");
```
:::tip
Die Methode `from(String value)` kann andere Schriftgrößenwerte wie rem und vw akzeptieren.
:::

### Schriftstil {#font-style}

Stellen Sie den Schriftstil mit der Methode `style()` ein, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `italic()`, um die Protokollausgabe kursiv zu machen.

```java
// Beispiele für Stile
console().style().italic().log("Kursivschrift");
console().style().normal().log("Normale Schrift");
```

### Texttransformation {#text-transformation}

Steuern Sie die Großschreibung der Zeichen in einer Nachricht mit der Methode `transform()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `capitalize()`, um den ersten Buchstaben jedes Wortes in Großbuchstaben umzuwandeln.

```java
// Beispiele für Transformation
// Texttransformation in Großbuchstaben
console().transform().capitalize().log("Texttransformation in Großbuchstaben");
// GROSSBUCHSTABENTEXTTRANSFORMATION 
console().transform().uppercase().log("Texttransformation in Großbuchstaben");
```

### Schriftgewicht {#font-weight}

Stellen Sie ein, wie dick der Text ist, mit der Methode `weight()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `ligther()`, um die Schrift leichter als normal zu machen.

```java
// Beispiele für Gewicht
console().weight().bold().log("Fette Schrift");
console().weight().lighter().log("Leichtere Schrift");
```

## Stile mischen {#mixing-styles}
Es ist möglich, Methoden zu mischen und anzupassen für eine benutzerdefinierte Protokollaussicht.

```java
// Eine Vielzahl von Optionen für eine benutzerdefinierte Protokollaussicht
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Stile mischen");
```
