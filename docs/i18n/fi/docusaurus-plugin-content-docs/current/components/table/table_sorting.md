---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 94345d1313939015f02cf46ecaa5af99
---
Lajittelu antaa käyttäjille mahdollisuuden järjestää tietoja sarakkeittain järjestyksessä, mikä tekee tiedoista helpommin luettavia ja analysoitavia. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeasti löytää korkeimmat tai matalimmat arvot tietyssä sarakkeessa.

:::tip Tietojen hallinta ja kysely
Lisätietoja siitä, miten käyttää `Repository`-mallia kokoelmien hallintaan ja kyselyihin, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::

<ComponentDemo
path='/webforj/tablesorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Oletusarvoisesti sarake ei ole lajittelukelpoinen ellet erikseen mahdollista sitä. Jotta voit mahdollistaa lajittelun tietyssä sarakkeessa, käytä `setSortable(true)` -metodia:

```java
table.getColumn("Ikä").setSortable(true);
```

## Monilajittelu {#multi-sorting}

:::warning Monisarakkeinen lajittelu ei ole oletusarvoisesti käytössä webforJ:ssä `25.00`
Ennen webforj `25.00`:aa taulut tukivat monisarakkeista lajittelua oletusarvoisesti. Versiosta `25.00` alkaen tämä käyttäytyminen muuttui - kehittäjien on nyt erikseen mahdollista jälkiviat monisarakkeinen lajittelu.
:::

Jos monilajittelu on tarpeen, `setMultiSorting(true)` on sovellettava tauluun. Tämä mahdollistaa käyttäjien lajittelun useita sarakkeita peräkkäin:

```java
table.setMultiSorting(true);
```

Monilajittelu käytössä, useiden sarakeotsikoiden napsauttaminen lajittelee ne peräkkäin. Lajittelun prioriteetti näkyy visuaalisesti taulun käyttöliittymässä.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Voit myös määrittää lajitteluprioriteetin ohjelmallisesti palvelinpuolen lajittelussa. Käytä `setSortIndex()` sarakkeilla, joita haluat lajitella, prioriteettijärjestyksessä:

```java
// Palvelinpuolen lajittelu
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info Sarakejärjestys on tärkeä
Ellei `setSortIndex()` -metodia käytetä, taulu oletuksena lajittelee sarakkeiden julkaisujärjestyksen mukaan.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Lajittelusuunta {#sort-direction}

Sarake voidaan lajitella kolmeen eri suuntaan:

- `SortDirection.ASC`: Lajittelee sarakkeen nousevassa järjestyksessä.
- `SortDirection.DESC`: Lajittelee sarakkeen laskevassa järjestyksessä.
- `SortDirection.NONE`: Ei lajittelua sovelleta sarakkeeseen.

Kun sarakkeessa on lajittelu käytössä, sarakkeen setin yläosassa näkyy joukko pystysuoria nuoliviittauksia. Nämä nuolet mahdollistavat käyttäjän vaihdella eri lajittelusuuntien välillä.

Kun nouseva järjestys on valittu, näkyviin tulee `^`, kun taas laskevassa järjestyksessä näkyy `v`.

## Asiakas- vs. palvelinpuolen lajittelu {#client-vs-server-side-sorting}

Tietojen lajittelu voidaan karkeasti jakaa kahteen päälähestymistapaan: **Asiakaslajittelu** ja **Palvelinlajittelu**.

### Asiakaslajittelu {#client-sorting}

Asiakaslajittelu tarkoittaa tietojen järjestämistä ja näyttämistä suoraan asiakasohjelman käyttöliittymässä. Se on lajittelu, jonka kanssa käyttäjät ovat vuorovaikutuksessa, kun he napsauttavat sarakeotsikoita, vaikuttaen tietojen visuaaliseen esitykseen näytöllä.

Kehittäjällä ei ole suoraa valvontaa asiakaspuolen lajittelussa, vaan se määräytyy Java-kielessä annetun saraketyypin mukaan. Seuraavia tyyppejä tuetaan tällä hetkellä:

- TEKSTI
- NUMERO
- BOOLE
- PÄIVÄ
- PÄIVÄMÄÄRÄ
- AIKA

:::info
Asiakaslajittelu ei toimi, kun vain osa tiedoista on saatavilla asiakkaalla.
:::

### Palvelinlajittelu {#server-sorting}

Toisin kuin asiakaspuolen lajittelu, palvelinlajittelu tarkoittaa tietojen järjestämistä ja organisointia palvelimella ennen niiden lähettämistä asiakkaalle. Tämä lähestymistapa on erityisen hyödyllinen suurten tietojoukkojen käsittelyssä, joita voi olla epäkäytännöllistä siirtää kokonaisuudessaan asiakkaalle.

Kehittäjillä on enemmän hallintaa palvelinlajittelun logiikasta. Tämä mahdollistaa monimutkaisten lajittelualgoritmien ja optimointien toteuttamisen, mikä tekee siitä sopivan laajoille tietojoukoille. Tämä varmistaa, että asiakas saa valmiiksi lajiteltuja tietoja, vähentäen laajamittaisen asiakaspuolen käsittelyn tarpeellisuutta.

:::info
Palvelinlajittelu on suorituskykyyn keskittyvä strategia, joka liittyy tietojoukkoihin, jotka ylittävät tehokkaan asiakaspuolen käsittelyn kyvykkyydet, ja se on oletusmenetelmä, jota `Table` käyttää.
:::

### Sarakkeen ominaisuuden nimi {#column-property-name}

Oletusarvoisesti `Table` käyttää sarakkeen ID:tä ominaisuutena, kun se rakentaa lajittelukriteerejä taustakannan varastolle. Kun sarakkeen näyttö-ID ei vastaa taustalla olevaa tietoa tai kun sarake näyttää lasketun arvon, käytä `setPropertyName()` kertoaksesi selkeästi `Table`:lle, minkä ominaisuuden mukaan lajitella.

```java
// Sarake ID on "Koko Nimi", mutta taustalle ominaisuus on "fullName"
table.addColumn("Koko Nimi", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Ominaisuuden nimi välitetään `OrderCriteria`:lle, kun lajittelu tapahtuu, jolloin taustarekisterit, kuten Spring Data JPA tai REST-adapterit, voivat rakentaa oikean `ORDER BY` -lauseen.

:::warning
Ilman `setPropertyName()` -metodia `Table` palautuu sarake-ID:hen. Jos se ei vastaa voimassa olevaa taustaprosessia, lajittelu epäonnistuu hiljaisesti tai palauttaa väärin järjestettyä tietoa.
:::

Myös sisäkkäisiä ominaisuuksia tuetaan piste-notaatiossa:

```java
table.addColumn("Kaupunki", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vertailijat {#comparators}

`Column` komponentti sallii kehittäjien käyttää Java `Comparators`-työkalua dynaamiseen ja mukautettuun lajitteluun. `Comparator` on mekanismi, jota käytetään järjestämään kaksi saman luokan objektia, vaikka luokka olisi käyttäjän määrittelema. Tämä toiminnallisuus antaa kehittäjille joustavuutta mukauttaa tiedon lajittelua, jolloin he voivat tarkastella oletuslajittelukäyttäytymistä luonnollisesti.

Jos haluat käyttää `Comparator`-lajittelua sarakkeessa, voit käyttää `setComparator()`-metodia. Tämä metodi mahdollistaa mukautetun `Comparator`-funktion määrittämisen, joka määrää lajittelulogiiikan.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Yllä olevassa esimerkissä määritellään mukautettu vertailufunktio, joka ottaa kaksi elementtiä (a ja b) ja määrittää lajittelujärjestyksen `Number`-attribuutin parsitun kokonaisarvon perusteella.

Vertailijoiden käyttö sarakelajittelussa on erityisen hyödyllistä käsiteltäessä ei-numeraalisia arvoja. Ne ovat myös hyödyllisiä monimutkaisten lajittelualgoritmien toteuttamiseen.

:::info
Oletusarvoisesti `Table` käyttää palvelinpuolen lajittelua, ja lajittelee ei-primejä arvoja Objectin `toString()`-metodin avulla, muuttaen ne merkkijonoarvoiksi ja lajittelemalla ne sitten.
:::
