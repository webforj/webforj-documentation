---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 2c6b6a04c5c33d9e3f1663d15c85a2e9
---
Sorting ermöglicht es Benutzern, Daten in Spalten nach Reihenfolge anzuordnen, wodurch Informationen leichter zu lesen und zu analysieren sind. Dies ist nützlich, wenn Benutzer schnell die höchsten oder niedrigsten Werte in einer bestimmten Spalte finden müssen.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Standardmäßig ist eine Spalte nicht sortierbar, es sei denn, sie wird explizit aktiviert. Um das Sortieren in einer bestimmten Spalte zu erlauben, verwenden Sie die Methode `setSortable(true)`:

```java 
table.getColumn("Age").setSortable(true);
```

## Mehrfachsortierung {#multi-sorting}

:::warning Mehrspaltensortierung standardmäßig in webforJ `25.00` deaktiviert
Vor webforj `25.00` unterstützten Tabellen standardmäßig die Mehrspaltensortierung. Ab Version `25.00` wurde dieses Verhalten geändert—Entwickler müssen nun die Mehrspaltensortierung explizit aktivieren.
:::

Wenn eine Mehrfachsortierung erforderlich ist, muss `setMultiSorting(true)` auf die Tabelle angewendet werden. Dies ermöglicht es Benutzern, mehrere Spalten in Folge zu sortieren:

```java
table.setMultiSorting(true);
```

Bei aktivierter Mehrfachsortierung wird beim Klicken auf mehrere Spaltenüberschriften diese nacheinander sortiert. Die Sortierpriorität wird visuell in der Tabellenbenutzeroberfläche angezeigt.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Sie können auch die Sortierpriorität programmgesteuert für die serverseitige Sortierung definieren. Verwenden Sie `setSortOrder()` für die Spalten, die Sie in der priorisierten Reihenfolge sortieren möchten:

```java
// Server-seitige Sortierreihenfolge
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Spaltenreihenfolge ist wichtig
Es sei denn, `setSortOrder()` wird verwendet, sortiert die Tabelle standardmäßig in der Reihenfolge, in der die Spalten deklariert sind.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Sortierreihenfolge {#sort-direction}

Es gibt drei verfügbare Einstellungen für die Richtung, in die eine Spalte sortiert werden kann:

- `SortDirection.ASC`: Sortiert die Spalte in aufsteigender Reihenfolge.
- `SortDirection.DESC`: Sortiert die Spalte in absteigender Reihenfolge.
- `SortDirection.NONE`: Keine Sortierung auf die Spalte angewendet.

Wenn eine Spalte das Sortieren aktiviert hat, sehen Sie eine Reihe von vertikalen Pfeilindikatoren oben in der jeweiligen Spalte. Diese Pfeile ermöglichen es dem Benutzer, zwischen den verschiedenen Sortierrichtungen umzuschalten.

Wenn die aufsteigende Reihenfolge ausgewählt ist, wird ein `^` angezeigt, während die absteigende Reihenfolge ein `v` anzeigt.


## Client- vs. serverseitige Sortierung {#client-vs-server-side-sorting}

Die Sortierung von Daten lässt sich grob in zwei Hauptansätze unterteilen: **Client-Sortierung** und **Server-Sortierung**.

### Client-Sortierung {#client-sorting}

Die Client-Sortierung beinhaltet das Anordnen und Anzeigen von Daten direkt innerhalb der Benutzeroberfläche der Client-Anwendung. Es handelt sich um die Sortierung, mit der die Benutzer interagieren, wenn sie auf Spaltenüberschriften klicken, was die visuelle Darstellung der Daten auf dem Bildschirm beeinflusst.

Der Entwickler hat keine direkte Kontrolle über die clientseitige Sortierung, sondern diese wird durch den in Java bereitgestellten Spaltentyp bestimmt. Die folgenden Typen werden derzeit unterstützt:

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

Im Gegensatz zur clientseitigen Sortierung bedeutet die Server-Sortierung, dass Daten auf dem Server angeordnet und organisiert werden, bevor sie an den Client übertragen werden. Dieser Ansatz ist besonders vorteilhaft, wenn mit großen Datensätzen gearbeitet wird, die möglicherweise nicht vollständig an den Client übertragen werden können.

Entwickler haben mehr Kontrolle über die Logik der Server-Sortierung. Dies ermöglicht die Implementierung komplexer Sortieralgorithmen und -optimierungen, was es für Szenarien mit umfangreichen Daten geeignet macht. Dadurch wird sichergestellt, dass der Client vordefinierte sortierte Daten erhält, was den Bedarf an umfangreicher clientseitiger Verarbeitung minimiert.

:::info
Die Server-Sortierung ist eine leistungsorientierte Strategie zur Verarbeitung von Datensätzen, die die Fähigkeiten der effizienten clientseitigen Verarbeitung übersteigen, und ist die Standardmethode, die von der `Table` verwendet wird.
:::

#### Comparatoren {#comparators}

Die `Column`-Komponente ermöglicht es Entwicklern, Java `Comparators` für dynamische und benutzerdefinierte Sortierung zu verwenden. Ein `Comparator` ist ein Mechanismus zum Ordnen zweier Objekte derselben Klasse, auch wenn diese Klasse benutzerdefiniert ist. Diese Funktionalität bietet Entwicklern die Flexibilität, wie Daten sortiert werden, und gewährt größere Kontrolle über das standardmäßige Sortierverhalten basierend auf der natürlichen Reihenfolge.

Um das `Comparator`-Sorting in einer `Column` zu nutzen, können Sie die Methode `setComparator()` verwenden. Mit dieser Methode können Sie eine benutzerdefinierte `Comparator`-Funktion definieren, die die Sortierlogik bestimmt.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Im obigen Beispiel wird eine benutzerdefinierte Comparator-Funktion spezifiziert, die zwei Elemente (a und b) nimmt und die Sortierreihenfolge basierend auf den analysierten Ganzzahlwerten des `Number`-Attributs definiert.

Die Verwendung von Comparators für die Spaltensortierung ist besonders nützlich beim Umgang mit nicht-numerischen Werten. Sie sind auch nützlich für die Implementierung komplexer Sortieralgorithmen.

:::info
Standardmäßig verwendet die `Table` die serverseitige Sortierung und sortiert nicht-primitives Werte mithilfe der `toString()`-Methode des Objekts, indem sie in ihre Stringwerte konvertiert und dann sortiert werden.
:::
