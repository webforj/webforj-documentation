---
sidebar_position: 100
title: RadioButton
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/radiobutton/RadioButton"/>

The `RadioButton` class creates an object that can be selected or deselected, and which displays its state to the user. By convention, only one radio button in a group can be selected at a time. Radio buttons are commonly used when mutually exclusive options are available, allowing the user to choose a single option from a set of choices.

Here is an example of how to create a `RadioButton` object:

```java
RadioButton myRadioButton = new RadioButton("RadioButton Text");
```

### Constructors

The `RadioButton` class has three constructors:

1. `RadioButton()`: Creates an empty `RadioButton` in the unchecked state.
2. `RadioButton(String)`: Creates a `RadioButton` with an attached label in the unchecked state.
3. `RadioButton(String, boolean)` Creates a `RadioButton` with an attached label in either the checked or unchecked state, based on the boolean passed (`true` for checked, `false` for unchecked).

### Text and Positioning

Radio buttons can utilize the ```setText(String foo)``` method, which will be positioned near the radio button according to the built-in `Position`.
DWCJ radio buttons have built-in functionality to set text to be displayed either to the right or left of the component. By default, the text will be displayed to the right of the component. Positioning of the horizontal text is supported by use of a built-in enum class. Show below are the two settings: <br/>

### Activation

Radio buttons can be controlled using two types of activation: manual activation and auto activation. These dictate when a `RadioButton` will change its state.

#### Manual Activation

When a radio button is set to manual activation, it means that it will not be automatically checked when it gains focus.
Manual activation allows the user to navigate through the radio button options using the keyboard or other input methods without immediately changing the selected option.

If the radio button is part of a group, selecting a different radio button within the group will automatically uncheck the previously selected radio button.
Manual activation provides finer control over the selection process, requiring an explicit action from the user to change the selected option.



#### Auto Activation

Automatic activation is the default state for a `RadioButton`, and means that the button will be checked on whenever it gains focus for any reason. This means that
not only clicking, but auto-focus or tab navigation will also check the button.

### Switches

A `RadioButton` can also be set to display as a switch provides which provides alternative visual representation for selecting options. Normally, radio buttons are circular or rounded in shape and indicate a single choice from a group of options. However, by calling the `setSwitch(boolean)` method, a `RadioButton` can be transformed into a switch that resembles a toggle switch or slider.

When a `RadioButton` is displayed as a switch, it typically appears as a oblong shape with an indicator that can be toggled on or off. This visual representation gives users a more intuitive and familiar interface, similar to physical switches commonly found in electronic devices. To do this, execute code similar to the following snippet:

```java
RadioButton myButton = new RadioButton();
myButton.setSwitch(true);
```

Setting a radio button to display as a switch can improve user experience by providing a clear and straightforward way to select options. It can enhance the visual appeal and usability of forms, settings panels, or any other interface element that requires multiple choices.

It's important to note that the behavior of the radio button remains the same, meaning only one option can be selected at a time within a group. The switch-like appearance is primarily a visual transformation while retaining the functionality of a radio button.

### Expanses
There are five checkbox expanses that are supported in the DWCJ which allow for quick styling without using CSS.
Expanses are supported by use of a built-in enum class. Below are the expanses supported for the checkbox component: <br/>

<br/>

### Events

The `RadioButton` class provides methods to add and remove event listeners for the following events:

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
myRadioButton.addCheckedListener( e -> {
  //Executed when the event fires
});
```

Additional syntactic sugar methods, or aliases, have been added to allow for alternative addition of events by using the `on` prefix followed by the event, such as:

```java
myRadioButton.onChecked( e -> {
    //Executed when the event fires
});
```

To remove an event listener, use the appropriate method:

```java
myRadioButton.removeCheckedListener(listener);
```

:::info
For a method to be removed via the appropriate removeListener method, the signature of the method must be saved. 
:::


### Usages

The `RadioButton` is best used in scenarios where users need to make a single selection from a predefined set of options. Here are some examples of when to use the `RadioButton`:

1. **Survey or Questionnaires**: Radio buttons are commonly used in surveys or questionnaires where users need to select a single response from a list of options.

2. **Preference Settings**: Applications that involve preference or settings panels often use Radio buttons to allow users to choose a single option from a set of mutually exclusive choices.

3. **Filtering or Sorting**: A `RadioButton` can be used in applications that require users to select a single filter or sorting option, such as sorting a list of items by different criteria.

### Best Practices 

To ensure an optimal user experience when using the RadioButton component, consider the following best practices:

1. **Clearly Label Options**: Provide clear and concise labels for each `RadioButton` option to accurately describe the choice. Labels should be easy to understand and distinguish from one another.

2. **Group Radio buttons**: Group related Radio buttons together to indicate their association. This helps users understand that only one option can be selected within a specific group. This can be done effectively using the DWCJ's [`RadioButtonGroup`](/docs/components/radio-group) component

3. **Provide Default Selection**: If applicable, consider providing a default selection for Radio buttons to guide users when they first encounter the options. The default selection should align with the most common or preferred choice.

### Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').RadioButton} />
