---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: aa5037e2faa2968328081b1811dcabb0
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

De `NumberField` component accepteert numerieke invoer en weigert automatisch ongeldige waarden. Het ondersteunt minimum- en maximumgrenzen, stapintervallen en plaatsaanduidingstekst.

<!-- INTRO_END -->

## Using `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField` breidt de gedeelde `Field` class uit, die gemeenschappelijke functies biedt voor alle invoerveldcomponenten. Het volgende voorbeeld creëert een `NumberField` met een label en plaatsaanduidingstekst.

<ComponentDemo
path='/webforj/numberfield'
files={['src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java']}
/>

## Field value {#field-value}

De `NumberField` component slaat zijn waarde op als een `Double`, waardoor nauwkeurige verwerking van zowel gehele getallen als decimale nummers mogelijk is.

### Getting the current value {#getting-the-current-value}

Je kunt de numerieke waarde die door de gebruiker is ingevoerd ophalen met:

```java
Double currentValue = numberField.getValue();
```

### Setting a new value {#setting-a-new-value}

Om de waarde van het veld programatisch in te stellen:

```java
numberField.setValue(42.5);
```

Als er geen waarde is ingevoerd en er geen standaardwaarde is ingesteld, retourneert `getValue()` `null`.

:::tip
Hoewel het veld is ontworpen om alleen geldige numerieke invoer te accepteren, houd er rekening mee dat de onderliggende waarde nullable is. Test altijd op null voordat je het resultaat gebruikt.
:::

## Usages {#usages}

De `NumberField` is het beste in gebruik in scenario's waarin het vastleggen, weergeven of manipuleren van numerieke gegevens essentieel is voor je app. Hier zijn enkele voorbeelden van wanneer je de `NumberField` kunt gebruiken:

1. **Numerieke Invoervelden**: Bij het ontwerpen van formulieren die numerieke invoer vereisen, vereenvoudigt het gebruik van een `NumberField` het invoerproces voor gebruikers. Dit is vooral handig voor toepassingen die gebruikersgegevens verzamelen of numerieke waarden vereisen.

2. **Gegevensanalyse en Berekeningen**: Een `NumberField` is bijzonder waardevol in apps die gegevensanalyse, berekeningen of wiskundige bewerkingen omvatten. Ze stellen gebruikers in staat om nauwkeurig numerieke waarden in te voeren of te manipuleren.

3. **Financiële en Budgetteringstoepassingen**: Apps die financiële berekeningen, budgettering of het bijhouden van uitgaven omvatten, vereisen vaak nauwkeurige numerieke invoer. Een `NumberField` zorgt voor een nauwkeurige invoer van financiële cijfers.

4. **Metingen en Eenheidconversie**: In apps die metingen of eenheidconversies behandelen, is de `NumberField` ideaal voor het invoeren van numerieke waarden met eenheden zoals lengte, gewicht of volume.

## Min and max value {#min-and-max-value}

Met de `setMin()` methode kun je de minimum aanvaardbare waarde in het nummerveld specificeren. Als een gebruiker een waarde invoert die lager is dan deze drempel, zal de component de validatie van de beperking niet doorstaan en gepaste feedback geven.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Minimum toegestaan: 0.0
```

Apart hiervan stelt de `setMax()` methode je in staat om de maximum aanvaardbare waarde te definiëren. Als een gebruiker een waarde invoert die hoger is dan deze limiet, wordt de invoer geweigerd. Wanneer zowel minimum- als maximumwaarden zijn ingesteld, moet de maximumwaarde groter dan of gelijk zijn aan de minimumwaarde.

```java
numberField.setMax(100.0); // Maximum toegestaan: 100.0
```

In deze configuratie zou het invoeren van een waarde zoals -5 of 150 ongeldig zijn, terwijl waarden tussen 0 en 100 worden geaccepteerd.

## Granularity {#granularity}

Je kunt de `setStep()` methode gebruiken om de granulariteit aan te geven waaraan de waarde moet voldoen wanneer je met de pijltjestoetsen de waarde wijzigt. Dit verhoogt of verlaagt de waarde van de component met een bepaalde stap telkens weer. Dit is niet van toepassing wanneer een gebruiker een waarde direct invoert, maar alleen wanneer de `NumberField` met de pijltjestoetsen wordt bediend.

## Placeholder text {#placeholder-text}

Je kunt een plaatsaanduidingstekst voor de `NumberField` instellen met de `setPlaceholder()` methode. De plaatsaanduidingstekst wordt weergegeven wanneer het veld leeg is, waardoor de gebruiker wordt aangespoord om passende invoer in de `NumberField` te geven.

:::tip Geef duidelijke context voor nauwkeurigheid
Als de numerieke invoer gerelateerd is aan een specifieke eenheid van meting of een bepaalde context heeft, geef dan duidelijke labeling of aanvullende informatie om gebruikers te begeleiden en nauwkeurige invoer te waarborgen.
:::

## Best practices {#best-practices}

Om een naadloze integratie en optimale gebruikerservaring te waarborgen, overweeg de volgende best practices bij het gebruik van de `NumberField`:

- **Toegankelijkheid**: Maak gebruik van de `NumberField` component met toegankelijkheid in gedachten, met inachtneming van toegankelijkheidsnormen zoals correcte labeling, ondersteuning voor toetsenbordnavigatie en compatibiliteit met ondersteunende technologieën. Zorg ervoor dat gebruikers met een handicap effectief met de `NumberField` kunnen interageren.

- **Gebruik Incrementeer/Decrementeer Knoppen**: Als dat passend is voor je app, overweeg dan om incrementeer- en decrementeerknoppen te gebruiken met de `NumberField`. Dit stelt gebruikers in staat om de numerieke waarde met een specifieke increment of decrement met één klik aan te passen.
