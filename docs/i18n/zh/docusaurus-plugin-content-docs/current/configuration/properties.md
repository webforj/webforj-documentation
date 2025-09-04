---
title: Property Configuration
sidebar_position: 30
_i18n_hash: e2cc183e859c85e0d1f4a24c196b8a55
---
# 配置 webforJ 属性

要成功部署和运行 webforJ 应用程序，需要几个关键配置文件：`webforJ.conf` 和 `web.xml`。这些文件控制应用程序的不同方面，从入口点和调试设置到 servlet 映射。

## 配置 `webforj.conf` {#configuring-webforjconf}

`webforJ.conf` 文件是 webforJ 中的核心配置文件，指定应用程序设置，如入口点、调试模式和客户端-服务器交互。该文件采用 [HOCON 格式](https://github.com/lightbend/config/blob/master/HOCON.md)，应位于 `resources` 目录中。

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

| 属性                          | 解释                                                                                                                                                                            | 默认值         |
|-------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------|
| **`webforj.entry`**           | 通过指定扩展 `webforj.App` 的类的完全限定名称来定义应用程序的入口点。如果未定义入口点，webforJ 将自动扫描类路径以查找扩展 `webforj.App` 的类。如果找到多个类，将会发生错误。当一个包中包含多个潜在入口点时，明确设置此项是必要的，以防止歧义，或者可以使用 `AppEntry` 注解在运行时指定入口点。 | `null`          |
| **`webforj.debug`**           | 启用调试模式。在调试模式下，webforJ 将向控制台打印额外的信息，并在浏览器中显示所有异常。调试模式默认是禁用的。 | `null`          |
| **`webforj.reloadOnServerError`** | 使用热重部署时，整个 WAR 文件将被替换。如果客户端尝试在服务器重新启动时向服务器发送请求，将会发生错误。此设置允许客户端在服务器暂时不可用时尝试页面重载，希望它将很快恢复在线。这仅适用于开发环境，并且只处理特定于热重部署的错误，而不处理其他类型的错误。 | `on`            |
| **`webforj.clientHeartbeatRate`** | 设置客户端 ping 服务器的间隔，以查看服务器是否仍然在线。这有助于维护通信。在开发中，将这设置为更短的间隔，例如 `8s`，以快速检测服务器可用性。不要在生产环境中将此设置低于 50 秒，以避免过多请求。 | `50s`           |
| **`webforj.components`**      | 指定时，基本路径决定 DWC 组件的加载位置。默认情况下，组件从托管该应用程序的服务器加载。然而，设置一个自定义基本路径允许从备用服务器或 CDN 加载组件。例如，要从 jsdelivr.com 加载组件，请将基本路径设置为: https://cdn.jsdelivr.net/gh/webforj/dwc-dist@1.0.0-${webforj.version} 加载的组件必须与使用的 webforJ 框架版本兼容；否则，应用程序可能无法按预期运行。此设置在没有引擎的标准 BBj 安装中被忽略。对于标准 BBj 安装，可以使用 `!COMPONENTS` STBL 管理此设置。 | `null`          |
| **`webforj.locale`**          | 定义应用程序的区域设置，确定语言、区域设置以及日期、时间和数字的格式。 | `null`          |
| **`webforj.assetsDir`**      | 指定用于服务静态文件的路由名称，而物理文件夹名称仍为 `static`。此配置在默认 `static` 路由与您的应用程序中定义的路由发生冲突时非常有用，允许您更改路由名称而无需重命名文件夹本身。 | `static`        |
| **`webforj.stringTable`**     | 使用的键值对映射，用于存储在应用程序中使用的字符串。适用于存储应用程序消息或标签。有关 `StringTable` 的更多信息，请参见 [这里](https://javadoc.io/doc/com.webforj/webforj-foundation/latest/com/webforj/environment/StringTable.html)。 | `{}`            |
| **`webforj.fileUpload.accept`** | 指定允许上传的文件类型。默认情况下，允许所有文件类型。支持的格式包括 MIME 类型，如 `image/*`、`application/pdf`、`text/plain`，或文件扩展名，如 `*.txt`。在使用标准 BBj 安装时，此设置被忽略，并通过 `fileupload-accept.txt` 管理。 | `[]`            |
| **`webforj.fileUpload.maxSize`** | 定义允许的最大文件上传大小，以字节为单位。默认情况下，没有限制。在使用标准 BBj 安装时，此设置被忽略，并通过 `fileupload-accept.txt` 管理。 | `null`          |
| **`license.cfg`**             | 配置许可证配置的目录。默认情况下，它与 webforJ 配置目录相同，但如果需要，可以自定义。 | `"."`           |

## 配置 `web.xml` {#configuring-webxml}

web.xml 文件是 Java web 应用程序的一个基本配置文件，在 webforJ 中，它定义重要的设置，如 servlet 配置、URL 模式和欢迎页面。此文件应位于您项目的部署结构中的 `WEB-INF` 目录中。

| 设置                                  | 解释                                                                                                                                                                                   | 默认值                     |
|---------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------|
| **`<display-name>`**                  | 设置 web 应用程序的显示名称，通常来自项目名称。此名称会出现在应用服务器的管理控制台中。                                                                                                        | `${project.name}`           |
| **`<servlet>` 和 `<servlet-mapping>`** | 定义 `WebforjServlet`，处理 webforJ 请求的核心 servlet。该 servlet 被映射到所有 URL (`/*`)，使其成为 web 请求的主要入口点。                                                 | `WebforjServlet`            |
| **`<load-on-startup>`**               | 指定 `WebforjServlet` 应在应用程序启动时加载。将此设置为 `1` 确保 servlet 立即加载，从而改善初始请求处理。                                                                  | `1`                         |
