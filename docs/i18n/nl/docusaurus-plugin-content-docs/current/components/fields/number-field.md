---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 2fcf0727f1bcfd60a2800bad252733ba
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

U kunt de `NumberField`-component gebruiken om numerieke invoer van een gebruiker te accepteren. Het zorgt ervoor dat alleen geldige numerieke waarden worden ingevoerd en biedt een handige interface voor het invoeren van nummers.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Waarde van het veld {#field-value}

De `NumberField`-component slaat zijn waarde op als een `Double`, waardoor nauwkeurige verwerking van zowel gehele getallen als decimale getallen mogelijk is.

### Het huidige waarde ophalen {#getting-the-current-value}

U kunt de numerieke waarde die door de gebruiker is ingevoerd ophalen met:

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
Hoewel het veld is ontworpen om alleen geldige numerieke invoer te accepteren, moet u er rekening mee houden dat de onderliggende waarde nullbaar is. Controleer altijd op null voordat u het resultaat gebruikt.
:::

## Gebruik {#usages}

De `NumberField` is het beste te gebruiken in situaties waarin het vastleggen, weergeven of manipuleren van numerieke gegevens essentieel is voor uw app. Hier zijn enkele voorbeelden van wanneer u de `NumberField` kunt gebruiken:

1. **Numerieke invoervelden**: Wanneer u formulieren ontwerpt die numerieke invoer vereisen, vereenvoudigt het gebruik van een `NumberField` het invoerproces voor gebruikers. Dit is vooral nuttig voor toepassingen die gebruikersgegevens verzamelen of numerieke waarden vereisen.

2. **Gegevensanalyse en berekeningen**: Een `NumberField` is bijzonder waardevol in apps die gegevensanalyse, berekeningen of wiskundige bewerkingen omvatten. Hiermee kunnen gebruikers nauwkeurig numerieke waarden invoeren of manipuleren.

3. **Financiële en budgetteringstoepassingen**: Apps die financiële berekeningen, budgettering of het bijhouden van uitgaven omvatten, vereisen vaak nauwkeurige numerieke invoer. Een `NumberField` zorgt voor een nauwkeurige invoer van financiële cijfers.

4. **Metingen en eenheidsconversie**: In apps die metingen of eenheidsconversies behandelen, is de `NumberField` ideaal voor het invoeren van numerieke waarden met eenheden zoals lengte, gewicht of volume.

## Min- en maxwaarde {#min-and-max-value}

Met de `setMin()`-methode kunt u de minimum aanvaardbare waarde in het nummer veld specificeren. Als een gebruiker een waarde invoert die lager is dan deze drempel, zal de component de beperkingvalidatie niet doorstaan en geschikte feedback geven.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Minimum toegestaan: 0.0
```

Daarnaast stelt de `setMax()`-methode u in staat om de maximum aanvaardbare waarde te definiëren. Als een gebruiker een waarde invoert die hoger is dan deze limiet, wordt de invoer afgewezen. Wanneer zowel minimum- als maximumwaarden zijn ingesteld, moet de maximumwaarde groter dan of gelijk zijn aan de minimumwaarde.

```java
numberField.setMax(100.0); // Maximum toegestaan: 100.0
```

In deze configuratie zou het invoeren van een waarde zoals -5 of 150 ongeldig zijn, terwijl waarden tussen 0 en 100 geaccepteerd worden.

## Granulariteit {#granularity}

U kunt de `setStep()`-methode gebruiken om de granulariteit te specificeren waaraan de waarde moet voldoen wanneer u de pijltoetsen gebruikt om de waarde te wijzigen. Dit verhoogt of verlaagd de waarde van de component met een bepaalde stap elke keer. Dit is niet van toepassing wanneer een gebruiker een waarde rechtstreeks invoert, maar alleen wanneer hij interactie heeft met de `NumberField` via de pijltoetsen.

## Placeholdertekst {#placeholder-text}

U kunt placeholdertekst voor de `NumberField` instellen met de `setPlaceholder()`-methode. De placeholdertekst wordt weergegeven wanneer het veld leeg is, om de gebruiker te helpen passende invoer in de `NumberField` in te voeren.

:::tip Geef duidelijke context voor nauwkeurigheid
Als de numerieke invoer betrekking heeft op een specifieke eenheid van meting of een bepaalde context heeft, geef dan duidelijke labeling of aanvullende informatie om gebruikers te begeleiden en nauwkeurige invoer te waarborgen.
:::

## Beste praktijken {#best-practices}

Om een naadloze integratie en optimale gebruikservaring te waarborgen, overweeg de volgende beste praktijken bij het gebruik van de `NumberField`:

- **Toegankelijkheid**: Gebruik de `NumberField`-component met toegankelijkheid in gedachten, en houd u aan toegankelijkheidsnormen zoals juiste labeling, ondersteuning voor toetsenbordnavigatie en compatibiliteit met ondersteunende technologieën. Zorg ervoor dat gebruikers met een beperking effectief kunnen omgaan met de `NumberField`.

- **Gebruik incrementele/decimale knoppen**: Als dat geschikt is voor uw app, overweeg dan om incrementele en decimale knoppen te gebruiken met de `NumberField`. Dit stelt gebruikers in staat om de numerieke waarde met een specifieke verhoging of verlaging met één klik aan te passen.
