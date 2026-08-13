---
title: Property Configuration
sidebar_position: 1
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: 0f672146394b053aaa5d59a7e59841b2
---
# 配置 webforJ 属性

为了成功部署和运行 webforJ 应用程序，需要几个关键的配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，包括入口点和调试设置，以及 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 的核心配置文件，指定应用程序设置，如入口点、调试模式和客户端-服务器交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应位于 `resources` 目录中。

:::tip
如果您正在与 [Spring](../integrations/spring/overview.md) 集成，可以在 `application.properties` 文件中设置这些 `webforj.conf` 属性。
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

| 属性                                 | 类型    | 说明                                                              | 默认值                |
| ------------------------------------ | ------- | ----------------------------------------------------------------- | --------------------- |
| **`webforj.assetsCacheControl`**     | 字符串  | 静态资源的 Cache-Control 头。                                     | `null`                |
| **`webforj.assetsDir`**              | 字符串  | 用于服务静态文件的路由名称，而实际文件夹名称保持为 `static`。这一配置在默认 `static` 路由与应用程序中定义的路由冲突时非常有用，可以更改路由名称，而无需重命名文件夹。 | `null`                |
| **`webforj.assetsExt`**              | 字符串  | 静态文件的默认文件扩展名。                                       | `null`                |
| **`webforj.assetsIndex`**            | 字符串  | 为目录请求提供的默认文件（例如，index.html）。                   | `null`                |
| **`webforj.clientHeartbeatRate`**    | 字符串  | 客户端 ping 服务器以查看其是否仍然活着的间隔。为了开发，设置为较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产中设置为 50 秒或更高以避免过多请求。 | `50s`                 |
| **`webforj.components`**             | 字符串  | 指定时，基本路径决定从哪里加载 DWC 组件。默认情况下，组件从托管应用程序的服务器加载。然而，设置自定义基本路径允许从另一个服务器或 CDN 加载组件。例如，要从 jsdelivr.com 加载组件，将基本路径设置为： https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 加载的组件必须与使用的 webforJ 框架版本兼容；否则，应用程序可能无法正常工作。在使用不带引擎的标准 BBj 安装时，将忽略此设置。对于标准 BBj 安装，设置可以通过 `!COMPONENTS` STBL 管理。 | `null`                |
| **`webforj.debug`**                  | 布尔值 | 启用调试模式。在调试模式下，webforJ 会向控制台打印额外信息，并在浏览器中显示所有异常。默认情况下禁用调试模式。 | `null`                |
| **`webforj.devtools.craftforj.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔值 | **仅限开发环境。** 启用 [craftforJ](../craftforj/overview.md)，这是一个检查运行中应用程序、编辑组件属性并将更改写回 Java 源代码的开发环境。还需要启用 `webforj.debug`。单独使用这两个属性都不够。 | `false`               |
| **`webforj.devtools.craftforj.hosts-allowed`**&nbsp;<DocChip chip='since' label='26.02' /> | 列表   | 允许超出运行应用程序的机器的客户端地址访问 craftforJ。默认情况下，只有该机器上的浏览器才能访问它。以 `*` 结尾的条目匹配前缀，`*` 的单个条目移除限制。请参阅 [craftforJ 安全](../craftforj/security.md)。 | 回环地址专用          |
| **`webforj.devtools.craftforj.project-root`**&nbsp;<DocChip chip='since' label='26.02' /> | 字符串  | craftforJ 查找您的源代码的目录，以防它无法从应用程序启动方式中确定。 | 检测到                |
| **`webforj.devtools.craftforj.source-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔值 | 是否允许 craftforJ 将属性更改和路由访问规则写入您的 Java 源代码。 | `true`                |
| **`webforj.devtools.craftforj.stylesheet-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔值 | 是否允许 craftforJ 将主题和样式保存到您的应用样式表中。 | `true`                |
| **`webforj.devtools.craftforj.ai.enabled`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔值 | 是否启用 [craftforJ AI 助手](../craftforj/ai.md)。 | `true`                |
| **`webforj.devtools.craftforj.ai.freeform-changes`**&nbsp;<DocChip chip='since' label='26.02' /> | 布尔值 | 助手是否可以自己编写 Java，而不仅仅是更改属性。每次编辑仍然必须编译，并仍然需要您的批准。 | `true`                |
| **`webforj.entry`**                  | 字符串  | 通过指定扩展 `webforj.App` 的类的完全限定名称来定义应用程序的入口点。如果未定义入口点，webforJ 将自动扫描类路径以查找扩展 `webforj.App` 的类。如果找到多个类，将会发生错误。当一个包包含多个潜在入口点时，显式设置这个是必需的以防止歧义；或者，可以使用 `AppEntry` 注解在运行时指定入口点。 | `null`                |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | 列表   | 作为 BCP 47 语言标签的支持区域列表（例如，`"en"`，`"en-US"`，`"fr"`，`"de-DE"`）。启用自动检测时，浏览器的首选区域将与此列表匹配。列表中的第一个区域用作默认回退。请参阅 [翻译](../advanced/i18n-localization.md)。 | `[]`                  |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | 布尔值 | 当为 `true` 时，应用程序区域在启动时自动从浏览器的首选语言设置。区域通过将浏览器的首选区域与 `supported-locales` 列表进行匹配来解决。当为 `false` 或 `supported-locales` 为空时，应用程序使用 `webforj.locale`。请参阅 [翻译](../advanced/i18n-localization.md)。 | `false`               |
| **`webforj.fileUpload.accept`**      | 列表   | 允许的文件上传类型。默认情况下，允许所有文件类型。支持的格式包括 MIME 类型，如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名如 `*.txt`。在使用标准 BBj 安装时，此设置将被忽略并通过 `fileupload-accept.txt` 管理。 | `[]`                  |
| **`webforj.fileUpload.maxSize`**     | 长整型 | 允许的最大文件大小（字节）。默认情况下，没有限制。在使用标准 BBj 安装时，此设置将被忽略并通过 `fileupload-accept.txt` 管理。 | `null`                |
| **`webforj.iconsDir`**               | 字符串  | 图标目录的 URL 端点（默认从 `resources/icons/` 提供服务）。 | `icons/`              |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | 布尔值 | 当为 `true` 时，包裹在 `<html>` 中的值将其内容呈现为 HTML。当为 `false` 时，相同的值将按字面意思显示。 | `true`                |
| **`webforj.license.cfg`**            | 字符串  | 许可证配置目录。默认情况下，它与 webforJ 配置目录相同，但如果需要，可以自定义。 | `"."`                 |
| **`webforj.license.startupTimeout`** | 整数   | 许可证启动超时时间（秒）。 | `null`                |
| **`webforj.locale`**                 | 字符串  | 应用程序的区域，确定语言、区域设置以及日期、时间和数字的格式。 | `null`                |
| **`webforj.quiet`**                  | 布尔值 | 在应用程序启动期间禁用加载图像。 | `false`               |
| **`webforj.reloadOnServerError`**    | 布尔值 | **仅限开发环境。** 在开发环境中，遇到与热重部署相关的错误时自动重新加载页面，但不包括其他错误类型。使用热重部署时，如果客户端在服务器重启期间向服务器发送请求，在 WAR 文件被交换时可能会发生错误。由于服务器可能很快上线，此设置允许客户端自动尝试重新加载页面。 | `false`               |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | 整数   | 应用程序将接受的最大请求大小（字节），作为防止过大请求耗尽服务器内存的保障。设置为 `0` 可禁用限制。 | `0`                   |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | 整数   | 应用程序每分钟启动的新会话数量，作为防止快速会话创建耗尽服务器资源的保障。设置为 `0` 可禁用速率限制。 | `0`                   |
| **`webforj.servlets[n].name`**       | 字符串  | Servlet 名称（如果未指定，则使用类名）。 | `null`                |
| **`webforj.servlets[n].className`**  | 字符串 | Servlet 的完全限定类名。 | `null`                |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet 初始化参数。 | `null`                |
| **`webforj.sessionTimeout`**         | 整数   | 会话超时时间（秒）。 | `60`                  |
| **`webforj.stringTable`**            | `Map<String,String>` | 用于在应用程序中存储字符串的键值对映射。对存储应用程序消息或标签非常有用。有关 `StringTable` 的更多信息，请参见 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。 | `{}`                   |
| **`webforj.mime.extensions`**            | `Map<String,String>` | 服务静态文件时文件扩展名的自定义 MIME 类型映射。允许您覆盖默认 MIME 类型或为自定义扩展定义 MIME 类型。映射键是文件扩展名（不带点），值是 MIME 类型。 | `{}`                   |

## 配置 `web.xml` {#configuring-webxml}

`web.xml` 文件是 Java Web 应用程序的基本配置文件，在 webforJ 中定义重要设置，如 servlet 配置、URL 模式和欢迎页面。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                                                                           | 默认值                   |
| ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------ |
| **`<display-name>`**                  | 设置 Web 应用程序的显示名称，通常源自项目名称。此名称出现在应用服务器的管理控制台中。                                                                                                           | `${project.name}`       |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，这是处理 webforJ 请求的核心 servlet。该 servlet 映射到所有 URL (`/*`)，使其成为 Web 请求的主要入口点。                                                                       | `WebforjServlet`        |
| **`<load-on-startup>`**               | 指定在应用启动时应加载 `WebforjServlet`。将其设置为 `1` 使 servlet 立即加载，这样可以改善初始请求的处理。                                                                               | `1`                      |
