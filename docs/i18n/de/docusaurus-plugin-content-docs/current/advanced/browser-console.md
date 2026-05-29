---
sidebar_position: 15
title: Browser Console
_i18n_hash: 843587956991faa037138ce8e8563e7a
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Die Verwendung der Konsole des Browsers zum Drucken von Programminformationen ist ein wesentlicher Bestandteil des Entwicklungsprozesses. 
Die <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> Hilfsklasse bietet Funktionen, die die Protokollierungsfähigkeiten durch Protokolltypen und Stile verbessern.

## Instanz {#instance}

Holen Sie sich eine Instanz von `BrowserConsole` mit der Methode `App.console()`. Drucken Sie jedes gewünschte `Object` als einen der fünf Protokolltypen: log, info, warn, error oder debug.

```java
import static com.webforj.App.console;
// Typen
console().log("Protokollnachricht");
console().info("Informationsnachricht");
console().warn("Warnnachricht");
console().error("Fehlermeldung");
console().debug("Debug-Nachricht");
```

## Stil {#styling}

Verwenden Sie die Builder-Methoden, um das Aussehen der Protokollnachricht festzulegen. Jeder Builder hat Optionen zur Änderung eines bestimmten Attributs. Es ist auch möglich, [mehrere Stile zu mischen](#mixing-styles).
Sobald eine Konsolenmeldung gedruckt wird, werden alle angewendeten Stile nicht auf nachfolgende Nachrichten übertragen, es sei denn, sie werden *explizit* neu definiert.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Verwenden Sie die Methode `setStyle`, um die Eigenschaften des `BrowserConsole`-Protokolls zu ändern, die nicht von den Build-Methoden angegeben sind.
:::

### Hintergrundfarbe {#background-color}

Setzen Sie die Hintergrundfarbe mit der Methode `background()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach Farben benannt sind, wie `blue()`, oder wählen Sie einen bestimmten Wert mit `colored(String color)`.

```java
// Hintergrundbeispiele
console().background().blue().log("Blauer Hintergrund");
console().background().colored("#031f8f").log("Benutzerdefinierter blauer Hintergrund");
```

### Textfarbe {#text-color}

Setzen Sie die Textfarbe mit der Methode `color()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach Farben benannt sind, wie `red()`, oder wählen Sie einen bestimmten Wert mit `colored(String color)`.

```java
// Farbbeispiele
console().background().red().log("Roter Text");
console().color().colored("#becad2").log("Benutzerdefinierter hellbläulich-grauer Text");
```

### Schriftgröße {#font-size}

Setzen Sie die Schriftgröße mit der Methode `size()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> zurückgibt.
Verwenden Sie Methoden, die nach einer Größe benannt sind, wie `small()`, oder wählen Sie einen bestimmten Wert mit `from(String value)`.

```java
// Größenbeispiele
console().size().small().log("Kleine Schrift");
console().size().from("30px").log("30px Schrift");
```
:::tip
Die Methode `from(String value)` kann auch andere Schriftgrößenwerte akzeptieren, wie rem und vw.
:::

### Schriftstil {#font-style}

Setzen Sie den Schriftstil mit der Methode `style()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> zurückgibt.
Zum Beispiel können Sie die Methode `italic()` verwenden, um das Konsolenprotokoll kursiv zu gestalten.

```java
// Stilbeispiele
console().style().italic().log("Kursive Schrift");
console().style().normal().log("Normale Schrift");
```

### Texttransformation {#text-transformation}

Steuern Sie die Großschreibung der Zeichen in einer Nachricht mit der Methode `transform()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `capitalize()`, um den ersten Buchstaben jedes Wortes in Großbuchstaben zu verwandeln.

```java
// Transformationsbeispiele
// Textveränderung Großschreibung
console().transform().capitalize().log("Textveränderung Großschreibung");
// TEXTVERÄNDERUNG GROSSSCHRIFT 
console().transform().uppercase().log("Textveränderung Großschrift");
```

### Schriftgewicht {#font-weight}

Stellen Sie ein, wie dick der Text ist, mit der Methode `weight()`, die den <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> zurückgibt.
Verwenden Sie beispielsweise die Methode `lighter()`, um die Schrift leichter als normal zu machen.

```java
// Gewichtbeispiele
console().weight().bold().log("Fette Schrift");
console().weight().lighter().log("Leichtere Schrift");
```

## Stile mischen {#mixing-styles}
Es ist möglich, Methoden zu mischen und anzupassen für eine benutzerdefinierte Protokollanzeige.

```java
// Eine Vielzahl von Optionen für die benutzerdefinierte Protokollanzeige
console()
  .weight().bolder()
  .size().larger()
  .color().gray()
  .style().italic()
  .transform().uppercase()
  .background().blue()
  .warn("Stile mischen");
```
