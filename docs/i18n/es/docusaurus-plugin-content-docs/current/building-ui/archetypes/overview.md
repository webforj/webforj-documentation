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

Para iniciar el desarrollo de tu aplicación webforJ, webforJ proporciona varias plantillas predefinidas, o **arquetipos**, para ayudarte a comenzar rápidamente con tu aplicación. Estos arquetipos están diseñados para darte una base sólida, permitiéndote concentrarte en construir las características de tu aplicación sin preocuparte por la configuración inicial.

Elige una plantilla que mejor se adapte a las necesidades de tu proyecto, copia el comando y pégalo en tu terminal para estructurar tu proyecto. Cada arquetipo viene con su propio conjunto de características y configuraciones para ayudarte a comenzar de manera eficiente.

<GalleryGrid>
  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>Una aplicación simple con un menú lateral y navegación en el área de contenido. Perfecta para aplicaciones que necesitan un sistema de navegación estructurado.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>Un proyecto con una interfaz de pestañas simple. Ideal para aplicaciones que requieren múltiples vistas o secciones accesibles a través de pestañas.</p>
  </GalleryCard>

  <GalleryCard header="Blank" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>Un proyecto inicial en blanco para aplicaciones webforJ. Esta plantilla proporciona una hoja en blanco para que construyas tu aplicación desde cero.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>El proyecto hello world demuestra los fundamentos de la construcción de una interfaz de usuario con webforJ. Esta plantilla es excelente para principiantes que desean comenzar rápidamente.</p>
    <div hidden>
      <p>Contenido del diálogo para el proyecto HelloWorld.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
