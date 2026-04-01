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

`Table`-luokka on monipuolinen komponentti, joka on suunniteltu esittämään taulukkomuotoista tietoa rakenteellisesti ja helposti ymmärrettävällä tavalla. Optimoitu suurten tietomäärien käsittelyyn korkealla suorituskyvyllä, tämä komponentti tarjoaa edistyneitä visualisointimahdollisuuksia ja kattavan kokoelman tapahtumia dynaamiseen käyttäjävuorovaikutukseen.

<!-- INTRO_END -->

## Luominen `Table` {#creating-a-table}

<!-- vale off -->
<ComponentDemo 
path='/webforj/datatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/DataTableView.java'
height='600px'
/>
<!-- vale on -->

Jotta voit luoda ja täyttää `Table`-komponentin sovelluksessa, voit noudattaa seuraavia vaiheita:

### 1. Luo entiteettiluokka {#1-create-an-entity-class}

Määrittele luokka edustamaan entiteettejä (tietoja), jotka haluat esittää taulukossa. Esimerkissä tämä luokka on MusicRecord.

```java
public class MusicRecord {
  // Kentät ja metodit kunkin tietueen attribuuttien esittämiseksi
}
```

### 2. Luo varasto {#2-create-a-repository}

Kun entiteettiluokka on luotu, käytä tätä täyttääksesi kokoelman näistä entiteeteistä halutulla tiedolla.

Tästä datasta on luotava `Repository`, jota voidaan käyttää `Table`-komponentissa. `CollectionRepository`-luokka on tarjottu muuntamaan mikä tahansa voimassa oleva Java-kokoelma käyttökelpoiseksi `Repository`-luokaksi, välttäen tarpeen toteuttaa oma `Repository`-luokka.

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

### 3. Instantiate `Table` ja lisää sarakkeet {#3-instantiate-table-and-add-columns}

Instanssoi uusi `Table`-objekti ja käytä jotakin annettua tehtävämenetelmää lisäämään halutut sarakkeet uudelle luodulle `Table`-komponentille:

```java
Table<MusicRecord> table = new Table<>();
table.addColumn("Numero", MusicRecord::getNumber);
table.addColumn("Otsikko", MusicRecord::getTitle);
table.addColumn("Artist", MusicRecord::getArtist);
table.addColumn("Genre", MusicRecord::getMusicType);
table.addColumn("Hinta", MusicRecord::getCost);
```

### 4. Aseta `Table`-tiedot {#4-set-the-table-data}

Viimeiseksi, aseta edellisessä vaiheessa luodulle `Table`:lle `Repository`:

```java
table.setRepository(Service.getMusicRecords());
```

:::info
Vaihtoehtoisesti `setItems()`-menetelmään voidaan siirtää mikä tahansa voimassa oleva Java-kokoelma, mikä luo taustalla `CollectionRepository`-instanssin puolestasi. 
:::

Alla on esimerkki yllä olevista vaiheista toteutettuna perus `Table`-komponentin luomiseksi:

<ComponentDemo 
path='/webforj/tablebasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableBasicView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Tyylit

<TableBuilder name="Table" />
