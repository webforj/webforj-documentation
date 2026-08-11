---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: 173c4a1d080dc6e0c01828131af61c08
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

De `DateField` component stelt gebruikers in staat om een datum in te voeren of te selecteren op jaar, maand en dag. Het verwerkt automatisch de validatie, zodat verkeerd geformatteerde data worden opgemerkt voordat het formulier wordt verzonden.

<!-- INTRO_END -->

## Gebruik van `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` breidt de gedeelde `Field` klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld creëert vertrek- en terugkeerdatumvelden die synchroon blijven, met minimum- en maximumbeperkingen om het selecteerbare bereik te beperken.

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## Veldwaarde (`LocalDate`) {#field-value-localdate}

De `DateField` component slaat zijn waarde intern op als een `LocalDate` object, dat een datum zonder tijd- of tijdzone-informatie vertegenwoordigt. Dit maakt een nauwkeurige verwerking van kalendergebaseerde invoer mogelijk op verschillende systemen.

:::info Weergave waarde VS geparsed waarde
Hoewel de **weergave waarde** zich aanpast aan de browser-locale van de gebruiker, zodat deze regionaal vertrouwde opmaak heeft (bijv. `MM/DD/YYYY` in de Verenigde Staten of `DD.MM.YYYY` in Europa), vertrouwt de **geparsed waarde** altijd op het vaste formaat van `yyyy-MM-dd`.
:::

### Het ophalen en instellen van de `LocalDate` waarde {#getting-and-setting-the-localdate-value}

Om de huidige waarde op te halen, gebruik de `getValue()` methode:

```java
LocalDate value = dateField.getValue();
```

Om de waarde programmatisch in te stellen, gebruik de `setValue()` methode:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Gebruik van `setText()` {#using-settext}

Je kunt ook een waarde toewijzen met een ruwe string, maar deze moet het exacte `yyyy-MM-dd` formaat volgen:

```java
dateField.setText("2024-04-27"); // geldig

dateField.setText("04/27/2024"); // ongeldig
```

:::warning
 Wanneer de `setText()` methode wordt gebruikt, wordt een `IllegalArgumentException` gegenereerd als de component de invoer niet kan parseren in het `yyyy-MM-dd` formaat.
:::

## Toepassingen {#usages}

De `DateField` is ideaal voor het kiezen en weergeven van data in jouw app. Hier zijn enkele voorbeelden van wanneer je de `DateField` moet gebruiken:

1. **Evenementplanning en kalenders**: Datumvelden zijn essentieel in apps die betrokken zijn bij het plannen van evenementen, reserveren van afspraken of het bijhouden van belangrijke data.

2. **Formulieren**: Versimpel het selectieproces van een datum voor een gebruiker die een formulier invult dat een datum vereist, zoals een verjaardag.

3. **Boek- en reserveringssystemen**: Apps die betrokken zijn bij boek- en reserveringssystemen vereisen vaak dat gebruikers specifieke data invoeren. Een datumveld stroomlijnt het proces en zorgt voor een nauwkeurige datumselectie.

4. **Taakbeheer en deadlines**: Datumvelden zijn waardevol in apps die betrokken zijn bij taakbeheer of het instellen van deadlines. Gebruikers kunnen eenvoudig vervaldata, startdata of andere tijdgevoelige informatie specificeren.

## Min- en maxwaarde {#min-and-max-value}

### De min waarde {#the-min-value}
De `setMin()` methode definieert de vroegste datum die een gebruiker in het component kan invoeren. Als de invoer eerder is dan de gespecificeerde minimum, zal de validatie van de beperking falen. Wanneer gebruikt samen met `setMax()`, moet de minimumdatum dezelfde of eerder zijn dan de maximumdatum.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimaal toegestaan: 1 januari 2023
```

### De max waarde {#the-max-value}
De `setMax()` methode definieert de laatst aanvaarde datum door het component. Als de ingevoerde datum later is dan de gespecificeerde maximum, is de invoer ongeldig. Wanneer beide waarden zijn gedefinieerd, moet de maximumdatum dezelfde of later zijn dan de minimum.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximaal toegestaan: 31 december 2023
```

## Statische hulpmiddelen {#static-utilities}

De `DateField` klasse biedt ook de volgende statische hulpfuncties:

- `fromDate(String dateAsString)`: Zet een datumsstring in `yyyy-MM-dd` formaat om naar een `LocalDate` object dat vervolgens kan worden gebruikt met dit veld, of elders.

- `toDate(LocalDate date)`: Zet een `LocalDate` object om naar een datumsstring in `yyyy-MM-dd` formaat.

- `isValidDate(String dateAsString)`: Controleert of de gegeven string een geldige `yyyy-MM-dd` datum is.

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `DateField` component, overweeg de volgende best practices:

- **Toegankelijkheid**: Maak gebruik van geschikte labels om ervoor te zorgen dat gebruikers met ondersteunende technologieën eenvoudig naar de datumvelden in jouw app kunnen navigeren en deze kunnen gebruiken.

- **Auto-populeer de huidige datum**: Vul het datumveld automatisch in met de huidige datum, indien passend voor het gebruik van jouw app.
