---
sidebar_position: 1
title: Security Architecture
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: df2f795c6b65edc60adb39b549cb780b
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# 安全架构 <DocChip chip='since' label='25.10' />

webforJ安全系统建立在接口和设计模式的基础上，能够灵活、可扩展地保护路线。本节解释了基础安全框架的功能，以及如何通过实现这些接口构建自定义安全解决方案。

:::tip[与Spring集成]
大多数应用程序应使用[Spring Security集成](/docs/security/getting-started)，因为它会为您自动配置所有内容。仅当您有特定要求或不使用Spring Boot时，才实现自定义安全。Spring集成建立在相同的基础架构上。
:::

您将了解到核心接口、评估器链模式、导航是如何被拦截和评估的，以及存储身份验证状态的不同方法。

:::info[关注架构和扩展点]
这些指南解释了基础架构和扩展点、您实现的接口以及它们如何协同工作。代码示例展示了**一种可能的方法**，而不是规定要求。您的实现可以根据需要使用不同的存储机制（JWT、数据库、LDAP）、不同的连线模式或不同的身份验证流程。
:::

## 您将学习什么 {#what-youll-learn}

- **基础架构**：定义安全行为的核心接口及其协同工作方式
- **导航拦截**：安全系统如何拦截导航请求并评估访问规则
- **评估器链模式**：安全规则如何使用责任链模式按优先级顺序进行评估
- **身份验证存储**：存储用户身份验证状态的不同方法（会话、JWT、数据库等）
- **完整实现**：一个展示所有组件如何连线在一起的工作示例

## 适合谁 {#who-this-is-for}

这些指南适用于想要：

- 为非Spring应用程序构建自定义安全实现的开发人员
- 理解基础架构以排查问题
- 实现自定义身份验证流程或授权逻辑
- 创建具有特定领域逻辑的安全评估器
- 与现有身份验证系统（LDAP、OAuth、自定义后端）集成

## 先决条件 {#prerequisites}

在深入这些指南之前，您应该：

- 完成[入门指南](/docs/security/getting-started)以理解安全概念
- 了解[注解指南](/docs/security/annotations)中的安全注解
- 熟悉责任链设计模式
- 具备Java接口和继承的经验

## 主题 {#topics}

<DocCardList className="topics-section" />
