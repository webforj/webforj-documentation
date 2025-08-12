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

`Table`-luokka on monipuolinen komponentti, joka on suunniteltu esittämään taulukkomuotoista tietoa rakenteellisella ja helposti ymmärrettävällä tavalla. Suunniteltu käsittelemään suuria tietojoukkoja korkealla suorituskyvyllä, tämä komponentti tarjoaa edistyksellisiä visualisointeja ja kattavan tapahtumasarjan dynaamiseen käyttäjävuorovaikutukseen.

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>


## Taulukon luominen {#creating-a-table}

Taulukon luomiseksi ja täyttämiseksi sovelluksessa voidaan seurata seuraavia vaiheita:

### 1. Luo entiteettiluokka {#1-create-an-entity-class}

Määritä luokka, joka edustaa taulukossa näytettäviä entiteettejä (dataa). Esimerkissä tämä luokka on MusicRecord.

```java
public class MusicRecord {
    // Kentät ja metodit, jotka edustavat jokaisen levyn attribuutteja
}
```

### 2. Luo varasto {#2-create-a-repository}

Kun entiteettiluokka on luotu, käytä tätä täyttämään kokoelma näistä entiteeteistä haluamallasi datalla.

Tästä datasta on luotava `Repository`, jota käytetään `Table`:ssa. `CollectionRepository`-luokka on tarjottu muuntamaan mikä tahansa voimassa oleva Java-kokoelma käytettäväksi `Repository`:ksi, jolloin oman `Repository`-luokan toteuttamista ei tarvita.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. Luodaan `Table` ja lisätään sarakkeita {#3-instantiate-table-and-add-columns}

Luo uusi `Table`-objekti ja käytä yhtä tarjotusta tehdasmenetelmästä lisätäksesi haluamasi sarakkeet juuri luotuun `Table`:een:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numero", MusicRecord::getNumber);
table.addColumn("Otsikko", MusicRecord::getTitle);
table.addColumn("Artisti", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Hinta", MusicRecord::getCost);
```

### 4. Aseta `Table`-data {#4-set-the-table-data}

Lopuksi, aseta edellisessä vaiheessa luodun `Table`:n `Repository`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Vaihtoehtoisesti `setItems()`-metodille voidaan välittää mikä tahansa voimassa oleva Java-kokoelma, joka luo taustalla `CollectionRepository`-luokan puolestasi.
:::

Alla on esimerkki yllä mainittujen vaiheiden toteuttamisesta perus `Table`-komponentin luomiseksi:

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
