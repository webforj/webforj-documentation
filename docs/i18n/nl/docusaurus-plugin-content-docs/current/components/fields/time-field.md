---
sidebar_position: 40
title: TimeField
slug: timefield
description: >-
  A component that provides a default browser-based time picker for selecting a
  time value through an input field.
sidebar_class_name: updated-content
_i18n_hash: aa5cbd6fb54c91be419380eeaf26e65b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TimeField" top='true'/>

<ParentLink parent="Field" />

`TimeField` is een gebruikersinterfacecomponent die gebruikers in staat stelt om tijden in te voeren of te selecteren in uren, minuten en optioneel seconden. Het biedt een intuïtieve en efficiënte manier om tijdsgerelateerde informatie in verschillende toepassingen te beheren.

<ComponentDemo 
path='/webforj/timefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/timefield/TimeFieldView.java'
/>

## Usages {#usages}

De `TimeField` is ideaal voor het kiezen en weergeven van tijden in jouw app. Hier zijn enkele voorbeelden van wanneer je de `TimeField` kunt gebruiken:

1. **Evenementplanning**: Tijdvelden zijn essentieel in apps die het instellen van tijden voor evenementen, afspraken of vergaderingen omvatten.

2. **Tijdregistratie en logging**: Apps die tijd bijhouden, zoals werkuren, hebben tijdvelden nodig voor nauwkeurige invoer.

3. **Herinneringen en alarmen**: Het gebruik van een tijdveld vereenvoudigt het invoerproces voor gebruikers die herinneringen of alarmen in jouw app instellen.

## Min and max value {#min-and-max-value}

Met de methoden `setMin()` en `setMax()` kun je een bereik van acceptabele tijden opgeven.

- **Voor `setMin()`**: Als de waarde die in de component is ingevoerd eerder is dan de opgegeven minimumtijd, zal de component falen bij de validatie van de beperkingen. Wanneer zowel de min- als max-waarden zijn ingesteld, moet de min-waarde een tijd zijn die gelijk is aan of eerder dan de max-waarde.

- **Voor `setMax()`**: Als de waarde die in de component is ingevoerd later is dan de opgegeven maximumtijd, zal de component falen bij de validatie van de beperkingen. Wanneer zowel de min- als max-waarden zijn ingesteld, moet de max-waarde een tijd zijn die gelijk is aan of later dan de min-waarde.

## Value handling and localization {#value-handling-and-localization}

Intern heeft de `TimeField` component zijn waarde vertegenwoordigd met een `LocalTime` object uit het `java.time` pakket. Dit stelt ontwikkelaars in staat om met exacte tijdwaarden te werken, ongeacht hoe ze visueel worden weergegeven.

Terwijl de **client-side component de tijd weergeeft met de lokale instellingen van de browser van de gebruiker**, is het geparsed en opgeslagen formaat altijd gestandaardiseerd als `HH:mm:ss`.

Als je een ruwe stringwaarde instelt, gebruik dan de `setText()` methode voorzichtig:

```java
timeField.setText("09:15:00"); // geldig
```

:::warning
 Wanneer je de `setText()` methode gebruikt, zal er een `IllegalArgumentException` worden opgegooid als de component de invoer niet kan parseren in het `HH:mm:ss` formaat.
:::

:::info Picker UI 
Het uiterlijk van de tijdkiezerinvoerveld hangt niet alleen af van de geselecteerde lokale instelling, maar ook van de gebruikte browser en het besturingssysteem. Dit zorgt voor automatische consistentie met de interface waar gebruikers al mee vertrouwd zijn.
:::

## Static utilities {#static-utilities}

De `TimeField` klasse biedt ook de volgende statische hulpprogramma's:

- `fromTime(String timeAsString)`: Converteer een tijdstring in HH:mm:ss formaat naar een LocalTime object dat vervolgens met deze klasse of elders kan worden gebruikt.

- `toTime(LocalTime time)`: Converteer een LocalTime naar een tijdstring in HH:mm:ss formaat.

- `isValidTime(String timeAsString)`: Controleer of de gegeven string een geldige HH:mm:ss tijd is. Dit retourneert een booleaanse waarde true als dat zo is, anders false.

## Best practices {#best-practices}

- **Bied duidelijke tijdformaatvoorbeelden**: Toon gebruikers duidelijk het verwachte tijdformaat nabij de `TimeField`. Gebruik voorbeelden of tijdelijke aanduidingen om hen te helpen de tijd correct in te voeren. Als het mogelijk is, geef het tijdformaat weer op basis van de locatie van de gebruiker.

- **Toegankelijkheid**: Gebruik de `TimeField` component met toegankelijkheid in gedachten, zodat deze voldoet aan de toegankelijkheidsnormen, zoals het bieden van geschikte labels, voldoende kleurcontrast en compatibiliteit met hulpmiddelen voor assistentie.

- **Resetoptie**: Bied een manier voor gebruikers om de `TimeField` gemakkelijk terug te zetten naar een lege of standaardstatus.
