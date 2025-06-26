---
title: MaskedNumberField
sidebar_position: 10
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-numberfield" />
<DocChip chip='since' label='24.10' />
<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

The `MaskedNumberField` is a text input designed for structured numeric entry. It ensures numbers are formatted consistently based on a defined mask, making it especially useful for financial forms, pricing fields, or any input where precision and readability matter.

This component supports number formatting, localization of decimal/grouping characters, and optional value constraints like minimums or maximums.

## Basics

The `MaskedNumberField` can be instantiated with or without parameters. It supports setting an initial value, a label, a placeholder, and an event listener to react to value changes.

This demo showcases a **Tip Calculator** that uses `MaskedNumberField` for intuitive numeric input. One field is configured to accept a formatted bill amount, while the other captures a whole-number tip percentage. Both fields apply numeric masks to ensure consistent and predictable formatting.

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumberFieldView.java'
height = '270px'
/>

## Mask rules

The `MaskedNumberField` uses a mask string to control how numeric input is formatted and displayed. 
Each character in the mask defines a specific formatting behavior, allowing precise control over how numbers appear.

### Mask characters

| Character | Description |
|-----------|-------------|
| `0`       | Always replaced by a digit (0–9). |
| `#`       | Suppresses leading zeroes. Replaced by the fill character to the left of the decimal point. For trailing digits, replaced by a space or zero. Otherwise, replaced by a digit. |
| `,`       | Used as a grouping separator (e.g. thousands). Replaced by the fill character if no digits precede it. Otherwise, shown as a comma. |
| `-`       | Displays a minus sign (`-`) if the number is negative. Replaced by the fill character if positive. |
| `+`       | Displays `+` for positive or `-` for negative numbers. |
| `$`       | Always results in a dollar sign. |
| `(`       | Inserts a left parenthesis `(` for negative values. Replaced by the fill character if positive. |
| `)`       | Inserts a right parenthesis `)` for negative values. Replaced by the fill character if positive. |
| `CR`      | Displays `CR` for negative numbers. Displays two spaces if the number is positive. |
| `DR`      | Displays `CR` for negative numbers. Displays `DR` for positive numbers. |
| `*`       | Inserts an asterisk `*`. |
| `.`       | Marks the decimal point. If no digits appear in the output, replaced by the fill character. After the decimal, fill characters are treated as spaces. |
| `B`       | Always becomes a space. Any other literal character is shown as-is. |

Some of the above characters can appear more than once in the mask for formatting. These include `-`, `+`, `$`, and
`(`. If any of these characters is present in the mask, the first one encountered will be moved
to the last position where a `#` or `,` was replaced by the fill character. If no such position
exists, the Double character is left where it is.

:::info No Automatic Rounding
A mask within a field does **NOT** round. For example, when placing a value such as `12.34567`
into a field that is masked with `###0.00`, you'll get `12.34`.
:::

## Group and decimal separators

The `MaskedNumberField` supports customization of **grouping** and **decimal** characters, making it easy to adapt number formatting to different locales or business conventions.

- The **group separator** is used to visually separate thousands (e.g. `1,000,000`).
- The **decimal separator** indicates the fractional part of a number (e.g. `123.45`).

This is useful in international applications where different regions use different characters (e.g. `.` vs `,`).

```java
field.setGroupCharacter(".");   // e.g. 1.000.000
field.setDecimalCharacter(","); // e.g. 123,45
```

:::tip Default Behavior
By default, `MaskedNumberField` applies group and decimal separators based on the app's current locale. You can override them at any time using the provided setters.
:::

## Negateable

The `MaskedNumberField` supports an option to control whether negative numbers are allowed.

By default, negative values like` -123.45` are allowed. To prevent this, use `setNegateable(false)` to restrict input to positive values only.

This is useful in business scenarios where values like quantities, totals, or percentages must always be non-negative.

```java
field.setNegateable(false);
```

When `negatable` is set to `false`, the field blocks any attempts to enter a minus sign or otherwise input negative values.

<ComponentDemo 
path='/webforj/maskednumnegatable/?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Min and max values

The `MaskedNumberField` supports setting numeric boundaries using `setMin()` and `setMax()`. 
These constraints help ensure that user input stays within a valid, expected range.

- **Minimum Value**  
  Use `setMin()` to define the lowest acceptable number:

  ```java
  field.setMin(10.0); // Minimum value: 10
  ```

  If the user enters a number below this threshold, it will be considered invalid.

- **Maximum Value**  
  Use `setMax()` to define the highest acceptable number:

  ```java
  field.setMax(100.0); // Maximum value: 100
  ```

  Values above this limit will be flagged as invalid.

## Restoring the value

The `MaskedNumberField` supports a restore feature that resets the field’s value to a predefined state. 
This can be useful when users need to undo changes, revert accidental edits, or return to a known default value.

To enable this behavior, define the target value using `setRestoreValue()`. 
When needed, the field can be reset programmatically using `restoreValue()`.

```java
numberField.setRestoreValue(1500.00);
numberField.restoreValue();
```

### Ways to restore the value

- **Programmatically** using `restoreValue()`
- **Via keyboard**, by pressing <kbd>ESC</kbd> (this is the default restore key unless overridden)

The restore value must be explicitly set. If not defined, the feature will not revert the field.

<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner`

The `MaskedNumberFieldSpinner` extends [`MaskedNumberField`](#basics) by adding spinner controls that let users increase or decrease the value using step buttons or arrow keys. 
This is ideal for inputs like quantities, pricing adjustments, rating controls, or any scenario where users make incremental changes.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/maskednumberfield/MaskedNumSpinnerView.java'
height = '120px'
/>

### Key features

- **Step Increments**  
  Use `setStep()` to define how much the value should change with each spin:

  ```java
  spinner.setStep(5.0); // Each spin adds or subtracts 5
  ```

- **Interactive Controls**  
  Users can click spinner buttons or use keyboard input to adjust the value.

- **All Features from MaskedNumberField**  
  Fully supports masks, formatting, grouping/decimal characters, min/max constraints, and restore logic.

## Styling

<TableBuilder name="MaskedNumberField" />