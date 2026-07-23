---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
_i18n_hash: 9688647e85d453578ccd59934e52e26b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

`TimeField` is een gebruikersinterfacecomponent die gebruikers in staat stelt om tijden in uren, minuten en optioneel seconden in te voeren of te selecteren. Het biedt een intuïtieve en efficiënte manier om tijdgerelateerde informatie in verschillende applicaties te verwerken.

<!-- INTRO_END -->

## Using the `TimeField` {#using-timefield}

<ParentLink parent="Field" />

`TimeField` breidt de gedeelde `Field`-klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld creëert een herinnerings `TimeField` dat is geïnitialiseerd op de huidige tijd.

<ComponentDemo
path='/webforj/timefield'
files={['src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java']}
/>

## Usages {#usages}

De `TimeField` is ideaal voor het kiezen en weergeven van tijden in je app. Hier zijn enkele voorbeelden van wanneer je de `TimeField` zou moeten gebruiken:

1. **Evenementplanning**: Tijdvelden zijn essentieel in apps die betrekking hebben op het instellen van tijden voor evenementen, afspraken of vergaderingen.

2. **Tijdregistratie en logging**: Apps die tijd bijhouden, zoals tijdregistratie, hebben tijdvelden nodig voor nauwkeurige invoer.

3. **Herinneringen en alarmen**: Het gebruik van een tijdveld vereenvoudigt het invoerproces voor gebruikers die herinneringen of alarmen in je app instellen.

## Min and max value {#min-and-max-value}

Met de methoden `setMin()` en `setMax()` kun je een bereik van aanvaardbare tijden specificeren.

- **Voor `setMin()`**: Als de waarde die in de component is ingevoerd eerder is dan de gespecificeerde minimumtijd, zal de component de validatiebeperkingen niet doorstaan. Wanneer zowel de min- als de max-waarden zijn ingesteld, moet de min-waarde een tijd zijn die gelijk is aan of eerder dan de max-waarde.

- **Voor `setMax()`**: Als de waarde die in de component is ingevoerd later is dan de gespecificeerde maximumtijd, zal de component de validatiebeperkingen niet doorstaan. Wanneer zowel de min- als de max-waarden zijn ingesteld, moet de max-waarde een tijd zijn die gelijk is aan of later dan de min-waarde.

## Value handling and localization {#value-handling-and-localization}

Intern zal de `TimeField`-component zijn waarde weergeven met een `LocalTime`-object uit het `java.time`-pakket. Dit stelt ontwikkelaars in staat om met nauwkeurige tijdwaarden te werken, ongeacht hoe ze visueel worden weergegeven.

Terwijl de **client-side component de tijd weergeeft met de locale van de browser van de gebruiker**, is het geparseerde en opgeslagen formaat altijd gestandaardiseerd als `HH:mm:ss`.

Als je een ruwe tekenreekswaarde instelt, gebruik dan de `setText()`-methode zorgvuldig:

```java
timeField.setText("09:15:00"); // geldig
```

:::warning
 Bij het gebruiken van de `setText()`-methode wordt een `IllegalArgumentException` gegooid als de component de invoer niet in het `HH:mm:ss`-formaat kan parseren.
:::


:::info Picker UI
De uitstraling van de tijdkiezerinvoer UI hangt niet alleen af van de geselecteerde locale, maar ook van de gebruikte browser en het besturingssysteem. Dit zorgt voor automatische consistentie met de interface waarmee gebruikers al vertrouwd zijn.
:::

## Static utilities {#static-utilities}

De `TimeField`-klasse biedt ook de volgende statische hulpfuncties:

- `fromTime(String timeAsString)`: Zet een tijdstring in het HH:mm:ss-formaat om naar een LocalTime-object dat kan worden gebruikt met deze klasse of elders.

- `toTime(LocalTime time)`: Zet een LocalTime om naar een tijdstring in het HH:mm:ss-formaat.

- `isValidTime(String timeAsString)`: Controleert of de gegeven string een geldige HH:mm:ss-tijd is. Dit geeft een boolean-waarde true terug als dat het geval is, anders false.

## Best practices {#best-practices}

- **Bied Heldere Voorbeelden van Tijdformaten**: Toon gebruikers duidelijk het verwachte tijdformaat nabij de `TimeField`. Gebruik voorbeelden of tijdelijke aanduidingen om hen te helpen de tijd correct in te voeren. Als het mogelijk is, toon dan het tijdformaat op basis van de locatie van de gebruiker.

- **Toegankelijkheid**: Maak gebruik van de `TimeField`-component met toegankelijkheid in gedachten, zorg ervoor dat deze voldoet aan de toegankelijkheidsnormen, zoals het bieden van passende labels, voldoende kleurcontrasten en compatibiliteit met ondersteunende technologieën.

- **Resetoptie**: Bied een manier voor gebruikers om de `TimeField` gemakkelijk terug te zetten naar een lege of standaardstatus.
