---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 53496a465aa0a0971cb4fdc55afa55de
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

Die Klasse `Table` ist eine vielseitige Komponente, die für die Präsentation von tabellarischen Informationen in einer strukturierten und leicht verständlichen Weise konzipiert ist. Optimiert für die Verarbeitung großer Datensätze mit hoher Leistung, bietet diese Komponente fortschrittliche Visualisierung und eine umfassende Suite von Ereignissen für ein dynamisches Benutzerengagement.

<!-- INTRO_END -->

## Erstellen einer `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->

Um eine `Table` in einer Anwendung zu erstellen und zu befüllen, können die folgenden Schritte unternommen werden:

### 1. Erstellen einer Entitätsklasse {#1-create-an-entity-class}

Definieren Sie eine Klasse, um die Entitäten (Daten) darzustellen, die Sie in der Tabelle anzeigen möchten. In diesem Beispiel ist diese Klasse MusicRecord.

```java
public class MusicRecord {
  // Felder und Methoden zur Darstellung der Attribute jedes Datensatzes
}
```

### 2. Erstellen eines Repositories {#2-create-a-repository}

Nachdem eine Entitätsklasse erstellt wurde, verwenden Sie diese, um eine Sammlung dieser Entitäten mit den gewünschten Daten zu füllen.

Aus diesen Daten muss ein `Repository` erstellt werden, das innerhalb der `Table` verwendet werden kann. Die Klasse `CollectionRepository` bietet sich an, um jede gültige Java-Sammlung in ein verwendbares `Repository` umzuwandeln, sodass es nicht erforderlich ist, Ihre eigene `Repository`-Klasse zu implementieren.

```java
List<MusicRecord> data = new Gson().fromJson(
  Assets.contentOf(
    Assets.resolveContextUrl("context://data/CDStore.json")
  ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Weitere Informationen
Für weitere Informationen zum `Repository`-Mustern in webforJ siehe die [Repository-Artikel](/docs/advanced/repository/overview).
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

Setzen Sie schließlich das `Repository` für die in der vorherigen Schritt erstellte `Table`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativ kann der `setItems()`-Methode jede gültige Java-Sammlung übergeben werden, die im Hintergrund eine `CollectionRepository` für Sie erstellt. 
:::

Nachfolgend ein Beispiel der obigen Schritte, die implementiert wurden, um eine grundlegende `Table`-Komponente zu erstellen:


<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Styling

<TableBuilder name="Table" />
