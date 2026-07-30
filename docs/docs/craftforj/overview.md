---
sidebar_position: 1
title: craftforJ
slug: /craftforj
hide_table_of_contents: true
hide_giscus_comments: true
description: Inspect the component tree of a running webforJ app, change components live, and write the changes you keep back into your Java source.
sidebar_class_name: new-content
---

<Head>
  <style>{`
  .container {
    max-width: 65em !important;
  }
  `}</style>
</Head>

<!-- vale off -->
import DocCardList from '@theme/DocCardList';

<!-- vale on -->

<DocChip chip='since' label='26.02' />

**craftforJ** is the visual development environment that ships with webforJ. It runs inside your app in development mode and gives you a live view of the components your Java code created. You can select a component, change its properties, see the running app update immediately, and write the changes you want to keep back into the Java file that created them.

Because craftforJ reads the app through webforJ itself, it describes the app in the terms you wrote it in. The tree lists your components rather than the markup the browser rendered, the properties are the ones your components declare, and the routes are the ones your router registered, together with the access rules you annotated them with.

<div class="videos-container">
      <video controls>
        <source src="https://cdn.webforj.com/webforj-documentation/video/craftforJ/intro.mp4" type="video/mp4" />
      </video>
</div>

## What you can do with it {#what-you-can-do-with-it}

- **[Inspect components](./inspector.md)** - browse the component tree, select a component by clicking it in the page, and change its properties while the app runs.
- **[Write changes to source](./source-changes.md)** - review your live edits as a diff and apply them to your Java files.
- **[Work with routes](./routes.md)** - see the routing table, navigate to any route, and change the access rules declared on it.
- **[Theme the app](./theme.md)** - adjust the design tokens your app is built from and save the result into your stylesheet.
- **[Use the AI agent](./ai.md)** - a coding agent inside the running app that writes Java freely, compiles what it wrote, and applies it with your approval.

## How it differs from a debugger {#how-it-differs-from-a-debugger}

A debugger pauses your code and shows you the state of your variables at that moment. craftforJ leaves the app running and shows you the interface your code produced, so you work with the result rather than the execution. The two answer different questions and are commonly used together.

## Development mode only {#development-mode-only}

craftforJ requires two separate settings to be enabled, and by default it answers only a browser running on the same machine as the app. Projects created with [startforJ](https://docs.webforj.com/startforj) or from a webforJ [archetype](/docs/building-ui/archetypes/overview) enable it for you, so it's available the first time you run them. See [Security](./security.md) for what craftforJ can reach and how to confirm it's off in production.

## Topics {#topics}

<DocCardList className="topics-section" />
