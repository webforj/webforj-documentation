---
title: Browser History
---

The `BrowserHistory` class in webforJ provides a high-level API to interact with the browser's history. Browser history allows web applications to keep track of the user's navigation within the app. By leveraging browser history, developers can enable features like back and forward navigation, state preservation, and dynamic URL management without requiring full-page reloads.

## Navigating through history

Managing the browser's history is a core feature for of most web apps. The `BrowserHistory` API enables developers to control how users navigate through the pages and states of their applications, mimicking or altering the standard browser behavior.

### Initializing or retrieving a history instance

To use the `BrowserHistory` API, you have two main options for obtaining a history instance:

1) **Creating a new history object**: If you are working independently of a routing context, you can create a new instance of the `BrowserHistory` class directly.

```java
BrowserHistory history = new BrowserHistory();
```
This approach is suitable for scenarios where you need to manage history explicitly outside of a routing framework.

2) **Retrieving the history from the `Router`** : If your app uses webforJ's [routing solution](../routing/overview), the `Router` component creates and manages a shared `BrowserHistory` instance. You can access this instance directly from the router, ensuring a consistent history management approach across your app.

```java
BrowserHistory history = Router.getCurrent().getHistory();
```
This method is recommended when your app relies on routing, as it maintains consistency in history management across all views and navigation actions.

### Managing history
The following methods can be used for history navigation in a webforJ app:

- `back()`: Moves the browser history back by one step, simulating a user pressing the back button in their browser. If there are no more entries in the history stack, it stays on the current page.

```java
history.back();
```

- `forward()`: Moves the browser history forward by one step, simulating a user pressing the forward button in their browser. This works only if there’s an entry ahead in the history stack.

  ```java
  history.forward();
  ```

- `go(int index)`: Navigates to a specific point in the history stack based on an index. A positive number moves forward, a negative number moves backward, and zero reloads the current page. This method provides more granular control compared to `back()` and `forward()`.

  ```java
  history.go(-2); // Moves back by two entries in the history stack
  ```

- `size()`: Retrieves the total number of entries in the history stack. This can be useful for understanding the user's navigation path or for implementing custom navigation controls.

  ```java
  int historySize = history.size();
  System.out.println("History Length: " + historySize);
  ```

- `getLocation()`: Returns the current URL path relative to the apps's origin. This method helps developers fetch the current path, which is useful for managing URL-based routing in single-page applications.

  ```java
  Optional<Location> location = history.getLocation();
  location.ifPresent(loc -> System.out.println("Current Path: " + loc.getFullURI()));
  ```

Understanding how to navigate efficiently is the cornerstone of building dynamic applications. Once you have the fundamentals of navigation, it's essential to know how to access and update the URLs associated with these navigation events.

## Accessing and updating URL

A core aspect of navigating and managing browser history is being able to access and update the current URL path efficiently. This is essential in modern web apps, where URL changes correspond to different views or states within the app. The `BrowserHistory` API offers a simple way to retrieve and manipulate the current path relative to the app's root.

:::tip webforJ `Router`
See the [`Router` article](../routing/overview) to learn more about comprehensive URL and route management
:::

`getLocation()` retrieves the current URL path relative to the app's origin. The `getLocation()` method returns an `Optional<Location>`, allowing you to obtain the path portion of the URL without the domain.

```java
Optional<Location> location = history.getLocation();
location.ifPresent(loc -> System.out.println("Current Path: " + loc.getFullURI()));
```

Accessing and manipulating URLs programmatically can help maintain the integrity of your app's navigation logic and support deep linking. With a clear understanding of URL management, the next step is to focus on effectively managing the state associated with these URLs.

## Managing state

`BrowserHistory` lets you save and manage custom state information using `pushState()` and `replaceState()` methods. By using state management methods, you can control what information is stored as part of the history entry, which helps in maintaining a consistent user experience when navigating back and forth within your app. The following methods can be used to manage state in your webforJ app.

- `pushState(Object state, Location location)`: Adds a new entry to the history stack. Accepts a state object and a `Location` object representing the new URL.

```java
Location location = new Location("/new-page");
history.pushState(myStateObject, location);
```

- `replaceState(Object state, Location location)`: Replaces the current history entry. This doesn't create a new entry in the stack like the above method.

```java
Location location = new Location("/updated-page");
history.replaceState(myStateObject, location);
```

- `getState(Class<T> classOfT)`: Retrieves the state object associated with the current history entry. This method returns an Optional containing the state object, which is deserialized into the specified class.

```java
Optional<MyState> currentState = history.getState(MyState.class);
currentState.ifPresent(state -> System.out.println("Current Page: " + state.getViewName()));
```

### Listening for state changes
The `BrowserHistory` class provides the ability to register event listeners that respond to changes in the history state.

The `addHistoryStateChangeListener(EventListener<HistoryStateChangeEvent> listener)` registers a listener that gets triggered when the state changes, such as when the user clicks the browser's back or forward buttons. This method sets up a listener for the browser's `popstate` event, allowing your app to respond to user actions or programmatically triggered state changes.


```java
history.addHistoryStateChangeListener(event -> {
    System.out.println("History state changed to: " + event.getLocation().getFullURI());
});
```

Effectively managing state allows you to create apps that respond dynamically to user actions. Users can navigate through your app without losing context, making for a smoother and more intuitive experience. Additionally, saving state enables advanced features like restoring view positions, maintaining filter or sort settings, and supporting deep linking—all of which contribute to a more engaging and reliable app.


## History size

Understanding the size of the browser’s history stack can be useful when building features that depend on the user's navigation path. For example, you may want to <!-- vale off -->disable <!-- vale on -->certain navigation actions if there are no more entries to navigate to, or display a breadcrumb trail based on the history size.

The `size()` method retrieves the total number of entries in the history stack.

```java
int historySize = history.size();
System.out.println("History Length: " + historySize);
```