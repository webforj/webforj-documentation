---
title: Upgrade to 25.00
description: Upgrade from 24.00 to 25.00
pagination_next: null
---

This documentation serves as a guide to upgrade webforJ apps from 24.00 to 25.00.
Here are the changes needed for existing apps to continue running smoothly.
As always, see the [GitHub release overview](https://github.com/webforj/webforj/releases) for a more comprehensive list of changes between releases.


## Jetty 12 web servers

webforJ 25.00 and higher utilize Jetty 12, using the Jakarta EE10 servlet architecture. If you're using the Jetty Maven plugin for development, migrate from Jakarta EE8 to Jakarta EE10. This upgrade will also require replacing anything that relied on the `javax.servlet` package with the `Jakarta.servlet` package.

### POM file changes

**Before**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee8</groupId>
  <artifactId>jetty-ee8-maven-plugin</artifactId>
  <version>10.x.xx</version>
```
**After**

```xml {2-4}
<plugin>
  <groupId>org.eclipse.jetty.ee10</groupId>
  <artifactId>jetty-ee10-maven-plugin</artifactId>
  <version>12.x.xx</version>
```

## API changes for the `App` class

Several deprecated `App` methods are removed in 25.00. The following sections outline what methods were replaced and the recommended replacements.

### Console logging

The utility class [`BrowserConsole`](../advanced/browser-console.md), dedicated for creating styled logs to the browser console, replaces the `consoleLog()` and `consoleError()` methods. Get the `BrowserConsole` by using the `console()` method:

```java
public class Application extends App{
  
  @Override
  public void run() throws WebforjException {
    console().log("Log message");
    console().error("Error message");
  }
}
```

### Web storage

For versions prior to webforJ 25.00, the `App` class has the methods `getLocalStorage()`, `getSessionStorage()`, and `getCookieStorage()` to get instances of the `LocalStorage`, `SessionStorage` and `CookieStorage` classes respectively. Going forward, each class has a `getCurrent()` method.

See [Web Storage](../advanced/web-storage.md) for more information.

### `Request` class

The `Request` class is now responsible for getting an app's URL, port, host, and protocol. So instead of using `App.getUrl()`, use `App.getCurrent().getUrl()`. The `getCurrent()` method also replaces the `getRequest()` method to get an instance of the `Request` class.

:::info
The `Request` class also has removed methods, jump to [`Request`](#request-changes) to see them.
:::

### `Page` class

The `getPage()` method is replaced with `Page.getCurrent()` to get the current page instance.

### Option dialogs

Instead of using the `msgbox()` method, use [`OptionDialog.showMessageDialog()`](../components/option-dialogs/message) to create message dialogs.

### App termination

The `cleanup()` method has been removed. There are now two methods for terminations, `onWillTerminate()` and `onDidTerminate()`.

See [Hooks for termination](../advanced/terminate-and-error-actions.md#hooks-for-termination) for more information.

## Table sorting

For webforJ 25.00 and higher, tables use single-column sorting by default. Columns will only be sorted by the most recently selected column header. To make a table use multi-column sorting, invoke the [`setMultiSorting()`](../components/table/sorting#multi-sorting) method:

```java
table.setMultiSorting(true);
```

## Hidden `TabbedPane` body

The `hideBody()` method is replaced with `setBodyHidden()` to maintain a consistent naming convention for methods.

## Rendering HTML inside components

In webforJ 25.00 and higher, there is a `setHtml()` method to help distinguish between setting literal and HTML text inside a component.
Setting HTML using the `setText()` method is still possible, but now requires explicitly wrapping it with `<html>` tags.

```java
// Valid uses of setText() and setHtml()
Button home = new Button();

home.setText("""
  <html>
    <h1>Home</h1>
  </html>
""");

home.setHtml("<h1>Home</h1>");

home.setText("Home");
```

```java
// Invalid uses of setText() and setHtml()
Button home = new Button();
home.setText("<h1>Home</h1>");
```

## HTML containers

The `com.webforj.component.htmlcontainer` package is no longer in webforJ. Use the more feature-rich `com.webforj.component.element` package instead. For a list of webforJ classes for standard HTML Elements, go to [HTML Element Components](../building-ui/web-components/html-elements.md).

## `Request` changes

- Just like the removal of the `getCookieStorage()` method for the `App` class, `Request` no longer has the `getCookie()` method. This reinforces the use of `CookieStorgage.getCurrent()` to get an instance of the `CookieStorage` class.

- The `getQueryParam()` method is now `getQueryParameter()`.

## `WebforjBBjBridge` changes

### Getting an instance of `WebforjBBjBridge`

The `Environment` class no longer has the `getWebforjHelper()` method, so use `getBridge()` instead.

### Using the `ConfirmDialog` component for the `msgbox()` method

Previous versions of webforJ uses strings and integers directly for the `WebforjBBjBridge` `msgbox()` method. However, messages for `WebforjBBjBridge` in webforJ 25.00 and higher use the [`ConfirmDialog`](../components/option-dialogs/confirm.md) component. This gives more control for which buttons are displayed and the message type.


**Before**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getWebforjHelper();

int msgboxResult = bridge.msgbox("Are you sure you want to delete this file?", 1, "Deletion");
```

**After**
```java
Environment environment = Environment.getCurrent();
WebforjBBjBridge bridge = environment.getBridge();

ConfirmDialog dialog = new ConfirmDialog(
      "Are you sure you want to delete this file?", "Deletion",
      ConfirmDialog.OptionType.OK_CANCEL, ConfirmDialog.MessageType.QUESTION);

int msgboxResult = bridge.msgbox(dialog);
```

<!-- ## Environment.logError removed -->

## `PasswordMediation` typo correction

The enum class `PasswordMediation`, used for indicating whether a user is required to login every visit to an app with a `Login` compoent, has a typo in previous webfroJ versions. `SILENT` replaces the typo `SILIENT` for webforJ 25.00 and higher.

## Auto-focusing methods

To keep webforJ consistent, methods like `setAutofocus()` and `isAutofocus()` now have a uniform capitalization like the HasAutoFocus interface. So components like `Dialog` and `Drawer` use `setAutoFocus()` and `isAutoFocus()` for 25.00 and higher.

## `BBjWindowAdapter` and `Panel` marked as `final`

The `BBjWindowAdapter` and `Panel` classes are now declared as `final`, meaning they can no longer be subclassed. This change improves stability and enforces consistent usage patterns.

<GiscusComments />