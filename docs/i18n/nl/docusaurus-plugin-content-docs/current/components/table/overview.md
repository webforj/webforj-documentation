---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 2e2825b1825bd2eb6ec7528399936749
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

De `Table`-klasse is een veelzijdige component die is ontworpen voor het presenteren van tabelinformatie op een gestructureerde en gemakkelijk begrijpelijke manier. Geoptimaliseerd voor het verwerken van grote datasets met hoge prestaties, biedt deze component geavanceerde visualisatie en een uitgebreide suite van gebeurtenissen voor dynamische gebruikersinteractie.

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Een `Table` maken {#creating-a-table}

Om een `Table` in een applicatie te maken en te vullen, kunnen de volgende stappen worden ondernomen:

### 1. Maak een entiteitsklasse {#1-create-an-entity-class}

Definieer een klasse die de entiteiten (gegevens) vertegenwoordigt die u in de tabel wilt weergeven. In het voorbeeld is deze klasse MusicRecord.

```java
public class MusicRecord {
    // Velden en methoden om de attributen van elk record weer te geven
}
```

### 2. Maak een repository {#2-create-a-repository}

Zodra een entiteitsklasse is gemaakt, gebruik deze dan om een collectie van deze entiteiten te vullen met de gewenste gegevens.

Hieruit moet een `Repository` worden gemaakt voor gebruik binnen de `Table`. De `CollectionRepository`-klasse is beschikbaar om elke geldige Java-collectie om te zetten in een bruikbare `Repository`, zodat de noodzaak om uw eigen `Repository`-klasse te implementeren, vervalt.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. Instantieer `Table` en voeg kolommen toe {#3-instantiate-table-and-add-columns}

Instantieer een nieuw `Table`-object en gebruik een van de meegeleverde fabrieksmethoden om de gewenste kolommen aan een nieuw gemaakte `Table` toe te voegen:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Nummer", MusicRecord::getNumber);
table.addColumn("Titel", MusicRecord::getTitle);
table.addColumn("Artiest", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Kosten", MusicRecord::getCost);
```

### 4. Stel de gegevens van de `Table` in {#4-set-the-table-data}

Stel uiteindelijk de `Repository` in voor de `Table` die in de vorige stap is gemaakt:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternatief kan de `setItems()`-methode elke geldige Java-collectie ontvangen, wat een `CollectionRepository` achter de schermen voor u zal maken. 
:::

Hieronder vindt u een voorbeeld van de bovenstaande stappen die zijn geïmplementeerd om een basis `Table`-component te creëren:

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
