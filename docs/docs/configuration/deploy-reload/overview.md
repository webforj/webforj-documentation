---
title: Redeployment and Live Reload
hide_table_of_contents: false
hide_giscus_comments: true
description: Apply code changes to a running webforJ app during development, on the server through hotswap or a restart, and in the browser through live reload.
---

During development, webforJ applies saved changes to the running app and updates the browser. Class changes reach the app through a [hotswap tool](/docs/configuration/deploy-reload/hotswap) or through a restart. Live reload updates the browser after either.

Projects created from an [archetype](/docs/introduction/getting-started) come configured. For an existing project, follow [Spring Boot](/docs/configuration/deploy-reload/spring-devtools) or [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin).

## How each change applies {#how-each-change-applies}

| Change | Result | Reference |
|---|---|---|
| Java class, hotswap tool attached | The class updates in the running app. The affected part of the page rebuilds and the app state stays. | [Hotswap](/docs/configuration/deploy-reload/hotswap) |
| Java class, no hotswap tool | The app restarts. The browser reloads when the app is ready. | [Spring Boot](/docs/configuration/deploy-reload/spring-devtools), [Jetty](/docs/configuration/deploy-reload/maven-jetty-plugin) |
| Stylesheet or image | The page applies it in place, without a reload. | [Settings](#settings) |
| Source under `src/main/frontend` | The watch rebuilds it and updates the browser. | [Frontend watch](/docs/configuration/deploy-reload/frontend-watch) |

## Settings {#settings}

These settings control live reload during development:

| Property | Default | Description |
|----------|---------|-------------|
| `webforj.devtools.livereload.enabled` | `false` | Turns live reload on for development runs. |
| `webforj.devtools.livereload.websocket-port` | `35730` | Port for the browser connection. |
| `webforj.devtools.livereload.websocket-path` | `/webforj-devtools-ws` | Path for the browser connection. |
| `webforj.devtools.livereload.static-resources-enabled` | `true` | Applies stylesheet and image changes in place instead of reloading the page. |
| `webforj.devtools.livereload.heartbeat-interval` | `30000` | Interval in milliseconds for the connection checks that detect a restarting server. |

The keys have no effect in a packaged app. Packaged apps contain no development tools.

## Topics {#topics}

<DocCardList className="topics-section" />
