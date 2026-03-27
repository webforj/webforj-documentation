---
sidebar_position: 1
title: Table
hide_giscus_comments: true
_i18n_hash: 0d467fd377fff1994c025ba8a95c957f
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-table" />
<DocChip chip='since' label='24.00' />
<JavadocLink type="table" location="com/webforj/component/table/Table" top='true'/>

`Table`-luokka on monipuolinen komponentti, joka on suunniteltu esittämään taulukkomuotoista tietoa rakenteellisesti ja helposti ymmärrettävällä tavalla. Suunniteltu käsittelemään suuria tietojoukkoja korkealla suorituskyvyllä, tämä komponentti tarjoaa edistyksellisiä visualisointeja ja kattavan valikoiman tapahtumia dynaamiseen käyttäjävuorovaikutukseen.

<!-- INTRO_END -->

## Taulukon luominen {#creating-a-table}

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->

Jotta voit luoda ja täyttää `Table`:n sovelluksessa, voit noudattaa seuraavia vaiheita:

### 1. Luo entiteettiluokka {#1-create-an-entity-class}

Määritä luokka, joka edustaa näyttämiäsi entiteettejä (tietoja) taulukossa. Esimerkissä tämä luokka on MusicRecord.

```java
public class MusicRecord {
    // Kentät ja metodit, jotka edustavat jokaisen tietueen attribuutteja
}
```

### 2. Luo varasto {#2-create-a-repository}

Kun entiteettiluokka on luotu, käytä sitä täyttämään kokoelma näistä entiteeteistä halutuilla tiedoilla.

Tämästä datasta tarvitset `Repository`:n käytettäväksi `Table`:ssa. `CollectionRepository`-luokka on tarjoitu muuntamaan mikä tahansa kelvollinen Java-kokoelma käyttökelpoiseksi `Repository`:ksi, välttäen tarpeen toteuttaa oma `Repository`-luokka.

```java
List<MusicRecord> data = new Gson().fromJson(
    Assets.contentOf(
        Assets.resolveContextUrl("context://data/CDStore.json")
    ), new TypeToken<List<MusicRecord>>() {}
);

CollectionRepository<MusicRecord> dataRepository = new CollectionRepository<>(data);
```

:::tip Lisätietoja
Lisätietoja `Repository`-mallista webforJ:ssä löytyy [Repository-artikkeleista](/docs/advanced/repository/overview).
:::

### 3. Luo `Table` ja lisää sarakkeita {#3-instantiate-table-and-add-columns}

Luo uusi `Table`-objekti ja käytä yhtä tarjoituista tehdasmenetelmistä lisätäksesi halutut sarakkeet juuri luotuun `Table`-komponenttiin:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numero", MusicRecord::getNumber);
table.addColumn("Otsikko", MusicRecord::getTitle);
table.addColumn("Artisti", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Hinta", MusicRecord::getCost);
```

### 4. Aseta `Table`:n tiedot {#4-set-the-table-data}

Lopuksi, aseta luodun `Table`:n `Repository`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Vaihtoehtoisesti `setItems()`-menetelmälle voidaan välittää mikä tahansa kelvollinen Java-kokoelma, joka luo taustalla `CollectionRepository`:n puolestasi. 
:::

Alla on esimerkki edellä luetelluista vaiheista, jotka on toteutettu perus `Table`-komponentin luomiseksi:


<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Tyylittely

<TableBuilder name="Table" />
