---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 987f1fb9ef8aa9e50ff4ec00320d2dd7
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ 被设计为一个与框架无关的 Java 应用程序的 UI 层。它专注于构建丰富的基于组件的用户界面，同时将后端架构决策完全交给您。这种明确的关注点分离使得 webforJ 能够与任何 Java 技术栈兼容，从传统的 servlet 到现代的微服务。

## 架构哲学 {#architecture-philosophy}

webforJ 故意将 UI 和后端关注点分开。与全面堆栈框架不同，全面堆栈框架会决定您整个应用程序的结构，webforJ 仅提供构建复杂用户界面所需的内容。持久层的选择、依赖注入框架、安全实现和服务架构完全独立于您的 UI 技术。

这种方法认识到大多数组织已经建立了后端模式、现有服务层和首选技术栈。webforJ 通过现代 UI 框架提升这些应用程序，而无需进行架构更改或技术迁移。您的领域逻辑、数据访问模式和安全实现继续以原来的方式工作。

## 后端框架兼容性 {#backend-framework-compatibility}

webforJ 可以与您已经使用的任何 Java 后端框架或架构模式配合使用。无论您是在 Jakarta EE 上构建，使用微服务架构，还是使用自定义框架，webforJ 都提供 UI 层，而不干扰您的后端设计。

对于某些流行框架，webforJ 提供特定集成，减少样板代码并简化开发。这些集成提供诸如自动将依赖注入到 UI 组件、简化配置和框架特定工具支持等便捷功能。如果您在下方未看到您的框架，这并不意味着 webforJ 不适用于它——这只是意味着您将使用框架的标准模式来配置连接，而不是使用预构建的集成。

以下集成是完全可选的。它们存在是为了改善使用特定框架的开发者体验，但无论您是否使用集成，webforJ 的核心功能都以相同的方式工作。您的后端框架继续管理服务、数据访问和业务逻辑，而 webforJ 处理展示层。

## 主题 {#topics}

<DocCardList className="topics-section" />
