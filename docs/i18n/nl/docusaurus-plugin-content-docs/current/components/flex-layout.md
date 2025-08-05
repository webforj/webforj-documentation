---
title: FlexLayout
sidebar_position: 45
_i18n_hash: df051e46de48f07807bf0dc3bcaa641a
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ biedt ontwikkelaars een efficiënte en intuïtieve manier om hun verschillende applicaties en componenten te structureren - de Flex Layout. Deze set tools stelt in staat om items zowel verticaal als horizontaal weer te geven.

## Flex layout eigenschappen {#flex-layout-properties}

De eigenschappen van de flex layout kunnen in twee categorieën worden ingedeeld: eigenschappen die van toepassing zijn op de items binnen een layout, en eigenschappen die van toepassing zijn op de layout zelf. De flex layout, of het bovenliggende element, is een doos/container die één of meer componenten kan bevatten. Alles binnen een Flex Layout wordt een item of kindelement genoemd. De Flex Layout biedt enkele robuuste uitlijnmogelijkheden, die kunnen worden bereikt met behulp van container- of itemeigenschappen.

:::tip
De layoutcomponent van webforJ volgt het patroon van [CSS's flexbox layout](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Deze tools zijn echter gemaakt voor gebruik in Java en vereisen geen toepassing van CSS buiten de Java API-methoden die worden aangeboden.
:::

## Container eigenschappen {#container-properties}

Container eigenschappen zijn van toepassing op alle componenten binnen een component en niet op de layout zelf. Ze hebben geen invloed op de oriëntatie of plaatsing van de bovenliggende container - alleen op de kindcomponenten binnenin.

### Richting {#direction}

De Flex Layout voegt componenten naast elkaar toe volgens de richting die door de ontwikkelaar is gekozen - ofwel horizontaal of verticaal. Wanneer je de builder gebruikt, maak dan gebruik van de `horizontal()`, `horizontalReverse()`, `vertical()` of `verticalReverse()` methoden wanneer je de `create()` methode op een `FlexLayout` object aanroept om deze layout in te stellen terwijl het object wordt aangemaakt.

Alternatief kun je de `setDirection()` methode gebruiken. De horizontale opties zijn `FlexDirection.ROW` (van links naar rechts) of `FlexDirection.ROW_REVERSE` (van rechts naar links), en de verticale opties zijn `FlexDirection.COLUMN` (van boven naar beneden) of `FlexDirection.COLUMN_REVERSE` (van beneden naar boven). Dit gebeurt met het FlexLayout-object, in plaats van met de builder.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionering {#positioning}

Componenten die horizontaal zijn toegevoegd, kunnen ook zowel horizontaal als verticaal worden gepositioneerd. Gebruik de `justify()`, `align()` en `contentAlign()` methoden van de Flex Layout Builder om de positionering in te stellen bij het maken van een nieuwe Flex Layout.

Alternatief kun je op het daadwerkelijke FlexLayout-object de `setJustifyContent()` methode gebruiken om items horizontaal te positioneren, en de `setAlignment()` methode om de verticale positionering in te stellen. Om het gebied rond componenten langs de kruisassen (y-as voor horizontale layouts) aan te passen, gebruik je de `setAlignContent()` methode.

:::tip
De `setAlignment()` methode bepaalt hoe items langs de kruisassen als geheel binnen de container worden weergegeven, en is effectief voor layouts met één lijn.

De `setAlignContent()` methoden bepalen de ruimte rond de kruisassen, en zullen alleen effect hebben wanneer een layout meerdere lijnen heeft.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Wrapping {#wrapping}

Om de Flex Layout component verder aan te passen, kun je het gedrag van de flex layout specificeren wanneer componenten niet meer in de weergave passen. Om dit in te stellen met de builder, maak je gebruik van de `nowrap()` (standaard), `wrap()` en `wrapReverse()` methoden om het wrappen in te stellen.

Alternatief, als je layout al bestaat, gebruik je de `setWrap()` methode om te dictateren hoe componenten zich zullen gedragen zodra ze niet meer in één regel passen.

### Spacing {#spacing}

Om minimale ruimte tussen items aan te brengen, kun je de gap-eigenschap instellen. Het past deze ruimte alleen toe tussen items, niet op de buitenste randen.

Het gedrag van de gap-eigenschap kan worden gezien als een minimale afstand tussen - deze eigenschap heeft alleen effect als het de grootste berekende ruimte tussen items is. Als de ruimte tussen items anders groter zou zijn door een andere berekende eigenschap, zoals door `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, dan zal de gap-eigenschap worden genegeerd.

### Flow {#flow}

Flex flow, dat een combinatie is van zowel de richting als de wrap-eigenschappen, kan worden ingesteld met de `setFlow()` methode op een Flex Layout object.

:::info
Om deze eigenschap te configureren bij het maken van de layout, gebruik je de juiste richtings- en wrap-methoden. Bijvoorbeeld, om een kolom wrap flow te creëren, gebruik je de `.vertical().wrap()` combinatie.
:::

### Container builder {#container-builder}

De volgende demo stelt je in staat om een container te bouwen met de gewenste flex eigenschappen die zijn geselecteerd uit de verschillende menu's. Deze tool kan niet alleen worden gebruikt om een visueel voorbeeld van de verschillende methoden te creëren, maar ook als een hulpmiddel om je eigen layouts te maken met je gewenste eigenschappen. Om een layout die je aanpast te gebruiken, kopieer je eenvoudig de uitvoercode en voeg je je gewenste elementen toe voor gebruik in je programma.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Item eigenschappen {#item-properties}

Itemeigenschappen hebben geen invloed op kindelementen binnen de Flex Layout, maar op de daadwerkelijke layout zelf. Dit is nuttig voor het stylen van een enkel Flex Layout-element dat een kind is van een groter Flex Layout-element, onafhankelijk van stijlen die op alle kinderen toepasbaar zijn.

### Volgorde {#order}

De `ItemOrder` eigenschap bepaalt hoe componenten binnen de Flex Layout worden weergegeven, en wanneer gebruikt op een Flex Layout zal het een item dat specifieke volgnummer aan die layout toekennen. Dit overschrijft de standaard "bronvolgorde" die in elk item is ingebouwd (de volgorde waarin een component aan zijn ouder wordt toegevoegd), en betekent dat het voor items met een hogere volgorde zal worden weergegeven en na items met een lagere volgorde.

Deze eigenschap accepteert een waardeloze gehele waarde die de relatieve volgorde van het flex-item binnen de container specificeert. Hoe lager de waarde, hoe eerder het item verschijnt in de volgorde. Bijvoorbeeld, een item met een volgorde waarde van 1 verschijnt voor een item met een volgorde waarde van 2.

:::caution
Het is belangrijk op te merken dat de volgorde eigenschap alleen de visuele volgorde van de items binnen de container beïnvloedt, niet hun daadwerkelijke positie in de DOM. Dit betekent dat schermlezers en andere hulpmiddelen voor toegankelijkheid de items nog steeds lezen in de volgorde waarin ze in de broncode verschijnen, niet in de visuele volgorde.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Zelf uitlijning {#self-alignment}

De zelfuitlijning van de Flex Layout verwijst naar hoe een enkele Flex Layout-object is uitgelijnd binnen zijn bovenliggende flex-container langs de kruisassen, die loodrecht staat op de hoofdas. De kruisassenuitlijning wordt geregeld door de `Alignment` eigenschap.

De align-self eigenschap specificeert de uitlijning van een enkel flex-item langs de kruisassen, wat de standaard uitlijning die is ingesteld door de `AlignContent` eigenschap in een Flex Layout-object overschrijft. Dit stelt je in staat om individuele Flex Layout-objecten anders uit te lijnen dan de andere in de container.

:::info
Zelfuitlijning gebruikt dezelfde waarden als inhoudsuitlijning
:::

Deze eigenschap is vooral nuttig wanneer je een specifiek item anders wilt uitlijnen dan de andere items in de container. Zie de onderstaande voorbeeld voor een voorbeeld van het uitlijnen van een enkel item:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex basis {#flex-basis}

`Item Basis` is een eigenschap die wordt gebruikt in combinatie met de richting van de Flex Layout om de initiële grootte van een flex-item in te stellen voordat de resterende ruimte wordt verdeeld.

De `Item Basis` eigenschap specificeert de standaardgrootte van een flex-item langs de hoofdas, die ofwel horizontaal (voor een rijrichting) of verticaal (voor een kolomrichting) is. Deze eigenschap stelt de breedte of hoogte van een flex-item in, afhankelijk van de waarde van de flex-direction eigenschap.

:::info
Standaard is de `Item Basis` eigenschap ingesteld op auto, wat betekent dat de grootte van het item wordt bepaald door de inhoud. Je kunt echter ook een specifieke grootte voor het item instellen met verschillende eenheden zoals pixels (px), ems (em), procenten (%) of een andere CSS-lengteeenheid.
:::

De volgende demo stelt je in staat om een of meer vakken te selecteren en de `Item Basis` voor de geselecteerde items te wijzigen.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex groeien / krimpen {#flex-grow--shrink}

`Item Grow` en `Item Shrink` zijn twee eigenschappen die in combinatie met elkaar en met de `Item Basis` eigenschap werken om te bepalen hoe flex-items groeien of krimpen om de beschikbare ruimte binnen een Flex Layout-object in te vullen.

De `Item Grow` eigenschap specificeert hoeveel het flex-item kan groeien ten opzichte van de andere items in de container. Het neemt een waardeloze waarde aan die een verhouding van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als een item een `Item Grow` waarde van 1 heeft en een ander item heeft een waarde van 2, dan zal het tweede item twee keer zoveel groeien als het eerste item.

De `Item Shrink` eigenschap specificeert daarentegen hoeveel het flex-item kan krimpen ten opzichte van de andere items in de container. Het neemt ook een waardeloze waarde aan die een verhouding van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als een item een `Item Shrink` waarde van 1 heeft en een ander item heeft een waarde van 2, dan zal het tweede item twee keer zoveel krimpen als het eerste item.

Wanneer een container meer ruimte heeft dan nodig is om zijn inhoud te huisvesten, zullen flex-items met een `Item Grow` waarde groter dan 0 uitbreiden om de beschikbare ruimte te vullen. De hoeveelheid ruimte die elk item krijgt, wordt bepaald door de verhouding van zijn `Item Grow` waarde tot de totale `Item Grow` waarde van alle items in de container.

Evenzo, wanneer een container niet genoeg ruimte heeft om zijn inhoud te huisvesten, zullen flex-items met een `Item Shrink` waarde groter dan 0 krimpen om in de beschikbare ruimte te passen. De hoeveelheid ruimte die elk item opgeeft, wordt bepaald door de verhouding van zijn `Item Shrink` waarde tot de totale `Item Shrink` waarde van alle items in de container.

## Voorbeeldformulier {#example-form}
Het onderstaande formulier demonstreert hoe `FlexLayout` invoervelden organiseert in een gestructureerde layout.

:::tip
Als je liever een kolomgebaseerde structuur hebt, bekijk dan de ColumnsLayout-versie van dit formulier in het [`ColumnsLayout`](../components/columns-layout) artikel om te zien hoe het zich verhoudt.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
