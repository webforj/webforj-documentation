---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: 70f471320621b40dc1bb4170e4cbf752
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

De `DateTimeField` component is ontworpen om gebruikers in staat te stellen zowel een datum als een tijd in te voeren. Dit omvat het specificeren van het jaar, de maand en de dag, samen met de tijd in uren en minuten. Het biedt gebruikers de mogelijkheid om hun invoer op nauwkeurigheid te valideren of een speciale datum-tijdkiezerinterface te gebruiken om het selectieproces te stroomlijnen.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Usages {#usages}

De `DateTimeField` is het beste in situaties waar het vastleggen of weergeven van zowel datum **als** tijd essentieel is voor uw app. Hier zijn enkele voorbeelden van wanneer u de `DateTimeField` moet gebruiken:

1. **Evenementplanning en kalenders**: Laat gebruikers evenementen efficiënt plannen, afspraken boeken en hun kalenders beheren door hen een enkele component te geven waarmee ze de datum en tijd kunnen kiezen.
<!-- vale off -->
2. **Inchecken en uitchecken**: Faciliteer de selectie van incheck- en uitchecktijden wanneer de periode meerdere dagen kan beslaan.
<!-- vale on -->
3. **Gegevensregistratie en tijdstempels**: Gebruik `DateTimeFields` voor apps die betrokken zijn bij het registreren van de datum en tijd waarop evenementen plaatsvinden of wanneer een gebruiker gegevens indient.

4. **Taakbeheer en deadlines**: `DateTimeFields` zijn waardevol in toepassingen die taakbeheer of het instellen van deadlines omvatten waarbij zowel de datum als de tijd relevant zijn voor een nauwkeurige planning.

## Field value (`LocalDateTime`) {#field-value-localdatetime}

Intern vertegenwoordigt de `DateTimeField` component zijn waarde met behulp van een `LocalDateTime` object uit het `java.time` pakket. Dit biedt nauwkeurige controle over zowel de datum- als tijdcomponenten van de invoer.

Terwijl de **client-side** waarde wordt gerenderd op basis van de locale van de browser van de gebruiker (bijvoorbeeld datum- en tijdformaten die overeenkomen met lokale conventies), volgt de **geparste** waarde een strikte en voorspelbare structuur: **`yyyy-MM-ddTHH:mm:ss`**.

### Het verkrijgen en instellen van de waarde {#getting-and-setting-the-value}

Om de huidige waarde te verkrijgen, gebruik de `getValue()` methode:

```java
LocalDateTime value = dateTimeField.getValue();
```

Om de waarde programmaatisch in te stellen, gebruik de `setValue()` methode:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Gebruik van `setText()` {#using-settext}

Als u de waarde via een ruwe string wilt instellen, moet deze het exacte formaat van `yyyy-MM-ddTHH:mm:ss` volgen.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // geldig

dateTimeField.setText("24-04-27T14:30:00"); // ongeldig
```

:::warning
 Wanneer de `setText()` methode wordt gebruikt, zal er een `IllegalArgumentException` worden opgegooid als de component de invoer niet kan parseren in het `yyyy-MM-ddTHH:mm:ss` formaat.
:::

## Statische utilities {#static-utilities}

De DateTimeField klasse biedt ook de volgende statische hulpprogramma's:

- `fromDateTime(String dateTimeAsString)`: Convert een datum- en tijdstring in `yyyy-MM-ddTHH:mm:ss` formaat naar een LocalDateTime object dat vervolgens met deze klasse of elders kan worden gebruikt.

- `toDateTime(LocalDateTime dateTime)`: Convert een LocalDateTime object naar een datum- en tijdstring in `yyyy-MM-ddTHH:mm:ss` formaat.

- `isValidDateTime(String dateTimeAsString)`: Controleert of de gegeven string een geldige `yyyy-MM-ddTHH:mm:ss` datum en tijd is. Dit zal een booleaanse waarde true retourneren als dat zo is, false anders.

## Min- en maxwaarde {#min-and-max-value}

### De min waarde {#the-min-value}

Als de waarde die in de component is ingevoerd eerder is dan de opgegeven minimumtijdstempel, zal de component falen bij de validatie van de voorwaarden. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de minwaarde een tijdstempel zijn die hetzelfde of eerder is dan de maxwaarde.

```java
// Stel de minimaal toegestane tijdstempel in: 1 januari 2023 om 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### De max waarde {#the-max-value}

Als de waarde die in de component is ingevoerd later is dan de opgegeven maximale tijdstempel, zal de component falen bij de validatie van de voorwaarden. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de maxwaarde een tijdstempel zijn die hetzelfde of later is dan de minwaarde.

```java
// Stel de maximaal toegestane tijdstempel in: 31 december 2023 om 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `DateTimeField` component, overweeg de volgende beste praktijken:

- **Gepersonaliseerde datumweergave**: Het lokaliseren van het datumformaat en het opnemen van regionale voorkeuren zorgt ervoor dat data in een vertrouwd formaat aan de gebruiker worden gepresenteerd.

- **Inclusief tijdzones**: Als uw app tijdgevoelige informatie over verschillende tijdzones behandelt, overweeg dan om de tijdzone-selectie naast het datumveld op te nemen om ervoor te zorgen dat de datum-tijd representatie accuraat is.

- **Toegankelijkheid**: Gebruik de `DateTimeField` met toegankelijkheid in gedachten. Zorg ervoor dat het voldoet aan toegankelijkheidsnormen, zoals het bieden van juiste labels en compatibel zijn met ondersteunende technologieën.

- **Auto-populeer huidige datum**: Overweeg om een optie te bieden om de huidige datum en tijd als standaardwaarde in te vullen in het datumtijdveld, als dat geschikt is voor het gebruiksdoel van uw app.
