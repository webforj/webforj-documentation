---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: e35c9b340f9faa796a4dbf5635f59495
---
Die `Table`-Komponente ermöglicht es Ihnen, Filter anzuwenden, um die angezeigten Daten basierend auf spezifischen Kriterien einzugrenzen. Filtering kann erreicht werden, indem ein Filterkriterium unter Verwendung der `setFilter(Predicate<T> filter)`-Methode definiert wird, die von dem mit der Tabelle verbundenen `Repository` bereitgestellt wird.

Das folgende Beispiel verwendet ein benutzerdefiniertes Kriterium aus dem Suchfeld und die `setBaseFilter()`-Methode, um einen Filter auf das `CollectionRepository` basierend auf den Titeln von `MusicRecord` anzuwenden. Wenn die `commit()`-Methode ausgelöst wird, wird die Tabelle mit den gefilterten Daten aktualisiert. 

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
Die `setBaseFilter()`-Methode gehört zur `CollectionRepository`-Klasse, nicht zur `Table`-Komponente.
:::
