---
title: MCP Apps
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Expose routed webforJ views as interactive MCP applications that an MCP host
  can open and use inside its own interface.
_i18n_hash: aa6dae85057948c6bbc1eae5c30e34b2
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# MCP 应用 <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />

MCP 应用允许一个具有 [MCP](https://modelcontextprotocol.io/) 能力的 AI 应用（也称为主机）在其对话中打开一个路由到的 webforJ 视图。该视图仍然是 Java 应用的一部分，因此它使用与浏览器中相同的组件、服务、路由和状态。

人和 AI 可以在相同的实时用户界面中工作。AI 可以在打开视图时提供输入，调用更改打开视图的操作，并接收用户在 UI 中做出的选择所带来的上下文。用户可以继续直接使用渲染的 webforJ 组件。

使用 Spring Boot 和 Spring AI 是发布 MCP 应用的主要方法。集成会发现标记的路由并将其添加到 Spring AI 的 MCP 服务器。首先设置 [Spring Boot](./spring)，然后用最小发布视图 [测试连接](./testing)。不使用 Spring Boot 的应用可以改用 [标准 Servlet 设置](./without-spring)。

:::info[主机支持各不相同]

MCP 应用是 MCP 规范的一个不断发展的扩展，因此主机以自己的节奏采用其修订和安全政策。应用声明其视图加载和连接的来源，允许这些来源的主机会渲染视图。主机也可以应用更严格的政策。打开工具始终返回其文本内容，而路由仍然作为常规浏览器页面可用。使用 [测试](./testing) 中的步骤验证每个目标主机。
:::

## 主题 {#topics}

<DocCardList className="topics-section" />
