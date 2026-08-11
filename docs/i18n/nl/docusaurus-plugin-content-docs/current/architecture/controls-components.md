---
sidebar_position: 10
title: BBj Controls and webforJ Components
description: >-
  See how webforJ components map one-to-one to BBj controls through the
  DwcComponent base class and why composition replaces inheritance.
_i18n_hash: 83f48323774737067ddd9a6bebb0373d
---
Het webforJ-framework is ontworpen om een Java API te bieden rond de BBj-taal zijn DWC en biedt een robuuste architectuur voor het bouwen en beheren van componenten.

## Mapping BBj-controles naar webforJ-componenten {#mapping-bbj-controls-to-webforj-components}
Een van de fundamentele principes van webforJ is de binding van BBj-controles met webforJ-componenten. In deze architectuur heeft elke webforJ-component die met het product wordt meegeleverd een één-op-één mapping met een onderliggende BBj-controle. Deze mapping zorgt ervoor dat Java-componenten het gedrag en de eigenschappen van hun BBj-tegenhangers naadloos weerspiegelen.

Deze nauwe correspondentie tussen webforJ-componenten en BBj-controles vereenvoudigt de ontwikkeling en stelt Java-ontwikkelaars in staat om te werken met vertrouwde concepten bij het bouwen van webgebaseerde applicaties zonder de noodzaak om enige BBj-code te schrijven.

## De `DwcComponent` basisklasse {#the-dwccomponent-base-class}
In het hart van de componentarchitectuur van webforJ ligt de DWCComponent basisklasse. Alle webforJ-componenten erven van deze klasse. Deze erfenis geeft elke webforJ-component toegang tot zijn onderliggende BBj-controle, waardoor er een directe verbinding is tussen de Java-component en de BBj-controle die hij vertegenwoordigt.

Het is echter belangrijk op te merken dat ontwikkelaars beperkt zijn in het verder uitbreiden van de DWCComponent-klasse. Pogingen om dit te doen zullen resulteren in een runtime-exceptie die dergelijke uitbreidingen verbiedt. Deze beperking is in place om de integriteit van de onderliggende BBj-controle te behouden en ervoor te zorgen dat ontwikkelaars deze niet onopzettelijk manipuleren op manieren die tot onbedoelde gevolgen kunnen leiden.

### Definitieve klassen en uitbreidingsbeperkingen {#final-classes-and-extension-restrictions}
In webforJ zijn de meeste componentklassen, met uitzondering van de ingebouwde HTML-elementen en eventuele klassen die hiervan erven, als `final` verklaard. Dit betekent dat ze niet beschikbaar zijn voor uitbreiding of subklassen. Deze ontwerpoverweging is opzettelijk en dient meerdere doelen:

1. **Controle over onderliggende BBj-controle**: Zoals eerder vermeld, zou het uitbreiden van webforJ-componentklassen ontwikkelaars controle geven over de onderliggende BBj-controle. Om de consistentie en voorspelbaarheid van het gedrag van componenten te behouden, is dit niveau van controle beperkt.

2. **Voorkomen van onbedoelde wijzigingen**: Het final maken van de componentklassen voorkomt onopzettelijke wijzigingen aan kerncomponenten, waardoor het risico op onverwachte gedragingen of kwetsbaarheden afneemt.

3. **Bevordering van het gebruik van composieten**: Om de functionaliteit van componenten uit te breiden, moedigt het webforJ-framework ontwikkelaars aan om een composietbenadering te gebruiken. Composietcomponenten zijn Java-klassen die andere webforJ-componenten of standaard HTML-elementen bevatten. Terwijl traditionele erfenis wordt afgeraden, bieden composietcomponenten een manier om nieuwe, aangepaste componenten te creëren die bestaande encapsuleren.

<!-- ## Componenten toevoegen aan de DOM
TODO: Praten over hoe webforJ zal zoeken naar een BBj-controle - betekent dat je iets niet kunt toevoegen dat geen controle heeft -->

## Composietcomponenten: uitbreiden door compositie {#composite-components-extending-through-composition}
In het webforJ-framework speelt het concept van composietcomponenten een cruciale rol in het uitbreiden van componentfunctionaliteit. Composietcomponenten zijn Java-klassen die niet beperkt zijn door het final-trefwoord, waardoor ontwikkelaars nieuwe componenten kunnen creëren die het gedrag van een enkele component uitbreiden of meerdere componenten combineren tot één, door bestaande componenten te combineren. Klassen die dit gedrag faciliteren zijn gecreëerd voor gebruik door ontwikkelaars. Zie de secties `Composite` en `ElementComposite` om te zien hoe je composietcomponenten correct kunt maken.

Deze benadering moedigt een meer modulaire en flexibele ontwikkelingsstijl aan, waardoor ontwikkelaars op maat gemaakte componenten kunnen bouwen die aan specifieke vereisten voldoen.
