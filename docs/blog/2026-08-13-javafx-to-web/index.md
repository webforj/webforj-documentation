---
title: "JavaFX to web: move your desktop UI to the browser in Java"
description: "Plan a JavaFX to web move that keeps Java. See how stages, scenes, controls, FXML and bindings map to webforJ, and which screens to port first."
slug: javafx-to-web
date: 2026-08-13
authors: Hyyan Abo Fakher
tags: [insights, modernization, mcp]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-08-13-javafx-to-web/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-08-13-javafx-to-web/cover.jpg)

Your JavaFX app works. Users like it, the domain logic took years to get right, and now someone wants it in a browser: no installer, no JRE on every laptop, access from a phone, or access from a customer site. Rewriting it in JavaScript would throw away the one thing you have plenty of, which is Java.

JavaFX to web does not need a second language. With webforJ, a full-stack Java framework, you keep Java, keep your services and domain classes, and rebuild the screens as routes and components that render in the browser. Screens move one at a time, so the app stays releasable the whole way.

<!-- truncate -->

:::tip See a finished app first
The [webforj-crud](https://github.com/webforj/built-with-webforj/tree/main/webforj-crud) app in the built-with-webforj repository shows a table, a search field, an add and edit dialog, validation and data binding on Spring Boot. It runs with Java and Maven, so you can read a real webforJ screen before you plan yours.
:::

## Why a rewrite in JavaScript hurts {#why-not-rewrite}

A desktop UI and a browser UI differ in where code runs. In JavaFX, everything lives in one JVM on the user's machine. In a browser app, the UI renders on the client while your Java runs on the server. Teams that hear this often conclude they need a JavaScript front end, a REST layer, and a second skill set to maintain.

That is the expensive path. You duplicate validation, you translate your model into JSON and back, and your Java developers stop owning the screens. webforJ removes the split: you write the UI in Java, the framework handles the browser side, and your existing code review, tests, and build tools keep working. Read the [architecture overview](/docs/architecture/overview) if you want the client and server picture.

## Concept mapping {#concept-mapping}

If you know JavaFX, you already know most of the shape of a webforJ app. The vocabulary changes and the structure stays close.

| JavaFX idea | webforJ equivalent |
| --- | --- |
| Main class and primary Stage | The `App` class and the browser page |
| Scenes and screens you swap | Routes, one view per URL |
| Controls (buttons, fields, tables) | webforJ components such as `Button`, `TextField`, `Table` |
| Layout panes | `FlexLayout`, `Div` and other containers, written in Java |
| FXML | Plain Java composition, no markup file |
| Custom controls | `Composite` components |
| Event handlers | Component listeners such as `onClick` |
| Properties and bindings | `BindingContext` for form to model binding |

A few of these deserve detail.

**Stage and Scene become routes.** A desktop app swaps scenes inside one window. A web app gives each screen a URL, so users can bookmark it, reload it, and share it. In webforJ you declare that with an annotation, as shown in the [routing docs](/docs/routing/overview):

```java
@Route(value = "dashboard")
public class DashboardView extends Composite<Div> {
  // Component logic here
}
```

**FXML goes away.** If you split layout into FXML and controllers, the port is a simplification. The layout and the behavior end up in the same Java class, so there is one file per screen to read. Layout panes map to [`FlexLayout`](/docs/components/flex-layout) and containers, and the screen logic you had in the controller moves into the view.

**Custom controls become composites.** A reusable control built from smaller ones is the same idea as a [`Composite`](/docs/building-ui/composing-components) in webforJ:

```java
public class BasicComposite extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BasicComposite() {
    // Access the bound component to configure it
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("3px")
      .add(new TextField(), new Button("Submit"));
  }
}
```

**Listeners carry over.** Wiring a button to an action is still a one-line lambda. The [events docs](/docs/building-ui/events) cover the listener pairs and how to remove them.

**Properties and bindings need a decision.** JavaFX properties let any part of the UI observe a value. webforJ takes a form-oriented approach: [data binding](/docs/data-binding/overview) connects fields to a Java bean with two-way synchronization and validation through `BindingContext`. For forms, which are most of a business app, this replaces a lot of hand-written listener code. For fine-grained reactive state outside forms, you decide per screen whether a plain listener is enough.

## What stays exactly as it is {#what-stays}

The best news in a migration is the code you leave alone.

- Domain classes, calculations and rules.
- Service and repository classes, as long as they do not depend on JavaFX types.
- Your build, your tests for everything below the UI, and your Java version policy.

A webforJ app can run on Spring Boot, so existing Spring services plug in directly. The seam to check is any model class that imports JavaFX properties for its own state. Those need a small decision: keep them as plain beans for the web UI, or wrap them behind a service. That is a design call for your team.

## Plan the move screen by screen {#screen-by-screen}

Do not port the app in one pass. Pick an order and ship in slices.

1. **Inventory the screens.** List every window and dialog, who uses it, and how often.
2. **Start with a form or list screen.** These are mostly fields and tables over services you already have, so the preceding mapping covers nearly everything.
3. **Build the shell next.** Navigation, a main layout and the login flow give every later screen a home.
4. **Move the busy screens.** Order by usage, so most users reach the web app early.
5. **Leave the rare screens for last.** A screen opened once a quarter can wait.
6. **Retire the desktop build** when the last route is live.

A specialized screen with heavy custom drawing, such as a canvas editor or a charting surface, is the hard case. You do not have to port it first. webforJ can embed a running desktop app through Webswing, which supports JavaFX, so a hard screen can ship as is while the rest moves. The [Webswing integration docs](/docs/integrations/webswing/overview) cover the setup, and the [Webswing and webforJ roadmap](/blog/webswing-and-webforj) explains the incremental path.

## Let an AI assistant do the port {#ai-port}

Screen-by-screen porting is repetitive, which makes it a good job for an AI coding assistant. The risk is that a general model guesses at framework classes. The webforJ AI plugin closes that gap.

The plugin bundles the [MCP server](/docs/ai-tooling/mcp), which lets the assistant look up classes in the webforJ knowledge base instead of recalling them, with the [Agent Skills](/docs/ai-tooling/agent-skills) that give it a workflow for building forms, styling, and creating components. The full setup is on the [AI tooling page](/docs/ai-tooling).

Give the assistant a narrow brief for each screen:

1. The JavaFX view and controller, plus the service classes it calls.
2. The route the new view should use.
3. A rule that the view calls existing services and does not copy their logic.
4. A request to look up every webforJ class through the MCP server before it finishes.

Review the output like any pull request. The result is plain Java next to your services, so your normal review and tests apply.

The assistant can also check its own work. Ask it for a test with every screen it ports, so each one comes back with a pass or a fail before you review it. That write, check, review cycle is [the AI verification loop for Java](/blog/ai-verification-loop-java), and it fits a migration well, because every screen you move is a small, testable unit.

## When JavaFX should stay {#when-javafx-stays}

Not every app belongs in a browser. Keep JavaFX when:

- The app drives local hardware, such as devices on a serial port or a kiosk.
- It must run fully offline on machines with no server.
- It renders heavy real-time graphics that need the user's own GPU.
- Only a handful of users run it, they are happy, and nobody asks for web access.

If your reasons for moving are distribution, access from anywhere, central updates or one shared deployment, the browser is the better home. You can also mix: the embedded path from the previous section lets a JavaFX screen live inside a web app until its time comes.

## Next step {#next-step}

Pick your simplest form screen. Generate a project with [startforJ](https://docs.webforj.com/startforj) or follow the [getting started guide](/docs/introduction/getting-started), connect the AI plugin, and port that one screen against your existing services. One finished screen shows you the full loop and makes the rest of the plan concrete.

## Frequently asked questions {#faq}

### How do I migrate JavaFX to web without learning JavaScript? {#faq-no-javascript}

Build the UI in Java with webforJ. Screens become routes, controls become webforJ components, and layout is Java code. Your services and domain classes stay as they are.

### Can I run a JavaFX app in the browser without rewriting it? {#faq-run-in-browser}

Yes, for screens you are not ready to change. webforJ embeds a running desktop app through Webswing, which supports JavaFX. Use it as a bridge while you rebuild screens as native webforJ views.

### Does FXML have a webforJ counterpart? {#faq-fxml}

No. webforJ composes layouts in Java, so the markup file and its controller merge into one view class. That usually makes a ported screen shorter to read.
