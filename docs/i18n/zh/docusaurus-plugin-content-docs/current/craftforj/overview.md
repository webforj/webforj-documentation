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
_i18n_hash: 771ef18b5a680d5b9260f06a2704f877
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
# craftforJ <DocChip chip='since' label='26.02' />
<!-- vale on -->

**craftforJ** 是随 webforJ 一同提供的可视化开发环境。它以开发模式运行在你的应用中，并实时展示 Java 代码创建的组件。你可以选择组件、修改其属性、立即查看运行中的应用如何更新，并将想要保留的更改写回创建这些组件的 Java 文件。

<!-- INTRO_END -->

由于 craftforJ 通过 webforJ 本身读取应用，它会按照你编写应用的方式来描述应用。树中列出的是你的组件，而不是浏览器渲染出的标记；属性是组件声明的属性；路由则是路由器注册的路由，以及你为其添加注解声明的访问规则。

<VideoPlayer video="craftforj/intro" width={1920} height={1080} title="Intro" />

## 你可以用它做什么 {#what-you-can-do-with-it}

- **[检查组件](/docs/craftforj/inspector)** - 浏览组件树，在页面中单击组件以选中它，并在应用运行时修改其属性。
- **[将更改写入源代码](/docs/craftforj/source-changes)** - 以差异形式查看实时编辑内容，并将其应用到 Java 文件。
- **[使用路由](/docs/craftforj/routes)** - 查看路由表、导航到任意路由，并修改其中声明的访问规则。
- **[设置应用主题](/docs/craftforj/theme)** - 调整构建应用所用的设计令牌，并将结果保存到样式表中。
- **[使用 AI 代理](/docs/craftforj/ai)** - 在运行中的应用内使用编码代理；它可以自由编写 Java、编译所写代码，并在获得你的批准后应用更改。

## 它与调试器有何不同 {#how-it-differs-from-a-debugger}

调试器会暂停代码，并显示那一刻变量的状态。craftforJ 则让应用继续运行，并展示代码生成的界面，因此你处理的是运行结果，而不是代码执行过程。两者回答的问题不同，通常会配合使用。

## 仅限开发模式 {#development-mode-only}

craftforJ 需要启用两个独立的设置；默认情况下，它只响应与应用运行在同一台机器上的浏览器。通过 [startforJ](https://docs.webforj.com/startforj) 创建的项目，或基于 webforJ [archetype](/docs/building-ui/archetypes/overview) 创建的项目，会自动为你启用 craftforJ，因此首次运行时即可使用。请参阅[安全性](/docs/craftforj/security)，了解 craftforJ 可以访问的内容，以及如何确认它在生产环境中已关闭。

## 主题 {#topics}

<DocCardList className="topics-section" />
