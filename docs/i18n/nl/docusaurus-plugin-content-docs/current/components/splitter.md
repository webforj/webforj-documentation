---
title: Splitter
sidebar_position: 115
description: >-
  Divide a layout into resizable master and detail panels with the Splitter
  component, with min and max sizes and orientation control.
_i18n_hash: c700d01058105b5b752ecfa560224fb5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

De `Splitter` component, ontworpen om inhoud binnen uw app te splitsen en te verkleinen, omvat twee resizebare componenten: de master en de detailcomponenten. Een scheidingslijn scheidt deze componenten, waardoor gebruikers de grootte van elke component dynamisch kunnen aanpassen aan hun voorkeuren.

<!-- INTRO_END -->

## Een splitter maken {#creating-a-splitter}

Maak een `Splitter` door twee componenten aan de constructor door te geven. De eerste wordt het masterpaneel en de tweede wordt het detailpaneel.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Min en max grootte {#min-and-max-size}

De `Splitter` component biedt methoden om minimale en maximale groottes voor zijn panelen in te stellen, zodat u het resizegedrag van de componenten binnen de `Splitter` kunt controleren. Wanneer gebruikers proberen panelen verder te verkleinen of te vergroten dan de opgegeven min- of maxgroottes, handhaaft de splittercomponent deze beperkingen, zodat de panelen binnen de gedefinieerde grenzen blijven.

### Groottes instellen {#setting-sizes}

De methode `setMasterMinSize(String masterMinSize)` specificeert de minimale grootte voor het masterpaneel van de splitter. Evenzo specificeert de methode `setMasterMaxSize(String masterMaxSize)` de maximale grootte voor het masterpaneel.

U kunt groottes specificeren met behulp van elke geldige CSS-eenheid, zoals hieronder weergegeven:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Oriëntatie {#orientation}

U kunt de oriëntatie in de `Splitter` component configureren, zodat u lay-outs kunt creëren die zijn afgestemd op specifieke ontwerpeisen. Door de oriëntatie op te geven, rangschikt de component panelen horizontaal of verticaal, wat flexibiliteit biedt in lay-outontwerp.

Om de oriëntatie te configureren, gebruikt u de ondersteunde oriëntaties Enum om op te geven of de `Splitter` horizontaal of verticaal moet worden weergegeven:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Relatieve positie {#relative-position}

Om de beginpositie van de scheidingslijn in de `Splitter` component in te stellen, gebruikt u `setPositionRelative`. Deze methode neemt een numerieke waarde van `0` tot `100` die het percentage van de gegeven ruimte in de `Splitter` vertegenwoordigt, en toont de scheiding bij het opgegeven percentage van de totale breedte:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Nesting {#nesting}

Nesting van splitters stelt u in staat om complexe lay-outs te creëren met niveaus van resizebare panelen. Het maakt de creatie van verfijnde gebruikersinterfaces mogelijk met gedetailleerde controle over de rangschikking en het verkleinen van inhoud.

Om Splitter-componenten te nestelen, maakt u nieuwe `Splitter` instanties aan en voegt u ze als kinderen toe aan bestaande `Splitter` componenten. Deze hiërarchische structuur maakt de creatie van multi-niveau lay-outs met flexibele resize-mogelijkheden mogelijk. Het onderstaande programma demonstreert dit:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='300px'
/>

## Auto opslaan {#auto-save}

De `Splitter` component bevat een AutoSave optie, die de status van panelgroottes naar lokale opslag opslaat om de afmetingen consistent te houden tussen herladen.

Wanneer u de auto-opslagconfiguratie instelt, slaat de `Splitter` component automatisch de status van panelgroottes op in de lokale opslag van de webbrowser. Dit zorgt ervoor dat de groottes die gebruikers voor de panelen kiezen, behouden blijven tussen paginareloads of browsersessies, waardoor de noodzaak voor handmatige aanpassingen wordt verminderd.

### De status opschonen {#cleaning-the-state}

Om de `Splitter` programmatisch terug te zetten naar de standaardinstellingen en afmetingen, roept u de methode `cleanState()` aan om eventuele opgeslagen statusgegevens met betrekking tot de `Splitter` component uit de lokale opslag van de webbrowser te verwijderen.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter/splitter-box.css',
]}
height='400px'
/>

In de bovenstaande demo activeert elke Splitter-instantie de AutoSave-functie door de methode `setAutosave` aan te roepen. Dit zorgt ervoor dat panelgroottes automatisch worden opgeslagen in de lokale opslag. Dus wanneer de browser opnieuw wordt geladen, blijven de groottes van deze splitters hetzelfde.

Het klikken op de knop "Staat wissen" roept de methode `cleanState()` aan en vernieuwt het browservenster om de oorspronkelijke afmetingen weer te geven.

## Stylen {#styling}

<TableBuilder name="Splitter" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Splitter` component, overweeg de volgende beste praktijken:

- **Aanpassen op basis van inhoud**: Bij het bepalen van de oriëntatie en beginafmetingen van panelen, houdt rekening met de prioriteit van de inhoud. Bijvoorbeeld, in een lay-out met een navigatiezijbalk en een hoofdinhoudsgedeelte, moet de zijbalk doorgaans smaller blijven met een ingestelde minimumgrootte voor duidelijke navigatie.

- **Strategisch nestelen**: Het nestelen van splitters kan veelzijdige lay-outs creëren, maar kan de UI compliceren en de prestaties beïnvloeden. Plan uw geneste lay-outs om ervoor te zorgen dat ze intuïtief zijn en de gebruikerservaring verbeteren.

- **Vergeet gebruikersvoorkeuren niet**: Gebruik de AutoSave-functie om gebruikersaanpassingen over sessies heen te onthouden, waardoor de gebruikerservaring verbetert. Bied een optie om gebruikers in staat te stellen terug te keren naar de standaardinstellingen.
