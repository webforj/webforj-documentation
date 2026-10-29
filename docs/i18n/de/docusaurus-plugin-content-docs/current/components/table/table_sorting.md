---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 94345d1313939015f02cf46ecaa5af99
---
Sortierung ermöglicht es Benutzern, Daten in Spalten anzuordnen, was die Informationen einfacher lesbar und analysierbar macht. Dies ist nützlich, wenn Benutzer schnell die höchsten oder niedrigsten Werte in einer bestimmten Spalte finden müssen.

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

Standardmäßig ist eine Spalte nicht sortierbar, es sei denn, sie wird ausdrücklich aktiviert. Um das Sortieren in einer bestimmten Spalte zu erlauben, verwenden Sie die Methode `setSortable(true)`:

```java
table.getColumn("Alter").setSortable(true);
```

## Mehrfachsortierung {#multi-sorting}

:::warning Mehrspaltensortierung standardmäßig in webforJ `25.00` deaktiviert
Vor webforj `25.00` unterstützten Tabellen standardmäßig die Mehrspaltensortierung. Ab Version `25.00` hat sich dieses Verhalten geändert – Entwickler müssen jetzt die Mehrspaltensortierung ausdrücklich aktivieren.
:::

Wenn eine Mehrfachsortierung erforderlich ist, muss `setMultiSorting(true)` auf der Tabelle angewendet werden. Dies ermöglicht es den Benutzern, mehrere Spalten in Folge zu sortieren:

```java
table.setMultiSorting(true);
```

Mit aktivierter Mehrfachsortierung wird durch Klicken auf mehrere Spaltenüberschriften diese sequenziell sortiert. Die Sortierungspriorität wird visuell in der Tabellenbenutzeroberfläche angezeigt.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Sie können auch die Sortierungspriorität programmgesteuert für die serverseitige Sortierung definieren. Verwenden Sie `setSortIndex()` für die Spalten, die Sie der Priorität nach sortieren möchten:

```java
// Sortierreihenfolge auf der Serverseite
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info Die Reihenfolge der Spalten ist wichtig
Sofern `setSortIndex()` nicht verwendet wird, sortiert die Tabelle standardmäßig nach der Reihenfolge, in der die Spalten deklariert sind.
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

Es stehen drei Einstellungen für die Richtung zur Verfügung, in der eine Spalte sortiert werden kann:

- `SortDirection.ASC`: Sortiert die Spalte in aufsteigender Reihenfolge.
- `SortDirection.DESC`: Sortiert die Spalte in absteigender Reihenfolge.
- `SortDirection.NONE`: Keine Sortierung auf die Spalte angewendet.

Wenn eine Spalte das Sortieren aktiviert hat, sehen Sie eine Reihe von vertikalen Pfeilsymbolen oben auf der betreffenden Spalte. Diese Pfeile ermöglichen es dem Benutzer, zwischen den verschiedenen Sortierrichtungen umzuschalten.

Wenn die aufsteigende Reihenfolge ausgewählt ist, wird ein `^` angezeigt, während die absteigende Reihenfolge ein `v` anzeigt.

## Client- vs. serverseitige Sortierung {#client-vs-server-side-sorting}

Die Sortierung von Daten kann grob in zwei Hauptansätze kategorisiert werden: **Client-Sortierung** und **Server-Sortierung**.

### Client-Sortierung {#client-sorting}

Die Client-Sortierung umfasst die Anordnung und Anzeige von Daten direkt innerhalb der Benutzeroberfläche der Client-Anwendung. Es handelt sich um die Sortierung, mit der Benutzer interagieren, wenn sie auf Spaltenüberschriften klicken, was die visuelle Darstellung von Daten auf dem Bildschirm beeinflusst.

Der Entwickler hat keinen direkten Einfluss auf die Sortierung auf der Client-Seite, sondern sie wird durch den Spaltentyp bestimmt, der in Java bereitgestellt wird. Folgende Typen werden derzeit unterstützt:

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

Im Gegensatz zur Client-Seiten-Sortierung bedeutet die Server-Sortierung, Daten auf dem Server zu ordnen und zu organisieren, bevor sie an den Client übertragen werden. Dieser Ansatz ist besonders vorteilhaft, wenn es sich um große Datensätze handelt, die möglicherweise nicht vollständig an den Client übertragen werden können.

Die Entwickler haben mehr Kontrolle über die Logik der Server-Sortierung. Dies ermöglicht die Implementierung komplexer Sortieralgorithmen und Optimierungen, wodurch sie für Szenarien mit umfangreichen Daten geeignet ist. So kann sichergestellt werden, dass der Client vorklassifizierte Daten erhält, was den Bedarf an umfangreicher Client-seitiger Verarbeitung minimiert.

:::info
Die Server-Sortierung ist eine leistungsorientierte Strategie zur Verarbeitung von Datensätzen, die die Fähigkeiten einer effizienten Client-seitigen Verarbeitung übertreffen, und ist das Standardverfahren, das von der `Table` verwendet wird.
:::

### Spalteneigenschaftsname {#column-property-name}

Standardmäßig verwendet die `Table` die ID einer Spalte als Eigenschaftsnamen, wenn Sortierkriterien für ein Backend-Repository erstellt werden. Wenn die Anzeige-ID einer Spalte nicht mit der zugrunde liegenden Daten-Eigenschaft übereinstimmt oder wenn die Spalte einen berechneten Wert anzeigt, verwenden Sie `setPropertyName()`, um der `Table` ausdrücklich zu sagen, nach welcher Eigenschaft sortiert werden soll.

```java
// Spalten-ID ist "Vollständiger Name", aber die Backend-Eigenschaft ist "fullName"
table.addColumn("Vollständiger Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Der Eigenschaftsname wird an die `OrderCriteria` weitergeleitet, wenn ein Sortierereignis ausgelöst wird, was es Backend-Repositorys wie Spring Data JPA oder REST-Adaptern ermöglicht, die korrekte `ORDER BY`-Klausel zu erstellen.

:::warning
Ohne `setPropertyName()` fällt die `Table` auf die Spalten-ID zurück. Wenn dies nicht mit einer gültigen Backend-Eigenschaft übereinstimmt, schlägt die Sortierung stillschweigend fehl oder gibt falsche Daten zurück.
:::

Verschachtelte Eigenschaftspfade werden ebenfalls unter Verwendung der Punktnotation unterstützt:

```java
table.addColumn("Stadt", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Comparatoren {#comparators}

Die `Column`-Komponente ermöglicht es Entwicklern, Java `Comparators` für dynamische und benutzerdefinierte Sortierungen zu verwenden. Ein `Comparator` ist ein Mechanismus, der verwendet wird, um zwei Objekte derselben Klasse anzuordnen, auch wenn diese Klasse benutzerdefinierte ist. Diese Funktionalität bietet Entwicklern die Flexibilität, die Art und Weise, wie Daten sortiert werden, anzupassen und eine höhere Kontrolle über das Standard-Sortierverhalten basierend auf der natürlichen Ordnung zu erhalten.

Um das Sortieren mit `Comparator` in einer `Column` zu nutzen, können Sie die Methode `setComparator()` verwenden. Diese Methode ermöglicht es Ihnen, eine benutzerdefinierte `Comparator`-Funktion zu definieren, die die Sortierlogik bestimmt.

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

Die Verwendung von Comparatoren für die Sortierung von Spalten ist besonders nützlich, wenn es sich um nicht-numerische Werte handelt. Sie sind auch nützlich für die Implementierung komplexer Sortieralgorithmen.

:::info
Standardmäßig verwendet die `Table` die serverseitige Sortierung und sortiert nicht primitive Werte unter Verwendung der `toString()`-Methode von Object, um sie in ihre String-Werte zu konvertieren und danach zu sortieren.
:::
