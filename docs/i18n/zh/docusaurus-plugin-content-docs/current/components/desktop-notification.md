---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 6bc148e8bcb06161115d0509601b516b
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

在 webforj 25.00 及更高版本中，**DesktopNotification** 组件提供了一个简单的接口，用于创建、显示和管理桌面通知。该组件专注于最小化配置和内置事件处理，可以在需要通知用户实时事件（如新消息、警报或系统事件）时使用，用户在浏览应用程序时会收到这些通知。

:::warning 实验特性
`DesktopNotification` 组件仍在不断发展，其 API 可能会随着成熟而发生变化。要开始使用此功能，请确保在您的 pom.xml 中包含以下依赖项。

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
::: 

:::info 先决条件
在集成 `DesktopNotification` 组件之前，请确保：

- 您的应用运行在 **安全上下文**（HTTPS）中。
- 浏览器未处于隐身或私人浏览模式。
- 用户已与应用互动（例如，点击按钮或按下键），因为通知需要用户手势才能显示。
- 用户已授予通知权限（如果需要，这将自动请求）。
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## 基本用法 {#basic-usage}

有多种方法可以创建和显示通知。在大多数场景中，最简单的方法是调用静态 `show` 方法，这些方法封装了完整的通知生命周期。

### 示例：显示基本通知 {#example-displaying-a-basic-notification}

```java
// 带有标题和消息的基本通知
DesktopNotification.show("更新可用", "您的下载已完成！");
```

这一行代码创建了一个具有标题和正文的通知，然后尝试显示它。

## 自定义通知 {#customizing-the-notification}

有多种选项可以根据应用程序的需求和通知的目的自定义显示的通知的外观和感觉。

### 设置自定义 `图标` {#setting-a-custom-icon}

默认情况下，通知通过[图标协议](../managing-resources/assets-protocols#the-icons-protocol)使用您定义的应用图标。您可以使用 `setIcon` 方法设置自定义图标。此组件支持不同的 URL 方案：

- [`context://`](../managing-resources/assets-protocols#the-context-protocol)：解析为指向应用资源文件夹的上下文 URL；图像为 base64 编码。
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol)：解析为 web 服务器 URL，提供完整的合格 URL。
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol)：解析为图标 URL。

**示例：**

```java
// 使用自定义图标 URL 创建通知
DesktopNotification notification = new DesktopNotification(
  "提醒", "会议在10分钟内开始。"
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## 通知事件 {#notification-events}

`DesktopNotification` 支持多种生命周期事件，可以附加监听器以处理事件，例如通知何时显示、关闭、点击或出现错误。

| 事件                   | 描述                                               | 何时使用                                               |
|----------------------|--------------------------------------------------|-------------------------------------------------------|
| **打开**             | 当通知显示时触发。                               | 记录通知显示，更新 UI，跟踪参与度。                   |
| **关闭**             | 当通知被关闭时触发。                             | 清理资源，记录关闭，执行后续操作。                      |
| **错误**             | 当通知出现错误或用户未授予权限时触发。          | 优雅地处理错误，通知用户，应用后备方案。                  |
| **点击**             | 当用户点击通知时触发。                           | 导航到特定部分，记录交互，重新聚焦应用。                |

```java
DesktopNotification notification = new DesktopNotification("警报", "您有新消息！")

// 附加打开事件的事件监听器
notification.onOpen(event -> {
  System.out.println("用户已打开通知。");
});

// 同样，监听点击事件
notification.onClick(event -> {
  System.out.println("通知被点击。");
});
```

:::warning 点击行为
浏览器安全策略防止通知点击事件自动使您的应用窗口或标签聚焦。此行为由浏览器强制执行，无法通过编程覆盖。如果您的应用需要窗口聚焦，您需要指示用户在与通知交互后在应用内部点击。
:::

## 安全性和兼容性考虑 {#security-and-compatibility-considerations}

在使用 **DesktopNotification** 组件时，请记住以下几点：

- **安全上下文：** 您的应用必须通过 HTTPS 提供，以确保大多数现代浏览器允许通知。
- **用户手势要求：** 仅在用户触发的操作后显示通知。简单地加载页面不会触发通知。
- **浏览器限制：** 并非所有浏览器都以相同的方式处理自定义图标或聚焦行为。例如，自定义图标可能无法在 Safari 中工作，而其他浏览器中的事件行为可能会有所不同。
- **权限：** 始终确保您的应用优雅地检查并请求用户的通知权限。

## 使用最佳实践 {#usage-best-practices}

在您的应用中使用 `DesktopNotification` 组件时，请牢记以下最佳实践：

- **告知用户：** 让用户知道为什么需要通知以及他们将如何受益。
- **提供后备方案：** 由于某些浏览器可能会限制通知，考虑使用其他方式提醒用户（例如，应用内消息）。
- **错误处理：** 始终注册错误监听器，以优雅地管理通知未能显示的情况。
