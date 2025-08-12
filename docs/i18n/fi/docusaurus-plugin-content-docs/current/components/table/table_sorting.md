---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: a51ea10e855e94a24cb6e74d8f774abe
---
Lajittelu antaa käyttäjille mahdollisuuden järjestää tietoja sarakkeittain järjestyksen mukaan, mikä tekee tiedoista helpommin luettavia ja analysoitavia. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeasti korkeimmat tai matalimmat arvot tietyssä sarakkeessa.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Oletusarvoisesti sarake ei ole lajittelukelpoinen, ellet erikseen mahdollista sitä. Jotta voit sallia lajittelun tietyssä sarakkeessa, käytä `setSortable(true)` -menetelmää:

```java 
table.getColumn("Ikä").setSortable(true);
```

## Monilajittelu {#multi-sorting}

:::warning Monisarakkeen lajittelu on oletusarvoisesti poistettu käytöstä webforJ:ssä `25.00`
Ennen webforj `25.00`:aa taulukot tukivat monisaraketta oletusarvoisesti. Versiosta `25.00` alkaen tämä käytäntö muuttui—kehittäjien on nyt erikseen mahdollista aktivoida monisarakkeiden lajittelu.
:::

Jos monilajittelu on tarpeen, `setMultiSorting(true)` on sovellettava taulukkoon. Tämä sallii käyttäjien lajitella useita sarakkeita peräkkäin:

```java
table.setMultiSorting(true);
```

Kun monilajittelu on mahdollista, useiden sarakeotsikoiden klikkaaminen lajittelee ne peräkkäin. Lajittelun prioriteetti näkyy visuaalisesti taulukon käyttöliittymässä.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Voit myös määrittää lajittelun prioriteetin ohjelmallisesti palvelinpuolen lajittelua varten. Käytä `setSortOrder()` sarakkeille, joita haluat lajittaa, prioriteettijärjestyksessä:

```java
// Palvelinpuolen lajittelujärjestys
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Sarakkeen järjestys on tärkeä
Ellei `setSortOrder()` -menetelmää käytetä, taulukko lajitellaan oletusarvoisesti sarakkeiden julkaisujärjestyksen mukaan.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Lajittelusuunta {#sort-direction}

Sarake voidaan lajitella kolmella eri asetuksella:

- `SortDirection.ASC`: Lajittelee sarakkeen nousevassa järjestyksessä.
- `SortDirection.DESC`: Lajittelee sarakkeen laskevassa järjestyksessä.
- `SortDirection.NONE`: Sarakkeeseen ei sovelleta lajittelua.

Kun sarakkeessa on lajittelu käytössä, näet sarakkeen yläosasessa joukon pystysuoria nuoliviittauksia. Nämä nuolet sallivat käyttäjän vaihtaa eri lajittelusuuntien välillä.

Kun nouseva järjestys on valittuna, näkyy `^`, kun taas laskevassa järjestyksessä näkyy `v`.

## Asiakas vs. palvelinpuolen lajittelu {#client-vs-server-side-sorting}

Tietojen lajittelu voidaan laajasti jakaa kahteen päämenetelmään: **Asiakaslajittelu** ja **Palvelinlajittelu**.

### Asiakaslajittelu {#client-sorting}

Asiakaslajittelu tarkoittaa tietojen järjestämistä ja näyttämistä suoraan asiakasohjelman käyttöliittymässä. Se on lajittelu, jota käyttäjät tekevät napsauttamalla sarakeotsikoita, mikä vaikuttaa tietojen visuaaliseen esitykseen näytöllä.

Kehittäjällä ei ole suoraa kontrollia asiakaspuolen lajittelusta, vaan se määräytyy Java:ssa määritellyn saraketyypin mukaan. Seuraavat tyypit ovat tällä hetkellä tuettuja:

- TEKSTI
- NUMERO
- BOOLEAAN
- PÄIVÄMÄÄRÄ
- PÄIVÄMÄÄRÄ_AIKA
- AIKA

:::info
Asiakaslajittelu ei toimi, kun vain osa tiedoista on saatavilla asiakkaalla.
:::

### Palvelinlajittelu {#server-sorting}

Toisin kuin asiakaspuolen lajittelu, palvelinlajittelu tarkoittaa tietojen järjestämistä ja organisointia palvelimella ennen niiden lähettämistä asiakkaalle. Tämä lähestymistapa on erityisen hyödyllinen suurten tietojoukkojen käsittelyssä, jotka saattavat olla epäkäytännöllisiä siirtää kokonaan asiakkaalle.

Kehittäjillä on enemmän kontrollia palvelinlajittelun logiikasta. Tämä mahdollistaa monimutkaisten lajittelualgoritmien ja optimointien toteuttamisen, mikä tekee siitä sopivan laajoille tietojoukoille. Näin varmistetaan, että asiakas saa esilajiteltua tietoa, mikä vähentää laajaa asiakaspuolen prosessointitarvetta.

:::info
Palvelinlajittelu on suorituskykyyn keskittyvä strategia tietojoukkojen käsittelyyn, jotka ylittävät tehokkaan asiakaspuolen prosessoinnin kyvyt, ja se on oletusmenetelmä, jota `Table` käyttää.
:::

#### Vertailijat {#comparators}

`Column`-komponentti antaa kehittäjille mahdollisuuden käyttää Java `Comparators`-verkkosovellusta dynaamiseen ja mukautettuun lajitteluun. `Comparator` on mekanismi, jota käytetään järjestämään kaksi saman luokan objektiä, vaikka se luokka olisi käyttäjän määrittelemä. Tämä toiminnallisuus antaa kehittäjille joustavuutta mukauttaa, kuinka tiedot lajitellaan, tarjoten suurempaa kontrollia oletuslajittelukäyttäytymiseen luonnollisen järjestyksen perusteella.

Voit hyödyntää `Comparator`-lajittelua `Column`-komponentissa käyttämällä `setComparator()`-menetelmää. Tämä menetelmä mahdollistaa mukautetun `Comparator`-funktion määrittämisen, joka määrää lajittelulogiikan.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Yllä olevassa esimerkissä määritellään mukautettu vertailijafunktio, joka ottaa kaksi elementtiä (a ja b) ja määrittelee lajittelujärjestyksen `Number`-attribuutin jäsenneltyjen kokonaislukuarvojen perusteella.

Vertailijoiden käyttäminen sarake- lajittelussa on erityisen hyödyllistä käsiteltäessä ei-numeerisia arvoja. Ne ovat myös hyödyllisiä monimutkaisten lajittelualgoritmien toteuttamisessa.

:::info
Oletusarvoisesti `Table` käyttää palvelinpuolen lajittelua ja lajittelee ei-alkuperäisiä arvoja käyttämällä `toString()`-menetelmää, muuntaen ne merkkijonoarvoiksi ja lajittaen ne sitten.
:::
