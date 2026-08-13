---
title: ColumnsLayout
sidebar_position: 25
description: >-
  Arrange child components into a responsive multi-column grid with the
  ColumnsLayout component using configurable breakpoints and alignment.
_i18n_hash: d75bb3fcf3260672e15ef9acbb38e295
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

De `ColumnsLayout` component rangschikt items in een responsieve kolom-gebaseerde lay-out die zich aanpast op basis van de beschikbare breedte. Breekpunten en uitlijningen worden automatisch beheerd, zodat het bouwen van multi-kolom formulieren en inhoudsroosters geen aangepaste responsieve logica vereist.

<!-- INTRO_END -->

## Standaardgedrag {#default-behavior}

Standaard rangschikt een `ColumnsLayout` items in twee kolommen en neemt het volledige breedte van zijn ouder. De weergave kan verder worden aangepast met breekpunten en uitlijningsinstellingen, die in de onderstaande secties worden behandeld.

<ComponentDemo
path='/webforj/columnslayout'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java']}
height='450px'
/>

:::info Horizontale indelingen
Dit kan worden gebruikt in plaats van, of in combinatie met, de [`FlexLayout`](./flex-layout) component - een even krachtig hulpmiddel voor horizontale indelingen.
:::

## Breekpunten {#breakpoints}

In zijn kern is de `ColumnsLayout` ontworpen om een vloeibaar, roosterachtig systeem te bieden dat zich aanpast aan de breedte van zijn bovenliggende container. In tegenstelling tot traditionele vaste rooster systemen, stelt deze lay-out ontwikkelaars in staat om een aantal kolommen op een bepaalde breedte op te geven en berekent dinamisch het aantal weergegeven kolommen op basis van ingestelde `Breakpoint` objecten.

Dit stelt een `ColumnsLayout` in staat om soepel over te schakelen van een meer beperkte ruimte op kleine schermen naar een breder gebied op grotere schermen, en biedt een responsief ontwerp aan een ontwikkelaar zonder dat er een aangepaste implementatie nodig is.

### Begrijpen van een `Breakpoint` {#understanding-a-breakpoint}

Een `Breakpoint` kan worden gespecificeerd met behulp van de `Breakpoint` klasse, die drie parameters neemt:

1. **Naam (optioneel)**:
Een breekpunt een naam geven stelt je in staat om er in toekomstige configuraties naar te verwijzen.

2. **Minimale breedte**:
Elk breekpunt heeft een specifiek bereik dat bepaalt wanneer de lay-out wordt toegepast. De minimale breedte wordt expliciet gedefinieerd, en het volgende breekpunt bepaalt de maximale breedte als deze bestaat. Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden zoals `vw`, `%`, of `em` op te geven.

3. **Aantal kolommen**:
Geef op hoeveel kolommen een breekpunt moet hebben met dit geheel getal.

:::info Evaluatie van `Breakpoint`
Breekpunten worden geëvalueerd in oplopende volgorde van de breedte, wat betekent dat de lay-out het eerste overeenkomende breekpunt zal gebruiken.
:::

### Breekpunten toepassen {#applying-breakpoints}

Breekpunten worden op een `ColumnsLayout` op een van de twee manieren toegepast: tijdens de constructie, of in een `List` met de `setBreakpoints()` methode:

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

De demonstratie hieronder toont een voorbeeld van het instellen van meerdere breekpunten bij constructie, het gebruiken van breekpunten om de [`Span`](#column-span-and-spans-per-breakpoint) van een component te configureren, en demonstreert de aanpassingscapaciteiten van de `ColumnsLayout` wanneer de app opnieuw wordt ingesteld:

<ComponentDemo
path='/webforj/columnslayoutbreakpoints'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java']}
height='375px'
/>

## Kolom `Span` en spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Kolom spans in `ColumnsLayout` stellen je in staat te bepalen hoeveel kolommen een item in beslag neemt, waardoor je meer controle krijgt over de uitstraling van je lay-out bij verschillende breedtes. Dit is vooral nuttig wanneer je bepaalde elementen meer of minder ruimte wilt laten innemen, afhankelijk van de schermgrootte of ontwerpeisen.

### Kolom spans beheren {#managing-column-spans}

Standaard neemt elk item in de `ColumnsLayout` precies één kolom in beslag. Je kunt dit gedrag echter aanpassen door de kolomspan voor individuele items in te stellen. Een span specificeert het aantal kolommen dat een item moet innemen.

```java
Button button = new Button("Klik hier");
layout.addComponent(button);
// Item beslaat twee kolommen
layout.setSpan(button, 2);
```

In het bovenstaande voorbeeld neemt de knop twee kolommen in beslag in plaats van de standaard één. De `setSpan()` methode stelt je in staat op te geven hoeveel kolommen een component binnen de lay-out moet beslaan.

### Kolom spans aanpassen met breekpunten {#adjusting-column-spans-with-breakpoints}

Je kunt ook kolom spans dynamisch aanpassen op basis van breekpunten. Deze functie is nuttig wanneer je wilt dat een item verschillende aantallen kolommen beslaat afhankelijk van de schermgrootte. Bijvoorbeeld, je wilt misschien dat een element één kolom inneemt op mobiele apparaten maar meerdere kolommen op grotere schermen.

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
//email veld zal twee kolommen beslaan wanneer het medium breekpunt actief is
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dit niveau van aanpassing zorgt ervoor dat je lay-out responsief blijft en geschikt gestructureerd is over verschillende apparaten.

## Items plaatsen binnen kolommen {#placing-items-within-columns}

`ColumnsLayout` biedt de mogelijkheid om items in specifieke kolommen te plaatsen, waardoor je meer controle krijgt over de rangschikking van elementen. Je kunt handmatig specificeren waar een item binnen de lay-out moet verschijnen, zodat belangrijke componenten worden weergegeven zoals bedoeld.

### Basis kolomplaatsing {#basic-column-placement}

Standaard worden items geplaatst in de volgende beschikbare kolom, waarbij van links naar rechts wordt gevuld. Je kunt dit gedrag echter overschrijven en de exacte kolom specificeren waarin een item moet worden geplaatst. Om een item in een specifieke kolom te plaatsen, gebruik je de `setColumn()` methode. In dit voorbeeld wordt de knop in de tweede kolom van de lay-out geplaatst, ongeacht de volgorde waarin deze is toegevoegd ten opzichte van andere componenten:

```java
Button button = new Button("Verzenden");
layout.addComponent(button);
// Plaats het item in de tweede kolom
layout.setColumn(button, 2);
```

### Plaatsing aanpassen per breekpunt {#adjusting-placement-per-breakpoint}

Net als met kolom spans, gebruik je breekpunten om de plaatsing van items aan te passen op basis van de schermgrootte. Dit is nuttig voor het herordenen of verplaatsen van elementen in de lay-out naarmate de viewport verandert.

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
//email veld verschijnt in de tweede kolom wanneer het medium breekpunt actief is
columnsLayout.setColumn(email, "medium", 2);
//...
```

In de volgende demonstratie, let op dat wanneer het `"medium"` breekpunt wordt geactiveerd, het `email` veld beide kolommen beslaat, en het `confirmPassword` veld in de eerste kolom wordt geplaatst, in plaats van de standaardplaatsing in de tweede kolom:

<ComponentDemo
path='/webforj/columnslayoutspancolumn'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java']}
height='375px'
/>

:::tip Vermijd botsingen
Wanneer meerdere items worden geplaatst in een lay-out met verschillende spans en/of kolomtoewijzingen, zorg ervoor dat de gecombineerde spans en plaatsingen van items in een rij zich niet overlappen. De lay-out probeert automatisch de ruimte netjes te beheren, maar zorgvuldige ontwerpeisen van spans en breekpunten voorkomen onbedoelde weergave van items.
:::

## Verticale en horizontale item uitlijningen {#vertical-and-horizontal-item-alignments}

Elk item in de `ColumnsLayout` kan zowel horizontaal als verticaal binnen zijn kolom worden uitgelijnd, waardoor controle wordt gegeven over hoe de inhoud binnen de lay-out is gepositioneerd.

**Horizontale uitlijning** van een item wordt geregeld met behulp van de `setHorizontalAlignment()` methode. Deze eigenschap bepaalt hoe een item binnen zijn kolom langs de horizontale as is uitgelijnd.

**Verticale uitlijning** specificeert hoe een item binnen zijn kolom langs de verticale as is gepositioneerd. Dit is nuttig wanneer kolommen verschillende hoogtes hebben en je wilt bepalen hoe items verticaal worden verdeeld.

Beschikbare `Alignment` opties omvatten:

- `START`: Lijnt het item uit aan de linkerkant van de kolom (standaard).
- `CENTER`: Centreert het item horizontaal binnen de kolom.
- `END`: Lijnt het item uit aan de rechterkant van de kolom.
- `STRETCH`: Strekt de component om de lay-out te vullen.
- `BASELINE`: Lijnt uit op basis van de tekst of inhoud binnen de kolom, waarbij items worden uitgelijnd op de tekstbasislijn in plaats van andere uitlijningsopties.
- `AUTO`: Auto uitlijning.

<ComponentDemo
path='/webforj/columnslayoutalignment'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java']}
height='500px'
/>

In de demo hierboven heeft de `Verzenden` knop `ColumnsLayout.Alignment.END` gekregen om ervoor te zorgen dat deze aan het einde, of in dit geval aan de rechterkant, van zijn kolom verschijnt.

## Item ruimte {#item-spacing}

Het beheersen van de ruimte tussen kolommen in de `ColumnsLayout` tussen kolommen (horizontale ruimte) en tussen rijen (verticale ruimte) helpt ontwikkelaars de lay-out fijn af te stellen.

Om de horizontale ruimte van de lay-out in te stellen, gebruik je de `setHorizontalSpacing()` methode:

```java
// Stel 20px ruimte in tussen kolommen
layout.setHorizontalSpacing(20);
```

Op dezelfde manier gebruik je de `setVerticalSpacing()` methode om de ruimte tussen rijen van de lay-out in te stellen:

```java
// Stel 15px ruimte in tussen rijen
layout.setVerticalSpacing(15);
```

:::tip CSS eenheden
Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden zoals `vw`, `%`, of `em` op te geven.
:::

## Horizontale en verticale lay-outs {#horizontal-and-vertical-layouts}

Het bouwen van responsieve en aantrekkelijke lay-outs is mogelijk met behulp van zowel de [`FlexLayout`](./flex-layout) component als de `ColumnsLayout` component, evenals een combinatie van de twee. Hieronder is een voorbeeld van het [formulier dat in de FlexLayout](./flex-layout#example-form) artikel is gemaakt, maar met een `ColumnLayout` schema in plaats daarvan:

<ComponentDemo
path='/webforj/columnslayoutform'
files={['src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java']}
height='700px'
/>

## Stijlen {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
