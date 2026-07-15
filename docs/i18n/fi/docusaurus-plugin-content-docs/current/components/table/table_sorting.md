---
sidebar_position: 15
title: Sorting
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: f577bea532193b97e6fef03a8bcb641b
---
Lajittelu antaa käyttäjille mahdollisuuden järjestää tietoja sarakkeissa järjestyksen mukaan, mikä tekee tiedoista helpommin luettavia ja analysoitavia. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeasti löytää korkeimmat tai matalimmat arvot tietyssä sarakkeessa.

:::tip Tietojen hallinta ja kysely
Tietoa siitä, kuinka käyttää `Repository`-mallia hallitaksesi ja kysyäksesi kokoelmia, katso [Repository-artikkelit](/docs/advanced/repository/overview).
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

Oletuksena sarake ei ole lajittelukelpoinen, ellei se ole erikseen sallittu. Salliaksesi lajittelun tietyssä sarakkeessa, käytä `setSortable(true)` -menetelmää:

```java
table.getColumn("Ikä").setSortable(true);
```

## Monilajittelu {#multi-sorting}

:::warning Monisarakkeinen lajittelu on oletuksena pois päältä webforJ:ssä `25.00`
Ennen webforj `25.00`:aa taulukot tukivat oletuksena monisarakkeista lajittelua. Versiosta `25.00` alkaen tämä käyttäytyminen muuttui—kehittäjien täytyy nyt erikseen sallia monisarakkeinen lajittelu.
:::

Jos monilajittelu on tarpeen, `setMultiSorting(true)` on sovellettava taulukkoon. Tämä sallii käyttäjien lajitella useita sarakkeita peräkkäin:

```java
table.setMultiSorting(true);
```

Monilajittelun ollessa käytössä, useiden sarakeotsikoiden klikkaaminen lajittelee ne peräkkäin. Lajittelun prioriteetti näkyy visuaalisesti taulukkokäyttöliittymässä.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Voit myös määrittää lajittelun prioriteetin ohjelmallisesti palvelinpuolen lajittelu varten. Käytä `setSortOrder()` sarakkeilla, joita haluat lajitella, prioriteettiluettelon mukaisesti:

```java
// Palvelinpuolen lajittelujärjestys
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Sarakkeen järjestys on tärkeä
Ellei `setSortOrder()` -menetelmää käytetä, taulukko käyttää oletuksena lajittelua sarakkeiden julkaisujärjestyksen mukaan.
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

Sarake voidaan lajitella kolmessa eri suunnassa:

- `SortDirection.ASC`: Lajittelee sarakkeen nousevassa järjestyksessä.
- `SortDirection.DESC`: Lajittelee sarakkeen laskevassa järjestyksessä.
- `SortDirection.NONE`: Sarakkeeseen ei ole sovellettu lajittelua.

Kun sarakkeessa on lajittelu käytössä, näet sarakeotsikon yläpuolella joukon pystysuoria nuoli-indikaattoreita. Nämä nuolet mahdollistavat käyttäjän vaihtaa eri lajittelusuuntien välillä.

Kun nouseva järjestys on valittuna, näkyy `^`, kun taas laskevassa järjestyksessä näkyy `v`.

## Asiakas vs. palvelinpuolen lajittelu {#client-vs-server-side-sorting}

Tietojen lajittelu voidaan laajasti jakaa kahteen päämenetelmään: **Asiakaslajittelu** ja **Palvelinlajittelu**.

### Asiakaslajittelu {#client-sorting}

Asiakaslajittelu tarkoittaa, että tiedot järjestetään ja näytetään suoraan asiakasohjelman käyttöliittymässä. Se on lajittelu, johon käyttäjät vuorovaikuttavat, kun he klikata sarakeotsikoita, vaikuttaen tiedon visuaaliseen esitykseen näytöllä.

Kehittäjällä ei ole suoraa vaikutusvaltaa asiakaspuolen lajitteluun, vaan se määräytyy Java:ssa annettujen sarakkeiden tyyppien mukaan. Seuraavia tyyppejä tuetaan tällä hetkellä:

- TEKSTI
- NUMERO
- BOOL
- PÄIVÄMÄÄRÄ
- PÄIVÄMÄÄRÄ-AIKA
- AIKA

:::info
Asiakaslajittelu ei toimi, kun vain osa tiedoista on saatavilla asiakkaalla.
:::

### Palvelinlajittelu {#server-sorting}

Toisin kuin asiakaspuolen lajittelu, palvelinlajittelu tarkoittaa tietojen järjestämistä ja järjestämistä palvelimella ennen kuin ne siirretään asiakkaalle. Tämä lähestymistapa on erityisen hyödyllinen, kun käsitellään suuria tietokokonaisuuksia, joita olisi mahdotonta siirtää kokonaan asiakkaalle.

Kehittäjillä on enemmän hallintaa palvelinlajittelun logiikasta. Tämä mahdollistaa monimutkaisten lajittelualgoritmien ja optimointien toteuttamisen, minkä vuoksi se on sopiva laajoja tietokokoelmia käsitellessä. Tämä varmistaa, että asiakas saa ennakko-lajitellut tiedot, mikä vähentää tarvetta laajalle asiakaspuolen käsittelylle.

:::info
Palvelinlajittelu on suorituskykyyn keskittyvä strategia käsiteltäessä tietokokonaisuuksia, jotka ylittävät tehokkaan asiakaspuolen käsittelyn kyvyt, ja on oletusmenetelmä `Table`:ssa.
:::

### Sarakeominaisuuden nimi {#column-property-name}

Oletuksena `Table` käyttää sarakkeen ID:tä ominaisuusnimenä, kun se rakentaa lajittelukriteereitä taustajärjestelmän tallennuspaikalle. Kun sarakkeen näyttö-ID ei vastaa taustalla olevaa tietovarantoa, tai kun sarake näyttää laskentatavan arvon, käytä `setPropertyName()` kertoaksesi nimenomaisesti `Table`:lle, minkä ominaisuuden mukaan lajittelua haluat.

```java
// Sarakkeen ID on "Koko Nimi", mutta taustatieto on "fullName"
table.addColumn("Koko Nimi", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Ominaisuuden nimi välitetään `OrderCriteria`:lle, kun lajittelutapahtuma laukaistaan, jolloin taustajärjestelmien kuten Spring Data JPA tai REST-adapterit voivat rakentaa oikean `ORDER BY` -lauseen.

:::warning
Ilman `setPropertyName()` -menetelmää `Table` palauttaa takaisin sarakkeen ID:lle. Jos tämä ei vastaa voimassa olevaa taustadatan ominaisuutta, lajittelu epäonnistuu hiljaa tai palauttaa väärin järjestettyjä tietoja.
:::

Myös sisäkkäiset ominaisuudet saavat tukea piste-notaatiossa:

```java
table.addColumn("Kaupunki", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vertailijat {#comparators}

`Column`-komponentti mahdollistaa kehittäjien käyttää Java `Comparators`-työkaluja dynaamiseen ja mukautettuun lajitteluun. `Comparator` on mekanismi, joka käytetään kahden saman luokan objektin järjestämiseen, vaikka luokka olisi käyttäjän määrittämä. Tämä toiminnallisuus tarjoaa kehittäjille joustavuutta räätälöidä, miten dataa lajitellaan, tarjoten suuremman hallinnan oletuslajittelukäyttäytymiseen verrattuna luonnolliseen järjestykseen.

Voit hyödyntää `Comparator`-lajittelua sarakkeessa käyttämällä `setComparator()` -menetelmää. Tämä menetelmä sallii sinun määrittää mukautetun `Comparator`-toiminnon, joka määrää lajittelulogiikan.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Edellä olevassa esimerkissä määritellään mukautettu vertailutoiminto, joka ottaa kaksi elementtiä (a ja b), ja määrittelee lajittelujärjestyksen `Number`-attribuutin parsettujen kokonaisarvojen perusteella.

Vertailijoiden käyttö sarakelajittelussa on erityisen hyödyllistä, kun käsitellään ei-numeerisia arvoja. Ne ovat myös hyödyllisiä monimutkaisten lajittelualgoritmien toteuttamisessa.

:::info
Oletuksena `Table` käyttää palvelinpuolen lajittelua, ja lajittelee ei-primitiviarvoja käyttämällä Objectin `toString()`-menetelmää, muuntamalla ne merkkijonoarvoiksi ja sitten lajitellen niitä.
:::
