---
title: MCP 应用
sidebar_position: 0
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Expose routed webforJ views as interactive MCP applications that an MCP host
  can open and use inside its own interface.
_i18n_hash: c654b2ac1c5344132602148ba19216b3
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

MCP 应用允许支持 [MCP](https://modelcontextprotocol.io/) 的 AI 应用（也称为宿主）在对话中打开一个经过路由的 webforJ 视图。该视图仍是 Java 应用的一部分，因此它使用的组件、服务、路由和状态与在浏览器中使用时相同。

用户和 AI 可以共同操作同一个实时 UI。AI 可以在打开视图时提供输入，调用可更改当前视图的操作，并接收用户在 UI 中所做选择的上下文。用户也可以继续直接使用渲染后的 webforJ 组件。

使用 Spring AI 的 Spring Boot 是发布 MCP 应用的主要方式。该集成会发现带标记的路由，并将其添加到 Spring AI 的 MCP 服务器中。请先从 [Spring Boot 设置](./spring) 开始，然后通过最简已发布视图[测试连接](./testing)。不使用 Spring Boot 的应用可以改用[标准 servlet 设置](./without-spring)。

<VideoPlayer video="mcp-apps/webforj-mcp-app" width={1920} height={1080} title="WebforJ MCP app" />

:::info[宿主的支持情况各不相同]

MCP Apps 是 MCP 规范的一项持续演进的扩展，因此各宿主采用其修订版本和安全策略的速度不尽相同。应用会声明其视图加载和连接所需的来源，允许这些来源的宿主便会渲染该视图。宿主也可能采用更严格的策略。打开工具始终会返回其文本内容，而该路由仍可作为普通浏览器页面访问。请按照[测试](./testing)中的步骤，验证你要面向的每个宿主。
:::

## 主题 {#topics}

<DocCardList className="topics-section" />
