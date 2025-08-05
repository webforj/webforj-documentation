---
title: Splitter
sidebar_position: 115
_i18n_hash: 0f8ea00bed7b930d5b7a8efe6bcd5446
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

De `Splitter` component, ontworpen om inhoud binnen uw app te verdelen en te schalen, encapsuleert twee resizable componenten: de master en de detail componenten. Een scheidingslijn scheidt deze componenten, waardoor gebruikers de grootte van elke component dynamisch kunnen aanpassen volgens hun voorkeuren.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Min en max grootte {#min-and-max-size}

De `Splitter` component biedt methoden om minimum en maximum groottes voor zijn panelen in te stellen, zodat u het schaalkarakter van de componenten binnen de `Splitter` kunt beheersen. Wanneer gebruikers proberen panelen buiten de opgegeven min- of maxgroottes te schalen, handhaaft de splittercomponent deze beperkingen, zodat panelen binnen de gedefinieerde grenzen blijven.

### Groottes instellen {#setting-sizes}

De `setMasterMinSize(String masterMinSize)` methode specificeert de minimale grootte voor het masterpaneel van de splitter. Evenzo specificeert de `setMasterMaxSize(String masterMaxSize)` methode de maximale grootte voor het masterpaneel.

U kunt groottes specificeren met behulp van geldige CSS-eenheden, zoals hieronder weergegeven:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Oriëntatie {#orientation}

U kunt de oriëntatie in de `Splitter` component configureren, zodat u lay-outs kunt creëren die zijn afgestemd op specifieke ontwerpeisen. Door de oriëntatie te specificeren, arrangeert de component panelen horizontaal of verticaal, wat veelzijdigheid biedt in het ontwerp van lay-outs.

Om de oriëntatie te configureren, gebruikt u de ondersteunde oriëntaties Enum om aan te geven of de `Splitter` horizontaal of verticaal moet worden weergegeven:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Relatieve positie {#relative-position}

Om de initiële positie van de scheidingslijn in de `Splitter` component in te stellen, gebruikt u `setPositionRelative`. Deze methode neemt een numerieke waarde van `0` tot `100`, die het percentage van de opgegeven ruimte in de `Splitter` vertegenwoordigt, en toont de scheidingslijn op het opgegeven percentage van de totale breedte:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Nesting {#nesting}

Nesting van Splitters stelt u in staat om complexe lay-outs te creëren met niveaus van resizable panelen. Het maakt de creatie van geavanceerde gebruikersinterfaces mogelijk met gedetailleerde controle over de arrangement en het schalen van inhoud.

Om Splitter componenten te nestelen, instantiate nieuwe `Splitter` instanties en voeg ze toe als kinderen aan bestaande `Splitter` componenten. Deze hiërarchische structuur maakt het mogelijk om multi-level lay-outs te creëren met flexibele schaalcapaciteiten. Het onderstaande programma demonstreert dit:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Auto opslaan {#auto-save}

De `Splitter` component bevat een AutoSave optie, die de status van panelgroottes opslaat in de lokale opslag om dimensies consistent te houden tussen herlaadbeurten.

Wanneer u de auto-opslagconfiguratie instelt, slaat de `Splitter` component automatisch de status van panelgroottes op in de lokale opslag van de webbrowser. Dit zorgt ervoor dat de groottes die gebruikers kiezen voor panelen behouden blijven bij het opnieuw laden van de pagina of sessies in de browser, wat de behoefte aan handmatige aanpassingen vermindert.

### Het statusbestand opschonen {#cleaning-the-state}

Om de `Splitter` programmatisch terug te zetten naar de standaardinstellingen en dimensies, roept u de `cleanState()` methode aan om eventuele opgeslagen statusgegevens met betrekking tot de `Splitter` component uit de lokale opslag van de webbrowser te verwijderen.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

In de voorgaande demo activeert elke Splitter-instantie de AutoSave-functie door de `setAutosave` methode aan te roepen. Dit zorgt ervoor dat panelgroottes automatisch worden opgeslagen in de lokale opslag. Bij het herladen van de browser blijven de groottes van deze splitters hetzelfde.

Door op de knop "Status wissen" te klikken, wordt de `cleanState()` methode aangeroepen en wordt het browservenster vernieuwd om de oorspronkelijke dimensies weer te geven.

## Styling {#styling}

<TableBuilder name="Splitter" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Splitter` component, overweeg de volgende beste praktijken:

- **Aanpassen op basis van inhoud**: Bij het beslissen over de oriëntatie en initiële groottes van panelen, overweeg de prioriteit van de inhoud. Bijvoorbeeld, in een lay-out met een navigatiezijbalk en een hoofdinhoudsgebied, moet de zijbalk doorgaans smaller blijven met een vaste min grootte voor duidelijke navigatie.

- **Strategische nesting**: Het nestelen van Splitters kan veelzijdige lay-outs creëren maar kan de UI compliceren en prestatie beïnvloeden. Plan uw genestelde lay-outs om ervoor te zorgen dat ze intuïtief zijn en de gebruikerservaring verbeteren.

- **Vergeet gebruikersvoorkeuren niet**: Gebruik de AutoSave functie om gebruikersaanpassingen tussen sessies te onthouden, wat de gebruikerservaring verbeterd. Bied een optie om gebruikers in staat te stellen naar de standaardinstellingen terug te keren.
