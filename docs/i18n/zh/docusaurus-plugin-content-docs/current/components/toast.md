---
title: Toast
sidebar_position: 140
description: >-
  Show transient notifications with the Toast component, configuring duration,
  theme, and placement via Toast.show or open.
_i18n_hash: d46665cad03749940ad45e5be8b3991d
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` 是一种小型的临时通知，用于向用户提供有关某个操作或事件的反馈。Toast 显示诸如成功确认、警告或错误的信息，而不会打断当前工作流程，并会在设定的持续时间后自动消失。

<!-- INTRO_END -->

`Toast.show()` 方法创建一个 `Toast`，将其添加到 `Frame` 中，并在一行代码中显示它。传递参数给 `show()` 方法以配置出现的 `Toast`：

```java
Toast.show("操作成功完成！", Theme.SUCCESS);
```

如果您希望更细粒度地控制组件，您还可以使用标准构造函数创建一个 `Toast`，并使用 `open()` 方法显示它。

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
与其他组件不同，`Toast` 不需要显式添加到像 `Frame` 这样的容器中。当您调用 `open()` 方法时，`Toast` 会自动附加到第一个应用程序的 `Frame` 中。
:::

Toast 是多功能的，为实时反馈提供微妙的通知。例如：

- 对于诸如表单提交、数据保存或错误等操作提供 **实时反馈**。
- 提供 **可自定义的主题**，以区分成功、错误、警告或信息消息。
- 提供 **灵活的定位** 选项，以在屏幕的不同区域显示通知，而不会打断用户的工作流程。

## 持续时间 {#duration}

您可以配置 `Toast` 通知在设定时间后消失，或持久保留在屏幕上，直到被关闭，具体取决于您的需求。您可以使用 `setDuration()` 方法自定义持续时间，或者简单地向构造函数或 `show()` 方法提供持续时间参数。

:::info 默认持续时间
默认情况下，`Toast` 在 5000 毫秒后自动关闭。
:::

```java
Toast toast = new Toast("示例通知");
toast.setDuration(10000);
toast.open();
```

### 持续性 Toasts {#persistent-toasts}

您可以通过设置负持续时间来创建一个持久的 `Toast`。持久 `Toast` 通知不会自动关闭，这在关键警报或需要用户进行某些交互或确认的情况下非常有用。

:::caution
请注意持久 `Toast` 通知，并确保提供一种方式让用户关闭通知。使用 `close()` 方法在用户确认或完成任何必需的交互后隐藏 `Toast`。
:::

```java
Toast toast = new Toast("操作成功完成！", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## 定位 {#placement}

通过 webforJ 的 `Toast` 组件，您可以选择通知在屏幕上的显示位置，以满足应用程序的设计和可用性要求。默认情况下，`Toast` 通知显示在屏幕底部中央。

您可以使用 `setPlacement` 方法设置 `Toast` 通知的定位，使用 `Toast.Placement` 枚举和以下值之一：

- **BOTTOM**: 将通知放置在屏幕底部中央。
- **BOTTOM_LEFT**: 将通知放置在屏幕左下角。
- **BOTTOM_RIGHT**: 将通知放置在屏幕右下角。
- **CENTER**: 将通知放置在屏幕中心。
- **TOP**: 将通知放置在屏幕顶部中央。
- **TOP_LEFT**: 将通知放置在屏幕左上角。
- **TOP_RIGHT**: 将通知放置在屏幕右上角。

这些选项允许您根据应用程序的设计和可用性需求控制 `Toast` 通知的定位。

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

通过自定义 `Toast` 通知的位置，您可以确保用户以适合特定应用程序、屏幕布局和上下文的方式接收信息。

## 堆叠 {#stacking}

`Toast` 组件可以同时显示多个通知，基于其位置垂直堆叠。更新的通知会更靠近放置边缘，推送较旧的通知更远。这确保用户不会错过重要信息，即使在发生很多事情时。

## 操作和交互性 {#actions-and-interactivity}

尽管默认情况下 `Toast` 通知不需要用户交互，但 webforJ 允许您添加按钮或其他交互元素，使其比简单通知更有用。

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

通过添加这种交互性，您可以让用户在不离开当前屏幕的情况下处理任务并执行操作，从而将 `Toast` 通知转变为一个有价值的交互和参与渠道。

## 样式 {#styling}

您可以像其他 webforJ 组件一样使用主题来样式化 `Toast` 通知，为用户提供与所显示信息类型相关的有价值的上下文，并在整个应用中创建一致的样式。您可以在创建 `Toast` 时设置主题或使用 `setTheme()` 方法。

```java
Toast toast = new Toast("示例通知", Theme.INFO);
```

```java
Toast toast = new Toast("示例通知");
toast.setTheme(Theme.INFO);
```

### 自定义主题 {#custom-themes}

除了使用内置主题外，您还可以为 `Toast` 通知创建自己的自定义主题。这为用户提供了更个性化和品牌化的体验，使您能够完全控制 `Toast` 的整体样式。

要为 `Toast` 添加自定义主题，您可以定义自定义 CSS 变量，这些变量会修改组件的外观。以下示例展示了如何使用 webforJ 创建一个具有自定义主题的 `Toast`。

:::info `Toast` 定向
由于 `Toast` 不位于 DOM 中的特定位置，您可以使用 CSS 变量对其进行定向。这些变量使在所有 Toast 通知中应用一致的自定义样式变得简单。
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
