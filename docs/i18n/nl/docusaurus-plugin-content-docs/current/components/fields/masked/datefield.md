---
title: MaskedDateField
sidebar_position: 5
sidebar_class_name: updated-content
_i18n_hash: 981d5cd2686c83144433a0135b1222dc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

De `MaskedDateField` is een tekstinvoer waarmee gebruikers data als getallen kunnen invoeren en die automatisch de invoer opmaakt op basis van een gedefinieerde masker wanneer het veld focus verliest. Het masker specificeert het verwachte datumformaat en leidt zowel invoer als weergave. De component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel voor consistente, regionspecifieke datumverwerking.

<!-- INTRO_END -->

## Basis {#basics}

:::tip Op zoek naar tijdinvoer?
De `MaskedDateField` richt zich uitsluitend op **datum** waarden. Als je een vergelijkbare component nodig hebt voor het invoeren en formatteren van **tijd**, kijk dan in de [`MaskedTimeField`](./timefield).
:::

De `MaskedDateField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een placeholder en een gebeurtenisluisteraar voor waarde wijzigingen definiëren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskerregels {#mask-rules}

De `MaskedDateField` ondersteunt meerdere datumformaten die wereldwijd worden gebruikt, die variëren in de volgorde van dag, maand en jaar. Gemeenschappelijke patronen zijn onder andere:

- **Dag/Maand/Jaar** (gebruikt in de meeste Europese landen)
- **Maand/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/Maand/Dag** (gebruikt in China, Japan en Korea; ook de ISO-standaard: `YYYY-MM-DD`)

Binnen deze formaten omvatten lokale variaties de keuze van scheidingstekens (bijv. `-`, `/`, of `.`), of jaren uit twee of vier cijfers bestaan, en of enkelcijferige maanden of dagen worden gepad met voorloopnullen.

Om met deze diversiteit om te gaan, gebruikt de `MaskedDateField` formatenindicatoren, die elk beginnen met `%`, gevolgd door een letter die een specifiek deel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe invoer wordt geparsed en hoe de datum wordt weergegeven.

### Datumformaatindicatoren {#date-format-indicators}

| Formaat | Beschrijving |
| ------- | ------------ |
| `%Y`    | Jaar         |
| `%M`    | Maand        |
| `%D`    | Dag          |

### Modifiers {#modifiers}

Modifiers stellen je in staat om meer controle te hebben over hoe componenten van de datum zijn opgemaakt:

| Modifier | Beschrijving                 |
| -------- | ---------------------------- |
| `z`      | Zero-fill                    |
| `s`      | Korte tekstrepresentatie     |
| `l`      | Lange tekstrepresentatie      |
| `p`      | Samengevoegd getal           |
| `d`      | Decimaal (standaardformaat)  |

Deze kunnen worden gecombineerd om een breed scala aan datummaskers te bouwen.

## Lokalisatie van datumformaten {#date-format-localization}

De `MaskedDateField` past zich aan regionale datumformaten aan door de juiste locatie in te stellen. Dit zorgt ervoor dat datums worden weergegeven en geparsed op een manier die voldoet aan de verwachtingen van de gebruiker.

| Regio          | Formaat     | Voorbeeld      |
| --------------- | ----------- | -------------- |
| Verenigde Staten| MM/DD/JJJJ | `07/04/2023`   |
| Europa         | DD/MM/JJJJ | `04/07/2023`   |
| ISO-standaard  | JJJJ-MM-DD | `2023-07-04`   |

Om lokalisatie toe te passen, gebruik de methode `setLocale()`. Deze accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel formattering als parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsinglogica {#parsing-logic}

De `MaskedDateField` parseert gebruikersinvoer op basis van het gedefinieerde datummasker. Het accepteert zowel volledige als afgekorte numerieke invoer met of zonder scheidingstekens, wat flexibele invoer mogelijk maakt terwijl het geldige datums garandeert. 
De parsinggedrag hangt af van de volgorde van het formaat dat door het masker is gedefinieerd (bijv. `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

Bijvoorbeeld, stel dat vandaag `15 september 2012` is, dit is hoe verschillende invoeren zouden worden geïnterpreteerd:

### Voorbeeld parsingscenario's {#example-parsing-scenarios}

| Invoer                              | YMD (ISO)                                                                                                                                                                                        | MDY (VS)                                                                            | DMY (EU)                                                                                                                      |
| ----------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>      | Een enkel cijfer wordt altijd geïnterpreteerd als een dagsaantal binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                 | Hetzelfde als YMD                                                                     | Hetzelfde als YMD                                                                                                         |
| <div align="center">`12`</div>     | Twee cijfers worden altijd geïnterpreteerd als een dagsaantal binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                  | Hetzelfde als YMD                                                                     | Hetzelfde als YMD                                                                                                         |
| <div align="center">`112`</div>    | Drie cijfers worden geïnterpreteerd als een 1-cijferige maand gevolgd door een 2-cijferig dagsaantal, dus dit zou 12 januari 2012 zijn.                                                      | Hetzelfde als YMD                                                                     | Drie cijfers worden geïnterpreteerd als een 1-cijferige dag gevolgd door een 2-cijferige maand, dus dit zou 1 december 2012 zijn. |
| <div align="center">`1004`</div>   | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                                | Hetzelfde als YMD                                                                     | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                            |
| <div align="center">`020304`</div> | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                                | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.    | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                           |
| <div align="center">`8 cijfers`</div> | Acht cijfers worden geïnterpreteerd als YYYYMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                   | Acht cijfers worden geïnterpreteerd als MMDDYYYY. Bijvoorbeeld, `06122004` is 12 juni 2004. | Acht cijfers worden geïnterpreteerd als DDMMYYYY. Bijvoorbeeld, `06122004` is 6 december 2004.                            |
| <div align="center">`12/6`</div>   | Twee getallen gescheiden door een geldige scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle tekens behalve letters en cijfers worden als geldige scheidingstekens beschouwd. | Hetzelfde als YMD                                                                     | Twee getallen gescheiden door een scheidingsteken worden geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.       |
| <div align="center">`3/4/5`</div>   | 5 april 2012                                                                                                                                                                                  | 4 maart 2005                                                                         | 3 april 2005                                                                                                               |

## Tekstuele datum parsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standaard accepteert de `MaskedDateField` alleen numerieke invoer voor datums. Je kunt echter **tekstuele datum parsing** inschakelen om gebruikers in staat te stellen maand- en dagsnamen in hun invoer in te voeren. Deze functie is vooral handig voor een natuurlijkere datum invoer.

Om tekstuele parsing in te schakelen, gebruik je de methode `setTextualDateParsing()`:

```java
dateField.setTextualDateParsing(true);
```

### Vervanging van maandnamen {#month-name-substitution}

Wanneer tekstuele parsing is ingeschakeld, kun je speciale modifiers in je masker gebruiken om maandnamen in plaats van numerieke waarden te accepteren:

- **`%Ms`** - Accepteert korte maandnamen (Jan, Feb, Mar, enz.)
- **`%Ml`** - Accepteert lange maandnamen (Januari, Februari, Maart, enz.)

Maandnamen kunnen in elke positie binnen het masker voorkomen, en het veld accepteert nog steeds numerieke invoer als alternatief.

#### Voorbeelden

| Masker | Invoer             | Resultaat                         |
| ------ | ------------------ | --------------------------------- |
| `%Ms/%Dz/%Yz` | `Sep/01/25`       | **Geldig** - Parsed als 1 september 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Geldig** - Parsed als 1 september 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25`       | **Geldig** - Numerieke fallback werkt nog steeds |

:::info
Alle 12 maanden zijn ondersteund in zowel korte (Jan, Feb, Mar, Apr, Mei, Jun, Jul, Aug, Sep, Okt, Nov, Dec) als lange (Januari, Februari, enz.) vormen.
:::
### Decoratie van dagsnamen {#day-name-decoration}

De namen van de dagen van de week kunnen worden opgenomen in de invoer voor betere leesbaarheid, maar ze zijn **alleen decoratief** en worden verwijderd tijdens het parseren. Ze beïnvloeden de werkelijke datuma.

- **`%Ds`** - Accepteert korte dagsnamen (Ma, Di, Wo, enz.)
- **`%Dl`** - Accepteert lange dagsnamen (Maandag, Dinsdag, Woensdag, enz.)

:::warning Dagsnamen vereisen een numerieke dag
Bij gebruik van de dagsnamen (`%Ds` of `%Dl`) moet je masker **ook** `%Dz` of `%Dd` bevatten om het werkelijke dagsaantal aan te geven. Zonder een numerieke dagcomponent is de invoer ongeldig.
:::

#### Voorbeelden

| Masker | Invoer                | Resultaat                      |
| ------ | --------------------- | ------------------------------ |
| `%Ds %Mz/%Dz/%Yz` | `Ma 09/01/25`      | **Geldig** - Dagsnaam is decoratief |
| `%Dl %Mz/%Dz/%Yz` | `Maandag 09/01/25` | **Geldig** - Dagsnaam is decoratief |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Di`      | **Geldig** - Dagsnaam aan het einde |
| `%Dl/%Mz/%Yz`    | `Maandag/09/25`  | **Ongeldig** - %Dz ontbreekt |
| `%Mz/%Dl/%Yz`    | `09/maandag/25`   | **Ongeldig** - %Dz ontbreekt |

Alle 7 weekdagen zijn ondersteund in zowel korte (Ma, Di, Wo, Do, Vr, Za, Zo) als lange (Maandag, Dinsdag, enz.) vormen.

### Extra parsingregels {#additional-parsing-rules}

Tekstuele datum parsing omvat verschillende nuttige functies:

- **Hoofdletterongevoelig:** Invoer zoals `MAANDAG 09/01/25`, `maandag 09/01/25`, of `Maandag 09/01/25` werkt allemaal op dezelfde manier.
- **Locale-gevoelig:** Maand- en dagsnamen moeten overeenkomen met de geconfigureerde locale van het veld. Bijvoorbeeld, met een Franse locale, gebruik `septembre` en niet `September`. Engelse namen worden niet herkend tenzij de locale is ingesteld op Engels.
  - Franse locale: `septembre/01/25` wordt herkend als September
  - Duitse locale: `Montag 09/01/25` wordt herkend met maandag als de dagsnaam

## Instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane datumbereik in een `MaskedDateField` beperken door gebruik te maken van de methoden `setMin()` en `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methoden accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedDateField` bevat een herstelfunctie die de waarde van het veld terugzet naar een vooraf gedefinieerde of oorspronkelijke staat. Dit is nuttig voor het terugdraaien van gebruikersinvoer of het resetten naar een standaarddatum.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmamatig**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaardhersteltoets tenzij overschreven door een gebeurtenisluisteraar)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`, waarbij je een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie doorgeeft.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met reguliere expressies met de methode `setPattern()`:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het formaat `MM/DD/JJJJ` (twee cijfers, schuine streep, twee cijfers, schuine streep, vier cijfers) als geldig worden beschouwd.

:::tip Formaat Reguliere Expressie
Het patroon moet de JavaScript RegExp-syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Aantekeningen over Invoeren
Het veld probeert numerieke datuminputs te parseren en te formatteren op basis van het huidige masker. Gebruik kan echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig maar semantisch onjuist of niet parseerbaar is (bijv. `99/99/9999`), kan het voldoen aan de patrooncontroles, maar falen in logische validatie.
Je moet altijd de invoerwaarde in je app-logica valideren, zelfs als er een regulier expressiepatroon is ingesteld, om te zorgen dat de datum zowel correct geformatteerd als betekenisvol is.
::::

## Datumkiezer {#date-picker}

De `MaskedDateField` bevat een ingebouwde kalenderkiezer waarmee gebruikers visueel een datum kunnen selecteren, in plaats van deze in te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt toegang krijgen tot de datumkiezer met `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Toon/verberg het pickericoon {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het kalendericoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker met het veld interacteert (bijv. klikt, op Enter drukt of pijltoetsen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Dwingen tot Selectie via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren via de kalenderkiezer (en niet handmatig een kunnen typen), combineer de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // opent de kiezer bij gebruiker interactie
dateField.setAllowCustomValue(false);    // schakelt handmatige tekstinvoer uit
```

Deze opstelling garandeert dat alle datum invoer via de kiezer UI komt, wat nuttig is wanneer je strikte formaat controle wilt en parsingproblemen van getypte invoer wilt uitsluiten.
:::

### Handmatig de kalender openen {#manually-open-the-calendar}

Om de kalender programmatish te openen:

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

De `MaskedDateFieldSpinner` breidt [`MaskedDateField`](#basics) uit door spinnerbedieningen toe te voegen waarmee gebruikers de datum kunnen verhogen of verlagen met behulp van pijltoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral handig in desktopachtige applicaties.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Belangrijkste functies {#key-features}

- **Interactief Datum Stapelen:**  
  Gebruik pijltoetsen of spinknoppen om de datumwaarde te verhogen of te verlagen.

- **Aangepaste Stap Eenheid:**  
  Kies welk onderdeel van de datum je wilt wijzigen met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties zijn `DAG`, `WEKEN`, `MAAND` en `JAAR`.

- **Min/Max Grenzen:**  
  Ondersteunt minimum en maximum toegestane datums met `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**  
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stapeling {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dit maakt elke spinstap om de datum met een week vooruit of achteruit te verplaatsen.

## Stijling {#styling}

<TableBuilder name="MaskedDateField" />
