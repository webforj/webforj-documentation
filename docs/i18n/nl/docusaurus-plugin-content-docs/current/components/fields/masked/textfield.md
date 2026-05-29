---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: 8ef566720a30ba07ae47b5a957804c52
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

De `MaskedTextField`-component biedt een configureerbaar tekstinvoerveld dat formatteringsregels en validatie afdwingt. Het is goed geschikt voor apps die gestructureerde invoer vereisen, zoals financiële, e-commerce- en gezondheidszorgsystemen.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De `MaskedTextField` kan met of zonder parameters worden geïnstantieerd. Je kunt een initiële waarde, een label, een tijdelijke tekst en een listener definiëren voor het geval de waarde verandert.

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - bijvoorbeeld: SAVE-2025")
```

## Maskerregels {#mask-rules}

De `MaskedTextField` formatteert tekstinvoer met behulp van een masker - een string die definieert welke tekens op elke positie zijn toegestaan. Dit zorgt voor consistente, gestructureerde invoer voor dingen zoals telefoonnummers, postcodes en ID-formaten.

:::tip Maskers programmatisch toepassen
Om strings met dezelfde maskersyntaxis buiten een veld te formatteren, bijvoorbeeld bij het weergeven van gegevens in een [`Table`](/docs/components/table/overview), gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility class.
:::

### Ondersteunde maskerkarakters {#supported-mask-characters}

| Karakter | Beschrijving                                                                                 |
|----------|---------------------------------------------------------------------------------------------|
| `X`      | Elk afdrukbaar teken                                                                         |
| `a`      | Elk alfabetisch teken (hoofdletters of kleine letters)                                       |
| `A`      | Elk alfabetisch teken; kleine letters worden omgezet naar hoofdletters                      |
| `0`      | Elk cijfer (0–9)                                                                            |
| `z`      | Elk cijfer of letter (hoofdletters of kleine letters)                                       |
| `Z`      | Elk cijfer of letter; kleine letters worden omgezet naar hoofdletters                       |

Alle andere karakters in het masker worden als letterlijke waarden behandeld en moeten exact worden getypt. 
Bijvoorbeeld, een masker zoals `XX@XX` vereist dat de gebruiker een `@` in het midden invoert.

- **Ongeldige karakters** worden stilletjes genegeerd.
- **Korte invoer** wordt opgevuld met spaties.
- **Lange invoer** wordt afgekapt om in het masker te passen.

### Voorbeelden {#examples}

```java
field.setMask("(000) 000-0000");     // Voorbeeld: (123) 456-7890
field.setMask("A00 000");            // Voorbeeld: A1B 2C3 (Canadese postcode)
field.setMask("ZZZZ-0000");          // Voorbeeld: ABCD-1234
field.setMask("0000-0000-0000-0000");// Voorbeeld: 1234-5678-9012-3456
```

:::tip Volledige invoer toegestaan
Als het masker alleen `X` bevat, gedraagt het veld zich als een standaard [`TextField`](../textfield), waardoor elke afdrukbare invoer is toegestaan.
Dit is nuttig wanneer je de mogelijkheid wilt behouden om te formatteren zonder strikte tekeneisen toe te passen.
:::

<ComponentDemo
path='/webforj/maskedtextfield'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java']}
height='250px'
/>

## Validatiepatronen {#validation-patterns}

Terwijl maskers de structuur van de invoer definiëren, kun je ze combineren met validatiepatronen om specifiekere invoerregels af te dwingen. Dit voegt een extra laag client-side validatie toe met behulp van reguliere expressies.

Gebruik de `setPattern()`-methode om een aangepaste reguliere expressie toe te passen:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Dwingt een 10-teken alfanumerieke code af
```

Dit zorgt ervoor dat de invoer niet alleen het masker volgt, maar ook voldoet aan een gedefinieerde structuur, zoals lengte of toegestane tekens.

Dit is vooral nuttig wanneer:

- Het masker te veel flexibiliteit toestaat
- Je een exacte lengte of een specifiek formaat (bijvoorbeeld hex, Base64, UUID) wilt afdwingen

:::tip Reguliere expressieformaat
Het patroon moet een geldige [JavaScript-reguliere expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals gebruikt door het `RegExp`-type. Meer details zijn te vinden in de [HTML-patroonattribuut documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Herstellen van de waarde {#restoring-the-value}

De `MaskedTextField` bevat een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde of originele staat. 
Dit kan nuttig zijn voor het ongedaan maken van wijzigingen van de gebruiker of het terugzetten naar een standaardinvoer.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatig**, door `restoreValue()` aan te roepen
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard-hersteltoets tenzij overschreven door een event listener)

Je kunt de waarde om te herstellen instellen met `setRestoreValue()`. Als er geen herstelwaarde is ingesteld, zal het veld terugkeren naar de initiële waarde op het moment dat het werd weergegeven.

<ComponentDemo
path='/webforj/maskedtextfieldrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java']}
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

De `MaskedTextFieldSpinner` breidt [`MaskedTextField`](#basics) uit door spinnerbesturingselementen toe te voegen waarmee gebruikers door een lijst van vooraf gedefinieerde waarden kunnen bladeren. 
Dit verbetert de gebruikerservaring in situaties waarin de invoer beperkt moet zijn tot een vaste set geldige opties.

<ComponentDemo
path='/webforj/maskedtextfieldspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java']}
height='120px'
/>

### Belangrijkste kenmerken {#key-features}

- **Optielijstondersteuning**  
  Vul de spinner met een lijst van geldige stringwaarden met `setOptions()`:

  ```java
  spinner.setOptions(List.of("Optie A", "Optie B", "Optie C"));
  ```

- **Programmatisch draaien**  
  Gebruik `spinUp()` en `spinDown()` om door opties te bladeren:

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
  Erf alle opmaak, maskerregels en patroonvalidatie volledig van `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />
