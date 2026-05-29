---
title: FlexLayout
sidebar_position: 45
_i18n_hash: cf7ba76f1e13488c6fa3a419ba6ceaca
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

De `FlexLayout` component ordent kindcomponenten in een rij of kolom met behulp van het CSS Flexbox-model. Het biedt controle over uitlijning, ruimte, wrapping en hoe items groeien of krimpen om de beschikbare ruimte op te vullen.

<!-- INTRO_END -->

## `FlexLayout` eigenschappen {#flex-layout-properties}

`FlexLayout` eigenschappen kunnen in twee categorieën worden gegroepeerd: eigenschappen die van toepassing zijn op de items binnen een layout, en eigenschappen die van toepassing zijn op de layout zelf. De `FlexLayout`, of het bovenliggende element, is een doos/container die een of meer componenten kan bevatten. Alles binnen een `FlexLayout` wordt een item of kindelement genoemd. De `FlexLayout` biedt enkele uitlijningsmogelijkheden, die kunnen worden bereikt met behulp van container- of itemeigenschappen.

:::tip
De `FlexLayout` component volgt het patroon van [CSS's flexbox layout](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Echter, `FlexLayout` is ontworpen om volledig in Java te worden gebruikt, en vereist geen gebruik van CSS buiten de Java API-methoden die worden geleverd.
:::

## Container eigenschappen {#container-properties}

Container eigenschappen zijn van toepassing op alle componenten binnen een component en niet op de layout zelf. Ze beïnvloeden de oriëntatie of plaatsing van de ouder niet, alleen de kindcomponenten binnenin.

### Richting {#direction}

De `FlexLayout` voegt componenten naast elkaar toe volgens zijn richting, hetzij horizontaal of verticaal. Bij het gebruik van de builder, koppel de `horizontal()`, `horizontalReverse()`, `vertical()`, of `verticalReverse()` methoden met de `FlexLayout.create()` methode om de layout te configureren terwijl het object wordt gemaakt.

Om de richting in te stellen op een bestaand `FlexLayout` object, gebruik je de `setDirection()` methode. De horizontale opties zijn `FlexDirection.ROW` (van links naar rechts) of `FlexDirection.ROW_REVERSE` (van rechts naar links), en de verticale opties zijn `FlexDirection.COLUMN` (van boven naar beneden) of `FlexDirection.COLUMN_REVERSE` (van onder naar boven). 

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Positionering {#positioning}

Componenten die horizontaal worden toegevoegd, kunnen ook zowel horizontaal als verticaal worden gepositioneerd. Gebruik de `justify()`, `align()` en `contentAlign()` methoden van de `FlexLayout` Builder om de positionering te configureren wanneer je een nieuwe `FlexLayout` maakt.

Alternatief, op het werkelijke `FlexLayout` object kun je de `setJustifyContent()` methode gebruiken om items horizontaal te positioneren, en de `setAlignment()` methode om de verticale positionering te configureren. Om het gebied rond componenten langs de kruisas (y-as voor horizontale layouts) te wijzigen, gebruik je de `setAlignContent()` methode.

:::tip
De `setAlignment()` methode regelt hoe items als geheel langs de kruisas binnen de container worden weergegeven, en is effectief voor eenlijnige layouts.

De `setAlignContent()` methode regelt de ruimte rond de kruisas, en zal alleen effect hebben wanneer een layout meerdere lijnen heeft.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Wrapping {#wrapping}

Om de `FlexLayout` component verder aan te passen, kun je het gedrag specificeren wanneer componenten worden toegevoegd die niet meer in de weergave passen. Om dit te configureren met behulp van de builder, gebruik de `nowrap()` (standaard), `wrap()`, en `wrapReverse()` methoden om wrapping te configureren. Om dit op een bestaand `FlexLayout` object te configureren, gebruik de `setWrap()` methode.

### Spacing {#spacing}

Om minimale ruimte tussen items toe te passen, kun je de `gap` eigenschap instellen. Dit past die ruimte alleen tussen items toe, niet op de buitenste randen. 

Het gedrag van de gap-eigenschap kan worden gezien als een minimale afstand tussen, zodat het alleen effect heeft als het de grootste berekende ruimte tussen items is. Als de ruimte tussen items anders groter zou zijn door een andere berekende eigenschap, zoals door `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, dan wordt de gap-eigenschap genegeerd.

### Flow {#flow}

Flex flow, dat een combinatie is van zowel de richting als de wrap-eigenschappen, kan worden ingesteld met de `setFlow()` methode op een `FlexLayout` object. 

:::info
Om deze eigenschap te configureren bij het maken van de layout, gebruik de juiste richtings- en wrap-methoden. Bijvoorbeeld, om een kolom wrap flow te creëren, gebruik de `.vertical().wrap()` combinatie.
:::

### Container builder {#container-builder}

De volgende demo stelt je in staat om een container te bouwen met de gewenste flex-eigenschappen geselecteerd uit de verschillende menu's. Dit hulpmiddel kan niet alleen worden gebruikt om een visueel voorbeeld van de verschillende methoden te creëren, maar ook om je eigen layouts te maken met de gewenste eigenschappen. Om een layout te gebruiken die je hebt aangepast, kopieer je eenvoudig de uitvoercode en voeg je je gewenste elementen toe voor gebruik in je programma.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Item eigenschappen {#item-properties}

Item eigenschappen beïnvloeden geen kindelementen binnen de `FlexLayout`, maar beïnvloeden de werkelijke layout zelf. Dit is nuttig voor het stylen van een enkel `FlexLayout` element dat een kind is van een groter `FlexLayout` element onafhankelijk van stijlen die op alle kinderen van toepassing zijn.

### Volgorde {#order}

De `ItemOrder` eigenschap bepaalt de volgorde waarin componenten worden weergegeven binnen de `FlexLayout`, en wanneer deze wordt gebruikt op een `FlexLayout` zal deze een item dat layout's specifieke volgnummer toekennen. Dit overschrijft de standaard "bronvolgorde" die in elk item is ingebouwd (de volgorde waarin een component aan zijn ouder wordt toegevoegd), en betekent dat het vóór items met een hogere volgorde zal worden weergegeven, en na items met een lagere volgorde.

Deze eigenschap accepteert een waarde zonder eenheid die de relatieve volgorde van het flex-item binnen de container specificeert. Hoe lager de waarde, hoe eerder het item in de volgorde verschijnt. Bijvoorbeeld, een item met een volgwaarde van 1 verschijnt vóór een item met een volgwaarde van 2.

:::caution
Het is belangrijk op te merken dat de volgorde-eigenschap alleen de visuele volgorde van de items binnen de container beïnvloedt, niet hun werkelijke positie in de DOM. Dit betekent dat schermlezers en andere ondersteunende technologieën de items nog steeds in de volgorde zullen lezen waarin ze in de broncode verschijnen, niet in de visuele volgorde.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Zelfuitlijning {#self-alignment}

De zelfuitlijning van `FlexLayout` verwijst naar hoe een enkel `FlexLayout` object is uitgelijnd binnen zijn bovenliggende flexcontainer langs de kruisas, die loodrecht staat op de hoofd-as. De uitlijning langs de kruisas wordt gecontroleerd door de `Alignment` eigenschap.

De align-self eigenschap specificeert de uitlijning van een enkel flex-item langs de kruisas, waardoor de standaarduitlijning die is ingesteld door de `AlignContent` eigenschap in een `FlexLayout` object wordt overschreven. Dit stelt je in staat om individuele `FlexLayout` objecten anders uit te lijnen dan de andere in de container.

:::info
Zelfuitlijning gebruikt dezelfde waarden als inhoudsuitlijning.
:::

Deze eigenschap is vooral nuttig wanneer je een specifiek item anders wilt uitlijnen dan de andere items in de container. Zie de onderstaande sample voor een voorbeeld van het uitlijnen van een enkel item:

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/resources/static/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Flex basis {#flex-basis}

`Item Basis` is een eigenschap die in combinatie met de richting van `FlexLayout` wordt gebruikt om de initiële grootte van een flex-item in te stellen voordat de resterende ruimte wordt verdeeld.

De `Item Basis` eigenschap specificeert de standaardgrootte van een flex-item langs de hoofd-as, die hetzij horizontaal (voor een rijrichting) of verticaal (voor een kolomrichting) is. Deze eigenschap stelt de breedte of hoogte van een flex-item in, afhankelijk van de waarde van de flex-direction eigenschap.

:::info
Standaard is de `Item Basis` eigenschap ingesteld op `auto`, wat betekent dat de grootte van het item wordt bepaald door zijn inhoud. Echter, je kunt ook een specifieke grootte voor het item instellen met behulp van verschillende eenheden zoals pixels (px), ems (em), percentages (%), of een andere CSS-lengte-eenheid.
:::

De volgende demo stelt je in staat om een of meer vakken te selecteren en de `Item Basis` voor de geselecteerde items te wijzigen.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Flex groei en krimp {#flex-grow--shrink}

`Item Grow` en `Item Shrink` zijn twee eigenschappen die samenwerken met elkaar en de `Item Basis` eigenschap om te bepalen hoe flex-items groeien of krimpen om de beschikbare ruimte binnen een `FlexLayout` object op te vullen.

De `Item Grow` eigenschap specificeert hoeveel het flex-item kan groeien ten opzichte van de andere items in de container. Het neemt een waarde zonder eenheid die een proportie van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als één item een `Item Grow` waarde van 1 heeft en een ander een waarde van 2, dan zal het tweede item twee keer zoveel groeien als het eerste item.

De `Item Shrink` eigenschap, aan de andere kant, specificeert hoeveel het flex-item kan krimpen ten opzichte van de andere items in de container. Het neemt ook een waarde zonder eenheid die een proportie van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als één item een `Item Shrink` waarde van 1 heeft en een ander een waarde van 2, dan zal het tweede item twee keer zoveel krimpen als het eerste item.

Wanneer een container meer ruimte heeft dan nodig is om zijn inhoud te huisvesten, zullen flex-items met een `Item Grow` waarde groter dan 0 zich uitbreiden om de beschikbare ruimte op te vullen. De hoeveelheid ruimte die elk item krijgt, wordt bepaald door de verhouding van zijn `Item Grow` waarde tot de totale `Item Grow` waarde van alle items in de container.

Evenzo, wanneer een container niet voldoende ruimte heeft om zijn inhoud te huisvesten, zullen flex-items met een `Item Shrink` waarde groter dan 0 krimpen om in de beschikbare ruimte te passen. De hoeveelheid ruimte die elk item opgeeft, wordt bepaald door de verhouding van zijn `Item Shrink` waarde tot de totale `Item Shrink` waarde van alle items in de container.

## Voorbeeldformulier {#example-form}
Het onderstaande formulier demonstratie hoe `FlexLayout` invoervelden organiseert in een gestructureerde lay-out. 

:::tip
Als je de voorkeur geeft aan een kolomgebaseerde structuur, kijk dan naar de `ColumnsLayout` versie van dit formulier in het [`ColumnsLayout`](../components/columns-layout) artikel om te zien hoe het vergeleken wordt.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/resources/static/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
