---
title: DesktopNotification
sidebar_position: 29
sidebar_class_name: new-content
---

<DocChip chip='since' label='25.00' />
<JavadocLink type="desktop-notification" location="com/webforj/component/desktopnotification/DesktopNotification" top='true'/>

In webforj 25.00 and higher, The **DesktopNotification** component provides a simple interface for creating, displaying, and managing desktop notifications. With a focus on minimal configuration and built-in event handling, the component can be used when needing to notify users about real-time events (such as new messages, alerts, or system events) while they're browsing your app.

:::warning experimental feature
The `DesktopNotification` component is still evolving, and its API may experience changes as it matures. To start using this feature, ensure that you include the following dependency in your pom.xml.

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-desktop-notification</artifactId>
</dependency>
```
:::

:::info Prerequisites
Before integrating the `DesktopNotification` component, ensure that:

- Your app runs in a **secure context** (HTTPS).
- The browser isn't in incognito or private browsing mode.
- The user has interacted with the app (e.g., clicked a button or pressed a key), since notifications require a user gesture to be displayed.
- The user has granted permissions for notifications (this will be requested automatically if needed).
:::

<div class="videos-container">
  <video controls>
    <source src="https://cdn.webforj.com/webforj-documentation/video/release/desktop_notifications.mp4" type="video/mp4"/>
  </video>
</div>

## Basic usage

There are multiple ways to create and display a notification. In most scenarios, the simplest approach is to call one of the static `show` methods which encapsulate the full notification lifecycle.

### Example: Displaying a basic notification

```java
// Basic notification with title and message
DesktopNotification.show("Update Available", "Your download is complete!");
```

This one-liner creates a notification with a title and body, then attempts to display it.

## Customizing the notification

There are various options for customizing the look and feel of the displayed notification, depending on needs of the app and the purpose of the notification. 

### Setting a custom `Icon`

By default, the notification uses your defined app icon through the [icons protocol](../managing-resources/assets-protocols#the-icons-protocol). You can set a custom icon using the `setIcon` method. The component supports different URL schemes:

- [`context://`](../managing-resources/assets-protocols#the-context-protocol): Resolved as a context URL pointing to the app’s resource folder; image is base64-encoded.
- [`ws://`](../managing-resources/assets-protocols#the-webserver-protocol): Resolved as a web server URL, giving a fully qualified URL.
- [`icons://`](../managing-resources/assets-protocols#the-icons-protocol): Resolved as an icons URL.

**Example:**

```java
// Creating a notification with a custom icon URL
DesktopNotification notification = new DesktopNotification(
  "Reminder", "Meeting starts in 10 minutes."
);
notification.setIcon("context://images/custom-icon.png");
notification.open();
```

## Notification events

The `DesktopNotification` supports several lifecycle events, and listeners can be attached to handle events, like when a notification is shown, closed, clicked, or encounters an error.

| Event                  | Description                                           | When to Use                                               |
|-----------------------------|-------------------------------------------------------|-----------------------------------------------------------|
| **Open** | Triggered when the notification is displayed.       | Log notification display, update UI, track engagement.    |
| **Close**| Triggered when the notification is closed.         | Cleanup resources, log dismissals, execute follow-up actions.|
| **Error**| Triggered when an error occurs with the notification or the user didn't grant permission.| Handle errors gracefully, notify the user, apply fallbacks.  |
| **Click**| Triggered when the user clicks on the notification. | Navigate to a specific section, log interactions, refocus the app. |


```java
DesktopNotification notification = new DesktopNotification("Alert", "You have a new message!")

// Attach an event listener for the open event
notification.onOpen(event -> {
  System.out.println("Notification was opened by the user.");
});

// Similarly, listen for the click event
notification.onClick(event -> {
  System.out.println("Notification clicked.");
});
```

:::warning Click Behavior
Browser security policies prevent the notification click event from automatically bringing your app window or tab into focus. This behavior is enforced by the browser and can not be overridden programmatically. If your app requires the window to be focused, you will need to instruct users to click within the app after interacting with the notification.
:::

## Security and compatibility considerations

When using the **DesktopNotification** component, keep the following points in mind:

- **Security Context:** Your app must be served over HTTPS to ensure that notifications are allowed by most modern browsers.
- **User Gesture Requirement:** Notifications are only displayed after a user-triggered action. Simply loading a page won't trigger a notification.
- **Browser Limitations:** Not all browsers handle custom icons or focus behavior the same way. For example, custom icons might not work in Safari, while event behavior may vary in other browsers.
- **Permissions:** Always verify that your app checks for and requests notification permissions from the user gracefully.

## Usage best practices

Keep the following best practices in mind while using the `DesktopNotification` component in your app:

- **Inform Your Users:** Let users know why notifications are needed and how they can benefit from them.
- **Provide Fallbacks:** Since some browsers may restrict notifications, consider alternative ways of alerting users (e.g., in-app messages).
- **Error Handling:** Always register an error listener to gracefully manage scenarios where notifications fail to display.