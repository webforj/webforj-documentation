---
sidebar_position: 38
sidebar_class_name: new-content
title: App badges
description: >-
  Paint notification badges onto the operating system app icon and the browser
  tab favicon.
_i18n_hash: ff5a388432db849aa6d7b7ac1f48aa89
---
# 应用徽章 <DocChip chip='since' label='26.01' />

webforJ 提供了两个互补的徽章 API。 <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink>`.setBadge` 用于绘制在 dock、任务栏或主屏幕上显示的操作系统应用图标。 <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink>`.setIconBadge` 用于绘制在浏览器标签条上显示的文档 favicon。它们针对不同的表面，并具有不同的先决条件，因此大多数应用都调用这两个 API，以获得最广泛的可见性。

<!-- INTRO_END -->

## 应用图标徽章 {#app-icon-badge}

`App.setBadge` 在操作系统用于应用的图标上渲染徽章：macOS dock、Windows 任务栏、Chrome OS 任务栏或 Android 主屏幕。

![macOS dock 中的应用图标徽章](/img/app-badges/app-badge.png)

### 先决条件 {#app-prerequisites}

徽章只有在以下所有情况为真时可见：

- 浏览器支持在应用图标上绘制徽章。
- 页面从安全上下文（HTTPS，或在开发期间使用 `http://localhost`）提供。普通 HTTP 源拒绝该调用。
- 应用已安装在设备上。安装流程因浏览器而异：Chromium 浏览器为任何携带清单的页面提供安装提示，macOS 上的 Safari 使用 **文件 → 添加到 Dock**，iOS 上的 Safari 使用 **共享 → 添加到主屏幕**。

要使应用在 Spring Boot 或独立的 webforJ 服务器下可安装，可以用 <JavadocLink type="foundation" location="com/webforj/annotation/AppProfile" code='true'>AppProfile</JavadocLink> 注解 <JavadocLink type="foundation" location="com/webforj/App" code='true'>App</JavadocLink> 子类。注解处理器会生成清单、应用图标链接标签和浏览器需要提供安装提示的元标签。

```java
@AppProfile(name = "收件箱", shortName = "收件箱")
public class Application extends App {}
```

请参阅 [可安装应用](../configuration/installable-apps) 页面，了解 `@AppProfile` 成员、图标大小以及有关安装流程的特定平台指导。

### 浏览器支持 {#app-browser-support}

安装后，徽章的呈现取决于浏览器。安装支持本身在 [可安装应用](../configuration/installable-apps#browser-support) 页面中进行了概述。

| 浏览器 | 安装后徽章呈现 |
| --- | --- |
| Chrome、Edge、Opera 以及其他 Chromium 浏览器（桌面和 Android） | 是 |
| macOS Sonoma（Safari 17）及更高版本上的 Safari | 是 |
| iOS 16.4 及更高版本上的 Safari | 是 |
| Firefox（所有平台） | 否。调用返回而不进行渲染。 |

### 设置和清除徽章 {#app-setting-clearing}

传递正整数以显示数字徽章。传递 `null` 或 `0` 以清除徽章。调用无参数重载以显示标志指示器（一个小点，确切视觉效果由平台定义）。

```java
App.setBadge(5);     // 数字徽章
App.setBadge();      // 无数字的标志指示器
App.setBadge(0);     // 清除
App.setBadge(null);  // 清除
```

`App.setBadge` 会立即返回。浏览器会异步将徽章写入操作系统表面，并且更改不会反馈给应用。

## 浏览器标签图标徽章 {#browser-tab-icon-badge}

`Page.setIconBadge` 将计数绘制到文档 favicon 上。它在任何标签中都有效，而无需安装，并且不需要清单。徽章在浏览器标签条中可见，也可以在其他呈现 favicon 的位置（例如书签或最近页面视图）中可见。

![带有数字徽章叠加的浏览器标签 favicon](/img/app-badges/icon-badge.png)

### 设置和清除徽章 {#tab-setting-clearing}

```java
Page page = Page.getCurrent();

page.setIconBadge(5);     // 数字徽章
page.setIconBadge();      // 无数字的标志指示器
page.setIconBadge(0);     // 清除
page.setIconBadge(null);  // 清除
```

清除徽章将恢复原始 favicon。

:::info 与 `BBjServices` 一起运行
当应用由 `BBjServices` 提供时，favicon 是在企业管理器中为应用配置的 **快捷方式图像**。徽章被绘制到企业管理器提供的任何图标上。如果没有配置快捷方式图像，`Page.setIconBadge` 将没有 favicon 进行叠加，并静默无操作。
:::

### 美化徽章 {#styling-the-badge}

传递一个 <JavadocLink type="foundation" location="com/webforj/IconBadgeOptions" code='true'>IconBadgeOptions</JavadocLink> 来控制颜色、形状和大小：

```java
IconBadgeOptions options = new IconBadgeOptions()
    .setColor(new Color(0x2e, 0x7d, 0x32))
    .setShape(IconBadgeOptions.Shape.SQUARE)
    .setSize(1.25);

Page.getCurrent().setIconBadge(5, options);
```

选项对象是一个值持有者。所有设置器返回 `this`，因此可以链式调用。

| 选项 | 类型 | 默认 | 说明 |
|---|---|---|---|
| `color` | `java.awt.Color` | `#e53935` | 徽章的背景颜色。文本颜色自动导出以形成对比，因此数字在选择的任何颜色上都保持可读。 |
| `shape` | `Shape` | `CIRCLE` | `CIRCLE` 或 `SQUARE`。 |
| `size` | `double` | `1.0` | 相对大小。`0.5` 是默认直径的一半；`1.5` 是更大 50%。徽章被限制在favicon画布内。 |

### 浏览器注意事项 {#browser-caveat}

Safari 在初始页面加载后不刷新 favicon。对 `Page.setIconBadge` 的调用在没有错误的情况下完成，但 Safari 继续显示原始图标。如果需要在 Safari 中提供可见提示，可以使用 `Page.setTitle` 将计数添加到文档标题前面。

```java
int unread = 5;
Page page = Page.getCurrent();
page.setIconBadge(unread);
page.setTitle("(" + unread + ") 收件箱");
```

## 选择两者中的哪一个 {#choosing-between-the-two}

| 表面 | API | 需要安装 | 在 Safari 中可见 |
|---|---|---|---|
| 操作系统应用图标 | `App.setBadge` | 是 | 是 （macOS Sonoma / iOS 16.4 及更高版本） |
| 浏览器标签 favicon | `Page.setIconBadge` | 否 | 否。调用在没有错误的情况下完成，但标签条不刷新。 |

大多数应用都会调用这两个 API，以便徽章在用户处于已安装窗口或常规浏览器标签中时都是可见的。
