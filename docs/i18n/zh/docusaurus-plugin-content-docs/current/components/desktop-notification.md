---
title: DesktopNotification
sidebar_position: 29
description: >-
  Send native OS notifications outside the browser window with the
  DesktopNotification component for real-time messages, alerts, and status
  changes.
_i18n_hash: 529ae2fce596f744b423574be0a95dc0
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

`DesktopNotification` 组件显示在浏览器窗口外的本地桌面通知。它可用于提醒用户实时事件，例如新消息、系统警报或状态更改，同时他们正在使用您的应用程序。

<!-- INTRO_END -->

## 设置和先决条件 {#setup-and-prerequisites}

<ExperimentalWarning />

要开始使用此功能，请在您的 pom.xml 中包含以下依赖项：

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

在整合 `DesktopNotification` 组件之前，确保：

- 您的应用程序在 **安全上下文**（HTTPS）中运行。
- 浏览器不在隐身或私人浏览模式下。
- 用户已与应用程序进行交互（例如，单击按钮或按下键），因为通知需要用户手势才能显示。
- 用户已授予通知权限（如果需要，将自动请求）。

## 基本用法 {#basic-usage}

有多种方法可以创建和显示通知。在大多数情况下，最简单的方法是调用其中一个静态 `show` 方法，该方法封装了完整的通知生命周期。

### 示例：显示基本通知 {#example-displaying-a-basic-notification}

```java
// 具有标题和消息的基本通知
DesktopNotification.show("更新可用", "您的下载已完成！");
```

这行代码创建了一个具有标题和正文的通知，然后尝试显示它。

## 自定义通知 {#customizing-the-notification}

有多种选项可自定义显示通知的外观和感觉，具体取决于应用程序的需求和通知的目的。

### 设置自定义 `图标` {#setting-a-custom-icon}

默认情况下，通知使用通过 [icons protocol](../managing-resources/assets-protocols#the-icons-protocol) 定义的应用程序图标。您可以使用 `setIcon` 方法设置自定义图标。该组件支持不同的 URL 方案：

- [`context://`](../managing-resources/assets-protocols#the-context-protocol)：解析为指向应用程序资源文件夹的上下文 URL；图像为 base64 编码。
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol)：解析为 web 服务器 URL，提供完全合格的 URL。
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol)：解析为图标 URL。

**示例：**

```java
// 使用自定义图标 URL 创建通知
DesktopNotification notification = new DesktopNotification(
  "提醒", "会议将在 10 分钟后开始。"
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## 通知事件 {#notification-events}

`DesktopNotification` 支持多个生命周期事件，可以附加监听器以处理事件，例如通知何时显示、关闭、单击或发生错误。

| 事件                  | 描述                                           | 何时使用                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **打开** | 当通知显示时触发。       | 记录通知显示，更新 UI，跟踪参与度。    |
| **关闭**| 当通知关闭时触发。         | 清理资源，记录拒绝，执行后续操作。|
| **错误**| 当通知出现错误或用户未授予权限时触发。| 友好地处理错误，通知用户，应用后备选项。  |
| **点击**| 当用户单击通知时触发。 | 导航到特定部分，记录交互，重新聚焦应用程序。 |

```java
DesktopNotification notification = new DesktopNotification("警报", "您有一条新消息！")

// 附加打开事件的事件监听器
notification.onOpen(event -> {
  System.out.println("通知已被用户打开。");
});

// 同样，监听点击事件
notification.onClick(event -> {
  System.out.println("通知被点击。");
});
```

:::warning 点击行为
浏览器安全策略防止通知单击事件自动将您的应用程序窗口或选项卡带入焦点。此行为由浏览器强制执行，无法通过编程方式覆盖。如果您的应用要求窗口获取焦点，您需要指示用户在与通知交互后点击应用内的内容。
:::

## 安全性和兼容性注意事项 {#security-and-compatibility-considerations}

使用 **DesktopNotification** 组件时，请记住以下几点：

- **安全上下文：** 您的应用程序必须通过 HTTPS 提供，以确保大多数现代浏览器允许通知。
- **用户手势要求：** 仅在用户触发的操作之后显示通知。仅加载页面不会触发通知。
- **浏览器限制：** 并非所有浏览器以相同的方式处理自定义图标或聚焦行为。例如，自定义图标可能在 Safari 中不起作用，而事件行为可能在其他浏览器中有所不同。
- **权限：** 始终验证您的应用程序是否优雅地检查并请求用户的通知权限。

## 使用最佳实践 {#usage-best-practices}

在您的应用程序中使用 `DesktopNotification` 组件时，请牢记以下最佳实践：

- **告知您的用户：** 让用户了解为什么需要通知以及他们如何从中受益。
- **提供后备选项：** 由于某些浏览器可能限制通知，请考虑其他警告用户的方法（例如应用内消息）。
- **错误处理：** 始终注册错误监听器以优雅地管理通知未能显示的情况。
