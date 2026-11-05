---
title: "Server-side UI framework for Java: how webforJ works"
description: "webforJ is a server-side UI framework for Java. Components live on the server, the browser renders them, and events return to Java handlers. See how it works."
slug: server-side-ui-framework-java
date: 2026-09-27
authors: Matthew Hawkins
tags: [community, web development, performance]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-27-server-side-ui-framework-java/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-27-server-side-ui-framework-java/cover.jpg)

webforJ is a server-side UI framework for Java. Your components are Java objects that run on the server, the browser renders them as web components, and user actions come back to your Java event handlers. There is no JavaScript app to write, no REST layer between the screen and your services, and your UI code never leaves the server.

This post explains the runtime model step by step: where components live, how events travel, which thread runs your code, and where state is kept. It also covers the trade-offs of a server-driven UI and how webforJ handles each one.

<!-- truncate -->

## What a server-side UI framework is {#what-it-is}

In a server-side UI framework, the program that decides what the screen looks like runs on the server. The client displays what the server describes and reports what the user did. You do not split the logic between two runtimes.

In webforJ, that program is ordinary Java. You build a view from component objects such as `Button`, `TextField` and `Table`, attach listeners, and route the class with `@Route`. The framework keeps the browser in step with your objects.

## How the browser and the server split the work {#how-it-splits-the-work}

When a user opens a webforJ app, the browser loads a small bootstrap script, about 2.5 kB gzipped, to start the session. The [client/server interaction page](/docs/architecture/client-server) describes the details. After that it downloads further chunks on demand. The code for a `Button`, for example, is sent once, when your app creates its first `Button`.

The traffic between the two sides falls into three groups:

| Direction | Example | Waits for a reply? |
| --- | --- | --- |
| Server to client | `setText()` on a component | No. Calls are batched. |
| Client to server | A `Button.onClick()` event | No. The event carries its data. |
| Round trip | `Label.getText()` or `Checkbox.isChecked()` | Yes. The app pauses until the client answers. |

The first two are the normal flow. The third is the one to watch, and the next section shows how to avoid it.

## Events return to Java handlers {#events-return-to-java}

A click in the browser becomes a call to a Java lambda. You register it with `onClick`, `onModify`, or the `add...Listener` method for the event you need. The [events guide](/docs/building-ui/events) lists them.

Events carry their payload with them, so read it from the event rather than asking the component again. This handler reads the text from the event and causes no round trip:

```java
field.onModify(event -> {
  String text = event.getText();
  // use text
});
```

Calling `field.getText()` inside the same handler would send a request to the browser and wait.

## One UI thread, and how to leave it {#threading}

All UI operations in webforJ happen on the `Environment` thread. Events are processed sequentially on it, so two handlers never run against the same components at the same moment.

Long work does not belong on that thread. Read the `Environment` in the handler, run the work on another thread and hand the result back with `Environment.runLater()`:

```java
report.onClick(e -> {
  status.setText("Working...");
  Environment env = Environment.getCurrent();
  new Thread(() -> {
    String result = slowReport();
    Environment.runLater(env, () -> status.setText(result));
  }).start();
});
```

The `Environment` is read in the handler, where it belongs to the user who clicked, and passed to the thread. With the target in hand `runLater()` works from any thread, including a library's own thread pool, as the [asynchronous updates guide](/docs/advanced/asynchronous-updates) explains. Reading `Environment.getCurrent()` on the background thread returns `null`, because that thread belongs to no user.

## Where state lives {#where-state-lives}

State lives on the server, in the objects you create. A text field's value, the rows in a table and your own fields in a view class are plain Java state. There is no second copy in a client store to keep in sync.

webforJ gives you scopes when you want that state to outlive a single view. With Spring, `@WebforjSessionScope` creates a bean shared across the browser tabs of one user session, and `@EnvironmentScope` creates one per tab or window. Session-scoped beans are stored as HTTP session attributes, so they must implement `Serializable`:

```java
@Service
@WebforjSessionScope
public class Cart implements Serializable {
  private final List<String> items = new ArrayList<>();

  public void add(String item) { items.add(item); }
  public int size() { return items.size(); }
}
```

Route classes are different. Each navigation to a route gets a fresh instance of the view, so per-visit state does not leak between users or between visits. The [scopes guide](/docs/integrations/spring/scopes) and the [Spring routing guide](/docs/integrations/spring/routing) cover both behaviors.

## What you gain {#what-you-gain}

**No API layer to secure.** A click handler calls your service in the same JVM, so the UI does not need endpoints that exist only to feed it. You still secure what is reachable: route security annotations such as `@RolesAllowed` are enforced before a component is rendered. See the [security overview](/docs/security/overview). The [Spring Boot without a REST API post](/blog/java-ui-without-rest-api) looks at what drops out of a project when both sides are Java.

**Your logic stays on the server.** The browser receives the instructions needed to render components and the data you put in them. Your validation rules, pricing logic and service calls are Java classes that run on the server. If an endpoint is still needed for another client, Spring MVC controllers can run beside the UI.

**The compiler covers the whole screen.** The UI, the handlers and the logic they call are one Java codebase. Rename a method on a service and the compiler flags every view that calls it. Your IDE refactoring tools work across the boundary because there is no boundary.

## The trade-offs, and how webforJ handles them {#trade-offs}

### Network round trips {#network-round-trips}

Every event travels to the server, so latency matters more than in an app that handles clicks locally. webforJ reduces the cost in three ways. Server-to-client calls are batched, events carry their data, and round-trip getters are the exception you avoid. For fast input such as typing, the `Debouncer` class runs an action only after activity settles:

```java
Debouncer debounce = new Debouncer(0.3f);

search.onModify(e -> debounce.run(() -> runSearch(e.getText())));
```

For `Element`-based components, `ElementEventOptions` can also filter, debounce, or throttle an event in the browser before it reaches the server. See the [debouncing page](/docs/advanced/debouncing) for details.

### Memory held per session {#memory-per-session}

Because state lives on the server, every active user keeps their components and your own session data in server memory. Size your servers for concurrent users, and keep session-scoped beans small. Use `@EnvironmentScope` or a regular field for data that belongs to one tab, and put large datasets behind a Spring Data repository, which `SpringDataRepository` pages for a `Table`, instead of holding every row in the view.

### Offline use {#offline-use}

A server-driven UI needs a live connection. If the connection drops, there is no server to render the next view. webforJ has an `@AppProfile` annotation that makes the app installable, so it gets an icon and its own window, and it can show app badges and receive push notifications. Installing does not add an offline mode. The [installable apps post](/blog/installable-apps) shows the setup.

You can also choose what the user sees when the app ends. `App` takes a close action, such as `MessageAction` with your own text or `RedirectAction` to another URL. The [shutdown and error actions page](/docs/advanced/terminate-and-error-actions) covers them.

## When to choose a server-side UI framework {#when-to-choose}

A server-side UI is a strong fit when:

- Your team writes Java and your users are online while they work, such as internal tools, back-office systems and customer portals.
- The data and rules behind the screen are sensitive and you want them to stay on the server.
- You want one codebase, one deployment, and one place to apply security rules.

Choose something else when the app must work without a network, drive local hardware, or respond at a pace that cannot afford a trip to the server. A desktop toolkit or a client-side app is the right tool for those jobs.

## Try it {#try-it}

Generate a project with [startforJ](https://docs.webforj.com/startforj) and open the sample view. Add a button, register a handler, and watch the browser update from your Java code. The [getting started guide](/docs/introduction/getting-started) covers the prerequisites.

## Frequently asked questions {#faq}

### What is a server-side UI framework? {#faq-what-is-it}

It is a framework where the UI is built and controlled by code running on the server. The browser displays the result and sends user events back. In webforJ you write that code in Java.

### Does webforJ run any code in the browser? {#faq-browser-code}

The browser runs a small bootstrap script and the web components that render your UI, and it loads further chunks as your app needs them. Your views, handlers and services are Java classes that run on the server.

### What happens to long-running tasks? {#faq-long-tasks}

Run them on a background thread and use `Environment.runLater()` with the `Environment` read in the handler to update components when they finish. That keeps the UI thread free to handle other events from the same user.

### Can a server-side UI work offline? {#faq-offline}

No. The server renders the UI, so the app needs a connection. A webforJ app can be installed on a device and can receive push notifications, but it still talks to your server to run.
