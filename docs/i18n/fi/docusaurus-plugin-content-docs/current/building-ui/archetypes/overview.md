---
sidebar_position: 0
title: Archetypes
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 3a6000cae65f67509fcf5bda23198a5c
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

Aloita webforJ-sovelluksesi kehitys, webforJ tarjoaa useita ennalta määriteltyjä malleja, tai **archetypeja**, jotta voit aloittaa sovelluksesi nopeasti. Nämä archetypet on suunniteltu tarjoamaan sinulle vahva perusta, jolloin voit keskittyä sovelluksesi ominaisuuksien kehittämiseen ilman huolta alkuasetuksista.

Valitse malli, joka parhaiten täyttää projektisi tarpeet, kopioi komento ja liitä se terminaaliisi projektiisi luodaksesi. Jokaisella archetypellä on omat ominaisuudet ja asetukset, jotka auttavat sinua pääsemään tehokkaasti alkuun.

<GalleryGrid>
  <GalleryCard header="Blank" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>Tyhjällä aloitusprojektilla webforJ-sovelluksille. Tämä malli tarjoaa puhtaan alustan, josta voit rakentaa sovelluksesi alusta alkaen.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>Projekti, jossa on yksinkertainen välilehtiliittymä. Ihanteellinen sovelluksille, jotka vaativat useita näkymiä tai osia, joihin pääsee käsiksi välilehtien kautta.</p>
  </GalleryCard>

  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>Yksinkertainen sovellus, jossa on sivuvalikko ja navigointi sisältöalueella. Täydellinen sovelluksille, jotka tarvitsevat rakenteellista navigointijärjestelmää.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>Hello world -projekti havainnollistaa käyttöliittymän rakentamisen perusteet webforJ:llä. Tämä malli on loistava aloittelijoille, jotka haluavat päästä alkuun nopeasti.</p>
    <div hidden>
      <p>Dialogin sisältö HelloWorld-projektille.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
