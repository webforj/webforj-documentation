---
sidebar_position: 15
title: Sorting
slug: sorting
_i18n_hash: 2c6b6a04c5c33d9e3f1663d15c85a2e9
---
Lajittelu antaa käyttäjille mahdollisuuden järjestää tietoja sarakkeissa järjestyksessä, mikä tekee tiedoista helpommin luettavia ja analysoitavia. Tämä on hyödyllistä, kun käyttäjät tarvitsevat nopeasti löytää korkeimmat tai matalimmat arvot tietyssä sarakkeessa.

<ComponentDemo 
path='/webforj/tablesorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Oletuksena sarake ei ole lajittelukelpoinen, ellei sitä ole nimenomaan sallittu. Jotta voit sallia lajittelun tietyssä sarakkeessa, käytä `setSortable(true)`-metodia:

```java 
table.getColumn("Age").setSortable(true);
```

## Monilajittelu {#multi-sorting}

:::warning Monisarakkeinen lajittelu on oletuksena pois päältä webforJ:ssä `25.00`
Ennen webforj `25.00`:ta taulukot tukivat monisarakkeista lajittelua oletuksena. Versiosta `25.00` eteenpäin tämä käyttäytyminen muuttui—kehittäjien täytyy nyt erikseen sallia monisarakkeinen lajittelu.
:::

Jos monilajittelu on tarpeen, `setMultiSorting(true)` on käytettävä taulukolle. Tämä mahdollistaa käyttäjien lajittelun useissa sarakkeissa peräkkäin:

```java
table.setMultiSorting(true);
```

Monilajittelu käytössä, useiden sarakeotsikoiden klikkaaminen lajittelee ne peräkkäin. Lajittelun prioriteetti näkyy visuaalisesti taulukon käyttöliittymässä.

<ComponentDemo 
path='/webforj/tablemultisorting?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Voit myös määrittää lajittelun prioriteetin ohjelmallisesti palvelinpuolen lajittelua varten. Käytä `setSortOrder()` sarakkeissa, joita haluat lajitella, prioriteettijärjestyksessä:

```java
// Palvelinpuolen lajittelujärjestys
nameColumn.setSortOrder(1);
ageColumn.setSortOrder(2);
```

:::info Sarakejärjestyksellä on merkitystä
Ellei `setSortOrder()`-menetelmää käytetä, taulukko lajittelee oletuksena sen mukaan, missä järjestyksessä sarakkeet on ilmoitettu.
:::

<ComponentDemo 
path='/webforj/tablesortorder?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableMultiSortingView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Lajittelusuunta {#sort-direction}

Sarakkeen lajittelulle on saatavilla kolme asetusta:

- `SortDirection.ASC`: Lajittelee sarakkeen nousevassa järjestyksessä.
- `SortDirection.DESC`: Lajittelee sarakkeen laskevassa järjestyksessä.
- `SortDirection.NONE`: Sarakkeeseen ei sovelleta lajittelua.

Kun sarakkeessa on lajittelu aktivoitu, näet sarakkeen yläosassa sarjan pystysuoria nuolimerkkejä. Nämä nuolet sallivat käyttäjän vaihtaa eri lajittelusuuntien välillä.

Kun nouseva järjestys on valittu, näytetään `^`, kun taas laskevassa järjestyksessä näytetään `v`.


## Asiakas vs. palvelinpuolen lajittelu {#client-vs-server-side-sorting}

Tietojen lajittelu voidaan laajasti jakaa kahteen päämenetelmän: **Asiakaslajittelu** ja **Palvelinlajittelu**.

### Asiakaslajittelu {#client-sorting}

Asiakaslajittelu tarkoittaa tietojen järjestämistä ja näyttämistä suoraan asiakasohjelman käyttöliittymässä. Se on lajittelu, jonka käyttäjät tekevät, kun he napsauttavat sarakeotsikoita, mikä vaikuttaa tietojen visuaaliseen esitykseen näytöllä.

Kehittäjällä ei ole suoraa hallintaa asiakaspuolen lajittelusta, vaan se määräytyy Java:ssa annetun saraketyypin mukaan. Seuraavat tyypit ovat tällä hetkellä tuettuina:

- TEKSTI
- NUMERO
- TOTTA
- PÄIVÄ
- AIKA
- AIKARYHMÄ

:::info
Asiakaslajittelu ei toimi, kun vain osa tiedoista on saatavilla asiakaspuolella.
:::

### Palvelinlajittelu {#server-sorting}

Toisin kuin asiakaspuolen lajittelu, palvelinlajittelu tarkoittaa tietojen järjestämistä ja organisointia palvelimella ennen niiden siirtämistä asiakaspäähän. Tämä lähestymistapa on erityisen hyödyllinen suurten tietojoukkojen käsittelemisessä, joita ei ehkä ole käytännöllistä siirtää kokonaan asiakkaille.

Kehittäjillä on enemmän hallintaa palvelinlajittelun logiikasta. Tämä sallii monimutkaisempien lajittelualgoritmien ja -optimointien toteuttamisen, mikä tekee siitä soveltuvan laajoille tietojoukoille. Tämä varmistaa, että asiakas saa esilajiteltuja tietoja, minimoiden laajan asiakaspuolen käsittelyn tarpeen.


:::info
Palvelinlajittelu on suorituskykyyn suuntautunut strategia, joka koskee tietojoukkoja, jotka ylittävät tehokkaan asiakaspuolen käsittelyn mahdollisuudet, ja se on `Table`-komponentin oletustapa.
:::

#### Vertailijat {#comparators}

`Column`-komponentti sallii kehittäjien käyttää Java `Comparators` dynaamisiin ja mukautettuihin lajitteluvalintoihin. `Comparator` on mekanismi, jota käytetään kahden saman luokan objektin järjestämiseen, vaikka se luokka olisikin käyttäjän määrittämä. Tämä toiminnallisuus tarjoaa kehittäjille joustavuuden mukauttaa tiedon lajittelu, antaen suuremman hallinnan oletuslajittelukäyttäytymien mukaan luonnolliseen järjestykseen.

Hyödyntääksesi `Comparator`-lajittelua `Column`-komponentissa, voit käyttää `setComparator()`-metodia. Tämä metodi sallii sinun määrittää mukautettu `Comparator`-funktion, joka ohjaa lajittelulogikkaa.

<ComponentDemo 
path='/webforj/tablecolumncomparator?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableColumnComparatorView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

Yllä olevassa esimerkissä määritellään mukautettu vertailijafunktio, joka ottaa kaksi elementtiä (a ja b) ja määrittää lajittelujärjestyksen `Number`-attribuutin käsiteltyjen kokonaisarvojen perusteella.

Vertailijoiden käyttö sarakelajittelussa on erityisen hyödyllistä ei-numeeristen arvojen käsittelyssä. Ne ovat myös hyödyllisiä monimutkaisten lajittelualgoritmien toteuttamisessa.

:::info
Oletuksena `Table` käyttää palvelinpuolen lajittelua, ja lajittelee ei-primitiviarvot käyttämällä Objectin `toString()`-metodia, muuttaen ne string-arvoiksi ja lajittelemalla ne sitten.
:::
