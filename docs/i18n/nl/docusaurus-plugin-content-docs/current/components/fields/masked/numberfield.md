---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: a2b0a5275077beea1c053993d47aa861
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Het `MaskedNumberField` is een tekstinvoerveld ontworpen voor gestructureerde numerieke invoer. Het zorgt ervoor dat nummers consistent worden opgemaakt op basis van een gedefinieerde masker, wat het bijzonder nuttig maakt voor financiële formulieren, prijsvelden of elke invoer waar precisie en leesbaarheid van belang zijn.

Deze component ondersteunt nummeropmaak, lokalisatie van decimale/groeperingskarakters en optionele waarde beperkingen zoals minimums of maximums.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Het `MaskedNumberField` kan worden geïnstantieerd met of zonder parameters. Het ondersteunt het instellen van een initiële waarde, een label, een plaatsaanduiding en een gebeurtenisluisteraar om te reageren op waarde wijzigingen.

Deze demo toont een **Foatokcalculator** die `MaskedNumberField` gebruikt voor intuïtieve numerieke invoer. Één veld is geconfigureerd om een geformatteerd rekeningbedrag te accepteren, terwijl het andere een hele getal fooi percentage vastlegt. Beide velden passen numerieke maskers toe om consistente en voorspelbare opmaak te waarborgen.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskerregels {#mask-rules}

Het `MaskedNumberField` gebruikt een maskerstring om te controleren hoe numerieke invoer wordt opgemaakt en weergegeven. 
Elk karakter in het masker definieert een specifieke opmaakgedrag, waardoor nauwkeurige controle over hoe cijfers verschijnen mogelijk is.

:::tip Toepassen van maskers programmatisch
Om cijfers met dezelfde masker syntax buiten een veld te formatteren, bijvoorbeeld bij het weergeven van gegevens in een [`Table`](/docs/components/table/overview), gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utilityklasse.
:::

### Maskerkarakters {#mask-characters}

| Karakter | Beschrijving |
|----------|--------------|
| `0`      | Altijd vervangen door een cijfer (0–9). |
| `#`      | Onderdrukt leidende nullen. Vervangen door het vulteken aan de linkerkant van het decimale punt. Voor achterliggende cijfers vervangen door een spatie of nul. Anders, vervangen door een cijfer. |
| `,`      | Gebruik als een groeperingsscheidingsteken (bijv. duizenden). Vervangen door het vulteken als er geen cijfers aan voorafgaan. Anders, als een komma weergegeven. |
| `-`      | Weergeven een minteken (`-`) als het getal negatief is. Vervangen door het vulteken als het positief is. |
| `+`      | Weergeven `+` voor positieve of `-` voor negatieve getallen. |
| `$`      | Geeft altijd een dollar teken. |
| `(`      | Voegt een linkse haakje `(` in voor negatieve waarden. Vervangen door het vulteken als het positief is. |
| `)`      | Voegt een rechtse haakje `)` in voor negatieve waarden. Vervangen door het vulteken als het positief is. |
| `CR`     | Weergeven `CR` voor negatieve getallen. Weergeven twee spaties als het getal positief is. |
| `DR`     | Weergeven `CR` voor negatieve getallen. Weergeven `DR` voor positieve getallen. |
| `*`      | Voegt een asterisk `*` in. |
| `.`      | Merkt het decimale punt aan. Als er geen cijfers in de uitvoer verschijnen, vervangen door het vulteken. Na het decimale punt worden vultekens als spaties behandeld. |
| `B`      | Wordt altijd een spatie. Elk ander letterlijk karakter wordt weergegeven zoals het is. |

Sommige van de bovenstaande karakters kunnen meer dan eens in het masker voorkomen voor opmaak. Dit zijn `-`, `+`, `$`, en
`(`. Als een van deze karakters in het masker aanwezig is, wordt de eerste die wordt aangetroffen verplaatst naar de laatste positie waar een `#` of `,` door het vulteken is vervangen. Als er geen dergelijke positie bestaat, blijft het dubbele karakter waar het is.

:::info Geen automatische afronding
Een masker binnen een veld rondt **NIET** af. Bijvoorbeeld, wanneer een waarde zoals `12.34567` in een veld wordt geplaatst dat is gemaskeerd met `###0.00`, krijg je `12.34`.
:::

## Groep en decimale scheidingstekens {#group-and-decimal-separators}

Het `MaskedNumberField` ondersteunt aanpassing van **groeps-** en **decimale** karakters, waardoor het eenvoudig is om nummeropmaak aan te passen aan verschillende lokale of zakelijke conventies.

- De **groepscheidingsteken** wordt gebruikt om duizenden visueel te scheiden (bijv. `1.000.000`).
- De **decimale scheidingsteken** geeft het fractionele deel van een getal aan (bijv. `123.45`).

Dit is nuttig in internationale toepassingen waar verschillende gebieden verschillende karakters gebruiken (bijv. `.` vs `,`).

```java
field.setGroupCharacter(".");   // bijv. 1.000.000
field.setDecimalCharacter(","); // bijv. 123,45
```

:::tip Standaardgedrag
Standaard past `MaskedNumberField` groeps- en decimale scheidingstekens toe op basis van de huidige locale van de app. Je kunt ze op elk moment overschrijven met de gegeven setters.
:::

## Negatief {#negateable}

Het `MaskedNumberField` ondersteunt een optie om te controleren of negatieve getallen zijn toegestaan.

Standaard zijn negatieve waarden zoals `-123.45` toegestaan. Om dit te voorkomen, gebruik `setNegateable(false)` om invoer te beperken tot alleen positieve waarden.

Dit is nuttig in zakelijke scenario's waar waarden zoals hoeveelheden, totalen of percentages altijd niet-negatief moeten zijn.

```java
field.setNegateable(false);
```

Wanneer `negatable` is ingesteld op `false`, blokkeert het veld elke poging om een minteken in te voeren of op andere wijze negatieve waarden in te voeren.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Min- en maxwaarden {#min-and-max-values}

Het `MaskedNumberField` ondersteunt het instellen van numerieke grenzen met `setMin()` en `setMax()`. 
Deze beperkingen helpen ervoor te zorgen dat invoer van de gebruiker binnen een geldig, verwacht bereik blijft.

- **Minimumwaarde**  
  Gebruik `setMin()` om het laagste acceptabele nummer te definiëren:

  ```java
  field.setMin(10.0); // Minimumwaarde: 10
  ```

  Als de gebruiker een getal onder deze drempel invoert, wordt dit als ongeldig beschouwd.

- **Maximumwaarde**  
  Gebruik `setMax()` om het hoogste acceptabele nummer te definiëren:

  ```java
  field.setMax(100.0); // Maximumwaarde: 100
  ```

  Waarden boven deze limiet worden als ongeldig gemarkeerd.

## Herstellen van de waarde {#restoring-the-value}

Het `MaskedNumberField` ondersteunt een herstel functie die de waarde van het veld op een vooraf gedefinieerde staat reset. 
Dit kan nuttig zijn wanneer gebruikers wijzigingen moeten ongedaan maken, per ongeluk bewerkingen moeten terugdraaien, of naar een bekende standaardwaarde moeten terugkeren.

Om dit gedrag in te schakelen, definieer de doelwaarde met `setRestoreValue()`. 
Indien nodig kan het veld programmatisch worden gereset met `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatisch** met `restoreValue()`
- **Via toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaard hersteltoets, tenzij overschreven)

De herstelwaarde moet expliciet worden ingesteld. Als deze niet gedefinieerd is, zal de functie het veld niet terugdraaien.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

Het `MaskedNumberFieldSpinner` breidt [`MaskedNumberField`](#basics) uit met spindoelen waarmee gebruikers de waarde kunnen verhogen of verlagen met stapknoppen of pijltjestoetsen. 
Dit is ideaal voor invoer zoals hoeveelheden, prijsaanpassingen, beoordelingscontroles, of elke situatie waarin gebruikers incrementele wijzigingen aanbrengen.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Belangrijkste kenmerken {#key-features}

- **Stapverhogingen**  
  Gebruik `setStep()` om te definiëren hoeveel de waarde moet veranderen met elke draai:

  ```java
  spinner.setStep(5.0); // Elke draai voegt of trekt 5 af
  ```

- **Interactieve bedieningselementen**  
  Gebruikers kunnen op spindknoppen klikken of toetsenbordinvoer gebruiken om de waarde aan te passen.

- **Alle functies van MaskedNumberField**  
  Ondersteunt volledig maskers, opmaak, groeps- en decimale karakters, min/max beperkingen en herstelloogiek.

## Stijl {#styling}

<TableBuilder name="MaskedNumberField" />
