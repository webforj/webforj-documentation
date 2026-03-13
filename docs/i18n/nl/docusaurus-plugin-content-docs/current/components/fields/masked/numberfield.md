---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 6eae8d772ec386aff55df31b674a1e84
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Het `MaskedNumberField` is een tekstinvoer ontworpen voor gestructureerde numerieke invoer. Het zorgt ervoor dat nummers consistent worden geformatteerd op basis van een gedefinieerde masker, waardoor het vooral nuttig is voor financiële formulieren, prijsvelden of elke invoer waar precisie en leesbaarheid belangrijk zijn.

Deze component ondersteunt nummerformattering, lokalisatie van decimale/groeperingskarakters en optionele waarde-beperkingen zoals minimum- of maximumwaarden.

<!-- INTRO_END -->

## Basisprincipes {#basics}

Het `MaskedNumberField` kan worden geïnitialiseerd met of zonder parameters. Het ondersteunt het instellen van een initiële waarde, een label, een placeholder en een gebeurtenislummenscherm om te reageren op waarde wijzigingen.

Deze demo laat een **Fooi Calculator** zien die gebruik maakt van `MaskedNumberField` voor intuïtieve numerieke invoer. Eén veld is geconfigureerd om een geformatteerd rekeningbedrag te accepteren, terwijl het andere een heel getal fooi percentage vastlegt. Beide velden passen numerieke maskers toe om consistente en voorspelbare formatting te waarborgen.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Maskerregels {#mask-rules}

Het `MaskedNumberField` gebruikt een maskerstring om te bepalen hoe numerieke invoer wordt geformatteerd en weergegeven. 
Elk teken in het masker definieert een specifieke formatteringsgedrag, waardoor nauwkeurige controle over hoe nummers verschijnen mogelijk is.

### Maskertekens {#mask-characters}

| Teken   | Beschrijving |
|---------|--------------|
| `0`     | Altijd vervangen door een cijfer (0–9). |
| `#`     | Onderdrukt leidende nullen. Vervangen door het vullerteken links van de decimale punt. Voor achterlopende cijfers, vervangen door een spatie of nul. Anders, vervangen door een cijfer. |
| `,`     | Gebruikt als een groeperingsscheidingsteken (bijv. duizenden). Vervangen door het vuller teken als er geen cijfers aan voorafgaan. Anders, weergegeven als een komma. |
| `-`     | Toont een minteken (`-`) als het nummer negatief is. Vervangen door het vuller teken als het positief is. |
| `+`     | Toont `+` voor positieve of `-` voor negatieve getallen. |
| `$`     | Resultaat is altijd een dollar teken. |
| `(`     | Voegt een linkse haakje `(` toe voor negatieve waarden. Vervangen door het vuller teken als positief. |
| `)`     | Voegt een rechtse haakje `)` toe voor negatieve waarden. Vervangen door het vuller teken als positief. |
| `CR`    | Toont `CR` voor negatieve getallen. Toont twee spaties als het getal positief is. |
| `DR`    | Toont `CR` voor negatieve getallen. Toont `DR` voor positieve getallen. |
| `*`     | Voegt een sterretje `*` toe. |
| `.`     | Merkt de decimale punt aan. Als er geen cijfers in de uitvoer verschijnen, vervangen door het vuller teken. Na de decimale, worden vuller tekens behandeld als spaties. |
| `B`     | Wordt altijd een spatie. Elk ander letterlijke teken wordt weergegeven zoals het is. |

Sommige van de bovenstaande tekens kunnen meer dan eens in het masker verschijnen voor formatting. Deze omvatten `-`, `+`, `$`, en 
`(`. Als een van deze tekens in het masker aanwezig is, wordt de eerste die tegenkomt verplaatst naar de laatste positie waar een `#` of `,` door het vuller teken is vervangen. Als er geen dergelijke positie bestaat, blijft het dubbele teken waar het is.

:::info Geen Automatische Afgerond
Een masker binnen een veld doet **NIET** afronden. Bijvoorbeeld, wanneer een waarde zoals `12.34567`
in een veld dat is gemaskerd met `###0.00` wordt geplaatst, krijg je `12.34`.
:::

## Groep en decimale scheidingstekens {#group-and-decimal-separators}

Het `MaskedNumberField` ondersteunt aanpassing van **groeperings** en **decimale** karakters, waardoor het eenvoudig is om nummerformattering aan te passen aan verschillende locales of bedrijfsconventies.

- De **groeperingsscheidingsteken** wordt gebruikt om duizenden visueel te scheiden (bijv. `1.000.000`).
- De **decimale scheidingsteken** geeft het fractionele deel van een getal aan (bijv. `123.45`).

Dit is nuttig in internationale applicaties waar verschillende regio's verschillende karakters gebruiken (bijv. `.` vs `,`).

```java
field.setGroupCharacter(".");   // bijv. 1.000.000
field.setDecimalCharacter(","); // bijv. 123,45
```

:::tip Standaard Gedrag
Standaard past `MaskedNumberField` groeperings- en decimalseparators toe op basis van de huidige locale van de app. Je kunt ze op elk moment overschrijven met de verstrekte setters.
:::

## Negatief {#negateable}

Het `MaskedNumberField` ondersteunt een optie om te controleren of negatieve getallen zijn toegestaan.

Standaard zijn negatieve waarden zoals `-123.45` toegestaan. Om dit te voorkomen, gebruik `setNegateable(false)` om de invoer alleen tot positieve waarden te beperken.

Dit is nuttig in bedrijfsituaties waarin waarden zoals hoeveelheden, totalen of percentages altijd niet-negatief moeten zijn.

```java
field.setNegateable(false);
```

Wanneer `negateable` is ingesteld op `false`, blokkeert het veld eventuele pogingen om een minteken in te voeren of anderszins negatieve waarden in te voeren.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Min en max waarden {#min-and-max-values}

Het `MaskedNumberField` ondersteunt het instellen van numerieke grenzen met `setMin()` en `setMax()`. 
Deze beperkingen helpen ervoor te zorgen dat de invoer van de gebruiker binnen een geldig, verwachte bereik blijft.

- **Minimale Waarde**  
  Gebruik `setMin()` om het laagste acceptabele nummer te definiëren:

  ```java
  field.setMin(10.0); // Minimale waarde: 10
  ```

  Als de gebruiker een nummer onder deze drempel voert, wordt het als ongeldig beschouwd.

- **Maximale Waarde**  
  Gebruik `setMax()` om het hoogste acceptabele nummer te definiëren:

  ```java
  field.setMax(100.0); // Maximale waarde: 100
  ```

  Waarden boven deze limiet worden gemarkeerd als ongeldig.

## Waarde Herstellen {#restoring-the-value}

Het `MaskedNumberField` ondersteunt een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde staat. 
Dit kan nuttig zijn wanneer gebruikers wijzigingen moeten ongedaan maken, per ongeluk edities moeten terugdraaien, of terug willen keren naar een bekende standaardwaarde.

Om dit gedrag in te schakelen, definieer de doelwaarde met `setRestoreValue()`. 
Wanneer nodig, kan het veld programmatisch worden gereset met `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatig** met `restoreValue()`
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard hersteltoets tenzij overschreven)

De herstelwaarde moet expliciet worden ingesteld. Indien niet gedefinieerd, zal de functie het veld niet terugdraaien.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

De `MaskedNumberFieldSpinner` breidt [`MaskedNumberField`](#basics) uit door spinners te voegen die gebruikers in staat stellen de waarde te verhogen of te verlagen met stapknoppen of pijltoetsen. 
Dit is ideaal voor invoer zoals hoeveelheden, prijsaanpassingen, beoordelingsbesturing, of elke situatie waarin gebruikers incrementele wijzigingen aanbrengen.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Sleutelkenmerken {#key-features}

- **Stapupgrades**  
  Gebruik `setStep()` om te definiëren hoeveel de waarde moet veranderen met elke draai:

  ```java
  spinner.setStep(5.0); // Elke draai voegt of trekt 5 af
  ```

- **Interactieve Besturing**  
  Gebruikers kunnen klikken op spinners of toetsenbordinvoer gebruiken om de waarde aan te passen.

- **Alle functies van MaskedNumberField**  
  Volledig ondersteund maskers, formatting, groeperings-/decimale karakters, min/max beperkingen en herstel logica.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
