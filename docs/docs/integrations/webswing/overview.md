---
title: Webswing
hide_table_of_contents: true
hide_giscus_comments: true
sidebar_class_name: new-content
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

Webswing is a web server technology that allows Java desktop applications (Swing, JavaFX, SWT) to run in a web browser without any modifications to the original source code. It renders the desktop app on the server and streams the interface to the browser using HTML5 canvas, handling all user interactions transparently.

## What Webswing solves

Many organizations have substantial investments in Java desktop applications that contain critical business logic developed over years or decades. These applications often can't be easily rewritten due to:

- Complex domain logic that would be risky to recreate
- Integration with desktop-specific libraries or hardware
- Time and cost constraints of a full rewrite
- Need to maintain feature parity with existing functionality

Webswing allows these applications to be web-accessible without modification, preserving their original functionality and appearance.

## Integration with webforJ

The webforJ Webswing integration provides the `WebswingConnector` component, which allows you to embed Webswing-hosted applications directly within your webforJ app. This creates opportunities for:

### Progressive modernization

Instead of an all-or-nothing rewrite, you can:

1. Start by embedding your existing Swing app via `WebswingConnector`
2. Build new features in webforJ around the embedded app
3. Gradually replace Swing components with webforJ equivalents
4. Eventually phase out the legacy app entirely

### Hybrid applications

Combine modern web UI built with webforJ with specialized desktop functionality:

- Use webforJ for user-facing interfaces, dashboards, and reports
- Leverage Swing for complex visualizations or specialized editors
- Maintain a single integrated app experience

## How it works

The integration operates through three layers:

1. **Webswing Server** - Runs your Java desktop app, capturing its visual output and processing user input
2. **WebswingConnector Component** - A webforJ component that embeds the Webswing client, managing the connection and communication with the server
3. **Communication Protocol** - Bidirectional messaging that allows your webforJ app to send commands to the Swing app and receive events back

When a user accesses your webforJ app, the `WebswingConnector` establishes a connection to the Webswing server. The server creates or reconnects to an app instance, and begins streaming the visual state to the browser. User interactions (mouse, keyboard) are captured and sent to the server, where they're replayed on the actual Swing app.

## Topics {#topics}

<DocCardList className="topics-section" />