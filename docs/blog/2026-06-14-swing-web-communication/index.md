---
title: "Swing to JavaScript communication with webforJ and Webswing"
description: "Swing to JavaScript communication without glue code: the webforJ WebswingConnector sends named actions to an embedded Swing app and receives its events in Java."
slug: swing-web-communication
date: 2026-06-14
authors: Eric Handtke
tags: [community, webswing, modernization, events]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-06-14-swing-web-communication/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-06-14-swing-web-communication/cover.jpg)

Webswing gets a Swing app into a browser tab in an afternoon. Then someone asks for the obvious next thing: when the user picks a customer in the Swing window, the web page around it should show that customer's orders. And the web page can't see anything that happens inside the Swing window.

That is the real problem with Swing to JavaScript communication. The fix is a message channel: the Swing app announces what happened as a named action, the web side reacts, and it can send actions back. In webforJ that channel is one component, and both ends are plain Java.

<!-- truncate -->

This post builds that channel step by step, from embedding the Swing app to a full round trip that edits a Swing row in a web dialog, and ends with the pitfalls to plan around.

## The problem with an embedded Swing app {#the-problem}

Once a Swing app is streamed into a page, it is a black box. The browser sees a rendered surface. Your Java web code sees nothing. A user double-clicks a customer row and the Swing app opens its own dialog, in its own look, with its own validation, and the rest of the page has no idea.

You can try to fix this from outside with screen scraping or shared databases, but both options couple you to implementation details. A message channel is cleaner. The Swing side announces that something happened, the web side decides what to do, and it replies with another message.

That is what the connector gives you. The [overview](/docs/integrations/webswing/overview) describes three layers: the Webswing server running your desktop app, the `WebswingConnector` component in webforJ, and a bidirectional message protocol between them. This post is about the third layer.

## How the message channel works {#how-it-works}

A message is an action: a name, an optional string payload, and an optional binary payload. The name is yours to invent. The connector does not interpret it.

| Direction | Swing side | webforJ side |
| --- | --- | --- |
| Swing to web | `sendActionEvent(name, data, binaryData)` from the Webswing API | `onAction()` listener |
| Web to Swing | `addBrowserActionListener(...)` from the Webswing API | `performAction(name, data, binaryData)` |

On the Swing side you add the `org.webswing:webswing-api` dependency and call `WebswingUtil.getWebswingApi()`. It returns `null` when the app runs as a normal desktop program, so a null check keeps one code base working in both modes. The [modernization tutorial](/docs/integrations/webswing/tutorial) walks through that part, and the Webswing project documents the [Java API](https://www.webswing.org/docs/25.1/integrate/api) in full.

Everything on the webforJ side lives in `com.webforj.component.webswing`, shipped in the `webforj-webswing` module, available since webforJ 25.10.

## Embed the Swing app first {#embed-the-swing-app}

Server setup, ports and CORS are covered in the [setup guide](/docs/integrations/webswing/setup). Webswing and webforJ must not share a port, and the webforJ origin must be allowed in the Webswing app configuration. With that done, the view is short:

```java
@Route("/")
public class CustomerBridgeView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final WebswingConnector connector;
  private final Paragraph status = new Paragraph("Connecting");

  public CustomerBridgeView() {
    connector = new WebswingConnector("http://localhost:8080/customers/");
    connector.setSize("100%", "600px");
    self.add(status, connector);
  }
}
```

The connector starts on its own when it is added to the page. If you want control over timing, the two-argument constructor `new WebswingConnector(url, false)` skips the automatic start and you call `start()` yourself.

## Know when Swing is ready {#know-when-ready}

The connector fires two lifecycle events. `onInitialize` fires when the Webswing connection is established and ready for communication. `onStart` fires when the Swing app has fully loaded and is running. Both are useful for telling the user what is happening:

```java
connector.onInitialize(e -> status.setText("Connected"));
connector.onStart(e -> status.setText("Swing app running"));
```

If the view needs to send an action as soon as the page opens, send it from the `onStart` listener so the Swing app is running when the action arrives.

## Receive events from Swing {#receive-events}

The listener is `onAction`. The event gives you `getActionName()`, plus `getActionData()` and `getActionBinaryData()`, both of which return an `Optional<String>` because Swing may send a name with no payload.

```java
connector.onAction(e -> {
  switch (e.getActionName()) {
    case "select-customer" -> e.getActionData().ifPresent(this::openEditor);
    case "export-ready" -> e.getActionBinaryData()
        .ifPresent(d -> status.setText("Got " + d.length() + " characters"));
    default -> { }
  }
});
```

Keep the `default` branch. Swing code changes over the years, and an action you did not plan for should be ignored on purpose, not crash a handler.

One detail worth knowing: the payload is a string, so structured data needs a format. I use JSON, because the Swing side already has a JSON library in most enterprise apps and the webforJ side can parse it with any Java JSON library you prefer.

## Send actions into Swing {#send-actions}

The other direction is `performAction()`. It has three overloads: the name alone, the name with a string payload, and the name with a string payload and a binary payload passed as a string.

```java
// no payload: ask Swing to reload its table
connector.performAction("refresh");

// with data: select a record by id
connector.performAction("selectRecord", "12345");
```

The Swing app decides what each name means. That makes the action names a contract between two code bases, and I would treat it like any other API. Write the names down in one place, version them if both sides ship separately, and keep payloads small. A customer id is a good payload. A whole object graph is a sign the web side should own that data.

## Round trip: edit a row in a web dialog {#round-trip}

Here is the pattern that earns its keep in practice. The Swing table stays, but its edit dialog moves to the web. A double-click in Swing sends `select-customer` with the row as JSON. webforJ opens a dialog, the user edits, and on save the web side sends `update-customer` back so Swing refreshes the row.

```java
private void openEditor(String json) {
  Dialog dialog = new Dialog();
  dialog.addToContent(new Paragraph(json));
  Button save = new Button("Save", ButtonTheme.PRIMARY);
  save.onClick(c -> {
    connector.performAction("update-customer", json);
    dialog.close();
  });
  dialog.addToFooter(save);
  self.add(dialog);
  dialog.open();
}
```

In a real view you would replace the `Paragraph` with a form bound to the customer. The point is the shape of the loop. Swing announces, webforJ renders the modern UI, webforJ replies, Swing updates its model. Neither side calls into the other's internals.

On the Swing side, the two hooks are `api.sendActionEvent("select-customer", json, null)` to announce the double-click and `api.addBrowserActionListener(...)` to react when `update-customer` arrives. The dual-mode check from earlier lets the same double-click open the old Swing dialog when the app runs on a desktop.

## Trade-offs and pitfalls {#trade-offs}

Some things to plan for before you build on this.

- **String payloads only.** Binary data travels as a string too, so large files are a poor fit. Pass an id and let both sides fetch the file from shared storage.
- **No delivery acknowledgement.** An action is a notification, not a request with a response. If the web side needs a result, define a second action that carries it back.
- **Names are your API.** A typo in one side is silent. Constants shared in a small module help if the two projects can both depend on it.
- **State lives in two places.** If both UIs show the same customer, decide which one owns the truth. I would make webforJ the owner and let Swing be a view that refreshes on `update-customer`.
- **It is a bridge, not the destination.** The point of the channel is to let you replace Swing screens one at a time. When a screen has a webforJ equivalent, delete its actions.

That last point is why I like this approach for modernization. This channel is what makes each step of a migration cheap: you swap one dialog, one table or one report, and the rest of the Swing app keeps running.

## Why webforJ for this {#why-webforj}

You could wire the same thing by hand with the Webswing JavaScript client, a REST endpoint and your own browser code. Then you own a second language, a second build and a second place where events get lost. With webforJ the whole bridge is Java on the web side: typed events, `Optional` payloads, normal components in the dialog, and the same view class that holds the rest of your page.

That also means the team that knows the Swing code can maintain the web side without picking up a front-end toolchain. The connector is part of the framework, not a plugin from a third party.

## Next step {#next-step}

Clone the tutorial project linked from the [modernization tutorial](/docs/integrations/webswing/tutorial), run the Webswing server, and make one action work end to end: a double-click in Swing that opens a webforJ dialog. The [communication page](/docs/integrations/webswing/communication) has the reference for every method used earlier. To start a fresh webforJ project, use [startforJ](https://docs.webforj.com/startforj).

## Frequently asked questions {#faq}

### How do I send events between Swing and web? {#faq-send-events}

Call `performAction(name, data)` on the `WebswingConnector` to send an action from webforJ to Swing, and register `onAction()` to receive actions Swing sends with the Webswing API. Both sides use string names and string payloads.

### Is there a Webswing Java bridge in webforJ? {#faq-java-bridge}

Yes. The `webforj-webswing` module provides `WebswingConnector`, which handles the connection and the message channel from Java. You do not write browser script for the exchange.

### Can I embed Swing in a web app and react to its events? {#faq-embed-swing}

Yes. Embed the Webswing-hosted app with `new WebswingConnector(url)`, then use `onStart` to know it is running and `onAction` to react when the Swing code sends an action.

### Can the Swing app keep running as a desktop program? {#faq-desktop-mode}

Yes. `WebswingUtil.getWebswingApi()` returns `null` outside Webswing, so the same code base can branch between the web path and the desktop path.
