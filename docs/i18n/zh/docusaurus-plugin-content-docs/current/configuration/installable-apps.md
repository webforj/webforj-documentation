---
title: Installable Apps
sidebar_position: 10
_i18n_hash: 76c2d63a5d5ea13f5ce55431108e6a3d
---
<DocChip chip='since' label='24.21' />
<JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" top='true'/>

`@AppProfile` 注解在 webforJ 中使您能够使应用可在支持的平台上安装。可安装的 Web 应用与设备的操作系统无缝集成。安装后，应用将在主屏幕或应用菜单中出现，类似于原生应用。为了实现这一点，必须提供某些元数据，如名称、描述和图标。这些细节帮助操作系统识别和显示应用。

:::info 安全来源要求
要使应用可安装，它必须来自安全来源，例如 `https`。此要求确保应用满足安装所需的安全标准。然而，当在开发期间从 `localhost` 本地提供应用时，此规则不适用。

<!-- vale off -->
有关安全上下文及其重要性的更多详细信息，请参阅 [Secure Contexts MDN 文档](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts)。
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## `@AppProfile` 注解 {#appprofile-annotation}

`@AppProfile` 注解应用于主应用类，并需要最少的配置。至少需要提供：

- **name**：应用的全名。
- **shortName**：在有限空间上下文中使用的名称的简短版本。

其他可选属性允许自定义应用的外观和行为。

当 `@AppProfile` 注解存在时，webforJ：

- 自动设置必要的元标签。
- 生成 [Web 应用清单](https://developer.mozilla.org/en-US/docs/Web/Manifest)。
- 提供相关资源，如图标和截图。

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

| **属性**        | **类型**                                           | **描述**                                                                                           | **默认值**         |
| -------------   | -------------------------------------------------- | ------------------------------------------------------------------------------------------------- | ----------------- |
| `name`          | `String`                                           | 应用的全名，显示在应用菜单和安装对话框中。                                                      | **强制**          |
| `shortName`     | `String`                                           | 名称的短版本，用于有限空间上下文中。长度不应超过 12 个字符。                                       | **强制**          |
| `description`   | `String`                                           | 应用的简要描述，在安装和应用设置中显示。                                                        | `""`              |
| `themeColor`    | `String`                                           | 应用的主题颜色，在启动应用时应用于浏览器 UI。                                                  | `"#ffffff"`       |
| `backgroundColor` | `String`                                         | 应用加载时的初始背景颜色。                                                                        | `"#f8fafc"`       |
| `startUrl`      | `String`                                           | 启动应用时打开的 URL。                                                                             | `"."`             |
| `display`       | `Display` **_枚举_**                               | 应用的显示模式（例如，`FULLSCREEN`、`STANDALONE`、`BROWSER`）。                                 | `STANDALONE`      |
| `orientation`   | `Orientation` **_枚举_**                           | 应用的默认方向（例如，`PORTRAIT`、`LANDSCAPE`、`NATURAL`）。                                    | `NATURAL`         |
| `icons`         | [`Icon[]`](#appprofileicon-properties)             | 不同分辨率下表示该应用的图标数组。                                                                | `[]`              |
| `defaultIcon`   | [`DefaultIcon`](#appprofiledefaulticon-properties) | 为应用指定默认图标。如果配置，则自动生成多种尺寸的图标路径。                                     | `icons://icon.png`|
| `screenshots`   | [`Screenshot[]`](#appprofilescreenshot-properties) | 应用的截图数组，用于安装对话框。                                                                  | `[]`              |
| `categories`    | `String[]`                                         | 应用的分类（例如，`Finance`、`Shopping`）。                                                      | `[]`              |

### `@AppProfile.Icon` 属性 {#appprofileicon-properties}

图标定义了应用在菜单和主屏幕上的视觉表示。`@AppProfile.Icon` 注解支持以下属性：

| **属性**                                                                     | **类型** | **描述**                                                                                       | **默认值**       |
| ----------------------------------------------------------------------------- | -------- | --------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                        | `String` | 图标的路径。这可以是绝对 URL，或一个 `ws://` 路径。                                          | **强制**         |
| `sizes`                                                                      | `String` | 一个字符串，指定图像的一个或多个尺寸，格式为 `宽度x高度`（例如，`512x512`）。               | **强制**         |
| `type`                                                                       | `String` | 图标的 MIME 类型（例如，`image/png`、`image/jpeg`）。如果未提供，则将进行检测             | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | 图标的用途（例如，`any`、`maskable`、`monochrome`）。                                        | `""`              |

### 示例 {#example}

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` 属性 {#appprofiledefaulticon-properties}

`DefaultIcon` 注解通过从基础图标生成多个尺寸变体来简化应用图标的配置。这对于确保与具有不同分辨率的设备的兼容性特别有用。

| **属性** | **类型** | **描述**                                                                  | **默认值**       |
| --------- | -------- | ------------------------------------------------------------------------ | ----------------- |
| `value`   | `String` | 基础图标文件的路径。这可以是绝对 URL，或一个 `ws://` 路径。             | **强制**         |
| `sizes`   | `int[]`  | 指定生成的尺寸的整数数组（例如，`{144, 192, 512}`）。                   | `{144, 192, 512}` |

:::info 图标文件要求
此配置不会自动生成应用的实际图标文件。相反，它使用 `@AppProfile.DefaultIcon` 注解为每个指定的尺寸生成相应的 [`@AppProfile.Icon`](#appprofileicon-properties) 条目。

#### 如果使用 [webserver 协议](../managing-resources/assets-protocols#the-webserver-protocol) {#if-using-the-webserver-protocol}
- 您必须在 `static/icons` 文件夹中提供一个基础 `icon.png` 文件。
- 您需要包含名为 `icon-144x144.png`、`icon-192x192.png` 和 `icon-512x512.png` 的其他图标变体。
- 这些特定尺寸确保与各种设备和分辨率的兼容性。

#### 如果使用 [icons 协议](../managing-resources/assets-protocols#the-icons-protocol) {#if-using-the-icons-protocol}

- 您需要在 `/icons` 文件夹中提供一个基础 `icon.png` 文件。
- 当请求不同尺寸的图标时，`icons` 端点动态提供不同的图标尺寸。
:::

### `@AppProfile.Screenshot` 属性 {#appprofilescreenshot-properties}

截图提供了应用在安装对话框或应用商店中的预览。`@AppProfile.Screenshot` 注解支持以下属性：

| **属性**                                                                                     | **类型** | **描述**                                                                                          | **默认值**       |
| -------------------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                                         | `String` | 截图的路径。这可以是绝对 URL，或一个 `ws://` 路径。                                             | **强制**         |
| `sizes`                                                                                       | `String` | 一个字符串，指定图像的一个或多个尺寸，格式为 `宽度x高度`（例如，`1080x1920`）。                  | **强制**         |
| `type`                                                                                        | `String` | 截图的 MIME 类型（例如，`image/png`、`image/jpeg`）。如果未提供，则将进行检测              | `""`              |
| `label`                                                                                       | `String` | 截图的描述性标签。                                                                                | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | 截图的形式因子（例如，`narrow`、`wide`）。                                                      | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | 截图所针对的平台（例如，`ios`、`android`）。                                                    | `""`              |

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
