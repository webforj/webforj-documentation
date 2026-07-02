---
sidebar_position: 7
title: Execute JavaScript
sidebar_class_name: new-content
description: Run client-side JavaScript from Java with executeJs, executeJsAsync, and executeJsVoidAsync at the app or element level.
slug: execute-javascript
---

import JavadocLink from '@site/src/components/DocsTools/JavadocLink';

<JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" top='true'/>

webforJ runs on the server, but there are times you need to reach the client: scroll the window, focus a field, read a browser value, or call a method on a web component. The <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> interface provides that bridge. It's implemented at two levels:

- The [`Page`](#app-level-execution) runs script in the context of the whole page.
- An [`Element`](#element-level-execution) runs script scoped to a single client element.

Both expose the same three methods, so once you know the shapes below, they read the same whether you call them on `Page` or an `Element`.

## Execution methods {#execution-methods}

Each level offers a synchronous method and two asynchronous ones. The difference is whether the calling thread waits and whether a result comes back.

1. **`executeJs(String script)`**: runs the script synchronously. The **executing thread is blocked** until the client returns, which costs one server-to-client round trip. The result comes back as an `Object` you can cast and use in Java.

2. **`executeJsAsync(String script)`**: runs the script asynchronously and **doesn't block the executing thread**. It returns a <JavadocLink type="foundation" location="com/webforj/PendingResult" code='true'>PendingResult</JavadocLink> that completes when the script finishes, so you can react to the result later.

3. **`executeJsVoidAsync(String script)`**: runs the script asynchronously and returns nothing to the server. Use it for fire-and-forget work where you don't need the result. Available since `24.11`.

:::tip Choosing a method
Reach for `executeJsVoidAsync` by default when you are only causing a side effect on the client (scrolling, focusing, calling a method). Use `executeJsAsync` when you need the value but want to stay non-blocking, and reserve the synchronous `executeJs` for the rare case where you must have the result before the next line of Java runs, since it holds the thread for a full round trip.
:::

### Reading results {#reading-results}

When a script returns a value, webforJ converts it to the matching Java type:

| JavaScript value        | Java type                            |
| ----------------------- | ------------------------------------ |
| number                  | `Integer`, `Long`, or `Double`       |
| string                  | `String`                             |
| boolean                 | `Boolean`                            |
| `null` or `undefined`   | `null`                               |
| any other type          | its string representation            |

Read values with `executeJsAsync`, which applies the conversion reliably. A returned number can arrive as `Integer`, `Long`, or `Double`, so read it through `Number`:

```java
Page.getCurrent()
    .executeJsAsync("return window.innerWidth;")
    .thenAccept(result -> {
      int width = ((Number) result).intValue();
      // use width
    });
```

:::warning Prefer the async form when you need the value
The synchronous `executeJs` returns `null` when the execution context isn't ready, for example when it's called before the component is attached. Use `executeJsAsync` whenever you depend on the returned value, and avoid casting a synchronous result to a specific type.
:::

## App-level execution {#app-level-execution}

Call the methods on <JavadocLink type="foundation" location="com/webforj/Page" code='true'>Page</JavadocLink> when the script concerns the page as a whole rather than one component. Get the current page with `Page.getCurrent()`.

A common case is scrolling back to the top after a route change. Nothing needs to come back, so `executeJsVoidAsync` fits:

```java
Page.getCurrent().executeJsVoidAsync(
    "window.scrollTo({ top: 0, behavior: 'smooth' });");
```

When you need a client value on the server, read it asynchronously and act on the result when it arrives:

```java
Page.getCurrent()
    .executeJsAsync("return navigator.language;")
    .thenAccept(language -> {
      // language is the browser locale, for example "en-US"
      applyLocale(String.valueOf(language));
    });
```

:::info Page versus element scope
Use [element-level execution](#element-level-execution) when the script needs to act on a specific client element rather than the page as a whole.
:::

In the demo below, selecting **Copy link** runs a script through `Page` with `executeJsVoidAsync` to write the invite link to the visitor's clipboard. Copying is a side effect with nothing to return, so the fire-and-forget method is the right fit.

<ComponentDemo
path='/webforj/executejavascript'
files={[
  'src/main/java/com/webforj/samples/views/javascript/ExecuteJavaScriptView.java',
]}
height='260px'
/>

## Element-level execution {#element-level-execution}

Calling the same methods on an <JavadocLink type="foundation" location="com/webforj/component/element/Element" code='true'>Element</JavadocLink> scopes the script to that element instead of the page. The return values and the synchronous and asynchronous behavior match the preceding page-level methods.

Element scripts queue until the element is attached to the DOM, then run, so you can call them during setup without waiting for attachment yourself.