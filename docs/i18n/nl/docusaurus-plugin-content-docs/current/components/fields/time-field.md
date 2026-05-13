---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: ca6e544259fc218b59cebd14d34e4530
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` is een gebruikersinterfacecomponent die gebruikers in staat stelt om tijden in uren, minuten en optioneel seconden in te voeren of te selecteren. Het biedt een intuïtieve en efficiënte manier om tijdgerelateerde informatie in verschillende toepassingen te verwerken.

<!-- INTRO_END -->

## Gebruik van de `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` breidt de gedeelde `Field`-klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld maakt een herinnering `TimeField` die is geïnitialiseerd op de huidige tijd.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Gebruik {#usages}

De `TimeField` is ideaal voor het kiezen en weergeven van tijden in uw app. Hier zijn enkele voorbeelden van wanneer u de `TimeField` moet gebruiken:

1. **Evenementplanning**: Tijdvelden zijn essentieel in apps die het instellen van tijden voor evenementen, afspraken of vergaderingen omvatten.

2. **Tijdregistratie en logging**: Apps die tijd bijhouden, zoals werkbriefjes, hebben tijdvelden nodig voor nauwkeurige invoer.

3. **Herinneringen en alarmen**: Het gebruik van een tijdveld vereenvoudigt het invoerproces voor gebruikers die herinneringen of alarmen in uw app instellen.

## Min- en maxwaarde {#min-and-max-value}

Met de methoden `setMin()` en `setMax()` kunt u een bereik van aanvaardbare tijden specificeren.

- **Voor `setMin()`**: Als de waarde die in de component is ingevoerd eerder is dan de gespecificeerde minimumtijd, zal de component de constraint-validatie niet doorstaan. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de minwaarde een tijd zijn die gelijk is aan of eerder is dan de maxwaarde.

- **Voor `setMax()`**: Als de waarde die in de component is ingevoerd later is dan de gespecificeerde maximumtijd, zal de component de constraint-validatie niet doorstaan. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de maxwaarde een tijd zijn die gelijk is aan of later is dan de minwaarde.

## Waarde-afhandeling en lokalisatie {#value-handling-and-localization}

Intern vertegenwoordigt de `TimeField`-component zijn waarde met behulp van een `LocalTime`-object uit het `java.time`-pakket. Dit stelt ontwikkelaars in staat om met precieze tijdwaarden te werken, ongeacht hoe ze visueel worden weergegeven.

Terwijl de **client-side component de tijd weergeeft met behulp van de browserlocale van de gebruiker**, is het geparsed en opgeslagen formaat altijd gestandaardiseerd als `HH:mm:ss`.

Als u een ruwe tekenreekswaarde instelt, gebruik dan de `setText()`-methode voorzichtig:

```java
timeField.setText("09:15:00"); // geldig
```

:::warning
 Wanneer de `setText()`-methode wordt gebruikt, wordt er een `IllegalArgumentException` opgegooid als de component de invoer niet kan parseren in het `HH:mm:ss`-formaat.
:::


:::info Picker UI 
Het uiterlijk van de tijdkiezerinvoer-UI hangt niet alleen af van de geselecteerde locale, maar ook van de gebruikte browser en het besturingssysteem. Dit zorgt voor automatische consistentie met de interface waar gebruikers al bekend mee zijn.
:::

## Statische hulpprogramma's {#static-utilities}

De `TimeField`-klasse biedt ook de volgende statische hulpmethoden:

- `fromTime(String timeAsString)`: Converteer een tijdtekenreeks in HH:mm:ss-formaat naar een LocalTime-object, dat vervolgens met deze klasse of elders kan worden gebruikt.

- `toTime(LocalTime time)`: Converteer een LocalTime naar een tijdtekenreeks in HH:mm:ss-formaat.

- `isValidTime(String timeAsString)`: Controleer of de gegeven tekenreeks een geldige HH:mm:ss-tijd is. Dit retourneert een booleanwaarde true als dat zo is, false anders.

## Beste praktijken {#best-practices}

- **Bied Duidelijke Voorbeelden van Tijdformaten**: Laat gebruikers duidelijk het verwachte tijdformaat zien nabij de `TimeField`. Gebruik voorbeelden of placeholders om hen te helpen de tijd correct in te voeren. Indien mogelijk, toon het tijdformaat op basis van de locatie van de gebruiker.

- **Toegankelijkheid**: Gebruik de `TimeField`-component met toegankelijkheid in gedachten en zorg ervoor dat deze voldoet aan de toegankelijkheidsnormen, zoals het bieden van de juiste labels, voldoende kleurcontrast en compatibiliteit met hulpprogramma's voor mensen met een beperking.

- **Resetoptie**: Bied een manier voor gebruikers om de `TimeField` eenvoudig terug te zetten naar een lege of standaardtoestand.
