---
sidebar_position: 1
title: 安全
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
_i18n_hash: b6707cb6491075a82ac19fb808840245
---
<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

# 安全性 <DocChip chip='since' label='25.10' />

:::note 公开预览
此功能处于公开预览中，已准备好用于生产环境。在预览期间，API 可能会根据开发者社区的反馈进行调整。任何更改将在发布说明中提前公告，并在必要时提供迁移指南。
:::

在现代 Web 应用程序中，**安全性** 指的是根据用户身份和权限控制对应用程序不同部分的访问。在 webforJ 中，安全性提供了一个 **路由级访问控制** 的框架，您可以保护视图、要求身份验证，并实施基于角色的权限。

## 传统路由与安全路由 {#traditional-vs-secured-routing}

在传统的无安全路由中，应用程序中的所有路由都对任何知道 URL 的人开放。这意味着用户可以在没有任何身份验证或授权检查的情况下导航到敏感页面，如管理面板或用户仪表板。开发者的负担是必须在每个组件中手动验证权限，从而导致安全执行的不一致和潜在的漏洞。

这种方法带来了几个问题：

1. **手动检查**：开发者必须记住在每个受保护的视图或布局中添加安全逻辑。
2. **一致性执行**：分散在代码库中的安全检查导致漏洞和错误。
3. **维护开销**：更改访问规则需要更新多个文件。
4. **没有集中控制**：没有单一的地方来理解或管理应用程序安全性。

webforJ 中的 **安全路由** 通过直接在路由级别启用访问控制来解决这个问题。安全系统在任何组件呈现之前自动执行规则，提供一种集中、声明式的应用程序安全方法。它的工作方式如下：

1. **声明式注释**：使用安全注释标记路由以定义访问要求。
2. **自动执行**：安全系统在渲染任何视图之前检查权限。
3. **集中配置**：在一个地方定义安全行为，并一致地应用它。
4. **灵活实现**：选择 Spring Security 集成或自定义的普通 Java 实现。

这种设计实现了 **身份验证**（验证用户身份）和 **授权**（验证用户可以访问的内容），只有获得授权的用户才能访问受保护的路由。未经授权的用户会根据配置的安全规则被自动重定向或拒绝访问。

## webforJ 中的安全路由示例 {#example-of-secured-routing-in-webforj}

以下是一个展示 webforJ 应用中不同安全级别的示例：

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
// 发票 - 需要会计角色
@Route(value = "/invoices", outlet = MainLayout.class)
@RolesAllowed("ACCOUNTANT")
public class InvoicesView extends Composite<FlexLayout> {

  public InvoicesView() {
    // 发票视图
  }
}
```

在此设置中：

- `LoginView` 被标记为 `@AnonymousAccess`，允许未经过身份验证的用户访问。
- `ProductsView` 没有安全注释，这意味着默认需要身份验证（当启用 `secure-by-default` 模式时）。
- `InvoicesView` 需要 `ACCOUNTANT` 角色，因此只有具有会计权限的用户才能访问发票。

## 安全如何工作 {#how-security-works}

当用户尝试导航到某个路由时，安全系统遵循以下流程：

1. **导航发起**：用户点击链接或输入 URL。
2. **安全验证**：在渲染组件之前，系统评估安全注释和规则。
3. **决策**：根据用户的身份验证状态和角色：
   - **授权**：允许导航并呈现组件。
   - **拒绝**：阻止导航并重定向到登录页面或访问拒绝页面。
4. **渲染或重定向**：请求的组件要么显示，要么用户被适当地重定向。

通过自动执行，安全规则在整个应用程序中一致适用，因此在呈现任何组件之前处理访问控制，开发者无需在每个视图中添加手动检查。

## 身份验证与授权 {#authentication-vs-authorization}

要正确实现应用程序中的安全性，了解这两个概念之间的区别很重要：

- **身份验证**：验证用户是谁。这通常发生在登录期间，用户提供凭证（用户名和密码）。一旦经过身份验证，用户的身份将存储在会话或安全上下文中。

- **授权**：验证经过身份验证的用户可以访问的内容。这涉及检查用户是否具有访问特定路由所需的角色或权限。授权在用户每次导航至受保护路由时都会发生。

webforJ 的安全系统处理这两个方面：

- 像 `@PermitAll` 这样的注释处理身份验证要求。
- 像 `@RolesAllowed` 这样的注释处理授权要求。

## 开始使用 {#getting-started}

本指南假设您使用 **Spring Boot 和 Spring Security**，这也是大多数 webforJ 应用程序推荐的方法。Spring Security 提供行业标准的身份验证和授权，并通过 Spring Boot 自动配置。

本文件的其余部分将引导您通过 Spring Security 保护您的路由，从基本设置到高级功能。如果您没有使用 Spring Boot 或需要自定义安全实现，请参阅 [安全架构指南](/docs/security/architecture/overview)，了解系统如何工作以及如何实施自定义安全性。

## 主题 {#topics}

<DocCardList className="topics-section" />
