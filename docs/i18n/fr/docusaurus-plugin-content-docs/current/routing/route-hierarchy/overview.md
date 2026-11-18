---
sidebar_position: 1
title: Hiérarchie des routes
hide_giscus_comments: true
description: >-
  Organisez les routes webforJ en arborescences parent-enfant avec des routes de
  vue, des routes de mise en page, des points d’insertion et des composants
  imbriqués.
_i18n_title_hash: 9efa0a285cc1eb30cf7a63d79dc97170
_i18n_description_hash: 7382223dd1bfaa572d36732205d62653
_i18n_hash: 4bfc9c9d46d57c866c67a2baaf2e3c3a
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Hiérarchie des routes

Les routes sont organisées dans une structure d'arbre hiérarchique qui permet aux développeurs de définir des mises en page, de gérer des vues et de rendre dynamiquement des composants dans différentes parties de l'application.

Les concepts clés que vous rencontrerez lors de la création d'une application routable webforJ incluent :

- **Hiérarchie des routes** : Organise les routes en structures parent-enfant pour un développement UI modulaire.
- **Types de routes** : Les routes sont classées en tant que **Routes de vue** ou **Routes de mise en page**, chacune servant un but différent.
- **Routes imbriquées** : Les routes peuvent être imbriquées les unes dans les autres, permettant aux composants parents de rendre des composants enfants dans des sorties désignées.
- **Sorties** : Composants où les vues enfants sont injectées dynamiquement dans les mises en page parents.
- **Mises en page** : Routes spéciales qui enveloppent des composants enfants sans contribuer à l'URL, fournissant des éléments UI partagés tels que des en-têtes, des pieds de page ou des barres latérales.

## Topics {#topics}

<DocCardList className="topics-section" />
