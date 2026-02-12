---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 93973075b9f8f9bcc3eddf18e8b01017
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

De `MaskedDateField` is een tekstinvoerveld dat is ontworpen voor gestructureerde datuminvoer. Het laat gebruikers data invoeren als **cijfers** en formatteert de invoer automatisch op basis van een gedefinieerde maskering wanneer het veld de focus verliest. Het masker is een tekenreeks die het verwachte datumformaat specificeert en zowel invoer als weergave begeleidt.

Deze component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel. Het is vooral nuttig in formulieren zoals registraties, boekingen en planning, waar consistente en regiogerichte datumformaten vereist zijn.

:::tip Op zoek naar tijdinvoer?
De `MaskedDateField` is uitsluitend gericht op **datum** waarden. Als je een vergelijkbare component nodig hebt voor het invoeren en formatteren van **tijd**, kijk dan in de [`MaskedTimeField`](./timefield) in plaats daarvan.
:::

## Basisprincipes {#basics}

De `MaskedDateField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een tijdelijke aanduiding en een gebeurtenislistener voor waarde wijzigingen definiëren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Masker regels {#mask-rules}

De `MaskedDateField` ondersteund meerdere datumformaten die over de hele wereld worden gebruikt, die variëren in de volgorde van dag, maand en jaar. Veel voorkomende patronen zijn:

- **Dag/Maand/Jaar** (gebruikt in het meeste van Europa)
- **Maand/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/Maand/Dag** (gebruikt in China, Japan en Korea; ook de ISO-standaard: `YYYY-MM-DD`)

Binnen deze formaten omvatten lokale variaties de keuze van scheidingsteken (bijv. `-`, `/`, of `.`), of jaren uit twee of vier cijfers bestaan en of eencijferige maanden of dagen worden aangevuld met leidende nullen.

Om met deze diversiteit om te gaan, gebruikt de `MaskedDateField` formaatindicatoren, elk beginnend met `%`, gevolgd door een letter die een specifiek onderdeel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe de invoer wordt geparsed en hoe de datum wordt weergegeven.

### Datum formaat indicatoren {#date-format-indicators}

| Formaat | Omschrijving |
| ------- | ------------ |
| `%Y`   | Jaar         |
| `%M`   | Maand        |
| `%D`   | Dag          |

### Modifiers {#modifiers}

Modifiers bieden meer controle over hoe componenten van de datum worden geformatted:

| Modifier | Omschrijving               |
| -------- | ------------------------- |
| `z`      | Zero-fill                 |
| `s`      | Korte tekstrepresentatie   |
| `l`      | Lange tekstrepresentatie    |
| `p`      | Gecomprimeerd getal       |
| `d`      | Decimaal (Standaardformaat)|

Deze kunnen worden gecombineerd om een breed scala aan datum maskers te bouwen.

## Datum formaat lokalisatie {#date-format-localization}

De `MaskedDateField` past zich aan regionale datumformaten aan door de juiste lokale instelling te kiezen. Dit zorgt ervoor dat data op een manier worden weergegeven en geparsed die voldoet aan de verwachtingen van de gebruiker.

| Regio        | Formaat     | Voorbeeld      |
| ------------ | ----------- | -------------- |
| Verenigde Staten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO Standaard  | YYYY-MM-DD | `2023-07-04` |

Om lokalisatie toe te passen, gebruik de `setLocale()` methode. Het accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel formatting als parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing logica {#parsing-logic}

De `MaskedDateField` parsed invoer van de gebruiker op basis van het gedefinieerde datum masker. Het accepteert zowel volledige als afgekorte numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is terwijl geldige data gegarandeerd zijn. 
De parsing-gedrag hangt af van de volgorde van het formaat dat door het masker is gedefinieerd (bijv. `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke sequenties worden geïnterpreteerd.

Bijvoorbeeld, veronderstel dat vandaag `15 september 2012` is, zo zou verschillende invoer worden geïnterpreteerd:

### Voorbeeld parsing scenario's {#example-parsing-scenarios}

| Invoer                                | YMD (ISO)                                                                                                                                                                                          | MDY (VS)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Een enkel cijfer wordt altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                                 | Hetzelfde als YMD                                                                         | Hetzelfde als YMD                                                                                                                  |
| <div align="center">`12`</div>       | Twee cijfers worden altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                                   | Hetzelfde als YMD                                                                         | Hetzelfde als YMD                                                                                                                  |
| <div align="center">`112`</div>      | Drie cijfers worden geïnterpreteerd als een 1-cijferig maandnummer gevolgd door een 2-cijferig dagnummer, dus dit zou 12 januari 2012 zijn.                                                                        | Hetzelfde als YMD                                                                         | Drie cijfers worden geïnterpreteerd als een 1-cijferig dagnummer gevolgd door een 2-cijferig maandnummer, dus dit zou 1 december 2012 zijn. |
| <div align="center">`1004`</div>     | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                                             | Hetzelfde als YMD                                                                         | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                                         |
| <div align="center">`020304`</div>   | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                                              | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.            | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                                         |
| <div align="center">`8 cijfers`</div> | Acht cijfers worden geïnterpreteerd als YYYYMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                                | Acht cijfers worden geïnterpreteerd als MMDDYYYY. Bijvoorbeeld, `06122004` is 12 juni 2004. | Acht cijfers worden geïnterpreteerd als DDMMYYYY. Bijvoorbeeld, `06122004` is 6 december 2004.                                        |
| <div align="center">`12/6`</div>     | Twee nummers gescheiden door een geldig scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle tekens behalve letters en cijfers worden beschouwd als geldige scheidingstekens. | Hetzelfde als YMD                                                                         | Twee nummers die gescheiden zijn door een scheidingsteken worden geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.                               |
| <div align="center">`3/4/5`</div>    | 5 april 2012                                                                                                                                                                                      | 4 maart 2005                                                                       | 3 april 2005                                                                                                                 |


## Textuele datum parsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standaard accepteert de `MaskedDateField` alleen numerieke invoer voor data. Je kunt echter **textuele datum parsing** inschakelen om gebruikers toe te staan maand- en dagnamen in hun invoer in te voeren. Deze functie is bijzonder nuttig voor het creëren van natuurlijkere datum invoer.

Om textuele parsing in te schakelen, gebruik je de `setTextualDateParsing()` methode:

```java
dateField.setTextualDateParsing(true);
```

### Maandnaam substitutie {#month-name-substitution}

Wanneer textuele parsing is ingeschakeld, kun je speciale modifiers in je masker gebruiken om maandnamen in plaats van numerieke waarden te accepteren:

- **`%Ms`** - Accepteert korte maandnamen (Jan, Feb, Mar, enz.)
- **`%Ml`** - Accepteert lange maandnamen (Januari, Februari, Maart, enz.)

Maandnamen kunnen op elke positie binnen het masker verschijnen, en het veld accepteert nog steeds numerieke invoer als back-up.

#### Voorbeelden

| Masker | Invoer | Resultaat |
| ------ | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Geldig** - Numerieke back-up werkt nog steeds |

:::info
Alle 12 maanden worden ondersteund in zowel korte (Jan, Feb, Mar, Apr, Mei, Jun, Jul, Aug, Sep, Okt, Nov, Dec) als lange (Januari, Februari, enz.) vormen.
:::
### Dagnaam decoratie {#day-name-decoration}

Dag-namen kunnen worden opgenomen in de invoer voor betere leesbaarheid, maar ze zijn **slechts decoratief** en worden tijdens het parseren verwijderd. Ze beïnvloeden de werkelijke datumwaarde niet.

- **`%Ds`** - Accepteert korte dagnamen (Mon, Tue, Wed, enz.)
- **`%Dl`** - Accepteert lange dagnamen (Maandag, Dinsdag, Woensdag, enz.)

:::warning Dag Namen Vereisen Numerieke Dag
Bij het gebruik van dag-namen (`%Ds` of `%Dl`), moet je masker **ook** `%Dz` of `%Dd` bevatten om het werkelijke dagnummer aan te geven. Zonder een numerieke dagcomponent zal de invoer ongeldig zijn.
:::

#### Voorbeelden

| Masker | Invoer | Resultaat |
| ------ | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Mon 09/01/25` | **Geldig** - Dagnaam is decoratief |
| `%Dl %Mz/%Dz/%Yz` | `Monday 09/01/25` | **Geldig** - Dagnaam is decoratief |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Tue` | **Geldig** - Dagnaam aan het einde |
| `%Dl/%Mz/%Yz` | `Maandag/09/25` | **Ongeldig** - Mist `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Maandag/25` | **Ongeldig** - Mist `%Dz` |

Alle 7 weekdagen worden ondersteund in zowel korte (Mon, Tue, Wed, Thu, Fri, Sat, Sun) als lange (Maandag, Dinsdag, enz.) vormen.

### Aanvullende parser regels {#additional-parsing-rules}

Textuele datum parsing omvat verschillende nuttige functies:

- **Hoofdletterongevoelig:** Invoer zoals `MAANDAG 09/01/25`, `maandag 09/01/25`, of `Maandag 09/01/25` werkt allemaal op dezelfde manier.
- **Locale-bewust:** Maand- en dagnamen moeten overeenkomen met de geconfigureerde locale van het veld. Bijvoorbeeld, met een Franse locale, gebruik `septembre` en niet `September`. Engelse namen zullen niet worden herkend tenzij de locale is ingesteld op Engels.
  - Franse locale: `septembre/01/25` wordt erkend als September
  - Duitse locale: `Montag 09/01/25` wordt erkend met maandag als de dagnaam

## Instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt de toegestane datumbereik in een `MaskedDateField` beperken met de `setMin()` en `setMax()` methodes:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methodes accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Waarde herstellen {#restoring-the-value}

De `MaskedDateField` bevat een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke toestand. Dit is nuttig voor het terugdraaien van gebruikersinvoer of het resetten naar een standaarddatum.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatologisch**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard hersteltoets tenzij overschreven door een gebeurtenislistener)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`, waarbij je een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie doorgeeft.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met reguliere expressies met de `setPattern()` methode:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `MM/DD/YYYY` formaat (twee cijfers, schuine streep, twee cijfers, schuine streep, vier cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressie Formaat
Het patroon moet voldoen aan de JavaScript RegExp-syntaxis zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notities over Invoerverwerking
Het veld probeert numerieke datum invoer te parseren en formatteren op basis van het huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig is maar semantisch onjuist of onparseerbaar (bijv. `99/99/9999`), kan het patrooncontroles doorstaan maar logische validatie mislukken.
Je moet altijd de invoerwaarde in je applicatielogica valideren, zelfs als een reguliere expressiepatroon is ingesteld, om ervoor te zorgen dat de datum zowel correct geformatteerd als betekenisvol is.
::::

## Datumkiezer {#date-picker}

De `MaskedDateField` bevat een ingebouwde kalenderkiezer waarmee gebruikers een datum visueel kunnen selecteren in plaats van deze in te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt toegang krijgen tot de datumkiezer met `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Toon/verberg het kiezericoon {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het kalendericoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Auto-open gedrag {#auto-open-behavior}

Je kunt de kiezer zo configureren dat deze automatisch opent wanneer de gebruiker met het veld interageert (bijv. klikt, op Enter drukt of pijltjestoetsen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Handhaving van Selectie via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren met de kalenderkiezer (en geen handmatige invoer kunnen geven), combineer de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
dateField.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opzet garandeert dat alle datum invoer via de gebruikersinterface van de kiezer komt, wat nuttig is wanneer je strikte controle over het formaat wilt en parsingproblemen van ingetikte invoer wilt uitsluiten.
:::

### Handmatig de kalender openen {#manually-open-the-calendar}

Om de kalender programmatig te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### Toont weken in de kalender {#show-weeks-in-the-calendar}

De kiezer kan optioneel weeknummers in de kalenderweergave weergeven:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

De `MaskedDateFieldSpinner` breidt [`MaskedDateField`](#basics) uit door spinnerbesturingselementen toe te voegen waarmee gebruikers de datum kunnen verhogen of verlagen met de pijltjestoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktopachtige applicaties.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Belangrijkste kenmerken {#key-features}

- **Interactief Datum Stapelen:**  
  Gebruik pijltjestoetsen of draaiknoppen om de datumswaarde te verhogen of te verlagen.

- **Aanpasbare Stap Eenheid:**  
  Kies welk onderdeel van de datum moet worden aangepast met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties zijn `DAY`, `WEEK`, `MONTH`, en `YEAR`.

- **Min/Max Grenzen:**  
  Erf ondersteuning voor minimale en maximale toegestane datums met `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**  
  Volledig compatibel met maskers en lokalisatie instellingen van `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stapeling {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dit maakt elke draai-stap de datum met een week vooruit of achteruit.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
