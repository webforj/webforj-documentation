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

Um die Entwicklung Ihrer webforJ-App zu starten, bietet webforJ mehrere vordefinierte Vorlagen oder **Archetypen**, die Ihnen helfen, Ihre App schnell zu starten. Diese Archetypen sind so gestaltet, dass sie Ihnen eine solide Grundlage bieten, sodass Sie sich auf den Aufbau der Funktionen Ihrer App konzentrieren können, ohne sich um die anfängliche Einrichtung kümmern zu müssen.

Wählen Sie eine Vorlage aus, die am besten zu den Bedürfnissen Ihres Projekts passt, kopieren Sie den Befehl und fügen Sie ihn in Ihr Terminal ein, um Ihr Projekt zu erstellen. Jeder Archetyp kommt mit seinem eigenen Satz von Funktionen und Konfigurationen, um Ihnen einen effizienten Start zu ermöglichen.

<GalleryGrid>
  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>Eine einfache App mit einem Seitenmenü und Navigation im Inhaltsbereich. Perfekt für Anwendungen, die ein strukturiertes Navigationssystem benötigen.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>Ein Projekt mit einer einfachen tabbasierten Schnittstelle. Ideal für Anwendungen, die mehrere Ansichten oder Abschnitte erfordern, die über Tabs zugänglich sind.</p>
  </GalleryCard>

  <GalleryCard header="Blank" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>Ein leeres Startprojekt für webforJ-Anwendungen. Diese Vorlage bietet eine saubere Grundlage, von der aus Sie Ihre App von Grund auf neu erstellen können.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>Das Hello World-Projekt demonstriert die Grundlagen des Aufbaus einer Benutzeroberfläche mit webforJ. Diese Vorlage eignet sich hervorragend für Anfänger, um schnell zu beginnen.</p>
    <div hidden>
      <p>Dialoginhalt für das HelloWorld-Projekt.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
