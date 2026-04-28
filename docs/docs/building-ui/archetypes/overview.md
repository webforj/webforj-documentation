---
sidebar_position: 0
title: Archetypes
hide_table_of_contents: true
hide_giscus_comments: true
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

To kickstart your webforJ app development, webforJ provides several predefined templates, or **archetypes**, to help you start your app quickly. These archetypes are designed to give you a solid foundation, allowing you to focus on building your app's features without worrying about the initial setup. 

Choose a template that best fits your project's needs, copy the command, and paste it into your terminal to scaffold your project. Each archetype comes with its own set of features and configurations to help you get started efficiently.

<GalleryGrid>
  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>A simple app with a side menu and navigation in the content area. Perfect for applications that need a structured navigation system.</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>A project with a simple tabbed interface. Ideal for applications that require multiple views or sections accessible via tabs.</p>
  </GalleryCard>

  <GalleryCard header="Blank" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>A blank starter project for webforJ applications. This template provides a clean slate for you to build your app from scratch.</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>The hello world project demonstrates the basics of building a UI with webforJ. This template is great for beginners to get started quickly.</p>
    <div hidden>
      <p>Dialog content for HelloWorld project.</p>
    </div>
  </GalleryCard>
</GalleryGrid>

