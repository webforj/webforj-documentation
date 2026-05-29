---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: 27b0727ced855ad047db6be3e142801f
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

De `ColumnsLayout` component rangschikt items in een responsieve op kolommen gebaseerde lay-out die zich aanpast aan de beschikbare breedte. Breakpoints en uitlijningen worden automatisch beheerd, zodat het bouwen van multi-kolommen formulieren en inhoudsgrid geen aangepaste responsieve logica vereist.

<!-- INTRO_END -->

## Standaard gedrag {#default-behavior}

Standaard rangschikt een `ColumnsLayout` items in twee kolommen en neemt het volledige breedte van zijn ouder. De weergave kan verder worden aangepast met breakpoints en uitlijninstellingen, die in de onderstaande secties worden behandeld.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Horizontale Lay-outs 
Dit kan worden gebruikt in plaats van, of in combinatie met, de [`FlexLayout`](./flex-layout) component - een even krachtige tool voor horizontale lay-outs.
:::

## Breakpoints {#breakpoints}

In zijn kern is de `ColumnsLayout` ontworpen om een vloeibaar, gridachtig systeem te bieden dat zich aanpast aan de breedte van zijn bovenliggende container. In tegenstelling tot traditionele vaste grid systemen, stelt deze lay-out ontwikkelaars in staat om een aantal kolommen op een gegeven breedte op te geven, en berekent dynamisch het aantal weergegeven kolommen op basis van de ingestelde `Breakpoint` objecten.

Dit stelt een `ColumnsLayout` in staat om soepel aan te passen van een meer beperkte ruimte op kleine schermen naar een breder gebied op grotere schermen, en biedt een responsief ontwerp aan een ontwikkelaar zonder dat een aangepaste implementatie nodig is.

### Begrijpen van een `Breakpoint` {#understanding-a-breakpoint}

Een `Breakpoint` kan worden gespecificeerd met de `Breakpoint` klasse, die drie parameters accepteert:

1. **Naam (optioneel)**:
Een breakpoint een naam geven stelt je in staat om het in toekomstige configuraties te verwijzen.

2. **Minimale breedte**:
Elke breakpoint heeft een specifiek bereik dat bepaalt wanneer zijn lay-out wordt toegepast. De minimale breedte is expliciet gedefinieerd, en de volgende breakpoint bepaalt de maximale breedte als deze bestaat. Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden op te geven zoals `vw`, `%` of `em`.

3. **Aantal kolommen**:
Geef op hoeveel kolommen een breakpoint moet hebben met dit geheel getal.


:::info Evaluatie van `Breakpoint`
Breakpoints worden geëvalueerd in opklimmende volgorde van de breedte, wat betekent dat de lay-out de eerste overeenkomende breakpoint zal gebruiken.
:::


### Toevoegen van breakpoints {#applying-breakpoints}

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

De demonstratie hieronder laat een voorbeeld zien van het instellen van meerdere breakpoints bij de constructie, het gebruiken van breakpoints om de [`Span`](#column-span-and-spans-per-breakpoint) van een component te configureren, en demonstreert de herformattering van de `ColumnsLayout` wanneer de app wordt gewijzigd:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Kolom `Span` en spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Kolom spans in `ColumnsLayout` stellen je in staat om te controleren hoeveel kolommen een item inneemt, waardoor je meer controle hebt over de uitstraling van je lay-out bij verschillende breedtes. Dit is vooral nuttig wanneer je bepaalde elementen meer of minder ruimte wilt geven, afhankelijk van de schermgrootte of ontwerpeisen.

### Beheren van kolom spans {#managing-column-spans}

Standaard neemt elk item in de `ColumnsLayout` exact één kolom in beslag. Je kunt dit gedrag echter aanpassen door de kolomspan voor individuele items in te stellen. Een span specificeert het aantal kolommen dat een item moet innemen.

```java
Button button = new Button("Klik op mij");
layout.addComponent(button);
// Item neemt twee kolommen in
layout.setSpan(button, 2);
```

In het bovenstaande voorbeeld neemt de knop twee kolommen in plaats van de standaard één. De `setSpan()` methode stelt je in staat om op te geven hoeveel kolommen een component binnen de lay-out moet innemen.

### Aanpassen van kolom spans met breakpoints {#adjusting-column-spans-with-breakpoints}

Je kunt ook kolom spans dynamisch aanpassen op basis van breakpoints. Deze functie is nuttig wanneer je wilt dat een item verschillende aantallen kolommen in beslag neemt, afhankelijk van de schermgrootte. Bijvoorbeeld, je wilt misschien dat een element één kolom op mobiele apparaten in beslag neemt, maar meerdere kolommen op grotere schermen.

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
//email veld zal twee kolommen in beslag nemen wanneer de medium breakpoint actief is
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dit niveau van aanpassing zorgt ervoor dat je lay-out responsief en goed gestructureerd blijft op verschillende apparaten.

## Items binnen kolommen plaatsen {#placing-items-within-columns}

`ColumnsLayout` biedt de mogelijkheid om items in specifieke kolommen te plaatsen, waardoor je meer controle heeft over de rangschikking van elementen. Je kunt handmatig opgeven waar een item binnen de lay-out moet verschijnen, zodat belangrijke componenten zoals bedoeld worden weergegeven.

### Basis kolom plaatsing {#basic-column-placement}

Standaard worden items geplaatst in de volgende beschikbare kolom, van links naar rechts vul je. Je kunt dit gedrag echter overschrijven en de exacte kolom opgeven waar een item moet worden geplaatst. Om een item in een specifieke kolom te plaatsen, gebruik de `setColumn()` methode. In dit voorbeeld wordt de knop in de tweede kolom van de lay-out geplaatst, ongeacht de volgorde waarin deze is toegevoegd ten opzichte van andere componenten:

```java
Button button = new Button("Verzenden");
layout.addComponent(button);
// Plaats het item in de tweede kolom
layout.setColumn(button, 2);  
```

### Aanpassen van plaatsing per breakpoint {#adjusting-placement-per-breakpoint}

Net als bij kolom spans, gebruik je breakpoints om de plaatsing van items aan te passen op basis van de schermgrootte. Dit is nuttig voor het herordenen of verplaatsen van elementen in de lay-out terwijl het viewport verandert.

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
//email veld zal verschijnen in de tweede kolom wanneer de medium breakpoint actief is
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In de volgende demonstratie, merk op dat wanneer de `"medium"` breakpoint wordt geactiveerd, het `email` veld beide kolommen in beslag neemt, en het `confirmPassword` veld in de eerste kolom wordt geplaatst, in plaats van de standaardplaatsing in de tweede kolom:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Voorkom botsingen
Wanneer meerdere items in een lay-out zijn geplaatst met verschillende spans en/of kolomtoewijzingen, zorg ervoor dat de gecombineerde spans en plaatsingen van items in een rij niet overlappen. De lay-out probeert automatisch de spatiëring goed te beheren, maar zorgvuldige ontwerping van spans en breakpoints voorkomt ongewenste weergave van items.
:::

## Verticale en horizontale uitlijnen van items {#vertical-and-horizontal-item-alignments}

Elk item in de `ColumnsLayout` kan zowel horizontaal als verticaal binnen zijn kolom worden uitgelijnd, wat controle geeft over hoe de inhoud binnen de lay-out is gepositioneerd.

**Horizontale uitlijning** van een item wordt geregeld met behulp van de `setHorizontalAlignment()` methode. Deze eigenschap bepaalt hoe een item zich binnen zijn kolom langs de horizontale as uitlijnt.

**Verticale uitlijning** specificeert hoe een item is gepositioneerd binnen zijn kolom langs de verticale as. Dit is nuttig wanneer kolommen verschillende hoogten hebben en je wilt controleren hoe items verticaal worden verdeeld. 

Beschikbare `Alignment` opties zijn onder andere:

- `START`: Lijnt het item uit aan de linkerkant van de kolom (standaard).
- `CENTER`: Centreert het item horizontaal binnen de kolom.
- `END`: Lijnt het item uit aan de rechterkant van de kolom.
- `STRETCH`: Strekt het component uit zodat het de lay-out opvult.
- `BASELINE`: Lijnt op basis van de tekst of inhoud binnen de kolom, waarbij items worden uitgelijnd op de tekstbaseline in plaats van andere uitlijningsopties.
- `AUTO`: Automatische uitlijning.

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

In de demo hierboven heeft de `Verzend` knop `ColumnsLayout.Alignment.END` gekregen om ervoor te zorgen dat deze aan het einde, of in dit geval aan de rechterkant, van zijn kolom verschijnt.

## Itemspatiëring {#item-spacing}

Het controleren van de ruimte tussen kolommen in de `ColumnsLayout` tussen kolommen (horizontale spatiëring) en tussen rijen (verticale spatiëring) helpt ontwikkelaars om de lay-out in te stellen.

Om de horizontale spatiëring van de lay-out in te stellen, gebruik je de `setHorizontalSpacing()` methode:

```java
// Stel 20px ruimte in tussen kolommen
layout.setHorizontalSpacing(20);  
```

Op dezelfde manier gebruik je de `setVerticalSpacing()` methode om de ruimte tussen rijen van de lay-out te configureren:

```java
// Stel 15px ruimte in tussen rijen
layout.setVerticalSpacing(15);  
```

:::tip CSS eenheden
Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden op te geven zoals `vw`, `%` of `em`.
:::

## Horizontale en verticale lay-outs {#horizontal-and-vertical-layouts}

Het is mogelijk om responsieve en aantrekkelijke lay-outs te bouwen met zowel de [`FlexLayout`](./flex-layout) component als de `ColumnsLayout` component, evenals een combinatie van beide. Hieronder staat een voorbeeld van de [formulier gemaakt in de FlexLayout](./flex-layout#example-form) artikel, maar met een `ColumnLayout` schema in plaats daarvan:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Stijlen {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
