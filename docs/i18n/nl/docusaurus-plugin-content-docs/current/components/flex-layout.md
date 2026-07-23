---
title: FlexLayout
sidebar_position: 45
description: >-
  Arrange children in rows or columns with the FlexLayout component, controlling
  direction, justification, alignment, wrapping, and growth.
_i18n_hash: cd16392e244062d863d403e50cc56ddd
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

De `FlexLayout` component ordent child-componenten in een rij of kolom met behulp van het CSS Flexbox-model. Het geeft je controle over uitlijning, ruimte, wikkelen en hoe items groeien of krimpen om beschikbare ruimte in te vullen.

<!-- INTRO_END -->

## `FlexLayout` eigenschappen {#flex-layout-properties}

`FlexLayout` eigenschappen kunnen worden gegroepeerd in twee categorieën: eigenschappen die van toepassing zijn op de items binnen een layout, en eigenschappen die van toepassing zijn op de layout zelf. De `FlexLayout`, of het ouder-element, is een doos/container die een of meer componenten kan bevatten. Alles binnen een `FlexLayout` wordt een item of child-element genoemd. De `FlexLayout` biedt enkele uitlijnmogelijkheden, die kunnen worden bereikt met behulp van container- of item-eigenschappen.

:::tip
De `FlexLayout` component volgt het patroon van [de flexbox layout van CSS](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Echter, `FlexLayout` is gemaakt om volledig in Java te worden gebruikt, en vereist geen gebruik van CSS buiten de aangeboden Java API-methoden.
:::

## Container eigenschappen {#container-properties}

Container eigenschappen zijn van toepassing op alle componenten binnen een component en niet op de layout zelf. Ze beïnvloeden de oriëntatie of plaatsing van de ouder, alleen de child-componenten binnenin.

### Richting {#direction}

De `FlexLayout` voegt componenten naast elkaar toe volgens zijn richting, zij het horizontaal of verticaal. Wanneer je de builder gebruikt, keten dan de `horizontal()`, `horizontalReverse()`, `vertical()`, of `verticalReverse()` methoden met de `FlexLayout.create()` methode om de layout in te stellen terwijl het object wordt aangemaakt.

Om de richting op een bestaand `FlexLayout` object te zetten, gebruik de `setDirection()` methode. De horizontale opties zijn `FlexDirection.ROW` (van links naar rechts) of `FlexDirection.ROW_REVERSE` (van rechts naar links), en de verticale opties zijn `FlexDirection.COLUMN` (van boven naar beneden) of `FlexDirection.COLUMN_REVERSE` (van onder naar boven).

<ComponentDemo
path='/webforj/flexdirection'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='275px'
/>

### Positionering {#positioning}

Componenten die horizontaal zijn toegevoegd kunnen ook zowel horizontaal als verticaal worden gepositioneerd. Gebruik de `justify()`, `align()` en `contentAlign()` methoden van de `FlexLayout` Builder om de positionering in te stellen bij het creëren van een nieuwe `FlexLayout`.

Alternatief, op het daadwerkelijke `FlexLayout` object kun je de `setJustifyContent()` methode gebruiken om items horizontaal te positioneren, en de `setAlignment()` methode om de verticale positionering in te stellen. Om het gebied rondom componenten langs de kruis-as (y-as voor horizontale layouts) te wijzigen, gebruik de `setAlignContent()` methode.

:::tip
De `setAlignment()` methode controleert hoe items als geheel worden weergegeven langs de kruis-as binnen de container, en is effectief voor enkel-regelige layouts.

De `setAlignContent()` methode controleert de ruimte rondom de kruis-as, en zal alleen effect hebben wanneer een layout meerdere regels heeft.
:::

<ComponentDemo
path='/webforj/flexpositioning'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='375px'
/>

### Wikkelen {#wrapping}

Om de `FlexLayout` component verder aan te passen, kun je het gedrag specificeren wanneer componenten worden toegevoegd die niet meer binnen de weergave passen. Om dit in te stellen met behulp van de builder, gebruik de `nowrap()` (default), `wrap()`, en `wrapReverse()` methoden om wikkelen in te stellen. Om dit op een bestaand `FlexLayout` object in te stellen, gebruik de `setWrap()` methode.

### Ruimte {#spacing}

Om minimale ruimte tussen items toe te passen, kun je de `gap` eigenschap instellen. Het past die ruimte alleen tussen items toe, niet op de buitenste randen.

Het gedrag van de gap-eigenschap kan worden gezien als een minimale afstand tussen, dus het zal alleen effect hebben als het de grootste berekende ruimte tussen items is. Als de ruimte tussen items anders groter zou zijn vanwege een andere berekende eigenschap, zoals door `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, dan zal de gap-eigenschap worden genegeerd.

### Stroom {#flow}

Flex-stroom, dat is een combinatie van zowel de richting als de wikkelen eigenschappen, kan worden ingesteld met de `setFlow()` methode op een `FlexLayout` object.

:::info
Om deze eigenschap in te stellen bij het creëren van de layout, gebruik de juiste richting- en wikkelen methoden. Bijvoorbeeld, om een kolom-wikkel-stroom te creëren, gebruik de `.vertical().wrap()` combinatie.
:::

### Container builder {#container-builder}

De volgende demo stelt je in staat om een container te bouwen met de gewenste flex-eigenschappen geselecteerd uit de verschillende menu’s. Dit hulpmiddel kan niet alleen worden gebruikt om een visueel voorbeeld te creëren van de verschillende methoden, maar ook om je eigen layouts te creëren met jouw gewenste eigenschappen. Om een layout die je hebt aangepast te gebruiken, kopieer je simpelweg de uitvoercode en voeg je de gewenste elementen toe voor gebruik in je programma.

<ComponentDemo
path='/webforj/flexcontainerbuilder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='600px'
/>

<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Item eigenschappen {#item-properties}

Item eigenschappen zullen geen invloed hebben op child-elementen binnen de `FlexLayout`, maar beïnvloeden de daadwerkelijke layout zelf. Dit is nuttig voor het stylen van een enkele `FlexLayout` element dat een kind is van een grotere `FlexLayout` element onafhankelijk van stijlen die van toepassing zijn op alle kinderen.

### Volgorde {#order}

De `ItemOrder` eigenschap bepaalt de volgorde waarin componenten worden weergegeven binnen de `FlexLayout`, en wanneer gebruikt op een `FlexLayout` zal het een item dat specifieke volgnummer van die layout toewijzen. Dit overschrijft de standaard "bronvolgorde" die in elk item is ingebouwd (de volgorde waarin een component aan zijn ouder is toegevoegd), en betekent dat het vóór items met een hogere volgorde zal worden weergegeven, en achter items met een lagere volgorde.

Deze eigenschap accepteert een unitless integer waarde die de relatieve volgorde van het flex-item binnen de container specificeert. Hoe lager de waarde, hoe eerder het item verschijnt in de volgorde. Bijvoorbeeld, een item met een volgorde waarde van 1 zal vóór een item met een volgorde waarde van 2 verschijnen.

:::caution
Het is belangrijk op te merken dat de volgorde-eigenschap alleen de visuele volgorde van de items binnen de container beïnvloedt, niet hun daadwerkelijke positie in de DOM. Dit betekent dat schermlezers en andere ondersteunende technologieën de items nog steeds zullen lezen in de volgorde waarin ze in de broncode verschijnen, niet in de visuele volgorde.
:::

<ComponentDemo
path='/webforj/flexorder'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='320px'
/>

### Zelf uitlijning {#self-alignment}

De zelf-uitlijning van `FlexLayout` verwijst naar hoe een enkel `FlexLayout` object is uitgelijnd binnen zijn ouder flex-container langs de kruis-as, die loodrecht is op de hoofd-as. De uitlijning van de kruis-as wordt gecontroleerd door de `Alignment` eigenschap.

De align-self eigenschap specificeert de uitlijning van een enkel flex-item langs de kruis-as, die de standaard-uitlijning die is ingesteld door de `AlignContent` eigenschap in een `FlexLayout` object, overschrijft. Dit stelt je in staat om individuele `FlexLayout` objecten anders uit te lijnen dan de anderen in de container.

:::info
Zelf uitlijning gebruikt dezelfde waarden als content uitlijning.
:::

Deze eigenschap is vooral nuttig wanneer je een specifiek item anders moet uitlijnen dan de andere items in de container. Zie het onderstaande voorbeeld voor een voorbeeld van het uitlijnen van een enkel item:

<ComponentDemo
path='/webforj/flexselfalign'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java',
  'src/main/frontend/css/flexlayout/container/flexContainerBuilder.css',
]}
height='350px'
/>

### Flex basis {#flex-basis}

`Item Basis` is een eigenschap die in combinatie met de richting van `FlexLayout` wordt gebruikt om de initiële grootte van een flex-item in te stellen voordat de resterende ruimte wordt verdeeld.

De `Item Basis` eigenschap specificeert de standaardgrootte van een flex-item langs de hoofd-as, die ofwel horizontaal (voor een Rij-richting) of verticaal (voor een Kolom-richting) is. Deze eigenschap stelt de breedte of hoogte van een flex-item in, afhankelijk van de waarde van de flex-direction eigenschap.

:::info
Standaard is de `Item Basis` eigenschap ingesteld op `auto`, wat betekent dat de grootte van het item wordt bepaald door de inhoud. Echter, je kunt ook een specifieke grootte voor het item instellen met verschillende eenheden zoals pixels (px), ems (em), percentages (%), of een andere CSS-lengte eenheid.
:::

De volgende demo stelt je in staat om een of meer vakken te selecteren en de `Item Basis` voor de geselecteerde items te wijzigen.

<ComponentDemo
path='/webforj/flexbasis'
files={['src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java']}
height='300px'
/>

### Flex groeien en krimpen {#flex-grow--shrink}

`Item Grow` en `Item Shrink` zijn twee eigenschappen die in combinatie met elkaar en de `Item Basis` eigenschap werken om te bepalen hoe flex-items groeien of krimpen om de beschikbare ruimte binnen een `FlexLayout` object te vullen.

De `Item Grow` eigenschap specificeert hoe veel het flex-item kan groeien ten opzichte van de andere items in de container. Het neemt een unitless waarde die een verhouding van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als een item een `Item Grow` waarde van 1 heeft en een ander een waarde van 2, zal het tweede item twee keer zoveel groeien als het eerste item.

De `Item Shrink` eigenschap daarentegen specificeert hoe veel het flex-item kan krimpen ten opzichte van de andere items in de container. Het neemt ook een unitless waarde die een verhouding van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als een item een `Item Shrink` waarde van 1 heeft en een ander een waarde van 2, zal het tweede item twee keer zoveel krimpen als het eerste item.

Wanneer een container meer ruimte heeft dan nodig is om zijn inhoud te huisvesten, zullen flex-items met een `Item Grow` waarde groter dan 0 zich uitbreiden om de beschikbare ruimte te vullen. De hoeveelheid ruimte die elk item krijgt, wordt bepaald door de verhouding van zijn `Item Grow` waarde tot de totale `Item Grow` waarde van alle items in de container.

Op dezelfde manier, wanneer een container niet genoeg ruimte heeft om zijn inhoud te huisvesten, zullen flex-items met een `Item Shrink` waarde groter dan 0 krimpen om in de beschikbare ruimte te passen. De hoeveelheid ruimte die elk item opgeeft, wordt bepaald door de verhouding van zijn `Item Shrink` waarde tot de totale `Item Shrink` waarde van alle items in de container.

## Voorbeeldformulier {#example-form}
Het formulier hieronder toont aan hoe `FlexLayout` invoervelden organiseert in een gestructureerde layout.

:::tip
Als je de voorkeur geeft aan een kolom-gebaseerde structuur, bekijk dan de `ColumnsLayout` versie van dit formulier in het [`ColumnsLayout`](../components/columns-layout) artikel om te zien hoe het zich verhoudt.
:::

<ComponentDemo
path='/webforj/flexlayout'
files={[
  'src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java',
  'src/main/frontend/css/flexlayout/flexLayout.css',
]}
height='620px'
/>
