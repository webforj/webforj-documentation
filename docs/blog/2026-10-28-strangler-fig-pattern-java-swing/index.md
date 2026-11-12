---
title: "Strangler Fig Pattern for a Java Swing App, Step by Step"
description: "Apply the strangler fig pattern to a Java Swing app. Run it in the browser with Webswing, embed it in webforJ, and replace screens one at a time."
slug: strangler-fig-pattern-java-swing
date: 2026-10-28
authors: webforJ
tags: [insights, modernization, webswing]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-28-strangler-fig-pattern-java-swing/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-28-strangler-fig-pattern-java-swing/cover.jpg)

You have a Swing app with years of business logic in it, and a full rewrite would stop everything else for as long as it takes. The strangler fig pattern for a Java Swing app is the usual way out: the new web app grows around the old one, and the old one is switched off only when nothing is left in it.

Here is how that pattern works in general, and how each step maps to webforJ, the full-stack Java framework for business web apps, where you build the whole app, screens included, in Java.

<!-- truncate -->

## What is the strangler fig pattern? {#what-is-it}

The name comes from a tree that grows around a host tree until the host is gone. In software it describes a migration in three moves:

1. A new system is built around the old one while the old one keeps running.
2. Traffic moves to the new system piece by piece, so users never face a cutover day.
3. When the last piece has moved, the old system is retired.

For a desktop app, a "piece" is a screen or a workflow. The hard part is the first move: the old app must be reachable from the new one, or users have to switch between two programs. That is where Webswing comes in.

## Can I migrate without a rewrite? {#without-rewrite}

You put the Swing app inside a web app and replace its screens one at a time. In webforJ the [progressive modernization](/docs/integrations/webswing/overview) path has four stages that follow the three moves:

1. Embed the existing Swing app in a webforJ app.
2. Build new features in webforJ around the embedded app.
3. Gradually replace Swing screens with webforJ equivalents.
4. Phase out the legacy app entirely.

Your Java team and your domain code stay in place the whole time. The sections below take the stages in order. The same path applied to an ERP is described in [modernizing an ERP UI one screen at a time](/blog/modernize-erp-ui-java).

## Step 1: run Swing in the browser {#step-one}

[Webswing](/docs/integrations/webswing/overview) is a web server technology that lets Java desktop applications (Swing, JavaFX, SWT) run in a browser without any modification to the source code. It renders the app on the server and streams the interface to the browser, and it forwards mouse and keyboard input back to the real app. The post on [running a Swing app in a browser tab without a rewrite](/blog/run-java-swing-app-in-browser) covers that step on its own.

This is the host tree of the pattern. Right away your users get a web address for the app they already use, and you have not touched a line of its code. The [setup guide](/docs/integrations/webswing/setup) covers installing the Webswing server, registering your app in the admin console and allowing your webforJ app's origin through CORS.

## Step 2: embed it with WebswingConnector {#step-two}

The `WebswingConnector` component embeds a Webswing-hosted app inside a webforJ view. It needs the `webforj-webswing` module, which you add to your `pom.xml`, and a webforJ version of `25.10` or later. The view below is the whole embedding:

```java
@Route
public class SwingAppView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private WebswingConnector connector;

  public SwingAppView() {
    connector = new WebswingConnector("http://localhost:8080/myapp/");
    connector.setSize("100%", "600px");
    self.add(connector);
  }
}
```

The connector opens the connection when it is added to the page, and the Swing UI renders inside it. From here the new system exists. It is a webforJ app with one view that happens to contain the old app, so you can add routes, navigation and layout around it without the Swing code knowing.

## Do old and new screens run together? {#side-by-side}

Yes. The connector supports [two-way communication](/docs/integrations/webswing/communication), so the Swing side and the webforJ side stay in sync while you move screens.

From webforJ you send actions to the Swing app with `performAction()`, optionally with data. The action names and data formats are defined by your Swing app. From the Swing side, custom actions sent through the Webswing Java API arrive in webforJ as action events:

```java
connector.performAction("selectRecord", "12345");

connector.onAction(event -> {
  switch (event.getActionName()) {
    case "select-customer":
      event.getActionData().ifPresent(data -> {
        // open a webforJ form for this customer
      });
      break;
  }
});
```

The connector also fires an initialize event when the connection is ready and a start event when the Swing app is loaded and interactive. Those two events are useful for showing the web chrome only once the old screen is usable.

## Step 3: replace screens one at a time {#step-three}

This is the strangling itself. The [modernization tutorial](/docs/integrations/webswing/tutorial) walks through a Swing customer table. When a user double-clicks a row, the Swing app, running under Webswing, sends a `select-customer` action instead of opening its own `JOptionPane` dialog. webforJ receives it, opens a web form built with webforJ components, and on save sends an `update-customer` action back so the Swing table refreshes.

One dialog has moved, and nothing else did. The tutorial lays out how the same idea continues:

1. Embed the entire Swing app and replace only the edit dialog.
2. Add webforJ navigation and menus around the embedded app.
3. Replace the table with a webforJ table, and keep Swing for features that are hard to replace.
4. Replace all remaining Swing components.

Because the original Swing app stays functional, you can fall back to desktop deployment if needed, test new screens next to existing ones, and migrate users gradually. For the screen-by-screen mapping of `JFrame`, `JTable` and `JOptionPane`, read our [Swing to web migration guide](/blog/swing-to-web-migration).

## Step 4: retire the old app {#step-four}

When the last Swing screen has a webforJ replacement, the connector view has nothing left to embed. You delete it, shut down the Webswing server, and the old app is gone. Users watched the interface change screen by screen, and the domain layer kept its Java the whole way.

## How long does it take? {#how-long}

Within four months, Prodin delivered their first web-based modules to customers. The details are in the [Prodin success story](https://webforj.com/wp-content/uploads/2024/10/BASIS_SuccessStory_Prodin.pdf).

## Let an AI agent do the port {#ai-port}

Replacing a screen is repetitive work: read the Swing screen, map its components, bind the data, and wire the actions through the connector. A coding agent can do each port, and the [webforJ AI plugin](/docs/ai-tooling) connects it to webforJ. One install adds the [MCP server](/docs/ai-tooling/mcp), which gives the agent live access to webforJ documentation and API checks, and the [Agent Skills](/docs/ai-tooling/agent-skills), which give it task-specific workflows for building forms, styling apps, and creating components.

With them the agent looks up each API in the real framework before writing it, and you review one finished screen at a time.

The agent can also check its own work. Ask it for a test with every screen it ports, so each one comes back with a pass or a fail before you review it. That write, check, review cycle is [the AI verification loop for Java](/blog/ai-verification-loop-java), and it fits a migration well, because every screen you move is a small, testable unit.

## Frequently asked questions {#faq}

### What is the strangler fig pattern for a Swing app? {#faq-pattern}

It is a migration approach where a new web app grows around the old Swing app, users move to it piece by piece, and the Swing app is retired once nothing depends on it. With webforJ, Webswing runs the Swing app in the browser and `WebswingConnector` embeds it in a webforJ app.

### Do I have to change my Swing source code first? {#faq-source}

No. Webswing runs Swing, JavaFX and SWT apps in the browser without modifications to the original source code. Changes to the Swing code are only needed when you want it to send custom actions to webforJ.

### Can old Swing screens and new web screens run side by side? {#faq-side-by-side}

Yes. The same page can hold a webforJ screen and the embedded Swing app, and the two exchange actions and events through the connector.

### How do I migrate a Swing app to the web gradually? {#faq-gradual}

Embed the app with `WebswingConnector`, build new screens in webforJ around it, replace Swing screens one at a time, and phase out the legacy app at the end.

### How long does a gradual Swing migration take? {#faq-how-long}

Within four months, Prodin delivered their first web-based modules to customers. The details are in the [Prodin success story](https://webforj.com/wp-content/uploads/2024/10/BASIS_SuccessStory_Prodin.pdf).

## Embed your Swing app in one view {#start}

Start with the [Webswing setup](/docs/integrations/webswing/setup): register your Swing app on a Webswing server, add the `webforj-webswing` dependency, and put `WebswingConnector` in one view. Then pick the dialog your users open most and replace it with a webforJ form.
