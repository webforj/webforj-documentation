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

Om de ontwikkeling van uw webforJ-app te starten, biedt webforJ verschillende vooraf gedefinieerde sjablonen, of **archetypes**, om u te helpen uw app snel te starten. Deze archetypes zijn ontworpen om u een solide basis te bieden, zodat u zich kunt concentreren op het bouwen van de functies van uw app zonder u zorgen te maken over de initiële setup.

Kies een sjabloon die het beste bij de behoeften van uw project past, kopieer de opdracht en plak deze in uw terminal om uw project te scaffolden. Elk archetype wordt geleverd met zijn eigen set functies en configuraties om u efficiënt op weg te helpen.

<GalleryGrid>
  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>Een eenvoudige app met een zijmenu en navigatie in het contentgebied. Perfect voor toepassingen die een gestructureerd navigatiesysteem nodig hebben.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>Een project met een eenvoudige tabinterface. Ideaal voor applicaties die meerdere weergaven of secties vereisen die toegankelijk zijn via tabs.</p>
  </GalleryCard>

  <GalleryCard header="Blank" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>Een blanco startproject voor webforJ-toepassingen. Dit sjabloon biedt een schone lei vanaf waar u uw app vanaf nul kunt opbouwen.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>Het hello world-project demonstreert de basisprincipes van het bouwen van een UI met webforJ. Dit sjabloon is geweldig voor beginners om snel aan de slag te gaan.</p>
    <div hidden>
      <p>Dialooginhoud voor het HelloWorld-project.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
