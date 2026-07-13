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

Pour commencer le développement de votre application webforJ, webforJ propose plusieurs modèles prédéfinis, ou **archétypes**, pour vous aider à démarrer rapidement votre application. Ces archétypes sont conçus pour vous fournir une base solide, vous permettant de vous concentrer sur la construction des fonctionnalités de votre application sans vous soucier de la configuration initiale.

Choisissez un modèle qui correspond le mieux aux besoins de votre projet, copiez la commande et collez-la dans votre terminal pour créer votre projet. Chaque archétype est livré avec son propre ensemble de fonctionnalités et de configurations pour vous aider à démarrer efficacement.

<GalleryGrid>
  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>Une application simple avec un menu latéral et une navigation dans la zone de contenu. Parfaite pour les applications ayant besoin d'un système de navigation structuré.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>Un projet avec une interface à onglets simple. Idéal pour les applications nécessitant plusieurs vues ou sections accessibles via des onglets.</p>
  </GalleryCard>

  <GalleryCard header="Blank" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>Un projet de démarrage vierge pour les applications webforJ. Ce modèle fournit une toile vierge pour vous permettre de construire votre application depuis zéro.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>Le projet hello world démontre les bases de la construction d'une interface utilisateur avec webforJ. Ce modèle est idéal pour les débutants qui souhaitent commencer rapidement.</p>
    <div hidden>
      <p>Contenu du dialogue pour le projet HelloWorld.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
