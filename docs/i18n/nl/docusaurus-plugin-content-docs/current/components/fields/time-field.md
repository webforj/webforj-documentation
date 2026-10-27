---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9b4c187f1c86207e13b38812f0eb7e6c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` is een gebruikersinterfacecomponent waarmee gebruikers tijden met uren en minuten precisie kunnen invoeren of selecteren. Indien er seconden worden opgegeven, worden deze door de component genegeerd.

<!-- INTRO_END -->

## Gebruik van de `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` breidt de gedeelde `Field`-klasse uit, die algemene functies biedt voor alle veldcomponenten. Het volgende voorbeeld creëert een herinnerings `TimeField` die is geïnitialiseerd op de huidige tijd.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Gebruik {#usages}

De `TimeField` is ideaal voor het kiezen en weergeven van tijden in uw app. Hier zijn enkele voorbeelden van wanneer u de `TimeField` kunt gebruiken:

1. **Evenementplanning**: Tijdvelden zijn essentieel in apps die betrokken zijn bij het instellen van tijden voor evenementen, afspraken of vergaderingen.

2. **Tijdregistratie en -logging**: Apps die tijd bijhouden, zoals uurregistraties, hebben tijdvelden nodig voor nauwkeurige invoer.

3. **Herinneringen en Alarmen**: Het gebruik van een tijdveld vereenvoudigt het invoerproces voor gebruikers die in uw app herinneringen of alarmen instellen.

## Min- en maxwaarde {#min-and-max-value}

Met de methoden `setMin()` en `setMax()` kunt u een bereik van acceptabele tijden opgeven.

- **Voor `setMin()`**: Als de waarde die in de component is ingevoerd eerder is dan de opgegeven minimale tijd, zal de component falen voor de constraintvalidatie. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de minwaarde een tijd zijn die gelijk is aan of eerder dan de maxwaarde.

- **Voor `setMax()`**: Als de waarde die in de component is ingevoerd later is dan de opgegeven maximale tijd, zal de component falen voor de constraintvalidatie. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de maxwaarde een tijd zijn die gelijk is aan of later dan de minwaarde.

## Waarde-afhandeling en lokalisatie {#value-handling-and-localization}

Intern vertegenwoordigt de `TimeField`-component zijn waarde met een `LocalTime`-object uit het `java.time`-pakket. Dit stelt ontwikkelaars in staat om met nauwkeurige tijdwaarden om te gaan, ongeacht hoe ze visueel worden weergegeven.

De browser bepaalt hoe de picker de tijd weergeeft voor de locale van de gebruiker. De tekstwaarde van het veld gebruikt het 24-uurs `HH:mm`-formaat, en de `LocalTime`-waarde wordt afgekapt op minuten.

Als u een ruwe tekenreekswaarde instelt, gebruik dan de methode `setText()` voorzichtig:

```java
timeField.setText("09:15");    // geldig
timeField.setText("09:15:30"); // ook geldig; seconden worden geblokkeerd, waardoor 09:15 overblijft
```

:::warning
Wanneer u `setText()` gebruikt, wordt er een `IllegalArgumentException` opgegeven als de invoer niet kan worden geparsed als een geldige tijd. Zowel `HH:mm` als `HH:mm:ss` invoer zijn geaccepteerd, maar seconden worden genegeerd.
:::


:::info Picker UI
Het uiterlijk van de tijdpicker input UI hangt af van de geselecteerde locale, de browser en het besturingssysteem. Dit creëert automatische consistentie met de interface waarmee gebruikers al bekend zijn.
:::

## Statische hulpprogramma's {#static-utilities}

De `TimeField`-klasse biedt ook de volgende statische hulpprogramma's:

- `fromTime(String timeAsString)`: Parse een tijdstring, met of zonder seconden, in een `LocalTime` afgekapt op minuten.

- `toTime(LocalTime time)`: Converteer een `LocalTime` naar een string in `HH:mm`-formaat, waarbij seconden worden genegeerd.

- `isValidTime(String timeAsString)`: Controleer of een tijdstring geldig is, inclusief `HH:mm` en `HH:mm:ss` invoer. Retourneert `true` als het geldig is en `false` anders.

## Beste praktijken {#best-practices}

- **Bied Duidelijke Voorbeelden van Tijdformaten**: Geef duidelijk aan welke tijdformaat van gebruikers wordt verwacht bij de `TimeField`. Gebruik voorbeelden of plaatsaanduidingen om hen te helpen de tijd correct in te voeren. Indien mogelijk, geef het tijdformaat weer op basis van de locatie van de gebruiker.

- **Toegankelijkheid**: Gebruik de `TimeField`-component met toegankelijkheid in gedachten, en voldoe aan toegankelijkheidsnormen zoals geschikte labels, voldoende kleurcontrast en compatibiliteit met assistieve technologieën.

- **Resetoptie**: Bied een manier voor gebruikers om de `TimeField` eenvoudig te wissen naar een lege of standaardtoestand.
