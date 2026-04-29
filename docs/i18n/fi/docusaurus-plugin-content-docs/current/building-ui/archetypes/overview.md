---
sidebar_position: 0
title: Archetypes
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: c823626e55ee8a43636f750d2d456e5c
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  /* 2-column grid so each screenshot gets more space. */
  article [class*='GalleryGrid_grid'],
  article [class*='grid_'] {
    grid-template-columns: repeat(2, minmax(0, 1fr)) !important;
  }
  `}</style>
</Head>

<!-- vale off -->

import GalleryCard from '@site/src/components/GalleryCard/GalleryCard';
import GalleryGrid from '@site/src/components/GalleryGrid/GalleryGrid';

<!-- vale on -->

Aloita webforJ-sovelluksen kehitys, webforJ tarjoaa useita ennalta määriteltyjä malleja tai **archetypes**, jotka auttavat sinua aloittamaan sovelluksesi nopeasti. Nämä archetypet on suunniteltu tarjoamaan sinulle vankka perusta, mikä mahdollistaa keskittymisen sovelluksesi ominaisuuksien rakentamiseen ilman, että sinun tarvitsee murehtia alkuasetuksista.

Valitse malli, joka parhaiten vastaa projektisi tarpeita, kopioi komento ja liitä se terminaaliisi projektiasi varten. Jokaisessa archetypessä on omat ominaisuudet ja konfiguraatiot, jotka auttavat sinua pääsemään tehokkaasti alkuun.

<GalleryGrid>
  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>Yksinkertainen sovellus, jossa on sivuvalikko ja navigointi sisällön alueella. Täydellinen sovelluksiin, jotka tarvitsevat rakenteellista navigointijärjestelmää.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>Projekti, jossa on yksinkertainen välilehtikilpi. Ihanteellinen sovelluksiin, joissa tarvitaan useita näkymiä tai osioita, joihin pääsee käsiksi välilehtien kautta.</p>
  </GalleryCard>

  <GalleryCard header="Blank" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>Tyhjää aloitusprojektia webforJ-sovelluksille. Tämä malli tarjoaa puhtaan alustan sovelluksesi rakentamiselle alusta alkaen.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>Hello world -projekti esittelee käyttöliittymän perustat webforJ:llä. Tämä malli on loistava aloittelijoille, jotta he voivat päästä nopeasti alkuun.</p>
    <div hidden>
      <p>Dialogin sisältö HelloWorld-projektissa.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
