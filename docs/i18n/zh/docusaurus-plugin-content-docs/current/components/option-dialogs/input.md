---
sidebar_position: 25
title: Input Dialog
_i18n_hash: 60c8f92b63b241996eda4f5a08df8027
---
# 输入对话框

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

`InputDialog` 是一个模态对话框，旨在提示用户输入。对话框阻止应用程序执行，直到用户提供输入或关闭对话框。

<ComponentDemo 
path='/webforj/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## 用法 {#usages}

`InputDialog` 提供了一种请求用户输入的方式，例如文本、数字或其他数据，确保他们在继续之前提供必要的信息。

## 类型 {#types}

### 输入类型 {#input-types}

`InputDialog` 支持不同类型的输入字段，允许您根据特定需求调整输入方式：

1. **文本**：标准单行文本输入。
2. **密码**：隐藏用户输入的密码输入字段。
3. **数字**：数字输入字段。
4. **电子邮件**：电子邮件地址的输入字段。
5. **URL**：URL的输入字段。
6. **搜索**：搜索文本输入字段。
7. **日期**：选择日期的输入字段。
8. **时间**：选择时间的输入字段。
9. **本地日期时间**：选择本地日期和时间的输入字段。
10. **颜色**：选择颜色的输入字段。

### 消息类型 {#message-type}

`InputDialog` 支持以下消息类型。当您配置一个类型时，对话框会在消息旁边显示一个图标，并根据 webforJ 设计系统规则更新对话框的主题。

1. `PLAIN`：不带图标显示消息，使用默认主题。
2. `ERROR`：在消息旁边显示错误图标，并应用错误主题。
3. `QUESTION`：在消息旁边显示问号图标，使用主要主题。
4. `WARNING`：在消息旁边显示警告图标，并应用警告主题。
5. `INFO`：在消息旁边显示信息图标，使用信息主题。

在以下示例中，用户被提示输入密码以访问应用程序。如果登录失败，用户将会再次被提示。

<ComponentDemo 
path='/webforj/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## 结果 {#result}

`InputDialog` 返回用户的输入作为字符串。如果用户在不提供输入的情况下关闭对话框，结果将为 `null`。

:::important
返回的字符串将从 `show()` 方法或等效的 `OptionDialog` 方法返回，如下所示。 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "请输入您的年龄:", "年龄输入", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("您输入了: " + result, "输入已接收");
} else {
    OptionDialog.showMessageDialog("未接收到输入", "输入已取消");
}
```

## 默认值 {#default-value}

`InputDialog` 允许您指定一个默认值，该值在对话框显示时出现在输入字段中。这可以为用户提供建议或之前输入的值。

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "请输入您的姓名:", "姓名输入", "约翰·多", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## 超时 {#timeout}

`InputDialog` 允许您设置超时持续时间，超时后对话框会自动关闭。此功能对于不关键的输入请求或不需要用户立即交互的操作非常有用。

您可以使用 `setTimeout(int timeout)` 方法配置对话框的超时。超时时间以秒为单位。如果在指定时间内没有任何用户交互，对话框将自动关闭。

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "请输入您的姓名:", "姓名输入", "约翰·多");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
    "您输入了: " + result, "输入已接收", "确定", MessageDialog.MessageType.INFO);
```

## 最佳实践 {#best-practices}

1. **清晰简洁的提示**：确保提示消息清楚地解释用户被要求提供什么信息。
2. **适当的输入类型**：选择与所需数据匹配的输入类型，以确保准确和相关的用户输入。
3. **合理的默认值**：设置提供有用建议或以前条目的默认值，以简化用户输入。
4. **谨慎使用超时**：对非关键输入请求设置超时，确保用户有足够时间提供所需信息。
5. **减少过度使用**：谨慎使用输入对话框，避免用户沮丧。将其保留用于需要特定用户输入的操作。
