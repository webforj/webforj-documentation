---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: f4d9229ae204894cda7263a6dc09ba0c
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

De `ColumnsLayout` component rangschikt items in een responsieve kolom-gebaseerde indeling die zich aanpast op basis van de beschikbare breedte. Breakpoints en uitlijningen worden automatisch beheerd, zodat het bouwen van meerkolomsformulieren en inhoudroosters geen aangepaste responsieve logica vereist.

<!-- INTRO_END -->

## Standaard gedrag {#default-behavior}

Standaard rangschikt een `ColumnsLayout` items in twee kolommen en neemt het volledige breedte van zijn ouder in. De weergave kan verder worden aangepast met breakpoints en uitlijningsinstellingen, die in de onderstaande secties worden behandeld.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

:::info Horizontale indelingen 
Dit kan worden gebruikt in plaats van, of in combinatie met, de [`FlexLayout`](./flex-layout) component - een even krachtig hulpmiddel voor horizontale indelingen.
:::

## Breakpoints {#breakpoints}

In wezen is de `ColumnsLayout` ontworpen om een vloeibaar, raster-achtig systeem te bieden dat zich aanpast aan de breedte van zijn bovenliggende container. In tegenstelling tot traditionele vaste raster systemen, staat deze lay-out ontwikkelaars toe om een aantal kolommen op een gegeven breedte op te geven, en berekent dynamisch het aantal weergegeven kolommen op basis van ingestelde `Breakpoint` objecten. 

Dit stelt een `ColumnsLayout` in staat om soepel aan te passen van een beperkter ruimte op kleine schermen naar een bredere ruimte op grotere schermen, en biedt een responsief ontwerp aan een ontwikkelaar zonder dat een aangepaste implementatie nodig is.

### Begrip van een `Breakpoint` {#understanding-a-breakpoint}

Een `Breakpoint` kan worden gespecificeerd met behulp van de `Breakpoint` klasse, die drie parameters neemt:

1. **Naam (optioneel)**:
Het benoemen van een breakpoint stelt je in staat om er naar te verwijzen in toekomstige configuraties.

2. **Minimale breedte**:
Elk breakpoint heeft een specifieke reeks die bepaalt wanneer zijn lay-out wordt toegepast. De minimale breedte wordt expliciet gedefinieerd, en het volgende breakpoint bepaalt de maximale breedte als deze bestaat. Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden zoals `vw`, `%`, of `em` op te geven.

3. **Aantal kolommen**:
Specificeer hoeveel kolommen een breakpoint moet hebben met dit geheel getal.

:::info Evaluatie van `Breakpoint`
Breakpoints worden in oplopende volgorde van de breedte geëvalueerd, wat betekent dat de lay-out het eerste overeenkomende breakpoint zal gebruiken.
:::


### Toepassen van breakpoints {#applying-breakpoints}

Breakpoints worden op een `ColumnsLayout` op een van de twee manieren toegepast: tijdens de constructie, of in een `List` met behulp van de `setBreakpoints()` methode: 

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

De demonstratie hieronder toont een voorbeeld van het instellen van meerdere breakpoints tijdens de constructie, met behulp van breakpoints om de [`Span`](#column-span-and-spans-per-breakpoint) van een component te configureren, en demonstreert de aanpassingscapaciteiten van de `ColumnsLayout` wanneer de app wordt gewijzigd:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Kolom `Span` en spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Kolomspannen in `ColumnsLayout` stellen je in staat om te controleren hoeveel kolommen een item in beslag neemt, waardoor je meer controle hebt over het uiterlijk van je lay-out bij verschillende breedtes. Dit is vooral nuttig wanneer je bepaalde elementen meer of minder ruimte wilt geven, afhankelijk van de schermgrootte of ontwerpeisen.

### Beheren van kolomspannen {#managing-column-spans}

Standaard neemt elk item in de `ColumnsLayout` precies één kolom in beslag. Je kunt dit gedrag echter aanpassen door de kolomspan voor individuele items in te stellen. Een span specificeert het aantal kolommen dat een item moet innemen.

```java
Button button = new Button("Klik op Mij");
layout.addComponent(button);
// Item beslaat twee kolommen
layout.setSpan(button, 2);
```

In het bovenstaande voorbeeld neemt de knop twee kolommen in beslag in plaats van de standaard één. De `setSpan()` methode stelt je in staat om op te geven hoeveel kolommen een component moet beslaan binnen de lay-out.

### Aanpassen van kolomspannen met breakpoints {#adjusting-column-spans-with-breakpoints}

Je kunt kolomspannen ook dynamisch aanpassen op basis van breakpoints. Deze functie is nuttig wanneer je wilt dat een item verschillende aantallen kolommen beslaat, afhankelijk van de schermgrootte. Bijvoorbeeld, je wilt misschien dat een element één kolom in beslag neemt op mobiele apparaten, maar meerdere kolommen op grotere schermen.

```java
TextField email = new TextField("Email");
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

Dit niveau van aanpassing zorgt ervoor dat je lay-out responsief en gepast gestructureerd blijft op verschillende apparaten.

## Items plaatsen binnen kolommen {#placing-items-within-columns}

`ColumnsLayout` biedt de mogelijkheid om items in specifieke kolommen te plaatsen, wat meer controle geeft over de rangschikking van elementen. Je kunt handmatig opgeven waar een item binnen de lay-out moet verschijnen, zodat belangrijke componenten zoals bedoeld worden weergegeven.

### Basis kolomplaatsing {#basic-column-placement}

Standaard worden items in de volgende beschikbare kolom geplaatst, waarbij van links naar rechts wordt gevuld. Je kunt dit gedrag echter overschrijven en de exacte kolom opgeven waar een item moet worden geplaatst. Om een item in een specifieke kolom te plaatsen, gebruik je de `setColumn()` methode. In dit voorbeeld wordt de knop in de tweede kolom van de lay-out geplaatst, ongeacht de volgorde waarin deze was toegevoegd ten opzichte van andere componenten:

```java
Button button = new Button("Verstuur");
layout.addComponent(button);
// Plaats het item in de tweede kolom
layout.setColumn(button, 2);  
```

### Aanpassen van plaatsing per breakpoint {#adjusting-placement-per-breakpoint}

Net als bij kolomspannen, gebruik je breakpoints om de plaatsing van items aan te passen op basis van de schermgrootte. Dit is nuttig voor het herordenen of verplaatsen van elementen in de lay-out naarmate het venster verandert.

```java
TextField email = new TextField("Email");
//...
List.of(
  new ColumnsLayout.Breakpoint("default", 0 , 1),
  new ColumnsLayout.Breakpoint("small", "20em", 1),
  new ColumnsLayout.Breakpoint("medium", "40em", 2),
  new ColumnsLayout.Breakpoint("large", "60em", 3)
)
//...
//emailveld zal verschijnen in de tweede kolom wanneer de medium breakpoint actief is
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In de volgende demonstratie, let op dat wanneer de `"medium"` breakpoint wordt geactiveerd, het `email` veld beide kolommen beslaat, en het `confirmPassword` veld in de eerste kolom wordt geplaatst, in plaats van zijn standaardplaatsing in de tweede kolom:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vermijd botsingen
Wanneer meerdere items in een lay-out met verschillende spannen en/of kolomtoewijzingen zijn geplaatst, zorg ervoor dat de gecombineerde spannen en plaatsingen van items in een rij zich niet overlappen. De lay-out probeert ruimte automatisch netjes te beheren, maar zorgvuldige ontwerp van spannen en breakpoints voorkomt ongewenste weergave van items.
:::

## Verticale en horizontale uitlijningen van items {#vertical-and-horizontal-item-alignments}

Elk item in de `ColumnsLayout` kan zowel horizontaal als verticaal binnen zijn kolom worden uitgelijnd, wat controle geeft over hoe de inhoud binnen de lay-out is gepositioneerd.

**Horizontale uitlijning** van een item wordt geregeld met behulp van de `setHorizontalAlignment()` methode. Deze eigenschap bepaalt hoe een item zich uitlijnt binnen zijn kolom langs de horizontale as.

**Verticale uitlijning** specificeert hoe een item is gepositioneerd binnen zijn kolom langs de verticale as. Dit is nuttig wanneer kolommen verschillende hoogtes hebben en je wilt controleren hoe items verticaal zijn verdeeld. 

Beschikbare `Alignment` opties zijn onder andere:

- `START`: Stelt het item aan de linkerkant van de kolom uit (standaard).
- `CENTER`: Centreert het item horizontaal binnen de kolom.
- `END`: Stelt het item aan de rechterkant van de kolom uit.
- `STRETCH`: Strekt de component uit om de lay-out te vullen.
- `BASELINE`: Stelt uit op basis van de tekst of inhoud binnen de kolom, waarbij items zijn uitgelijnd aan de tekstbasislijn in plaats van andere uitlijningsopties.
- `AUTO`: Automatische uitlijning.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In de bovenstaande demo is de `Verstuur` knop gegeven `ColumnsLayout.Alignment.END` om ervoor te zorgen dat deze aan het einde, of in dit geval aan de rechterkant, van zijn kolom verschijnt.

## Itemafstand {#item-spacing}

Het beheersen van de ruimte tussen kolommen in de `ColumnsLayout` tussen kolommen (horizontale ruimte) en tussen rijen (verticale ruimte) helpt ontwikkelaars de lay-out verfijnen.

Om de horizontale afstand van de lay-out in te stellen, gebruik je de `setHorizontalSpacing()` methode:

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

## Horizontale en verticale indelingen {#horizontal-and-vertical-layouts}

Het bouwen van responsieve en aantrekkelijke indelingen is mogelijk met zowel de [`FlexLayout`](./flex-layout) component als de `ColumnsLayout` component, evenals een combinatie van de twee. Hieronder staat een voorbeeld van het [formulier dat in het FlexLayout](./flex-layout#example-form) artikel is gemaakt, maar met gebruik van een `ColumnLayout` schema:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Styling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
