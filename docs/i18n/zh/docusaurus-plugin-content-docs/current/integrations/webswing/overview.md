---
title: 使用 webforJ 逐步现代化 Swing 应用
hide_table_of_contents: true
hide_giscus_comments: true
description: 使用 Webswing 在浏览器中运行 Swing、JavaFX 或 SWT 应用，将其嵌入 webforJ 应用，并逐步用网页界面替换。
_i18n_title_hash: 698c1a9e98e2ef808c204b0bc4be4669
_i18n_description_hash: 3bd3bdba7583bc1699e8d6153577eb56
_i18n_hash: 53117d208e8a0b0a2e46705f9edd1cdc
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing 可在浏览器中运行 Swing、JavaFX 或 SWT 应用，无需修改源代码。借助 webforJ，您可以将该应用嵌入 Web 应用中，并逐个屏幕地替换它。要让两者互相传递消息，您需要将 Webswing API 添加到 Swing 应用中。

Webswing 是一种 Web 服务器技术。它在服务器上渲染桌面应用，并通过 HTML5 canvas 将界面流式传输到浏览器，同时透明地处理所有用户交互。

如需了解整个流程的概览，从在浏览器中运行桌面应用到将其退役，请参阅 [Swing 现代化](/modernization)。

## Webswing 解决的问题 {#what-webswing-solves}

许多组织在 Java 桌面应用上投入了大量资源。这些应用包含经过多年甚至数十年开发的关键业务逻辑。由于以下原因，这些应用通常难以轻易重写：

- 复杂的领域逻辑，重新实现存在风险
- 与桌面专用库或硬件集成
- 全面重写的时间和成本限制
- 需要保持与现有应用的功能一致

Webswing 可让这些应用无需修改即可通过 Web 访问，同时保留原有功能和外观。

## 与 webforJ 集成 {#integration-with-webforj}

webforJ 的 Webswing 集成提供了 `WebswingConnector` 组件，可将由 Webswing 托管的应用直接嵌入 webforJ 应用中。这为以下场景提供了可能：

### 渐进式现代化 {#progressive-modernization}

无需一次性重写，您可以：

1. 首先通过 `WebswingConnector` 嵌入现有的 Swing 应用
2. 围绕嵌入的应用，在 webforJ 中构建新功能
3. 逐步用 webforJ 对应组件替换 Swing 组件
4. 最终完全淘汰旧版应用

### 混合应用 {#hybrid-applications}

将使用 webforJ 构建的现代 Web UI 与专用桌面功能相结合：

- 使用 webforJ 构建面向用户的界面、仪表板和报告
- 保留 Swing，用于复杂可视化或专用编辑器
- 维持统一集成的应用体验

:::tip 替换 Swing 对应组件
请参阅 [从 Swing 迁移](/docs/introduction/coming-from-swing)，了解 Swing 组件与 webforJ 对应组件之间的对比。对比中还包含相关参考资料，帮助您使用各个 webforJ 对应功能。
:::

## 工作原理 {#how-it-works}

此集成通过以下三个层实现：

1. **Webswing Server**：运行 Java 桌面应用，捕获其视觉输出并处理用户输入
2. **WebswingConnector Component**：一种 webforJ 组件，用于嵌入 Webswing 客户端，并管理与服务器的连接和通信
3. **Communication Protocol**：双向消息传递，使 webforJ 应用可以向 Swing 应用发送命令并接收其返回的事件

用户访问 webforJ 应用时，`WebswingConnector` 会与 Webswing 服务器建立连接。服务器会创建应用实例或重新连接到现有实例，并开始向浏览器流式传输视觉状态。用户交互（鼠标、键盘）会被捕获并发送到服务器，然后在实际的 Swing 应用中重放。

## 主题 {#topics}

<DocCardList className="topics-section" />
