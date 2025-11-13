---
title: 属性配置
sidebar_position: 30
sidebar_class_name: updated-content
_i18n_hash: fe000276baa9ac8b0773e5c4372d8463
---
# 配置 webforJ 属性

要成功部署和运行 webforJ 应用，需要几个关键的配置文件：`webforj.conf` 和 `web.xml`。这些文件控制应用行为的不同方面，从入口点和调试设置到 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforj.conf` 文件是 webforJ 的核心配置文件，指定应用设置，如入口点、调试模式和客户端-服务器交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应位于 `resources` 目录中。

:::tip
如果您正在与 [Spring](../integrations/spring/overview.md) 集成，您可以在 `application.properties` 文件中设置这些 `webforj.conf` 属性。
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

| 属性                               | 类型    | 说明                                                           | 默认值               |
|------------------------------------|---------|----------------------------------------------------------------|---------------------|
| **`webforj.assetsCacheControl`**   | 字符串 | 静态资源的 Cache-Control 头。                                 | `null`              |
| **`webforj.assetsDir`**            | 字符串 | 用于服务静态文件的路由名称，而实际文件夹名称保持为 `static`。此配置有助于当默认的 `static` 路由与您应用中定义的路由冲突时，允许您更改路由名称而无需重命名文件夹本身。 | `null`              |
| **`webforj.assetsExt`**            | 字符串 | 静态文件的默认文件扩展名。                                   | `null`              |
| **`webforj.assetsIndex`**          | 字符串 | 目录请求（例如，index.html）所服务的默认文件。               | `null`              |
| **`webforj.clientHeartbeatRate`**  | 字符串 | 客户端向服务器查询是否仍然存活的间隔。对于开发，建议将此设置为较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产中设置为 50 秒或更长以避免过多请求。 | `50s`               |
| **`webforj.components`**           | 字符串 | 当指定时，基本路径决定 DWC 组件的加载位置。默认情况下，组件从托管应用的服务器加载。然而，设置自定义基本路径允许从其他服务器或 CDN 加载组件。例如，要从 jsdelivr.com 加载组件，将基本路径设置为： https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version}。确保加载的组件与使用的 webforJ 框架版本兼容；否则，应用可能无法按预期工作。当使用不带引擎的标准 BBj 安装时，此设置将被忽略。对于标准 BBj 安装，此设置可通过 `!COMPONENTS` STBL 管理。 | `null`              |
| **`webforj.debug`**                | 布尔   | 启用调试模式。在调试模式下，webforJ 将向控制台打印附加信息并在浏览器中显示所有异常。默认情况下，调试模式处于禁用状态。  | `null`              |
| **`webforj.entry`**                | 字符串 | 通过指定扩展 `webforj.App` 的类的完全限定名称来定义应用的入口点。如果未定义入口点，webforJ 将自动扫描类路径以查找扩展 `webforj.App` 的类。如果找到多个类，将会发生错误。当一个包中包含多个可能的入口点时，显式设置此项为必要，以防止模糊，或者可以使用 `AppEntry` 注释在运行时指定入口点。 | `null`              |
| **`webforj.fileUpload.accept`**    | 列表   | 允许的文件类型用于文件上传。默认情况下，允许所有文件类型。支持的格式包括 MIME 类型如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名如 `*.txt`。在使用标准 BBj 安装时，此设置将被忽略，并通过 `fileupload-accept.txt` 管理。 | `[]`                |
| **`webforj.fileUpload.maxSize`**   | 长整型 | 允许的文件上传最大大小，以字节为单位。默认情况下，没有限制。使用标准 BBj 安装时，此设置将被忽略，并通过 `fileupload-accept.txt` 管理。 | `null`              |
| **`webforj.iconsDir`**             | 字符串 | 图标目录的 URL 端点（默认从 `resources/icons/` 提供）。                 | `icons/`            |
| **`webforj.license.cfg`**          | 字符串 | 许可证配置目录。默认情况下，它与 webforJ 配置目录相同，但可以根据需要进行自定义。 | `"."`               |
| **`webforj.license.startupTimeout`** | 整数   | 许可证启动超时（以秒为单位）。                                      | `null`              |
| **`webforj.locale`**               | 字符串 | 应用的语言环境，决定语言、区域设置以及日期、时间和数字的格式。           | `null`              |
| **`webforj.quiet`**                | 布尔   | 在应用启动期间禁用加载图像。                                      | `false`             |
| **`webforj.reloadOnServerError`**  | 布尔   | **仅限开发环境。** 在开发环境中，出错时自动重新加载页面，但不处理其他类型的错误。当使用热重部署时，如果客户端在服务器重新启动时向服务器发送请求，可能会在 WAR 文件交换时发生错误。由于服务器可能很快会重新上线，此设置允许客户端自动尝试重新加载页面。 | `false`             |
| **`webforj.servlets[n].name`**     | 字符串 | Servlet 名称（如果未指定则使用类名）。                            | `null`              |
| **`webforj.servlets[n].className`** | 字符串 | Servlet 的完全限定类名。                                          | `null`              |
| **`webforj.servlets[n].config.<key>`** | `Map<String,String>` | Servlet 初始化参数。                                         | `null`              |
| **`webforj.sessionTimeout`**       | 整数   | 会话超时持续时间（以秒为单位）。                                   | `60`                |
| **`webforj.stringTable`**          | `Map<String,String>` | 一组键值对，用于在应用中存储字符串。用于存储应用消息或标签。有关 `StringTable` 的更多信息，请参见 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。 | `{}`                |
| **`webforj.mime.extensions`**      | `Map<String,String>` | 提供静态文件时文件扩展名的自定义 MIME 类型映射。允许您覆盖默认 MIME 类型或为自定义扩展定义 MIME 类型。映射的键是文件扩展名（不带点），值是 MIME 类型。 | `{}`                |

## 配置 `web.xml` {#configuring-webxml}

`web.xml` 文件是 Java Web 应用的重要配置文件，在 webforJ 中定义了重要设置，如 servlet 配置、URL 模式和欢迎页面。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                         | 默认值                     |
|----------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
| **`<display-name>`**                   | 设置 Web 应用的显示名称，通常来源于项目名称。此名称出现在应用服务器的管理控制台中。                                                   | `${project.name}`          |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，处理 webforJ 请求的核心 servlet。该 servlet 映射到所有 URL (`/*`)，使其成为 Web 请求的主要入口点。        | `WebforjServlet`          |
| **`<load-on-startup>`**                | 指定 `WebforjServlet` 应在应用启动时加载。设置此值为 `1` 可使 servlet 立即加载，从而改善初始请求处理。                             | `1`                       |
