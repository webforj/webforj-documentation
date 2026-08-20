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
_i18n_hash: 6b642a9d173c5943acbb99934542e3a3
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<DocChip chip='since' label='26.02' />

**craftforJ** 是与 webforJ 一起提供的视觉开发环境。它在开发模式下运行于您的应用程序中，提供您 Java 代码所创建组件的实时视图。您可以选择一个组件，修改其属性，立即看到运行中的应用程序更新，并将您想要保留的更改写回创建它们的 Java 文件中。

<!-- INTRO_END -->

因为 craftforJ 是通过 webforJ 自身读取应用程序，所以它以您编写的术语描述该应用程序。树形结构列出了您的组件，而不是浏览器呈现的标记，属性则是您的组件声明的内容，路由是您已注册的路由，以及您为其注释的访问规则。

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4" type="video/mp4" />
      </video>
</div>

## 您可以用它做什么 {#what-you-can-do-with-it}

- **[检查组件](/docs/craftforj/inspector)** - 浏览组件树，在页面中单击选择组件，并在应用程序运行时更改其属性。
- **[将更改写入源代码](/docs/craftforj/source-changes)** - 作为差异查看您的实时编辑并将其应用到您的 Java 文件中。
- **[使用路由](/docs/craftforj/routes)** - 查看路由表，导航到任何路由，并更改声明在其上的访问规则。
- **[为应用程序设计主题](/docs/craftforj/theme)** - 调整构建应用程序的设计变量，并将结果保存在样式表中。
- **[使用 AI 代理](/docs/craftforj/ai)** - 运行应用程序内部的编码代理，能够自由编写 Java，编译所写内容，并在您批准后应用它。

## 它与调试器的不同 {#how-it-differs-from-a-debugger}

调试器暂停您的代码并显示当时变量的状态。craftforJ 让应用程序保持运行并显示您的代码生成的界面，因此您可以处理结果而不是执行过程。这两者回答不同的问题，并且通常一起使用。

## 仅在开发模式下 {#development-mode-only}

craftforJ 需要启用两个单独的设置，默认情况下，它只响应与应用程序运行在同一机器上的浏览器。使用 [startforJ](https://docs.webforj.com/startforj) 创建的项目或来自 webforJ [原型](/docs/building-ui/archetypes/overview) 的项目为您启用它，因此在首次运行时可用。有关 craftforJ 可以访问的内容以及如何确认在生产中关闭的内容，请参见 [安全性](/docs/craftforj/security)。

## 主题 {#topics}

<DocCardList className="topics-section" />
