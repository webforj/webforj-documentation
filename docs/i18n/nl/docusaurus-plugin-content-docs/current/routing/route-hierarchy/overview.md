---
sidebar_position: 1
title: Routehiërarchie
hide_giscus_comments: true
description: >-
  Organiseer webforJ-routes in ouder-kindstructuren met weergaveroutes,
  lay-outroutes, outlets en geneste componenten.
_i18n_title_hash: 9efa0a285cc1eb30cf7a63d79dc97170
_i18n_description_hash: 7382223dd1bfaa572d36732205d62653
_i18n_hash: 4bfc9c9d46d57c866c67a2baaf2e3c3a
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Route hiërarchie

Routes zijn georganiseerd in een hiërarchische boomstructuur die ontwikkelaars in staat stelt om lay-outs te definiëren, weergaven te beheren en componenten dynamisch weer te geven in verschillende delen van de app.

De belangrijkste concepten die je tegenkomt bij het bouwen van een routable app voor webforJ zijn onder andere:

- **Routehiërarchie**: Organiseert routes in ouder-kind structuren voor modulair UI-ontwikkeling.
- **Route Types**: Routes worden gecategoriseerd als **View Routes** of **Layout Routes**, waarbij elke een ander doel dient.
- **Geneste Routes**: Routes kunnen binnen elkaar genest zijn, waardoor oudercomponenten kindcomponenten kunnen weergeven in aangewezen uitgangen.
- **Uitgangen**: Componenten waar kindweergaven dynamisch worden geïnjecteerd in ouderlay-outs.
- **Lay-outs**: Speciale routes die kindcomponenten omhullen zonder bij te dragen aan de URL, en gedeelde UI-elementen bieden zoals headers, footers of zijbalken.

## Topics {#topics}

<DocCardList className="topics-section" />
