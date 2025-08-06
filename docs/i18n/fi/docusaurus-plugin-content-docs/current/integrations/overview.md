---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 366829e324b872af8247a509f9c55783
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ on suunniteltu kehyksistä riippumattomaksi käyttöliittymäkerrokseksi Java-sovelluksille. Se keskittyy täysin rikkaita, komponenttipohjaisia käyttöliittymiä rakentamiseen samalla kun jättää taustarakennetta koskevat ratkaisut aivan sinun päätettäväksesi. Tämä selkeä huolehtimisen erottelu mahdollistaa sen, että webforJ toimii minkä tahansa Java-teknologiapinon kanssa, perinteisistä servleteistä moderneihin mikropalveluihin.

## Arkkitehtuurifilosofia {#architecture-philosophy}

webforJ erottaa tarkoituksella käyttöliittymän ja taustaratkaisut. Toisin kuin täysipinoiset kehykset, jotka määräävät koko sovellusrakenteesi, webforJ tarjoaa vain sen, mitä tarvitset monimutkaisten käyttöliittymien rakentamiseen. Valintasi pysyvyyden kerroksessa, riippuvuuksien injektoimisessa, turvallisuuden toteutuksessa ja palveluarkkitehtuurissa pysyy täysin riippumattomana käyttöliittymätekniikastasi.

Tämä lähestymistapa tunnustaa, että useimmilla organisaatioilla on vakiintuneita taustakäytäntöjä, olemassa olevia palvelukerroksia ja mieltymyksiään teknologiapinoissa. webforJ parantaa näitä sovelluksia modernilla käyttöliittymäkehyksellä ilman arkkitehtonisia muutoksia tai teknologiamaahanmuuttoja. Domain-logiikkasi, tiedonsiirtokäytäntösi ja turvallisuustoteutuksesi toimivat edelleen tarkalleen kuten ennenkin.

## Taustakehyksen yhteensopivuus {#backend-framework-compatibility}

webforJ toimii minkä tahansa Java-taustakehyksen tai arkkitehtuurimallin kanssa, jota jo käytät. Olitpa sitten rakentamassa Jakarta EE:llä, käyttämässä mikropalveluarkkitehtuuria tai työskentelemässä mukautetun kehyksen parissa, webforJ tarjoaa käyttöliittymäkerroksen häiritsemättä taustasuunnittelua.

Tietyille suosituimmille kehyksille webforJ tarjoaa erityisiä integraatioita, jotka vähentävät turhaa koodia ja yksinkertaistavat kehitystä. Nämä integraatiot tarjoavat etuja, kuten automaattisen riippuvuuksien injektoinnin käyttöliittymäkomponentteihin, yksinkertaistetun konfiguroinnin ja kehyskohtaisen työkalutuen. Jos et näe kehystäsi alla lueteltuna, se ei tarkoita, etteikö webforJ toimisi sen kanssa - se tarkoittaa vain, että sinun täytyy määrittää yhteys käyttäen kehykseesi standardeja käytäntöjä sen sijaan, että käyttäisit valmiiksi rakennettua integraatiota.

Alla olevat integraatiot ovat täysin valinnaisia. Ne on luotu parantamaan kehittäjäkokemusta käytettäessä tiettyjä kehyksiä, mutta webforJ:n ydintoiminnot toimivat identtisesti riippumatta siitä, käytätkö integraatiota vai et. Taustakehyksesi hallitsee edelleen palveluja, tiedonsiirtoa ja liiketoimintalogiikkaa, kun taas webforJ huolehtii esityskerroksesta.

## Aiheet {#topics}

<DocCardList className="topics-section" />
