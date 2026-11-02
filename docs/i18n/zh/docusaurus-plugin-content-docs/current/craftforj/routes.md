---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: b276ce8df35b91ed18f07c8a686fb688
---
Routes 标签显示了运行的应用程序的路由表，处于路由器所持有的 [层级](/docs/routing/route-hierarchy/overview) 中，当前活动的路由会被标记。动态注册的路由 [动态](/docs/routing/routes-registration) 会与注释路由一起显示。

![标记当前活动路由的路由树](/img/craftforj/routes/tree.png#rounded-border)

## 路由详情 {#route-details}

选择一个路由将显示路由器对其的了解，包括其路径、背后的类、附加的生命周期观察者及其配置。您可以从这里在源查看器中打开该类。

## 从 craftforJ 导航 {#navigating-from-craftforj}

您可以直接从 craftforJ 导航到任何路由。需要参数的路由为每个参数提供一个字段，并在您填写时解析路径，因此您可以在出发前确认最终到达的位置。

这种导航是真正的导航，因此您应用的 [生命周期观察者](/docs/routing/navigation-lifecycle/observers) 将像用户一样运行。树也会随着应用的变化而变化，因此在应用内部导航将移动标记。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4#t=0.5" type="video/mp4" />
  </video>
</div>

## 访问规则 {#access-rules}

每个路由都有一个 [安全注解](/docs/security/annotations) 的徽章，您可以从工具栏中将树缩小到公共或受保护的路由。

只有 `@RolesAllowed` 和 `@DenyAll` 被计算为受保护的。`@PermitAll` 不指定任何角色，只要求有人登陆，因此过滤器将其视为公共。检查路由是否通过角色限制访问时，请牢记这一点。

![每个路由上都有访问徽章的路由树](/img/craftforj/routes/access-badge.png#rounded-border)

您还可以从 craftforJ 更改路由的访问规则。craftforJ 将注解写入路由的类中，并重启应用，因此此更改会经过与任何其他 [源更改](/docs/craftforj/source-changes) 相同的审查过程。当 craftforJ 没有权限写入 Java 时，该选项将不可用。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4#t=0.5" type="video/mp4" />
  </video>
</div>
