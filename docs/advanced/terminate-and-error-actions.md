---
title: Terminate and Error Actions
---

When developing applications with the webforJ, it's essential to define how your app behaves when it terminates or encounters an error. The framework provides mechanisms to customize these behaviors through `terminate` and `error` actions.

This article explains how to configure `terminate` and `error` actions in your app, the default behaviors, and how to customize them to fit your needs.

## Overview

The `App` class allows you to define actions that execute when the app terminates normally or when it encounters an error. These actions are instances of the `AppCloseAction` interface and can be set using:

- `setTerminateAction(AppCloseAction action)`: Sets the action to execute upon normal termination.
- `setErrorAction(AppCloseAction action)`: Sets the action to execute when an error occurs.

Available implementations of `AppCloseAction` include:

- `DefaultAction`: Clears the browser and displays a localized message prompting the user to reload the app.
- `NoneAction`: Performs no action, effectively resetting any previously set action.
- `MessageAction`: Displays a custom link message.
- `RedirectAction`: Redirects the user to a specified URL.

:::info Distinguishing Termination Actions from Error Actions in webforJ
webforJ doesn't treat termination due to a thrown or unhandled exception as an error action, but rather as a termination action because the app shuts down normally. An error action occurs when the app receives a termination signal due to an external error, such as when the browser can't connect to the server running the app.
:::

## Default behavior

In webforJ version `24.11` and earlier, the app defaults to using `DefaultAction` for both termination and error events. This means that when the app terminates or encounters an error, the browser displays a message prompting the user to reload the app.

Starting from version `24.12`, webforJ defaults to `NoneAction` for both termination and error events. This change means that no action is taken when the app terminates or an error occurs, allowing webforJ to delegate error handling to an appropriate `ErrorHandler` if one is configured, or to fall back on its generic error handling mechanisms. By using `NoneAction`, the app avoids disrupting the default error handling flow, enabling you to define custom error handlers or rely on webforJ’s built-in error management.

## Customizing actions

To change the default behavior, use the `setTerminateAction()` and `setErrorAction()` methods in your `App` subclass.

### Setting a custom message action

If you want to display a custom message upon normal termination:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Set a custom message action
    setTerminateAction(new MessageAction(
        "Thank you for using our application!. Click to reload"
    ));
  }
}
```

### Setting a redirect action

To redirect the user to a specific URL upon normal termination:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Set a redirect action on error
    setTerminateAction(new RedirectAction(
        "https://example.com/error"
    ));
  }
}
```

## Terminating the app

You can programmatically terminate your app by calling the `terminate()` method:

```java
public class MyApp extends App {

  @Override
  public void run() throws WebforjException {
    // Terminate the application under certain conditions
    if (someCondition) {
      terminate();
    }
  }
}
```

Upon calling `terminate()`, the action defined by `setTerminateAction()` executes.

## Hooks for termination

The `App` class provides hook methods to perform actions before and after termination:

- `onWillTerminate()`: Called before termination.
- `onDidTerminate()`: Called after termination.

```java
public class MyApp extends App {

  @Override
  protected void onWillTerminate() {
    // Perform cleanup tasks
  }

  @Override
  protected void onDidTerminate() {
    // Actions after termination
  }
}
```

### Custom termination page 

In some cases, you might want to display a custom termination page when your app ends, providing users with a personalized message or additional resources. This can be achieved by overriding the `onDidTerminate()` method in your `App` subclass and injecting custom HTML into the page.

Here's an example of how to create a custom termination page:

```java
public class WebforjHelloWorld extends App {

  @Override
  public void run() throws WebforjException {
    setTerminateAction(new NoneAction());
    terminate();
  }

  @Override
  protected void onDidTerminate() {
    String html = """
    <div style="display: flex; justify-content: center; align-items: center; height: 100vh; flex-direction: column;">
        <h1>Thank you for using webforJ</h1>
        <p>For more information, visit <a href="https://webforj.com">webforj.com</a></p>
    </div>
    """;

    Page.getCurrent().executeJsVoidAsync(
        String.format("document.body.innerHTML = `%s`", html)
    );
  }
}
```