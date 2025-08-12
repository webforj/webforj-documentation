---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 701dcaccf198fbf507d1cd19c4bd995d
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

De `MaskedTextField`-component is bedoeld om een configureerbaar en gemakkelijk te valideren tekstinvoer te leveren. Het is goed geschikt voor apps die geformatteerde invoer vereisen, zoals financiële, e-commerce en gezondheidszorgapps.

## Basis {#basics}

De `MaskedTextField` kan worden geïnstalleerd met of zonder parameters. Je kunt een initiële waarde, een label, een plaatsaanduidingstekst en een listener definiëren voor het geval de waarde verandert.

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - bijvoorbeeld: SAVE-2025")
```

## Masker regels {#mask-rules}

De `MaskedTextField` formatteert tekstinvoer met behulp van een masker - een string die definieert welke tekens op elke positie zijn toegestaan. Dit zorgt voor consistente, gestructureerde invoer voor zaken als telefoonnummers, postcodes en ID-formaten.

### Ondersteunde maskerkarakters {#supported-mask-characters}

| Karakter | Beschrijving                                                                                  |
|----------|---------------------------------------------------------------------------------------------|
| `X`      | Elk afdrukbaar teken                                                                         |
| `a`      | Elk alfabetisch teken (hoofdletters of kleine letters)                                        |
| `A`      | Elk alfabetisch teken; kleine letters worden omgezet in hoofdletters                        |
| `0`      | Elk cijfer (0–9)                                                                             |
| `z`      | Elk cijfer of letter (hoofdletters of kleine letters)                                        |
| `Z`      | Elk cijfer of letter; kleine letters worden omgezet in hoofdletters                         |

Alle andere tekens in het masker worden behandeld als letterlijke tekens en moeten precies worden getypt. 
Bijvoorbeeld, een masker zoals `XX@XX` vereist dat de gebruiker een `@` in het midden invoert.

- **Ongeldige tekens** worden stilletjes genegeerd.
- **Korte invoer** wordt aangevuld met spaties.
- **Lange invoer** wordt afgekapt om in het masker te passen.

### Voorbeelden {#examples}

```java
field.setMask("(000) 000-0000");     // Voorbeeld: (123) 456-7890
field.setMask("A00 000");            // Voorbeeld: A1B 2C3 (Canadese postcode)
field.setMask("ZZZZ-0000");          // Voorbeeld: ABCD-1234
field.setMask("0000-0000-0000-0000");// Voorbeeld: 1234-5678-9012-3456
```

:::tip Volledige invoer toegestaan
Als het masker alleen `X` bevat, gedraagt het veld zich als een standaard [`TextField`](../text-field.md), waarbij elke afdrukbare invoer is toegestaan.
Dit is nuttig als je de mogelijkheid wilt voorbehouden om te formatteren zonder strikte tekenregels toe te passen.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validatiepatronen {#validation-patterns}

Hoewel maskers de structuur van de invoer definiëren, kun je ze combineren met validatiepatronen om meer specifieke invoerregels af te dwingen. Dit voegt een extra laag client-side validatie toe met behulp van reguliere expressies.

Gebruik de `setPattern()`-methode om een aangepaste reguliere expressie toe te passen:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Dwingt een 10-teken alfanumerieke code af
```

Dit zorgt ervoor dat de invoer niet alleen overeenkomt met het masker, maar ook voldoet aan een gedefinieerde structuur, zoals lengte of toegestane tekens.

Dit is vooral nuttig wanneer:

- Het masker te veel flexibiliteit toestaat
- Je exact lengte of een specifiek formaat wilt afdwingen (bijv. hex, Base64, UUID)

:::tip Reguliere expressieformaat
Het patroon moet een geldige [JavaScript-reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals gebruikt door het `RegExp`-type. Je kunt meer details vinden in de [documentatie van het HTML-patroonattribuut](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Herstellen van de waarde {#restoring-the-value}

De `MaskedTextField` bevat een herstelfunctie die de waarde van het veld reset naar een vooraf gedefinieerde of originele staat. 
Dit kan nuttig zijn om wijzigingen van de gebruiker terug te draaien of terug te keren naar een standaardinvoer.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmeerbaar**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaard hersteltoets, tenzij overschreven door een gebeurtenislistener)

Je kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`. Als er geen herstelwaarde is ingesteld, zal het veld terugvallen naar de initiële waarde op het moment dat het werd weergegeven.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

De `MaskedTextFieldSpinner` breidt de [`MaskedTextField`](#basics) uit door spinregels toe te voegen waarmee gebruikers door een lijst van vooraf gedefinieerde waarden kunnen bladeren. 
Dit verbetert de gebruikerservaring in situaties waarin de invoer moet worden beperkt tot een vaste set geldige opties.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Belangrijkste kenmerken {#key-features}

- **Ondersteuning voor optieslijst**  
  Vul de spinner met een lijst van geldige stringwaarden met behulp van `setOptions()`:

  ```java
  spinner.setOptions(List.of("Optie A", "Optie B", "Optie C"));
  ```

- **Programmeerbaar draaien**  
  Gebruik `spinUp()` en `spinDown()` om door opties te navigeren:

  ```java
  spinner.spinUp();   // Selecteert de volgende optie
  spinner.spinDown(); // Selecteert de vorige optie
  ```

- **Indexcontrole**  
  Stel de huidige geselecteerde index in of haal deze op met:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Masker compatibiliteit**  
  Erf volledig alle opmaak, maskerregels en patroonvalidatie van `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />
