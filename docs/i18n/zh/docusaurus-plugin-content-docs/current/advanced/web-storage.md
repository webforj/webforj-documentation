---
sidebar_position: 10
title: Web Storage
_i18n_hash: ec80b71a3de50c878acee0f99d4eb371
---
<!-- vale off -->
# Web Storage <DocChip chip='since' label='23.06' />
<!-- vale on -->

[Web storage](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API) 是 Web 开发中的一个基本概念，它允许网站在客户端存储数据。这使得 Web 应用程序可以在用户的浏览器上本地保存状态、偏好设置和其他信息。Web 存储提供了一种方法来在页面重新加载和浏览器会话之间持久化数据，减少了对重复服务器请求的需求，并启用了离线功能。

webforJ 支持三种机制来存储客户端数据：[**Cookies**](#cookies)、[**Session Storage**](#session-storage) 和 [**Local Storage**](#local-storage)。

:::tip Web Storage 在开发者工具中的使用
您可以在浏览器的开发者工具中查看当前的 cookie、地方存储和会话存储的键值对。
:::

## 差异总结 {#summary-of-differences}
| 特性              | Cookies                                      | Session Storage                          | Local Storage                            |
|--------------------|----------------------------------------------|------------------------------------------|------------------------------------------|
| **持久性**        | 可配置的到期时间                            | 页面会话的持续时间                       | 直到明确删除为止                         |
| **存储大小**      | 每个 cookie [4 KB](https://en.wikipedia.org/wiki/HTTP_cookie#Implementation)                             | 约 [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           | 约 [5-10](https://en.wikipedia.org/wiki/Web_storage#Storage_size) MB                           |
| **使用场景**      | 用户身份验证、偏好设置、跟踪               | 临时数据、表单数据                        | 持久设置、用户偏好                       |
| **安全性**        | 容易遭受 XSS 攻击，可用标志保护            | 会话结束时清除，风险较小                  | 通过 JavaScript 访问，存在潜在风险     |

## 使用 web storage {#using-web-storage}
webforJ 中的 <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink>、<JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> 和 <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> 类都扩展了抽象的 <JavadocLink type="foundation" location="com/webforj/webstorage/WebStorage" code='true'>WebStorage</JavadocLink> 类。要获取适当的对象，请使用静态方法 `CookieStorage.getCurrent()`、`SessionStorage.getCurrent()` 或 `LocalStorage.getCurrent()`。要添加、获取和删除键值对，请使用 `add(key, value)`、`get(key)` 和 `remove(key)` 方法。

## Cookies {#cookies}
[Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies) 是存储在客户端的小数据块，并随着每个 HTTP 请求发送到服务器。它们通常用于记住用户会话、偏好设置和身份验证信息。除了键值对外，cookie 还可以具有属性。要为 cookie 设置属性，请使用 `add(key, value, attributes)`。

### 主要特性： {#key-features}
- 可以跨不同域存储数据
- 支持到期日期
- 存储大小小，通常限制为 4 KB
- 随每个 HTTP 请求发送
- 可以具有属性

:::info Cookie 到期
默认情况下，webforJ 中的 cookies 在 30 天后到期。您可以通过 `max-age` 或 `expires` 属性更改此设置。
:::

### 使用 cookies {#using-cookies}

以下代码片段演示了 <JavadocLink type="foundation" location="com/webforj/webstorage/CookieStorage" code='true'>CookieStorage</JavadocLink> 对象的使用。

```java
// 访问 cookie 存储
CookieStorage cookieStorage = CookieStorage.getCurrent();

// 添加新 cookie 或更新现有 cookie
cookieStorage.add("username", "JohnDoe", "Max-Age=3600; Secure; HttpOnly");

// 使用给定键访问 cookie
String username = cookieStorage.get("username");

// 使用给定键移除 cookie
cookieStorage.remove("username");
```
:::info Cookie 安全性
某些 cookie 属性，如 `Secure` 和 `SameSite=None`，需要使用 HTTPS 的安全上下文。这些属性确保 cookies 仅在安全连接下发送，从而保护它们不被拦截。有关更多信息，请参见 [MDN cookie 安全性文档](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#security)。 
:::

### 使用场景 {#use-cases}
以下使用场景非常适合利用 cookies：

- **用户身份验证**：存储会话令牌以保持用户登录。
- **偏好设置**：保存用户的偏好设置，如主题设置或语言。
- **跟踪**：收集用户行为的信息以用于分析。


## Session storage {#session-storage}
会话存储在页面会话期间存储数据。该数据仅在同一会话内可访问，并在页面或选项卡关闭时清除。然而，数据在重新加载和恢复时保持有效。会话存储最适合在单个页面会话中存储临时数据，并在同一会话中的不同页面之间维护状态。

### 主要特性 {#key-features-1}
- 数据不会随着每个 HTTP 请求发送
- 存储大小大于 cookies
- 页面或选项卡关闭时数据被清除
- 数据在选项卡之间不共享

### 在 webforJ 中使用会话存储 {#using-session-storage-in-webforj}

以下代码片段演示了 <JavadocLink type="foundation" location="com/webforj/webstorage/SessionStorage" code='true'>SessionStorage</JavadocLink> 对象的使用。

```java
// 访问会话存储
SessionStorage sessionStorage = SessionStorage.getCurrent();

// 添加新或更新现有的会话存储对
sessionStorage.add("currentPage", "3");

// 使用给定键访问会话存储对
String currentPage = sessionStorage.get("currentPage");

// 使用给定键移除会话存储对
sessionStorage.remove("currentPage");
```

### 使用场景 {#use-cases-1}
以下使用场景非常适合利用会话存储：

- **临时数据存储**：存储仅在用户处于特定页面或会话时需要持久保存的数据。
- **表单数据**：临时保存表单数据以供在会话中使用。

## Local storage {#local-storage}
本地存储存储没有过期日期的数据。它即使在浏览器关闭后仍会保持有效，并且每当用户重新访问网站时都可以访问。本地存储最适合用于存储用户偏好设置或设置、缓存数据以提高性能，以及在会话之间保存应用状态。

### 主要特性 {#key-features-2}

- 数据在会话之间持久化
- 数据不会随着每个 HTTP 请求发送。
- 存储大小大于 cookies
- 不适合存储敏感数据
- 您必须自己管理数据，因为浏览器从不自动删除它

### 在 webforJ 中使用本地存储 {#using-local-storage-in-webforj}

以下代码片段演示了 <JavadocLink type="foundation" location="com/webforj/webstorage/LocalStorage" code='true'>LocalStorage</JavadocLink> 对象的使用。

```java
// 访问本地存储
LocalStorage localStorage = LocalStorage.getCurrent();

// 添加新或更新现有的本地存储对
localStorage.add("theme", "dark");

// 使用给定键访问本地存储对
String theme = localStorage.get("theme");

// 使用给定键移除本地存储对
localStorage.remove("theme");
```

### 使用场景 {#use-cases-2}
以下使用场景非常适合利用本地存储：

- **持久数据**：存储应该在多个会话中可用的数据。
- **偏好设置**：保存持久存在的用户设置和偏好。
