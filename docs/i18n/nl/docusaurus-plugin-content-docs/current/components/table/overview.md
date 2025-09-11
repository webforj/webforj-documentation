---
sidebar_position: 1
title: Table
hide_giscus_comments: true
sidebar_class_name: has-new-content
_i18n_hash: 59525ff188c1c03526b8c601a82c7a76
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

De `Table` klasse is een veelzijdig component dat is ontworpen voor het presenteren van tabelinformatie op een gestructureerde en gemakkelijk te begrijpen manier. Geoptimaliseerd voor het omgaan met grote datasets met hoge prestaties, biedt dit component geavanceerde visualisatie en een uitgebreide reeks evenementen voor dynamische gebruikersbetrokkenheid.

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->


## Een `Table` maken {#creating-a-table}

Om een `Table` in een applicatie te maken en te vullen, kunnen de volgende stappen worden genomen:

### 1. Maak een entiteitsklasse {#1-create-an-entity-class}

Definieer een klasse om de entiteiten (gegevens) weer te geven die u in de tabel wilt tonen. In het voorbeeld is deze klasse MusicRecord.

```java
public class MusicRecord {
    // Velden en methoden om de attributen van elk record weer te geven
}
```

### 2. Maak een repository {#2-create-a-repository}

Zodra een entiteitsklasse is gemaakt, gebruik deze dan om een collectie van deze entiteiten met de gewenste gegevens te vullen.

Vanuit deze gegevens moet er een `Repository` worden gemaakt voor gebruik binnen de `Table`. De `CollectionRepository` klasse wordt verstrekt om elke geldige Java-collectie om te zetten in een bruikbare `Repository`, waardoor het noodzakelijk is om uw eigen `Repository`-klasse te implementeren.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Meer informatie
Voor meer informatie over het `Repository`-patroon in webforJ, zie de [Repository-artikelen](/docs/advanced/repository/overview).
:::

### 3. Instantieer `Table` en voeg kolommen toe {#3-instantiate-table-and-add-columns}

Instantiateer een nieuw `Table` object en gebruik een van de verstrekte fabrieksmethoden om de gewenste kolommen toe te voegen aan een nieuw gemaakte `Table`:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Nummer", MusicRecord::getNumber);
table.addColumn("Titel", MusicRecord::getTitle);
table.addColumn("Artiest", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Kosten", MusicRecord::getCost);
```

### 4. Stel de `Table` gegevens in {#4-set-the-table-data}

Stel tenslotte de `Repository` in voor de `Table` die in de vorige stap is gemaakt:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Als alternatief kan de `setItems()`-methode elke geldige Java-collectie ontvangen, die een `CollectionRepository` onder de motorkap voor u creëert. 
:::

Hieronder is een voorbeeld van de bovenstaande stappen geïmplementeerd om een basis `Table` component te maken:

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Stijl {#styling}

<TableBuilder name="Table" />
