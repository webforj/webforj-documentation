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

`Table`-luokka on monipuolinen komponentti, joka on suunniteltu esittämään taulukkomuotoista tietoa rakenteellisesti ja helposti ymmärrettävällä tavalla. Optimoi suuret tietojoukot korkean suorituskyvyn avulla, tämä komponentti tarjoaa edistyksellistä visualisointia ja kattavan tapahtumasarjan dynaamiseen käyttäjävuorovaikutukseen.

<!-- INTRO_END -->

## Table- komponentin luominen {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

Luodaksesi ja täyttääksesi `Table`-komponentin sovelluksessa, voit seurata seuraavia vaiheita:

### 1. Luo entiteettiluokka {#1-create-an-entity-class}

Määrittele luokka edustamaan entiteettejä (data), joita haluat näyttää taulukossa. Esimerkissä tämä luokka on MusicRecord.

```java
public class MusicRecord {
  // Kentät ja menetelmät, jotka edustavat kunkin tietueen attribuutteja
}
```

### 2. Luo repositorio {#2-create-a-repository}

Kun entiteettiluokka on luotu, käytä tätä täyttämään kokoelma näistä entiteeteistä halutulla datalla.

Tämästä datasta on luotava `Repository`, jota käytetään `Table`-komponentissa. `CollectionRepository`-luokkaa tarjotaan, jotta mikä tahansa kelvollinen Java-kokoelma voitaisiin muuttaa käyttökelpoiseksi `Repository`:ksi, jolloin omaa `Repository`-luokkaa ei tarvitse toteuttaa.

```java
List<MusicRecord> data = new Gson().fromJson(
  Assets.contentOf(
    Assets.resolveContextUrl("context://data/CDStore.json")
  ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Lisätietoja
Lisätietoja `Repository`-mallista webforJ:ssä, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::

### 3. Instansoi `Table` ja lisää sarakkeet {#3-instantiate-table-and-add-columns}

Instansoi uusi `Table`-objekti ja käytä yhtä tarjotuista tehdasmenetelmistä lisätäksesi halutut sarakkeet juuri luotuun `Table`:en:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Number", MusicRecord::getNumber);
table.addColumn("Title", MusicRecord::getTitle);
table.addColumn("Artist", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Cost", MusicRecord::getCost);
```

### 4. Aseta `Table`-datan {#4-set-the-table-data}

Lopuksi, aseta `Repository` aikaisemmin luodulle `Table`:lle:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Vaihtoehtoisesti, `setItems()`-menetelmään voidaan syöttää mikä tahansa kelvollinen Java-kokoelma, jolloin se luo automaattisesti `CollectionRepository`:n.
:::

Alla on esimerkki edellä mainituista vaiheista toteutettuna luomaan perus `Table`-komponentti:

<ComponentDemo
path='/webforj/tablebasic'
files={[
  'src/main/java/com/webforj/samples/views/table/TableBasicView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Tyylittely

<TableBuilder name="Table" />
