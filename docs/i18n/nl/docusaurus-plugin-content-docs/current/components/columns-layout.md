---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: a1c9e9a41325f2f1ffb75fd07204106a
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

De `ColumnsLayout` component ordent items in een responsieve kolom-gebaseerde lay-out die zich aanpast op basis van de beschikbare breedte. Breakpoints en uitlijnen worden automatisch beheerd, zodat het bouwen van multi-kolom formulieren en content grids geen aangepaste responsieve logica vereist.

<!-- INTRO_END -->

## Standaardgedrag {#default-behavior}

Standaard ordent een `ColumnsLayout` items in twee kolommen en neemt de volledige breedte van de ouder in. De weergave kan verder worden aangepast met breakpoints en uitlijninstellingen, die in de onderstaande secties worden behandeld.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info Horizontale lay-outs 
Dit kan worden gebruikt in plaats van, of in combinatie met, de [`FlexLayout`](./flex-layout) component - een even krachtig hulpmiddel voor horizontale lay-outs.
:::

## Breakpoints {#breakpoints}

In essentie is de `ColumnsLayout` ontworpen om een vloeibaar, grid-achtig systeem te bieden dat zich aanpast aan de breedte van de bovenliggende container. In tegenstelling tot traditionele vaste grid-systemen, staat deze lay-out ontwikkelaars toe om een aantal kolommen op een bepaalde breedte op te geven en berekent dynamisch het aantal weergegeven kolommen op basis van ingestelde `Breakpoint` objecten.

Dit stelt een `ColumnsLayout` in staat om soepel aan te passen van een meer beperkte ruimte op kleine schermen naar een bredere ruimte op grotere schermen, waarmee een responsief ontwerp aan een ontwikkelaar wordt geboden zonder dat een aangepaste implementatie nodig is.

### Een `Breakpoint` begrijpen {#understanding-a-breakpoint}

Een `Breakpoint` kan worden opgegeven met behulp van de `Breakpoint` class, die drie parameters accepteert:

1. **Naam (optioneel)**:
Een breakpoint een naam geven stelt je in staat om deze in toekomstige configuraties te verwijzen.

2. **Minimale breedte**:
Elke breakpoint heeft een specifieke range die bepaalt wanneer de lay-out wordt toegepast. De minimale breedte wordt expliciet gedefinieerd, en de volgende breakpoint bepaalt de maximale breedte als deze bestaat. Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` om andere eenheden zoals `vw`, `%` of `em` op te geven.

3. **Aantal kolommen**:
Specificeer hoeveel kolommen een breakpoint zou moeten hebben met dit geheel getal.

:::info Evaluatie van `Breakpoint`
Breakpoints worden beoordeeld in oplopende volgorde van de breedte, wat betekent dat de lay-out de eerste passende breakpoint gebruikt.
:::

### Breakpoints toepassen {#applying-breakpoints}

Breakpoints worden op een `ColumnsLayout` op een van twee manieren toegepast: tijdens de constructie, of in een `List` met de `setBreakpoints()` methode: 

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // Eén kolom bij breedtes >= 0px
  new Breakpoint(0, 1),
  // Twee kolommen bij breedtes >= 600px
  new Breakpoint(600, 2),
  // Vier kolommen bij breedtes >= 1200px
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

De demonstratie hieronder toont een voorbeeld van het instellen van meerdere breakpoints bij constructie, het gebruiken van breakpoints om de [`Span`](#column-span-and-spans-per-breakpoint) van een component te configureren, en toont de resizingmogelijkheden van de `ColumnsLayout` wanneer de app wordt gewijzigd:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Kolom `Span` en spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Kolomspannen in `ColumnsLayout` stellen je in staat om te controleren hoeveel kolommen een item in beslag neemt, waardoor je meer controle krijgt over de uitstraling van je lay-out bij verschillende breedtes. Dit is vooral nuttig wanneer je bepaalde elementen meer of minder ruimte wilt laten innemen, afhankelijk van de schermgrootte of ontwerpeisen.

### Kolomspannen beheren {#managing-column-spans}

Standaard neemt elk item in de `ColumnsLayout` precies één kolom in beslag. Je kunt dit gedrag echter aanpassen door de kolomspan voor individuele items in te stellen. Een span specificeert het aantal kolommen dat een item zou moeten innemen.

```java
Button button = new Button("Klik op mij");
layout.addComponent(button);
// Item neemt twee kolommen in beslag
layout.setSpan(button, 2);
```

In het bovenstaande voorbeeld neemt de knop twee kolommen in beslag in plaats van de standaard één. De `setSpan()` methode stelt je in staat om aan te geven hoeveel kolommen een component in de lay-out moet beslaan.

### Kolomspannen aanpassen met breakpoints {#adjusting-column-spans-with-breakpoints}

Je kunt ook kolomspannen dynamisch aanpassen op basis van breakpoints. Deze functie is nuttig wanneer je wilt dat een item verschillende aantallen kolommen beslaat, afhankelijk van de schermgrootte. Bijvoorbeeld, je wilt misschien dat een element een enkele kolom in beslag neemt op mobiele apparaten, maar meerdere kolommen op grotere schermen.

```java
TextField email = new TextField("E-mail");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//emailveld zal twee kolommen beslaan wanneer de medium breakpoint actief is
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dit niveau van aanpassing zorgt ervoor dat je lay-out responsief en goed gestructureerd blijft op verschillende apparaten.

## Items in kolommen plaatsen {#placing-items-within-columns}

`ColumnsLayout` biedt de mogelijkheid om items in specifieke kolommen te plaatsen, waardoor meer controle wordt gegeven over de rangschikking van elementen. Je kunt handmatig specificeren waar een item moet verschijnen binnen de lay-out, zodat belangrijke componenten worden weergegeven zoals bedoeld.

### Basis kolomplaatsing {#basic-column-placement}

Standaard worden items geplaatst in de volgende beschikbare kolom, vul van links naar rechts. Je kunt echter dit gedrag overschrijven en de exacte kolom opgeven waar een item moet worden geplaatst. Om een item in een specifieke kolom te plaatsen, gebruik je de `setColumn()` methode. In dit voorbeeld wordt de knop in de tweede kolom van de lay-out geplaatst, ongeacht de volgorde waarin deze is toegevoegd ten opzichte van andere componenten:

```java
Button button = new Button("Indienen");
layout.addComponent(button);
// Plaats het item in de tweede kolom
layout.setColumn(button, 2);  
```

### Plaatsing aanpassen per breakpoint {#adjusting-placement-per-breakpoint}

Net als bij kolomspannen gebruik je breakpoints om de plaatsing van items aan te passen op basis van de schermgrootte. Dit is nuttig voor het herordenen of verplaatsen van elementen in de lay-out naarmate de viewport verandert.

```java
TextField email = new TextField("E-mail");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//emailveld verschijnt in de tweede kolom wanneer de medium breakpoint actief is
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In de volgende demonstratie, merk op dat wanneer de `"medium"` breakpoint wordt geactiveerd, het `email` veld beide kolommen beslaat, en het `confirmPassword` veld in de eerste kolom wordt geplaatst, in plaats van zijn standaardplaatsing in de tweede kolom:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vermijd botsingen
Wanneer meerdere items zijn geplaatst in een lay-out met verschillende spans en/of kolomtoewijzingen, zorg ervoor dat de gecombineerde spans en plaatsingen van items in een rij elkaar niet overlappen. De lay-out probeert automatisch de ruimte netjes te beheren, maar zorgvuldig ontwerp van spans en breakpoints voorkomt ongewenste weergave van items.
:::

## Verticale en horizontale itemuitlijningen {#vertical-and-horizontal-item-alignments}

Elk item in de `ColumnsLayout` kan zowel horizontaal als verticaal binnen zijn kolom worden uitgelijnd, wat controle biedt over hoe de inhoud binnen de lay-out is gepositioneerd.

**Horizontale uitlijning** van een item wordt geregeld met de `setHorizontalAlignment()` methode. Deze eigenschap bepaalt hoe een item zich binnen zijn kolom langs de horizontale as uitlijnt.

**Verticale uitlijning** specificeert hoe een item binnen zijn kolom wordt gepositioneerd langs de verticale as. Dit is nuttig wanneer kolommen variërende hoogtes hebben en je wilt bepalen hoe items verticaal worden verdeeld.

Beschikbare `Alignment` opties zijn onder andere:

- `START`: Lijnt het item aan de linkerkant van de kolom (standaard).
- `CENTER`: Centreert het item horizontaal binnen de kolom.
- `END`: Lijnt het item aan de rechterkant van de kolom.
- `STRETCH`: Rekent de component uit om de lay-out te vullen.
- `BASELINE`: Lijnt op basis van de text of inhoud binnen de kolom, lijnt items aan de tekstbaseline in plaats van andere uitlijnopties.
- `AUTO`: Automatische uitlijning.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In de demo hierboven is de `Indienen` knop ingesteld op `ColumnsLayout.Alignment.END` om ervoor te zorgen dat deze aan het einde, of in dit geval aan de rechterkant, van zijn kolom verschijnt.

## Item spacing {#item-spacing}

Het beheersen van de ruimte tussen kolommen in de `ColumnsLayout` tussen kolommen (horizontale spacing) en tussen rijen (verticale spacing) helpt ontwikkelaars de lay-out te verfijnen.

Om de horizontale spacing van de lay-out in te stellen, gebruik je de `setHorizontalSpacing()` methode:

```java
// Zet 20px ruimte tussen kolommen
layout.setHorizontalSpacing(20);  
```

Evenzo gebruik je de `setVerticalSpacing()` methode om de ruimte tussen rijen van de lay-out te configureren:

```java
// Zet 15px ruimte tussen rijen
layout.setVerticalSpacing(15);  
```

:::tip CSS-eenheden
Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden zoals `vw`, `%`, of `em` op te geven.
:::

## Horizontale en verticale lay-outs {#horizontal-and-vertical-layouts}

Het bouwen van responsieve en aantrekkelijke lay-outs is mogelijk met zowel de [`FlexLayout`](./flex-layout) component als de `ColumnsLayout` component, evenals een combinatie van de twee. Hieronder is een voorbeeld van het [formulier gemaakt in de FlexLayout](./flex-layout#example-form) artikel, maar met een `ColumnsLayout` schema in plaats daarvan:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Stijlen {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
