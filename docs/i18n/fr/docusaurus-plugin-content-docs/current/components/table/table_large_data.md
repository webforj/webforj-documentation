---
sidebar_position: 25
title: Grandes ensembles de données
slug: data
_i18n_hash: a8c510d518375e324ae1f1f0c95b5004
---
## Défilement virtuel {#virtual-scrolling}

Le composant `Table` est conçu pour gérer efficacement de grands ensembles de données en utilisant le défilement virtuel, qui est une technique utilisée dans les applications web pour optimiser le rendu et les performances de grandes listes ou tables en ne rendant que les éléments visibles à l'écran.

### Rendu initial {#initial-render}

Le défilement virtuel est un modèle de conception dans lequel, au début, seule une petite sous-partie des éléments qui s'intègrent dans la zone visible du conteneur défilable est rendue. Cela minimise le nombre d'éléments DOM créés et accélère le processus de rendu initial.

### Chargement dynamique {#dynamic-loading}
Au fur et à mesure que l'utilisateur fait défiler vers le bas ou vers le haut, de nouveaux éléments sont chargés dynamiquement dans la vue. Ces éléments sont généralement récupérés à partir de la source de données en fonction de la position actuelle du défilement.

### Recyclage d'éléments {#item-recycling}
Au lieu de créer un nouvel élément DOM pour chaque élément, le défilement virtuel utilise souvent des éléments DOM existants. À mesure qu'un élément sort de la zone visible, son élément DOM est recyclé et réutilisé pour un nouvel élément entrant dans la zone visible. Ce processus de recyclage aide à réduire l'utilisation de la mémoire et améliore les performances.

### Avantages en termes de performance : {#performance-benefits}

Le principal avantage du défilement virtuel est l'amélioration des performances, notamment lors de la gestion d'un grand nombre d'éléments. Cela réduit la quantité de manipulation DOM et améliore la réactivité globale de l'interface utilisateur.

Le tableau ci-dessous montre tous les gagnants olympiques - un grand ensemble de données qui bénéficie grandement de la fonctionnalité de défilement virtuel du tableau :

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Overscan {#overscan}

Définir la propriété `Overscan` du tableau détermine combien de lignes rendre en dehors de la zone visible. Ce paramètre peut être configuré avec la méthode `setOverscan(double value)`.

Une valeur d'overscan plus élevée peut réduire la fréquence de rendu lors du défilement, mais au prix de rendre plus de lignes que celles visibles à un moment donné. Cela peut être un compromis entre la performance de rendu et la fluidité du défilement.
