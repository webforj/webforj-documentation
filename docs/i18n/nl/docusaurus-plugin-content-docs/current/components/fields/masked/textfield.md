---
title: MaskedTextField
sidebar_position: 15
description: >-
  Enforce formatted text entry with the MaskedTextField, supporting mask
  characters for digits, letters, and literals for IDs and codes.
_i18n_hash: 5f6c175ffd4b8d75f3b65c7b77bb13fe
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

De `MaskedTextField` component biedt een configureerbare tekstinvoer die opmaakregels en validatie afdwingt. Het is goed geschikt voor apps die gestructureerde invoer vereisen, zoals financiële systemen, e-commerce en gezondheidszorg.

Deze component kan worden geïnstantieerd met of zonder parameters. Je kunt een iniële waarde, een label, een voorbeeldtekst en een luisteraar definiëren in geval de waarde verandert.

<!-- INTRO_END -->

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - bijv.: SAVE-2025")
```

## Maskregels {#mask-rules}

De `MaskedTextField` formatteert tekstinvoer met een mask - een string die definieert welke tekens op elke positie zijn toegestaan. Dit zorgt voor consistente, gestructureerde invoer voor zaken als telefoonnummers, postcodes en ID-formaten.

:::tip Toepassen van masks via programmering
Om strings met dezelfde mask-syntaxis buiten een veld te formatteren, bijvoorbeeld wanneer gegevens worden weergegeven in een [`Table`](/docs/components/table/overview), gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility-klasse.
:::

### Ondersteunde masktekens {#supported-mask-characters}

| Teken     | Beschrijving                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Elk afdrukbaar teken                                                                        |
| `a`       | Elk alfabetisch teken (hoofdletter of kleine letter)                                         |
| `A`       | Elk alfabetisch teken; kleine letters worden omgezet naar hoofdletters                      |
| `0`       | Elk cijfer (0–9)                                                                            |
| `z`       | Elk cijfer of letter (hoofdletter of kleine letter)                                         |
| `Z`       | Elk cijfer of letter; kleine letters worden omgezet naar hoofdletters                       |

Alle andere tekens in het mask worden behandeld als letterlijke tekens en moeten precies worden getypt.
Bijvoorbeeld, een mask zoals `XX@XX` vereist dat de gebruiker een `@` in het midden invoert.

- **Ongeldige tekens** worden stilzwijgend genegeerd.
- **Korte invoer** wordt opgevuld met spaties.
- **Lange invoer** wordt afgekapt om in het mask te passen.

### Voorbeelden {#examples}

```java
field.setMask("(000) 000-0000");     // Voorbeeld: (123) 456-7890
field.setMask("A00 000");            // Voorbeeld: A1B 2C3 (Canadese postcode)
field.setMask("ZZZZ-0000");          // Voorbeeld: ABCD-1234
field.setMask("0000-0000-0000-0000");// Voorbeeld: 1234-5678-9012-3456
```

:::tip Volledige invoer toegestaan
Als het mask alleen `X` bevat, gedraagt het veld zich als een standaard [`TextField`](../textfield), wat elke afdrukbare invoer toelaat.
Dit is nuttig wanneer je de mogelijkheid wilt behouden om te formatteren zonder strikte tekenregels toe te passen.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validatiepatronen {#validation-patterns}

Terwijl masks de structuur van de invoer definiëren, kun je ze combineren met validatiepatronen om meer specifieke invoerregels af te dwingen. Dit voegt een extra laag client-side validatie toe met behulp van reguliere expressies.

Gebruik de `setPattern()` methode om een aangepaste reguliere expressie toe te passen:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Dwingt een 10-teken alfanumerieke code af
```

Dit zorgt ervoor dat de invoer niet alleen overeenkomt met het mask, maar ook voldoet aan een gedefinieerde structuur, zoals lengte of toegestane tekens.

Dit is vooral nuttig wanneer:

- Het mask te veel flexibiliteit toestaat
- Je een exacte lengte of een specifiek formaat wilt afdwingen (bijv. hex, Base64, UUID)

:::tip Formaat van reguliere expressie
Het patroon moet een geldige [JavaScript reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals gebruikt door het `RegExp` type. Je kunt meer details vinden in de [HTML patroonattributen documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Herstellen van de waarde {#restoring-the-value}

De `MaskedTextField` omvat een herstelfunctie die de waarde van het veld reset naar een vooraf gedefinieerde of originele staat.
Dit kan nuttig zijn om gebruikerswijzigingen terug te draaien of terug te keren naar een standaardinvoer.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmerend**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaardhersteltoets tenzij overschreven door een gebeurtenisluisteraar)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`. Als er geen herstelwaarde is ingesteld, keert het veld terug naar de initiële waarde op het moment dat het werd weergegeven.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

De `MaskedTextFieldSpinner` breidt de `MaskedTextField` uit door spinnerbesturingselementen toe te voegen waarmee gebruikers door een lijst van vooraf gedefinieerde waarden kunnen bladeren.
Dit verbetert de gebruikerservaring in situaties waarin de invoer moet worden beperkt tot een vaste set van geldige opties.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Belangrijkste functies {#key-features}

- **Ondersteuning voor optieslijst**
  Vul de spinner met een lijst van geldige stringwaarden met `setOptions()`:

  ```java
  spinner.setOptions(List.of("Optie A", "Optie B", "Optie C"));
  ```

- **Programmerend draaien**
  Gebruik `spinUp()` en `spinDown()` om door de opties te navigeren:

  ```java
  spinner.spinUp();   // Selecteert de volgende optie
  spinner.spinDown(); // Selecteert de vorige optie
  ```

- **Indexbeheer**
  Stel de huidige selectie-index in of haal deze op met:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Maskcompatibiliteit**
  Erft volledig alle opmaak, maskregels en patroonvalidatie van de `MaskedTextField`.

## Stijlen {#styling}

<TableBuilder name="MaskedTextField" />
