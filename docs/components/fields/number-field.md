---
sidebar_position: 3
title: NumberField
slug: numberfield
description: A component that provides a default browser-based input field for entering numeric values, with built-in controls for incrementing or decrementing the value.
---

<DocChip chip='shadow' />
<DocChip chip='name' label="dwc-field" />
<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

<ParentLink parent="Field" />

You can use the `NumberField` component to accept numerical input from a user. It ensures that only valid numeric values are entered and provides a convenient interface for inputting numbers.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/numberfield?'
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/fields/numberfield/NumberFieldView.java'
/>

## Usages

The `NumberField` is best used in scenarios where capturing, displaying, or manipulating numerical data is essential to your app. Here are some examples of when to use the `NumberField`:

1. **Numeric Input Forms**: When designing forms that require numeric inputs, using a `NumberField` simplifies the input process for users. This is particularly useful for applications that collect user data or require numerical values.

2. **Data Analysis and Calculations**: A `NumberField` is particularly valuable in apps that involve data analysis, calculations, or mathematical operations. They allow users to input or manipulate numeric values accurately.

3. **Financial and Budgeting Applications**: Apps that involve financial calculations, budgeting, or tracking expenses often require precise numeric inputs. A `NumberField` ensures accurate entry of financial figures.

4. **Measurement and Unit Conversion**: In apps that deal with measurements or unit conversions, the `NumberField` is ideal for inputting numerical values with units such as length, weight, or volume.

## Maximum and minimum

With the `setMax()` and `setMin()` methods, you can specify a range of acceptable numbers. If a user manually enters a value into the number field outside the specified range, the component will let the user know what's acceptable. Also, if a maximum or minimum is already set, the value for the other method must be lower or higher respectively.

## Granularity

You can use the `setStep()` method to specify the granularity that the value must adhere to when using arrow keys to modify the value. This will increment or decrement the component's value by a certain step each time. This doesn't apply when a user enters a value directly, but only when interacting with the `NumberField` using the arrow keys.

## Placeholder text

You can set placeholder text for the `NumberField` using the `setPlaceholder()` method. The placeholder text is displayed when the field is empty, helping to prompt the user to enter appropriate input into the `NumberField`.

:::tip
If the numeric input relates to a specific unit of measurement or has a particular context, provide clear labeling or additional information to guide users and ensure accurate input.
:::

## Best practices

To ensure a seamless integration and optimal user experience, consider the following best practices when using the `NumberField`:

- **Accessibility**: Utilize the `NumberField` component with accessibility in mind, adhering to accessibility standards such as proper labeling, keyboard navigation support, and compatibility with assistive technologies. Ensure that users with disabilities can interact with the `NumberField` effectively.

- **Utilize Increment/Decrement Buttons**: If appropriate for your app, consider utilizing increment and decrement buttons with the `NumberField`. This allows users to adjust the numeric value by a specific increment or decrement with a single click.