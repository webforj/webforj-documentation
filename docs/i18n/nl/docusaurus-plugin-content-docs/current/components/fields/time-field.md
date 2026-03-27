---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 994cad91e2870d59f3c0eec7c2b47141
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` is een gebruikersinterfacecomponent die gebruikers in staat stelt om tijden in uren, minuten en optioneel seconden in te voeren of te selecteren. Het biedt een intuïtieve en efficiënte manier om tijdgerelateerde informatie in verschillende applicaties te verwerken.

<!-- INTRO_END -->

## Gebruik van de `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` breidt de gedeelde `Field`-klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld creëert een herinnering `TimeField` dat is ingesteld op de huidige tijd.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Gebruik {#usages}

De `TimeField` is ideaal voor het kiezen en weergeven van tijden in uw app. Hier zijn enkele voorbeelden van wanneer u de `TimeField` kunt gebruiken:

1. **Evenementplanning**: Tijdvelden zijn essentieel in apps die tijden voor evenementen, afspraken of vergaderingen instellen.

2. **Tijdregistratie en logging**: Apps die tijd bijhouden, zoals urenstaten, hebben tijdvelden nodig voor accurate invoer.

3. **Herinneringen en alarmen**: Het gebruik van een tijdveld vereenvoudigt het invoerproces voor gebruikers die herinneringen of alarmen in uw app instellen.

## Min- en maxwaarde {#min-and-max-value}

Met de methoden `setMin()` en `setMax()` kunt u een bereik van acceptabele tijden opgeven.

- **Voor `setMin()`**: Als de waarde die in de component is ingevoerd eerder is dan de opgegeven minimumtijd, zal de component niet voldoen aan de validatiebeperkingen. Wanneer zowel de min- als de maxwaarden zijn ingesteld, moet de minwaarde een tijd zijn die gelijk is aan of eerder is dan de maxwaarde.

- **Voor `setMax()`**: Als de waarde die in de component is ingevoerd later is dan de opgegeven maximumtijd, zal de component niet voldoen aan de validatiebeperkingen. Wanneer zowel de min- als de maxwaarden zijn ingesteld, moet de maxwaarde een tijd zijn die gelijk is aan of later is dan de minwaarde.

## Waardeafhandeling en lokalisatie {#value-handling-and-localization}

Intern vertegenwoordigt de `TimeField`-component zijn waarde met een `LocalTime`-object uit het `java.time`-pakket. Dit stelt ontwikkelaars in staat om met precieze tijdwaarden te werken, ongeacht hoe deze visueel worden weergegeven.

Hoewel de **client-side component de tijd weergeeft met de lokale instellingen van de browser van de gebruiker**, is het geparsed en opgeslagen formaat altijd gestandaardiseerd als `HH:mm:ss`.

Als u een ruwe tekenreekswaarde instelt, gebruik dan de methode `setText()` voorzichtig:

```java
timeField.setText("09:15:00"); // geldig
```

:::warning
 Wanneer u de methode `setText()` gebruikt, wordt een `IllegalArgumentException` opgegooid als de component de invoer niet in het `HH:mm:ss`-formaat kan parseren.
:::


:::info Picker UI 
Het uiterlijk van de tijdkiezerinvoer-UI hangt niet alleen af van de geselecteerde locatie, maar ook van de browser en het besturingssysteem dat wordt gebruikt. Dit zorgt voor automatische consistentie met de interface waarmee gebruikers al bekend zijn.
:::

## Statistische hulpprogramma's {#static-utilities}

De `TimeField`-klasse biedt ook de volgende statische hulpprogramma's:

- `fromTime(String timeAsString)`: Converteer een tijdstring in HH:mm:ss-formaat naar een LocalTime-object dat vervolgens met deze klasse of elders kan worden gebruikt.

- `toTime(LocalTime time)`: Converteer een LocalTime naar een tijdstring in HH:mm:ss-formaat.

- `isValidTime(String timeAsString)`: Controleer of de gegeven string een geldige HH:mm:ss tijd is. Dit retourneert een boolean waarde true als dat zo is, false anders.

## Best practices {#best-practices}

- **Voorzie Heldere Voorbeelden van Tijdformaten**: Toon gebruikers duidelijk het verwachte tijdformaat nabij de `TimeField`. Gebruik voorbeelden of placeholders om hen te helpen de tijd correct in te voeren. Indien mogelijk, geef het tijdformaat weer op basis van de locatie van de gebruiker.

- **Toegankelijkheid**: Gebruik de `TimeField`-component met toegankelijkheid in gedachten, en zorg ervoor dat deze voldoet aan toegankelijkheidsnormen zoals het bieden van de juiste labels, voldoende kleurencontrast en compatibiliteit met ondersteunende technologieën.

- **Resetoptie**: Bied een manier voor gebruikers om de `TimeField` gemakkelijk te wissen naar een lege of standaardtoestand.
