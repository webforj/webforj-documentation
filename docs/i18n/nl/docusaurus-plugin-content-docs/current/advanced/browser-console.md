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

Het gebruik van de console van de browser om programmainformatie af te drukken, is een integraal onderdeel van het ontwikkelingsproces. De <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> utility klasse biedt functies die de logmogelijkheden verbeteren via logtypes en styling.

<!-- :::info
Voor `24.10` werden de methoden `App.consoleLog()` en `App.consoleError()` gebruikt om dit gedrag mogelijk te maken, maar ze zijn sindsdien gemarkeerd voor afschrijving.
::: -->

## Instance {#instance}

Haal een instantie van `BrowserConsole` op met de `App.console()` methode. Druk elk gewenst `Object` af als een van de vijf logtypes: log, info, warn, error of debug.

```java
import static com.webforj.App.console;
// Types
console().log("Log bericht");
console().info("Info bericht");
console().warn("Waarschuwing bericht");
console().error("Fout bericht");
console().debug("Debug bericht");
```

## Styling {#styling}

Gebruik de builder methoden om het uiterlijk van het logbericht in te stellen. Elke builder heeft opties om een specifieke eigenschap te wijzigen. Het is ook mogelijk om [meerdere stijlen te combineren](#mixing-styles). Zodra een consolebericht is afgedrukt, worden eventuele toegepaste stijlen niet overgedragen naar volgende berichten, tenzij *expliciet* opnieuw gedefinieerd.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Gebruik de `setStyle` methode om de eigenschappen van de `BrowserConsole` log te wijzigen die niet door de builders zijn opgegeven.
:::

### Achtergrondkleur {#background-color}

Stel de achtergrondkleur in met de `background()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> retourneert. Gebruik methoden die naar kleur zijn genoemd, zoals `blue()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Achtergrond Voorbeelden
console().background().blue().log("Blauwe achtergrond");
console().background().colored("#031f8f").log("Aangepaste blauwe achtergrond");
```

### Tekstkleur {#text-color}

Stel de tekstkleur in met de `color()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> retourneert. Gebruik methoden die naar kleur zijn genoemd, zoals `red()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Kleur Voorbeelden
console().background().red().log("Rode tekst");
console().color().colored("#becad2").log("Aangepaste licht blauwgrijze tekst");
```

### Lettergrootte {#font-size}

Stel de lettergrootte in met de `size()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> retourneert. Gebruik methoden die naar een grootte zijn genoemd, zoals `small()`, of kies een specifieke waarde met `from(String value)`.

```java
//Grootte Voorbeelden
console().size().small().log("Kleine lettertype");
console().size().from("30px").log("30px lettertype");
```
:::tip
De `from(String value)` methode kan andere lettergrootte waarde accepteren, zoals rem en vw.
:::

### Letterstijl {#font-style}

Stel de letterstijl in met de `style()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> retourneert. Gebruik bijvoorbeeld de `italic()` methode om de console log cursief te maken.

```java
// Stijl Voorbeelden
console().style().italic().log("Cursieve lettertype");
console().style().normal().log("Normale lettertype");
```

### Tekentransformatie {#text-transformation}

Beheers de kapitalisering van de karakters in een bericht met de `transform()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> retourneert. Gebruik bijvoorbeeld de `capitalize()` methode om de eerste letter van elk woord in hoofdletters te veranderen.

```java
// Transformeer Voorbeelden
// Kapitaliseren Tekst Transformatie
console().transform().capitalize().log("Kapitalizeer tekst transformatie");
// HOOFDLETTERS TEKST TRANSFORMATIE
console().transform().uppercase().log("Hoofdletters tekst transformatie");
```

### Lettergewicht {#font-weight}

Stel in hoe dik de tekst is met de `weight()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> retourneert. Gebruik bijvoorbeeld de `ligther()` methode om het lettertype lichter te maken dan normaal.

```java
// Gewicht Voorbeelden
console().weight().bold().log("Vet lettertype");
console().weight().lighter().log("Lichter lettertype");
```

## Stijlen combineren {#mixing-styles}
Het is mogelijk om methoden te combineren voor een aangepaste loggingweergave.

```java
// Een verscheidenheid aan opties voor aangepaste loggingweergave
console()
  .weight().bolder()
  .size().larger()
  .color().gray()
  .style().italic()
  .transform().uppercase()
  .background().blue()
  .warn("Stijlen combineren");
```
