---
title: MaskedNumberField
---

<DocChip chip='shadow' />

<DocChip chip='name' label="dwc-masked-numberfield" />

<JavadocLink type="foundation" location="com/webforj/component/field/MaskedNumberField" top='true'/>

The `MaskedNumberField` component in webforJ provides a user-friendly solution for numeric input, making it ideal for financial applications, data entry forms, and measurement tools. It allows for structured numeric input and can be instantiated with or without parameters, supporting an initial value, label, or placeholder text to guide users.

This demo showcases a Tip Calculator that leverages `MaskedNumberField` for intuitive input. One field accepts a formatted bill amount, while the other captures a whole-number tip percentage:

<ComponentDemo 
path='/webforj/maskednumberfield?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/maskednumberfield/MaskedNumberFieldView.java'
height = '150px'
/>

## Supported masks

Masks define how numeric input is displayed and processed in the `MaskedNumberField`. Each character in the mask serves a specific purpose, allowing developers to enforce structured, consistent input formats. The table below outlines the available mask characters and their behaviors:

| Character  | Description                                                                 |
|------------|-----------------------------------------------------------------------------|
| `0`        | Replaced by a digit, 0-9.                                                 |
| `#`        | Suppresses leading zeroes. Fills trailing zeroes or spaces.                |
| `,`        | Adds a grouping character, such as a comma for thousands.                     |
| `.`        | Represents the decimal point.                                              |
| `-`        | Adds a minus sign for negative numbers.                                    |
| `+`        | Adds a plus or minus sign depending on the value.                          |
| `$`        | Adds a dollar sign.                                                       |
| `(`, `)`   | Encloses negative numbers in parentheses.                                  |
| `CR`/`DR`  | Adds "CR" or "DR" for credit or debit representation.                      |
| `*`        | Adds an asterisk to the number.                                            |
| `B`        | Always becomes a space.                                                   |

## Group and decimal separators 

The `MaskedNumberField` provides flexibility for internationalization by supporting custom characters for grouping and decimal separators. These separators define how numbers are formatted for better readability. 

For example, the group separator is used to divide thousands, and the decimal separator indicates the fractional part of a number. 

```java 
field.setGroupCharacter(".");
field.setDecimalCharacter(",");
```

:::tip Default Separators
By default, the component applies characters based on the app's locale.
:::

## Negatable

By default, the `MaskedNumberField` allows negative values. When the negatable property is enabled, users can input numbers with a negative sign at the beginning. However, if negatable is disabled, any attempts to input a negative number are blocked, ensuring only positive values can be entered.

```java
MaskedNumberField field = new MaskedNumberField();
field.setNegateable(false); // Restricts input to positive numbers only
```

<ComponentDemo 
path='/webforj/maskednumnegatable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/maskednumberfield/MaskedNumNegatableView.java'
height = '150px'
/>

## Min and max values

To ensure valid numeric input, the `MaskedNumberField` allows developers to set minimum and maximum values.

The setMin() method defines the lowest acceptable number. If a user enters a value below this limit, the field either rejects it or adjusts it based on the implementation.

```java
MaskedNumberField field = new MaskedNumberField();
field.setMin(10.0); // Sets the minimum value to 10
```

The `setMax()` method defines the largest acceptable value. Inputs exceeding this value are similarly restricted.

```java
MaskedNumberField field = new MaskedNumberField();
field.setMax(100.0); // Sets the maximum value to 100
```

<ComponentDemo 
path='/webforj/maskednumminmax?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/maskednumberfield/MaskedNumMinMaxView.java'
height = '150px'
/>

## Restoring value

The `MaskedNumberField` includes a `restoreValue()` method that allows you to programmatically reset the field’s value to its initial state. This feature is useful in scenarios where users might make unintentional changes or need to quickly revert to the original value for validation or comparison purposes.

To enable the restore feature, you must define the value to be restored using the `setRestoreValue()` method. This ensures that the field knows which value to revert to when the `restoreValue()` method is called. Here's an example:

```java
MaskedNumberField numberField = new MaskedNumberField("Enter Amount", 1500.00);
numberField.setRestoreValue(1500.00); // Sets the restore value
numberField.restoreValue(); // Reverts the field to the specified value
```
<ComponentDemo 
path='/webforj/maskednumrestore?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/maskednumberfield/MaskedNumRestoreView.java'
height = '150px'
/>

## `MaskedNumberFieldSpinner`

For a more interactive input experience, `MaskedNumberFieldSpinner` extends `MaskedNumberField` by introducing spinner controls. These allow users to increment or decrement values using predefined steps, making it ideal for quantity selection, numerical adjustments, or time settings.

The step size can be controlled using setStep(), defining how much the value changes with each interaction.

<ComponentDemo 
path='/webforj/maskednumspinner?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/maskednumberfield/MaskedNumSpinnerView.java'
height = '100px'
/>

:::tip Default Step
By default, the increment is set to 1.0.
:::

## Parts and CSS properties

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').NumberField} />