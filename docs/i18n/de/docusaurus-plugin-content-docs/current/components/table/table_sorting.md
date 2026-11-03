---
sidebar_position: 15
title: Sortierung
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 7a910f5bdcaa8a9e053bcda6706808c2
---
Die Sortierung ermöglicht es Benutzern, Daten in Spalten nach Reihenfolge anzuordnen, sodass Informationen leichter zu lesen und zu analysieren sind. Das ist hilfreich, wenn Benutzer schnell die höchsten oder niedrigsten Werte in einer bestimmten Spalte finden müssen.

:::tip Daten verwalten und abfragen
Informationen zur Verwendung des `Repository`-Musters zum Verwalten und Abfragen von Sammlungen finden Sie in den [Repository-Artikeln](/docs/advanced/repository/overview).
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

Standardmäßig ist eine Spalte nicht sortierbar, sofern dies nicht ausdrücklich aktiviert wird. Verwenden Sie die Methode `setSortable(true)`, um die Sortierung einer bestimmten Spalte zuzulassen:

```java
table.getColumn("Age").setSortable(true);
```

## Mehrspaltige Sortierung {#multi-sorting}

:::warning Mehrspaltige Sortierung ist in webforJ `25.00` standardmäßig deaktiviert
Vor webforJ `25.00` unterstützten Tabellen standardmäßig die Sortierung nach mehreren Spalten. Ab Version `25.00` hat sich dieses Verhalten geändert – Entwickler müssen die mehrspaltige Sortierung jetzt ausdrücklich aktivieren.
:::

Wenn eine mehrspaltige Sortierung erforderlich ist, muss `setMultiSorting(true)` auf die Tabelle angewendet werden. Dadurch können Benutzer mehrere Spalten nacheinander sortieren:

```java
table.setMultiSorting(true);
```

Bei aktivierter mehrspaltiger Sortierung werden mehrere Spalten beim Klicken auf ihre Spaltenüberschriften nacheinander sortiert. Die Sortierpriorität wird in der Tabellenoberfläche visuell angezeigt.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Für die serverseitige Sortierung können Sie die Sortierpriorität auch programmgesteuert festlegen. Verwenden Sie `setSortIndex()` für die zu sortierenden Spalten und geben Sie dabei die Priorität an:

```java
// Server-side sort order
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info Die Spaltenreihenfolge ist wichtig
Sofern `setSortIndex()` nicht verwendet wird, sortiert die Tabelle standardmäßig in der Reihenfolge, in der die Spalten deklariert wurden.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortOrderView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Sortierrichtung {#sort-direction}

Für die Sortierrichtung einer Spalte stehen drei Einstellungen zur Verfügung:

- `SortDirection.ASC`: Sortiert die Spalte aufsteigend.
- `SortDirection.DESC`: Sortiert die Spalte absteigend.
- `SortDirection.NONE`: Die Spalte wird nicht sortiert.

Wenn die Sortierung für eine Spalte aktiviert ist, werden oben in der Spalte vertikale Pfeile angezeigt. Mit diesen Pfeilen kann der Benutzer zwischen den verschiedenen Sortierrichtungen wechseln.

Bei aufsteigender Sortierung wird ein `^` angezeigt, bei absteigender Sortierung ein `v`.


## Client- und serverseitige Sortierung {#client-vs-server-side-sorting}

Die Datensortierung lässt sich grundsätzlich in zwei Ansätze unterteilen: **clientseitige Sortierung** und **serverseitige Sortierung**.

### Clientseitige Sortierung {#client-sorting}

Bei der clientseitigen Sortierung werden Daten direkt in der Benutzeroberfläche der Clientanwendung angeordnet und angezeigt. Dies ist die Sortierung, mit der Benutzer interagieren, wenn sie auf Spaltenüberschriften klicken und so die visuelle Darstellung der Daten auf dem Bildschirm beeinflussen.

Entwickler haben keine direkte Kontrolle über die clientseitige Sortierung. Sie wird stattdessen durch den in Java angegebenen Spaltentyp bestimmt. Derzeit werden folgende Typen unterstützt:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Die clientseitige Sortierung funktioniert nicht, wenn dem Client nur ein Teil der Daten zur Verfügung steht.
:::

### Serverseitige Sortierung {#server-sorting}

Im Gegensatz zur clientseitigen Sortierung werden Daten bei der serverseitigen Sortierung auf dem Server angeordnet und organisiert, bevor sie an den Client übertragen werden. Dieser Ansatz ist besonders vorteilhaft bei großen Datensätzen, deren vollständige Übertragung an den Client möglicherweise nicht praktikabel ist.

Entwickler haben mehr Kontrolle über die Logik der serverseitigen Sortierung. Dadurch können komplexe Sortieralgorithmen und Optimierungen implementiert werden, was diesen Ansatz für umfangreiche Datenmengen geeignet macht. So erhält der Client vorsortierte Daten, wodurch der erforderliche clientseitige Verarbeitungsaufwand reduziert wird.


:::info
Die serverseitige Sortierung ist eine leistungsorientierte Strategie für Datensätze, die die Möglichkeiten einer effizienten clientseitigen Verarbeitung übersteigen. Sie ist die von `Table` standardmäßig verwendete Methode.
:::

### Eigenschaftsname der Spalte {#column-property-name}

Standardmäßig verwendet `Table` die ID einer Spalte als Eigenschaftsnamen, wenn Sortierkriterien für ein Backend-Repository erstellt werden. Wenn die Anzeige-ID einer Spalte nicht mit der zugrunde liegenden Dateneigenschaft übereinstimmt oder die Spalte einen berechneten Wert anzeigt, verwenden Sie `setPropertyName()`, um `Table` explizit mitzuteilen, nach welcher Eigenschaft sortiert werden soll.

```java
// Column ID is "Full Name", but the backend property is "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Der Eigenschaftsname wird beim Auslösen eines Sortierereignisses an `OrderCriteria` weitergegeben. Dadurch können Backend-Repositorys wie Spring Data JPA oder REST-Adapter die korrekte `ORDER BY`-Klausel erstellen.

:::warning
Ohne `setPropertyName()` verwendet `Table` ersatzweise die Spalten-ID. Wenn diese nicht mit einer gültigen Backend-Eigenschaft übereinstimmt, schlägt die Sortierung unbemerkt fehl oder liefert falsch sortierte Daten.
:::

Verschachtelte Eigenschaftspfade werden ebenfalls unterstützt und können mit Punktnotation angegeben werden:

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparatoren {#comparators}

Die Komponente `Column` ermöglicht Entwicklern die Verwendung von Java-`Comparator`en für dynamische und benutzerdefinierte Sortierungen. Ein `Comparator` ist ein Mechanismus, mit dem zwei Objekte derselben Klasse geordnet werden können, auch wenn diese Klasse benutzerdefiniert ist. Diese Funktion bietet Entwicklern die Flexibilität, die Sortierung von Daten anzupassen und das standardmäßige Sortierverhalten anhand der natürlichen Ordnung gezielt zu steuern.

Um die Sortierung mit `Comparator` in einer `Column` zu verwenden, können Sie die Methode `setComparator()` einsetzen. Mit dieser Methode lässt sich eine benutzerdefinierte `Comparator`-Funktion festlegen, die die Sortierlogik bestimmt.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Im obigen Beispiel wird eine benutzerdefinierte Comparator-Funktion angegeben, die zwei Elemente (a und b) entgegennimmt und die Sortierreihenfolge anhand der geparsten Ganzzahlwerte des Attributs `Number` bestimmt.

Die Verwendung von Comparatoren für die Sortierung von Spalten ist besonders hilfreich beim Umgang mit nicht numerischen Werten. Sie eignen sich auch zur Implementierung komplexer Sortieralgorithmen.

:::info
Standardmäßig verwendet `Table` die serverseitige Sortierung und sortiert nicht primitive Werte mithilfe der Methode `toString()` von Object, indem die Werte in Zeichenfolgen umgewandelt und anschließend sortiert werden.
:::
