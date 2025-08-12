---
title: Alert
sidebar_position: 5
_i18n_hash: d6b9cd03da84860fd2768d9633f3b38a
---
<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-alert" />
<DocChip chip='since' label='25.00' />
<JavadocLink type="alert" location="com/webforj/component/alert/Alert" top='true'/>

`Alert` 组件在 webforJ 中为用户提供上下文反馈消息。这是一种多功能的方式，可以在您的应用中显示重要信息、警告或通知。

警报帮助用户关注关键信息，而不会破坏他们的工作流程。它们非常适合系统消息、表单验证反馈或需要清晰可见但不恼人的状态更新。

以下是一个警报组件的示例：

<ComponentDemo 
path='/webforj/alert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertView.java'
height = '100px'
/>

## Dismissing alerts {#dismissing-alerts}

如果您希望用户有机会关闭 `Alert`，可以通过调用 `setClosable()` 方法使其可关闭。

```java 
Alert alert = new Alert("Heads up! This alert can be dismissed.");
closableAlert.setClosable(true);
```
警报通常不仅仅是显示消息—当被关闭时，它们可以触发后续操作。使用 `AlertCloseEvent` 来处理这些情况，并在用户关闭 `Alert` 时作出响应。

<ComponentDemo 
path='/webforj/closablealert?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/ClosableAlertView.java'
height = '100px'
/>

:::tip 可重用的警告组件
关闭警报只是隐藏它—并不会销毁组件，因此您可以在需要时再次使用它。
:::

## Styling {#styling}

### Themes {#themes}

`Alert` 组件支持多种 <JavadocLink type="foundation" location="com/webforj/component/Theme"> 主题 </JavadocLink> 以在视觉上区分不同类型的消息—如成功、错误、警告或信息。这些主题可以通过 `setTheme()` 方法应用，或者直接在构造函数中使用。

<ComponentDemo 
path='/webforj/alertthemes?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertThemesView.java' 
height = '475px'
/>

### Expanses {#expanses}

扩展定义了 `Alert` 组件的视觉大小。您可以使用 `setExpanse()` 方法设置它，或者直接传递给构造函数。可用的选项来自 Expanse 枚举：`XSMALL`、`SMALL`、`MEDIUM`、`LARGE` 和 `XLARGE`。

<ComponentDemo 
path='/webforj/alertexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/alert/AlertExpansesView.java'
height = '400px'
/>

<TableBuilder name="Alert" />
