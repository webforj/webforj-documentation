---
title: DesktopNotification
sidebar_position: 29
_i18n_hash: b7e4651594dee824d6bcdf1ac32e1998
---
<DocChip chip='since' label='25.00' />
<DocChip chip='experimental' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

`DesktopNotification` 组件显示浏览器窗口外的原生桌面通知。它可以用来提醒用户有关实时事件，如新消息、系统警报或状态变化，尤其是在用户使用您的应用时。

<!-- INTRO_END -->

## 设置和前提条件 {#setup-and-prerequisites}

<ExperimentalWarning />

要开始使用此功能，请在您的 pom.xml 中包含以下依赖关系：

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```

在集成 `DesktopNotification` 组件之前，请确保：

- 您的应用在 **安全上下文** （HTTPS）下运行。
- 浏览器不处于隐身模式或私人浏览模式。
- 用户已与应用进行交互（例如，点击了一下按钮或按下了一个键），因为通知需要用户的操作才能显示。
- 用户已授予通知权限（如果需要，将自动请求该权限）。

## 基本用法 {#basic-usage}

创建和显示通知有多种方法。在大多数情况下，最简单的方法是调用一个静态 `show` 方法，该方法封装了完整的通知生命周期。

### 示例：显示基本通知 {#example-displaying-a-basic-notification}

```java
// 带有标题和消息的基本通知
DesktopNotification.show("更新可用", "您的下载已完成!");
```

这行代码创建了一个具有标题和主体的通知，然后尝试显示它。

## 自定义通知 {#customizing-the-notification}

根据应用的需求和通知的目的，有多种选项可以自定义显示的通知的外观和感觉。

### 设置自定义 `Icon` {#setting-a-custom-icon}

默认情况下，通知使用您定义的应用图标，通过 [icons 协议](../managing-resources/assets-protocols#the-icons-protocol)。您可以使用 `setIcon` 方法设置自定义图标。该组件支持不同的 URL 协议：

- [`context://`](../managing-resources/assets-protocols#the-context-protocol)：解析为指向应用资源文件夹的上下文 URL；图像是 base64 编码的。
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol)：解析为 web 服务器 URL，给出一个完全合格的 URL。
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

`DesktopNotification` 支持多个生命周期事件，并且可以附加监听器以处理事件，例如，当通知被显示、关闭、点击或遇到错误时。

| 事件                  | 描述                                           | 何时使用                                               |
|-----------------------------|---------------------------------------------------|----------------------------------------------------------|
| **打开** | 当通知显示时触发。       | 记录通知显示，更新用户界面，跟踪互动。    |
| **关闭**| 当通知关闭时触发。         | 清理资源，记录关闭，执行后续操作。|
| **错误**| 当通知发生错误或用户未授予权限时触发。| 优雅处理错误，通知用户，应用后备方案。  |
| **点击**| 当用户点击通知时触发。 | 导航到特定部分，记录互动，重新聚焦应用。 |


```java
DesktopNotification notification = new DesktopNotification("警报", "您有新的消息!")

// 监听打开事件
notification.onOpen(event -> {
  System.out.println("通知已被用户打开。");
});

// 同样，监听点击事件
notification.onClick(event -> {
  System.out.println("通知被点击。");
});
```

:::warning 点击行为
浏览器安全策略会阻止通知点击事件自动使您的应用窗口或标签获得焦点。此行为由浏览器执行，无法通过编程方式覆盖。如果您的应用需要窗口获得焦点，您需要指示用户在与通知交互后点击应用内的内容。
:::

## 安全性和兼容性考虑 {#security-and-compatibility-considerations}

使用 **DesktopNotification** 组件时，请记住以下几点：

- **安全上下文：** 您的应用必须通过 HTTPS 提供，以确保大多数现代浏览器允许通知。
- **用户操作要求：** 仅在用户触发的操作后显示通知。简单加载页面不会触发通知。
- **浏览器限制：** 并非所有浏览器以相同的方式处理自定义图标或聚焦行为。例如，自定义图标可能在 Safari 中不起作用，而事件行为在其他浏览器中可能有所不同。
- **权限：** 始终确认您的应用优雅地检查和请求用户的通知权限。

## 使用最佳实践 {#usage-best-practices}

在应用中使用 `DesktopNotification` 组件时，请牢记以下最佳实践：

- **告知用户：** 让用户知道为何需要通知以及他们如何从中受益。
- **提供后备方案：** 由于某些浏览器可能会限制通知，请考虑替代方式来提醒用户（例如，应用内消息）。
- **错误处理：** 始终注册错误监听器，以优雅地处理通知无法显示的情况。
