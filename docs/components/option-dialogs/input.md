---
sidebar_position: 4
title: Input
---

# Input Dialog

<DocChip chip='shadow' />

<JavadocLink type="foundation" location="com/webforj/component/optiondialog/InputDialog" top='true'/>

An `InputDialog` is a modal dialog designed to prompt the user for input. The dialog blocks app execution until the user provides the input or closes the dialog.


<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/inputdialogbasic?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogBasicView.java'
height = '500px'
/>

## Usages

The `InputDialog` provides a way to request input from users, such as text, numbers, or other data, ensuring that they provide necessary information before proceeding.

## Types

### Input types

The `InputDialog` supports different types of input fields, allowing you to tailor the input method to your specific needs:

1. **TEXT**: A standard single-line text input.
2. **PASSWORD**: A password input field that hides the user's input.
3. **NUMBER**: A numeric input field.
4. **EMAIL**: An input field for email addresses.
5. **URL**: An input field for URLs.
6. **SEARCH**: A search text input field.
7. **DATE**: An input field for selecting dates.
8. **TIME**: An input field for selecting time.
9. **DATETIME_LOCAL**: An input field for selecting local date and time.
10. **COLOR**: An input field for selecting a color.

### Message type

The `InputDialog` supports the following message types. When you configures a type, The dialog displays an icon beside the message, and the dialog's theme updates according to the webforJ design system rules.

1. `PLAIN`: Displays the message without an icon, using the default theme.
2. `ERROR`: Displays an error icon next to the message with the error theme applied.
3. `QUESTION`: Displays a question mark icon beside the message, using the primary theme.
4. `WARNING`: Displays a warning icon next to the message with the warning theme applied.
5. `INFO`: Displays an info icon beside the message, using the info theme.

In the following sample, The user is prompted to enter its password to access the app. If login fails, the user will prompted
again.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/inputdialogtype?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/optiondialog/input/InputDialogTypeView.java'
height = '350px'
/>

## Result

The `InputDialog` returns the user's input as a string. If the user closes the dialog without providing input, the result will be `null`.

:::important
The resulting string will be returned from the `show()` method, or the equivalent `OptionDialog` method as shown below. 
:::

```java showLineNumbers
String result = OptionDialog.showInputDialog(
    "Please enter your age:", "Age Input", "", InputDialog.InputType.NUMBER);

if (result != null) {
    OptionDialog.showMessageDialog("You entered: " + result, "Input Received", "OK");
} else {
    OptionDialog.showMessageDialog("No input received", "Input Canceled", "OK");
}
```

## Default value

The `InputDialog` allows you to specify a default value that appears in the input field when the dialog is displayed. This can provide users with a suggestion or a previously entered value.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Please enter your name:", "Name Input", "John Doe", InputDialog.InputType.TEXT);
String result = dialog.show();
```

## Timeout

The `InputDialog` allows you to set a timeout duration after which the dialog automatically closes. This feature is useful for non-critical input requests or actions that don't require the user's immediate interaction.

You can configure the timeout for the dialog using the `setTimeout(int timeout)` method. The timeout duration is in seconds. If the specified time elapses without any user interaction, the dialog closes automatically.

```java showLineNumbers
InputDialog dialog = new InputDialog(
    "Please enter your name:", "Name Input", "John Doe");
dialog.setTimeout(5);
String result = dialog.show();

OptionDialog.showMessageDialog(
        "You entered: " + result, "Input Received", "OK", MessageDialog.MessageType.INFO);
```

## Best practices

1. **Clear and Concise Prompts**: Ensure the prompt message clearly explains what information the user is being asked to provide.
2. **Appropriate Input Types**: Choose input types that match the required data to ensure accurate and relevant user input.
3. **Logical Default Values**: Set default values that provide useful suggestions or previous entries to streamline user input.
5. **Judicious Use of Timeout**: Set timeouts for non-critical input requests, ensuring users have enough time to provide the required information.
6. **Minimize Overuse**: Use input dialogs sparingly to avoid user frustration. Reserve them for actions requiring specific user input.