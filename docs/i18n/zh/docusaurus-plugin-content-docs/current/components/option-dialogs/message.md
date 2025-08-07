---
sidebar_position: 30
title: Message
_i18n_hash: 575bdfd5364ffdbd911ac0ebe0628359
---
# 消息对话框

<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

`MessageDialog`是一个模态对话框，旨在向用户显示消息，并提供一个`OK`按钮以关闭对话框。在用户与其交互或因超时关闭之前，它会阻止应用程序执行。

```java
OptionDialog.showMessageDialog("Hello World!");
```

## 用法 {#usages}

消息对话框提供了一种显示信息警报的方法，例如通知、更新或仅需要用户确认而不提供任何输入的简单消息。

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## 消息类型 {#message-type}

`MessageDialog`支持以下消息类型。当你配置一种类型时，对话框会在消息旁边显示一个图标，并且对话框的主题会根据webforJ设计系统规则更新。

1. `PLAIN`: 无图标地显示消息，使用默认主题。
2. `ERROR`: 在消息旁边显示错误图标，并应用错误主题。
3. `QUESTION`: 在消息旁边显示问号图标，使用主要主题。
4. `WARNING`: 在消息旁边显示警告图标，并应用警告主题。
5. `INFO`: 在消息旁边显示信息图标，使用信息主题。

在以下示例中，代码配置了一个类型为`WARNING`的消息对话框，具有自定义标题和消息。

<ComponentDemo 
path='/webforj/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip 对话框与按钮主题
默认情况下，对话框的主题由消息类型决定。你可以使用`setTheme(Theme theme)`方法自定义对话框的主题，并使用`setButtonTheme(ButtonTheme theme)`方法独立调整按钮主题，以创建不同的变体。
:::

## 按钮文本 {#button-text}

你可以使用`setButtonText(String text)`配置对话框按钮的文本。

```java
OptionDialog.showMessageDialog("Hello World!", "Title", "Got it");
```

## HTML处理 {#html-processing}

默认情况下，消息对话框处理并渲染HTML内容。你可以通过配置使其显示原始文本来关闭此功能。

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## 超时 {#timeout}

`MessageDialog`允许你设置超时时间，在此之后对话框将自动关闭。此功能对非关键通知或无需用户立即交互的信息非常有用。

你可以使用`setTimeout(int timeout)`方法配置对话框的超时。超时时间以秒为单位。如果在指定时间内没有任何用户交互，对话框将自动关闭。

```java showLineNumbers
MessageDialog dialog = new MessageDialog("此对话框即将超时", "超时");
dialog.setTimeout(2);
dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简洁的消息**：保持消息简短明了，避免使用技术术语；使用用户友好的语言。
2. **适当的消息类型**：
   - 对于关键问题使用`ERROR`。
   - 对于警示通知使用`WARNING`。
   - 对于一般信息使用`INFO`。
3. **一致的主题**：使对话框和按钮主题与应用程序的设计一致。
4. **谨慎使用超时**：对非关键通知设置超时，并确保用户有足够的时间阅读消息。
5. **避免过度使用**：适度使用对话框，以防止用户的挫败感，并留给需要用户操作或确认的重要消息。
