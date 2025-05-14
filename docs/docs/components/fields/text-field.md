---
sidebar_position: 35
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/TextField" top='true'/>

<ParentLink parent="Field" />

The `TextField` component allows users to enter and edit text in a single line. You can configure the field to display a specific virtual keyboard, such as a numeric keypad, email input, telephone input, or URL input. The component also provides built-in validation to reject values that don't adhere to the specified type.

## Usages

The `TextField` is suitable for a wide range of scenarios where text input or editing is required. Here are some examples of when to use the `TextField`:

1. **Form Inputs**: A `TextField` is commonly used in forms for capturing user input, such as names, addresses, or comments. It's best to use a `TextField` in an app when the input is generally short enough to fit on one line.

2. **Search Functionality**: `TextField` can be used to provide a search box, allowing users to input search queries.

3. **Text Editing**: A `TextField` is ideal for apps that require text editing or content creation, such as document editors, chat apps, or note-taking apps.

## Types

You can specify the type of the TextField using the `setType()` method. Similarly, you can retrieve the type using the `getType()` method, which will return an enum value.

- `Type.TEXT`: This is the default type for a `TextField` and automatically removes line breaks from the input value

- `Type.EMAIL`: This type is for entering email addresses. It validates the input according to standard email syntax. Additionally, it provides compatible browsers and devices with a dynamic keyboard that simplifies the typing process by including commonly used keys like <kbd>.com</kbd> and <kbd>@</kbd>.

  :::note
  While this validation confirms the email address's format, it doesn't guarantee that the email exists.
  :::

- `Type.TEL`: This type is used for entering a telephone number. The field will display a telephone keypad in some devices with dynamic keypads.

- `Type.URL`: This type is for entering URLs. It validates if a user enters a URL that includes a scheme and a domain name, for example: https://webforj.com. Additionally, it provides compatible browsers and devices with a dynamic keyboard that simplifies the typing process by including commonly used keys like <kbd>:</kbd>, <kbd>/</kbd>, and <kbd>.com</kbd>.

- `Type.SEARCH`: A single-line text field for entering search strings. Line-breaks are automatically removed from the input value.

<ComponentDemo 
path='/webforj/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Placeholder text

You can set placeholder text for the `TextField` using the `setPlaceholder()` method. The placeholder text is displayed when the field is empty, helping to prompt the user to enter appropriate input into the `TextField`.

## Selected text

It's possible to interact with the `TextField` class to retrieve a user's selected text, and to get information about the user's selection. You can retrieve the selected text in the `TextField` using the `getSelectedText()` method. This behavior would commonly be used in conjunction with an event. 

```java
TextField textField = new TextField("Enter something...");
Button getSelectionBtn = new Button("Get Selected Text");

getSelectionBtn.onClick(e -> {
  String selected = textField.getSelectedText();
  System.out.println("Selected text: " + selected);
});
```

Similarly, it's possible to retrieve the current selection range of the `TextField` using the `getSelectionRange()` method. This returns a `SelectionRange` object representing the start and end indices of the selected text.

:::tip Using `getSelectedText()` vs event payload
While you can call `getSelectedText()` manually inside an event handler, it’s more efficient to use the selection data provided in the event’s payload—such as in a `SelectionChangeEvent`—to avoid additional lookups.
:::

## Pattern matching

You can use the `setPattern()` method to define a validation rule for the `TextField` using a regular expression. This enables constraint validation on the input value, requiring it to match the specified pattern.

The pattern must be a valid [JavaScript regular expression](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), as interpreted by the browser. The `u` (Unicode) flag is applied internally to ensure accurate matching of Unicode code points. Don't wrap the pattern in forward slashes (`/`), as those aren't required and will be treated as literal characters.

```java
textField.setPattern("[A-Za-z]{3}\\d{2}"); // e.g. ABC12
```

If no pattern is provided, or the syntax is invalid, the validation rule is ignored.

:::tip
You can use `setTitle()` to specify a tooltip that explains the pattern's requirements to the user. It’s also recommended to include additional hints or guidance next to the input when using complex patterns.
:::

## Minimum and maximum length

The `TextField` component supports constraint validation based on the number of characters entered by the user. This can be controlled using the `setMinLength()` and `setMaxLength()` methods.

### `setMaxLength()`

This method sets the **maximum number of UTF-16 code units** allowed in the text field. The value must be `0` or greater. If not set, or set to an invalid value, no maximum is enforced.

```java
textField.setMaxLength(20); // User cannot enter more than 20 characters
```

The field fails constraint validation if the input length exceeds the `maxLength`. This validation is only triggered when the value is changed by the user.

### `setMinLength()`

This method sets the **minimum number of UTF-16 code units** that must be entered for the field to be considered valid. The value must be a non-negative integer and shouldn't exceed the configured `maxLength`.

```java
textField.setMinLength(5); // User must enter at least 5 characters
```

If the input contains fewer characters than the minimum required, the input will fail constraint validation. Like with `maxLength`, this rule only applies when the user changes the field's value.

:::tip
Use `setMinLength()` and `setMaxLength()` together to enforce consistent length boundaries for user input. Consider pairing them with a `setTitle()` tooltip or inline hint to inform users about length requirements.
:::




## Best practices

The following section outlines some suggested best practices for utilization of the `TextField`.

- **Provide Clear Labels and Instructions**: Label the `TextField` clearly to indicate the type of information users should enter. Provide additional instructional text or placeholder values to guide users and set expectations for the required input.

- **Spell Checking and Auto-Complete**: When applicable, consider utilizing spellchecking with `setSpellCheck()` and/or using autocomplete in a `TextField`. These features can help users input information faster and reduce errors by providing suggested values based on previous inputs or predefined options.

- **Accessibility**: Utilize the `TextField` component with accessibility in mind, adhering to accessibility standards such as proper labeling, keyboard navigation support, and compatibility with assistive technologies. Ensure that users with disabilities can interact with the `TextField` effectively.