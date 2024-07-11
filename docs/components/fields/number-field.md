---
sidebar_position: 4
title: NumberField
slug: numberfield
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-field" clickable={false} iconName='code'/>

<JavadocLink type="foundation" location="com/webforj/component/field/NumberField" top='true' />

:::success **Important**
The `NumberField` class is a Field component, and as such shares all of the commonalities belonging to a Field. Please refer to the **[Field documentation page](/docs/components/fields)** for an overview of Field properties, events, and other important information.
:::

The `NumberField` component can be used to accept numerical input from the user. It ensures that only valid numeric values are entered and provides a convenient interface for inputting numbers.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.fielddemos.NumberFieldDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/fielddemos/NumberFieldDemo.java'
javaC=''
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/fieldstyles/date_field_styles.css'
/>

### Usages

The `NumberField` is best used in scenarios where capturing or manipulating numerical data is crucial to the user interface or application functionality. Here are some examples of when to use the `NumberField`:

1. **Numeric Input Forms**: When designing forms that require numeric inputs, using a `NumberField` simplifies the input process for users. This is particularly useful for applications that collect user data or require numerical values.

2. **Data Analysis and Calculations**: A `NumberField` will be particularly valuable in applications that involve data analysis, calculations, or mathematical operations. They enable users to input or manipulate numeric values accurately.

3. **Financial and Budgeting Applications**: Applications that involve financial calculations, budgeting, or tracking expenses often require precise numeric inputs. A `NumberField` ensures accurate entry of financial figures.

4. **Measurement and Unit Conversion**: In applications that deal with measurements or unit conversions, the `NumberField` is ideal for inputting numerical values with units such as length, weight, or volume.

### Constructors

The `NumberField` class has three constructors:

1. `NumberField(String label, Double value)`: Creates a `NumberField` with a given label and value.
2. `NumberField(String label)`: Creates a `NumberField` with a given label but with no pre-populated numerical value.
3. `NumberField()`: Creates a `NumberField` without any provided information.

### Maximum and Minimum

You can use the `setMax()` and `setMin()` methods to specify the acceptable time range. If the value entered into the component is outside of the specified value, the component fails constraint validation. Also, if there is already a maximum or minimum set, the value given to the other method must be lower or higher respectively.

### Granularity

You can use the `setStep` method to specify the granularity that the value must adhere to when using arrow keys to modify the value. This will increment or decrement the component's value by a certain step each time. This does not apply when a user enters a value directly, but only when interacting with the `NumberField` using the arrow keys.

### Placeholder Text

You can set placeholder text for the `NumberField` using the `setPlaceholder` method. The placeholder text is displayed when the field is empty, helping to prompt the user to enter appropriate input into the `NumberField`.

:::tip
If the numeric input relates to a specific unit of measurement or has a particular context, provide clear labeling or additional information to guide users and ensure accurate input.
:::

### Best Practices

To ensure a seamless integration and optimal user experience, consider the following best practices when using the `NumberField`:

1. **Consider Accessibility**: Utilize the NumberField component with accessibility in mind, adhering to accessibility standards such as proper labeling, keyboard navigation support, and compatibility with assistive technologies. Ensure that users with disabilities can interact with the NumberField effectively.

2. **Utilize Increment/Decrement Buttons**: If appropriate for your application, consider utilizing increment and decrement buttons with the NumberField. This allows users to adjust the numeric value by a specific increment or decrement with a single click.

3. **Handle Minimum and Maximum Values**: If your application requires limiting the numeric input range, incorporate validation and visual cues to indicate the acceptable minimum and maximum values. Prevent users from entering values outside the defined range.