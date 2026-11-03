---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: b276ce8df35b91ed18f07c8a686fb688
---
Het tabblad Routes toont de routeringstabel van de draaiende app in de [hiërarchie](/docs/routing/route-hierarchy/overview) waarin de router deze vasthoudt, met de actieve route gemarkeerd. Routes die [dynamisch](/docs/routing/routes-registration) zijn geregistreerd, verschijnen naast de geannoteerde.

![De routeringsboom met de actieve route gemarkeerd](/img/craftforj/routes/tree.png#rounded-border)

## Route-details {#route-details}

Een route selecteren toont wat de router erover weet, inclusief het pad, de bijbehorende klasse, de levenscycluswaarnemers die eraan zijn gekoppeld en de configuratie. Je kunt deze klasse vanuit hier openen in de source viewer.

## Navigeren vanuit craftforJ {#navigating-from-craftforj}

Je kunt rechtstreeks naar elke route navigeren vanuit craftforJ. Routes die parameters vereisen bieden een veld voor elk en lossen het pad op terwijl je ze invult, zodat je kunt bevestigen waar je terechtkomt voordat je gaat.

Op deze manier navigeren is echte navigatie, zodat de [levenscycluswaarnemers](/docs/routing/navigation-lifecycle/observers) van je app precies zo functioneren als voor een gebruiker. De boom volgt ook de app, dus navigeren in de app zelf verplaatst de marker.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## Toegangsregels {#access-rules}

Elke route heeft een badge voor de op deze route gedeclareerde [beveiligingsannotatie](/docs/security/annotations), en je kunt de boom beperken tot openbare of beschermde routes vanuit de werkbalk.

Alleen `@RolesAllowed` en `@DenyAll` worden als beschermd beschouwd. `@PermitAll` benoemt geen rollen en vereist alleen dat iemand is ingelogd, zodat de filter het als openbaar behandelt. Houd hier rekening mee wanneer je controleert welke routes toegang beperken op basis van rol.

![De routeringsboom met een toegangsbadge op elke route](/img/craftforj/routes/access-badge.png#rounded-border)

Je kunt ook de toegangsregel van een route wijzigen vanuit craftforJ. craftforJ schrijft de annotatie in de klasse van de route en de app wordt opnieuw opgestart, zodat de wijziging dezelfde beoordeling ondergaat als elke andere [bronwijziging](/docs/craftforj/source-changes). De optie is niet beschikbaar wanneer craftforJ geen toestemming heeft om Java te schrijven.

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4#t=0.5" type="video/mp4" />
  </video>
</div>
