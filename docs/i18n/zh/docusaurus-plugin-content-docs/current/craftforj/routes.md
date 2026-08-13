---
title: Routes
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: 8a8c4099d3bd0d4ff988038cee6a5c15
---
Routes选项卡显示正在运行的应用程序的路由表，处于路由器持有的[层次结构](/docs/routing/route-hierarchy/overview)中，活动路由会被标记。以[动态方式](/docs/routing/routes-registration)注册的路由与注释的路由并列显示。

![标记了活动路由的路由树](/img/craftforj/routes/tree.png#rounded-border)

## 路由详情 {#route-details}

选择一个路由会显示路由器对其的了解，包括它的路径、背后的类、附加到它的生命周期观察者以及其配置。您可以从这里在源查看器中打开该类。

## 从craftforJ导航 {#navigating-from-craftforj}

您可以直接从craftforJ导航到任何路由。需要参数的路由为每一个提供了一个字段，并在您填写时解析路径，因此您可以在出发前确认您将到达的位置。

以这种方式导航是真正的导航，因此您应用的[生命周期观察者](/docs/routing/navigation-lifecycle/observers)会像用户一样运行。树也跟随应用，因此在应用内导航会移动标记。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-navigate.mp4" type="video/mp4" />
  </video>
</div>

## 访问规则 {#access-rules}

每个路由都有一个[安全注解](/docs/security/annotations)声明的徽章，您可以从工具栏将树缩小为公共或受保护的路由。

只有`@RolesAllowed`和`@DenyAll`算作受保护的。`@PermitAll`不指定任何角色，只要求某人已登录，因此过滤器将其视为公共。当您检查哪些路由按角色限制访问时，请记住这一点。

![每个路由上都有访问徽章的路由树](/img/craftforj/routes/access-badge.png#rounded-border)

您还可以从craftforJ更改路由的访问规则。craftforJ将注解写入路由的类中，并且应用程序会重新启动，因此更改将经过与任何其他[源更改](/docs/craftforj/source-changes)相同的审核。当craftforJ没有权限写入Java时，此选项将不可用。

<div class="videos-container">
  <video controls preload="metadata">
    <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/route-security.mp4" type="video/mp4" />
  </video>
</div>
