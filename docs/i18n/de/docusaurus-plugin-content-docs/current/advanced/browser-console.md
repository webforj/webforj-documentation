---
sidebar_position: 5
title: Browser Console
_i18n_hash: 8a6d28f2824de2020cf5b225d9ff458e
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Die Verwendung der Konsole des Browsers zur Ausgabe wertvoller Programminformationen ist ein wesentlicher Bestandteil des Entwicklungsprozesses. Die <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink>-Hilfsklasse bietet zahlreiche Funktionen zur Verbesserung der Protokollierungsfähigkeiten.

<!-- :::info
Vor `24.10` ermöglichten die Methoden `App.consoleLog()` und `App.consoleError()` dieses Verhalten, wurden jedoch mittlerweile als veraltet markiert.
::: -->

## Instance {#instance}

Holen Sie sich eine Instanz von `BrowserConsole` mit der Methode `App.console()`. Drucken Sie jedes gewünschte `Object` als eine der fünf Protokollarten: log, info, warn, error oder debug.

```java
import static com.webforj.App.console;
// Typen
console().log("Protokollnachricht");
console().info("Informationsnachricht");
console().warn("Warnmeldung");
console().error("Fehlermeldung");
console().debug("Debugnachricht");
```

## Styling {#styling}

Verwenden Sie die Builder-Methoden, um das Aussehen der Protokollnachricht festzulegen. Jeder Builder bietet Optionen zur Änderung einer spezifischen Eigenschaft. Es ist auch möglich, [mehrere Stile zu mischen](#mixing-styles).
Sobald eine Konsolennachricht ausgegeben wird, wird jede angewandte Formatierung nicht auf nachfolgende Nachrichten übertragen, es sei denn, sie wird *ausdrücklich* neu definiert.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Verwenden Sie die Methode `setStyle`, um die Eigenschaften des `BrowserConsole`-Protokolls zu ändern, die nicht durch die Builder festgelegt sind.
:::

### Hintergrundfarbe {#background-color}

Legen Sie die Hintergrundfarbe mit der Methode `background()` fest, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> zurückgibt.
Verwenden Sie nach Farbe benannte Methoden, wie `blue()`, oder wählen Sie einen spezifischen Wert mit `colored(String color)`.

```java
// Hintergrundbeispiele
console().background().blue().log("Blauer Hintergrund");
console().background().colored("#031f8f").log("Benutzerdefinierter blauer Hintergrund");
```

### Textfarbe {#text-color}

Legen Sie die Textfarbe mit der Methode `color()` fest, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> zurückgibt.
Verwenden Sie nach Farbe benannte Methoden, wie `red()`, oder wählen Sie einen spezifischen Wert mit `colored(String color)`.

```java
// Farbbeispiele
console().background().red().log("Roter Text");
console().color().colored("#becad2").log("Benutzerdefinierter hellblaugrauer Text");
```

### Schriftgröße {#font-size}

Legen Sie die Schriftgröße mit der Methode `size()` fest, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> zurückgibt.
Verwenden Sie nach Größe benannte Methoden, wie `small()`, oder wählen Sie einen spezifischen Wert mit `from(String value)`.

```java
// Größenbeispiele
console().size().small().log("Kleine Schrift");
console().size().from("30px").log("30px Schrift");
```
:::tip
Die Methode `from(String value)` kann andere Schriftgrößenwerte wie rem und vw annehmen.
:::

### Schriftstil {#font-style}

Legen Sie den Schriftstil mit der Methode `style()` fest, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `italic()`, um das Konsolenprotokoll kursiv zu machen.

```java
// Stilbeispiele
console().style().italic().log("Kursive Schrift");
console().style().normal().log("Normale Schrift");
```

### Texttransformation {#text-transformation}

Steuern Sie die Groß- und Kleinschreibung der Zeichen in einer Nachricht mit der Methode `transform()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `capitalize()`, um den ersten Buchstaben jedes Wortes groß zu machen.

```java
// Transformationsbeispiele
// Kapitalisierungs-Texttransformation
console().transform().capitalize().log("Kapitalisierungs-Texttransformation");
// GROSSBUCHSTABEN-Texttransformation 
console().transform().uppercase().log("GROSSBUCHSTABEN-Texttransformation");
```

### Schriftgewicht {#font-weight}

Legen Sie fest, wie dick der Text ist, mit der Methode `weight()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `lighter()`, um die Schrift leichter als normal zu machen.

```java
// Gewicht- Beispiele
console().weight().bold().log("Fette Schrift");
console().weight().lighter().log("Leichtere Schrift");
```

## Stile mischen {#mixing-styles}
Es ist möglich, Methoden zu kombinieren und anzupassen, um eine benutzerdefinierte Protokollanzeige zu erstellen.

```java
// Eine Vielzahl von Optionen für eine benutzerdefinierte Protokollanzeige
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Stile mischen");
```
