---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 3d719856c08ce148bcd2999878d8c161
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

De `MaskedTimeField` is een tekstinvoerveld ontworpen voor nauwkeurige, gestructureerde tijdsregistratie. Het stelt gebruikers in staat om tijden in te voeren als **getallen** en formatteert automatisch de invoer op basis van een gedefinieerde masker wanneer het veld de focus verliest. Het masker is een tekenreeks die het verwachte tijdformaat specificeert en zowel invoer als weergave begeleidt.

Deze component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel. Het is vooral nuttig in tijdgevoelige formulieren zoals schema's, tijdregistraties en reserveringen.

:::tip Op zoek naar input voor data?
De `MaskedTimeField` is gebouwd voor **alleen tijd** invoer. If je op zoek bent naar een component om **data** te behandelen met een vergelijkbare maskergebaseerde formatting, kijk dan naar de [`MaskedDateField`](./datefield.md).
:::

## Basisprincipes {#basics}

De `MaskedTimeField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een placeholder en een evenementlistener voor waarde veranderingen definiëren.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskerregels {#mask-rules}

De `MaskedTimeField` gebruikt format indicatoren om te definiëren hoe tijd wordt geparsed en weergegeven. Elke format indicator begint met een `%` gevolgd door een letter die een tijdcomponent vertegenwoordigt.

### Tijdformaat indicatoren {#time-format-indicators}

| Formaat | Beschrijving         |
|---------|---------------------|
| `%H`    | Uur (24-uurs)      |
| `%h`    | Uur (12-uurs)      |
| `%m`    | Minuut             |
| `%s`    | Seconde             |
| `%p`    | AM/PM              |

### Modifiers {#modifiers}

Modifiers verfijnen de weergave van tijdcomponenten:

| Modifier | Beschrijving               |
|----------|---------------------------|
| `z`      | Zero-fill                 |
| `s`      | Korte tekstrepresentatie   |
| `l`      | Lange tekstrepresentatie    |
| `p`      | Samengesteld nummer        |
| `d`      | Decimaal (standaard formaat)|

Deze maken flexibele en locatie-vriendelijke tijdformattering mogelijk.

## Lokalisatie van het tijdformaat {#time-format-localization}

De `MaskedTimeField` ondersteunt lokalisatie door de juiste lokale instelling in te stellen. Dit zorgt ervoor dat tijdinvoer en uitgang voldoen aan regionale conventies.

```java
field.setLocale(Locale.GERMANY);
```

Dit beïnvloedt hoe AM/PM-indicatoren worden weergegeven, hoe scheidingstekens worden behandeld en hoe waarden worden geparsed.

## Parsinglogica {#parsing-logic}

De `MaskedTimeField` parsed gebruikersinvoer op basis van het gedefinieerde tijdmasker. Het accepteert zowel volledige als afgekorte numerieke invoer, met of zonder scheidingstekens, wat flexibele invoer mogelijk maakt terwijl geldige tijden worden gegarandeerd. 
De parsinggedrag hangt af van de formatvolgorde die door het masker is gedefinieerd (bijvoorbeeld `%Hz:%mz` voor uur/minuut). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

### Voorbeeld parsingscenario's {#example-parsing-scenarios}

| Invoer  | Masker          | Geïnterpreteerd Als |
|---------|-----------------|----------------------|
| `900`   | `%Hz:%mz`       | `09:00`              |
| `1345`  | `%Hz:%mz`       | `13:45`              |
| `0230`  | `%hz:%mz %p`    | `02:30 AM`           |
| `1830`  | `%hz:%mz %p`    | `06:30 PM`           |

## Instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane tijdsbereik in een `MaskedTimeField` beperken met de methoden `setMin()` en `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide methoden accepteren waarden van het type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Waarde herstellen {#restoring-the-value}

De `MaskedTimeField` bevat een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. Dit kan nuttig zijn om wijzigingen ongedaan te maken of terug te keren naar een standaardtijd.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatisch**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard hersteltoets, tenzij overschreven door een evenementlistener)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met behulp van reguliere expressies met de methode `setPattern()`:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `HH:mm` formaat (twee cijfers, dubbele punt, twee cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressie Formaat
Het patroon moet voldoen aan de JavaScript RegExp-syntaxis zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over Invoerverwerking
Het veld probeert numerieke tijdinvoeren te parseren en te formatteren op basis van het huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig maar semantisch onjuist of onparseerbaar is (bijvoorbeeld `99:99`), kan deze patrooncontroles doorstaan maar faalt logische validatie.
Je moet altijd de invoerwaarde in je app-logica valideren, zelfs als een reguliere expressie patroon is ingesteld, om ervoor te zorgen dat de tijd zowel goed geformatteerd als betekenisvol is.
:::

## Tijdkiezer {#time-picker}

De `MaskedTimeField` omvat een ingebouwde tijdkiezer waarmee gebruikers een tijd visueel kunnen selecteren, in plaats van deze in te typen. Dit verhoogt de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Toegang tot de picker {#accessing-the-picker}

Je kunt de tijdkiezer openen met `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Toon/verborgen de picker-icoon {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het klokicoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de picker configureren om automatisch te openen wanneer de gebruiker interactie heeft met het veld (bijvoorbeeld klikken, op Enter drukken of pijltoetsen):

```java
picker.setAutoOpen(true);
```

:::tip Dwing Selectie Via de Picker
Om ervoor te zorgen dat gebruikers alleen een tijd kunnen selecteren met behulp van de picker (en niet handmatig één kunnen typen), combineer de volgende twee instellingen:

```java
field.getPicker().setAutoOpen(true); // Opent de picker bij gebruik van de gebruiker
field.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opzet garandeert dat alle tijdinvoer via de picker UI komt, wat nuttig is wanneer je strikte formatcontrole wilt en parseproblemen van getypte invoer wilt elimineren.
:::

### Handmatig de picker openen {#manually-open-the-picker}

Om de tijdkiezer programma-matig te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### Instellen van de stap van de picker {#setting-the-picker-step}

Je kunt het interval tussen selecteerbare tijden in de picker definiëren met `setStep()`. Dit stelt je in staat om te bepalen hoe gedetailleerd de tijdsopties zijn—ideaal voor scenario's zoals plannen in blokken van 15 minuten.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Stap Beperking
De stap moet een gelijkmatige deling van een uur of een volle dag zijn. Anders zal een uitzondering worden gegooid.
:::

Dit zorgt ervoor dat de vervolgkeuzelijst voorspelbare, gelijkmatig verspreide waarden bevat zoals `09:00`, `09:15`, `09:30`, enzovoort.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

De `MaskedTimeFieldSpinner` breidt [`MaskedTimeField`](#basics) uit door spinnerbedieningselementen toe te voegen waarmee gebruikers de tijd kunnen verhogen of verlagen met behulp van pijltoetsen of UI-knoppen. Het biedt een meer begeleide interactiestijl, wat vooral nuttig is in desktopachtige toepassingen.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Belangrijkste kenmerken {#key-features}

- **Interactieve Tijdstappen:**  
  Gebruik pijltoetsen of spin-knoppen om de tijdwaarde te verhogen of verlagen.

- **Aanpasbare Spin-eenheid:**  
  Kies welk deel van de tijd moet worden gewijzigd met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Opties zijn onder andere `HOUR`, `MINUTE`, `SECOND` en `MILLISECOND`.

- **Min/Max Grenzen:**  
  Erf ondersteuning voor minimale en maximale toegestane tijden met behulp van `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**  
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedTimeField`.

### Voorbeeld: Configureer stappen per uur {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Stijlen {#styling}

<TableBuilder name="MaskedTimeField" />
