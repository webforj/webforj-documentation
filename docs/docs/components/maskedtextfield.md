---
title: MaskedTextField
---

<DocChip chip='shadow' />

<DocChip chip='name' label="dwc-masked-textfield" />

<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

The `MaskedTextField` component aims to deliver a configurable and easily validatable text input. It's ideal for apps requiring formatted input, such as financial, e-commerce, and healthcare apps.


## Basics

The `MaskedTextField` can be instantiated with or without parameters. You can define an initial value, label, a placeholder text, and a listener in case the value changes.

## Supported masks

The `MaskedTextField` supports various mask patterns to format text input in a structured way. These masks allow for flexibility in defining acceptable input types. Key supported mask characters include:

| Character  | Description                                                                                  |
|------------|----------------------------------------------------------------------------------------------|
| `X`        | Any printable character.                                                                     |
| `a`        | Any alphabetic character.                                                                    |
| `A`        | Any alphabetic character. Converts lower-case alphabetic characters to upper case.           |
| `o`        | Any digit.                                                                                   |
| `z`        | Any digit or alphabetic character.                                                           |
| `Z`        | Any digit or alphabetic character. Converts lower-case alphabetic characters to upper case.  |

Any characters other than these, when used in a mask, represent themselves. For example, setting the mask to "XX@XX" would expect an "@" in the middle. Input mismatches, for example entering a letter in a digit-only position, are ignored. Input longer than the mask is truncated, and shorter input is padded with spaces.



## Combining masks with patterns

In addition to the above rules for masking you can combine them with patterns to utilize regular expressions instead of just masks.
Regular expressions allow you to create custom patterns for advanced input validation. You can define a custom pattern using the setPattern() method. For example:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Enforces a 10-character alphanumeric code
```
This ensures additional flexibility for scenarios requiring complex formatting and validation.

:::info Regular expression conventions
The pattern must be a valid JavaScript regular expression, as used by the RegExp type and as documented in the [regular expressions guide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions). For more information about patterns see [this guide](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restore method

The `MaskedTextField` provides a restore feature, allowing users to reset the field’s value to its original state:

- **Programmatically**: Call the `restoreValue()` method to reset the field.
- **Keyboard Interaction**: Press the restore key to revert the value.

It's possible to utilize `setrestorevalue()` to programmatically set what value should be restored instead of the default behaviour using the start value of the field before editing. This feature is particularly useful for scenarios where users may want to undo changes or return to the default input state.

:::info Default restore key
The restore key is "ESC" by default, unless overwritten specifically with event listeners reacting to keystrokes. 
:::

## `MaskedTextFieldSpinner`

The `MaskedTextFieldSpinner` extends the `MaskedTextField` by introducing spinner controls, allowing users to spin through a predefined set of options. 


