---
title: Toast
sidebar_position: 140
_i18n_hash: 563743d9f91aff0002f8965cbf719d99
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

`Toast` 是一种小型、临时的通知，旨在向用户反馈某个操作或事件。Toast 会在不打断当前工作流程的情况下显示成功确认、警告或错误等消息，并在设定的持续时间后自动消失。

<!-- INTRO_END -->

## 基础 {#basics}

webforJ 提供了一种快速简便的方法，通过 `Toast.show()` 方法在一行代码中创建 `Toast` 组件，该方法创建一个 `Toast` 组件，将其添加到 `Frame` 中并显示。您可以向 `show` 方法传递参数以配置所显示的 `Toast`：

```java
Toast.show("操作成功完成！", Theme.SUCCESS);
```

如果您希望更精细地控制组件，您还可以使用标准构造函数创建 `Toast`，并使用 `open()` 方法将其显示。

```java
Toast toast = new Toast("操作成功完成！", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo
path='/webforj/toast'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastView.java',
  'src/main/resources/static/css/toast/toastTheme.css',
]}
height='200px'
/>

:::info 默认行为
与其他组件不同，`Toast` 不需要显式添加到像 `Frame` 这样的容器中。当您调用 `open()` 方法时，`Toast` 会自动附加到第一个应用 `Frame`。
:::

Toast 是多功能的，提供实时反馈的微妙通知。例如：

- **实时反馈** 例如表单提交、数据保存或错误的操作。
- **可自定义的主题** 用于区分成功、错误、警告或信息消息。
- **灵活的放置** 选项可以在屏幕的不同区域显示通知，而不打断用户的工作流程。

## 持续时间 {#duration}

您可以配置 `Toast` 通知在设定的持续时间后消失，或者保持在屏幕上直到用户关闭，具体取决于您的需求。您可以使用 `setDuration()` 方法自定义持续时间，或简单地将持续时间参数传递给构造函数或 `show()` 方法。

:::info 默认持续时间
默认情况下，`Toast` 会在 5000 毫秒后自动关闭。
:::

```java
Toast toast = new Toast("示例通知");
toast.setDuration(10000);
toast.open();
```

### 持久性 Toast {#persistent-toasts}

您可以通过设置负持续时间来创建一个持久的 `Toast`。持久的 `Toast` 通知不会自动关闭，这在需要用户进行某些交互或确认的关键警报中非常有用。

:::caution
注意持久性 `Toast` 通知，并确保提供一种方式使用户能够关闭通知。使用 `close()` 方法在用户确认或完成任何所需交互后隐藏 `Toast`。
:::

```java
Toast toast = new Toast("操作成功完成！", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## 放置 {#placement}

使用 webforJ 的 `Toast` 组件，您可以选择通知在屏幕上出现的位置，以满足您应用的设计和可用性需求。默认情况下，`Toast` 通知位于屏幕底部中心。

您可以使用 `setPlacement` 方法设置 `Toast` 通知的放置，使用 `Toast.Placement` 枚举之一，值如下：

- **BOTTOM**: 将通知放置在屏幕底部中心。
- **BOTTOM_LEFT**: 将通知放置在屏幕左下角。
- **BOTTOM_RIGHT**: 将通知放置在屏幕右下角。
- **TOP**: 将通知放置在屏幕顶部中心。
- **TOP_LEFT**: 将通知放置在屏幕左上角。
- **TOP_RIGHT**: 将通知放置在屏幕右上角。

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

通过自定义 `Toast` 通知的位置，您可以确保用户以适合特定应用、屏幕布局和上下文的方式接收信息。

## 堆叠 {#stacking}

`Toast` 组件可以同时显示多个通知，纵向堆叠，根据它们的放置进行排列。较新的通知会更靠近放置边缘，推送较旧的通知更远。这确保用户不会错过重要信息，即使有很多事件发生。

## 操作和交互性 {#actions-and-interactivity}

虽然默认情况下 `Toast` 通知不需要用户交互，但 webforJ 允许您添加按钮或其他交互元素，使它们比简单通知更有用。

<ComponentDemo
path='/webforj/toastcookies'
files={['src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java']}
height='350px'
/>

通过添加这种交互，您可以让用户在不离开当前屏幕的情况下处理任务并执行操作，将 `Toast` 通知转变为一个有价值的交互和参与渠道。

## 样式 {#styling}

您可以像其他 webforJ 组件一样使用主题来样式化 `Toast` 通知，为用户提供有关所显示信息类型的有价值上下文，并创建整个应用的一致样式。您可以在创建 `Toast` 时设置主题，也可以使用 `setTheme()` 方法。

```java
Toast toast = new Toast("示例通知", Theme.INFO);
```

```java
Toast toast = new Toast("示例通知");
toast.setTheme(Theme.INFO);
```

### 自定义主题 {#custom-themes}

除了使用内置主题外，您还可以为 `Toast` 通知创建自己的自定义主题。这使用户体验更加个性化和品牌化，使您能够完全控制 `Toast` 的整体样式。

要为 `Toast` 添加自定义主题，您可以定义自定义 CSS 变量，修改组件的外观。以下示例演示了如何使用 webforJ 创建具有自定义主题的 `Toast`。

:::info `Toast` 定位
由于 `Toast` 不位于 DOM 中的特定位置，您可以使用 CSS 变量来定位它。这些变量使您能够轻松地在所有 Toast 通知中应用一致的自定义样式。
:::

<ComponentDemo
path='/webforj/toasttheme'
files={[
  'src/main/java/com/webforj/samples/views/toast/ToastThemeView.java',
  'src/main/resources/static/css/toast/toastTheme.css',
]}
height='200px'
/>

<TableBuilder name="Toast" />
