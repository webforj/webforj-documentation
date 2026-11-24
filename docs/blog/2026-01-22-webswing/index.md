---
title: "Webswing and webforJ: a modernization roadmap"
description: "Turn a legacy Java desktop app into a web app by using webforJ and Webswing to deploy it to the web and modernize it incrementally."
slug: webswing-and-webforj
date: 2026-01-22
authors: Garrison Osteen
tags: [integrations, modernization, webswing]
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/webswing/blog_webswing_cover.png)

If you're lucky enough to be writing a new web app from scratch, it's easy to see how webforJ could benefit you.
webforJ, a full-stack Java framework, simplifies your deployment and provides a [set of components](/docs/components/overview) that conforms to web standards and user expectations, all while you enjoy the familiar experience of coding in Java and integrating with the Java ecosystem.
But what if you already have a Java desktop app, and you need to deploy it to the web?
Do you have to rewrite the whole thing, or can you **modernize your legacy Java code into a fully functional web app**?
Look no further, because webforJ has the answer: deploy your existing Java app to the web quickly with Webswing, and gradually modernize it into a true web app with webforJ.

Webswing and webforJ are partners, and that's the short version of this post: two companies, one path. Webswing runs the existing Java desktop app in the browser, and webforJ carries it forward screen by screen, with a connector between them.

<!-- truncate -->

## Why web apps? {#why-web-apps}
You already have a Java desktop app, so why change?
Well, hosting your app on the web comes with a "host" of benefits.
For one, users are able to access your app from any device with a browser, and opening it is as simple and familiar as clicking a link.
Users also don't need to download any software or worry about version compatibility and upgrades.
They don't even need to install Java! By making your app available on the web, you take care of all of that for them, streamlining their experience so they can focus on the things they care about.
Accessing an app on the web is more than just a convenience. It's the experience that users expect, and it shows that you can meet their needs.


## What is Webswing? {#what-is-webswing}
![Webswing logo](https://cdn.webforj.com/webforj-documentation/blogs/webswing/webswing.png)
[Webswing](https://www.webswing.org/) provides a quick way to get your Java app online, whether it's written in Swing, JavaFX or SWT.
Essentially, Webswing is a web server that hosts your desktop Java apps, making them available on the web without any code changes.
Instead of scrapping your codebase and starting from scratch, you can simply host it with Webswing and run it in a browser as-is.

Using Webswing to host your app gives you *speed*, *simplicity*, and *stability*.
It gets your app online quickly, allowing your users to enjoy the benefits of a web app immediately, without waiting on lengthy development efforts.
Most apps won't require any source code changes, making it a simple solution to implement.
Additionally, you don't need to worry about problems caused by any UI changes, because your app will look and work the same in the browser as on the desktop.

However, this is just the beginning. Your users and developers will appreciate all these benefits, but they will surely want more.
So what's the next step?

## Two companies, one path {#webswing--webforj}

Although Webswing is available as a standalone option, the story doesn't have to end there.
The partnership of webforJ and Webswing provides a **complete roadmap for Java app modernization**, making web development in Java possible, no matter where you're starting from.
So, to set yourself up for future success, use Webswing and webforJ together, and be a Java web developer!
When you use Webswing with webforJ, in addition to bringing your app immediately to the web, you also crucially create a clear path forward for your future development.

The partnership is public on both sides. Webswing lists webforJ on its [partners page](https://www.webswing.org/en/company/partners), and webforJ announced the partnership with the [25.10 release](/blog/whats-new-v25.10). The two teams ran a joint webinar and appeared together at Java Forum Stuttgart 2025.

The path has two parts:

- **Webswing** runs the existing desktop app on its server and streams it to the browser.
- **webforJ** is the Java framework the new screens are written in, and it ships the `WebswingConnector` that joins the two. It comes from BASIS, which has built development tools since 1985.


## Implementation {#implementation}

webforJ integrates with Webswing through the `WebswingConnector` component, which embeds an app hosted on a Webswing server in your webforJ app.
This component manages communication between your app and the Webswing server through commands and events, giving you the ability to both control and respond to the embedded app.
For the Swing app to send its own events, it needs a small addition that uses the Webswing Java API.
See the Webswing integration [Communication](/docs/integrations/webswing/communication) page for technical details on how to coordinate between webforJ and Webswing.

## Incremental modernization {#incremental-modernization}

Once you deploy your app with the `WebswingConnector` component in webforJ, you can start modernizing it incrementally, while the app keeps working in full.
See the [modernization tutorial](/docs/integrations/webswing/tutorial) for more information on this process.
In short, it's easy to keep everything working while you convert portions of your app to webforJ components.
Every dialog or menu that you implement in webforJ will improve your users' experience and convey that your app is at home on the web.
Users will feel more comfortable, and you avoid the higher risk of all-or-nothing rewrites while the app moves toward a full web UI.
To weigh this path against a rewrite or a browser-only move, read the [Java Swing to web migration guide](/blog/migrate-swing-app-to-web).

To make the embedded Swing screens match the new ones, add the [Swing look and feel](/docs/integrations/webswing/look-and-feel) to the Swing app. It's one JAR on the classpath and one call at startup, and it gives the Swing app the colors, fonts, buttons, and focus outlines of webforJ. It comes with a light and a dark palette. Both sides then share one palette, so users don't feel they switch apps.

The same split shows where AI fits. On the Webswing side the Swing UI streams as it is, so an AI assistant has nothing there to change. On the webforJ side, an assistant can draft the new screens in Java and return each one with a test for your developers to review. That cycle is [the AI verification loop for Java](/blog/ai-verification-loop-java).

With Webswing and webforJ, you have no reason to stay locked into the desktop environment!
Your app wants to join the rest of its peers in the browser, and you can bring it there today.
[Download Webswing](https://www.webswing.org/downloads), modernize your app, and set yourself up for success.

## See also {#see-also}

- [Webswing and webforJ modernization tutorial](/docs/integrations/webswing/tutorial)
- [Webswing and webforJ integration setup](/docs/integrations/webswing/setup)
- [Webswing and webforJ communication](/docs/integrations/webswing/communication)
- [Webswing.org](https://www.webswing.org/)
- [Webswing integration demo ](https://webswing.webforj.com/)

## Plan the path with both teams in view {#plan-your-move}

To see how the two parts fit your own desktop app and talk it through with us, start on the [Swing modernization page](/modernization).
