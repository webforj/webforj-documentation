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

Om de ontwikkeling van uw webforJ-app te starten, biedt webforJ verschillende vooraf gedefinieerde sjablonen, of **archetypen**, om u te helpen uw app snel te starten. Deze archetypen zijn ontworpen om u een solide basis te geven, zodat u zich kunt concentreren op het bouwen van de functies van uw app zonder u zorgen te maken over de initiële setup.

Kies een sjabloon die het beste past bij de behoeften van uw project, kopieer de opdracht en plak deze in uw terminal om uw project op te zetten. Elk archetype heeft zijn eigen set functies en configuraties om u efficiënt op weg te helpen.

<GalleryGrid>
  <GalleryCard header="Blank" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>Een leeg startproject voor webforJ-toepassingen. Dit sjabloon biedt een schone lei waarmee u uw app vanaf nul kunt bouwen.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>Een project met een eenvoudige tab-interface. Ideaal voor toepassingen die meerdere weergaven of secties vereisen die toegankelijk zijn via tabs.</p>
  </GalleryCard>

  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>Een eenvoudige app met een zijmenu en navigatie in het contentgebied. Perfect voor toepassingen die een gestructureerd navigatiesysteem nodig hebben.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>Het hello world-project demonstreert de basisprincipes van het bouwen van een UI met webforJ. Dit sjabloon is geweldig voor beginners om snel aan de slag te gaan.</p>
    <div hidden>
      <p>Dialooginhoud voor het HelloWorld-project.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
