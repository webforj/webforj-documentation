---
title: Splitter
sidebar_position: 115
_i18n_hash: 340bcd9862027e6bfb967c0e6a9b5ec1
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

De `Splitter` component, ontworpen om inhoud binnen uw app te verdelen en te herformatteren, omvat twee verstelbare componenten: de master- en detailcomponenten. Een scheidingslijn scheidt deze componenten, waardoor gebruikers de maat van elke component dynamisch kunnen aanpassen aan hun voorkeuren.

<!-- INTRO_END -->

## Een splitter creëren {#creating-a-splitter}

Creëer een `Splitter` door twee componenten aan de constructor door te geven. De eerste wordt het masterpaneel en de tweede wordt het detailpaneel.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Min en max grootte {#min-and-max-size}

De `Splitter` component biedt methoden om minimum- en maksimumgroottes voor zijn panelen in te stellen, waardoor u het herformatteergedrag van de componenten binnen de `Splitter` kunt controleren. Wanneer gebruikers proberen panelen groter of kleiner te maken dan de gespecificeerde min- of maxgroottes, handhaaft de splittercomponent deze beperkingen, zodat panelen binnen de gedefinieerde grenzen blijven.

### Groottes instellen {#setting-sizes}

De methode `setMasterMinSize(String masterMinSize)` specificeert de minimumgrootte voor het masterpaneel van de splitter. Evenzo specificeert de methode `setMasterMaxSize(String masterMaxSize)` de maximumgrootte voor het masterpaneel.

U kunt groottes specificeren met behulp van geldige CSS-eenheden, zoals hieronder weergegeven:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Oriëntatie {#orientation}

U kunt de oriëntatie in de `Splitter` component configureren, waardoor u lay-outs kunt maken die zijn afgestemd op specifieke ontwerpeisen. Door de oriëntatie op te geven, organiseert de component panelen horizontaal of verticaal, wat veelzijdigheid biedt in het ontwerp van de lay-out.

Om de oriëntatie te configureren, gebruikt u de ondersteunde oriëntatie-enum om op te geven of de `Splitter` horizontaal of verticaal moet worden weergegeven:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Relatieve positie {#relative-position}

Om de initiële positie van de scheidingsbalk in de `Splitter` component in te stellen, gebruikt u `setPositionRelative`. Deze methode neemt een numerieke waarde van `0` tot `100` die het percentage van de gegeven ruimte in de `Splitter` weergeeft en toont de scheidingslijn op het opgegeven percentage van de totale breedte:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Nesten {#nesting}

Het nesting van Splitter-componenten maakt het mogelijk om complexe lay-outs te creëren met niveaus van verstelbare panelen. Het stelt de creatie van geavanceerde gebruikersinterfaces in staat met gedetailleerde controle over de rangschikking en het herformatteren van inhoud.

Om Splitter-componenten te nesten, maakt u nieuwe `Splitter` instanties aan en voegt u deze als kinderen toe aan bestaande `Splitter` componenten. Deze hiërarchische structuur maakt het mogelijk om multi-niveau lay-outs met flexibele herformattering te creëren. Het onderstaande programma demonstreert dit:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='300px'
/>

## Auto opslaan {#auto-save}

De `Splitter` component bevat een AutoSave optie, die de status van panelgroottes opslaat in de lokale opslag om de afmetingen consistent te houden tussen herladen.

Wanneer u de auto-opslagconfiguratie instelt, slaat de `Splitter` component automatisch de status van panelgroottes op in de lokale opslag van de webbrowser. Dit zorgt ervoor dat de groottes die gebruikers kiezen voor panelen behouden blijven over verschillende pagina herlaadbeurten of browsersessies, waardoor de noodzaak voor handmatige aanpassingen vermindert.

### Het schoonmaken van de status {#cleaning-the-state}

Om programmatic de `Splitter` terug te zetten naar de standaardinstellingen en -afmetingen, roept u de methode `cleanState()` aan om elk opgeslagen statusdata gerelateerd aan de `Splitter` component uit de lokale opslag van de webbrowser te verwijderen.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/resources/static/css/splitter-box.css',
]}
height='400px'
/>

In de voorgaande demo activeert elke Splitter-instantie de AutoSave functie door de methode `setAutosave` aan te roepen. Dit zorgt ervoor dat panelgroottes automatisch worden opgeslagen in de lokale opslag. Bij het herladen van de browser blijven de groottes van deze splitters hetzelfde.

Klik op de knop "Status wissen" die de methode `cleanState()` aanroept en verfrist het browservenster om de oorspronkelijke afmetingen weer te geven.

## Styling {#styling}

<TableBuilder name="Splitter" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Splitter` component, overweeg de volgende beste praktijken:

- **Aanpassen op basis van inhoud**: Wanneer u beslist over de oriëntatie en initiële groottes van panelen, overweeg de prioriteit van de inhoud. In een lay-out met een navigatiezijbalk en een hoofdinhoudsgedeelte, moet de zijbalk meestal smaller blijven met een vaste minimumgrootte voor duidelijke navigatie.

- **Strategisch nestelen**: Nestelen van splitters kan veelzijdige lay-outs creëren, maar kan de UI compliceren en de prestaties beïnvloeden. Plan uw geneste lay-outs om ervoor te zorgen dat ze intuïtief zijn en de gebruikerservaring verbeteren.

- **Vergeet de gebruikersvoorkeuren niet**: Gebruik de AutoSave functie om gebruikersaanpassingen tussen sessies te onthouden, wat de gebruikerservaring verbeterd. Bied een optie aan om gebruikers in staat te stellen terug te keren naar de standaardinstellingen.
