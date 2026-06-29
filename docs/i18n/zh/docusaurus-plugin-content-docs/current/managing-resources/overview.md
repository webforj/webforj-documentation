---
sidebar_position: 1
title: Managing Resources
description: >-
  Manage JavaScript, CSS, and other assets in webforJ apps with declarative
  annotations, runtime injection APIs, and custom URL protocols.
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: ddf6edc65adddf9e8eb952916a120e1f
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

应用程序依赖于各种类型的资源，如JavaScript、CSS和图像。本文档提供了对webforJ资源处理机制的全面技术探索，涵盖声明性注释、编程API方法和自定义协议的利用。

webforJ采用模块化的方法进行资源管理，提供多种机制以满足不同应用需求：

- **前端打包器**：通过编译条目将npm包、组件框架和样式表语言引入应用。这是前端资产的默认路径，它执行注释所做的一切。
- **声明性注释**：在组件或应用级别嵌入JavaScript和CSS资源，无需构建步骤。
- **基于API的动态注入**：在运行时注入资源以实现动态应用行为。
- **自定义协议**：提供标准化的方法论以便于资源访问。
- **文件流和受控下载**：促进资源文件的管理检索和传输。

## Topics {#topics}

<DocCardList className="topics-section" />
