---
title: Toast
sidebar_position: 140
_i18n_hash: c98ff64ae02ebe46d84c803492685d05
---
<DocChip chip="shadow" />
<DocChip chip="name" label="dwc-toast" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

一个 `Toast` 是一个小型的临时通知，用于向用户提供有关某个操作或事件的反馈。Toast 可以在不打断当前工作流程的情况下，显示如成功确认、警告或错误等消息，并在设定的持续时间后自动消失。

<!-- INTRO_END -->

## 基础 {#basics}

webforJ 提供了一种快速简便的方式，通过 `Toast.show()` 方法用一行代码创建 `Toast` 组件，该方法创建一个 `Toast` 组件，将其添加到 `Frame` 并显示出来。您可以向 `show` 方法传递参数以配置显示的 `Toast`：

```java
Toast.show("操作成功完成！", Theme.SUCCESS);
```

如果您希望更精细地控制组件，您还可以使用标准构造函数创建 `Toast`，并使用 `open()` 方法进行显示。

```java
Toast toast = new Toast("操作成功完成！", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='/webforj/toast?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

:::info 默认行为
与其他组件不同，`Toast` 不需要显式地添加到像 `Frame` 这样的容器中。当您调用 `open()` 方法时，`Toast` 会自动附加到第一个应用 `Frame`。
:::

Toasts 是多功能的，提供实时反馈的细微通知。例如：

- **实时反馈**，适用于表单提交、数据保存或错误等操作。
- **可定制的主题**，用于区分成功、错误、警告或信息消息。
- **灵活的放置** 选项，可以在不同的屏幕区域显示通知，而不会打断用户的工作流程。

## 持续时间 {#duration}

您可以配置 `Toast` 通知在设定时间后消失，或在屏幕上持续停留，直到它们被关闭，这取决于您的需求。您可以使用 `setDuration()` 方法自定义持续时间，或者直接向构造函数或 `show()` 方法提供一个持续时间参数。

:::info 默认持续时间
默认情况下，`Toast` 在 5000 毫秒后自动关闭。
:::

```java
Toast toast = new Toast("示例通知");
toast.setDuration(10000);
toast.open();
```

### 持久的 Toast {#persistent-toasts}

您可以通过设置负持续时间来创建一个持久的 `Toast`。持久的 `Toast` 通知不会自动关闭，这在需要用户进行某些交互或确认的关键警报中非常有用。

:::caution
请注意持久的 `Toast` 通知，并确保为用户提供一种关闭通知的方法。使用 `close()` 方法在用户确认后或完成任何必要的交互后隐藏 `Toast`。
:::

```java
Toast toast = new Toast("操作成功完成！", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## 放置 {#placement}

使用 webforJ 的 `Toast` 组件，您可以选择通知在屏幕上的出现位置，以适应您的应用设计和可用性需求。默认情况下，`Toast` 通知出现在屏幕的底部中心。

您可以使用 `setPlacement` 方法通过 `Toast.Placement` 枚举设置 `Toast` 通知的 `placement`，可选值如下：

- **BOTTOM**：将通知放置在屏幕底部中心。
- **BOTTOM_LEFT**：将通知放置在屏幕左下角。
- **BOTTOM_RIGHT**：将通知放置在屏幕右下角。
- **TOP**：将通知放置在屏幕顶部中心。
- **TOP_LEFT**：将通知放置在屏幕左上角。
- **TOP_RIGHT**：将通知放置在屏幕右上角。

这些选项允许您根据应用的设计和可用性需求控制 `Toast` 通知的放置。

```java
Toast toast = new Toast("示例通知");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='/webforj/toastplacement?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastPlacementView.java'
height='600px'
/>

通过自定义 `Toast` 通知的放置，您可以确保用户以适合任何给定应用、屏幕布局和上下文的方式接收信息。

## 堆叠 {#stacking}

`Toast` 组件可以同时显示多个通知，按照放置位置垂直堆叠。较新的通知更靠近放置边缘，将较旧的通知推得更远。这确保了即使发生了很多事情，用户也不会错过重要的信息。

## 操作与交互 {#actions-and-interactivity}

尽管 `Toast` 通知默认不需要用户交互，webforJ 允许您添加按钮或其他交互元素，以使其更有用，而不仅仅是简单的通知。

<ComponentDemo 
path='/webforj/toastcookies?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastCookiesView.java'
height='350px'
/>

通过添加这种交互，您可以让用户能够在不离开当前屏幕的情况下处理任务和执行操作，从而将 `Toast` 通知转变为一个有价值的互动和参与渠道。

## 样式 {#styling}

您可以像其他 webforJ 组件一样用主题样式化 `Toast` 通知，为用户提供有关所显示信息类型的有价值上下文，并在整个应用中创建一致的样式。您可以在创建 `Toast` 时设置主题，或使用 `setTheme()` 方法。

```java
Toast toast = new Toast("示例通知", Theme.INFO);
```

```java
Toast toast = new Toast("示例通知");
toast.setTheme(Theme.INFO);
```

### 自定义主题 {#custom-themes}

除了使用内置主题外，您还可以为 `Toast` 通知创建自己的自定义主题。这为用户提供了更个性化和品牌化的体验，让您完全控制 `Toast` 的整体样式。

要为 `Toast` 添加自定义主题，您可以定义自定义 CSS 变量，以修改组件的外观。以下示例演示了如何使用 webforJ 创建具有自定义主题的 `Toast`。

:::info `Toast` 定位
由于 `Toast` 不位于 DOM 的特定位置，您可以使用 CSS 变量进行定位。这些变量使得在所有 Toast 通知中应用一致的自定义样式变得简单。
:::

<ComponentDemo 
path='/webforj/toasttheme?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/toast/ToastThemeView.java'
cssURL='/css/toast/toastTheme.css'
height='200px'
/>

<TableBuilder name="Toast" />
