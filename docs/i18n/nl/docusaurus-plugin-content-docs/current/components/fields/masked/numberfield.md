---
title: MaskedNumberField
sidebar_position: 10
_i18n_hash: b528c4f1b76e02e7bd6fe132df47198c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

Het `MaskedNumberField` is een tekstinvoer ontwerp voor gestructureerde numerieke invoer. Het zorgt ervoor dat getallen consistent worden opgemaakt op basis van een gedefinieerde masker, waardoor het vooral nuttig is voor financiële formulieren, prijsvelden of elke invoer waar precisie en leesbaarheid belangrijk zijn.

Deze component ondersteunt nummeropmaak, lokalisatie van decimale/sgroeperingscharacters en optionele waarde beperkingen zoals minimum of maximum.

## Basisprincipes {#basics}

Het `MaskedNumberField` kan worden geïnstantieerd met of zonder parameters. Het ondersteunt het instellen van een initiële waarde, een label, een placeholder en een eventlistener om te reageren op waarde wijzigingen.

Deze demo toont een **Fooien Calculator** die `MaskedNumberField` gebruikt voor intuïtieve numerieke invoer. Een veld is geconfigureerd om een geformatteerd rekenbedrag te accepteren, terwijl het andere een percentage voor fooien met gehele getallen vastlegt. Beide velden passen numerieke maskers toe om een consistente en voorspelbare opmaak te waarborgen.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Masker regels {#mask-rules}

Het `MaskedNumberField` gebruikt een masker string om te controleren hoe numerieke invoer wordt opgemaakt en weergegeven. 
Elk teken in het masker definieert een specifieke opmaakgedrag, wat nauwkeurige controle mogelijk maakt over hoe getallen verschijnen.

:::tip Toepassen van maskers programmatisch
Om getallen te formatteren met dezelfde maskersyntaxis buiten een veld, bijvoorbeeld bij het weergeven van gegevens in een [`Table`](/docs/components/table/overview), gebruik de [`MaskDecorator`](/docs/advanced/mask-decorator) utility klasse.
:::

### Masker karakters {#mask-characters}

| Teken     | Beschrijving |
|-----------|-------------|
| `0`       | Altijd vervangen door een cijfer (0–9). |
| `#`       | Onderdrukt de leidende nullen. Vervangen door het vulteken links van het decimaal. Voor opvolgende cijfers, vervangen door een spatie of nul. Anders, vervangen door een cijfer. |
| `,`       | Wordt gebruikt als een groeperingsscheidingsteken (bijv. duizenden). Vervangen door het vulteken als er geen cijfers aan voorafgaan. Anders, weergegeven als een komma. |
| `-`       | Toont een minteken (`-`) als het getal negatief is. Vervangen door het vulteken als positief. |
| `+`       | Toont `+` voor positieve of `-` voor negatieve getallen. |
| `$`       | Leidt altijd tot een dollarteken. |
| `(`       | Voegt een linkse haakje `(` in voor negatieve waarden. Vervangen door het vulteken als positief. |
| `)`       | Voegt een rechter haakje `)` in voor negatieve waarden. Vervangen door het vulteken als positief. |
| `CR`      | Toont `CR` voor negatieve getallen. Toont twee spaties als het getal positief is. |
| `DR`      | Toont `CR` voor negatieve getallen. Toont `DR` voor positieve getallen. |
| `*`       | Voegt een asterisk `*` in. |
| `.`       | Merkt het decimaalpunt aan. Als er geen cijfers verschijnen in de uitvoer, vervangen door het vulteken. Na het decimaal, worden vultekens behandeld als spaties. |
| `B`       | Wordt altijd een spatie. Elk ander letterlijke teken wordt weergegeven zoals het is. |

Sommige van de bovenstaande tekens kunnen meer dan eens in het masker voorkomen voor opmaak. Deze omvatten `-`, `+`, `$`, en
`(`. Als een van deze tekens in het masker aanwezig is, zal de eerste die tegenkomt verplaatst worden naar de laatste positie waar een `#` of `,` werd vervangen door het vulteken. Als er geen dergelijke positie bestaat, blijft het dubbele teken waar het is.

:::info Geen Automatische Afronding
Een masker binnen een veld doet **NIET** afronden. Bijvoorbeeld, wanneer je een waarde zoals `12.34567` plaatst in een veld dat gemaskeerd is met `###0.00`, krijg je `12.34`.
:::

## Groep en decimale scheidingstekens {#group-and-decimal-separators}

Het `MaskedNumberField` ondersteunt de aanpassing van **groeperings** en **decimale** karakters, waardoor het eenvoudig is om nummeropmaak aan te passen aan verschillende locale of zakelijke conventies.

- De **groep separator** wordt gebruikt om duizenden visueel te scheiden (bijv. `1.000.000`).
- De **decimale separator** geeft het fractie gedeelte van een nummer aan (bijv. `123,45`).

Dit is nuttig in internationale toepassingen waar verschillende regio's verschillende karakters gebruiken (bijv. `.` vs `,`).

```java
field.setGroupCharacter(".");   // bijv. 1.000.000
field.setDecimalCharacter(","); // bijv. 123,45
```

:::tip Standaard Gedrag
Standaard past `MaskedNumberField` groep en decimale scheidingstekens toe op basis van de huidige locale van de app. Je kunt ze op elk moment overschrijven met de verstrekte setters.
:::

## Negateerbaar {#negateable}

Het `MaskedNumberField` ondersteunt een optie om te bepalen of negatieve getallen zijn toegestaan.

Standaard zijn negatieve waarden zoals `-123.45` toegestaan. Om dit te voorkomen, gebruik `setNegateable(false)` om invoer tot alleen positieve waarden te beperken.

Dit is nuttig in zakelijke scenario's waar waarden zoals hoeveelheden, totaals of percentages altijd niet-negatief moeten zijn.

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

Het `MaskedNumberField` ondersteunt het instellen van numerieke grenzen met `setMin()` en `setMax()`. 
Deze beperkingen helpen ervoor te zorgen dat gebruikersinvoer binnen een geldig, verwachte bereik blijft.

- **Minimumwaarde**  
  Gebruik `setMin()` om het laagste aanvaardbare nummer te definiëren:

  ```java
  field.setMin(10.0); // Minimumwaarde: 10
  ```

  Als de gebruiker een nummer onder deze drempel invoert, wordt dit als ongeldig beschouwd.

- **Maximumwaarde**  
  Gebruik `setMax()` om het hoogste aanvaardbare nummer te definiëren:

  ```java
  field.setMax(100.0); // Maximumwaarde: 100
  ```

  Waarden boven deze limiet zullen als ongeldig worden gemarkeerd.

## Herstellen van de waarde {#restoring-the-value}

Het `MaskedNumberField` ondersteunt een herstel functie die de waarde van het veld terugzet naar een vooraf gedefinieerde staat. 
Dit kan nuttig zijn wanneer gebruikers wijzigingen moeten ongedaan maken, toevallige bewerkingen moeten terugdraaien of terug moeten keren naar een bekende standaardwaarde.

Om dit gedrag in te schakelen, definieer de doelwaarde met `setRestoreValue()`. 
Indien nodig kan het veld programatisch worden gereset met `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Manieren om de waarde te herstellen {#ways-to-restore-the-value}

- **Programatisch** met `restoreValue()`
- **Via toetsenbord**, door op <kbd>ESC</kbd> te drukken (dit is de standaardhersteltoets tenzij overschreven)

De herstelwaarde moet expliciet worden ingesteld. Als deze niet is gedefinieerd, zal de functie het veld niet terugdraaien.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner` {#maskednumberfieldspinner}

De `MaskedNumberFieldSpinner` breidt [`MaskedNumberField`](#basics) uit door spinnerschakelaars toe te voegen die gebruikers in staat stellen om de waarde te verhogen of te verlagen met stapknoppen of pijltoetsen. 
Dit is ideaal voor invoeren zoals hoeveelheden, prijsaanpassingen, beoordelingscontroles of elke situatie waarin gebruikers incrementele wijzigingen aanbrengen.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Belangrijkste functies {#key-features}

- **Stapverhogingen**  
  Gebruik `setStep()` om te definiëren hoeveel de waarde moet veranderen met elke spin:

  ```java
  spinner.setStep(5.0); // Elke spin voegt of trekt 5 af
  ```

- **Interactieve Besturingselementen**  
  Gebruikers kunnen op spinners drukken of toetsenbordinvoer gebruiken om de waarde aan te passen.

- **Alle functies van MaskedNumberField**  
  Ondersteunt volledig maskers, opmaak, groeperings-/decimale karakters, min/max beperkingen en herstelloogica.

## Styling {#styling}

<TableBuilder name="MaskedNumberField" />
