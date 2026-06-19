---
title: Installable Apps
sidebar_position: 10
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 2d76df483c951a64d266380d7c96b692
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

`@AppProfile` 注解在 webforJ 中使您能够使您的应用在受支持的平台上可安装。 
可安装的 Web 应用与设备的操作系统集成。 
安装后，它们会出现在主屏幕或应用菜单上，类似于本地应用。 
为了实现这一点，必须提供某些元数据，例如名称、描述和图标。 
这些细节帮助操作系统识别和显示应用程序。

:::info 安全来源要求
为了使应用可安装，它必须从安全来源提供，例如 `https`。 
浏览器会拒绝在不安全的来源上的安装尝试。然而，当在开发过程中从 `localhost` 本地提供应用时，此规则不适用。

<!-- vale off -->
有关安全上下文及其重要性的更多详细信息，请参阅 [安全上下文 MDN 文档](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)。
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## 浏览器支持 {#browser-support}

安装 Web 应用的支持因浏览器和平台而异。

### 桌面 {#browser-support-desktop}

- **Chromium 浏览器**（Chrome、Edge、Opera、Brave 等）在所有受支持的桌面操作系统上安装任何附带清单文件的应用。
- **Safari** 在 macOS Sonoma（Safari 17）及更高版本中支持 **文件 → 添加到 Dock**。该流程适用于任何 Web 应用，无论是否有清单文件。
- **Firefox** 不支持在桌面上从清单文件安装 Web 应用。

### 移动 {#browser-support-mobile}

- 在 **Android** 上，Chrome、Edge、Firefox、Opera 和三星互联网浏览器均支持安装 Web 应用。
- 在 **iOS 16.3 及更早版本**中，Web 应用只能从 Safari 安装（**分享 → 添加到主屏幕**）。
- 在 **iOS 16.4 及更高版本**中，Web 应用可以从 Safari、Chrome、Edge、Firefox 和 Orion 的分享菜单安装。

## `@AppProfile` 注解 {#appprofile-annotation}

`@AppProfile` 注解应用于主应用类，需要最少的配置。至少，您需要提供：

- **name**: 应用的完整名称。
- **shortName**: 用于有限空间上下文的简洁版本名称。

其他可选属性允许自定义应用的外观和行为。

当 `@AppProfile` 注解存在时，webforJ：

- 自动设置必要的元标签。
- 生成 [Web 应用程序清单](https://developer.mozilla.org/en-US/docs/Web/Manifest)。
- 提供相关资源，如图标和屏幕截图。

### 示例：应用 `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank 是一个使用 webforJ 构建的简单银行应用程序",
  screenshots = {
    @AppProfile.Screenshot(
      src = "ws://img/screenshots/s1.jpg",
      sizes = "1080x1920"
    )
  }
)
public class Application extends App {
}
```

## `@AppProfile` 属性 {#appprofile-properties}

以下表格列出了 `@AppProfile` 注解支持的所有属性：

| **属性**        | **类型**                                 | **描述**                                                                 | **默认值**         |
| --------------- | ---------------------------------------- | ------------------------------------------------------------------------ | ------------------- |
| `name`          | `String`                                 | 应用的完整名称，显示在应用菜单和安装对话框中。                         | **必填**           |
| `shortName`     | `String`                                 | 名称的简短版本，用于空间有限的上下文。不得超过 12 个字符。               | **必填**           |
| `description`   | `String`                                 | 应用的简要描述，安装时和应用设置中显示。                                | `""`                |
| `themeColor`    | `String`                                 | 应用的主题颜色，当应用启动时应用于浏览器 UI。                          | `"#ffffff"`         |
| `backgroundColor`| `String`                                | 应用加载时的初始背景颜色。                                              | `"#f8fafc"`         |
| `startUrl`      | `String`                                 | 启动应用时打开的 URL。                                                  | `"."`               |
| `display`       | `Display` **_Enum_**                     | 应用的显示模式（例如，`FULLSCREEN`、`STANDALONE`、`BROWSER`）。          | `STANDALONE`        |
| `orientation`   | `Orientation` **_Enum_**                 | 应用的默认方向（例如，`PORTRAIT`、`LANDSCAPE`、`NATURAL`）。            | `NATURAL`           |
| `icons`         | [`Icon[]`](#appprofileicon-properties)   | 表示应用在不同分辨率下的图标数组。                                     | `[]`                |
| `defaultIcon`   | [`DefaultIcon`](#appprofiledefaulticon-properties)| 指定应用的默认图标。如果配置，则自动生成多种大小的图标路径。           | `icons://icon.png` |
| `screenshots`   | [`Screenshot[]`](#appprofilescreenshot-properties)| 应用的屏幕截图数组，用于安装对话框。                                   | `[]`                |
| `categories`    | `String[]`                               | 用于对应用进行分类的类别（如 `Finance`、`Shopping`）。                  | `[]`                |

### `@AppProfile.Icon` 属性 {#appprofileicon-properties}

图标定义了应用在菜单和主屏幕中的视觉表示。`@AppProfile.Icon` 注解支持以下属性：

| **属性**                                                                    | **类型**   | **描述**                                                                 | **默认值**       |
| -------------------------------------------------------------------------- | ---------- | ------------------------------------------------------------------------ | ----------------- |
| `src`                                                                     | `String`   | 图标的路径。这可以是一个绝对 URL 或 `ws://` 路径。                       | **必填**         |
| `sizes`                                                                   | `String`   | 指定图像的一个或多个尺寸的字符串，格式为 `WidthxHeight`（例如 `512x512`）。 | **必填**         |
| `type`                                                                    | `String`   | 图标的媒体类型（例如 `image/png`、`image/jpeg`）。如果不提供，则将自动检测 | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | 图标的目的（例如 `any`、`maskable`、`monochrome`）。                   | `""`              |

### 示例 {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` 属性 {#appprofiledefaulticon-properties}

`DefaultIcon` 注解通过从基础图标生成多个大小变体来简化应用图标的配置。
它生成设备通常请求的分辨率的图标。

| **属性** | **类型** | **描述**                                                                  | **默认值**       |
| -------- | -------- | ------------------------------------------------------------------------ | ----------------- |
| `value`  | `String` | 基础图标文件的路径。这可以是一个绝对 URL 或 `ws://` 路径。               | **必填**         |
| `sizes`  | `int[]`  | 要生成的大小数组，以整数指定（例如 `{144, 192, 512}`）。                | `{144, 192, 512}` |

:::info 图标文件要求
此配置不会自动为应用生成实际的图标文件。相反，它使用 `@AppProfile.DefaultIcon` 注解为每个指定大小生成相应的 [`@AppProfile.Icon`](#appprofileicon-properties) 条目。

#### 如果使用 [webserver 协议](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- 您必须在 `static/icons` 文件夹中提供基础 `icon.png` 文件。
- 您需要包含名为 `icon-144x144.png`、`icon-192x192.png` 和 `icon-512x512.png` 的其他图标变体。
- 这些特定的尺寸涵盖设备通常请求的分辨率。

#### 如果使用 [icons 协议](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- 您需要在 `/icons` 文件夹中提供基础 `icon.png` 文件。
- `icons` 端点会在请求时动态提供不同的图标大小。
:::

### `@AppProfile.Screenshot` 属性 {#appprofilescreenshot-properties}

屏幕截图在安装对话框或应用商店中提供应用的预览。`@AppProfile.Screenshot` 注解支持以下属性：

| **属性**                                                                                   | **类型** | **描述**                                                                                                | **默认值**       |
| ----------------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                                    | `String` | 截图的路径。这可以是一个绝对 URL 或 `ws://` 路径。                                                    | **必填**         |
| `sizes`                                                                                  | `String` | 指定图像尺寸的字符串，格式为 `WidthxHeight`（例如 `1080x1920`）。                                     | **必填**         |
| `type`                                                                                   | `String` | 截图的媒体类型（例如 `image/png`、`image/jpeg`）。如果未提供，则将自动检测。                         | `""`              |
| `label`                                                                                  | `String` | 截图的描述性标签。                                                                                   | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | 截图的形式因素（例如 `narrow`、`wide`）。                                                            | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)   | `String` | 截图所针对的平台（例如 `ios`、`android`）。                                                          | `""`              |

### 示例 {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<div class="videos-container">
  <video controls>
    <source src="/video/install-android.mp4" type="video/mp4"/>
  </video>
</div>
