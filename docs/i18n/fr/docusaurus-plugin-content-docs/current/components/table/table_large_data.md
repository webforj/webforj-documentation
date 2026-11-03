---
sidebar_position: 25
title: Grands ensembles de données
slug: data
description: >-
  Handle large Table datasets efficiently with virtual scrolling, dynamic row
  loading, DOM recycling, and overscan tuning.
_i18n_hash: 63e45952d3d3f88d3aa435435c0905e6
---
## Défilement virtuel {#virtual-scrolling}

Le composant `Table` est conçu pour gérer efficacement de grands ensembles de données grâce au défilement virtuel. Cette technique, utilisée dans les applications web, optimise le rendu et les performances des grandes listes ou tables en ne rendant que les éléments visibles à l’écran.

### Rendu initial {#initial-render}

Le défilement virtuel est un modèle de conception dans lequel seul un petit sous-ensemble d’éléments, correspondant à la zone visible du conteneur défilant, est rendu au départ. Cela réduit le nombre d’éléments DOM créés et accélère le rendu initial.

### Chargement dynamique {#dynamic-loading}
Lorsque l’utilisateur fait défiler la page vers le haut ou vers le bas, de nouveaux éléments sont chargés dynamiquement dans la vue. Ils sont généralement récupérés depuis la source de données en fonction de la position de défilement actuelle.

### Réutilisation des éléments {#item-recycling}
Au lieu de créer un nouvel élément DOM pour chaque élément, le défilement virtuel réutilise souvent les éléments DOM existants. Lorsqu’un élément sort de la zone visible, son élément DOM est recyclé et réaffecté à un nouvel élément qui entre dans la zone visible. Ce processus de recyclage réduit la mémoire utilisée et améliore les performances.

### Avantages en termes de performances : {#performance-benefits}

Le principal avantage du défilement virtuel est l’amélioration des performances, en particulier lorsqu’il faut gérer un grand nombre d’éléments. Il réduit les manipulations du DOM et améliore la réactivité générale de l’interface utilisateur.

Le composant `Table` ci-dessous affiche tous les vainqueurs olympiques — un vaste ensemble de données qui tire grandement parti de la fonctionnalité de défilement virtuel de la table :

<ComponentDemo
path='/webforj/tableolympicwinners'
files={['src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java']}
height='600px'
/>

## Surbalayage {#overscan}

La propriété `Overscan` de la table détermine le nombre de lignes à rendre en dehors de la zone visible. Elle peut être configurée avec la méthode `setOverscan(double value)`.

Une valeur de surbalayage plus élevée peut réduire la fréquence des rendus pendant le défilement, au prix du rendu d’un plus grand nombre de lignes que celles visibles à un instant donné. Il s’agit d’un compromis entre les performances de rendu et la fluidité du défilement.
