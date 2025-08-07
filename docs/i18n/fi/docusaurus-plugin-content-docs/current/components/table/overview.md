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

`Table`-luokka on monipuolinen komponentti, joka on suunniteltu esittämään taulukkomuotoista tietoa rakenteellisella ja helposti ymmärrettävällä tavalla. Suunniteltu käsittelemään suuria aineistoja korkealla suorituskyvyllä, tämä komponentti tarjoaa edistyksellisiä visualisointeja ja kattavan tapahtumasarjan dynaamiseen käyttäjävuorovaikutukseen.

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Taulukon luominen {#creating-a-table}

Jotta voit luoda ja täyttää `Table`-komponentin sovelluksessa, voit ottaa seuraavat vaiheet käyttöön:

### 1. Luo entiteettiluokka {#1-create-an-entity-class}

Määritä luokka, joka edustaa niitä entiteettejä (tietoja), joita haluat näyttää taulukossa. Esimerkissä tämä luokka on MusicRecord.

```java
public class MusicRecord {
    // Kentät ja metodit kunkin tietueen attribuuttien esittämiseksi
}
```

### 2. Luo varasto {#2-create-a-repository}

Kun entiteettiluokka on luotu, käytä sitä täyttääksesi kokoelman näistä entiteeteistä halutulla datalla.

Tämän datan perusteella tarvitaan `Repository`, joka käytetään `Table`-komponentissa. `CollectionRepository`-luokka on tarjottu, jotta voit muuttaa minkä tahansa kelpaavan Java-kokoelman käyttökelpoiseksi `Repository`:ksi, ilman että sinun tarvitsee toteuttaa omaa `Repository`-luokkaa.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. Instantiate `Table` ja lisää sarakkeita {#3-instantiate-table-and-add-columns}

Luo uusi `Table`-olio ja käytä yhtä annetuista tehdasmenetelmistä lisätäksesi halutut sarakkeet uudelle `Table`-komponentille:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numero", MusicRecord::getNumber);
table.addColumn("Otsikko", MusicRecord::getTitle);
table.addColumn("Artist", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Hinta", MusicRecord::getCost);
```

### 4. Aseta `Table`-tiedot {#4-set-the-table-data}

Lopuksi, aseta Repository aiemmin luodulle `Table`-komponentille:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Vaihtoehtoisesti `setItems()`-metodille voidaan antaa mikä tahansa kelpaava Java-kokoelma, joka luo taustalla `CollectionRepository`-instanssin puolestasi.
:::

Alla on esimerkki yllä olevista vaiheista, jotka on toteutettu perus `Table`-komponentin luomiseksi:

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>
