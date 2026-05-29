---
sidebar_position: 16
title: MaskDecorator
sidebar_class_name: new-content
_i18n_hash: 30ecd8eeaa79a3e5f963e319373d1378
---
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/MaskDecorator" top='true'/>

`MaskDecorator` is een statische utilityklasse voor het toepassen van maskers op strings, getallen, datums en tijden buiten een invoerveld. Het gebruikt dezelfde maskersyntax als de [gemaskeerde veldcomponenten](/docs/components/fields/masked/overview) van webforJ, waardoor het eenvoudig is om waarden consistent te formatteren en te parseren—of het nu in een displaylabel, een [`Table`](/docs/components/table/overview) renderer, of op een andere plek in je app is.

Gebruik `MaskDecorator` wanneer je waarden programmeertmatig wilt formatteren voor weergave in plaats van voor interactieve invoer, zoals in tabelcelrenderers, alleen-lezen labels, geëxporteerde rapporten, of in elke context waar een formulierinvoerveld niet geschikt is. Voor interactieve formatting terwijl een gebruiker typt, gebruik je in plaats daarvan een gemaskeerde veldcomponent.

## Masking strings {#masking-strings}

Gebruik `forString()` om een karaktermasker toe te passen op een eenvoudige stringwaarde:

```java
String result = MaskDecorator.forString("abc123", "AAA-000");
// → "ABC-123"
```

Het masker bepaalt welke karakters op elke positie worden geaccepteerd.

### Mask characters {#string-mask-characters}

| Karakter | Beschrijving |
|----------|--------------|
| `X`      | Elk afdrukbaar karakter |
| `a`      | Elk alfabetisch karakter |
| `A`      | Elk alfabetisch karakter; kleine letters worden omgezet naar hoofdletters |
| `0`      | Elk cijfer (0–9) |
| `z`      | Elk cijfer of letter |
| `Z`      | Elk cijfer of letter; kleine letters worden omgezet naar hoofdletters |

Elk ander karakter in het masker wordt behandeld als een literal en wordt onveranderd in de output ingevoegd. Ongeldige karakters in de invoer worden stilzwijgend genegeerd, korte invoer wordt opgevuld met spaties en lange invoer wordt ingekort om in het masker te passen.

### Voorbeelden {#string-examples}

```java
MaskDecorator.forString("1234567890", "(000) 000-0000");  // → "(123) 456-7890"
MaskDecorator.forString("a1b2c3",     "A0A 0A0");         // → "A1B 2C3"
MaskDecorator.forString("1234",       "ZZZZ-0000");        // → "1234-    " (opgevuld)
```

## Masking numbers {#masking-numbers}

Gebruik `forNumber()` om een numerieke waarde te formatteren met behulp van een getalmasker:

```java
String result = MaskDecorator.forNumber(1234567.89, "#,###,##0.00");
// → "1,234,567.89"
```

### Mask characters {#number-mask-characters}

| Karakter | Beschrijving |
|----------|--------------|
| `0`      | Altijd vervangen door een cijfer (0–9) |
| `#`      | Onderdrukt leidende nullen. Wordt vervangen door het opvulkarakter links van de decimaal. Voor achtergaande cijfers rechts wordt vervangen door een spatie of nul. Anders wordt vervangen door een cijfer |
| `,`      | Wordt gebruikt als een groepscheidingsteken. Wordt vervangen door het opvulkarakter als er nog geen cijfers zijn geplaatst; anders wordt het weergegeven als een komma |
| `-`      | Toont `-` als het getal negatief is; vervangen door het opvulkarakter als het positief is |
| `+`      | Toont `+` als het positief is, of `-` als het negatief is |
| `$`      | Resultaat altijd een dollarteken |
| `(`      | Voegt `(` in als het getal negatief is; vervangen door het opvulkarakter als het positief is |
| `)`      | Voegt `)` in als het getal negatief is; vervangen door het opvulkarakter als het positief is |
| `CR`     | Voegt `CR` in voor negatieve getallen; twee spaties voor positieve getallen |
| `DR`     | Voegt `CR` in voor negatieve getallen; `DR` voor positieve getallen |
| `*`      | Voegt altijd een asterisk in |
| `.`      | Merkt de decimale punt aan. Wordt vervangen door het opvulkarakter als er geen cijfers in de output verschijnen. Na de decimaal wordt het opvulkarakter een spatie |
| `B`      | Wordt altijd een spatie; elk ander literair karakter wordt onveranderd gekopieerd |

De karakters `-`, `+`, `$` en `(` kunnen zweven: de eerste keer dat ze voorkomen, wordt verplaatst naar de laatste positie waar een `#` of `,` werd vervangen door het opvulkarakter.

:::info Afrondgedrag
`forNumber()` rondt waarden af om overeen te komen met de decimale precisie in het masker. Bijvoorbeeld, `MaskDecorator.forNumber(12.34567, "###0.00")` produceert `"  12.35"`.
:::

### Voorbeelden {#number-examples}

```java
MaskDecorator.forNumber(1234.5,    "###,##0.00");  // → "  1,234.50"
MaskDecorator.forNumber(-9876.0,   "###,##0.00-"); // → "  9,876.00-"
MaskDecorator.forNumber(42.0,      "$###,##0.00"); // → "     $42.00"
MaskDecorator.forNumber(0.5,       "#0.000");      // → " 0.500"
```

## Masking dates {#masking-dates}

Gebruik `forDate()` om een `LocalDate` waarde te formatteren met een datummasker:

```java
LocalDate date = LocalDate.of(2025, 7, 4);
String result = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
// → "07/04/2025"
```

Gebruik `parseDate()` om een gemaskeerde datumstring terug te parseren naar een `LocalDate`:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl");
// → LocalDate.of(2025, 7, 4)
```

Een locale-gevoelige overload is beschikbaar bij het parseren van strings die weeknummerreferenties bevatten:

```java
LocalDate date = MaskDecorator.parseDate("07/04/2025", "%Mz/%Dz/%Yl", Locale.US);
```

### Date format indicators {#date-format-indicators}

| Formaat | Beschrijving |
|---------|--------------|
| `%Y`    | Jaar         |
| `%M`    | Maand        |
| `%D`    | Dag          |

### Modifiers {#date-modifiers}

Een optionele modifier volgt onmiddellijk het formaatindicatie:

| Modifier | Beschrijving                     |
|----------|----------------------------------|
| `z`      | Zero-fill                       |
| `s`      | Korte tekstweergave            |
| `l`      | Lange tekstweergave            |
| `p`      | Samengesteld nummer             |
| `d`      | Decimaal (standaardformaat)    |

### Voorbeelden {#date-examples}

```java
LocalDate d = LocalDate.of(2025, 3, 5);

MaskDecorator.forDate(d, "%Mz/%Dz/%Yl");  // → "03/05/2025"
MaskDecorator.forDate(d, "%Dz.%Mz.%Yz");  // → "05.03.25"
MaskDecorator.forDate(d, "%Dl, %Ml %Dz");  // → "Woensdag, Maart 05"
MaskDecorator.forDate(d, "%Yl-%Mz-%Dz");  // → "2025-03-05"
```

## Masking times {#masking-times}

Gebruik `forTime()` om een `LocalTime` waarde te formatteren met een tijdmasker:

```java
LocalTime time = LocalTime.of(14, 30, 0);
String result = MaskDecorator.forTime(time, "%Hz:%mz");
// → "14:30"
```

Gebruik `parseTime()` om een gemaskeerde tijdstring terug te parseren naar een `LocalTime`:

```java
LocalTime time = MaskDecorator.parseTime("14:30", "%Hz:%mz");
// → LocalTime.of(14, 30)
```

Een locale-gevoelige overload is beschikbaar bij het parseren van strings die gelokaliseerde AM/PM-waarden bevatten:

```java
LocalTime time = MaskDecorator.parseTime("02:30 pm", "%hz:%mz %p", Locale.US);
```

### Time format indicators {#time-format-indicators}

| Formaat | Beschrijving          |
|---------|-----------------------|
| `%H`    | Uur (24-uurs klok)    |
| `%h`    | Uur (12-uurs klok)    |
| `%m`    | Minuten               |
| `%s`    | Seconden              |
| `%p`    | am/pm                 |

### Modifiers {#time-modifiers}

Tijdmaskers gebruiken dezelfde modifiers als datummaskers. Zie [Date modifiers](#date-modifiers).

### Voorbeelden {#time-examples}

```java
LocalTime t = LocalTime.of(9, 5, 30);

MaskDecorator.forTime(t, "%Hz:%mz:%sz");  // → "09:05:30"
MaskDecorator.forTime(t, "%hz:%mz %p");   // → "09:05 am"
MaskDecorator.forTime(t, "%Hz%mz");       // → "0905"
```

## Masking date and time {#masking-datetime}

Gebruik `forDateTime()` om een `LocalDateTime` waarde te formatteren met een gecombineerd datum- en tijdmasker:

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);
String result = MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");
// → "07/04/2025 14:30"
```

### Format indicators {#datetime-format-indicators}

`forDateTime()` ondersteunt alle datum- en tijdformaatindicatoren in elke combinatie. Zie [Date format indicators](#date-format-indicators) en [Time format indicators](#time-format-indicators) voor de volledige lijst.

### Modifiers {#datetime-modifiers}

Alle modifiers die zijn beschreven in [Date modifiers](#date-modifiers) zijn van toepassing op zowel de datum- als tijds delen van een gecombineerd masker.

### Voorbeelden {#datetime-examples}

```java
LocalDateTime dt = LocalDateTime.of(2025, 7, 4, 14, 30, 0);

MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz");      // → "07/04/2025 14:30"
MaskDecorator.forDateTime(dt, "%Mz/%Dz/%Yl %Hz:%mz:%sz");  // → "07/04/2025 14:30:00"
MaskDecorator.forDateTime(dt, "%Dz.%Mz.%Yz %hz:%mz %p");  // → "04.07.25 02:30 pm"
```

## Handling null results {#handling-null-results}

:::warning
Alle `for*()` en `parse*()` methoden retourneren `null` als de invoer ongeldig is of niet kan worden geparsed. Verifieer altijd dat het resultaat niet null is voordat je het in je app-logica gebruikt.
:::

```java
String formatted = MaskDecorator.forDate(date, "%Mz/%Dz/%Yl");
if (formatted != null) {
  label.setText(formatted);
}
```
