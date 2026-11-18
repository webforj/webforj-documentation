---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Embed legacy Swing, JavaFX, or SWT desktop apps inside a webforJ UI through
  Webswing for progressive modernization and hybrid experiences.
_i18n_hash: 300c45aa8cc9f51831219f75d74d31b6
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing 是一种 Web 服务器技术，可让 Java 桌面应用程序（Swing、JavaFX、SWT）无需修改原始源代码即可在 Web 浏览器中运行。它在服务器上渲染桌面应用，并使用 HTML5 canvas 将界面流式传输到浏览器，同时以透明方式处理所有用户交互。

## Webswing 解决的问题 {#what-webswing-solves}

许多组织在 Java 桌面应用程序上投入了大量资源，这些应用程序包含经过多年甚至数十年开发的关键业务逻辑。由于以下原因，这些应用程序通常难以轻易重写：

- 复杂的领域逻辑，重新实现可能带来风险
- 与桌面专用库或硬件的集成
- 全面重写所需的时间和成本
- 需要维持与现有功能的一致性

Webswing 无需修改这些应用程序，即可让它们通过 Web 访问，同时保留其原有功能和外观。

## 与 webforJ 集成 {#integration-with-webforj}

webforJ 的 Webswing 集成提供了 `WebswingConnector` 组件，可让你将由 Webswing 托管的应用程序直接嵌入 webforJ 应用中。这为以下场景创造了可能：

### 渐进式现代化 {#progressive-modernization}

无需一次性重写，你可以：

1. 首先通过 `WebswingConnector` 嵌入现有的 Swing 应用
2. 围绕嵌入的应用在 webforJ 中构建新功能
3. 逐步将 Swing 组件替换为 webforJ 对应组件
4. 最终完全淘汰旧版应用

### 混合应用 {#hybrid-applications}

将使用 webforJ 构建的现代 Web UI 与专用桌面功能相结合：

- 使用 webforJ 构建面向用户的界面、仪表板和报表
- 利用 Swing 实现复杂的可视化或专用编辑器
- 保持统一、集成的应用体验

:::tip 替换 Swing 对应组件
请参阅[从 Swing 迁移](/docs/introduction/coming-from-swing)，了解 Swing 组件与 webforJ 对应组件之间的比较。比较内容还包含相关参考资料，帮助你使用各项 webforJ 对应功能。
:::

## 工作原理 {#how-it-works}

此集成通过三个层级运行：

1. **Webswing Server**：运行你的 Java 桌面应用，捕获其视觉输出并处理用户输入
2. **WebswingConnector Component**：一种 webforJ 组件，用于嵌入 Webswing 客户端，并管理与服务器的连接和通信
3. **Communication Protocol**：双向消息传递机制，让 webforJ 应用能够向 Swing 应用发送命令并接收事件

当用户访问你的 webforJ 应用时，`WebswingConnector` 会与 Webswing 服务器建立连接。服务器会创建一个应用实例或重新连接到现有实例，然后开始将视觉状态流式传输到浏览器。用户交互（鼠标、键盘）会被捕获并发送到服务器，再由服务器在实际的 Swing 应用中重放。

## 主题 {#topics}

<DocCardList className="topics-section" />
