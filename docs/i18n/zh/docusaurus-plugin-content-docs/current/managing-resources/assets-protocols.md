---
sidebar_position: 2
title: Assets Protocols
_i18n_hash: b37158687b15dc2b94a7543624eaa09f
---
webforJ 支持自定义资产协议，能够实现结构化和高效的资源访问。这些协议抽象了静态和动态资源检索的复杂性，确保资产能够在应用内无缝获取和集成。

## webserver 协议 {#the-webserver-protocol}

**`ws://`** 协议允许您检索托管在 webforJ 应用静态文件夹中的资产。所有位于应用类路径 `src/main/resources/static` 内的文件都可以直接通过浏览器访问。例如，如果您在 **resources/static** 文件夹中有一个名为 **css/app.css** 的文件，可以通过 **`/static/css/app.css`** 访问它。

**ws://** 协议消除了在资源 URL 中硬编码 `static` 前缀的需要，可以防止根据部署上下文而更改的前缀。如果 web 应用被部署在根目录以外的上下文下，例如 **/mycontext**，则 **css/app.css** 的 URL 将是：**`/mycontext/static/css/app.css`**  

:::tip 配置静态前缀
您可以使用 [webforj 配置](../configuration/properties#configuration-options) 选项 `webforj.assetsDir` 控制 `static` 前缀。此设置指定用于服务静态文件的路由名称，同时 **物理文件夹仍然命名为 `static`**。如果默认静态路由与您的应用中的某个路由发生冲突，这特别有用，因为它允许您更改路由名称而无需重命名文件夹。
:::

您可以使用 <JavadocLink type="foundation" location="com/webforj/utilities/Assets" code='true'>Assets</JavadocLink> 工具类解析给定的 веб 服务器 URL。这在您有自定义组件需要支持该协议时可以是非常有用的。

```java
String url = Assets.resolveWebServerUrl("ws://js/app.js");
```

## 上下文协议 {#the-context-protocol}

上下文协议映射到应用的类路径中的 `src/main/resources` 资源。一些资源 API 方法和注解支持此协议，以读取位于资源文件夹中的文件内容并将其发送到浏览器。例如，`InlineJavaScript` 注解允许从资源文件夹中读取 JavaScript 文件的内容，并在客户端内联它。

例如：

```java
String content = Assets.contentOf(
  Assets.resolveContextUrl("context://data/customers.json")
);
```

## 图标协议 {#the-icons-protocol}

**`icons://`** 协议提供了一种动态的图标管理方法，使生成和服务图标在 [可安装应用](../configuration/installable-apps) 中更加高效和灵活。此协议允许您根据请求的文件名和参数动态生成图标，消除了在许多情况下预生成图标的需求。

```java
Img icon = new Img("icons://icon-192x192.png")
```

### 基础图标 {#base-icon}

当使用 `icons://` 协议请求图标时，将根据请求的图标文件名动态派生一个基础图像。这确保了图标设计的一致性，并在未提供基础图标时自动回退到默认图像。

- **示例 1:** 请求: `/icons/icon-192x192.png` → 基础图标: `resources/icons/icon.png`
- **示例 2:** 请求: `/icons/icon-different-192x192.png` → 基础图标: `resources/icons/icon-different.png`

如果请求的图标没有基础图像存在，将使用默认的 webforJ 徽标作为回退。

:::tip `webforj.iconsDir`
默认情况下，webforJ 从 `resources/icons/` 目录提供图标。您可以通过在 webforJ 配置文件中设置 `webforj.iconsDir` 属性来更改端点名称。这仅更改用于访问图标的 URL 端点，而不更改实际的文件夹名称。默认端点为 `icons/`。
:::

### 重写图标 {#overriding-icons}

您可以通过将预生成的图像放置在 `resources/icons/` 目录中来覆盖特定的图标大小。当需要自定义某些大小或样式时，这提供了更大的图标外观控制。

- **示例:** 如果 `resources/icons/icon-192x192.png` 存在，它将直接提供，而不是动态生成。

### 自定义图标外观 {#customizing-icon-appearance}

`icons://` 协议支持允许您自定义动态生成图标外观的附加参数。这包括用于填充和背景颜色的选项。

- **填充**: `padding` 参数可用于控制图标周围的填充。接受的值范围为 `0`，表示没有填充，至 `1`，表示最大填充。
  - **示例:** `/icons/icon-192x192.png?padding=0.3`
  
- **背景颜色**: `background` 参数允许您设置图标的背景颜色。它支持以下值:
  - **`transparent`**: 无背景颜色。
  <!-- vale off -->
  - **十六进制颜色代码**: 自定义背景颜色（例如，`FFFFFF` 表示白色）。
  <!-- vale on -->
  - **`auto`**: 尝试自动检测图标的背景颜色。

例如: 
  
  - **示例 1:** `/icons/icon-192x192.png?background=000000`
  - **示例 2:** `/icons/icon-192x192.png?background=auto`
