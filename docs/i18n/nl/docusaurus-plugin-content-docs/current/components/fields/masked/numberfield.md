---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: f247d28107239e0db4a24823c8a1ef60
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Het `MaskedNumberField` is een tekstinvoer ontworpen voor het consistent formatteren van numerieke invoer, op basis van een gedefinieerde mask. Het is handig voor financiële formulieren, prijsvelden, of elke invoer waar precisie en leesbaarheid belangrijk zijn.

Deze component kan worden aangemaakt met of zonder parameters. Het ondersteunt nummerformattering, lokalisatie van decimale/groeperingskarakters en optionele waarde-beperkingen zoals minimums of maximums. Het ondersteunt ook het instellen van een initiële waarde, een label, een placeholder, en een gebeurtenislistener om te reageren op waarde wijzigingen.

<!-- INTRO_END -->

Het onderstaande voorbeeld toont een **Fooi Calculator** die `MaskedNumberField` gebruikt voor intuïtieve numerieke invoer. Eén veld is geconfigureerd om een geformatteerd rekeningbedrag te accepteren, terwijl de andere een whole-number fooi percentage vastlegt.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Mask regels {#mask-rules}

Het `MaskedNumberField` gebruikt een mask string om te controleren hoe numerieke invoer wordt geformatteerd en weergegeven. Iedere karakter in het mask definieert een specifiek formatteringsgedrag, waardoor nauwkeurige controle mogelijk is over hoe nummers verschijnen.

:::tip Maskers programmatisch toepassen
Om nummers te formatteren met dezelfde maskersyntaxis buiten een veld, bijvoorbeeld bij het weergeven van gegevens in een [`Table`](/docs/components/table/overview), gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility klasse.
:::

### Masker karakteren {#mask-characters}

| Karakter | Beschrijving |
|----------|--------------|
| `0`      | Altijd vervangen door een cijfer (0–9). |
| `#`      | Onderdrukt leidende nullen. Vervangen door het opvulkaraakater aan de linkerkant van het decimaalpunt. Voor achterliggende cijfers, vervangen door een spatie of nul. Anders, vervangen door een cijfer. |
| `,`      | Gebruikt als een groeperingsscheidingsteken (bijv. duizenden). Vervangen door het opvulkaraakater als er geen cijfers voorafgaan. Anders, weergegeven als een komma. |
| `-`      | Toont een minteken (`-`) als het nummer negatief is. Vervangen door het opvulkaraakater als positief. |
| `+`      | Toont `+` voor positieve of `-` voor negatieve nummers. |
| `$`      | Resultaat in een dollar teken. |
| `(`      | Voegt een linkse haakje `(` in voor negatieve waarden. Vervangen door het opvulkaraakater als positief. |
| `)`      | Voegt een rechtse haakje `)` in voor negatieve waarden. Vervangen door het opvulkaraakater als positief. |
| `CR`     | Toont `CR` voor negatieve nummers. Toont twee spaties als het nummer positief is. |
| `DR`     | Toont `CR` voor negatieve nummers. Toont `DR` voor positieve nummers. |
| `*`      | Voegt een asterisk `*` in. |
| `.`      | Markeert het decimaal punt. Als er geen cijfers in de uitvoer verschijnen, vervangen door het opvulkaraakater. Na het decimaal, worden opvulkaraakters behandeld als spaties. |
| `B`      | Wordt altijd een spatie. Elke andere letterlijke karakter wordt weergegeven zoals het is. |

Sommige van de bovenstaande karakters kunnen meer dan eens in het mask verschijnen voor formatting. Deze omvatten `-`, `+`, `$`, en `(`. Als een van deze karakters in het mask aanwezig is, wordt de eerste die tegenkomt naar de laatste positie verplaatst waar een `#` of `,` door het opvulkaraakater is vervangen. Als er geen dergelijke positie bestaat, blijft het Dubbele karakter waar het is.

:::info Geen Automatische Afronding
Een mask binnen een veld rondt **NIET** af. Bijvoorbeeld, wanneer een waarde zoals `12.34567` in een veld met het mask `###0.00` wordt geplaatst, krijg je `12.34`.
:::

## Groep en decimaal scheidingstekens {#group-and-decimal-separators}

Het `MaskedNumberField` ondersteunt aanpassing van **groeperings** en **decimaal** karakters, waardoor het gemakkelijk is om nummerformattering aan te passen aan verschillende lokale of zakelijke conventies.

- De **groepscheiding** wordt gebruikt om duizenden visueel te scheiden (bijv. `1,000,000`).
- De **decimaal scheiding** geeft het fractionele deel van een nummer aan (bijv. `123.45`).

Dit is nuttig in internationale toepassingen waar verschillende regio's verschillende karakters gebruiken (bijv. `.` vs `,`).

```java
field.setGroupCharacter(".");   // bijv. 1.000.000
field.setDecimalCharacter(","); // bijv. 123,45
```

:::tip Standaard Gedrag
Standaard past `MaskedNumberField` groeps- en decimaal scheidingstekens toe op basis van de huidige lokale van de app. Je kunt ze op elk moment overschrijven met de provided setters.
:::

## Negateerbaar {#negateable}

Het `MaskedNumberField` ondersteunt een optie om te controleren of negatieve nummers zijn toegestaan.

Standaard zijn negatieve waarden zoals `-123.45` toegestaan. Om dit te voorkomen, gebruik `setNegateable(false)` om invoer te beperken tot alleen positieve waarden.

Dit is nuttig in zakelijke scenario's waar waarden zoals hoeveelheden, totalen, of percentages altijd niet-negatief moeten zijn.

```java
field.setNegateable(false);
```

Wanneer `negateable` is ingesteld op `false`, blokkeert het veld elke poging om een minteken in te voeren of anderszins negatieve waarden in te voeren.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Min en max waarden {#min-and-max-values}

Het `MaskedNumberField` ondersteunt het instellen van numerieke grenzen met behulp van `setMin()` en `setMax()`. Deze beperkingen helpen om ervoor te zorgen dat de invoer van de gebruiker binnen een geldige, verwachte range blijft.

- **Minimale Waarde**
  Gebruik `setMin()` om het laagste acceptabele nummer te definiëren:

  ```java
  field.setMin(10.0); // Minimale waarde: 10
  ```

  Als de gebruiker een nummer invoert dat onder deze drempel ligt, wordt het als ongeldig beschouwd.

- **Maximale Waarde**
  Gebruik `setMax()` om het hoogste acceptabele nummer te definiëren:

  ```java
  field.setMax(100.0); // Maximale waarde: 100
  ```

  Waarden boven deze limiet worden als ongeldig gemarkeerd.

## Het waarde herstellen {#restoring-the-value}

Het `MaskedNumberField` ondersteunt een herstelfunctie die de waarde van het veld naar een vooraf gedefinieerde staat reset. Dit kan nuttig zijn wanneer gebruikers wijzigingen moeten ongedaan maken, per ongeluk aangebrachte bewerkingen willen terugdraaien, of willen terugkeren naar een bekende standaardwaarde.

Om een specifieke waarde te herstellen, definieer het gebruik van `setRestoreValue()`. Wanneer nodig, kan het veld programatisch worden gereset met behulp van `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programatisch** met `restoreValue()`
- **Via toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaard hersteltoets tenzij overschreven)

Als er geen herstelwaarde is ingesteld, stellen zowel `restoreValue()` als <kbd>ESC</kbd> de waarde van het veld in op `null`.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

De `MaskedNumberFieldSpinner` breidt het `MaskedNumberField` uit door spinnerbesturingselementen toe te voegen waarmee gebruikers de waarde kunnen verhogen of verlagen met stapknoppen of pijltoetsen. Dit is ideaal voor invoer zoals hoeveelheden, prijsaanpassingen, beoordelingscontroles, of elk scenario waarin gebruikers incrementele wijzigingen aanbrengen.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Belangrijkste kenmerken {#key-features}

- **Stap Vermeerderingen**
  Gebruik `setStep()` om te definiëren hoeveel de waarde met elke draai moet veranderen:

  ```java
  spinner.setStep(5.0); // Elke draai voegt 5 toe of trekt 5 af
  ```

- **Interactieve Besturingselementen**
  Gebruikers kunnen op spinnerknoppen klikken of toetsenbordinvoer gebruiken om de waarde aan te passen.

- **Alle Kenmerken van MaskedNumberField**
  Volledig ondersteuning voor maskers, formatting, groeperings/decimaal karakters, min/max beperkingen, en herstel-logica.

## Stijling {#styling}

<TableBuilder name="MaskedNumberField" />
