---
title: Setup and Configuration
sidebar_position: 2
---

Integrating Webswing with webforJ involves two components: the Webswing server that hosts your Swing app, and the `WebswingConnector` component in your webforJ app that embeds it.

## Prerequisites

Before you begin, make sure you have the following prerequisites:

- **Java desktop app** - A Swing, JavaFX, or SWT app packaged as a JAR file
- **Webswing server** - Download from [webswing.org](https://webswing.org)
- **webforJ version `25.10` or later** - Required for `WebswingConnector` support

## Architecture overview

The integration architecture consists of:

1. **Webswing Server**: runs your Swing app, captures the GUI rendering, and handles user input
2. **webforJ Application**: hosts your web app with the embedded `WebswingConnector`
3. **Browser Client**: displays both the webforJ UI and the embedded Swing app

:::important Port Configuration
Webswing and webforJ must run on different ports to avoid conflicts. Both webforJ and Webswing typically run on port `8080`. You should change either the Webswing port or the webforJ port.
:::

## Webswing server setup

### Installation and startup

1. **Download Webswing** from the [official website](https://www.webswing.org/en/downloads)
2. **Extract the archive** to your preferred location (e.g., `/opt/webswing` or `C:\webswing`)
3. **Start the server** using the platform-specific scripts:

<Tabs>
      <TabItem value="Linux" label="Linux" default>
        ```bash
        webswing.sh
        ```
      </TabItem>
      <TabItem value="macOS" label="macOS">
        ```bash
        webswing.command
        ```
      </TabItem>
      <TabItem value="Windows" label="Windows">
        ```bash
        webswing.bat
        ```
      </TabItem>
</Tabs>


4. **Verify the server is running** by accessing `http://localhost:8080`

### Application configuration

Once the server is running, access the admin console at `http://localhost:8080/admin` to add and configure your Swing app.

In the admin console, configure:

- **Application Name** - Becomes part of the URL path (e.g., `myapp` → `http://localhost:8080/myapp/`)
- **Main Class** - The entry point of your Swing app
- **Classpath** - Path to your app JAR and dependencies
- **JVM Arguments** - Memory settings, system properties, and other JVM options
- **Home Directory** - Working directory for the app

After configuration, your Swing app will be accessible at `http://localhost:8080/[app-name]/`

## webforJ Integration

Once your Webswing server is running with your Swing app configured, you can integrate it into your webforJ app. This involves adding the dependency, configuring CORS, and creating a view with the `WebswingConnector` component.

### Add dependency

Add the Webswing integration module to your webforJ project. This provides the `WebswingConnector` component and related classes.

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### CORS configuration

Cross-Origin Resource Sharing (CORS) configuration is required when Webswing and webforJ run on different ports or domains. The browser's same-origin policy blocks requests between different origins without proper CORS headers.

Create a servlet filter to add CORS headers to your webforJ app:

```java title="CorsFilter.java"
package com.example.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // pass
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (response instanceof HttpServletResponse) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setHeader("Access-Control-Allow-Origin", "*");
      httpResponse.setHeader("Access-Control-Allow-Methods",
          "GET, POST, PUT, DELETE, OPTIONS, HEAD");
      httpResponse.setHeader("Access-Control-Allow-Headers",
          "Content-Type, Authorization, X-Requested-With");
      httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // pass
  }
}
```

Register the filter in your `web.xml`:

```xml
<filter>
  <filter-name>CorsFilter</filter-name>
  <filter-class>com.example.config.CorsFilter</filter-class>
</filter>

<filter-mapping>
  <filter-name>CorsFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>
```

:::important Access in Production Environments
For production environments, replace the wildcard (`*`) in `Access-Control-Allow-Origin` with your specific Webswing server URL for security.
:::

### Basic Implementation

Create a view that embeds your Swing app using the `WebswingConnector`:

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private WebswingConnector connector;

  public SwingAppView() {
    // Initialize the connector with your Webswing application URL
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // Set the display dimensions
    connector.setSize("100%", "600px");

    // Add to the view container
    getBoundComponent().add(connector);
  }
}
```

The connector automatically establishes a connection to the Webswing server when added to the DOM. The Swing app's UI is then rendered within the connector component.

## Configuration options

The `WebswingOptions` class allows you to customize the connector's behavior. By default, the connector starts automatically when created and uses standard connection settings. You can modify this behavior by creating a `WebswingOptions` instance and applying it to the connector.

For example, to hide the logout button in a production environment where you manage authentication through your webforJ app:

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // Hide the logout button

connector.setOptions(options);
```

Or if you need manual control over when the connection starts:

```java
// Create connector without auto-start
WebswingConnector connector = new WebswingConnector(url, false);

// Configure and start when ready
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

The options cover connection management, authentication, debugging, and monitoring.