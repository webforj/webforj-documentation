---
sidebar_position: 1
title: Table
hide_giscus_comments: true
_i18n_hash: 0d467fd377fff1994c025ba8a95c957f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

Die `Table`-Klasse ist eine vielseitige Komponente, die dafür entwickelt wurde, tabellarische Informationen in einer strukturierten und leicht verständlichen Weise darzustellen. Optimiert für die Verarbeitung großer Datensätze mit hoher Leistung bietet diese Komponente fortschrittliche Visualisierung und eine umfassende Suite von Ereignissen für dynamisches Benutzerengagement.

<!-- INTRO_END -->

## Erstellung einer `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->

Um eine `Table` in einer Anwendung zu erstellen und zu befüllen, können die folgenden Schritte unternommen werden:

### 1. Erstellen Sie eine Entität Klasse {#1-create-an-entity-class}

Definieren Sie eine Klasse zur Darstellung der Entitäten (Daten), die Sie in der Tabelle anzeigen möchten. In diesem Beispiel ist diese Klasse MusicRecord.

```java
public class MusicRecord {
    // Felder und Methoden zur Darstellung der Attribute jedes Datensatzes
}
```

### 2. Erstellen Sie ein Repository {#2-create-a-repository}

Sobald eine Entität Klasse erstellt wurde, verwenden Sie diese, um eine Sammlung dieser Entitäten mit den gewünschten Daten zu füllen.

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

### 3. Instanziieren Sie die `Table` und fügen Sie Spalten hinzu {#3-instantiate-table-and-add-columns}

Instanziieren Sie ein neues `Table`-Objekt und verwenden Sie eine der bereitgestellten Fabrikmethoden, um die gewünschten Spalten zu einer neu erstellten `Table` hinzuzufügen:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Nummer", MusicRecord::getNumber);
table.addColumn("Titel", MusicRecord::getTitle);
table.addColumn("Künstler", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Kosten", MusicRecord::getCost);
```

### 4. Setzen Sie die Daten der `Table` {#4-set-the-table-data}

Setzen Sie schließlich das `Repository` für die in den vorherigen Schritt erstellte `Table`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternativ kann die Methode `setItems()` jeder gültigen Java-Sammlung übergeben werden, die im Hintergrund ein `CollectionRepository` für Sie erstellt. 
:::

Nachfolgend ein Beispiel der obigen Schritte zur Erstellung einer grundlegenden `Table`-Komponente:

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Styling

<TableBuilder name="Table" />
