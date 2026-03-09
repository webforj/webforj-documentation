---
title: Message
sidebar_position: 30
_i18n_hash: 1925f377637c75ea99d29272f31258ff
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

一个 `MessageDialog` 是一个模态对话框，旨在向用户显示消息，并带有一个 `OK` 按钮用于关闭对话框。它在用户与之交互或因超时关闭之前，阻止应用程序的执行。

<!-- INTRO_END -->

## 用法 {#usages}

使用静态的 `showMessageDialog` 方法来显示基本消息。

```java
OptionDialog.showMessageDialog("Hello World!");
```

要对对话框的外观和行为进行更精确的控制，可以直接创建一个 `MessageDialog` 实例。

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## 消息类型 {#message-type}

`MessageDialog` 支持以下消息类型。当您配置类型时，对话框在消息旁边显示一个图标，并且对话框的主题根据 webforJ 设计系统规则进行更新。

1. `PLAIN`：在不显示图标的情况下显示消息，使用默认主题。
2. `ERROR`：在消息旁边显示错误图标，应用错误主题。
3. `QUESTION`：在消息旁边显示问号图标，使用主要主题。
4. `WARNING`：在消息旁边显示警告图标，应用警告主题。
5. `INFO`：在消息旁边显示信息图标，使用信息主题。

在下面的示例中，代码配置了一个类型为 `WARNING` 的消息对话框，具有自定义标题和消息。

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip 对话框和按钮主题
默认情况下，对话框根据消息类型确定主题。您可以使用 `setTheme(Theme theme)` 方法自定义对话框的主题，并使用 `setButtonTheme(ButtonTheme theme)` 方法独立调整按钮主题，以创建不同的变体。
:::

## 按钮文本 {#button-text}

您可以使用 `setButtonText(String text)` 配置对话框按钮的文本。

```java
OptionDialog.showMessageDialog("Hello World!", "Title", "Got it");
```

## HTML 处理 {#html-processing}

默认情况下，消息对话框处理并渲染 HTML 内容。您可以通过配置它显示原始文本来关闭此功能。

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## 超时 {#timeout}

`MessageDialog` 允许您设置超时持续时间，在此之后对话框会自动关闭。此功能对于不需要用户立即互动的非关键通知或信息非常有用。

您可以使用 `setTimeout(int timeout)` 方法为对话框配置超时。超时时间单位为秒。如果指定时间内没有用户互动，则对话框会自动关闭。

```java showLineNumbers
MessageDialog dialog = new MessageDialog("此对话框将很快超时", "超时");
dialog.setTimeout(2);
dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简洁的消息**：保持消息简短明了，避免使用技术术语；使用用户友好的语言。
2. **适当的消息类型**：
   - 对于关键问题使用 `ERROR`。
   - 对于警告通知使用 `WARNING`。
   - 对于一般信息使用 `INFO`。
3. **一致的主题**：确保对话框和按钮的主题与您的应用程序设计一致。
4. **谨慎使用超时**：为非关键通知设置超时，并确保用户有足够的时间阅读消息。
5. **避免过度使用**：慎用对话框，以防止用户沮丧，并保留用于需要用户行动或确认的重要消息。
