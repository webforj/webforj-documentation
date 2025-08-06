---
title: Toast
sidebar_position: 140
_i18n_hash: 7350867dde3a34f2c5fe2e40c4d745c4
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` 通知是一种微妙而不打扰的弹出通知，旨在为用户提供实时反馈和信息。这些通知通常用于告知用户有关操作的信息，如成功的操作、警告或错误，而不会中断他们的工作流程。`Toast` 通知通常在设定的时间后消失，并且不需要用户的回应。

借助 webforJ 的 `Toast` 组件，您可以轻松实现这些通知，通过提供相关信息，以熟悉、不干扰且无缝的方式增强用户体验。

## 基础 {#basics}

webforJ 提供了一种快速简便的方式通过 `Toast.show()` 方法在一行代码中创建 `Toast` 组件，该方法创建一个 `Toast` 组件，将其添加到 `Frame` 中并展示。您可以向 `show` 方法传递参数以配置显示的 `Toast`：

```java
Toast.show("操作成功完成！", Theme.SUCCESS);
```

如果您希望对组件进行更精细的控制，您还可以使用标准构造函数创建一个 `Toast` 并使用 `open()` 方法显示它。

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
与其他组件不同，`Toast` 不需要显式添加到像 `Frame` 这样的容器中。当您调用 `open()` 方法时，`Toast` 会自动附加到第一个应用 `Frame`。
:::

Toast 是多功能的，并提供微妙的通知以获得实时反馈。例如：

- **实时反馈** 用于表单提交、数据保存或错误等操作。
- **可定制的主题** 用于区分成功、错误、警告或信息消息。
- **灵活的放置** 选项可在不打断用户工作流程的情况下，在屏幕的不同区域显示通知。

## 持续时间 {#duration}

您可以配置 `Toast` 通知在设定的持续时间后消失或在屏幕上持续显示直到被解除，具体取决于您的需求。您可以使用 `setDuration()` 方法自定义持续时间，或简单地向构造函数或 `show()` 方法传递一个持续时间参数。

:::info 默认持续时间
默认情况下，`Toast` 会在 5000 毫秒后自动关闭。
:::

```java
Toast toast = new Toast("示例通知");
toast.setDuration(10000);
toast.open();
```

### 持久性 Toasts {#persistent-toasts}

您可以通过设置负持续时间来创建一个持久性 `Toast`。持久性 `Toast` 通知不会自动关闭，这对于关键警报或需要用户某些交互或确认的情况很有用。

:::caution
注意持久性 `Toast` 通知，并确保提供一个方式让用户可以解除通知。一旦用户确认了通知或完成任何所需的交互，请使用 `close()` 方法隐藏 `Toast`。
:::

```java
Toast toast = new Toast("操作成功完成！", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## 放置 {#placement}

通过 webforJ 的 `Toast` 组件，您可以选择通知在屏幕上的出现位置，以满足您的应用设计和可用性要求。默认情况下，`Toast` 通知出现在屏幕的底部中央。

您可以使用 `setPlacement` 方法设置 Toast 通知的 `placement`，使用 `Toast.Placement` 枚举值之一：

- **BOTTOM**: 将通知放置在屏幕底部中央。
- **BOTTOM_LEFT**: 将通知放置在屏幕左下角。
- **BOTTOM_RIGHT**: 将通知放置在屏幕右下角。
- **TOP**: 将通知放置在屏幕顶部中央。
- **TOP_LEFT**: 将通知放置在屏幕左上角。
- **TOP_RIGHT**: 将通知放置在屏幕右上角。

这些选项允许您根据应用设计和可用性需求来控制 `Toast` 通知的放置。

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

通过自定义 `Toast` 通知的放置，您可以确保用户以适合特定应用、屏幕布局和上下文的方式接收信息。

## 堆叠 {#stacking}

`Toast` 组件可以同时显示多个通知，根据其放置垂直堆叠。更新的通知会出现在放置边缘更近的地方，推动较旧的通知更远。这样可以确保用户不会错过重要信息，即使在有很多活动的情况下。

## 操作和互动 {#actions-and-interactivity}

尽管 `Toast` 通知默认不需要用户交互，但 webforJ 允许您添加按钮或其他互动元素，使其比简单的通知更有用。

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

通过添加这种交互性，您可以让用户能够处理任务并执行操作，而无需离开当前屏幕，使 `Toast` 通知成为一个宝贵的互动和参与渠道。

## 样式 {#styling}

您可以像其他 webforJ 组件一样使用主题来样式化 `Toast` 通知，为用户提供有关所显示信息类型的有价值背景，并在您的应用中创建一致的样式。您可以在创建 Toast 时设置主题，或使用 `setTheme()` 方法。

```java
Toast toast = new Toast("示例通知", Theme.INFO);
```

```java
Toast toast = new Toast("示例通知");
toast.setTheme(Theme.INFO);
```

### 自定义主题 {#custom-themes}

除了使用内置主题外，您还可以为 `Toast` 通知创建自己的自定义主题。这为提供更个性化和品牌的用户体验提供了可能，让您对 `Toast` 的整体样式有充分的控制。

要向 `Toast` 添加自定义主题，您可以定义自定义 CSS 变量，这些变量修改组件的外观。以下示例演示如何使用 webforJ 创建具有自定义主题的 `Toast`。

:::info `Toast` 定位
由于 `Toast` 不位于 DOM 中的特定位置，您可以使用 CSS 变量对其进行定位。这些变量使得在所有 Toast 通知中应用一致的自定义样式变得简单。
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
