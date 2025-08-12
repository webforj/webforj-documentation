---
sidebar_position: 10
title: Web Storage
_i18n_hash: 12a907c67d42dedcc6ca3b62fe99e549
---
<!-- vale off -->
# Web Storage <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Web storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) 是 web 开发中的一个基本概念，它允许网站在客户端存储数据。这使得 web 应用能够在用户的浏览器中本地保存状态、偏好和其他信息。Web 存储提供了一种在页面重新加载和浏览器会话之间持久化数据的方法，减少了对服务器重复请求的需要，并支持离线功能。

webforJ 支持三种存储客户端数据的机制：[**Cookies**](#cookies)、[**Session Storage**](#session-storage) 和 [**Local Storage**](#local-storage)。

:::tip Web Storage in Developer Tools
您可以在浏览器的开发者工具中查看当前的 cookie、本地存储和会话存储的键值对。
:::

## Summary of differences {#summary-of-differences}
| Feature            | Cookies                                      | Session Storage                          | Local Storage                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **Persistence**    | 可配置的过期日期                           | 页面会话的持续时间                        | 直到明确删除才会持久存在                |
| **Storage Size**   | 每个 cookie [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation)                             | 大约 [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | 大约 [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **Use Cases**      | 用户认证、偏好、跟踪                        | 临时数据、表单数据                        | 持久设置、用户偏好                       |
| **Security**       | 易受 XSS 攻击，可以通过标志加以保护       | 在会话结束时清除，风险较小                | 通过 JavaScript 可访问，潜在风险        |

## Using web storage {#using-web-storage}
在 webforJ 中，<JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>、<JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> 类均扩展了抽象的 <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink> 类。要获取适当的对象，请使用静态方法 `CookieStorage.getCurrent()`、`SessionStorage.getCurrent()` 或 `LocalStorage.getCurrent()`。要添加、获取和删除键值对，请使用 `add(key, value)`、`get(key)` 和 `remove(key)` 方法。

## Cookies {#cookies}
[Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) 是存储在客户端的小数据块，并随着每个 HTTP 请求发送到服务器。它们通常用于记住用户会话、偏好和认证信息。除了键值对以外，cookie 还可以有属性。要为 cookies 设置属性，请使用 `add(key, value, attributes)`。

### Key features: {#key-features}
- 可以跨不同域存储数据
- 支持过期日期
- 存储大小小，通常限制在 4 KB
- 随每个 HTTP 请求发送
- 可以有属性

:::info Cookie Expiration
默认情况下，webforJ 中的 cookies 在 30 天后过期。您可以通过 `max-age` 或 `expires` 属性更改此设置。
:::

### Using cookies {#using-cookies}

以下代码片段演示了使用 <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> 对象。

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
某些 cookie 属性，例如 `Secure` 和 `SameSite=None`，需要使用 HTTPS 的安全上下文。这些属性确保 cookies 仅通过安全连接发送，保护它们不被拦截。有关更多信息，请参见 [MDN documentation on cookie security](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security)。
:::

### Use cases {#use-cases}
以下用例非常适合利用 cookies：

- **用户认证**：存储会话令牌以保持用户登录状态。
- **偏好设置**：保存用户偏好，例如主题设置或语言。
- **跟踪**：收集有关用户行为的信息以进行分析。

## Session storage {#session-storage}
会话存储在一个页面会话的持续时间内存储数据。数据只能在同一会话内访问，当页面或标签关闭时将被清除。然而，在重新加载和恢复时，数据会保留。会话存储最适合在单个页面会话中存储临时数据以及在同一会话中的不同页面之间维护状态。

### Key features {#key-features-1}
- 数据不会随着每个 HTTP 请求发送
- 存储大小大于 cookies
- 当页面或标签关闭时，数据会被清除
- 数据不会跨标签共享

### Using session storage in webforJ {#using-session-storage-in-webforj}

以下代码片段演示了使用 <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> 对象。

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

### Use cases {#use-cases-1}
以下用例非常适合利用会话存储：

- **临时数据存储**：存储只在用户处于特定页面或会话时需要保留的数据。
- **表单数据**：在会话内临时保存表单数据。

## Local storage {#local-storage}
本地存储以无过期日期的方式存储数据。即使在关闭浏览器之后，它仍会持久存在，并且用户重新访问网站时可以访问。本地存储最适合存储用户的偏好或设置、缓存数据以提高性能以及在会话之间保存应用状态。

### Key features {#key-features-2}

- 数据在会话之间持久存在
- 数据不会随着每个 HTTP 请求发送
- 存储大小大于 cookies
- 不适合存储敏感数据
- 您必须自己管理数据，因为浏览器不会自动删除它

### Using local storage in webforJ {#using-local-storage-in-webforj}

以下代码片段演示了使用 <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> 对象。

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

### Use cases {#use-cases-2}
以下用例非常适合利用本地存储：

- **持久数据**：存储在多个会话之间可用的数据。
- **偏好设置**：保存用户设置和长期保持的偏好。
