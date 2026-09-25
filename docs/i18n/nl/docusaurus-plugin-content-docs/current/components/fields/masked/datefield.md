---
title: MaskedDateField
sidebar_position: 5
description: >-
  Capture localized date input with the MaskedDateField, applying configurable
  masks, format indicators, parsing rules, and validation.
_i18n_hash: d63b5c4325ef201b54da5b78b4e66f1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Het `MaskedDateField` is een tekstinvoer waarmee gebruikers datums als getallen kunnen invoeren en de invoer automatisch wordt opgemaakt op basis van een gedefinieerde maskering wanneer het veld de focus verliest. De maskering specificeert het verwachte datumformaat en leidt zowel de invoer als de weergave. De component ondersteunt flexibele parsing, validatie, lokalisatie en het herstel van waarden voor consistente, regio-specifieke datuman handling.

<!-- INTRO_END -->

:::tip Op zoek naar tijdinvoer?
Het `MaskedDateField` is uitsluitend gericht op **datum** waarden. Als je een vergelijkbare component nodig hebt voor het invoeren en opmaken van **tijd**, kijk dan in de [`MaskedTimeField`](/docs/components/fields/masked/timefield).
:::

Het `MaskedDateField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een plaatsaanduiding en een eventlistener voor waarde wijzigingen definiëren.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskregels {#mask-rules}

Het `MaskedDateField` ondersteunt meerdere datumformaten die over de hele wereld worden gebruikt, die variëren op basis van de volgorde van dag, maand en jaar. Veelvoorkomende patronen zijn:

- **Dag/Maand/Jaar** (gebruikt in het grootste deel van Europa)
- **Maand/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/Maand/Dag** (gebruikt in China, Japan en Korea; ook de ISO-standaard: `YYYY-MM-DD`)

Binnen deze formaten omvatten lokale variaties de keuze van scheidingstekens (bijv. `-`, `/`, of `.`), of jaren twee of vier cijfers zijn, en of enkelvoudige maanden of dagen zijn gevuld met voorloopnullen.

Om met deze diversiteit om te gaan, gebruikt het `MaskedDateField` opmaakindicatoren, die beginnen met `%`, gevolgd door een letter die een specifiek onderdeel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe invoer wordt geparsed en hoe de datum wordt weergegeven.

:::tip Maskers programmatisch toepassen
Om datums te formatteren of te parseren met dezelfde maskersyntax buiten een veld, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) hulpkklasse.
:::

### Datumformaatindicatoren {#date-format-indicators}

| Formaat | Beschrijving |
| ------ | ----------- |
| `%Y`   | Jaar        |
| `%M`   | Maand       |
| `%D`   | Dag         |

### Modifiers {#modifiers}

Modifiers bieden meer controle over hoe componenten van de datum worden opgemaakt:

| Modifier | Beschrijving               |
| -------- | ------------------------- |
| `z`      | Zero-fill                 |
| `s`      | Korte tekstweergave      |
| `l`      | Lange tekstweergave      |
| `p`      | Samengevoegd getal       |
| `d`      | Decimaal (standaardformaat)  |

Deze kunnen worden gecombineerd om een grote verscheidenheid aan datummaskers te bouwen.

## Lokalisatie van datumformaten {#date-format-localization}

Het `MaskedDateField` past zich aan regionale datumformaten aan door de juiste locale in te stellen. Dit zorgt ervoor dat datums worden weergegeven en geparsed op een manier die overeenkomt met de verwachtingen van de gebruiker.

| Regio        | Formaat     | Voorbeeld      |
| ------------- | ---------- | ------------ |
| Verenigde Staten | MM/DD/YYYY | `07/04/2023` |
| Europa        | DD/MM/YYYY | `04/07/2023` |
| ISO Standaard  | YYYY-MM-DD | `2023-07-04` |

Om lokalisatie toe te passen, gebruik de `setLocale()` methode. Deze accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel formattering als parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsinglogica {#parsing-logic}

Het `MaskedDateField` parseert gebruikersinvoer op basis van de gedefinieerde datummasker. Het accepteert zowel volledige als verkorte numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is, terwijl geldige datums worden gegarandeerd. Het parseren hangt af van de volgorde van het formaat die door de maskering is gedefinieerd (bijv. `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

Bijvoorbeeld, aangenomen dat vandaag `15 september 2012` is, is dit hoe verschillende invoeren zouden worden geïnterpreteerd:

### Voorbeeld parsingscenario's {#example-parsing-scenarios}

| Invoer                                | YMD (ISO)                                                                                                                                                                                          | MDY (VS)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Een enkel cijfer wordt altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                                 | Hetzelfde als YMD                                                                         | Hetzelfde als YMD                                                                                                                  |
| <div align="center">`12`</div>       | Twee cijfers worden altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                                   | Hetzelfde als YMD                                                                         | Hetzelfde als YMD                                                                                                                  |
| <div align="center">`112`</div>      | Drie cijfers worden geïnterpreteerd als een 1-cijferig maandnummer gevolgd door een 2-cijferig dagnummer, dus dit zou 12 januari 2012 zijn.                                                                        | Hetzelfde als YMD                                                                         | Drie cijfers worden geïnterpreteerd als een 1-cijferig dagnummer gevolgd door een 2-cijferig maandnummer, zodat dit 1 december 2012 zou zijn. |
| <div align="center">`1004`</div>     | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                                             | Hetzelfde als YMD                                                                         | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                                         |
| <div align="center">`020304`</div>   | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                                              | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.            | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                                         |
| <div align="center">`8 cijfers`</div> | Acht cijfers worden geïnterpreteerd als YYYYMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                                | Acht cijfers worden geïnterpreteerd als MMDDYYYY. Bijvoorbeeld, `06122004` is 12 juni 2004. | Acht cijfers worden geïnterpreteerd als DDMMYYYY. Bijvoorbeeld, `06122004` is 6 december 2004.                                        |
| <div align="center">`12/6`</div>     | Twee getallen gescheiden door een geldig scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle karakters behalve letters en cijfers worden als geldige scheidingstekens beschouwd. | Hetzelfde als YMD                                                                         | Twee getallen gescheiden door een scheidingsteken worden geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.                               |
| <div align="center">`3/4/5`</div>    | 5 april 2012                                                                                                                                                                                      | 4 maart 2005                                                                       | 3 april 2005                                                                                                                 |


## Textuele datum parsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standaard accepteert het `MaskedDateField` alleen numerieke invoer voor datums. Je kunt echter **tekstuele datumparsing** inschakelen om gebruikers toe te staan maand- en dagnamen in hun invoer in te voeren. Deze functie is bijzonder nuttig voor het creëren van natuurlijkere datuminvoer.

Om tekstuele parsing in te schakelen, gebruik de `setTextualDateParsing()` methode:

```java
dateField.setTextualDateParsing(true);
```

### Vervanging van maandnamen {#month-name-substitution}

Wanneer tekstuele parsing is ingeschakeld, kun je speciale modifiers in je masker gebruiken om maandnamen in plaats van numerieke waarden te accepteren:

- **`%Ms`** - Accepteert korte maandnamen (Jan, Feb, Mrt, etc.)
- **`%Ml`** - Accepteert lange maandnamen (Januari, Februari, Maart, etc.)

Maandnamen kunnen op elke positie binnen het masker verschijnen, en het veld accepteert nog steeds numerieke invoer als fallback.

#### Voorbeelden {#examples}

| Masker | Invoer | Resultaat |
| ---- | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Geldig** - Parseert als 1 september 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Geldig** - Parseert als 1 september 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Geldig** - Parseert als 1 september 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Geldig** - Numerieke fallback werkt nog steeds |

:::info
Alle 12 maanden worden ondersteund in zowel korte (Jan, Feb, Mrt, Apr, Mei, Jun, Jul, Aug, Sep, Okt, Nov, Dec) als lange (Januari, Februari, enz.) vormen.
:::
### Decoratie van dagnamen {#day-name-decoration}

Dagen-van-de-weeknamen kunnen in de invoer worden opgenomen voor betere leesbaarheid, maar ze zijn **alleen decoratief** en worden tijdens het parseren verwijderd. Ze hebben geen invloed op de daadwerkelijke datumwaarde.

- **`%Ds`** - Accepteert korte dagnamen (Ma, Di, Wo, enz.)
- **`%Dl`** - Accepteert lange dagnamen (Maandag, Dinsdag, Woensdag, enz.)

:::warning Dagnaam vereist numerieke dag
Wanneer je dag-van-de-weeknamen gebruikt (`%Ds` of `%Dl`), moet je masker **ook** `%Dz` of `%Dd` bevatten om het daadwerkelijke dagnummer te specificeren. Zonder een numeriek dagcomponent is de invoer ongeldig.
:::

#### Voorbeelden {#examples-1}

| Masker | Invoer | Resultaat |
| ---- | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Ma 09/01/25` | **Geldig** - Dagmaand is decoratief |
| `%Dl %Mz/%Dz/%Yz` | `Maandag 09/01/25` | **Geldig** - Dagmaand is decoratief |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Di` | **Geldig** - Dagmaand aan het einde |
| `%Dl/%Mz/%Yz` | `Maandag/09/25` | **Ongeldig** - Ontbrekende `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Maandag/25` | **Ongeldig** - Ontbrekende `%Dz` |

Alle 7 weekdagen worden ondersteund in zowel korte (Ma, Di, Wo, Do, Vr, Za, Zo) als lange (Maandag, Dinsdag, enz.) vormen.

### Aanvullende parserregels {#additional-parsing-rules}

Tekstuele datumparsing omvat verschillende handige functies:

- **Hoofdletterongevoelig:** Invoer zoals `MAANDAG 09/01/25`, `maandag 09/01/25` of `Maandag 09/01/25` werkt allemaal op dezelfde manier.
- **Locale-gevoelig:** Maand- en dagnamen moeten overeenkomen met de geconfigureerde locale van het veld. Bijvoorbeeld, met een Franse locale gebruik je `septembre` in plaats van `September`. Engelse namen worden niet herkend tenzij de locale is ingesteld op Engels.
  - Franse locale: `septembre/01/25` wordt herkend als September
  - Duitse locale: `Montag 09/01/25` wordt herkend met maandag als dagnaam

## Instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane daterange in een `MaskedDateField` beperken met de methodes `setMin()` en `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methoden accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Herstellen van de waarde {#restoring-the-value}

Het `MaskedDateField` bevat een herstelfeature die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. Dit is nuttig voor het terugdraaien van gebruikersinvoer of het resetten naar een standaarddatum.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatisch**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaardhersteltoets, tenzij overschreven door een eventlistener)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`, waarbij je een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie doorgeeft.

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

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het formaat `MM/DD/YYYY` (twee cijfers, schuine streep, twee cijfers, schuine streep, vier cijfers) als geldig worden beschouwd.

:::tip Formaat van reguliere expressies
Het patroon moet de JavaScript RegExp-syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over invoerhandling
Het veld probeert numerieke datuminvoer te parseren en te formatteren op basis van het huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig maar semantisch onjuist of onparseerbaar is (bijv. `99/99/9999`), kan deze voldoen aan patrooncontroles maar mislukken bij logische validatie.
Je zou altijd de invoerwaarde in je app-logica moeten valideren, zelfs als er een reguliere expressiepater wordt ingesteld, om ervoor te zorgen dat de datum zowel correct is opgemaakt als zinvol.
::::

## Datumkiezer {#date-picker}

Het `MaskedDateField` bevat een ingebouwde kalenderkiezer waarmee gebruikers visueel een datum kunnen selecteren, in plaats van deze te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo
path='/webforj/maskeddatefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java']}
height='450px'
/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt de datumkiezer bereiken met `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Toon/verberg het pickericoon {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het kalendericoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Automatisch open gedrag {#auto-open-behavior}

Je kunt de kiezer zo configureren dat deze automatisch opent wanneer de gebruiker met het veld interacteert (bijv. klikt, op Enter drukt of pijlen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Dwing Selectie via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren met behulp van de kalenderkiezer (en niet handmatig eentje typen), combineer je de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
dateField.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opstelling garandeert dat alle datuminvoer via de kiezerinterface komt, wat nuttig is wanneer je strikte formaatcontrole wilt en parserproblemen van getypte invoer wilt uitsluiten.
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

De kiezer kan optioneel weeknummers in de kalenderweergave tonen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

De `MaskedDateFieldSpinner` breidt de `MaskedDateField` uit door spinhulpmiddelen toe te voegen waarmee gebruikers de datum kunnen verhogen of verlagen met de pijlen of UI-knoppen. Het biedt een meer begeleide interactiestijl, vooral nuttig in desktopachtige toepassingen.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Belangrijkste kenmerken {#key-features}

- **Interactief Datuum Stapelen:**
  Gebruik pijlen of spin-knoppen om de datum waarde te verhogen of te verlagen.

- **Aanpasbare Stap Eenheid:**
  Kies welk onderdeel van de datum je wilt aanpassen met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties zijn `DAG`, `WEKEN`, `MAAND` en `JAAR`.

- **Min/Max Grenswaarden:**
  Bevat ondersteuning voor minimum en maximum toegestane datums met `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stapeling {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Hierdoor gaat elke spinstap de datum met een week vooruit of achteruit.

## Stijlen {#styling}

<TableBuilder name="MaskedDateField" />
