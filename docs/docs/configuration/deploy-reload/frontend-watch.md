---
title: Frontend watch
sidebar_position: 20
description: Rebuild the sources under src/main/frontend while a webforJ app runs, applying stylesheet and image output in place and reloading the view for script output.
---

The frontend watch rebuilds the sources under `src/main/frontend` while the app runs and sends the output to the browser. It's the development side of the [frontend bundler](/docs/managing-resources/bundler/overview) and requires `webforj.devtools.livereload.enabled` to be on, see the [settings](/docs/configuration/deploy-reload/overview#settings).

## Running the watch {#running-the-watch}

Run the `watch` goal before the goal that starts the app. An archetype project sets this as its default goal, so `mvn` with no arguments runs both:

```bash
mvn compile webforj:watch spring-boot:run
```

```bash
mvn compile webforj:watch jetty:run
```

To run the watch as a standalone build step, see [Build and tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## How the output applies {#how-the-output-applies}

The browser action depends on the produced output, not on the edited file:

| Output | Browser action |
|---|---|
| Stylesheet, from a `.css`, `.scss`, `.sass`, or `.less` source | Applied in place. No reload, form data and scroll position stay. |
| Image | Swapped in place. No reload. |
| Any other output, such as compiled `.ts`, `.tsx`, or `.js` | The view reloads. |

When one rebuild produces several files, the browser applies them in place only if every file qualifies. Otherwise it reloads once, so a change never applies partially.

## During a server restart {#during-a-server-restart}

A Java change without a [hotswap tool](/docs/configuration/deploy-reload/hotswap) restarts the server. Through the restart:

- Applied styles stay on the page.
- An indicator shows while the server is down. It appears for a restart only, not for a manual reload.
- The page reloads when the app is ready, not before.

A `@BundleEntry` addition or removal takes effect when that restart completes.
