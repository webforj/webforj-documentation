---
title: Alert
sidebar_position: 5
_i18n_hash: e876e23a7ee171611e8747deef02d93c
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert` 组件在 webforJ 中为用户提供上下文反馈消息。这是一种显示重要信息、警告或通知的多功能方式。

警报帮助引起对关键信息的关注，而不会干扰用户的工作流程。他们非常适合系统消息、表单验证反馈或需要清晰可见但不具侵入性的状态更新。

下面是一个警报组件的例子：

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## 关闭警报 {#dismissing-alerts}

如果您希望给用户关闭 `Alert` 的选项，可以通过调用 `setClosable()` 方法使其可关闭。

```java 
Alert alert = new Alert("注意！此警报可以被关闭。");
closableAlert.setClosable(true);
```
警报通常不仅仅是显示消息——当关闭时，它们可以触发后续操作。使用 `AlertCloseEvent` 处理这些情况，响应用户关闭 `Alert`。

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip 可重用的警报组件
关闭警报仅会隐藏它——它不会销毁组件，因此您可以在需要时将其重用。
:::

## 样式 {#styling}

### 主题 {#themes}

`Alert` 组件支持多种 <JavadocLink type="foundation" location="com/webforj/component/Theme"> 主题 </JavadocLink>，用于在视觉上区分不同类型的消息——例如成功、错误、警告或信息。可以使用 `setTheme()` 方法或直接在构造函数中应用这些主题。

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>

### 范围 {#expanses}

范围定义 `Alert` 组件的视觉大小。可以使用 `setExpanse()` 方法设置，或直接传递给构造函数。可用的选项来自 Expanse 枚举：`XSMALL`、`SMALL`、`MEDIUM`、`LARGE` 和 `XLARGE`。

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alert" />
