---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: 433c612e16b0476f720deb896cb73f4a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

De `MaskedDateField` is een tekstinvoer waarmee gebruikers data als nummers kunnen invoeren en automatisch het invoerformaat op basis van een gedefinieerde masker wanneer het veld de focus verliest. De masker specificeert het verwachte datumformaat en leidt zowel invoer als weergave. De component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel voor consistente, regio-specifieke datumverwerking.

<!-- INTRO_END -->

## Basis {#basics}

:::tip Op zoek naar tijdinvoer?
De `MaskedDateField` richt zich uitsluitend op **datum** waarden. Als je een vergelijkbare component nodig hebt voor het invoeren en formatteren van **tijd**, kijk dan naar de [`MaskedTimeField`](./timefield).
:::

De `MaskedDateField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een placeholder en een gebeurtenishandler voor waarde wijzigingen definiëren.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Masker regels {#mask-rules}

De `MaskedDateField` ondersteunt meerdere datumformaten die wereldwijd worden gebruikt, die verschillen in de volgorde van dag, maand en jaar. Veelvoorkomende patronen zijn:

- **Dag/MAAND/Jaar** (gebruikt in de meeste delen van Europa)
- **MAAND/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/MAAND/Dag** (gebruikt in China, Japan en Korea; ook de ISO-standaard: `YYYY-MM-DD`)

Binnen deze formaten omvatten lokale variaties de keuze van scheidingsteken (bijv. `-`, `/`, of `.`), of jaren twee of vier cijfers zijn, en of enkelcijferige maanden of dagen worden aangevuld met leidende nullen.

Om met deze diversiteit om te gaan, gebruikt de `MaskedDateField` formaat indicatoren, die allemaal beginnen met `%`, gevolgd door een letter die een specifiek onderdeel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe de invoer wordt geparsed en hoe de datum wordt weergegeven.

:::tip Maskers programmatisch toepassen
Om data te formatteren of te parseren met dezelfde masker syntax buiten een veld, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility klasse.
:::

### Datum formaat indicatoren {#date-format-indicators}

| Formaat | Beschrijving |
| ------- | ------------ |
| `%Y`    | Jaar         |
| `%M`    | Maand        |
| `%D`    | Dag          |

### Modifiers {#modifiers}

Modifiers bieden meer controle over hoe componenten van de datum zijn geformatteerd:

| Modifier | Beschrijving               |
| -------- | --------------------------- |
| `z`      | Zero-fill                   |
| `s`      | Korte tekstrepresentatie    |
| `l`      | Lange tekstrepresentatie     |
| `p`      | Samengepakte nummer        |
| `d`      | Decimaal (standaardformaat) |

Deze kunnen worden gecombineerd om een breed scala aan datum maskers te bouwen.

## Datum formaat lokalisatie {#date-format-localization}

De `MaskedDateField` past zich aan regionale datumformaten aan door de juiste locale in te stellen. Dit zorgt ervoor dat data worden weergegeven en geparsed op een manier die overeenkomt met de verwachtingen van de gebruiker.

| Regio        | Formaat     | Voorbeeld      |
| ------------ | ----------- | -------------- |
| Verenigde Staten | MM/DD/YYYY | `07/04/2023` |
| Europa       | DD/MM/YYYY | `04/07/2023` |
| ISO Standaard| YYYY-MM-DD | `2023-07-04` |

Om lokalisatie toe te passen, gebruik de `setLocale()` methode. Het accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel formatting als parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing logica {#parsing-logic}

De `MaskedDateField` parseert gebruikersinvoer op basis van de gedefinieerde datum masker. Het accepteert zowel volledige als afgekorte numerieke invoer met of zonder scheidingstekens, wat flexibele invoer mogelijk maakt terwijl ervoor gezorgd wordt dat de data geldig zijn.
De parsing gedrag hangt af van de volgorde van het formaat dat door de masker is gedefinieerd (bijv. `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

Bijvoorbeeld, aangenomen dat vandaag `15 september 2012` is, zo zouden verschillende invoeren worden geïnterpreteerd:

### Voorbeelden scenarioparseren {#example-parsing-scenarios}

| Invoer                                | YMD (ISO)                                                                                                                                                                                           | MDY (VS)                                                                            | DMY (EU)                                                                                                                     |
| -------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>         | Een enkel cijfer wordt altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                                  | Hetzelfde als YMD                                                                     | Hetzelfde als YMD                                                                                                          |
| <div align="center">`12`</div>        | Twee cijfers worden altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                                | Hetzelfde als YMD                                                                     | Hetzelfde als YMD                                                                                                          |
| <div align="center">`112`</div>       | Drie cijfers worden geïnterpreteerd als een 1-cijferige maand nummer gevolgd door een 2-cijferige dagnummer, dus dit zou 12 januari 2012 zijn.                                                 | Hetzelfde als YMD                                                                     | Drie cijfers worden geïnterpreteerd als een 1-cijferige dag nummer gevolgd door een 2-cijferige maand nummer, dus dit zou 1 december 2012 zijn. |
| <div align="center">`1004`</div>      | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                               | Hetzelfde als YMD                                                                     | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                              |
| <div align="center">`020304`</div>    | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                               | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.     | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                             |
| <div align="center">`8 cijfers`</div>  | Acht cijfers worden geïnterpreteerd als YYYYMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                | Acht cijfers worden geïnterpreteerd als MMDDYYYY. Bijvoorbeeld, `06122004` is 12 juni 2004. | Acht cijfers worden geïnterpreteerd als DDMMYYYY. Bijvoorbeeld, `06122004` is 6 december 2004.                            |
| <div align="center">`12/6`</div>      | Twee nummers gescheiden door een geldig scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle tekens behalve letters en cijfers worden als geldige scheidingstekens beschouwd. | Hetzelfde als YMD                                                                     | Twee nummers gescheiden door een scheidingsteken wordt geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.            |
| <div align="center">`3/4/5`</div>     | 5 april 2012                                                                                                                                                                                     | 4 maart 2005                                                                      | 3 april 2005                                                                                                               |


## Tekstuele datum parsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standaard accepteert de `MaskedDateField` alleen numerieke input voor data. Je kunt echter **tekstuele datum parsing** inschakelen om gebruikers toe te staan maand- en dagnamen in hun invoer op te nemen. Deze functie is bijzonder nuttig voor het creëren van een natuurlijkere datuminvoer.

Om tekstuele parsing in te schakelen, gebruik de `setTextualDateParsing()` methode:

```java
dateField.setTextualDateParsing(true);
```

### Vervangingen maandnaam {#month-name-substitution}

Wanneer tekstuele parsing is ingeschakeld, kun je speciale modifiers in je masker gebruiken om maandnamen in plaats van numerieke waarden te accepteren:

- **`%Ms`** - Accepteert korte maandnamen (Jan, Feb, Mar, enz.)
- **`%Ml`** - Accepteert lange maandnamen (Januari, Februari, Maart, enz.)

Maandnamen kunnen op elke positie binnen de masker verschijnen, en het veld accepteert nog steeds numerieke invoer als fallback.

#### Voorbeelden {#examples}

| Masker | Invoer | Resultaat |
| ------ | ------ | --------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Geldig** - Numerieke fallback werkt nog steeds |

:::info
Alle 12 maanden worden ondersteund in zowel korte (Jan, Feb, Mar, Apr, Mei, Jun, Jul, Aug, Sep, Okt, Nov, Dec) als lange (Januari, Februari, enz.) vormen.
:::
### Decoratie van dagnaam {#day-name-decoration}

Dagen van de week namen kunnen worden opgenomen in de invoer voor betere leesbaarheid, maar zijn **alleen decoratief** en worden tijdens parsing verwijderd. Ze beïnvloeden de werkelijke datumwaarde niet.

- **`%Ds`** - Accepteert korte dagnamen (Ma, Di, Wo, enz.)
- **`%Dl`** - Accepteert lange dagnamen (Maandag, Dinsdag, Woensdag, enz.)

:::warning Dag Namen Vereisen Numerieke Dag
Bij gebruik van dag-van-de-week namen (`%Ds` of `%Dl`), moet je masker **ook** `%Dz` of `%Dd` bevatten om het werkelijke dagnummer aan te geven. Zonder een numerieke dagcomponent is de invoer ongeldig.
:::

#### Voorbeelden {#examples-1}

| Masker | Invoer | Resultaat |
| ------ | ------ | --------- |
| `%Ds %Mz/%Dz/%Yz` | `Ma 09/01/25` | **Geldig** - Dagnaam is decoratief |
| `%Dl %Mz/%Dz/%Yz` | `Maandag 09/01/25` | **Geldig** - Dagnaam is decoratief |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Di` | **Geldig** - Dagnaam aan het einde |
| `%Dl/%Mz/%Yz` | `Maandag/09/25` | **Ongeldig** - Ontbrekend `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Maandag/25` | **Ongeldig** - Ontbrekend `%Dz` |

Alle 7 weekdagen worden ondersteund in zowel korte (Ma, Di, Wo, Do, Vr, Za, Zo) als lange (Maandag, Dinsdag, enz.) vormen.

### Aanvullende parsing regels {#additional-parsing-rules}

Tekstuele datum parsing omvat verschillende handige functies:

- **Hoofdletterongevoelig:** Invoer zoals `MAANDAG 09/01/25`, `maandag 09/01/25`, of `Maandag 09/01/25` werkt allemaal op dezelfde manier.
- **Locale-bewust:** Maand- en dagnamen moeten overeenkomen met de geconfigureerde locale van het veld. Bijvoorbeeld, met een Franse locale, gebruik `septembre` en niet `September`. Engelse namen worden niet herkend tenzij de locale is ingesteld op Engels.
  - Franse locale: `septembre/01/25` wordt herkend als september
  - Duitse locale: `Montag 09/01/25` wordt herkend met maandag als de dagnaam

## Min/max beperkingen instellen {#setting-minmax-constraints}

Je kunt het toegestane datumbereik in een `MaskedDateField` beperken met behulp van de `setMin()` en `setMax()` methoden:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methoden accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## De waarde herstellen {#restoring-the-value}

De `MaskedDateField` bevat een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. Dit is nuttig om gebruikersinvoer terug te draaien of terug te zetten naar een standaard datum.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmeren**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaard hersteltoets tenzij overschreven door een gebeurtenishandler)

Je kunt de waarde die je wilt herstellen instellen met `setRestoreValue()`, door een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie te doorgeven.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validatie patronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met reguliere expressies met de `setPattern()` methode:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `MM/DD/YYYY` formaat (twee cijfers, schuine streep, twee cijfers, schuine streep, vier cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressie Formaat
Het patroon moet de JavaScript RegExp syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over Invoerafhandeling
Het veld probeert numerieke datum invoer te parseren en te formatteren op basis van de huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig is maar semantisch ongeldig of niet parseerbaar (bijv. `99/99/9999`), kan het patroon controles doorstaan maar logische validatie falen.
Je zou altijd de invoerwaarde in je app-logica moeten valideren, zelfs als een regulier expressiepunt is ingesteld, om ervoor te zorgen dat de datum zowel correct is geformatteerd als betekenisvol.
::::

## Datumkiezer {#date-picker}

De `MaskedDateField` bevat een ingebouwde kalenderkiezer waarmee gebruikers visueel een datum kunnen selecteren, in plaats van deze te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

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

### Toon/verberg het kiesericoon {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het kalendicoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker interactie heeft met het veld (bijv. klikt, op Enter of pijltjestoetsen drukt):

```java
picker.setAutoOpen(true);
```

:::tip Dwing Selectie Via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren met behulp van de kalenderkiezer (en niet handmatig een datum kunnen invoeren), combineer je de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // opent de kiezer bij gebruikersinteractie
dateField.setAllowCustomValue(false);    // schakelt handmatige tekstinvoer uit
```

Deze setup garandeert dat alle datum invoer via de gebruikersinterface van de kiezer komt, wat nuttig is wanneer je strikte formatcontrole wilt en parsingproblemen van getypte invoer wilt elimineren.
:::

### Handmatig de kalender openen {#manually-open-the-calendar}

Om de kalender programmeermatg te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### Bekijk weken in de kalender {#show-weeks-in-the-calendar}

De kiezer kan optioneel weeknummers in de kalenderweergave weergeven:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

De `MaskedDateFieldSpinner` breidt de [`MaskedDateField`](#basics) uit door spinnerbesturingselementen toe te voegen waarmee gebruikers de datum kunnen verhogen of verlagen met behulp van pijltjestoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktop-achtige applicaties.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Sleutelkenmerken {#key-features}

- **Interactieve Datum Stappen:**
  Gebruik pijltjestoetsen of draaiknoppen om de datumwaarde te verhogen of te verlagen.

- **Aanpasbare Stap Eenheid:**
  Kies welk onderdeel van de datum je wilt wijzigen met behulp van `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties omvatten `DAY`, `WEEK`, `MONTH` en `YEAR`.

- **Min/Max Grenzen:**
  Neemt ondersteuning voor minimum en maximum toegestane data over met behulp van `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stappen {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dit zorgt ervoor dat elke stap de datum met een week vooruit of achteruit verschuift.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
