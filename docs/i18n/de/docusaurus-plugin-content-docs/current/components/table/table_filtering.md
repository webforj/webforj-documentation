---
sidebar_position: 35
title: Filterung
slug: filtering
description: >-
  Filtern Sie Tabellenzeilen, indem Sie über das gebundene Repository ein
  Predicate anwenden und die Ergebnisse beim Commit aktualisieren.
_i18n_title_hash: 9a588db8471730dbfebac65cd5467ad8
_i18n_description_hash: 16cf6eedec1585c3016bf61678214883
_i18n_hash: 4e0709a55b763f553eeb8ddb8a3abb32
---
Die `Table`-Komponente ermöglicht es Ihnen, Filterungen zu implementieren, um die angezeigten Daten basierend auf spezifischen Kriterien zu verfeinern. Filterung kann erreicht werden, indem ein Filterkriterium mit der Methode `setFilter(Predicate<T> filter)` definiert wird, die von dem mit der Tabelle verbundenen `Repository` bereitgestellt wird.

Im folgenden Beispiel wird ein benutzerdefiniertes Kriterium aus dem Suchfeld und die Methode `setBaseFilter()` verwendet, um einen Filter auf das `CollectionRepository` basierend auf den Titeln von `MusicRecord` anzuwenden. Wenn die Methode `commit()` ausgelöst wird, aktualisiert sich die Tabelle mit den gefilterten Daten.

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
Die Methode `setBaseFilter()` gehört zur Klasse `CollectionRepository`, nicht zur `Table`-Komponente.
:::(note)
