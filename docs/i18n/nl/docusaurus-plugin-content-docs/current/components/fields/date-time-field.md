---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
_i18n_hash: 9ccb17716ffd5fa2d32781fc603d5042
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

De `DateTimeField`-component stelt gebruikers in staat om zowel een datum als een tijd in één veld in te voeren, inclusief jaar, maand, dag, uren en minuten. Het valideert invoer op nauwkeurigheid en kan een datum-tijdkiezer tonen om de selectie gemakkelijker te maken.

<!-- INTRO_END -->

## Gebruik van `DateTimeField` {#using-datetimefield}

<ParentLink parent="Field" />

`DateTimeField` breidt de gedeelde `Field`-klasse uit, die gemeenschappelijke functies biedt voor alle veldcomponenten. Het volgende voorbeeld creëert een gelabeld `DateTimeField` voor het selecteren van een vertrekdatum en -tijd.

<ComponentDemo
path='/webforj/datetimefield'
files={['src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java']}
/>

## Toepassingen {#usages}

De `DateTimeField` is het beste te gebruiken in scenario's waar het essentieel is om zowel de datum **als** de tijd vast te leggen of weer te geven in uw app. Hier zijn enkele voorbeelden wanneer u de `DateTimeField` kunt gebruiken:

1. **Evenementplanning en agenda's**: Laat gebruikers evenementen efficiënt plannen, afspraken boeken en hun agenda's beheren door ze een enkel component te geven waarmee ze de datum en tijd kunnen kiezen.
<!-- vale off -->
2. **Inchecken en uitchecken**: Vergemakkelijk de keuze van gebruikers voor incheck- en uitchecktijden wanneer de periode meerdere dagen kan beslaan.
<!-- vale on -->
3. **Gegevensregistratie en tijdstempels**: Gebruik `DateTimeFields` voor apps die de datum en tijd van gebeurtenissen of wanneer een gebruiker gegevens indient, moeten registreren.

4. **Taakbeheer en deadlines**: `DateTimeFields` zijn waardevol in toepassingen die taakbeheer of het instellen van deadlines omvatten waarbij zowel de datum als de tijd relevant zijn voor nauwkeurige planning.

## Veldwaarde (`LocalDateTime`) {#field-value-localdatetime}

Intern vertegenwoordigt de `DateTimeField`-component zijn waarde met een `LocalDateTime`-object uit het `java.time`-pakket. Waarden worden afgerond tot minuten, zodat seconden en fracties van seconden worden weggegooid.

De browser toont de datum en tijd volgens de lokale instellingen van de gebruiker, terwijl de tekstwaarde van het veld het formaat `yyyy-MM-ddTHH:mm` gebruikt. `LocalDateTime` omvat geen tijdzone, en invoer met een tijdzone-suffix zoals `Z` of `+02:00` wordt niet geaccepteerd.

### Waarde ophalen en instellen {#getting-and-setting-the-value}

Om de huidige waarde op te halen, gebruikt u de `getValue()`-methode:

```java
LocalDateTime value = dateTimeField.getValue();
```

Om de waarde programmatatisch in te stellen, gebruikt u de `setValue()`-methode:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30));
```

### Gebruik van `setText()` {#using-settext}

Om de waarde vanuit een ruwe tekenreeks in te stellen, gebruikt u `setText()` met een lokale datum-tijd, zoals `yyyy-MM-ddTHH:mm`. Invoer kan ook seconden of fracties van seconden bevatten, die dan worden weggegooid.

```java
dateTimeField.setText("2024-04-27T14:30");       // geldig
dateTimeField.setText("2024-04-27T14:30:45");    // geldig; secondes worden weggegooid

dateTimeField.setText("24-04-27T14:30");        // ongeldig; gebruik een viercijferig jaar
dateTimeField.setText("2024-04-27T14:30Z");      // ongeldig; tijdzone-suffixen worden niet geaccepteerd
```

:::warning
Bij het gebruik van `setText()` wordt een `IllegalArgumentException` gegooid als de invoer niet kan worden geparsed als een geldige lokale datum-tijd. Zowel invoer met minuut-nauwkeurigheid als invoer met seconden worden geaccepteerd, maar tijdzone-suffixen niet.
:::

## Statische hulpmiddelen {#static-utilities}

De DateTimeField-klasse biedt ook de volgende statische hulpprogramma's:

- `fromDateTime(String dateTimeAsString)`: parseer een lokale datum-tijdtekenreeks, met of zonder seconden, naar een `LocalDateTime` dat is afgerond tot minuten.

- `toDateTime(LocalDateTime dateTime)`: converteer een `LocalDateTime` naar een tekenreeks in het formaat `yyyy-MM-ddTHH:mm`. Seconden en fracties van seconden worden weggegooid.

- `isValidDateTime(String dateTimeAsString)`: controleer of een tekenreeks een geldige lokale datum-tijd is. Invoer met seconden of fracties van seconden wordt geaccepteerd. Retourneert `false` voor ongeldige invoer of tijdzone-suffixen.

## Min- en maxwaarde {#min-and-max-value}

### De minwaarde {#the-min-value}

Als de waarde die in het component is ingevoerd eerder is dan de opgegeven minimale tijdstempel, zal het component niet voldoen aan de constraint-validatie. Wanneer zowel de min- als maxwaarde zijn ingesteld, moet de minwaarde een tijdstempel zijn die hetzelfde is of eerder dan de maxwaarde.

```java
// Stel de minimum toegestane tijdstempel in: 1 januari 2023 om 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### De maxwaarde {#the-max-value}

Als de waarde die in het component is ingevoerd later is dan de opgegeven maximale tijdstempel, zal het component niet voldoen aan de constraint-validatie. Wanneer zowel de min- als maxwaarde zijn ingesteld, moet de maxwaarde een tijdstempel zijn die hetzelfde is of later dan de minwaarde.

```java
// Stel de maximale toegestane tijdstempel in: 31 december 2023 om 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `DateTimeField`-component, overweeg de volgende beste praktijken:

- **Gepersonaliseerde datumweergave**: Door het datumformaat te lokaliseren en regionale voorkeuren te integreren, zorg je ervoor dat data in een vertrouwd formaat aan de gebruiker worden gepresenteerd.

- **Tijdzones apart behandelen**: als uw app over tijdzones heen werkt, bied dan een aparte tijdzone-selector aan en behandel conversies in uw app. De `DateTimeField` slaat alleen de lokale datum en tijd op.

- **Toegankelijkheid**: Gebruik de `DateTimeField` met toegankelijkheid in gedachten. Zorg ervoor dat het voldoet aan toegankelijkheidsnormen, zoals het bieden van goede labels en compatibel zijn met ondersteunende technologieën.

- **Auto-invullen van de huidige datum**: overweeg een optie te bieden om de huidige datum en tijd automatisch in te vullen als standaardwaarde in het datumtijdveld, indien passend voor het gebruiksdoel van uw app.
