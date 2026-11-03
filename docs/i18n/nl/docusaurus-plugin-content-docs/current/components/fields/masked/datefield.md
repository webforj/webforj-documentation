---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: b327e838739be59acec7bfbedc3bb027
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

De `MaskedDateField` is een tekstinvoer waarmee gebruikers datums als nummers kunnen invoeren en die automatisch de invoer formatteert op basis van een gedefinieerde maskering wanneer het veld de focus verliest. De maskering specificeert het verwachte datumformaat en leidt zowel de invoer als de weergave. De component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel voor consistente, regio-specifieke datumverwerking.

<!-- INTRO_END -->

:::tip Op zoek naar tijdinvoer?
De `MaskedDateField` is uitsluitend gericht op **datum** waarden. Als je een vergelijkbare component nodig hebt voor het invoeren en formatteren van **tijd**, kijk dan in de [`MaskedTimeField`](/docs/components/fields/masked/timefield) in plaats daarvan.
:::

De `MaskedDateField` kan met of zonder parameters worden geïnstantieerd. Je kunt een initiële waarde, een label, een placeholder en een gebeurtenislijsten voor waarde wijzigingen definiëren.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskerregels {#mask-rules}

De `MaskedDateField` ondersteunt meerdere datumformaten die wereldwijd worden gebruikt en die variëren in de volgorde van dag, maand en jaar. Veelvoorkomende patronen zijn:

- **Dag/Maand/Jaar** (gebruikt in de meeste delen van Europa)
- **Maand/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/Maand/Dag** (gebruikt in China, Japan en Zuid-Korea; ook de ISO-standaard: `YYYY-MM-DD`)

Binnen deze formaten omvatten lokale variaties de keuze van scheidingstekens (bijv. `-`, `/` of `.`), of jaren twee of vier cijfers zijn, en of enkelcijferige maanden of dagen worden gepadded met voorloopnullen.

Om met deze diversiteit om te gaan, gebruikt de `MaskedDateField` formaatindicatoren, die beginnen met `%`, gevolgd door een letter die een specifiek onderdeel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe de invoer wordt geparsed en hoe de datum wordt weergegeven.

:::tip Maskers programmatisch toepassen
Om datums met dezelfde maskersyntaxis buiten een veld te formatteren of parseren, gebruik de utilityklasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Datumformaatindicatoren {#date-format-indicators}

| Formaat | Beschrijving |
| ------- | ------------ |
| `%Y`    | Jaar         |
| `%M`    | Maand        |
| `%D`    | Dag          |

### Modifiers {#modifiers}

Modifiers bieden meer controle over hoe de componenten van de datum zijn geformatteerd:

| Modifier | Beschrijving                       |
| -------- | ---------------------------------- |
| `z`      | Zero-fill                         |
| `s`      | Korte tekstweergave               |
| `l`      | Lange tekstweergave               |
| `p`      | Gecomprimeerd nummer               |
| `d`      | Decimaal (standaardformaat)       |

Deze kunnen worden gecombineerd om een breed scala aan datummaskers te bouwen.

## Lokalisatie van datumformaten {#date-format-localization}

De `MaskedDateField` past zich aan regionale datumformaten aan door de juiste locale in te stellen. Dit zorgt ervoor dat datums worden weergegeven en geparsed op een manier die overeenkomt met de verwachtingen van de gebruiker.

| Regio        | Formaat     | Voorbeeld      |
| ------------ | ----------- | -------------- |
| Verenigde Staten | MM/DD/YYYY | `07/04/2023` |
| Europa       | DD/MM/YYYY  | `04/07/2023`  |
| ISO-standaard| YYYY-MM-DD  | `2023-07-04`  |

Om lokalisatie toe te passen, gebruik de methode `setLocale()`. Deze accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel de opmaak als de parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsinglogica {#parsing-logic}

De `MaskedDateField` parseert gebruikersinvoer op basis van de gedefinieerde datummasker. Het accepteert zowel complete als verkorte numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is terwijl geldige datums worden gewaarborgd. De parsinggedragingen hangen af ​​van de formatvolgorde die door de masker is gedefinieerd (bijv. `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke sequenties worden geïnterpreteerd.

Bijvoorbeeld, aangenomen dat vandaag `15 september 2012` is, zo zouden verschillende invoer worden geïnterpreteerd:

### Voorbeeld parsingscenario's {#example-parsing-scenarios}

| Invoer                                 | YMD (ISO)                                                                                                                                                                                      | MDY (VS)                                                                          | DMY (EU)                                                                                                                          |
| -------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| ---------------------------------------------------------------------------------| ------------------------------------------------------------------------------------------------------------------------------ |
| <div align="center">`1`</div>         | Een enkelcijferige invoer wordt altijd geïnterpreteerd als een dag binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                 | Hetzelfde als YMD                                                                  | Hetzelfde als YMD                                                                                                                  |
| <div align="center">`12`</div>        | Twee cijfers worden altijd geïnterpreteerd als een dag binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                           | Hetzelfde als YMD                                                                  | Hetzelfde als YMD                                                                                                                  |
| <div align="center">`112`</div>       | Drie cijfers worden geïnterpreteerd als een 1-cijferige maand gevolgd door een 2-cijferige dag, dus dit zou 12 januari 2012 zijn.                                                           | Hetzelfde als YMD                                                                  | Drie cijfers worden geïnterpreteerd als een 1-cijferige dag gevolgd door een 2-cijferige maand, dus dit zou 1 december 2012 zijn. |
| <div align="center">`1004`</div>      | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                              | Hetzelfde als YMD                                                                  | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                                    |
| <div align="center">`020304`</div>    | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                               | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.          | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                                    |
| <div align="center">`8 cijfers`</div> | Acht cijfers worden geïnterpreteerd als YYYYMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                | Acht cijfers worden geïnterpreteerd als MMDDYYYY. Bijvoorbeeld, `06122004` is 12 juni 2004.    | Acht cijfers worden geïnterpreteerd als DDMMYYYY. Bijvoorbeeld, `06122004` is 6 december 2004.                                   |
| <div align="center">`12/6`</div>      | Twee getallen gescheiden door een geldig scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle karakters behalve letters en cijfers worden beschouwd als geldige scheidingstekens. | Hetzelfde als YMD                                                                  | Twee getallen gescheiden door een scheidingsteken worden geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.                 |
| <div align="center">`3/4/5`</div>     | 5 april 2012                                                                                                                                                                                | 4 maart 2005                                                                         | 3 april 2005                                                                                                                    |

## Tekstuele datum parsing <DocChip chip='since' label='25.10' /> {#textual-date-parsing}

Standaard accepteert de `MaskedDateField` alleen numerieke invoer voor datums. Je kunt echter **tekstuele datum parsing** inschakelen om gebruikers toe te staan maand- en dagnamen in hun invoer in te voeren. Deze functie is bijzonder nuttig voor het creëren van natuurlijkere datumeingangen.

Om tekstuele parsing in te schakelen, gebruik de methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Vervanging van maandnamen {#month-name-substitution}

Wanneer tekstuele parsing is ingeschakeld, kun je speciale modifiers in je masker gebruiken om maandnamen in plaats van numerieke waarden te accepteren:

- **`%Ms`** - Accepteert korte maandnamen (Jan, Feb, Mar, enz.)
- **`%Ml`** - Accepteert lange maandnamen (januari, februari, maart, enz.)

Maandnamen kunnen in elke positie binnen het masker verschijnen, en het veld accepteert nog steeds numerieke invoer als fallback.

#### Voorbeelden {#examples}

| Masker | Invoer             | Resultaat                                  |
| ------ | ------------------| ------------------------------------------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25`         | **Geldig** - Parseert als 1 september 2025    |
| `%Ml/%Dz/%Yz` | `September/01/25`  | **Geldig** - Parseert als 1 september 2025    |
| `%Dz/%Ml/%Yz` | `01/September/25`   | **Geldig** - Parseert als 1 september 2025    |
| `%Mz/%Dz/%Yz` | `09/01/25`         | **Geldig** - Numerieke fallback werkt nog steeds |

:::info
Alle 12 maanden worden ondersteund in zowel korte (Jan, Feb, Mar, Apr, Mei, Jun, Jul, Aug, Sep, Okt, Nov, Dec) als lange (januari, februari, enz.) vormen.
:::
### Decoratie van de dagnaam {#day-name-decoration}

Dag-van-de-week namen kunnen in de invoer worden opgenomen voor betere leesbaarheid, maar ze zijn **slechts decoratief** en worden verwijderd tijdens parsing. Ze hebben geen invloed op de daadwerkelijke datumwaarde.

- **`%Ds`** - Accepteert korte dagnamen (ma, di, wo, enz.)
- **`%Dl`** - Accepteert lange dagnamen (maandag, dinsdag, woensdag, enz.)

:::warning Dagnamen vereisen een numerieke dag
Bij gebruik van dag-van-de-week namen (`%Ds` of `%Dl`) moet je masker **ook** `%Dz` of `%Dd` bevatten om het werkelijke dagnummer op te geven. Zonder een numerieke dagcomponent is de invoer ongeldig.
:::

#### Voorbeelden {#examples-1}

| Masker                  | Invoer                 | Resultaat                                  |
| ----------------------- | --------------------- | ------------------------------------------- |
| `%Ds %Mz/%Dz/%Yz`      | `Ma 09/01/25`        | **Geldig** - De dagnaam is decoratief      |
| `%Dl %Mz/%Dz/%Yz`      | `Maandag 09/01/25`   | **Geldig** - De dagnaam is decoratief      |
| `%Mz/%Dz/%Yz %Ds`      | `09/01/25 Di`        | **Geldig** - De dagnaam aan het einde      |
| `%Dl/%Mz/%Yz`          | `Maandag/09/25`      | **Ongeldig** - `%Dz` ontbreekt             |
| `%Mz/%Dl/%Yz`          | `09/Maandag/25`      | **Ongeldig** - `%Dz` ontbreekt             |

Alle 7 weekdagen worden ondersteund in zowel korte (ma, di, wo, do, vr, za, zo) als lange (maandag, dinsdag, enz.) vormen.

### Aanvullende parsingregels {#additional-parsing-rules}

Tekstuele datum parsing omvat verschillende handige functies:

- **Hoofdletterongevoelig:** Invoer zoals `MAANDAG 09/01/25`, `maandag 09/01/25` of `Maandag 09/01/25` werkt op dezelfde manier.
- **Locale-bewust:** Maand- en dagnamen moeten overeenkomen met de geconfigureerde locale van het veld. Bijvoorbeeld, met een Franse locale, gebruik `septembre` in plaats van `September`. Engelse namen worden niet herkend tenzij de locale op Engels is ingesteld.
  - Franse locale: `septembre/01/25` wordt herkend als September
  - Duitse locale: `Montag 09/01/25` wordt herkend met maandag als de dagnaam

## Min/max beperkingen instellen {#setting-minmax-constraints}

Je kunt het toegestaan datumbereik in een `MaskedDateField` beperken met de methoden `setMin()` en `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methoden accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedDateField` bevat een herstelfunctie die de waarde van het veld opnieuw instelt op een vooraf gedefinieerde of originele staat. Dit is nuttig voor het terugdraaien van gebruikersinvoer of om terug te keren naar een standaarddatum.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatisch**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaardhersteltoets tenzij overschreven door een gebeurtenislijsten)

Je kunt de waarde instellen om te herstellen met `setRestoreValue()`, door een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie door te geven.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met reguliere expressies met de methode `setPattern()`:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `MM/DD/YYYY` formaat (twee cijfers, schuine streep, twee cijfers, schuine streep, vier cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressieformaat
Het patroon moet de JavaScript RegExp-syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Aantekeningen over invoerbehandeling
Het veld probeert numerieke datum invoer te parseren en formatteren op basis van de huidige maskering. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig maar semantisch ongeldig of niet parseerbaar is (bijv. `99/99/9999`), kan deze patrooncontroles doorstaan maar falen in logische validatie.
Je moet altijd de invoerwaarde in de logica van je applicatie valideren, zelfs als een reguliere expressiepatroon is ingesteld, om ervoor te zorgen dat de datum zowel correct is geformatteerd als betekenisvol.
::::

## Datumkiezer {#date-picker}

De `MaskedDateField` bevat een ingebouwde kalenderkiezer waarmee gebruikers een datum visueel kunnen selecteren in plaats van deze in te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer een nauwkeurige invoer vereist is.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt de datumkiezer openen met `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Toon/verberg het pickericoon {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het kalendericoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Auto-open gedrag {#auto-open-behavior}

Je kunt de kiezer zo configureren dat deze automatisch opent wanneer de gebruiker interactie heeft met het veld (bijvoorbeeld, klikt, op Enter drukt of pijltoetsen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Dwingen van selectie via de kiezer
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren met behulp van de kalenderkiezer (en niet handmatig typen), combineer de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
dateField.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze configuratie garandeert dat alle datum invoer via de gebruikersinterface van de kiezer komt, wat nuttig is wanneer je strikte opmaakcontrole wilt en parsingproblemen uit getypte invoer wilt elimineren.
:::

### Handmatig de kalender openen {#manually-open-the-calendar}

Om de kalender programmatisch te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### Toon weken in de kalender {#show-weeks-in-the-calendar}

De kiezer kan optioneel weeknummers in de kalenderweergave weergeven:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

De `MaskedDateFieldSpinner` breidt de `MaskedDateField` uit door spinners te voegen waarmee gebruikers de datum met pijltoetsen of UI-knoppen kunnen verhogen of verlagen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktop-achtige toepassingen.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Belangrijkste functies {#key-features}

- **Interactieve datumverandering:**
  Gebruik pijltoetsen of spin-knoppen om de datumwaarde te verhogen of te verlagen.

- **Aanpasbare stap eenheid:**
  Kies welk onderdeel van de datum moet worden gewijzigd met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties zijn `DAY`, `WEEK`, `MONTH`, en `YEAR`.

- **Min/Max grenzen:**
  Erf de ondersteuning voor minimum en maximum toegestane datums met `setMin()` en `setMax()`.

- **Geformatteerde uitvoer:**
  Volledig compatibel met maskeringen en lokalisatie-instellingen van `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stappen {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dit maakt elke draai- of terugdraai-stap een week vooruit of achteruit in de datum.

## Stijling {#styling}

<TableBuilder name="MaskedDateField" />
