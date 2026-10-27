---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: e0312bf77de08272221f84c9c231c2df
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` 是一种小的临时通知，出现时为用户提供有关某个操作或事件的反馈。Toast 显示信息，如成功确认、警告或错误，而不会干扰当前的工作流程，并在设定的时间后自动消失。

<!-- INTRO_END -->

`Toast.show()` 方法创建一个 `Toast`，将其添加到 `Frame` 中，并用一行代码显示它。通过向 `show()` 传递参数，可以配置出现的 `Toast`：

```java
Toast.show("操作成功完成！", Theme.SUCCESS);
```

如果您希望对组件有更细粒度的控制，也可以使用标准构造函数创建一个 `Toast`，并使用 `open()` 方法显示它。

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
与其他组件不同，`Toast` 不需要显式添加到像 `Frame` 这样的容器中。当您调用 `open()` 方法时，`Toast` 会自动附加到第一个应用 `Frame`。
:::

Toast 是多功能的，为实时反馈提供微妙的通知。例如：

- **实时反馈** 以便于操作，如表单提交、数据保存或错误。
- **可定制的主题** 用以区分成功、错误、警告或信息性消息。
- **灵活的放置** 选项，以便在不干扰用户工作流程的情况下，在屏幕的不同区域显示通知。

## 持续时间 {#duration}

您可以配置 `Toast` 通知在设定的持续时间后消失，或在屏幕上保持直到被关闭，具体取决于您的需求。您可以使用 `setDuration()` 方法来自定义持续时间，或者简单地将持续时间参数提供给构造函数或 `show()` 方法。

:::info 默认持续时间
默认情况下，`Toast` 在 5000 毫秒后自动关闭。
:::

```java
Toast toast = new Toast("示例通知");
toast.setDuration(10000);
toast.open();
```

### 持久通知 {#persistent-toasts}

您可以通过设置负的持续时间来创建一个持久的 `Toast`。持久的 `Toast` 通知不会自动关闭，这在关键警报或需要用户进行某种交互或确认的情况下非常有用。

:::caution
小心使用持久的 `Toast` 通知，并确保为用户提供关闭通知的方法。使用 `close()` 方法在用户确认或完成任何所需交互后隐藏 `Toast`。
:::

```java
Toast toast = new Toast("操作成功完成！", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## 放置 {#placement}

使用 webforJ 的 `Toast` 组件，您可以选择通知在屏幕上的显示位置，以符合您的应用设计和可用性需求。默认情况下，`Toast` 通知出现在屏幕的底部中心。

您可以使用 `setPlacement` 方法设置 `Toast` 通知的放置位置，该方法使用 `Toast.Placement` 枚举值之一：

- **BOTTOM**: 将通知放置在屏幕的底部中心。
- **BOTTOM_LEFT**: 将通知放置在屏幕的左下角。
- **BOTTOM_RIGHT**: 将通知放置在屏幕的右下角。
- **TOP**: 将通知放置在屏幕的顶部中心。
- **TOP_LEFT**: 将通知放置在屏幕的左上角。
- **TOP_RIGHT**: 将通知放置在屏幕的右上角。

这些选项使您可以根据应用的设计和可用性需求控制 `Toast` 通知的放置。

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

`Toast` 组件可以同时显示多个通知，根据其放置方向垂直堆叠。新的通知靠近放置边缘出现，推送旧的通知更远。这确保即使在忙碌时用户也不会错过重要信息。

## 动作和交互性 {#actions-and-interactivity}

尽管 `Toast` 通知默认不需要用户交互，但 webforJ 允许您添加按钮或其他交互元素，使它们比简单的通知更有用。

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

通过添加这种交互性，您可以让用户处理任务并执行操作，而无需离开当前屏幕，将 `Toast` 通知转变为有价值的交互和参与渠道。

## 样式 {#styling}

您可以像其他 webforJ 组件一样使用主题来样式化 `Toast` 通知，为用户提供关于所显示信息类型的有价值上下文，并在整个应用中创建一致的样式。您可以选择在创建 `Toast` 时设置主题，或者使用 `setTheme()` 方法。

```java
Toast toast = new Toast("示例通知", Theme.INFO);
```

```java
Toast toast = new Toast("示例通知");
toast.setTheme(Theme.INFO);
```

### 自定义主题 {#custom-themes}

除了使用内置主题外，您还可以为 `Toast` 通知创建自己的自定义主题。这允许更个性化和品牌化的用户体验，让您对 `Toast` 的整体样式有完全的控制。

要向 `Toast` 添加自定义主题，您可以定义自定义 CSS 变量，这些变量修改组件的外观。以下示例演示了如何使用 webforJ 创建一个具有自定义主题的 `Toast`。

:::info `Toast` 目标
由于 `Toast` 没有位于 DOM 中的特定位置，您可以使用 CSS 变量进行目标定位。这些变量使得在所有 `Toast` 通知中应用一致的自定义样式变得简单。
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
