---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: a384d1b849730a301f5bc1d0a20e9c41
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Route hiërarchie

Routes zijn georganiseerd in een hiërarchische boomstructuur die ontwikkelaars in staat stelt om lay-outs te definiëren, weergaven te beheren en componenten dynamisch weer te geven in verschillende delen van de app.

De belangrijkste concepten die je tegenkomt bij het bouwen van een routable webforJ-app zijn:

- **Route Hiërarchie**: Organiseert routes in ouder-kindstructuren voor modulaire UI-ontwikkeling.
- **Route Types**: Routes worden gecategoriseerd als **View Routes** of **Layout Routes**, die elk een ander doel dienen.
- **Geneste Routes**: Routes kunnen binnen elkaar genest zijn, waardoor oudercomponenten kindcomponenten kunnen weergeven in aangewezen uitgangen.
- **Uitgangen**: Componenten waar kindweergaven dynamisch in ouderlay-outs worden geïnjecteerd.
- **Lay-outs**: Speciale routes die kindcomponenten omhullen zonder bij te dragen aan de URL, en gedeelde UI-elementen bieden zoals headers, footers of zijbalken.

## Topics {#topics}

<DocCardList className="topics-section" />
