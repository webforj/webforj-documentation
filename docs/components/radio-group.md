---
title: RadioButtonGroup
slug: radiobuttongroup
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/component/optioninput/RadioButtonGroup" top='true'/>

The `RadioButtonGroup` class is used to group related radio buttons together, which helps establish the mutual exclusivity among the options within that group. Users can select only one radio button within a given radio group. When a user selects a radio button within a group, any previously selected radio button in the same group automatically becomes deselected. This ensures that only one option can be chosen at a time.


<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.radiobuttondemos.RadioButtonGroupDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/radiobuttondemos/RadioButtonGroupDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/radiobuttonstyles/radiobutton_styles.css'
height="200px"
/>

:::important
The `RadioButtonGroup` component will not render an HTML element on the page. Rather, it is only
logic that ensures a group of RadioButtons behave as a group instead of individually.
:::

## Usages

The `RadioButtonGroup` is best used in scenarios where users need to make a single selection from a predefined set of options presented as radio buttons. Here are some examples of when to use the `RadioButtonGroup`:

1. **Survey or Questionnaires**: `RadioButtonGroup` components are commonly used in surveys or questionnaires where users need to select a single response from a list of options.

2. **Preference Settings**: Applications that involve preference or settings panels often use RadioButtonGroup component to allow users to choose a single option from a set of mutually exclusive choices.

3. **Filtering or Sorting**: A `RadioButton` can be used in applications that require users to select a single filter or sorting option, such as sorting a list of items by different criteria.

## Constructors

The `RadioButtonGroup` class has four constructors:

1. `RadioButtonGroup()`: Creates an empty `RadioButtonGroup` in the unchecked state.
2. `RadioButtonGroup(String name)`: Creates a `RadioButtonGroup` with an name.
3. `RadioButtonGroup(RadioButton... buttons)`: Creates a `RadioButtonGroup` with one or more `RadioButton` objects assigned to the group.
4. `RadioButtonGroup(String name, RadioButton... buttons)` Creates a `RadioButtonGroup` with both a name and one or more `RadioButton` objects assigned to the group.

A `RadioButton` component stores the group to which it belongs, which can be accessed via the `getButtonGroup()` method.

## Adding and Removing RadioButtons

It is possible to add and remove singular or multiple `RadioButton` objects to a group, ensuring that they exhibit mutually-exclusive checking behavior, and are associated with any name that may belong to the group.

## Naming

The name attribute in a `RadioButtonGroup` groups related RadioButtons together, allowing users to make a single choice from the options provided and enforcing exclusivity among the RadioButtons. The name of a group is not reflected in the DOM, however, and is a convenience utility for the Java developer.

## Best Practices 

To ensure an optimal user experience when using the RadioButton component, consider the following best practices:

1. **Clearly Label Options**: Provide clear and concise labels for each `RadioButton` option to accurately describe the choice. Labels should be easy to understand and distinguish from one another.

2. **Provide Default Selection**: If applicable, consider providing a default selection for Radio buttons to guide users when they first encounter the options. The default selection should align with the most common or preferred choice.