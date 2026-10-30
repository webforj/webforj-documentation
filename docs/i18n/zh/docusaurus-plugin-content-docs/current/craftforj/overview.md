---
sidebar_position: 1
title: craftforJ
slug: /craftforj
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Inspect the component tree of a running webforJ app, change components live,
  and write the changes you keep back into your Java source.
sidebar_class_name: new-content
_i18n_hash: e5fd219fd4360d24535c26696d1c9d1d
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** 是与 webforJ 一并提供的视觉开发环境。它在开发模式下运行于您的应用程序中，并实时展示您的 Java 代码所创建的组件。您可以选择组件，修改其属性，看到正在运行的应用程序立即更新，并将您希望保留的更改写回创建它们的 Java 文件中。

<!-- INTRO_END -->

因为 craftforJ 通过 webforJ 读取应用，因此它使用您编写时的术语来描述应用。树状图列出了您的组件，而不是浏览器渲染的标记，属性是您组件声明的，路由是您的路由器注册的，以及您注解的访问规则。

<div class="videos-container">
      <video controls preload="metadata">
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4#t=0.5" type="video/mp4" />
      </video>
</div>

## 您可以用它做什么 {#what-you-can-do-with-it}

- **[检查组件](/docs/craftforj/inspector)** - 浏览组件树，通过点击页面中的组件来选择，并在应用程序运行时更改其属性。
- **[将更改写入源代码](/docs/craftforj/source-changes)** - 以差异的形式查看您的实时编辑并将其应用到您的 Java 文件中。
- **[使用路由](/docs/craftforj/routes)** - 查看路由表，导航到任何路由，修改其声明的访问规则。
- **[设置应用主题](/docs/craftforj/theme)** - 调整您的应用所基于的设计令牌，并将结果保存至您的样式表中。
- **[使用 AI 代理](/docs/craftforj/ai)** - 在正在运行的应用中，编写 Java 代码的代理，能够自由编写、编译其所写内容，并在您批准后应用。

## 它与调试器的不同之处 {#how-it-differs-from-a-debugger}

调试器会暂停您的代码，并显示您变量的状态。craftforJ 则保持应用运行，并展示您的代码所生成的界面，让您与结果进行交互，而不是与执行过程打交道。这两者回答的问题不同，通常可以一起使用。

## 仅限开发模式 {#development-mode-only}

craftforJ 需要启用两个独立的设置，默认情况下，它仅对与应用运行在同一机器上的浏览器做出响应。使用 [startforJ](https://docs.webforj.com/startforj) 创建的项目或从 webforJ [原型](/docs/building-ui/archetypes/overview) 中创建的项目默认启用该功能，因此在您第一次运行时即可使用。请参阅 [安全性](/docs/craftforj/security) 以了解 craftforJ 可以访问的内容以及如何在生产环境中确认其已关闭。

## 主题 {#topics}

<DocCardList className="topics-section" />
