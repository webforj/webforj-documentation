---
sidebar_position: 5
title: Browser Console
_i18n_hash: 340e3d6f1d09c67ecc3d2d93bcd23b28
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Het gebruik van de console van de browser om waardevolle programmainformatie af te drukken, is een integraal onderdeel van het ontwikkelingsproces. De <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> hulpprogrammaklasse heeft een heleboel functies om de logmogelijkheden te verbeteren.

<!-- :::info
Voor `24.10` maakten de methoden `App.consoleLog()` en `App.consoleError()` dit gedrag mogelijk, maar ze zijn inmiddels gemarkeerd voor veroudering.
::: -->

## Instance {#instance}

Krijg een instantie van `BrowserConsole` met de `App.console()` methode. Druk elk gewenst `Object` af als een van de vijf logtypen: log, info, warn, error of debug.

```java
import static com.webforj.App.console;
// Typen
console().log("Logbericht");
console().info("Infobericht");
console().warn("Waarschuwing bericht");
console().error("Foutbericht");
console().debug("Debugbericht");
```

## Styling {#styling}

Gebruik de buildermethoden om het uiterlijk van het logbericht in te stellen. Elke builder heeft opties om een specifieke eigenschap te veranderen. Het is ook mogelijk om [meerdere stijlen te combineren](#mixing-styles).
Zodra een consolebericht is afgedrukt, worden eventuele stylingtoepassingen niet overgedragen naar daaropvolgende berichten, tenzij ze *expliciet* opnieuw gedefinieerd worden.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Gebruik de `setStyle` methode om de eigenschappen van de `BrowserConsole` log die niet door de builders zijn gespecificeerd te wijzigen.
:::

### Achtergrondkleur {#background-color}

Stel de achtergrondkleur in met de `background()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> retourneert.
Gebruik methoden die naar kleur zijn genoemd, zoals `blue()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Achtergrondvoorbeelden
console().background().blue().log("Blauwe achtergrond");
console().background().colored("#031f8f").log("Aangepaste blauwe achtergrond");
```

### Tekstkleur {#text-color}

Stel de tekstkleur in met de `color()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> retourneert.
Gebruik methoden die naar kleur zijn genoemd, zoals `red()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Kleurvoorbeelden
console().background().red().log("Rode tekst");
console().color().colored("#becad2").log("Aangepaste licht blauwig-grijze tekst");
```

### Lettertypegrootte {#font-size}

Stel de lettertypegrootte in met de `size()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> retourneert.
Gebruik methoden die naar een grootte zijn genoemd, zoals `small()`, of kies een specifieke waarde met `from(String value)`.

```java
// Groottevoorbeelden
console().size().small().log("Klein lettertype");
console().size().from("30px").log("30px lettertype");
```
:::tip
De `from(String value)` methode kan andere lettergroottewaarden accepteren, zoals rem en vw.
:::

### Schriftstijl {#font-style}

Stel de schriftstijl in met de `style()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `italic()` methode om de console log cursief te maken.

```java
// Stijlvoorbeelden
console().style().italic().log("Cursief lettertype");
console().style().normal().log("Normaal lettertype");
```

### Tekentransformatie {#text-transformation}

Beheers de kapitalisatie van de tekens in een bericht met de `transform()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `capitalize()` methode om de eerste letter van elk woord in hoofdletters om te zetten.

```java
// Transformeer voorbeelden
// Kapitaliseer Teksttransformatie
console().transform().capitalize().log("Kapitalizeer teksttransformatie");
// HOOFDLETTER TEKSTTRANSFORMATIE 
console().transform().uppercase().log("Hoofdletter teksttransformatie");
```

### Lettertypegewicht {#font-weight}

Stel in hoe dik de tekst is met de `weight()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `ligther()` methode om het lettertype lichter dan normaal te maken.

```java
// Gewicht voorbeelden
console().weight().bold().log("Vet lettertype");
console().weight().lighter().log("Lichter lettertype");
```

## Stijlen combineren {#mixing-styles}
Het is mogelijk om methoden te combineren voor een aangepaste logweergave.

```java
// Een verscheidenheid aan opties voor een aangepaste logweergave
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Stijlen combineren");
```
