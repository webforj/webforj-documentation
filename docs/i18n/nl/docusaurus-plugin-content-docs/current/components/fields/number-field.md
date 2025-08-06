---
sidebar_position: 25
title: NumberField
slug: numberfield
description: >-
  A component that provides a default browser-based input field for entering
  numeric values, with built-in controls for incrementing or decrementing the
  value.
_i18n_hash: 0d5052fd2f20b391e0eaadbf7c771e5e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

Je kunt de `NumberField` component gebruiken om numerieke invoer van een gebruiker te accepteren. Het zorgt ervoor dat alleen geldige numerieke waarden worden ingevoerd en biedt een gebruiksvriendelijke interface voor het invoeren van getallen.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Veldwaarde {#field-value}

De `NumberField` component slaat zijn waarde op als een `Double`, wat nauwkeurige verwerking van zowel gehele als decimale getallen mogelijk maakt.

### De huidige waarde ophalen {#getting-the-current-value}

Je kunt de numerieke waarde die door de gebruiker is ingevoerd ophalen met:

```java
Double currentValue = numberField.getValue();
```

### Een nieuwe waarde instellen {#setting-a-new-value}

Om het veld programmatically in te stellen:

```java
numberField.setValue(42.5);
```

Als er geen waarde is ingevoerd en er geen standaardwaarde is ingesteld, retourneert `getValue()` `null`.

:::tip
Hoewel het veld is ontworpen om alleen geldige numerieke invoer te accepteren, moet je er rekening mee houden dat de onderliggende waarde nullable is. Test altijd op null voordat je het resultaat gebruikt.
:::

## Toepassingen {#usages}

De `NumberField` is het beste te gebruiken in scenario's waar het vastleggen, weergeven of manipuleren van numerieke gegevens essentieel is voor je app. Hier zijn enkele voorbeelden van wanneer je de `NumberField` kunt gebruiken:

1. **Numerieke Invoervormen**: Bij het ontwerpen van formulieren die numerieke invoer vereisen, vereenvoudigt het gebruik van een `NumberField` het invoerproces voor gebruikers. Dit is bijzonder nuttig voor applicaties die gebruikersgegevens verzamelen of numerieke waarden vereisen.

2. **Data-analyse en Berekeningen**: Een `NumberField` is bijzonder waardevol in apps die data-analyse, berekeningen of wiskundige bewerkingen omvatten. Het stelt gebruikers in staat om numerieke waarden nauwkeurig in te voeren of te manipuleren.

3. **Financiële en Begrotingstoepassingen**: Apps die financiële berekeningen, begroting of het bijhouden van uitgaven omvatten, vereisen vaak nauwkeurige numerieke invoer. Een `NumberField` zorgt voor een nauwkeurige invoer van financiële cijfers.

4. **Metingen en Eenheid Conversie**: In apps die te maken hebben met metingen of eenheid conversies, is de `NumberField` ideaal voor het invoeren van numerieke waarden met eenheden zoals lengte, gewicht of volume.

## Min- en maxwaarde {#min-and-max-value}

Met de `setMin()` methode kun je de minimale aanvaardbare waarde in het getalveld specificeren. Als een gebruiker een waarde invoert die lager is dan deze drempel, zal de component de constraint-validatie falen en geschikte feedback geven.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Minimum toegestaan: 0.0
```

Daarnaast stelt de `setMax()` methode je in staat de maximale aanvaardbare waarde te definiëren. Als een gebruiker een waarde invoert die hoger is dan deze limiet, wordt de invoer afgewezen. Wanneer zowel minimale als maximale waarden zijn ingesteld, moet de maximale waarde groter zijn dan of gelijk aan de minimale waarde.

```java
numberField.setMax(100.0); // Maximum toegestaan: 100.0
```

In deze configuratie zou het invoeren van een waarde zoals -5 of 150 ongeldig zijn, terwijl waarden tussen 0 en 100 worden geaccepteerd.

## Granulariteit {#granularity}

Je kunt de `setStep()` methode gebruiken om de granulariteit op te geven waaraan de waarde moet voldoen bij het gebruik van de pijltjestoetsen om de waarde te wijzigen. Dit zal de waarde van de component elke keer met een bepaalde stap verhogen of verlagen. Dit is niet van toepassing wanneer een gebruiker een waarde rechtstreeks invoert, maar alleen wanneer interactie wordt met de `NumberField` met de pijltjestoetsen.

## Placeholder tekst {#placeholder-text}

Je kunt placeholder tekst instellen voor de `NumberField` met behulp van de `setPlaceholder()` methode. De placeholder tekst wordt weergegeven wanneer het veld leeg is, wat helpt om de gebruiker te stimuleren om de juiste invoer in de `NumberField` in te voeren.

:::tip Geef duidelijke context voor nauwkeurigheid
Als de numerieke invoer betrekking heeft op een specifieke eenheid van meting of een bepaalde context heeft, geef dan duidelijke etikettering of aanvullende informatie om gebruikers te begeleiden en nauwkeurige invoer te waarborgen.
:::

## Beste praktijken {#best-practices}

Om een naadloze integratie en optimale gebruikerservaring te waarborgen, overweeg de volgende beste praktijken bij het gebruik van de `NumberField`:

- **Toegankelijkheid**: Gebruik de `NumberField` component met toegankelijkheid in gedachten, met inachtneming van toegankelijkheidsnormen zoals juiste etikettering, ondersteuning voor navigatie met het toetsenbord en compatibiliteit met hulptechnologieën. Zorg ervoor dat gebruikers met een beperking effectief met de `NumberField` kunnen interageren.

- **Gebruik Incrementeer-/Decrementeer-knoppen**: Indien geschikt voor je app, overweeg dan het gebruik van incrementeer- en decrementeerknoppen met de `NumberField`. Dit stelt gebruikers in staat om de numerieke waarde met een specifieke stap of decrement met een enkele klik aan te passen.
