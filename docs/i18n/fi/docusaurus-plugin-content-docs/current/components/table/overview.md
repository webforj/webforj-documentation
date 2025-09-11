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

Luokka `Table` on monipuolinen komponentti, joka on suunniteltu esittämään taulukkomuotoista tietoa jäsennellyllä ja helposti ymmärrettävällä tavalla. Optimoitu suurten tietomäärien käsittelyyn korkealla suorituskyvyllä, tämä komponentti tarjoaa edistyksellistä visualisointia ja kattavan tapahtumavalikoiman dynaamiseen käyttäjävuorovaikutukseen.

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->


## Taulukon luominen {#creating-a-table}

Jotta voit luoda ja täyttää `Table`-komponentin sovelluksessa, voit noudattaa seuraavia vaiheita:

### 1. Luo entiteettiluokka {#1-create-an-entity-class}

Määritä luokka, joka edustaa entiteettejä (dataa), joita haluat näyttää taulukossa. Esimerkissä tämä luokka on MusicRecord.

```java
public class MusicRecord {
    // Kentät ja metodit, jotka edustavat jokaisen tietueen attributteja
}
```

### 2. Luo repositorio {#2-create-a-repository}

Kun entiteettiluokka on luotu, käytä sitä täyttämään kokoelma näistä entiteeteistä halutulla datalla.

Tämäntyyppistä dataa varten on luotava `Repository`, jota käytetään `Table`-komponentissa. `CollectionRepository`-luokka on tarjolla muuntamaan mikä tahansa kelvollinen Java-kokoelma käytettäväksi `Repository`:ksi, jolloin oman `Repository`-luokan toteuttaminen ei ole tarpeen.

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

### 3. Instanssia `Table` ja lisää sarakkeita {#3-instantiate-table-and-add-columns}

Instanssia uusi `Table`-objekti ja käytä jotakin annetuista tehdastekniikoista lisätäksesi halutut sarakkeet uudelle `Table`:lle:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numero", MusicRecord::getNumber);
table.addColumn("Otsikko", MusicRecord::getTitle);
table.addColumn("Esittäjä", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Hinta", MusicRecord::getCost);
```

### 4. Aseta `Table`-data {#4-set-the-table-data}

Viimeiseksi, aseta `Repository` aiemmin luodulle `Table`:lle:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Vaihtoehtoisesti `setItems()`-metodille voidaan antaa mikä tahansa kelvollinen Java-kokoelma, joka luo taustalla `CollectionRepository`:n puolestasi. 
:::

Alla on esimerkki yllä mainituista vaiheista, jotka on toteutettu perustavanlaatuisen `Table`-komponentin luomiseksi:


<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Tyylittely

<TableBuilder name="Table" />
