---
title: Toast
sidebar_position: 140
_i18n_hash: 2027a7fa9671b2b8eb47a3f173ca6f41
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` 通知是一种微妙且不引人注目的弹出通知，旨在为用户提供实时反馈和信息。这些通知通常用于告知用户操作结果，例如成功的操作、警告或错误，而不会中断他们的工作流程。`Toast` 通知通常在设定的时间后消失，并且不需要用户响应。

借助 webforJ 的 `Toast` 组件，您可以轻松实现这些通知，以便以熟悉、不打扰用户的方式提供相关信息，从而增强用户体验。

## Basics {#basics}

webforJ 提供了一种快速便捷的方法，通过 `Toast.show()` 方法在一行代码中创建 `Toast` 组件，该方法会创建一个 `Toast` 组件，添加到 `Frame` 中并显示它。您可以将参数传递给 `show` 方法来配置显示的 `Toast`：

```java
Toast.show("操作成功完成！", Theme.SUCCESS);
```

如果您希望对组件有更精细的控制，您也可以使用标准构造函数创建 `Toast`，并使用 `open()` 方法显示它。

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

Toast 是多功能的，可以提供实时反馈的微妙通知。例如：

- **实时反馈**，用于表单提交、数据保存或错误等操作。
- **可定制的主题**，用于区分成功、错误、警告或信息消息。
- **灵活的放置** 选项，可以在屏幕的不同区域显示通知，而不会干扰用户的工作流程。

## Duration {#duration}

您可以配置 `Toast` 通知，使其在设定的持续时间后消失，或在屏幕上持续存在，直到被关闭，具体取决于您的需求。您可以使用 `setDuration()` 方法自定义持续时间，或者简单地将持续时间参数提供给构造函数或 `show()` 方法。

:::info 默认持续时间
默认情况下，`Toast` 在 5000 毫秒后自动关闭。
:::

```java
Toast toast = new Toast("示例通知");
toast.setDuration(10000);
toast.open();
```

### Persistent toasts {#persistent-toasts}

您可以通过设置负持续时间来创建持久的 `Toast`。持久的 `Toast` 通知不会自动关闭，这在需要用户进行某种互动或确认的情况下可能很有用。

:::caution
请小心使用持久的 `Toast` 通知，确保为用户提供一种方法以关闭通知。在用户确认后或完成任何必要的互动时，请使用 `close()` 方法隐藏 `Toast`。
:::

```java
Toast toast = new Toast("操作成功完成！", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Placement {#placement}

借助 webforJ 的 `Toast` 组件，您可以选择通知在屏幕上的出现位置，以适应您的应用设计和可用性需求。默认情况下，`Toast` 通知出现在屏幕的底部中心。

您可以使用 `setPlacement` 方法设置 `Toast` 通知的 `placement`，使用 `Toast.Placement` 枚举以及以下值之一：

- **BOTTOM**: 将通知放置在屏幕底部中心。
- **BOTTOM_LEFT**: 将通知放置在屏幕左下角。
- **BOTTOM_RIGHT**: 将通知放置在屏幕右下角。
- **TOP**: 将通知放置在屏幕顶部中心。
- **TOP_LEFT**: 将通知放置在屏幕左上角。
- **TOP_RIGHT**: 将通知放置在屏幕右上角。

这些选项允许您根据应用的设计和可用性需求来控制 `Toast` 通知的位置。

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

通过自定义 `Toast` 通知的位置，您可以确保用户以适合任何给定应用、屏幕布局和上下文的方式接收信息。

## Stacking {#stacking}

`Toast` 组件可以同时显示多个通知，根据其位置垂直堆叠。新的通知会出现在放置边缘附近，推迟旧的通知，从而确保用户不会错过重要信息，即使在忙碌的情况下。

## Actions and Interactivity {#actions-and-interactivity}

虽然 `Toast` 通知默认情况下不需要用户互动，但 webforJ 允许您添加按钮或其他交互元素，使其比简单的通知更有用。

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

通过添加这种类型的互动，您可以让用户能够处理任务并执行操作，而无需离开当前屏幕，从而将 `Toast` 通知转变为有价值的互动和参与渠道。

## Styling {#styling}

您可以像其他 webforJ 组件一样使用主题样式来修饰 `Toast` 通知，为用户提供关于所显示信息类型的有价值的上下文，并在整个应用中创建一致的样式。您可以在创建 `Toast` 时设置主题或使用 `setTheme()` 方法。

```java
Toast toast = new Toast("示例通知", Theme.INFO);
```

```java
Toast toast = new Toast("示例通知");
toast.setTheme(Theme.INFO);
```

### Custom themes {#custom-themes}

除了使用内置主题外，您还可以为 `Toast` 通知创建自定义主题。这可以让您提供更个性化和品牌化的用户体验，使您完全控制 `Toast` 的整体样式。

要为 `Toast` 添加自定义主题，您可以定义自定义 CSS 变量，这些变量会修改组件的外观。以下示例演示了如何使用 webforJ 创建带有自定义主题的 `Toast`。

:::info `Toast` 定位
由于 `Toast` 不位于 DOM 中的特定位置，因此您可以使用 CSS 变量对其进行定位。这些变量便于在所有 `Toast` 通知中应用一致的自定义样式。
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
