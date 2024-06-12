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
- Supports expiration dates

### Disadvantages:
- Limited storage size (about 4KB)
- Sent with every HTTP request

:::info
By default, cookies in webforJ expire after 30 days. This can be change with the `max-age` or `expires` attributes.
<!-- Is this configurable? -->
:::

### Using Cookies in webforJ


In the following code snippet, a <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true' suffix='#<init>()'>CookieStorage</JavadocLink> object is obtained using the `CookieStorage.getCurrent()` method to either create a new object or retrieve it if it already exists. A key/value pair with optional attributes is written to the cookie using the `add(key, value, attributes)` method, and the value is obtained using the `get(key)` method. The data can be removed from the Cookie with the `remove(key)` method.

```java
CookieStorage cookieStorage = CookieStorage.getCurrent();
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");
String username = cookieStorage.get("username");
cookieStorage.remove("username");
```

## Session Storage
### Advantages and Disadvantages

## Local Storage
### Advantages and Disadvantages

## Web Storage in webforJ
### Advantages and Disadvantages
