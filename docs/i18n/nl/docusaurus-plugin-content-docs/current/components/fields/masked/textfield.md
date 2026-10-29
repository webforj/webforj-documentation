---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 9ee372191dc17b2510f020bc908f0060
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

De `MaskedTextField` component biedt een configureerbare tekstinvoer die formatteringsregels en validatie afdwingt. Het is goed geschikt voor apps die gestructureerde invoer vereisen, zoals financiële, e-commerce- en gezondheidszorgsystemen.

Deze component kan met of zonder parameters worden geïnstantieerd. Je kunt een initiële waarde, een label, een placeholder tekst en een listener definiëren in het geval dat de waarde verandert.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - bijvoorbeeld: SAVE-2025");
```

## Maskregels {#mask-rules}

De `MaskedTextField` formatteert tekstinvoer met behulp van een masker - een tekenreeks die definieert welke tekens op elke positie zijn toegestaan. Dit zorgt voor consistente, gestructureerde invoer voor zaken zoals telefoonnummers, postcode en ID-formaten.

:::tip Maskers programmatisch toepassen
Om strings met dezelfde masker syntaxis buiten een veld te formatteren, bijvoorbeeld bij het weergeven van gegevens in een [`Table`](/docs/components/table/overview), gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility class.
:::

### Ondersteunde maskertekens {#supported-mask-characters}

| Teken     | Beschrijving                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Elk afdrukbaar teken                                                                         |
| `a`       | Elk alfabetisch teken (hoofdletters of kleine letters)                                        |
| `A`       | Elk alfabetisch teken; kleine letters worden omgezet naar hoofdletters                       |
| `0`       | Elk cijfer (0–9)                                                                             |
| `z`       | Elk cijfer of letter (hoofdletters of kleine letters)                                        |
| `Z`       | Elk cijfer of letter; kleine letters worden omgezet naar hoofdletters                        |

Alle andere tekens in het masker worden als letterlijke tekens behandeld en moeten exact worden getypt. 
Bijvoorbeeld, een masker zoals `XX@XX` vereist dat de gebruiker een `@` in het midden invoert.

- **Ongeldige tekens** worden stilletjes genegeerd.
- **Korte invoer** wordt opgevuld met spaties.
- **Lange invoer** wordt ingekort om in het masker te passen.

### Voorbeelden {#examples}

```java
field.setMask("(000) 000-0000");     // Voorbeeld: (123) 456-7890
field.setMask("A0A 0A0");            // Voorbeeld: A1B 2C3 (Canadese postcode)
field.setMask("ZZZZ-0000");          // Voorbeeld: ABCD-1234
field.setMask("0000-0000-0000-0000");// Voorbeeld: 1234-5678-9012-3456
```

:::tip Volledige invoer toegestaan
Als het masker alleen `X` bevat, gedraagt het veld zich als een standaard [`TextField`](../textfield), wat elke afdrukbare invoer toestaat. Dit is handig wanneer je de mogelijkheid wilt reserveren om te formatteren zonder strikte tekenregels toe te passen.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validatiepatronen {#validation-patterns}

Terwijl maskers de structuur van de invoer definiëren, kun je ze combineren met validatiepatronen om specifieke invoerregels af te dwingen. Dit voegt een extra laag van client-side validatie toe met behulp van reguliere expressies.

Gebruik de `setPattern()` methode om een aangepast regulier expressie toe te passen:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Dwingt een 10-teken alfanumerieke code af
```

Dit zorgt ervoor dat de invoer niet alleen overeenkomt met het masker, maar ook voldoet aan een gedefinieerde structuur, zoals lengte of toegestane tekens.

Dit is vooral nuttig wanneer:

- Het masker te veel flexibiliteit toelaat
- Je een exacte lengte of een specifiek formaat wilt afdwingen (bijv. hex, Base64, UUID)

:::tip Formaat van reguliere expressie
Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals gebruikt door het `RegExp` type. Je kunt meer details vinden in de [HTML patroon attribuut documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Het waarde herstellen {#restoring-the-value}

De `MaskedTextField` bevat een herstelfunctie die de waarde van het veld reset naar een gedefinieerde of oorspronkelijke staat. Dit kan handig zijn voor het ongedaan maken van gebruikerswijzigingen of om terug te keren naar een standaard invoer.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmamatig**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaard hersteltoets, tenzij overschreven door een event listener)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`. Als er geen herstelwaarde is ingesteld, stellen zowel `restoreValue()` als <kbd>ESC</kbd> de veldwaarde in op `null`.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

De `MaskedTextFieldSpinner` breidt de `MaskedTextField` uit door spinners te voegen die gebruikers in staat stellen om door een lijst van vooraf gedefinieerde waarden te cycled. Dit verbetert de gebruikerservaring in situaties waar de invoer moet worden beperkt tot een vaste set geldige opties.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Belangrijkste kenmerken {#key-features}

- **Optielijst ondersteuning**
  Vul de spinner met een lijst van geldige tekenreeksen met `setOptions()`:

  ```java
  spinner.setOptions(List.of("Optie A", "Optie B", "Optie C"));
  ```

- **Programmerend draaien**
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
  Erf volledig alle opmaak-, maskerregels en patroonvalidatie van `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />
