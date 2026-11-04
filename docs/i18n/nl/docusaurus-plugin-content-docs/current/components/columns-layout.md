---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: 3705994e0ddda1a54c0582b290942549
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

De `ColumnsLayout` component rangschikt items in een responsieve kolomgebaseerde layout die zich aanpast op basis van de beschikbare breedte. Breakpoints en uitlijningen worden automatisch beheerd, zodat het bouwen van formulieren met meerdere kolommen en contentraster geen aangepaste responsieve logica vereist.

<!-- INTRO_END -->

## Standaard gedrag {#default-behavior}

Standaard neemt een `ColumnsLayout` de volledige breedte van zijn ouder aan en rangschikt items in een enkele kolom, waarbij het overschakelt naar twee kolommen bij breedtes van `40em` en meer.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Horizontale lay-outs
Dit kan worden gebruikt in plaats van, of in combinatie met, de [`FlexLayout`](./flex-layout) component - een even krachtige tool voor horizontale lay-outs.
:::

## Breakpoints {#breakpoints}

De `ColumnsLayout` is ontworpen om een vloeibaar, grid-achtig systeem te bieden dat zich aanpast aan de breedte van zijn oudercontainer. In tegenstelling tot traditionele vaste gridsystemen, laat deze lay-out ontwikkelaars toe om een aantal kolommen op een gegeven breedte op te geven en berekent dynamisch het aantal weergegeven kolommen op basis van ingestelde `Breakpoint` objecten.

Hierdoor kan een `ColumnsLayout` soepel aanpassen van een meer beperkte ruimte op kleine schermen naar een breder gebied op grotere schermen, en biedt het een responsief ontwerp aan een ontwikkelaar zonder aangepaste implementatie.

### Begrijpen van een `Breakpoint` {#understanding-a-breakpoint}

Een `Breakpoint` kan worden gespecificeerd met de `Breakpoint` klasse, die drie parameters aanneemt:

1. **Naam (optioneel)**:
Een breakpoint een naam geven stelt je in staat het in toekomstige configuraties te refereren.

2. **Minimale breedte**:
Elke breakpoint heeft een specifiek bereik dat bepaalt wanneer de lay-out wordt toegepast. De minimale breedte is expliciet gedefinieerd, en de volgende breakpoint bepaalt de maximale breedte als deze bestaat. Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden zoals `vw`, `%`, of `em` op te geven.

3. **Aantal kolommen**:
Geef met dit geheel getal aan hoeveel kolommen een breakpoint moet hebben.

:::info Evaluatie van `Breakpoint`
Breakpoints worden geëvalueerd in oplopende volgorde van de breedte, wat betekent dat de lay-out de eerste overeenkomende breakpoint zal gebruiken.
:::

### Toepassen van breakpoints {#applying-breakpoints}

Breakpoints worden op een `ColumnsLayout` toegepast op een van de twee manieren: tijdens de constructie, of in een `List` met de `setBreakpoints()` methode:

```java
ColumnsLayout layout = new ColumnsLayout();

List<Breakpoint> breakpoints = List.of(
  // Één kolom bij breedtes >= 0px
  new Breakpoint(0, 1),
  // Twee kolommen bij breedtes >= 600px
  new Breakpoint(600, 2),
  // Vier kolommen bij breedtes >= 1200px
  new Breakpoint(1200, 4));

layout.setBreakpoints(breakpoints);
```

De onderstaande demonstratie toont een voorbeeld van het instellen van meerdere breakpoints bij constructie, het gebruiken van breakpoints om de [`Span`](#column-span-and-spans-per-breakpoint) van een component te configureren, en demonstreert de herverdelingseigenschappen van de `ColumnsLayout` wanneer de app wordt aangepast:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Kolom `Span` en spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Kolomspans in `ColumnsLayout` stellen je in staat te bepalen hoeveel kolommen een item in beslag neemt, waardoor je meer controle krijgt over de weergave van je lay-out bij verschillende breedtes. Dit is vooral nuttig wanneer je bepaalde elementen meer of minder ruimte wilt laten innemen, afhankelijk van de schermgrootte of ontwerpvereisten.

### Beheren van kolomspans {#managing-column-spans}

Standaard neemt elk item in de `ColumnsLayout` precies één kolom in beslag. Je kunt dit gedrag echter aanpassen door de kolomspan voor individuele items in te stellen. Een span specificeert het aantal kolommen dat een item in beslag moet nemen.

```java
Button button = new Button("Klik op Mij");
layout.add(button);
// Item beslaat twee kolommen
layout.setSpan(button, 2);
```

In het bovenstaande voorbeeld neemt de knop twee kolommen in beslag in plaats van de standaard één. De `setSpan()` methode stelt je in staat om aan te geven hoeveel kolommen een component moet beslaan binnen de lay-out.

### Aanpassen van kolomspans met breakpoints {#adjusting-column-spans-with-breakpoints}

Je kunt ook de kolomspans dynamisch aanpassen op basis van breakpoints. Deze functie is nuttig wanneer je wilt dat een item een verschillend aantal kolommen beslaat, afhankelijk van de schermgrootte. Je wilt bijvoorbeeld misschien dat een element één kolom in beslag neemt op mobiele apparaten, maar meerdere kolommen op grotere schermen.

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
//E-mailveld beslaat twee kolommen wanneer de medium breakpoint actief is
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dit niveau van aanpassing zorgt ervoor dat je lay-out responsief en passend blijft gestructureerd over verschillende apparaten.

## Items binnen kolommen plaatsen {#placing-items-within-columns}

`ColumnsLayout` biedt de mogelijkheid om items in specifieke kolommen te plaatsen, waardoor je meer controle hebt over de rangschikking van elementen. Je kunt handmatig specificeren waar een item binnen de lay-out moet verschijnen, zodat belangrijke componenten worden weergegeven zoals bedoeld.

### Basis kolomplaatsing {#basic-column-placement}

Standaard worden items in de volgende beschikbare kolom geplaatst, van links naar rechts vulend. Je kunt dit gedrag echter overschrijven en de exacte kolom specificeren waar een item geplaatst moet worden. Om een item in een specifieke kolom te plaatsen, gebruik je de `setColumn()` methode. In dit voorbeeld wordt de knop in de tweede kolom van de lay-out geplaatst, ongeacht de volgorde waarin deze is toegevoegd ten opzichte van andere componenten:

```java
Button button = new Button("Indienen");
layout.add(button);
// Plaats het item in de tweede kolom
layout.setColumn(button, 2);
```

### Aanpassen van plaatsing per breakpoint {#adjusting-placement-per-breakpoint}

Net als bij kolomspans gebruik je breakpoints om de plaatsing van items aan te passen op basis van de schermgrootte. Dit is nuttig voor het herschikken of verplaatsen van elementen in de lay-out wanneer de viewport verandert.

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
//E-mailveld verschijnt in de tweede kolom wanneer de medium breakpoint actief is
columnsLayout.setColumn(email, "medium", 2);
//...
```

In de volgende demonstratie, let op dat wanneer de `"medium"` breakpoint wordt geactiveerd, het `email` veld beide kolommen beslaat:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Vermijd botsingen
Wanneer meerdere items in een lay-out met verschillende spans en/of kolomtoewijzingen worden geplaatst, zorg ervoor dat de gecombineerde spans en plaatsingen van items in een rij elkaar niet overlappen. De lay-out probeert automatisch de ruimte mooi te beheren, maar een zorgvuldige inrichting van spans en breakpoints voorkomt onbedoelde weergave van items.
:::

## Verticale en horizontale itemuitlijningen {#vertical-and-horizontal-item-alignments}

Elk item in de `ColumnsLayout` kan zowel horizontaal als verticaal binnen zijn kolom worden uitgelijnd, waardoor je controle krijgt over hoe de inhoud binnen de lay-out gepositioneerd is.

**Horizontale uitlijning** van een item wordt geregeld met de `setHorizontalAlignment()` methode. Deze eigenschap bepaalt hoe een item binnen zijn kolom langs de horizontale as is uitgelijnd.

**Verticale uitlijning** specificeert hoe een item binnen zijn kolom langs de verticale as is gepositioneerd. Dit is nuttig wanneer kolommen verschillende hoogtes hebben en je wilt controleren hoe items verticaal worden verdeeld.

Beschikbare `Alignment` opties omvatten:

- `START`: Lijnt het item uit aan de linkerkant van de kolom.
- `CENTER`: Centreert het item horizontaal binnen de kolom.
- `END`: Lijnt het item uit aan de rechterkant van de kolom.
- `STRETCH`: Rekent de component om de lay-out te vullen.
- `BASELINE`: Lijnt op basis van de tekst of inhoud binnen de kolom, en lijnt items uit aan de tekstbasislijn in plaats van andere uitlijningsopties.
- `AUTO`: Auto-uitlijning (standaard).

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

In de demo hierboven is de `Indienen` knop gegeven `ColumnsLayout.Alignment.END` om ervoor te zorgen dat deze aan het eind, of in dit geval aan de rechterkant, van zijn kolom verschijnt.

## Itemspacing {#item-spacing}

Controle over de ruimte in de `ColumnsLayout` tussen kolommen (horizontale spacing) en tussen rijen (verticale spacing) helpt ontwikkelaars de lay-out te optimaliseren.

Om de horizontale spacing van de lay-out in te stellen, gebruik je de `setHorizontalSpacing()` methode:

```java
// Stel 20px ruimte in tussen kolommen
layout.setHorizontalSpacing(20);
```

Op dezelfde manier gebruik je de `setVerticalSpacing()` methode om de ruimte tussen rijen van de lay-out te configureren:

```java
// Stel 15px ruimte in tussen rijen
layout.setVerticalSpacing(15);
```

:::tip CSS-eenheden
Spacing accepteert een geheel getal voor pixels, of een `String` voor andere eenheden zoals `vw` of `%`.
:::

## Horizontale en verticale lay-outs {#horizontal-and-vertical-layouts}

Het maken van responsieve en aantrekkelijke lay-outs is mogelijk met zowel de [`FlexLayout`](./flex-layout) component als de `ColumnsLayout` component, evenals een combinatie van beide. Hieronder staat een voorbeeld van het [formulier gemaakt in de FlexLayout](./flex-layout#example-form) artikel, maar met een `ColumnLayout` schema in plaats daarvan:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Stijlen {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
