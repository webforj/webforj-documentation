---
sidebar_position: 15
title: Browser Console
_i18n_hash: 843587956991faa037138ce8e8563e7a
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Het gebruik van de console van de browser om programmainformatie af te drukken is een essentieel onderdeel van het ontwikkelingsproces. 
De <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> hulpprogrammaklasse biedt functies die de loggingmogelijkheden verbeteren via logtypes en styling.

<!-- :::info
Voor `24.10` maakten de methoden `App.consoleLog()` en `App.consoleError()` dit gedrag mogelijk, maar ze zijn inmiddels gemarkeerd voor afschaffing.
::: -->

## Instance {#instance}

Krijg een instantie van `BrowserConsole` met behulp van de `App.console()` methode. Druk elk gewenst `Object` af als een van de vijf logtypes: log, info, waarschuwen, fout of debug.

```java
import static com.webforj.App.console;
// Types
console().log("Logbericht");
console().info("Infobericht");
console().warn("Waarschuwingsbericht");
console().error("Foutbericht");
console().debug("Debugbericht");
```

## Styling {#styling}

Gebruik de builder-methoden om het uiterlijk van het logbericht in te stellen. Elke builder heeft opties om een specifieke eigenschap te veranderen. Het is ook mogelijk om [meerdere stijlen te mengen](#mixing-styles).
Zodra een consolebericht is afgedrukt, worden eventuele toegepaste stijlen niet overgedragen naar daaropvolgende berichten, tenzij ze *expliciet* opnieuw gedefinieerd worden.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Gebruik de `setStyle` methode om de eigenschappen van het `BrowserConsole` log te wijzigen die niet door de builders zijn gespecificeerd.
:::

### Achtergrondkleur {#background-color}

Stel de achtergrondkleur in met de `background()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> retourneert.
Gebruik methoden genoemd naar kleur, zoals `blue()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Achtergrondvoorbeelden
console().background().blue().log("Blauwe achtergrond");
console().background().colored("#031f8f").log("Aangepaste blauwe achtergrond");
```

### Tekstkleur {#text-color}

Stel de tekstkleur in met de `color()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> retourneert.
Gebruik methoden genoemd naar kleur, zoals `red()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Kleurvoorbeelden
console().background().red().log("Rode tekst");
console().color().colored("#becad2").log("Aangepaste lichtblauw-grijze tekst");
```

### Lettergrootte {#font-size}

Stel de lettergrootte in met de `size()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> retourneert.
Gebruik methoden genoemd naar een grootte, zoals `small()`, of kies een specifieke waarde met `from(String value)`.

```java
// Groottevoorbeelden
console().size().small().log("Kleine letter");
console().size().from("30px").log("30px letter");
```
:::tip
De `from(String value)` methode kan andere lettergroottewaarden accepteren, zoals rem en vw.
:::

### Letterstijl {#font-style}

Stel de letterstijl in met de `style()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `italic()` methode om de console log cursief te maken.

```java
// Stijlvormen
console().style().italic().log("Cursieve letter");
console().style().normal().log("Normale letter");
```

### Tekentransformatie {#text-transformation}

Beheer de hoofdletters van de tekens in een bericht met de `transform()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `capitalize()` methode om de eerste letter van elk woord in hoofdletters te transformeren.

```java
// Transformvoorbeelden
// Hoofdletter Tekentransformatie
console().transform().capitalize().log("Hoofdletter teksttransformatie");
// HOOFDLETTERS TEKENTRANSFORMATIE 
console().transform().uppercase().log("Hoofdletters teksttransformatie");
```

### Lettergewicht {#font-weight}

Stel in hoe dik de tekst is met de `weight()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `ligther()` methode om de letter lichter dan normaal te maken.

```java
// Gewichtvoorbeelden
console().weight().bold().log("Vette letter");
console().weight().lighter().log("Lichtere letter");
```

## Stijlen mengen {#mixing-styles}
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
  .warn("Stijlen mengen");
```
