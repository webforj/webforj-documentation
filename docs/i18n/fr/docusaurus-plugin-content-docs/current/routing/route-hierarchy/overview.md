---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: 66716282278634ab574f3620a2a660ce
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Hiérarchie des routes

Les routes sont organisées en une structure d'arbre hiérarchique qui permet aux développeurs de définir des mises en page, de gérer des vues et de rendre dynamiquement des composants à travers diverses parties de l'application. 

Les concepts clés que vous rencontrerez lors de la création d'une application routable webforJ incluent :

- **Hiérarchie des routes** : Organise les routes en structures parent-enfant pour le développement d'UI modulaire.
- **Types de routes** : Les routes sont classées en tant que **routes de vue** ou **routes de mise en page**, chacune ayant un objectif différent.
- **Routes imbriquées** : Les routes peuvent être imbriquées les unes dans les autres, permettant aux composants parent de rendre des composants enfants dans des sorties désignées.
- **Sorties** : Composants où des vues enfants sont injectées dynamiquement dans des mises en page parent.
- **Mises en page** : Routes spéciales qui enveloppent des composants enfants sans contribuer à l'URL, fournissant des éléments d'UI partagés tels que des en-têtes, des pieds de page ou des barres latérales.

## Topics {#topics}

<DocCardList className="topics-section" />
