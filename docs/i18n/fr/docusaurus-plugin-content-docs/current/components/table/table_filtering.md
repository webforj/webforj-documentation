---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: e35c9b340f9faa796a4dbf5635f59495
---
Le composant `Table` vous permet de mettre en œuvre un filtrage pour affiner les données affichées en fonction de critères spécifiques. Le filtrage peut être réalisé en définissant un critère de filtrage à l'aide de la méthode `setFilter(Predicate<T> filter)` fournie par le `Repository` associé à la table.

L'exemple suivant utilise un critère défini par l'utilisateur à partir du champ de recherche et la méthode `setBaseFilter()` pour appliquer un filtre au `CollectionRepository` en fonction des titres des `MusicRecord`. Lorsque la méthode `commit()` est déclenchée, la table se rafraîchit avec les données filtrées.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablefiltering'
files={[
  'src/main/java/com/webforj/samples/views/table/TableFilteringView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>
<!-- vale on -->

:::note
La méthode `setBaseFilter()` appartient à la classe `CollectionRepository`, et non au composant `Table`.
:::
