---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 66df4ab330f26adccbed654c27c6be23
---
# 配置 webforJ 属性

要成功部署和运行 webforJ 应用程序，需要一些关键的配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，包括入口点、调试设置和 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 的核心配置文件，指定应用程序设置，例如入口点、调试模式和客户端与服务器的交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应位于 `resources` 目录中。

:::tip
如果您正在与 [Spring Framework](../integrations/spring/overview.md) 集成，可以在 `application.properties` 文件中设置这些 `webforj.conf` 属性。
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

| 属性                                  | 类型    | 说明                                                       | 默认值                |
|-------------------------------------|---------|----------------------------------------------------------|----------------------|
| **`webforj.assetsCacheControl`**     | 字符串  | 静态资源的 Cache-Control 头部。                           | `null`               |
| **`webforj.assetsDir`**              | 字符串  | 用于提供静态文件的路由名称，而实际文件夹名称保持为 `static`。如果默认 `static` 路由与应用程序中定义的路由冲突，则此配置非常有用，允许您更改路由名称而不重命名文件夹。| `null`               |
| **`webforj.assetsExt`**              | 字符串  | 静态文件的默认文件扩展名。                               | `null`               |
| **`webforj.assetsIndex`**            | 字符串  | 目录请求的默认文件（例如，index.html）。                 | `null`               |
| **`webforj.clientHeartbeatRate`**    | 字符串  | 客户端请求服务器以查看其是否仍然在线的间隔。对于开发，将其设置为较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产中设置为 50 秒或更高，以避免过多请求。 | `50s`                |
| **`webforj.components`**             | 字符串  | 指定时，基路径确定 DWC 组件的加载位置。默认情况下，从托管应用程序的服务器加载组件。然而，设置自定义基路径允许从备用服务器或 CDN 加载组件。例如，要从 jsdelivr.com 加载组件，请将基路径设置为: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 重要的是，加载的组件必须与所使用的 webforJ 框架版本兼容；否则，应用程序可能无法按预期运行。使用没有引擎的标准 BBj 安装时，此设置将被忽略。对于标准 BBj 安装，可以通过 `!COMPONENTS` STBL 管理此设置。 | `null`               |
| **`webforj.debug`**                  | 布尔型 | 启用调试模式。在调试模式下，webforJ 将向控制台打印额外的信息，并在浏览器中显示所有异常。调试模式默认情况下是禁用的。| `null`               |
| **`webforj.entry`**                  | 字符串  | 定义应用程序的入口点，通过指定扩展了 `webforj.App` 的类的完全限定名称。如果未定义入口点，webforJ 将自动扫描类路径以查找扩展了 `webforj.App` 的类。如果找到多个类，将会出现错误。当一个包包含多个潜在的入口点时，显式设置此项是必需的，以防止模棱两可，或者，可以使用 `AppEntry` 注释在运行时指定入口点。| `null`               |
| **`webforj.fileUpload.accept`**      | 列表    | 允许的文件类型进行文件上传。默认情况下，允许所有文件类型。支持的格式包括 MIME 类型，如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名，如 `*.txt`。使用标准 BBj 安装时，此设置将被忽略，并通过 `fileupload-accept.txt` 管理。| `[]`                 |
| **`webforj.fileUpload.maxSize`**     | 长整型  | 允许的最大文件大小（以字节为单位）进行文件上传。默认情况下，没有限制。使用标准 BBj 安装时，此设置将被忽略，并通过 `fileupload-accept.txt` 管理。| `null`               |
| **`webforj.iconsDir`**               | 字符串  | 图标目录的 URL 端点（默认从 `resources/icons/` 提供）。 | `icons/`             |
| **`webforj.license.cfg`**            | 字符串  | 许可证配置的目录。默认情况下，它与 webforJ 配置目录相同，但如果需要可以自定义。| `"."`                |
| **`webforj.license.startupTimeout`** | 整数    | 许可证启动超时（以秒为单位）。                        | `null`               |
| **`webforj.locale`**                 | 字符串  | 应用程序的区域设置，确定语言、地区设置，以及日期、时间和数字的格式。| `null`               |
| **`webforj.quiet`**                  | 布尔型 | 禁用应用程序启动过程中的加载图像。                       | `false`              |
| **`webforj.reloadOnServerError`**    | 布尔型 | **仅限开发环境。** 在开发环境中，在与热重新部署相关的错误上自动重新加载页面，但不适用于其他错误类型。当使用热重部署时，如果客户端在服务器重新启动时向服务器发送请求，则可能会在交换 WAR 文件时发生错误。因为服务器很可能很快恢复在线，所以此设置允许客户端自动尝试页面重新加载。| `false`              |
| **`webforj.servlets[n].name`**       | 字符串  | Servlet 名称（如果未指定，则使用类名）。                | `null`               |
| **`webforj.servlets[n].className`**  | 字符串  | Servlet 的完全限定类名。                             | `null`               |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet 初始化参数。                                   | `null`               |
| **`webforj.sessionTimeout`**         | 整数    | 会话超时时间（以秒为单位）。                           | `60`                 |
| **`webforj.stringTable`**            | `Map<String,String>` | 用于存储字符串的键值对映射，供应用程序使用。用于存储应用程序消息或标签。有关 `StringTable` 的更多信息，请访问 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。| `{}`                 |

## 配置 `web.xml` {#configuring-webxml}

`web.xml` 文件是 Java web 应用程序的重要配置文件，在 webforJ 中定义了重要的设置，如 servlet 配置、URL 模式和欢迎页面。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                                                                        | 默认值                   |
|----------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|
| **`<display-name>`**                   | 设置 web 应用程序的显示名称，通常源自项目名称。此名称出现在应用服务器的管理控制台中。                                                                                                         | `${project.name}`       |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，核心 servlet 用于处理 webforJ 请求。该 servlet 映射到所有 URLs (`/*`)，成为 web 请求的主要入口点。                                                          | `WebforjServlet`        |
| **`<load-on-startup>`**                | 指定在应用程序启动时应加载 `WebforjServlet`。将此设置为 `1` 会立即加载 servlet，从而改善初始请求处理。                                                                                 | `1`                     |
