---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: 853a4bb057c1a3499c26d4714120170f
---
# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing 是一种Web服务器技术，允许Java桌面应用程序（Swing、JavaFX、SWT）在不修改原始源代码的情况下在Web浏览器中运行。它在服务器上渲染桌面应用程序，并使用HTML5画布将界面流式传输到浏览器，透明地处理所有用户交互。

## Webswing解决了什么 {#what-webswing-solves}

许多组织在Java桌面应用程序上进行了大量投资，这些应用程序包含经过多年或几十年开发的重要业务逻辑。这些应用程序通常无法轻易重写，因为：

- 复杂的领域逻辑，重建风险较大
- 与特定于桌面的库或硬件的集成
- 全面重写的时间和成本限制
- 需要与现有功能保持功能平价

Webswing 允许这些应用程序在无需修改的情况下可通过Web访问，保留其原始功能和外观。

## 与webforJ的集成 {#integration-with-webforj}

webforJ的Webswing集成提供了 `WebswingConnector` 组件，允许您将Webswing托管的应用程序直接嵌入到您的webforJ应用程序中。这为您创造了机会：

### 渐进式现代化 {#progressive-modernization}

您可以：

1. 首先通过 `WebswingConnector` 嵌入现有的Swing应用程序
2. 在嵌入的应用程序周围构建webforJ中的新功能
3. 逐步用webforJ等效组件替换Swing组件
4. 最终完全淘汰传统应用程序

### 混合应用程序 {#hybrid-applications}

结合使用webforJ构建的现代Web用户界面与专门的桌面功能：

- 使用webforJ用于用户界面、仪表板和报告
- 利用Swing进行复杂可视化或专业编辑器
- 维护单一集成的应用程序体验

## 它是如何工作的 {#how-it-works}

集成通过三个层次进行：

1. **Webswing服务器**：运行您的Java桌面应用程序，捕获其视觉输出并处理用户输入
2. **WebswingConnector组件**：一个webforJ组件，嵌入Webswing客户端，管理与服务器的连接和通信
3. **通信协议**：双向消息传递，允许您的webforJ应用程序发送命令到Swing应用程序并接收事件回传

当用户访问您的webforJ应用程序时，`WebswingConnector` 建立与Webswing服务器的连接。服务器创建或重新连接到一个应用程序实例，并开始向浏览器流式传输视觉状态。用户交互（鼠标、键盘）被捕获并发送到服务器，在实际的Swing应用程序上重放。

## 主题 {#topics}

<DocCardList className="topics-section" />
