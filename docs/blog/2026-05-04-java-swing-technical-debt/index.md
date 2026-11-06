---
title: "Java Swing technical debt: what staying costs you"
description: "Is Swing dead? No, but a Swing app carries Java Swing technical debt that grows with every browser-only request. Here is how to decide, and how webforJ pays it down."
slug: java-swing-technical-debt
date: 2026-05-04
authors: webforJ
tags: [community, modernization, webswing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-05-04-java-swing-technical-debt/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-05-04-java-swing-technical-debt/cover.jpg)

Java Swing technical debt has little to do with a broken toolkit. Swing still ships in the JDK and still works. The debt is that a desktop-only UI cannot be reached from a browser, a phone or a shared link, and every request to change that lands on a codebase that was never built for it. webforJ pays that debt down without a big-bang rewrite: you embed the running Swing app in a webforJ page with `WebswingConnector`, then replace screens with webforJ components one at a time, all in Java.

<!-- truncate -->

:::tip Migrate with an AI assistant
webforJ now has an [AI plugin](/docs/ai-tooling) for Claude Code, Copilot, Cursor, and other assistants. It gives them the webforJ docs and project tools, so they can help you port Swing screens to webforJ one at a time.
:::

## Is Swing dead? {#is-swing-dead}

No. Swing is part of the `java.desktop` module of the JDK and your existing apps keep compiling and running. What has changed is the ground around it. Browser plug-ins are gone, which is why the Applet API, including `javax.swing.JApplet`, was [deprecated for removal in JDK 17](https://openjdk.org/jeps/398). The old path of "Swing in the browser through an applet" is closed for good.

So "is Swing dead" is the wrong question. The useful question is what it costs to keep a Swing app as the only way users reach your business logic. That cost is the debt, and it shows up in places you can name.

## Where Swing debt shows up {#where-swing-debt-shows-up}

Swing debt rarely appears as a bug. It appears as things you cannot say yes to.

- **Access.** Users need an installed client or a remote desktop session. A manager who wants to open one record from a phone cannot.
- **Deployment.** Each client machine needs a compatible Java runtime and a way to receive updates. A web app updates in one place, on the server.
- **Integration.** Embedding a dashboard, a report or a login flow from another web system means building the bridge yourself.
- **Hiring and onboarding.** New developers expect to ship web UIs. A desktop-only toolkit narrows who can maintain the app.
- **Look and feel.** Users compare your app to the web apps they use all day, and layout managers and custom painting make that gap expensive to close.

None of these needs a rewrite on its own. Together they explain why teams start asking about modernization, and the first answer is usually a full rewrite, which is also the most expensive one.

## Four ways to deal with it {#four-ways-to-deal-with-it}

| Option | What changes | Main cost | Main risk |
|---|---|---|---|
| Stay on desktop | Nothing | Debt keeps growing | Every new web request is refused |
| Remote desktop wrapper | Users reach the same UI remotely | Still a desktop UI, rendered remotely | Users reach the same desktop UI remotely |
| Full rewrite | Everything | Months of parallel work | Business logic recreated and re-broken |
| Embed, then replace with webforJ | One screen at a time | Planned, incremental work | Two UIs coexist for a while |

The last row is the one we recommend, and it reduces the debt gradually while the app stays in production.

## Why not a full rewrite {#why-not-a-full-rewrite}

Years of Swing code hold rules nobody wrote down anywhere else: validation quirks, rounding rules, special cases for one customer. A rewrite has to rediscover all of them. The usual blockers are complex domain logic that is risky to recreate, desktop-specific libraries or hardware, the time and cost of a full rewrite, and the need to keep feature parity. [Webswing](/docs/integrations/webswing/overview) lets those apps keep running in the browser.

The better plan keeps the old app alive while the new one grows around it. You do not choose between the old UI and the new one on day one. You move users across screen by screen.

## Java Swing to the web, step by step {#java-swing-to-the-web}

webforJ is the full-stack Java framework where you build the whole app, screens included, in Java and run it on the server, so your team keeps its language, its build tools, and its domain code. The path looks like this:

1. **Host the Swing app on a Webswing server.** [Webswing](https://www.webswing.org) runs your Swing, JavaFX or SWT app unchanged on the server and streams its interface to the browser. It is a separate server from your webforJ app, so the two need different ports and a CORS setting. The [setup guide](/docs/integrations/webswing/setup) covers both.
2. **Add the integration module.** The `webforj-webswing` artifact provides `WebswingConnector` and is available since webforJ 25.10.
3. **Embed the app in a webforJ route.** The connector renders the Swing UI inside a normal webforJ view.
4. **Build new features in webforJ.** New screens use webforJ components and sit next to the embedded app, inside the same navigation and layout.
5. **Replace screens until none are left.** Retire each Swing screen when its webforJ replacement is ready.

The dependency is one block in your `pom.xml`:

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

The view below embeds the legacy orders screen, adds a webforJ button that triggers a refresh inside the Swing app, and listens for an action the Swing app sends back:

```java
@Route("orders")
public class LegacyOrdersView extends Composite<FlexLayout> {

  public LegacyOrdersView() {
    WebswingConnector swing = new WebswingConnector("https://swing.example.com/orders/");
    swing.setSize("100%", "600px");

    Button refresh = new Button("Refresh orders");
    refresh.onClick(e -> swing.performAction("refresh"));

    swing.onAction(event -> {
      if ("orderSaved".equals(event.getActionName())) {
        event.getActionData().ifPresent(id -> refresh.setText("Saved order " + id));
      }
    });

    getBoundComponent().setDirection(FlexDirection.COLUMN);
    getBoundComponent().add(refresh, swing);
  }
}
```

The action names, `refresh` and `orderSaved` here, are defined by your Swing app through the Webswing Java API. The [communication page](/docs/integrations/webswing/communication) describes the lifecycle events and the payloads you can send in each direction.

## What you get after migrating {#what-you-get-after-migrating}

Once a screen is rebuilt in webforJ, it is a server-side Java UI. State and business logic stay on the server, and the browser receives a thin client. The framework batches server-to-client calls, and the browser loads a small JavaScript bootstrap and fetches further chunks on demand (see [client and server](/docs/architecture/client-server)). You write Java with components, events and data binding, not HTML templates or a separate JavaScript front end.

That addresses the debts listed earlier. Users open a URL, deployment happens once on the server, and the UI matches what people expect from the web. The team that maintained Swing can maintain this, because it is Java.

## A decision guide {#a-decision-guide}

Use these questions to pick your starting point.

- **Does anyone outside the office need this app?** Then the browser is already a requirement, and embedding through Webswing is your first step.
- **Is the code mostly forms, tables and reports?** Rebuild those screens first in webforJ. They map directly to webforJ components and give the quick visible win.
- **Is there a specialized editor or canvas that nobody wants to change?** Keep it embedded. A hybrid app is a valid end state, and it combines webforJ dashboards and reports with Swing for specialized visualizations.
- **Is the team about to lose the one person who knows the Swing code?** Start now. Embedding is a small first step that buys time.
- **Is the app being retired within a year?** Stay put. Modernizing something with an end date is not a good use of effort.

## Start with one screen {#start-with-one-screen}

Pick the Swing screen that gets the most requests from people who cannot run the desktop client. Host it on Webswing, embed it with `WebswingConnector`, and put it behind a webforJ route. If you are new to webforJ, [getting started](/docs/introduction/getting-started) creates a running project in minutes, and [startforJ](https://docs.webforj.com/startforj) generates one with the dependencies you choose.

## Frequently asked questions {#faq}

### Is Swing dead? {#faq-is-swing-dead}

No. Swing is still part of the JDK's `java.desktop` module and existing apps keep running. What is gone is the browser plug-in route, since the Applet API was deprecated for removal in JDK 17. The real issue is that a desktop-only UI cannot meet browser-first requirements.

### Is Java Swing end of life? {#faq-java-swing-end-of-life}

Swing has not been removed from the JDK, so it has no end-of-life date we can point to. Your support timeline comes from the Java runtime you deploy it on and the libraries your app depends on. Plan the modernization around your own requirements, not around a Swing sunset.

### Why modernize a Swing app? {#faq-why-modernize-swing}

Modernize when users need browser access, when client deployment becomes a burden, or when your Swing expertise is concentrated in a few people. With webforJ you can do it gradually: embed the Swing app first, then replace screens with Java-based webforJ components while the app stays in production.
