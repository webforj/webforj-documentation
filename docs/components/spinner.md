---
title: Spinner
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-spinner" />


<JavadocLink type="spinner" location="com/webforj/component/spinner/Spinner" top='true'/>

The `Spinner` component provides a visual indicator that indicates ongoing processing or loading in the background. It's often used to show that the system is fetching data or when a process takes time to complete. The spinner offers user feedback, signaling that the system is actively working.

## Basics

To create a `Spinner`, you can specify the theme and expanse. The basic syntax involves creating a `Spinner` instance and defining its appearance and behavior through methods such as `setTheme()` and `setExpanse()`.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/spinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDemoView.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/spinnerstyles/spinnerdemo.css'
height = '225px'
/>

## Managing speed and pausing

It's possible to set the speed in milliseconds for the `Spinner` and pause/resume the animation with ease. 

Use cases for setting speeds include differentiating between loading processes. For example, faster `Spinners` are suitable for smaller tasks, while slower `Spinners` are better for larger tasks. Pausing is useful when user action or confirmation is required before continuing the process.

### Adjusting speed

You can control how fast the `Spinner` rotates by adjusting its speed in milliseconds using the `setSpeed()` method. A lower value makes the `Spinner` rotate faster, while higher values will slow it down.

```java
spinner.setSpeed(500); // Rotates faster
```

:::info Default Speed
By default, the `Spinner` will take 1000 milliseconds to complete one full rotation.
:::

### Pausing and resuming

Pausing the `Spinner` is useful when a program is temporarily halted or waiting for user input. It lets users know that the program is on hold, rather than actively running, which enhances clarity during multi-step processes.

To pause and resume the Spinner, use the `setPaused()` method. This is particularly helpful when you need to temporarily stop the spinning animation.      

```java
spinner.setPaused(true);  // Pause the spinner
spinner.setPaused(false); // Resume the spinner
```

This example shows how to go about setting the speed and how to pause/resume the `Spinner`:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/spinnerspeeddemo?'  
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerSpeedDemoView.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/spinnerstyles/spinnerspeeddemo.css'
height = '150px'
/>

## Spin direction

The direction of the `Spinner` can be controlled to rotate **clockwise** or **counterclockwise**. You can specify this behavior using the `setClockwise()` method.

```java
spinner.setClockwise(false);  // Rotates counterclockwise
spinner.setClockwise(true);   // Rotates clockwise
```

This option visually indicates a special state or serves as a unique design choice. Changing the spin direction can help differentiate between types of processes, such as progress vs. reversal, or provide a distinct visual cue in specific contexts.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/spinnerdirectiondemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerDirectionDemoView.java'
height = '150px'
/>

## Styling

### Themes

The `Spinner` component comes with several built-in themes that allow you to quickly apply styles without needing custom CSS. These themes change the visual appearance of the spinner, making it appropriate for different use cases and contexts. Using these predefined themes ensures consistency in styling throughout your app.

While spinners serve various situations, here are some example use cases for the different themes:

- **Primary**: Ideal for emphasizing a loading state that's a key part of the user flow, such as while submitting a form or processing an important action.
  
- **Success**: Useful to represent successful background processes, such as when a user submits a form and the app is performing the final steps of the process.
  
- **Danger**: Use this for risky or high-stakes operations, like deleting important data or making irreversible changes, where a visual indicator of urgency or caution is necessary.
  
- **Warning**: Use this to indicate a cautionary or less urgent process, such as when the user waits for data validation, but doesn't require immediate action.

- **Gray**: Works well for subtle background processes, such as low-priority or passive loading tasks, like when fetching supplemental data that doesn't directly impact the user experience.
  
- **Info**: Suitable for loading scenarios where you're providing additional information or clarification to the user, such as displaying a spinner alongside a message that explains the ongoing process.

You can apply these themes programmatically to the spinner, providing visual cues that align with the context and importance of the operation.

You can specify this behavior using the `setTheme()` method.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/spinnerthemedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerThemeDemoView.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/spinnerstyles/spinnerthemedemo.css'
height = '100px'
/>

### Expanses

You can adjust the size of the spinner, known as **expanse**, to fit the visual space you need. The spinner supports various sizes including `Expanse.SMALL`, `Expanse.MEDIUM`, and `Expanse.LARGE`.

<ComponentDemo 
path= 'https://demo.webforj.com/webapp/controlsamples/spinnerexpansedemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/spinner/SpinnerExpanseDemoView.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/spinnerstyles/spinnerexpansedemo.css'
height = '100px'
/>

### Shadow parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Spinner} table='parts' exclusions=''/>

### CSS properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Spinner} exclusions='' table='properties'/>

### Reflected attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.


<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Spinner} table="reflects"/>

