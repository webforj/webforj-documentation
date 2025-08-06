---
title: MaskedTextField
sidebar_position: 15
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-textfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

The `MaskedTextField` component aims to deliver a configurable and easily validatable text input. It's well-suited for apps requiring formatted input, such as financial, e-commerce, and healthcare apps.


## Basics {#basics}

The `MaskedTextField` can be instantiated with or without parameters. You can define an initial value, a label, a placeholder text, and a listener in case the value changes.

```java
MaskedTextField field = new MaskedTextField("Account ID");
field.setMask("ZZZZ-0000")
  .setHelperText("Mask: ZZZZ-0000 - for example: SAVE-2025")
```

## Mask rules {#mask-rules}

The `MaskedTextField` formats text input using a mask - a string that defines what characters are allowed at each position. This ensures consistent, structured input for things like phone numbers, postal codes, and ID formats.

### Supported mask characters {#supported-mask-characters}

| Character | Description                                                                                 |
|-----------|---------------------------------------------------------------------------------------------|
| `X`       | Any printable character                                                                     |
| `a`       | Any alphabetic character (uppercase or lowercase)                                           |
| `A`       | Any alphabetic character; lowercase letters are converted to uppercase                      |
| `0`       | Any digit (0–9)                                                                             |
| `z`       | Any digit or letter (uppercase or lowercase)                                                |
| `Z`       | Any digit or letter; lowercase letters are converted to uppercase                           |

All other characters in the mask are treated as literals and must be typed exactly. 
For example, a mask like `XX@XX` requires the user to enter an `@` in the middle.

- **Invalid characters** are silently ignored.
- **Short input** is padded with spaces.
- **Long input** is truncated to fit the mask.

### Examples {#examples}

```java
field.setMask("(000) 000-0000");     // Example: (123) 456-7890
field.setMask("A00 000");            // Example: A1B 2C3 (Canadian postal code)
field.setMask("ZZZZ-0000");          // Example: ABCD-1234
field.setMask("0000-0000-0000-0000");// Example: 1234-5678-9012-3456
```

:::tip Full Input Allowed
If the mask only contains `X`, the field behaves like a standard [`TextField`](../text-field.md), allowing any printable input.
This is useful when you want to reserve the ability to format without applying strict character rules.
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Validation patterns {#validation-patterns}

While masks define the structure of the input, you can combine them with validation patterns to enforce more specific input rules. This adds an extra layer of client-side validation using regular expressions.

Use the `setPattern()` method to apply a custom regular expression:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Enforces a 10-character alphanumeric code
```

This ensures that input not only matches the mask but also conforms to a defined structure, such as length or allowed characters.

This is especially useful when:

- The mask allows too much flexibility
- You want to enforce exact length or a specific format (e.g. hex, Base64, UUID)

:::tip Regular Expression Format
The pattern must be a valid [JavaScript regular expression](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions), as used by the `RegExp` type. You can find more details in the [HTML pattern attribute documentation](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restoring the value {#restoring-the-value}

The `MaskedTextField` includes a restore feature that resets the field’s value to a predefined or original state. 
This can be useful for undoing user changes or reverting to a default input.

```java
field.setRestoreValue("ABC123");
field.restoreValue();
```

### Ways to restore the value {#ways-to-restore-the-value}

- **Programmatically**, by calling `restoreValue()`
- **Via keyboard**, by pressing <kbd>ESC</kbd> (this is the default restore key unless overridden by an event listener)

You can set the value to restore with `setRestoreValue()`. If no restore value is set, the field will revert to the initial value at the time it was rendered.

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner` {#maskedtextfieldspinner}

The `MaskedTextFieldSpinner` extends [`MaskedTextField`](#basics) by adding spinner controls that let users cycle through a list of predefined values. 
This improves the user experience in situations where the input should be constrained to a fixed set of valid options.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='120px'
/>

### Key features {#key-features}

- **Option List Support**  
  Populate the spinner with a list of valid string values using `setOptions()`:

  ```java
  spinner.setOptions(List.of("Option A", "Option B", "Option C"));
  ```

- **Programmatic Spinning**  
  Use `spinUp()` and `spinDown()` to move through options:

  ```java
  spinner.spinUp();   // Selects the next option
  spinner.spinDown(); // Selects the previous option
  ```

- **Index Control**  
  Set or retrieve the current selection index with:

  ```java
  spinner.setOptionIndex(1);
  int current = spinner.getOptionIndex();
  ```

- **Mask Compatibility**  
  Fully inherits all formatting, mask rules, and pattern validation from `MaskedTextField`.

## Styling {#styling}

<TableBuilder name="MaskedTextField" />