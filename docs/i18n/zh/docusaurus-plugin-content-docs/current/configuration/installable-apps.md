---
title: Installable Apps
sidebar_position: 10
_i18n_hash: 611c70817a57e6cad940081f90d4e0a2
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

`@AppProfile` 注解在 webforJ 中使您能够使您的应用程序在支持的平台上可安装。可安装的网络应用程序与设备的操作系统无缝集成。安装后，它们会出现在主屏幕或应用菜单中，与本地应用相似。为了实现这一点，必须提供某些元数据，例如名称、描述和图标。这些详细信息有助于操作系统识别和显示应用程序。

:::info 安全源要求
要使应用程序可安装，必须从安全源提供，例如 `https`。此要求确保应用程序符合安装所需的安全标准。但是，在开发期间从 `localhost` 本地提供应用程序时，此规则不适用。

<!-- vale off -->
有关安全上下文及其重要性的更多详细信息，请参阅 [安全上下文 MDN 文档](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)。
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## `@AppProfile` 注解 {#appprofile-annotation}

`@AppProfile` 注解应用于主应用程序类，并需要最少的配置。至少，您需要提供：

- **name**: 应用程序的全名。
- **shortName**: 用于空间有限上下文的简洁名称版本。

其他可选属性允许自定义应用程序的外观和行为。

当 `@AppProfile` 注解存在时，webforJ：

- 自动设置必要的元标签。
- 生成 [网络应用程序清单](https://developer.mozilla.org/en-US/docs/Web/Manifest)。
- 提供相关资源，例如图标和屏幕截图。

### 示例：应用 `@AppProfile` {#example-applying-appprofile}

```java
@AppProfile(
  name = "Zyntric Bank",
  shortName = "ZBank",
  description = "Zyntric Bank 是一款使用 webforJ 构建的简单银行应用程序",
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

| **属性**           | **类型**                                            | **描述**                                                                                              | **默认值**           |
| ------------------ | -------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | --------------------- |
| `name`             | `String`                                          | 应用程序的全名，显示在应用菜单和安装对话框中。                                                      | **强制**             |
| `shortName`        | `String`                                          | 名称的简短版本，用于空间有限的上下文。不得超过 12 个字符。                                          | **强制**             |
| `description`      | `String`                                          | 应用程序的简要描述，在安装时和应用程序设置中显示。                                                  | `""`                  |
| `themeColor`       | `String`                                          | 应用程序的主题颜色，在启动应用程序时应用于浏览器用户界面。                                         | `"#ffffff"`           |
| `backgroundColor`  | `String`                                          | 应用程序加载时的初始背景颜色。                                                                        | `"#f8fafc"`           |
| `startUrl`         | `String`                                          | 启动应用程序时打开的 URL。                                                                              | `"."`                 |
| `display`          | `Display` **_Enum_**                              | 应用程序的显示模式（例如，`FULLSCREEN`，`STANDALONE`，`BROWSER`）。                                   | `STANDALONE`          |
| `orientation`      | `Orientation` **_Enum_**                          | 应用程序的默认方向（例如，`PORTRAIT`，`LANDSCAPE`，`NATURAL`）。                                   | `NATURAL`             |
| `icons`            | [`Icon[]`](#appprofileicon-properties)           | 代表应用程序在不同分辨率下的图标数组。                                                               | `[]`                  |
| `defaultIcon`      | [`DefaultIcon`](#appprofiledefaulticon-properties) | 指定应用程序的默认图标。如果配置，则自动生成多个大小的图标路径。                                   | `icons://icon.png` |
| `screenshots`      | [`Screenshot[]`](#appprofilescreenshot-properties) | 应用程序的屏幕截图数组，在安装对话框中使用。                                                       | `[]`                  |
| `categories`       | `String[]`                                        | 用于对应用程序进行分类的类别（例如，`Finance`，`Shopping`）。                                       | `[]`                  |

### `@AppProfile.Icon` 属性 {#appprofileicon-properties}

图标定义了您的应用程序在菜单和主屏幕中的视觉表示。`@AppProfile.Icon` 注解支持以下属性：

| **属性**                                                                       | **类型** | **描述**                                                                                             | **默认值**        |
| -------------------------------------------------------------------------------- | -------- | ---------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                            | `String` | 图标的路径。这可以是绝对 URL 或 `ws://` 路径。                                                        | **强制**         |
| `sizes`                                                                          | `String` | 字符串，指定图像的一个或多个尺寸，格式为 `宽度x高度`（例如，`512x512`）。                           | **强制**         |
| `type`                                                                           | `String` | 图标的 MIME 类型（例如，`image/png`，`image/jpeg`）。如果未提供，则将进行检测                   | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | 图标的用途（例如，`any`，`maskable`，`monochrome`）。                                               | `""`              |

### 示例 {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` 属性 {#appprofiledefaulticon-properties}

`DefaultIcon` 注解通过从基础图标生成多个大小变体来简化应用程序图标的配置。这对于确保与不同分辨率的设备的兼容性特别有用。

| **属性**  | **类型** | **描述**                                                                  | **默认值**       |
| ----------- | -------- | -------------------------------------------------------------------------- | ----------------- |
| `value`     | `String` | 基础图标文件的路径。这可以是绝对 URL 或 `ws://` 路径。                      | **强制**         |
| `sizes`     | `int[]`  | 要生成的大小数组，以整数指定（例如，`{144, 192, 512}`）。                  | `{144, 192, 512}` |

:::info 图标文件要求
此配置不会自动生成应用程序的实际图标文件。相反，它使用 `@AppProfile.DefaultIcon` 注解为每个指定大小生成相应的 [`@AppProfile.Icon`](#appprofileicon-properties) 条目。

#### 如果使用 [webserver 协议](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- 您必须在 `static/icons` 文件夹中提供基础 `icon.png` 文件。
- 您需要包含名为 `icon-144x144.png`、`icon-192x192.png` 和 `icon-512x512.png` 的其他图标变种。
- 这些特定大小确保与各种设备和分辨率的兼容性。

#### 如果使用 [icons 协议](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- 您需要在 `/icons` 文件夹中提供基础 `icon.png` 文件。
- `icons` 端点在请求时动态提供不同大小的图标。
:::

### `@AppProfile.Screenshot` 属性 {#appprofilescreenshot-properties}

屏幕截图在安装对话框或应用商店中提供应用程序的预览。`@AppProfile.Screenshot` 注解支持以下属性：

| **属性**                                                                                        | **类型** | **描述**                                                                                             | **默认值**       |
| ----------------------------------------------------------------------------------------------- | -------- | ---------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                            | `String` | 屏幕截图的路径。这可以是绝对 URL 或 `ws://` 路径。                                               | **强制**         |
| `sizes`                                                                                          | `String` | 字符串，指定图像的一个或多个尺寸，格式为 `宽度x高度`（例如，`1080x1920`）。                        | **强制**         |
| `type`                                                                                           | `String` | 屏幕截图的 MIME 类型（例如，`image/png`，`image/jpeg`）。如果未提供，则将进行检测                   | `""`              |
| `label`                                                                                          | `String` | 屏幕截图的描述性标签。                                                                               | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor)   | `String` | 屏幕截图的形式因素（例如，`narrow`，`wide`）。                                                     | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)        | `String` | 屏幕截图所针对的平台（例如，`ios`，`android`）。                                                   | `""`              |

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
