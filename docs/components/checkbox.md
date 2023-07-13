---
sidebar_position: 10
title: CheckBox
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';
import { useState } from 'react';

<JavadocLink type="engine" location="org/dwcj/component/checkbox/CheckBox"/>

<!-- 
<Tabs>
  <TabItem value="Implementation" label="Implementation"> -->


The `CheckBox` class is a java class in `org.dwcj.component.checkbox` package. It creates a component that can be selected or deselected, and which displays its state to the user. When clicked, a check mark appears inside the box, to indicate an affirmative choice (on). When clicked again, the check mark disappears, indicating a negative choice (off).

Here is an example of how to create a `CheckBox` object:

```java
CheckBox myCheckbox = new CheckBox("Checkbox Text");
```

### Constructors

The `CheckBox` class has three constructors:

1. `CheckBox()`: Creates an empty `CheckBox` in the unchecked state.
2. `CheckBox(String)`: Creates a `CheckBox` with an attached label in the unchecked state.
3. `CheckBox(String, boolean)` Creates a `CheckBox` with an attached label in either the checked or unchecked state, based on the boolean passed (`true` for checked, `false` for unchecked).

### Text and Positioning

Check boxes can utilize the ```setText(String foo)``` method, which will be positioned near the check box according to the built-in `Position`.
DWCJ checkboxes have built-in functionality to set text to be displayed either to the right or left of the box. By default, the text will be displayed to the right of the component. Positioning of the horizontal text is supported by use of a built-in enum class. Show below are the two settings: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.checkboxdemos.CheckboxHorizontalText' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/checkboxdemos/CheckboxHorizontalText.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/checkbox/Horizontal.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/checkboxstyles/text_styles.css' 
javaHighlight='{18}'
/>

<br/>

### Indeterminism

The DWCJ's `CheckBox` component supports indeterminism, which is a UI pattern commonly used in forms and lists to indicate that a group of checkboxes has a mixture of checked and unchecked states. This state is represented by a third visual state, typically displayed as a filled square or a dash inside the checkbox. There are a few common use cases associated with the indeterminate status:

- Selecting multiple items: Indeterminism is useful when users need to select multiple items from a list or a set of options. It allows users to indicate that they want to select some, but not all, of the available choices.

- Hierarchical data: Indeterminism can be employed in scenarios where there is a hierarchical relationship between CheckBoxes. For example, when selecting categories and subcategories, the indeterminate state can represent that some subcategories are selected while others are not, and the parent category is in the indeterminate state.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.checkboxdemos.CheckboxIndeterminate' 
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/checkboxdemos/CheckboxIndeterminate.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/checkboxstyles/text_styles.css' 
height = '225px'
/>

### Events

The `CheckBox` class provides methods to add and remove event listeners for the following events:

| Events | Description |
|:-:|-|
|`BlurEvent`| An event that is triggered when an element loses focus. It occurs when the user interacts with an element, such as clicking inside an input field, and then moves the focus away from that element, typically by clicking outside of it or tabbing to another element on the page. |
|`FocusEvent`| An event that is triggered when an element gains focus, opposite of a blur event. It occurs when the user interacts with an element, typically by clicking inside an input field or navigating to it using the keyboard's tab key, causing the element to become active and ready to receive user input. |
|`CheckedEvent`| An event that is triggered when the state of a component changes and becomes checked. It occurs when the user interacts with a checkbox element by clicking or tapping on it, causing the checkbox to transition from an unchecked state to a checked state. |
|`UncheckedEvent`| An event that is triggered when the state of a component changes and becomes unchecked. It occurs when the user interacts with a checkbox element by clicking or tapping on it, causing the checkbox to transition from an checked state to a unchecked state. |
|`ToggleEvent`| A ToggleEvent fires a CheckBox or a similar UI element changes its state between "on" and "off" or "active" and "inactive." It represents the action of toggling a setting, feature, or state of an element such as a CheckBox or Radio Button. |
|`MouseEnterEvent`| An event that is triggered when the mouse cursor enters the boundaries of an element. It occurs when the user moves the mouse pointer over the specified element, indicating that the mouse has entered its area. |
|`MouseExitEvent`| An event that is triggered when the mouse cursor exits the boundaries of an element. It occurs when the user moves the mouse pointer out of the boundaries of the specified element, indicating that the mouse has exited its area. |
|`RightMouseDownEvent`| An event refers to an event that is triggered when the user presses the right mouse button while the cursor is over an element. It allows you to capture the specific action of the user's right mouse button being pressed down within the boundaries of the element. |

<br />

To add an event listener, use the appropriate method:

```java
myCheckBox.addCheckedListener( e -> {
  //Executed when the event fires
});
```

Additional syntactic sugar methods, or aliases, have been added to allow for alternative addition of events by using the `on` prefix followed by the event, such as:

```java
myCheckBox.onChecked( e -> {
    //Executed when the event fires
});
```

To remove an event listener, use the appropriate method:

```java
myCheckBox.removeCheckedListener(listener);
```

:::info
For a method to be removed via the appropriate removeListener method, the signature of the method must be saved. 
:::

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.checkboxdemos.CheckboxEventDemo' 
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/checkboxdemos/CheckboxEventDemo.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/checkboxstyles/text_styles.css'
/>

### Expanses
There are five checkbox expanses that are supported in the DWCJ which allow for quick styling without using CSS.
Expanses are supported by use of a built-in enum class. Below are the expanses supported for the checkbox component: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.checkboxdemos.CheckboxExpanseDemo' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/checkboxdemos/CheckboxExpanseDemo.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/checkbox/Horizontal.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/checkboxstyles/expanse_styles.css' 
javaHighlight='{17,21,25,29,33}'
/>

<!-- </TabItem>

<TabItem value="Styling" label="Styling"> -->

### Usages

The `CheckBox` is best used in scenarios where users need to make multiple selections from a list of options. Here are some examples of when to use the `CheckBox`:

1. **Task or Feature Selection**: Checkboxes are commonly used when users need to select multiple tasks or features to perform certain actions or configurations.

2. **Preference Settings**: Applications that involve preference or settings panels often use Checkboxes to allow users to choose multiple options from a set of choices. This is best for options which are not mutually exclusive.

3. **Filtering or Sorting**: A `CheckBox` can be used in applications that require users to select multiple filters or categories, such as filtering search results or selecting multiple items for further actions.

### Best Practices 

To ensure an optimal user experience when using the RadioButton component, consider the following best practices:

1. **Clearly Label Options**: Provide clear and concise labels for each `CheckBox` option to accurately describe the choice. Labels should be easy to understand and distinguish from one another.

2. **Group Radio buttons**: Group related Checkboxes together to indicate their association. This helps users understand that multiple options can be selected within a specific group.

3. **Provide Default Selection**: If applicable, consider providing a default selection for Radio buttons to guide users when they first encounter the options. The default selection should align with the most common or preferred choice.

4. **Indeterminism**: If a parent `CheckBox` component has multiple components belonging to it in a way in which some can be checked on and others checked off, use the indeterminate property to show that not all `CheckBox` components are checked or unchecked.

<br/>



### Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').Checkbox} />

<!-- 
</TabItem>
</Tabs> -->