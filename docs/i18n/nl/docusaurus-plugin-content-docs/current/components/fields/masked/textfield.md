---
title: MaskedTextField
sidebar_position: 15
_i18n_hash: c50931f8465e3be081ecfee03a3ef559
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

De `MaskedTextField` component biedt een configureerbare tekstinvoer die opmaakregels en validatie afdwingt. Het is bijzonder geschikt voor apps die gestructureerde invoer vereisen, zoals financiële, e-commerce- en zorgsystemen.

<!-- INTRO_END -->

## Basis {#basics}

De `MaskedTextField` kan worden aangemaakt met of zonder parameters. U kunt een initiële waarde, een label, een tijdelijke tekst en een listener definiëren voor het geval de waarde verandert.

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - bijvoorbeeld: SAVE-2025")
```

## Mask regels {#mask-rules}

De `MaskedTextField` formatteert tekstinvoer met behulp van een masker - een string die definieert welke tekens op elke positie zijn toegestaan. Dit zorgt voor consistente, gestructureerde invoer voor zaken als telefoonnummers, postcodes en ID-formaten.

:::tip Masks programmatisch toepassen
Om strings met dezelfde maskersyntaxis buiten een veld te formatteren, bijvoorbeeld bij het weergeven van gegevens in een [`Table`](/docs/components/table/overview), gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) hulpprogramma klasse.
:::

### Ondersteunde maskerkarakters {#supported-mask-characters}

| Karakter | Beschrijving                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Elk afdrukbaar teken                                                                       |
| `a`       | Elk alfabetisch teken (hoofdletters of kleine letters)                                       |
| `A`       | Elk alfabetisch teken; kleine letters worden omgezet in hoofdletters                        |
| `0`       | Elk cijfer (0–9)                                                                           |
| `z`       | Elk cijfer of letter (hoofdletters of kleine letters)                                       |
| `Z`       | Elk cijfer of letter; kleine letters worden omgezet in hoofdletters                         |

Alle andere tekens in het masker worden als literaal behandeld en moeten exact worden getypt. 
Bijvoorbeeld, een masker zoals `XX@XX` vereist dat de gebruiker een `@` in het midden invoert.

- **Ongeldige tekens** worden stilletjes genegeerd.
- **Korte invoer** wordt aangevuld met spaties.
- **Lange invoer** wordt ingekort om in het masker te passen.

### Voorbeelden {#examples}

```java
field.setMask("(000) 000-0000");     // Voorbeeld: (123) 456-7890
field.setMask("A00 000");            // Voorbeeld: A1B 2C3 (Canadese postcode)
field.setMask("ZZZZ-0000");          // Voorbeeld: ABCD-1234
field.setMask("0000-0000-0000-0000");// Voorbeeld: 1234-5678-9012-3456
```

:::tip Volledige invoer toegestaan
Als het masker alleen `X` bevat, gedraagt het veld zich als een standaard [`TextField`](../textfield), waardoor elke afdrukbare invoer mogelijk is.
Dit is nuttig wanneer u de mogelijkheid wilt reserveren om te formatteren zonder strikte tekenregels toe te passen.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validatiepatronen {#validation-patterns}

Terwijl maskers de structuur van de invoer definiëren, kunt u ze combineren met validatiepatronen om meer specifieke invoerregels af te dwingen. Dit voegt een extra laag client-side validatie toe met behulp van reguliere expressies.

Gebruik de `setPattern()`-methode om een aangepaste reguliere expressie toe te passen:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Dwingt een 10-teken alfanumerieke code af
```

Dit zorgt ervoor dat de invoer niet alleen overeenkomt met het masker, maar ook voldoet aan een gedefinieerde structuur, zoals lengte of toegestane tekens.

Dit is vooral nuttig wanneer:

- Het masker te veel flexibiliteit toelaat
- U een exacte lengte of een specifiek formaat (bijv. hex, Base64, UUID) wilt afdwingen

:::tip Reguliere Expressie Formaat
Het patroon moet een geldige [JavaScript regulier expressie](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions) zijn, zoals gebruikt door het `RegExp` type. U kunt meer details vinden in de [HTML patroon attribuut documentatie](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## De waarde herstellen {#restoring-the-value}

De `MaskedTextField` bevat een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde of oorspronkelijke staat. 
Dit kan nuttig zijn voor het ongedaan maken van gebruikerswijzigingen of terugkeren naar een standaard invoer.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatig**, door `restoreValue()` aan te roepen
- **Via het toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaard hersteltoets, tenzij overschreven door een gebeurtenislifter)

U kunt de waarde die moet worden hersteld instellen met `setRestoreValue()`. Als er geen herstelwaarde is ingesteld, zal het veld terugkeren naar de initiële waarde op het moment dat het werd gerenderd.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

De `MaskedTextFieldSpinner` breidt de [`MaskedTextField`](#basics) uit door spinnerbesturingselementen toe te voegen waarmee gebruikers door een lijst van vooraf gedefinieerde waarden kunnen bladeren. 
Dit verbetert de gebruikerservaring in situaties waarin de invoer moet worden beperkt tot een vaste set geldige opties.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Belangrijke kenmerken {#key-features}

- **Optielijst ondersteuning**  
  Vul de spinner met een lijst van geldige stringwaarden met behulp van `setOptions()`:

  ```java
  spinner.setOptions(List.of("Optie A", "Optie B", "Optie C"));
  ```

- **Programmatisch spinnen**  
  Gebruik `spinUp()` en `spinDown()` om door opties te bewegen:

  ```java
  spinner.spinUp();   // Selecteert de volgende optie
  spinner.spinDown(); // Selecteert de vorige optie
  ```

- **Index controle**  
  Stel de huidige selectie-index in of haal deze op met:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Masker compatibiliteit**  
  Erf volledig alle opmaak-, maskerregels en patroonvalidatie van `MaskedTextField`.

## Stijl {#styling}

<TableBuilder name="MaskedTextField" />
