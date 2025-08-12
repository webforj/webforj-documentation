---
sidebar_position: 15
title: DateTimeField
slug: datetimefield
description: >-
  A component that provides a default browser-based date and time picker for
  selecting both date and time through a single input field.
sidebar_class_name: updated-content
_i18n_hash: dd6fe3e8a737f5b016f92629d9767dbb
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/DateTimeField" top='true'/>

<ParentLink parent="Field" />

De `DateTimeField` component is ontworpen om gebruikers in staat te stellen zowel een datum als een tijd in te voeren. Dit omvat het specificeren van het jaar, de maand en de dag, samen met de tijd in uren en minuten. Het biedt gebruikers de optie om hun invoer te valideren op nauwkeurigheid of een speciale date-time picker interface te gebruiken om het selecteren te vergemakkelijken.

<ComponentDemo 
path='/webforj/datetimefield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/datetimefield/DateTimeFieldView.java'
/>

## Gebruiken {#usages}

De `DateTimeField` is het beste in situaties waarbij het vastleggen of weergeven van zowel datum **als** tijd essentieel is voor je app. Hier zijn enkele voorbeelden van wanneer je de `DateTimeField` kunt gebruiken:

1. **Evenementen plannen en agenda's**: Laat gebruikers efficiënt evenementen plannen, afspraken boeken en hun agenda's beheren door ze een enkele component te geven waarmee ze de datum en tijd kunnen kiezen.
<!-- vale off -->
2. **Inchecken en uitchecken**: Faciliteer de selectie van incheck- en uitchecktijden wanneer de periode meerdere dagen kan beslaan.
<!-- vale on -->
3. **Gegevensregistratie en tijdstempels**: Gebruik `DateTimeFields` voor apps die het vastleggen van de datum en tijd van gebeurtenissen of wanneer een gebruiker gegevens indient.

4. **Taakbeheer en deadlines**: `DateTimeFields` zijn waardevol in applicaties die betrekking hebben op taakbeheer of het stellen van deadlines waarbij zowel de datum als de tijd relevant zijn voor nauwkeurige planning.

## Veldwaarde (`LocalDateTime`) {#field-value-localdatetime}

Intern vertegenwoordigt de `DateTimeField` component zijn waarde met een `LocalDateTime` object uit het `java.time` pakket. Dit biedt nauwkeurige controle over zowel de datum- als tijdcomponenten van de invoer.

Hoewel de **client-side** waarde is weergegeven op basis van de browserlocatie van de gebruiker (bijvoorbeeld datum- en tijdformaten die overeenkomen met lokale conventies), volgt de **geparsed** waarde een strikte en voorspelbare structuur: **`yyyy-MM-ddTHH:mm:ss`**.

### Huidige waarde ophalen en instellen {#getting-and-setting-the-value}

Om de huidige waarde op te halen, gebruik de `getValue()` methode:

```java
LocalDateTime value = dateTimeField.getValue();
```

Om programatisch de waarde in te stellen, gebruik de `setValue()` methode:

```java
dateTimeField.setValue(LocalDateTime.of(2024, 4, 27, 14, 30, 0));
```

### `setText()` gebruiken {#using-settext}

Als je de waarde via een ruwe string wilt instellen, moet deze exact voldoen aan het formaat `yyyy-MM-ddTHH:mm:ss`.

```java
dateTimeField.setText("2024-04-27T14:30:00"); // geldig

dateTimeField.setText("24-04-27T14:30:00"); // ongeldig
```

:::warning
 Wanneer de `setText()` methode wordt gebruikt, zal een `IllegalArgumentException` worden gegooid als de component de invoer niet kan parseren in het `yyyy-MM-ddTHH:mm:ss` formaat.
:::

## Statische hulpprogramma's {#static-utilities}

De DateTimeField-klasse biedt ook de volgende statische hulpprogramma-methoden:

- `fromDateTime(String dateTimeAsString)`: Converteer een datum- en tijdstring in `yyyy-MM-ddTHH:mm:ss` formaat naar een LocalDateTime object dat vervolgens met deze klasse of elders kan worden gebruikt.

- `toDateTime(LocalDateTime dateTime)`: Converteer een LocalDateTime object naar een datum- en tijdstring in `yyyy-MM-ddTHH:mm:ss` formaat.

- `isValidDateTime(String dateTimeAsString)`: Controleert of de opgegeven string een geldige `yyyy-MM-ddTHH:mm:ss` datum en tijd is. Dit zal een booleaanse waarde true retourneren als dat zo is, false anders.

## Min- en maxwaarde {#min-and-max-value}

### De minwaarde {#the-min-value}

Als de waarde die in de component wordt ingevoerd eerder is dan de gespecificeerde minimumtijdstempel, zal de component falen bij de beperkingvalidatie. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de minwaarde een tijdstempel zijn die gelijk is aan of eerder dan de maxwaarde.

```java
// Stel de minimale toegestane tijdstempel in: 1 januari 2023 om 08:00
dateTimeField.setMin(LocalDateTime.of(2023, 1, 1, 8, 0));
```

### De maxwaarde {#the-max-value}

Als de waarde die in de component wordt ingevoerd later is dan de gespecificeerde maximumtijdstempel, zal de component falen bij de beperkingvalidatie. Wanneer zowel de min- als maxwaarden zijn ingesteld, moet de maxwaarde een tijdstempel zijn die gelijk is aan of later dan de minwaarde.

```java
// Stel de maximale toegestane tijdstempel in: 31 december 2023 om 18:00
dateTimeField.setMax(LocalDateTime.of(2023, 12, 31, 18, 0));
```

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `DateTimeField` component, overweeg dan de volgende beste praktijken:

- **Gepersonaliseerde datumweergave**: Het localiseren van het datumformaat en het opnemen van regionale voorkeuren zorgt ervoor dat datums in een vertrouwd formaat aan de gebruiker worden gepresenteerd.

- **Include tijdzones**: Als je app met tijdgevoelige informatie omgaat over verschillende tijdzones, overweeg dan om tijdzone-selectie naast het datuminvoerveld op te nemen om een nauwkeurige datum-tijdweergave te waarborgen.

- **Toegankelijkheid**: Gebruik de `DateTimeField` met toegankelijkheid in gedachten. Zorg ervoor dat het voldoet aan de toegankelijkheidseisen, zoals het bieden van de juiste labels en compatibel zijn met hulpmiddelen voor toegankelijkheid.

- **Automatisch huidige datum invullen**: Overweeg om een optie aan te bieden om de huidige datum en tijd automatisch in te vullen als een standaardwaarde in het datumtijdveld, indien passend voor de use case van je app.
