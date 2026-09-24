---
title: 配置
sidebar_position: 8
description: >-
  Every craftforJ configuration property, its default, and what turning each
  feature off changes.
_i18n_hash: 025fb1766af8cbcb741f6f353bdf6523
---
craftforJ 在 `webforj.conf` 中配置。属性名称在 [Spring](/docs/integrations/spring/overview) 中是相同的，因此如果你的配置位于 `application.properties`，请在此处进行设置。

## 必需属性 {#required-properties}

| 属性 | 类型 | 默认值 | 描述 |
|------|------|--------|------|
| **`webforj.debug`** | 布尔值 | `false` | 启用调试模式。craftforJ 需要此项 |
| **`webforj.devtools.craftforj.enabled`** | 布尔值 | `false` | 启用 craftforJ |

这两个属性必须启用。有关 craftforJ 为什么需要两个设置而不是一个的原因，请参见 [安全性](/docs/craftforj/security#two-required-settings)。

## 访问 {#access}

| 属性 | 类型 | 默认值 | 描述 |
|------|------|--------|------|
| **`webforj.devtools.craftforj.hosts-allowed`** | 列表或字符串 | 仅环回 | 允许超出运行应用程序的机器的客户端地址 |

默认情况下，只有与应用程序在同一机器上的浏览器才能访问 craftforJ。要允许其他机器，请列出它们的地址。以 `*` 结尾的条目匹配前缀，单个 `*` 则完全取消限制。

```ini title="webforj.conf"
webforj.devtools.craftforj.hosts-allowed = ["192.168.1.42", "10.0.0.*"]
```

:::warning 通配符允许任何能访问你的应用的人
craftforJ 会读取和写入你的项目源代码。仅在你确定谁能访问该端口的网络上使用 `*`，例如仅由你使用的容器。切勿在共享网络上使用它。
:::

## 项目根目录 {#project-root}

| 属性 | 类型 | 默认值 | 描述 |
|------|------|--------|------|
| **`webforj.devtools.craftforj.project-root`** | 字符串 | 检测到 | 你的源代码所在的目录 |

craftforJ 根据应用程序的启动方式确定你的项目位置。不寻常的项目布局和某些容器设置会妨碍该检测。如果 [应用程序信息](/docs/craftforj/app-info) 报告了错误的项目根目录，请在此处设置。

## 功能标志 {#feature-flags}

这些默认情况下都是启用的。关闭其中一个会限制 craftforJ 被允许执行的操作。

| 属性 | 关闭它会移除 |
|------|--------------|
| **`webforj.devtools.craftforj.source-changes`** | 将属性更改写回 Java，并更改路由访问 |
| **`webforj.devtools.craftforj.stylesheet-changes`** | 将主题和样式保存到样式表中 |
| **`webforj.devtools.craftforj.ai.enabled`** | AI 助手 |
| **`webforj.devtools.craftforj.ai.freeform-changes`** | 助手自行编写 Java |

关闭一个标志会禁止所有使用该应用的用户使用该功能。craftforJ 的设置是每个开发者的，且只能进一步限制，因此开发者无法重新启用应用程序关闭的功能。

:::info 你关闭的功能仍然可以看到
当一个标志关闭时，控制仍然在 craftforJ 中，并被标记为连接的应用不支持。
:::

:::warning 在生产环境中
请保持 `webforj.devtools.craftforj.enabled` 不设置。有关完整清单，请参见 [安全性](/docs/craftforj/security#in-production)。
:::
