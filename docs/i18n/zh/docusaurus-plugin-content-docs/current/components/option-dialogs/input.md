---
title: Input Dialog
sidebar_position: 25
_i18n_hash: 1dbd6d7664b01a9c3282ff4f3df65ea8
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

`InputDialog` 是一个模式对话框，旨在提示用户输入。该对话框阻止应用程序执行，直到用户提供输入或关闭对话框。

<!-- INTRO_END -->

## 用法 {#usages}

`InputDialog` 提示用户输入，例如文本、数字或其他数据。因为对话框是模态的，应用程序在等待用户响应之前不会继续：

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## 类型 {#types}

### 输入类型 {#input-types}

`InputDialog` 支持不同类型的输入字段，让您可以根据具体需要定制输入方式：

1. **TEXT**: 标准单行文本输入。
2. **PASSWORD**: 隐藏用户输入的密码输入字段。
3. **NUMBER**: 数字输入字段。
4. **EMAIL**: 用于电子邮件地址的输入字段。
5. **URL**: 用于网址的输入字段。
6. **SEARCH**: 搜索文本输入字段。
7. **DATE**: 用于选择日期的输入字段。
8. **TIME**: 用于选择时间的输入字段。
9. **DATETIME_LOCAL**: 用于选择本地日期和时间的输入字段。
10. **COLOR**: 用于选择颜色的输入字段。

### 消息类型 {#message-type}

`InputDialog` 支持以下消息类型。当您配置一种类型时，对话框会在消息旁边显示一个图标，并且对话框主题会根据 webforJ 设计系统规则进行更新。

1. `PLAIN`: 显示消息而不带图标，使用默认主题。
2. `ERROR`: 显示错误图标及应用错误主题。
3. `QUESTION`: 在消息旁边显示问号图标，使用主要主题。
4. `WARNING`: 显示警告图标及应用警告主题。
5. `INFO`: 在消息旁边显示信息图标，使用信息主题。

在以下示例中，用户被提示输入密码以访问应用程序。如果登录失败，用户将被再次提示。

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## 结果 {#result}

`InputDialog` 返回用户输入的字符串。如果用户在未提供输入的情况下关闭对话框，结果将为 `null`。

:::important
返回的字符串将通过 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "请输入您的年龄：", "年龄输入", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("您输入的是: " + result, "输入已接收");
} else {
    OptionDialog.showMessageDialog("未接收输入", "输入已取消");
}
```

## 默认值 {#default-value}

`InputDialog` 允许您指定一个默认值，该值在对话框显示时出现在输入字段中。这可以为用户提供建议或先前输入的值。

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "请输入您的名字：", "名字输入", "约翰·多", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## 超时 {#timeout}

`InputDialog` 允许您设置超时期限，超过此期限对话框将自动关闭。此功能对于非关键输入请求或不需要用户立即交互的操作很有用。

您可以使用 `setTimeout(int timeout)` 方法配置对话框的超时时间。超时时间以秒为单位。如果指定的时间在没有用户交互的情况下经过，对话框将自动关闭。

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "请输入您的名字：", "名字输入", "约翰·多");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "您输入的是: " + result, "输入已接收", "确定", MessageDialog.MessageType.INFO);
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**: 确保提示消息清晰地解释用户需要提供什么信息。
2. **适当的输入类型**: 选择与所需数据匹配的输入类型，以确保用户输入的准确性和相关性。
3. **逻辑默认值**: 设置提供有用建议或以前条目的默认值，以简化用户输入。
5. **谨慎使用超时**: 对于非关键输入请求设置超时，确保用户有足够的时间提供所需信息。
6. **减少过度使用**: 适度使用输入对话框，以避免用户挫败感。保留它们用于需要特定用户输入的操作。
