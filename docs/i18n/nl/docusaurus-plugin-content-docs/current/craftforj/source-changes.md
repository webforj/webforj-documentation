---
title: Writing Changes to Source
sidebar_position: 4
description: >-
  Review the changes you made in craftforJ as a diff, choose where each one is
  written, and apply them to your Java source.
_i18n_hash: c79e8574cbf260fd784a2cffc00a0ab5
---
Een wijziging in een eigenschap in craftforJ verandert de actieve app en verder niets. Om een wijziging vast te houden, review je deze en schrijf je deze in het Java-bestand waaruit het afkomstig is. Deze pagina beschrijft die stap.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/apply-changes.mp4" type="video/mp4" />
  </video>
</div>

:::warning craftforJ schrijft naar je project
Houd je werk in versiebeheer. Bekijk het verschil voordat je het toepast, en bekijk het opnieuw voordat je het commit.
:::

## Pendende wijzigingen {#pending-changes}

Elke eigenschap die je verandert wordt vastgelegd als een pendende wijziging, en craftforJ toont hoeveel er wachten. Pendende wijzigingen overleven een pagina-herlaad en een routewijziging, omdat craftforJ ze opnieuw toepast wanneer je componenten opnieuw worden opgebouwd.

## Beoordelen en toepassen {#reviewing-and-applying}

Druk op <kbd>Cmd/Ctrl</kbd> + <kbd>S</kbd> om de beoordeling te openen. Wijzigingen zijn gegroepeerd op basis van het bestand waarin ze terechtkomen. Elke wijziging toont de eigenschap met de oude en nieuwe waarde, en breidt zich uit naar het verschil van het bestand. Als een wijziging een berekende waarde door een vaste waarde zou vervangen, waarschuwt craftforJ je en noemt het de expressie die gaat worden vervangen. Niets wordt geschreven totdat je het toepast. Voordat je dat doet, kun je elke wijziging afzonderlijk terugdraaien of verwerpen.

![De beoordeling met wijzigingen gegroepeerd per bestand en één uitgebreid naar het verschil](/img/craftforj/source-changes/review.png#rounded-border)

## Kiezen waar een wijziging wordt geschreven {#choosing-where-a-change-is-written}

Waar een wijziging wordt geschreven, bepaalt hoe ver deze reikt. Wanneer een component direct in een weergave wordt opgebouwd, gaat de wijziging naar die weergave. Wanneer het binnen een herbruikbare klasse wordt opgebouwd, heb je twee opties:

- **Het gebruik** - de plaats waar de component wordt gebruikt, wat alleen het scherm voor je verandert. Dit is de standaardoptie.
- **De definitie** - de plaats waar de component wordt opgebouwd, wat elk scherm verandert dat deze gebruikt.

Elke pendende wijziging toont welke van de twee van toepassing is en laat je er tussen schakelen. Sommige eigenschappen kunnen alleen bij de definitie worden geschreven, omdat de component deze zelf instelt in plaats van ze van de aanroeper te accepteren. craftforJ markeert deze voordat je ze toepast.

## Nadat je het hebt toegepast {#after-you-apply}

Het schrijven van Java zorgt ervoor dat je app opnieuw wordt opgebouwd en opnieuw wordt gestart. craftforJ meldt de herstart, wacht daarop en maakt opnieuw verbinding met je selectie en je resterende pendende wijzigingen intact. Geleverde wijzigingen verlaten de pendende lijst zodra ze in je bestanden staan.

Dit is het enige punt waar je herlaadinstelling belangrijk is. craftforJ heeft geen live herlaad nodig om te werken, omdat alles wat je verandert terwijl je inspecteert, onmiddellijke invloed heeft op de actieve app, zonder dat er een wederopbouw bij betrokken is. Schrijven naar de bron is anders: het verandert een bestand waaruit je app is opgebouwd, dus de app moet opnieuw worden opgebouwd voordat de wijziging uit jouw code komt in plaats van uit craftforJ. Met [live reload](/docs/configuration/deploy-reload/overview) geconfigureerd, gebeurt dat vanzelf. Zonder dit moet je de app zelf opnieuw starten.

## Het uitschakelen {#turning-it-off}

Je kunt het schrijven naar Java voor een app in de craftforJ-instellingen uitschakelen, of het volledig verwijderen met de [`source-changes`](/docs/craftforj/configuration#feature-flags) eigenschap. Met een van beide uitgeschakeld werkt het bewerken van eigenschappen nog steeds, maar blijft het live.
