---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: 110061605b615701c1832988833fe959
---
Le composant `Table` vous permet de mettre en œuvre une fonctionnalité de filtrage pour affiner les données affichées en fonction de critères spécifiques. Le filtrage peut être réalisé en définissant un critère de filtrage à l'aide de la méthode `setFilter(Predicate<T> filter)` fournie par le `Repository` associé à la table.

<ComponentDemo
path='/webforj/tablefiltering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Dans l'exemple ci-dessus, la méthode `setFilter()` est utilisée pour définir un critère de filtrage basé sur le titre de `MusicRecord`. 

:::tip
La méthode `setFilter()` appartient à la classe `Repository`, et n'est pas un comportement intégré de la `Table` elle-même.
:::

Le filtre est ensuite appliqué lorsque l'utilisateur modifie le contenu du champ de recherche, mettant à jour le searchTerm et déclenchant la méthode `commit()` pour rafraîchir les données affichées.
