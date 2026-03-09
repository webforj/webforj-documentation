---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: e90e93f7db172a33b2ce205bfd6a6b3c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

De `DateTimeField` component stelt gebruikers in staat om zowel een datum als een tijd in één veld in te voeren, inclusief jaar, maand, dag, uren en minuten. Het valideert de invoer op nauwkeurigheid en kan een datum-tijdkiezer presenteren om de selectie gemakkelijker te maken.

<!-- INTRO_END -->

## Gebruik van `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` breidt de gedeelde `Field` klasse uit, die algemene functies biedt voor alle veldcomponenten. In het volgende voorbeeld wordt een gelabelde `DateTimeField` gemaakt voor het selecteren van een vertrekdatum en -tijd.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Gebruik {#usages}

De `DateTimeField` is het best te gebruiken in scenario's waarbij het belangrijk is om zowel datum **als** tijd vast te leggen of weer te geven voor je app. Hier zijn enkele voorbeelden wanneer je de `DateTimeField` moet gebruiken:

1. **Eventplanning en Kalenders**: Laat gebruikers efficiënt evenementen plannen, afspraken maken en hun agenda's beheren door hen een enkele component te geven waarmee ze de datum en tijd kunnen kiezen.
<!-- vale off -->
2. **Inchecken en uitchecken**: Faciliteer de selectie van incheck- en uitchecktijden voor perioden die meerdere dagen kunnen beslaan.
<!-- vale on -->
3. **Gegevensregistratie en Timestamps**: Gebruik `DateTimeFields` voor apps die betrokken zijn bij het vastleggen van de datum en tijd waarop gebeurtenissen plaatsvinden of wanneer een gebruiker gegevens indient.

4. **Taakbeheer en Deadlines**: `DateTimeFields` zijn waardevol in applicaties die betrokken zijn bij taakbeheer of het instellen van deadlines waar zowel de datum als de tijd relevant zijn voor nauwkeurige planning.

## Veldwaarde (`LocalDateTime`) {#field-value-localdatetime}

Intern vertegenwoordigt de `DateTimeField` component zijn waarde met behulp van een `LocalDateTime` object uit het `java.time` pakket. Dit biedt nauwkeurige controle over zowel de datum- als tijdcomponenten van de invoer.

Hoewel de **client-side** waarde wordt weergegeven op basis van de locale van de browser van de gebruiker (bijvoorbeeld, datum- en tijdformaten die overeenkomen met lokale conventies), volgt de **geparste** waarde een strikte en voorspelbare structuur: **`yyyy-MM-ddTHH:mm:ss`**.

### Het verkrijgen en instellen van de waarde {#getting-and-setting-the-value}

Om de huidige waarde op te halen, gebruik de `getValue()` methode:

```java
LocalDateTime value = dateTimeField.getValue();
```

Om de waarde programmatisch in te stellen, gebruik de `setValue()` methode:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### Gebruik van `setText()` {#using-settext}

Als je de waarde via een ruwe string wilt instellen, moet deze voldoen aan het exacte formaat van `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // geldig

dateTimeField.setText("24-04-27T14:30:00"); // ongeldig
```

:::warning
 Wanneer je de `setText()` methode gebruikt, wordt er een `IllegalArgumentException` opgegooid als de component de invoer niet kan parseren in het `yyyy-MM-ddTHH:mm:ss` formaat.
:::

## Statische hulpprogramma's {#static-utilities}

De DateTimeField klasse biedt ook de volgende statische hulpprogramma's:

- `fromDateTime(String dateTimeAsString)`: Converteer een datum- en tijdstring in het `yyyy-MM-ddTHH:mm:ss` formaat naar een LocalDateTime object dat vervolgens kan worden gebruikt met deze klasse, of elders.

- `toDateTime(LocalDateTime dateTime)`: Converteer een LocalDateTime object naar een datum- en tijdstring in het `yyyy-MM-ddTHH:mm:ss` formaat.

- `isValidDateTime(String dateTimeAsString)`: Controleert of de gegeven string een geldige `yyyy-MM-ddTHH:mm:ss` datum en tijd is. Dit zal een boolean waarde true retourneren als dat zo is, of false anders.

## Min- en maxwaarde {#min-and-max-value}

### De minwaarde {#the-min-value}

Als de waarde die in de component is ingevoerd eerder is dan de gespecificeerde minimumtijdstempel, zal de component falen bij de constraintvalidatie. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de minwaarde een tijdstempel zijn die hetzelfde is of eerder dan de maxwaarde.

```java
// Stel de minimum toegestane tijdstempel in: 1 januari 2023 om 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### De maxwaarde {#the-max-value}

Als de waarde die in de component is ingevoerd later is dan de gespecificeerde maximumtijdstempel, zal de component falen bij de constraintvalidatie. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de maxwaarde een tijdstempel zijn die hetzelfde is of later dan de minwaarde.

```java
// Stel de maximum toegestane tijdstempel in: 31 december 2023 om 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `DateTimeField` component, overweeg de volgende best practices:

- **Gepersonaliseerde Datum Weergave**: Lokaliseren van het datumformaat en het opnemen van regionale voorkeuren zorgt ervoor dat data in een vertrouwd formaat aan de gebruiker worden gepresenteerd.

- **Inclusief Tijdzones**: Als je app tijdgevoelige informatie over verschillende tijdzones verwerkt, overweeg dan om tijdzone selectie naast het datumveld op te nemen om een nauwkeurige datum-tijdweergave te waarborgen.

- **Toegankelijkheid**: Gebruik de `DateTimeField` met toegankelijkheid in gedachten. Zorg ervoor dat deze voldoet aan toegankelijkheidsstandaarden, zoals het bieden van correcte labels en compatibiliteit met ondersteunende technologieën.

- **Auto-populeer Huidige Datum**: Overweeg om een optie te bieden om de huidige datum en tijd als standaardwaarde in te vullen in het datumtijdveld, indien passend voor het gebruiksdoel van je app.
