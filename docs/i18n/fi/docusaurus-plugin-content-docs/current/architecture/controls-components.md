---
sidebar_position: 10
title: Komponenttiarkkitehtuuri
description: >-
  See how webforJ components are built on the DwcComponent base class, why
  component classes are final, and why composition replaces inheritance.
_i18n_hash: 13c29b780cb00a918fe6e0a98c5e783a
---
webforJ tarjoaa Java-rajapinnan verkkokomponenttikirjastoon ja hallinnoi jokaista komponenttia palvelimelta käsin. Tällä sivulla kerrotaan, miten komponentit on jäsennelty ja miten niitä voi laajentaa.

## Komponenttien rakenne {#how-components-are-structured}
Jokainen tuotteen mukana toimitettava webforJ-komponentti on Java-luokka, jolla on yksi yhteen -vastaavuus DWC-komponenttikirjaston asiakaspuolen verkkokomponenttiin. Java-luokka sisältää komponentin tilan ja API:n palvelimella, ja kehys pitää selaimessa olevan verkkokomponentin synkronoituna sen kanssa.

Käsittelet vain Java-luokkaa. Kehys huolehtii ominaisuuksista, tapahtumista ja selaimessa tapahtuvasta renderöinnistä, joten voit rakentaa verkkosovelluksia pelkällä Javalla.

## `DwcComponent`-perusluokka {#the-dwccomponent-base-class}
webforJ:n komponenttiarkkitehtuurin ytimessä on DWCComponent-perusluokka. Kaikki webforJ-komponentit perivät tämän luokan. Se hallinnoi Java-komponentin ja sen asiakaspuolen vastineen välistä yhteyttä, mukaan lukien luontia, ominaisuuksien päivityksiä ja tapahtumia.

On kuitenkin tärkeää huomata, että kehittäjät eivät saa laajentaa DWCComponent-luokkaa. Tällainen yritys johtaa ajonaikaiseen poikkeukseen, joka estää laajennuksen. Rajoitus on voimassa, jotta yhteys säilyy ehjänä ja kehittäjät eivät vahingossa käsittele sitä tavoilla, joilla voi olla tahattomia seurauksia.

### Final-luokat ja laajennusrajoitukset {#final-classes-and-extension-restrictions}
webforJ:ssä useimmat komponenttiluokat on määritelty `final`-luokiksi. Poikkeuksena ovat sisäänrakennetut HTML-elementit ja kaikki niistä perivät luokat. Tämä tarkoittaa, ettei näitä komponenttiluokkia voi laajentaa tai aliluokittaa. Tämä suunnitteluratkaisu on tarkoituksellinen ja palvelee useita tarkoituksia:

1. **Asiakaspuolen yhteyden hallinta**: Kuten aiemmin mainittiin, webforJ-komponenttiluokkien laajentaminen paljastaisi Java-komponentin ja sen asiakaspuolen vastineen välisen sisäisen yhteyden. Komponenttien toiminnan yhdenmukaisuuden ja ennakoitavuuden säilyttämiseksi tätä hallintaa rajoitetaan.

2. **Tahattomien muutosten estäminen**: Komponenttiluokkien määrittely `final`-luokiksi estää ydinkomponenttien tahattomat muutokset ja vähentää odottamattoman toiminnan tai haavoittuvuuksien riskiä.

3. **Koostekomponenttien käytön edistäminen**: Komponenttien toiminnallisuuden laajentamiseksi webforJ-kehys kannustaa kehittäjiä käyttämään koostepohjaista lähestymistapaa. Koostekomponentit ovat Java-luokkia, jotka sisältävät muita webforJ-komponentteja tai tavallisia HTML-elementtejä. Vaikka perinteistä periytymistä ei suositella, koostekomponenttien avulla voi luoda uusia mukautettuja komponentteja, jotka kapseloivat olemassa olevia komponentteja.

<!-- ## Komponenttien lisääminen DOM:iin
TODO: Kerro, miten webforJ selvittää komponentin asiakaspuolen vastineen -->

## Koostekomponentit: laajentaminen koostamisen avulla {#composite-components-extending-through-composition}
webforJ-kehyksessä koostekomponenteilla on keskeinen rooli komponenttien toiminnallisuuden laajentamisessa. Koostekomponentit ovat Java-luokkia, joita `final`-avainsana ei rajoita. Niiden avulla kehittäjät voivat luoda uusia komponentteja, jotka laajentavat yksittäisen komponentin toimintaa tai yhdistävät useita komponentteja yhdeksi koostamalla olemassa olevia komponentteja. Kehittäjien käyttöön on luotu luokkia, jotka mahdollistavat tämän toimintatavan. Katso `Composite`- ja `ElementComposite`-osiot, joissa kerrotaan, miten koostekomponentteja luodaan oikein.

Tämä lähestymistapa edistää modulaarisempaa ja joustavampaa kehitystyyliä sekä auttaa kehittäjiä rakentamaan räätälöityjä komponentteja, jotka vastaavat tiettyihin vaatimuksiin.

<!--
## Komponenttihierarkia {#component-hierarchy}
TODO: Luo ja näytä kaavio -->
