---
title: Overview
hide_giscus_comments: true
_i18n_hash: 4d70b1e894fa3ca05afb5a4bc6ed982d
---
Tämä opetusohjelma on suunniteltu opastamaan sinua vaihe vaiheelta sovelluksen luomisprosessissa. Tämä sovellus, jonka tarkoituksena on hallita asiakastietoja, näyttää, kuinka käyttää webforJ:ta toimivan ja käyttäjäystävällisen käyttöliittymän rakentamiseen, jossa on ominaisuuksia asiakastietojen tarkasteluun, lisäämiseen ja muokkaamiseen. Jokainen osa rakentaa edellisen päälle, mutta voit halutessasi jättää osia väliin.

Jokainen vaihe opetusohjelmassa tuottaa ohjelman, joka kootaan WAR-tiedostoksi, jota voidaan julkaista mille tahansa Java-web-sovellipalvelimelle. Tässä opetusohjelmassa käytetään Maven Jetty -laajennusta sovelluksen paikalliseen julkaisuun. Tämä kevyt asetelma varmistaa, että sovellus voi käynnistyä nopeasti ja että muutokset näkyvät reaaliaikaisesti kehityksen aikana.

## Opetusohjelmasovelluksen ominaisuudet {#tutorial-app-features}

 - Työskentely tietojen kanssa taulukkona.
 - Käyttäen [`ObjectTable`](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/ObjectTable.html) ja omaisuuden hallintaa.
 - [Reititys](../../routing/overview) ja [navigointi](../../routing/route-navigation)
 - [Databindings](../../data-binding/overview) ja [validointi](../../data-binding/validation/overview)

## Esivaatimukset {#prerequisites}

Saadaksesi kaiken irti tästä opetusohjelmasta, oletetaan, että sinulla on perusymmärrys Java-ohjelmoinnista ja olet tuttu työkalujen, kuten Maven, kanssa. Jos olet uusi webforJ:n käyttäjä, älä huoli - kehyksen perusteet käsitellään matkan varrella.

Seuraavat työkalut/resurssit tulisi olla läsnä kehityskoneellasi:

<!-- vale off -->
- Java 17 tai uudempi
- Maven
- Java IDE
- Verkkoselain
- Git (suositeltava mutta ei pakollinen)
<!-- vale on -->

:::tip webforJ Esivaatimukset
Katso [tämä artikkeli](../prerequisites) saadaksesi tarkempaa tietoa vaadituista työkaluista.
:::

## Osat {#sections}

Opetusohjelma on jaettu seuraaviin osiin. Siirry järjestyksessä kattavan oppaan saamiseksi tai ohita tarpeen mukaan erityiseen tietoon.

:::tip Projektin asetukset
Niille, jotka haluavat ohittaa tietyt aiheet, on suositeltavaa ensin lukea Projektin asetukset -osa ennen eteenpäin siirtymistä.
:::

<DocCardList className="topics-section" />
