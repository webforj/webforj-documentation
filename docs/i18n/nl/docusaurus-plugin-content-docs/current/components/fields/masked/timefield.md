---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 17c5f6ce7fa234dbeb848c4bcab41e60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

De `MaskedTimeField` is een tekstinvoer waarmee gebruikers tijden als **nummers** kunnen invoeren en de invoer automatisch wordt opgemaakt op basis van een gedefinieerde maskering wanneer het veld de focus verliest. Het masker specificeert het verwachte tijdformaat, dat zowel de invoer als weergave helpt. De component ondersteunt flexibele parsing, validatie, lokalisatie en waardeherstellen voor consistente tijdsafhandeling.

<!-- INTRO_END -->

## Basisprincipes {#basics}

:::tip Op zoek naar invoer voor datums?
De `MaskedTimeField` is ontworpen voor **enkel tijd** invoer. Als je op zoek bent naar een component om **datums** met vergelijkbare maskergebaseerde opmaak te verwerken, kijk dan naar de [`MaskedDateField`](./datefield.md).
:::

De `MaskedTimeField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een plaatsaanduiding en een eventlistener voor waarde veranderen definiëren.

<ComponentDemo path='/webforj/maskedtimefield?' javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java' height='120px'/>

## Maskerregels {#mask-rules}

De `MaskedTimeField` gebruikt formaatindicatoren om te definiëren hoe tijd wordt geparsed en weergegeven. Elke formaatindicator begint met een `%` gevolgd door een letter die een tijdcomponent vertegenwoordigt.

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
| `z`      | Null-vulling               |
| `s`      | Korte tekstrepresentatie   |
| `l`      | Lange tekstrepresentatie    |
| `p`      | Gecomprimeerd nummer       |
| `d`      | Decimaal (standaardformaat)|

Deze maken flexibele en plaatselijke tijdsopmaak mogelijk.

## Lokalisatie van tijdformaat {#time-format-localization}

De `MaskedTimeField` ondersteunt lokalisatie door de juiste locale in te stellen. Dit zorgt ervoor dat tijdinvoer en -uitvoer overeenkomen met regionale conventies.

```java
field.setLocale(Locale.GERMANY);
```

Dit heeft invloed op hoe AM/PM-indicatoren worden weergegeven, hoe scheidingstekens worden behandeld en hoe waarden worden geparsed.

## Parsinglogica {#parsing-logic}

De `MaskedTimeField` parseert gebruikersinvoer op basis van het gedefinieerde tijdmasker. Het accepteert zowel complete als afgebroken numerieke invoer met of zonder scheidingstekens, waardoor flexibele invoer mogelijk is, terwijl ervoor wordt gezorgd dat het geldige tijden zijn.
De parseringsgedrag hangt af van de formele volgorde die door het masker is gedefinieerd (bijvoorbeeld `%Hz:%mz` voor uur/minuut). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

### Voorbeeld van parse-scenario's {#example-parsing-scenarios}

| Invoer  | Masker        | Geïnterpreteerd Als |
|---------|---------------|---------------------|
| `900`   | `%Hz:%mz`     | `09:00`             |
| `1345`  | `%Hz:%mz`     | `13:45`             |
| `0230`  | `%hz:%mz %p`  | `02:30 AM`          |
| `1830`  | `%hz:%mz %p`  | `06:30 PM`          |

## Min/max-beperkingen instellen {#setting-minmax-constraints}

Je kunt het toegestane tijdsbereik in een `MaskedTimeField` beperken met de methoden `setMin()` en `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide methoden accepteren waarden van het type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Invoer buiten het gedefinieerde bereik wordt als ongeldig beschouwd.

## Waardeherstel {#restoring-the-value}

De `MaskedTimeField` bevat een herstelfunctie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. Dit kan nuttig zijn om wijzigingen ongedaan te maken of om terug te keren naar een standaardtijd.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programma-technisch**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaardhersteltoets, tenzij overschreven door een eventlistener)

<ComponentDemo 
path='/webforj/maskedtimefieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java' 
height='120px'/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met reguliere expressies met de methode `setPattern()`:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `HH:mm` formaat (twee cijfers, dubbele punt, twee cijfers) als geldig worden beschouwd.

:::tip Reguliere expressieformaat
Het patroon moet voldoen aan de syntaxis van JavaScript RegExp zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over invoerhandling
Het veld probeert numerieke tijdinvoeren te parseren en op te maken op basis van het huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig is, maar semantisch onjuist of niet parseerbaar (bijvoorbeeld `99:99`), kan het patrooncontroles doorstaan maar foutieve logische validatie krijgen.
Je moet de invoerwaarde altijd valideren in de logica van je app, zelfs als er een regulier expressiepatroon is ingesteld, om ervoor te zorgen dat de tijd zowel correct is opgemaakt als betekenisvol.
:::

## Tijdkiezer {#time-picker}

De `MaskedTimeField` bevat een ingebouwde tijdkiezer waarmee gebruikers visueel een tijd kunnen selecteren, in plaats van deze in te voeren. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer een nauwkeurige invoer vereist is.

<ComponentDemo 
path='/webforj/maskedtimefieldpicker?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java' 
height='450px'/>

### Toegang tot de kiezer {#accessing-the-picker}

Je kunt toegang krijgen tot de tijdkiezer met `getPicker()`:

```java
TimePicker picker = field.getPicker();
```

### Toon/verberg het icon voor de kiezer {#showhide-the-picker-icon}

Gebruik `setIconVisible()` om het klokicoon naast het veld te tonen of te verbergen:

```java
picker.setIconVisible(true); // toont het icoon
```

### Automatisch openen gedrag {#auto-open-behavior}

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker met het veld interactie heeft (bijv. klikt, op Enter drukt of pijltoetsen gebruikt):

```java
picker.setAutoOpen(true);
```

:::tip Dwing selectie via de kiezer
Om ervoor te zorgen dat gebruikers alleen een tijd kunnen selecteren via de kiezer (en niet handmatig typen), combineer je de volgende twee instellingen:

```java
field.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
field.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opstelling garandeert dat alle tijdinvoer via de UI van de kiezer komt, wat nuttig is wanneer je strikte opmaakcontrole wilt en parseringsproblemen van ingevoerde tekst wilt vermijden.
:::

### Handmatig de kiezer openen {#manually-open-the-picker}

Om de tijdkiezer programmatically te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### De stap van de kiezer instellen {#setting-the-picker-step}

Je kunt het interval tussen de selecteerbare tijden in de kiezer instellen met `setStep()`. Dit stelt je in staat om te controleren hoe gedetailleerd de tijdsopties zijn—ideaal voor scenario's zoals plannen in blokken van 15 minuten.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Stapbeperkingen
De stap moet gelijkmatig een uur of een hele dag delen. Anders wordt een uitzondering opgegooid.
:::

Dit zorgt ervoor dat de dropdownlijst voorspelbare, gelijkmatig verdeelde waarden bevat zoals `09:00`, `09:15`, `09:30`, enz.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

De `MaskedTimeFieldSpinner` breidt [`MaskedTimeField`](#basics) uit door spinnerbedieningselementen toe te voegen waarmee gebruikers de tijd kunnen verhogen of verlagen met behulp van pijltoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktop-achtige toepassingen.

<ComponentDemo 
path='/webforj/maskedtimefieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java' 
height='450px'/>

### Belangrijkste functies {#key-features}

- **Interactieve Tijdstappen:**  
  Gebruik pijltoetsen of draai-knoppen om de tijdwaarde te verhogen of te verlagen.

- **Aanpasbare draai-eenheid:**  
  Kies welk onderdeel van de tijd moet worden gewijzigd met `setSpinField()`:

  ```java
  spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
  ```

  Opties zijn onder andere `HOUR`, `MINUTE`, `SECOND` en `MILLISECOND`.

- **Min/max-grenzen:**  
  Ondersteunt minimum en maximum toegestane tijden met `setMin()` en `setMax()`.

- **Geformatteerde uitvoer:**  
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedTimeField`.

### Voorbeeld: Configureren van stappen per uur {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Stylen {#styling}

<TableBuilder name="MaskedTimeField" />
