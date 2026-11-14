---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: ec7a4521cd9c4328f9cd6550ebc9d90b
---
Het tabblad Routes toont de routeringstabel van de actieve app in de [hiërarchie](/docs/routing/route-hierarchy/overview) waarin de router deze bijhoudt, met de actieve route gemarkeerd. Routes die [dynamisch](/docs/routing/routes-registration) zijn geregistreerd, verschijnen naast routes met annotaties.

![De routestructuur met de actieve route gemarkeerd](/img/craftforj/routes/tree.png#rounded-border)

## Routedetails {#route-details}

Als je een route selecteert, zie je wat de router erover weet, waaronder het pad, de bijbehorende klasse, de lifecycle-observers die eraan gekoppeld zijn en de configuratie. Je kunt de klasse hier openen in de broncodeviewer.

## Navigeren vanuit craftforJ {#navigating-from-craftforj}

Je kunt vanuit craftforJ rechtstreeks naar elke route navigeren. Routes met parameters bieden voor elke parameter een veld en vullen het pad in terwijl je de waarden opgeeft. Zo kun je bevestigen waar je terechtkomt voordat je gaat.

Navigeren op deze manier is een echte navigatie, dus de [lifecycle-observers](/docs/routing/navigation-lifecycle/observers) van je app worden precies zo uitgevoerd als bij een gebruiker. De boomstructuur volgt de app ook, dus wanneer je in de app zelf navigeert, verplaatst de markering mee.

<VideoPlayer video="craftforj/route-navigate" width={1920} height={1000} title="Route navigate" />

## Toegangsregels {#access-rules}

Elke route heeft een badge voor de [beveiligingsannotatie](/docs/security/annotations) die erop is gedeclareerd. Via de werkbalk kun je de boomstructuur beperken tot openbare of beveiligde routes.

Alleen `@RolesAllowed` en `@DenyAll` gelden als beveiligd. `@PermitAll` noemt geen rollen en vereist alleen dat iemand is aangemeld, dus het filter behandelt deze als openbaar. Houd hier rekening mee wanneer je controleert welke routes de toegang op basis van rollen beperken.

![De routestructuur met een toegangsbadge op elke route](/img/craftforj/routes/access-badge.png#rounded-border)

Je kunt ook de toegangsregel van een route wijzigen vanuit craftforJ. craftforJ schrijft de annotatie naar de klasse van de route en de app wordt opnieuw gestart. De wijziging doorloopt dus dezelfde controle als elke andere [broncodewijziging](/docs/craftforj/source-changes). Deze optie is niet beschikbaar wanneer craftforJ geen Java-code mag schrijven.

<VideoPlayer video="craftforj/route-security" width={1920} height={1000} title="Route security" />
