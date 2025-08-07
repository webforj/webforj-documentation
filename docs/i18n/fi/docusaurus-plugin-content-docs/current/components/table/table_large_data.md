---
sidebar_position: 25
title: Large Data Sets
slug: data
_i18n_hash: 83619419eb87c85aa5e309ee153af7fb
---
## Virtuaalinen vieritystekniikka {#virtual-scrolling}

`Table`-komponentti on rakennettu tehokkaasti käsittelemään suuria tietokantoja hyödyntämällä virtuaalista vieritystä, joka on verkko- sovelluksissa käytettävä tekniikka suurten luetteloiden tai taulukoiden renderoinnin ja suorituskyvyn optimointiin renderoimalla vain näkyvissä olevat kohteet näytöllä.

### Alkuperäinen renderointi {#initial-render}

Virtuaalinen vieritys on suunnittelumalli, jossa aluksi renderoidaan vain pieni osa kohteista, jotka mahtuvat näkyvään alueeseen vieritettävässä astiassa. Tämä minimoi luotujen DOM-elementtien määrän ja nopeuttaa alkuperäistä renderointiprosessia.

### Dynaaminen lataus {#dynamic-loading}
Kun käyttäjä vierittää alaspäin tai ylöspäin, uusia kohteita ladataan dynaamisesti näkymään. Nämä kohteet haetaan tyypillisesti tietolähteestä nykyisen vierityksen mukaan.

### Kohteen kierrätys {#item-recycling}
Sen sijaan, että jokaiselle kohteelle luotaisiin uusi DOM-elementti, virtuaalinen vieritys käyttää usein olemassa olevia DOM-elementtejä uudelleen. Kun kohde siirtyy näkyvistä, sen DOM-elementti kierrätetään ja käytetään uudelleen uuden kohteen vuoksi, joka tulee näkyvään alueeseen. Tämä kierrätysprosessi auttaa vähentämään muistinkäyttöä ja parantaa suorituskykyä.

### Suorituskyvyn edut: {#performance-benefits}

Virtuaalisen vierityksen pääetuna on parannettu suorituskyky, erityisesti käsiteltäessä suurta määrää kohteita. Se vähentää DOM-manipuloinnin määrää ja parantaa käyttöliittymän yleistä reaktiokykyä.

Alla oleva `Table` näyttää kaikki olympiavoittajat - suuren tietokannan, joka hyötyy suuresti taulukon virtuaalisen vierityksen toiminnallisuudesta:

<ComponentDemo
path='/webforj/tableolympicwinners?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/TableOlympicWinnersView.java'
urls={['https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/MusicRecord.java', 
'https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/table/Service.java']}
height='600px'
/>

## Overscan {#overscan}

Taulukon `Overscan`-ominaisuuden asettaminen määrittää, kuinka monta riviä renderoidaan näkyvän alueen ulkopuolella. Tämä asetetaan `setOverscan(double value)`-menetelmällä.

Korkeampi overscan-arvo voi vähentää renderointitaajuutta vieritettäessä, mutta se maksaa enemmän rivejä, joista on näkyvissä kerrallaan. Tämä voi olla kauppatavarat renderoinnin suorituskyvyn ja vierityksen sujuvuuden välillä.
