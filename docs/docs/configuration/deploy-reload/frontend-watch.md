---
title: Frontend watch
sidebar_position: 20
sidebar_class_name: new-content
description: Rebuild frontend sources while a webforJ app runs and refresh the browser, with stylesheet and image changes applied in place and script changes reloading the view.
---

The frontend watch rebuilds your `src/main/frontend` sources while the app runs and refreshes the browser, so a frontend change shows up without a manual build or a server restart. It's the development side of the [frontend bundler](/docs/managing-resources/bundler/overview).

## Running the watch {#running-the-watch}

Run it alongside your server, as the goal before the one that starts the app. A Spring project sets this as its default goal, so `mvn` with no arguments starts both:

```bash
mvn compile webforj:watch spring-boot:run
```

Against the [Maven Jetty plugin](/docs/configuration/deploy-reload/maven-jetty-plugin), start it the same way:

```bash
mvn compile webforj:watch jetty:run
```

For the watch as a build step, see [Build and tests](/docs/managing-resources/bundler/build-and-tests#the-development-watch).

## What happens on a change {#what-happens-on-a-change}

When you save a source, the watch rebuilds and sends the changed files to the browser. What the browser does depends on the output, not the source you edited:

- A **stylesheet** patches in place, with no reload, so form data and scroll position stay. A `.css`, `.scss`, `.sass`, or `.less` edit lands here.
- An **image** swaps in place, with no reload.
- **Anything else** reloads the view. A `.ts`, `.tsx`, or `.js` edit lands here, since new behavior needs a fresh page.

If a rebuild touches several files and all can patch in place, the browser stays put. If even one can't, it reloads, so you never see a change land halfway.

## Across a server restart {#across-a-server-restart}

A Java change restarts the server, through [Spring DevTools](/docs/configuration/deploy-reload/spring-devtools), the [Maven Jetty plugin](/docs/configuration/deploy-reload/maven-jetty-plugin), or [JRebel](/docs/configuration/deploy-reload/jrebel). The watch keeps the frontend in step with it:

- **Your styles stay applied** across the restart, instead of flashing unstyled.
- **The page reloads once the app is ready**, not before, so you don't hit an error from reloading too early. A small indicator shows while the server restarts; a manual reload shows nothing.
- **Adding or removing a `@BundleEntry` takes effect on the next restart.**

## Configuration {#configuration}

Live reload is a webforJ setting, so it applies whichever server you run. A Spring app reads these keys from `application.properties`; a standalone server, such as the [Maven Jetty plugin](/docs/configuration/deploy-reload/maven-jetty-plugin), reads the same keys from `webforj.conf`.

```ini title="application.properties"
# Refresh the browser on a change
webforj.devtools.livereload.enabled=true

# Patch stylesheets and images in place instead of reloading (default: true)
webforj.devtools.livereload.static-resources-enabled=true

# Port and path the browser connects on (defaults: 35730, /webforj-devtools-ws)
webforj.devtools.livereload.websocket-port=35730
webforj.devtools.livereload.websocket-path=/webforj-devtools-ws

# Heartbeat interval in milliseconds (default: 30000)
webforj.devtools.livereload.heartbeat-interval=30000
```