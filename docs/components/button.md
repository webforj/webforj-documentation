---
sidebar_position: 5
title: Button
---

import ComponentDemo from '@site/src/components/DocsTools/ComponentDemo';
import TableBuilder from '@site/src/components/DocsTools/TableBuilder';
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="engine" location="org/dwcj/component/button/Button" top='true'/>


The `Button` component is a versatile UI element that allows users to trigger actions or interact with your application. Buttons are essential for creating intuitive and interactive user interfaces.

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.buttondemos.ButtonDemo' 
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/buttondemos/ButtonDemo.java'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/demo_styles.css'
height='300px'
/>

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

The `Button` class has three constructors:

1. `Button()`: Creates an empty `Button` without any text inside the component.
2. `Button(String text)`: Creates a `Button` with text inside the component.
3. `Button(String text, EventListener<ButtonClickEvent> onClickListener)` Creates a `Button` with text inside the component, and an <JavadocLink type="engine" location="org/dwcj/component/event/EventListener" code='true'>EventListener</JavadocLink> for a <JavadocLink type="engine" location="org/dwcj/component/button/event/ButtonClickEvent" code='true'>ButtonClickEvent</JavadocLink> which fires when a user clicks on the `Button`.

Here is an example of how to create a `Button` object:

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
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.buttondemos.ButtonIcon' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/buttondemos/ButtonIcon.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Icon.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/icon_styles.css' 
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
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.buttondemos.ButtonDisable' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/buttondemos/ButtonDisable.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Disable.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/disable_styles.css'
/>

<br />

Disabling a button can be done at any time in the code by using the <JavadocLink type="engine" location="org/dwcj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> function. For added convenience, a button can also be disabled when clicked using the built-in <JavadocLink type="engine" location="org/dwcj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> function.

<br />

## Events

The `Button` class provides methods to add and remove event listeners for the following events:

<!-- | Events | Description |
|:-:|-|
|<JavadocLink type="engine" location="org/dwcj/component/event/BlurEvent" code='true'>BlurEvent</JavadocLink>|An event that is triggered when an element loses focus. It occurs when the user interacts with an element, such as clicking inside an input field, and then moves the focus away from that element, typically by clicking outside of it or tabbing to another element on the page.|
|<JavadocLink type="engine" location="org/dwcj/component/button/event/ButtonClickEvent" code='true'>ButtonClickEvent</JavadocLink>|The click event is triggered when a user interacts with a button by clicking or tapping on it. When a component's click event is triggered, it indicates that the user has performed an action to activate the component.|
|<JavadocLink type="engine" location="org/dwcj/component/event/FocusEvent" code='true'>FocusEvent</JavadocLink>|An event that is triggered when an element gains focus, opposite of a blur event. It occurs when the user interacts with an element, typically by clicking inside an input field or navigating to it using the keyboard's tab key, causing the element to become active and ready to receive user input.|
|<JavadocLink type="engine" location="org/dwcj/component/event/MouseEnterEvent" code='true'>MouseEnterEvent</JavadocLink>|An event that is triggered when the mouse cursor enters the boundaries of an element. It occurs when the user moves the mouse pointer over the specified element, indicating that the mouse has entered its area.|
|<JavadocLink type="engine" location="org/dwcj/component/event/MouseExitEvent" code='true'>MouseExitEvent</JavadocLink>|An event that is triggered when the mouse cursor exits the boundaries of an element. It occurs when the user moves the mouse pointer out of the boundaries of the specified element, indicating that the mouse has exited its area.|
|<JavadocLink type="engine" location="org/dwcj/component/event/RightMouseDownEvent" code='true'>RightMouseDownEvent</JavadocLink>|An event refers to an event that is triggered when the user presses the right mouse button while the cursor is over an element. It allows you to capture the specific action of the user's right mouse button being pressed down within the boundaries of the element.| -->

- [`BlurEvent`](../components/events/BlurEvent)
- [`ButtonClickEvent`](../components/events/ButtonClickEvent)
- [`FocusEvent`](../components/events/FocusEvent)
- [`MouseEnterEvent`](../components/events/MouseEnterEvent)
- [`MouseExitEvent`](../components/events/MouseExitEvent)
- [`RightMouseDownEvent`](../components/events/RightMouseDownEvent)


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



### Removing Events

To remove an event listener, use the appropriate method:

```java
myButton.removeClickListener(listener);
```

:::tip
Below is a simple demonstration using a `ClickEvent` - notice the event payload is used to attain information that is then utilized within the callback, which reduces the number of round trips made between the client and server. This is much more efficient than querying the component for the required information whenever data that is provided in the event payload is needed. 
:::

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.buttondemos.ButtonEvent' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/buttondemos/ButtonEvent.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Event.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/event_styles.css'
height='150px'
/>

## Styling

### Themes

DWCJ button components come with <JavadocLink type="engine" location="org/dwcj/component/button/Button.Theme">14 discrete themes built in for quick styling</JavadocLink> without the use of CSS.
Shown below are example buttons with each of the supported Themes applied: <br/>


<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.buttondemos.ButtonThemes' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/buttondemos/ButtonThemes.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Theme.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/theme_styles.css'
height='175px'
/>

### Expanses
There are five button expanses that are supported in the DWCJ which allow for quick styling without using CSS.
Below are the various expanses supported for the button component: <br/>

<ComponentDemo 
path='https://hot.bbx.kitchen/webapp/controlsamples?class=component_demos.buttondemos.ButtonExpanses' 
javaE='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/java/component_demos/buttondemos/ButtonExpanses.java'
javaC='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/code_snippets/button/Expanses.txt'
cssURL='https://raw.githubusercontent.com/DwcJava/ControlSamples/main/src/main/resources/css/buttonstyles/expanse_styles.css'
height='75px'
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