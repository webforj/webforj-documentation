---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: e8f61966c5b7d0745f65f23172dd114a
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing 是一种 Web 服务器技术，它允许 Java 桌面应用程序（Swing、JavaFX、SWT）在 Web 浏览器中运行，而无需对原始源代码进行任何修改。它在服务器上渲染桌面应用程序，并使用 HTML5 画布将界面流式传输到浏览器，透明地处理所有用户交互。

## Webswing 解决了什么

许多组织在 Java 桌面应用程序上进行了大量投资，这些应用程序包含多年或几十年开发的关键业务逻辑。这些应用程序通常无法轻易重写，因为：

- 复杂的领域逻辑，重新创建将带来风险
- 与桌面特定库或硬件的集成
- 完全重写的时间和成本限制
- 需要与现有功能保持特性一致

Webswing 允许这些应用程序无需修改即可实现 Web 访问，保留其原始功能和外观。

## 与 webforJ 的集成

webforJ Webswing 集成提供 `WebswingConnector` 组件，使您能够直接在 webforJ 应用程序中嵌入 Webswing 托管的应用程序。这为以下情况创造了机会：

### 渐进式现代化

您可以选择非全或无的重写，而是：

1. 首先通过 `WebswingConnector` 嵌入现有的 Swing 应用程序
2. 在嵌入的应用程序周围构建新功能
3. 逐渐用 webforJ 等效组件替换 Swing 组件
4. 最终完全淘汰遗留应用程序

### 混合应用程序

将使用 webforJ 构建的现代 Web 用户界面与专用桌面功能相结合：

- 使用 webforJ 进行面向用户的界面、仪表板和报告
- 利用 Swing 进行复杂的可视化或专用编辑器
- 维护单一的集成应用体验

## 工作原理

该集成通过三个层次进行操作：

1. **Webswing 服务器**：运行您的 Java 桌面应用程序，捕获其视觉输出并处理用户输入
2. **WebswingConnector 组件**：webforJ 组件，嵌入 Webswing 客户端，管理与服务器的连接和通信
3. **通信协议**：双向消息传递，使您的 webforJ 应用程序能够向 Swing 应用程序发送命令并接收事件

当用户访问您的 webforJ 应用程序时，`WebswingConnector` 建立与 Webswing 服务器的连接。服务器创建或重新连接到一个应用实例，并开始将视觉状态流式传输到浏览器。用户交互（鼠标、键盘）被捕获并发送到服务器，在实际的 Swing 应用程序上重放。

## 主题 {#topics}

<DocCardList className="topics-section" />
