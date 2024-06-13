---
sidebar_position: 30
title: Web Storage
---
import JavadocLink from '@site/src/components/DocsTools/JavadocLink';


<JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" top='true'/>

## Overview
[Web storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) is a fundamental concept in web development that allows websites to store data on the client side. This enables web applications to save state, preferences, and other information locally on the user's browser, enhancing user experience and application performance.

Web storage is essential for creating interactive and dynamic web applications. It provides a way to persist data across page reloads and browser sessions, reducing the need for repeated server requests and enabling offline capabilities.

Web storage can be implemented with [**Cookies**](#cookies), [**Session Storage**](#session-storage), or [**Local Storage**](#local-storage).

### Summary of Differences
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

### Using Cookies in webforJ

In the following code snippet, a <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> object is obtained using the `CookieStorage.getCurrent()` method to either create a new object or retrieve it if it already exists. A key/value pair with optional attributes is written to the cookie using the `add(key, value, attributes)` method, and the value is obtained using the `get(key)` method. The data can be removed from the Cookie with the `remove(key)` method.

```java
CookieStorage cookieStorage = CookieStorage.getCurrent();
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");
String username = cookieStorage.get("username");
cookieStorage.remove("username");
```

## Session Storage
Session storage stores data for the duration of a page session. The data is accessible only within the same session and is cleared when the page or tab is closed, but persists for reloads and restores. Session storage is best for storing temporary data during a single page session and maintaining state across different pages in the same session.

### Advantages
- Data is not sent with every HTTP request
- Larger storage size than cookies

### Disadvantages
- Data is lost when the page or tab is closed
- Data is not shared across tabs

### Using Session Storage in webforJ

In the following code snippet, a <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> object is obtained using the `SessionStorage.getCurrent()` method to either create a new object or retrieve it if it already exists. A key/value pair is written to the object using the `add(key, value)` method, and the value is obtained using the `get(key)` method. The data can be removed from the object with the `remove(key)` method.

```java
SessionStorage sessionStorage = SessionStorage.getCurrent();
sessionStorage.add("currentPage", "3");
String username = sessionStorage.get("currentPage");
sessionStorage.remove("currentPage");
```

## Local Storage
Local storage stores data with no expiration date. It persists even after the browser is closed, and can be accessed whenever the user revisits the website. Local storage is best for storing user preferences or settings, caching data to improve performance, and saving application state across sessions.

### Advantages

- Data persists across sessions
- Data is not sent with every HTTP request.
- Larger storage size than cookies

### Disadvantages

- Not suitable for sensitive data
- You must manage data yourself, since it will not ever be deleted automatically

### Local Storage in webforJ

In the following code snippet, a <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> object is obtained using the `LocalStorage.getCurrent()` method to either create a new object or retrieve it if it already exists. A key/value pair is written to the object using the `add(key, value)` method, and the value is obtained using the `get(key)` method. The data can be removed from the object with the `remove(key)` method.

```java
LocalStorage localStorage = LocalStorage.getCurrent();
localStorage.add("theme", "dark");
String username = localStorage.get("theme");
localStorage.remove("theme");
```