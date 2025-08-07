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

为了启动您的 webforJ 应用程序开发，webforJ 提供了几个预定义模板或 **原型**，帮助您快速开始应用程序。这些原型旨在为您提供一个坚实的基础，让您可以专注于构建应用程序的功能，而无需担心初始设置。

选择最适合您项目需求的模板，复制命令并将其粘贴到终端以搭建您的项目。每个原型都有自己的一套功能和配置，帮助您高效地开始。

<GalleryGrid>
  <GalleryCard header="Blank" href="blank" image="/img/archetypes/blank.png" effect="none">
    <p>一个空白的 webforJ 应用程序起始项目。这个模板为您从零开始构建应用提供了一个干净的起点。</p>
  </GalleryCard>

  <GalleryCard header="Tabs" href="tabs" image="/img/archetypes/tabs.png" effect="none">
    <p>一个具有简单标签界面的项目。非常适合需要多个视图或区域通过标签访问的应用程序。</p>
  </GalleryCard>

  <GalleryCard header="SideMenu" href="sidemenu" image="/img/archetypes/sidemenu.png" effect="none">
    <p>一个简单的应用，具有侧边菜单和内容区域的导航。非常适合需要结构化导航系统的应用程序。</p>
  </GalleryCard>

  <GalleryCard header="HelloWorld" href="hello-world" image="/img/archetypes/hello-world.png" effect="none">
    <p>hello world 项目展示了使用 webforJ 构建用户界面的基础。这一模板非常适合初学者快速入门。</p>
    <div hidden>
      <p>HelloWorld 项目的对话框内容。</p>
    </div>
  </GalleryCard>
</GalleryGrid>
