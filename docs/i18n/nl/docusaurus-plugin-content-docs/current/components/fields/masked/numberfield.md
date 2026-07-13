---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: 1ce8783919180d45f2f7321c559fc26a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

De `MaskedNumberField` is een tekstinvoer ontworpen voor gestructureerde numerieke invoer. Het zorgt ervoor dat getallen consistent worden opgemaakt op basis van een gedefinieerde masker, wat het bijzonder nuttig maakt voor financiële formulieren, prijsvakken of elke invoer waarbij precisie en leesbaarheid belangrijk zijn.

Deze component ondersteunt nummeropmaak, lokalisatie van decimale/groeperingskarakters en optionele waarde beperkingen zoals minimums of maximums.

<!-- INTRO_END -->

## Basisprincipes {#basics}

De `MaskedNumberField` kan worden geïnstantieerd met of zonder parameters. Het ondersteunt het instellen van een initiële waarde, een label, een placeholder en een gebeurtenisluisteraar om te reageren op waarde wijzigingen.

Deze demo toont een **Tip Calculator** die `MaskedNumberField` gebruikt voor intuïtieve numerieke invoer. Eén veld is geconfigureerd om een geformatteerd rekeningbedrag te accepteren, terwijl het andere een gehele getal fooipercentage vastlegt. Beide velden passen numerieke maskers toe om consistente en voorspelbare opmaak te waarborgen.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskerregels {#mask-rules}

De `MaskedNumberField` gebruikt een maskerstring om te beheersen hoe numerieke invoer wordt opgemaakt en weergegeven. Elke karakter in het masker definieert een specifieke opmaakgedrag, waardoor een nauwkeurige controle over hoe nummers verschijnen mogelijk is.

:::tip Programmatic masks toepassen
Om getallen met dezelfde masker syntaxis buiten een veld op te maken, bijvoorbeeld bij het weergeven van gegevens in een [`Table`](/docs/components/table/overview), gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) hulpkleding klasse.
:::

### Maskerkarakters {#mask-characters}

| Karakter | Beschrijving |
|-----------|-------------|
| `0`       | Wordt altijd vervangen door een cijfer (0–9). |
| `#`       | Onderdrukt voorafgaande nullen. Vervangen door het vulkarakter links van het decimale punt. Voor achtervolgende cijfers, vervangen door een spatie of nul. Anders, vervangen door een cijfer. |
| `,`       | Gebruikt als een groeperingsscheidingsteken (bijv. duizenden). Vervangen door het vulkarakter als er geen cijfers voorafgaan. Anders, wordt weergegeven als een komma. |
| `-`       | Toont een minteken (`-`) als het getal negatief is. Vervangen door het vulkarakter als het positief is. |
| `+`       | Toont `+` voor positieve of `-` voor negatieve getallen. |
| `$`       | Resultaat altijd een dollar-teken. |
| `(`       | Voegt een links haakje `(` in voor negatieve waarden. Vervangen door het vulkarakter als het positief is. |
| `)`       | Voegt een rechts haakje `)` in voor negatieve waarden. Vervangen door het vulkarakter als het positief is. |
| `CR`      | Toont `CR` voor negatieve getallen. Toont twee spaties als het getal positief is. |
| `DR`      | Toont `CR` voor negatieve getallen. Toont `DR` voor positieve getallen. |
| `*`       | Voegt een asterisk `*` in. |
| `.`       | Merkt het decimale punt aan. Als er geen cijfers in de output verschijnen, dan wordt het vervangen door het vulkarakter. Na het decimale punt worden vulkarakters behandeld als spaties. |
| `B`       | Wordt altijd een spatie. Elke andere letterlijke karakter wordt weergegeven zoals het is. |

Sommige van de bovenstaande tekens kunnen meer dan eens in het masker voorkomen voor opmaak. Deze omvatten `-`, `+`, `$`, en `(`. Als een van deze karakters in het masker aanwezig is, wordt de eerste die wordt aangetroffen verplaatst naar de laatste positie waar een `#` of `,` werd vervangen door het vulkarakter. Als er geen dergelijke positie bestaat, blijft het dubbele teken waar het is.

:::info Geen Automatische Afronding
Een masker binnen een veld doet **NIET** afronden. Bijvoorbeeld, wanneer een waarde zoals `12.34567` in een veld wordt geplaatst dat gemaskeerd is met `###0.00`, krijg je `12.34`.
:::

## Groeperings- en decimaal scheidingstekens {#group-and-decimal-separators}

De `MaskedNumberField` ondersteunt aanpassing van **groeperings** en **decimaal** karakters, waardoor het gemakkelijk is om nummeropmaak aan te passen aan verschillende locaties of zakelijke conventies.

- De **groepscheidingsteken** wordt gebruikt om duizenden visueel te scheiden (bijv. `1.000.000`).
- De **decimale scheidingsteken** geeft het fractionele deel van een getal aan (bijv. `123,45`).

Dit is nuttig in internationale toepassingen waar verschillende regio's verschillende karakters gebruiken (bijv. `.` vs `,`).

```java
field.setGroupCharacter(".");   // bijv. 1.000.000
field.setDecimalCharacter(","); // bijv. 123,45
```

:::tip Standaard Gedrag
Standaard past `MaskedNumberField` groeps- en decimale scheidingstekens toe op basis van de huidige locale van de app. Je kunt ze op elk moment overschrijven met de aangeboden setters.
:::

## Negateerbaar {#negateable}

De `MaskedNumberField` ondersteunt een optie om te controleren of negatieve getallen zijn toegestaan.

Standaard zijn negatieve waarden zoals `-123,45` toegestaan. Om dit te voorkomen, gebruik `setNegateable(false)` om de invoer tot alleen positieve waarden te beperken.

Dit is nuttig in zakelijke scenario's waar waarden zoals hoeveelheden, totaalbedragen of percentages altijd niet-negatief moeten zijn.

```java
field.setNegateable(false);
```

Wanneer `negatable` is ingesteld op `false`, blokkeert het veld elke poging om een minteken in te voeren of anders negatieve waarden in te voeren.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Min en max waarden {#min-and-max-values}

De `MaskedNumberField` ondersteunt het instellen van numerieke grenzen met `setMin()` en `setMax()`. Deze beperkingen helpen ervoor te zorgen dat de invoer van de gebruiker binnen een geldig, verwachte bereik blijft.

- **Minimale waarde**
  Gebruik `setMin()` om het laagst acceptabele getal gedefinieerd te krijgen:

  ```java
  field.setMin(10.0); // Minimale waarde: 10
  ```

  Als de gebruiker een getal onder deze drempel invoert, wordt het als ongeldig beschouwd.

- **Maximale waarde**
  Gebruik `setMax()` om het hoogste acceptabele getal gedefinieerd te krijgen:

  ```java
  field.setMax(100.0); // Maximale waarde: 100
  ```

  Waarden boven deze limiet worden als ongeldig gemarkeerd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedNumberField` ondersteunt een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde staat. Dit kan nuttig zijn wanneer gebruikers wijzigingen moeten ongedaan maken, per ongeluk aangebrachte bewerkingen willen terugdraaien of terug willen keren naar een bekende standaardwaarde.

Om dit gedrag in te schakelen, definieer de doelwaarde met `setRestoreValue()`. Wanneer nodig, kan het veld programatisch worden gereset met `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programatisch** met `restoreValue()`
- **Via toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaard hersteltoets tenzij overschreven)

De herstelwaarde moet expliciet worden ingesteld. Als deze niet gedefinieerd is, zal de functie het veld niet terugdraaien.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

De `MaskedNumberFieldSpinner` breidt [`MaskedNumberField`](#basics) uit door spinners te toevoegen die gebruikers in staat stellen de waarde te verhogen of verlagen met stapknoppen of pijltoetsen. Dit is ideaal voor invoeren zoals hoeveelheden, prijsaanpassingen, beoordelingscontroles of elke situatie waarin gebruikers incrementele aanpassingen maken.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Belangrijke kenmerken {#key-features}

- **Stap Incremente**
  Gebruik `setStep()` om te definiëren hoeveel de waarde moet veranderen met elke draai:

  ```java
  spinner.setStep(5.0); // Elke draai voegt 5 toe of trekt 5 af
  ```

- **Interactieve Controles**
  Gebruikers kunnen op spinner knoppen klikken of gebruikmaken van toetsenbordinvoer om de waarde aan te passen.

- **Alle Kenmerken van MaskedNumberField**
  Ondersteunt volledig maskers, opmaak, groeperings/decimale karakters, min/max beperkingen en herstel logica.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
