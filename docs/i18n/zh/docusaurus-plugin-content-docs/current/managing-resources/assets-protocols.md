---
sidebar_position: 2
title: Assets Protocols
description: >-
  Reference app resources with the webforJ ws, context, and icons protocols to
  load static files, classpath content, and dynamic icons.
_i18n_hash: 3928ba255cb9887c80c20f904baf62b8
---
webforJ 支持自定义资产协议，使资源访问结构化和高效。这些协议抽象了静态和动态资源检索的复杂性，确保资产在应用程序中无缝获取和集成。

## 网络服务器协议 {#the-webserver-protocol}

**`ws://`** 协议允许您检索托管在 webforJ 应用程序静态文件夹中的资产。所有位于应用程序类路径 `src/main/resources/static` 中的文件可以直接从浏览器访问。例如，如果您在 **resources/static** 中有一个名为 **css/app.css** 的文件，它可以通过 **`/static/css/app.css`** 访问。

**ws://** 协议消除了在资源 URL 中硬编码 `static` 前缀的需要，保护您免受根据部署上下文更改前缀的影响。如果 Web 应用程序在根上下文以外的上下文中部署，例如 **/mycontext**，那么 **css/app.css** 的 URL 将是： **`/mycontext/static/css/app.css`**

:::tip 配置静态前缀
您可以使用 [webforj 配置](../configuration/properties#configuration-options) 选项 `webforj.assetsDir` 控制 `static` 前缀。该设置指定用于提供静态文件的路由名称，而 **物理文件夹仍然命名为 `static`**。如果默认静态路由与您的应用程序中的路由发生冲突，这尤其有用，因为它允许您在不重命名文件夹的情况下更改路由名称。
:::

您可以使用 <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> 实用程序类解析给定的 Web 服务器 URL。如果您有一个需要支持该协议的自定义组件，这将非常有用。

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## 上下文协议 {#the-context-protocol}

上下文协议映射到应用程序类路径 `src/main/resources` 中的资源。一些资源 API 方法和注释支持此协议以读取位于资源文件夹中的文件内容并将其内容发送到浏览器。例如，`InlineJavaScript` 注释允许从资源文件夹中读取 JavaScript 文件的内容并在客户端内联。

例如：

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## 图标协议 {#the-icons-protocol}

**`icons://`** 协议提供了一种动态的图标管理方法，使生成和提供图标在 [可安装应用程序](../configuration/installable-apps) 中高效而灵活。该协议允许您根据请求的文件名和参数动态生成图标，消除了许多情况下对预生成图标的需求。

```java
Img icon = new Img("icons://icon-192x192.png")
```

### 基础图标 {#base-icon}

当使用 `icons://` 协议请求图标时，基础图像将动态从请求的图标文件名衍生。这确保了图标设计的一致性，并允许在未提供基础图标时自动回退到默认图像。

- **示例 1:** 请求: `/icons/icon-192x192.png` → 基础图标: `resources/icons/icon.png`
- **示例 2:** 请求: `/icons/icon-different-192x192.png` → 基础图标: `resources/icons/icon-different.png`

如果请求的图标没有基础图像，则使用默认的 webforJ 徽标作为后备。

:::tip `webforj.iconsDir`
默认情况下，webforJ 从 `resources/icons/` 目录提供图标。您可以通过在 webforJ 配置文件中设置 `webforj.iconsDir` 属性来更改端点名称。这仅更改用于访问图标的 URL 端点，而不是实际的文件夹名称。默认端点为 `icons/`。
:::

### 覆盖图标 {#overriding-icons}

您可以通过将预生成的图像放入 `resources/icons/` 目录来覆盖特定的图标大小。这在某些大小或样式需要自定义时，可以提供对图标外观的更大控制。

- **示例:** 如果 `resources/icons/icon-192x192.png` 存在，它将直接提供，而不是动态生成。

### 自定义图标外观 {#customizing-icon-appearance}

`icons://` 协议支持额外的参数，允许您自定义动态生成图标的外观。这包括填充和背景颜色的选项。

- **填充**: `padding` 参数可用于控制图标周围的填充。接受的值范围从 `0`（表示没有填充）到 `1`（表示最大填充）。
  - **示例:** `/icons/icon-192x192.png?padding=0.3`

- **背景颜色**: `background` 参数允许您设置图标的背景颜色。支持以下值：
  - **`transparent`**: 无背景颜色。
  - **十六进制颜色代码**: 自定义背景颜色（例如，`FFFFFF` 为白色）。
  - **`auto`**: 尝试自动检测图标的背景颜色。

例如：

- **示例 1:** `/icons/icon-192x192.png?background=000000`
- **示例 2:** `/icons/icon-192x192.png?background=auto`
