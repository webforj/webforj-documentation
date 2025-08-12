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

Om de ontwikkeling van je webforJ-app te starten, biedt webforJ verschillende vooraf gedefinieerde sjablonen, of **archetypen**, om je te helpen je app snel te starten. Deze archetypen zijn ontworpen om je een stevige basis te geven, zodat je je kunt concentreren op het bouwen van de functies van je app zonder je zorgen te maken over de initiële setup.

Kies een sjabloon die het beste past bij de behoeften van je project, kopieer de opdracht en plak deze in je terminal om je project op te zetten. Elk archetype komt met zijn eigen set functies en configuraties om je te helpen efficiënt te starten.

<GalleryGrid>
  <GalleryCard header="Leeg" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>Een leeg starter project voor webforJ-toepassingen. Dit sjabloon biedt een schone lei om je app vanaf nul te bouwen.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>Een project met een eenvoudige tabinterface. Ideaal voor toepassingen die meerdere weergaven of secties vereisen die toegankelijk zijn via tabs.</p>
  </GalleryCard>

  <GalleryCard header="Zijmenu" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>Een simpele app met een zijmenu en navigatie in het inhoudsgebied. Perfect voor toepassingen die een gestructureerd navigatiesysteem nodig hebben.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>Het hello world-project demonstreert de basis van het bouwen van een UI met webforJ. Dit sjabloon is geweldig voor beginners om snel aan de slag te gaan.</p>
    <div hidden>
      <p>Dialooginhoud voor het HelloWorld-project.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
