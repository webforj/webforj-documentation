---
title: MaskedTextField
sidebar_position: 18
---

<DocChip chip='shadow' />

<DocChip chip='name' label="dwc-textfield" />

<JavadocLink type="foundation" location="com/webforj/component/field/MaskedTextField" top='true'/>

The `MaskedTextField` component aims to deliver a configurable and easily validatable text input. It's suited well for apps requiring formatted input, such as financial, e-commerce, and healthcare apps.


## Basics

The `MaskedTextField` can be instantiated with or without parameters. You can define an initial value, label, a placeholder text, and a listener in case the value changes.

## Mask rules

The `MaskedTextField` supports various mask patterns to format text input in a structured way. Masking allows for very specific sets of rules to be applied to input. Key supported mask characters include:

| Character  | Description                                                                                  |
|------------|----------------------------------------------------------------------------------------------|
| `X`        | Any printable character.                                                                     |
| `a`        | Any alphabetic character.                                                                    |
| `A`        | Any alphabetic character. Converts lower-case alphabetic characters to upper case.           |
| `o`        | Any digit.                                                                                   |
| `z`        | Any digit or alphabetic character.                                                           |
| `Z`        | Any digit or alphabetic character. Converts lower-case alphabetic characters to upper case.  |

Any characters other than these, when used in a mask, represent themselves. For example, setting the mask to `XX@XX` would expect an `@` in the middle. Input mismatches, for example entering a letter in a digit-only position, are ignored. Input longer than the mask is truncated, and shorter input is padded with spaces.

```java
MaskedTextField field = new MaskedTextField();
field.setMask("(ooo) ooo-oooo"); // Example: (123) 456-7890
field.setMask("Aoo ooo"); // Example: A1B 2C3 (Canadian postal code format)
field.setMask("ZZZZ-oooo"); // Example: ABCD-1234
field.setMask("oooo-oooo-oooo-oooo"); // Example: 1234-5678-9012-3456
```

:::info Unrestricted Input
If a mask consists of only `X` it behaves like a standard [Textfield](./text-field.md).
:::

<ComponentDemo 
path='/webforj/maskedtextfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldView.java'
height='250px'
/>

## Combining masks with patterns

In addition to the above rules for masking you can combine them with patterns to utilize regular expressions instead of just masks.
Regular expressions allow you to create custom patterns for advanced input validation. You can define a custom pattern using the `setPattern()` method. For example:

```java
field.setPattern("[A-Za-z0-9]{10}"); // Enforces a 10-character alphanumeric code
```
This ensures additional flexibility for scenarios requiring complex formatting and validation.

:::info Regular expression conventions
The pattern must be a valid JavaScript regular expression, as used by the RegExp type and as documented in the [regular expressions guide](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions). For more information about patterns see [this guide](https://developer.mozilla.org/en-US/docs/Web/HTML/Attributes/pattern#overview).
:::

## Restore method

The `MaskedTextField` provides a restore feature, allowing users to reset the field’s value to its original state.

It's possible to utilize `setRestoreValue()` to programmatically set what value should be restored instead of the default behaviour using the start value of the field before editing. This feature is particularly useful for scenarios where users may want to undo changes or return to the default input state.

There are two ways to trigger the restore:

- **Programmatically**: Call the `restoreValue()` method to reset the field.
- **Keyboard Interaction**: Press the restore key to revert the value.

:::info Default restore key
The restore key is <kbd>ESC</kbd> by default, unless overwritten specifically with event listeners reacting to keystrokes. 
:::

<ComponentDemo 
path='/webforj/maskedtextfieldrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldRestoreView.java'
height='200px'
/>

## `MaskedTextFieldSpinner`

The `MaskedTextFieldSpinner` extends the `MaskedTextField` by introducing spinner controls, allowing users to spin through a predefined set of options. It's also possible to spin through these options programmatically using either `spinUp()` or `spinDown()`.

<ComponentDemo 
path='/webforj/maskedtextfieldspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskedtextfield/MaskedTextFieldSpinnerView.java'
height='100px'
/>

## Styling

### Shadow parts

These are the various parts of the [shadow DOM](../../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextField} table='parts' exclusions=''/>

### CSS properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextField} exclusions='' table='properties'/>

### Reflected attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').TextField} table="reflects" />
