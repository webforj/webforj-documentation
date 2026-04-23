---
title: Splitter
sidebar_position: 115
_i18n_hash: 9eb7b2aa3890f16f8fe8a2d4c303b227
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>

De `Splitter` component, ontworpen om inhoud binnen uw app te verdelen en te vergroten, omvat twee aanpasbare componenten: de master- en detailcomponenten. Een verdeler scheidt deze componenten, waardoor gebruikers de grootte van elke component dynamisch kunnen aanpassen aan hun voorkeuren.

<!-- INTRO_END -->

## Een splitter maken {#creating-a-splitter}

Maak een `Splitter` door twee componenten aan de constructor door te geven. De eerste wordt het masterpaneel en de tweede wordt het detailpaneel.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Min- en maxgrootte {#min-and-max-size}

De `Splitter` component biedt methoden om minimum- en maximumgroottes voor zijn panelen in te stellen, waardoor u het vergrotingsgedrag van de componenten binnen de `Splitter` kunt beheersen. Wanneer gebruikers proberen panelen groter of kleiner te maken dan de opgegeven minimum- of maximumgroottes, handhaaft de splittercomponent deze beperkingen, zodat de panelen binnen de gedefinieerde grenzen blijven.

### Groottes instellen {#setting-sizes}

De methode `setMasterMinSize(String masterMinSize)` specificeert de minimale grootte voor het masterpaneel van de splitter. Evenzo specificeert de methode `setMasterMaxSize(String masterMaxSize)` de maximale grootte voor het masterpaneel.

U kunt groottes specificeren met behulp van geldige CSS-eenheden, zoals hieronder weergegeven:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Oriëntatie {#orientation}

U kunt de oriëntatie in de `Splitter` component configureren, waardoor u lay-outs kunt maken die zijn afgestemd op specifieke ontwerpeisen. Door de oriëntatie op te geven, ordent de component panelen horizontaal of verticaal, wat veelzijdigheid biedt in het ontwerp van de lay-out.

Om de oriëntatie te configureren, gebruikt u de ondersteunde oriëntaties Enum om op te geven of de `Splitter` horizontaal of verticaal moet worden weergegeven:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Relatieve positie {#relative-position}

Om de initiële positie van de verdelerbalk in de `Splitter` component in te stellen, gebruikt u `setPositionRelative`. Deze methode neemt een numerieke waarde van `0` tot `100`, die het percentage van de gegeven ruimte in de `Splitter` vertegenwoordigt, en geeft de verdeler weer op het opgegeven percentage van de totale breedte:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Nesten {#nesting}

Nesting van splitters stelt u in staat om complexe lay-outs te creëren met niveaus van aanpasbare panelen. Het maakt het mogelijk om geavanceerde gebruikersinterfaces te creëren met gedetailleerde controle over de rangschikking en vergroting van inhoud.

Om Splitter-componenten te nestelen, instantiate nieuwe `Splitter`-instanties en voeg ze toe als kinderen aan bestaande `Splitter`-componenten. Deze hiërarchische structuur maakt de creatie van meerlaagse lay-outs met flexibele vergrotingsmogelijkheden mogelijk. Het programma hieronder demonstreert dit:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='300px'
/>

## Auto-opslag {#auto-save}

De `Splitter` component omvat een AutoSave-optie, die de status van paneelgroottes opslaat in de lokale opslag om de afmetingen consistent te houden tussen herlaadacties.

Wanneer u de auto-opslagconfiguratie instelt, slaat de `Splitter` component automatisch de status van paneelgroottes op in de lokale opslag van de webbrowser. Dit zorgt ervoor dat de groottes die gebruikers kiezen voor panelen behouden blijven bij het opnieuw laden van de pagina of browser-sessies, waardoor de noodzaak voor handmatige aanpassingen vermindert.

### De status opschonen {#cleaning-the-state}

Om programatisch de `Splitter` terug te brengen naar de standaardinstellingen en afmetingen, roept u de methode `cleanState()` aan om alle opgeslagen statusgegevens die verband houden met de `Splitter` component uit de lokale opslag van de webbrowser te verwijderen.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/components/SplitterBox.java',]}
cssURL='/css/splitter-box.css'
height='400px'
/>

In de voorgaande demo activeert elke Splitter-instantie de AutoSave-functie door de methode `setAutosave` aan te roepen. Dit zorgt ervoor dat paneelgroottes automatisch worden opgeslagen in de lokale opslag. Hierdoor blijven de groottes van deze splitters hetzelfde bij het opnieuw laden van de browser.

Door op de knop "Status wissen" te klikken, wordt de methode `cleanState()` aangeroepen en wordt het browservenster vernieuwd om de originele afmetingen weer te geven.

## Stijling {#styling}

<TableBuilder name="Splitter" />

## Beste praktijken {#best-practices}

Om een optimale gebruikerservaring te waarborgen bij het gebruik van de `Splitter` component, houdt u rekening met de volgende best practices: 

- **Aanpassen op basis van inhoud**: Bij het bepalen van de oriëntatie en initiële groottes van panelen, overweeg de prioriteit van de inhoud. Bijvoorbeeld, in een lay-out met een navigatie-zijbalk en een hoofdinhoudgebied, moet de zijbalk doorgaans smaller blijven met een ingestelde minimumgrootte voor duidelijke navigatie.

- **Strategisch nesten**: Nesting van splitters kan veelzijdige lay-outs creëren, maar kan de gebruikersinterface compliceren en de prestaties beïnvloeden. Plan uw geneste lay-outs om ervoor te zorgen dat ze intuïtief zijn en de gebruikerservaring verbeteren.

- **Vergeet gebruikersvoorkeuren niet**: Gebruik de AutoSave-functie om gebruikersaanpassingen tussen sessies te onthouden, wat de gebruikerservaring verbetert. Bied een optie om gebruikers in staat te stellen terug te keren naar de standaardinstellingen.
