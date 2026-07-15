---
sidebar_position: 1
title: Table
hide_giscus_comments: true
description: >-
  Display tabular data with the Table component, binding entity classes to a
  Repository to populate columns and render rows.
_i18n_hash: 680ee8ce00bf4efc4ae933a913fe1c1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

Die Klasse `Table` ist eine vielseitige Komponente, die zum Präsentieren von tabellarischen Informationen in strukturierter und leicht verständlicher Weise entwickelt wurde. Optimiert für die Verarbeitung großer Datensätze mit hoher Leistung, bietet diese Komponente erweiterte Visualisierungen und eine umfassende Suite von Ereignissen für eine dynamische Benutzerinteraktion.

<!-- INTRO_END -->

## Erstellung einer `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

Um eine `Table` in einer Anwendung zu erstellen und zu befüllen, können folgende Schritte unternommen werden:

### 1. Erstellen einer Entitätsklasse {#1-create-an-entity-class}

Definieren Sie eine Klasse, um die Entitäten (Daten), die Sie in der Tabelle anzeigen möchten, darzustellen. In diesem Beispiel ist diese Klasse MusicRecord.

```java
public class MusicRecord {
  // Felder und Methoden zur Darstellung der Attribute jedes Datensatzes
}
```

### 2. Erstellen eines Repositories {#2-create-a-repository}

Nachdem eine Entitätsklasse erstellt wurde, verwenden Sie diese, um eine Sammlung dieser Entitäten mit den gewünschten Daten zu füllen.

Aus diesen Daten muss ein `Repository` erstellt werden, das innerhalb der `Table` verwendet wird. Die Klasse `CollectionRepository` wird bereitgestellt, um jede gültige Java-Sammlung in ein verwendbares `Repository` zu verwandeln, wodurch die Notwendigkeit entfällt, Ihre eigene `Repository`-Klasse zu implementieren.

```java
List<MusicRecord> data = new Gson().fromJson(
  Assets.contentOf(
    Assets.resolveContextUrl("context://data/CDStore.json")
  ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Weitere Informationen
Für weitere Informationen zum `Repository`-Muster in webforJ siehe die [Repository-Artikel](/docs/advanced/repository/overview).
:::

### 3. Instanziierung der `Table` und Hinzufügen von Spalten {#3-instantiate-table-and-add-columns}

Instanziieren Sie ein neues `Table`-Objekt und verwenden Sie eine der bereitgestellten Fabrikmethoden, um die gewünschten Spalten zu einer neu erstellten `Table` hinzuzufügen:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Nummer", MusicRecord::getNumber);
table.addColumn("Titel", MusicRecord::getTitle);
table.addColumn("Künstler", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Kosten", MusicRecord::getCost);
```

### 4. Setzen der `Table`-Daten {#4-set-the-table-data}

Setzen Sie schließlich das `Repository` für die in der vorherigen Schritt erstellte `Table`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativ kann der Methode `setItems()` jede gültige Java-Sammlung übergeben werden, die unter der Haube ein `CollectionRepository` für Sie erstellt.
:::

Im Folgenden ist ein Beispiel für die oben genannten Schritte, um eine grundlegende `Table`-Komponente zu erstellen:

<ComponentDemo
path='/webforj/tablebasic'
files={[
  'src/main/java/com/webforj/samples/views/table/TableBasicView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Stilgestaltung {#styling}

<TableBuilder name="Table" />
