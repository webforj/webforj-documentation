---
title: MaskedNumberField
sidebar_position: 10
description: >-
  Format numeric input with the MaskedNumberField using configurable mask
  characters, grouping, decimal separators, and locale settings.
_i18n_hash: bba6de4e793a65cc887af236d206bb46
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Het `MaskedNumberField` is een tekstinvoerontwerp voor het consistent opmaken van numerieke invoer, gebaseerd op een gedefinieerde maskering. Het is nuttig voor financiële formulieren, prijsvelden of elke invoer waarbij nauwkeurigheid en leesbaarheid van groot belang zijn.

Deze component kan worden geïnstantieerd met of zonder parameters. Het ondersteunt nummeropmaak, lokalisatie van decimale/groeperingskarakters en optionele waarde-beperkingen zoals minimum- of maximumwaarden. Het ondersteunt ook het instellen van een initiële waarde, een label, een plaatsaanduiding en een evenementlistener om te reageren op waarde wijzigingen.

<!-- INTRO_END -->

Het onderstaande voorbeeld toont een **Tip Calculator** die `MaskedNumberField` gebruikt voor intuïtieve numerieke invoer. Eén veld is geconfigureerd om een opgemaakte rekeningbedrag te accepteren, terwijl het andere een percentage voor een fooi in gehele getallen vastlegt.

<ComponentDemo
path='/webforj/maskednumberfield'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java']}
height='270px'
/>

## Maskregels {#mask-rules}

Het `MaskedNumberField` gebruikt een masker-string om te controleren hoe numerieke invoer wordt opgemaakt en weergegeven. Elk teken in het masker definieert een specifiek opmaakgedrag, waardoor nauwkeurige controle over hoe getallen verschijnen mogelijk is.

:::tip Toepassen van maskers programmatisch
Om getallen met dezelfde maskersyntaxis buiten een veld te formatteren, bijvoorbeeld bij het weergeven van gegevens in een [`Table`](/docs/components/table/overview), gebruik de utility klasse [`MaskDecorator`](/docs/advanced/mask-decorator).
:::

### Masker karakters {#mask-characters}

| Teken | Beschrijving |
|-------|-------------|
| `0`   | Wordt altijd vervangen door een cijfer (0-9). |
| `#`   | Verbergt leidende nullen. Wordt vervangen door het vulteken aan de linkerkant van het decimale punt. Voor achtervolgende cijfers vervangen door een spatie of nul. Anders vervangen door een cijfer. |
| `,`   | Wordt gebruikt als een groeperingsscheidingsteken (bijv. duizenden). Wordt vervangen door het vulteken als er geen cijfers aan voorafgaan. Anders wordt het weergegeven als een komma. |
| `-`   | Toont een minteken (`-`) als het nummer negatief is. Wordt vervangen door het vulteken als het positief is. |
| `+`   | Toont `+` voor positieve of `-` voor negatieve getallen. |
| `$`   | Levert altijd een dollar teken op. |
| `(`   | Voegt een linkse haak toe `(` voor negatieve waarden. Wordt vervangen door het vulteken als het positief is. |
| `)`   | Voegt een rechte haak toe `)` voor negatieve waarden. Wordt vervangen door het vulteken als het positief is. |
| `CR`  | Toont `CR` voor negatieve getallen. Toont twee spaties als het getal positief is. |
| `DR`  | Toont `CR` voor negatieve getallen. Toont `DR` voor positieve getallen. |
| `*`   | Voegt een asterisk `*` toe. |
| `.`   | Merkt het decimale punt aan. Als er geen cijfers in de uitvoer verschijnen, wordt het vervangen door het vulteken. Na het decimale punt worden vultekens behandeld als spaties. |
| `B`   | Wordt altijd een spatie. Elk ander letterlijke teken wordt zoals het is weergegeven. |

Sommige van bovenstaande karakters kunnen meer dan eens in het masker voorkomen voor opmaak. Dit geldt voor `-`, `+`, `$`, en `(`. Als een van deze karakters in het masker aanwezig is, wordt de eerste die tegenkomt naar de laatste positie verplaatst waar een `#` of `,` door het vulteken werd vervangen. Als er geen dergelijke positie bestaat, blijft het dubbele teken waar het is.

:::info Geen Automatische Afronding
Een masker binnen een veld afgerond **NIET**. Wanneer bijvoorbeeld een waarde zoals `12.34567` in een veld wordt geplaatst dat is gemaskeerd met `###0.00`, krijg je `12.34`.
:::

## Groeperings- en decimalseparators {#group-and-decimal-separators}

Het `MaskedNumberField` ondersteunt de aanpassing van **groepering** en **decimale** karakters, waardoor het eenvoudig is om nummeropmaak aan te passen aan verschillende locaties of zakelijke conventies.

- De **groeperingsscheidingsteken** wordt gebruikt om duizenden visueel te scheiden (bijv. `1.000.000`).
- De **decimale scheidingsteken** geeft het fractionele deel van een nummer aan (bijv. `123.45`).

Dit is nuttig in internationale toepassingen waar verschillende regio's verschillende karakters gebruiken (bijv. `.` versus `,`).

```java
field.setGroupCharacter(".");   // bijv. 1.000.000
field.setDecimalCharacter(","); // bijv. 123,45
```

:::tip Standaard Gedrag
Standaard past `MaskedNumberField` groeperings- en decimalseparators toe op basis van de huidige locale van de app. Je kunt deze op elk moment overschrijven met de verstrekte setters.
:::

## Negateerbaar {#negateable}

Het `MaskedNumberField` ondersteunt een optie om te controleren of negatieve nummers zijn toegestaan.

Standaard zijn negatieve waarden zoals `-123.45` toegestaan. Om dit te voorkomen, gebruik `setNegateable(false)` om invoer alleen op positieve waarden te beperken.

Dit is nuttig in zakelijke scenario's waar waarden zoals hoeveelheden, totalen of percentages altijd niet-negatief moeten zijn.

```java
field.setNegateable(false);
```

Wanneer `negateable` is ingesteld op `false`, blokkeert het veld pogingen om een minteken in te voeren of anderszins negatieve waarden in te voeren.

<ComponentDemo
path='/webforj/maskednumnegatable/'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java']}
height='150px'
/>

## Min- en maxwaarden {#min-and-max-values}

Het `MaskedNumberField` ondersteunt het instellen van numerieke grenzen met `setMin()` en `setMax()`. Deze beperkingen helpen ervoor te zorgen dat gebruikersinvoer binnen een geldige, verwachte reeks blijft.

- **Minimale Waarde**
  Gebruik `setMin()` om het laagste aanvaardbare nummer te definiëren:

  ```java
  field.setMin(10.0); // Minimale waarde: 10
  ```

  Als de gebruiker een nummer onder deze drempel invoert, wordt het als ongeldig beschouwd.

- **Maximale Waarde**
  Gebruik `setMax()` om het hoogste aanvaardbare nummer te definiëren:

  ```java
  field.setMax(100.0); // Maximale waarde: 100
  ```

  Waarden boven deze limiet zullen als ongeldig worden gemarkeerd.

## Herstellen van de waarde {#restoring-the-value}

Het `MaskedNumberField` ondersteunt een herstelfunctie die de waarde van het veld terugzet naar een vooraf gedefinieerde staat. Dit kan nuttig zijn wanneer gebruikers wijzigingen moeten ongedaan maken, per ongeluk bewerkte waarden willen herstellen of terug willen keren naar een bekende standaardwaarde.

Om dit gedrag mogelijk te maken, definieer je de doelwaarde met `setRestoreValue()`. Wanneer nodig, kan het veld programmatisch worden gereset met `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Wijzen om de waarde te herstellen {#ways-to-restore-the-value}

- **Programmatisch** met `restoreValue()`
- **Via toetsenbord**, door <kbd>ESC</kbd> in te drukken (dit is de standaardhersteltoets tenzij anders ingesteld)

De herstelwaarde moet expliciet worden ingesteld. Als deze niet is gedefinieerd, zal de functie het veld niet terugzetten.

<ComponentDemo
path='/webforj/maskednumrestore'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java']}
height='150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

De `MaskedNumberFieldSpinner` breidt het `MaskedNumberField` uit door spincontroles toe te voegen die gebruikers in staat stellen de waarde te verhogen of verlagen met stapknoppen of pijltoetsen. Dit is ideaal voor invoer zoals hoeveelheden, prijsaanpassingen, beoordelingscontroles of elke situatie waarin gebruikers incrementele wijzigingen aanbrengen.

<ComponentDemo
path='/webforj/maskednumspinner'
files={['src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java']}
height='120px'
/>

### Belangrijkste kenmerken {#key-features}

- **Stapverhogingen**
  Gebruik `setStep()` om te definiëren hoeveel de waarde moet veranderen bij elke draai:

  ```java
  spinner.setStep(5.0); // Elke draai voegt of trekt 5 af
  ```

- **Interactieve Besturingselementen**
  Gebruikers kunnen op spin-knoppen klikken of toetsenbordinvoer gebruiken om de waarde aan te passen.

- **Alle Kenmerken van MaskedNumberField**
  Volledige ondersteuning voor maskers, opmaak, groeperings-/decimale karakters, min/max beperkingen en herstellogica.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
