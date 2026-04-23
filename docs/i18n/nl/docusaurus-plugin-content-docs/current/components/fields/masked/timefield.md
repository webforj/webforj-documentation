---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: bfaa13bee2b1c6dd1c88c8af989a6532
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

Het `MaskedTimeField` is een tekstinvoer waarmee gebruikers tijden als **getallen** kunnen invoeren en automatisch de invoer opmaakt op basis van een gedefinieerd masker wanneer het veld de focus verliest. Het masker specificeert het verwachte tijdformaat en leidt zowel de invoer als de weergave. De component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel voor consistente tijdsbehandeling.

<!-- INTRO_END -->

## Basisprincipes {#basics}

:::tip Op zoek naar datum invoer?
Het `MaskedTimeField` is bedoeld voor **tijd-only** invoer. Als je op zoek bent naar een component die **datums** met een vergelijkbare opmaak op basis van maskers kan verwerken, bekijk dan de [`MaskedDateField`](./datefield.md).
:::

Het `MaskedTimeField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een tijdelijke aanduiding en een gebeurtenisluisteraar voor waarde wijzigingen definiëren.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskerregels {#mask-rules}

Het `MaskedTimeField` gebruikt formaatindicatoren om te definiëren hoe tijd wordt geparsed en weergegeven. Elke formaatindicator begint met een `%` gevolgd door een letter die een tijdcomponent vertegenwoordigt.

:::tip Maskers programmatically toepassen
Om tijden met dezelfde masker syntaxis buiten een veld te formatteren of te parsen, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility klasse.
:::

### Tijdformaat indicatoren {#time-format-indicators}

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
|----------|----------------------------|
| `z`      | Voorraad met nullen        |
| `s`      | Korte tekstweergave        |
| `l`      | Lange tekstweergave        |
| `p`      | Samengedrukte nummer       |
| `d`      | Decimaal (standaard formaat)|

Deze staan flexibele en regionale-tijd formatering toe.

## Tijdformaat lokalisatie {#time-format-localization}

Het `MaskedTimeField` ondersteunt lokalisatie door de juiste locale in te stellen. Dit zorgt ervoor dat tijdinvoer en uitvoer overeenkomen met regionale gewoonten.

```java
field.setLocale(Locale.GERMANY);
```

Dit heeft invloed op hoe AM/PM-indicatoren worden weergegeven, hoe scheidingstekens worden behandeld en hoe waarden worden geparsed.

## Parsinglogica {#parsing-logic}

Het `MaskedTimeField` parseert gebruikersinvoer op basis van het gedefinieerde tijdmasker. Het accepteert zowel complete als afgekorte numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is terwijl geldige tijden worden gegarandeerd. Het parseren gedrag hangt af van de formaatvolgorde die door het masker is gedefinieerd (bijv. `%Hz:%mz` voor uur/minuut). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

### Voorbeeld parseringsscenario's {#example-parsing-scenarios}

| Invoer  | Masker         | Geïntpreteerd als|
|---------|----------------|------------------|
| `900`   | `%Hz:%mz`      | `09:00`          |
| `1345`  | `%Hz:%mz`      | `13:45`          |
| `0230`  | `%hz:%mz %p`   | `02:30 AM`       |
| `1830`  | `%hz:%mz %p`   | `06:30 PM`       |

## Instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane tijdsbereik in een `MaskedTimeField` beperken met de methoden `setMin()` en `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide methoden accepteren waarden van het type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Invoeren buiten het gedefinieerde bereik worden als ongeldig beschouwd.

## Herstellen van de waarde {#restoring-the-value}

Het `MaskedTimeField` bevat een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. Dit kan nuttig zijn om wijzigingen ongedaan te maken of terug te keren naar een standaardtijd.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Wijzen om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatig**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard hersteltoets, tenzij overschreven door een gebeurtenisluisteraar)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met behulp van reguliere expressies met de methode `setPattern()`:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dit patroon zorgt ervoor dat alleen waarden die voldoen aan het `HH:mm` formaat (twee cijfers, dubbele punt, twee cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressie Formaat
Het patroon moet volgen volgens de JavaScript RegExp-syntaxis zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over Invoerverwerking
Het veld probeert numerieke tijdinvoeren te parseren en formatteren op basis van het huidige masker. Echter, gebruikers kunnen nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig is maar semantisch onjuist of onparseerbaar is (bijv. `99:99`), kan deze patrooncontroles doorstaan maar logische validatie niet.
Je moet altijd de invoerwaarde in je applicatielogica valideren, zelfs als er een reguliere expressiepatroon is ingesteld, om ervoor te zorgen dat de tijd zowel correct opgemaakt als betekenisvol is.
:::

## Tijdkiezer {#time-picker}

Het `MaskedTimeField` omvat een ingebouwde tijdkiezer waarmee gebruikers visueel een tijd kunnen selecteren in plaats van deze in te typen. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt toegang krijgen tot de tijdkiezer met `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Toon/verberg het icoon van de kiezer {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het klokicoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Auto-open gedrag {#auto-open-behavior}

Je kunt de kiezer zo configureren dat deze automatisch opent wanneer de gebruiker met het veld interageert (bijv. klikt, op Enter of pijltoetsen drukt):

```java
picker.setAutoOpen(true);
```

:::tip Dwingen tot Selectie Via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een tijd selecteren met behulp van de kiezer (en niet handmatig typen), combineer je de volgende twee instellingen:

```java
field.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
field.setAllowCustomValue(false);    // Deactiveert handmatige tekstinvoer
```

Deze opzet garandeert dat alle tijdinvoer via de kieser UI komt, wat nuttig is wanneer je strikt formatbeheer wilt en parseringsproblemen van getypte invoer wilt uitsluiten.
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

### Instellen van de stap van de kiezer {#setting-the-picker-step}

Je kunt het interval tussen selecteerbare tijden in de kiezer definiëren met `setStep()`. Dit stelt je in staat om te controleren hoe gedetailleerd de tijd opties zijn—ideaal voor scenario's zoals plannen in blokken van 15 minuten.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Stap Beperking
De stap moet een gelijkmatige deler zijn van een uur of een hele dag. Anders zal er een uitzondering worden opgegooid.
:::

Dit zorgt ervoor dat de vervolgkeuzelijst voorspelbare, regelmatig verdeelde waarden bevat zoals `09:00`, `09:15`, `09:30`, enz.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

De `MaskedTimeFieldSpinner` breidt [`MaskedTimeField`](#basics) uit door spinbesturingselementen toe te voegen waarmee gebruikers de tijd kunnen verhogen of verlagen met behulp van pijltoetsen of UI-knoppen. Het biedt een meer begeleide interactiestijl, vooral nuttig in desktop-achtige applicaties.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Belangrijkste kenmerken {#key-features}

- **Interactief Tijd Stapelen:**  
  Gebruik pijltoetsen of draaimiddelen om de tijdwaarde te verhogen of verlagen.

- **Aanpasbare Draai Eenheid:**  
  Kies welk onderdeel van de tijd je wilt wijzigen met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Opties zijn `HOUR`, `MINUTE`, `SECOND` en `MILLISECOND`.

- **Min/Max Grenzen:**  
  Erfelijke ondersteuning voor minimum en maximum toegestane tijden met `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**  
  Volledig compatibel met maskers en lokalisatie instellingen van `MaskedTimeField`.

### Voorbeeld: Configureer stapeling per uur {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Stijling {#styling}

<TableBuilder name="MaskedTimeField" />
