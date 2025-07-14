---
sidebar_position: 50
title: Deploying Additional Servlets
---

webforJ routes all requests through `WebforjServlet`, which is mapped to `/*` in web.xml. This servlet handles the component lifecycle, routing, and UI updates for your webforJ app. 

Sometimes you need to deploy additional servlets alongside your webforJ app - for example, when integrating third-party libraries that provide their own servlets, or when you need servlet features that operate outside of webforJ's component model.

webforJ supports two approaches for deploying custom servlets:

## Approach 1: Remapping `WebforjServlet`

By remapping WebforjServlet from `/*` to a specific path like `/ui/*`, you free up the URL namespace for custom servlets. This approach requires modifying `web.xml` but gives custom servlets direct access to their URL patterns.

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

Keep `WebforjServlet` at `/*` and configure custom servlets in `webforJ.conf`. `WebforjServlet` will intercept all requests and proxy matching patterns to your custom servlets.

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