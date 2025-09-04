---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 987f1fb9ef8aa9e50ff4ec00320d2dd7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ on suunniteltu kehyksistä riippumattomaksi UI-kerrokseksi Java-sovelluksille. Se keskittyy yksinomaan rikkaiden, komponenttipohjaisten käyttäjäliittymien rakentamiseen, jättäen taustarakenteen päätökset täysin sinun päätettäväksesi. Tämä selkeä huolenaiheiden erottelu mahdollistaa webforJ:n toimimisen minkä tahansa Java-tekniikkapinnan kanssa, perinteisistä servlet-pohjaisista ratkaisusta moderneihin mikropalveluihin.

## Arkkitehtuurifilosofia {#architecture-philosophy}

webforJ erottelee tarkoituksellisesti UI- ja taustahuolenaiheet. Verran kunnianhimoisiin koko-pinnoituksiin, jotka säätelevät koko sovellustasi, webforJ tarjoaa vain sen, mitä tarvitset kehittyneiden käyttäjäliittymien rakentamiseen. Valintasi kestävyyskerroksesta, riippuvuuden injektointikehyksestä, turvallisuusratkaisusta ja palveluarkkitehtuurista pysyy täysin itsenäisenä UI-teknologiastasi.

Tämä lähestymistapa tunnustaa, että suurimmalla osalla organisaatioista on vakiintuneet taustamallit, olemassa olevat palvelukerrokset ja mieluisat teknologiapinot. webforJ parantaa näitä sovelluksia modernilla UI-kehyksellä ilman arkkitehtonisia muutoksia tai teknologiamigraatioita. Domain-logiikkasi, tietojen käyttömallit ja turvallisuusratkaisut toimivat yhä aivan kuten ennenkin.

## Taustakehysyhteensopivuus {#backend-framework-compatibility}

webforJ toimii minkä tahansa Java-taustakehyksen tai arkkitehtuurimallin kanssa, jota jo käytät. Olitpa rakentamassa Jakarta EE:lle, käyttämässä mikropalveluarkkitehtuuria tai työskentelemässä mukautetun kehyksen kanssa, webforJ tarjoaa UI-kerroksen häiritsemättä taustasuunnitelmaasi.

Tiettyjen suosittujen kehysten osalta webforJ tarjoaa erityisiä integraatioita, jotka vähentävät boilerplate-koodia ja tehostavat kehitystä. Nämä integraatiot tarjoavat mukavuuksia, kuten automaattisen riippuvuuden injektoinnin UI-komponentteihin, yksinkertaistetun konfiguroinnin ja kehyskohtaisen työkalutuen. Jos et näe kehystäsi mainittuna alla, se ei tarkoita, että webforJ ei toimisi sen kanssa - se yksinkertaisesti tarkoittaa, että sinun on konfiguroitava yhteys kehysmallisi vakiintuneiden perusperiaatteiden mukaan sen sijaan, että käyttäisit esivalmistettua integraatiota.

Alla olevat integraatiot ovat täysin valinnaisia. Ne on suunniteltu parantamaan kehittäjäkokemusta, kun käytetään tiettyjä kehyksiä, mutta webforJ:n ydintoiminnot toimivat identtisesti olitpa sitten käyttänyt integraatiota tai et. Taustakehyksesi jatkaa palvelujen, tietojen käyttämisen ja liiketoimintalogiikan hallintaa, kun taas webforJ käsittelee esityskerrosta.

## Aiheet {#topics}

<DocCardList className="topics-section" />
