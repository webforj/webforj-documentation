---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 5bd41c7d02fb7ae0c934db0a4e2ffb60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

De `MaskedDateField` is een tekstinvoerveld waarmee gebruikers datums als cijfers kunnen invoeren en die automatisch het invoerformaat op basis van een gedefinieerde maskering formatteren wanneer het veld de focus verliest. De maskering specificeert het verwachte datumformaat, wat de invoer en weergave begeleidt. De component ondersteunt flexibele parsing, validatie, lokalisatie en herstel van waarden voor consistente, regio-specifieke datumverwerking.

<!-- INTRO_END -->

## Basis {#basics}

:::tip Op zoek naar tijdinvoer?
De `MaskedDateField` richt zich uitsluitend op **datum** waarden. Als je een vergelijkbare component nodig hebt voor het invoeren en formatteren van **tijd**, kijk dan in de [`MaskedTimeField`](./timefield).
:::

De `MaskedDateField` kan worden geïnstantieerd met of zonder parameters. Je kunt een beginwaarde, een label, een tijdelijke aanduiding en een gebeurtenislijsten voor waarde veranderingen definiëren.

<ComponentDemo
path='/webforj/maskeddatefield'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java']}
height='120px'
/>

## Maskerregels {#mask-rules}

De `MaskedDateField` ondersteunt meerdere datumformaten die wereldwijd worden gebruikt, die verschillen op basis van de volgorde van dag, maand en jaar. Veelvoorkomende patronen zijn onder andere:

- **Dag/Maand/Jaar** (gebruikt in de meeste delen van Europa)
- **Maand/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/Maand/Dag** (gebruikt in China, Japan en Korea; ook de ISO-standaard: `YYYY-MM-DD`)

Binnen deze formaten omvatten lokale variaties de keuze van scheidingstekens (bijv., `-`, `/`, of `.`), of jaren uit twee of vier cijfers bestaan, en of enkele cijfers voor maanden of dagen worden aangevuld met voorloop nullen.

Om met deze diversiteit om te gaan, gebruikt de `MaskedDateField` formaatindicatoren, die elk beginnen met `%`, gevolgd door een letter die een specifiek onderdeel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe invoer wordt geparsed en hoe de datum wordt weergegeven.

:::tip Maskers programatisch toepassen
Om datums met dezelfde maskersyntaxis buiten een veld te formatteren of te parseren, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) hulpprogramma klasse.
:::

### Datumformaatindicatoren {#date-format-indicators}

| Formaat | Beschrijving |
| ------- | ------------ |
| `%Y`    | Jaar         |
| `%M`    | Maand        |
| `%D`    | Dag          |

### Modifiers {#modifiers}

Modifiers bieden meer controle over hoe onderdelen van de datum worden geformatted:

| Modifier | Beschrijving                  |
| -------- | ----------------------------- |
| `z`      | Zero-fill                    |
| `s`      | Korte tekstrepresentatie      |
| `l`      | Lange tekstrepresentatie      |
| `p`      | Samengevoegd nummer          |
| `d`      | Decimaal (standaardformaat)  |

Deze kunnen worden gecombineerd om een breed scala aan datum maskers te bouwen.

## Lokalisatie van datumformaten {#date-format-localization}

De `MaskedDateField` past zich aan regionale datumformaten aan door de juiste locale in te stellen. Dit zorgt ervoor dat datums worden weergegeven en geparsed op een manier die aansluit bij de verwachtingen van de gebruiker.

| Regio        | Formaat     | Voorbeeld      |
| ------------ | ----------- | -------------- |
| Verenigde Staten | MM/DD/YYYY | `07/04/2023` |
| Europa       | DD/MM/YYYY | `04/07/2023` |
| ISO Standaard | YYYY-MM-DD | `2023-07-04` |

Om lokalisatie toe te passen, gebruik de `setLocale()` methode. Het accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel formattering als parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsinglogica {#parsing-logic}

De `MaskedDateField` parseert gebruikersinvoer op basis van de gedefinieerde datum maskering. Het accepteert zowel volledige als verkorte numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is terwijl ervoor wordt gezorgd dat datums geldig zijn. De parsinggedrag hangt af van de format order die door de maskering is gedefinieerd (bijv., `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

Bijvoorbeeld, aangenomen dat vandaag `15 september 2012` is, zou dit de manier zijn waarop verschillende invoeren zouden worden geïnterpreteerd:

### Voorbeeld parsingscenario's {#example-parsing-scenarios}

| Invoer                                | YMD (ISO)                                                                                                                                                                                        | MDY (VS)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Een enkel cijfer wordt altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                                  | Hetzelfde als YMD                                                                     | Hetzelfde als YMD                                                                                                        |
| <div align="center">`12`</div>       | Twee cijfers worden altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                                  | Hetzelfde als YMD                                                                     | Hetzelfde als YMD                                                                                                        |
| <div align="center">`112`</div>      | Drie cijfers worden geïnterpreteerd als een 1-cijferige maandnummer gevolgd door een 2-cijferig dagnummer, dus dit zou 12 januari 2012 zijn.                                                                          | Hetzelfde als YMD                                                                     | Drie cijfers worden geïnterpreteerd als een 1-cijferig dagnummer gevolgd door een 2-cijferig maandnummer, dus dit zou 1 december 2012 zijn. |
| <div align="center">`1004`</div>     | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                              | Hetzelfde als YMD                                                                     | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                         |
| <div align="center">`020304`</div>   | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                               | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.          | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                           |
| <div align="center">`8 cijfers`</div> | Acht cijfers worden geïnterpreteerd als YYYYMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                  | Acht cijfers worden geïnterpreteerd als MMDDYYYY. Bijvoorbeeld, `06122004` is 12 juni 2004. | Acht cijfers worden geïnterpreteerd als DDMMYYYY. Bijvoorbeeld, `06122004` is 6 december 2004.                           |
| <div align="center">`12/6`</div>     | Twee nummers gescheiden door een geldig scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle tekens behalve letters en cijfers worden als geldige scheidingstekens beschouwd. | Hetzelfde als YMD                                                                     | Twee nummers gescheiden door een scheidingsteken worden geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.       |
| <div align="center">`3/4/5`</div>    | 5 april 2012                                                                                                                                                                                      | 4 maart 2005                                                                        | 3 april 2005                                                                                                           |

## Tekstuele datum parsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standaard accepteert de `MaskedDateField` alleen numerieke invoer voor datums. Je kunt echter **tekstuele datum parsing** inschakelen om gebruikers in staat te stellen maand- en dagnamen in hun invoer in te voeren. Deze functie is vooral handig om meer natuurlijke datuminvoer te creëren.

Om tekstuele parsing in te schakelen, gebruik de `setTextualDateParsing()` methode:

```java
dateField.setTextualDateParsing(true);
```

### Substitutie van maandnamen {#month-name-substitution}

Wanneer tekstuele parsing is ingeschakeld, kun je speciale modifiers in je masker gebruiken om maandnamen in plaats van numerieke waarden te accepteren:

- **`%Ms`** - Accepteert korte maandnamen (Jan, Feb, Mar, enz.)
- **`%Ml`** - Accepteert lange maandnamen (Januari, Februari, Maart, enz.)

Maandnamen kunnen op elke positie binnen de maskering verschijnen en het veld accepteert nog steeds numerieke invoer als fallback.

#### Voorbeelden

| Masker | Invoer | Resultaat |
| ------ | ------ | --------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Geldig** - Numerieke fallback werkt nog steeds |

:::info
Alle 12 maanden worden ondersteund in zowel korte (Jan, Feb, Mar, Apr, Mei, Jun, Jul, Aug, Sep, Okt, Nov, Dec) als lange (Januari, Februari, enz.) vormen.
:::

### Decoratie van dagnamen {#day-name-decoration}

Dagnamen van de week kunnen in de invoer worden opgenomen voor betere leesbaarheid, maar ze zijn **slechts decoratief** en worden tijdens het parsen verwijderd. Ze beïnvloeden de daadwerkelijke datumwaarde niet.

- **`%Ds`** - Accepteert korte dagnamen (Ma, Di, Wo, enz.)
- **`%Dl`** - Accepteert lange dagnamen (Maandag, Dinsdag, Woensdag, enz.)

:::warning Dagnamen vereisen numerieke dag
Wanneer je dagnamen van de week gebruikt (`%Ds` of `%Dl`), moet je masker **ook** `%Dz` of `%Dd` bevatten om het feitelijke dagnummer te specificeren. Zonder een numeriek dagcomponent is de invoer ongeldig.
:::

#### Voorbeelden

| Masker | Invoer | Resultaat |
| ------ | ------ | --------- |
| `%Ds %Mz/%Dz/%Yz` | `Ma 09/01/25` | **Geldig** - Dagnaam is decoratief |
| `%Dl %Mz/%Dz/%Yz` | `Maandag 09/01/25` | **Geldig** - Dagnaam is decoratief |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Di` | **Geldig** - Dagnaam aan het einde |
| `%Dl/%Mz/%Yz` | `Maandag/09/25` | **Ongeldig** - `%Dz` ontbreekt |
| `%Mz/%Dl/%Yz` | `09/Maandag/25` | **Ongeldig** - `%Dz` ontbreekt |

Alle 7 weekdagen worden ondersteund in zowel korte (Ma, Di, Wo, Do, Vr, Za, Zo) als lange (Maandag, Dinsdag, enz.) vormen.

### Aanvullende parsingregels {#additional-parsing-rules}

Tekstuele datum parsing omvat verschillende handige functies:

- **Hoofdletterongevoelig:** Invoer zoals `MAANDAG 09/01/25`, `maandag 09/01/25`, of `Maandag 09/01/25` werkt allemaal hetzelfde.
- **Locale-bewust:** Maand- en dagnamen moeten overeenkomen met de geconfigureerde locale van het veld. Gebruik bijvoorbeeld met een Franse locale `septembre` in plaats van `September`. Engelse namen worden niet herkend, tenzij de locale is ingesteld op Engels.
  - Franse locale: `septembre/01/25` wordt herkend als september
  - Duitse locale: `Montag 09/01/25` wordt herkend met maandag als de dagnamen

## Het instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane datumbereik in een `MaskedDateField` beperken met behulp van de `setMin()` en `setMax()` methoden:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methoden accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Het herstellen van de waarde {#restoring-the-value}

De `MaskedDateField` bevat een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde of originele staat. Dit is nuttig voor het terugdraaien van gebruikersinvoer of het resetten naar een standaarddatum.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Wijzen om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatig**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard hersteltoets tenzij overschreven door een gebeurtenislijsten)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`, met een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie.

<ComponentDemo
path='/webforj/maskeddatefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java']}
height='120px'
/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met behulp van reguliere expressies met de `setPattern()` methode:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `MM/DD/YYYY` formaat (twee cijfers, schuine streep, twee cijfers, schuine streep, vier cijfers) als geldig worden beschouwd.

:::tip Formaat van Reguliere Expressie
Het patroon moet de JavaScript RegExp-syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Aantekeningen over Invoerafhandeling
Het veld probeert numerieke datuminvoer te parseren en te formatteren op basis van de huidige maskering. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig maar semantisch onjuist of niet parseerbaar is (bijv. `99/99/9999`), kan deze voldoen aan patrooncontroles maar falen in logische validatie.
Je moet altijd de invoerwaarde in je applicatielogica valideren, zelfs als er een regulier expressiepatroon is ingesteld, om ervoor te zorgen dat de datum zowel correct is geformatted als betekenisvol.
::::

## Datumkiezer {#date-picker}

De `MaskedDateField` bevat een ingebouwde kalenderkiezer waarmee gebruikers visueel een datum kunnen selecteren in plaats van deze in te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

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

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker interactie heeft met het veld (bijv. klikt, op Enter drukt of pijltjestoetsen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Afgedwongen selectie via de kiezer
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren met behulp van de kalenderkiezer (en niet handmatig een datum kunnen invoeren), combineer dan de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
dateField.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze setup garandeert dat alle datum invoer via de kiezer UI komt, wat nuttig is wanneer je strikte formatcontrole wilt en parserproblemen van getypte invoer wilt uitsluiten.
:::

### Handmatig de kalender openen {#manually-open-the-calendar}

Om de kalender programatisch te openen:

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

De `MaskedDateFieldSpinner` breidt [`MaskedDateField`](#basics) uit door spinnerbedieningen toe te voegen waarmee gebruikers de datum kunnen verhogen of verlagen met behulp van pijltjestoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktopstijltoepassingen.

<ComponentDemo
path='/webforj/maskeddatefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java']}
height='450px'
/>

### Belangrijke kenmerken {#key-features}

- **Interactief Datum Stapelen:**  
  Gebruik pijltjestoetsen of spindknoppen om de datumwaarde te verhogen of verlagen.

- **Aanpasbare stap-eenheid:**  
  Kies welk onderdeel van de datum je wilt wijzigen met behulp van `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties omvatten `DAY`, `WEEK`, `MONTH` en `YEAR`.

- **Min/Max grenzen:**  
  Erft ondersteuning voor minimum en maximum toegestane datums met behulp van `setMin()` en `setMax()`.

- **Geformatteerde uitvoer:**  
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stappen {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dit zorgt ervoor dat elke spinstap de datum met een week vooruit of achteruit gaat.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
