---
title: Installable Apps
sidebar_position: 1
---

The `@AppProfile` annotation in webforJ enables you to make your app installable on supported platforms. 
Installable web apps integrate seamlessly with the device's operating system. 
When installed, they appear on the home screen or app menu, similar to native apps. 
To achieve this, certain metadata such as name, description, and icons must be provided. 
These details help the operating system identify and display the app.

:::info Secure Origin Requirement
For an app to be installable, it must be served from a secure origin, such as `https`. 
This requirement ensures that the app meets the security standards necessary for installation. However, this rule doesn't apply when serving the app locally from `localhost` during development.

<!-- vale off -->
For more details about secure contexts and their importance, refer to the [Secure Contexts MDN documentation](https://developer.mozilla.org/en-US/docs/Web/Security/Secure_Contexts).
<!-- vale on -->
:::

<div class="videos-container">
  <video controls>
    <source src="/video/install-chrome.mp4" type="video/mp4"/>
  </video>
</div>

## `@AppProfile` annotation

The `@AppProfile` annotation is applied to the main app class and requires minimal configuration. At a minimum, you need to provide:

- **name**: The full name of the app.
- **shortName**: A concise version of the name for use in limited-space contexts.

Additional optional properties allow customization of the app's appearance and behavior.

When the `@AppProfile` annotation is present, webforJ:

- Automatically sets up necessary meta tags.
- Generates a [Web Application Manifest](https://developer.mozilla.org/en-US/docs/Web/Manifest).
- Serves related resources such as icons and screenshots.

### Example: Applying `@AppProfile`

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

## `@AppProfile` properties

The following table lists all properties supported by the `@AppProfile` annotation:

| **Property**      | **Type**                                           | **Description**                                                                                           | **Default Value**     |
| ----------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------- | --------------------- |
| `name`            | `String`                                           | The full name of the app, displayed in app menus and installation dialogs.                                | **Mandatory**         |
| `shortName`       | `String`                                           | A short version of the name, used in contexts with limited space. Shouldn't exceed 12 characters.         | **Mandatory**         |
| `description`     | `String`                                           | A brief description of the app, displayed during installation and in app settings.                        | `""`                  |
| `themeColor`      | `String`                                           | The theme color for the app, applied to the browser UI when the app is launched.                          | `"#ffffff"`           |
| `backgroundColor` | `String`                                           | The initial background color for the app during loading.                                                  | `"#f8fafc"`           |
| `startUrl`        | `String`                                           | The URL to open when the app is launched.                                                                 | `"."`                 |
| `display`         | `Display` **_Enum_**                               | The display mode of the app (e.g., `FULLSCREEN`, `STANDALONE`, `BROWSER`).                                | `STANDALONE`          |
| `orientation`     | `Orientation` **_Enum_**                           | The default orientation of the app (e.g., `PORTRAIT`, `LANDSCAPE`, `NATURAL`).                            | `NATURAL`             |
| `icons`           | [`Icon[]`](#appprofileicon-properties)             | An array of icons representing the app at different resolutions.                                          | `[]`                  |
| `defaultIcon`     | [`DefaultIcon`](#appprofiledefaulticon-properties) | Specifies a default icon for the app. Automatically generates icon paths in multiple sizes if configured. | `ws://icons/icon.png` |
| `screenshots`     | [`Screenshot[]`](#appprofilescreenshot-properties) | An array of screenshots for the app, used in installation dialogs.                                        | `[]`                  |
| `categories`      | `String[]`                                         | Categories to classify the app (e.g., `Finance`, `Shopping`).                                             | `[]`                  |

### `@AppProfile.Icon` properties

Icons define the visual representation of your app in menus and home screens. The `@AppProfile.Icon` annotation supports the following properties:

| **Property**                                                                     | **Type** | **Description**                                                                                        | **Default Value** |
| -------------------------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------ | ----------------- |
| `src`                                                                            | `String` | The path to the icon. This can be an absolute URL, or a `ws://` path.                                     | **Mandatory**     |
| `sizes`                                                                          | `String` | A string that specifies one or more sizes of the image in the format `WidthxHeight` (e.g., `512x512`). | **Mandatory**     |
| `type`                                                                           | `String` | The MIME type of the icon (e.g., `image/png`, `image/jpeg`). If not provided then it will be detected  | `""`              |
| [`purpose`](https://developer.mozilla.org/en-US/docs/Web/Manifest/icons#purpose) | `String` | The purpose of the icon (e.g., `any`, `maskable`, `monochrome`).                                       | `""`              |

### Example

```java
@AppProfile.Icon(
  src = "ws://icons/icon-512x512.png",
  sizes = "512x512",
  type = "image/png"
)
```

### `@AppProfile.DefaultIcon` properties

The `DefaultIcon` annotation simplifies the configuration of app icons by generating multiple size variants from a base icon.
This is particularly useful for ensuring compatibility across devices with varying resolutions.

| **Property** | **Type** | **Description**                                                                 | **Default Value** |
| ------------ | -------- | ------------------------------------------------------------------------------- | ----------------- |
| `value`      | `String` | The path to the base icon file. This can be an absolute URL, or a `ws://` path. | **Mandatory**     |
| `sizes`      | `int[]`  | An array of sizes to generate, specified as integers (e.g., `{144, 192, 512}`). | `{144, 192, 512}` |

:::info Icon File Requirements
This configuration doesn't generate the actual icon files for the app on the fly. Instead, it uses the `@AppProfile.DefaultIcon` annotation to generate corresponding [`@AppProfile.Icon`](#appprofileicon-properties) for each specified size.
You are expected to provide a base `icon.png` file in the `static/icons` folder and include additional variations named `icon-144x144.png`, `icon-192x192.png`, and `icon-512x512.png`.
These sizes ensure compatibility with various devices and resolutions.

Make sure the icons are properly optimized and meet the required dimensions. webforJ dynamically includes these icons in the manifest and serves them when the app is accessed.
:::

### `@AppProfile.Screenshot` properties

Screenshots provide a preview of the app in installation dialogs or app stores. The `@AppProfile.Screenshot` annotation supports the following properties:

| **Property**                                                                                  | **Type** | **Description**                                                                                             | **Default Value** |
| --------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------------------------------------------- | ----------------- |
| `src`                                                                                         | `String` | The path to the screenshot. This can be an absolute URL, or a `ws://` path.                                 | **Mandatory**     |
| `sizes`                                                                                       | `String` | A string that specifies one or more sizes of the image in the format `WidthxHeight` (e.g., `1080x1920`).    | **Mandatory**     |
| `type`                                                                                        | `String` | The MIME type of the screenshot (e.g., `image/png`, `image/jpeg`). If not provided then it will be detected | `""`              |
| `label`                                                                                       | `String` | A descriptive label for the screenshot.                                                                     | `""`              |
| [`formFactor`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#form_factor) | `String` | The form factor of the screenshot (e.g., `narrow`, `wide`).                                                 | `""`              |
| [`platform`](https://developer.mozilla.org/en-US/docs/Web/Manifest/screenshots#platform)      | `String` | The platform for which the screenshot is intended (e.g., `ios`, `android`).                                 | `""`              |

### Example

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
