---
sidebar_position: 5
title: Button
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/button/Button"/>

The `Button` class is a component that represents a button element in a user interface. It provides various functionalities and customization options to create interactive buttons.

## Usages

The `Button` class is a versatile component that is commonly used in various situations where user interactions and actions need to be triggered. Here are some typical scenarios where you might need a button in your application:

1. **Form Submission**: Buttons are often used to submit form data. For example, in a web application, you can use a button labeled "Submit" to send user input to the server for processing.

2. **User Actions**: Buttons are used to allow users to perform specific actions within the application. For instance, you can have a button labeled "Delete" to initiate the deletion of a selected item or a button labeled "Save" to save changes made to a document.

3. **Navigation**: Buttons can be used for navigation purposes, such as moving between different sections or pages within an application. You can create buttons with labels like "Next," "Previous," or "Back" to enable easy navigation for users.

4. **Confirmation Dialogs**: Buttons are often included in confirmation dialogs to provide options for users to confirm or cancel an action. For example, you can have buttons labeled "OK" and "Cancel" to allow users to confirm or cancel a critical operation.

5. **Interaction Triggers**: Buttons can serve as triggers for interactions or events within the application. By clicking a button, users can initiate complex actions or trigger animations, refreshing content, or updating the display.

Feel free to explore the various customization options available in the Button class to tailor the buttons' appearance, behavior, and functionality to suit your application's specific needs.


<br />

## Constructors

The `CheckBox` class has three constructors:

1. `Button()`: Creates an empty `Button` without any text inside the component.
2. `Button(String text)`: Creates a `Button` with text inside the component.
3. `Button(String text, EventListener<ButtonClickEvent> onClickListener)` Creates a `Button` with text inside the component, and an `EventListener` for a click event which fires when a user clicks on the `Button`.

Here is an example of how to create a `CheckBox` object:

```java
Button myButton = new Button("Button Text");
```

### Adding Icons to Buttons

In addition to, or instead of having text on a button, it is possible to add an icon to a button as well. Out of the box, the following icon pools can be used:

<ol>
    <li><a href='https://tabler-icons.io/'> Tabler </a></li>
    <li><a href='https://feathericons.com/'> Feather </a></li>
    <li><a href='https://fontawesome.com/'> Font Awesome Free </a></li>
</ol>

Below are examples of buttons with text to the left and right, as well as a button with only an icon:

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonIcon' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/buttondemos/ButtonIcon.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Icon.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/icon_styles.css' 
javaHighlight='{15,17,19}'
height="100px"
/>


<!-- ![Adding icons to buttons](./_images/button/button_icons.jpg) d -->
<br />

To add these icons, set the button's text to have an `<html>` tag, with a `<bbj-icon>` tag inside with the name attribute set accordingly. In addition to an icon, include text to the left or right of the `<bbj-icon>` tag to include a label as well.

<br />

## Disabling a Button

Button components, like many others, can be disabled to convey to a user that a certain action is not yet or is no longer available. A disabled button will increase the gray scale of the button, and is available for 
all button themes and expanses. <br/><br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonDisable' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/buttondemos/ButtonDisable.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Disable.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/disable_styles.css'
javaHighlight='{49-52}'
/>

<br />

Disabling a button can be done at any time in the code by using the ```setEnabled(boolean foo)``` function. For added convenience, a button can also be disabled when clicked using the built-in ```setDisabledOnClick(boolean foo)``` function.

<br />

## Events

The `Button` class provides methods to add and remove event listeners for the following events:

| Events | Description |
|:-:|-|
|`BlurEvent`|An event that is triggered when an element loses focus. It occurs when the user interacts with an element, such as clicking inside an input field, and then moves the focus away from that element, typically by clicking outside of it or tabbing to another element on the page.|
|`ClickEvent`|The click event is triggered when a user interacts with a button by clicking or tapping on it. When a component's click event is triggered, it indicates that the user has performed an action to activate the component.|
|`FocusEvent`|An event that is triggered when an element gains focus, opposite of a blur event. It occurs when the user interacts with an element, typically by clicking inside an input field or navigating to it using the keyboard's tab key, causing the element to become active and ready to receive user input.|
|`MouseEnterEvent`|An event that is triggered when the mouse cursor enters the boundaries of an element. It occurs when the user moves the mouse pointer over the specified element, indicating that the mouse has entered its area.|
|`MouseExitEvent`|An event that is triggered when the mouse cursor exits the boundaries of an element. It occurs when the user moves the mouse pointer out of the boundaries of the specified element, indicating that the mouse has exited its area.|
|`RightMouseDownEvent`|An event refers to an event that is triggered when the user presses the right mouse button while the cursor is over an element. It allows you to capture the specific action of the user's right mouse button being pressed down within the boundaries of the element.|

<br />

### Adding Events

To add an event listener, use the appropriate method:

```java
myButton.addClickListener( e -> {
  //Executed when the event fires
});
```

Additional syntactic sugar methods, or aliases, have been added to allow for alternative addition of events by using the `on` prefix followed by the event, such as:

```java
myButton.onClick( e -> {
    //Executed when the event fires
});
```

:::tip
Using the event payload that comes with various events to attain information reduces the number of round trips made when instead querying the component for the required information. 
:::

### Removing Events

To remove an event listener, use the appropriate method:

```java
myButton.removeClickListener(listener);
```

:::info
For a method to be removed via the appropriate removeListener method, the signature of the method must be saved. 
:::


## Styling

### Themes

DWCJ button components come with 14 themes built in for quick styling without the use of CSS.
Shown below are example buttons with each of the supported Themes applied: <br/>


<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonThemes' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/buttondemos/ButtonThemes.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Theme.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/theme_styles.css'
javaHighlight='{23-36}'
/>

### Expanses
There are five button expanses that are supported in the DWCJ which allow for quick styling without using CSS.
Below are the various expanses supported for the button component: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=control_demos.buttondemos.ButtonExpanses' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/control_demos/buttondemos/ButtonExpanses.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Expanses.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/expanse_styles.css'
javaHighlight='{18-22}'
/>

### Shadow Parts

These are the various parts of the shadow DOM for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').Button}  table='parts'/>

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').Button}  table='properties'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_bbj_control_map.json').Button} table="reflects"/>


## Best Practices 

To ensure an optimal user experience when using the `Button` component, consider the following best practices:

1. **Proper Text**: Use clear and concise text for text within your `Button` component to provide a clear indication of its purpose.

2. **Appropriate Visual Styling**: Consider the visual styling and theme of the `Button` to ensure consistency with your application's design. For example, a "Cancel" `Button` component should be styled differently than a "Confirm" component.

3. **Efficient Event Handling**: Handle `Button` events efficiently and provide appropriate feedback to users. Refer to [this section](./button/#adding-events) to review efficient event adding behaviors.

4. **Testing and Accessibility**: Test the Button's behavior in different scenarios, such as when it is disabled or receives focus, to ensure a smooth user experience.
Follow accessibility guidelines to make the `Button` usable for all users, including those who rely on assistive technologies.