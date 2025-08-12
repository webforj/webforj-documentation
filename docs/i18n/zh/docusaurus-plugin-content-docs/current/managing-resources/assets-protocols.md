---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: a7482285684e797c3cfc30d025a95482
---
webforJ 支持自定义资产协议，以实现结构化和高效的资源访问。这些协议抽象了静态和动态资源检索的复杂性，确保资产在应用中无缝获取和集成。

## Webserver 协议 {#the-webserver-protocol}

**`ws://`** 协议允许您检索托管在 webforJ 应用的静态文件夹中的资产。位于应用类路径 `src/main/resources/static` 的所有文件可以直接从浏览器访问。例如，如果您在 **resources/static** 中有一个名为 **css/app.css** 的文件，则可以通过 **`/static/css/app.css`** 访问它。

**ws://** 协议消除了在资源 URL 中硬编码 `static` 前缀的需要，避免了由于部署上下文不同而导致前缀变化的风险。如果 web 应用程序在根目录以外的上下文中部署，例如 **/mycontext**，则 **css/app.css** 的 URL 将为: **`/mycontext/static/css/app.css`**。

:::tip 配置静态前缀
您可以使用 [webforj 配置](../configuration/properties#configuration-options) 选项 `webforj.assetsDir` 来控制 `static` 前缀。此设置指定用于服务静态文件的路由名称，同时 **物理文件夹仍称为 `static`**。如果默认静态路由与您应用程序中的路由冲突，这一点特别有用，因为它允许您更改路由名称而无需重命名文件夹。
:::

您可以使用 <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> 工具类来解析给定的 web 服务器 URL。如果您有一个需要支持该协议的自定义组件，这非常有用。

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## 上下文协议 {#the-context-protocol}

上下文协议映射到应用程序类路径 `src/main/resources` 中的资源。一些资源 API 方法和注释支持此协议，以读取位于资源文件夹中的文件内容并将其内容发送到浏览器。例如，`InlineJavaScript` 注释允许从资源文件夹读取 JavaScript 文件的内容并将其内联到客户端。

例如：

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## 图标协议 {#the-icons-protocol}

**`icons://`** 协议提供了一种动态图标管理的方法，使生成和提供 [可安装应用](../configuration/installable-apps) 中的图标变得高效且灵活。该协议允许您根据请求的文件名和参数动态生成图标，从而消除在许多情况下需要预生成图标的需求。

```java
Img icon = new Img("icons://icon-192x192.png")
```

### 基础图标 {#base-icon}

当使用 `icons://` 协议请求图标时，会根据请求的图标文件名动态生成基础图像。这确保了图标设计的一致性，并在没有提供基础图标时自动回退到默认图像。

- **示例 1:** 请求：`/icons/icon-192x192.png` → 基础图标：`resources/icons/icon.png`
- **示例 2:** 请求：`/icons/icon-different-192x192.png` → 基础图标：`resources/icons/icon-different.png`

如果请求的图标没有基础图像，则使用默认的 webforJ 标志作为回退。

:::tip `webforj.iconsDir`
默认情况下，webforJ 从 `resources/icons/` 目录提供图标。您可以通过在 webforJ 配置文件中设置 `webforj.iconsDir` 属性来更改端点名称。这仅更改用于访问图标的 URL 端点，而不改变实际的文件夹名称。默认端点为 `icons/`。
:::

### 覆盖图标 {#overriding-icons}

您可以通过将预生成的图像放置在 `resources/icons/` 目录中来覆盖特定的图标尺寸。这在需要定制某些尺寸或样式的图标外观时提供了更大的控制。

- **示例:** 如果存在 `resources/icons/icon-192x192.png`，它将直接提供，而不是动态生成。

### 自定义图标外观 {#customizing-icon-appearance}

`icons://` 协议支持附加参数，允许您自定义动态生成图标的外观。这包括用于填充和背景颜色的选项。

- **填充**：`padding` 参数可用于控制图标周围的填充。接受的值范围从 `0`（表示没有填充）到 `1`（表示最大填充）。
  - **示例:** `/icons/icon-192x192.png?padding=0.3`
  
- **背景颜色**：`background` 参数允许您设置图标的背景颜色。它支持以下值：
  - **`transparent`**：无背景颜色。
  <!-- vale off -->
  - **十六进制颜色代码**：自定义背景颜色（例如，`FFFFFF` 表示白色）。
  <!-- vale on -->
  - **`auto`**：尝试自动检测图标的背景颜色。

  例如：
  
  - **示例 1:** `/icons/icon-192x192.png?background=000000`
  - **示例 2:** `/icons/icon-192x192.png?background=auto`
