---
title: Alert
sidebar_position: 5
_i18n_hash: b69c428a86cd23667ade00afb734aeec
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert` 组件在 webforJ 中为用户提供上下文反馈消息。这是一种多功能的方式来显示应用中的重要信息、警告或通知。

Alerts 有助于吸引用户的注意力，而不会干扰他们的工作流程。它们非常适合系统消息、表单验证反馈或需要清晰可见但不具侵入性的状态更新。

<!-- INTRO_END -->

## 创建警报 {#creating-alerts}

一个 `Alert` 可以包含丰富的内容，比如文本、按钮和其他组件。设置主题以视觉上区分所显示的消息类型。

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '110px'
/>

## 关闭警报 {#dismissing-alerts}

如果您希望用户可以选择关闭 `Alert`，可以通过调用 `setClosable()` 方法使其可关闭。

```java 
Alert alert = new Alert("注意！这个警报可以被关闭。");
closableAlert.setClosable(true);
```
警报通常不仅仅是显示消息——当关闭时，它们可以触发后续操作。使用 `AlertCloseEvent` 来处理这些情况，并在用户关闭 `Alert` 时做出响应。

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip 可重用警报组件
关闭警报仅仅是隐藏它——并不会销毁组件，因此您可以在需要时重新使用它。
:::

## 样式 {#styling}

### 主题 {#themes}

`Alert` 组件支持多种 <JavadocLink type="foundation" location="com/webforj/component/Theme"> 主题 </JavadocLink> 以视觉上区分不同类型的消息——例如成功、错误、警告或信息。这些主题可以使用 `setTheme()` 方法或直接在构造函数中应用。

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '650px'
/>

### 大小 {#expanses}

大小定义了 `Alert` 组件的视觉尺寸。您可以使用 `setExpanse()` 方法设置，或直接传递给构造函数。可用的选项来自 Expanse 枚举：`XSMALL`、`SMALL`、`MEDIUM`、`LARGE` 和 `XLARGE`。

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '600px'
/>

<TableBuilder name="Alert" />
