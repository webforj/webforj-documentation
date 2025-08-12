---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 3dde6158741882c0936e6cfe5abdad49
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

Die `Table`-Klasse ist eine vielseitige Komponente, die zum Präsentieren von tabellarischen Informationen in einer strukturierten und leicht verständlichen Weise entwickelt wurde. Optimiert für die Verarbeitung großer Datenmengen mit hoher Leistung bietet diese Komponente eine erweiterte Visualisierung und eine umfassende Suite von Ereignissen für dynamisches Benutzerengagement.

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>


## Erstellung einer `Table` {#creating-a-table}

Um eine `Table` in einer Anwendung zu erstellen und zu befüllen, können die folgenden Schritte durchgeführt werden:

### 1. Erstellen einer Entitätsklasse {#1-create-an-entity-class}

Definieren Sie eine Klasse, die die Entitäten (Daten) darstellt, die Sie in der Tabelle anzeigen möchten. In diesem Beispiel ist diese Klasse MusicRecord.

```java
public class MusicRecord {
    // Felder und Methoden zur Darstellung der Attribute jedes Datensatzes
}
```

### 2. Erstellen eines Repositorys {#2-create-a-repository}

Sobald eine Entitätsklasse erstellt wurde, verwenden Sie diese, um eine Sammlung dieser Entitäten mit den gewünschten Daten zu füllen.

Aus diesen Daten muss ein `Repository` erstellt werden, das innerhalb der `Table` verwendet wird. Die Klasse `CollectionRepository` wird bereitgestellt, um jede gültige Java-Sammlung in ein verwendbares `Repository` zu verwandeln, sodass die Notwendigkeit entfällt, Ihre eigene `Repository`-Klasse zu implementieren.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. Instanziierung der `Table` und Hinzufügen von Spalten {#3-instantiate-table-and-add-columns}

Instantiate a new `Table` object, and use one of the provided factory methods to add the desired columns to a newly created `Table`:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Nummer", MusicRecord::getNumber);
table.addColumn("Titel", MusicRecord::getTitle);
table.addColumn("Künstler", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Kosten", MusicRecord::getCost);
```

### 4. Setzen der `Table`-Daten {#4-set-the-table-data}

Setzen Sie schließlich das `Repository` für die in den vorherigen Schritt erstellte `Table`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativ kann der `setItems()`-Methode jede gültige Java-Sammlung übergeben werden, die Ihnen im Hintergrund ein `CollectionRepository` erstellt. 
:::

Im Folgenden finden Sie ein Beispiel für die oben beschriebenen Schritte, die zur Erstellung einer grundlegenden `Table`-Komponente implementiert wurden:

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
