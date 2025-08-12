---
title: Splitter
sidebar_position: 115
_i18n_hash: 7a830c81311c3830e4d1c36bd08903c5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-splitter" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="splitter" location="com/webforj/component/layout/splitter/Splitter" top='true'/>


De `Splitter` component, ontworpen om inhoud binnen uw app te verdelen en te schalen, encapsuleert twee schaalbare componenten: de master- en detailcomponenten. Een scheidingslijn scheidt deze componenten, waardoor gebruikers de grootte van elke component dynamisch kunnen aanpassen aan hun voorkeuren.

<ComponentDemo 
path='/webforj/splitterbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterBasicView.java'
height='300px'
/>

## Min en max grootte {#min-and-max-size}

De `Splitter` component biedt methoden om minimum- en maximumgroottes voor zijn panelen in te stellen, waardoor u het schalingsgedrag van de componenten binnen de `Splitter` kunt beheersen. Wanneer gebruikers proberen panelen buiten de opgegeven min- of max-groottes te schalen, handhaaft de splittercomponent deze beperkingen, zodat panelen binnen de gedefinieerde grenzen blijven.

### Groottes instellen {#setting-sizes}

De `setMasterMinSize(String masterMinSize)` methode specificeert de minimumgrootte voor het masterpaneel van de splitter. Evenzo specificeert de `setMasterMaxSize(String masterMaxSize)` methode de maximumgrootte voor het masterpaneel.

U kunt groottes opgeven met behulp van geldige CSS-eenheden, zoals hieronder weergegeven:

<ComponentDemo 
path='/webforj/splitterminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterMinMaxView.java'
height='300px'
/>

## Oriëntatie {#orientation}

U kunt de oriëntatie in de `Splitter` component configureren, waardoor u lay-outs kunt creëren die zijn afgestemd op specifieke ontwerpeisen. Door de oriëntatie op te geven, rangschikt de component panelen horizontaal of verticaal, wat veelzijdigheid in lay-outontwerp biedt.

Om de oriëntatie te configureren, gebruikt u de ondersteunde oriëntaties Enum om te specificeren of de `Splitter` horizontaal of verticaal moet worden weergegeven:

<ComponentDemo 
path='/webforj/splitterorientation?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterOrientationView.java'
height='300px'
/>

## Relatieve positie {#relative-position}

Om de initiële positie van de scheidingslijn in de `Splitter` component in te stellen, gebruikt u `setPositionRelative`. Deze methode neemt een numerieke waarde van `0` tot `100` die het percentage van de gegeven ruimte in de `Splitter` vertegenwoordigt en toont de scheidingslijn op het opgegeven percentage van de totale breedte:

<ComponentDemo 
path='/webforj/splitterposition?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterPositionView.java'
height='300px'
/>

## Nesting {#nesting}

Nesting van Splitters stelt u in staat om complexe lay-outs te creëren met niveaus van schaalbare panelen. Het maakt de creatie van geavanceerde gebruikersinterfaces mogelijk met granulaire controle over de rangschikking en schaling van inhoud.

Om Splitter-componenten te nesten, instantiëert u nieuwe `Splitter` instanties en voegt u ze als kinderen toe aan bestaande `Splitter` componenten. Deze hiërarchische structuur maakt de creatie van multi-niveau lay-outs met flexibele schalingsmogelijkheden mogelijk. Het onderstaande programma demonstreert dit:

<ComponentDemo 
path='/webforj/splitternested?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterNestedView.java'
height='300px'
/>

## Autosave {#auto-save}

De `Splitter` component bevat een AutoSave-optie, die de status van panelgroottes opslaat in de lokale opslag om de afmetingen consistent te houden tussen herlaadacties.

Wanneer u de auto-save configuratie instelt, slaat de `Splitter` component automatisch de status van de panelgroottes op in de lokale opslag van de webbrowser. Dit zorgt ervoor dat de groottes die gebruikers voor panelen kiezen, persistent zijn tijdens het herladen van de pagina of browser sessies, waardoor de behoefte aan handmatige aanpassingen wordt verminderd.

### Het wissen van de status {#cleaning-the-state}

Om de `Splitter` programatisch terug te zetten naar de standaardinstellingen en afmetingen, roept u de `cleanState()` methode aan om eventuele opgeslagen statusgegevens met betrekking tot de `Splitter` component uit de lokale opslag van de webbrowser te verwijderen.

<ComponentDemo 
path='/webforj/splitterautosave?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/splitter/SplitterAutoSaveView.java'
height='400px'
/>

In de voorgaande demo activeert elke Splitter-instantie de AutoSave-functie door de `setAutosave` methode aan te roepen. Dit zorgt ervoor dat de panelgroottes automatisch worden opgeslagen in de lokale opslag. Wanneer de browser wordt herladen, blijven de groottes van deze splitters hetzelfde.

Klikken op de knop "Status wissen" roept de `cleanState()` methode aan en ververst het browservenster om de oorspronkelijke afmetingen weer te geven.

## Stijl {#styling}

<TableBuilder name="Splitter" />

## Best practices {#best-practices}

Om een optimale gebruikerservaring te garanderen bij het gebruik van de `Splitter` component, houd rekening met de volgende best practices: 

- **Aanpassen op basis van inhoud**: Bij het bepalen van de oriëntatie en initiële groottes van panelen, houdt rekening met de prioriteit van de inhoud. Bijvoorbeeld, in een lay-out met een navigatiesidebar en een hoofdinhoudsgebied, moet de sidebar meestal smaller blijven met een ingestelde minimumgrootte voor duidelijke navigatie.

- **Strategisch nesting**: Nesting van splitters kan veelzijdige lay-outs creëren, maar kan de UI compliceren en de prestaties beïnvloeden. Plan uw geneste lay-outs om ervoor te zorgen dat ze intuïtief zijn en de gebruikerservaring verbeteren.

- **Vergeet gebruikersvoorkeuren niet**: Gebruik de AutoSave-functie om gebruikersaanpassingen over sessies heen te onthouden, wat de gebruikerservaring verbetert. Bied een optie om gebruikers toe te staan om terug te keren naar de standaardinstellingen.
