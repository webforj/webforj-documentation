---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 626c7e147632731dfdc761116a8abdc9
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

De `MaskedNumberField` is een tekstinvoerveld dat is ontworpen voor gestructureerde numerieke invoer. Het zorgt ervoor dat getallen consequent worden opgemaakt op basis van een gedefinieerde masker, waardoor het bijzonder nuttig is voor financiële formulieren, prijsvelden of elke invoer waarbij precisie en leesbaarheid belangrijk zijn.

Deze component ondersteunt nummeropmaak, lokalisatie van decimale/groeptekens en optionele waarde-beperkingen zoals minimums of maximums.

## Basics {#basics}

De `MaskedNumberField` kan worden gemaakt met of zonder parameters. Het ondersteunt het instellen van een initiële waarde, een label, een tijdelijke aanduiding en een gebeurtenislistener om te reageren op waarde wijzigingen.

Deze demo toont een **Tip Calculator** die gebruikmaakt van `MaskedNumberField` voor intuïtieve numerieke invoer. Eén veld is geconfigureerd om een geformatteerd rekeningsbedrag te accepteren, terwijl het andere een hele nummer percentage voor fooien vastlegt. Beide velden passen numerieke maskers toe om consistente en voorspelbare opmaak te garanderen.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Mask regels {#mask-rules}

De `MaskedNumberField` gebruikt een maskerstring om te controleren hoe numerieke invoer wordt opgemaakt en weergegeven. 
Elk teken in het masker definieert een specifiek opmaakgedrag, waardoor nauwkeurige controle mogelijk is over hoe getallen verschijnen.

### Masker tekens {#mask-characters}

| Teken     | Beschrijving |
|-----------|-------------|
| `0`       | Wordt altijd vervangen door een cijfer (0–9). |
| `#`       | Onderdrukt leidende nullen. Wordt vervangen door het vulteken links van het decimaal. Voor achterlopende cijfers wordt vervangen door een spatie of nul. Anders, vervangen door een cijfer. |
| `,`       | Wordt gebruikt als groepseparator (bijv. duizenden). Wordt vervangen door het vulteken als er geen cijfers aan voorafgaan. Anders, weergegeven als een komma. |
| `-`       | Toont een minteken (`-`) als het getal negatief is. Wordt vervangen door het vulteken als het positief is. |
| `+`       | Toont `+` voor positieve of `-` voor negatieve getallen. |
| `$`       | Leidt altijd tot een dollar teken. |
| `(`       | Voegt een linker haakje `(` in voor negatieve waarden. Wordt vervangen door het vulteken als positief. |
| `)`       | Voegt een rechter haakje `)` in voor negatieve waarden. Wordt vervangen door het vulteken als positief. |
| `CR`      | Toont `CR` voor negatieve getallen. Toont twee spaties als het getal positief is. |
| `DR`      | Toont `CR` voor negatieve getallen. Toont `DR` voor positieve getallen. |
| `*`       | Voegt een asterisk `*` in. |
| `.`       | Merkt het decimaal punt aan. Als er geen cijfers in de uitvoer verschijnen, wordt vervangen door het vulteken. Na het decimaal worden vultekens behandeld als spaties. |
| `B`       | Wordt altijd een spatie. Elk ander letterlijk teken wordt zo getoond als het is. |

Sommige van bovenstaande tekens kunnen meer dan eens in het masker voorkomen voor opmaak. Dit omvat `-`, `+`, `$`, en
`(`. Als een van deze tekens aanwezig is in het masker, wordt de eerste die tegenkomen verplaatst naar de laatste positie waar een `#` of `,` werd vervangen door het vulteken. Als er geen dergelijke positie bestaat, blijft het dubbele teken waar het is.

:::info Geen Automatische Afronding
Een masker binnen een veld ronden **NIET** af. Bijvoorbeeld, wanneer een waarde zoals `12.34567` in een veld met het masker `###0.00` wordt geplaatst, krijg je `12.34`.
:::

## Groep en decimaal separators {#group-and-decimal-separators}

De `MaskedNumberField` ondersteunt aanpassing van **groep** en **decimaal** tekens, waardoor het gemakkelijk is om nummeropmaak aan te passen aan verschillende locaties of zakelijke conventies.

- De **groepseparator** wordt gebruikt om duizenden visueel te scheiden (bijv. `1.000.000`).
- De **decimaal separator** geeft het fractionele deel van een getal aan (bijv. `123.45`).

Dit is nuttig in internationale toepassingen waar verschillende regio's verschillende tekens gebruiken (bijv. `.` vs `,`).

```java
field.setGroupCharacter(".");   // bijv. 1.000.000
field.setDecimalCharacter(","); // bijv. 123,45
```

:::tip Standaard Gedrag
Standaard past `MaskedNumberField` groep- en decimaal scheidingstekens toe op basis van de huidige locatie van de app. Je kunt ze op elk moment overschrijven met de meegeleverde setters.
:::

## Negateerbaar {#negateable}

De `MaskedNumberField` ondersteunt een optie om te controleren of negatieve getallen zijn toegestaan.

Standaard zijn negatieve waarden zoals `-123.45` toegestaan. Om dit te voorkomen, gebruik `setNegateable(false)` om de invoer alleen tot positieve waarden te beperken.

Dit is nuttig in zakelijke scenario's waarin waarden zoals hoeveelheden, totalen of percentages altijd niet-negatief moeten zijn.

```java
field.setNegateable(false);
```

Wanneer `negatable` is ingesteld op `false`, blokkeert het veld elke poging om een minteken in te voeren of anderszins negatieve waarden in te voeren.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Min en max waarden {#min-and-max-values}

De `MaskedNumberField` ondersteunt het instellen van numerieke grenzen met `setMin()` en `setMax()`. 
Deze beperkingen helpen ervoor te zorgen dat de gebruikersinvoer binnen een geldige, verwachte reeks blijft.

- **Minimale Waarde**  
  Gebruik `setMin()` om het laagste acceptabele nummer te definiëren:

  ```java
  field.setMin(10.0); // Minimale waarde: 10
  ```

  Als de gebruiker een getal invoert dat onder deze drempel ligt, wordt het als ongeldig beschouwd.

- **Maximale Waarde**  
  Gebruik `setMax()` om het hoogste acceptabele nummer te definiëren:

  ```java
  field.setMax(100.0); // Maximale waarde: 100
  ```

  Waarden boven deze limiet worden als ongeldig gemarkeerd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedNumberField` ondersteunt een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde staat. 
Dit kan nuttig zijn wanneer gebruikers wijzigingen moeten ongedaan maken, per ongeluk bewerkingen moeten terugdraaien of moeten terugkeren naar een bekende standaardwaarde.

Om dit gedrag in te schakelen, definieer de doelwaarde met `setRestoreValue()`. 
Wanneer nodig, kan het veld programmatig worden gereset met `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatig** met `restoreValue()`
- **Via het toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaard hersteltoets tenzij overschreven)

De herstelwaarde moet expliciet worden ingesteld. Als deze niet is gedefinieerd, wordt de functie de waarde niet terugzetten.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

De `MaskedNumberFieldSpinner` breidt [`MaskedNumberField`](#basics) uit door spinners te toevoegen waarmee gebruikers de waarde kunnen verhogen of verlagen met stapsgewijze knoppen of pijltjestoetsen. 
Dit is ideaal voor invoer zoals hoeveelheden, prijsaanpassingen, beoordelingscontrole of elke situatie waarin gebruikers incrementele wijzigingen aanbrengen.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Belangrijkste functies {#key-features}

- **Stap Increments**  
  Gebruik `setStep()` om te definiëren hoeveel de waarde moet veranderen met elke draai:

  ```java
  spinner.setStep(5.0); // Elke draai voegt 5 toe of trekt eraf
  ```

- **Interactieve Controls**  
  Gebruikers kunnen op spinnerknoppen klikken of toetsenbordinvoer gebruiken om de waarde aan te passen.

- **Alle Functies van MaskedNumberField**  
  Volledig ondersteuning voor maskers, opmaak, groep/decilm tekens, min/max beperkingen en herstel logica.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
