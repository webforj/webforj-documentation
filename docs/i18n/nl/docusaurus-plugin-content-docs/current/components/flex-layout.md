---
title: FlexLayout
sidebar_position: 45
_i18n_hash: 5c12042a5890f07259e77e0d2111a5c6
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

De `FlexLayout` component rangschikt kindcomponenten in een rij of kolom met behulp van het CSS Flexbox-model. Het geeft je controle over uitlijning, ruimte, wikkeling en hoe items groeien of krimpen om de beschikbare ruimte te vullen.

<!-- INTRO_END -->

## Flex layout eigenschappen {#flex-layout-properties}

De eigenschappen van de flex layout kunnen worden onderverdeeld in twee categorieën: eigenschappen die van toepassing zijn op de items binnen een layout, en eigenschappen die van toepassing zijn op de layout zelf. De flex layout, of het bovenliggende element, is een doos/container die één of meer componenten kan bevatten. Alles binnen een Flex Layout wordt een item of kindelement genoemd. De Flex Layout biedt enkele robuuste uitlijnmogelijkheden, die kunnen worden bereikt met behulp van container- of itemeigenschappen.

:::tip
De layoutcomponent van webforJ volgt het patroon van de [CSS flexbox-layout](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Deze tools zijn echter gemaakt om volledig in Java te worden gebruikt en vereisen geen toepassing van CSS buiten de Java API-methoden die worden aangeboden.
:::

## Container eigenschappen {#container-properties}

Container eigenschappen zijn van toepassing op alle componenten binnen een component en niet op de layout zelf. Ze zullen de oriëntatie of plaatsing van de bovenliggende niet beïnvloeden - alleen de kindcomponenten binnenin.

### Richting {#direction}

De Flex Layout zal componenten naast elkaar toevoegen volgens de richting gekozen door de ontwikkelaar - ofwel horizontaal of verticaal. Wanneer je de builder gebruikt, maak je gebruik van de `horizontal()`, `horizontalReverse()`, `vertical()` of `verticalReverse()` methoden bij het aanroepen van de `create()`-methode op een `FlexLayout` object om deze layout te configureren terwijl het object wordt aangemaakt.

Je kunt ook de `setDirection()` methode gebruiken. De horizontale opties zijn `FlexDirection.ROW` (van links naar rechts) of `FlexDirection.ROW_REVERSE` (van rechts naar links), en de verticale opties zijn `FlexDirection.COLUMN` (van boven naar beneden) of `FlexDirection.COLUMN_REVERSE` (van beneden naar boven). Dit gebeurt met het FlexLayout-object, in plaats van met de builder.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionering {#positioning}

Componenten die horizontaal worden toegevoegd, kunnen ook zowel horizontaal als verticaal worden gepositioneerd. Gebruik de `justify()`, `align()` en `contentAlign()` methoden van de Flex Layout Builder om de positionering te configureren bij het maken van een nieuwe Flex Layout.

Alternatief, op het daadwerkelijke FlexLayout object kun je de `setJustifyContent()` methode gebruiken om items horizontaal te positioneren, en de `setAlignment()` methode om de verticale positionering te configureren. Om het gebied rond componenten langs de kruis-as (y-as voor horizontale layouts) aan te passen, gebruik je de `setAlignContent()` methode.

:::tip
De `setAlignment()` methode regelt hoe items zich langs de kruis-as als geheel binnen de container zullen weergeven, en is effectief voor single-line layouts.

De `setAlignContent()` methode regelt de ruimte rond de kruis-as en zal alleen effect hebben wanneer een layout meerdere lijnen heeft.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Wikkeling {#wrapping}

Om de Flex Layout component verder aan te passen, kun je het gedrag van de flex layout specificeren wanneer componenten die worden toegevoegd niet langer binnen de weergave passen. Om dit te configureren met de builder, gebruik je de `nowrap()` (standaard), `wrap()` en `wrapReverse()` methoden om te configureren hoe te wikkelen.

Als je layout al bestaat, gebruik dan de `setWrap()` methode om te bepalen hoe componenten zich zullen gedragen wanneer ze niet meer op één lijn kunnen passen.

### Ruimte {#spacing}

Om een minimale ruimte tussen items toe te passen, kun je de gap-eigenschap instellen. Het past deze ruimte alleen tussen items toe, niet aan de buitenranden.

Het gedrag van de gap-eigenschap kan worden beschouwd als een minimale afstand tussen - deze eigenschap zal alleen effect hebben als het de grootste berekende ruimte tussen items is. Als de ruimte tussen items anders groter zou zijn vanwege een andere berekende eigenschap, zoals door `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, dan zal de gap-eigenschap worden genegeerd.

### Stroom {#flow}

Flex-stroom, dat een combinatie is van zowel de richting als de wikkeling-eigenschappen, kan worden ingesteld met de `setFlow()` methode op een Flex Layout-object. 

:::info
Om deze eigenschap te configureren bij het maken van de layout, gebruik de juiste richting- en wikkelmethode. Gebruik bijvoorbeeld de `.vertical().wrap()` combinatie om een kolomwikkelstroom te creëren.
:::

### Container builder {#container-builder}

De volgende demo stelt je in staat om een container te bouwen met de gewenste flex-eigenschappen geselecteerd uit de verschillende menu's. Deze tool kan niet alleen worden gebruikt om een visueel voorbeeld van de verschillende methoden te maken, maar ook als hulpmiddel om je eigen layouts met je gewenste eigenschappen te creëren. Om een layout te gebruiken die je hebt gepersonaliseerd, kopieer je eenvoudig de uitvoercode en voeg je je gewenste elementen toe voor gebruik in je programma.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- LARGE CODE SNIPPET SHOWING CONTAINER -->
## Item eigenschappen {#item-properties}

Item eigenschappen zullen geen invloed hebben op de kindelementen binnen de Flex Layout, maar eerder op de daadwerkelijke Layout zelf. Dit is nuttig voor het stijlen van een enkele Flex Layout-element die een kind is van een groter Flex Layout-element, onafhankelijk van stijlen die op alle kinderen worden toegepast.

### Volgorde {#order}

De `ItemOrder` eigenschap bepaalt hoe componenten worden weergegeven binnen de Flex Layout, en wanneer gebruikt op een Flex Layout, zal het een item dat specifieke volgorde nummer van die layout toewijzen. Dit overschrijft de standaard "bronvolgorde" die in elk item is ingebouwd (de volgorde waarin een component aan zijn bovenliggende wordt toegevoegd), en betekent dat het voor items met een hogere volgorde moet worden weergegeven, en na items met een lagere volgorde.

Deze eigenschap accepteert een geheel waar waarde zonder eenheid die de relatieve volgorde van het flex-item binnen de container specificeert. Hoe lager de waarde, hoe eerder het item in de volgorde verschijnt. Bijvoorbeeld, een item met een volgorde waarde van 1 verschijnt voor een item met een volgorde waarde van 2.

:::caution
Het is belangrijk op te merken dat de volgorde-eigenschap alleen de visuele volgorde van de items binnen de container beïnvloedt, niet hun werkelijke positie in de DOM. Dit betekent dat schermlezers en andere hulpmiddelen voor mensen met een beperking de items nog steeds lezen in de volgorde waarin ze in de broncode voorkomen, niet in de visuele volgorde.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Zelf uitlijning {#self-alignment}

De zelfuitlijning van de Flex Layout verwijst naar hoe een enkel Flex Layout-object is uitgelijnd binnen zijn bovenliggende flex-container langs de kruis-as, die loodrecht staat op de hoofd-as. De kruis-asuitlijning wordt geregeld door de `Alignment` eigenschap.

De align-self-eigenschap specificeert de uitlijning van een enkel flex-item langs de kruis-as, waarmee de standaarduitlijning die is ingesteld door de `AlignContent` eigenschap in een Flex Layout-object, wordt overschreven. Dit stelt je in staat om individuele Flex Layout-objecten anders uit te lijnen dan de anderen in de container.

:::info
Zelfuitlijning gebruikt dezelfde waarden als inhoudsuitlijning.
:::

Deze eigenschap is vooral nuttig wanneer je een specifiek item anders moet uitlijnen dan de andere items in de container. Zie de onderstaande voorbeeld voor een voorbeeld van het uitlijnen van een enkel item:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex basis {#flex-basis}

`Item Basis` is een eigenschap die wordt gebruikt in combinatie met de richting van de Flex Layout om de initiële grootte van een flex-item in te stellen voordat er aanvullende ruimte wordt verdeeld.

De `Item Basis` eigenschap specificeert de standaardgrootte van een flex-item langs de hoofd-as, die ofwel horizontaal (voor een rijrichting) of verticaal (voor een kolomrichting) kan zijn. Deze eigenschap stelt de breedte of hoogte van een flex-item in afhankelijk van de waarde van de flex-direction eigenschap.

:::info
Standaard is de `Item Basis` eigenschap ingesteld op auto, wat betekent dat de grootte van het item wordt bepaald door de inhoud ervan. Je kunt echter ook een specifieke grootte voor het item instellen met verschillende eenheden zoals pixels (px), ems (em), percentages (%) of een andere CSS-lengte-eenheid.
:::

De volgende demo stelt je in staat om een of meer vakken te selecteren en de `Item Basis` voor de geselecteerde items te wijzigen.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex groeien / krimpen {#flex-grow--shrink}

`Item Grow` en `Item Shrink` zijn twee eigenschappen die samenwerken met elkaar en met de `Item Basis` eigenschap om te bepalen hoe flex-items groeien of krimpen om de beschikbare ruimte binnen een Flex Layout-object te vullen.

De `Item Grow` eigenschap specificeert hoeveel het flex-item kan groeien ten opzichte van de andere items in de container. Het neemt een waarde zonder eenheid aan die een verhouding van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als één item een `Item Grow` waarde van 1 heeft en een ander een waarde van 2, zal het tweede item twee keer zoveel groeien als het eerste item.

De `Item Shrink` eigenschap daarentegen specificeert hoeveel het flex-item kan krimpen ten opzichte van de andere items in de container. Het neemt ook een waarde zonder eenheid aan die een verhouding van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als één item een `Item Shrink` waarde van 1 heeft en een ander een waarde van 2, zal het tweede item twee keer zoveel krimpen als het eerste item.

Wanneer een container meer ruimte heeft dan nodig is om zijn inhoud te herbergen, zullen flex-items met een `Item Grow` waarde groter dan 0 zich uitbreiden om de beschikbare ruimte te vullen. De hoeveelheid ruimte die elk item krijgt, wordt bepaald door de verhouding van zijn `Item Grow` waarde tot de totale `Item Grow` waarde van alle items in de container.

Evenzo, wanneer een container niet genoeg ruimte heeft om zijn inhoud te herbergen, zullen flex-items met een `Item Shrink` waarde groter dan 0 zich krimpen om in de beschikbare ruimte te passen. De hoeveelheid ruimte die elk item opgeeft, wordt bepaald door de verhouding van zijn `Item Shrink` waarde tot de totale `Item Shrink` waarde van alle items in de container.

## Voorbeeldformulier {#example-form}
Het onderstaande formulier demonstreert hoe `FlexLayout` invoervelden organiseert in een gestructureerde layout. 

:::tip
Als je de voorkeur geeft aan een kolomgebaseerde structuur, bekijk dan de ColumnsLayout-versie van dit formulier in het [`ColumnsLayout`](../components/columns-layout) artikel om te zien hoe het zich verhoudt.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
