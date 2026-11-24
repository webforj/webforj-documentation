---
title: "Run a Java Swing Application in the Browser Without a Rewrite"
description: "Run a Java Swing application in the browser without changing its source. Webswing streams it to a tab, and webforJ lets web screens replace it over time."
slug: run-java-swing-app-in-browser
date: 2026-10-30
authors: webforJ
tags: [integrations, webswing, modernization]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-30-run-java-swing-app-in-browser/cover-v3.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-30-run-java-swing-app-in-browser/cover-v3.jpg)

<!-- vale Google.WordListCase = NO -->
Your Swing app has run the business for years, and now people want it in a browser. Some work remotely, some have laptops without Java, and a rewrite would take months before anyone gets a result. So the real question is narrow: can you run a Java Swing application in the browser with the code you already have?
<!-- vale Google.WordListCase = YES -->

You can. Webswing runs the existing app on a server and streams its interface to a browser tab, and the source code doesn't change. Once the app lives in the browser, webforJ, the full-stack Java framework for business web apps where you build the whole app, screens included, in Java, can embed it and take over its screens one at a time.

<!-- truncate -->

## Can a Swing app run in a web browser? {#can-it-run}

Yes. [Webswing](/docs/integrations/webswing/overview) is a web server technology that lets Java desktop apps built with Swing, JavaFX or SWT run in a web browser without any modification to their source code. The app still runs as a Java program, on the server. Webswing renders it there, streams the interface to the browser through an HTML5 canvas, and sends mouse and keyboard input back to the server, where it's replayed on the actual Swing app.

Your users get the app they already know. They no longer install Java or start a JAR on their own machine.

## Do I have to change my Swing code? {#change-code}

Not to get it into the browser. Webswing takes the app packaged as a JAR file, and the work happens on the server:

1. Download Webswing, extract the archive and start the server with the script for your platform.
2. Open the admin console and register the app: a name, which becomes part of the URL path, the main class, the classpath with your JAR and its dependencies, JVM arguments and a home directory.
3. Open the server address followed by the app name, and the Swing app appears in the browser.

The [setup guide](/docs/integrations/webswing/setup) walks through each setting. Code changes only come in later, if you want the Swing app to talk to a web app around it. Then you add the Webswing API dependency to the Swing project. The API returns `null` when the app runs as a regular desktop app, so one build keeps working in both places.

## Why go further than a browser tab? {#why-further}

Webswing on its own gives you the old app at a web address. Most teams want new screens built for the web, while the parts that still work keep running. If you are still weighing options, one post explains [what the alternatives to Swing mean](/blog/java-swing-alternatives) for your team and your Swing code.

That's the job of webforJ's `WebswingConnector` component. It embeds a Webswing-hosted app directly inside a webforJ app, so the old app becomes one part of a new web app instead of the whole thing.

## Embed the app in a webforJ view {#embed}

The connector ships in the `webforj-webswing` module and needs webforJ `25.10` or later. You place it in a view with the address of your Webswing app, and it connects when it's added to the page. The Swing UI then renders inside the component.

Two settings matter when both servers run. Webswing and webforJ both default to port `8080`, so one of them has to move. And because the webforJ page loads content from the Webswing server, you add the webforJ app's origin to the allowed origins of your app in the Webswing admin console.

From here you have a webforJ app with one view that holds the old app. You can add routes and new screens around it without the Swing code knowing.

To make the old app look like part of the new one, add the [Swing look and feel](/docs/integrations/webswing/look-and-feel) to the Swing app. It's one JAR on the classpath and one call at startup, and the rest of the Swing code doesn't change. The Swing screens then use the colors, fonts, and buttons of webforJ and can take the primary color of the webforJ app, so users don't feel they switch apps.

## What happens after it runs in the browser? {#after}

The two sides talk through the connector, as the [communication guide](/docs/integrations/webswing/communication) shows. The webforJ app calls `performAction()` to send a named action, with optional data, to the Swing app. In the other direction, the connector fires an initialize event when the connection is ready, a start event when the Swing app has fully loaded, and action events for the custom actions the Swing app sends through the Webswing Java API.

That channel is what later lets web screens take over one at a time, and nothing forces that step on a schedule. The original Swing app stays functional the whole time, so you can fall back to desktop deployment and move users over gradually. How the new app grows around the old one until it can be retired is the subject of the [strangler fig pattern for Swing](/blog/strangler-fig-pattern-java-swing). The same path applies to a [legacy enterprise user interface](/blog/modernize-erp-ui-java), replaced one screen at a time with Java web views.

## What AI can and can't reach here {#ai-port}

Inside Webswing the Swing app reaches the browser as a canvas stream, so an AI assistant has nothing there to read or edit. Where an assistant helps is the step after this one: drafting the new webforJ screens that sit around the embedded app, each returned with a test that passes or fails. [The AI verification loop for Java](/blog/ai-verification-loop-java) explains those checks, and they apply to the webforJ code, not to the streamed Swing UI.

## Frequently asked questions {#faq}

### Does this work for JavaFX and SWT apps too? {#faq-javafx-swt}

Yes. Webswing runs Swing, JavaFX and SWT desktop apps in the browser, and `WebswingConnector` embeds any app the Webswing server hosts.

### Should I compare this with a full rewrite? {#faq-rewrite}

If you are still choosing a direction, [how to migrate a Swing app to the web](/blog/migrate-swing-app-to-web) compares the paths side by side, and [Java Swing alternatives](/blog/java-swing-alternatives) weighs what can replace Swing itself. This post covers the first step of the combined one: getting the existing app into a browser today.

## Register your Swing app on Webswing {#start}

Install the Webswing server, register your Swing app in the admin console and open it in a browser. Then follow the [Webswing setup](/docs/integrations/webswing/setup) to add the `webforj-webswing` dependency and put `WebswingConnector` in one view, and pick the dialog your users open most as the first screen to replace.

## Talk to us about your app {#plan-your-move}

When the app runs in the browser and you want to plan what comes after it with our team, start on the [Swing modernization page](/modernization).
