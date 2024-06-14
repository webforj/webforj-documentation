---
sidebar_position: 30
title: Web Storage
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';


<JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" top='true'/>

## Overview
[Web storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) is a fundamental concept in web development that allows websites to store data on the client side. This enables web applications to save state, preferences, and other information locally on the user's browser, enhancing user experience and app performance.

Web storage is essential for creating interactive and dynamic web applications. It provides a way to persist data across page reloads and browser sessions, reducing the need for repeated server requests and enabling offline capabilities.

Web storage can be implemented with [**Cookies**](#cookies), [**Session Storage**](#session-storage), or [**Local Storage**](#local-storage).

:::tip
You can see current cookie, local storage and session storage key-value pairs in your browser's developer tools.
:::

### Summary of differences
| Feature            | Cookies                                      | Session Storage                          | Local Storage                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **Persistence**    | Configurable expiration date                 | Duration of the page session             | Persistent until explicitly deleted      |
| **Storage Size**   | ~4KB per cookie                              | Around 5-10MB                            | Around 5-10MB                            |
| **Use Cases**      | User authentication, preferences, tracking   | Temporary data, form data                | Persistent settings, user preferences    |
| **Security**       | Vulnerable to XSS, can be secured with flags | Cleared on session end, less risk        | Accessible via JavaScript, potential risk|


## Cookies
Cookies are small pieces of data stored on the client side and sent to the server with each HTTP request. They are often used to remember user sessions, preferences, and authentication information.

### Advantages:
- Can store data across different domains
- Support expiration dates

### Disadvantages:
- Small storage size (about 4KB)
- Sent with every HTTP request

:::info
By default, cookies in webforJ expire after 30 days. This can be change with the `max-age` or `expires` attributes.
<!-- Is this configurable? -->
:::

### Using cookies in webforJ

In the following code snippet, a <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> object is obtained using the `CookieStorage.getCurrent()` method to either create a new object or retrieve it if it already exists. A key/value pair with optional attributes is written to the cookie using the `add(key, value, attributes)` method, and the value is obtained using the `get(key)` method. The data can be removed from the Cookie with the `remove(key)` method.

```java
// Access cookie storage
CookieStorage cookieStorage = CookieStorage.getCurrent();

// Add a new cookie or update an existing cookie
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// Access a cookie with a given key
String username = cookieStorage.get("username");

// Remove a cookie with a given key
cookieStorage.remove("username");
```

### Use cases
The following use cases are well-suited for utilization of cookies:

- **User Authentication**: Store session tokens to keep users logged in.
- **Preferences**: Save user preferences, such as theme settings or language.
- **Tracking**: Collect information about user behavior for analytics.


## Session storage
Session storage stores data for the duration of a page session. The data is accessible only within the same session and is cleared when the page or tab is closed, but persists for reloads and restores. Session storage is best for storing temporary data during a single page session and maintaining state across different pages in the same session.

### Advantages
- Data is not sent with every HTTP request
- Larger storage size than cookies

### Disadvantages
- Data is lost when the page or tab is closed
- Data is not shared across tabs

### Using session storage in webforJ

In the following code snippet, a <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> object is obtained using the `SessionStorage.getCurrent()` method to either create a new object or retrieve it if it already exists. A key/value pair is written to the object using the `add(key, value)` method, and the value is obtained using the `get(key)` method. The data can be removed from the object with the `remove(key)` method.

```java
// Access session storage
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Add a new or update an existing session storage pair
sessionStorage.add("currentPage", "3");

// Access a session storage pair with a given key
String username = sessionStorage.get("currentPage");

// Remove a session storage pair with a given key
sessionStorage.remove("currentPage");
```

### Use cases
The following use cases are well-suited for utilization of session storage:

- **Temporary Data Storage**: Store data that only needs to persist while the user is on a particular page or session.
- **Form Data**: Temporarily save form data that needs to be used within the session.

## Local storage
Local storage stores data with no expiration date. It persists even after the browser is closed, and can be accessed whenever the user revisits the website. Local storage is best for storing user preferences or settings, caching data to improve performance, and saving application state across sessions.

### Advantages

- Data persists across sessions
- Data is not sent with every HTTP request.
- Larger storage size than cookies

### Disadvantages

- Not suitable for sensitive data
- You must manage data yourself, since it will not ever be deleted automatically

### Using local storage in webforj

In the following code snippet, a <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> object is obtained using the `LocalStorage.getCurrent()` method to either create a new object or retrieve it if it already exists. A key/value pair is written to the object using the `add(key, value)` method, and the value is obtained using the `get(key)` method. The data can be removed from the object with the `remove(key)` method.

```java
// Access local storage
LocalStorage localStorage = LocalStorage.getCurrent();

// Add a new or update an existing local storage pair
localStorage.add("theme", "dark");

// Access a local storage pair with a given key
String username = localStorage.get("theme");

// Remove a local storage pair with a given key
localStorage.remove("theme");
```

### Use cases
The following use cases are well-suited for utilization of local storage:

- **Persistent Data**: Store data that should be available across multiple sessions.
- **Preferences**: Save user settings and preferences that persist over time.