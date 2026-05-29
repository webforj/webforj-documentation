---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: a996ccdd786de35de1dece0a5fc8f27a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

De `DateField` component stelt gebruikers in staat om een datum in te voeren of te selecteren op jaar, maand en dag. Het behandelt validatie automatisch, zodat verkeerd geformatteerde data worden opgemerkt voordat het formulier wordt ingediend.

<!-- INTRO_END -->

## Gebruik van `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` breidt de gedeelde `Field` klasse uit, die gemeenschappelijke functies biedt voor alle veldencomponenten. Het volgende voorbeeld creëert vertrek- en retour DateFields die in sync blijven, met min- en max-beperkingen om het selecteerbare bereik te limiteren.

<ComponentDemo
path='/webforj/datefield'
files={['src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java']}
/>

## Veldwaarde (`LocalDate`) {#field-value-localdate}

De `DateField` component slaat zijn waarde intern op als een `LocalDate` object, dat een datum zonder tijd of tijdzone-informatie vertegenwoordigt. Dit stelt nauwkeurige afhandeling van kalendergebaseerde invoer over verschillende systemen mogelijk.

:::info Weergavewaarde VS geparsed waarde 
Terwijl de **weergavewaarde** zich aanpast aan de browserlocatie van de gebruiker, zodat deze regionaal vertrouwd geformatteerd is (bijv. `MM/DD/YYYY` in de Verenigde Staten of `DD.MM.YYYY` in Europa), vertrouwt de **geparsed waarde** altijd op het vaste formaat van `yyyy-MM-dd`.
:::

### Het verkrijgen en instellen van de `LocalDate` waarde {#getting-and-setting-the-localdate-value}

Om de huidige waarde op te halen, gebruik de `getValue()` methode:

```java
LocalDate value = dateField.getValue();
```

Om de waarde programatisch in te stellen, gebruik de `setValue()` methode:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Het gebruik van `setText()` {#using-settext}

Je kunt ook een waarde toewijzen met behulp van een ruwe string, maar deze moet het exacte `yyyy-MM-dd` formaat volgen:

```java
dateField.setText("2024-04-27"); // geldig

dateField.setText("04/27/2024"); // ongeldig
```

:::warning
Wanneer je de `setText()` methode gebruikt, wordt een `IllegalArgumentException` opgegooid als de component de invoer niet kan parseren in het `yyyy-MM-dd` formaat.
:::

## Toepassingen {#usages}

De `DateField` is ideaal voor het kiezen en weergeven van data in je app. Hier zijn enkele voorbeelden wanneer de `DateField` te gebruiken:

1. **Evenementplanning en agenda's**: Datumvelden zijn essentieel in apps die het plannen van evenementen, het boeken van afspraken of het bijhouden van belangrijke data omvatten.

2. **Formuliervelden**: Vereenvoudig het proces van datumselectie voor een gebruiker die een formulier invult dat een datum vereist, zoals een verjaardag.

3. **Boekings- en reserveringssystemen**: Apps die boekings- en reserveringssystemen omvatten, vereisen vaak dat gebruikers specifieke data invoeren. Een datumveld stroomlijnt het proces en zorgt voor nauwkeurige datumselectie.

4. **Taakbeheer en deadlines**: Datumvelden zijn waardevol in apps die taakbeheer of het stellen van deadlines omvatten. Gebruikers kunnen gemakkelijk vervaldatums, startdata of andere tijdgebonden informatie specificeren.

## Min- en maxwaarde {#min-and-max-value}

### De min waarde {#the-min-value}
De `setMin()` methode definieert de vroegste datum die een gebruiker in de component kan invoeren. Als de invoer eerder is dan de opgegeven minimumdatum, zal de validatie voor beperkingen falen. Wanneer gebruikt met `setMax()`, moet de minimumdatum gelijk aan of eerder zijn dan de maximumdatum.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimum toegestaan: 1 januari 2023
```

### De max waarde {#the-max-value}
De `setMax()` methode definieert de laatste datum die de component accepteert. Als de ingevoerde datum later is dan de opgegeven maximumdatum, is de invoer ongeldig. Wanneer beide waarden zijn gedefinieerd, moet de maximumdatum gelijk aan of later zijn dan de minimumdatum.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximum toegestaan: 31 december 2023
```

## Statische hulpprogramma's {#static-utilities}

De `DateField` klasse biedt ook de volgende statische hulpprogramma-methoden:

- `fromDate(String dateAsString)`: Converteer een datumstring in `yyyy-MM-dd` formaat naar een `LocalDate` object dat vervolgens kan worden gebruikt met dit veld of elders.

- `toDate(LocalDate date)`: Converteer een `LocalDate` object naar een datumstring in `yyyy-MM-dd` formaat.

- `isValidDate(String dateAsString)`: Controleer of de opgegeven string een geldige `yyyy-MM-dd` datum is.

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `DateField` component, overweeg de volgende beste praktijken:

- **Toegankelijkheid**: Gebruik geschikte labels om ervoor te zorgen dat gebruikers met hulpmiddelen voor toegankelijkheid gemakkelijk kunnen navigeren en de datumvelden in je app kunnen gebruiken.

- **Auto-populeer de huidige datum**: Vul desgewenst het datumveld automatisch in met de huidige datum.
