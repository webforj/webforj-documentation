---
sidebar_position: 10
title: Check Box
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';

<div style={{width: "100%" , display: "flex", justifyContent: "flex-end", marginBottom: "-50px"}}>
<p style={{color: "gray"}} >API:&nbsp;</p>
<b><a href="https://javadoc.io/static/org.dwcj/dwcj-engine/0.15.0/org/dwcj/controls/checkbox/CheckBox.html" style={{justifySelf: "flex-end"}}> Java </a></b>
</div>

The `CheckBox` class is a java class in `org.dwcj.component.checkbox` package. It creates a component that can be selected or deselected, and which displays its state to the user. When clicked, a check mark appears inside the box, to indicate an affirmative choice (on). When clicked again, the check mark disappears, indicating a negative choice (off).

Here is an example of how to create a `CheckBox` object:

```java
CheckBox myCheckbox = new CheckBox("Checkbox Text");
```

## Constructors

The `CheckBox` class has three constructors:

1. `CheckBox()`: Creates an empty `CheckBox` in the unchecked state.
2. `CheckBox(String)`: Creates a `CheckBox` with an attached label in the unchecked state.
3. `CheckBox(String, boolean)` Creates a `CheckBox` with an attached label in either the checked or unchecked state, based on the boolean passed (`true` for checked, `false` for unchecked).

## Properties

The `CheckBox` component has various properties that can be manipulated with provided API methods.

### Text and Positioning

Check boxes can utilize the ```setText(String foo)``` method, which will positioned near the check box according to the built-in `HorizontalTextPosition`.
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
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.checkboxdemos.CheckboxHorizontalText' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/checkboxdemos/CheckboxHorizontalText.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/checkbox/Horizontal.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/checkboxstyles/text_styles.css' 
javaHighlight='{18}'
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

<br/>

## Events

The `CheckBox` class provides methods to add and remove event listeners for the following events:

- `BlurEvent;`
- `CheckedEvent;`
- `EventDispatcher;`
- `EventListener;`
- `FocusEvent;`
- `MouseEnterEvent;`
- `MouseExitEvent;`
- `RightMouseDownEvent;`
- `ToggleEvent;`
- `UncheckedEvent;`

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

## Parts and CSS Properties

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').Checkbox} />