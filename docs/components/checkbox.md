---
title: CheckBox
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-checkbox" />

<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

The `CheckBox` class creates a component that can be selected or deselected, and which displays its state to the user. When clicked, a check mark appears inside the box, to indicate an affirmative choice (on). When clicked again, the check mark disappears, indicating a negative choice (off).

By providing a clear and straightforward visual indication of selection status, checkboxes enhance user interaction and decision-making, making them an essential element in modern user interfaces.

## Usages

The `CheckBox` is best used in scenarios where users need to make multiple selections from a list of options. Here are some examples of when to use the `CheckBox`:

1. **Task or Feature Selection**: Checkboxes are commonly used when users need to select multiple tasks or features to perform certain actions or configurations.

2. **Preference Settings**: Applications that involve preference or settings panels often use Checkboxes to allow users to choose multiple options from a set of choices. This is best for options which are not mutually exclusive. For instance:

> - Enabling or disabling notifications
> - Choosing a dark mode or light mode theme
> - Selecting email notification preferences

3. **Filtering or Sorting**: A `CheckBox` can be used in applications that require users to select multiple filters or categories, such as filtering search results or selecting multiple items for further actions.

4. **Form Inputs**: Checkboxes are commonly used in forms to allow users to select multiple options or make binary choices. For example:
   > - Subscribe to a newsletter
   > - Agree to terms and conditions
   > - Select items for purchase or booking

## Text and positioning

Check boxes can utilize the <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> method, which will be positioned near the check box according to the built-in <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>.

Checkboxes have built-in functionality to set text to be displayed either to the right or left of the box. By default, the text will be displayed to the right of the component. Positioning of the text is supported by use of the <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> enum. Show below are the two settings: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/checkboxhorizontaltext?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxHorizontalTextView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/checkbox/Horizontal.txt'
height = '200px'
/>

<br/>

## Indeterminism

The `CheckBox` component supports indeterminism, which is a UI pattern commonly used in forms and lists to indicate that a group of checkboxes has a mixture of checked and unchecked states. This state is represented by a third visual state, typically displayed as a filled square or a dash inside the checkbox. There are a few common use cases associated with indeterminism:

- **Selecting multiple items**: Indeterminism is useful when users need to select multiple items from a list or a set of options. It allows users to indicate that they want to select some, but not all, of the available choices.

- **Hierarchical data**: Indeterminism can be employed in scenarios where there is a hierarchical relationship between CheckBoxes. For example, when selecting categories and subcategories, indeterminism can represent that some subcategories are selected while others are not, and the parent component is in the indeterminate state.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/checkboxindeterminate?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxIndeterminateView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/checkbox/Indeterminate.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/checkbox/checkboxIndeterminate.css' 
height = '150px'
/>

## Styling

### Expanses

The following <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses values </JavadocLink> allow for quick styling without using CSS.
Expanses are supported by use of the `Expanse` enum class. Below are the expanses supported for the checkbox component: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/checkboxexpanse?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/checkbox/CheckboxExpanseView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/checkbox/Expanse.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/checkbox/checkboxExpanse.css' 
height = '100px'
/>

<br/>

### Shadow parts

These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/\_dwc_control_map.json').Checkbox} table="parts"/>

### CSS properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/\_dwc_control_map.json').Checkbox} table="properties"/>

### Reflected attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/\_dwc_control_map.json').Checkbox} table="reflects"/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-checkbox' table="dependencies"/>

## Best practices

To ensure an optimal user experience when using the `Checkbox` component, consider the following best practices:

1. **Clearly Label Options**: Provide clear and concise labels for each `CheckBox` option to accurately describe the choice. Labels should be easy to understand and distinguish from one another.

2. **Group CheckBoxes**: Group related Checkboxes together to indicate their association. This helps users understand that multiple options can be selected within a specific group.

3. **Provide Default Selection**: If applicable, consider providing a default selection for CheckBoxes to guide users when they first encounter the options. The default selection should align with the most common or preferred choice.

4. **Indeterminism**: If a parent `CheckBox` component has multiple components belonging to it in a way in which some can be checked on and others checked off, use the indeterminate property to show that not all `CheckBox` components are checked or unchecked.
