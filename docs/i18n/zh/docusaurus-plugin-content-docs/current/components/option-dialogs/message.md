---
sidebar_position: 30
title: Message
_i18n_hash: 633e8c1297144da8b39cfd7ca2e77e5c
---
# 消息对话框

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

`MessageDialog` 是一种模态对话框，旨在向用户显示消息，并提供一个 `OK` 按钮来关闭对话框。在用户与之交互或由于超时关闭之前，它会阻塞应用程序的执行。

```java
OptionDialog.showMessageDialog("你好，世界！");
```

## 用法 {#usages}

消息对话框提供了一种显示信息警报的方法，例如通知、更新或仅需用户确认的简单消息，而不需要提供任何输入。

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "你好，世界", "你好，世界", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## 消息类型 {#message-type}

`MessageDialog` 支持以下消息类型。当您配置一个类型时，对话框在消息旁边显示图标，并且对话框的主题根据 webforJ 设计系统规则进行更新。

1. `PLAIN`：不显示图标，使用默认主题。
2. `ERROR`：在消息旁边显示错误图标，应用错误主题。
3. `QUESTION`：在消息旁边显示问号图标，使用主主题。
4. `WARNING`：在消息旁边显示警告图标，应用警告主题。
5. `INFO`：在消息旁边显示信息图标，使用信息主题。

在以下示例中，代码配置了类型为 `WARNING` 的消息对话框，并带有自定义标题和消息。

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip 对话框和按钮主题
默认情况下，对话框的主题基于消息类型确定。您可以使用 `setTheme(Theme theme)` 方法自定义对话框的主题，并独立使用 `setButtonTheme(ButtonTheme theme)` 方法调整按钮主题，以创建不同的变体。
:::

## 按钮文本 {#button-text}

您可以使用 `setButtonText(String text)` 配置对话框按钮的文本。

```java
OptionDialog.showMessageDialog("你好，世界！", "标题", "明白了");
```

## HTML 处理 {#html-processing}

默认情况下，消息对话框处理并渲染 HTML 内容。您可以通过配置它以显示原始文本来关闭此功能。

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>你好，世界</b>", "你好，世界", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## 超时 {#timeout}

`MessageDialog` 允许您设置超时持续时间，超过该时间后，对话框将自动关闭。此功能适用于不需要用户立即交互的非关键通知或信息。

您可以使用 `setTimeout(int timeout)` 方法为对话框配置超时。超时时间以秒为单位。如果在指定时间内没有用户交互，对话框将自动关闭。

```java showLineNumbers
MessageDialog dialog = new MessageDialog("此对话框即将超时", "超时");
dialog.setTimeout(2);
dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简洁的消息**：保持消息短小精悍，避免使用技术术语；使用用户友好的语言。
2. **适当的消息类型**：
   - 对于关键问题使用 `ERROR`。
   - 对于警告通知使用 `WARNING`。
   - 对于一般信息使用 `INFO`。
3. **一致的主题**：将对话框和按钮主题与您的应用程序的设计保持一致。
4. **明智使用超时**：对非关键通知设置超时，并确保用户有足够的时间阅读消息。
5. **避免过度使用**：适度使用对话框，以防止用户沮丧，并保留用于需要用户操作或确认的重要消息。
