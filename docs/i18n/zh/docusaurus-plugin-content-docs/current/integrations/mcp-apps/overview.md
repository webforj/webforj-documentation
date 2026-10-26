---
title: MCP 应用
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Expose routed webforJ views as interactive MCP applications that an MCP host
  can open and use inside its own interface.
_i18n_hash: 27896fdcd80b0f7414e1e41f1087d848
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale Google.Headings = NO -->
# MCP 应用 <DocChip chip='since' label='26.02' /> <DocChip chip='experimental' />
<!-- vale Google.Headings = YES -->

MCP 应用允许一个支持 [MCP](https://modelcontextprotocol.io/) 的 AI 应用，也称为主机，在其对话中打开路由的 webforJ 视图。该视图仍然是 Java 应用的一部分，因此使用与浏览器中相同的组件、服务、路由和状态。

人和 AI 可以在相同的实时用户界面中工作。当打开视图时，AI 可以提供输入，调用更改开放视图的操作，并根据用户在 UI 中所做的选择接收上下文。用户可以继续直接使用渲染的 webforJ 组件。

使用 Spring Boot 和 Spring AI 是发布 MCP 应用的主要方式。集成会发现标记的路由并将其添加到 Spring AI 的 MCP 服务器。首先从 [Spring Boot 设置](./spring) 开始，然后使用最小的发布视图 [测试连接](./testing)。不使用 Spring Boot 的应用可以改用 [标准 Servlet 设置](./without-spring)。

<div class="videos-container">
    <video controls>
      <source src="https://cdn.webforj.com/webforj-documentation/video/mcp-apps/webforj-mcp-app.mp4" type="video/mp4" />
    </video>
</div>

:::info[主机支持有所不同]

MCP 应用是 MCP 规范的一项不断发展的扩展，因此主机以自己的节奏采纳其修订和安全政策。应用声明其加载和连接的视图来源，允许它们的主机会呈现该视图。主机也可以施加更严格的政策。打开工具始终返回其文本内容，路由仍然作为常规浏览器页面可用。请通过 [测试](./testing) 中的步骤验证每个目标主机。
:::

## 主题 {#topics}

<DocCardList className="topics-section" />
