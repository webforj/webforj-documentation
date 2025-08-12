---
title: FlexLayout
sidebar_position: 45
_i18n_hash: bd3f6177603a98c20d4958a9c40dd49f
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

webforJ biedt ontwikkelaars een efficiënte en intuïtieve manier om hun verschillende applicaties en componenten te lay-outen - de Flex Layout. Deze toolset maakt het mogelijk om items zowel verticaal als horizontaal weer te geven.

## Flex layout eigenschappen {#flex-layout-properties}

De eigenschappen van de flex layout kunnen worden gegroepeerd in twee categorieën: eigenschappen die van toepassing zijn op de items binnen een lay-out, en eigenschappen die van toepassing zijn op de lay-out zelf. De flex layout, of het bovenliggende element, is een doos/container die een of meer componenten kan bevatten. Alles binnen een Flex Layout wordt een item of kindelement genoemd. De Flex Layout biedt robuuste uitlijnmogelijkheden, die kunnen worden bereikt met de hulp van container- of item eigenschappen.

:::tip
De lay-outcomponent van webforJ volgt het patroon van de [CSS flexbox lay-out](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Deze tools zijn echter ontworpen om volledig in Java te worden gebruikt en vereisen geen toepassing van CSS buiten deJava API-methoden die worden geboden.
:::

## Container eigenschappen {#container-properties}

Container eigenschappen zijn van toepassing op alle componenten binnen een component en niet op de lay-out zelf. Hiermee hebben ze geen invloed op de oriëntatie of plaatsing van het bovenliggende element - alleen op de kindcomponenten binnenin.

### Richting {#direction}

De Flex Layout voegt componenten naast elkaar toe volgens de richting die door de ontwikkelaar is gekozen - ofwel horizontaal of verticaal. Wanneer je de builder gebruikt, gebruik je de `horizontal()`, `horizontalReverse()`, `vertical()` of `verticalReverse()` methoden bij het aanroepen van de `create()` methode op een `FlexLayout` object om deze lay-out in te stellen terwijl het object wordt aangemaakt.

Alternatief kan je de `setDirection()` methode gebruiken. De horizontale opties zijn `FlexDirection.ROW` (van links naar rechts) of `FlexDirection.ROW_REVERSE` (van rechts naar links), en de verticale opties zijn `FlexDirection.COLUMN` (van boven naar beneden) of `FlexDirection.COLUMN_REVERSE` (van beneden naar boven). Dit gebeurt met het FlexLayout object, in tegenstelling tot de builder.

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionering {#positioning}

Componenten die horizontaal worden toegevoegd, kunnen zowel horizontaal als verticaal worden gepositioneerd. Gebruik de `justify()`, `align()` en `contentAlign()` methoden van de Flex Layout Builder om de positionering in te stellen bij het maken van een nieuwe Flex Layout.

Alternatief, op het eigenlijke FlexLayout object kun je de `setJustifyContent()` methode gebruiken om items horizontaal te positioneren, en de `setAlignment()` methode om verticale positionering in te stellen. Om het gebied rondom componenten langs de kruis-as (y-as voor horizontale lay-outs) te wijzigen, gebruik de `setAlignContent()` methode.

:::tip
De `setAlignment()` methode bepaalt hoe items als geheel langs de kruis-as binnen de container worden weergegeven en is effectief voor één-regelige lay-outs.

De `setAlignContent()` methode bepaalt de ruimte rondom de kruis-as en heeft alleen effect wanneer een lay-out meerdere regels heeft.  
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Wikkelen {#wrapping}

Om de Flex Layout-component verder aan te passen, kun je het gedrag van de flex layout specificeren wanneer componenten die worden toegevoegd niet meer binnen het display passen. Gebruik hiervoor in de builder de - `nowrap()` (standaard), `wrap()` en `wrapReverse()` methoden om het wikkelen te configureren.

Alternatief, als je lay-out al bestaat, gebruik de `setWrap()` methode om te dictateren hoe componenten zich zullen gedragen zodra ze niet meer op één regel passen.

### Ruimte {#spacing}

Om minimale ruimte tussen items toe te passen, kun je de gap-eigenschap instellen. Deze eigenschap past die ruimte alleen toe tussen items en niet op de uiterste randen.

Het gedrag van de gap-eigenschap kan worden gezien als een minimale afstand tussen - deze eigenschap heeft alleen effect als deze de grootste berekende ruimte tussen items is. Als de ruimte tussen items anders groter zou zijn door een andere berekende eigenschap, zoals `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, dan zal de gap-eigenschap worden genegeerd.

### Stroom {#flow}

Flex flow, een combinatie van zowel de richting- als de wikkelen-eigenschappen, kan worden ingesteld met de `setFlow()` methode op een Flex Layout object.

:::info
Om deze eigenschap in te stellen bij het maken van de lay-out, gebruik je de juiste richting- en wikkelmethode. Bijvoorbeeld, om een kolom-wikkel-stroom te creëren, gebruik je de `.vertical().wrap()` combinatie.
:::

### Container builder {#container-builder}

De volgende demo stelt je in staat om een container te bouwen met de gewenste flex-eigenschappen geselecteerd uit de verschillende menu's. Deze tool kan worden gebruikt om niet alleen een visueel voorbeeld van de verschillende methoden te maken, maar ook als een hulpmiddel om je eigen lay-outs met de gewenste eigenschappen te creëren. Om een lay-out te gebruiken die je hebt aangepast, kopieer je eenvoudig de uitvoercode en voeg je jouw gewenste elementen toe voor gebruik in je programma.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>

## Item eigenschappen {#item-properties}

Item eigenschappen hebben geen invloed op de kindelementen binnen de Flex Layout, maar op de daadwerkelijke Lay-out zelf. Dit is nuttig voor het stylen van een enkel Flex Layout-element dat een kind is van een groter Flex Layout-element onafhankelijk van stijlen die op alle kinderen van toepassing zijn.

### Volgorde {#order}

De `ItemOrder` eigenschap bepaalt hoe componenten binnen de Flex Layout worden weergegeven, en wanneer deze wordt gebruikt op een Flex Layout, wijst deze een item dat specifieke volgnummer van de lay-out toe. Dit overschrijft de standaard "bronnenvolgorde" die in elk item is ingebouwd (de volgorde waarin een component aan zijn bovenliggende element wordt toegevoegd), en betekent dat het vóór items met een hogere volgorde wordt weergegeven, en na items met een lagere volgorde.

Deze eigenschap accepteert een eenheidsloos geheel getal dat de relatieve volgorde van het flex-item binnen de container specificeert. Hoe lager de waarde, hoe eerder het item in de volgorde verschijnt. Bijvoorbeeld, een item met een volgorde waarde van 1 verschijnt vóór een item met een volgorde waarde van 2.

:::caution
Het is belangrijk op te merken dat de volgorde-eigenschap alleen de visuele volgorde van de items binnen de container beïnvloedt, niet hun daadwerkelijke positie in de DOM. Dit betekent dat schermlezers en andere hulpmiddelen de items nog steeds lezen in de volgorde waarin ze in de broncode verschijnen, niet in de visuele volgorde.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Zelf uitlijning {#self-alignment}

De zelf-uitlijning van de Flex Layout verwijst naar hoe een enkel Flex Layout-object is uitgelijnd binnen zijn bovenliggende flex-container langs de kruis-as, die loodrecht staat op de hoofd-as. De uitlijning van de kruis-as wordt geregeld door de `Alignment` eigenschap.

De align-self eigenschap specificeert de uitlijning van een enkel flex-item langs de kruis-as, en overschrijft de standaard uitlijning die is ingesteld door de `AlignContent` eigenschap in een Flex Layout object. Dit stelt je in staat om individuele Flex Layout-objecten anders uit te lijnen dan de anderen in de container.

:::info
Zelf-uitlijning gebruikt dezelfde waarden als content-uitlijning.
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

De `Item Basis` eigenschap specificeert de standaardgrootte van een flex-item langs de hoofd-as, die ofwel horizontaal (voor een rijrichting) of verticaal (voor een kolomrichting) is. Deze eigenschap stelt de breedte of hoogte van een flex-item in, afhankelijk van de waarde van de flex-direction eigenschap.

:::info
Standaard is de `Item Basis` eigenschap ingesteld op auto, wat betekent dat de grootte van het item wordt bepaald door zijn inhoud. Je kunt echter ook een specifieke grootte voor het item instellen met verschillende eenheden, zoals pixels (px), ems (em), percentages (%) of een andere CSS-lengte-eenheid.
:::

De volgende demo stelt je in staat om een of meer vakken te selecteren en de `Item Basis` voor de geselecteerde items te wijzigen.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex groeien / krimpen {#flex-grow--shrink}

`Item Grow` en `Item Shrink` zijn twee eigenschappen die samen met elkaar en met de `Item Basis` eigenschap werken om te bepalen hoe flex-items groeien of krimpen om de beschikbare ruimte binnen een Flex Layout-object in te vullen.

De `Item Grow` eigenschap specificeert hoeveel het flex-item kan groeien ten opzichte van de andere items in de container. Het neemt een eenheidsloze waarde die een verhouding vertegenwoordigt van de beschikbare ruimte die aan het item moet worden toegegekend. Bijvoorbeeld, als één item een `Item Grow` waarde van 1 heeft en een ander een waarde van 2 heeft, zal het tweede item twee keer zoveel groeien als het eerste item.

De `Item Shrink` eigenschap daarentegen specificeert hoeveel het flex-item kan krimpen ten opzichte van de andere items in de container. Het neemt ook een eenheidsloze waarde die een verhouding vertegenwoordigt van de beschikbare ruimte die aan het item moet worden toegegekend. Bijvoorbeeld, als één item een `Item Shrink` waarde van 1 heeft en een ander een waarde van 2 heeft, zal het tweede item twee keer zoveel krimpen als het eerste item.

Wanneer een container meer ruimte heeft dan nodig is om zijn inhoud te huisvesten, zullen flex-items met een `Item Grow` waarde groter dan 0 zich uitbreiden om de beschikbare ruimte in te vullen. De hoeveelheid ruimte die elk item krijgt, wordt bepaald door de verhouding van zijn `Item Grow` waarde tot de totale `Item Grow` waarde van alle items in de container.

Evenzo, wanneer een container niet genoeg ruimte heeft om zijn inhoud te huisvesten, zullen flex-items met een `Item Shrink` waarde groter dan 0 krimpen om in de beschikbare ruimte te passen. De hoeveelheid ruimte die elk item opgeeft, wordt bepaald door de verhouding van zijn `Item Shrink` waarde tot de totale `Item Shrink` waarde van alle items in de container.

## Voorbeeldformulier {#example-form}
Het formulier hieronder demonstreert hoe `FlexLayout` invoervelden organiseert in een gestructureerde lay-out.

:::tip
Als je de voorkeur geeft aan een kolom-gebaseerde structuur, kijk dan naar de ColumnsLayout-versie van dit formulier in het [`ColumnsLayout`](../components/columns-layout) artikel om te zien hoe het zich verhoudt.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
