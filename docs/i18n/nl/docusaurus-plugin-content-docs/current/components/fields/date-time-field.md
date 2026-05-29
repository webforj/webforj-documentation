---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 1214ec1391242fb6b3ff7f60664a6f79
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

De `DateTimeField` component stelt gebruikers in staat om zowel een datum als een tijd in één veld in te voeren, inclusief jaar, maand, dag, uren en minuten. Het valideert de invoer op nauwkeurigheid en kan een datum-tijd kiezer presenteren om de selectie te vergemakkelijken.

<!-- INTRO_END -->

## Gebruik van `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` breidt de gedeelde `Field` klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld maakt een gelabelde `DateTimeField` aan voor het selecteren van een vertrekdatum en tijd.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Toepassingen {#usages}

De `DateTimeField` is het beste te gebruiken in scenario's waarin het vastleggen of weergeven van zowel datum **als** tijd essentieel is voor uw app. Hier zijn enkele voorbeelden van wanneer u de `DateTimeField` moet gebruiken:

1. **Evenementplanning en agenda's**: Laat gebruikers efficiënt evenementen plannen, afspraken boeken en hun agenda's beheren door hen een enkel component te geven waarmee ze de datum en tijd kunnen kiezen.
<!-- vale off -->
2. **Inchecken en uitchecken**: Faciliteer de selectie van incheck- en uitchecktijden wanneer de periode meerdere dagen kan beslaan.
<!-- vale on -->
3. **Gegevensregistratie en tijdstempels**: Gebruik `DateTimeFields` voor apps die de datum en tijd van gebeurtenissen of wanneer een gebruiker gegevens indient moeten registreren.

4. **Taakbeheer en deadlines**: `DateTimeFields` zijn waardevol in toepassingen die betrekking hebben op taakbeheer of het instellen van deadlines waarbij zowel de datum als de tijd relevant zijn voor nauwkeurige planning.

## Veldwaarde (`LocalDateTime`) {#field-value-localdatetime}

Intern vertegenwoordigt de `DateTimeField` component zijn waarde met een `LocalDateTime` object uit het `java.time` pakket. Dit biedt precieze controle over zowel de datum- als de tijdcomponenten van de invoer.

Terwijl de **client-side** waarde wordt weergegeven op basis van de browserlocale van de gebruiker (bijvoorbeeld, datum- en tijdformaten die passen bij lokale conventies), volgt de **geparste** waarde een strikte en voorspelbare structuur: **`yyyy-MM-ddTHH:mm:ss`**.

### Waarde ophalen en instellen {#getting-and-setting-the-value}

Om de huidige waarde op te halen, gebruikt u de `getValue()` methode:

```java
LocalDateTime value = dateTimeField.getValue();
```

Om de waarde programmatisch in te stellen, gebruikt u de `setValue()` methode:

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
 Wanneer u de `setText()` methode gebruikt, wordt er een `IllegalArgumentException` gegooid als de component de invoer in het `yyyy-MM-ddTHH:mm:ss` formaat niet kan parseren.
:::

## Statistische hulpprogramma's {#static-utilities}

De DateTimeField klasse biedt ook de volgende statische hulpprogramma's:

- `fromDateTime(String dateTimeAsString)`: Zet een datum- en tijdstring in `yyyy-MM-ddTHH:mm:ss` formaat om naar een LocalDateTime object dat vervolgens met deze klasse of ergens anders kan worden gebruikt.

- `toDateTime(LocalDateTime dateTime)`: Zet een LocalDateTime object om naar een datum- en tijdstring in `yyyy-MM-ddTHH:mm:ss` formaat.

- `isValidDateTime(String dateTimeAsString)`: Controleert of de opgegeven string een geldige `yyyy-MM-ddTHH:mm:ss` datum en tijd is. Dit retourneert een boolean waarde true als dat het geval is, false anders.

## Min- en maxwaarde {#min-and-max-value}

### De minwaarde {#the-min-value}

Als de waarde die in de component is ingevoerd eerder is dan de opgegeven minimale timestamp, zal de component de constraint validatie niet doorstaan. Wanneer zowel de min- als de maxwaarden zijn ingesteld, moet de minwaarde een timestamp zijn die gelijk is aan of eerder dan de maxwaarde.

```java
// Stel de minimum toegestane timestamp in: 1 januari 2023 om 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### De maxwaarde {#the-max-value}

Als de waarde die in de component is ingevoerd later is dan de opgegeven maximale timestamp, zal de component de constraint validatie niet doorstaan. Wanneer zowel de min- als de maxwaarden zijn ingesteld, moet de maxwaarde een timestamp zijn die gelijk is aan of later dan de minwaarde.

```java
// Stel de maximum toegestane timestamp in: 31 december 2023 om 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `DateTimeField` component, overweeg de volgende beste praktijken:

- **Glocalized date display**: Lokaliseren van het datumformaat en het opnemen van regionale voorkeuren zorgt ervoor dat datums in een bekend formaat aan de gebruiker worden gepresenteerd.

- **Inclusie van tijdzones**: Als uw app te maken heeft met tijdgevoelige informatie over verschillende tijdzones, overweeg dan om tijdzone-selectie naast het datumveld op te nemen om een nauwkeurige datum-tijdweergave te waarborgen.

- **Toegankelijkheid**: Gebruik de `DateTimeField` met toegankelijkheid in gedachten. Zorg ervoor dat het voldoet aan de toegankelijkheidsnormen, zoals het bieden van juiste labels en compatibiliteit met ondersteunende technologieën.

- **Automatisch invullen van de huidige datum**: Overweeg de optie om de huidige datum en tijd automatisch in te vullen als standaardwaarde in het datumtijdveld, indien passend voor het gebruiksdoel van uw app.
