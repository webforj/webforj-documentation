---
sidebar_position: 1
title: Table
hide_giscus_comments: true
description: >-
  Display tabular data with the Table component, binding entity classes to a
  Repository to populate columns and render rows.
_i18n_hash: 680ee8ce00bf4efc4ae933a913fe1c1a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

`Table`-luokka on monipuolinen komponentti, joka on suunniteltu esittämään taulukkomuotoista tietoa rakenteellisella ja helposti ymmärrettävällä tavalla. Suunniteltu käsittelemään suuria tietojoukkia korkealla suorituskyvyllä, tämä komponentti tarjoaa kehittyneitä visualisointimahdollisuuksia ja kattavan tapahtumakompleksin dynaamiseen käyttäjävuorovaikutukseen.

<!-- INTRO_END -->

## Taulukon luominen {#creating-a-table}

<!-- vale off -->
<ComponentDemo
path='/webforj/datatable'
files={['src/main/java/com/webforj/samples/views/table/DataTableView.java']}
height='600px'
/>
<!-- vale on -->

Taulukon luomiseksi ja täyttämiseksi sovelluksessa voidaan ottaa seuraavat vaiheet:

### 1. Luo entiteettiluokka {#1-create-an-entity-class}

Määrittele luokka, joka edustaa entiteettejä (dataa), joita haluat näyttää taulukossa. Esimerkissä tämä luokka on MusicRecord.

```java
public class MusicRecord {
  // Kentät ja metodit, jotka edustavat jokaisen tietueen attribuutteja
}
```

### 2. Luo varasto {#2-create-a-repository}

Kun entiteettiluokka on luotu, käytä tätä täyttääksesi kokoelman näistä entiteeteistä halutulla datalla.

Tästä datasta on luotava `Repository`, jota käytetään `Table`-komponentin sisällä. `CollectionRepository`-luokka on tarjottu muuntamaan mikä tahansa kelvollinen Java-kokoelma käyttökelpoiseksi `Repository`:ksi, joten omaa `Repository`-luokkaa ei tarvitse toteuttaa.

```java
List<MusicRecord> data = new Gson().fromJson(
  Assets.contentOf(
    Assets.resolveContextUrl("context://data/CDStore.json")
  ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Lisätietoja
Lisätietoja `Repository`-kuviosta webforJ:ssä, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::

### 3. Luo `Table` ja lisää sarakkeita {#3-instantiate-table-and-add-columns}

Luo uusi `Table`-objekti ja käytä jotain tarjotuista tehdasmenetelmistä lisätäksesi haluamasi sarakkeet juuri luotuun `Table`:en:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numero", MusicRecord::getNumber);
table.addColumn("Otsikko", MusicRecord::getTitle);
table.addColumn("Esittäjä", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Hinta", MusicRecord::getCost);
```

### 4. Aseta `Table`-datan {#4-set-the-table-data}

Lopuksi, aseta aiemmassa vaiheessa luodun `Table`:n `Repository`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Vaihtoehtoisesti `setItems()`-metodille voi antaa minkä tahansa kelvollisen Java-kokoelman, mikä luo taustalla `CollectionRepository`:n automaattisesti puolestasi.
:::

Alla on esimerkki yllä olevista vaiheista toteutettuna perus `Table`-komponentin luomiseksi:

<ComponentDemo
path='/webforj/tablebasic'
files={[
  'src/main/java/com/webforj/samples/views/table/TableBasicView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Tyylittely {#styling}

<TableBuilder name="Table" />
