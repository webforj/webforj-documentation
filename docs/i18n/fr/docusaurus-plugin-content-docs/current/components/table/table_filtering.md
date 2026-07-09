---
sidebar_position: 35
title: Filtering
slug: filtering
description: >-
  Narrow Table rows by applying a Predicate through the bound Repository,
  refreshing results on commit.
_i18n_hash: 4e0709a55b763f553eeb8ddb8a3abb32
---
Le composant `Table` vous permet d'implémenter un filtrage pour réduire les données affichées en fonction de critères spécifiques. Le filtrage peut être réalisé en définissant un critère de filtrage à l'aide de la méthode `setFilter(Predicate<T> filter)` fournie par le `Repository` associé à la table.

L'exemple suivant utilise un critère défini par l'utilisateur à partir du champ de recherche et la méthode `setBaseFilter()` pour appliquer un filtre au `CollectionRepository` en fonction des titres de `MusicRecord`. Lorsque la méthode `commit()` est déclenchée, la table se rafraîchit avec les données filtrées.

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
La méthode `setBaseFilter()` appartient à la classe `CollectionRepository`, pas au composant `Table`.
:::
