---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: 6421e3007af8e795adefa317a13363f0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` is een gebruikersinterfacecomponent die gebruikers in staat stelt om tijden in uren, minuten en optioneel seconden in te voeren of te selecteren. Het biedt een intuïtieve en efficiënte manier om tijdgerelateerde informatie in verschillende applicaties te beheren.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages {#usages}

De `TimeField` is ideaal voor het kiezen en weergeven van tijden in je app. Hier zijn enkele voorbeelden van wanneer je de `TimeField` kunt gebruiken:

1. **Evenementplanning**: Tijdvelden zijn essentieel in apps die betrekking hebben op het instellen van tijden voor evenementen, afspraken of vergaderingen.

2. **Tijdregistratie en logging**: Apps die tijd registreren, zoals urenlijsten, hebben tijdvelden nodig voor nauwkeurige invoer.

3. **Herinneringen en alarmen**: Het gebruik van een tijdveld vereenvoudigt het invoerproces voor gebruikers die herinneringen of alarmen in je app instellen.

## Min- en maxwaarde {#min-and-max-value}

Met de methoden `setMin()` en `setMax()` kun je een reeks acceptabele tijden specificeren.

- **Voor `setMin()`**: Als de waarde die in de component wordt ingevoerd eerder is dan de gespecificeerde minimumtijd, faalt de component in het valideren van de beperkingen. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de minwaarde een tijd zijn die gelijk is aan of eerder is dan de maxwaarde.

- **Voor `setMax()`**: Als de waarde die in de component wordt ingevoerd later is dan de gespecificeerde maximumtijd, faalt de component in het valideren van de beperkingen. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de maxwaarde een tijd zijn die gelijk is aan of later is dan de minwaarde.

## Waardebehandeling en lokalisatie {#value-handling-and-localization}

Intern vertegenwoordigt de `TimeField` component zijn waarde met behulp van een `LocalTime` object uit het `java.time` pakket. Dit stelt ontwikkelaars in staat om interactie te hebben met nauwkeurige tijdwaarden, ongeacht hoe ze visueel worden weergegeven.

Hoewel de **client-side component de tijd weergeeft met behulp van de browser-locale van de gebruiker**, is het geparseerde en opgeslagen formaat altijd gestandaardiseerd als `HH:mm:ss`.

Als je een ruwe tekenreekswaarde instelt, gebruik dan de `setText()` methode voorzichtig:

```java
timeField.setText("09:15:00"); // geldig
```

:::warning
Wanneer je de `setText()` methode gebruikt, wordt er een `IllegalArgumentException` opgegooid als de component de invoer niet kan parseren in het `HH:mm:ss` formaat.
:::

:::info Picker UI 
De uitstraling van de tijdkiezer invoerinterface hangt niet alleen af van de geselecteerde locale, maar ook van de browser en het besturingssysteem dat wordt gebruikt. Dit zorgt voor automatische consistentie met de interface waar gebruikers al mee bekend zijn.
:::

## Statische hulpprogramma's {#static-utilities}

De `TimeField` klasse biedt ook de volgende statische hulpprogramma-methoden:

- `fromTime(String timeAsString)`: Converteer een tijdstring in HH:mm:ss formaat naar een `LocalTime` object dat vervolgens met deze klasse of elders kan worden gebruikt.

- `toTime(LocalTime time)`: Converteer een `LocalTime` naar een tijdstring in HH:mm:ss formaat.

- `isValidTime(String timeAsString)`: Controleer of de gegeven string een geldige HH:mm:ss tijd is. Dit retourneert een boolean waarde true als dat zo is, false anders.

## Beste praktijken {#best-practices}

- **Bied duidelijke voorbeelden van tijdformaten**: Toon gebruikers duidelijk het verwachte tijdformaat nabij de `TimeField`. Gebruik voorbeelden of plaatsaanduidingen om hen te helpen de tijd correct in te voeren. Als het mogelijk is, toon dan het tijdformaat op basis van de locatie van de gebruiker.

- **Toegankelijkheid**: Gebruik de `TimeField` component met toegankelijkheid in gedachten, en zorg ervoor dat deze voldoet aan toegankelijkheidsnormen zoals het bieden van juiste labels, voldoende kleurcontrast en compatibiliteit met ondersteunende technologieën.

- **Resetoptie**: Bied een manier voor gebruikers om de `TimeField` gemakkelijk leeg of op de standaardstatus terug te zetten.
