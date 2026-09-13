---
title: 访问用户
sidebar_position: 4
_i18n_hash: 5a29cabdc472de49bcc1db895b982485
---
Spring Security 将经过身份验证的用户信息存储在 `SecurityContextHolder` 中，通过它可以在应用程序中访问用户名、角色和权限。 本节将介绍如何在 webforJ 视图和组件中检索和使用这些信息。

## 获取当前用户信息 {#get-current-user-information}

Spring Security 将经过身份验证的用户信息存储在 `SecurityContextHolder` 中，该机制在应用程序中的任何地方都提供线程安全的访问：

```java
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

// 获取当前认证信息
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// 获取用户名
String username = authentication.getName();

// 获取权限（角色）
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

// 检查用户是否具有特定角色
boolean isAdmin = authorities.stream()
  .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
```

`SecurityContextHolder` 是 Spring Security 用于访问当前用户身份验证详细信息的核心机制。它可以在应用程序中的任何地方使用，包括 webforJ 视图构造函数和方法。

## 在视图中显示用户信息 {#display-user-information-in-views}

在您的 webforJ 视图中直接访问用户信息，以显示个性化内容：

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // 显示用户信息
    H1 welcome = new H1("欢迎, " + username + "!");

    self.add(welcome);
  }
}
```

## 基于角色的条件渲染 {#conditional-rendering-based-on-roles}

根据用户角色显示或隐藏 UI 元素，以控制用户可以看到哪些功能：

```java title="DashboardView.java"
@Route("/dashboard")
@PermitAll
public class DashboardView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DashboardView() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    // 检查特定角色
    boolean isAdmin = auth.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // 有条件地添加仅管理员可用的按钮
    if (isAdmin) {
      Button adminPanel = new Button("管理员面板");
      adminPanel.onClick(e -> Router.getCurrent().navigate(AdminView.class));
      self.add(adminPanel);
    }
  }
}
```
