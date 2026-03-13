---
title: Toast
sidebar_position: 140
_i18n_hash: 0ac4df1a045e2706f2e9309327ba4683
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

一个 `Toast` 是一种小型、临时通知，用于给用户提供有关某个操作或事件的反馈。Toast 显示消息，如成功确认、警告或错误，而不会打断当前工作流程，并在设定的时间后自动消失。

<!-- INTRO_END -->

## 基础 {#basics}

webforJ 提供了一种快速简便的方法来创建 `Toast` 组件，只需一行代码，即可使用 `Toast.show()` 方法创建 `Toast` 组件，将其添加到 `Frame` 中并显示。您可以向 `show` 方法传递参数，以配置显示的 `Toast`：

```java
Toast.show("操作成功完成！", Theme.SUCCESS);
```

如果您想进一步控制该组件，还可以使用标准构造函数创建 `Toast` 并使用 `open()` 方法来显示它。

```java
Toast toast = new Toast("操作成功完成！", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
height='200px'
/>

:::info 默认行为
与其他组件不同，`Toast` 不需要显式添加到容器（如 `Frame`）中。当您调用 `open()` 方法时，`Toast` 会自动附加到第一个应用 `Frame`。
:::

Toast 是多功能的，提供实时反馈的细微通知。例如：

- **实时反馈**，用于表单提交、数据保存或错误的操作。
- **可定制主题**，以区分成功、错误、警告或信息性消息。
- **灵活的放置** 选项，能够在屏幕的不同区域显示通知，而不会打断用户的工作流程。

## 持续时间 {#duration}

您可以配置 `Toast` 通知在设定的持续时间后消失，或保持在屏幕上，直到被用户关闭，具体取决于您的需求。您可以使用 `setDuration()` 方法自定义持续时间，或者简单地将持续时间参数传递给构造函数或 `show()` 方法。

:::info 默认持续时间
默认情况下，`Toast` 在 5000 毫秒后自动关闭。
:::

```java
Toast toast = new Toast("示例通知");
toast.setDuration(10000);
toast.open();
```

### 持续的 Toast {#persistent-toasts}

您可以通过设置负持续时间来创建一个持续的 `Toast`。持续的 `Toast` 通知不会自动关闭，这在处理关键警报或需要用户进行某些交互或确认的情况下非常有用。

:::caution
请谨慎使用持续的 `Toast` 通知，并确保提供一种方式让用户关闭通知。在用户确认或完成任何必需的交互后，使用 `close()` 方法来隐藏 `Toast`。
:::

```java
Toast toast = new Toast("操作成功完成！", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## 放置 {#placement}

使用 webforJ 的 `Toast` 组件，您可以选择通知在屏幕上出现的位置，以满足应用的设计和可用性要求。默认情况下，`Toast` 通知出现在屏幕的底部中心。

您可以使用 `setPlacement` 方法设置 `Toast` 通知的 `placement`，通过 `Toast.Placement` 枚举选择以下值之一：

- **BOTTOM**: 将通知放置在屏幕底部中心。
- **BOTTOM_LEFT**: 将通知放置在屏幕左下角。
- **BOTTOM_RIGHT**: 将通知放置在屏幕右下角。
- **TOP**: 将通知放置在屏幕顶部中心。
- **TOP_LEFT**: 将通知放置在屏幕左上角。
- **TOP_RIGHT**: 将通知放置在屏幕右上角。

这些选项允许您根据应用的设计和可用性需求控制 `Toast` 通知的放置。

```java
Toast toast = new Toast("示例通知");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='500px'
/>

通过自定义 `Toast` 通知的放置，您可以确保用户在适当的方式下接收信息，以适应任何给定的应用、屏幕布局和上下文。

## 堆叠 {#stacking}

`Toast` 组件可以同时显示多个通知，根据它们的放置垂直堆叠。较新的通知出现得更靠近放置边缘，推送较旧的通知更远，这确保用户不会在信息繁多的情况下错过重要信息。

## 操作和交互性 {#actions-and-interactivity}

尽管 `Toast` 通知默认情况下不需要用户交互，webforJ 允许您添加按钮或其他交互元素，使其比简单的通知更有用。

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

通过添加这种交互性，您可以使用户能够处理任务和执行操作，而无需离开当前屏幕，从而将 `Toast` 通知转变为一种宝贵的交互和参与渠道。

## 样式 {#styling}

您可以像其他 webforJ 组件一样为 `Toast` 通知提供主题样式，向用户提供有关所显示信息类型的有价值的上下文，并在整个应用中创建一致的样式。您可以在创建 `Toast` 时设置主题或使用 `setTheme()` 方法。

```java
Toast toast = new Toast("示例通知", Theme.INFO);
```

```java
Toast toast = new Toast("示例通知");
toast.setTheme(Theme.INFO);
```

### 自定义主题 {#custom-themes}

除了使用内置主题外，您还可以为 `Toast` 通知创建自己的自定义主题。这允许实现更个性化和品牌化的用户体验，使您可以完全控制 `Toast` 的整体样式。

要向 `Toast` 添加自定义主题，您可以定义自定义 CSS 变量，这些变量修改组件的外观。以下示例演示了如何使用 webforJ 创建一个带有自定义主题的 `Toast`。

:::info `Toast` 目标定位
由于 `Toast` 不在 DOM 中的特定位置，因此您可以使用 CSS 变量对其进行目标定位。这些变量使您可以在所有 Toast 通知之间应用一致的自定义样式。
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
