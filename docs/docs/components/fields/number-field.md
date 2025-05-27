---
sidebar_position: 25
title: NumberField
slug: numberfield
description: A component that provides a default browser-based input field for entering numeric values, with built-in controls for incrementing or decrementing the value.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<DocChip chip='since' label='23.02' />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

You can use the `NumberField` component to accept numerical input from a user. It ensures that only valid numeric values are entered and provides a convenient interface for inputting numbers.

<ComponentDemo 
path='/webforj/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-documentation/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Field value

The `NumberField` component stores its value as a `Double`, allowing accurate handling of both integers and decimal numbers.

### Getting the current value

You can retrieve the numeric value entered by the user using:

```java
Double currentValue = numberField.getValue();
```

### Setting a new value

To set the field programmatically:

```java
numberField.setValue(42.5);
```

If no value has been entered and no default value is set, `getValue()` will return `null`.

:::tip
While the field is designed to accept valid numeric input only, keep in mind that the underlying value is nullable. Always test for null before using the result.
:::

## Usages

The `NumberField` is best used in scenarios where capturing, displaying, or manipulating numerical data is essential to your app. Here are some examples of when to use the `NumberField`:

1. **Numeric Input Forms**: When designing forms that require numeric inputs, using a `NumberField` simplifies the input process for users. This is particularly useful for applications that collect user data or require numerical values.

2. **Data Analysis and Calculations**: A `NumberField` is particularly valuable in apps that involve data analysis, calculations, or mathematical operations. They allow users to input or manipulate numeric values accurately.

3. **Financial and Budgeting Applications**: Apps that involve financial calculations, budgeting, or tracking expenses often require precise numeric inputs. A `NumberField` ensures accurate entry of financial figures.

4. **Measurement and Unit Conversion**: In apps that deal with measurements or unit conversions, the `NumberField` is ideal for inputting numerical values with units such as length, weight, or volume.

## Min and max value

With the `setMin()` method, you can specify the minimum acceptable value in the number field. If a user enters a value lower than this threshold, the component will fail constraint validation and provide appropriate feedback.

```java
NumberField numberField = new NumberField();
numberField.setMin(0.0); // Minimum allowed: 0.0
```

Separately, the `setMax()` method allows you to define the maximum acceptable value. If a user enters a value higher than this limit, the input will be rejected. When both minimum and maximum values are set, the maximum must be greater than or equal to the minimum.

```java
numberField.setMax(100.0); // Maximum allowed: 100.0
```

In this configuration, entering a value like -5 or 150 would be invalid, while values between 0 and 100 are accepted.

## Granularity

You can use the `setStep()` method to specify the granularity that the value must adhere to when using arrow keys to modify the value. This will increment or decrement the component's value by a certain step each time. This doesn't apply when a user enters a value directly, but only when interacting with the `NumberField` using the arrow keys.

## Placeholder text

You can set placeholder text for the `NumberField` using the `setPlaceholder()` method. The placeholder text is displayed when the field is empty, helping to prompt the user to enter appropriate input into the `NumberField`.

:::tip Give clear context for accuracy
If the numeric input relates to a specific unit of measurement or has a particular context, provide clear labeling or additional information to guide users and ensure accurate input.
:::

## Best practices

To ensure a seamless integration and optimal user experience, consider the following best practices when using the `NumberField`:

- **Accessibility**: Utilize the `NumberField` component with accessibility in mind, adhering to accessibility standards such as proper labeling, keyboard navigation support, and compatibility with assistive technologies. Ensure that users with disabilities can interact with the `NumberField` effectively.

- **Utilize Increment/Decrement Buttons**: If appropriate for your app, consider utilizing increment and decrement buttons with the `NumberField`. This allows users to adjust the numeric value by a specific increment or decrement with a single click.