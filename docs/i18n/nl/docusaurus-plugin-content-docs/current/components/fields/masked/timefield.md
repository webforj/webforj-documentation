---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 2631f01d383c134ba92d8ad03f5a57d3
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

De `MaskedTimeField` is een tekstinvoer die gebruikers in staat stelt om tijden in te voeren als **nummers** en automatisch de invoer te formatteren op basis van een gedefinieerde mask wanneer het veld de focus verliest. Het mask specificeert het verwachte tijdformaat, wat zowel de invoer als de weergave begeleidt. De component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel voor consistente tijdsverwerking.

<!-- INTRO_END -->

## Basis {#basics}

:::tip Op zoek naar datum invoer?
De `MaskedTimeField` is gebouwd voor **alleen tijd** invoer. Als je op zoek bent naar een component die **datums** met vergelijkbare mask-gebaseerde formatting verwerkt, kijk dan naar de [`MaskedDateField`](./datefield.md).
:::

De `MaskedTimeField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een placeholder en een gebeurtenisluisteraar voor waarde wijzigingen definiëren.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Mask regels {#mask-rules}

De `MaskedTimeField` gebruikt formaatindicatoren om te definiëren hoe tijd wordt geparsed en weergegeven. Elke formaatindicator begint met een `%` gevolgd door een letter die een tijdcomponent vertegenwoordigt.

:::tip Maskers programmatisch toepassen
Om tijden te formatteren of te parsen met dezelfde maskersyntax buiten een veld, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility klasse.
:::

### Tijd formaatindicatoren {#time-format-indicators}

| Formaat | Beschrijving         |
|---------|---------------------|
| `%H`    | Uur (24-uurs)      |
| `%h`    | Uur (12-uurs)      |
| `%m`    | Minuut              |
| `%s`    | Seconde             |
| `%p`    | AM/PM               |

### Modifiers {#modifiers}

Modifiers verfijnen de weergave van tijdcomponenten:

| Modifier | Beschrijving               |
|----------|---------------------------|
| `z`      | Zero-fill                 |
| `s`      | Korte tekstrepresentatie  |
| `l`      | Lange tekstrepresentatie   |
| `p`      | Samengevoegd nummer       |
| `d`      | Decimaal (standaard formaat)  |

Deze maken flexibele en lokale tijd formatting mogelijk.

## Tijd formaat lokalisatie {#time-format-localization}

De `MaskedTimeField` ondersteunt lokalisatie door het instellen van de juiste lokale instelling. Dit zorgt ervoor dat tijd invoer en uitvoer overeenkomen met regionale conventies.

```java
field.setLocale(Locale.GERMANY);
```

Dit heeft invloed op hoe AM/PM-indicatoren worden weergegeven, hoe scheidingstekens worden behandeld en hoe waarden worden geparsed.

## Parsing logica {#parsing-logic}

De `MaskedTimeField` parseert gebruikersinvoer op basis van het gedefinieerde tijdmask. Het accepteert zowel volledige als afgekorte numerieke invoer met of zonder scheidingstekens, wat flexibele invoer mogelijk maakt terwijl geldige tijden worden gegarandeerd. 
De parseerlogica hangt af van de volgorde van het formaat die door het mask is gedefinieerd (bijvoorbeeld, `%Hz:%mz` voor uur/minuut). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

### Voorbeeld parsing scenario's {#example-parsing-scenarios}

| Invoer | Mask          | Geïnterpreteerd als|
|--------|---------------|---------------|
| `900`  | `%Hz:%mz`     | `09:00`       |
| `1345` | `%Hz:%mz`     | `13:45`       |
| `0230` | `%hz:%mz %p`  | `02:30 AM`    |
| `1830` | `%hz:%mz %p`  | `06:30 PM`    |

## Instellen min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane tijdsbereik in een `MaskedTimeField` beperken met de `setMin()` en `setMax()` methoden:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide methoden accepteren waarden van type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedTimeField` bevat een herstelfunctie die de waarde van het veld terugzet naar een vooraf gedefinieerde of originele staat. Dit kan nuttig zijn voor het ongedaan maken van wijzigingen of het terugkeren naar een standaardtijd.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programma**matig, door `restoreValue()` aan te roepen 
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaardhersteltoets tenzij overschreven door een gebeurtenisluisteraar)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met reguliere expressies met de `setPattern()` methode:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dit patroon zorgt ervoor dat alleen waarden die voldoen aan het formaat `HH:mm` (twee cijfers, een dubbele punt, twee cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressie Formaat
Het patroon moet de JavaScript RegExp-syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over Invoerhandling
Het veld probeert numerieke tijdsinputs te parseren en te formatteren op basis van het huidige mask. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig is maar semantisch onjuist of niet te parseren (bijv. `99:99`), kan het de patrooncontroles doorstaan maar falen bij de logische validatie.
Je moet altijd de invoerwaarde in je app-logica valideren, zelfs als een reguliere expressiepatroon is ingesteld, om ervoor te zorgen dat de tijd zowel correct geformatteerd als betekenisvol is.
:::

## Tijdkiezer {#time-picker}

De `MaskedTimeField` bevat een ingebouwde tijdkiezer die gebruikers in staat stelt om een tijd visueel te selecteren, in plaats van deze te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt de tijdkiezer krijgen met `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Toon/verberg het chooser-pictogram {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het klokpictogram naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het pictogram
```

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker met het veld interactie heeft (bijv. klikt, de Enter-toets of pijltoetsen indrukt):

```java
picker.setAutoOpen(true);
```

:::tip Dwing Selectie via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een tijd selecteren met de kiezer (en niet handmatig typen), combineer de volgende twee instellingen:

```java
field.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
field.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze configuratie garandeert dat alle tijdinvoer via de gebruikersinterface van de kiezer komt, wat nuttig is wanneer je strikte formatcontrole wilt en parsingproblemen van getypte invoer wilt elimineren.
:::

### Open de kiezer handmatig {#manually-open-the-picker}

Om de tijdkiezer programmatisch te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### Stel de stap van de kiezer in {#setting-the-picker-step}

Je kunt het interval tussen selecteerbare tijden in de kiezer definiëren met `setStep()`. Dit stelt je in staat te controleren hoe fijnmazig de tijdsopties zijn—ideaal voor scenario's zoals plannen in blokken van 15 minuten.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Stapbeperking
De stap moet een gelijke verdeling zijn over een uur of een volledige dag. Anders wordt er een uitzondering opgegooid.
:::

Dit zorgt ervoor dat de dropdownlijst voorspelbare, gelijkmatig verdeelde waarden bevat zoals `09:00`, `09:15`, `09:30`, enzovoort.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

De `MaskedTimeFieldSpinner` breidt de [`MaskedTimeField`](#basics) uit door spinnerbedieningen toe te voegen waarmee gebruikers de tijd kunnen verhogen of verlagen met pijltoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktopachtige toepassingen.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Belangrijkste kenmerken {#key-features}

- **Interactief Tijdsverhoging:**
  Gebruik pijltoetsen of draaiknoppen om de tijdwaarde te verhogen of verlagen.

- **Aangepaste Spin Eenheid:**
  Kies welk onderdeel van de tijd je wilt wijzigen met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Opties zijn onder andere `HOUR`, `MINUTE`, `SECOND`, en `MILLISECOND`.

- **Min/Max Grenswaarden:**
  Erft ondersteuning voor minimale en maximale toegestane tijden met behulp van `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedTimeField`.

### Voorbeeld: Configureer stapper op uur {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Stijling {#styling}

<TableBuilder name="MaskedTimeField" />
