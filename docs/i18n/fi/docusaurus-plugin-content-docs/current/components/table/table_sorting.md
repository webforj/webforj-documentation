---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 99281603bebefd43f033e9d0c958c366
---
Järjestäminen antaa käyttäjille mahdollisuuden laittaa tietoja sarakkeisiin järjestyksessä, mikä tekee tiedoista helpommin luettavia ja analysoitavia. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeasti suurimmat tai pienimmät arvot tietyssä sarakkeessa.

:::tip Tietojen hallinta ja kysely
Tietoa siitä, miten käyttää `Repository`-mallia tietokokoelmien hallintaan ja kyselyyn, katso [Repository-artikkeleita](/docs/advanced/repository/overview).
:::

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Oletusarvoisesti sarake ei ole lajiteltavissa, ellei se erikseen aktivoida. Voit sallia lajittelun tietyssä sarakkeessa käyttämällä `setSortable(true)`-metodia:

```java 
table.getColumn("Ikä").setSortable(true);
```

## Monilajittelu {#multi-sorting}

:::warning Monisarakkeen lajittelu on pois päältä oletuksena webforJ:ssä `25.00`
Ennen webforj `25.00`:ta taulukot tukivat monisarakkeen lajittelua oletuksena. Versiosta `25.00` alkaen tämä käyttäytyminen muuttui—kehittäjien on nyt erikseen aktivoitava monisarakkeen lajittelu.
:::

Jos monilajittelua tarvitaan, `setMultiSorting(true)` on sovellettava taulukkoon. Tämä mahdollistaa käyttäjien lajittelemien useita sarakkeita peräkkäin:

```java
table.setMultiSorting(true);
```

Kun monilajittelu on käytössä, useiden sarakeotsikoiden napsauttaminen lajittelee ne peräkkäin. Lajittelun prioriteetti näkyy visuaalisesti taulukon käyttöliittymässä.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Voit myös määrittää lajittelun prioriteetin ohjelmallisesti palvelinpuolen lajittelua varten. Käytä `setSortOrder()` niissä sarakkeissa, joita haluat lajitella, prioriteetti järjestyksessä:

```java
// Palvelinpuolen lajittelujärjestys
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Sarakkeen järjestys on tärkeä
Ellei `setSortOrder()`-metodia käytetä, taulukko lajittelee oletusarvoisesti sen mukaan, missä järjestyksessä sarakkeet on määritelty.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Lajittelusuunta {#sort-direction}

Kolme käytettävissä olevaa asetusta sarakkeen lajittelusuunnalle ovat:

- `SortDirection.ASC`: Lajittelee sarakkeen nousevassa järjestyksessä.
- `SortDirection.DESC`: Lajittelee sarakkeen laskevassa järjestyksessä.
- `SortDirection.NONE`: Ei lajittelua sarakkeelle.

Kun sarakkeella on lajittelu aktivoituna, näet sarakkeen yläosassa joukon pystysuoria nuoli-indikaattoreita. Nämä nuoleet antavat käyttäjälle mahdollisuuden vaihtaa eri lajittelusuuntien välillä.

Kun nouseva järjestys on valittu, näytetään `^`, kun taas laskevassa järjestyksessä näytetään `v`.


## Asiakas vs. palvelinpuolen lajittelu {#client-vs-server-side-sorting}

Tietojen lajittelu voidaan laajasti jakaa kahteen päämenetelmään: **Asiakaslajittelu** ja **Palvelinlajittelu**.

### Asiakaslajittelu {#client-sorting}

Asiakaslajittelu tarkoittaa tietojen järjestämistä ja näyttämistä suoraan asiakasohjelman käyttöliittymässä. Tämä on lajittelu, johon käyttäjät tekevät vuorovaikutusta napsauttamalla sarakeotsikoita, ja se vaikuttaa tietojen visuaaliseen esitykseen näytöllä.

Kehittäjällä ei ole suoraa hallintaa asiakaspuolen lajittelun osalta, vaan se määräytyy Javaan annettujen saraketyyppien mukaan. Tällä hetkellä tuetut tyypit ovat:

- TEKSTI
- NUMERO
- BOOLEAN
- PÄIVÄ
- PÄIVÄYS-AIKA
- AIKA

:::info
Asiakaslajittelu ei toimi, kun vain osa tiedoista on saatavilla asiakkaalla.
:::

### Palvelinlajittelu {#server-sorting}

Toisin kuin asiakaspuolen lajittelu, palvelinlajittelu tarkoittaa tietojen järjestämistä ja organisoimista palvelimella ennen niiden lähettämistä asiakkaalle. Tämä lähestymistapa on erityisen hyödyllinen suurten tietojoukkojen käsittelyssä, jotka voivat olla epätodennäköisiä siirtää kokonaan asiakkaalle.

Kehittäjillä on enemmän hallintaa palvelinlaitteiden lajittelulogiikasta. Tämä mahdollistaa monimutkaisten lajittelualgoritmien ja optimointien toteuttamisen, mikä tekee siitä soveltuvan laajoille tietojoukoille. Tämä varmistaa, että asiakas saa esilajitellut tiedot, vähentäen laajaa asiakaspuolen käsittelyä.

:::info
Palvelinlajittelu on suorituskykyyn perustuva strategia, joka liittyy tietojoukkoihin, jotka ylittävät tehokkaan asiakaspuolen käsittelyn kapasiteetit, ja on oletusmenetelmä, jota `Table` käyttää.
:::

### Sarakkeen ominaisuuden nimi {#column-property-name}

Oletusarvoisesti `Table` käyttää sarakkeen ID:tä ominaisuuden nimenä, kun se rakentaa lajittelukriteereitä taustajärjestelmälle. Kun sarakkeen näyttö-ID ei vastaa taustatietojen ominaisuutta tai kun sarake näyttää laskettua arvoa, käytä `setPropertyName()`-metodia kertoaksesi `Table`:lle, minkä ominaisuuden mukaan lajittaa.

```java
// Sarakkeen ID on "Koko Nimi", mutta taustatietojen ominaisuus on "fullName"
table.addColumn("Koko Nimi", Person::getFullName)
     .setSortable(true)
     .setPropertyName("fullName");
```

Ominaisuusnimi välitetään `OrderCriteria`-laitteeseen, kun lajittelutapahtuma aktivoituu, mikä mahdollistaa taustajärjestelmien, kuten Spring Data JPA:n tai REST-sovittimien, rakentaa oikean `ORDER BY`-lauseen.

:::warning
Ilman `setPropertyName()`:a `Table` palautuu sarakkeen ID:hen. Jos tämä ei vastaa voimassa olevaa taustatietojen ominaisuutta, lajittelu epäonnistuu hiljaa tai palauttaa väärin järjestettyjä tietoja.
:::

Sisäkkäiset ominaisuuspolut ovat myös tuettuja piste-merkinnällä:

```java
table.addColumn("Kaupunki", Person::getCity)
     .setSortable(true)
     .setPropertyName("address.city");
```

#### Vertailijat {#comparators}

`Column`-komponentti mahdollistaa kehittäjien käyttää Java `Comparators`-toimintoa dynaamiseen ja mukautettuun lajitteluun. `Comparator` on mekanismi, jota käytetään kahden saman luokan objektin järjestämiseen, vaikka se luokka olisi käyttäjän määrittelemä. Tämä toiminnallisuus antaa kehittäjille joustavuutta mukauttaa, miten tiedot lajitellaan, ja antaa enemmän hallintaa oletusarvoisen lajittelukäyttäytymisen osalta luonnollisen järjestyksen perusteella.

Hyödynnä `Comparator`-lajittelua `Column`-komponentissa voit käyttää `setComparator()`-metodia. Tämä metodi mahdollistaa määrittää mukautetun `Comparator`-funktion, joka määrää lajittelulogiikan.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Edellä olevassa esimerkissä määritellään mukautettu vertailufunktio, joka ottaa kaksi elementtiä (a ja b) ja määrittää lajittelujärjestyksen `Number`-attribuutin jäsennellyistä kokonaisarvoista.

Vertailijoiden käyttö sarakelajittelussa on erityisen hyödyllistä, kun käsitellään ei-numeerisia arvoja. Ne ovat myös hyödyllisiä monimutkaisten lajittelualgoritmien toteuttamisessa.

:::info
Oletusarvoisesti `Table` käyttää palvelinpuolen lajittelua ja lajittelee ei-alkuperäiset arvot käyttämällä `toString()`-metodia Object-luokassa, muuttaen ne merkkijonoarvoiksi ja lajittelemalla ne sitten.
:::
