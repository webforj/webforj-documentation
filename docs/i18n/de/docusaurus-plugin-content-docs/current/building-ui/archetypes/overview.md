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
  /* 2-spaltiges Grid, damit jeder Screenshot mehr Platz erhält. */
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

Um die Entwicklung Ihrer webforJ-App zu starten, bietet webforJ mehrere vordefinierte Vorlagen oder **Archetypen**, die Ihnen helfen, Ihre App schnell zu starten. Diese Archetypen sind so konzipiert, dass sie Ihnen eine solide Grundlage bieten, damit Sie sich auf den Aufbau der Funktionen Ihrer App konzentrieren können, ohne sich um die anfängliche Einrichtung kümmern zu müssen.

Wählen Sie eine Vorlage aus, die am besten zu den Anforderungen Ihres Projekts passt, kopieren Sie den Befehl und fügen Sie ihn in Ihr Terminal ein, um Ihr Projekt zu erstellen. Jeder Archetyp verfügt über sein eigenes Set an Funktionen und Konfigurationen, um Ihnen den Einstieg zu erleichtern.

<GalleryGrid>
  <GalleryCard header="Seitenmenü" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>Eine einfache App mit einem Seitenmenü und Navigation im Inhaltsbereich. Perfekt für Anwendungen, die ein strukturiertes Navigationssystem benötigen.</p>
  </GalleryCard>

  <GalleryCard header="Reiter" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>Ein Projekt mit einer einfachen Reiteroberfläche. Ideal für Anwendungen, die mehrere Ansichten oder Abschnitte erforden, die über Reiter zugänglich sind.</p>
  </GalleryCard>

  <GalleryCard header="Leer" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>Ein leeres Starterprojekt für webforJ-Anwendungen. Diese Vorlage bietet eine leere Seite, auf der Sie Ihre App von Grund auf neu aufbauen können.</p>
  </GalleryCard>

  <GalleryCard header="HalloWelt" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>Das Hallo Welt-Projekt demonstriert die Grundlagen des Aufbaus einer UI mit webforJ. Diese Vorlage ist hervorragend für Anfänger, um schnell loszulegen.</p>
    <div hidden>
      <p>Dialoginhalt für das HalloWelt-Projekt.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
