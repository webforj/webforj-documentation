---
sidebar_position: 1
title: 应用安全
hide_table_of_contents: true
hide_giscus_comments: true
description: webforJ 的服务器驱动模型如何保护你的应用、你仍需负责应对的威胁，以及如何确保部署安全。
_i18n_title_hash: 2993e06f9e34b9da0ed41173d7d56a4a
_i18n_description_hash: 4dfd7c6db5603bf452b8b8617acd834f
_i18n_hash: f23f925b237729d4160089bb260cf329
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

在服务器上运行你的 Java 应用程序（请参见 [客户端和服务器](/docs/architecture/client-server)）包含了大量网络攻击的防护，甚至在你写下第一行代码之前。尽管如此，这并没有减轻你的责任。这些页面解释了界限的所在，webforJ 为你处理了什么，你需要负责哪些方面，以及如何保持生产部署的安全。

有关谁可以访问每个视图的控制，请查看 [安全](/docs/security/overview) 部分的其余内容，了解身份验证和路由级授权。

## 主题 {#topics}

<DocCardList className="topics-section" />
