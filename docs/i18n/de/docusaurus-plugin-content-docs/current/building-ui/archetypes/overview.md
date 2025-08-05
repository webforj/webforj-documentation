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

Um die Entwicklung Ihrer webforJ-App zu starten, bietet webforJ mehrere vordefinierte Vorlagen oder **Archetypen**, die Ihnen helfen, Ihre App schnell zu beginnen. Diese Archetypen sind so konzipiert, dass sie Ihnen eine solide Grundlage bieten, sodass Sie sich auf den Aufbau der Funktionen Ihrer App konzentrieren können, ohne sich um die anfängliche Einrichtung kümmern zu müssen.

Wählen Sie eine Vorlage aus, die am besten zu den Anforderungen Ihres Projekts passt, kopieren Sie den Befehl und fügen Sie ihn in Ihr Terminal ein, um Ihr Projekt zu erstellen. Jeder Archetyp verfügt über ein eigenes Set an Funktionen und Konfigurationen, um Ihnen den effizienten Start zu erleichtern.

<GalleryGrid>
  <GalleryCard header="Blank" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>Ein leeres Starterprojekt für webforJ-Anwendungen. Diese Vorlage bietet Ihnen eine saubere Grundlage, um Ihre App von Grund auf zu erstellen.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>Ein Projekt mit einer einfachen Tab-Oberfläche. Ideal für Anwendungen, die mehrere Ansichten oder Sektionen erfordern, die über Tabs zugänglich sind.</p>
  </GalleryCard>

  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>Eine einfache App mit einem Seitenmenü und Navigation im Inhaltsbereich. Perfekt für Anwendungen, die ein strukturiertes Navigationssystem benötigen.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>Das Hello World-Projekt zeigt die Grundlagen des Aufbaus einer Benutzeroberfläche mit webforJ. Diese Vorlage eignet sich hervorragend für Anfänger, um schnell zu starten.</p>
    <div hidden>
      <p>Dialoginhalt für das HelloWorld-Projekt.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
