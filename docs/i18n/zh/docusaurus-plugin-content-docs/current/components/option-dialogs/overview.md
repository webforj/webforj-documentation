---
sidebar_position: 35
title: Option Dialogs
hide_giscus_comments: true
_i18n_hash: 4d818d70f6238be10dc8913d19ed47b7
---
<!-- vale off -->
import DocCardList from '@theme/DocCardList';

# 选项对话框
<!-- vale on -->

选项对话框为应用程序提供了一种与用户交流并收集其输入的方式。这些对话框是模态的，意味着它们会阻止应用程序执行，直到用户与其交互，从而确保在继续之前首先处理重要信息。

webforJ中的选项对话框类似于Swing中的`JOptionPane`，解决了在Web应用程序中处理阻塞对话框的基本问题。

:::tip 模态性
在webforJ中使用选项对话框创建模态对话框时，对话框会阻止用户对应用程序其他部分的输入，并仅处理模态对话框的事件。这确保了对话框保持响应，同时防止与其他部分的交互，从而提升用户体验并维护应用程序流程。服务器在对话框关闭或返回值之前将停止处理任何进一步的请求。
:::

## 主题 {#topics}

<DocCardList className="topics-section" />
