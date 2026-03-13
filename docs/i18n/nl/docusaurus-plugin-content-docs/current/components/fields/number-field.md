---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: e1cde7099182ddabd898e0c5391fe8b7
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

De `NumberField` component accepteert numerieke invoer en wijst automatisch ongeldige waarden af. Het ondersteunt minimum- en maximumgrenzen, stapintervallen en plaatsaanduidingstekst.

<!-- INTRO_END -->

## Gebruik van `NumberField` {#using-numberfield}

<ParentLink parent="Field" />

`NumberField` breidt de gedeelde `Field`-klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld maakt een `NumberField` aan met een label en plaatsaanduidingstekst.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Veldwaarde {#field-value}

De `NumberField` component slaat zijn waarde op als een `Double`, waardoor nauwkeurige verwerking van zowel gehele als decimale getallen mogelijk is.

### De huidige waarde ophalen {#getting-the-current-value}

Je kunt de numerieke waarde die door de gebruiker is ingevoerd ophalen met:

```java
Double currentValue = numberField.getValue();
```

### Een nieuwe waarde instellen {#setting-a-new-value}

Om het veld programmatisch in te stellen:

```java
numberField.setValue(42.5);
```

Als er geen waarde is ingevoerd en er geen standaardwaarde is ingesteld, retourneert `getValue()` `null`.

:::tip
Hoewel het veld is ontworpen om alleen geldige numerieke invoer te accepteren, houd er rekening mee dat de onderliggende waarde nullabel is. Test altijd op null voordat je het resultaat gebruikt.
:::

## Toepassingen {#usages}

De `NumberField` is het meest geschikt in scenario's waarin het vastleggen, weergeven of manipuleren van numerieke gegevens essentieel is voor je app. Hier zijn enkele voorbeelden van wanneer je de `NumberField` kunt gebruiken:

1. **Numerieke Invoervormen**: Bij het ontwerpen van formulieren die numerieke invoer vereisen, vereenvoudigt het gebruik van een `NumberField` het invoerproces voor gebruikers. Dit is bijzonder nuttig voor applicaties die gebruikersgegevens verzamelen of numerieke waarden vereisen.

2. **Gegevensanalyse en Berekeningen**: Een `NumberField` is bijzonder waardevol in apps die gegevensanalyse, berekeningen of wiskundige bewerkingen omvatten. Het stelt gebruikers in staat om nauwkeurig numerieke waarden in te voeren of te manipuleren.

3. **Financiële en Budgetteringsapplicaties**: Apps die financiële berekeningen, budgettering of het volgen van uitgaven omvatten, vereisen vaak nauwkeurige numerieke invoer. Een `NumberField` zorgt voor een nauwkeurige invoer van financiële cijfers.

4. **Metingen en Eenheidsconversie**: In apps die met metingen of eenheidsconversies omgaan, is de `NumberField` ideaal voor het invoeren van numerieke waarden met eenheden zoals lengte, gewicht of volume.

## Minimum- en maximumwaarde {#min-and-max-value}

Met de `setMin()`-methode kun je de minimaal toegestane waarde in het nummergebied specificeren. Als een gebruiker een waarde onder deze drempel invoert, zal de component de validatiebeperkingen niet halen en geschikte feedback geven.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Minimale toegestaan: 0.0
```

Daarnaast stelt de `setMax()`-methode je in staat om de maximaal toegestane waarde te definiëren. Als een gebruiker een waarde boven deze limiet invoert, wordt de invoer afgewezen. Wanneer zowel minimum- als maximumwaarden zijn ingesteld, moet de maximumwaarde groter zijn dan of gelijk zijn aan de minimumwaarde.

```java
numberField.setMax(100.0); // Maximale toegestaan: 100.0
```

In deze configuratie zou het invoeren van een waarde zoals -5 of 150 ongeldig zijn, terwijl waarden tussen 0 en 100 worden geaccepteerd.

## Granulariteit {#granularity}

Je kunt de `setStep()`-methode gebruiken om de granulariteit op te geven waar de waarde aan moet voldoen bij het gebruiken van de pijltoetsen om de waarde te wijzigen. Dit verhoogt of verlaagt de waarde van de component met een bepaalde stap telkens weer. Dit geldt niet wanneer een gebruiker een waarde rechtstreeks invoert, maar alleen bij interactie met de `NumberField` met de pijltoetsen.

## Plaatsaanduidingstekst {#placeholder-text}

Je kunt plaatsaanduidingstekst instellen voor de `NumberField` met behulp van de `setPlaceholder()`-methode. De plaatsaanduidingstekst wordt weergegeven wanneer het veld leeg is, wat helpt om de gebruiker aan te sporen om geschikte invoer in de `NumberField` te doen.

:::tip Geef duidelijke context voor nauwkeurigheid
Als de numerieke invoer betrekking heeft op een specifieke eenheid van meting of een bepaalde context heeft, geef dan duidelijke labels of aanvullende informatie om gebruikers te begeleiden en nauwkeurige invoer te waarborgen.
:::

## Best Practices {#best-practices}

Om een naadloze integratie en optimale gebruikerservaring te waarborgen, overweeg de volgende best practices bij het gebruik van de `NumberField`:

- **Toegankelijkheid**: Gebruik de `NumberField`-component met toegankelijkheid in gedachten en houd je aan toegankelijkheidsnormen zoals goede labeling, ondersteuning voor toetsenbordnavigatie en compatibiliteit met behulp van assistieve technologieën. Zorg ervoor dat gebruikers met een handicap effectief kunnen interageren met de `NumberField`.

- **Gebruik Incrementeer-/decrementeerknoppen**: Als dit geschikt is voor je app, overweeg dan om incrementeer- en decrementeerknoppen te gebruiken met de `NumberField`. Dit stelt gebruikers in staat om de numerieke waarde met een specifieke increment of decrement met één klik aan te passen.
