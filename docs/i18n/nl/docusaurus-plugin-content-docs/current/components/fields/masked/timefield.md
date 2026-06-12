---
title: MaskedTimeField
sidebar_position: 20
_i18n_hash: 97e5bc068e72cfd770c26fed4ceca434
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-timefield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTimeField" top='true'/>

De `MaskedTimeField` is een tekstinvoer waarmee gebruikers tijden kunnen invoeren als **getallen** en automatisch de invoer opmaakt op basis van een gedefinieerde masker wanneer het veld de focus verliest. De masker specificeert het verwachte tijdformaat, wat zowel de invoer als de weergave begeleidt. De component ondersteunt flexibele parsering, validatie, lokalisatie en waardeherstel voor consistente tijdsafhandeling.

<!-- INTRO_END -->

## Basisprincipes {#basics}

:::tip Op zoek naar datum invoer?
De `MaskedTimeField` is ontworpen voor **tijd-only** invoer. Als je een component zoekt om **datums** met een vergelijkbare masker-gebaseerde opmaak te behandelen, kijk dan naar de [`MaskedDateField`](./datefield.md).
:::

De `MaskedTimeField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een plaatsaanduiding en een gebeurtenislijster voor waarde wijzigingen definiëren.

<ComponentDemo
path='/webforj/maskedtimefield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldView.java']}
height='120px'
/>

## Masker regels {#mask-rules}

De `MaskedTimeField` gebruikt formaatindicatoren om te definiëren hoe tijd wordt geparsed en weergegeven. Elke formaatindicator begint met een `%` gevolgd door een letter die een tijdcomponent vertegenwoordigt.

:::tip Maskers programmatically toepassen
Om tijden met dezelfde masker syntaxis buiten een veld op te maken of te parseren, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility klasse.
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
| `z`      | Null vullen                |
| `s`      | Korte tekst representatie  |
| `l`      | Lange tekst representatie   |
| `p`      | Samengevoegd getal        |
| `d`      | Decimaal (standaardformaat)|

Deze zorgen voor flexibele en lokale tijdopmaak.

## Tijd formaat lokalisatie {#time-format-localization}

De `MaskedTimeField` ondersteunt lokalisatie door de juiste locale in te stellen. Dit zorgt ervoor dat tijdinvoer en -uitvoer overeenkomen met regionale conventies.

```java
field.setLocale(Locale.GERMANY);
```

Dit beïnvloedt hoe AM/PM-indicatoren worden weergegeven, hoe scheidingstekens worden behandeld en hoe waarden worden geparsed.

## Parseringslogica {#parsing-logic}

De `MaskedTimeField` parseert gebruikersinvoer op basis van de gedefinieerde tijdmasker. Het accepteert zowel volledige als verkorte numerieke invoer met of zonder scheidingstekens, wat flexibele invoer mogelijk maakt terwijl geldige tijden worden gewaarborgd.
De parseringsgedrag hangt af van de volgorde van het formaat zoals gedefinieerd door de masker (bijv. `%Hz:%mz` voor uur/minuut). Dit formaat bepaalt hoe numerieke reeksen worden geïnterpreteerd.

### Voorbeeld parseringsscenario's {#example-parsing-scenarios}

| Invoer  | Masker         | Geïnterpreteerd Als |
|---------|----------------|---------------------|
| `900`   | `%Hz:%mz`      | `09:00`             |
| `1345`  | `%Hz:%mz`      | `13:45`             |
| `0230`  | `%hz:%mz %p`   | `02:30 AM`          |
| `1830`  | `%hz:%mz %p`   | `06:30 PM`          |

## Instellen min/max beperkingen {#setting-minmax-constraints}

Je kunt het toegestane tijdsbereik in een `MaskedTimeField` beperken met de methoden `setMin()` en `setMax()`:

```java
field.setMin(LocalTime.of(8, 0));
field.setMax(LocalTime.of(18, 0));
```

Beide methoden accepteren waarden van het type [`java.time.LocalTime`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/LocalTime.html). Invoeren buiten het gedefinieerde bereik worden als ongeldig beschouwd.

## Waarde herstellen {#restoring-the-value}

De `MaskedTimeField` bevat een herstelfunctie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. Dit kan nuttig zijn om wijzigingen ongedaan te maken of terug te keren naar een standaardtijd.

```java
field.setRestoreValue(LocalTime.of(12, 0));
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatig**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard hersteltoets tenzij overschreven door een gebeurtenislijster)

<ComponentDemo
path='/webforj/maskedtimefieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldRestoreView.java']}
height='120px'
/>

## Validatiepatronen {#validation-patterns}

Je kunt client-side validatieregels toepassen met behulp van reguliere expressies met de methoden `setPattern()`:

```java
field.setPattern("^\\d{2}:\\d{2}$");
```

Dit patroon zorgt ervoor dat alleen waarden die overeenkomen met het `HH:mm` formaat (twee cijfers, dubbele punt, twee cijfers) als geldig worden beschouwd.

:::tip Reguliere Expressie Formaat
Het patroon moet voldoen aan de JavaScript RegExp-syntaxis zoals gedocumenteerd [hier](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions).
:::

:::warning Opmerkingen over Invoerverwerking
Het veld probeert numerieke tijdinvoer te parseren en op te maken op basis van de huidige masker. Gebruikers kunnen echter nog steeds handmatig waarden invoeren die niet overeenkomen met het verwachte formaat. Als de invoer syntactisch geldig is maar semantisch onjuist of niet parseerbaar is (bijv. `99:99`), kan het patrooncontroles doorstaan maar de logische validatie mislukken.
Je moet altijd de invoerwaarde in je app-logica valideren, zelfs als er een regulier expressiepatroon is ingesteld, om ervoor te zorgen dat de tijd zowel correct geformatteerd als betekenisvol is.
:::

## Tijdkiezer {#time-picker}

De `MaskedTimeField` bevat een ingebouwde tijdkiezer waarmee gebruikers een tijd visueel kunnen selecteren, in plaats van deze in te voeren. Dit verbetert de bruikbaarheid voor minder technische gebruikers of wanneer nauwkeurige invoer vereist is.

<ComponentDemo
path='/webforj/maskedtimefieldpicker'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldPickerView.java']}
height='450px'
/>

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

Je kunt de kiezer configureren om automatisch te openen wanneer de gebruiker interactie heeft met het veld (bijv. klikt, Enter of pijltoetsen indrukt):

```java
picker.setAutoOpen(true);
```

:::tip Selectie Dwingen via de Kiezer
Om ervoor te zorgen dat gebruikers alleen een tijd kunnen selecteren met de kiezer (en niet handmatig typen), combineer je de volgende twee instellingen:

```java
field.getPicker().setAutoOpen(true); // Opent de kiezer bij gebruikersinteractie
field.setAllowCustomValue(false);    // Schakelt handmatige tekstinvoer uit
```

Deze opstelling garandeert dat alle tijdinvoer via de kiezer UI komt, wat nuttig is wanneer je strikte formatcontrole wilt en parseringsproblemen van ingetikte invoer wilt uitsluiten.
:::

### Handmatig de kiezer openen {#manually-open-the-picker}

Om de tijdkiezer programmatig te openen:

```java
picker.open();
```

Of gebruik de alias:

```java
picker.show(); // hetzelfde als open()
```

### Instellen van de stap van de kiezer {#setting-the-picker-step}

Je kunt het interval tussen selecteerbare tijden in de kiezer definiëren met `setStep()`. Dit stelt je in staat om te controleren hoe gedetailleerd de tijdopties zijn - ideaal voor scenario's zoals planning in blokken van 15 minuten.

```java
field.getPicker().setStep(Duration.ofMinutes(15));
```

:::warning Stap Beperking
De stap moet een uur of een volledige dag gelijkmatig verdelen. Anders wordt er een uitzondering opgegooid.
:::

Dit zorgt ervoor dat de dropdownlijst voorspelbare, gelijkmatig verdeelde waarden bevat zoals `09:00`, `09:15`, `09:30`, enzovoorts.

## `MaskedTimeFieldSpinner` {#maskedtimefieldspinner}

De `MaskedTimeFieldSpinner` breidt de [`MaskedTimeField`](#basics) uit door spinnerbesturingselementen toe te voegen waarmee gebruikers de tijd kunnen verhogen of verlagen met behulp van pijltoetsen of UI-knoppen. Het biedt een meer geleide interactiestijl, vooral nuttig in desktopachtige toepassingen.

<ComponentDemo
path='/webforj/maskedtimefieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtimefield/MaskedTimeFieldSpinnerView.java']}
height='450px'
/>

### Belangrijkste functies {#key-features}

- **Interactief Tijdstappen:**  
  Gebruik pijltoetsen of spinknoppen om de tijdwaarde te verhogen of te verlagen.

- **Aanpasbare Spin Eenheid:**  
  Kies welke tijdscomponent je wilt wijzigen met `setSpinField()`:

```java
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.MINUTE);
```

Opties zijn onder andere `HOUR`, `MINUTE`, `SECOND`, en `MILLISECOND`.

- **Min/Max Grenzen:**  
  Erft ondersteuning voor minimale en maximale toegestane tijden met behulp van `setMin()` en `setMax()`.

- **Geformatteerde Uitvoer:**  
  Volledig compatibel met maskers en lokalisatie-instellingen van `MaskedTimeField`.

### Voorbeeld: Configureren van stappen per uur {#example-configure-stepping-by-hour}

```java
MaskedTimeFieldSpinner spinner = new MaskedTimeFieldSpinner();
spinner.setSpinField(MaskedTimeFieldSpinner.SpinField.HOUR);
```

## Styling {#styling}

<TableBuilder name="MaskedTimeField" />
