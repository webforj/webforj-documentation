---
sidebar_position: 35
title: Filtering
slug: filtering
sidebar_class_name: updated-content
_i18n_hash: 008eef50f8ab27ec3f8a455fb5649f41
---
Die `Table`-Komponente ermöglicht es Ihnen, eine Filterfunktion zu implementieren, um die angezeigten Daten basierend auf bestimmten Kriterien einzuschränken. Filtern kann erreicht werden, indem ein Filterkriterium mit der Methode `setFilter(Predicate<T> filter)` definiert wird, die von dem mit der Tabelle verbundenen `Repository` bereitgestellt wird.

Das folgende Beispiel verwendet ein benutzerdefiniertes Kriterium aus dem Suchfeld und die Methode `setBaseFilter()`, um einen Filter für das `CollectionRepository` basierend auf den Titeln von `MusicRecord` anzuwenden. Wenn die Methode `commit()` ausgelöst wird, wird die Tabelle mit den gefilterten Daten aktualisiert.

<!-- vale off -->
<ComponentDemo
path='/webforj/tablefiltering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
<!-- vale on -->

:::note
Die Methode `setBaseFilter()` gehört zur Klasse `CollectionRepository`, nicht zur `Table`-Komponente.
:::
