---
title: Yleisnäkymä
hide_giscus_comments: true
_i18n_hash: 4174ea766ba47277c5bcb607c4111e29
---
Tämä opas on suunniteltu opastamaan sinua askel askeleelta prosessissa sovelluksen luomiseksi. Tämä sovellus, joka on suunniteltu hallitsemaan asiakastietoja, osoittaa, kuinka webforJ:ta käytetään toimivan ja käyttäjäystävällisen käyttöliittymän rakentamiseen, jossa on ominaisuuksia asiakastietojen tarkasteluun, lisäämiseen ja muokkaamiseen. Jokainen osa rakentaa edellisen päälle, mutta voit vapaasti hypätä eteenpäin tarpeen mukaan.

Jokainen vaihe tässä oppaassa tuottaa ohjelman, joka kääntyy WAR-tiedostoksi, joka voidaan ottaa käyttöön missä tahansa Java-web-sovelluspalvelimessa. Tässä oppaassa käytetään Maven Jetty -laajennusta sovelluksen paikalliseen käyttöönottoon. Tämä kevyt kokoonpano varmistaa, että sovellus käynnistyy nopeasti ja että muutokset näkyvät reaaliaikaisesti kehityksen aikana.

## Tutorial app features {#tutorial-app-features}

 - Työskentely tietojen kanssa taulukossa.
 - [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) ja omaisuuden hallinta.
 - [Reititys](../../routing/overview) ja [navigointi](../../routing/route-navigation)
 - [Tietositeet](../../data-binding/overview) ja [validointi](../../data-binding/validation/overview)

## Prerequisites {#prerequisites}

Saadaksesi parhaan hyödyn tästä oppaasta, on oletettavaa, että sinulla on perusymmärrys Java-ohjelmoinnista ja olet tuttu työkaluista kuten Maven. Jos olet uusi webforJ:n parissa, ei hätää - kehyksen perusteet käsitellään matkan varrella.

Seuraavat työkalut/resurssit tulisi olla läsnä kehityskoneellasi

<!-- vale off -->
- Java 17 tai korkeampi
- Maven
- Java IDE
- Verkkoselain
- Git (suositeltava mutta ei pakollinen)
<!-- vale on -->

:::tip webforJ Prerequisites
Katso [tämä artikkeli](../prerequisites) saadaksesi tarkemman katsauksen vaadituista työkaluista.
:::

## Sections {#sections}

Opas on jaettu seuraaviin osiin. Edisty järjestelmällisesti kattavan läpikäynnin vuoksi tai voit hypätä eteenpäin saadaksesi erityistä tietoa.

:::tip Project setup
Niille, jotka haluavat hypätä eteenpäin tiettyihin aiheisiin, on suositeltavaa ensin lukea Projektin asetukset -osa ennen jatkamista. 
:::

<DocCardList className="topics-section" />
