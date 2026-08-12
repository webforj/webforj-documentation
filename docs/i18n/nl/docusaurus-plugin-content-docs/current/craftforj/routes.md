---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: 8a8c4099d3bd0d4ff988038cee6a5c15
---
Het tabblad Routes toont de routeringstabel van de actieve app in de [hiërarchie](/docs/routing/route-hierarchy/overview) waarin de router deze beheert, met de actieve route gemarkeerd. Routes die [dynamisch](/docs/routing/routes-registration) zijn geregistreerd, verschijnen naast geannoteerde routes.

![De routestructuur met de actieve route gemarkeerd](/img/craftforj/routes/tree.png#rounded-border)

## Route details {#route-details}

Het selecteren van een route toont wat de router erover weet, inclusief het pad, de klasse erachter, de levenscycluswaarnemers die eraan zijn gekoppeld en de configuratie. Je kunt die klasse vanuit hier openen in de bronviewer.

## Navigeren vanuit craftforJ {#navigating-from-craftforj}

Je kunt direct naar elke route navigeren vanuit craftforJ. Routes die parameters vereisen, bieden een veld voor elk daarvan en lossen het pad op terwijl je deze invult, zodat je kunt bevestigen waar je zult landen voordat je gaat.

Navigeren op deze manier is een echte navigatie, zodat de [levenscycluswaarnemers](/docs/routing/navigation-lifecycle/observers) van je app precies werken zoals ze zouden doen voor een gebruiker. De structuur volgt ook de app, zodat navigeren in de app zelf de marker verplaatst.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4" type="video/mp4" />
  </video>
</div>

## Toegangsregels {#access-rules}

Elke route heeft een badge voor de [beveiligingsannotatie](/docs/security/annotations) die erop is verklaard, en je kunt de structuur verkleinen tot openbare of beschermde routes vanuit de werkbalk.

Alleen `@RolesAllowed` en `@DenyAll` tellen als beschermd. `@PermitAll` benoemt geen rollen en vereist alleen dat iemand is ingelogd, zodat de filter het als openbaar behandelt. Houd hier rekening mee wanneer je controleert welke routes de toegang op basis van rol beperken.

![De routestructuur met een toegangsbadge op elke route](/img/craftforj/routes/access-badge.png#rounded-border)

Je kunt ook de toegangsregel van een route veranderen vanuit craftforJ. craftforJ schrijft de annotatie in de klasse van de route en de app herstart, zodat de wijziging dezelfde beoordeling ondergaat als elke andere [bronwijziging](/docs/craftforj/source-changes). De optie is niet beschikbaar wanneer craftforJ niet is toegestaan om Java te schrijven.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4" type="video/mp4" />
  </video>
</div>
