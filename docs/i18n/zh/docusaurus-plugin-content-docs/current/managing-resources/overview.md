---
sidebar_position: 1
title: Managing Resources
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: f882ad17e626d45c6ed92c4e14abe040
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

应用程序依赖各种类型的资源，例如 JavaScript、CSS 和图像。本文档提供了 webforJ 资源处理机制的全面技术探索，涵盖声明性注解、编程 API 方法和自定义协议的利用。

webforJ 采用模块化的方法来管理资源，提供多种机制以满足不同应用的需求：

- **声明性注解**：在组件或应用级别嵌入 JavaScript 和 CSS 资源。
- **基于 API 的动态注入**：在运行时注入资源以启用动态应用行为。
- **自定义协议**：提供标准化的方法论用于资源访问。
- **文件流和受控下载**：促进资源文件的管理检索和传输。

## Topics {#topics}

<DocCardList className="topics-section" />
