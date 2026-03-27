---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: b910fd6dedb911a21f3d37b17658c2cc
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

De `MaskedTextField`-component biedt een configureerbaar tekstinvoer die formatteringsregels en validatie afdwingt. Het is bijzonder geschikt voor apps die gestructureerde input vereisen, zoals financiële, e-commerce en gezondheidszorgsystemen.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De `MaskedTextField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initieel waarde, een label, een placeholdertekst en een listener definiëren voor het geval de waarde verandert.

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - bijvoorbeeld: SAVE-2025")
```

## Maskregels {#mask-rules}

De `MaskedTextField` formatteert tekstinvoer met behulp van een masker - een string die definieert welke karakters op elke positie zijn toegestaan. Dit zorgt voor consistente, gestructureerde input voor zaken zoals telefoonnummers, postcodes en identiteitsformaten.

### Ondersteunde maskerkarakters {#supported-mask-characters}

| Karakter  | Beschrijving                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Elk afdrukbaar teken                                                                        |
| `a`       | Elke alfabetische teken (hoofdletters of kleine letters)                                     |
| `A`       | Elke alfabetische teken; kleine letters worden omgezet naar hoofdletters                    |
| `0`       | Elk cijfer (0–9)                                                                            |
| `z`       | Elk cijfer of letter (hoofdletters of kleine letters)                                       |
| `Z`       | Elk cijfer of letter; kleine letters worden omgezet naar hoofdletters                       |

Alle andere tekens in het masker worden behandeld als literals en moeten exact worden getypt. 
Bijvoorbeeld, een masker zoals `XX@XX` vereist dat de gebruiker een `@` in het midden invoert.

- **Ongeldige karakters** worden stilzwijgend genegeerd.
- **Korte invoer** wordt opgevuld met spaties.
- **Lange invoer** wordt afgeknipt om in het masker te passen.

### Voorbeelden {#examples}

```java
field.setMask("(000) 000-0000");     // Voorbeeld: (123) 456-7890
field.setMask("A00 000");            // Voorbeeld: A1B 2C3 (Canadese postcode)
field.setMask("ZZZZ-0000");          // Voorbeeld: ABCD-1234
field.setMask("0000-0000-0000-0000");// Voorbeeld: 1234-5678-9012-3456
```

:::tip Volledige invoer toegestaan
Als het masker alleen `X` bevat, gedraagt het veld zich als een standaard [`TextField`](../textfield), waarbij elke afdrukbare invoer is toegestaan.
Dit is handig wanneer je de mogelijkheid wilt behouden om te formatteren zonder strikte karakterregels toe te passen.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validatiepatronen {#validation-patterns}

Terwijl maskers de structuur van de invoer definiëren, kun je ze combineren met validatiepatronen om meer specifieke invoerregels af te dwingen. Dit voegt een extra laag van client-side validatie toe met behulp van reguliere expressies.

Gebruik de `setPattern()`-methode om een aangepast reguliere expressie toe te passen:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Dwingt een alfanumerieke code van 10 tekens af
```

Dit zorgt ervoor dat de invoer niet alleen overeenkomt met het masker, maar ook voldoet aan een gedefinieerde structuur, zoals lengte of toegestane karakters.

Dit is vooral nuttig wanneer:

- Het masker te veel flexibiliteit toestaat
- Je exact lengte of een specifiek formaat wilt afdwingen (bijv. hex, Base64, UUID)

:::tip Reguliere expressie-indeling
Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals gebruikt door het `RegExp`-type. Je kunt meer details vinden in de [HTML-patroonattributen documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Het herstellen van de waarde {#restoring-the-value}

De `MaskedTextField` bevat een hersteloptie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. 
Dit kan nuttig zijn om wijzigingen van de gebruiker ongedaan te maken of terug te keren naar een standaardinvoer.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatig**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaardhersteltoets, tenzij overschreven door een eventlistener)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`. Als er geen herstelwaarde is ingesteld, zal het veld terugkeren naar de initiële waarde op het moment dat het werd weergegeven.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

De `MaskedTextFieldSpinner` breidt [`MaskedTextField`](#basics) uit door spinnercontroles toe te voegen waarmee gebruikers kunnen cykelen door een lijst met vooraf gedefinieerde waarden. 
Dit verbetert de gebruikerservaring in situaties waar de invoer beperkt moet worden tot een vastgestelde set geldige opties.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Belangrijkste kenmerken {#key-features}

- **Optielijstondersteuning**  
  Vul de spinner met een lijst van geldige tekenreekswaarden met `setOptions()`:

  ```java
  spinner.setOptions(List.of("Optie A", "Optie B", "Optie C"));
  ```

- **Programmatistisch draaien**  
  Gebruik `spinUp()` en `spinDown()` om door opties te bewegen:

  ```java
  spinner.spinUp();   // Selecteert de volgende optie
  spinner.spinDown(); // Selecteert de vorige optie
  ```

- **Indexcontrole**  
  Stel de huidige selectie-index in of haal deze op met:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskercompatibiliteit**  
  Erf volledig alle formattering, maskregels en patroonvalidatie van `MaskedTextField`.

## Stijlen {#styling}

<TableBuilder name="MaskedTextField" />
