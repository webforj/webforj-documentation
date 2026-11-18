---
title: "Webswing with webforJ: modernize a Swing app step by step"
hide_table_of_contents: true
hide_giscus_comments: true
description: Use Webswing to run a Swing, JavaFX, or SWT app in the browser, embed it in a webforJ app, and replace it with web screens one at a time.
---

# Webswing <DocChip chip='since' label='25.10' />

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing runs a Swing, JavaFX, or SWT app in the browser without source code changes. With webforJ, you embed that app in a web app and replace it one screen at a time. For the two to exchange messages, you add the Webswing API to the Swing app.

Webswing is a web server technology. It renders the desktop app on the server and streams the interface to the browser using HTML5 canvas, handling all user interactions transparently.

For an overview of the whole path, from running the desktop app in the browser to retiring it, see [Swing modernization](/modernization).

## What Webswing solves {#what-webswing-solves}

Many organizations have substantial investments in Java desktop applications that contain critical business logic developed over years or decades. These applications often can't be easily rewritten due to:

- Complex domain logic that would be risky to recreate
- Integration with desktop-specific libraries or hardware
- Time and cost constraints of a full rewrite
- Need to maintain feature parity with the existing app

Webswing allows these applications to be web-accessible without modification. They keep their original features and appearance.

## Integration with webforJ {#integration-with-webforj}

The webforJ Webswing integration provides the `WebswingConnector` component, which allows you to embed Webswing-hosted applications directly within your webforJ app. This creates opportunities for:

### Progressive modernization {#progressive-modernization}

Instead of an all-or-nothing rewrite, you can:

1. Start by embedding your existing Swing app via `WebswingConnector`
2. Build new features in webforJ around the embedded app
3. Gradually replace Swing components with webforJ equivalents
4. Eventually phase out the legacy app entirely

### Hybrid applications {#hybrid-applications}

Combine modern web UI built with webforJ with specialized desktop features:

- Use webforJ for user-facing interfaces, dashboards, and reports
- Keep Swing for complex visualizations or specialized editors
- Maintain a single integrated app experience

:::tip Replacing Swing counterparts
Refer to [Coming from Swing](/docs/introduction/coming-from-swing) for comparisons between Swing components and their webforJ equivalents. The comparisons include relevant references to help you use each webforJ equivalent feature.
:::

## How it works {#how-it-works}

The integration operates through three layers:

1. **Webswing Server**: runs your Java desktop app, capturing its visual output and processing user input
2. **WebswingConnector Component**: a webforJ component that embeds the Webswing client, managing the connection and communication with the server
3. **Communication Protocol**: bidirectional messaging that allows your webforJ app to send commands to the Swing app and receive events back

When a user accesses your webforJ app, the `WebswingConnector` establishes a connection to the Webswing server. The server creates or reconnects to an app instance, and begins streaming the visual state to the browser. User interactions (mouse, keyboard) are captured and sent to the server, where they're replayed on the actual Swing app.

## Topics {#topics}

<DocCardList className="topics-section" />