---
title: Splitter
sidebar_position: 115
_i18n_hash: 2f66a9093a3c1f6e339df8fb42048a55
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

De `Splitter` component, ontworpen om inhoud binnen je app te verdelen en opnieuw te schalen, omvat twee resizable componenten: de master en de detailcomponenten. Een scheidingslijn scheidt deze componenten, waardoor gebruikers de grootte van elke component dynamisch kunnen aanpassen aan hun voorkeuren.

<!-- INTRO_END -->

## Een splitter maken {#creating-a-splitter}

Maak een `Splitter` door twee componenten aan de constructor door te geven. De eerste wordt het masterpaneel en de tweede het detailpaneel.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Min en max grootte {#min-and-max-size}

De `Splitter` component biedt methoden om minimum en maximum groottes voor zijn panelen in te stellen, waardoor je het schalengedrag van de componenten binnen de `Splitter` kunt controleren. Wanneer gebruikers proberen de panelen groter of kleiner te maken dan de opgegeven min- of max-groottes, handhaaft de splittercomponent deze beperkingen, zodat de panelen binnen de gedefinieerde grenzen blijven.

### Groottes instellen {#setting-sizes}

De methode `setMasterMinSize(String masterMinSize)` specificeert de minimumgrootte voor het masterpaneel van de splitter. Evenzo specificeert de methode `setMasterMaxSize(String masterMaxSize)` de maximumgrootte voor het masterpaneel.

Je kunt groottes specificeren met behulp van geldige CSS-eenheden, zoals hieronder weergegeven:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Oriëntatie {#orientation}

Je kunt de oriëntatie in de `Splitter` component configureren, zodat je indelingen kunt maken die zijn aangepast aan specifieke ontwerpeisen. Door de oriëntatie op te geven, arrangeert de component panelen horizontaal of verticaal, wat veelzijdigheid in het ontwerp van indelingen biedt.

Om de oriëntatie te configureren, gebruik je de ondersteunde oriëntaties Enum om aan te geven of de `Splitter` horizontaal of verticaal moet worden weergegeven:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Relatieve positie {#relative-position}

Om de initiële positie van de scheidingsbalk in de `Splitter` component in te stellen, gebruik je `setPositionRelative`. Deze methode neemt een numerieke waarde van `0` tot `100` die het percentage van de gegeven ruimte in de `Splitter` vertegenwoordigt, en stelt de scheiding op het gegeven percentage van de totale breedte weer:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Nesten {#nesting}

Het nestelen van Splitters stelt je in staat om complexe indelingen te creëren met niveaus van resizable panelen. Het maakt de creatie van geavanceerde gebruikersinterfaces mogelijk met gedetailleerde controle over de schikking en het schalen van inhoud.

Om Splitter componenten te nestelen, installeer je nieuwe `Splitter` instanties en voeg je ze als kinderen toe aan bestaande `Splitter` componenten. Deze hiërarchische structuur maakt het mogelijk om indelingen op meerdere niveaus te creëren met flexibele schalingsmogelijkheden. Het onderstaande programma demonstreert dit:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Autosave {#auto-save}

De `Splitter` component bevat een AutoSave-optie, waarmee de status van paneelgroottes in de lokale opslag wordt opgeslagen om afmetingen consistent te houden tussen herlaadbeurten.

Wanneer je de autosave-configuratie instelt, slaat de `Splitter` component automatisch de status van paneelgroottes op in de lokale opslag van de webbrowser. Dit zorgt ervoor dat de groottes die gebruikers voor panelen kiezen, behouden blijven bij het herladen van de pagina of browersessies, waardoor de behoefte aan handmatige aanpassingen vermindert.

### De status opschonen {#cleaning-the-state}

Om programmatically de `Splitter` terug te zetten naar standaardinstellingen en afmetingen, roep je de methode `cleanState()` aan om gegevens met betrekking tot de `Splitter` component uit de lokale opslag van de webbrowser te verwijderen.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

In de voorafgaande demo activeert elke Splitter-instantie de AutoSave-functie door de methode `setAutosave` aan te roepen. Dit zorgt ervoor dat paneelgroottes automatisch worden opgeslagen in de lokale opslag. Bij het herladen van de browser blijven de groottes van deze splitters hetzelfde.

Door op de knop "Clear State" te klikken, wordt de methode `cleanState()` aangeroepen en wordt het browservenster vernieuwd om de oorspronkelijke afmetingen weer te geven.

## Stijlen {#styling}

<TableBuilder name="Splitter" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Splitter` component, overweeg de volgende beste praktijken:

- **Aanpassen op basis van inhoud**: Bij het kiezen van de oriëntatie en initiële groottes van panelen, houd rekening met de prioriteit van de inhoud. Bijvoorbeeld, in een lay-out met een navigatiesidebar en een hoofdinhoudsgebied, moet de sidebar typischerwijs smaller blijven met een ingestelde min-grootte voor duidelijke navigatie.

- **Strategisch nestelen**: Nestelen van splitters kan veelzijdige indelingen creëren, maar kan de UI compliceren en de prestaties beïnvloeden. Plan je geneste indelingen om ervoor te zorgen dat ze intuïtief zijn en de gebruikerservaring verbeteren.

- **Vergeet gebruikersvoorkeuren niet**: Gebruik de AutoSave-functie om gebruikersaanpassingen over sessies heen te onthouden, wat de gebruikerservaring verbetert. Bied een optie om gebruikers terug te laten keren naar de standaardinstellingen.
