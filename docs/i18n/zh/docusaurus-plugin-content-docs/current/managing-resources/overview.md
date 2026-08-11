---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: 7aee2ee29fd227575e12f1450422d0a1
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

应用程序依赖于各种类型的资源，例如 JavaScript、CSS 和图像。本文件提供了对 webforJ 资源处理机制的全面技术探讨，涵盖声明性注释、程序化 API 方法和自定义协议的使用。

webforJ 采用模块化的资源管理方法，提供多种机制以满足不同应用的需求：

- **前端捆绑器**：通过编译入口将 npm 包、组件框架和样式表语言引入应用。这是前端资产的默认路径，它完成了所有注释所做的工作。
- **声明性注释**：在组件或应用级别嵌入 JavaScript 和 CSS 资源，无需构建步骤。
- **基于 API 的动态注入**：在运行时注入资源，以启用动态应用行为。
- **自定义协议**：提供标准化的方法论以获取资源。
- **文件流和受控下载**：促进资源文件的管理检索和传输。

## Topics {#topics}

<DocCardList className="topics-section" />
