---
title: Alert
sidebar_position: 5
_i18n_hash: 32072a9b5fdae80b41d77ee1d9742ea5
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert` 组件在 webforJ 中为用户提供上下文反馈消息。这是一种多功能的方式来在您的应用中显示重要信息、警告或通知。

警报帮助吸引注意力到关键的信息，而不会干扰用户的工作流程。它们非常适合系统消息、表单验证反馈或需要明显但不具侵入性的状态更新。

<!-- INTRO_END -->

## 创建警报 {#creating-alerts}

`Alert` 可以容纳丰富的内容，如文本、按钮和其他组件。设置主题以在视觉上区分所显示消息的类型。

<ComponentDemo
path='/webforj/alert'
files={['src/main/java/com/webforj/samples/views/alert/AlertView.java']}
height='110px'
/>

## 关闭警报 {#dismissing-alerts}

如果您希望给用户关闭 `Alert` 的选项，可以通过调用 `setClosable()` 方法使其可关闭。

```java 
Alert alert = new Alert("注意！此警报可以关闭。");
closableAlert.setClosable(true);
```
警报通常不仅仅用于显示消息——它们可以在被关闭时触发后续行动。使用 `AlertCloseEvent` 来处理这些情况并在用户关闭 `Alert` 时做出响应。

<ComponentDemo
path='/webforj/closablealert'
files={['src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java']}
height='100px'
/>

:::tip 可重用的警报组件
关闭警报仅仅是将其隐藏——并不会销毁组件，因此您可以在需要时再用它。
:::


## 样式设置 {#styling}

### 主题 {#themes}

`Alert` 组件支持多种 <JavadocLink type="foundation" location="com/webforj/component/Theme"> 主题 </JavadocLink> 以在视觉上区分不同类型的消息，如成功、错误、警告或信息。这些主题可以通过 `setTheme()` 方法或直接在构造函数中应用。

<ComponentDemo
path='/webforj/alertthemes'
files={['src/main/java/com/webforj/samples/views/alert/AlertThemesView.java']}
height='650px'
/>


### 扩展 {#expanses}

扩展定义了 `Alert` 组件的视觉大小。您可以使用 `setExpanse()` 方法设置，也可以直接传递给构造函数。可用的选项来自 Expanse 枚举：`XSMALL`、`SMALL`、`MEDIUM`、`LARGE` 和 `XLARGE`。

<ComponentDemo
path='/webforj/alertexpanses'
files={['src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java']}
height='600px'
/>

<TableBuilder name="Alert" />
