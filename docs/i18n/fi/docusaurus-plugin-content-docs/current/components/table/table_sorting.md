---
sidebar_position: 15
title: Lajittelu
slug: sorting
description: >-
  Enable per-column sorting on the Table, configure multi-column sorting, and
  set sort priority programmatically.
_i18n_hash: 7a910f5bdcaa8a9e053bcda6706808c2
---
Lajittelun avulla käyttäjät voivat järjestää sarakkeiden tietoja, mikä helpottaa tietojen lukemista ja analysointia. Tästä on hyötyä, kun käyttäjien on löydettävä nopeasti tietyn sarakkeen suurimmat tai pienimmät arvot.

:::tip Tietojen hallinta ja kyselyt
Lisätietoja `Repository`-mallin käyttämisestä kokoelmien hallintaan ja niistä tehtäviin kyselyihin on [Repository-artikkeleissa](/docs/advanced/repository/overview).
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

Saraketta ei oletusarvoisesti voi lajitella, ellei lajittelua ole erikseen otettu käyttöön. Voit sallia tietyn sarakkeen lajittelun käyttämällä `setSortable(true)`-metodia:

```java
table.getColumn("Age").setSortable(true);
```

## Monisarakelajittelu {#multi-sorting}

:::warning Usean sarakkeen lajittelu on oletusarvoisesti poistettu käytöstä webforJ `25.00` -versiossa
Ennen webforj `25.00` -versiota taulukot tukivat oletusarvoisesti usean sarakkeen lajittelua. Versiosta `25.00` alkaen tämä muuttui: kehittäjien on nyt otettava usean sarakkeen lajittelu erikseen käyttöön.
:::

Jos monisarakelajittelua tarvitaan, taulukossa on kutsuttava `setMultiSorting(true)`-metodia. Näin käyttäjät voivat lajitella useita sarakkeita peräkkäin:

```java
table.setMultiSorting(true);
```

Kun monisarakelajittelu on käytössä, useiden sarakeotsikoiden napsauttaminen lajittelee sarakkeet peräkkäin. Lajittelun prioriteetti näkyy taulukon käyttöliittymässä.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Voit myös määrittää lajittelun prioriteetin ohjelmallisesti palvelinpuolen lajittelua varten. Kutsu `setSortIndex()`-metodia niille sarakkeille, jotka haluat lajitella, prioriteettijärjestyksessä:

```java
// Server-side sort order
nameColumn.setSortIndex(1);
ageColumn.setSortIndex(2);
```

:::info Sarakkeiden järjestyksellä on merkitystä
Ellei `setSortIndex()`-metodia käytetä, taulukko lajittelee oletusarvoisesti sarakkeiden määritysjärjestyksessä.
:::

<ComponentDemo
path='/webforj/tablesortorder'
files={[
  'src/main/java/com/webforj/samples/views/table/TableSortOrderView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Lajittelusuunta {#sort-direction}

Sarakkeen lajittelusuunnalle on kolme mahdollista asetusta:

- `SortDirection.ASC`: lajittelee sarakkeen nousevaan järjestykseen.
- `SortDirection.DESC`: lajittelee sarakkeen laskevaan järjestykseen.
- `SortDirection.NONE`: saraketta ei lajitella.

Kun sarakkeen lajittelu on käytössä, sarakeotsikon yläosassa näkyy pystysuuntainen nuolipari. Näiden nuolten avulla käyttäjä voi vaihtaa lajittelusuuntaa.

Kun valittuna on nouseva järjestys, näkyvissä on `^`, ja laskevassa järjestyksessä näkyvissä on `v`.


## Asiakas- ja palvelinpuolen lajittelu {#client-vs-server-side-sorting}

Tietojen lajittelu voidaan jakaa kahteen pääasialliseen lähestymistapaan: **asiakaspuolen lajitteluun** ja **palvelinpuolen lajitteluun**.

### Asiakaspuolen lajittelu {#client-sorting}

Asiakaspuolen lajittelussa tiedot järjestetään ja näytetään suoraan asiakassovelluksen käyttöliittymässä. Kyseessä on lajittelu, jota käyttäjät käyttävät napsauttaessaan sarakeotsikoita. Se vaikuttaa tietojen visuaaliseen esitystapaan näytöllä.

Kehittäjä ei voi hallita asiakaspuolen lajittelua suoraan, vaan se määräytyy Javassa määritetyn saraketyypin mukaan. Tällä hetkellä tuetaan seuraavia tyyppejä:

- TEXT
- NUMBER
- BOOLEAN
- DATE
- DATETIME
- TIME

:::info
Asiakaspuolen lajittelu ei toimi, jos asiakkaan käytettävissä on vain osa tiedoista.
:::

### Palvelinpuolen lajittelu {#server-sorting}

Asiakaspuolen lajittelusta poiketen palvelinpuolen lajittelussa tiedot järjestetään palvelimella ennen niiden lähettämistä asiakkaalle. Tämä lähestymistapa on erityisen hyödyllinen käsiteltäessä suuria tietojoukkoja, joiden siirtäminen kokonaisuudessaan asiakkaalle voi olla epäkäytännöllistä.

Kehittäjillä on enemmän mahdollisuuksia hallita palvelinpuolen lajittelun logiikkaa. Näin voidaan toteuttaa monimutkaisia lajittelualgoritmeja ja optimointeja, mikä sopii laajojen tietojoukkojen käsittelyyn. Tällöin asiakas saa valmiiksi lajitellut tiedot, mikä vähentää laajan asiakaspuolen käsittelyn tarvetta.


:::info
Palvelinpuolen lajittelu on suorituskykyyn keskittyvä strategia sellaisten tietojoukkojen käsittelyyn, joiden koko ylittää tehokkaan asiakaspuolen käsittelyn mahdollisuudet. Se on `Table`-komponentin oletusarvoinen lajittelutapa.
:::

### Sarakkeen ominaisuuden nimi {#column-property-name}

Oletusarvoisesti `Table` käyttää sarakkeen tunnusta ominaisuuden nimenä muodostaessaan lajitteluehtoja taustajärjestelmän repositorylle. Kun sarakkeen näyttötunnus ei vastaa tietojen taustalla olevan ominaisuuden nimeä tai kun sarake näyttää lasketun arvon, kerro `setPropertyName()`-metodilla `Table`-komponentille, minkä ominaisuuden mukaan lajitellaan.

```java
// Column ID is "Full Name", but the backend property is "fullName"
table.addColumn("Full Name", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Ominaisuuden nimi välitetään `OrderCriteria`-oliolle lajittelutapahtuman yhteydessä. Näin taustajärjestelmän repositoryt, kuten Spring Data JPA tai REST-sovittimet, voivat muodostaa oikean `ORDER BY` -lausekkeen.

:::warning
Jos `setPropertyName()`-metodia ei käytetä, `Table` käyttää sarakkeen tunnusta. Jos se ei vastaa kelvollista taustajärjestelmän ominaisuutta, lajittelu epäonnistuu huomaamatta tai palauttaa väärässä järjestyksessä olevat tiedot.
:::

Myös sisäkkäisiä ominaisuuspolkuja tuetaan piste-erottimella:

```java
table.addColumn("City", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vertailijat {#comparators}

`Column`-komponentin avulla kehittäjät voivat käyttää Javan `Comparator`-vertailijoita dynaamiseen ja mukautettuun lajitteluun. `Comparator` on mekanismi, jolla järjestetään kaksi saman luokan oliota, vaikka luokka olisi käyttäjän määrittämä. Tämän toiminnon ansiosta kehittäjät voivat mukauttaa tietojen lajittelua ja hallita luonnolliseen järjestykseen perustuvaa oletuslajittelua tarkemmin.

Voit ottaa `Comparator`-lajittelun käyttöön `Column`-komponentissa käyttämällä `setComparator()`-metodia. Sen avulla voit määrittää mukautetun `Comparator`-funktion, joka määrää lajittelulogiikan.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Yllä olevassa esimerkissä määritetään mukautettu vertailijafunktio, joka ottaa kaksi alkiota (a ja b) ja määrittää lajittelujärjestyksen `Number`-määritteen jäsennettyjen kokonaislukuarvojen perusteella.

Vertailijoiden käyttö sarakkeiden lajittelussa on erityisen hyödyllistä käsiteltäessä muita kuin numeerisia arvoja. Niistä on hyötyä myös monimutkaisten lajittelualgoritmien toteuttamisessa.

:::info
Oletusarvoisesti `Table` käyttää palvelinpuolen lajittelua ja lajittelee ei-primitiiviset arvot Object-luokan `toString()`-metodilla muuntamalla ne merkkijonoiksi ja lajittelemalla sitten niiden mukaan.
:::
