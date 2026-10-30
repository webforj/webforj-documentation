---
title: Schrijven van Wijzigingen naar Bron
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: dd96407889e9e9fa1de4af299809dcad
---
Het wijzigen van een eigenschap in craftforJ verandert de actieve app en niets anders. Om een wijziging te behouden, beoordeel je deze en schrijf je deze in het Java-bestand waaruit het afkomstig is. Deze pagina beschrijft die stap.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

:::warning craftforJ schrijft naar je project
Houd je werk onder versiebeheer. Lees het verschil voordat je het toepast en lees het opnieuw voordat je commit.
:::

## Hangende wijzigingen {#pending-changes}

Elke eigenschap die je wijzigt, wordt geregistreerd als een hangende wijziging, en craftforJ toont hoeveel er wachten. Hangende wijzigingen overleven een pagina-herladen en een routeverandering, omdat craftforJ ze opnieuw toepast wanneer je componenten opnieuw worden opgebouwd.

## Beoordelen en toepassen {#reviewing-and-applying}

Druk op <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> om de beoordeling te openen. Wijzigingen zijn gegroepeerd op basis van het bestand waarin ze terechtkomen. Elke wijziging toont de eigenschap met zijn oude en nieuwe waarde en strekt zich uit tot het verschil van het bestand. Als een wijziging een berekende waarde zou vervangen door een vaste waarde, waarschuwt craftforJ je en noemt het de expressie die het op het punt staat te vervangen. Niets wordt geschreven totdat je het toepast. Voordat je dat doet, kun je elke wijziging afzonderlijk terugdraaien of verwijderen.

![De beoordeling met wijzigingen gegroepeerd per bestand en één uitgebreid naar het verschil](/img/craftforj/source-changes/review.png#rounded-border)

## Kiezen waar een wijziging wordt geschreven {#choosing-where-a-change-is-written}

Waar een wijziging wordt geschreven, bepaalt hoe ver deze reikt. Wanneer een component rechtstreeks in een weergave wordt opgebouwd, gaat de wijziging naar die weergave. Wanneer het is opgebouwd binnen een herbruikbare klasse, heb je twee opties:

- **Het gebruik** - de plaats waar de component wordt gebruikt, wat alleen het scherm voor je verandert. Dit is de standaard.
- **De definitie** - de plaats waar de component wordt opgebouwd, wat elk scherm verandert dat deze gebruikt.

Elke hangende wijziging toont welke van de twee van toepassing is en laat je tussen hen schakelen. Sommige eigenschappen kunnen alleen worden geschreven bij de definitie, omdat de component ze zelf instelt in plaats van ze van de aanroeper te accepteren. craftforJ markeert deze voordat je toepast.

## Nadat je toepast {#after-you-apply}

Het schrijven van Java zorgt ervoor dat je app opnieuw wordt opgebouwd en herstart. craftforJ rapporteert de herstart, wacht daarop en herverbindt met je selectie en je overgebleven hangende wijzigingen intact. Toegepaste wijzigingen verdwijnen uit de hangende lijst zodra ze in je bestanden staan.

Dit is het enige moment waarop je herlaadinstelling van belang is. craftforJ heeft geen live reload nodig om te functioneren, omdat alles wat je wijzigt terwijl je inspecteert, onmiddellijk effect heeft in de draaiende app, zonder dat er een herbouw nodig is. Schrijven naar de bron is anders: het verandert een bestand waar je app uit is opgebouwd, dus de app moet opnieuw worden opgebouwd voordat de wijziging van jouw code komt in plaats van van craftforJ. Met [live reload](/docs/configuration/deploy-reload/overview) geconfigureerd, gebeurt dat vanzelf. Zonder deze functie moet je de app zelf herstarten.

## Uitschakelen {#turning-it-off}

Je kunt het schrijven naar Java voor een app in de instellingen van craftforJ uitschakelen, of het volledig verwijderen met de [`source-changes`](/docs/craftforj/configuration#feature-flags) eigenschap. Met beide uitgeschakeld, werkt het bewerken van eigenschappen nog steeds maar blijft het live.
