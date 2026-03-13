---
title: FlexLayout
sidebar_position: 45
_i18n_hash: ddb7d5ef1e583af6e3a7072d91329c7b
---
<JavadocLink type="flexlayout" location="com/webforj/component/layout/flexlayout/FlexLayout" top='true'/>
<DocChip chip='since' label='24.00' />

De `FlexLayout` component ordent kindcomponenten in een rij of kolom met behulp van het CSS Flexbox-model. Het biedt je controle over uitlijning, ruimte, wrapping en hoe items groeien of krimpen om beschikbare ruimte in te nemen.

<!-- INTRO_END -->

## `FlexLayout` eigenschappen {#flex-layout-properties}

`FlexLayout` eigenschappen kunnen worden onderverdeeld in twee categorieën: eigenschappen die van toepassing zijn op de items binnen een layout, en eigenschappen die van toepassing zijn op de layout zelf. De `FlexLayout`, of het bovenliggende element, is een doos/container die één of meer componenten kan bevatten. Alles binnen een `FlexLayout` wordt een item of kinder-element genoemd. De `FlexLayout` biedt enkele uitlijnmogelijkheden, die kunnen worden bereikt met behulp van container- of itemeigenschappen.

:::tip
De `FlexLayout` component volgt het patroon van [CSS's flexbox indeling](https://css-tricks.com/snippets/css/a-guide-to-flexbox/). Echter, `FlexLayout` is gemaakt om volledig in Java te worden gebruikt en vereist geen gebruik van CSS buiten de Java API-methoden die worden geleverd.
:::

## Container eigenschappen {#container-properties}

Container eigenschappen zijn van toepassing op alle componenten binnen een component en niet op de layout zelf. Ze beïnvloeden de oriëntatie of plaatsing van het bovenliggende element niet, alleen de kindercomponenten binnenin.

### Richting {#direction}

De `FlexLayout` voegt componenten naast elkaar toe volgens zijn richting, hetzij horizontaal of verticaal. Wanneer je de builder gebruikt, keten je de methoden `horizontal()`, `horizontalReverse()`, `vertical()` of `verticalReverse()` met de methode `FlexLayout.create()` om de layout in te stellen terwijl het object wordt aangemaakt.

Om de richting in te stellen op een bestaand `FlexLayout` object, gebruik je de methode `setDirection()`. De horizontale opties zijn `FlexDirection.ROW` (van links naar rechts) of `FlexDirection.ROW_REVERSE` (van rechts naar links), en de verticale opties zijn `FlexDirection.COLUMN` (van boven naar beneden) of `FlexDirection.COLUMN_REVERSE` (van beneden naar boven). 

<ComponentDemo 
path='/webforj/flexdirection?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexDirectionView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="275px"
/>

### Positionering {#positioning}

Componenten die horizontaal worden toegevoegd, kunnen ook zowel horizontaal als verticaal worden gepositioneerd. Gebruik de methoden `justify()`, `align()` en `contentAlign()` van de `FlexLayout` Builder om de positionering in te stellen bij het creëren van een nieuwe `FlexLayout`.

Alternatief kun je op het daadwerkelijke `FlexLayout` object de methode `setJustifyContent()` gebruiken om items horizontaal te positioneren, en de methode `setAlignment()` om verticale positionering in te stellen. Om het gebied rond componenten langs de kruis-as (y-as voor horizontale layouts) te wijzigen, gebruik je de methode `setAlignContent()`.

:::tip
De methode `setAlignment()` bepaalt hoe items als geheel worden weergegeven langs de kruis-as binnen de container en is effectief voor enkel-regelige layouts.

De methode `setAlignContent()` bepaalt de ruimte rond de kruis-as en heeft alleen effect wanneer een layout meerdere regels heeft.
:::

<ComponentDemo 
path='/webforj/flexpositioning?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexPositioningView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="375px"
/>

### Wrapping {#wrapping}

Om de `FlexLayout` component verder aan te passen, kun je zijn gedrag specificeren wanneer componenten worden toegevoegd die niet meer binnen de weergave passen. Om dit in te stellen met de builder, gebruik je de methoden `nowrap()` (standaard), `wrap()` en `wrapReverse()` om wrapping te configureren. Om dit op een bestaand `FlexLayout` object in te stellen, gebruik je de methode `setWrap()`.

### Ruimte {#spacing}

Om minimale ruimte tussen items toe te passen, kun je de `gap` eigenschap instellen. Het past die ruimte alleen tussen items toe, niet op de buitenste randen.

Het gedrag van de gap eigenschap kan worden gezien als een minimale afstand tussen items, dus het zal alleen effect hebben als het de grootste berekende ruimte tussen items is. Als de ruimte tussen items anders groter zou zijn door een andere berekende eigenschap, zoals door `setAlignContent(FlexContentAlignment.SPACE_BETWEEN)`, dan zal de gap eigenschap worden genegeerd.

### Stroom {#flow}

Flex stroom, wat een combinatie is van zowel de richting als de wrap-eigenschappen, kan worden ingesteld met de methode `setFlow()` op een `FlexLayout` object. 

:::info
Om deze eigenschap in te stellen bij het creëren van de layout, gebruik je de juiste richtings- en wrap-methoden. Bijvoorbeeld, om een kolom-wrapping-stroom te creëren, gebruik je de combinatie `.vertical().wrap()`.
:::

### Container builder {#container-builder}

De volgende demo stelt je in staat om een container te bouwen met de gewenste flex-eigenschappen die zijn geselecteerd uit de verschillende menu's. Deze tool kan niet alleen worden gebruikt om een visueel voorbeeld van de verschillende methoden te creëren, maar ook om je eigen layouts te maken met jouw gewenste eigenschappen. Om een layout te gebruiken die je customization hebt gegeven, kopieer eenvoudig de uitvoercode en voeg je gewenste elementen toe voor gebruik in je programma.

<ComponentDemo 
path='/webforj/flexcontainerbuilder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/container/FlexContainerBuilderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="600px"
/>


<!-- BIG CODE SNIPPET SHOWING CONTAINER -->
## Item eigenschappen {#item-properties}

Item eigenschappen hebben geen invloed op kinder-elementen binnen de `FlexLayout`, maar beïnvloeden de daadwerkelijke layout zelf. Dit is nuttig voor het stylen van een enkele `FlexLayout` element dat een kind is van een grotere `FlexLayout` element onafhankelijk van stijlen die op alle kinderen worden toegepast.

### Order {#order}

De `ItemOrder` eigenschap bepaalt de volgorde waarin componenten binnen de `FlexLayout` worden weergegeven, en wanneer gebruikt op een `FlexLayout` zal een item dat specifieke volgorde nummer aan de layout toekennen. Dit vervangt de standaard "bronvolgorde" die in elk item is ingebouwd (de volgorde waarin een component aan zijn ouder is toegevoegd) en betekent dat het vóór items met een hogere volgorde zal worden weergegeven en na items met een lagere volgorde.

Deze eigenschap accepteert een waarde zonder eenheidsgetal die de relatieve volgorde van het flex-item binnen de container specificeert. Hoe lager de waarde, hoe eerder het item verschijnt in de volgorde. Bijvoorbeeld, een item met een orderwaarde van 1 zal verschijnen vóór een item met een orderwaarde van 2.

:::caution
Het is belangrijk op te merken dat de volgorde-eigenschap alleen de visuele volgorde van de items binnen de container beïnvloedt, niet hun daadwerkelijke positie in de DOM. Dit betekent dat screenreaders en andere ondersteunende technologieën de items nog steeds in de volgorde zullen lezen waarin ze in de broncode verschijnen, niet in de visuele volgorde.
:::

<ComponentDemo 
path='/webforj/flexorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexOrderView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="320px"
/>

### Zelf uitlijning {#self-alignment}

De zelf-uitlijning van `FlexLayout` verwijst naar hoe een enkel `FlexLayout` object is uitgelijnd binnen zijn bovenliggende flex-container langs de kruis-as, die loodrecht staat op de hoofd-as. De kruis-as uitlijning wordt gecontroleerd door de `Alignment` eigenschap.

De align-self eigenschap specificeert de uitlijning van een enkel flex-item langs de kruis-as, waardoor de standaard uitlijning die is ingesteld door de `AlignContent` eigenschap in een `FlexLayout` object wordt overschreven. Dit stelt je in staat om individuele `FlexLayout` objecten anders uit te lijnen dan de anderen in de container.

:::info
Zelf-uitlijning gebruikt dezelfde waarden als content-uitlijning.
:::

Deze eigenschap is bijzonder nuttig wanneer je een specifiek item anders wilt uitlijnen dan de andere items in de container. Zie het onderstaande voorbeeld voor een voorbeeld van het uitlijnen van een enkel item:

<ComponentDemo 
path='/webforj/flexselfalign?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/item/FlexSelfAlignView.java'
cssURL='/css/flexlayout/container/flexContainerBuilder.css'
height="350px"
/>

### Flex basis {#flex-basis}

`Item Basis` is een eigenschap die wordt gebruikt in combinatie met de richting van `FlexLayout` om de initiële grootte van een flex-item in te stellen voordat een resterende ruimte wordt verdeeld.

De `Item Basis` eigenschap specificeert de standaardgrootte van een flex-item langs de hoofd-as, die horizontaal (voor een rijrichting) of verticaal (voor een kolomrichting) is. Deze eigenschap stelt de breedte of hoogte van een flex-item in afhankelijk van de waarde van de flex-direction eigenschap.

:::info
Standaard is de `Item Basis` eigenschap ingesteld op `auto`, wat betekent dat de grootte van het item wordt bepaald door de inhoud ervan. Je kunt echter ook een specifieke maat voor het item instellen met verschillende eenheden zoals pixels (px), ems (em), percentages (%) of enige andere CSS-lengte-eenheid.
:::

De volgende demo stelt je in staat om een of meer vakken te selecteren en de `Item Basis` voor de geselecteerde items te wijzigen.

<ComponentDemo 
path='/webforj/flexbasis?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexBasisView.java'
height="300px"
/>

### Flex groeien en krimpen {#flex-grow--shrink}

`Item Grow` en `Item Shrink` zijn twee eigenschappen die in combinatie met elkaar en de `Item Basis` eigenschap werken om te bepalen hoe flex-items groeien of krimpen om de beschikbare ruimte binnen een `FlexLayout` object in te vullen.

De `Item Grow` eigenschap specificeert hoeveel het flex-item kan groeien ten opzichte van de andere items in de container. Het neemt een waarde zonder eenheid aan die een proportie van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als een item een `Item Grow` waarde van 1 heeft en een ander een waarde van 2, zal het tweede item tweemaal zoveel groeien als het eerste item.

De `Item Shrink` eigenschap, aan de andere kant, specificeert hoeveel het flex-item kan krimpen ten opzichte van de andere items in de container. Het neemt ook een waarde zonder eenheid aan die een proportie van de beschikbare ruimte vertegenwoordigt die aan het item moet worden toegewezen. Bijvoorbeeld, als een item een `Item Shrink` waarde van 1 heeft en een ander een waarde van 2, zal het tweede item tweemaal zoveel krimpen als het eerste item.

Wanneer een container meer ruimte heeft dan nodig is om zijn inhoud te huisvesten, zullen flex-items met een `Item Grow` waarde groter dan 0 zich uitbreiden om de beschikbare ruimte in te vullen. De hoeveelheid ruimte die elk item krijgt, wordt bepaald door de verhouding van zijn `Item Grow` waarde tot de totale `Item Grow` waarde van alle items in de container.

Evenzo, wanneer een container niet genoeg ruimte heeft om zijn inhoud te huisvesten, zullen flex-items met een `Item Shrink` waarde groter dan 0 krimpen om in de beschikbare ruimte te passen. De hoeveelheid ruimte die elk item opgeeft, wordt bepaald door de verhouding van zijn `Item Shrink` waarde tot de totale `Item Shrink` waarde van alle items in de container.

## Voorbeeld formulier {#example-form}
Het onderstaande formulier demonstreert hoe `FlexLayout` invoervelden organiseert in een gestructureerde layout.

:::tip
Als je een kolom-gebaseerde structuur verkiest, kijk dan naar de `ColumnsLayout` versie van dit formulier in het [`ColumnsLayout`](../components/columns-layout) artikel om te zien hoe het zich verhoudt.
:::

<ComponentDemo 
path='/webforj/flexlayout?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/flexlayout/FlexLayoutView.java'
cssURL='/css/flexlayout/flexLayout.css'
height="620px"
/>
