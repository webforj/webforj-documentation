---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: 07365e349ec9393e79a13969504861bd
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

一个 `Toast` 是一个小的、临时通知，旨在给用户关于操作或事件的反馈。Toast 发送的信息如成功确认、警告或错误，不会中断当前的工作流程，并会在设定的持续时间后自动消失。

<!-- INTRO_END -->

## 基础 {#basics}

webforJ 提供了一种快速简单的方法，可以用一行代码创建 `Toast` 组件，使用 `Toast.show()` 方法，它会创建一个 `Toast` 组件，将其添加到 `Frame` 中并显示。您可以向 `show` 方法传递参数以配置显示的 `Toast`：

```java
Toast.show("操作成功完成！", Theme.SUCCESS);
```

如果您想要对组件进行更细致的控制，也可以使用标准构造函数创建一个 `Toast` 并使用 `open()` 方法来显示它。

```java
Toast toast = new Toast("操作成功完成！", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info 默认行为
与其他组件不同，`Toast` 不需要明确添加到像 `Frame` 的容器中。当您调用 `open()` 方法时，`Toast` 会自动附加到第一个应用 `Frame`。
:::

Toasts 是多功能的，可以提供实时反馈的细微通知。例如：

- **实时反馈** 操作，如表单提交、数据保存或错误。
- **可定制的主题**，用于区分成功、错误、警告或信息性消息。
- **灵活的放置** 选项，以便在屏幕的不同区域显示通知，而不会打断用户的工作流程。

## 持续时间 {#duration}

您可以配置 `Toast` 通知在设定的持续时间后消失，或者在屏幕上保持直至用户关闭，具体取决于您的需求。您可以使用 `setDuration()` 方法自定义持续时间，或者简单地在构造函数或 `show()` 方法中提供持续时间参数。

:::info 默认持续时间
默认情况下，`Toast` 会在 5000 毫秒后自动关闭。
:::

```java
Toast toast = new Toast("示例通知");
toast.setDuration(10000);
toast.open();
```

### 持续通知 {#persistent-toasts}

您可以通过设置负数持续时间来创建持久的 `Toast`。持久的 `Toast` 通知不会自动关闭，这在需要用户进行某些交互或确认的关键警报情况下非常有用。

:::caution
使用持久的 `Toast` 通知时请小心，并确保提供一种方式让用户关闭通知。一旦用户确认或完成任何必要的交互，请使用 `close()` 方法来隐藏 `Toast`。
:::

```java
Toast toast = new Toast("操作成功完成！", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## 放置 {#placement}

使用 webforJ 的 `Toast` 组件，您可以选择通知在屏幕上出现的位置，以符合您的应用设计和可用性要求。默认情况下，`Toast` 通知出现在屏幕底部中间。

您可以使用 `setPlacement` 方法设置 `Toast` 通知的放置位置，使用 `Toast.Placement` 枚举，选择以下值之一：

- **BOTTOM**: 将通知放置在屏幕底部中心。
- **BOTTOM_LEFT**: 将通知放置在屏幕左下角。
- **BOTTOM_RIGHT**: 将通知放置在屏幕右下角。
- **TOP**: 将通知放置在屏幕顶部中心。
- **TOP_LEFT**: 将通知放置在屏幕左上角。
- **TOP_RIGHT**: 将通知放置在屏幕右上角。

这些选项使您能够根据应用的设计和可用性需求控制 `Toast` 通知的放置。

```java
Toast toast = new Toast("示例通知");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo
path='/webforj/toastplacement'
files={['src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java']}
height='600px'
/>

通过自定义 `Toast` 通知的放置，您可以确保用户以适合任何给定应用、屏幕布局和上下文的方式接收信息。

## 堆叠 {#stacking}

`Toast` 组件可以同时显示多个通知，基于它们的放置位置垂直堆叠。较新的通知出现在放置边缘附近，推送较旧的通知远离。这确保用户不会错过重要信息，即使在繁忙的情况下。

## 操作和互动 {#actions-and-interactivity}

虽然 `Toast` 通知默认不需要用户交互，webforJ 允许您添加按钮或其他交互元素，使它们比简单通知更有用。

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

通过添加这种交互性，您可以让用户在不离开当前屏幕的情况下处理任务并执行操作，将 `Toast` 通知转变为一个有价值的交互和参与渠道。

## 样式 {#styling}

您可以像其他 webforJ 组件一样使用主题为 `Toast` 通知进行样式设置，为用户提供关于显示信息类型的有价值的上下文，并在整个应用中创建一致的样式。您可以在创建 `Toast` 时设置主题，或使用 `setTheme()` 方法。

```java
Toast toast = new Toast("示例通知", Theme.INFO);
```

```java
Toast toast = new Toast("示例通知");
toast.setTheme(Theme.INFO);
```

### 自定义主题 {#custom-themes}

除了使用内置主题，您还可以为 `Toast` 通知创建自己的自定义主题。这为用户提供了一种更个性化和品牌化的体验，使您能够完全控制 `Toast` 的整体样式。

要为 `Toast` 添加自定义主题，您可以定义自定义 CSS 变量，以修改组件的外观。以下示例展示了如何使用 webforJ 创建一个具有自定义主题的 `Toast`。

:::info `Toast` 定位
由于 `Toast` 没有位于 DOM 的特定位置，因此您可以使用 CSS 变量来定位它。这些变量使您可以在所有 Toast 通知中轻松应用一致的自定义样式。
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/frontend/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
