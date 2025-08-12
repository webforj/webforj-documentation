---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: a8c510d518375e324ae1f1f0c95b5004
---
## Virtuaalinen vieritys {#virtual-scrolling}

`Table`-komponentti on suunniteltu käsittelemään suuria tietoaineistoja tehokkaasti hyödyntämällä virtuaalista vieritystä, joka on tekniikka, jota käytetään verkkosovelluksissa suurten luetteloiden tai taulukoiden renderoinnin ja suorituskyvyn optimointiin renderoimalla vain näytöllä näkyvät kohteet.

### Alkuperäinen renderointi {#initial-render}

Virtuaalinen vieritys on suunnittelumalli, jossa aluksi renderoidaan vain pieni valikoima kohteita, jotka mahtuvat vieritettävän säiliön näkyvään alueeseen. Tämä minimoi luotujen DOM-elementtien määrän ja nopeuttaa alkuperäistä renderointiprosessia.

### Dynaaminen lataaminen {#dynamic-loading}
Kun käyttäjä vierittää alaspäin tai ylöspäin, uusia kohteita lisätään dynaamisesti näkymään. Nämä kohteet haetaan tyypillisesti tietolähteestä nykyisen vierityspaikan mukaan.

### Kohteiden kierrätys {#item-recycling}
Sen sijaan, että luotaisiin uusi DOM-elementti jokaiselle kohteelle, virtuaalinen vieritys usein käyttää olemassa olevia DOM-elementtejä uudelleen. Kun kohde siirtyy näkymättömälle alueelle, sen DOM-elementti kierrätetään ja käytetään uudelleen uudelle kohteelle, joka tulee näkyvälle alueelle. Tämä kierrätysprosessi auttaa vähentämään muistinkäyttöä ja parantaa suorituskykyä.

### Suorituskykyedut: {#performance-benefits}

Virtuaalisen vierityksen pääetuna on parantunut suorituskyky, erityisesti suurten kohdemäärien käsittelyssä. Se vähentää DOM-manipulaation määrää ja lisää käyttöliittymän yleistä reagointikykyä.

Alla oleva `Table` näyttää kaikki olympiavoittajat - suuren tietoaineiston, joka hyötyy suuresti taulukon virtuaalisen vieritysomaisuuden toiminnallisuudesta:

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Ylikierros {#overscan}

Taulukon `Overscan`-ominaisuuden asettaminen määrittää, kuinka monta riviä renderoidaan näkyvän alueen ulkopuolella. Tämä asetus voidaan määrittää `setOverscan(double value)`-menetelmällä.

Korkeampi ylikierrosarvo voi vähentää renderoinnin tiheyttä vieritettäessä, mutta se tarkoittaa myös, että renderoidaan enemmän rivejä kuin on nähtävissä kerrallaan. Tämä voi olla kompromissi renderointisuorituskyvyn ja vierityksen sujuvuuden välillä.
