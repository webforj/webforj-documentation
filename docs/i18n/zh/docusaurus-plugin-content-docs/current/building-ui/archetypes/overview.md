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

为了启动您的 webforJ 应用程序开发，webforJ 提供了几个预定义模板或 **原型**，帮助您快速开始应用程序。这些原型旨在为您提供坚实的基础，使您能够专注于构建应用程序的功能，而无需担心初始设置。

选择最符合您项目需求的模板，复制命令并粘贴到终端中以搭建项目。每个原型都有自己的一套功能和配置，帮助您高效启动。

<GalleryGrid>
  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/light/sidemenu.webp" imageDark="/img/archetypes/dark/sidemenu.webp" effect="none">
    <p>一个带有侧边菜单和内容区域导航的简单应用程序。非常适合需要结构化导航系统的应用。</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/light/tabs.webp" imageDark="/img/archetypes/dark/tabs.webp" effect="none">
    <p>一个具有简单选项卡界面的项目。非常适合需要通过选项卡访问多个视图或部分的应用程序。</p>
  </GalleryCard>

  <GalleryCard header="Blank" href="blank" image="/img/archetypes/light/blank.webp" imageDark="/img/archetypes/dark/blank.webp" effect="none">
    <p>一个用于 webforJ 应用程序的空白启动项目。此模板为您提供了一个干净的起点，以便从头开始构建您的应用。</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/light/hello-world.webp" imageDark="/img/archetypes/dark/hello-world.webp" effect="none">
    <p>hello world 项目演示了如何使用 webforJ 构建用户界面的基础知识。此模板非常适合初学者快速入门。</p>
    <div hidden>
      <p>HelloWorld 项目的对话框内容。</p>
    </div>
  </GalleryCard>
</GalleryGrid>
