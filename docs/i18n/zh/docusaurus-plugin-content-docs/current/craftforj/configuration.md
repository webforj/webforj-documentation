---
title: 配置
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: f7e73354e4d9548e79122405c21ae275
---
craftforJ 在 `webforj.conf` 中进行配置。属性名称在 [Spring](/docs/integrations/spring/overview) 中是相同的，因此如果你的配置存放在 `application.properties` 中，请在那里设置它们。

## 必需的属性 {#required-properties}

| 属性 | 类型 | 默认值 | 描述 |
|------|------|---------|------|
| **`webforj.debug`** | 布尔值 | `false` | 启用调试模式。craftforJ 需要此模式 |
| **`webforj.devtools.craftforj.enabled`** | 布尔值 | `false` | 启用 craftforJ |

这两个属性必须启用。有关为什么 craftforJ 需要两个设置而不是一个，请参见 [安全性](/docs/craftforj/security#two-required-settings)

## 访问 {#access}

| 属性 | 类型 | 默认值 | 描述 |
|------|------|---------|------|
| **`webforj.devtools.craftforj.hosts-allowed`** | 列表或字符串 | 仅回环地址 | 允许超出运行应用程序的机器的客户端地址 |

默认情况下，只有与应用程序在同一机器上的浏览器可以访问 craftforJ。要允许其他机器，列出它们的地址。以 `*` 结尾的条目匹配前缀，单个 `*` 则完全移除限制。

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning 通配符允许任何可以访问你的应用程序的人
craftforJ 读取和写入你的项目源代码。仅在你确定谁可以访问端口的网络上使用 `*`，例如仅由你使用的容器。切勿在共享网络上使用它。
:::

## 项目根目录 {#project-root}

| 属性 | 类型 | 默认值 | 描述 |
|------|------|---------|------|
| **`webforj.devtools.project-root`** | 字符串 | 自动检测 | 你的源代码所在的目录 |

craftforJ 根据应用程序的启动方式来确定你的项目位置。不寻常的项目布局和某些容器设置会导致检测失败。如果 [应用信息](/docs/craftforj/app-info) 报告错误的项目根目录，请在此处设置。

```ini title="webforj.conf"
webforj.devtools.project-root = "/home/me/projects/my-app"
```

在 Spring 中，请在 `application.properties` 中设置相同的键。

:::info 在 26.03 中重命名
此设置以前为 `webforj.devtools.craftforj.project-root`。旧键仍然可以作为后备使用，但已被弃用，并在使用时记录警告。如果同时设置了两个键，则 `webforj.devtools.project-root` 优先。
:::

## 功能标志 {#feature-flags}

这些标志默认情况下都是启用的。关闭其中一个会缩小 craftforJ 的可执行操作范围。

| 属性 | 关闭它将移除的功能 |
|------|------------------------|
| **`webforj.devtools.craftforj.source-changes`** | 将属性更改写回 Java，以及更改路由访问 |
| **`webforj.devtools.craftforj.stylesheet-changes`** | 将主题和样式保存到你的样式表中 |
| **`webforj.devtools.craftforj.ai.enabled`** | AI 助手 |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | 助手自己编写 Java |

关闭某个标志会对使用该应用的所有用户关闭该功能。craftforJ 的设置是针对每个开发者的，并且只能进一步缩小，因此开发者无法将应用关闭的能力重新开启。

:::info 你关闭的功能始终可见
当某个标志关闭时，控制权仍在 craftforJ 中，并标记为连接的应用不支持。
:::

:::warning 在生产环境中
请保持 `webforj.devtools.craftforj.enabled` 未设置。有关完整检查清单，请参见 [安全性](/docs/craftforj/security#in-production)。
:::
