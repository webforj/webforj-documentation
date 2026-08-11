---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
description: >-
  Protect webforJ routes with declarative annotations and centralized
  authentication and authorization enforcement.
_i18n_hash: 850b9636996cb17a07a7aff25ac3cd0e
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# 安全性 <DocChip chip='since' label='25.10' />

:::note 公共预览
此功能处于公共预览阶段，准备好用于生产用途。在预览期间，API 可能会根据开发者社区的反馈进行改进。任何更改将提前通过发布说明进行公告，并在必要时提供迁移指南。
:::

在现代 Web 应用程序中，**安全性** 是指根据用户身份和权限控制对应用程序不同部分的访问。在 webforJ 中，安全性提供了一种 **路由级访问控制** 框架，在这里您可以保护视图、要求认证，并强制执行基于角色的权限。

<AISkillTip skill="webforj-securing-apps" />

## 传统路由与安全路由 {#traditional-vs-secured-routing}

在传统的非安全路由中，应用程序中的所有路由对任何知道 URL 的人都是可访问的。这意味着用户可以在没有任何身份验证或授权检查的情况下导航到敏感页面，例如管理面板或用户仪表板。开发者的负担是手动在每个组件中验证权限，这会导致安全实施不一致和潜在的漏洞。

这种方法引入了几个问题：

1. **手动检查**：开发者必须记得在每个受保护的视图或布局中添加安全逻辑。
2. **实施不一致**：分散在代码库中的安全检查会导致漏洞和错误。
3. **维护开销**：更改访问规则需要更新多个文件。
4. **没有集中控制**：没有集中管理应用程序安全性的地方。

webforJ 中的 **安全路由** 通过直接在路由级别启用访问控制来解决此问题。安全系统在渲染任何组件之前自动强制执行规则，提供集中的声明性应用程序安全方法。它的工作原理如下：

1. **声明性注释**：用安全注解标记路由以定义访问要求。
2. **自动强制执行**：安全系统在渲染任何视图之前检查权限。
3. **集中配置**：在一个地方定义安全行为并一致地应用。
4. **灵活的实现**：在 Spring Security 集成或自定义纯 Java 实现之间进行选择。

该设计使得 **身份验证**（验证用户身份）和 **授权**（验证用户可以访问的内容）得以实现，因此只有经过授权的用户才被允许访问受保护的路由。未经授权的用户将根据配置的安全规则自动重定向或拒绝访问。

## webforJ 中的安全路由示例 {#example-of-secured-routing-in-webforj}

下面是一个显示 webforJ 应用程序中不同安全级别的示例：

```java title="LoginView.java"
// 公开登录页面 - 任何人都可以访问
@Route("/login")
@AnonymousAccess
public class LoginView extends Composite<Login> {
  private final Login self = getBoundComponent();

  public LoginView() {
    self.onSubmit(e -> {
      handleLogin(e.getUsername(), e.getPassword());
    });

    whenAttached().thenAccept(c -> {
      self.open();
    });
  }
}
```

```java title="ProductsView.java"
// 产品 - 需要身份验证
@Route(value = "/", outlet = MainLayout.class)
public class ProductsView extends Composite<FlexLayout> {

  public ProductsView() {
    // 产品视图
  }
}
```

```java title="InvoicesView.java"
// 发票 - 需要会计师角色
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // 发票视图
  }
}
```

在此设置中：

- `LoginView` 被标记为 `@AnonymousAccess`，允许未认证用户访问。
- `ProductsView` 没有安全注释，这意味着默认情况下需要身份验证（在启用 `secure-by-default` 模式时）。
- `InvoicesView` 需要 `ACCOUNTANT` 角色，因此只有具有会计权限的用户才能访问发票。

## 安全性如何工作 {#how-security-works}

当用户尝试导航到某个路由时，安全系统遵循以下流程：

1. **导航发起**：用户点击链接或输入 URL。
2. **安全验证**：在渲染组件之前，系统评估安全注释和规则。
3. **决策**：根据用户的身份验证状态和角色：
   - **授权**：允许导航并渲染组件。
   - **拒绝**：阻止导航并重定向到登录页面或访问拒绝页面。
4. **渲染或重定向**：要么显示请求的组件，要么用户被适当重定向。

通过自动强制执行，安全规则在您的整个应用程序中一致应用，因此在任何组件渲染之前处理访问控制，开发者无需在每个视图中添加手动检查。

## 身份验证与授权 {#authentication-vs-authorization}

要正确实施应用程序的安全性，了解这两个概念之间的区别非常重要：

- **身份验证**：验证用户是谁。这通常发生在登录时，用户提供凭证（用户名和密码）。一旦经过身份验证，用户的身份会存储在会话或安全上下文中。

- **授权**：验证经过身份验证的用户可以访问什么。这涉及到检查用户是否具有访问特定路由所需的角色或权限。每次用户导航到受保护的路由时都会进行授权。

webforJ 的安全系统处理这两个方面：

- 像 `@PermitAll` 的注释处理身份验证要求。
- 像 `@RolesAllowed` 的注释处理授权要求。

## 开始使用 {#getting-started}

本指南假设您正在使用 **Spring Boot 和 Spring Security**，这是大多数 webforJ 应用程序推荐的方式。Spring Security 提供行业标准的身份验证和授权，并通过 Spring Boot 自动配置。

本文件的其余部分将指导您通过 Spring Security 来保护您的路由，从基本设置到高级功能。如果您不使用 Spring Boot 或需要自定义安全实现，请参见 [安全架构指南](/docs/security/architecture/overview) 以了解系统的工作原理以及如何实现自定义安全性。

## 主题 {#topics}

<DocCardList className="topics-section" />
