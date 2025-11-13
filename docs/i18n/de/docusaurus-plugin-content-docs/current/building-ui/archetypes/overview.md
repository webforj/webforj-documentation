---
sidebar_position: 0
title: Archetypen
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

Um die Entwicklung Ihrer webforJ-App zu starten, bietet webforJ mehrere vordefinierte Vorlagen oder **Archetypen**, die Ihnen helfen, Ihre App schnell zu starten. Diese Archetypen sind so konzipiert, dass sie Ihnen eine solide Grundlage bieten, sodass Sie sich darauf konzentrieren können, die Funktionen Ihrer App zu entwickeln, ohne sich um die anfängliche Einrichtung sorgen zu müssen.

Wählen Sie eine Vorlage, die am besten zu den Anforderungen Ihres Projekts passt, kopieren Sie den Befehl und fügen Sie ihn in Ihr Terminal ein, um Ihr Projekt zu scaffolden. Jeder Archetyp verfügt über seine eigenen Funktionen und Konfigurationen, die Ihnen helfen, effizient zu starten.

<GalleryGrid>
  <GalleryCard header="Blank" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>Ein leeres Starterprojekt für webforJ-Anwendungen. Diese Vorlage bietet eine saubere Grundlage, um Ihre App von Grund auf neu zu erstellen.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>Ein Projekt mit einer einfachen registerkartenbasierten Benutzeroberfläche. Ideal für Anwendungen, die mehrere Ansichten oder Abschnitte erfordern, die über Registerkarten zugänglich sind.</p>
  </GalleryCard>

  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>Eine einfache App mit einem Seitenmenü und Navigation im Inhaltsbereich. Perfekt für Anwendungen, die ein strukturiertes Navigationssystem benötigen.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>Das Hello World-Projekt demonstriert die Grundlagen des Aufbaus einer Benutzeroberfläche mit webforJ. Diese Vorlage ist großartig für Anfänger, um schnell zu starten.</p>
    <div hidden>
      <p>Dialoginhalt für das HelloWorld-Projekt.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
