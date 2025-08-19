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

Luokka `Table` on monipuolinen komponentti, joka on suunniteltu esittämään taulukkomuotoista tietoa rakenteellisesti ja helposti ymmärrettävästi. Optimoitu suurten tietoaineistojen käsittelyyn korkealla suorituskyvyllä, tämä komponentti tarjoaa edistyksellisiä visualisointeja ja kattavan valikoiman tapahtumia dynaamiseen käyttäjien sitoutumiseen.

<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>


## Taulukon luominen {#creating-a-table}

Jotta voit luoda ja täyttää `Table`-komponentin sovelluksessa, voit seurata seuraavia vaiheita:

### 1. Luo entiteettiluokka {#1-create-an-entity-class}

Määrittele luokka, joka edustaa niitä entiteettejä (dataa), jotka haluat näyttää taulukossa. Esimerkissä tämä luokka on MusicRecord.

```java
public class MusicRecord {
    // Kentät ja metodit, jotka edustavat kunkin tietueen ominaisuuksia
}
```

### 2. Luo repository {#2-create-a-repository}

Kun entiteettiluokka on luotu, käytä sitä täyttämään kokoelma näistä entiteeteistä halutulla datalla.

Tästä datasta on luotava `Repository`, jota käytetään `Table`-komponentissa. `CollectionRepository`-luokka on saatavilla, jotta voit muuttaa minkä tahansa kelvollisen Java-kokoelman käytettävissä olevaksi `Repositoryksi`, jolloin oman `Repository`-luokan toteuttamista ei tarvitse.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

### 3. Instansoi `Table` ja lisää sarakkeet {#3-instantiate-table-and-add-columns}

Instansoi uusi `Table`-objekti ja käytä yhtä tarjottuista tehdastekniikoista lisätäksesi halutut sarakkeet juuri luotuun `Table`-komponenttiin:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numero", MusicRecord::getNumber);
table.addColumn("Otsikko", MusicRecord::getTitle);
table.addColumn("Artisti", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Hinta", MusicRecord::getCost);
```

### 4. Aseta `Table`-data {#4-set-the-table-data}

Lopuksi, aseta edellisessä vaiheessa luotun `Table`-komponentin `Repository`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Vaihtoehtoisesti, `setItems()`-metodille voidaan antaa mikä tahansa kelvollinen Java-kokoelma, joka luo taustalla `CollectionRepository`-komponentin sinulle. 
:::

Alla on esimerkki yllä olevista vaiheista, jotka on toteutettu luomaan perus `Table`-komponentti:


<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj/documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Tyylittely

<TableBuilder name="Table" />
