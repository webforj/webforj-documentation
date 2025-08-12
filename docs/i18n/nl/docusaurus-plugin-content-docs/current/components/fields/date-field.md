---
sidebar_position: 10
title: DateField
slug: datefield
description: >-
  A component that provides a default browser-based date picker for selecting a
  date through an input field.
sidebar_class_name: updated-content
_i18n_hash: 9f7f8e2c82305667ea1ace187df17915
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateField" top='true'/>

<ParentLink parent="Field" />

De `DateField` is een veldcomponent waarmee gebruikers data kunnen invoeren of selecteren op basis van jaar, maand en dag. Het biedt een intuïtieve en efficiënte manier om met datagerelateerde informatie in verschillende apps om te gaan en biedt de flexibiliteit om de invoer van de gebruiker te valideren.

<ComponentDemo 
path='/webforj/datefield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datefield/DateFieldView.java'
/>

## Veldwaarde (`LocalDate`) {#field-value-localdate}

De `DateField`-component slaat zijn waarde intern op als een `LocalDate`-object, dat een datum zonder tijd- of tijdzone-informatie vertegenwoordigt. Dit maakt een nauwkeurige verwerking van op de kalender gebaseerde invoer over verschillende systemen mogelijk.

:::info Weergegeven waarde VS geparste waarde 
Terwijl de **weergegeven waarde** zich aanpast aan de browserlocatie van de gebruiker, waardoor regionaal bekende opmaak wordt gegarandeerd (bijv. `MM/DD/YYYY` in de Verenigde Staten of `DD.MM.YYYY` in Europa), vertrouwt de **geparste waarde** altijd op het vaste formaat van `yyyy-MM-dd`.
:::

### Het `LocalDate`-waarde verkrijgen en instellen {#getting-and-setting-the-localdate-value}

Om de huidige waarde op te halen, gebruik je de methode `getValue()`:

```java
LocalDate value = dateField.getValue();
```

Om de waarde programmatically in te stellen, gebruik je de methode `setValue()`:

```java
dateField.setValue(LocalDate.of(2024, 4, 27));
```

### Gebruik van `setText()` {#using-settext}

Je kunt ook een waarde toewijzen via een ruwe tekst, maar deze moet het exacte `yyyy-MM-dd`-formaat volgen:

```java
dateField.setText("2024-04-27"); // geldig

dateField.setText("04/27/2024"); // ongeldig
```

:::warning
 Wanneer de `setText()`-methode wordt gebruikt, zal een `IllegalArgumentException` worden opgegooid als de component de invoer niet kan parseren in het `yyyy-MM-dd`-formaat.
:::

## Toepassingen {#usages}

De `DateField` is ideaal voor het kiezen en weergeven van data in je app. Hier zijn enkele voorbeelden van wanneer je de `DateField` zou moeten gebruiken:

1. **Evenementplanning en Kalenders**: Datumvelden zijn essentieel in apps die het plannen van evenementen, het boeken van afspraken of het bijhouden van belangrijke data omvatten.

2. **Formuliervelden**: Vereenvoudig het proces van datuminvoer voor een gebruiker die een formulier invult dat een datum vereist, zoals een verjaardag.

3. **Boeking- en Reserveringssystemen**: Apps die inhouden dat gebruikers specifieke data moeten invoeren bij boekingen en reserveringen, profiteren vaak van een datumveld. Dit stroomlijnt het proces en zorgt voor een nauwkeurige datuminvoer.

4. **Taakbeheer en Deadlines**: Datumvelden zijn waardevol in apps die taakinformatie of deadlines omvatten. Gebruikers kunnen eenvoudig vervaldatums, startdatums of andere tijdgevoelige informatie opgeven.

## Min- en maxwaarde {#min-and-max-value}

### De minwaarde {#the-min-value}
De methode `setMin()` definieert de vroegste datum die een gebruiker in de component kan invoeren. Als de invoer eerder is dan de opgegeven minimumwaarde, zal de validatie van de beperking falen. Wanneer deze wordt gebruikt samen met `setMax()`, moet de minimumwaarde een datum zijn die hetzelfde of eerder is dan de maximumwaarde.

```java
dateField.setMin(LocalDate.of(2023, 1, 1)); // Minimale toegestane waarde: 1 januari 2023
```

### De maxwaarde {#the-max-value}
De methode `setMax()` definieert de laatste datum die de component accepteert. Als de ingevoerde datum later is dan de opgegeven maximumwaarde, is de invoer ongeldig. Wanneer beide waarden zijn gedefinieerd, moet de maximumwaarde een datum zijn die hetzelfde of later is dan de minimumwaarde.

```java
dateField.setMax(LocalDate.of(2023, 12, 31)); // Maximale toegestane waarde: 31 december 2023
```

## Statische hulpprogramma's {#static-utilities}

De `DateField`-klasse biedt ook de volgende statische hulpprogramma's:

- `fromDate(String dateAsString)`: Converteert een datumstring in `yyyy-MM-dd`-formaat naar een `LocalDate`-object dat vervolgens kan worden gebruikt met dit veld, of elders.

- `toDate(LocalDate date)`: Converteert een `LocalDate`-object naar een datumstring in `yyyy-MM-dd`-formaat.

- `isValidDate(String dateAsString)`: Controleert of de gegeven string een geldige `yyyy-MM-dd`-datum is.

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruiken van de `DateField`-component, overweeg je de volgende beste praktijken:

- **Toegankelijkheid**: Gebruik geschikte labels om ervoor te zorgen dat gebruikers met ondersteunende technologieën gemakkelijk kunnen navigeren naar en gebruik maken van de datumvelden in je app.

- **Auto-vul Huidige Datum**: Vul, indien passend voor de use case van je app, het datumveld automatisch met de huidige datum.
