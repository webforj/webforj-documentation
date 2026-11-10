---
title: Integraatiot
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
description: >-
  Combine webforJ with Spring Boot, Spring Data, Kotlin DSL, Webswing, JBang,
  and AI tooling while keeping your backend stack independent.
_i18n_hash: 2272fe4265260266e667c1b22703a3b6
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ on liiketoiminnan verkkosovelluksiin tarkoitettu täyden pinon Java-kehys, joka ei ole sidottu tiettyyn taustateknologiaan. Rakennat koko sovelluksen Javalla näytöistä ja reitityksestä lomakkeisiin, tietojen sidontaan ja reittien suojaukseen, kun taas pysyväistallennuskerros, riippuvuuksien injektointi, palvelut ja tausta-arkkitehtuuri ovat omassa päätäntävallassasi. Siksi webforJ toimii minkä tahansa Java-teknologiapinon kanssa perinteisistä servleteistä Spring Bootiin ja mikropalveluihin.

## Arkkitehtuurifilosofia {#architecture-philosophy}

webforJ tarjoaa kaiken, mitä sovelluksesi tarvitsee tietojen esittämiseen käyttäjille ja niiden käsittelyyn: komponentit, asettelut, reitityksen, sidontaa ja validointia sisältävät lomakkeet, tietovarastojen taustalla toimivat taulukot, reittien suojauksen ja käännökset. Se ei määrää, miten järjestelmäsi muu osa toimii. Pysyvän tallennuskerroksen, riippuvuuksien injektointikehyksen, identiteetintarjoajan ja palveluarkkitehtuurin valinta on webforJ:stä riippumaton.

Tässä lähestymistavassa tunnistetaan, että useimmilla organisaatioilla on vakiintuneet taustajärjestelmien toimintamallit, olemassa olevat palvelukerrokset ja suosimansa teknologiapinot. webforJ tuo nämä sovellukset verkkoon edellyttämättä arkkitehtuurimuutoksia tai teknologiasiirtymiä. Toimialueen logiikka, tietojen käyttömallit ja tietoturvatoteutukset toimivat edelleen täsmälleen kuten ennenkin.

## Taustajärjestelmäkehysten yhteensopivuus {#backend-framework-compatibility}

webforJ toimii minkä tahansa jo käyttämäsi Java-taustajärjestelmäkehyksen tai arkkitehtuurimallin kanssa. Rakennatpa sovellusta Jakarta EE:n päälle, käytät mikropalveluarkkitehtuuria tai työskentelet mukautetun kehyksen kanssa, webforJ kytkeytyy siihen vaikuttamatta taustajärjestelmäsi suunnitteluun.

Joillekin suosituille kehyksille webforJ tarjoaa erityisiä integraatioita, jotka vähentävät toistuvan koodin määrää ja sujuvoittavat kehitystä. Nämä integraatiot tarjoavat hyödyllisiä ominaisuuksia, kuten riippuvuuksien automaattisen injektoinnin käyttöliittymäkomponentteihin, yksinkertaistetun konfiguroinnin ja kehyskohtaisen työkalutuen. Jos et näe käyttämääsi kehystä alla olevassa luettelossa, se ei tarkoita, ettei webforJ toimisi sen kanssa. Se tarkoittaa, että määrität yhteyden käyttämällä kehyksesi vakiintuneita toimintamalleja valmiin integraation sijaan.

Alla kuvatut integraatiot ovat täysin valinnaisia. Ne parantavat kehittäjäkokemusta tiettyjen kehysten kanssa, mutta webforJ:n ydintoiminnot toimivat samalla tavalla riippumatta siitä, käytätkö integraatiota. Taustajärjestelmäkehyksesi hallitsee edelleen palveluita, tietojen käyttöä ja liiketoimintalogiikkaa, kun taas webforJ vastaa näytöistä, navigoinnista ja käyttäjän toiminnasta.

## Aiheet {#topics}

<DocCardList className="topics-section" />
