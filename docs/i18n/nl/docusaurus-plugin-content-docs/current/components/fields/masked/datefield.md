---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: 6c75156564c20c2d451ebe7046213c37
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

De `MaskedDateField` is een tekstinvoerveld waarmee gebruikers data kunnen invoeren als nummers en het invoer automatisch opmaakt op basis van een gedefinieerde maskering wanneer het veld zijn focus verliest. De maskering specificeert het verwachte datumnotatieformat, dat zowel de invoer als de weergave begeleidt. De component ondersteunt flexibele parsing, validatie, lokalisatie en herstel van waarden voor consistente, regiogebonden datumverwerking.

<!-- INTRO_END -->

## Basisprincipes {#basics}

:::tip Op zoek naar tijdinvoer?
De `MaskedDateField` richt zich uitsluitend op **datum** waarden. Als je een vergelijkbare component nodig hebt voor het invoeren en formatteren van **tijd**, kijk dan in de [`MaskedTimeField`](./timefield).
:::

De `MaskedDateField` kan worden geïnstalleerd met of zonder parameters. Je kunt een initiële waarde, een label, een plaatsaanduiding en een evenementlistener voor waarde wijzigingen definiëren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskerregels {#mask-rules}

De `MaskedDateField` ondersteunt meerdere datumnotaties die wereldwijd worden gebruikt, die variëren op basis van de volgorde van dag, maand en jaar. Veel voorkomende patronen zijn:

- **Dag/Maand/Jaar** (gebruikt in de meeste van Europa)
- **Maand/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/Maand/Dag** (gebruikt in China, Japan en Zuid-Korea; ook de ISO-standaard: `JJJJ-MM-DD`)

Binnen deze formaten zijn er lokale variaties, waaronder de keuze van scheidingstekens (bijv. `-`, `/` of `.`), of jaren uit twee of vier cijfers bestaan, en of enkelcijferige maanden of dagen worden aangevuld met voorloopnullen.

Om deze diversiteit aan te pakken, gebruikt de `MaskedDateField` format indicatoren, elk beginnend met `%`, gevolgd door een letter die een specifiek deel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe invoer wordt geparsed en hoe de datum wordt weergegeven.

:::tip Maskers programmatic toepassen
Om datums met dezelfde masker-syntaxis buiten een veld te formatteren of te parseren, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility-class.
:::

### Datumformat indicatoren {#date-format-indicators}

| Formaat | Beschrijving |
| ------- | ------------ |
| `%Y`    | Jaar         |
| `%M`    | Maand        |
| `%D`    | Dag          |

### Modifiers {#modifiers}

Modifiers bieden meer controle over hoe componenten van de datum worden opgemaakt:

| Modifier | Beschrijving                |
| -------- | --------------------------- |
| `z`      | Voorloopnul                |
| `s`      | Korte tekstrepresentatie    |
| `l`      | Lange tekstrepresentatie     |
| `p`      | Verpakt nummer              |
| `d`      | Decimaal (standaardformaat) |

Deze kunnen worden gecombineerd om een breed scala aan datummaskers te bouwen.

## Lokalisatie van datumformaten {#date-format-localization}

De `MaskedDateField` past zich aan regionale datumformaten aan door de juiste locale in te stellen. Dit zorgt ervoor dat datums worden weergegeven en geparsed op een manier die overeenkomt met de verwachtingen van de gebruiker.

| Regio        | Formaat     | Voorbeeld      |
| ------------ | ----------- | -------------- |
| Verenigde Staten | MM/DD/JJJJ | `07/04/2023`  |
| Europa       | DD/MM/JJJJ | `04/07/2023`  |
| ISO-standaard | JJJJ-MM-DD | `2023-07-04`  |

Om lokalisatie toe te passen, gebruik de `setLocale()` methode. Het accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel opmaak als parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsinglogica {#parsing-logic}

De `MaskedDateField` parseert gebruikersinvoer op basis van de gedefinieerde datummasker. Het accepteert zowel volledige als afgekorte numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is terwijl ervoor wordt gezorgd dat de datums geldig zijn.
De parsering komt overeen met de volgorde van het formaat dat door de masker is gedefinieerd (bijv. `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

Bijvoorbeeld, als vandaag `15 september 2012` is, dan zou dit zijn hoe verschillende invoeren zouden worden geïnterpreteerd:

### Voorbeeld parsing scenario's {#example-parsing-scenarios}

| Invoer                                | YMD (ISO)                                                                                                                                                                                          | MDY (VS)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Een enkel cijfer wordt altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                                | Hetzelfde als YMD                                                                     | Hetzelfde als YMD                                                                                                                  |
| <div align="center">`12`</div>       | Twee cijfers worden altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                               | Hetzelfde als YMD                                                                     | Hetzelfde als YMD                                                                                                                  |
| <div align="center">`112`</div>      | Drie cijfers worden geïnterpreteerd als een 1-cijferige maandnummer gevolgd door een 2-cijferig dagnummer, dus dit zou 12 januari 2012 zijn.                                                                        | Hetzelfde als YMD                                                                     | Drie cijfers worden geïnterpreteerd als een 1-cijferig dagnummer gevolgd door een twee-cijferig maandnummer, dus dit zou 1 december 2012 zijn. |
| <div align="center">`1004`</div>     | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                                              | Hetzelfde als YMD                                                                     | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                                         |
| <div align="center">`020304`</div>   | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                                              | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.            | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                                         |
| <div align="center">`8 cijfers`</div> | Acht cijfers worden geïnterpreteerd als JJJJMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                                | Acht cijfers worden geïnterpreteerd als MMDDJJJJ. Bijvoorbeeld, `06122004` is 12 juni 2004. | Acht cijfers worden geïnterpreteerd als DDMMJJJJ. Bijvoorbeeld, `06122004` is 6 december 2004.                                        |
| <div align="center">`12/6`</div>     | Twee nummers gescheiden door een geldig scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle tekens behalve letters en cijfers worden als geldige scheidingstekens beschouwd. | Hetzelfde als YMD                                                                     | Twee nummers gescheiden door een scheidingsteken worden geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.                               |
| <div align="center">`3/4/5`</div>    | 5 april 2012                                                                                                                                                                                      | 4 maart 2005                                                                       | 3 april 2005                                                                                                                 |


## Tekstuele datum parsing <DocChip chip='since' label='25.11' /> {#textual-date-parsing}

Standaard accepteert de `MaskedDateField` alleen numerieke invoer voor datums. Je kunt echter **tekstuele datumparsing** inschakelen om gebruikers in staat te stellen maand- en dagnamen in hun invoer op te geven. Deze functie is bijzonder nuttig voor het creëren van natuurlijkere datuminvoer.

Om tekstuele parsing in te schakelen, gebruik de `setTextualDateParsing()` methode:

```java
dateField.setTextualDateParsing(true);
```

### Vervanging van maandnamen {#month-name-substitution}

Wanneer tekstuele parsing is ingeschakeld, kun je speciale modifiers in je masker gebruiken om maandnamen in plaats van numerieke waarden te accepteren:

- **`%Ms`** - Accepteert korte maandnamen (Jan, Feb, Mar, enz.)
- **`%Ml`** - Accepteert lange maandnamen (Januari, Februari, Maart, enz.)

Maandnamen kunnen op elke positie binnen het masker voorkomen, en het veld accepteert nog steeds numerieke invoer als fallback.

#### Voorbeelden

| Masker | Invoer | Resultaat |
| ------ | ----- | ------ |
| `%Ms/%Dz/%Yz` | `Sep/01/25` | **Geldig** - Parseert als 1 september 2025 |
| `%Ml/%Dz/%Yz` | `September/01/25` | **Geldig** - Parseert als 1 september 2025 |
| `%Dz/%Ml/%Yz` | `01/September/25` | **Geldig** - Parseert als 1 september 2025 |
| `%Mz/%Dz/%Yz` | `09/01/25` | **Geldig** - Numerieke fallback werkt nog steeds |

:::info
Alle 12 maanden worden ondersteund in zowel korte (Jan, Feb, Mar, Apr, Mei, Jun, Jul, Aug, Sep, Okt, Nov, Dec) als lange (Januari, Februari, enz.) vormen.
:::
### Decoratie van dagnamen {#day-name-decoration}

Dagen-van-de-week namen kunnen in de invoer worden opgenomen voor betere leesbaarheid, maar ze zijn **alleen decoratief** en worden verwijderd tijdens het parseren. Ze beïnvloeden de werkelijke datumwaarde niet.

- **`%Ds`** - Accepteert korte dagnamen (Ma, Di, Wo, enz.)
- **`%Dl`** - Accepteert lange dagnamen (Maandag, Dinsdag, Woensdag, enz.)

:::warning Dagnamen vereisen een numerieke dag
Wanneer je dag-van-de-week namen gebruikt (`%Ds` of `%Dl`), **moet** je masker ook `%Dz` of `%Dd` bevatten om het daadwerkelijke dagnummer op te geven. Zonder een numeriek dagcomponent is de invoer ongeldig.
:::

#### Voorbeelden

| Masker | Invoer | Resultaat |
| ------ | ----- | ------ |
| `%Ds %Mz/%Dz/%Yz` | `Ma 09/01/25` | **Geldig** - Dagnaam is decoratief |
| `%Dl %Mz/%Dz/%Yz` | `Maandag 09/01/25` | **Geldig** - Dagnaam is decoratief |
| `%Mz/%Dz/%Yz %Ds` | `09/01/25 Di` | **Geldig** - Dagnaam aan het einde |
| `%Dl/%Mz/%Yz` | `Maandag/09/25` | **Ongeldig** - Ontbrekend `%Dz` |
| `%Mz/%Dl/%Yz` | `09/Maandag/25` | **Ongeldig** - Ontbrekend `%Dz` |

Alle 7 weekdagen worden ondersteund in zowel korte (Ma, Di, Wo, Do, Vr, Za, Zo) als lange (Maandag, Dinsdag, enz.) vormen.

### Aanvullende parserregels {#additional-parsing-rules}

Tekstuele datumparsing omvat verschillende nuttige functies:

- **Hoofdletterongevoelig:** Invoer zoals `MAANDAG 09/01/25`, `maandag 09/01/25` of `Maandag 09/01/25` werkt op dezelfde manier.
- **Locale-gevoelig:** Maand- en dagnamen moeten overeenkomen met de geconfigureerde locale van het veld. Bijvoorbeeld, met een Franse locale, gebruik `septembre` en niet `September`. Engelse namen worden niet herkend tenzij de locale is ingesteld op Engels.
  - Franse locale: `septembre/01/25` wordt herkend als September
  - Duitse locale: `Montag 09/01/25` wordt herkend met maandag als de dagnaam

## Instellen van min/max-beperkingen {#setting-minmax-constraints}

Je kunt het toegestane datumbereik in een `MaskedDateField` beperken met de methoden `setMin()` en `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methoden accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedDateField` bevat een herstelfunctie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. Dit is nuttig voor het terugdraaien van gebruikersinvoer of het resetten naar een standaarddatum.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatically**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard hersteltoets tenzij overschreven door een evenementlistener)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`, waarbij een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie wordt doorgegeven.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met behulp van reguliere expressies met de methode `setPattern()`:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `MM/DD/JJJJ` formaat (twee cijfers, slash, twee cijfers, slash, vier cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressieformaat
Het patroon moet de syntaxis van JavaScript RegExp volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over invoerhandeling
Het veld probeert numerieke datuminvoeren te parseren en op te maken op basis van de huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig is, maar semantisch onjuist of niet parseerbaar is (bijv. `99/99/9999`), kan deze patrooncontroles doorstaan, maar logische validatie mislukt.
Je moet altijd de invoerwaarde in je app-logica valideren, zelfs als er een reguliere expressiepatroon is ingesteld, om ervoor te zorgen dat de datum zowel correct is opgemaakt als betekenisvol.
::::

## Datumpicker {#date-picker}

De `MaskedDateField` bevat een ingebouwde kalenderpicker waarmee gebruikers een datum visueel kunnen selecteren, in plaats van deze in te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Toegang tot de picker {#accessing-the-picker}

Je kunt de datumpicker openen met `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Toon/verberg het picker-pictogram {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het kalenderpictogram naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het pictogram
```

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de picker zo configureren dat deze automatisch opent wanneer de gebruiker met het veld interacteert (bijv. klikt, op Enter drukt of pijltjestoetsen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Keuze Handhaven via de Picker
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren met behulp van de kalenderpicker (en niet handmatig een datum kunnen invoeren), combineer de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // Opent de picker bij gebruikersinteractie
dateField.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opstelling garandeert dat alle datuminvoer via de picker-ui komt, wat handig is wanneer je strikte formatcontrole wilt en parserproblemen van type-invoer wilt uitsluiten.
:::

### Handmatig de kalender openen {#manually-open-the-calendar}

Om de kalender programmatically te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### Toon weken in de kalender {#show-weeks-in-the-calendar}

De picker kan optioneel weeknummers in de kalenderweergave weergeven:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

De `MaskedDateFieldSpinner` breidt de [`MaskedDateField`](#basics) uit door spincontrols toe te voegen waarmee gebruikers de datum kunnen verhogen of verlagen met behulp van pijltjestoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktop-gebaseerde applicaties.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Belangrijke functies {#key-features}

- **Interactieve Datumstappen:**  
  Gebruik pijltjestoetsen of draaiknoppen om de datumwaarde te verhogen of te verlagen.

- **Aangepaste Stap Eenheid:**  
  Kies welk onderdeel van de datum je wilt wijzigen met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties zijn `DAG`, `WEEK`, `MAAND` en `JAAR`.

- **Min/max-grenzen:**  
  Neemt de ondersteuning voor minimale en maximale toegestane datums over met `setMin()` en `setMax()`.

- **Geformatteerde uitvoer:**  
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stappen {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dit zorgt ervoor dat elke draaistap de datum met een week vooruit of achteruit gaat.

## Stylen {#styling}

<TableBuilder name="MaskedDateField" />
