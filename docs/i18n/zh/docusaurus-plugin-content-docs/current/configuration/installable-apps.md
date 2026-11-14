---
title: 可安装应用
sidebar_position: 20
description: >-
  Annotate a webforJ app with AppProfile to generate a Web App Manifest with
  icons, screenshots, and metadata for device installation.
_i18n_hash: 9aa99cc9da864a6808d72602b6e5c572
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

webforJ 中的 `@AppProfile` 注解可让应用在受支持的平台上安装。
可安装的 Web 应用能够与设备的操作系统集成。
安装后，它们会像原生应用一样显示在主屏幕或应用菜单中。
为实现这一点，必须提供名称、描述和图标等元数据。
这些信息有助于操作系统识别和显示应用。

:::info 安全来源要求
应用必须通过安全来源（例如 `https`）提供，才能安装。
浏览器会拒绝来自不安全来源的安装尝试。不过，在开发期间从本地 `localhost` 提供应用时，不受此规则限制。

<!-- vale off -->
有关安全上下文及其重要性的详细信息，请参阅 [MDN 安全上下文文档](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)。
<!-- vale on -->
:::

<VideoPlayer video="installable-apps/install-chrome" width={1908} height={1052} title="Install chrome" />

## 浏览器支持 {#browser-support}

Web 应用的安装支持情况因浏览器和平台而异。

### 桌面端 {#browser-support-desktop}

- **Chromium 浏览器**（Chrome、Edge、Opera、Brave 等）可在所有受支持的桌面操作系统上安装包含清单文件的应用。
- **Safari** 在 macOS Sonoma（Safari 17）及更高版本中支持 **文件 → 添加到程序坞**。无论 Web 应用是否包含清单文件，都可以使用此流程。
- **Firefox** 不支持在桌面端通过清单文件安装 Web 应用。

### 移动端 {#browser-support-mobile}

- 在 **Android** 上，Chrome、Edge、Firefox、Opera 和 Samsung Internet 均支持安装 Web 应用。
- 在 **iOS 16.3 及更早版本**上，只能通过 Safari 安装 Web 应用（**分享 → 添加到主屏幕**）。
- 在 **iOS 16.4 及更高版本**上，可通过 Safari、Chrome、Edge、Firefox 和 Orion 中的分享菜单安装 Web 应用。

## `@AppProfile` 注解 {#appprofile-annotation}

`@AppProfile` 注解应用于应用主类，只需进行少量配置。至少需要提供：

- **name**：应用的完整名称。
- **shortName**：应用名称的简短版本，用于空间有限的场景。

其他可选属性可用于自定义应用的外观和行为。

使用 `@AppProfile` 注解后，webforJ 会：

- 自动设置所需的 meta 标签。
- 生成 [Web 应用清单](https://developer.mozilla.org/en-US/docs/Web/Manifest)。
- 提供图标和屏幕截图等相关资源。

### 示例：应用 `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank is a simple banking application built with webforJ",
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

下表列出了 `@AppProfile` 注解支持的所有属性：

| **属性**          | **类型**                                           | **说明**                                                                                       | **默认值**            |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | 应用的完整名称，显示在应用菜单和安装对话框中。                                | **必填**         |
| `shortName`       | `String`                                           | 应用名称的简短版本，用于空间有限的场景。建议不超过 12 个字符。         | **必填**         |
| `description`     | `String`                                           | 应用的简要说明，显示在安装过程中和应用设置中。                        | `""`                  |
| `themeColor`      | `String`                                           | 应用的主题颜色，在启动应用时应用于浏览器 UI。                          | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | 应用加载期间使用的初始背景颜色。                                                  | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | 启动应用时打开的 URL。                                                                 | `"."`                 |
| `display`         | `Display` **_Enum_**                               | 应用的显示模式（例如 `FULLSCREEN`、`STANDALONE`、`BROWSER`）。                                | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | 应用的默认方向（例如 `PORTRAIT`、`LANDSCAPE`、`NATURAL`）。                            | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | 表示应用的图标数组，包含不同分辨率。                                          | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | 指定应用的默认图标。配置后会自动生成多个尺寸的图标路径。 | `icons://icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | 应用的屏幕截图数组，用于安装对话框。                                        | `[]`                  |
| `categories`      | `String[]`                                         | 用于对应用进行分类的类别（例如 `Finance`、`Shopping`）。                                             | `[]`                  |

### `@AppProfile.Icon` 属性 {#appprofileicon-properties}

图标定义应用在菜单和主屏幕中的视觉表示。`@AppProfile.Icon` 注解支持以下属性：

| **属性**                                                                         | **类型** | **说明**                                                                                         | **默认值** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                            | `String` | 图标的路径。可以是绝对 URL，也可以是 `ws://` 路径。                                     | **必填**     |
| `sizes`                                                                          | `String` | 指定图像一个或多个尺寸的字符串，格式为 `WidthxHeight`（例如 `512x512`）。 | **必填**     |
| `type`                                                                           | `String` | 图标的媒体类型（例如 `image/png`、`image/jpeg`）。如果未提供，则会自动检测。  | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | 图标的用途（例如 `any`、`maskable`、`monochrome`）。                                       | `""`              |

### 示例 {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` 属性 {#appprofiledefaulticon-properties}

`DefaultIcon` 注解可从基础图标生成多个尺寸变体，从而简化应用图标的配置。
它会生成设备通常请求的分辨率对应的图标。

| **属性** | **类型** | **说明**                                                                    | **默认值** |
| ------------ | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`      | `String` | 基础图标文件的路径。可以是绝对 URL，也可以是 `ws://` 路径。 | **必填**     |
| `sizes`      | `int[]`  | 要生成的尺寸数组，以整数指定（例如 `{144, 192, 512}`）。 | `{144, 192, 512}` |

:::info 图标文件要求
此配置不会自动生成应用的实际图标文件。相反，它使用 `@AppProfile.DefaultIcon` 注解，为每个指定尺寸生成相应的 [`@AppProfile.Icon`](#appprofileicon-properties) 条目。

#### 如果使用 [webserver 协议](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- 必须在 `static/icons` 文件夹中提供基础 `icon.png` 文件。
- 还需要包含名为 `icon-144x144.png`、`icon-192x192.png` 和 `icon-512x512.png` 的其他尺寸图标。
- 这些特定尺寸涵盖了设备通常请求的分辨率。

#### 如果使用 [icons 协议](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- 需要在 `/icons` 文件夹中提供基础 `icon.png` 文件。
- 请求时，`icons` 端点会动态提供不同尺寸的图标。
:::

### `@AppProfile.Screenshot` 属性 {#appprofilescreenshot-properties}

屏幕截图可在安装对话框或应用商店中预览应用。`@AppProfile.Screenshot` 注解支持以下属性：

| **属性**                                                                                  | **类型** | **说明**                                                                                             | **默认值** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                         | `String` | 屏幕截图的路径。可以是绝对 URL，也可以是 `ws://` 路径。                                 | **必填**     |
| `sizes`                                                                                       | `String` | 指定图像一个或多个尺寸的字符串，格式为 `WidthxHeight`（例如 `1080x1920`）。    | **必填**     |
| `type`                                                                                        | `String` | 屏幕截图的媒体类型（例如 `image/png`、`image/jpeg`）。如果未提供，则会自动检测。 | `""`              |
| `label`                                                                                       | `String` | 屏幕截图的描述性标签。                                                                     | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | 屏幕截图的外形规格（例如 `narrow`、`wide`）。                                                 | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | 屏幕截图适用的平台（例如 `ios`、`android`）。                                 | `""`              |

### 示例 {#example-1}

```java
@AppProfile.Screenshot(
  src = "ws://img/screenshots/s1.jpg",
  sizes = "1080x1920"
)
```

<VideoPlayer video="installable-apps/install-android" width={374} height={824} title="Install android" />
