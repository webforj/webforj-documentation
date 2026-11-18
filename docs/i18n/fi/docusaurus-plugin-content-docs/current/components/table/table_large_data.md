---
sidebar_position: 25
title: Suuret tietojoukot
slug: data
description: >-
  Käsittele suuria Table-datasettejä tehokkaasti virtuaalivierityksellä, rivien
  dynaamisella latauksella, DOM-elementtien kierrätyksellä ja overscan-asetusten
  hienosäädöllä.
_i18n_title_hash: 1b0d4950ca3cbdd2297a5f2ff1dd19c8
_i18n_description_hash: 9fb9c6561a4655fb69c94ec6235850e8
_i18n_hash: 63e45952d3d3f88d3aa435435c0905e6
---
## Virtuaalivieritys {#virtual-scrolling}

`Table`-komponentti on suunniteltu käsittelemään suuria tietojoukkoja tehokkaasti virtuaalivierityksen avulla. Virtuaalivieritys on verkkosovelluksissa käytetty tekniikka, jolla optimoidaan suurten luetteloiden tai taulukoiden renderöintiä ja suorituskykyä renderöimällä vain näytöllä näkyvät kohteet.

### Ensimmäinen renderöinti {#initial-render}

Virtuaalivieritys on suunnittelumalli, jossa aluksi renderöidään vain pieni osajoukko kohteista, jotka mahtuvat vieritettävän säilön näkyvälle alueelle. Tämä minimoi luotavien DOM-elementtien määrän ja nopeuttaa ensimmäistä renderöintikertaa.

### Dynaaminen lataaminen {#dynamic-loading}
Kun käyttäjä vierittää näkymää ylös tai alas, uusia kohteita ladataan näkymään dynaamisesti. Nämä kohteet haetaan yleensä tietolähteestä nykyisen vierityskohdan perusteella.

### Kohteiden kierrättäminen {#item-recycling}
Jokaiselle kohteelle ei luoda uutta DOM-elementtiä, vaan virtuaalivierityksessä käytetään usein olemassa olevia DOM-elementtejä uudelleen. Kun kohde siirtyy pois näkyvältä alueelta, sen DOM-elementti kierrätetään ja otetaan käyttöön uudelle näkyvälle alueelle tulevalle kohteelle. Tämä kierrätysprosessi vähentää muistin käyttöä ja parantaa suorituskykyä.

### Suorituskykyedut {#performance-benefits}

Virtuaalivierityksen tärkein etu on suorituskyvyn paraneminen erityisesti silloin, kun käsitellään suuria määriä kohteita. Se vähentää DOM-muokkauksia ja parantaa käyttöliittymän yleistä reagointikykyä.

Alla oleva `Table` näyttää kaikki olympiavoittajat. Kyseessä on suuri tietojoukko, joka hyötyy merkittävästi taulukon virtuaalivieritystoiminnosta:

<ComponentDemo
path='/webforj/tableolympicwinners'
files={['src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java']}
height='600px'
/>

## Ylirenderöinti {#overscan}

Taulukon `Overscan`-ominaisuus määrittää, kuinka monta riviä renderöidään näkyvän alueen ulkopuolelta. Asetuksen voi määrittää `setOverscan(double value)`-metodilla.

Suurempi ylirenderöinnin arvo voi vähentää renderöintikertojen tiheyttä vieritettäessä, mutta samalla renderöidään enemmän rivejä kuin kerralla on näkyvissä. Tämä voi muodostaa kompromissin renderöintisuorituskyvyn ja vierityksen sulavuuden välillä.
