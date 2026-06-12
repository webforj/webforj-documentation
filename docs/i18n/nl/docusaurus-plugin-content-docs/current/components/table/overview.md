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

De `Table` klasse is een veelzijdig component dat is ontworpen voor het presenteren van tabulaire informatie op een gestructureerde en gemakkelijk begrijpelijke manier. Geoptimaliseerd voor het verwerken van grote datasets met hoge prestaties, biedt dit component geavanceerde visualisatie en een uitgebreide suite van evenementen voor dynamische gebruikersbetrokkenheid.

<!-- INTRO_END -->

## Een `Table` maken {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

Om een `Table` in een applicatie te maken en te vullen, kunnen de volgende stappen worden genomen:

### 1. Maak een entity klasse {#1-create-an-entity-class}

Definieer een klasse om de entiteiten (gegevens) weer te geven die je in de tabel wilt tonen. In dit voorbeeld is deze klasse MusicRecord.

```java
public class MusicRecord {
  // Velden en methoden om de attributen van elk record voor te stellen
}
```

### 2. Maak een repository {#2-create-a-repository}

Zodra een entity klasse is gemaakt, gebruik deze dan om een collectie van deze entiteiten te vullen met de gewenste gegevens.

Vanuit deze gegevens moet een `Repository` worden gemaakt voor gebruik binnen de `Table`. De `CollectionRepository` klasse wordt geleverd om elke geldige Java-collectie om te zetten in een bruikbare `Repository`, waardoor de noodzaak om je eigen `Repository` klasse te implementeren, vervalt.

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

Instantieer een nieuw `Table` object en gebruik een van de aangeboden fabrieksmethoden om de gewenste kolommen toe te voegen aan een nieuw gemaakte `Table`:

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
Als alternatief kan de `setItems()` methode elke geldige Java-collectie worden gegeven, wat een `CollectionRepository` onder de motorkap voor je creëert.
:::

Hieronder staat een voorbeeld van de bovenstaande stappen geïmplementeerd om een basis `Table` component te creëren:

<ComponentDemo
path='/webforj/tablebasic'
files={[
  'src/main/java/com/webforj/samples/views/table/TableBasicView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Styling

<TableBuilder name="Table" />
