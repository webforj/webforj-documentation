---
sidebar_position: 0
title: Archetypes
description: >-
  Pick a starter archetype such as SideMenu, Tabs, Blank, or HelloWorld to
  scaffold a webforJ project with prebuilt structure and dependencies.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 6bf7d20225657d88d7a8d10ebe56b878
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

Aloita webforJ-sovelluksesi kehittäminen, webforJ tarjoaa useita ennalta määriteltyjä malleja eli **archetypeja** auttaakseen sinua aloittamaan sovelluksesi nopeasti. Nämä archetypet on suunniteltu tarjoamaan vakaa perusta, jolloin voit keskittyä sovelluksesi ominaisuuksien rakentamiseen ilman, että sinun tarvitsee huolehtia alkuasetuksesta.

Valitse malli, joka parhaiten vastaa projektisi tarpeita, kopioi komento ja liitä se terminaaliisi rakentaaksesi projektisi. Jokaisella archetypellä on oma joukko ominaisuuksia ja asetuksia, jotka auttavat sinua pääsemään tehokkaasti alkuun.

<GalleryGrid>
  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>Yksinkertainen sovellus sivuvalikolla ja navigaatiolla sisältöalueella. Täydellinen sovelluksille, jotka tarvitsevat jäsentynyttä navigointijärjestelmää.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>Projektissa on yksinkertainen välilehtiliittymä. Ihanteellinen sovelluksille, jotka tarvitsevat useita näkymiä tai osioita, joita pääsee käsiksi välilehtien kautta.</p>
  </GalleryCard>

  <GalleryCard header="Blank" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>Tyhjää aloitusprojektia webforJ-sovelluksille. Tämä malli tarjoaa puhtaan alustan, jolta voit rakentaa sovelluksesi alusta alkaen.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>Hello world -projekti havainnollistaa käyttöliittymän rakentamisen perusteita webforJ:kalla. Tämä malli on erinomainen aloittelijoille, jotka haluavat aloittaa nopeasti.</p>
    <div hidden>
      <p>Dialogisisältö HelloWorld -projektille.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
