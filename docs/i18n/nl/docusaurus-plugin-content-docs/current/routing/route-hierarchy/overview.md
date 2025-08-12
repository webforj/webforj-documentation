---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: 66716282278634ab574f3620a2a660ce
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Route-hiërarchie

Routen zijn georganiseerd in een hiërarchische boomstructuur die ontwikkelaars in staat stelt om lay-outs te definiëren, weergaven te beheren en componenten dynamisch te renderen in verschillende delen van de app.

De belangrijkste concepten die je tegenkomt bij het bouwen van een webforJ routable-app zijn onder andere:

- **Routehiërarchie**: Organiseert routes in ouder-kindstructuren voor modulaire UI-ontwikkeling.
- **Route Types**: Routen worden geclassificeerd als **Bekijkroutes** of **Layoutroutes**, die elk een ander doel dienen.
- **Geneste Routen**: Routes kunnen binnen elkaar genest zijn, waardoor oudercomponenten kindcomponenten kunnen renderen in aangewezen uitgangen.
- **Uitgangen**: Componenten waar kindweergaven dynamisch in ouderlay-outs worden geïnjecteerd.
- **Lay-outs**: Speciale routes die kindcomponenten omhullen zonder bij te dragen aan de URL, en gedeelde UI-elementen zoals koptekst, voettekst of zijkanten bieden.

## Onderwerpen {#topics}

<DocCardList className="topics-section" />
