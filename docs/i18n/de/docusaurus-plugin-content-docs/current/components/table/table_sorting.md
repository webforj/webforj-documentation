---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 16c35fa416c4ebba3b680deb2d8925ef
---
Das Sortieren ermöglicht es Benutzern, Daten in Spalten nach Reihenfolge anzuordnen, was das Lesen und Analysieren von Informationen erleichtert. Dies ist nützlich, wenn Benutzer schnell die höchsten oder niedrigsten Werte in einer bestimmten Spalte finden müssen.

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musterns zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

<ComponentDemo
path='/webforj/tablesorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Standardmäßig ist eine Spalte nicht sortierbar, es sei denn, sie wird ausdrücklich aktiviert. Um das Sortieren in einer bestimmten Spalte zu ermöglichen, verwenden Sie die Methode `setSortable(true)`:

```java 
table.getColumn("Alter").setSortable(true);
```

## Mehrfachsortierung {#multi-sorting}

:::warning Mehrspaltige Sortierung standardmäßig in webforJ `25.00` deaktiviert
Vor webforj `25.00` unterstützten Tabellen standardmäßig die Mehrspaltensortierung. Ab Version `25.00` hat sich dieses Verhalten geändert – Entwickler müssen die Mehrspaltensortierung jetzt ausdrücklich aktivieren.
:::

Falls eine Mehrfachsortierung erforderlich ist, muss `setMultiSorting(true)` auf die Tabelle angewendet werden. Dies ermöglicht es den Benutzern, mehrere Spalten nacheinander zu sortieren:

```java
table.setMultiSorting(true);
```

Mit aktivierter Mehrfachsortierung bewirkt ein Klick auf mehrere Spaltenüberschriften, dass diese nacheinander sortiert werden. Die Sortierreihenfolge wird visuell in der Tabellenbenutzeroberfläche angezeigt.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Sie können auch die Sortierreihenfolge programmgesteuert für die serverseitige Sortierung definieren. Verwenden Sie `setSortOrder()` für die Spalten, die Sie in der Reihenfolge der Priorität sortieren möchten:

```java
// Serverseitige Sortierreihenfolge
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Spaltenreihenfolge ist wichtig
Sofern `setSortOrder()` nicht verwendet wird, sortiert die Tabelle standardmäßig in der Reihenfolge, in der die Spalten deklariert sind.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Sortierrichtung {#sort-direction}

Es gibt drei verfügbare Einstellungen für die Richtung, in die eine Spalte sortiert werden kann:

- `SortDirection.ASC`: Sortiert die Spalte in aufsteigender Reihenfolge.
- `SortDirection.DESC`: Sortiert die Spalte in absteigender Reihenfolge.
- `SortDirection.NONE`: Keine Sortierung wird auf die Spalte angewendet.

Wenn eine Spalte das Sortieren aktiviert hat, sehen Sie eine Reihe von vertikalen Pfeilsymbolen oben in der betreffenden Spalte. Diese Pfeile ermöglichen es dem Benutzer, zwischen den verschiedenen Sortierdirektionen umzuschalten.

Wenn die aufsteigende Reihenfolge ausgewählt wird, wird ein `^` angezeigt, während die absteigende Reihenfolge ein `v` anzeigt.

## Client- vs. serverseitiges Sortieren {#client-vs-server-side-sorting}

Das Sortieren von Daten kann grob in zwei Hauptansätze kategorisiert werden: **Client-Sortierung** und **Server-Sortierung**.

### Client-Sortierung {#client-sorting}

Die Client-Sortierung umfasst das Anordnen und Anzeigen von Daten direkt innerhalb der Benutzeroberfläche der Client-Anwendung. Es ist das Sortieren, mit dem Benutzer interagieren, wenn sie auf Spaltenüberschriften klicken, und es beeinflusst die visuelle Darstellung der Daten auf dem Bildschirm.

Der Entwickler hat keine direkte Kontrolle über die Client-seitige Sortierung, diese wird vielmehr durch den in Java bereitgestellten Spaltentyp bestimmt. Die folgenden Typen werden derzeit unterstützt:

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

Im Gegensatz zur clientseitigen Sortierung beinhaltet die Server-Sortierung das Anordnen und Organisieren von Daten auf dem Server, bevor sie an den Client übertragen werden. Dieser Ansatz ist insbesondere bei großen Datensätzen von Vorteil, die möglicherweise nicht vollständig an den Client übertragen werden können.

Entwickler haben mehr Kontrolle über die Logik der Server-Sortierung. Dies ermöglicht die Implementierung komplexer Sortieralgorithmen und Optimierungen, was diesen Ansatz für Szenarien mit umfangreichen Daten geeignet macht. Dadurch wird sichergestellt, dass der Client vor-sortierte Daten erhält, was den Bedarf an umfangreicher clientseitiger Verarbeitung minimiert.

:::info
Die Server-Sortierung ist eine leistungsorientierte Strategie zum Umgang mit Datensätzen, die die Möglichkeiten einer effizienten clientseitigen Verarbeitung überschreiten, und ist die Standardmethode, die von der `Table` verwendet wird.
:::

### Spalteneigenschaftsname {#column-property-name}

Standardmäßig verwendet die `Table` die ID einer Spalte als Eigenschaftsnamen, wenn sie Sortierkriterien für ein Backend-Repository erstellt. Wenn die Anzeige-ID einer Spalte nicht mit der zugrunde liegenden Daten-Eigenschaft übereinstimmt oder wenn die Spalte einen berechneten Wert anzeigt, verwenden Sie `setPropertyName()`, um der `Table` ausdrücklich zu sagen, nach welcher Eigenschaft sortiert werden soll.

```java
// Spalten-ID ist "Vollständiger Name", aber die Backend-Eigenschaft ist "fullName"
table.addColumn("Vollständiger Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Der Eigenschaftenname wird an die `OrderCriteria` weitergeleitet, wenn ein Sortierereignis ausgelöst wird, sodass Backend-Repositories wie Spring Data JPA oder REST-Adapter die korrekte `ORDER BY`-Klausel erstellen können.

:::warning
Ohne `setPropertyName()` fällt die `Table` auf die Spalten-ID zurück. Wenn dies keine gültige Backend-Eigenschaft hat, wird das Sortieren stillschweigend fehlschlagen oder falsch geordnete Daten zurückgeben.
:::

Verschachtelte Eigenschafts-Pfade werden ebenfalls unterstützt, indem die Punktnotation verwendet wird:

```java
table.addColumn("Stadt", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparators {#comparators}

Die `Column`-Komponente ermöglicht Entwicklern die Verwendung von Java `Comparators` für dynamisches und benutzerdefiniertes Sortieren. Ein `Comparator` ist ein Mechanismus zum Ordnen von zwei Objekten der gleichen Klasse, selbst wenn diese Klasse benutzerdefiniert ist. Diese Funktionalität bietet Entwicklern die Flexibilität, die Art und Weise, wie Daten sortiert werden, anzupassen und mehr Kontrolle über das standardmäßige Sortierverhalten basierend auf der natürlichen Reihenfolge zu haben.

Um `Comparator`-Sortierungen in einer `Column` zu nutzen, können Sie die Methode `setComparator()` verwenden. Diese Methode ermöglicht es Ihnen, eine benutzerdefinierte `Comparator`-Funktion zu definieren, die die Sortierlogik diktiert.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Im obigen Beispiel wird eine benutzerdefinierte Comparator-Funktion spezifiziert, die zwei Elemente (a und b) nimmt und die Sortierreihenfolge basierend auf den geparsten Ganzzahlwerten des `Number`-Attributs definiert.

Die Verwendung von Comparators für die Spaltensortierung ist besonders nützlich, wenn nicht-numerische Werte behandelt werden. Sie sind auch nützlich für die Implementierung komplexer Sortieralgorithmen.

:::info
Standardmäßig verwendet die `Table` die serverseitige Sortierung und sortiert nicht-primitive Werte mithilfe der `toString()`-Methode von Object, wodurch sie in ihre Zeichenfolgenwerte umgewandelt und dann sortiert werden.
:::
