---
title: Toast
---

<DocChip chip="shadow" />

<DocChip chip="name" label="dwc-toast" />

<JavadocLink type="toast" location="com/webforj/component/toast/Toast" top='true'/>

A `Toast` notification is a subtle and unobtrusive pop-up notification designed to provide users with real-time feedback and information. These notifications are typically used to inform users about operations such as successful actions, warnings, or errors without interrupting their workflow. `Toast` notifications typically disappear after a set amount of time and don't require a user response.

With webforJ's `Toast` component, you can easily implement these notifications to enhance user experience by providing relevant information in a familiar, non-intrusive, and seamless manner. 

## Basics

webforJ provides a quick and easy way to create a `Toast` component in a single line of code with the `Toast.show()` method, which creates a `Toast` component, adds it to the `Frame`, and displays it. You can pass parameters to the `show` method to configure the displayed `Toast`:

```java
Toast.show("Operation completed successfully!", Theme.SUCCESS);
```


If you want more fine-grained control over the component, you can also create a `Toast` with a standard constructor and use the `open()` method to display it.

```java
Toast toast = new Toast("Operation completed successfully!", 3000, Theme.SUCCESS, Placement.TOP);
toast.open();
```

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.toastdemos.ToastDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/componentdemos/toastdemos/ToastDemo.java'
height='200px'
/>

## Usages

The `Toast` component provides a subtle, non-intrusive notification to inform users of various system states or actions. It enhances the user experience by offering:

- **Real-time feedback** for actions like form submissions, data saves, or errors.
- **Customizable themes** for differentiating between success, error, warning, or informational messages.
- **Flexible placement** options to show notifications in different areas of the screen without interrupting the user’s workflow.

One common use case is a **Cookies Consent Notification**, where users can interact with the notification to accept or customize their cookie preferences.

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.toastdemos.ToastCookiesDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/componentdemos/toastdemos/ToastCookiesDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/toaststyles/toastcookiesdemo.css'
height='350px'
/>

## Duration

You can configure `Toast` notifications to disappear after a set duration or persist on the screen until they are dismissed, depending on your needs. By default, a `Toast` closes automatically after 3000 milliseconds. You can customize the duration with the `setDuration()` method, or simply supply a duration parameter to the constructor or the `show()` method.

:::info Default Duration
By default, the `Toast` will have a duration of 3000 milliseconds.
:::

```java
Toast toast = new Toast("Sample Notification");
toast.setDuration(5000);
toast.open();
```

### Persistent toasts

You can create a persistent `Toast` by setting a negative duration. Persistent `Toast` notifications won't close automatically, which can be useful for critical alerts or in cases where some interaction or acknowledgement is required from the user.

:::caution
Be careful with persistent `Toast` notifications, and make sure to provide a way for the user to dismiss the notification. Use the `close()` method to hide the `Toast` once the user has acknowledged it or completed any required interaction.
:::

```java
Toast toast = new Toast("Operation completed successfully!", -1, Theme.SUCCESS, Placement.TOP);
toast.open();
```

## Placement

With webforJ's `Toast` component, you can choose where the notification appears on the screen to suit your app's design and usability requirements. By default, `Toast` notifications appear at the bottom center of the screen. 

You can set the `placement` of a Toast notification with the `setPlacement` method using the `Toast.Placement` enum with one of the following values:

- **BOTTOM**: Places the notification at the bottom center of the screen.
- **BOTTOM_LEFT**: Places the notification at the bottom-left corner of the screen.
- **BOTTOM_RIGHT**: Places the notification at the bottom-right corner of the screen.
- **TOP**: Places the notification at the top center of the screen.
- **TOP_LEFT**: Places the notification at the top-left corner of the screen.
- **TOP_RIGHT**: Places the notification at the top-right corner of the screen.

These options allow you to control the placement of the `Toast` notification based on your app's design and usability needs.

```java
Toast toast = new Toast("Sample Notification");
toast.setPlacement(Toast.Placement.TOP_LEFT);
toast.open();
```

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.toastdemos.ToastPlacementDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/componentdemos/toastdemos/ToastPlacementDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/toaststyles/toastplacementdemo.css'
height='300px'
/>

By customizing the placement of your `Toast` notifications, you can ensure that users receive information in a way that is appropriate for any given app, screen layout, and context.

### Stacking

The `Toast` component can display multiple notifications simultaneously, stacking them vertically based on their placement. Newer notifications appear closer to the placement edge, pushing older notifications farther away. This ensures that users don't miss important information, even when there is a lot going on.

## Actions and Interactivity

Although `Toast` notifications don't require user interaction by default, webforJ allows you to add buttons or other interactive elements to make them more useful than simple notifications. 

By adding this kind of interactivity, you can give users the ability to handle tasks and perform actions without navigating away from their current screen, transforming a `Toast` notification into a valuable channel of interaction and engagement. 

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.toastdemos.ToastInteractiveDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/componentdemos/toastdemos/ToastInteractiveDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/toaststyles/toastinteractivedemo.css'
height='300px'
/>

## Styling

You can style `Toast` notifications with themes just like other webforJ components, providing users with valuable context about the type of information being shown, and creating a consistent style throughout your app. You can either set the theme when you create the Toast or use the `setTheme()` method.

```java
Toast toast = new Toast("Sample Notification", Theme.INFO);
```

```java
Toast toast = new Toast("Sample Notification");
toast.setTheme(Theme.INFO);
```

### Custom Themes

In addition to using built-in themes, you can create your own custom themes for `Toast` notifications. This allows for a more personalized and branded user experience, giving you full control over the overall styling of the `Toast`.

To add a custom theme to a `Toast`, you can define custom CSS variables, which modify the appearance of the component. The following example demonstrates how to create a `Toast` with a custom theme using webforJ.

:::info `Toast` Targeting
Since the `Toast` isn't located in a specific position in the DOM, you can target it using CSS variables. These variables make it easy to apply consistent custom styles across all Toast notifications.
:::

<ComponentDemo 
path='https://demo.webforj.com/webapp/controlsamples?class=componentdemos.toastdemos.ToastThemeDemo' 
javaE='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/java/demos/componentdemos/toastdemos/ToastThemeDemo.java'
cssURL='https://raw.githubusercontent.com/webforj/ControlSamples/main/src/main/resources/css/toaststyles/toastthemedemo.css'
height='200px'
/>

### Shadow Parts

These are the various parts of the [shadow DOM](../glossary#shadow-dom) for the component, which will be required when styling via CSS is desired.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Toast} table='parts'/>

### Reflected Attributes

The reflected attributes of a component will be shown as attributes in the rendered HTML element for the component in the DOM. This means that styling can be applied using these attributes.

<TableBuilder tag={require('@site/docs/components/_dwc_control_map.json').Toast} table="reflects" />