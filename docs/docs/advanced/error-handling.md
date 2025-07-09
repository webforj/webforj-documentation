---
title: Error Handling
---

Error handling is a crucial aspect of developing robust web apps. In webforJ, error handling is designed to be flexible and customizable, allowing developers to handle exceptions in a way that best suits their app's needs.

## Overview

In webforJ, error handling revolves around the `ErrorHandler` interface. This interface allows developers to define how their app should respond when exceptions occur during execution. By default, webforJ provides a `GlobalErrorHandler` that handles all exceptions in a generic way. However, developers can create custom error handlers for specific exceptions to provide more tailored responses.

## Discovering and using error handlers

webforJ uses Java's Service Provider Interface (SPI) to discover and load error handlers.

### Discovery process

1. **Service Registration**: Error handlers are registered via the `META-INF/services` mechanism.
2. **Service Loading**: On app startup, webforJ loads all classes listed in `META-INF/services/com.webforj.error.ErrorHandler`.
3. **Error Handling**: When an exception occurs, webforJ checks if an error handler exists for that specific exception.

### Handler selection

- If a specific handler for the exception exists, it is used.
- If no specific handler is found, but a custom global error handler `WebforjGlobalErrorHandler` is defined, it is used.
- If neither is found, the default `GlobalErrorHandler` is used.

## The `ErrorHandler` Interface

The `ErrorHandler` interface is designed to handle errors that occur during the execution of a webforJ app. Applications that want to manage specific exceptions should implement this interface.

### Methods

- **`onError(Throwable throwable, boolean debug)`**: Called when an error occurs. This method should contain the logic for handling the exception.
- **`showErrorPage(String title, String content)`**: A default method that displays the error page with the given title and content.

### Naming convention

The implementing class must be named after the exception it handles, with the suffix `ErrorHandler`. For example, to handle `NullPointerException`, the class should be named `NullPointerExceptionErrorHandler`.

### Registration

The custom error handler must be registered in the `META-INF/services/com.webforj.error.ErrorHandler` file so that webforJ can discover and utilize it.

## Implementing a custom error handler

The following steps detail the implementation of a custom error handler for a specific exception:

### Step 1: Create the error handler class

Create a new class that implements the `ErrorHandler` interface and is named after the exception it handles.

```java
package com.example.error;

import com.webforj.error.ErrorHandler;

public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Custom handling logic for NullPointerException
    String title = "Null Pointer Exception";
    String content = "A null value was encountered where an object is required.";

    showErrorPage(title, content);
  }
}
```

:::info `showErrorPage()` method
The `showErrorPage` method is a utility method that utilizes the webforJ API to send the provided HTML content and page title to the browser, displaying an error page. When an exception occurs and the app is unable to recover, it becomes impossible to use webforJ components to build a custom error page. However, the `Page` API remains accessible, allowing the developer to redirect or display an error page as a final attempt.
:::

### Step 2: Register the error handler

Create a file named `com.webforj.error.ErrorHandler` inside the `META-INF/services` directory of your app. Add the fully qualified name of your error handler class to this file.

**File**: `META-INF/services/com.webforj.error.ErrorHandler`

```
com.example.error.NullPointerExceptionErrorHandler
```

Now, whenever a `NullPointerException` is thrown, webforJ selects your registered handler and execute its logic to handle the error.

## Using `AutoService` to simplify registration

It’s easy for developers to forget to update or correctly specify service descriptors. By using Google's `AutoService`, you can automate the generation of the `META-INF/services/com.webforj.error.ErrorHandler` file. All you need to do is annotate the error handler with the `AutoService` annotation. You can learn more about [AutoService here](https://github.com/google/auto/blob/main/service/README.md).

```java
@AutoService(ErrorHandler.class)
public class NullPointerExceptionErrorHandler implements ErrorHandler {

  @Override
  public void onError(Throwable throwable, boolean debug) {
    // Custom handling logic for NullPointerException
    String title = "Null Pointer Exception";
    String content = "A null value was encountered where an object is required.";

    showErrorPage(title, content);
  }
}
```

## The `GlobalErrorHandler` class

The `GlobalErrorHandler` is the default error handler provided by webforJ. It implements the `ErrorHandler` interface and provides generic error handling.

### Behavior

- **Logging**: Errors are logged to both the server and browser consoles.
- **Error Page Display**: Depending on the debug mode, the error page displays the stack trace or a generic error message.

### Defining a custom global error handler

To define a global error handler, you need to create a new error handler named `WebforjGlobalErrorHandler`. then follow [the steps to register error handlers](#step-2-register-the-error-handler) as explained previously. In this case, webforJ first look for any custom error handlers to manage exceptions. If none are found, webforJ falls back to the custom global error handler.

:::info
If multiple `WebforjGlobalErrorHandler` are registerd then webforJ selects the first one 
:::