---
title: 属性配置
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: fd84f72aa62a993485d33fe9a94ab0d1
---
# 配置 webforJ 属性

为了成功部署和运行 webforJ 应用程序，需要几个关键的配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，从入口点和调试设置到 Servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 的核心配置文件，指定应用程序设置，如入口点、调试模式和客户端-服务器交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应位于 `resources` 目录中。

:::tip
如果您正与 [Spring](../integrations/spring/overview.md) 进行集成，可以在 `application.properties` 文件中设置这些 `webforj.conf` 属性。
:::

### 示例 `webforj.conf` 文件 {#example-webforjconf-file}

```Ini
# 此配置文件采用 HOCON 格式：
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### 配置选项 {#configuration-options}

| 属性                                 | 类型    | 说明                                                             | 默认值                |
|--------------------------------------|---------|-------------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | 字符串  | 静态资源的 Cache-Control 头。                                     | `null` |
| **`webforj.assetsDir`**              | 字符串  | 用于服务静态文件的路由名称，而实际文件夹名称保持为 `static`。此配置有助于在默认 `static` 路由与应用程序中定义的路由冲突时更改路由名称，而无需重命名文件夹本身。 | `null`               |
| **`webforj.assetsExt`**              | 字符串  | 静态文件的默认文件扩展名。                                     | `null` |
| **`webforj.assetsIndex`**            | 字符串  | 目录请求（例如，index.html）服务的默认文件。                   | `null` |
| **`webforj.clientHeartbeatRate`**    | 字符串  | 客户端向服务器发送心跳检查其是否仍然在线的间隔。对于开发，设置为较短的间隔，例如 `8s`，以快速检测服务器的可用性。在生产中设置为 50 秒或更高以避免过多请求。 | `50s`           |
| **`webforj.components`**             | 字符串  | 指定时，基本路径决定从何处加载 DWC 组件。默认情况下，组件从托管应用程序的服务器加载。然而，设置自定义基本路径允许从另一个服务器或 CDN 加载组件。例如，要从 jsdelivr.com 加载组件，将基本路径设置为：https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 加载的组件必须与使用中的 webforJ 框架版本兼容；否则，应用程序可能无法正常工作。使用不带引擎的标准 BBj 安装时，此设置将被忽略。对于标准 BBj 安装，该设置可以通过 `!COMPONENTS` STBL 管理。 | `null`          |
| **`webforj.debug`**                  | 布尔型 | 启用调试模式。在调试模式下，webforJ 会向控制台打印额外信息，并在浏览器中显示所有异常。调试模式默认情况下是禁用的。 | `null`          |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔型 | **仅限开发环境。** 启用 [craftforJ](../craftforj/overview.md)，该开发环境可以检查正在运行的应用程序、编辑组件属性并将更改写回 Java 源文件。需要启用 `webforj.debug`。两个属性单独都不够。 | `false` |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | 列表   | 被允许访问 craftforJ 的客户端地址，超出运行应用的机器。 默认情况下，只有该机器上的浏览器可以访问它。以 `*` 结尾的条目与前缀匹配，单个 `*` 会移除限制。参见 [craftforJ 安全](../craftforj/security.md)。 | 仅回环 |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔型 | craftforJ 是否可以将属性更改和路由访问规则写入 Java 源文件。 | `true` |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔型 | craftforJ 是否可以将主题和样式保存到应用样式表中。 | `true` |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔型 | [craftforJ AI 助手](../craftforj/ai.md) 是否可用。 | `true` |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔型 | 助手是否可以编写自己的 Java 代码，而不仅仅是更改属性。每次编辑仍然必须编译，并且仍然需要您的批准。 | `true` |
| **`webforj.devtools.project-root`**&nbsp;<DocChip chip='since' label='26.03' /> | 字符串  | `craftforJ` 寻找源文件的目录，以防它无法从应用程序的启动方式确定。 替换已弃用的 `webforj.devtools.craftforj.project-root`，但在未设置此键时仍会被读取。 | 检测到 |
| **`webforj.entry`**                  | 字符串  | 通过指定扩展 `webforj.App` 的类的完全限定名称来定义应用程序的入口点。如果没有定义入口点，webforJ 将自动扫描类路径，以查找扩展 `webforj.App` 的类。如果找到多个类，将会发生错误。当一个包包含多个潜在的入口点时，必须明确设置以防止歧义，或者可以使用 `AppEntry` 注释在运行时指定入口点。 | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | 列表   | 作为 BCP 47 语言标签的支持区域列表（例如，`"en"`、`"en-US"`、`"fr"`、`"de-DE"`）。当启用自动检测时，将浏览器的首选区域与此列表进行匹配。列表中的第一个区域用作默认回退。参见 [翻译](../advanced/i18n-localization.md)。 | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | 布尔型 | 当 `true` 时，应用程序区域在启动时自动根据浏览器的首选语言设置。区域是通过将浏览器的首选区域与 `supported-locales` 列表进行匹配解析的。当 `false` 或 `supported-locales` 为空时，应用程序使用 `webforj.locale`。参见 [翻译](../advanced/i18n-localization.md)。 | `false` |
| **`webforj.fileUpload.accept`**      | 列表   | 允许的文件上传类型。默认情况下，所有文件类型都是允许的。支持的格式包括 MIME 类型，例如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名，如 `*.txt`。在使用标准 BBj 安装时，此设置将被忽略，并通过 `fileupload-accept.txt` 管理。 | `[]`            |
| **`webforj.fileUpload.maxSize`**     | 长整型 | 允许的文件上传最大大小（以字节为单位）。默认情况下，没有限制。在使用标准 BBj 安装时，此设置将被忽略，并通过 `fileupload-accept.txt` 管理。 | `null`          |
| **`webforj.iconsDir`**               | 字符串  | 图标目录的 URL 端点（默认从 `resources/icons/` 服务）。 | `icons/` |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | 布尔型 | 当 `true` 时，包裹在 `<html>` 中的值以 HTML 渲染其内容。当 `false` 时，显示相同的值为文字。 | `true` |
| **`webforj.license.cfg`**            | 字符串  | 许可证配置目录。默认情况下，它与 webforJ 配置目录相同，但可以根据需要自定义。 | `"."`  |
| **`webforj.license.startupTimeout`** | 整数   | 许可证启动超时（以秒为单位）。 | `null` |
| **`webforj.locale`**                 | 字符串  | 应用程序的区域，决定语言、区域设置以及日期、时间和数字的格式。 | `null` |
| **`webforj.quiet`**                  | 布尔型 | 在应用程序启动期间禁用加载图像。 | `false` |
| **`webforj.reloadOnServerError`**    | 布尔型 | **仅限开发环境。** 在开发环境中，在与热重新部署相关的错误上自动重新加载页面，但不适用于其他错误类型。当使用热重新部署时，如果客户端在服务器重新启动时向服务器发送请求，则在 WAR 文件交换期间可能会发生错误。由于服务器很可能很快就会恢复在线，此设置允许客户端自动尝试重新加载页面。 | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | 整数   | 应用程序将接受的最大请求（以字节为单位），作为防止超大请求耗尽服务器内存的保护措施。设置为 `0` 以禁用限制。 | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | 整数   | 应用程序每分钟将启动的新会话数量，作为防止快速会话创建耗尽服务器资源的保护措施。设置为 `0` 以禁用速率限制。 | `0` |
| **`webforj.servlets[n].name`**       | 字符串  | Servlet 名称（如果未指定，则使用类名）。 | `null` |
| **`webforj.servlets[n].className`**  | 字符串 | Servlet 的完全限定类名。 | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet 初始化参数。 | `null` |
| **`webforj.sessionTimeout`**         | 整数   | 会话超时持续时间（以秒为单位）。 | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | 用于存储在应用程序中使用的字符串的键值对映射。对于存储应用程序消息或标签非常有用。有关 `StringTable` 的更多信息，请参见 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。 | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | 提供静态文件服务时，文件扩展名的自定义 MIME 类型映射。允许您覆盖默认 MIME 类型或为自定义扩展定义 MIME 类型。该映射的键是文件扩展名（没有点），值是 MIME 类型。 | `{}`            |

## 配置 `web.xml` {#configuring-webxml}

`web.xml` 文件是 Java Web 应用程序的基本配置文件，在 webforJ 中，它定义了 servlet 配置、URL 模式和欢迎页面等重要设置。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                                   | 默认值                   |
|--------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|
| **`<display-name>`**                    | 设置 Web 应用程序的显示名称，通常来自项目名称。该名称出现在应用服务器的管理控制台中。                                                                     | `${project.name}`        |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，用于处理 webforJ 请求的核心 servlet。此 servlet 映射到所有 URL (`/*`)，使其成为 Web 请求的主要入口点。                       | `WebforjServlet`         |
| **`<load-on-startup>`**                 | 指定 `WebforjServlet` 应在应用程序启动时加载。将其设置为 `1` 会使 servlet 马上加载，从而改善初始请求处理。                                            | `1`                      |
