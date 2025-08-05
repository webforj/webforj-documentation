---
title: ColumnsLayout
sidebar_position: 25
_i18n_hash: ed7626149e8b31e663de874e83935567
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-columns-layout" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="columnslayout" location="com/webforj/component/layout/columnslayout/ColumnsLayout" top='true'/>

De `ColumnsLayout` component in webforJ stelt ontwikkelaars in staat om lay-outs te maken met behulp van een flexibele en responsieve verticale lay-out. Deze lay-out biedt dynamische kolommen die zich aanpassen aan de beschikbare breedte. Deze component vereenvoudigt het maken van multi-kolom lay-outs door automatisch breekpunten en uitlijningen te beheren.

:::info Horizontale Lay-outs
Dit kan worden gebruikt in plaats van, of in combinatie met, de [`FlexLayout`](./flex-layout) component - een even krachtig hulpmiddel voor horizontale lay-outs.
:::

## Basisprincipes {#basics}

Wanneer de `ColumnsLayout` voor het eerst wordt geïnstantieerd, gebruikt deze twee kolommen om de aan de lay-out toegevoegde items weer te geven. Standaard neemt het de volledige breedte van zijn bovenliggende elementen in en groeit het indien nodig om extra inhoud te passen. De weergave van toegevoegde items kan verder worden gekalibreerd met behulp van de [`Breakpoint`](./columns-layout#breakpoints) en [`Alignment`](./columns-layout#vertical-and-horizontal-item-alignments) instellingen, die in de volgende secties in meer detail worden besproken.

<ComponentDemo 
path='/webforj/columnslayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutView.java'
height="450px"
/>

## Breekpunten {#breakpoints}

In wezen is de `ColumnsLayout` ontworpen om een vloeiend, roosterachtig systeem te bieden dat zich aanpast aan de breedte van zijn bovenliggende container. In tegenstelling tot traditionele vaste rooster systemen, stelt deze lay-out ontwikkelaars in staat om een aantal kolommen bij een bepaalde breedte op te geven, en berekent dynamisch het aantal weergegeven kolommen op basis van ingestelde `Breakpoint` objecten.

Dit stelt een `ColumnsLayout` in staat om soepel aan te passen van een meer beperkte ruimte op kleine schermen naar een bredere ruimte op grotere schermen, en biedt een responsief ontwerp aan een ontwikkelaar zonder dat aangepaste implementatie nodig is.

### Begrijpen van een `Breakpoint` {#understanding-a-breakpoint}

Een `Breakpoint` kan worden gespecificeerd met de `Breakpoint` klasse, die drie parameters accepteert:

1. **Naam (optioneel)**:
Een breekpunt een naam geven stelt je in staat om het in toekomstige configuraties te verwijzen.

2. **Minimale breedte**:
Elk breekpunt heeft een specifiek bereik dat bepaalt wanneer zijn lay-out wordt toegepast. De minimale breedte is expliciet gedefinieerd, en het volgende breekpunt bepaalt de maximale breedte als deze bestaat. Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden zoals `vw`, `%` of `em` op te geven.

3. **Aantal kolommen**:
Geef op hoeveel kolommen een breekpunt moet hebben met dit gehele getal.


:::info Evaluatie van `Breakpoint`
Breekpunten worden geëvalueerd in oplopende volgorde van de breedte, wat betekent dat de lay-out het eerste overeenkomende breekpunt zal gebruiken.
:::


### Breekpunten toepassen {#applying-breakpoints}

Breekpunten worden op een `ColumnsLayout` op een van de twee manieren toegepast: tijdens de constructie, of door gebruik te maken van de `addBreakpoint(Breakpoint)` methode zoals hieronder weergegeven.

```java
ColumnsLayout layout = new ColumnsLayout()
    // Eén kolom bij breedtes >= 0px
    .addBreakpoint(new Breakpoint(0, 1))
    // Twee kolommen bij breedtes >= 600px
    .addBreakpoint(new Breakpoint(600, 2))
    // Vier kolommen bij breedtes >= 1200px
    .addBreakpoint(new Breakpoint(1200, 4));  
```

De demonstratie hieronder toont een voorbeeld van het instellen van meerdere breekpunten bij constructie, het gebruik van breekpunten om de [`Span`](#column-span-and-spans-per-breakpoint) van een component te configureren, en demonstreert de wijzigingsmogelijkheden van de `ColumnsLayout` wanneer de app wordt gewijzigd in grootte:

<ComponentDemo 
path='/webforj/columnslayoutbreakpoints?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutBreakpointsView.java'
height="375px"
/>

## Kolom `Span` en spans per `Breakpoint` {#column-span-and-spans-per-breakpoint}

Kolomspans in `ColumnsLayout` stellen je in staat om te controleren hoeveel kolommen een item bezet, waardoor je meer controle hebt over het uiterlijk van je lay-out bij verschillende breedtes. Dit is vooral nuttig wanneer je bepaalde elementen meer of minder ruimte wilt laten innemen, afhankelijk van de schermgrootte of ontwerpeisen.

### Beheren van kolomspans {#managing-column-spans}

Standaard neemt elk item in de `ColumnsLayout` precies één kolom in. Je kunt echter dit gedrag aanpassen door de kolomspan voor individuele items in te stellen. Een span geeft het aantal kolommen aan dat een item zou moeten innemen.

```java
Button button = new Button("Klik op mij");
layout.addComponent(button);
// Item beslaat twee kolommen
layout.setSpan(button, 2);
```

In het bovenstaande voorbeeld neemt de knop twee kolommen in in plaats van de standaard één. De `setSpan()` methode stelt je in staat om op te geven hoeveel kolommen een component zou moeten beslaan binnen de lay-out.

### Aanpassen van kolomspans met breekpunten {#adjusting-column-spans-with-breakpoints}

Je kunt ook kolomspans dynamisch aanpassen op basis van breekpunten. Deze functie is handig wanneer je wilt dat een item verschillende aantallen kolommen beslaat, afhankelijk van de schermgrootte. Bijvoorbeeld, je wilt misschien dat een element één kolom in beslag neemt op mobiele apparaten, maar meerdere kolommen op grotere schermen.

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
//email veld zal twee kolommen beslaan wanneer het medium breekpunt actief is
columnsLayout.setSpan(email, "medium", 2);
//...
```

Dit niveau van maatwerk zorgt ervoor dat je lay-out responsief en goed gestructureerd blijft op verschillende apparaten.

## Items plaatsen binnen kolommen {#placing-items-within-columns}

`ColumnsLayout` biedt de mogelijkheid om items in specifieke kolommen te plaatsen, waardoor meer controle over de schikking van elementen mogelijk is. Je kunt handmatig opgeven waar een item binnen de lay-out moet verschijnen, zodat belangrijke componenten worden weergegeven zoals bedoeld.

### Basis kolomplaatsing {#basic-column-placement}

Standaard worden items in de volgende beschikbare kolom geplaatst, van links naar rechts. Je kunt echter dit gedrag overschrijven en de exacte kolom specificeren waarin een item moet worden geplaatst. Om een item in een specifieke kolom te plaatsen, gebruik je de `setColumn()` methode. In dit voorbeeld wordt de knop in de tweede kolom van de lay-out geplaatst, ongeacht de volgorde waarin deze was toegevoegd ten opzichte van andere componenten:

```java
Button button = new Button("Verzenden");
layout.addComponent(button);
// Plaats het item in de tweede kolom
layout.setColumn(button, 2);  
```

### Aanpassen van plaatsing per breekpunt {#adjusting-placement-per-breakpoint}

Net als bij kolomspans, gebruik je breekpunten om de plaatsing van items aan te passen op basis van de schermgrootte. Dit is nuttig voor het opnieuw ordenen of verplaatsen van elementen in de lay-out wanneer het venster verandert.

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
//email veld zal in de tweede kolom verschijnen wanneer het medium breekpunt actief is
columnsLayout.setColumn(email, "medium", 2); 
//...
```

In de volgende demonstratie, let op dat wanneer het `"medium"` breekpunt wordt geactiveerd, het `email` veld beide kolommen beslaat, en het `confirmPassword` veld in de eerste kolom is geplaatst, in plaats van de standaardplaatsing in de tweede kolom:

<ComponentDemo 
path='/webforj/columnslayoutspancolumn?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutSpanColumnView.java'
height="375px"
/>

:::tip Vermijd botsingen
Wanneer meerdere items in een lay-out worden geplaatst met verschillende spans en/of kolomtoewijzingen, zorg ervoor dat de gecombineerde spans en plaatsingen van items in een rij niet overlappen. De lay-out probeert de ruimte automatisch netjes te beheren, maar zorgvuldige ontwerping van spans en breekpunten voorkomt onbedoelde weergave van items.
:::

## Verticale en horizontale itemuitlijningen {#vertical-and-horizontal-item-alignments}

Elk item in de `ColumnsLayout` kan zowel horizontaal als verticaal binnen zijn kolom worden uitgelijnd, waardoor controle over de positionering van inhoud binnen de lay-out mogelijk is.

**Horizontale uitlijning** van een item wordt gecontroleerd met behulp van de `setHorizontalAlignment()` methode. Deze eigenschap bepaalt hoe een item binnen zijn kolom langs de horizontale as is uitgelijnd.

**Verticale uitlijning** specificeert hoe een item binnen zijn kolom langs de verticale as is gepositioneerd. Dit is nuttig wanneer kolommen verschillende hoogtes hebben en je wilt controleren hoe items verticaal worden verdeeld.

Beschikbare `Alignment` opties zijn onder andere:

- `START`: Lijnt het item aan de linkerkant van de kolom (standaard).
- `CENTER`: Centreert het item horizontaal binnen de kolom.
- `END`: Lijnt het item aan de rechterkant van de kolom.
- `STRETCH`: Rek de component om de lay-out te vullen
- `BASELINE`: Lijnt op basis van de tekst of inhoud binnen de kolom, waarbij items worden uitgelijnd op de tekstbasis in plaats van op andere uitlijningsopties.
- `AUTO`: Automatische uitlijning.

<ComponentDemo 
path='/webforj/columnslayoutalignment?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutAlignmentView.java'
height="500px"
/>

In de demo hierboven is de `Verzenden` knop gegeven `ColumnsLayout.Alignment.END` om ervoor te zorgen dat deze aan het einde, of in dit geval aan de rechterkant, van zijn kolom verschijnt.

## Itemruimte {#item-spacing}

Het beheersen van de ruimte tussen kolommen in de `ColumnsLayout` tussen kolommen (horizontale ruimte) en tussen rijen (verticale ruimte) helpt ontwikkelaars om de lay-out fijn af te stemmen.

Om de horizontale ruimte van de lay-out in te stellen, gebruik je de `setHorizontalSpacing()` methode:

```java
// Zet 20px ruimte tussen kolommen
layout.setHorizontalSpacing(20);  
```

Evenzo, gebruik de `setVerticalSpacing()` methode om de ruimte tussen rijen van de lay-out in te stellen:

```java
// Zet 15px ruimte tussen rijen
layout.setVerticalSpacing(15);  
```

:::tip CSS-eenheden
Je kunt een geheel getal gebruiken om de minimale breedte in pixels te definiëren of een `String` gebruiken om andere eenheden zoals `vw`, `%` of `em` op te geven.
:::

## Horizontale en verticale lay-outs {#horizontal-and-vertical-layouts}

Het bouwen van responsieve en aantrekkelijke lay-outs is mogelijk met zowel de [`FlexLayout`](./flex-layout) component als de `ColumnsLayout` component, evenals een combinatie van de twee. Hieronder is een voorbeeld van de [formulier gecreëerd in de FlexLayout](./flex-layout#example-form) artikel, maar met een `ColumnLayout` schema in plaats daarvan:

<ComponentDemo 
path='/webforj/columnslayoutform?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/columnslayout/ColumnsLayoutFormView.java'
height="700px"
/>

## Stijling {#styling}

<TableBuilder name="ColumnsLayout" clientComponent />
