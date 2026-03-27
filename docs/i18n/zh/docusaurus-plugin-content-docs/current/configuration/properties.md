---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 668649d2e0f92ebc4012e0c58cd1b706
---
# 配置 webforJ 属性

要成功部署和运行 webforJ 应用程序，需要几个关键的配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，从入口点和调试设置到 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 的核心配置文件，指定应用程序设置，如入口点、调试模式和客户端与服务器的交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应位于 `resources` 目录中。

:::tip
如果您正在与 [Spring](../integrations/spring/overview.md) 集成，可以在 `application.properties` 文件中设置这些 `webforj.conf` 属性。
:::



### 示例 `webforj.conf` 文件 {#example-webforjconf-file}

```Ini
# 该配置文件采用 HOCON 格式：
# https://github.com/lightbend/config/blob/master/HOCON.md

webforj.entry = com.webforj.samples.Application
webforj.debug = true
webforj.reloadOnServerError = on
webforj.clientHeartbeatRate = 1s
```

### 配置选项 {#configuration-options}

| 属性                                 | 类型    | 说明                                                         | 默认值                |
|--------------------------------------|---------|--------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**     | String  | 静态资源的 Cache-Control 头。                                 | `null` |
| **`webforj.assetsDir`**              | String  | 用于提供静态文件的路由名称，而实际文件夹名称保持为 `static`。 该配置有助于在默认 `static` 路由与应用程序中定义的路由冲突时，允许您更改路由名称而无需重新命名文件夹。       | `null`               |
| **`webforj.assetsExt`**              | String  | 静态文件的默认文件扩展名。                                  | `null` |
| **`webforj.assetsIndex`**            | String  | 目录请求时服务的默认文件（例如，index.html）。              | `null` |
| **`webforj.clientHeartbeatRate`**    | String  | 客户端 ping 服务器以查看其是否仍然存活的间隔。对于开发，设置为较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产中设置为 50 秒或更高，以避免过量请求。 | `50s`           |
| **`webforj.components`**             | String  | 当指定时，基本路径确定 DWC 组件的加载位置。默认情况下，组件从托管应用程序的服务器加载。然而，设置自定义基本路径允许从备用服务器或 CDN 加载组件。例如，要从 jsdelivr.com 加载组件，请将基本路径设置为： https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 加载的组件必须与使用的 webforJ 框架版本兼容；否则，应用程序可能无法按预期功能运行。 该设置在使用不带引擎的标准 BBj 安装时被忽略。对于标准 BBj 安装，该设置可以通过 `!COMPONENTS` STBL 管理。 | `null`          |
| **`webforj.debug`**                  | Boolean | 启用调试模式。在调试模式下，webforJ 会将额外的信息打印到控制台，并在浏览器中显示所有异常。默认情况下禁用调试模式。 | `null`          |
| **`webforj.entry`**                  | String  | 通过指定扩展 `webforj.App` 的类的完全限定名称来定义应用程序的入口点。如果未定义入口点，webforJ 将自动扫描类路径以寻找扩展 `webforj.App` 的类。如果找到多个类，则会发生错误。当一个包包含多个潜在入口点时，必须显式设置此项以防止歧义，或者可以使用 `AppEntry` 注释在运行时指定入口点。 | `null`          |
| **`webforj.i18n.supported-locales`**&nbsp;<DocChip chip='since' label='25.12' /> | List | 作为 BCP 47 语言标签的支持区域列表（例如，`"en"`、`"en-US"`、`"fr"`、`"de-DE"`）。启用自动检测时，浏览器的首选区域与此列表进行匹配。列表中的第一个区域用作默认回退。有关更多信息，请参见 [翻译](../advanced/i18n-localization.md)。 | `[]` |
| **`webforj.i18n.auto-detect`**&nbsp;<DocChip chip='since' label='25.12' /> | Boolean | 当为 `true` 时，应用程序区域在启动时会自动从浏览器的首选语言设置。通过将浏览器的首选区域与 `supported-locales` 列表匹配来解析区域。当为 `false` 或 `supported-locales` 为空时，应用程序使用 `webforj.locale`。有关更多信息，请参见 [翻译](../advanced/i18n-localization.md)。 | `false` |
| **`webforj.fileUpload.accept`**      | List    | 文件上传的允许文件类型。默认情况下，允许所有文件类型。支持的格式包括 MIME 类型，如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名，如 `*.txt`。在使用标准 BBj 安装时，该设置被忽略，并通过 `fileupload-accept.txt` 管理。 | `[]`            |
| **`webforj.fileUpload.maxSize`**     | Long    | 允许的文件上传最大文件大小，以字节为单位。默认情况下没有限制。在使用标准 BBj 安装时，该设置被忽略，并通过 `fileupload-accept.txt` 管理。 | `null`          |
| **`webforj.iconsDir`**               | String  | 图标目录的 URL 端点（默认从 `resources/icons/` 提供）。 | `icons/` |
| **`webforj.license.cfg`**            | String  | 许可证配置的目录。默认情况下，与 webforJ 配置目录相同，但可以根据需要进行自定义。 | `"."`  |
| **`webforj.license.startupTimeout`** | Integer | 许可证启动超时（以秒为单位）。                              | `null` |
| **`webforj.locale`**                 | String  | 应用程序的区域，决定语言、区域设置以及日期、时间和数字的格式。 | `null` |
| **`webforj.quiet`**                  | Boolean | 禁用应用程序启动期间的加载图像。                          | `false` |
| **`webforj.reloadOnServerError`**    | Boolean | **仅限开发环境。** 在开发环境中，与热重部署相关的错误时，自动重新加载页面，但不包括其他类型的错误。当使用热重部署时，如果客户端在服务器重新启动时向其发送请求，可能会在 WAR 文件被交换时发生错误。由于服务器可能很快就会恢复在线，因此此设置允许客户端自动尝试重新加载页面。  | `false` |
| **`webforj.servlets[n].name`**       | String  | servlet 名称（如果未指定，则使用类名）。                     | `null` |
| **`webforj.servlets[n].className`**  | String | servlet 的完全限定类名。                                     | `null` |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | servlet 初始化参数。                                        | `null` |
| **`webforj.sessionTimeout`**         | Integer | 会话超时持续时间（以秒为单位）。                              | `60` |
| **`webforj.stringTable`**            | `Map<String,String>` | 用于存储应用程序字符串的键值对映射。方便用于存储应用程序消息或标签。有关 `StringTable` 的更多信息，请参见 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。 | `{}`            |
| **`webforj.mime.extensions`**            | `Map<String,String>` | 提供静态文件时的文件扩展名的自定义 MIME 类型映射。允许您覆盖默认 MIME 类型或为自定义扩展定义 MIME 类型。映射的键是文件扩展名（不包含点），值是 MIME 类型。 | `{}`            |

## 配置 `web.xml` {#configuring-webxml}

`web.xml` 文件是 Java Web 应用程序的一个重要配置文件，在 webforJ 中，它定义了重要设置，如 servlet 配置、URL 模式和欢迎页面。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                                                                            | 默认值                   |
|--------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|
| **`<display-name>`**                  | 设置 Web 应用程序的显示名称，通常源自项目名称。该名称出现在应用服务器的管理控制台中。                                                                                                       | `${project.name}`       |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，这是处理 webforJ 请求的核心 servlet。该 servlet 映射到所有 URL（`/*`），使其成为 Web 请求的主要入口点。                                                        | `WebforjServlet`        |
| **`<load-on-startup>`**               | 指定 `WebforjServlet` 应在应用程序启动时加载。将此设置为 `1` 可使 servlet 立即加载，从而提高初始请求处理能力。                                                                            | `1`                     |
