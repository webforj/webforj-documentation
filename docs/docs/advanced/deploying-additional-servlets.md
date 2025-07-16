---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
---
<!-- vale off -->
# Deploying Additional Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ routes all requests through `WebforjServlet`, which is mapped to `/*` in web.xml by default. This servlet manages the component lifecycle, routing, and UI updates that power your webforJ app.

In some scenarios, you may need to deploy additional servlets alongside your webforJ app:
- Integrating third-party libraries that provide their own servlets
- Implementing REST APIs or webhooks
- Handling file uploads with custom processing
- Supporting legacy servlet-based code

webforJ provides two approaches for deploying custom servlets alongside your app:

## Approach 1: Remapping `WebforjServlet`

This approach remaps the `WebforjServlet` from `/*` to a specific path like `/ui/*`, freeing up the URL namespace for custom servlets. While this requires modifying `web.xml`, it gives custom servlets direct access to their URL patterns without any proxy overhead.

```xml
<web-app>
  <!-- WebforjServlet remapped to handle only /ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- Custom servlet with its own URL pattern -->
  <servlet>
    <servlet-name>HelloWorldServlet</servlet-name>
    <servlet-class>com.example.HelloWorldServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>HelloWorldServlet</servlet-name>
    <url-pattern>/hello-world</url-pattern>
  </servlet-mapping>
</web-app>
```

With this configuration:
- webforJ components are accessible under `/ui/`
- Custom servlet handles requests to `/hello-world`
- No proxy mechanism involved - direct servlet container routing

:::tip Spring Boot configuration
When using webforJ with Spring Boot, there's no `web.xml` file. Instead, configure the servlet mapping in `application.properties`:

```Ini
webforj.servlet-mapping=/ui/*
```

This property remaps `WebforjServlet` from the default `/*` to `/ui/*`, freeing up the URL namespace for your custom servlets. Don't include quotes around the value - they will be interpreted as part of the URL pattern.
:::

## Approach 2: `WebforjServlet` proxy configuration

This approach keeps `WebforjServlet` at `/*` and configures custom servlets in `webforJ.conf`. The `WebforjServlet` intercepts all requests and proxies matching patterns to your custom servlets.

### Standard web.xml configuration

```xml
<servlet>
  <servlet-name>WebforjServlet</servlet-name>
  <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
  <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
  <servlet-name>WebforjServlet</servlet-name>
  <url-pattern>/*</url-pattern>
</servlet-mapping>

<!-- Custom servlet with its own URL pattern -->
<servlet>
  <servlet-name>HelloWorldServlet</servlet-name>
  <servlet-class>com.example.HelloWorldServlet</servlet-class>
</servlet>
<servlet-mapping>
  <servlet-name>HelloWorldServlet</servlet-name>
  <url-pattern>/hello-world</url-pattern>
</servlet-mapping>
</web-app>
```

### webforJ.conf configuration

```hocon
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

With this configuration:
- `WebforjServlet` handles all requests
- Requests to `/hello-world` are proxied to `HelloWorldServlet`