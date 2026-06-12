---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
_i18n_hash: 2eb59302da44bcdd27d6366419bd78ad
---
# 配置 webforJ 属性

要成功部署和运行 webforJ 应用程序，需要一些关键的配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，从入口点和调试设置到 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 中的核心配置文件，指定应用程序设置，如入口点、调试模式和客户端-服务器交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，并应位于 `resources` 目录中。

:::tip
如果您正在集成 [Spring](../integrations/spring/overview.md)，可以在 `application.properties` 文件中设置这些 `webforj.conf` 属性。
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

| 属性                                 | 类型    | 说明                                                       | 默认值                |
|--------------------------------------|---------|----------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | 静态资源的 Cache-Control 头部。                          | `null` |
| **`webforj.assetsDir`**              | String  | 用于提供静态文件的路由名称，而实际文件夹名称仍为 `static`。此配置有助于在默认 `static` 路由与您应用中定义的路由冲突时，允许您更改路由名称而无需重命名文件夹本身。       | `null`               |
| **`webforj.assetsExt`**              | String  | 静态文件的默认文件扩展名。                             | `null` |
| **`webforj.assetsIndex`**            | String  | 用于目录请求的默认文件（例如，index.html）。          | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | 客户端向服务器发送 ping 以检查其是否仍然活着的间隔。对于开发，设置为较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产中设置为 50 秒或更高，以避免过多的请求。 | `50s`           |
| **`webforj.components`**             | String  | 如果指定，基本路径决定 DWC 组件的加载位置。默认情况下，组件从托管应用程序的服务器加载。然而，设置自定义基本路径允许组件从另一个服务器或 CDN 加载。例如，要从 jsdelivr.com 加载组件，请将基本路径设置为： https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 重要的是，加载的组件必须与使用的 webforJ 框架版本兼容；否则，应用程序可能无法正常工作。本设置在使用没有引擎的标准 BBj 安装时被忽略。对于标准 BBj 安装，此设置可以通过 `!COMPONENTS` STBL 进行管理。 | `null`          |
| **`webforj.debug`**                  | Boolean | 启用调试模式。在调试模式下，webforJ 将打印额外信息到控制台，并在浏览器中显示所有异常。调试模式默认情况下是禁用的。 | `null`          |
| **`webforj.entry`**                  | String  | 通过指定扩展 `webforj.App` 的类的完全限定名称来定义应用程序的入口点。如果未定义入口点，webforJ 将自动扫描类路径以查找扩展 `webforj.App` 的类。如果发现多个类，则会发生错误。当一个包中包含多个潜在的入口点时，显式设置此项是必须的，以防止歧义，或者可以使用 `AppEntry` 注释在运行时指定入口点。 | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | List | 支持的区域设置列表，作为 BCP 47 语言标签（例如，`"en"`、`"en-US"`、`"fr"`、`"de-DE"`）。启用自动检测时，浏览器的首选区域设置会与此列表匹配。列表中的第一个区域设置被用作默认回退。请参见 [翻译](../advanced/i18n-localization.md)。 | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | 当 `true` 时，应用程序的区域设置在启动时自动从浏览器的首选语言设置。区域设置通过将浏览器的首选区域设置与 `supported-locales` 列表匹配来解析。当 `false` 或 `supported-locales` 为空时，应用程序使用 `webforj.locale`。请参见 [翻译](../advanced/i18n-localization.md)。 | `false` |
| **`webforj.fileUpload.accept`**      | List    | 文件上传允许的文件类型。默认情况下，所有文件类型都是允许的。支持的格式包括 MIME 类型，如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名，如 `*.txt`。在使用标准 BBj 安装时，此设置被忽略并通过 `fileupload-accept.txt` 管理。 | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | 文件上传允许的最大文件大小，以字节为单位。默认情况下，不限制。使用标准 BBj 安装时，此设置被忽略并通过 `fileupload-accept.txt` 管理。 | `null`          |
| **`webforj.iconsDir`**               | String  | 图标目录的 URL 端点（默认从 `resources/icons/` 提供）。 | `icons/` |
| **`webforj.license.cfg`**            | String  | 许可证配置的目录。默认情况下，它与 webforJ 配置目录相同，但如果需要，可以自定义。 | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | 许可证启动超时时间（以秒为单位）。 | `null` |
| **`webforj.locale`**                 | String  | 应用程序的区域设置，确定语言、区域设置以及日期、时间和数字的格式。 | `null` |
| **`webforj.quiet`**                  | Boolean | 禁用应用程序启动期间的加载图像。 | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **仅限开发环境。** 在开发环境中，在与热重部署相关的错误出现时，自动重新加载页面，但不适用于其他错误类型。当使用热重部署时，如果客户端在服务器重新启动时向服务器发送请求，则在 WAR 文件被替换时可能会出现错误。由于服务器可能很快就会恢复在线，因此此设置允许客户端自动尝试页面重新加载。 | `false` |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | 应用程序接受的最大请求，单位为字节，用作防止过大请求耗尽服务器内存的保护措施。设置为 `0` 禁用限制。 | `0` |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | Integer | 应用程序每分钟启动的新会话数，作为防止快速会话创建耗尽服务器资源的保护措施。设置为 `0` 禁用速率限制。 | `0` |
| **`webforj.servlets[n].name`**       | String  | servlet 名称（如果未指定，则使用类名）。 | `null` |
| **`webforj.servlets[n].className`**  | String | servlet 的完全限定类名。 | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | servlet 初始化参数。 | `null` |
| **`webforj.sessionTimeout`**         | Integer | 会话超时持续时间（以秒为单位）。 | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | 用于存储应用程序中使用的字符串的键值对映射。对于存储应用程序消息或标签非常有用。更多关于 `StringTable` 的信息可以在 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)找到。 | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | 为提供静态文件时的文件扩展名定义的自定义 MIME 类型映射。这允许您覆盖默认的 MIME 类型或为自定义扩展名定义 MIME 类型。映射键是文件扩展名（不带点），值是 MIME 类型。 | `{}`            |

## 配置 `web.xml` {#configuring-webxml}

`web.xml` 文件是 Java Web 应用程序的基本配置文件，在 webforJ 中，它定义了重要设置，如 servlet 配置、URL 模式和欢迎页面。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                                                       | 默认值                     |
| --------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------- |
| **`<display-name>`**                    | 设置 Web 应用程序的显示名称，通常来源于项目名称。此名称出现在应用服务器的管理控制台中。                                                                                          | `${project.name}`          |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，这是处理 webforJ 请求的核心 servlet。该 servlet 映射到所有 URL（`/*`），成为 Web 请求的主要入口点。                                                   | `WebforjServlet`           |
| **`<load-on-startup>`**                 | 指定在应用程序启动时应加载 `WebforjServlet`。将其设置为 `1` 会使该 servlet 立即加载，这改善了初始请求的处理。                                                                       | `1`                        |
