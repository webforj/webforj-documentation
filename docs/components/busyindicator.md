---
title: BusyIndicator
---

<DocChip tooltipText="This component will render with a shadow DOM, an API built into the browser that facilitates encapsulation." label="Shadow" component="a" href="../glossary#shadow-dom" target="_blank" clickable={true} iconName="shadow" />


<DocChip tooltipText="The name of the web component that will render in the DOM." label="dwc-loading" clickable={false} iconName='code'/>


<JavadocLink type="foundation" location="com/webforj/BusyIndicator" top='true'/>

The `BusyIndicator` provides visual cues to ensure that users are aware of ongoing processes, preventing them from interacting with the system prematurely. It typically covers the entire app interface for global operations.

While the [`Loading`](../components/loading) component focuses on specific areas or components within the app, the `BusyIndicator` handles global, app-wide processes and blocks interaction across the entire interface. This difference in scope makes the [`Loading`](../components/loading) component ideal for more localized, component-specific scenarios, such as loading data in a particular section of a page. In contrast, the `BusyIndicator` is suitable for system-wide operations that affect the entire app, such as initializing the app or performing a major data sync.

## Basics

The `BusyIndicator` in webforJ displays as a simple spinner, making it easy to use without configuration. However, you can customize it by adding a message, adjusting the spinner's theme, or modifying visibility settings. This allows you to provide more context or style while maintaining a functional, out-of-the-box solution.

In this example, the `BusyIndicator` prevents any user actions across the entire interface until the operation completes.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/busydemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusyDemoView.java'
height = '300px'
/>

## Backdrops

The `BusyIndicator` component in webforJ allows you to display a backdrop to block user interaction while a process is ongoing. By default, the component enables the backdrop, but you have the option to turn it off if needed.

The `BusyIndicator` shows a backdrop by default. You can control the visibility of the backdrop using the `setBackdropVisible()` method, as shown below:

```java
BusyIndicator busyIndicator = getBusyIndicator();
busyIndicator.setBackdropVisible(false);  // Disables the backdrop
busyIndicator.open();
```
:::info Turning Off Backdrop
Even when you turn off the backdrop, the `BusyIndicator` component continues to block user interaction to ensure the underlying process completes uninterrupted. The backdrop simply controls the visual overlay, not the interaction blocking behavior.
:::

## `Spinner`

The `BusyIndicator` component in webforJ includes a `Spinner` that visually indicates a background operation is in progress. You can customize this spinner with several options, including its size, speed, direction, theme, and visibility.

Here's an example of how you can customize the spinner within a `BusyIndicator` component:

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples/busyspinnerdemo?' 
javaE='https://raw.githubusercontent.com/webforj/webforj-docs-samples/refs/heads/main/src/main/java/com/webforj/samples/views/busyindicator/BusySpinnerDemoView.java'
height = '200px'
/>

## Use cases
- **Page-Wide Processing**  
   The `BusyIndicator` is well-suited for larger, page-wide operations, such as when a user initiates a task that affects the entire page, like uploading a file or processing data across multiple sections. It can inform users that the entire app is working, preventing further interaction until the process is complete.

- **Critical System Operations**  
   When performing system-critical tasks such as syncing data, applying system-wide updates, or processing sensitive information, the `BusyIndicator` gives clear visual feedback that a major operation is ongoing, allowing the user to wait until it’s completed.

- **Asynchronous Data Loads**  
   In scenarios where asynchronous data processing is involved, such as when calling multiple APIs or waiting for complex computations, the `BusyIndicator` component actively indicates that the system is busy, prompting users to wait before performing additional actions.

## Styling 

### Shadow Parts

These are the various parts of the [shadow DOM](../../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').BusyIndicator} table='parts' exclusions=''/>

### CSS Properties

These are the various CSS properties that are used in the component, with a short description of their use.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').BusyIndicator} exclusions='' table='properties'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.


<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').BusyIndicator} table="reflects"/>


