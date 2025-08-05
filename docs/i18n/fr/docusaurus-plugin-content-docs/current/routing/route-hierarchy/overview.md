---
sidebar_position: 1
title: Route Hierarchy
hide_giscus_comments: true
_i18n_hash: a384d1b849730a301f5bc1d0a20e9c41
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

# Hiérarchie des itinéraires

Les itinéraires sont organisés dans une structure d'arbre hiérarchique qui permet aux développeurs de définir des mises en page, de gérer des vues et de rendre dynamiquement des composants à travers diverses parties de l'application.

Les concepts clés que vous rencontrerez lors de la création d'une application routable webforJ incluent :

- **Hiérarchie des itinéraires** : Organise les itinéraires en structures parent-enfant pour le développement d'interface utilisateur modulaire.
- **Types d'itinéraires** : Les itinéraires sont catégorisés comme **Itinéraires de vue** ou **Itinéraires de mise en page**, chacun ayant un objectif différent.
- **Itinéraires imbriqués** : Les itinéraires peuvent être imbriqués les uns dans les autres, permettant aux composants parents de rendre les composants enfants dans des emplacements désignés.
- **Emplacements** : Composants où les vues enfants sont injectées dynamiquement dans les mises en page parents.
- **Mises en page** : Itinéraires spéciaux qui englobent les composants enfants sans contribuer à l'URL, fournissant des éléments d'interface utilisateur partagés tels que des en-têtes, des pieds de page ou des barres latérales.

## Topics {#topics}

<DocCardList className="topics-section" />
