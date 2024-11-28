---
title: Button
---

import FiberSmartRecordIcon from '@mui/icons-material/FiberSmartRecord';
import Chip from '@mui/material/Chip';

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-button" />


<JavadocLink type="foundation" location="com/webforj/component/button/Button" top='true'/>

A `Button` component is a fundamental user interface element used in application development to create interactive elements that trigger actions or events when clicked or activated. It serves as a clickable element that users can interact with to perform various actions within an application or website. 

The primary purpose of the `Button` component is to provide a clear and intuitive call-to-action for users, guiding them to perform specific tasks such as submitting a form, navigating to another page, triggering a function, or initiating a process. Buttons are essential for enhancing user interactions, improving accessibility, and creating a more engaging user experience.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/button?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonView.java'
height='300px'
/>

<!-- tabs={['ButtonDemo.java', 'demo_styles.css']} -->

## Usages

The `Button` class is a versatile component that is commonly used in various situations where user interactions and actions need to be triggered. Here are some typical scenarios where you might need a button in your application:

1. **Form Submission**: Buttons are often used to submit form data. For example, in an application, you can use:

  > - A "Submit" button to send data to the server
  > - A "Clear" button to remove any information already present in the form


2. **User Actions**: Buttons are used to allow users to perform specific actions within the application. For instance, you can have a button labeled:

  > - "Delete" to initiate the deletion of a selected item
  > - "Save" to save changes made to a document or page.

3. **Confirmation Dialogs**: Buttons are often included in [`Dialog`](../components/dialog) components built for various purposes to provide options for users to confirm or cancel an action, or any other functionality that is built into the [`Dialog`](../components/dialog) you're using.

4. **Interaction Triggers**: Buttons can serve as triggers for interactions or events within the application. By clicking a button, users can initiate complex actions or trigger animations, refreshing content, or updating the display.

5. **Navigation**: Buttons can be used for navigation purposes, such as moving between different sections or pages within an application. Buttons for navigation could include:

  > - "Next" - Takes the user to the next page or section of the current application or page.
  > - "Previous" - Returns the user to the previous page of the application or section they're in.
  > - "Back" Returns the user to the first part of the application or page they're in.

## Adding icons to buttons

Incorporating an icon into a button can greatly improve your app's design, allowing users to quickly identify actionable items on the screen. The [`Icon`](./icon.md) component provides a wide selection of icons to choose from.

By utilizing the `setPrefixComponent()` and `setSuffixComponent()` methods, you have the flexibility to determine whether an `Icon` should appear before or after the text on a button. Alternatively, the `setIcon()` method can be used to add an `Icon` after the text, but before the button's `suffix` slot.

<!-- Add this back in once Icon has been merged -->
<!-- Refer to the [Icon component](../components/icon) page for more information on configuring and customizing icons. -->

:::tip
By default, an `Icon` inherits the button's theme and expanse.
:::

Below are examples of buttons with text to the left and right, as well as a button with only an icon:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/buttonicon?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonIconView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/button/Icon.txt'
height="200px"
/>

### Names

The `Button` component utilizes naming, which is used for accessibility. When a name is not explicitly set, the label of the `Button` will be used instead. However, some icons do not have labels, and only display non-text elements, such as icons. In this case, it is expedient to use the `setName()` method to ensure that the `Button` component created complies with accessibility standards.

## Disabling a button

Button components, like many others, can be disabled to convey to a user that a certain action is not yet or is no longer available. A disabled button will decrease the opacity of the button, and is available for all button themes and expanses.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/buttondisable?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonDisableView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/button/Disable.txt'
/>

<br />

Disabling a button can be done at any time in the code by using the <JavadocLink type="foundation" location="com/webforj/component/HasEnable" code='true'>setEnabled(boolean enabled)</JavadocLink> function. For added convenience, a button can also be disabled when clicked using the built-in <JavadocLink type="foundation" location="com/webforj/component/button/Button" code='true' suffix='#setDisableOnClick(java.lang.Boolean)'>setDisabledOnClick(boolean enabled)</JavadocLink> function.

In some applications, clicking a button triggers a long-running action. In most cases, the application might want to ensure that only a single click is processed.  This can be an issue in high-latency environments when the user clicks the button multiple times before the application has had a chance to start processing the resulting action. 

:::tip
Disabling on click not only helps optimize the processing of actions, but also prevents the developer from needing to implement this behavior on their own, as this method has been optimized to reduce round trip communications.
:::

## Styling

### Themes

`Button` components come with <JavadocLink type="foundation" location="com/webforj/component/button/ButtonTheme">14 discrete themes </JavadocLink> built in for quick styling without the use of CSS. These themes are pre-defined styles that can be applied to buttons to change their appearance and visual presentation. They offer a quick and consistent way to customize the look of buttons throughout an application. 

While there are many use cases for each of the various themes, some examples uses are:

  - **Danger**: Best for actions with severe consequences, such as clearing filled-out information or permanently deleting an account/data.
  - **Default**: Appropriate for actions throughout an application that don't require special attention and are generic, such as toggling a setting.
  - **Primary**: Appropriate as a main "call-to-action" on a page, such as signing up, saving changes, or continuing to another page.
  - **Success**: Excellent for visualizing successful completion of an element in an application, such as the submission of a form or completion of a sign-up process. The success theme can by programmatically applied once a successful action has been completed.
  - **Warning**: Useful to indicate that a user is about to perform a potentially risky action, such as navigating away from a page with unsaved changes. These actions are often less impactful than those that would use the Danger theme.
  - **Gray**: Good for subtle actions, such as minor settings or actions that are more supplementary to a page, and not part of the main functionality.
  - **Info**: Good for providing additional clarifying information to a user.

Shown below are example buttons with each of the supported Themes applied: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/buttonthemes?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonThemesView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/button/Theme.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/button/buttonThemes.css'
height='175px'
/>

### Expanses
The following <JavadocLink type="foundation" location="com/webforj/component/Expanse"> Expanses values </JavadocLink> allow for quick styling without using CSS. This allows for manipulation of the Button's dimensions without having to explicitly set it using any styling. In addition to simplifying styling, it also helps create and maintain a uniformity in your application. The default `Button` expanse is `Expanse.MEDIUM`.

Different sizes are often appropriate for different uses:
  - **Larger** expanse values are suited to buttons which should grab attention, emphasize functionality or are integral to the core functionality of an application or page.
  - **Medium** expanse buttons, the default size, should be utilized as a "standard size", when a button's behavior is no more or less important than other similar components.
  - **Smaller** expanse values should be used for buttons that do not have integral behaviors in the application, and serve a more supplementary or utilitarian role, rather than play an important part in user interaction. This includes `Button` components being used only with icons for utilitarian purposes.

Below are the various expanses supported for the `Button` component: <br/>

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/buttonexpanses?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/button/ButtonExpansesView.java'
javaC='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/code_snippets/button/Expanses.txt'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/button/buttonExpanses.css'
height='200px'
/>

### Shadow parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Button} table='parts' exclusions={require('@site/static/exclusions.json').button.parts} />

### Slots

Listed below are the slots available for utilization within the `Button` component. These slots act as placeholders within the component that control where the children of a customized element should be inserted within the shadow tree.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Button} table='slots' exclusions={require('@site/static/exclusions.json').button.slots} />

### CSS properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Button} exclusions={require('@site/static/exclusions.json').button.properties} table='properties'/>

### Reflected attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Button} table="reflects" exclusions={require('@site/static/exclusions.json').button.reflects}/>

### Dependencies

This component relies on the following components - see the related article for more detailed styling information:

<TableBuilder tag='dwc-button' table="dependencies"/>

## Best practices 

To ensure an optimal user experience when using the `Button` component, consider the following best practices:

1. **Proper Text**: Use clear and concise text for text within your `Button` component to provide a clear indication of its purpose.

2. **Appropriate Visual Styling**: Consider the visual styling and theme of the `Button` to ensure consistency with your application's design. For example:
  > - A "Cancel" `Button` component should be styled with the appropriate theme or CSS styling to ensure users are sure they want to cancel an action
  > - A "Confirm" `Button` would have a different styling from a "Cancel" button, but would similarly stand out to ensure that users know this is a special action.

3. **Efficient Event Handling**: Handle `Button` events efficiently and provide appropriate feedback to users. Refer to [Events](../building-ui/events) to review efficient event adding behaviors.

4. **Testing and Accessibility**: Test the Button's behavior in different scenarios, such as when it is disabled or receives focus, to ensure a smooth user experience.
Follow accessibility guidelines to make the `Button` usable for all users, including those who rely on assistive technologies.
