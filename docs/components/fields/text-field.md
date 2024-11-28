---
sidebar_position: 1
title: TextField
slug: textfield
description: A single-line input component for entering and editing text data.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
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
path='https://demo.webforj.com/webapp/controlsamples/textfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/fields/textfield/TextFieldView.java'
/>

## Placeholder text

You can set placeholder text for the `TextField` using the `setPlaceholder()` method. The placeholder text is displayed when the field is empty, helping to prompt the user to enter appropriate input into the `TextField`.

## Selected text

It's possible to interact with the `TextField` class to retrieve a user's selected text, and to get information about the user's selection. You can retrieve the selected text in the `TextField` using the `getSelectedText()` method. This behavior would commonly be used in conjunction with an event. Similarly, it's possible to retrieve the current selection range of the `TextField` using the `getSelectionRange()` method. This returns a `SelectionRange` object representing the start and end indices of the selected text.

## Best practices

The following section outlines some suggested best practices for utilization of the `TextField`.

- **Provide Clear Labels and Instructions**: Label the `TextField` clearly to indicate the type of information users should enter. Provide additional instructional text or placeholder values to guide users and set expectations for the required input.

- **Spell Checking and Auto-Complete**: When applicable, consider utilizing spellchecking with `setSpellCheck()` and/or using autocomplete in a `TextField`. These features can help users input information faster and reduce errors by providing suggested values based on previous inputs or predefined options.

- **Accessibility**: Utilize the `TextField` component with accessibility in mind, adhering to accessibility standards such as proper labeling, keyboard navigation support, and compatibility with assistive technologies. Ensure that users with disabilities can interact with the `TextField` effectively.