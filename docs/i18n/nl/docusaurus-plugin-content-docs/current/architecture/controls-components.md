---
sidebar_position: 10
title: Componentarchitectuur
description: >-
  See how webforJ components are built on the DwcComponent base class, why
  component classes are final, and why composition replaces inheritance.
_i18n_hash: 13c29b780cb00a918fe6e0a98c5e783a
---
webforJ biedt je een Java-API voor een bibliotheek met webcomponenten en beheert elke component vanaf de server. Op deze pagina wordt uitgelegd hoe componenten zijn opgebouwd en hoe je ze kunt uitbreiden.

## Opbouw van componenten {#how-components-are-structured}
Elke webforJ-component die bij het product wordt geleverd, is een Java-klasse die één-op-één is gekoppeld aan een client-side webcomponent uit de DWC-componentenbibliotheek. De Java-klasse bevat de status en API van de component op de server, en het framework houdt de webcomponent in de browser hiermee gesynchroniseerd.

Je werkt alleen met de Java-klasse. Het framework handelt eigenschappen, gebeurtenissen en weergave in de browser af, zodat je webapps uitsluitend in Java bouwt.

## De basisklasse `DwcComponent` {#the-dwccomponent-base-class}
De DWCComponent-basisklasse vormt de kern van de componentarchitectuur van webforJ. Alle webforJ-componenten erven van deze klasse. De klasse beheert de koppeling tussen de Java-component en de client-side tegenhanger, waaronder de creatie, updates van eigenschappen en gebeurtenissen.

Het is echter belangrijk om te weten dat ontwikkelaars de DWCComponent-klasse niet verder mogen uitbreiden. Een poging om dit te doen leidt tot een runtime-uitzondering die dergelijke uitbreidingen verhindert. Deze beperking is ingesteld om de integriteit van de koppeling te behouden en ervoor te zorgen dat ontwikkelaars deze niet per ongeluk op manieren aanpassen die onbedoelde gevolgen kunnen hebben.

### Final-klassen en beperkingen op uitbreidingen {#final-classes-and-extension-restrictions}
In webforJ zijn de meeste componentklassen als `final` gedeclareerd, met uitzondering van de ingebouwde HTML-elementen en alle klassen die daarvan erven. Dit betekent dat ze niet kunnen worden uitgebreid of als superklasse kunnen worden gebruikt. Deze ontwerpkeuze is bewust gemaakt en dient meerdere doelen:

1. **Controle over de client-side koppeling**: Zoals eerder vermeld, zou het uitbreiden van webforJ-componentklassen de interne koppeling tussen de Java-component en de client-side tegenhanger blootleggen. Om de consistentie en voorspelbaarheid van het gedrag van componenten te behouden, is deze mate van controle beperkt.

2. **Onbedoelde wijzigingen voorkomen**: Door de componentklassen `final` te maken, worden onbedoelde wijzigingen aan kerncomponenten voorkomen en neemt het risico af op onverwacht gedrag of beveiligingsproblemen.

3. **Het gebruik van composieten bevorderen**: Om de functionaliteit van componenten uit te breiden, moedigt het webforJ-framework ontwikkelaars aan om compositie te gebruiken. Composietcomponenten zijn Java-klassen die andere webforJ-componenten of standaard HTML-elementen bevatten. Hoewel traditionele overerving wordt afgeraden, bieden composietcomponenten een manier om nieuwe, aangepaste componenten te maken die bestaande componenten omvatten.

<!-- ## Componenten toevoegen aan de DOM
TODO: Bespreken hoe webforJ de client-side tegenhanger van een component bepaalt -->

## Composietcomponenten: uitbreiden door compositie {#composite-components-extending-through-composition}
In het webforJ-framework speelt het concept van composietcomponenten een belangrijke rol bij het uitbreiden van de functionaliteit van componenten. Composietcomponenten zijn Java-klassen die niet beperkt zijn door het sleutelwoord `final`. Daardoor kunnen ontwikkelaars nieuwe componenten maken die het gedrag van één component uitbreiden of meerdere componenten combineren door bestaande componenten samen te stellen. Er zijn klassen beschikbaar die dit gedrag mogelijk maken. Zie de onderdelen `Composite` en `ElementComposite` voor meer informatie over het correct maken van composietcomponenten.

Deze aanpak stimuleert een meer modulaire en flexibele ontwikkelstijl, zodat ontwikkelaars componenten op maat kunnen maken die aan specifieke vereisten voldoen.

<!--
## Componenthiërarchie {#component-hierarchy}
TODO: Een afbeelding maken en tonen -->
