---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 3c9156ad5da204816bd4ce783003cbf7
---
Sorting ermöglicht es Benutzern, Daten in Spalten nach Reihenfolge anzuordnen, wodurch Informationen leichter zu lesen und zu analysieren sind. Dies ist nützlich, wenn Benutzer schnell die höchsten oder niedrigsten Werte in einer bestimmten Spalte finden müssen.

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Standardmäßig ist eine Spalte nicht sortierbar, es sei denn, sie ist ausdrücklich aktiviert. Um das Sortieren in einer bestimmten Spalte zu ermöglichen, verwenden Sie die Methode `setSortable(true)`:

```java 
table.getColumn("Age").setSortable(true);
```

## Mehrfachsortierung {#multi-sorting}

:::warning Mehrspaltensortierung standardmäßig in webforJ `25.00` deaktiviert
Vor webforj `25.00` unterstützten Tabellen standardmäßig die Mehrspaltensortierung. Ab Version `25.00` hat sich dieses Verhalten geändert - Entwickler müssen die Mehrspaltensortierung nun ausdrücklich aktivieren.
:::

Wenn Mehrfachsortierung erforderlich ist, muss `setMultiSorting(true)` auf die Tabelle angewendet werden. Dies ermöglicht es Benutzern, mehrere Spalten in der richtigen Reihenfolge zu sortieren:

```java
table.setMultiSorting(true);
```

Mit aktivierter Mehrfachsortierung wird das Klicken auf mehrere Spaltenüberschriften sie der Reihe nach sortieren. Die Sortierpriorität wird visuell in der Tabellenbenutzeroberfläche angezeigt.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Sie können auch die Sortierpriorität programmatisch für die serverseitige Sortierung definieren. Verwenden Sie `setSortOrder()` für die Spalten, die Sie reklamieren möchten, in der gewünschten Reihenfolge:

```java
// Serverseitige Sortierreihenfolge
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Die Spaltenreihenfolge ist wichtig
Sofern `setSortOrder()` nicht verwendet wird, sortiert die Tabelle standardmäßig in der Reihenfolge, in der die Spalten deklariert sind.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Sortierreihenfolge {#sort-direction}

Es gibt drei verfügbare Einstellungen für die Richtung, in der eine Spalte sortiert werden kann:

- `SortDirection.ASC`: Sortiert die Spalte aufsteigend.
- `SortDirection.DESC`: Sortiert die Spalte absteigend.
- `SortDirection.NONE`: Es wird keine Sortierung auf die Spalte angewendet.

Wenn eine Spalte sortierbar ist, sehen Sie eine Reihe von vertikalen Pfeilsymbolen oben in der betreffenden Spalte. Diese Pfeile ermöglichen dem Benutzer, zwischen den verschiedenen Sortierrichtungen umzuschalten.

Wenn die aufsteigende Reihenfolge ausgewählt ist, wird ein `^` angezeigt, während die absteigende Reihenfolge ein `v` anzeigt.

## Client- vs. serverseitige Sortierung {#client-vs-server-side-sorting}

Die Sortierung von Daten kann grob in zwei Hauptansätze unterteilt werden: **Client-Sortierung** und **Server-Sortierung**.

### Client-Sortierung {#client-sorting}

Die Client-Sortierung umfasst das Anordnen und Anzeigen von Daten direkt innerhalb der Benutzeroberfläche der Client-Anwendung. Es ist die Sortierung, mit der Benutzer interagieren, wenn sie auf die Spaltenüberschriften klicken, und sie beeinflusst die visuelle Darstellung der Daten auf dem Bildschirm.

Der Entwickler hat keine direkte Kontrolle über die Client-seitige Sortierung, da diese durch den in Java bereitgestellten Spaltentyp bestimmt wird. Die folgenden Typen werden derzeit unterstützt:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Die Client-Sortierung funktioniert nicht, wenn nur ein Teil der Daten im Client verfügbar ist.
:::

### Server-Sortierung {#server-sorting}

Im Gegensatz zur Client-seitigen Sortierung umfasst die Server-Sortierung das Anordnen und Organisieren von Daten auf dem Server, bevor diese an den Client übertragen werden. Dieser Ansatz ist besonders vorteilhaft beim Umgang mit großen Datensätzen, die möglicherweise nicht vollständig an den Client übertragen werden können.

Entwickler haben mehr Kontrolle über die Logik der Server-Sortierung. Dies ermöglicht die Implementierung komplexer Sortieralgorithmen und Optimierungen, die für Szenarien mit umfangreichen Daten geeignet sind. Damit wird sichergestellt, dass der Client vorgesortierte Daten erhält, was den Bedarf an umfangreicher Client-seitiger Verarbeitung minimiert.

:::info
Die Server-Sortierung ist eine leistungsorientierte Strategie für den Umgang mit Datensätzen, die die Fähigkeiten einer effizienten Client-seitigen Verarbeitung überschreiten, und ist die standardmäßige Methode, die von der `Table` verwendet wird.
:::

#### Comparatoren {#comparators}

Die `Column`-Komponente ermöglicht es Entwicklern, Java `Comparators` für dynamische und benutzerdefinierte Sortierungen zu verwenden. Ein `Comparator` ist ein Mechanismus, der verwendet wird, um zwei Objekte derselben Klasse zu ordnen, auch wenn diese Klasse benutzerdefiniert ist. Diese Funktionalität bietet Entwicklern die Flexibilität, wie Daten sortiert werden, und ermöglicht eine höhere Kontrolle über das standardmäßige Sortierverhalten basierend auf der natürlichen Reihenfolge.

Um die `Comparator`-Sortierung in einer `Column` zu nutzen, können Sie die Methode `setComparator()` verwenden. Diese Methode ermöglicht es Ihnen, eine benutzerdefinierte `Comparator`-Funktion zu definieren, die die Sortierlogik festlegt.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Im obigen Beispiel wird eine benutzerdefinierte Comparatorfunktion angegeben, die zwei Elemente (a und b) entgegennimmt und die Sortierreihenfolge basierend auf den geparsten Ganzzahlen des `Number`-Attributs festlegt.

Die Verwendung von Comparators für die Spaltensortierung ist besonders nützlich, wenn Sie mit nicht-numerischen Werten umgehen. Sie sind auch nützlich für die Implementierung komplexer Sortieralgorithmen.

:::info
Standardmäßig verwendet die `Table` die serverseitige Sortierung und sortiert nicht-primitivwerte mit der `toString()`-Methode von Object, wodurch sie in ihre String-Werte umgewandelt und dann sortiert werden.
:::
