---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 7fd4306a016d3734d34336b8136c6e11
---
Het webforJ-framework is ontworpen om een Java API te bieden rond de BBj-taal's DWC en biedt een robuuste architectuur voor het bouwen en beheren van componenten.

## Mappen van BBj-controles naar webforJ-componenten {#mapping-bbj-controls-to-webforj-components}
Een van de fundamentele principes van webforJ is de binding van BBj-controles met webforJ-componenten. In deze architectuur heeft elke webforJ-component die met het product wordt geleverd een één-op-één mapping met een onderliggend BBj-controle. Deze mapping zorgt ervoor dat Java-componenten het gedrag en de eigenschappen van hun BBj-tegenhangers naadloos weerspiegelen.

Deze nauwe overeenkomst tussen webforJ-componenten en BBj-controles vereenvoudigt de ontwikkeling en stelt Java-ontwikkelaars in staat om met vertrouwde concepten te werken bij het bouwen van webgebaseerde toepassingen zonder dat ze BBj-code hoeven te schrijven.

## De `DwcComponent` basisclass {#the-dwccomponent-base-class}
In het hart van de componentarchitectuur van webforJ ligt de DWCComponent-basisclass. Alle webforJ-componenten erven van deze class. Deze erfelijkheid verleent elke webforJ-component toegang tot zijn onderliggende BBj-controle, wat een directe link biedt tussen de Java-component en de BBj-controle die hij vertegenwoordigt.

Het is echter belangrijk op te merken dat ontwikkelaars beperkt zijn in het verder uitbreiden van de DWCComponent-class. Proberen dit te doen, resulteert in een runtime-exceptie die dergelijke extensies verbiedt. Deze beperking is ingesteld om de integriteit van de onderliggende BBj-controle te waarborgen en ervoor te zorgen dat ontwikkelaars het niet per ongeluk manipuleren op manieren die kunnen leiden tot ongewenste gevolgen.

### Finale classes en uitbreidingsbeperkingen {#final-classes-and-extension-restrictions}
In webforJ zijn de meeste componentclasses, met uitzondering van de ingebouwde HTML-elementen en alle classes die hiervan erven, verklaard als `final`. Dit betekent dat ze niet beschikbaar zijn voor uitbreiding of subclassing. Deze ontwerpkeuze is opzettelijk en dient meerdere doeleinden:

1. **Beheer Over Onderliggende BBj-controle**: Zoals eerder genoemd, zou het uitbreiden van webforJ-componentclasses ontwikkelaars controle geven over de onderliggende BBj-controle. Om de consistentie en voorspelbaarheid van het gedrag van componenten te behouden, is dit niveau van controle beperkt.

2. **Voorkomen van Onbedoelde Wijzigingen**: Door de componentclasses `final` te maken, worden onbedoelde wijzigingen aan kerncomponenten voorkomen, waardoor het risico op onverwachte gedragingen of kwetsbaarheden wordt verminderd.

3. **Bevorderen van het Gebruik van Composieten**: Om de functionaliteit van componenten uit te breiden, moedigt het webforJ-framework ontwikkelaars aan om een composietaanpak te gebruiken. Composietcomponenten zijn Java-classes die andere webforJ-componenten of standaard HTML-elementen bevatten. Terwijl traditionele erfelijkheid wordt afgeraden, bieden composietcomponenten een manier om nieuwe, aangepaste componenten te creëren die bestaande componenten encapsuleren.

## Composietcomponenten: uitbreiden door middel van compositie {#composite-components-extending-through-composition}
In het webforJ-framework speelt het concept van composietcomponenten een cruciale rol in het uitbreiden van de functionaliteit van componenten. Composietcomponenten zijn Java-classes die niet beperkt zijn door het final-keyword, waardoor ontwikkelaars nieuwe componenten kunnen creëren die het gedrag van een enkele component uitbreiden, of meerdere componenten tot één combineren door bestaande componenten samen te voegen. Klassen die dit gedrag mogelijk maken, zijn gemaakt voor gebruik door ontwikkelaars. Zie de secties `Composite` en `ElementComposite` om te zien hoe u composietcomponenten op de juiste manier kunt maken.

Deze aanpak moedigt een meer modulaire en flexibele ontwikkelingsstijl aan, waardoor ontwikkelaars op maat gemaakte componenten kunnen bouwen die aan specifieke vereisten voldoen.
