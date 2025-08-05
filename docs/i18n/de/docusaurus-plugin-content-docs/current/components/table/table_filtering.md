---
sidebar_position: 35
title: Filtering
slug: filtering
_i18n_hash: 110061605b615701c1832988833fe959
---
Die `Table`-Komponente ermöglicht es Ihnen, eine Filterfunktionalität zu implementieren, um die angezeigten Daten basierend auf bestimmten Kriterien einzugrenzen. Die Filterung kann erreicht werden, indem Sie ein Filterkriterium mit der Methode `setFilter(Predicate<T> filter)` definieren, die von dem mit der Tabelle verbundenen `Repository` bereitgestellt wird.

<ComponentDemo
path='/webforj/tablefiltering?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableFilteringView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Im obigen Beispiel wird die Methode `setFilter()` verwendet, um ein Filterkriterium basierend auf dem Titel von `MusicRecord` zu definieren.

:::tip
Die Methode `setFilter()` gehört zur Klasse `Repository` und ist kein integriertes Verhalten der `Table` selbst.
:::

Der Filter wird dann angewendet, wenn der Benutzer den Inhalt des Suchfelds ändert, wodurch der searchTerm aktualisiert und die Methode `commit()` ausgelöst wird, um die angezeigten Daten zu aktualisieren.
