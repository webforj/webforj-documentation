---
title: Integrations
hide_table_of_contents: true
sidebar_class_name: new-content
hide_giscus_comments: true
_i18n_hash: 366829e324b872af8247a509f9c55783
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

webforJ 被设计为一个与框架无关的 Java 应用程序 UI 层。它专注于构建丰富的、基于组件的用户界面，而将后端架构的决策完全留给您。这样的关注点清晰分离使 webforJ 能够与任何 Java 技术栈一起工作，从传统的 servlet 到现代的微服务。

## 架构哲学 {#architecture-philosophy}

webforJ 有意地将 UI 和后端的关注点分开。与全栈框架不同，后者会决定整个应用程序的结构，webforJ 仅提供构建复杂用户界面所需的内容。您选择的持久层、依赖注入框架、安全实现和服务架构完全独立于您的 UI 技术。

这种方法认识到大多数组织已经建立了后端模式、现有的服务层和首选的技术栈。webforJ 通过现代 UI 框架来改善这些应用程序，而无需进行架构更改或技术迁移。您的领域逻辑、数据访问模式和安全实现仍将正常工作。

## 后端框架兼容性 {#backend-framework-compatibility}

webforJ 可以与您已经使用的任何 Java 后端框架或架构模式一起工作。无论您是在 Jakarta EE 上构建、使用微服务架构，还是使用自定义框架，webforJ 都提供 UI 层，而不会干扰您的后端设计。

对于某些流行框架，webforJ 提供特定的集成，减少样板代码并简化开发。这些集成提供了便利，例如自动将依赖注入到 UI 组件中、简化配置以及针对框架的工具支持。如果您没有在下面看到您的框架，这并不意味着 webforJ 无法与之一起工作 - 这仅意味着您将使用框架的标准模式配置连接，而不是使用预构建的集成。

下面的集成完全是可选的。它们旨在改善使用特定框架的开发者体验，但无论您是否使用集成，webforJ 核心功能均以相同方式工作。您的后端框架将继续管理服务、数据访问和业务逻辑，而 webforJ 则处理表示层。

## 主题 {#topics}

<DocCardList className="topics-section" />
