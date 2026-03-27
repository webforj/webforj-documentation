---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
_i18n_hash: bf6829e0fafbd0c69a49a5563e8a298b
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

De `DateField` component stelt gebruikers in staat om een datum in te voeren of te selecteren per jaar, maand en dag. Het behandelt validatie automatisch, zodat verkeerd geformatteerde datums worden opgemerkt voordat het formulier wordt ingediend.

<!-- INTRO_END -->

## Gebruik van `DateField` {#using-datefield}

<ParentLink parent="Field" />

`DateField` breidt de gedeelde `Field` klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld creëert vertrek- en retour-DateFields die gesynchroniseerd blijven, met minimum- en maximumbeperkingen om het selecteerbare bereik te beperken.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Veldwaarde (`LocalDate`) {#field-value-localdate}

De `DateField` component slaat zijn waarde intern op als een `LocalDate` object, dat een datum zonder tijd- of tijdzone-informatie vertegenwoordigt. Dit maakt nauwkeurige verwerking van kalendervoorzieningen mogelijk in verschillende systemen.

:::info Weergegeven waarde VS geparsed waarde 
Hoewel de **weergegeven waarde** zich aanpast aan de browser-locale van de gebruiker, waardoor regionaal vertrouwde opmaak wordt gegarandeerd (bijv. `MM/DD/YYYY` in de Verenigde Staten of `DD.MM.YYYY` in Europa), is de **geparsed waarde** altijd afhankelijk van het vaste formaat van `yyyy-MM-dd`.
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
 Bij het gebruik van de `setText()` methode wordt een `IllegalArgumentException` opgeworpen als de component de invoer niet kan parseren in het `yyyy-MM-dd` formaat.
:::

## Toepassingen {#usages}

De `DateField` is ideaal voor het kiezen en weergeven van datums in je app. Hier zijn enkele voorbeelden van wanneer je de `DateField` moet gebruiken:

1. **Evenementplanning en Agenda's**: Datumvelden zijn essentieel in apps die betrokken zijn bij het plannen van evenementen, het boeken van afspraken, of het bijhouden van belangrijke datums.

2. **Formulierinvoer**: Vereenvoudig het datumselectieproces voor een gebruiker die een formulier invult dat een datum vereist, zoals een verjaardag.

3. **Boekings- en Reserveringssystemen**: Apps die betrokken zijn bij boekings- en reserveringssystemen vereisen vaak dat gebruikers specifieke datums invoeren. Een datumveld stroomlijnt het proces en zorgt voor nauwkeurige datumselectie.

4. **Taakbeheer en Deadlines**: Datumvelden zijn waardevol in apps die betrokken zijn bij taakbeheer of het stellen van deadlines. Gebruikers kunnen eenvoudig vervaldata, startdata of andere tijdgevoelige informatie specificeren.

## Min- en maxwaarde {#min-and-max-value}

### De minwaarde {#the-min-value}
De `setMin()` methode definieert de vroegste datum die een gebruiker in de component kan invoeren. Als de invoer eerder is dan het opgegeven minimum, zal het falen bij de validatie van de beperkingen. Wanneer deze samen met `setMax()` wordt gebruikt, moet het minimum een datum zijn die hetzelfde is als of eerder dan het maximum.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimum toegestaan: 1 januari 2023
```

### De maxwaarde {#the-max-value}
De `setMax()` methode definieert de laatst toegestane datum die de component accepteert. Als de ingevoerde datum later is dan het opgegeven maximum, is de invoer ongeldig. Wanneer beide waarden zijn gedefinieerd, moet het maximum een datum zijn die hetzelfde is als of later dan het minimum.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximum toegestaan: 31 december 2023
```

## Statische hulpprogramma's {#static-utilities}

De `DateField` klasse biedt ook de volgende statische hulpprogramma's:

- `fromDate(String dateAsString)`: Converteer een datumstring in `yyyy-MM-dd` formaat naar een `LocalDate` object dat vervolgens kan worden gebruikt met dit veld, of elders.

- `toDate(LocalDate date)`: Converteer een `LocalDate` object naar een datumstring in `yyyy-MM-dd` formaat.

- `isValidDate(String dateAsString)`: Controleert of de gegeven string een geldige `yyyy-MM-dd` datum is.

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `DateField` component, overweeg de volgende beste praktijken:

- **Toegankelijkheid**: Gebruik geschikte labels om ervoor te zorgen dat gebruikers met assistieve technologieën eenvoudig kunnen navigeren naar en gebruik maken van de datumvelden in je app.

- **Automatisch vullen met de huidige datum**: Vul indien relevant voor het gebruiksdoel van je app het datumveld automatisch in met de huidige datum.
