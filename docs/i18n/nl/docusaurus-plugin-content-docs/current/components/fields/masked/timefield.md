---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: e50a52f19876f98eec1bd825ca82cd6a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

De `MaskedTimeField` is een tekstinvoerveld ontworpen voor nauwkeurige, gestructureerde tijdinvoer. Het stelt gebruikers in staat om tijden als **getallen** in te voeren en formatteert automatisch de invoer op basis van een gedefinieerde maskering wanneer het veld de focus verliest. De maskering is een string die het verwachte tijdformaat specificeert, ter begeleiding van zowel invoer als weergave.

Deze component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel. Het is vooral nuttig in tijdgevoelige formulieren zoals schema's, urenstaten en reserveringen.

:::tip Op zoek naar datum invoer?
De `MaskedTimeField` is gebouwd voor **enkel tijd** invoer. Als je op zoek bent naar een component om **datums** met een soortgelijke op masker gebaseerde opmaak te verwerken, kijk dan naar de [`MaskedDateField`](./datefield.md).
:::

## Basisprincipes {#basics}

De `MaskedTimeField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een tijdelijke aanduiding en een evenementlistener voor waarde wijzigingen definiëren.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskerregels {#mask-rules}

De `MaskedTimeField` gebruikt formateringsindicatoren om te definiëren hoe tijd wordt geparsed en weergegeven. Elke formateringsindicator begint met een `%` gevolgd door een letter die een tijdcomponent vertegenwoordigt.

### Tijdformaatindicatoren {#time-format-indicators}

| Formaat | Beschrijving         |
|---------|----------------------|
| `%H`    | Uur (24-uurs)       |
| `%h`    | Uur (12-uurs)       |
| `%m`    | Minuut              |
| `%s`    | Seconde             |
| `%p`    | AM/PM               |

### Modifiers {#modifiers}

Modifiers verfijnen de weergave van tijdcomponenten:

| Modifier | Beschrijving               |
|----------|----------------------------|
| `z`      | Voorloopnullen            |
| `s`      | Korte tekstweergave       |
| `l`      | Lange tekstweergave       |
| `p`      | Samengevoegd nummer       |
| `d`      | Decimaal (standaard formaat)|

Deze zorgen voor flexibele en locale-vriendelijke tijdopmaak.

## Lokalisatie van tijdformaat {#time-format-localization}

De `MaskedTimeField` ondersteunt lokalisatie door de juiste locale in te stellen. Dit zorgt ervoor dat tijdinvoer en -uitvoer overeenkomen met regionale conventies.

```java
field.setLocale(Locale.GERMANY);
```

Dit beïnvloedt hoe AM/PM-indicatoren worden weergegeven, hoe scheidingstekens worden behandeld en hoe waarden worden geparsed.

## Parsinglogica {#parsing-logic}

De `MaskedTimeField` parseert gebruikersinvoer op basis van de gedefinieerde tijdmaskering. Het accepteert zowel complete als afgekorte numerieke invoer met of zonder scheidingstekens, wat flexibele invoer mogelijk maakt terwijl geldige tijden worden gegarandeerd.
Het parseergedrag hangt af van de volgorde van het formaat dat door de maskering is gedefinieerd (bijv. `%Hz:%mz` voor uur/minuut). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

### Voorbeeld van parsingscenario's {#example-parsing-scenarios}

| Invoer  | Masker       | Geïnterpreteerd als |
|---------|--------------|----------------------|
| `900`   | `%Hz:%mz`    | `09:00`              |
| `1345`  | `%Hz:%mz`    | `13:45`              |
| `0230`  | `%hz:%mz %p` | `02:30 AM`           |
| `1830`  | `%hz:%mz %p` | `06:30 PM`           |

## Minimale/maximale beperkingen instellen {#setting-minmax-constraints}

Je kunt de toegestane tijdsbereik in een `MaskedTimeField` beperken met de methoden `setMin()` en `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide methoden accepteren waarden van het type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Invoeren buiten het gedefinieerde bereik worden als ongeldig beschouwd.

## Waardeherstel {#restoring-the-value}

De `MaskedTimeField` bevat een herstelfunctie die de waarde van het veld terugzet naar een vooraf gedefinieerde of oorspronkelijke staat. Dit kan nuttig zijn om wijzigingen ongedaan te maken of terug te keren naar een standaardtijd.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatic**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaardhersteltoets, tenzij overschreven door een evenementlistener)

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

:::tip Formaat van reguliere expressies
Het patroon moet voldoen aan de JavaScript RegExp-syntaxis zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over invoerhandling
Het veld probeert numerieke tijdinvoeren te parseren en op te maken op basis van de huidige maskering. Echter, gebruikers kunnen nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig maar semantisch onjuist of onparseerbaar is (bijv. `99:99`), kan het patroonchecks doorstaan maar faalt in logische validatie.
Je moet de invoerwaarde altijd valideren in je app-logica, zelfs als er een regulier expressiepatroon is ingesteld, om ervoor te zorgen dat de tijd zowel correct is opgemaakt als betekenisvol.
:::

## Tijdkiezer {#time-picker}

De `MaskedTimeField` bevat een ingebouwde tijdkiezer die gebruikers in staat stelt om visueel een tijd te selecteren, in plaats van deze in te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt de tijdkiezer krijgen met `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Toon/verberg het icoon van de kiezer {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het klokicoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de kiezer zo configureren dat deze automatisch opent wanneer de gebruiker interactie heeft met het veld (bijv. klikt, op Enter of pijltjestoetsen drukt):

```java
picker.setAutoOpen(true);
```

:::tip Dwingen tot selectie via de kiezer
Om ervoor te zorgen dat gebruikers alleen een tijd kunnen selecteren met behulp van de kiezer (en niet handmatig typen), combineer de volgende twee instellingen:

```java
field.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
field.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opzet garandeert dat alle tijdinvoer via de picker UI komt, wat nuttig is wanneer je strikte formaten wilt controleren en parseringsproblemen van getypte invoer wilt elimineren.
:::

### Handmatig de kiezer openen {#manually-open-the-picker}

Om de tijdkiezer programmatisch te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### Stapgrootte van de kiezer instellen {#setting-the-picker-step}

Je kunt het interval tussen selecteerbare tijden in de kiezer definiëren met `setStep()`. Dit stelt je in staat om te controleren hoe gedetailleerd de tijdopties zijn—ideaal voor scenario's zoals plannen in blokken van 15 minuten.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Stapbeperking
De stap moet gelijkmatig een uur of een volle dag verdelen. Anders wordt er een uitzondering gegooid.
:::

Dit zorgt ervoor dat de dropdownlijst voorspelbare, gelijkmatig verdeelde waarden bevat zoals `09:00`, `09:15`, `09:30`, enzovoort.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

De `MaskedTimeFieldSpinner` breidt [`MaskedTimeField`](#basics) uit door spinnerbesturingselementen toe te voegen waarmee gebruikers de tijd kunnen verhogen of verlagen met behulp van pijltjestoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktopachtige toepassingen.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Belangrijkste kenmerken {#key-features}

- **Interactieve tijdstap:**  
  Gebruik de pijltjestoetsen of spin-knoppen om de tijdwaarde te verhogen of verlagen.

- **Aanpasbare spin-eenheid:**  
  Kies welk onderdeel van de tijd je wilt wijzigen met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Opties omvatten `HOUR`, `MINUTE`, `SECOND` en `MILLISECOND`.

- **Min/Max-grenzen:**  
  Erf ondersteuning voor minimale en maximale toegestane tijden met `setMin()` en `setMax()`.

- **Geformatteerde uitvoer:**  
  Volledig compatibel met maskeringen en lokalisatie-instellingen van `MaskedTimeField`.

### Voorbeeld: Stap configureren per uur {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Stijlen {#styling}

<TableBuilder name="MaskedTimeField" />
