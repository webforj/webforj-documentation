---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 9431d33c6fea2dd9d4ff4b165877e7d5
---
## Défilement virtuel {#virtual-scrolling}

Le composant `Table` est conçu pour gérer efficacement de grands ensembles de données en utilisant le défilement virtuel, qui est une technique utilisée dans les applications web pour optimiser le rendu et les performances de grandes listes ou tableaux en ne rendant que les éléments visibles à l'écran.

### Rendu initial {#initial-render}

Le défilement virtuel est un modèle de conception dans lequel, au départ, seule une petite partie des éléments qui s'insèrent dans la zone visible du conteneur défilant est rendue. Cela minimise le nombre d'éléments DOM créés et accélère le processus de rendu initial.

### Chargement dynamique {#dynamic-loading}
Au fur et à mesure que l'utilisateur défile vers le bas ou vers le haut, de nouveaux éléments sont dynamiquement chargés dans la vue. Ces éléments sont généralement récupérés depuis la source de données en fonction de la position de défilement actuelle.

### Récupération d'éléments {#item-recycling}
Au lieu de créer un nouvel élément DOM pour chaque élément, le défilement virtuel réutilise souvent les éléments DOM existants. Lorsqu'un élément sort de la zone visible, son élément DOM est recyclé et réutilisé pour un nouvel élément entrant dans la zone visible. Ce processus de recyclage contribue à réduire l'utilisation de la mémoire et améliore les performances.

### Avantages en termes de performances : {#performance-benefits}

L'avantage principal du défilement virtuel est l'amélioration des performances, en particulier lors du traitement d'un grand nombre d'éléments. Il réduit la quantité de manipulation du DOM et améliore la réactivité globale de l'interface utilisateur.

Le tableau ci-dessous `Table` montre tous les gagnants olympiques - un grand ensemble de données qui bénéficie grandement de la fonctionnalité de défilement virtuel du tableau :

<ComponentDemo
path='/webforj/tableolympicwinners'
files={[
  'src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Overscan {#overscan}

Définir la propriété `Overscan` du tableau détermine combien de lignes rendre en dehors de la zone visible. Ce paramètre peut être configuré avec la méthode `setOverscan(double value)`.

Une valeur d'overscan plus élevée peut réduire la fréquence de rendu lors du défilement, mais au prix de rendre plus de lignes que celles qui sont visibles à un moment donné. Cela peut être un compromis entre les performances de rendu et la fluidité du défilement.
