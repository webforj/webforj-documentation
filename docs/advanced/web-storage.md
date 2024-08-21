---
sidebar_position: 30
title: Web Storage
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';


## Overview
[Web storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) is a fundamental concept in web development that allows websites to store data on the client side. This enables web applications to save state, preferences, and other information locally on the user's browser. Web storage provides a way to persist data across page reloads and browser sessions, reducing the need for repeated server requests and enabling offline capabilities.

webforJ supports three mechanisms for storing client data: [**Cookies**](#cookies), [**Session Storage**](#session-storage), and [**Local Storage**](#local-storage).

:::tip Web Storage in Developer Tools
You can see current cookie, local storage and session storage key-value pairs in your browser's developer tools.
:::

### Summary of differences
| Feature            | Cookies                                      | Session Storage                          | Local Storage                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **Persistence**    | Configurable expiration date                 | Duration of the page session             | Persistent until explicitly deleted      |
| **Storage Size**   | [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation) per cookie                             | Around [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | Around [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Use Cases**      | User authentication, preferences, tracking   | Temporary data, form data                | Persistent settings, user preferences    |
| **Security**       | Vulnerable to XSS, can be secured with flags | Cleared on session end, less risk        | Accessible via JavaScript, potential risk|

### Using web storage
The <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>, <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink>, and <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> classes in webforJ all extend the abstract <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink> class. To obtain the appropriate object, use the static methods `CookieStorage.getCurrent()`,  `SessionStorage.getCurrent()`, or `LocalStorage.getCurrent()`. To add, get, and remove key-value pairs, use the `add(key, value)`, `get(key)`, and `remove(key)` methods.

## Cookies
[Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) are small pieces of data stored on the client side and sent to the server with each HTTP request. They are often used to remember user sessions, preferences, and authentication information. In addition to the key-value pairs, cookies may also have attributes. To set attributes for cookies, use `add(key, value, attributes)`.

### Key features:
- Can store data across different domains
- Support expiration dates
- Small storage size, typically restricted to 4 KB
- Sent with every HTTP request
- Can have attributes

:::info Cookie Expiration
By default, cookies in webforJ expire after 30 days. You can change this with the `max-age` or `expires` attributes.
:::

### Using cookies

The following code snippet demonstrates the use of the <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> object.

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
:::info Cookie Security
Certain cookie attributes, such as `Secure` and `SameSite=None`, require a secure context using HTTPS. These attributes ensure that cookies are only sent over secure connections, protecting them from being intercepted. For more information, see the [MDN documentation on cookie security](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security).
:::

### Use cases
The following use cases are well-suited for utilization of cookies:

- **User Authentication**: Store session tokens to keep users logged in.
- **Preferences**: Save user preferences, such as theme settings or language.
- **Tracking**: Collect information about user behavior for analytics.


## Session storage
Session storage stores data for the duration of a page session. The data is accessible only within the same session and is cleared when the page or tab is closed. However, the data persists for reloads and restores. Session storage is best for storing temporary data during a single page session and maintaining state across different pages in the same session.

### Key features
- Data isn't sent with every HTTP request
- Larger storage size than cookies
- Data is cleared when the page or tab is closed
- Data isn't shared across tabs

### Using session storage in webforJ

The following code snippet demonstrates the use of the <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> object.

```java
// Access session storage
SessionStorage sessionStorage = SessionStorage.getCurrent();

// Add a new or update an existing session storage pair
sessionStorage.add("currentPage", "3");

// Access a session storage pair with a given key
String currentPage = sessionStorage.get("currentPage");

// Remove a session storage pair with a given key
sessionStorage.remove("currentPage");
```

### Use cases
The following use cases are well-suited for utilization of session storage:

- **Temporary Data Storage**: Store data that only needs to persist while the user is on a particular page or session.
- **Form Data**: Temporarily save form data for use within the session.

## Local storage
Local storage stores data with no expiration date. It persists even after the browser is closed, and can be accessed whenever the user revisits the website. Local storage is best for storing user preferences or settings, caching data to improve performance, and saving app state across sessions.

### Key features

- Data persists across sessions
- Data isn't sent with every HTTP request.
- Larger storage size than cookies
- Not suitable for sensitive data
- You must manage data yourself, since the browser never automatically deletes it

### Using local storage in webforj

The following code snippet demonstrates the use of the <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> object.

```java
// Access local storage
LocalStorage localStorage = LocalStorage.getCurrent();

// Add a new or update an existing local storage pair
localStorage.add("theme", "dark");

// Access a local storage pair with a given key
String theme = localStorage.get("theme");

// Remove a local storage pair with a given key
localStorage.remove("theme");
```

### Use cases
The following use cases are well-suited for utilization of local storage:

- **Persistent Data**: Store data that should be available across multiple sessions.
- **Preferences**: Save user settings and preferences that persist over time.