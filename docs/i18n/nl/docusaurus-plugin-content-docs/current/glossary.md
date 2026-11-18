---
sidebar_position: 1000
title: Woordenlijst
sidebar_class_name: sidebar--item__hidden
slug: glossary
description: >-
  Definitions for terms used across webforJ documentation, including DOM, Shadow
  DOM, and other web platform and framework concepts.
_i18n_hash: b8c6955dd483c1d5904668a163666015
---
## DOM {#dom}

De DOM (Document Object Model) is een programmeerinterface voor webdocumenten. Deze stelt de paginastructuur voor als een boom van objecten, waarbij elk knooppunt overeenkomt met een HTML-element. JavaScript en webframeworks gebruiken de DOM om de inhoud, structuur en opmaak van webpagina's dynamisch te benaderen en aan te passen.

## Shadow DOM {#shadow-dom}

Shadow DOM is een webstandaard waarmee de DOM (Document Object Model) en CSS binnen een specifiek element, de shadow tree genoemd, kunnen worden ingekapseld. Deze geïsoleerde DOM en CSS staan los van de DOM van het hoofddocument, waardoor er effectief een afgebakende scope voor de component ontstaat. Shadow DOM helpt bij het maken van zelfstandige, herbruikbare webcomponenten die aan een webpagina kunnen worden toegevoegd zonder dat u zich zorgen hoeft te maken over conflicten met andere stijlen en scripts op de pagina.

Het introduceert ook functies zoals shadow parts en slots, waarmee ontwikkelaars bepaalde delen van de shadow tree beschikbaar kunnen maken voor aanpassing door de bovenliggende pagina. Zo kan inhoud op flexibele wijze aan de component worden doorgegeven en kan het uiterlijk ervan worden aangepast, terwijl de inkapseling behouden blijft.

## Componenten met een beperkte scope {#scoped-components}

Componenten waarvan de stijlen zijn geïsoleerd om conflicten met stijlen buiten de component te voorkomen. Deze isolatie wordt bereikt door unieke data-attributen op de stijlen van de component toe te passen, in plaats van te vertrouwen op de native shadow DOM.
