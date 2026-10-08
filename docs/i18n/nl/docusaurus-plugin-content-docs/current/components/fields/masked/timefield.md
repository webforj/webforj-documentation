---
title: MaskedTimeField
sidebar_position: 20
description: >-
  Capture time input with the MaskedTimeField, applying 12 or 24-hour masks,
  format indicators, locale-aware parsing, and validation.
_i18n_hash: 07256952a84572a67b1fe2b66dd5b5a5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

De `MaskedTimeField` is een tekstinvoer waarmee gebruikers tijden kunnen invoeren als **nummers** en automatisch de invoer op basis van een gedefinieerde maskering formatteert wanneer het veld zijn focus verliest. Het masker specificeert het verwachte tijdsformaat, en helpt zowel bij de invoer als bij de weergave. De component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstel voor consistente tijdsbehandeling.

<!-- INTRO_END -->

:::tip Op zoek naar een datum invoer?
De `MaskedTimeField` is gebouwd voor **alleen-tijd** invoer. Als je op zoek bent naar een component om **data** met een vergelijkbare masker-gebaseerde opmaak te verwerken, kijk dan naar de [`MaskedDateField`](/docs/components/fields/masked/datefield).
:::

De `MaskedTimeField` kan met of zonder parameters worden geïnitialiseerd. Je kunt een initiële waarde, een label, een placeholder en een gebeurtenisluisteraar voor waarde- wijzigingen definiëren.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Masker regels {#mask-rules}

De `MaskedTimeField` gebruikt formaatindicaties om te definiëren hoe tijd wordt geparsed en weergegeven. Elke formaatindicatie begint met een `%` gevolgd door een letter die een tijdcomponent vertegenwoordigt.

:::tip Maskers programmatically toepassen
Om tijden met dezelfde masker syntaxis buiten een veld te formatteren of te parsen, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) hulpprogrammaklasse.
:::

### Tijd formaat indicaties {#time-format-indicators}

| Formaat | Beschrijving         |
|---------|---------------------|
| `%H`    | Uur (24-uurs)      |
| `%h`    | Uur (12-uurs)      |
| `%m`    | Minuten             |
| `%s`    | Seconden            |
| `%p`    | AM/PM               |

### Modifiers {#modifiers}

Modifiers verfijnen de weergave van tijdcomponenten:

| Modifier | Beschrijving               |
|----------|---------------------------|
| `z`      | Voorloop nul              |
| `s`      | Korte tekstrepresentatie   |
| `l`      | Lange tekstrepresentatie    |
| `p`      | Samengepakt nummer         |
| `d`      | Decimaal (standaardformaat) |

Deze bieden flexibele en locale-vriendelijke tijdopmaak.

## Tijd formaat lokalisatie {#time-format-localization}

De `MaskedTimeField` ondersteunt lokalisatie door de juiste locale in te stellen. Dit zorgt ervoor dat tijdinvoer en -uitvoer overeenkomen met regionale conventies.

```java
field.setLocale(Locale.GERMANY);
```

Dit heeft invloed op hoe AM/PM-indicatoren worden weergegeven, hoe scheidingstekens worden behandeld en hoe waarden worden geparsed.

## Parsing logica {#parsing-logic}

De `MaskedTimeField` parseert gebruikersinvoer op basis van het gedefinieerde tijdmasker. Het accepteert zowel volledige als afgekorte numerieke invoer met of zonder scheidingstekens, wat flexibele invoer mogelijk maakt terwijl geldige tijden worden gegarandeerd. De parsinggedrag hangt af van de volgorde van het formaat dat door het masker is gedefinieerd (bijv. `%Hz:%mz` voor uur/minuut). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

### Voorbeeld parsing scenario's {#example-parsing-scenarios}

| Invoer  | Masker         | Geïnterpreteerd Als|
|---------|----------------|--------------------|
| `900`   | `%Hz:%mz`      | `09:00`            |
| `1345`  | `%Hz:%mz`      | `13:45`            |
| `0230`  | `%hz:%mz %p`   | `02:30 AM`         |
| `1830`  | `%hz:%mz %p`   | `06:30 PM`         |

## Instellen van min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane tijdsbereik in een `MaskedTimeField` beperken met de `setMin()` en `setMax()` methoden:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide methoden accepteren waarden van het type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Invoeren buiten het gedefinieerde bereik worden als ongeldig beschouwd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedTimeField` bevat een herstelfunctie die de waarde van het veld reset naar een vooraf gedefinieerde of originele staat. Dit kan nuttig zijn om wijzigingen ongedaan te maken of om terug te keren naar een standaardtijd.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatically**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaardhersteltoets tenzij vervangen door een gebeurtenisluisteraar)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validatie patronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met reguliere expressies met de `setPattern()` methode:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `HH:mm` formaat (twee cijfers, dubbele punt, twee cijfers) als geldig worden beschouwd.

:::tip Formaat van Reguliere Expressie
Het patroon moet de JavaScript RegExp-syntaxis volgen zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Notities over Input Afhandeling
Het veld probeert numerieke tijdsinputs te parseren en formatteren op basis van het huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet aan het verwachte formaat voldoen. Als de invoer syntactisch geldig maar semantisch onjuist of onparseerbaar is (bijv. `99:99`), kan deze patrooncontroles doorstaan maar de logische validatie mislukken. Je moet altijd de invoerwaarde in je app-logica valideren, zelfs als een reguliere expressie patroon is ingesteld, om ervoor te zorgen dat de tijd zowel correct is opgemaakt als betekenisvol is.
:::

## Tijdkiezer {#time-picker}

De `MaskedTimeField` bevat een ingebouwde tijdkiezer waarmee gebruikers een tijd visueel kunnen selecteren, in plaats van deze in te voeren. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt de tijdkiezer openen met `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Toon/verberg het icoon van de kiezer {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het klokicoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker interactie heeft met het veld (bijv. klikt, Enter of pijltjestoetsen indrukt):

```java
picker.setAutoOpen(true);
```

:::tip Dwing Selectie via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een tijd kunnen selecteren met de kiezer (en niet handmatig typen), combineer de volgende twee instellingen:

```java
field.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
field.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opzet garandeert dat alle tijdinvoer via de kiezer UI komt, wat nuttig is wanneer je strikte formatcontrole wilt en parserproblemen van getypte invoer wilt elimineren.
:::

### Handmatig de kiezer openen {#manually-open-the-picker}

Om de tijdkiezer programatisch te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // zelfde als open()
```

### Instellen van de stap van de kiezer {#setting-the-picker-step}

Je kunt het interval tussen selecteerbare tijden in de kiezer definiëren met `setStep()`. Dit stelt je in staat om te controleren hoe gedetailleerd de tijdsopties zijn—ideaal voor scenario's zoals plannen in blokken van 15 minuten.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Stap Beperking
De stap moet een gehele uur of een volledige dag gelijkmatig verdelen. Anders wordt er een uitzondering gegenereerd.
:::

Dit zorgt ervoor dat de dropdownlijst voorspelbare, gelijkmatig verdeelde waarden bevat zoals `09:00`, `09:15`, `09:30`, enz.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

De `MaskedTimeFieldSpinner` breidt de `MaskedTimeField` uit door spindomeinen toe te voegen waarmee gebruikers de tijd kunnen verhogen of verlagen met behulp van pijltjestoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktopachtige applicaties.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Belangrijkste kenmerken {#key-features}

- **Interactieve Tijdstapsgewijze:**
  Gebruik pijltjestoetsen of spindrukknoppen om de tijdwaarde te verhogen of te verlagen.

- **Aanpasbare Spin-eenheid:**
  Kies welk deel van de tijd je wilt wijzigen met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Opties zijn onder andere `HOUR`, `MINUTE`, `SECOND` en `MILLISECOND`.

- **Min/Max Grenzen:**
  Erft ondersteuning voor minimum en maximum toegestane tijden met `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**
  Volledig compatibel met masks en lokalisatie-instellingen van `MaskedTimeField`.

### Voorbeeld: Configureer stappen per uur {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
