---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: f76242de3a742ad3a930e1581f688592
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

De `MaskedDateField` is een tekstinvoerkontrol ontworpen voor gestructureerde datuminvoer. Het stelt gebruikers in staat om data als **getallen** in te voeren en formatteert de invoer automatisch op basis van een gedefinieerde masker wanneer het veld de focus verliest. De masker is een string die het verwachte datumformaat specificeert, wat zowel de invoer als weergave begeleidt.

Deze component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel. Het is vooral nuttig in formulieren zoals registraties, boekingen en planning, waar consistente en regio-specifieke datumformaten vereist zijn.

:::tip Op zoek naar tijdinvoer?
De `MaskedDateField` richt zich uitsluitend op **datum** waarden. Als je een vergelijkbare component nodig hebt voor het invoeren en formatteren van **tijd**, kijk dan in de [`MaskedTimeField`](./timefield).
:::

## Basis {#basics}

De `MaskedDateField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een placeholder en een gebeurtenislistener voor waarde wijzigingen definiëren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Maskerregels {#mask-rules}

De `MaskedDateField` ondersteunt meerdere datumformaten die wereldwijd worden gebruikt, welke variëren op basis van de volgorde van dag, maand en jaar. Veelvoorkomende patronen zijn onder andere:

- **Dag/Maand/Jaar** (gebruikt in de meeste delen van Europa)
- **Maand/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/Maand/Dag** (gebruikt in China, Japan en Korea; ook de ISO standaard: `YYYY-MM-DD`)

Binnen deze formaten zijn er lokale variaties, zoals de keuze van scheidingstekens (bijv. `-`, `/`, of `.`), of jaren uit twee of vier cijfers bestaan, en of enkelcijferige maanden of dagen zijn aangevuld met voorloopnullen.

Om met deze diversiteit om te gaan, gebruikt de `MaskedDateField` formatteringsindicatoren, die elk beginnen met `%`, gevolgd door een letter die een specifiek onderdeel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe invoer wordt geparsed en hoe de datum wordt weergegeven.

### Datumformaatindicatoren {#date-format-indicators}

| Formaat | Beschrijving |
| ------- | ------------ |
| `%Y`    | Jaar         |
| `%M`    | Maand        |
| `%D`    | Dag          |

### Modifiers {#modifiers}

Modifiers bieden meer controle over hoe componenten van de datum worden opgemaakt:

| Modifier | Beschrijving                  |
| -------- | ----------------------------- |
| `z`      | Voorloopnullen               |
| `s`      | Korte tekstweergave          |
| `l`      | Lange tekstweergave          |
| `p`      | Samengevoegd getal           |
| `d`      | Decimaal (standaardformaat)  |

Deze kunnen worden gecombineerd om een breed scala aan datum maskers te bouwen.

## Datumformaatlokalisatie {#date-format-localization}

De `MaskedDateField` past zich aan regionale datumformaten aan door de juiste locale in te stellen. Dit zorgt ervoor dat data worden weergegeven en geparsed op een manier die voldoet aan de verwachtingen van de gebruiker.

| Regio        | Formaat     | Voorbeeld     |
| ------------ | ----------- | ------------- |
| Verenigde Staten | MM/DD/YYYY | `07/04/2023` |
| Europa       | DD/MM/YYYY  | `04/07/2023` |
| ISO Standaard| YYYY-MM-DD  | `2023-07-04` |

Om lokalisatie toe te passen, gebruik de `setLocale()` methode. Deze accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel opmaak als parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing logica {#parsing-logic}

De `MaskedDateField` parsed gebruikersinvoer op basis van de gedefinieerde datum masker. Het accepteert zowel volledige als afgekorte numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is terwijl geldige data worden verzekerd.
Het parsinggedrag hangt af van de formaatvolgorde die door de masker is gedefinieerd (bijv. `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

Bijvoorbeeld, uitgaande van het feit dat vandaag `15 september 2012` is, dit is hoe verschillende invoeren zouden worden geïnterpreteerd:

### Voorbeeld parsingscenario's {#example-parsing-scenarios}

| Invoer                                | YMD (ISO)                                                                                                                                                                                          | MDY (VS)                                                                            | DMY (EU)                                                                                                                     |
| ------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Een enkel cijfer wordt altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                               | Zelfde als YMD                                                                         | Zelfde als YMD                                                                                                                  |
| <div align="center">`12`</div>       | Twee cijfers worden altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                                   | Zelfde als YMD                                                                         | Zelfde als YMD                                                                                                                  |
| <div align="center">`112`</div>      | Drie cijfers worden geïnterpreteerd als een 1-cijferige maandnummer gevolgd door een 2-cijferig dagnummer, dus dit zou 12 januari 2012 zijn.                                                                        | Zelfde als YMD                                                                         | Drie cijfers worden geïnterpreteerd als een 1-cijferig dagnummer gevolgd door een 2-cijferig maandnummer, dus dit zou 1 december 2012 zijn. |
| <div align="center">`1004`</div>     | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                                             | Zelfde als YMD                                                                         | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                                         |
| <div align="center">`020304`</div>   | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                                              | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.            | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                                         |
| <div align="center">`8 cijfers`</div> | Acht cijfers worden geïnterpreteerd als YYYYMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                                | Acht cijfers worden geïnterpreteerd als MMDDYYYY. Bijvoorbeeld, `06122004` is 12 juni 2004. | Acht cijfers worden geïnterpreteerd als DDMMYYYY. Bijvoorbeeld, `06122004` is 6 december 2004.                                        |
| <div align="center">`12/6`</div>     | Twee getallen gescheiden door een geldig scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle tekens behalve letters en cijfers worden als geldige scheidingstekens beschouwd. | Zelfde als YMD                                                                         | Twee getallen gescheiden door een scheidingsteken wordt geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.                               |
| <div align="center">`3/4/5`</div>    | 5 april 2012                                                                                                                                                                                      | 4 maart 2005                                                                       | 3 april 2005                                                                                                                 |

## Instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane daterange in een `MaskedDateField` beperken met de methoden `setMin()` en `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methoden accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedDateField` bevat een herstelfunctie die de waarde van het veld terugzet naar een vooraf gedefinieerde of oorspronkelijke staat. Dit is nuttig voor het terugzetten van gebruikersinvoer of om naar een standaarddatum te resetten.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmamatig**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door te drukken op <kbd>ESC</kbd> (dit is de standaardhersteltoets, tenzij overschreven door een gebeurtenislistener)

Je kunt de te herstellen waarde instellen met `setRestoreValue()`, door een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie door te geven.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met reguliere expressies met de methode `setPattern()`:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `MM/DD/YYYY` formaat (twee cijfers, schuine streep, twee cijfers, schuine streep, vier cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressie Formaat
Het patroon moet de JavaScript RegExp-syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over Invoerhandling
Het veld probeert numerieke datuminvoer te parseren en te formatteren op basis van de huidige masker. Echter, gebruikers kunnen nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig is, maar semantisch onjuist of onparseerbaar is (bijv. `99/99/9999`), kan deze de patrooncontroles doorstaan maar logische validatie mislukken.
Je moet altijd de invoerwaarde in je app-logica valideren, zelfs als er een reguliere expressiepatroon is ingesteld, om ervoor te zorgen dat de datum zowel correct is opgemaakt als betekenisvol.
::::

## Datumkiezer {#date-picker}

De `MaskedDateField` bevat een ingebouwde kalenderkiezer die gebruikers in staat stelt om een datum visueel te selecteren, in plaats van deze in te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt toegang krijgen tot de datumkiezer met `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Toon/verberg het icoon van de kiezer {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het kalendericoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Auto-open gedrag {#auto-open-behavior}

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker met het veld interageert (bijvoorbeeld, klikt, Enter drukt of pijltjestoetsen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Verplicht Selectie Via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren met behulp van de kalenderkiezer (en niet handmatig een kunnen typen), combineer de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
dateField.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opzet garandeert dat alle datum invoer via de kiezer UI komt, wat nuttig is wanneer je strikte opmaakcontrole wilt en parserproblemen van getypte invoer wilt uitsluiten.
:::

### Handmatig de kalender openen {#manually-open-the-calendar}

Om de kalender programatisch te openen:

```java
picker.open();
```

Of gebruik het alias:

```java
picker.show(); // zelfde als open()
```

### Toon weken in de kalender {#show-weeks-in-the-calendar}

De kiezer kan optioneel weeknummers in de kalenderweergave tonen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

De `MaskedDateFieldSpinner` breidt [`MaskedDateField`](#basics) uit door spinnercontroles toe te voegen waarmee gebruikers de datum kunnen verhogen of verlagen met behulp van pijltjestoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktopstijl applicaties.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Belangrijkste kenmerken {#key-features}

- **Interactieve Datumstappen:**  
  Gebruik pijltjestoetsen of spindekanalen om de datumwaarde te verhogen of te verlagen.

- **Aanpasbare Stap Eenheid:**  
  Kies welk onderdeel van de datum je wilt wijzigen met behulp van `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties zijn `DAY`, `WEEK`, `MONTH`, en `YEAR`.

- **Min/Max Grenzen:**  
  Neemt ondersteuning voor minimum en maximum toegestane datums over met `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**  
  Volledig compatibel met maskers en lokalisatie-instellingen van de `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stappen {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dit zorgt ervoor dat elke spinstap de datum met een week vooruit of achteruit gaat.

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
