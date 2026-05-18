---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 16c35fa416c4ebba3b680deb2d8925ef
---
Lajittelu mahdollistaa käyttäjien järjestää tietoja sarakkeissa jonoon, mikä tekee tiedoista helpommin luettavia ja analysoitavia. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeasti löytää tietyssä sarakkeessa korkeimmat tai matalimmat arvot.

:::tip Datan hallinta ja kysely
Tietoa siitä, kuinka käyttää `Repository`-mallia kokoelmien hallintaan ja kyselyyn, katso [Repository-artikkelit](/docs/advanced/repository/overview).
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

Oletuksena sarake ei ole lajittelukelpoinen, ellei se nimenomaisesti mahdollista. Jos haluat sallia lajittelun tietyssä sarakkeessa, käytä `setSortable(true)`-metodia:

```java 
table.getColumn("Ikä").setSortable(true);
```

## Monilajittelu {#multi-sorting}

:::warning Monisarakkeinen lajittelu pois päältä oletuksena webforJ `25.00`
Ennen webforj `25.00`, taulukot tukivat monisarakkeista lajittelua oletuksena. Versiosta `25.00` alkaen tämä käyttäytyminen muuttui—kehittäjien täytyy nyt nimenomaisesti mahdollistaa monisarakkeinen lajittelu.
:::

Jos monilajittelua tarvitaan, `setMultiSorting(true)` täytyy soveltaa taulukkoon. Tämä sallii käyttäjien lajitella useita sarakkeita peräkkäin:

```java
table.setMultiSorting(true);
```

Kun monilajittelu on käytössä, useiden sarakeotsikoiden napsauttaminen lajitellaan peräkkäin. Lajittelun prioriteetti näkyy visuaalisesti taulukon käyttöliittymässä.

<ComponentDemo
path='/webforj/tablemultisorting'
files={[
  'src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Voit myös määritellä lajittelun prioriteetin ohjelmallisesti palvelinpuolen lajittelua varten. Käytä `setSortOrder()` sarakkeissa, jotka haluat lajitella, prioriteettijärjestyksessä:

```java
// Palvelinpuolen lajittelujärjestys
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Sarakejärjestyksellä on merkitystä
Ellei `setSortOrder()`-metodia käytetä, taulukko lajittelee oletuksena sarakkeiden julkaisujärjestyksen mukaan.
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

Sarake voidaan lajitella kolmella eri asetuksella:

- `SortDirection.ASC`: Lajitellaan sarake nousevaan järjestykseen.
- `SortDirection.DESC`: Lajitellaan sarake laskevaan järjestykseen.
- `SortDirection.NONE`: Sarakkeeseen ei sovelleta lajittelua.

Kun sarakkeessa on lajittelu käytössä, näet joukon pystysuoria nuoli-ilmaisin, jotka näkyvät sarakkeen yläosassa. Nämä nuolet mahdollistavat käyttäjän vaihtaa lajittelusuuntien välillä.

Kun nouseva järjestys on valittu, `^` näytetään, kun taas laskeva järjestys näyttää `v`.


## Asiakas vs. palvelinpuolen lajittelu {#client-vs-server-side-sorting}

Tietojen lajittelu voidaan pääasiassa jakaa kahteen päämenetelmään: **Asiakaslajittelu** ja **Palvelinlajittelu**.

### Asiakaslajittelu {#client-sorting}

Asiakaslajittelu tarkoittaa tietojen järjestämistä ja esittämistä suoraan asiakasohjelman käyttöliittymässä. Se on lajittelu, johon käyttäjät vaikuttavat napsauttamalla sarakeotsikoita, ja se vaikuttaa tietojen visuaaliseen esitykseen näytöllä.

Kehittäjällä ei ole suoraa kontrollia asiakaspuolen lajittelusta, vaan se määräytyy Java-ohjelmassa annettujen saraketyyppien mukaan. Seuraavat tyypit ovat tällä hetkellä tuettuina:

- TEKSTI
- NUMERO
- BOOL
- PÄIVÄ
- PÄIVÄMÄÄRÄ
- AIKA

:::info
Asiakaslajittelu ei toimi, kun vain osa tiedoista on saatavilla asiakaspuolella.
:::

### Palvelinlajittelu {#server-sorting}

Toisin kuin asiakaspuolen lajittelu, palvelinlajittelu tarkoittaa tietojen järjestämistä ja organisointia palvelimella ennen niiden siirtämistä asiakkaalle. Tämä lähestymistapa on erityisen hyödyllinen suurten tietojoukkojen käsittelyssä, joita voi olla epäkäytännöllistä siirtää kokonaan asiakkaalle.

Kehittäjillä on enemmän kontrollia palvelinlajittelun logiikan yli. Tämä mahdollistaa monimutkaisten lajittelualgoritmien ja optimointien toteuttamisen, mikä tekee siitä sopivan laajojen tietomäärien handlaamiseen. Tämä varmistaa myös, että asiakas saa etukäteen lajiteltua dataa, mikä minimoi laajamittaisen asiakaspuolen käsittelyn tarpeen.

:::info
Palvelinlajittelu on suorituskykyyn keskittyvä strategia suurten tietojoukkojen käsittelemiseen, joka ylittää tehokkaan asiakaspuolen käsittelyn mahdollisuudet, ja se on `Table`:n oletusmenetelmä.
:::

### Sarakeominaisuuden nimi {#column-property-name}

Oletuksena `Table` käyttää sarakkeen ID:tä ominaisuusnimenä taustatietovaraston lajitteluparametrien kokoamisessa. Kun sarakkeen näyttö ID ei vastaa taustatietovaraston ominaisuutta, tai kun sarake näyttää lasketun arvon, käytä `setPropertyName()`-metodia ilmoittaaksesi nimenomaisesti `Table`:lle, mikä ominaisuus lajitellaan.

```java
// Sarakkeen ID on "Koko Nimi", mutta taustatietovaraston ominaisuus on "fullName"
table.addColumn("Koko Nimi", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Ominaisuuden nimi siirretään `OrderCriteria`:lle, kun lajittelu tapahtuu, jolloin taustatietovarastot, kuten Spring Data JPA tai REST-adapterit, voivat luoda oikean `ORDER BY`-lauseen.

:::warning
Ilman `setPropertyName()`, `Table` käyttää oletuksena sarakkeen ID:tä. Jos tämä ei vastaa voimassa olevaa taustatietovaraston ominaisuutta, lajittelu epäonnistuu hiljaisesti tai palauttaa väärin järjestettyjä tietoja.
:::

Myös sisennetyt ominaisuuspolut tuetaan piste-notaatiolla:

```java
table.addColumn("Kaupunki", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vertailijat {#comparators}

`Column`-komponentti mahdollistaa kehittäjien käyttää Java `Comparators`-mekanismeja dynaamiseen ja mukautettuun lajitteluun. `Comparator` on mekanismi, jota käytetään järjestämään kaksi samaa luokkaa olevaa objektia, jopa jos kyseessä on käyttäjän määrittämä luokka. Tämä toiminnallisuus antaa kehittäjille joustavuutta mukauttaa, kuinka tietoja lajitellaan, tarjoten korkeamman kontrollin oletuslajittelukäyttäytymisestä luonnollisen järjestyksen mukaan.

Käyttääksesi `Comparator`-lajittelua `Column`:ssa, voit käyttää `setComparator()`-metodia. Tämä metodi mahdollistaa mukautetun `Comparator`-funktion määrittämisen, joka määrää lajittelulogiikan.

<ComponentDemo
path='/webforj/tablecolumncomparator'
files={[
  'src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

Yllä olevassa esimerkissä määritellään mukautettu vertailijafunktio, joka ottaa kaksi elementtiä (a ja b) ja määrittää lajittelujärjestyksen `Number`-attribuutin jäsenneltyjen kokonaisarvojen mukaan.

Vertailijoiden käyttäminen sarakelajittelussa on erityisen hyödyllistä, kun käsitellään ei-numeerisia arvoja. Ne ovat myös hyödyllisiä monimutkaisten lajittelualgoritmien toteuttamisessa.

:::info
Oletuksena `Table` käyttää palvelinpuolen lajittelua ja lajittelee ei-perinteisiä arvoja Objectin `toString()`-metodin avulla, muuttaen ne merkkijonoarvoiksi ja lajitellen ne sitten.
:::
