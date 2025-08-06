---
title: MaskedDateField
sidebar_position: 5
_i18n_hash: e2073fda6d7853bbacc6431c615e8cff
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-datefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedDateField" top='true'/>

Het `MaskedDateField` is een tekstinvoerveld dat is ontworpen voor gestructureerde datuminvoer. Hiermee kunnen gebruikers data invoeren als **getallen** en wordt de invoer automatisch opgemaakt op basis van een gedefinieerde masker wanneer het veld de focus verliest. Het masker is een tekenreeks die het verwachte datumformaat specificeert en beide invoer en weergave begeleidt.

Deze component ondersteunt flexibele parsing, validatie, lokalisatie en het herstellen van waarden. Het is vooral nuttig in formulieren zoals registraties, boekingen en planning, waar consistente en regio-specifieke datumformaten vereist zijn.

:::tip Op zoek naar tijdinvoer?
Het `MaskedDateField` richt zich uitsluitend op **datum** waarden. Als je een soortgelijke component nodig hebt voor het invoeren en formatteren van **tijd**, kijk dan naar de [`MaskedTimeField`](./timefield).
:::

## Basis {#basics}

Het `MaskedDateField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een tijdelijke aanduiding en een gebeurtenisluisteraar voor waarde wijzigingen definiëren.

<ComponentDemo path='/webforj/maskeddatefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldView.java' height='120px'/>

## Masker regels {#mask-rules}

Het `MaskedDateField` ondersteunt meerdere datumformaten die over de hele wereld worden gebruikt, die variëren in de volgorde van dag, maand en jaar. Veelvoorkomende patronen zijn:

- **Dag/Monat/Jaar** (gebruikt in de meeste delen van Europa)
- **Maand/Dag/Jaar** (gebruikt in de Verenigde Staten)
- **Jaar/Maand/Dag** (gebruikt in China, Japan en Korea; ook de ISO-standaard: `YYYY-MM-DD`)

Binnen deze formaten omvatten lokale variaties de keuze van scheidingstekens (bijv. `-`, `/`, of `.`), of jaren twee of vier cijfers zijn, en of enkelcijferige maanden of dagen worden aangevuld met voorloopnullen.

Om deze diversiteit te beheren, gebruikt het `MaskedDateField` format indicators, die beginnen met `%`, gevolgd door een letter die een specifiek onderdeel van de datum vertegenwoordigt. Deze indicatoren definiëren hoe invoer wordt geparsed en hoe de datum wordt weergegeven.

### Datum formaat indicatoren {#date-format-indicators}

| Formaat | Beschrijving    |
| ------- | --------------- |
| `%Y`    | Jaar            |
| `%M`    | Maand           |
| `%D`    | Dag             |

### Modifiers {#modifiers}

Modifiers bieden meer controle over hoe componenten van de datum worden opgemaakt:

| Modifier | Beschrijving                    |
| -------- | --------------------------------|
| `z`      | Nullen aanvullen                |
| `s`      | Korte tekstweergave             |
| `l`      | Lange tekstweergave             |
| `p`      | Samengesteld getal               |
| `d`      | Decimaal (standaardformaat)     |

Deze kunnen worden gecombineerd om een breed scala aan datum maskers te bouwen.

## Datum formaat lokalisatie {#date-format-localization}

Het `MaskedDateField` past zich aan regionale datumformaten aan door de juiste locale in te stellen. Dit zorgt ervoor dat data worden weergegeven en geparsed op een manier die overeenkomt met de verwachtingen van de gebruiker.

| Regio          | Formaat     | Voorbeeld       |
| ---------------| ------------| --------------- |
| Verenigde Staten | MM/DD/YYYY | `07/04/2023`   |
| Europa        | DD/MM/YYYY  | `04/07/2023`   |
| ISO-standaard | YYYY-MM-DD  | `2023-07-04`   |

Om lokalisatie toe te passen, gebruik je de `setLocale()` methode. Deze accepteert een [`java.util.Locale`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html) en past automatisch zowel de opmaak als de parsing aan:

```java
dateField.setLocale(Locale.FRANCE);
```

## Parsing logica {#parsing-logic}

Het `MaskedDateField` parseert gebruikersinvoer op basis van het gedefinieerde datum masker. Het accepteert zowel volledige als afgekorte numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is terwijl geldige data worden gewaarborgd. 
De parsinggedrag is afhankelijk van de formaatvolgorde die door het masker is gedefinieerd (bijv. `%Mz/%Dz/%Yz` voor maand/dag/jaar). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

Bijvoorbeeld, aangenomen dat vandaag `15 september 2012` is, is dit hoe verschillende invoeren zouden worden geïnterpreteerd:

### Voorbeeld parsing scenario's {#example-parsing-scenarios}

| Invoer                               | YMD (ISO)                                                                                                                                                                                          | MDY (VS)                                                                           | DMY (EU)                                                                                                                   |
| ------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| <div align="center">`1`</div>        | Een enkel cijfer wordt altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 1 september 2012 zijn.                                                                                 | Hetzelfde als YMD                                                                   | Hetzelfde als YMD                                                                                                        |
| <div align="center">`12`</div>       | Twee cijfers worden altijd geïnterpreteerd als een dagnummer binnen de huidige maand, dus dit zou 12 september 2012 zijn.                                                                                   | Hetzelfde als YMD                                                                   | Hetzelfde als YMD                                                                                                        |
| <div align="center">`112`</div>      | Drie cijfers worden geïnterpreteerd als een 1-cijferige maand nummer gevolgd door een 2-cijferige dagnummer, dus dit zou 12 januari 2012 zijn.                                                                        | Hetzelfde als YMD                                                                   | Drie cijfers worden geïnterpreteerd als een 1-cijferige dagnummer gevolgd door een 2-cijferige maand nummer, dus dit zou 1 december 2012 zijn. |
| <div align="center">`1004`</div>     | Vier cijfers worden geïnterpreteerd als MMDD, dus dit zou 4 oktober 2012 zijn.                                                                                                                         | Hetzelfde als YMD                                                                   | Vier cijfers worden geïnterpreteerd als DDMM, dus dit zou 10 april 2012 zijn.                                           |
| <div align="center">`020304`</div>   | Zes cijfers worden geïnterpreteerd als YYMMDD, dus dit zou 4 maart 2002 zijn.                                                                                                                          | Zes cijfers worden geïnterpreteerd als MMDDYY, dus dit zou 3 februari 2004 zijn.  | Zes cijfers worden geïnterpreteerd als DDMMYY, dus dit zou 2 maart 2004 zijn.                                          |
| <div align="center">`8 cijfers`</div> | Acht cijfers worden geïnterpreteerd als YYYYMMDD. Bijvoorbeeld, `20040612` is 12 juni 2004.                                                                                                           | Acht cijfers worden geïnterpreteerd als MMDDYYYY. Bijvoorbeeld, `06122004` is 12 juni 2004. | Acht cijfers worden geïnterpreteerd als DDMMYYYY. Bijvoorbeeld, `06122004` is 6 december 2004.                              |
| <div align="center">`12/6`</div>     | Twee getallen gescheiden door een geldig scheidingsteken worden geïnterpreteerd als MM/DD, dus dit zou 6 december 2012 zijn. <br />Opmerking: Alle tekens behalve letters en cijfers worden als geldige scheidingstekens beschouwd. | Hetzelfde als YMD                                                                   | Twee getallen gescheiden door een scheidingsteken worden geïnterpreteerd als DD/MM, dus dit zou 12 juni 2012 zijn.       |
| <div align="center">`3/4/5`</div>    | 5 april 2012                                                                                                                                                                                      | 4 maart 2005                                                                       | 3 april 2005                                                                                                            |

## Instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane datumbereik in een `MaskedDateField` beperken met de methoden `setMin()` en `setMax()`:

```java
dateField.setMin(LocalDate.of(2020, 1, 1));
dateField.setMax(LocalDate.of(2030, 12, 31));
```

Beide methoden accepteren waarden van het type [`java.time.LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Waarde herstellen {#restoring-the-value}

Het `MaskedDateField` heeft een herstel functie die de waarde van het veld terugzet naar een vooraf gedefinieerde of originele staat. Dit is nuttig voor het terugdraaien van gebruikersinvoer of om terug te zetten naar een standaarddatum.

```java
dateField.setRestoreValue(LocalDate.of(2025, 1, 1));
dateField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmeerbaar**, door `restoreValue()` aan te roepen.
- **Via het toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaard hersteltoets, tenzij overschreven door een gebeurtenisluisteraar).

Je kunt de waarde om te herstellen instellen met `setRestoreValue()`, door een [`LocalDate`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalDate.html) instantie door te geven.

<ComponentDemo 
path='/webforj/maskeddatefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldRestoreView.java' 
height='120px'/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met behulp van reguliere expressies met de `setPattern()` methode:

```java
dateField.setPattern("^\\d{2}/\\d{2}/\\d{4}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het formaat `MM/DD/YYYY` (twee cijfers, schuine streep, twee cijfers, schuine streep, vier cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressie Formaat
Het patroon moet de JavaScript RegExp-syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notities over Invoerschema
Het veld probeert numerieke datainvoeren te parseren en op te maken op basis van het huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig maar semantisch onjuist of onverwerkbaar is (bijv. `99/99/9999`), kan deze door patrooncontroles komen, maar falen bij logische validatie.
Je moet altijd de invoerwaarde in je applicatielogica valideren, zelfs als een reguliere expressiepatroon is ingesteld, om ervoor te zorgen dat de datum zowel correct is opgemaakt als betekenisvol.
::::

## Datumkiezer {#date-picker}

Het `MaskedDateField` bevat een ingebouwde kalenderkiezer die gebruikers in staat stelt visueel een datum te selecteren, in plaats van deze in te typen. Dit verhoogt de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskeddatefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldPickerView.java' 
height='450px'/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt de datumkiezer openen met `getPicker()`:

```java
DatePicker picker = dateField.getPicker();
```

### Toon/verberg het icoon van de kiezer {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het kalendericoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Auto-open gedrag {#auto-open-behavior}

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker met het veld interactie heeft (bijvoorbeeld klikt, op Enter drukt of pijltoetsen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Dwingen tot Selectie via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een datum kunnen selecteren met de kalenderkiezer (en niet handmatig een datum kunnen typen), combineer je de volgende twee instellingen:

```java
dateField.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
dateField.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opstelling garandeert dat alle datum invoer via de kiezer UI komt, wat nuttig is wanneer je strikte opmaakcontrole wilt en het parseren van problemen van getypte invoer wilt uitsluiten.
:::

### Handmatig de kalender openen {#manually-open-the-calendar}

Om de kalender programmatisch te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // zelfde als open()
```

### Toon weken in de kalender {#show-weeks-in-the-calendar}

De kiezer kan optioneel weeknummers in de kalenderweergave tonen:

```java
picker.setShowWeeks(true);
```

## `MaskedDateFieldSpinner` {#maskeddatefieldspinner}

De `MaskedDateFieldSpinner` breidt [`MaskedDateField`](#basics) uit door spinners toe te voegen waarmee gebruikers de datum kunnen verhogen of verlagen met behulp van de pijltoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktopachtige toepassingen.

<ComponentDemo 
path='/webforj/maskeddatefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskeddatefield/MaskedDateFieldSpinnerView.java' 
height='450px'/>

### Belangrijkste kenmerken {#key-features}

- **Interactieve Datumstappen:**  
  Gebruik pijltoetsen of spindrukknoppen om de datumswaarde te verhogen of te verlagen.

- **Aanpasbare Stap Eenheid:**  
  Kies welk onderdeel van de datum moet worden aangepast met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedDateFieldSpinner.SpinField.MONTH);
  ```

  Opties zijn onder andere `DAY`, `WEEK`, `MONTH`, en `YEAR`.

- **Min/Max Grenzen:**  
  Erft ondersteuning voor minimale en maximale toegestane datums met behulp van `setMin()` en `setMax()`.

- **Geformatteerde Output:**  
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedDateField`.

### Voorbeeld: Configureer wekelijkse stappen {#example-configure-weekly-stepping}

```java
MaskedDateFieldSpinner spinner = new MaskedDateFieldSpinner();
spinner.setSpinField(MaskedDateFieldSpinner.SpinField.WEEK);
```

Dit maakt elke spinstap voorwaarts of achterwaarts de datum met één week. 

## Styling {#styling}

<TableBuilder name="MaskedDateField" />
