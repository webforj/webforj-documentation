---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: 00d399f2bcfb22c884608aa8a0975573
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

De `MaskedNumberField` is een tekstinvoer ontworpen voor gestructureerde numerieke invoer. Het zorgt ervoor dat nummers consistent worden opgemaakt op basis van een gedefinieerde maskering, wat het vooral nuttig maakt voor financiële formulieren, prijsvelden of elke invoer waar precisie en leesbaarheid van belang zijn.

Deze component ondersteunt nummeropmaak, lokalisatie van decimale/groeperingssymbolen en optionele waarde-beperkingen zoals minimum- of maximumwaarden.

## Basis {#basics}

De `MaskedNumberField` kan worden gemaakt met of zonder parameters. Het ondersteunt het instellen van een initiële waarde, een label, een placeholder en een gebeurtenisluistern om te reageren op waarde veranderingen.

Deze demo laat een **Fooiencalculator** zien die `MaskedNumberField` gebruikt voor intuïtieve numerieke invoer. Eén veld is geconfigureerd om een geformatteerd factuurbedrag te accepteren, terwijl het andere een percentage voor fooien als geheel getal opvangt. Beide velden passen numerieke maskers toe om consistentie en voorspelbare opmaak te waarborgen.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Maskerregels {#mask-rules}

De `MaskedNumberField` gebruikt een maskerstring om te regelen hoe numerieke invoer wordt opgemaakt en weergegeven. 
Elk teken in het masker definieert een specifieke opmaakgedraging, waardoor precieze controle mogelijk is over hoe nummers verschijnen.

### Maskertekens {#mask-characters}

| Teken     | Beschrijving |
|-----------|--------------|
| `0`       | Altijd vervangen door een cijfer (0–9). |
| `#`       | Onderdrukt leidende nullen. Wordt vervangen door het opvulteken links van de decimale punt. Voor achterlopende cijfers wordt vervangen door een spatie of nul. Anders wordt vervangen door een cijfer. |
| `,`       | Gebruikt als groeperingsscheiding (bijv. duizenden). Wordt vervangen door het opvulteken als er geen cijfers aan voorafgaan. Anders wordt het weergegeven als een komma. |
| `-`       | Toont een minteken (`-`) als het nummer negatief is. Wordt vervangen door het opvulteken als het positief is. |
| `+`       | Toont `+` voor positieve of `-` voor negatieve nummers. |
| `$`       | Resultaat is altijd een dollarteken. |
| `(`       | Voegt een linkerhaakje `(` in voor negatieve waarden. Wordt vervangen door het opvulteken als het positief is. |
| `)`       | Voegt een rechterhaakje `)` in voor negatieve waarden. Wordt vervangen door het opvulteken als het positief is. |
| `CR`      | Toont `CR` voor negatieve nummers. Toont twee spaties als het nummer positief is. |
| `DR`      | Toont `CR` voor negatieve nummers. Toont `DR` voor positieve nummers. |
| `*`       | Voegt een sterretje `*` in. |
| `.`       | Merkt de decimale punt aan. Als er geen cijfers in de uitvoer verschijnen, wordt vervangen door het opvulteken. Na de decimale worden opvultekens behandeld als spaties. |
| `B`       | Wordt altijd een spatie. Elk ander letterlijke teken wordt zoals het is weergegeven. |

Een aantal van de bovenstaande tekens kan meer dan eens in het masker voorkomen voor opmaak. Deze omvatten `-`, `+`, `$` en `(`. Als een van deze tekens in het masker aanwezig is, wordt het eerste dat wordt tegengekomen verplaatst naar de laatste positie waar een `#` of `,` werd vervangen door het opvulteken. Als er geen dergelijke positie bestaat, blijft het dubbele teken waar het is.

:::info Geen Automatische Afronding
Een masker binnen een veld **rondt niet** af. Bijvoorbeeld, wanneer een waarde zoals `12.34567` in een veld met `###0.00` wordt geplaatst, krijg je `12.34`.
:::

## Groep- en decimalseparators {#group-and-decimal-separators}

De `MaskedNumberField` ondersteunt aanpassing van **groeperings** en **decimale** symbolen, waardoor het eenvoudig is om nummerformattering aan te passen aan verschillende regio's of zakelijke conventies.

- De **groepscheiding** wordt gebruikt om duizenden visueel te scheiden (bijv. `1.000.000`).
- De **decimale scheiding** geeft het fractionele deel van een nummer aan (bijv. `123,45`).

Dit is nuttig in internationale toepassingen waar verschillende regio's verschillende tekens gebruiken (bijv. `.` vs `,`).

```java
field.setGroupCharacter(".");   // bijv. 1.000.000
field.setDecimalCharacter(","); // bijv. 123,45
```

:::tip Standaardgedrag
Standaard past `MaskedNumberField` groeps- en decimalseparators toe op basis van de huidige locale van de app. Je kunt ze op elk moment overschrijven met de verstrekte setters.
:::

## Negatieve waarden {#negateable}

De `MaskedNumberField` ondersteunt een optie om te regelen of negatieve nummers zijn toegestaan.

Standaard zijn negatieve waarden zoals `-123,45` toegestaan. Om dit te voorkomen, gebruik `setNegateable(false)` om invoer tot alleen positieve waarden te beperken.

Dit is nuttig in zakelijke scenario's waar waarden zoals hoeveelheden, totalen of percentages altijd niet-negatief moeten zijn.

```java
field.setNegateable(false);
```

Wanneer `negatable` op `false` is ingesteld, blokkeert het veld elke poging om een minteken in te voeren of op een andere manier negatieve waarden in te voeren.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Minimale en maximale waarden {#min-and-max-values}

De `MaskedNumberField` ondersteunt het instellen van numerieke grenzen met `setMin()` en `setMax()`. 
Deze beperkingen helpen ervoor te zorgen dat gebruikersinvoer binnen een geldige, verwachte range blijft.

- **Minimale Waarde**  
  Gebruik `setMin()` om het laagste aanvaardbare nummer te definiëren:

  ```java
  field.setMin(10.0); // Minimale waarde: 10
  ```

  Als de gebruiker een nummer onder deze drempel invoert, wordt dit als ongeldig beschouwd.

- **Maximale Waarde**  
  Gebruik `setMax()` om het hoogste aanvaardbare nummer te definiëren:

  ```java
  field.setMax(100.0); // Maximale waarde: 100
  ```

  Waarden boven deze limiet worden als ongeldig gemarkeerd.

## Herstellen van de waarde {#restoring-the-value}

De `MaskedNumberField` ondersteunt een herstel functie die de waarde van het veld reset naar een vooraf gedefinieerde staat. 
Dit kan nuttig zijn wanneer gebruikers wijzigingen moeten ongedaan maken, per ongeluk gemaakte bewerkingen moeten terugdraaien of naar een bekende standaardwaarde moeten terugkeren.

Om dit gedrag in te schakelen, definieer je de doelwaarde met `setRestoreValue()`. 
 Wanneer nodig, kan het veld programmatic worden gereset met `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatic** met `restoreValue()`
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaardhersteltoets tenzij anders ingesteld)

De te herstellen waarde moet expliciet worden ingesteld. Als deze niet is gedefinieerd, zal de functie het veld niet terugdraaien.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

De `MaskedNumberFieldSpinner` breidt [`MaskedNumberField`](#basics) uit door spinner-bedieningen toe te voegen die het gebruikers mogelijk maken om de waarde te verhogen of te verlagen met stapknoppen of pijltoetsen. 
Dit is ideaal voor invoeren zoals hoeveelheden, prijsaanpassingen, beoordelingscontroles of elk scenario waarin gebruikers incrementele wijzigingen aanbrengen.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Belangrijke functies {#key-features}

- **Stapverhogingen**  
  Gebruik `setStep()` om te definiëren hoe ver de waarde moet veranderen met elke draaibeweging:

  ```java
  spinner.setStep(5.0); // Elke draai voegt of trekt 5 af
  ```

- **Interactieve bedieningselementen**  
  Gebruikers kunnen op spinner-knoppen klikken of toetsenbordinvoer gebruiken om de waarde aan te passen.

- **Alle functies van MaskedNumberField**  
  Ondersteunt volledig maskers, opmaak, groeps-/decimale symbolen, min/max beperkingen en herstel-logica.

## Stijl {#styling}

<TableBuilder name="MaskedNumberField" />
