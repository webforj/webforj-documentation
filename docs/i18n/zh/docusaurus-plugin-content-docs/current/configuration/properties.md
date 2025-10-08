---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e7a922cb3f035dd19fdc282d245bdf2c
---
# 配置 webforJ 属性

要成功部署和运行 webforJ 应用程序，需要几个关键的配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，从入口点和调试设置到 Servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 的核心配置文件，指定应用设置，例如入口点、调试模式和客户端与服务器的交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应该位于 `resources` 目录中。

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

| 属性                                   | 类型     | 说明                                                             | 默认值                |
|----------------------------------------|----------|------------------------------------------------------------------|----------------------|
| **`webforj.assetsCacheControl`**       | 字符串   | 静态资源的 Cache-Control 标头。                                   | `null`               |
| **`webforj.assetsDir`**                | 字符串   | 用于服务静态文件的路由名称，而实际文件夹名称保持为 `static`。此配置在默认 `static` 路由与应用中定义的路由冲突时非常有用，允许您更改路由名称而无需重命名文件夹。 | `null`               |
| **`webforj.assetsExt`**                | 字符串   | 静态文件的默认文件扩展名。                                      | `null`               |
| **`webforj.assetsIndex`**              | 字符串   | 默认为目录请求提供的文件（例如，index.html）。                  | `null`               |
| **`webforj.clientHeartbeatRate`**      | 字符串   | 客户端向服务器发送心跳信号的间隔，检查其是否仍然在线。在开发中，将其设置为较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产中，将其设置为 50 秒或更高，以避免过多请求。 | `50s`                |
| **`webforj.components`**               | 字符串   | 当指定时，基础路径决定 DWC 组件的加载位置。默认情况下，组件从托管应用程序的服务器加载。但是，设置自定义基础路径允许从替代服务器或 CDN 加载组件。例如，要从 jsdelivr.com 加载组件，可以将基础路径设置为：https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 重要的是，加载的组件必须与所使用的 webforJ 框架版本兼容；否则，应用程序可能无法正常工作。使用不带引擎的标准 BBj 安装时，此设置将被忽略。对于标准 BBj 安装，此设置可以通过 `!COMPONENTS` STBL 管理。 | `null`               |
| **`webforj.debug`**                    | 布尔型  | 启用调试模式。在调试模式下，webforJ 将在控制台打印额外信息，并在浏览器中显示所有异常。默认情况下，调试模式是禁用的。 | `null`               |
| **`webforj.entry`**                    | 字符串   | 通过指定扩展 `webforj.App` 的类的完全限定名来定义应用程序的入口点。如果未定义入口点，webforJ 将自动扫描类路径以查找扩展 `webforj.App` 的类。如果找到多个类，将会发生错误。当一个包中包含多个潜在入口点时，显式设置此项是必要的，以防止模棱两可的情况，或者可以使用 `AppEntry` 注解在运行时指定入口点。 | `null`               |
| **`webforj.fileUpload.accept`**        | 列表     | 允许上传的文件类型。默认情况下，允许所有文件类型。支持的格式包括 MIME 类型如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名如 `*.txt`。在使用标准 BBj 安装时，此设置将被忽略并通过 `fileupload-accept.txt` 管理。 | `[]`                 |
| **`webforj.fileUpload.maxSize`**       | 长整型   | 允许的最大文件大小（以字节为单位）。默认情况下，没有限制。在使用标准 BBj 安装时，此设置将被忽略并通过 `fileupload-accept.txt` 管理。 | `null`               |
| **`webforj.iconsDir`**                 | 字符串   | 图标目录的 URL 端点（默认从 `resources/icons/` 服务）。       | `icons/`             |
| **`webforj.license.cfg`**              | 字符串   | 许可证配置的目录。默认情况下，与 webforJ 配置目录相同，但如果需要，可以自定义。 | `"."`                |
| **`webforj.license.startupTimeout`**   | 整数     | 许可证启动超时时间（以秒为单位）。                              | `null`               |
| **`webforj.locale`**                   | 字符串   | 应用程序的区域设置，确定语言、地区设置以及日期、时间和数字的格式。 | `null`               |
| **`webforj.quiet`**                    | 布尔型  | 启动应用程序时禁用加载图像。                                    | `false`              |
| **`webforj.reloadOnServerError`**      | 布尔型  | **仅适用于开发环境。** 在开发环境中，自动在与热重新部署相关的错误上重新加载页面，但不适用于其他错误类型。当使用热重新部署时，如果客户端在服务器重新启动时向其发送请求，则在交换 WAR 文件时可能会发生错误。由于服务器可能很快恢复在线，因此此设置允许客户端自动尝试页面重载。 | `false`              |
| **`webforj.servlets[n].name`**         | 字符串   | Servlet 名称（如果未指定，将使用类名）。                       | `null`               |
| **`webforj.servlets[n].className`**    | 字符串   | Servlet 的完全限定类名。                                       | `null`               |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet 初始化参数。                                           | `null`               |
| **`webforj.sessionTimeout`**           | 整数     | 会话超时时间（以秒为单位）。                                   | `60`                 |
| **`webforj.stringTable`**              | `Map<String,String>` | 用于存储应用程序字符串的键值对映射。适合存储应用消息或标签。更多关于 `StringTable` 的信息可在 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html) 找到。 | `{}`                 |

## 配置 `web.xml` {#configuring-webxml}

`web.xml` 文件是 Java Web 应用程序的重要配置文件，在 webforJ 中，它定义了 Servlet 配置、URL 模式和欢迎页面等重要设置。此文件应该位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                                                                                 | 默认值                   |
|----------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|
| **`<display-name>`**                   | 设置 Web 应用程序的显示名称，通常来源于项目名称。此名称出现在应用服务器的管理控制台中。                                                                                                                                           | `${project.name}`       |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，该核心 Servlet 用于处理 webforJ 请求。此 Servlet 映射到所有 URL (`/*`)，使其成为 Web 请求的主要入口点。                                                                                                          | `WebforjServlet`        |
| **`<load-on-startup>`**                 | 指定 `WebforjServlet` 应在应用程序启动时加载。将其设置为 `1` 可以让 Servlet 立即加载，从而改善初始请求处理。                                                                                                                   | `1`                     |
