---
title: Message
sidebar_position: 30
_i18n_hash: 4540b0f4317acc598d4970d0f16ae757
---
<DocChip chip='shadow' />
<DocChip chip='since' label='24.02' />
<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

一个 `MessageDialog` 是一个模态对话框，用于向用户显示消息，并带有一个 `OK` 按钮以关闭对话框。在用户与其交互或因超时关闭之前，它会阻止应用程序执行。

<!-- INTRO_END -->

## 用法 {#usages}

使用静态 `showMessageDialog` 方法来显示基本消息。

```java
OptionDialog.showMessageDialog("Hello World!");
```

为了更好地控制对话框的外观和行为，可以直接创建 `MessageDialog` 实例。

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## 消息类型 {#message-type}

`MessageDialog` 支持以下消息类型。当您配置一种类型时，对话框会在消息旁边显示图标，并且对话框的主题会根据 webforJ 设计系统规则进行更新。

1. `PLAIN`：以默认主题显示消息，不带图标。
2. `ERROR`：在消息旁边显示错误图标，并应用错误主题。
3. `QUESTION`：在消息旁边显示问号图标，使用主主题。
4. `WARNING`：在消息旁边显示警告图标，并应用警告主题。
5. `INFO`：在消息旁边显示信息图标，使用信息主题。

在以下示例中，代码配置了一种类型为 `WARNING` 的消息对话框，带有自定义标题和消息。

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

默认情况下，消息对话框处理和渲染 HTML 内容。您可以通过配置它以显示原始文本来关闭此功能。

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
  "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## 超时 {#timeout}

`MessageDialog` 允许您设置超时持续时间，超时后对话框将自动关闭。这个功能对于非关键通知或不需要用户立即交互的信息非常有用。

您可以使用 `setTimeout(int timeout)` 方法为对话框配置超时。超时持续时间以秒为单位。如果在指定的时间内没有任何用户交互，对话框将自动关闭。

```java showLineNumbers
MessageDialog dialog = new MessageDialog("This dialog will timeout soon", "Timeout");
dialog.setTimeout(2);
dialog.show();
```

## 最佳实践 {#best-practices}

1. **清晰简洁的信息**：保持信息简短明了，避免技术术语；使用用户友好的语言。
2. **适当的消息类型**：
   - 对于关键问题使用 `ERROR`。
   - 对于警告通知使用 `WARNING`。
   - 对于一般信息使用 `INFO`。
3. **一致的主题**：确保对话框和按钮的主题与您的应用设计相一致。
4. **谨慎使用超时**：为非关键通知设置超时，并确保用户有足够的时间阅读消息。
5. **避免过度使用**：适度使用对话框，以防止用户挫败感，并留用于重要消息需要用户操作或确认的场合。
