---
sidebar_position: 1
title: Table
hide_giscus_comments: true
_i18n_hash: 3b4ddfbfc0fb9c5d67fa60136a23af73
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

Die `Table`-Klasse ist eine vielseitige Komponente, die entwickelt wurde, um tabellarische Informationen in einer strukturierten und leicht verständlichen Weise darzustellen. Optimiert für die Verarbeitung großer Datensätze mit hoher Leistung, bietet diese Komponente erweiterte Visualisierungsfunktionen und eine umfassende Suite von Ereignissen für eine dynamische Benutzerinteraktion.

<!-- INTRO_END -->

## Erstellung einer `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

Um eine `Table` in einer Anwendung zu erstellen und zu füllen, können die folgenden Schritte durchgeführt werden:

### 1. Erstellen einer Entitätsklasse {#1-create-an-entity-class}

Definieren Sie eine Klasse, die die Entitäten (Daten) darstellt, die Sie in der Tabelle anzeigen möchten. In diesem Beispiel ist diese Klasse MusicRecord.

```java
public class MusicRecord {
  // Felder und Methoden zur Darstellung der Attribute jedes Datensatzes
}
```

### 2. Erstellen eines Repositories {#2-create-a-repository}

Sobald eine Entitätsklasse erstellt wurde, verwenden Sie diese, um eine Sammlung dieser Entitäten mit den gewünschten Daten zu füllen.

Basierend auf diesen Daten muss ein `Repository` für die Verwendung innerhalb der `Table` erstellt werden. Die Klasse `CollectionRepository` wird bereitgestellt, um jede gültige Java-Sammlung in ein verwendbares `Repository` umzuwandeln, wodurch die Notwendigkeit entfällt, eine eigene `Repository`-Klasse zu implementieren.

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

### 3. Instanziieren der `Table` und Hinzufügen von Spalten {#3-instantiate-table-and-add-columns}

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

Setzen Sie schließlich das `Repository` für die in den vorherigen Schritten erstellte `Table`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativ kann der `setItems()`-Methode jede gültige Java-Sammlung übergeben werden, die im Hintergrund ein `CollectionRepository` für Sie erstellt. 
:::

Nachfolgend finden Sie ein Beispiel der oben genannten Schritte, die implementiert wurden, um eine grundlegende `Table`-Komponente zu erstellen:


<ComponentDemo
path='/webforj/tablebasic'
files={[
  'src/main/java/com/webforj/samples/views/table/TableBasicView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Stilgebung

<TableBuilder name="Table" />
