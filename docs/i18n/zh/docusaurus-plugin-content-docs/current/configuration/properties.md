---
title: Property Configuration
sidebar_position: 30
_i18n_hash: dea9eb679150ca6124fb625c7d04d27c
---
# 配置 webforJ 属性

要成功部署和运行 webforJ 应用程序，需要几个关键配置文件：`webforJ.conf` 和 `web.xml`。这些文件控制应用程序行为的不同方面，从入口点和调试设置到 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforJ.conf` 文件是 webforJ 中的核心配置文件，指定应用设置，例如入口点、调试模式和客户端-服务器交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md) 编写，应该放在 `resources` 目录中。

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

| 属性                           | 说明                                                                                                                                                                                     | 默认值         |
|--------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| **`webforj.entry`**            | 通过指定扩展 `webforj.App` 的类的完全限定名称来定义应用程序的入口点。如果未定义入口点，webforJ 将自动扫描类路径，查找扩展 `webforj.App` 的类。如果找到多个类，将会发生错误。当某个包包含多个潜在的入口点时，必须明确设置此项以防止歧义，或者可以使用 `AppEntry` 注解在运行时指定入口点。 | `null`          |
| **`webforj.debug`**            | 启用调试模式。在调试模式下，webforJ 将向控制台打印额外信息，并在浏览器中显示所有异常。调试模式默认是禁用的。 | `null`          |
| **`webforj.reloadOnServerError`** | 使用热重载时，整个 WAR 文件会被交换。如果客户端在服务器重启时尝试向服务器发送请求，将发生错误。此设置允许客户端在服务器暂时不可用时尝试重新加载页面，希望它会很快重新连接。此设置仅适用于开发环境，仅处理与热重载特定相关的错误，而不是其他类型的错误。 | `on`            |
| **`webforj.clientHeartbeatRate`** | 设置客户端向服务器发送心跳以检查其是否仍然存活的间隔。这有助于维持通信。对于开发，建议设置较短的间隔，例如 `8s`，以快速检测服务器可用性。在生产中不要设置低于 50 秒，以避免过多请求。 | `50s`           |
| **`webforj.components`**       | 当指定时，基路径确定 DWC 组件的加载位置。默认情况下，组件从托管应用程序的服务器加载。然而，设置自定义基路径允许从其他服务器或 CDN 加载组件。例如，要从 jsdelivr.com 加载组件，将基路径设置为： https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 加载的组件必须与使用的 webforJ 框架的版本兼容；否则，应用程序可能无法按预期工作。在没有引擎的标准 BBj 安装中，此设置将会被忽略。对于标准 BBj 安装，可以使用 `!COMPONENTS` STBL 管理此设置。 | `null`          |
| **`webforj.locale`**           | 定义应用程序的区域设置，确定语言、区域设置以及日期、时间和数字格式。 | `null`          |
| **`webforj.assetsDir`**       | 指定用于提供静态文件的路由名称，而物理文件夹名称保持为 `static`。此配置在默认 `static` 路由与应用程序中定义的路由冲突时很有用，允许您更改路由名称而无需重命名文件夹本身。 | `static`        |
| **`webforj.stringTable`**      | 用于存储应用程序中的字符串的键值对映射。适用于存储应用程序消息或标签。有关 `StringTable` 的更多信息，请查看 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。 | `{}`            |
| **`webforj.fileUpload.accept`** | 指定文件上传的允许文件类型。默认情况下，允许所有文件类型。支持格式包括像 `image/*`，`application/pdf`，`text/plain` 等 MIME 类型，或者像 `*.txt` 这样的文件扩展名。当使用标准 BBj 安装时，此设置将被忽略，并通过 `fileupload-accept.txt` 管理。 | `[]`            |
| **`webforj.fileUpload.maxSize`** | 定义允许的文件上传最大大小，以字节为单位。默认情况下，没有限制。当使用标准 BBj 安装时，此设置将被忽略，并通过 `fileupload-accept.txt` 管理。 | `null`          |
| **`license.cfg`**              | 配置许可证文件夹的目录。默认情况下，它与 webforJ 配置目录相同，但可以根据需要进行自定义。 | `"."`           |

## 配置 `web.xml` {#configuring-webxml}

web.xml 文件是 Java 网络应用程序的基本配置文件，在 webforJ 中，定义了重要设置，如 servlet 配置、URL 模式、欢迎页面。该文件应位于项目部署结构的 `WEB-INF` 目录中。

| 设置                                   | 说明                                                                                                                                                                                         | 默认值                       |
|---------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------|
| **`<display-name>`**                  | 设置 web 应用程序的显示名称，通常源自项目名称。此名称出现在应用服务器的管理控制台中。                                                                                                        | `${project.name}`             |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，处理 webforJ 请求的核心 servlet。该 servlet 映射到所有 URL (`/*`)，成为 web 请求的主要入口点。                                                                 | `WebforjServlet`              |
| **`<load-on-startup>`**               | 指定 `WebforjServlet` 应在应用程序启动时加载。将其设置为 `1` 确保 servlet 立即加载，从而改善初始请求处理。                                                                      | `1`                           |
