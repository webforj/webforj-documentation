---
sidebar_position: 3
title: Confirm
---

# Confirm Dialog

<DocChip chip='shadow' />

<JavadocLink type="ConfirmDialog" location="com/webforj/component/optiondialog/ConfirmDialog" top='true'/>

A `ConfirmDialog` is a modal dialog designed to allow the user to choose one of a set of up to 3 options. The dialog blocks app execution until the user interacts with it or it closes due to a timeout.

```java
ConfirmDialog.Result result = OptionDialog.showConfirmDialog(
    "Do you confirm?",
    "Confirmation",
    ConfirmDialog.OptionType.OK_CANCEL,
    ConfirmDialog.MessageType.QUESTION);
```

## Usages

The `ConfirmDialog` provides a way to ask users for confirmation or to choose between multiple options, such as `Yes/No` or `OK/Cancel`, ensuring that they acknowledge and confirm their actions.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/confirmdialogconstructor?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogConstructorView.java'
height = '350px'
/>

## Types

### Option type

The `ConfirmDialog` supports the following option types, which determine the buttons displayed in the dialog:

1. **`OK`**: Displays an `OK` button.
2. **`OK_CANCEL`**: Displays `OK` and `Cancel` buttons.
3. **`ABORT_RETRY_IGNORE`**: Displays `Abort`, `Retry`, and `Ignore` buttons.
4. **`YES_NO_CANCEL`**: Displays `Yes`, `No`, and `Cancel` buttons.
5. **`YES_NO`**: Displays `Yes` and `No` buttons.
6. **`RETRY_CANCEL`**: Displays `Retry` and `Cancel` buttons.
7. **`CUSTOM`**: Displays custom buttons as specified.

### Message type

The `ConfirmDialog` supports the following message types. When you configures a type, The dialog displays an icon beside the message, and the dialog's theme updates according to the webforJ design system rules.

1. `PLAIN`: Displays the message without an icon, using the default theme.
2. `ERROR`: Displays an error icon next to the message with the error theme applied.
3. `QUESTION`: Displays a question mark icon beside the message, using the primary theme.
4. `WARNING`: Displays a warning icon next to the message with the warning theme applied.
5. `INFO`: Displays an info icon beside the message, using the info theme.

In the following sample, the code configures a confirm dialog of type `CUSTOM` with a custom title and message.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/confirmdialogoptions?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/confirm/ConfirmDialogOptionsView.java'
height = '350px'
/>

## Result

The `ConfirmDialog` returns a result based on the user's interaction with the dialog. This result indicates which button the user clicked or if the dialog was dismissed due to a timeout.

:::important
The result will be returned from the `show()` method, or the equivalent `OptionDialog` method as shown below. 
:::

The `ConfirmDialog.Result` enum includes the following possible results:

1. **`OK`**: The user clicked the `OK` button.
2. **`CANCEL`**: The user clicked the `CANCEL` button.
3. **`YES`**: The user clicked the `YES` button.
4. **`NO`**: The user clicked the `NO` button.
5. **`ABORT`**: The user clicked the `ABORT` button.
6. **`RETRY`**: The user clicked the `RETRY` button.
7. **`IGNORE`**: The user clicked the `IGNORE` button.
8. **`FIRST_CUSTOM_BUTTON`**: The user clicked the first custom button
9. **`SECOND_CUSTOM_BUTTON`**: The user clicked the second custom button
10. **`THIRD_CUSTOM_BUTTON`**: The user clicked the third custom button
11. **`TIMEOUT`**: The dialog timeouts.
12. **`UNKNOWN`**: An unknown result, typically used as a default or error state.

```java showLineNumbers
if (result == ConfirmDialog.Result.FIRST_CUSTOM_BUTTON) {
    OptionDialog.showMessageDialog("Changes discarded", "Discarded", "Got it");
} else {
    OptionDialog.showMessageDialog(
        "Changes saved", "Saved", "Got it", MessageDialog.MessageType.INFO);
}
```

## Default button

The `ConfirmDialog` allows you to specify a default button that is preselected when the dialog is displayed. This enhances the user experience by providing a suggested action that can be quickly confirmed by pressing the <kbd>Enter</kbd> key.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Are you sure?", "Confirm", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND); // second button
dialog.show();
```

## Buttons text

You can configure the text of the buttons using the `setButtonText(ConfirmDialog.Button button, String text)` method.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Are you sure?", "Confirm", ConfirmDialog.OptionType.CUSTOM);
dialog.setButtonText(ConfirmDialog.Button.FIRST, "Absolutely");
dialog.setButtonText(ConfirmDialog.Button.SECOND, "Nope");
dialog.show();
```

## HTML processing

By default, the confirm dialog processes and renders HTML content. You can turn off this feature by configuring it to display raw text instead.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "<b>Are you sure?</b>", "Confirm",
    ConfirmDialog.OptionType.YES_NO, ConfirmDialog.MessageType.QUESTION);
dialog.setRawText(true);
dialog.show();
```

## Timeout

The `ConfirmDialog` allows you to set a timeout duration after which the dialog automatically closes. This feature is useful for non-critical confirmations or actions that don't require the user's immediate interaction.

You can configure the timeout for the dialog using the `setTimeout(int timeout)` method. The timeout duration is in seconds. If the specified time elapses without any user interaction, the dialog closes automatically.

```java showLineNumbers
ConfirmDialog dialog = new ConfirmDialog(
    "Are you sure?", "Confirm", ConfirmDialog.OptionType.YES_NO);
dialog.setDefaultButton(Button.SECOND);
dialog.setTimeout(3);
ConfirmDialog.Result result = dialog.show();

switch (result) {
  case TIMEOUT:
    OptionDialog.showMessageDialog(
        "You took too long to decide", "Timeout", "Got it",
        MessageDialog.MessageType.WARNING);
    break;
  case YES:
    OptionDialog.showMessageDialog(
        "You clicked Yes", "Yes", "Got it",
        MessageDialog.MessageType.INFO);
    break;
  default:
    OptionDialog.showMessageDialog(
        "You clicked No", "No", "Got it",
        MessageDialog.MessageType.INFO);
    break;
}
```

## Best practices

1. **Clear and Concise Prompts**: Ensure the prompt message clearly explains what action the user is being asked to confirm. Avoid ambiguity.
2. **Appropriate Option Types**: Choose option types that match the context of the action. For simple yes/no decisions, use straightforward options. For more complex scenarios, provide additional buttons like "Cancel" to allow users to back out without making a choice.
3. **Logical Default Button**: Set a default button that aligns with the most likely or recommended user action to streamline decision-making.
4. **Consistent Theming**: Align the dialog and button themes with your app's design for a cohesive user experience.
5. **Judicious Use of Timeout**: Set timeouts for non-critical confirmations, ensuring users have enough time to read and understand the prompt.
6. **Minimize Overuse**: Use confirm dialogs sparingly to avoid user frustration. Reserve them for critical actions requiring explicit user confirmation.