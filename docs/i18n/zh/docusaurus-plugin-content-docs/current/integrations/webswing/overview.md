---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 8dcd8fdee2734f6b4b243b0ea82fa1c2
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing 是一种网页服务器技术，允许 Java 桌面应用程序（Swing、JavaFX、SWT）在网页浏览器中运行，而无需对原始源代码进行任何修改。它在服务器上渲染桌面应用程序，并使用 HTML5 画布将界面流式传输到浏览器，透明地处理所有用户交互。

## Webswing 解决了什么

许多组织在 Java 桌面应用程序上投入了大量资金，这些应用程序包含了经过多年或数十年发展的关键业务逻辑。这些应用程序往往无法轻易重写，原因包括：

- 复杂的领域逻辑，重建风险较高
- 与桌面特定库或硬件的集成
- 完全重写的时间和成本限制
- 需要与现有功能保持特性一致性

Webswing 允许这些应用程序在不修改的情况下即可实现网页访问，保留其原始功能和外观。

## 与 webforJ 的集成

webforJ Webswing 集成提供了 `WebswingConnector` 组件，允许您将 Webswing 托管的应用程序直接嵌入到您的 webforJ 应用中。这为以下提供了机会：

### 渐进式现代化

您可以选择不进行全盘重写，而是：

1. 通过 `WebswingConnector` 嵌入现有的 Swing 应用
2. 在嵌入的应用周围构建 webforJ 的新功能
3. 逐渐用 webforJ 等效组件替换 Swing 组件
4. 最终完全淘汰旧版应用

### 混合应用

将使用 webforJ 构建的现代网页 UI 与专门的桌面功能相结合：

- 使用 webforJ 来处理面向用户的界面、仪表板和报告
- 利用 Swing 进行复杂的可视化或专门编辑器
- 维护单一集成的应用体验

## 工作原理

该集成通过三个层次操作：

1. **Webswing 服务器**：运行您的 Java 桌面应用，捕获其视觉输出并处理用户输入
2. **WebswingConnector 组件**：一个 webforJ 组件，嵌入 Webswing 客户端，管理与服务器的连接和通信
3. **通信协议**：双向消息传递，使您的 webforJ 应用可以向 Swing 应用发送命令并接收事件反馈

当用户访问您的 webforJ 应用时，`WebswingConnector` 会建立与 Webswing 服务器的连接。服务器创建或重新连接到应用实例，并开始将视觉状态流式传输到浏览器。用户交互（鼠标、键盘）会被捕获并发送到服务器，在实际的 Swing 应用上进行重放。

## 主题 {#topics}

<DocCardList className="topics-section" />
