---
sidebar_position: 10
title: BBj Controls and webforJ Components
_i18n_hash: 929625ea8b8335de7326ecb067dca773
---
Het webforJ-framework is ontworpen om een Java-API te bieden rond de BBj-taal's DWC en biedt een robuuste architectuur voor het bouwen en beheren van componenten.

## Mapping BBj-controls naar webforJ-componenten {#mapping-bbj-controls-to-webforj-components}
Een van de fundamentele principes van webforJ is de binding van BBj-controls met webforJ-componenten. In deze architectuur heeft elke webforJ-component die met het product wordt geleverd een een-op-een mapping met een onderliggend BBj-control. Deze mapping zorgt ervoor dat Java-componenten het gedrag en de eigenschappen van hun BBj-tegenhangers naadloos spiegel.

Deze nauwe overeenkomst tussen webforJ-componenten en BBj-controls vereenvoudigt de ontwikkeling en stelt Java-ontwikkelaars in staat om met vertrouwde concepten te werken bij het bouwen van webgebaseerde toepassingen zonder de noodzaak om BBj-code te schrijven.

## De `DwcComponent` basis klasse {#the-dwccomponent-base-class}
In het hart van de componentarchitectuur van webforJ ligt de DWCComponent-basis klasse. Alle webforJ-componenten zijn afgeleid van deze klasse. Deze overerving geeft elke webforJ-component toegang tot het onderliggende BBj-control, wat een directe link biedt tussen de Java-component en het BBj-control dat het vertegenwoordigt.

Het is echter belangrijk op te merken dat ontwikkelaars niet zijn toegestaan om de DWCComponent-klasse verder uit te breiden. Proberen dit te doen resulteert in een runtime-exceptie die dergelijke uitbreidingen verbiedt. Deze beperking is ingesteld om de integriteit van het onderliggende BBj-control te waarborgen en ervoor te zorgen dat ontwikkelaars het niet onbedoeld manipuleren op manieren die tot onvoorspelbare gevolgen kunnen leiden.

### Eindclasses en uitbreidingsbeperkingen {#final-classes-and-extension-restrictions}
In webforJ zijn de meeste componentklassen, met uitzondering van de ingebouwde HTML-elementen en eventuele klassen die deze uitbreiden, als `final` gedeclareerd. Dit betekent dat ze niet beschikbaar zijn voor uitbreiding of subklassen. Deze ontwerpkeuze is doelbewust en dient meerdere doelen:

1. **Controle over het onderliggende BBj-control**: Zoals eerder vermeld, zou het uitbreiden van webforJ-componentklassen ontwikkelaars controle geven over het onderliggende BBj-control. Om de consistentie en voorspelbaarheid van het gedrag van componenten te behouden, is dit niveau van controle beperkt.

2. **Voorkomen van onbedoelde wijzigingen**: Het final maken van de componentklassen voorkomt onopzettelijke wijzigingen aan kerncomponenten, waardoor het risico op onverwacht gedrag of kwetsbaarheden vermindert.

3. **Bevordering van het gebruik van composieten**: Om de functionaliteit van componenten uit te breiden, moedigt het webforJ-framework ontwikkelaars aan om een composietbenadering te gebruiken. Composietcomponenten zijn Java-klassen die andere webforJ-componenten of standaard HTML-elementen bevatten. Terwijl traditionele overerving wordt afgeraden, bieden composietcomponenten een manier om nieuwe, aangepaste componenten te maken die bestaande componenten encapsuleren.

## Composietcomponenten: uitbreiden door compositie {#composite-components-extending-through-composition}
In het webforJ-framework speelt het concept van composietcomponenten een cruciale rol bij het uitbreiden van de functionaliteit van componenten. Composietcomponenten zijn Java-klassen die niet beperkt zijn door het final-trefwoord, waardoor ontwikkelaars nieuwe componenten kunnen maken die het gedrag van een enkele component uitbreiden of meerdere componenten in één combineren door bestaande componenten te composeren. Klassen die dit gedrag mogelijk maken zijn gecreëerd voor gebruik door ontwikkelaars. Zie de secties `Composite` en `ElementComposite` om te zien hoe je composietcomponenten op de juiste manier kunt maken.

Deze benadering moedigt een meer modulaire en flexibele ontwikkelingsstijl aan, waardoor ontwikkelaars op maat gemaakte componenten kunnen bouwen die aan specifieke eisen voldoen.
