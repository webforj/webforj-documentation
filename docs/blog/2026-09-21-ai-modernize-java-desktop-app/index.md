---
title: "Migrate a Java desktop app to the web with AI: a decision guide"
description: "How to use AI to migrate a Java desktop app to the web with webforJ: when to rebuild screens, when to embed the Swing app, and how the MCP server keeps AI honest."
slug: ai-modernize-java-desktop-app
date: 2026-09-21
authors: webforJ
tags: [ai, modernization, webswing, mcp]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-09-21-ai-modernize-java-desktop-app/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-09-21-ai-modernize-java-desktop-app/cover.jpg)

Asking an AI assistant to convert a whole Swing app in one pass sounds fast. In practice you get thousands of lines nobody has reviewed, written against APIs the model half remembers, and a release date that keeps slipping.

There is a better way to migrate a Java desktop app to the web with AI: move it one screen at a time. Some screens are worth rebuilding in webforJ with an assistant connected to the webforJ MCP server. Others keep running as they are, embedded in the web app through `WebswingConnector`, until their turn comes. Your business logic stays in Java, and users get a web app from the first release.

<!-- truncate -->

The hard part is deciding which screen goes which way. This guide gives you a simple test for each screen, the brief to hand the assistant, and the limits to plan around.

## Why not let AI convert the whole app? {#why-not-convert-everything}

A prompt like "convert this Swing app to a web app" produces code that looks plausible and fails in ways you find late. A desktop app holds years of event wiring, modal dialogs, and state that lives in widgets. A general-purpose model has no map of the web framework you are targeting, so it guesses at class names, and you spend the saved time fixing guesses.

Two things change that. First, the target has to be a framework where the UI is written in Java, so the assistant maps Swing concepts to Java concepts instead of crossing into HTML, CSS, and a second language. webforJ builds every screen from Java components, which keeps a migrated screen close to the original structure. Second, the assistant needs live access to the real API surface. That is the job of the [webforJ MCP server](/docs/ai-tooling/mcp), which lets the assistant search the webforJ knowledge base, scaffold projects, and validate class names instead of recalling them from training data.

## Three ways to migrate {#three-ways}

Most desktop apps need a mix. Pick per screen, not per app.

| Path | What happens | Best for |
| --- | --- | --- |
| Embed | Webswing streams the unchanged Swing app into a webforJ page | Complex editors, charting canvases, screens nobody wants to change |
| Rebuild with AI | An assistant writes webforJ views from the Swing screen | Forms, lists, dashboards, CRUD screens |
| Keep the logic, replace the shell | Service and domain classes are reused, only the UI layer is new | Apps with a clean split between model and view |

The embed path is a real release strategy. It is how you ship the first web release while the rebuild is still running.

## Embed first: ship the old screen {#embed-first}

Webswing runs a Swing, JavaFX, or SWT app on the server and streams its interface to the browser, with no change to the original source. webforJ wraps that in the `WebswingConnector` component, available since 25.10. The [Webswing integration docs](/docs/integrations/webswing/overview) describe the setup: a Webswing server hosts the app, and your webforJ view points at its URL.

```java
@Route("orders")
public class LegacyOrdersView extends Composite<Div> {
  private final WebswingConnector legacy =
      new WebswingConnector("http://localhost:8080/orders-desktop/");
  private final Button refresh = new Button("Refresh orders");

  public LegacyOrdersView() {
    legacy.setSize("100%", "600px");

    refresh.onClick(e -> legacy.performAction("refresh"));

    legacy.onAction(event -> {
      if ("orderSelected".equals(event.getActionName())) {
        event.getActionData().ifPresent(id -> refresh.setText("Order " + id));
      }
    });

    getBoundComponent().add(refresh, legacy);
  }
}
```

The connector is bidirectional. `performAction` sends a command to the Swing app, and `onAction` receives the actions the Swing app sends back. The action names and data formats are defined by your Swing code through the Webswing Java API, which is covered in [Communication](/docs/integrations/webswing/communication). That channel is how a new webforJ toolbar can drive a legacy screen, and how a legacy selection can update a new web panel next to it.

One practical rule from the setup docs: Webswing and webforJ need different ports, and cross-origin embedding needs the allowed origins configured in the Webswing admin console. Do this on day one, because it is the first thing that blocks a demo.

## Rebuild with AI: the brief {#rebuild-with-ai}

Once the embedded app is live, pick the screens worth rebuilding. Good candidates share three traits: they are mostly forms and tables, the data comes from services you already have, and users complain about them.

Install the [webforJ AI plugin](/docs/ai-tooling), which bundles the MCP server with the [Agent Skills](/docs/ai-tooling/agent-skills). The skills give the assistant a workflow for building forms, styling an app, and creating reusable components. The MCP server answers the lookup questions along the way, such as which webforJ component fits a screen element or how to bind a field.

For each screen, give the assistant a narrow brief:

1. The Swing class for the screen and the service classes it calls.
2. The route the new view should live on.
3. A rule that the view calls the existing services and does not copy their logic.
4. A request to verify every webforJ class against the MCP server before finishing.

Then review the result like any pull request. Because the output is plain Java in the same language as your services, your existing code review, static analysis, and unit tests apply unchanged.

The webforJ AI plugin also installs in Claude Code and several other assistants. The [MCP server page](/docs/ai-tooling/mcp) lists the clients and the exact commands.

## Keep the logic, swap the shell {#keep-the-logic}

The cheapest migrations are the ones where Swing was only the view. If your services, repositories, and domain classes do not import `javax.swing`, they move as they are. A webforJ app can sit on Spring Boot, so existing Spring services plug in directly.

Where Swing leaked into the model, with listeners on data objects or `SwingWorker` doing business work, the assistant can help you find those seams, but the decision of where to cut is a design call. Keep that decision with your team.

## How to decide per screen {#decide-per-screen}

Use this order of questions for every screen in the app.

1. Is it a form, list, or dashboard? Rebuild it with AI.
2. Is it a specialized editor or canvas with deep Swing code? Embed it and revisit later.
3. Do users open it once a quarter? Embed it and leave it.
4. Does it block the first web release? Embed it, so the release ships.

The embed-then-rebuild order keeps the app releasable the whole way through. Each rebuilt screen replaces one embedded route, and the Webswing server shrinks until you can turn it off.

## Keeping the upgrade path clean {#upgrade-path}

Rebuilt apps need to stay current once they exist. For webforJ-to-webforJ version changes, the project ships OpenRewrite recipes that update dependencies, rename methods, and leave `TODO` comments where a manual fix is needed. You run `mvn rewrite:dryRun` to preview a patch and `mvn rewrite:run` to apply it. The recipes cover webforJ upgrades, not Swing migration, so treat them as the maintenance tool for the app you are about to build. Details are on the [automated upgrades page](/docs/upgrading/automated-upgrades).

## Next step {#next-step}

Start with one screen. Install the AI plugin from the [AI tooling docs](/docs/ai-tooling), generate a project through [startforJ](https://docs.webforj.com/startforj), and embed your running Swing app with the [Webswing setup guide](/docs/integrations/webswing/setup). Rebuild the simplest form first, so the team sees the full loop before you plan the rest.

## Frequently asked questions {#faq}

### Can AI rebuild a Swing app as a web app? {#faq-rebuild-swing}

It can rebuild individual screens well when it targets a Java UI framework and checks the API through the webforJ MCP server. It does not replace a design review of the whole app. Plan per-screen rebuilds with human review, and embed the screens you are not ready to change.

### Do I need to rewrite my business logic? {#faq-rewrite-logic}

Not if it is separated from the Swing view. Service and domain classes stay in Java and can be called from webforJ views, including in a Spring Boot app. Logic tied to Swing widgets needs to be pulled out first.

### How do LLMs modernize legacy Java safely? {#faq-llm-safely}

Constrain them. Give each task one screen, a fixed target framework, and live access to the real API through MCP, then run your normal tests and review. The assistant writes the first draft and your team keeps responsibility for the merge.

### Can the old Swing app run inside the new web app? {#faq-embed}

Yes. Webswing renders the desktop app on the server, and `WebswingConnector` embeds it in a webforJ page, with actions flowing both ways. This supports Swing, JavaFX, and SWT apps and needs webforJ 25.10 or later.
