---
title: Splitter
sidebar_position: 115
description: >-
  Divide a layout into resizable master and detail panels with the Splitter
  component, with min and max sizes and orientation control.
_i18n_hash: 0683e5c7589bbf3fa42b8ea137c4f809
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

De `Splitter` component, ontworpen om content binnen je app op te delen en te schalen, omvat twee verwijderbare componenten: de master- en detailcomponenten. Een scheidingslijn scheidt deze componenten, zodat gebruikers de grootte van elke component dynamisch kunnen aanpassen aan hun voorkeuren.

<!-- INTRO_END -->

## Een splitter maken {#creating-a-splitter}

Maak een `Splitter` door twee componenten aan de constructor door te geven. De eerste wordt het masterpaneel en de tweede wordt het detailpaneel.

<ComponentDemo
path='/webforj/splitterbasic'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Min en max grootte {#min-and-max-size}

De `Splitter` component biedt methoden om minimum- en maximumgroottes voor zijn panelen in te stellen, zodat je het schalingsgedrag van de componenten binnen de `Splitter` kunt controleren. Wanneer gebruikers proberen panelen groter of kleiner te maken dan de aangegeven min- of maxgroottes, handhaaft de splittercomponent deze beperkingen, zodat panelen binnen de gedefinieerde grenzen blijven.

### Groottes instellen {#setting-sizes}

De methode `setMasterMinSize(String masterMinSize)` specificeert de minimumgrootte voor het masterpaneel van de splitter. Evenzo specificeert de methode `setMasterMaxSize(String masterMaxSize)` de maximumgrootte voor het masterpaneel.

Je kunt groottes opgeven met behulp van alle geldige CSS-eenheden, zoals hieronder weergegeven:

<ComponentDemo
path='/webforj/splitterminmax'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Oriëntatie {#orientation}

Je kunt de oriëntatie in de `Splitter` component configureren, zodat je indelingen kunt maken die zijn aangepast aan specifieke ontwerpeisen. Door de oriëntatie op te geven, regelt de component panelen horizontaal of verticaal, waardoor veelzijdigheid in het ontwerp van indelingen mogelijk wordt.

Om de oriëntatie te configureren, gebruik je de ondersteunde oriëntaties Enum om op te geven of de `Splitter` horizontaal of verticaal moet worden weergegeven:

<ComponentDemo
path='/webforj/splitterorientation'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Relatieve positie {#relative-position}

Om de initiële positie van de scheidingslijn in de `Splitter` component in te stellen, gebruik je `setPositionRelative`. Deze methode neemt een numerieke waarde van `0` tot `100`, die het percentage van de gegeven ruimte in de `Splitter` weergeeft, en toont de scheidingslijn op het opgegeven percentage van de totale breedte:

<ComponentDemo
path='/webforj/splitterposition'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Nesten {#nesting}

Het nesteren van splitters stelt je in staat om complexe indelingen te creëren met niveaus van verwijderbare panelen. Het maakt de creatie van verfijnde gebruikersinterfaces mogelijk met gedetailleerde controle over de indeling en schaling van content.

Om Splitter componenten te nesten, instantieer je nieuwe `Splitter` instanties en voeg je ze als kinderen toe aan bestaande `Splitter` componenten. Deze hiërarchische structuur maakt de creatie van multi-level indelingen met flexibele schaling mogelijk. Het programma hieronder demonstreert dit:

<ComponentDemo
path='/webforj/splitternested'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='300px'
/>

## Auto opslaan {#auto-save}

De `Splitter` component bevat een AutoSave optie, die de staat van paneelgroottes opslaat in de lokale opslag om de afmetingen consistent te houden tussen herlaadbeurten.

Wanneer je de auto-save configuratie instelt, slaat de `Splitter` component automatisch de staat van paneelgroottes op in de lokale opslag van de webbrowser. Dit zorgt ervoor dat de groottes die gebruikers voor panelen kiezen, blijven bestaan tussen pagina-herlaadbeurten of browsersessies, waardoor de behoefte aan handmatige aanpassingen wordt verminderd.

### De staat schoonmaken {#cleaning-the-state}

Om de `Splitter` programmatisch terug te zetten naar de standaardinstellingen en afmetingen, roep je de methode `cleanState()` aan om eventuele opgeslagen staatgegevens met betrekking tot de `Splitter` component uit de lokale opslag van de webbrowser te verwijderen.

<ComponentDemo
path='/webforj/splitterautosave'
files={[
  'src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java',
  'src/main/java/com/webforj/samples/components/SplitterBox.java',
  'src/main/frontend/css/splitter-box.css',
]}
height='400px'
/>

In de voorgaande demo activeert elke Splitter instantie de AutoSave-functie door de methode `setAutosave` aan te roepen. Dit zorgt ervoor dat paneelgroottes automatisch worden opgeslagen in de lokale opslag. Bij het herladen van de browser blijven de groottes van deze splitters hetzelfde.

Door op de knop "Staat wissen" te klikken, wordt de methode `cleanState()` aangeroepen en vernieuwt het browvenster om de oorspronkelijke afmetingen weer te geven.

## Stijlen {#styling}

<TableBuilder name="Splitter" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Splitter` component, overweeg je de volgende beste praktijken:

- **Aanpassen op basis van content**: Overweeg bij het bepalen van de oriëntatie en initiële groottes van panelen de prioriteit van de content. Bijvoorbeeld, in een indeling met een navigatiezijbalk en een hoofdinhoudsgebied, zou de zijbalk doorgaans smaller moeten blijven met een vast minimumgrootte voor duidelijke navigatie.

- **Strategisch nestelen**: Nestelen van splitters kan veelzijdige indelingen creëren, maar kan de gebruikersinterface compliceren en de prestaties beïnvloeden. Plan je geneste indelingen om ervoor te zorgen dat ze intuïtief zijn en de gebruikerservaring verbeteren.

- **Denk aan gebruikersvoorkeuren**: Gebruik de AutoSave-functie om gebruikersaanpassingen tussen sessies te onthouden en de gebruikerservaring te verbeteren. Bied een optie aan waarmee gebruikers naar de standaardinstellingen kunnen terugkeren.
