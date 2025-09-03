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

De `Table` klasse is een veelzijdig component dat is ontworpen voor het presenteren van tabelinformatie op een gestructureerde en gemakkelijk te begrijpen manier. Geoptimaliseerd voor het verwerken van grote datasets met hoge prestaties, biedt dit component geavanceerde visualisatie en een uitgebreide suite van evenementen voor dynamische gebruikersbetrokkenheid.

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>


## Een `Table` maken {#creating-a-table}

Om een `Table` in een applicatie te maken en te vullen, kunnen de volgende stappen worden genomen:

### 1. Maak een entiteitsklasse {#1-create-an-entity-class}

Definieer een klasse om de entiteiten (gegevens) weer te geven die je in de tabel wilt tonen. In dit voorbeeld is deze klasse MusicRecord.

```java
public class MusicRecord {
    // Velden en methoden om de attributen van elk record weer te geven
}
```

### 2. Maak een repository {#2-create-a-repository}

Zodra er een entiteitsklasse is gemaakt, gebruik deze dan om een verzameling van deze entiteiten met de gewenste gegevens te vullen.

Van deze gegevens moet er een `Repository` worden gemaakt voor gebruik binnen de `Table`. De `CollectionRepository` klasse is voorzien om elke geldige Java-verzameling om te zetten in een bruikbare `Repository`, zodat je geen eigen `Repository` klasse hoeft te implementeren.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. Instantieer `Table` en voeg kolommen toe {#3-instantiate-table-and-add-columns}

Instantieer een nieuw `Table` object en gebruik een van de beschikbare fabrieksmethoden om de gewenste kolommen aan een nieuw aangemaakte `Table` toe te voegen:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Nummer", MusicRecord::getNumber);
table.addColumn("Titel", MusicRecord::getTitle);
table.addColumn("Artiest", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Kosten", MusicRecord::getCost);
```

### 4. Stel de gegevens van de `Table` in {#4-set-the-table-data}

Stel tenslotte de `Repository` in voor de `Table` die in de vorige stap is gemaakt:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternatief kan de `setItems()` methode elke geldige Java-verzameling aannemen, wat onder de motorkap een `CollectionRepository` voor je aanmaakt. 
:::

Hieronder is een voorbeeld van de bovenstaande stappen geïmplementeerd om een basis `Table` component te creëren:


<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Stijling

<TableBuilder name="Table" />
