---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: fb5b6ef5a20567d8a86d04c022a0449e
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

De `ColumnsLayout` component in webforJ stelt ontwikkelaars in staat om lay-outs te maken met behulp van een flexibele en responsieve verticale lay-out. Deze lay-out biedt dynamische kolommen die zich aanpassen op basis van de beschikbare breedte. Deze component vereenvoudigt het creëren van multi-kolom lay-outs door automatisch de breekpunten en uitlijningen te beheren.

:::info Horizontale lay-outs 
Dit kan worden gebruikt in plaats van, of in combinatie met, de [`FlexLayout`](./flex-layout) component - een even krachtige tool voor horizontale lay-outs.
:::

## Basisprincipes {#basics}

Wanneer de `ColumnsLayout` voor het eerst wordt geïnstantieerd, gebruikt deze twee kolommen om items weer te geven die aan de lay-out zijn toegevoegd. Standaard neemt deze de volledige breedte van zijn bovenliggende elementen en groeit indien nodig om extra inhoud te bevatten. De weergave van toegevoegde items kan verder worden afgestemd met behulp van de [`Breakpoint`](./columns-layout#breakpoints) en [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) instellingen, die in de volgende secties in meer detail worden besproken.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Breekpunten {#breakpoints}

In wezen is de `ColumnsLayout` ontworpen om een vloeiend, rasterachtig systeem te bieden dat zich aanpast aan de breedte van zijn bovenliggende container. In tegenstelling tot traditionele vaste raster systemen, laat deze lay-out ontwikkelaars toe om een aantal kolommen op een bepaalde breedte op te geven, en berekent dynamisch het aantal weergegeven kolommen op basis van ingestelde `Breakpoint` objecten. 

Dit stelt een `ColumnsLayout` in staat om soepel aan te passen van een meer beperkte ruimte op kleine schermen naar een breder gebied op grotere schermen, en biedt een responsief ontwerp aan een ontwikkelaar zonder dat een op maat gemaakte implementatie nodig is.

### Een `Breakpoint` begrijpen {#understanding-a-breakpoint}

Een `Breakpoint` kan worden gespecificeerd met behulp van de `Breakpoint` klasse, die drie parameters accepteert:

1. **Naam (optioneel)**:
Het benoemen van een breekpunt maakt het mogelijk om hier in toekomstige configuraties naar te verwijzen.

2. **Minimale breedte**:
Ieder breekpunt heeft een specifieke range die bepaalt wanneer zijn lay-out wordt toegepast. De minimale breedte wordt expliciet gedefinieerd, en het volgende breekpunt bepaalt de maximale breedte als deze bestaat. Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden op te geven, zoals `vw`, `%`, of `em`.

3. **Aantal kolommen**:
Geef op hoeveel kolommen een breekpunt moet hebben met dit geheel getal.

:::info Evaluatie van `Breakpoint`
Breekpunten worden geëvalueerd in oplopende volgorde van de breedte, wat betekent dat de lay-out het eerste bijpassende breekpunt zal gebruiken.
:::

### Breekpunten toepassen {#applying-breakpoints}

Breekpunten worden op een `ColumnsLayout` toegepast op een van de twee manieren: tijdens de constructie, of door gebruik te maken van de `addBreakpoint(Breakpoint)` methode zoals hieronder weergegeven. 

```java
ColumnsLayout layout = new ColumnsLayout()
    // Eén kolom bij breedtes >= 0px
    .addBreakpoint(new Breakpoint(0, 1))
    // Twee kolommen bij breedtes >= 600px
    .addBreakpoint(new Breakpoint(600, 2))
    // Vier kolommen bij breedtes >= 1200px
    .addBreakpoint(new Breakpoint(1200, 4));  
```

De demonstratie hieronder toont een voorbeeld van het instellen van meerdere breekpunten tijdens de constructie, het gebruik van breekpunten om de [`Span`](#column-span-and-spans-per-breakpoint) van een component te configureren, en demonstreert de herformattering mogelijkheden van de `ColumnsLayout` wanneer de app wordt gewijzigd:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Kolom `Span` en spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Kolomspannen in `ColumnsLayout` stellen je in staat te regelen hoeveel kolommen een item in beslag neemt, waardoor je meer controle hebt over het uiterlijk van je lay-out bij verschillende breedtes. Dit is vooral handig wanneer je wilt dat bepaalde elementen meer of minder ruimte in beslag nemen, afhankelijk van de schermgrootte of ontwerpeisen.

### Kolomspannen beheren {#managing-column-spans}

Standaard neemt elk item in de `ColumnsLayout` precies één kolom in beslag. Je kunt dit gedrag echter aanpassen door de kolomspan voor individuele items in te stellen. Een span specificeert het aantal kolommen dat een item moet innemen.

```java
Button button = new Button("Klik op mij");
layout.addComponent(button);
// Item beslaat twee kolommen
layout.setSpan(button, 2);
```

In het bovenstaande voorbeeld neemt de knop twee kolommen in beslag in plaats van de standaard één. De `setSpan()` methode stelt je in staat op te geven hoeveel kolommen een component binnen de lay-out moet beslaan.

### Kolomspannen aanpassen met breekpunten {#adjusting-column-spans-with-breakpoints}

Je kunt ook kolomspannen dynamisch aanpassen op basis van breekpunten. Deze functie is handig wanneer je wilt dat een item verschillende aantallen kolommen beslaat, afhankelijk van de schermgrootte. Bijvoorbeeld, je wilt misschien dat een element een enkele kolom op mobiele apparaten in beslag neemt, maar meerdere kolommen op grotere schermen.

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
//emailveld zal twee kolommen beslaan wanneer het middelgrote breekpunt actief is
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dit niveau van aanpassing zorgt ervoor dat je lay-out responsief blijft en geschikt gestructureerd is op verschillende apparaten.

## Items binnen kolommen plaatsen {#placing-items-within-columns}

`ColumnsLayout` biedt de mogelijkheid om items in specifieke kolommen te plaatsen, waardoor je meer controle hebt over de rangschikking van elementen. Je kunt handmatig specificeren waar een item moet verschijnen binnen de lay-out, zodat belangrijke componenten zoals bedoeld worden weergegeven.

### Basis kolomplaatsing {#basic-column-placement}

Standaard worden items geplaatst in de eerst beschikbare kolom, van links naar rechts vullen. Je kunt dit gedrag echter overschrijven en de exacte kolom specificeren waar een item moet worden geplaatst. Om een item in een specifieke kolom te plaatsen, gebruik je de `setColumn()` methode. In dit voorbeeld wordt de knop in de tweede kolom van de lay-out geplaatst, ongeacht de volgorde waarin deze ten opzichte van andere componenten is toegevoegd:

```java
Button button = new Button("Verzenden");
layout.addComponent(button);
// Plaats het item in de tweede kolom
layout.setColumn(button, 2);  
```

### Plaatsing aanpassen per breekpunt {#adjusting-placement-per-breakpoint}

Net zoals met kolomspannen, gebruik je breekpunten om de plaatsing van items op basis van de schermgrootte aan te passen. Dit is handig voor het herordenen of verplaatsen van elementen in de lay-out naarmate de viewport verandert.

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
//emailveld verschijnt in de tweede kolom wanneer middelgrote breekpunt actief is
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In de volgende demonstratie, let op het feit dat wanneer het `"medium"` breekpunt wordt geactiveerd, het `email` veld beide kolommen beslaat, en het `confirmPassword` veld wordt geplaatst in de eerste kolom, in plaats van zijn standaard plaatsing in de tweede kolom:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vermijd botsingen
Wanneer meerdere items in een lay-out met verschillende spannen en/of kolomtoewijzingen worden geplaatst, zorg ervoor dat de gecombineerde spannen en plaatsingen van items in een rij niet overlappen. De lay-out probeert automatisch de ruimte soepel te beheren, maar zorgvuldige planning van spannen en breekpunten voorkomt ongewenste weergave van items.
:::

## Verticale en horizontale itemuitlijningen {#vertical-and-horizontal-item-alignments}

Elk item in de `ColumnsLayout` kan zowel horizontaal als verticaal binnen zijn kolom worden uitgelijnd, wat controle geeft over hoe de inhoud binnen de lay-out is gepositioneerd.

**Horizontale uitlijning** van een item wordt geregeld met behulp van de `setHorizontalAlignment()` methode. Deze eigenschap bepaalt hoe een item zich binnen zijn kolom langs de horizontale as uitlijnt.

**Verticale uitlijning** specificeert hoe een item is gepositioneerd binnen zijn kolom langs de verticale as. Dit is nuttig wanneer kolommen verschillende hoogtes hebben en je wilt regelen hoe items verticaal worden verdeeld. 

Beschikbare `Alignment` opties zijn:

- `START`: Lijnt het item links van de kolom uit (standaard).
- `CENTER`: Centreert het item horizontaal binnen de kolom.
- `END`: Lijnt het item rechts van de kolom uit.
- `STRETCH`: Rekent het component om de lay-out te vullen.
- `BASELINE`: Linieert op basis van de tekst of inhoud binnen de kolom, waardoor items worden uitgelijnd op de tekstbasislijn in plaats van andere uitlijningsopties.
- `AUTO`: Automatische uitlijning.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In de demo hierboven heeft de `Verzenden` knop `ColumnsLayout.Alignment.END` gekregen om ervoor te zorgen dat deze aan het einde, of in dit geval rechts, van zijn kolom verschijnt.

## Itemafstand {#item-spacing}

Het regelen van de ruimte tussen kolommen in de `ColumnsLayout` tussen kolommen (horizontale afstand) en tussen rijen (verticale afstand) helpt ontwikkelaars de lay-out te verfijnen.

Om de horizontale afstand van de lay-out in te stellen, gebruik je de `setHorizontalSpacing()` methode:

```java
// Stel 20px ruimte in tussen kolommen
layout.setHorizontalSpacing(20);  
```

Evenzo gebruik je de `setVerticalSpacing()` methode om de ruimte tussen de rijen van de lay-out in te stellen:

```java
// Stel 15px ruimte in tussen rijen
layout.setVerticalSpacing(15);  
```

:::tip CSS-eenheden
Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden op te geven, zoals `vw`, `%`, of `em`.
:::

## Horizontale en verticale lay-outs {#horizontal-and-vertical-layouts}

Responsieve en aantrekkelijke lay-outs bouwen is mogelijk met zowel de [`FlexLayout`](./flex-layout) component als de `ColumnsLayout` component, evenals een combinatie van beide. Hieronder is een voorbeeld van het [formulier dat is gemaakt in het FlexLayout](./flex-layout#example-form) artikel, maar met een `ColumnLayout` schema in plaats van:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Stijlen {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
