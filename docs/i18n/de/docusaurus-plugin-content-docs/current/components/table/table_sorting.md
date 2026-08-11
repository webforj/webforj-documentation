---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: f577bea532193b97e6fef03a8bcb641b
---
Die Sortierung ermöglicht es Benutzern, Daten in Spalten nach Reihenfolge anzuordnen, wodurch Informationen leichter lesbar und analysierbar werden. Dies ist nützlich, wenn Benutzer schnell die höchsten oder niedrigsten Werte in einer bestimmten Spalte finden müssen.

:::tip Verwaltung und Abfrage von Daten
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
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
table.getColumn("Age").setSortable(true);
```

## Mehrfachsortierung {#multi-sorting}

:::warning Mehrspalten-Sortierung standardmäßig in webforJ `25.00` deaktiviert
Vor webforj `25.00` unterstützten Tabellen standardmäßig die Mehrspalten-Sortierung. Ab der Version `25.00` hat sich dieses Verhalten geändert – Entwickler müssen jetzt die Mehrspalten-Sortierung ausdrücklich aktivieren.
:::

Wenn eine Mehrfachsortierung erforderlich ist, muss `setMultiSorting(true)` auf die Tabelle angewendet werden. Dies ermöglicht es Benutzern, mehrere Spalten nacheinander zu sortieren:

```java
table.setMultiSorting(true);
```

Mit aktivierter Mehrfachsortierung werden mehrere Spaltenüberschriften nacheinander sortiert. Die Sortierpriorität wird visuell in der Tabellenbenutzeroberfläche angezeigt.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Sie können auch die Sortierpriorität programmatisch für die serverseitige Sortierung definieren. Verwenden Sie `setSortOrder()` für die Spalten, die Sie in der Reihenfolge der Priorität sortieren möchten:

```java
// Serverseitige Sortierreihenfolge
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Die Reihenfolge der Spalten ist wichtig
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

Es gibt drei verfügbare Einstellungen für die Richtung, in der eine Spalte sortiert werden kann:

- `SortDirection.ASC`: Sortiert die Spalte in aufsteigender Reihenfolge.
- `SortDirection.DESC`: Sortiert die Spalte in absteigender Reihenfolge.
- `SortDirection.NONE`: Keine Sortierung auf die Spalte angewendet.

Wenn eine Spalte die Sortierung ermöglicht, sehen Sie am oberen Ende der Spalte eine Reihe von vertikalen Pfeilen erscheinen. Diese Pfeile ermöglichen es dem Benutzer, zwischen den verschiedenen Sortierrichtungen umzuschalten.

Wenn die aufsteigende Reihenfolge ausgewählt ist, wird ein `^` angezeigt, während bei absteigender Reihenfolge ein `v` angezeigt wird.

## Client- vs. serverseitige Sortierung {#client-vs-server-side-sorting}

Die Sortierung von Daten kann grob in zwei Hauptansätze unterteilt werden: **Client-Sortierung** und **Server-Sortierung**.

### Client-Sortierung {#client-sorting}

Die Client-Sortierung beinhaltet das Anordnen und Darstellen von Daten direkt innerhalb der Benutzeroberfläche der Client-Anwendung. Es ist die Sortierung, mit der Benutzer interagieren, wenn sie auf Spaltenüberschriften klicken, und Einfluss auf die visuelle Darstellung der Daten auf dem Bildschirm nehmen.

Der Entwickler hat keine direkte Kontrolle über die clientseitige Sortierung, die vielmehr durch den Spaltentyp bestimmt wird, der in Java bereitgestellt wird. Die folgenden Typen werden derzeit unterstützt:

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

Im Gegensatz zur clientseitigen Sortierung bedeutet die Server-Sortierung, dass die Daten auf dem Server angeordnet und organisiert werden, bevor sie an den Client übertragen werden. Dieser Ansatz ist besonders vorteilhaft bei der Verarbeitung großer Datensätze, die möglicherweise nicht vollständig an den Client übertragen werden können.

Entwickler haben mehr Kontrolle über die Logik der Server-Sortierung. Dies ermöglicht die Implementierung komplexer Sortieralgorithmen und Optimierungen, was es für Szenarien mit umfangreichen Daten geeignet macht. Dadurch wird sichergestellt, dass der Client vor-sortierte Daten erhält, was die Notwendigkeit umfangreicher clientseitiger Verarbeitung minimiert.

:::info
Die Server-Sortierung ist eine leistungsorientierte Strategie für den Umgang mit Datensätzen, die die Möglichkeiten einer effizienten clientseitigen Verarbeitung übersteigen, und ist die Standardmethode, die von der `Table` verwendet wird.
:::

### Spalteneigenschaftsname {#column-property-name}

Standardmäßig verwendet die `Table` die ID einer Spalte als Eigenschaftsnamen, wenn sie Sortierkriterien für ein Backend-Repository aufbaut. Wenn die angezeigte ID einer Spalte nicht mit der zugrunde liegenden Dateneigenschaft übereinstimmt oder wenn die Spalte einen berechneten Wert anzeigt, verwenden Sie `setPropertyName()`, um der `Table` ausdrücklich mitzuteilen, nach welcher Eigenschaft sortiert werden soll.

```java
// Spalten-ID ist "Vollständiger Name", aber die Backend-Eigenschaft ist "fullName"
table.addColumn("Vollständiger Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Der Eigenschaftsname wird an die `OrderCriteria` weitergeleitet, wenn ein Sortierereignis ausgelöst wird, damit Backend-Repositories wie Spring Data JPA oder REST-Adapter die korrekte `ORDER BY`-Klausel aufbauen können.

:::warning
Ohne `setPropertyName()` fällt die `Table` auf die Spalten-ID zurück. Wenn dies nicht mit einer gültigen Backend-Eigenschaft übereinstimmt, schlägt die Sortierung stillschweigend fehl oder gibt falsche Daten zurück.
:::

Verschachtelte Eigenschaftspfad sind ebenfalls unter Verwendung der Punktnotation unterstützt:

```java
table.addColumn("Stadt", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vergleichsoperatoren {#comparators}

Die `Column`-Komponente ermöglicht es Entwicklern, Java `Comparators` für dynamisches und benutzerdefiniertes Sortieren zu verwenden. Ein `Comparator` ist ein Mechanismus, der verwendet wird, um zwei Objekte derselben Klasse zu ordnen, selbst wenn diese Klasse benutzerdefiniert ist. Diese Funktionalität bietet Entwicklern Flexibilität, wie Daten sortiert werden, und ermöglicht eine höhere Kontrolle über das Standard-Sortierverhalten basierend auf natürlicher Ordnung.

Um `Comparator`-Sortierung in einer `Column` zu nutzen, können Sie die Methode `setComparator()` verwenden. Diese Methode ermöglicht es Ihnen, eine benutzerdefinierte `Comparator`-Funktion zu definieren, die die Sortierlogik bestimmt.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Im obigen Beispiel wird eine benutzerdefinierte Vergleichsfunktion angegeben, die zwei Elemente (a und b) annimmt und die Sortierreihenfolge basierend auf den geparsten Ganzzahlwerten des `Number`-Attributs definiert.

Die Verwendung von Comparators für die Spalten-Sortierung ist besonders nützlich, wenn nicht-numerische Werte behandelt werden. Sie sind auch nützlich, um komplexe Sortieralgorithmen zu implementieren.

:::info
Standardmäßig verwendet die `Table` die serverseitige Sortierung und sortiert nicht-primitiven Werte mithilfe der `toString()`-Methode von Object, wobei diese in ihre String-Werte umgewandelt und dann sortiert werden.
:::
