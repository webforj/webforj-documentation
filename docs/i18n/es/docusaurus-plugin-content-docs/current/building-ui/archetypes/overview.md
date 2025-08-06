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

Para iniciar el desarrollo de tu aplicación webforJ, webforJ proporciona varias plantillas predefinidas, o **arquetipos**, para ayudarte a comenzar tu aplicación rápidamente. Estos arquetipos están diseñados para darte una base sólida, permitiéndote concentrarte en construir las características de tu aplicación sin preocuparte por la configuración inicial.

Elige una plantilla que mejor se adapte a las necesidades de tu proyecto, copia el comando y pégalo en tu terminal para crear tu proyecto. Cada arquetipo viene con su propio conjunto de características y configuraciones para ayudarte a comenzar de manera eficiente.

<GalleryGrid>
  <GalleryCard header="En blanco" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>Un proyecto de inicio en blanco para aplicaciones webforJ. Esta plantilla proporciona una pizarra en blanco para que construyas tu aplicación desde cero.</p>
  </GalleryCard>

  <GalleryCard header="Pestañas" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>Un proyecto con una interfaz simple de pestañas. Ideal para aplicaciones que requieren múltiples vistas o secciones accesibles a través de pestañas.</p>
  </GalleryCard>

  <GalleryCard header="Menú lateral" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>Una aplicación simple con un menú lateral y navegación en el área de contenido. Perfecto para aplicaciones que necesitan un sistema de navegación estructurado.</p>
  </GalleryCard>

  <GalleryCard header="Hola Mundo" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>El proyecto hola mundo demuestra lo básico de construir una UI con webforJ. Esta plantilla es excelente para que los principiantes comiencen rápidamente.</p>
    <div hidden>
      <p>Contenido del diálogo para el proyecto Hola Mundo.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
