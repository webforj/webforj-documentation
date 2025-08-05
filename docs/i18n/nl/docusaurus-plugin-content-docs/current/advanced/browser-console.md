---
sidebar_position: 5
title: Browser Console
_i18n_hash: 8a6d28f2824de2020cf5b225d9ff458e
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Het gebruik van de console van de browser om waardevolle programmainformatie af te drukken, is een integraal onderdeel van het ontwikkelingsproces. De <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> hulpprogrammaklasse komt met een aantal functies om de logmogelijkheden te verbeteren.

<!-- :::info
Voor `24.10` maakten de `App.consoleLog()` en `App.consoleError()` methoden dit gedrag mogelijk, maar ze zijn inmiddels gemarkeerd voor afschaffing.
::: -->

## Instance {#instance}

Krijg een instantie van `BrowserConsole` met behulp van de `App.console()` methode. Print elk gewenst `Object` als een van de vijf logtypes: log, info, waarschuwing, fout of debug.

```java
import static com.webforj.App.console;
// Types
console().log("Logbericht");
console().info("Infobericht");
console().warn("Waarschuwing bericht");
console().error("Foutbericht");
console().debug("Debugbericht");
```

## Styling {#styling}

Gebruik de builder-methoden om het uiterlijk van het logbericht in te stellen. Elke builder heeft opties om een specifieke eigenschap te wijzigen. Het is ook mogelijk om [meerdere stijlen te mengen](#mixing-styles).
Zodra een consolebericht is afgedrukt, worden toegepaste stijlen niet overgedragen naar volgende berichten, tenzij *expliciet* opnieuw gedefinieerd.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Gebruik de `setStyle` methode om de eigenschappen van de `BrowserConsole` log die niet zijn opgegeven door de builders te wijzigen.
:::

### Achtergrondkleur {#background-color}

Stel de achtergrondkleur in met de `background()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> retourneert.
Gebruik methoden die naar kleur zijn genoemd, zoals `blue()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Voorbeelden Achtergrond
console().background().blue().log("Blauwe achtergrond");
console().background().colored("#031f8f").log("Aangepaste blauwe achtergrond");
```

### Tekstkleur {#text-color}

Stel de tekstkleur in met de `color()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> retourneert.
Gebruik methoden die naar kleur zijn genoemd, zoals `red()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Kleur Voorbeelden
console().background().red().log("Rode tekst");
console().color().colored("#becad2").log("Aangepaste licht blauw-grijze tekst");
```

### Lettergrootte {#font-size}

Stel de lettergrootte in met de `size()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> retourneert.
Gebruik methoden die naar een grootte zijn genoemd, zoals `small()`, of kies een specifieke waarde met `from(String value)`.

```java
//Grootte Voorbeelden
console().size().small().log("Kleine letter");
console().size().from("30px").log("30px letter");
```
:::tip
De `from(String value)` methode kan andere lettergrootte waarden accepteren, zoals rem en vw.
:::

### Letterstijl {#font-style}

Stel de letterstijl in met de `style()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `italic()` methode om de console log cursief te maken.

```java
// Stijl Voorbeelden
console().style().italic().log("Cursieve letter");
console().style().normal().log("Normale letter");
```

### Teksttransformatie {#text-transformation}

Beheer de kapitalisatie van de tekens in een bericht met de `transform()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `capitalize()` methode om de eerste letter van elk woord in hoofdletters weer te geven.

```java
// Transformeer Voorbeelden
// Kapitaliseer Teksttransformatie
console().transform().capitalize().log("Kapitaliseer teksttransformatie");
// HOOFDLETTERS TEKSTTRANSFORMATIE 
console().transform().uppercase().log("Hoofdletters teksttransformatie");
```

### Lettergewicht {#font-weight}

Stel in hoe dik de tekst is met de `weight()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `lighter()` methode om de letter lichter dan normaal te maken.

```java
// Gewicht Voorbeelden
console().weight().bold().log("Vette letter");
console().weight().lighter().log("Lichtere letter");
```

## Stijlen mengen {#mixing-styles}
Het is mogelijk om methoden te combineren voor een aangepaste logweergave.

```java
// Een verscheidenheid aan opties voor aangepaste logweergave
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Stijlen mengen");
```
