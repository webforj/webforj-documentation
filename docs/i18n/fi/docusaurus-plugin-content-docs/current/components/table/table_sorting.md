---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 3c9156ad5da204816bd4ce783003cbf7
---
Lajittelu antaa käyttäjille mahdollisuuden järjestää tietoja sarakkeittain, mikä tekee tiedoista helpommin luettavia ja analysoitavia. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeaa pääsyä tietyn sarakkeen korkeimpiin tai matalimpiin arvoihin.

:::tip Tietojen hallinta ja kysely
Tietoa siitä, miten käyttää `Repository`-mallia kokoelmien hallintaan ja kyselyyn, katso [Repository-artikkelit](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Oletuksena saraketta ei voi lajitella, ellet erikseen ota sitä käyttöön. Sallitaksesi lajittelun tietyssä sarakkeessa, käytä `setSortable(true)`-menetelmää:

```java 
table.getColumn("Ikä").setSortable(true);
```

## Monilajittelu {#multi-sorting}

:::warning Monisarakkeinen lajittelu on oletuksena pois käytöstä webforJ:ssä `25.00`
Ennen webforj `25.00`:aa taulut tukivat monisarakkeista lajittelua oletuksena. Versiosta `25.00` alkaen tämä käyttäytyminen muuttui—kehittäjien on nyt erikseen otettava käyttöön monisarakkeinen lajittelu.
:::

Jos monilajittelu on tarpeen, `setMultiSorting(true)` on sovellettava taululle. Tämä antaa käyttäjille mahdollisuuden lajitella useita sarakkeita peräkkäin:

```java
table.setMultiSorting(true);
```

Monilajittelun ollessa käytössä useiden sarakeotsikoiden klikkaaminen lajittelee ne peräkkäin. Lajittelun prioriteetti näkyy visuaalisesti taulun käyttöliittymässä.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Voit myös määrittää lajittelun prioriteetin ohjelmallisesti palvelinpuolen lajittelua varten. Käytä `setSortOrder()` haluamillesi sarakkeille lajittelun prioriteetin mukaan:

```java
// Palvelinpuolen lajittelujärjestys
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Sarakkeen järjestyksellä on merkitystä
Ellei `setSortOrder()`-menetelmää käytetä, taulu lajittelee oletuksena sarakkeet sen mukaan, missä järjestyksessä ne on määritelty.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Lajittelusuunta {#sort-direction}

Sarakkeen lajittelusuunnalle on kolme saatavilla olevaa asetusta:

- `SortDirection.ASC`: Lajittelee sarakkeen nousevassa järjestyksessä.
- `SortDirection.DESC`: Lajittelee sarakkeen laskevassa järjestyksessä.
- `SortDirection.NONE`: Sarakkeeseen ei ole sovellettu lajittelua.

Kun sarakkeella on lajittelu käytössä, voit nähdä joukon pystysuoria nuoli-indikaattoreita sarakkeen yläpäässä. Nämä nuolit mahdollistavat käyttäjän vaihdella eri lajittelusuuntien välillä.

Kun nouseva järjestys on valittuna, näytetään `^`, kun taas laskeva järjestys näyttää `v`.


## Asiakkaan vs. palvelimen puolen lajittelu {#client-vs-server-side-sorting}

Tietojen lajittelu voidaan laajalti luokitella kahteen päämenetelmään: **Asiakkaan lajittelu** ja **Palvelimen lajittelu**.

### Asiakkaan lajittelu {#client-sorting}

Asiakkaan lajittelu tarkoittaa tietojen järjestämistä ja näyttämistä suoraan asiakasohjelman käyttöliittymässä. Se on lajittelu, jonka kanssa käyttäjät ovat vuorovaikutuksessa napsauttaessaan sarakeotsikoita, vaikuttaen tietojen visuaaliseen esitykseen näytöllä.

Kehittäjällä ei ole suoraa hallintaa asiakaspuolen lajittelusta, vaan se määräytyy Javaan määritetyn saraketyypin mukaan. Seuraavat tyypit ovat tällä hetkellä tuettuja:

- TEKSTI
- NUMERO
- BOOLEANI
- PÄIVÄ
- PÄIVÄYS-AIKA
- AIKA

:::info
Asiakkaan lajittelu ei toimi, kun vain osa tiedoista on saatavilla asiakkaassa.
:::

### Palvelimen lajittelu {#server-sorting}

Erottaen asiakaspuolen lajittelusta, palvelimen lajittelu tarkoittaa tietojen järjestämistä ja organisoimista palvelimella ennen niiden siirtämistä asiakkaalle. Tämä lähestymistapa on erityisen hyödyllinen suurten tietojoukkojen käsittelyssä, jotka saattavat olla epäkäytännöllisiä siirtää kokonaan asiakkaalle.

Kehittäjillä on enemmän hallintaa palvelinpuolen lajittelun logiikasta. Tämä mahdollistaa monimutkaisempien lajittelualgoritmien ja optimointien toteuttamisen, mikä tekee siitä sopivan laajojen tietojen skenaarioihin. Tämä varmistaa, että asiakas saa esilajiteltua dataa, mikä minimoi laajan asiakaspuolen käsittelyn tarpeen.

:::info
Palvelimen lajittelu on suorituskykyyn suuntautunut strategia tietojoukoille, jotka ylittävät tehokkaan asiakaspuolen käsittelyn kyvyt, ja se on oletusmenetelmä, jota `Table` käyttää.
:::

#### Vertailijat {#comparators}

`Column`-komponentti mahdollistaa kehittäjien käyttää Java `Comparators`-toimintoja dynaamiseen ja mukautettuun lajitteluun. `Comparator` on mekanismi, jota käytetään järjestämään kaksi samaan luokkaan kuuluvaa objektia, vaikka kyseinen luokka olisi käyttäjän määrittelemä. Tämä toiminnallisuus tarjoaa kehittäjille joustavuutta mukauttaa, miten tiedot lajitellaan, antaen suurempaa hallintaa oletuslajittelukäyttäytymiseen verrattuna luonnolliseen järjestykseen.

Voit hyödyntää `Comparator`-lajittelua `Column`-komponentissa käyttämällä `setComparator()`-menetelmää. Tämä menetelmä mahdollistaa mukautetun `Comparator`-funktion määrittämisen, joka ohjaa lajittelu-logiikkaa.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Edellisessä esimerkissä on määritelty mukautettu vertailufunktio, joka ottaa kaksi elementtiä (a ja b) ja määrittää lajittelujärjestyksen `Number`-attribuutin käsiteltyjen kokonaislukuarvojen perusteella.

Vertailijoiden käyttäminen sarakkeen lajittelussa on erityisen hyödyllistä, kun käsitellään numeerisesti ei-juoksevia arvoja. Ne ovat myös hyödyllisiä monimutkaisten lajittelualgoritmien toteuttamiseen.

:::info
Oletuksena `Table` käyttää palvelinpuolen lajittelua ja lajittelee ei-primitiiviset arvot objekti`toString()`-menetelmän avulla, muuttaen ne merkkijonoarvoiksi ja lajittaen sitten ne.
:::
