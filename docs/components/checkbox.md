---
title: CheckBox
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import EventTable from '@site/src/components/DocsTools/EventTable';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import DocChip from '@site/src/components/DocsTools/DocChip';

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />

<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-checkbox" clickable={false} iconName='code'/>

<JavadocLink type="foundation" location="com/webforj/component/optioninput/CheckBox" top='true'/>

The `CheckBox` class creates a component that can be selected or deselected, and which displays its state to the user. When clicked, a check mark appears inside the box, to indicate an affirmative choice (on). When clicked again, the check mark disappears, indicating a negative choice (off).

By providing a clear and straightforward visual indication of selection status, checkboxes enhance user interaction and decision-making, making them an essential element in modern user interfaces.

## Usages

The `CheckBox` is best used in scenarios where users need to make multiple selections from a list of options. Here are some examples of when to use the `CheckBox`:

1. **Task or Feature Selection**: Checkboxes are commonly used when users need to select multiple tasks or features to perform certain actions or configurations.

2. **Preference Settings**: Applications that involve preference or settings panels often use Checkboxes to allow users to choose multiple options from a set of choices. This is best for options which are not mutually exclusive. For instance:

 >- Enabling or disabling notifications
 >- Choosing a dark mode or light mode theme
 >- Selecting email notification preferences

3. **Filtering or Sorting**: A `CheckBox` can be used in applications that require users to select multiple filters or categories, such as filtering search results or selecting multiple items for further actions.

4. **Form Inputs**: Checkboxes are commonly used in forms to allow users to select multiple options or make binary choices. For example:
  >- Subscribe to a newsletter
  >- Agree to terms and conditions
  >- Select items for purchase or booking


## Constructors

The `CheckBox` class has three constructors:

1. <JavadocLink type="foundation" location="com/webforj/component/checkbox/CheckBox" code='true' suffix='#<init>()'>Checkbox()</JavadocLink>: Creates an empty <code>CheckBox</code> in the unchecked state.
2. <JavadocLink type="foundation" location="com/webforj/component/checkbox/CheckBox" code='true' suffix='#<init>(java.lang.String)'>Checkbox(String text)</JavadocLink>: Creates a <code>CheckBox</code> with an attached label in the unchecked state.
3. <JavadocLink type="foundation" location="com/webforj/component/checkbox/CheckBox" code='true' suffix='#<init>(java.lang.String,boolean)'>Checkbox(String text boolean checked)</JavadocLink>: Creates a <code>CheckBox</code> with an attached label in either the checked or unchecked state, based on the boolean passed (<code>true</code> for checked, <code>false</code> for unchecked).

Here is an example of how to create a `CheckBox` object:

```java
CheckBox enabled = new CheckBox("Enable Option");
```

## Text and Positioning

Check boxes can utilize the <JavadocLink type="foundation" location="com/webforj/component/AbstractOptionInput" code='true' suffix='#setText(java.lang.String)'>setText(String text)</JavadocLink> method, which will be positioned near the check box according to the built-in <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink>. 

Checkboxes have built-in functionality to set text to be displayed either to the right or left of the box. By default, the text will be displayed to the right of the component. Positioning of the text is supported by use of the  <JavadocLink type="foundation" location="com/webforj/component/TextPosition" code='true' suffix=''>Position</JavadocLink> enum. Show below are the two settings: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.checkboxdemos.CheckboxHorizontalText' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/checkboxdemos/CheckboxHorizontalText.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/checkbox/Horizontal.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/checkboxstyles/text_styles.css' 
height = '200px'
/>

<br/>

## Indeterminism

The `CheckBox` component supports indeterminism, which is a UI pattern commonly used in forms and lists to indicate that a group of checkboxes has a mixture of checked and unchecked states. This state is represented by a third visual state, typically displayed as a filled square or a dash inside the checkbox. There are a few common use cases associated with indeterminism:

- **Selecting multiple items**: Indeterminism is useful when users need to select multiple items from a list or a set of options. It allows users to indicate that they want to select some, but not all, of the available choices.

- **Hierarchical data**: Indeterminism can be employed in scenarios where there is a hierarchical relationship between CheckBoxes. For example, when selecting categories and subcategories, indeterminism can represent that some subcategories are selected while others are not, and the parent component is in the indeterminate state.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.checkboxdemos.CheckboxIndeterminate' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/checkboxdemos/CheckboxIndeterminate.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/checkbox/Indeterminate.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/checkboxstyles/text_styles.css' 
height = '150px'
/>

## Styling

### Expanses
The following <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses values </JavadocLink> allow for quick styling without using CSS.
Expanses are supported by use of the `Expanse` enum class. Below are the expanses supported for the checkbox component: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.checkboxdemos.CheckboxExpanseDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/componentdemos/checkboxdemos/CheckboxExpanseDemo.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/checkbox/Expanse.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/checkboxstyles/expanse_styles.css' 
javaHighlight='{17,21,25,29,33}'
/>

<br/>


### Shadow Parts

These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Checkbox} table="parts"/>

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.


<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Checkbox} table="properties"/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.


<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Checkbox} table="reflects"/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-checkbox' table="dependencies"/>


## Best Practices 

To ensure an optimal user experience when using the `Checkbox` component, consider the following best practices:

1. **Clearly Label Options**: Provide clear and concise labels for each `CheckBox` option to accurately describe the choice. Labels should be easy to understand and distinguish from one another.

2. **Group CheckBoxes**: Group related Checkboxes together to indicate their association. This helps users understand that multiple options can be selected within a specific group.

3. **Provide Default Selection**: If applicable, consider providing a default selection for CheckBoxes to guide users when they first encounter the options. The default selection should align with the most common or preferred choice.

4. **Indeterminism**: If a parent `CheckBox` component has multiple components belonging to it in a way in which some can be checked on and others checked off, use the indeterminate property to show that not all `CheckBox` components are checked or unchecked.