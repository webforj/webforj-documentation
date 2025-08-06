---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: ee9981c57d9964a3f759b116dbd75af2
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

De `DateField` is een veldcomponent die gebruikers in staat stelt om datums in te voeren of te selecteren op jaar, maand en dag. Het biedt een intuïtieve en efficiënte manier om dategerelateerde informatie in verschillende apps te verwerken en biedt de flexibiliteit om de invoer van de gebruiker te valideren.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Veldwaarde (`LocalDate`) {#field-value-localdate}

De `DateField` component slaat zijn waarde intern op als een `LocalDate` object, dat een datum zonder tijd of tijdzone-informatie vertegenwoordigt. Dit zorgt voor een nauwkeurige verwerking van kalendergerelateerde invoer over verschillende systemen.

:::info Weergegeven waarde VS geparsed waarde 
Terwijl de **weergegeven waarde** zich aanpast aan de browserlocatie van de gebruiker en zorgt voor regionaal vertrouwde opmaak (bijv. `MM/DD/YYYY` in de Verenigde Staten of `DD.MM.YYYY` in Europa), is de **geparsed waarde** altijd afhankelijk van het vaste formaat van `yyyy-MM-dd`.
:::

### Halen en instellen van de `LocalDate` waarde {#getting-and-setting-the-localdate-value}

Om de huidige waarde op te halen, gebruikt u de `getValue()` methode:

```java
LocalDate value = dateField.getValue();
```

Om de waarde programmatisch in te stellen, gebruikt u de `setValue()` methode:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Gebruik van `setText()` {#using-settext}

U kunt ook een waarde toewijzen met een ruwe string, maar deze moet het exacte `yyyy-MM-dd` formaat volgen:

```java
dateField.setText("2024-04-27"); // geldig

dateField.setText("04/27/2024"); // ongeldig
```

:::warning
 Wanneer u de `setText()` methode gebruikt, wordt er een `IllegalArgumentException` opgeworpen als de component de invoer niet kan parseren in het `yyyy-MM-dd` formaat.
:::

## Gebruiksmogelijkheden {#usages}

De `DateField` is ideaal voor het kiezen en weergeven van datums in uw app. Hier zijn enkele voorbeelden van wanneer u de `DateField` moet gebruiken:

1. **Evenementplanning en Agenda's**: Datumvelden zijn essentieel in apps die betrokken zijn bij het plannen van evenementen, het boeken van afspraken of het bijhouden van belangrijke data.

2. **Formuliervelden**: Vereenvoudig het proces van datumnuance voor een gebruiker die een formulier invult dat een datum vereist, zoals een verjaardag.

3. **Boekings- en Reserveringssystemen**: Apps die betrokken zijn bij boekingen en reserveringssystemen vereisen vaak dat gebruikers specifieke datums invoeren. Een datumveld stroomlijnt het proces en zorgt voor een nauwkeurige datumselectie.

4. **Taakbeheer en Deadlines**: Datumvelden zijn waardevol in apps die betrokken zijn bij taakbeheer of het instellen van deadlines. Gebruikers kunnen eenvoudig vervaldatums, startdatums of andere tijdgebonden informatie specificeren.

## Min en max waarde {#min-and-max-value}

### De min waarde {#the-min-value}
De `setMin()` methode definieert de vroegste datum die een gebruiker in de component kan invoeren. Als de invoer vroeger is dan de gespecificeerde minimumdatum, mislukt de validatie. Wanneer deze samen met `setMax()` wordt gebruikt, moet de minimumdatum gelijk aan of eerder zijn dan de maximumdatum.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimum toegestaan: 1 januari 2023
```

### De max waarde {#the-max-value}
De `setMax()` methode definieert de laatst acceptabele datum voor de component. Als de ingevoerde datum later is dan de gespecificeerde maximumdatum, is de invoer ongeldig. Wanneer beide waarden zijn gedefinieerd, moet de maximumdatum gelijk aan of later zijn dan de minimumdatum.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximum toegestaan: 31 december 2023
```

## Statische hulpprogramma's {#static-utilities}

De `DateField` klasse biedt ook de volgende statische hulpprogramma-methoden:

- `fromDate(String dateAsString)`: Zet een datumnotatie in `yyyy-MM-dd` formaat om naar een `LocalDate` object dat vervolgens met dit veld of elders kan worden gebruikt.

- `toDate(LocalDate date)`: Zet een `LocalDate` object om naar een datumnotatie in `yyyy-MM-dd` formaat.

- `isValidDate(String dateAsString)`: Controleert of de gegeven string een geldige `yyyy-MM-dd` datum is.

## Best practices {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de `DateField` component, houdt u rekening met de volgende best practices:

- **Toegankelijkheid**: Gebruik geschikte labels om ervoor te zorgen dat gebruikers met ondersteunende technologieën gemakkelijk kunnen navigeren naar en gebruikmaken van de datumvelden in uw app.

- **Automatisch invullen van de huidige datum**: Vul indien passend voor het gebruiksdoel van uw app het datumveld automatisch in met de huidige datum.
