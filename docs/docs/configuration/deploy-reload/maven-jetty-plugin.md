---
title: Maven Jetty plugin
---

The Maven Jetty plugin is a popular tool that allows developers to run Java web apps within an embedded Jetty server directly from their Maven projects. 

The Jetty Plugin launches an embedded Jetty server that monitors your app’s files, including Java classes and resources, for changes. When it detects updates, it automatically redeploys the app, which speeds up development by eliminating manual build and deployment steps. 

## Jetty configurations

Here are some essential configurations for fine-tuning the plugin’s hot deployment and server interaction settings:

| Property                          | Description                                                                                                                                                                           | Default        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`scan`**         | Configures how often the Jetty server scans for file changes in the **`pom.xml`**. The skeleton project sets this to `2` seconds. Increasing this interval can reduce CPU load but may delay changes being reflected in the app. | `1`            |

## webforJ configurations

| Property                          | Description                                                                                                                                                                           | Default        |
|-----------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| **`webforj.reloadOnServerError`** | When using hot redeploy, the whole WAR file is swapped. If the client sends a request while the server is restarting, an error occurs. This setting allows the client to attempt a page reload, assuming the server will be back online shortly. Only applies to development environments and only handles errors specific to hot redeployment. | `on`           |
| **`webforj.clientHeartbeatRate`** | Sets the interval for client pings to query server availability. This keeps the client-server communication open. For development, use shorter intervals for faster error detection. In production, set this to at least 50 seconds to avoid excessive requests. | `50s`          |

## Usage considerations

While the Jetty Plugin is highly effective for development, it has a few potential limitations:

- **Memory and CPU usage**: Frequent file scanning, set by low `scan` values in the `pom.xml`, can increase resource consumption, especially on large projects. Increasing the interval may reduce load but also slows down redeployment.

- **Limited production use**: The Jetty Plugin is designed for development, not for production environments. It lacks the performance optimization and security configurations required for production, making it better suited to local testing.

- **Session management**: During hot redeployment, user sessions may not be preserved, especially when large structural changes occur in the code. This can disrupt tests involving user session data, requiring manual session management or workaround configurations for development.