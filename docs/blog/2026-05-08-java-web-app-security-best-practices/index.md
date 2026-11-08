---
title: "Best practices for Java web application security in webforJ"
description: "Java web application security best practices for webforJ apps: what the server-driven model stops, what stays yours, and a checklist to harden production."
slug: java-web-app-security-best-practices
date: 2026-05-08
authors: webforJ
tags: [tutorial, security, spring]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-05-08-java-web-app-security-best-practices/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-05-08-java-web-app-security-best-practices/cover.jpg)

Java web app security best practices start with one choice: keep the interface logic on the server. webforJ does exactly that. Your views run in Java, the browser only renders them, and whole classes of attack never get a foothold. What remains is a short list of habits: gate every route, treat input as untrusted, cap uploads and requests, keep secrets out of the repo, and run over HTTPS. This post covers each one for a webforJ app.

<!-- truncate -->

## What does webforJ protect by default? {#what-webforj-protects}

Because a webforJ app executes in Java on the server (see [Client/Server Interaction](/docs/architecture/client-server)), the browser holds no business logic to tamper with. Two protections follow from that design.

- **Cross-site scripting (XSS).** When you set a component's text, the value shows as literal characters. A string like `<b>hi</b>` appears as typed and never runs. Markup renders only when you opt in with `setHtml`.
- **Cross-site request forgery (CSRF).** The framework only trusts requests that belong to the current user's session, so a page on another origin can't drive your app for the user.

That covers the attacks that usually need the most framework plumbing. The rest of this post is about the parts that are still yours, which is where most real incidents come from.

## How do I secure a Java web app UI? {#secure-java-web-ui}

Start with who can reach each view. A secure Java web app checklist begins with route access, because a hidden button isn't a lock. webforJ route security uses annotations such as `@AnonymousAccess`, `@PermitAll`, `@RolesAllowed` and `@DenyAll`, and a secure-by-default mode makes an unmarked route require sign-in. The [route security overview](/docs/security/overview) covers the setup with Spring Security.

Then repeat the rule in the handler. `setEnabled(false)` and `setVisible(false)` are interface cues, and a manipulated client can re-enable a control and trigger its action. The server-side code must check permission on its own:

```java
@Route("/admin/orders")
@RolesAllowed("ADMIN")
public class OrdersView extends Composite<Div> {
  private final Button delete = new Button("Delete order", ButtonTheme.DANGER);

  public OrdersView() {
    getBoundComponent().add(delete);
    delete.onClick(e -> deleteOrder(42L));
  }

  private void deleteOrder(long id) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    boolean admin = auth != null && auth.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    if (!admin) {
      Toast.show("Not allowed", Theme.DANGER);
      return;
    }
    // delete the order
  }
}
```

The route annotation keeps non-admins out of the view, and the check in the handler keeps the action safe even if something reaches it another way.

## Which OWASP risks still apply? {#owasp-java-web-ui}

The OWASP Top 10 is a useful lens for any [OWASP Java web UI](https://owasp.org/www-project-top-ten/) review. In a webforJ app, four items need your attention.

### Injection through JavaScript and SQL {#injection}

`executeJs` runs the string you give it as a program. If you concatenate an untrusted value into it, the value becomes code. Send the value as data and run a fixed script that reads it back through the `component` keyword:

```java
// Shown as literal characters, never as markup
para.setText(name);
// The value travels as data, the script stays fixed
el.setProperty("greetName", name);
el.executeJs("greet(component.greetName)");
```

SQL works the same way. webforJ sits outside your data layer, so use prepared statements and bound parameters, never string concatenation.

### Markup you opt into {#opt-in-markup}

The framework doesn't scrub the markup you wrap in `<html>`. If any part of it comes from a person, a stored record or a query string, clean it first with a maintained library such as [jsoup](https://jsoup.org/) or the [OWASP Java HTML Sanitizer](https://owasp.org/www-project-java-html-sanitizer/) and an allowlist of tags.

### Broken access control {#broken-access-control}

This is the route and handler pairing from the previous section. Grant the narrowest access that lets people work.

### Validation on the server {#server-side-validation}

A manipulated client can send anything, so re-validate values before you persist or act on them. [Data binding and validation](/docs/data-binding/validation/overview) runs in Java on the server, so constraints you attach to a model, including Jakarta validation, hold regardless of what the browser sends. Treat it as an integrity layer and not as a defense against injection.

## How do I harden a Java web app? {#harden-java-web-app}

Hardening is mostly configuration, and a few switches are off until you turn them on.

| Concern | What to do |
| --- | --- |
| Request size | Set `webforj.security.maxContentLength` (bytes). Default `0` means no limit |
| Session flooding | Set `webforj.security.maxInitPerMinute`. Default `0` means no limit |
| Upload size | Set `webforj.fileUpload.maxSize` and use `setMaxFileSize()` and `setMaxFiles()` on the upload component |
| Transport | Serve HTTPS only and redirect plain HTTP at the proxy or load balancer |
| Secrets | Read them from the environment, never from `webforj.conf` or the repo |
| Errors | Register a custom error handler so users never see stack traces |

Both security properties are listed in [Property Configuration](/docs/configuration/properties). Treat the app limits as the inner layer and bound the request size at your servlet container or reverse proxy too, because a browser-side limit can always be bypassed.

If you use Spring Security, `WebforjSecurityConfigurer.webforj()` excludes the framework's own requests from Spring's CSRF verification so the app loads. Leave that verification on for any endpoints you add yourself.

Two more habits matter here. Keep dependencies current, and pin a patched version of a transitive library in your `pom.xml` when the fix ships before the library that pulls it in. And confirm that debug mode is off in the configuration you deploy, since `webforj.debug` prints extra information and shows exceptions in the browser. The [production hardening guide](/docs/security/application-security/production-hardening) walks through each of these.

## How should I handle secrets? {#handle-secrets}

A database password or API key never belongs in code, in `webforj.conf` or in a repository. Store it as an environment variable where the app runs and read it at startup. On Spring Boot, reference it from `application.properties`:

```properties
spring.datasource.password=${DB_PASSWORD}
spring.config.import=optional:file:./secrets.properties
```

Add the secrets file to `.gitignore`, and rotate anything that was ever pushed, since removing it from new commits doesn't undo the old ones. See [Managing Secrets](/docs/security/application-security/managing-secrets) for the details.

## Security checklist for webforJ apps {#security-checklist}

1. Mark every route with an access annotation and enable secure-by-default.
2. Repeat the permission check inside every sensitive handler.
3. Never concatenate input into `executeJs`. Use `setProperty` and read it with `component`.
4. Sanitize any markup that includes outside data before `setHtml`.
5. Use parameterized queries.
6. Set `maxContentLength`, `maxInitPerMinute` and upload limits.
7. Serve over HTTPS and bound request size at the proxy.
8. Keep secrets in the environment and out of Git.
9. Update dependencies and switch debug mode off in production.

## Next step {#next-step}

Open the [Common Threats](/docs/security/application-security/common-threats) page and compare each section with your own views. Starting a new project? Generate one at [startforJ](https://docs.webforj.com/startforj) and add route security before you write the first view.

## Frequently asked questions {#faq}

### What should a Java web app security checklist include? {#faq-best-practices}

Restrict every route, validate on the server, bind queries as parameters, cap request and upload sizes, keep secrets external and serve over HTTPS. webforJ's server-driven model handles XSS and CSRF for its own traffic, so these habits cover what is left.

### Does webforJ protect against XSS? {#faq-xss}

Yes for normal use. Text you set on a component renders literally, and markup renders only when you call `setHtml`. If that markup includes outside data, sanitize it first with a library such as jsoup.

### Is disabling a button enough to block an action? {#faq-disabled-button}

No. A disabled or hidden control is an interface cue, and a manipulated client can re-enable it. Put the permission check in the server-side handler.

### How do I limit uploads and request size in webforJ? {#faq-limits}

Use `setMaxFileSize()` and `setMaxFiles()` on the upload component, then set `webforj.fileUpload.maxSize` and `webforj.security.maxContentLength` in your configuration. Bound the size at your proxy as well.

### Where should API keys and passwords live? {#faq-secrets}

In environment variables or an external secrets file kept out of version control. On Spring Boot, reference them with `${...}` placeholders in `application.properties`.
