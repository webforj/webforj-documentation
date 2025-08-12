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

Para comenzar el desarrollo de tu aplicación webforJ, webforJ ofrece varias plantillas predefinidas, o **arquetipos**, para ayudarte a iniciar tu aplicación rápidamente. Estos arquetipos están diseñados para proporcionarte una base sólida, permitiéndote centrarte en construir las características de tu aplicación sin preocuparte por la configuración inicial.

Elige una plantilla que se ajuste mejor a las necesidades de tu proyecto, copia el comando y pégalo en tu terminal para crear la estructura de tu proyecto. Cada arquetipo viene con su propio conjunto de características y configuraciones para ayudarte a comenzar de manera eficiente.

<GalleryGrid>
  <GalleryCard header="Blank" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>Un proyecto inicial en blanco para aplicaciones webforJ. Esta plantilla proporciona una base limpia para que construyas tu aplicación desde cero.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>Un proyecto con una interfaz de pestañas simple. Ideal para aplicaciones que requieren múltiples vistas o secciones accesibles a través de pestañas.</p>
  </GalleryCard>

  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>Una aplicación simple con un menú lateral y navegación en el área de contenido. Perfecta para aplicaciones que necesitan un sistema de navegación estructurado.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>El proyecto hello world demuestra lo básico de construir una interfaz de usuario con webforJ. Esta plantilla es excelente para principiantes que quieren comenzar rápidamente.</p>
    <div hidden>
      <p>Contenido del diálogo para el proyecto HelloWorld.</p>
    </div>
  </GalleryCard>
</GalleryGrid>
