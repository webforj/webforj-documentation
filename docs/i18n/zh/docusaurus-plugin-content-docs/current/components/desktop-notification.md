---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: 79d5ddb69e13f8536439346d8d4ee85d
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

`DesktopNotification` 组件在浏览器窗口外显示本地桌面通知。它可用于提醒用户实时事件，例如新消息、系统警报或状态更改，同时他们在使用您的应用程序。

<!-- INTRO_END -->

## 设置和先决条件 {#setup-and-prerequisites}

:::warning 实验性特性
`DesktopNotification` 组件仍在发展中，其 API 可能会随着其成熟而发生变化。要开始使用此功能，请确保在您的 pom.xml 中包含以下依赖项。

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

在集成 `DesktopNotification` 组件之前，请确保：

- 您的应用程序在 **安全上下文**（HTTPS）中运行。
- 浏览器未处于隐身浏览或私密浏览模式。
- 用户已与应用程序进行交互（例如，点击按钮或按下键），因为通知需要用户手势才能显示。
- 用户已授予通知权限（如果需要，将自动请求此权限）。

## 基本用法 {#basic-usage}

有多种方式可以创建和显示通知。在大多数情况下，最简单的方法是调用一个静态 `show` 方法，该方法封装了整个通知生命周期。

### 示例：显示基本通知 {#example-displaying-a-basic-notification}

```java
// 带有标题和消息的基本通知
DesktopNotification.show("更新可用", "您的下载已完成！");
```

这一行代码创建了一个带有标题和正文的通知，然后尝试将其显示。

## 自定义通知 {#customizing-the-notification}

根据应用程序的需要和通知的目的，有多种选项可以自定义显示通知的外观和感觉。

### 设置自定义 `图标` {#setting-a-custom-icon}

默认情况下，通知使用您通过 [icons 协议](../managing-resources/assets-protocols#the-icons-protocol) 定义的应用程序图标。您可以使用 `setIcon` 方法设置自定义图标。该组件支持不同的 URL 方案：

- [`context://`](../managing-resources/assets-protocols#the-context-protocol)：解析为指向应用程序资源文件夹的上下文 URL；图像以 base64 编码。
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol)：解析为 Web 服务器 URL，给出完全合格的 URL。
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol)：解析为图标 URL。

**示例：**

```java
// 创建带有自定义图标 URL 的通知
DesktopNotification notification = new DesktopNotification(
  "提醒", "会议将在 10 分钟后开始。"
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## 通知事件 {#notification-events}

`DesktopNotification` 支持多个生命周期事件，监听器可以附加以处理事件，例如显示、关闭、点击通知或遇到错误时。

| 事件                  | 描述                                           | 何时使用                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **打开** | 当通知显示时触发。       | 记录通知显示，更新 UI，跟踪交互。    |
| **关闭**| 当通知关闭时触发。         | 清理资源，记录解除，执行后续操作。|
| **错误**| 当通知发生错误或用户未授权时触发。| 优雅地处理错误，通知用户，应用后备方案。  |
| **点击**| 当用户点击通知时触发。 | 导航到特定部分，记录交互，重新聚焦应用。 |

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
浏览器安全策略防止通知点击事件自动将您的应用窗口或标签页聚焦。此行为由浏览器强制执行，无法通过编程方式覆盖。如果您的应用需要窗口聚焦，您需要指导用户在与通知交互后在应用中点击。
:::

## 安全性和兼容性考虑 {#security-and-compatibility-considerations}

使用 **DesktopNotification** 组件时，请牢记以下要点：

- **安全上下文**：您的应用必须通过 HTTPS 提供，以确保绝大多数现代浏览器允许通知。
- **用户手势要求**：仅在用户触发的操作之后显示通知。简单加载页面不会触发通知。
- **浏览器限制**：并非所有浏览器以相同方式处理自定义图标或聚焦行为。例如，自定义图标在 Safari 中可能无法正常工作，而在其他浏览器中的事件行为可能会有所不同。
- **权限**：始终确保您的应用优雅地检查并请求用户的通知权限。

## 使用最佳实践 {#usage-best-practices}

使用 `DesktopNotification` 组件时，请牢记以下最佳实践：

- **告知您的用户**：让用户知道为什么需要通知以及他们可以如何从中受益。
- **提供后备方案**：由于某些浏览器可能限制通知，请考虑以其他方式提醒用户（例如，在应用内消息）。
- **错误处理**：始终注册错误监听器，以优雅地处理通知无法显示的场景。
