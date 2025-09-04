---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: a51ea10e855e94a24cb6e74d8f774abe
---
Sortierung ermöglicht es den Benutzern, Daten in Spalten nach Reihenfolge anzuordnen, was Informationen leichter lesbar und analysierbar macht. Dies ist nützlich, wenn Benutzer schnell die höchsten oder niedrigsten Werte in einer bestimmten Spalte finden müssen.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Standardmäßig ist eine Spalte nicht sortierbar, es sei denn, sie wird explizit aktiviert. Um die Sortierung in einer bestimmten Spalte zu ermöglichen, verwenden Sie die Methode `setSortable(true)`:

```java 
table.getColumn("Alter").setSortable(true);
```

## Mehrfachsortierung {#multi-sorting}

:::warning Mehrspaltensortierung standardmäßig in webforJ `25.00` deaktiviert
Vor webforj `25.00` unterstützten Tabellen standardmäßig die Mehrspaltensortierung. Ab Version `25.00` wurde dieses Verhalten geändert - Entwickler müssen jetzt die Mehrspaltensortierung ausdrücklich aktivieren.
:::

Wenn Mehrfachsortierungen erforderlich sind, muss `setMultiSorting(true)` auf die Tabelle angewendet werden. Dadurch können Benutzer mehrere Spalten nacheinander sortieren:

```java
table.setMultiSorting(true);
```

Mit aktivierter Mehrfachsortierung wird das Klicken auf mehrere Spaltenüberschriften in der Reihenfolge sortiert. Die Sortierpriorität wird visuell in der Tabellenbenutzeroberfläche angezeigt.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Sie können auch die Sortierpriorität programmgesteuert für die serverseitige Sortierung definieren. Verwenden Sie `setSortOrder()` für die Spalten, die Sie in der Reihenfolge der Priorität sortieren möchten:

```java
// Reihenfolge der serverseitigen Sortierung
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Die Reihenfolge der Spalten ist wichtig
Es sei denn, `setSortOrder()` wird verwendet, sortiert die Tabelle standardmäßig in der Reihenfolge, in der die Spalten deklariert sind.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Sortierrichtung {#sort-direction}

Es gibt drei verfügbare Einstellungen für die Richtung, in die eine Spalte sortiert werden kann:

- `SortDirection.ASC`: Sortiert die Spalte in aufsteigender Reihenfolge.
- `SortDirection.DESC`: Sortiert die Spalte in absteigender Reihenfolge.
- `SortDirection.NONE`: Keine Sortierung auf die Spalte angewendet.

Wenn eine Spalte die Sortierung aktiviert hat, sehen Sie eine Reihe von vertikalen Pfeilindikatoren an der Oberseite der gesetzten Spalte. Diese Pfeile ermöglichen es dem Benutzer, zwischen den verschiedenen Sortierrichtungen zu wechseln.

Wenn die aufsteigende Reihenfolge ausgewählt ist, wird ein `^` angezeigt, während die absteigende Reihenfolge ein `v` anzeigt.


## Client- vs. serverseitige Sortierung {#client-vs-server-side-sorting}

Die Sortierung von Daten kann grob in zwei Hauptansätze unterteilt werden: **Client-Sortierung** und **Server-Sortierung**.

### Client-Sortierung {#client-sorting}

Die Client-Sortierung umfasst das Anordnen und Anzeigen von Daten direkt in der Benutzeroberfläche der Client-Anwendung. Es ist die Sortierung, mit der Benutzer interagieren, wenn sie auf Spaltenüberschriften klicken, was die visuelle Darstellung der Daten auf dem Bildschirm beeinflusst.

Der Entwickler hat keine direkte Kontrolle über die clientseitige Sortierung, sondern ist durch den Spaltentyp bestimmt, der in Java bereitgestellt wird. Die folgenden Typen werden derzeit unterstützt:

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

Im Gegensatz zur clientseitigen Sortierung umfasst die Server-Sortierung das Anordnen und Organisieren von Daten auf dem Server, bevor sie an den Client übermittelt werden. Dieser Ansatz ist besonders vorteilhaft, wenn es sich um große Datensätze handelt, die möglicherweise nicht vollständig an den Client übertragen werden können.

Entwickler haben mehr Kontrolle über die Logik der Server-Sortierung. Dies ermöglicht die Implementierung komplexer Sortieralgorithmen und -optimierungen, die sie für Szenarien mit umfangreichen Daten geeignet machen. Dadurch wird sichergestellt, dass der Client vor-sortierte Daten erhält, was den Bedarf an umfangreicher clientseitiger Verarbeitung minimiert.

:::info
Die Server-Sortierung ist eine leistungsorientierte Strategie für den Umgang mit Datensätzen, die die Möglichkeiten einer effizienten clientseitigen Verarbeitung überschreiten, und ist die standardmäßige Methode, die von der `Table` verwendet wird.
:::

#### Comparatoren {#comparators}

Die `Column`-Komponente ermöglicht es Entwicklern, Java `Comparators` für dynamisches und benutzerdefiniertes Sortieren zu verwenden. Ein `Comparator` ist ein Mechanismus, der verwendet wird, um zwei Objekte der gleichen Klasse zu ordnen, selbst wenn diese Klasse benutzerdefiniert ist. Diese Funktionalität bietet Entwicklern die Flexibilität, anzupassen, wie Daten sortiert werden, und dabei mehr Kontrolle über das Standard-Sortierverhalten basierend auf natürlicher Reihenfolge zu haben.

Um `Comparator`-Sortierung in einer `Column` zu nutzen, können Sie die Methode `setComparator()` verwenden. Diese Methode ermöglicht es Ihnen, eine benutzerdefinierte `Comparator`-Funktion zu definieren, die die Sortierlogik bestimmt.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Im obigen Beispiel wird eine benutzerdefinierte Comparator-Funktion definiert, die zwei Elemente (a und b) nimmt und die Sortierreihenfolge basierend auf den geparsten Ganzzahlwerten des `Number`-Attributs definiert.

Die Verwendung von Comparators für die Spaltensortierung ist besonders nützlich, wenn nicht-numerische Werte behandelt werden. Sie sind auch nützlich, um komplexe Sortieralgorithmen zu implementieren.

:::info
Standardmäßig verwendet die `Table` die serverseitige Sortierung und sortiert nicht-primitive Werte mit der `toString()`-Methode von Object, konvertiert sie in ihre Zeichenfolgenwerte und sortiert sie dann.
:::
