---
sidebar_position: 0
title: Archetypes
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 5a88f2db8f4185a676299eace305d70f
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->

import GalleryCard from '@site/src/components/GalleryCard/GalleryCard';
import GalleryGrid from '@site/src/components/GalleryGrid/GalleryGrid';

<!-- vale on -->

Aloita webforJ-sovelluksesi kehitys webforJ:n tarjoamilla useilla ennalta määritellyillä malleilla eli **archetypeilla**, jotka auttavat sinua käynnistämään sovelluksesi nopeasti. Nämä archetypit on suunniteltu tarjoamaan vahva perusta, jotta voit keskittyä sovelluksesi ominaisuuksien rakentamiseen ilman, että sinun tarvitsee huolehtia alkuasetuksista.

Valitse malli, joka parhaiten vastaa projektisi tarpeita, kopioi komento ja liitä se terminaaliisi projektisi luomiseksi. Jokainen archetype tulee omien ominaisuuksiensa ja asetustensa kanssa, jotka auttavat sinua pääsemään tehokkaasti alkuun.

<GalleryGrid>
  <GalleryCard header="Tyhjää" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>Tyhjää aloitusprojekti webforJ-sovelluksille. Tämä malli tarjoaa puhtaan alustan, jolta voit rakentaa sovellustasi alusta alkaen.</p>
  </GalleryCard>

  <GalleryCard header="Välilehdet" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>Projekti, jossa on yksinkertainen välilehtiliittymä. Ihanteellinen sovelluksille, jotka vaativat useita näkymiä tai osioita, joihin pääsee käsiksi välilehtien kautta.</p>
  </GalleryCard>

  <GalleryCard header="SivunValikko" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>Yksinkertainen sovellus, jossa on sivuvalikko ja navigointi sisällössä. Täydellinen sovelluksille, jotka tarvitsevat jäsentynyttä navigointijärjestelmää.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>Hello world -projekti havainnollistaa käyttöliittymän rakentamisen perusteita webforJ:llä. Tämä malli on loistava aloittelijoille, jotka haluavat päästä nopeasti alkuun.</p>
    <div hidden>
      <p>Dialogin sisältö HelloWorld-projektille.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
