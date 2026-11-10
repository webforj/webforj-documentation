---
title: 集成
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: has-new-content
description: >-
  Combine webforJ with Spring Boot, Spring Data, Kotlin DSL, Webswing, JBang,
  and AI tooling while keeping your backend stack independent.
_i18n_hash: 2272fe4265260266e667c1b22703a3b6
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ 是一个面向业务 Web 应用的全栈 Java 框架，不依赖特定后端。你可以使用 Java 构建整个应用，从界面和路由到表单、数据绑定和路由安全；而持久化层、依赖注入、服务和后端架构则由你自行选择。因此，webforJ 可以与任何 Java 技术栈配合使用，从传统 Servlet 到 Spring Boot 和微服务架构均可。

## 架构理念 {#architecture-philosophy}

webforJ 涵盖了应用向用户展示数据并处理数据所需的一切：组件、布局、路由、带绑定和验证功能的表单、由存储库提供支持的表格、路由安全以及翻译。它不会规定系统其余部分的工作方式。你可以独立于 webforJ 选择持久化层、依赖注入框架、身份提供方和服务架构。

这种方式考虑到了大多数组织已经确立的后端模式、现有服务层和偏好的技术栈。webforJ 能让这些应用接入 Web，而无需更改架构或迁移技术。你的领域逻辑、数据访问模式和安全实现都可以照常运行。

## 后端框架兼容性 {#backend-framework-compatibility}

webforJ 可与任何你已在使用的 Java 后端框架或架构模式配合使用。无论你基于 Jakarta EE 构建应用、采用微服务架构，还是使用自定义框架，webforJ 都能与之连接，而不会干扰你的后端设计。

对于一些常用框架，webforJ 提供了专门的集成，以减少样板代码并简化开发。这些集成提供了诸如自动向 UI 组件注入依赖、简化配置以及支持框架专用工具等便利功能。如果你没有在下方看到所用的框架，并不意味着 webforJ 无法与之配合使用。这意味着你将使用该框架的标准模式配置连接，而不是使用预构建的集成。

下方的集成都完全可选。它们旨在提升使用特定框架时的开发体验；无论是否使用集成，webforJ 的核心功能都完全相同。你的后端框架继续负责管理服务、数据访问和业务逻辑，而 webforJ 负责界面、导航和用户交互。

## 主题 {#topics}

<DocCardList className="topics-section" />
