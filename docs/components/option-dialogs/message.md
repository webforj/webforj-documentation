---
sidebar_position: 2
title: Message
---

# Message Dialog

<DocChip chip='shadow' />

<JavadocLink type="foundation" location="com/webforj/component/optiondialog/MessageDialog" top='true'/>

A `MessageDialog` is a modal dialog designed to display a message to the user with an `OK` button to dismiss the dialog. It blocks app execution until the user interacts with it or it closes due to a timeout.

```java
OptionDialog.showMessageDialog("Hello World!");
```

## Usages

The Message Dialog provides a way to display informational alerts, such as notifications, updates, or simple messages that only require the user to acknowledge them without providing any input.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "Hello World", "Hello World", MessageDialog.MessageType.INFO);
dialog.setBlurred(true);
dialog.setAlignment(MessageDialog.Alignment.TOP);
dialog.show();
```

## Message type

The `MessageDialog` supports the following message types. When you configures a type, The dialog displays an icon beside the message, and the dialog's theme updates according to the webforJ design system rules.

1. `PLAIN`: Displays the message without an icon, using the default theme.
2. `ERROR`: Displays an error icon next to the message with the error theme applied.
3. `QUESTION`: Displays a question mark icon beside the message, using the primary theme.
4. `WARNING`: Displays a warning icon next to the message with the warning theme applied.
5. `INFO`: Displays an info icon beside the message, using the info theme.

In the following sample, The code configures a message dialog of type `WARNING`. with a custom title and message.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/messagedialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/message/MessageDialogTypeView.java'
height = '350px'
/>

:::tip Dialog & Button Theme
By default, the dialog's determines the theme based on the message type. You can customize the dialog's theme using the `setTheme(Theme theme)` method and independently adjust the button theme with the `setButtonTheme(ButtonTheme theme)` method to create different variations.
:::

## Button text

You can configure the dialog button's text using the `setButtonText(String text)`.

```java
OptionDialog.showMessageDialog("Hello World!", "Title", "Got it");
```

## HTML processing

By default, the message dialog processes and renders HTML content. You can turn off this feature by configuring it to display raw text instead.

```java showLineNumbers
MessageDialog dialog = new MessageDialog(
    "<b>Hello World</b>", "Hello World", MessageDialog.MessageType.INFO);
dialog.setRawText(true);
dialog.show();
```

## Timeout

The `MessageDialog` allows you to set a timeout duration after which the dialog automatically closes. This feature is useful for non-critical notifications or information that doesn't require the user's immediate interaction.

You can configure the timeout for the dialog using the `setTimeout(int timeout)` method. The timeout duration is in seconds. If the specified time elapses without any user interaction, the dialog closes automatically.

```java showLineNumbers
MessageDialog dialog = new MessageDialog("Hello World", "Title");
dialog.setTimeout(2);
dialog.show();
```

## Best practices

1. **Clear and Concise Messages**: Keep messages short and to the point and avoid technical jargon; use user-friendly language.
2. **Appropriate Message Types**:
   - Use `ERROR` for critical issues.
   - Use `WARNING` for cautionary notices.
   - Use `INFO` for general information.
3. **Consistent Theming**: Align dialog and button themes with your apps's design.
4. **Judicious Use of Timeout**: Set timeouts for non-critical notifications and ensure users have enough time to read the message.
5. **Avoid Overuse**: Use dialogs sparingly to prevent user frustration and reserve for important messages requiring user action or acknowledgment.