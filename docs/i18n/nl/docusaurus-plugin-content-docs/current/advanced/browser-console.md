---
sidebar_position: 15
title: Browser Console
_i18n_hash: fd0e46761a5fd8b887a39b7a51e9b66b
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/BrowserConsole" top='true'/>

Het gebruik van de console van de browser om programmainformatie af te drukken is een integraal onderdeel van het ontwikkelingsproces. 
De <JavadocLink type="foundation" location="com/webforj/BrowserConsole" code='true'>BrowserConsole</JavadocLink> utility-klasse biedt functies die de loggingcapaciteiten verbeteren via logtypes en styling.

## Instance {#instance}

Krijg een instantie van `BrowserConsole` met behulp van de `App.console()` methode. Druk elk gewenst `Object` af als een van de vijf logtypes: log, info, warn, error of debug.

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

Gebruik de builder-methoden om het uiterlijk van het logbericht in te stellen. Elke builder heeft opties om een specifieke eigenschap te wijzigen. Het is ook mogelijk om [meerdere stijlen te mixen](#mixing-styles).
Zodra een consolebericht is afgedrukt, worden aangebrachte stylingtoepassingen niet overgedragen naar volgende berichten, tenzij *expliciet* opnieuw gedefinieerd.

- [`background()`](#background-color)
- [`color()`](#text-color)
- [`size()`](#font-size)
- [`style()`](#font-style)
- [`transform()`](#text-transformation)
- [`weight()`](#font-weight)

:::tip
Gebruik de `setStyle` methode om de eigenschappen van het `BrowserConsole` log te wijzigen die niet zijn gespecificeerd door de builders.
:::

### Achtergrondkleur {#background-color}

Stel de achtergrondkleur in met de `background()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.BackgroundColorBuilder" code='true'>BackgroundColorBuilder</JavadocLink> retourneert.
Gebruik methoden die zijn genoemd naar kleur, zoals `blue()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Achtergrond Voorbeelden
console().background().blue().log("Blauwe achtergrond");
console().background().colored("#031f8f").log("Aangepaste blauwe achtergrond");
```

### Tekstkleur {#text-color}

Stel de tekstkleur in met de `color()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.ColorBuilder" code='true'>ColorBuilder</JavadocLink> retourneert.
Gebruik methoden die zijn genoemd naar kleur, zoals `red()`, of kies een specifieke waarde met `colored(String color)`.

```java
// Kleur Voorbeelden
console().background().red().log("Rode tekst");
console().color().colored("#becad2").log("Aangepaste lichtblauw-grijze tekst");
```

### Lettergrootte {#font-size}

Stel de lettergrootte in met de `size()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontSizeBuilder" code='true'>FontSizeBuilder</JavadocLink> retourneert.
Gebruik methoden die zijn genoemd naar een grootte, zoals `small()`, of kies een specifieke waarde met `from(String value)`.

```java
//Grootte Voorbeelden
console().size().small().log("Kleine letter");
console().size().from("30px").log("30px letter");
```
:::tip
De `from(String value)` methode kan andere lettergroottewaarden accepteren, zoals rem en vw.
:::

### Letterstijl {#font-style}

Stel de letterstijl in met de `style()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontStyleBuilder" code='true'>FontStyleBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `italic()` methode om het consolelog cursief te maken.

```java
// Stijl Voorbeelden
console().style().italic().log("Cursieve letter");
console().style().normal().log("Normale letter");
```

### Teksttransformatie {#text-transformation}

Beheer de kapitalisatie van de karakters in een bericht met de `transform()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.TextTransformBuilder" code='true'>TextTransformBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `capitalize()` methode om de eerste letter van elk woord in hoofdletters om te zetten.

```java
// Transformeer Voorbeelden
// Hoofdletters Tekst Transformatie
console().transform().capitalize().log("Hoofdletters tekst transformatie");
// HOOFDLETTERS TEKST TRANSFORMATIE 
console().transform().uppercase().log("Hoofdletters tekst transformatie");
```

### Lettergewicht {#font-weight}

Stel in hoe dik de tekst is met de `weight()` methode, die de <JavadocLink type="foundation" location="com/webforj/BrowserConsole.FontWeightBuilder" code='true'>FontWeightBuilder</JavadocLink> retourneert.
Gebruik bijvoorbeeld de `ligther()` methode om het lettertype lichter dan normaal te maken.

```java
// Gewicht Voorbeelden
console().weight().bold().log("Vette letter");
console().weight().lighter().log("Lichtere letter");
```

## Stijlen mixen {#mixing-styles}
Het is mogelijk om methoden te mixen en matchen voor een aangepaste logging-weergave.

```java
// Een verscheidenheid aan opties voor een aangepaste logging-weergave
console()
    .weight().bolder()
    .size().larger()
    .color().gray()
    .style().italic()
    .transform().uppercase()
    .background().blue()
    .warn("Stijlen mixen");
```
