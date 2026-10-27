---
title: 安全
sidebar_position: 9
description: >-
  What craftforJ can reach in your project, how it restricts access, and how to
  confirm it's disabled in production.
_i18n_hash: 5ffbc5b5c6e6cfcf64143712a21944d5
---
craftforJ 读取和写入它所附加项目的源代码。本页描述了有关这一点的界限，以及如何确认在您部署的构建中 craftforJ 已关闭。

## 两个必需设置 {#two-required-settings}

craftforJ 需要启用以下两个设置：

- `webforj.debug`
- `webforj.devtools.craftforj.enabled`

这两个设置单独并不会起作用。一个在调试模式下运行到生产环境的应用不会暴露 craftforJ，而一个在共享配置文件中包含 craftforJ 属性的应用在调试模式外也不会暴露它。

通过 [startforJ](https://docs.webforj.com/startforj) 创建的项目或通过 webforJ [原型](/docs/building-ui/archetypes/overview) 创建的项目都启用了这两个设置，因此 craftforJ 从第一次运行就能正常工作。在您部署之前，请按照下面的 [生产检查表](#in-production) 逐项检查。

## 默认本地访问 {#local-access-by-default}

只有在运行应用的机器上的浏览器可以访问 craftforJ。其他一切请求都会被拒绝，并且这一点在您没有进行任何配置的情况下也适用。如需从另一台机器访问 craftforJ，请在 [`hosts-allowed`](/docs/craftforj/configuration#access) 中命名该机器。地址是逐字匹配的，因此客户端无法通过伪装成其他内容来通过验证。

:::warning 通配符完全移除限制
将 `hosts-allowed = "*"` 设置为允许任何可以访问您应用端口的人读取和写入您的项目源代码。它存在于封闭环境中，例如仅您可以访问的容器。请勿在其他地方使用它。
:::

## 无新增 HTTP 接口 {#no-added-http-surface}

craftforJ 不会向您的应用添加任何 HTTP 端点、servlet 或过滤器。它通过您的应用已经建立的连接工作，因此启用 craftforJ 后，您的应用响应的请求集与未启用时完全相同。

## 请求来自您的页面 {#requests-come-from-your-page}

craftforJ 仅对来自您服务器实际提供的页面的请求起作用。任何从其他地方进入页面的脚本，例如被破坏的依赖项或粘贴到控制台中的内容，都无法驱动 craftforJ。

## API 密钥 {#api-keys}

您的密钥存储在运行您应用的机器上。[AI 助手](/docs/craftforj/ai) 在浏览器中运行，因此 craftforJ 必须将密钥传递给它以进行工作，并且在页面打开时它会将该密钥保持在内存中。不会写入浏览器存储，关闭页面后不会留下任何内容。

助手然后通过浏览器与您的提供商进行通信，而不是通过您的服务器。没有中继，没有代理，没有遥测，也没有第三方在其中。

到达您的提供商的仅仅是对话本身，包括助手查看的您应用的部分以及它所拍摄的任何截图。在将托管模型指向运行真实数据的应用之前，请考虑这一点。正在本地运行的模型会将所有内容保留在您的机器上。

## craftforJ 可以更改的内容 {#what-craftforj-can-change}

启用所有功能后，craftforJ 可以：

- 读取项目根目录下的任何源文件
- 写入 Java 源文件，包括路由访问注释
- 写入您应用的样式表
- 更改和移除运行中的应用中的组件
- 导航运行中的应用

这些功能可以 [独立关闭](/docs/craftforj/configuration#feature-flags)，每次写入磁盘都通过您审核的 diff 进行。

## 在生产中 {#in-production}

请关闭 craftforJ。除非您手动启用它，否则它是关闭的，因此在大多数情况下无需进行任何操作。确认步骤：

1. 在您实际部署的配置中，`webforj.devtools.craftforj.enabled` 未设置或为 `false`。
2. 在同一配置中，`webforj.debug` 未设置或为 `false`。
3. 环境变量或仅适用于生产的配置文件不设置这两个属性。
4. 加载已部署的应用并确认页面上没有 craftforJ 的触发。

有关更广泛的视图，请参见 [生产强化](/docs/security/application-security/production-hardening)。

## 报告安全问题 {#reporting-a-security-issue}

如果您在 craftforJ 中发现安全问题，请通过 [webforJ 安全政策](https://github.com/webforj/webforj/security) 报告，而不是在公开问题中报告。
