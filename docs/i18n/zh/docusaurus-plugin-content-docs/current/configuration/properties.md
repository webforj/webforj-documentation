---
title: Property Configuration
sidebar_position: 30
_i18n_hash: 3e14c2d47a7963fe901feda071971419
---
# 配置 webforJ 属性

要成功部署和运行一个 webforJ 应用程序，需要几个关键的配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，从入口点和调试设置到 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 的核心配置文件，指定应用程序设置，如入口点、调试模式和客户端-服务器交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应位于 `resources` 目录中。

:::tip
如果您正在与 [Spring 框架](../integrations/spring/overview.md) 集成，可以在 `application.properties` 文件中设置这些 `webforj.conf` 属性。
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

| 属性                                  | 类型     | 说明                                                         | 默认值                  |
|---------------------------------------|----------|------------------------------------------------------------|------------------------|
| **`webforj.assetsCacheControl`**      | 字符串   | 静态资源的 Cache-Control 头部。                           | `null`                 |
| **`webforj.assetsDir`**               | 字符串   | 用于服务静态文件的路由名称，而实际文件夹名称仍为 `static`。此配置在默认 `static` 路由与应用中定义的路由冲突时，非常有用，允许您更改路由名称，而无需重命名文件夹本身。 | `null`                 |
| **`webforj.assetsExt`**               | 字符串   | 静态文件的默认文件扩展名。                                | `null`                 |
| **`webforj.assetsIndex`**             | 字符串   | 目录请求（例如，index.html）服务的默认文件。               | `null`                 |
| **`webforj.clientHeartbeatRate`**     | 字符串   | 客户端向服务器 ping 的间隔，以查看其是否仍然存活。对于开发，设置较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产环境中设置为 50 秒或更高，以避免过多的请求。 | `50s`                  |
| **`webforj.components`**              | 字符串   | 当指定时，基路径确定 DWC 组件的加载位置。默认情况下，组件从托管应用的服务器加载。但是，设置自定义基路径允许从备用服务器或 CDN 加载组件。例如，若要从 jsdelivr.com 加载组件，请将基路径设置为：https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 加载的组件必须与所使用的 webforJ 框架版本兼容；否则，应用程序可能无法按预期功能运行。在没有引擎的标准 BBj 安装中，此设置被忽略。对于标准 BBj 安装，可以通过 `!COMPONENTS` STBL 管理该设置。 | `null`                 |
| **`webforj.debug`**                   | 布尔值   | 启用调试模式。在调试模式下，webforJ 将向控制台打印更多信息，并在浏览器中显示所有异常。默认情况下，禁用调试模式。 | `null`                 |
| **`webforj.entry`**                   | 字符串   | 通过指定完全限定的类名来定义应用程序的入口点，该类扩展 `webforj.App`。如果未定义入口点，webforJ 将自动扫描类路径以查找扩展 `webforj.App` 的类。如果找到多个类，将发生错误。当一个包中包含多个潜在入口点时，必须明确设置此项以防止歧义，或者可以使用 `AppEntry` 注释在运行时指定入口点。 | `null`                 |
| **`webforj.fileUpload.accept`**       | 列表     | 允许的文件类型，用于文件上传。默认情况下，允许所有文件类型。支持的格式包括 MIME 类型，如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名，如 `*.txt`。在使用标准 BBj 安装时，此设置被忽略，并通过 `fileupload-accept.txt` 进行管理。 | `[]`                   |
| **`webforj.fileUpload.maxSize`**      | 长整型   | 允许的最大上传文件大小，以字节为单位。默认情况下，没有限制。在使用标准 BBj 安装时，此设置被忽略，并通过 `fileupload-accept.txt` 进行管理。 | `null`                 |
| **`webforj.iconsDir`**                | 字符串   | 图标目录的 URL 端点（默认从 `resources/icons/` 提供）。 | `icons/`               |
| **`webforj.license.cfg`**             | 字符串   | 许可证配置的目录。默认情况下，它与 webforJ 配置目录相同，但可以根据需要进行自定义。 | `"."`                  |
| **`webforj.license.startupTimeout`**  | 整数     | 许可证启动超时时间（以秒为单位）。                           | `null`                 |
| **`webforj.locale`**                  | 字符串   | 应用程序的语言环境，决定语言、区域设置以及日期、时间和数字的格式。 | `null`                 |
| **`webforj.quiet`**                   | 布尔值   | 禁用应用程序启动期间的加载图像。                            | `false`                |
| **`webforj.reloadOnServerError`**     | 布尔值   | **仅限开发环境。** 在开发环境中，自动重载页面，处理与热重新部署相关的错误，而不处理其他错误类型。在使用热重新部署时，如果客户端在服务器重启期间向服务器发送请求，可能会出现错误，因为 WAR 文件正在被交换。由于服务器可能很快会恢复在线，此设置允许客户端尝试自动重新加载页面。 | `false`                |
| **`webforj.servlets[n].name`**        | 字符串   | servlet 名称（如果没有指定，则使用类名）。                      | `null`                 |
| **`webforj.servlets[n].className`**   | 字符串   | servlet 的完全限定类名。                                     | `null`                 |
| **`webforj.servlets[n].config.<key>`**| `Map<String,String>` | servlet 初始化参数。                                        | `null`                 |
| **`webforj.sessionTimeout`**          | 整数     | 会话超时时间（以秒为单位）。                                 | `60`                   |
| **`webforj.stringTable`**             | `Map<String,String>` | 用于存储应用程序中字符串的键值对映射。适用于存储应用程序消息或标签。有关 `StringTable` 的更多信息，请参阅 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。 | `{}`                   |

## 配置 `web.xml` {#configuring-webxml}

web.xml 文件是 Java Web 应用程序的重要配置文件，在 webforJ 中，它定义了重要的设置，如 servlet 配置、URL 模式、欢迎页面。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                      | 默认值                       |
|---------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|------------------------------|
| **`<display-name>`**                  | 设置 web 应用程序的显示名称，通常来源于项目名称。此名称出现在应用服务器的管理控制台中。                                                 | `${project.name}`            |
| **`<servlet>` 和 `<servlet-mapping>`**| 定义 `WebforjServlet`，这是处理 webforJ 请求的核心 servlet。该 servlet 映射到所有 URL (`/*`)，使其成为 web 请求的主要入口点。     | `WebforjServlet`             |
| **`<load-on-startup>`**               | 指定 `WebforjServlet` 应在应用程序启动时加载。将此设置为 `1` 使 servlet 立即加载，从而改善初始请求处理。                                   | `1`                          |
