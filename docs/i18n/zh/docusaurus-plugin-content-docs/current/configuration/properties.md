---
title: Property Configuration
sidebar_position: 30
description: >-
  Set webforJ entry points, debug mode, locales, file upload limits, and servlet
  mappings through webforj.conf and web.xml.
sidebar_class_name: updated-content
_i18n_hash: c58a4908cfbde685bc0b30f6023e1df6
---
# 配置 webforJ 属性

要成功部署和运行 webforJ 应用程序，需要几个关键配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，从入口点和调试设置到 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 的核心配置文件，指定应用程序设置，如入口点、调试模式和客户端-服务器交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应位于 `resources` 目录中。

:::tip
如果您与 [Spring](../integrations/spring/overview.md) 集成，可以在 `application.properties` 文件中设置这些 `webforj.conf` 属性。
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

| 属性                               | 类型    | 说明                                                       | 默认值                |
|-----------------------------------|---------|------------------------------------------------------------|----------------------|
| **`webforj.assetsCacheControl`**   | 字符串  | 静态资源的 Cache-Control 头部。                           | `null`              |
| **`webforj.assetsDir`**            | 字符串  | 用于提供静态文件的路由名称，而实际文件夹名称保持为 `static`。该配置对于当默认 `static` 路由与应用程序中定义的路由冲突时很有帮助，允许您在不重命名文件夹的情况下更改路由名称。 | `null`              |
| **`webforj.assetsExt`**            | 字符串  | 静态文件的默认文件扩展名。                               | `null`              |
| **`webforj.assetsIndex`**          | 字符串  | 目录请求时提供的默认文件（例如，index.html）。           | `null`              |
| **`webforj.clientHeartbeatRate`**  | 字符串  | 客户端向服务器发送请求以检查是否仍在运行的间隔。对于开发，将此设置为较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产环境中设置为 50 秒或更高，以避免过多请求。 | `50s`               |
| **`webforj.components`**           | 字符串  | 当指定时，基本路径确定 DWC 组件的加载位置。默认情况下，组件从托管应用程序的服务器加载。但是，设置自定义基本路径允许组件从替代服务器或 CDN 加载。例如，要从 jsdelivr.com 加载组件，将基本路径设置为： https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 加载的组件必须与使用的 webforJ 框架版本兼容；否则，应用程序可能无法正常运行。如果使用标准 BBj 安装，此设置将被忽略。对于标准 BBj 安装，该设置可以通过 `!COMPONENTS` STBL 管理。 | `null`              |
| **`webforj.debug`**                | 布尔型 | 启用调试模式。在调试模式下，webforJ 将打印额外信息到控制台，并在浏览器中显示所有异常。默认情况下禁用调试模式。 | `null`              |
| **`webforj.entry`**                | 字符串  | 通过指定扩展 `webforj.App` 的类的完全限定名称来定义应用程序的入口点。如果未定义入口点，webforJ 将自动扫描类路径以查找扩展 `webforj.App` 的类。如果发现多个类，将发生错误。当包包含多个潜在的入口点时，必须显式设置此项以防止歧义，或者可以使用 `AppEntry` 注解在运行时指定入口点。 | `null`              |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | 列表 | 作为 BCP 47 语言标签的支持区域列表（例如，`"en"`、`"en-US"`、`"fr"`、`"de-DE"`）。启用自动检测时，浏览器的首选区域将与此列表匹配。列表中的第一个区域用作默认后备。请参阅 [翻译](../advanced/i18n-localization.md)。 | `[]`                |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | 布尔型 | 当 `true` 时，应用程序区域在启动时自动设置为浏览器的首选语言。区域通过将浏览器的首选区域与 `supported-locales` 列表匹配来解决。当 `false` 或 `supported-locales` 为空时，应用程序使用 `webforj.locale`。请参阅 [翻译](../advanced/i18n-localization.md)。 | `false`             |
| **`webforj.fileUpload.accept`**    | 列表    | 文件上传的允许文件类型。默认情况下，允许所有文件类型。支持的格式包括 MIME 类型，如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名，如 `*.txt`。使用标准 BBj 安装时，此设置将被忽略并通过 `fileupload-accept.txt` 管理。 | `[]`                  |
| **`webforj.fileUpload.maxSize`**   | 长整型  | 允许文件上传的最大文件大小，以字节为单位。默认情况下，没有限制。使用标准 BBj 安装时，此设置将被忽略并通过 `fileupload-accept.txt` 管理。 | `null`              |
| **`webforj.iconsDir`**             | 字符串  | 图标目录的 URL 端点（默认从 `resources/icons/` 提供。）。 | `icons/`            |
| **`webforj.legacyHtmlInText`**&nbsp;<DocChip chip='since' label='26.01' /> | 布尔型 | 当 `true` 时，用 `<html>` 包裹的值将其内容呈现为 HTML。当 `false` 时，将字面显示相同的值。 | `true`              |
| **`webforj.license.cfg`**          | 字符串  | 许可证配置的目录。默认与 webforJ 配置目录相同，但可以根据需要自定义。 | `"."`               |
| **`webforj.license.startupTimeout`** | 整数   | 许可证启动超时（以秒为单位）。 | `null`              |
| **`webforj.locale`**               | 字符串  | 应用程序的区域，决定语言、区域设置以及日期、时间和数字的格式。 | `null`              |
| **`webforj.quiet`**                | 布尔型 | 在应用程序启动期间禁用加载图像。 | `false`             |
| **`webforj.reloadOnServerError`**  | 布尔型 | **仅适用于开发环境。** 在开发环境中，遇到与热重新部署相关的错误时，自动重新加载页面，而不是其他错误类型。当使用热重新部署时，如果客户端在服务器重新启动时向其发送请求，则在 WAR 文件被交换时可能会发生错误。由于服务器很可能很快重启，因此该设置允许客户端自动尝试重新加载页面。 | `false`             |
| **`webforj.security.maxContentLength`**&nbsp;<DocChip chip='since' label='25.10' /> | 整数   | 应用程序将接受的最大请求大小，以字节为单位，作为防止超大请求耗尽服务器内存的保护措施。设置为 `0` 以禁用限制。 | `0`                  |
| **`webforj.security.maxInitPerMinute`**&nbsp;<DocChip chip='since' label='25.10' /> | 整数   | 应用程序每分钟将启动的新会话数，作为防止快速创建会话耗尽服务器资源的保护措施。设置为 `0` 以禁用速率限制。 | `0`                  |
| **`webforj.servlets[n].name`**     | 字符串  | Servlet 名称（如果未指定，则使用类名）。 | `null`              |
| **`webforj.servlets[n].className`** | 字符串 | Servlet 的完全限定类名。 | `null`              |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet 初始化参数。 | `null`              |
| **`webforj.sessionTimeout`**       | 整数   | 会话超时持续时间（以秒为单位）。 | `60`                  |
| **`webforj.stringTable`**          | `Map<String,String>` | 用于在应用程序中存储字符串的键值对映射。用于存储应用程序消息或标签。有关 `StringTable` 的更多信息，请参阅 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。 | `{}`                  |
| **`webforj.mime.extensions`**       | `Map<String,String>` | 在提供静态文件时自定义文件扩展名的 MIME 类型映射。允许您覆盖默认 MIME 类型或为自定义扩展名定义 MIME 类型。映射键是文件扩展名（不带点），值是 MIME 类型。 | `{}`                  |

## 配置 `web.xml` {#configuring-webxml}

`web.xml` 文件是 Java 网络应用程序的基本配置文件，在 webforJ 中定义重要设置，如 servlet 配置、URL 模式和欢迎页面。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                                                               | 默认值               |
|---------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|
| **`<display-name>`**                   | 设置网络应用程序的显示名称，通常源自项目名称。该名称出现在应用服务器的管理控制台中。                                                                                        | `${project.name}`   |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，这是处理 webforJ 请求的核心 servlet。该 servlet 映射到所有 URL (`/*`)，使其成为网络请求的主要入口点。                                           | `WebforjServlet`    |
| **`<load-on-startup>`**                | 指定 `WebforjServlet` 应在应用启动时加载。将此设置为 `1` 会使 servlet 立即加载，从而改善初始请求处理。                                                                   | `1`                 |
