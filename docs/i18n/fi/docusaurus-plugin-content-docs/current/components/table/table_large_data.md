---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 9431d33c6fea2dd9d4ff4b165877e7d5
---
## Virtuaalinen vieritys {#virtual-scrolling}

`Table`-komponentti on rakennettu käsittelemään suuria tietoaineistoja tehokkaasti käyttämällä virtuaalista vieritystä, joka on tekniikka, jota käytetään verkkosovelluksissa suurten luetteloiden tai taulukoiden renderöinnin ja suorituskyvyn optimoimiseksi renderöimällä vain näkyvät kohteet näytöllä.

### Alkuperäinen renderointi {#initial-render}

Virtuaalinen vieritys on suunnittelumalli, jossa aluksi renderöidään vain pieni osa kohteista, jotka mahtuvat vieritettävän säilön näkyvään alueeseen. Tämä minimoi luotujen DOM-elementtien määrän ja nopeuttaa alkuperäistä renderointiprosessia.

### Dynaaminen lataus {#dynamic-loading}
Kun käyttäjä vierittää alaspäin tai ylöspäin, uusia kohteita ladataan dynaamisesti näkymään. Nämä kohteet haetaan yleensä tietolähteestä nykyisen vieritysposition perusteella.

### Kohteiden kierrätys {#item-recycling}
Sen sijaan, että jokaiselle kohteelle luotaisiin uusi DOM-elementti, virtuaalinen vieritys usein hyödyntää olemassa olevia DOM-elementtejä. Kun kohde liikkuu näkyviltä alueilta, sen DOM-elementti kierrätetään ja käytetään uudelleen uudelle näkyvälle kohteelle. Tämä kierrätysprosessi auttaa vähentämään muistinkäyttöä ja parantaa suorituskykyä.

### Suorituskykyedut: {#performance-benefits}

Virtuaalisen vierityksen pääetu on parantunut suorituskyky, erityisesti käsiteltäessä suurta määrää kohteita. Se vähentää DOM-manipulaation määrää ja parantaa käyttöliittymän yleistä reaktiivisuutta.

Alla oleva `Table` näyttää kaikki olympiavoittajat - suuren tietoaineiston, joka hyötyy suuresti taulukon virtuaalisen vieritysominaisuuden käytöstä:

<ComponentDemo
path='/webforj/tableolympicwinners'
files={[
  'src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java',
  'src/main/java/com/webforj/samples/views/table/MusicRecord.java',
  'src/main/java/com/webforj/samples/views/table/Service.java',
]}
height='600px'
/>

## Ylikatselu {#overscan}

Taulukon `Overscan`-ominaisuuden asettaminen määrää, kuinka monta riviä renderöidään näkyvän alueen ulkopuolella. Tämän asetuksen voi määrittää `setOverscan(double value)`-menetelmällä.

Korkeampi ylikatseluarvo voi vähentää renderoinnin tiheyttä vieritettäessä, mutta se maksaa enemmän näkyvissä olevia rivejä renderöimällä kerralla. Tämä voi olla kauppatapa renderoinnin suorituskyvyn ja vierityksen sulavuuden välillä.
