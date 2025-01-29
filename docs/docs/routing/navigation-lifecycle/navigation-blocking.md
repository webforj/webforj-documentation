---
sidebar_position: 3
title: Navigation Blocking
---

Navigation blocking adds one or more layers of control to the entire underlying router API. If any blocking handlers are present, navigation will be prevented as follows:

If the navigation is triggered by something controlled at the router level, you can perform any task or show a UI prompt to the user to confirm the action. Each component implementing the `WillLeaveObserver` in the [route tree](../route-hierarchy/overview) will be called. The implementer must invoke `accept` to continue the navigation or `reject` to block it. If multiple components implement the `WillLeaveObserver` in the route's tree, the veto handlers will be executed sequentially in the reverse order

:::info Practical Example of Veto Handling
To see how vetoing works in practice, refer to the [Using Lifecycle Observers examples](observers#example-handling-unsaved-changes-with-willleaveobserver)
:::

For page events that can't be controlled directly, the router doesn't interfere or enforce a specific behavior. However, developers can still listen to the [`beforeunload`](https://developer.mozilla.org/en-US/docs/Web/API/Window/beforeunload_event) event to make a final attempt at warning the user about unsaved data if necessary.

```java
PageEventOptions options = new PageEventOptions();
options.setCode(""" 
  event.preventDefault();
  return true;
  """);
Page.getCurrent().addEventListener("beforeunload", e -> {}, options);
```

## Browser back button

The back button operates outside the control of web applications, making it difficult to intercept or prevent its action across all browsers consistently. Instead of trying to block the back button, it's more effective to design your UI/UX in a way that mitigates the impact. Consider strategies like saving unsaved data in [session storage](../../advanced/web-storage#session-storage), so if a user navigates away and returns, their progress is safely restored. This approach ensures data protection without relying on unreliable browser behavior.