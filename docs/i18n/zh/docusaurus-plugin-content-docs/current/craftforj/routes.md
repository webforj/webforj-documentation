---
title: 路由
sidebar_position: 5
description: >-
  See every registered route in a running webforJ app, navigate to it from
  craftforJ, and change the access rules declared on it.
_i18n_hash: ec7a4521cd9c4328f9cd6550ebc9d90b
---
“Routes”选项卡会显示运行中应用的路由表，路由器会按照[层级结构](/docs/routing/route-hierarchy/overview)保存路由，并标记当前活动路由。[动态](/docs/routing/routes-registration)注册的路由也会与带注解的路由一同显示。

![标记了活动路由的路由树](/img/craftforj/routes/tree.png#rounded-border)

## 路由详情 {#route-details}

选择某个路由后，可以查看路由器掌握的相关信息，包括其路径、对应的类、附加到该路由的生命周期观察器，以及路由配置。你可以在此处的源代码查看器中打开对应的类。

## 从 craftforJ 导航 {#navigating-from-craftforj}

你可以直接从 craftforJ 导航到任意路由。带参数的路由会为每个参数提供一个输入框，并在你填写时解析路径，这样你就能在导航前确认目标位置。

这种方式执行的是真正的导航，因此应用的[生命周期观察器](/docs/routing/navigation-lifecycle/observers)会像用户导航时一样运行。路由树也会跟随应用状态，因此在应用本身中导航时，标记也会随之移动。

<VideoPlayer video="craftforj/route-navigate" width={1920} height={1000} title="Route navigate" />

## 访问规则 {#access-rules}

每个路由都会显示其声明的[安全注解](/docs/security/annotations)徽标，你也可以通过工具栏将路由树筛选为仅显示公开或受保护的路由。

只有 `@RolesAllowed` 和 `@DenyAll` 会被视为受保护。`@PermitAll` 不指定任何角色，只要求用户已登录，因此筛选器会将其视为公开路由。检查哪些路由限制了基于角色的访问时，请注意这一点。

![每个路由都带有访问权限徽标的路由树](/img/craftforj/routes/access-badge.png#rounded-border)

你也可以在 craftforJ 中更改路由的访问规则。craftforJ 会将注解写入路由所属的类，然后重启应用，因此这一更改会像其他[源代码更改](/docs/craftforj/source-changes)一样经过审查。如果 craftforJ 无权写入 Java，则此选项不可用。

<VideoPlayer video="craftforj/route-security" width={1920} height={1000} title="Route security" />
