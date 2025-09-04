---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 9e123638ff60f46c96d369bce79da44e
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

Die `Table`-Klasse ist ein vielseitiges Component, das entwickelt wurde, um tabellarische Informationen strukturiert und leicht verständlich darzustellen. Optimiert für die Verarbeitung großer Datensätze mit hoher Leistung, bietet dieses Component erweiterte Visualisierungsmöglichkeiten und eine umfassende Suite von Ereignissen für dynamisches Benutzerengagement.

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>


## Erstellung einer `Table` {#creating-a-table}

Um eine `Table` in einer Anwendung zu erstellen und zu befüllen, können die folgenden Schritte unternommen werden:

### 1. Erstellen einer Entitätsklasse {#1-create-an-entity-class}

Definieren Sie eine Klasse, die die Entitäten (Daten) darstellt, die Sie in der Tabelle anzeigen möchten. In diesem Beispiel ist diese Klasse MusicRecord.

```java
public class MusicRecord {
    // Felder und Methoden zur Darstellung der Attribute jedes Datensatzes
}
```

### 2. Erstellen eines Repositories {#2-create-a-repository}

Sobald eine Entitätsklasse erstellt wurde, verwenden Sie diese, um eine Sammlung dieser Entitäten mit den gewünschten Daten zu füllen.

Aus diesen Daten muss ein `Repository` erstellt werden, das innerhalb der `Table` verwendet wird. Die Klasse `CollectionRepository` wird bereitgestellt, um jede gültige Java-Sammlung in ein verwendbares `Repository` zu verwandeln, sodass die Implementierung einer eigenen `Repository`-Klasse entfällt.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. Instanziierung von `Table` und Hinzufügen von Spalten {#3-instantiate-table-and-add-columns}

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

Setzen Sie schließlich das `Repository` für die `Table`, die im vorherigen Schritt erstellt wurde:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativ kann die Methode `setItems()` mit jeder gültigen Java-Sammlung aufgerufen werden, was im Hintergrund ein `CollectionRepository` für Sie erstellt. 
:::

Unten steht ein Beispiel der obigen Schritte, die implementiert wurden, um ein einfaches `Table`-Component zu erstellen:


<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Styling

<TableBuilder name="Table" />
