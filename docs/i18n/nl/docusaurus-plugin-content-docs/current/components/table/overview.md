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

De `Table` klasse is een veelzijdig component dat is ontworpen voor het presenteren van tabelinformatie op een gestructureerde en gemakkelijk te begrijpen manier. Geoptimaliseerd voor het verwerken van grote datasets met hoge prestaties, biedt dit component geavanceerde visualisatie en een uitgebreide suite van evenementen voor dynamische gebruikersbetrokkenheid.

<!-- INTRO_END -->

## Een `Table` maken {#creating-a-table}

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->

Om een `Table` in een applicatie te maken en te vullen, kunnen de volgende stappen worden genomen:

### 1. Maak een entiteitklasse {#1-create-an-entity-class}

Definieer een klasse om de entiteiten (gegevens) weer te geven die je in de tabel wilt tonen. In het voorbeeld is deze klasse MusicRecord.

```java
public class MusicRecord {
  // Velden en methoden om de attributen van elk record weer te geven
}
```

### 2. Maak een repository {#2-create-a-repository}

Zodra een entiteitklasse is aangemaakt, gebruik deze dan om een collectie van deze entiteiten met de gewenste gegevens te vullen.

Vanuit deze gegevens moet er een `Repository` worden aangemaakt voor gebruik binnen de `Table`. De `CollectionRepository` klasse is beschikbaar om elke geldige Java-collectie om te zetten in een bruikbare `Repository`, zodat je geen eigen `Repository` klasse hoeft te implementeren.

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

Instantieer een nieuw `Table` object en gebruik een van de beschikbare fabrieksmethoden om de gewenste kolommen toe te voegen aan een nieuw aangemaakt `Table`:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Nummer", MusicRecord::getNumber);
table.addColumn("Titel", MusicRecord::getTitle);
table.addColumn("Artiest", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Kosten", MusicRecord::getCost);
```

### 4. Stel de `Table`-gegevens in {#4-set-the-table-data}

Stel ten slotte de `Repository` in voor de `Table` die in de vorige stap is aangemaakt:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Alternatief kan de `setItems()` methode elke geldige Java-collectie accepteren, wat onder de motorkap een `CollectionRepository` voor je aanmaakt. 
:::

Hier is een voorbeeld van de bovenstaande stappen geïmplementeerd om een basis `Table` component te creëren:


<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Stijlen

<TableBuilder name="Table" />
