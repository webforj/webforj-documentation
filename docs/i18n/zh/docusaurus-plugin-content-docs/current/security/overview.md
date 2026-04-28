---
sidebar_position: 1
title: Security
hide_table_of_contents: true
hide_giscus_comments: true
_i18n_hash: fe28b9f0c456b9880785afcc5d4d5f23
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
该功能处于公共预览阶段，并可用于生产。 在预览期间，API可能会根据开发者社区的反馈进行调整。 所有更改将在发布说明中提前通知，并在必要时提供迁移指南。
:::

在现代Web应用程序中，**安全性**是指根据用户身份和权限控制对应用程序不同部分的访问。 在webforJ中，安全性提供了一个**路由级访问控制**的框架，您可以保护视图、要求身份验证并实施基于角色的权限。

<AISkillTip skill="webforj-securing-apps" />

## 传统与安全路由 {#traditional-vs-secured-routing}

在传统的无安全路由中，应用程序中的所有路由对任何知道URL的人都是可访问的。 这意味着用户可以在没有任何身份验证或授权检查的情况下，导航到敏感页面，如管理员面板或用户仪表板。 开发人员的责任是在每个组件中手动验证权限，导致安全执行不一致和潜在漏洞。

这种方法带来了几个问题：

1. **手动检查**：开发人员必须记得在每个受保护的视图或布局中添加安全逻辑。
2. **执行不一致**：安全检查分散在代码库中，导致漏洞和错误。
3. **维护开销**：更改访问规则需要更新多个文件。
4. **缺乏集中管理**：没有单一位置来理解或管理应用程序安全。

webforJ中的**安全路由**通过在路由级别直接启用访问控制来解决这一问题。 安全系统在任何组件呈现之前自动执行规则，提供一个集中、声明式的应用安全方法。 工作方式如下：

1. **声明性注释**：标记路由以安全注释定义访问要求。
2. **自动执行**：安全系统在渲染任何视图之前检查权限。
3. **集中配置**：在一个地方定义安全行为并一致应用。
4. **灵活实现**：可以选择Spring Security集成或自定义的纯Java实现。

这种设计使得**身份验证**（验证用户身份）和**授权**（验证用户能够访问的内容）成为可能，因此只有授权用户才能获得访问受保护路由的权限。 未经授权的用户将根据配置的安全规则自动被重定向或拒绝访问。

## webforJ中的安全路由示例 {#example-of-secured-routing-in-webforj}

以下是显示webforJ应用程序中不同安全级别的示例：

```java title="LoginView.java"
// 公共登录页面 - 任何人都可以访问
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

- `LoginView`被标记为`@AnonymousAccess`，允许未认证用户访问。
- `ProductsView`没有安全注释，这意味着它默认需要身份验证（当启用`secure-by-default`模式时）。
- `InvoicesView`需要`ACCOUNTANT`角色，因此只有具有会计权限的用户才能访问发票。

## 安全性如何工作 {#how-security-works}

当用户尝试导航到一个路由时，安全系统遵循以下流程：

1. **导航启动**：用户点击链接或输入URL。
2. **安全验证**：在渲染组件之前，系统评估安全注释和规则。
3. **决策**：根据用户的身份验证状态和角色：
   - **允许**：允许导航并渲染组件。
   - **拒绝**：阻止导航并重定向到登录页面或访问拒绝页面。
4. **渲染或重定向**：要么请求的组件显示，要么用户被适当地重定向。

通过自动执行，安全规则在整个应用程序中一致地应用，因此访问控制在渲染任何组件之前得到处理，开发人员不需要在每个视图中添加手动检查。

## 身份验证与授权 {#authentication-vs-authorization}

要正确实施应用程序的安全性，了解这两个概念之间的区别很重要：

- **身份验证**：验证用户是谁。 这通常发生在登录期间，用户提供凭据（用户名和密码）。 一旦经过身份验证，用户的身份就会存储在会话或安全上下文中。

- **授权**：验证经过身份验证的用户可以访问的内容。 这涉及检查用户是否具有访问特定路由所需的角色或权限。 每当用户导航到受保护路由时，都会进行授权。

webforJ的安全系统同时处理这两个方面：

- 类似于`@PermitAll`的注释处理身份验证要求。
- 类似于`@RolesAllowed`的注释处理授权要求。

## 入门 {#getting-started}

本指南假设您正在使用**Spring Boot与Spring Security**，这是大多数webforJ应用程序的推荐方法。 Spring Security提供了行业标准的身份验证和授权，并通过Spring Boot实现自动配置。

本文件的其余部分将引导您通过Spring Security保护路由，从基本设置到高级功能。如果您不使用Spring Boot或需要自定义安全实现，请参阅[安全架构指南](/docs/security/architecture/overview)，以了解系统如何工作以及如何实施自定义安全性。

## 主题 {#topics}

<DocCardList className="topics-section" />
