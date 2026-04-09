---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 99281603bebefd43f033e9d0c958c366
---
Sortierung ermöglicht es Benutzern, Daten in Spalten nach Reihenfolge anzuordnen, was Informationen leichter lesbar und analysierbar macht. Dies ist nützlich, wenn Benutzer schnell die höchsten oder niedrigsten Werte in einer bestimmten Spalte finden müssen.

:::tip Daten verwalten und abfragen
Für Informationen zur Verwendung des `Repository`-Musters zur Verwaltung und Abfrage von Sammlungen siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Standardmäßig ist eine Spalte nicht sortierbar, es sei denn, sie wird ausdrücklich aktiviert. Um das Sortieren einer bestimmten Spalte zu ermöglichen, verwenden Sie die Methode `setSortable(true)`:

```java 
table.getColumn("Alter").setSortable(true);
```

## Mehrfachsortierung {#multi-sorting}

:::warning Mehrspaltensortierung standardmäßig in webforJ `25.00` deaktiviert
Vor webforj `25.00` unterstützten Tabellen standardmäßig die Mehrspaltensortierung. Ab Version `25.00` hat sich dieses Verhalten geändert – Entwickler müssen nun die Mehrspaltensortierung ausdrücklich aktivieren.
:::

Wenn Mehrfachsortierung benötigt wird, muss `setMultiSorting(true)` auf die Tabelle angewendet werden. Dies ermöglicht es Benutzern, mehrere Spalten nacheinander zu sortieren:

```java
table.setMultiSorting(true);
```

Mit aktiver Mehrfachsortierung führt das Klicken auf mehrere Spaltenüberschriften zu einer sequentiellen Sortierung. Die Sortierpriorität wird visuell in der Tabellenbenutzeroberfläche angezeigt.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Sie können auch die Sortierpriorität programmgesteuert für die serverseitige Sortierung definieren. Verwenden Sie `setSortOrder()` für die Spalten, die Sie in der Reihenfolge der Priorität sortieren möchten:

```java
// Serverseitige Sortierreihenfolge
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Spaltenreihenfolge ist wichtig
Wenn `setSortOrder()` nicht verwendet wird, sortiert die Tabelle standardmäßig in der Reihenfolge, in der die Spalten deklariert sind.
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
- `SortDirection.NONE`: Es wird keine Sortierung auf die Spalte angewendet.

Wenn eine Spalte das Sortieren aktiviert hat, sehen Sie eine Reihe von vertikalen Pfeilsymbolen oben in der betreffenden Spalte. Diese Pfeile ermöglichen es dem Benutzer, zwischen den verschiedenen Sortierdirketoren umzuschalten.

Wenn die aufsteigende Reihenfolge ausgewählt ist, wird ein `^` angezeigt, während die absteigende Reihenfolge ein `v` anzeigen wird.

## Client- vs. serverseitige Sortierung {#client-vs-server-side-sorting}

Die Sortierung von Daten kann grob in zwei Hauptansätze kategorisiert werden: **Client-Sortierung** und **Server-Sortierung**.

### Client-Sortierung {#client-sorting}

Die Client-Sortierung umfasst das Anordnen und Anzeigen von Daten direkt innerhalb der Benutzeroberfläche der Client-Anwendung. Es ist die Sortierung, mit der Benutzer interagieren, wenn sie auf Spaltenüberschriften klicken, was die visuelle Darstellung der Daten auf dem Bildschirm beeinflusst.

Der Entwickler hat keine direkte Kontrolle über die clientseitige Sortierung, da sie durch den in Java bereitgestellten Spalentyp bestimmt wird. Die folgenden Typen werden derzeit unterstützt:

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

Im Gegensatz zur clientseitigen Sortierung beinhaltet die Server-Sortierung das Anordnen und Organisieren von Daten auf dem Server, bevor sie an den Client übermittelt werden. Dieser Ansatz ist besonders vorteilhaft, wenn mit großen Datensätzen gearbeitet wird, die möglicherweise nicht vollständig an den Client übertragen werden können.

Entwickler haben mehr Kontrolle über die Logik der Server-Sortierung. Dies ermöglicht die Implementierung komplexer Sortieralgorithmen und Optimierungen, was für Szenarien mit umfangreichen Daten geeignet ist. Dadurch wird sichergestellt, dass der Client bereits vorsortierte Daten erhält, wodurch der Bedarf an umfangreicher clientseitiger Verarbeitung minimiert wird.

:::info
Die Server-Sortierung ist eine leistungsorientierte Strategie zur Verarbeitung von Datensätzen, die die Fähigkeiten der effizienten clientseitigen Verarbeitung überschreiten, und ist die standardmäßig verwendete Methode durch die `Table`.
:::

### Spalten-Eigenschaftsname {#column-property-name}

Standardmäßig verwendet die `Table` die ID einer Spalte als Eigenschaftsnamen, wenn Sortierkriterien für ein Backend-Repository erstellt werden. Wenn die Anzeige-ID einer Spalte nicht mit der zugrunde liegenden Daten-Eigenschaft übereinstimmt oder wenn die Spalte einen berechneten Wert anzeigt, verwenden Sie `setPropertyName()`, um der `Table` explizit zu sagen, nach welcher Eigenschaft sortiert werden soll.

```java
// Spalten-ID ist "Vollständiger Name", aber die Backend-Eigenschaft ist "fullName"
table.addColumn("Vollständiger Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Der Eigenschaftsname wird an die `OrderCriteria` weitergeleitet, wenn ein Sortierereignis ausgelöst wird, was es Backend-Repositories wie Spring Data JPA oder REST-Adaptern ermöglicht, die korrekte `ORDER BY`-Klausel zu erstellen.

:::warning
Ohne `setPropertyName()` fällt die `Table` auf die Spalten-ID zurück. Wenn dies nicht mit einer gültigen Backend-Eigenschaft übereinstimmt, wird die Sortierung stillschweigend fehlschlagen oder falsch geordnete Daten zurückgeben.
:::

Verschachtelte Eigenschaftswege werden ebenfalls mithilfe der Punktnotation unterstützt:

```java
table.addColumn("Stadt", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vergleichsoperatoren {#comparators}

Die `Column`-Komponente ermöglicht es Entwicklern, Java `Comparators` für dynamisches und benutzerdefiniertes Sortieren zu verwenden. Ein `Comparator` ist ein Mechanismus, der verwendet wird, um zwei Objekte derselben Klasse zu ordnen, selbst wenn diese Klasse benutzerdefiniert ist. Diese Funktionalität bietet Entwicklern die Flexibilität, anzupassen, wie Daten sortiert werden, und ein höheres Maß an Kontrolle über das Standard-Sortierverhalten basierend auf der natürlichen Ordnung zu ermöglichen.

Um die Sortierung mittels `Comparator` in einer `Column` zu nutzen, können Sie die Methode `setComparator()` verwenden. Diese Methode ermöglicht es Ihnen, eine benutzerdefinierte `Comparator`-Funktion zu definieren, die die Sortierlogik bestimmt.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Im obigen Beispiel wird eine benutzerdefinierte Vergleichsfunktion spezifiziert, die zwei Elemente (a und b) nimmt und die Sortierreihenfolge basierend auf den geparsten Ganzzahlen des `Number`-Attributs definiert.

Die Verwendung von Comparators für die Spaltensortierung ist besonders nützlich, wenn mit nicht-numerischen Werten gearbeitet wird. Sie sind auch nützlich, um komplexe Sortieralgorithmen zu implementieren.

:::info
Standardmäßig verwendet die `Table` die serverseitige Sortierung und sortiert nicht-primitiv Werte unter Verwendung der `toString()`-Methode von Object, wobei diese in ihre Stringwerte umgewandelt und dann sortiert werden.
:::
