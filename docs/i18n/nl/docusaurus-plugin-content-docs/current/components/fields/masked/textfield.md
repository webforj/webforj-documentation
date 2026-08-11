---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 10866226b1025c8c4c0a28499d46de38
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

De `MaskedTextField` component biedt een configureerbare tekstinvoer die formatteerregels en validatie af enforced. Het is goed geschikt voor apps die gestructureerde invoer vereisen, zoals financiële, e-commerce en zorgsystemen.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De `MaskedTextField` kan worden geïnstantieerd met of zonder parameters. Je kunt een initiële waarde, een label, een placeholder-tekst en een listener definiëren in geval de waarde verandert.

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - bijvoorbeeld: SAVE-2025")
```

## Mask regels {#mask-rules}

De `MaskedTextField` formatteert tekstinvoer met behulp van een masker - een tekenreeks die definieert welke tekens op elke positie zijn toegestaan. Dit zorgt voor consistente, gestructureerde invoer voor dingen zoals telefoonnummers, postcodes en ID-formaten.

:::tip Programma-matig maskers toepassen
Om tekenreeksen met dezelfde maskersyntaxis buiten een veld te formatteren, bijvoorbeeld wanneer gegevens in een [`Table`](/docs/components/table/overview) worden weergegeven, gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) hulpprogrammaklas.
:::

### Ondersteunde masker-karakters {#supported-mask-characters}

| Karakter  | Beschrijving                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Elk afdrukbaar teken                                                                       |
| `a`       | Elk alfabetisch teken (hoofdletter of kleine letter)                                         |
| `A`       | Elk alfabetisch teken; kleine letters worden omgezet naar hoofdletters                      |
| `0`       | Elk cijfer (0–9)                                                                            |
| `z`       | Elk cijfer of letter (hoofdletter of kleine letter)                                          |
| `Z`       | Elk cijfer of letter; kleine letters worden omgezet naar hoofdletters                       |

Alle andere tekens in het masker worden als letterlijke tekens behandeld en moeten precies worden getypt.
Bijvoorbeeld, een masker zoals `XX@XX` vereist dat de gebruiker een `@` in het midden invoert.

- **Ongeldige tekens** worden stilzwijgend genegeerd.
- **Korte invoer** wordt opgevuld met spaties.
- **Lange invoer** wordt ingekort om in het masker te passen.

### Voorbeelden {#examples}

```java
field.setMask("(000) 000-0000");     // Voorbeeld: (123) 456-7890
field.setMask("A00 000");            // Voorbeeld: A1B 2C3 (Canadese postcode)
field.setMask("ZZZZ-0000");          // Voorbeeld: ABCD-1234
field.setMask("0000-0000-0000-0000");// Voorbeeld: 1234-5678-9012-3456
```

:::tip Volledige invoer toegestaan
Als het masker alleen `X` bevat, gedraagt het veld zich als een standaard [`TextField`](../textfield), wat elke afdrukbare invoer mogelijk maakt.
Dit is nuttig wanneer je de mogelijkheid wilt behouden om te formatteren zonder strikte karakterregels toe te passen.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validatiepatronen {#validation-patterns}

Terwijl maskers de structuur van de invoer definiëren, kun je ze combineren met validatiepatronen om specifiekere invoerregels af te dwingen. Dit voegt een extra laag van client-side validatie toe met behulp van reguliere expressies.

Gebruik de `setPattern()` methode om een aangepaste reguliere expressie toe te passen:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Dwingt een alfanumerieke code van 10 tekens af
```

Dit zorgt ervoor dat de invoer niet alleen overeenkomt met het masker, maar ook voldoet aan een gedefinieerde structuur, zoals lengte of toegestane tekens.

Dit is vooral nuttig wanneer:

- Het masker te veel flexibiliteit toestaat
- Je exact lengte of een specifiek formaat wilt afdwingen (bijvoorbeeld hex, Base64, UUID)

:::tip Formaat van reguliere expressie
Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals gebruikt door het `RegExp` type. Je kunt meer details vinden in de [HTML patroon attribuut documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## De waarde herstellen {#restoring-the-value}

De `MaskedTextField` bevat een herstel functie die de waarde van het veld terugzet naar een vooraf gedefinieerde of oorspronkelijke staat.
Dit kan nuttig zijn voor het ongedaan maken van gebruikerswijzigingen of het terugkeren naar een standaardinvoer.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programma-matig**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaardhersteltoets, tenzij overschreven door een gebeurtenislistener)

Je kunt de waarde om te herstellen instellen met `setRestoreValue()`. Als er geen herstelwaarde is ingesteld, gaat het veld terug naar de initiële waarde op het moment dat het werd weergegeven.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

De `MaskedTextFieldSpinner` breidt [`MaskedTextField`](#basics) uit door spinners te toevoegen waarmee gebruikers door een lijst van vooraf gedefinieerde waarden kunnen cycled.
Dit verbetert de gebruikerservaring in situaties waar de invoer moet worden beperkt tot een vaste set van geldige opties.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Belangrijkste functies {#key-features}

- **Optielijstondersteuning**
  Vul de spinner met een lijst van geldige tekenwaarden met `setOptions()`:

  ```java
  spinner.setOptions(List.of("Optie A", "Optie B", "Optie C"));
  ```

- **Programma-matig draaien**
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
  Erf volledig alle opmaak, maskerregels en patroonvalidatie van `MaskedTextField`.

## Stijling {#styling}

<TableBuilder name="MaskedTextField" />
