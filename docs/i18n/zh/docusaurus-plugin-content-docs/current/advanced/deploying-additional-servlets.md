---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# 部署额外的 Servlet <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ 通过默认映射到 `/*` 的 `WebforjServlet` 路由所有请求。该 servlet 管理组件生命周期、路由和 UI 更新，为您的 webforJ 应用提供支持。

在某些情况下，您可能需要在 webforJ 应用旁边部署额外的 servlets：
- 集成提供自定义 servlets 的第三方库
- 实现 REST API 或 Webhook
- 处理带有自定义处理的文件上传
- 支持遗留的基于 servlet 的代码

webforJ 提供了两种在您的应用旁边部署自定义 servlets 的方法：

## 方法 1：重新映射 `WebforjServlet` {#approach-1-remapping-webforjservlet}

此方法将 `WebforjServlet` 从 `/*` 重新映射到特定路径，如 `/ui/*`，释放 URL 命名空间以用于自定义 servlets。虽然这需要修改 `web.xml`，但它使自定义 servlets 可以直接访问其 URL 模式，而无需任何代理开销。

```xml
<web-app>
  <!-- WebforjServlet 重新映射为仅处理 /ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- 具有自己 URL 模式的自定义 servlet -->
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

通过此配置：
- webforJ 组件在 `/ui/` 下可访问
- 自定义 servlet 处理对 `/hello-world` 的请求
- 不涉及代理机制 - 直接 servlet 容器路由

:::tip Spring Boot 配置
在使用 webforJ 与 Spring Boot 时，没有 `web.xml` 文件。而是，在 `application.properties` 中配置 servlet 映射：

```Ini
webforj.servlet-mapping=/ui/*
```

此属性将 `WebforjServlet` 从默认的 `/*` 重新映射到 `/ui/*`，释放您自定义 servlets 的 URL 命名空间。请勿在值周围包含引号 - 它们会被解释为 URL 模式的一部分。
:::

## 方法 2：`WebforjServlet` 代理配置 {#approach-2-webforjservlet-proxy-configuration}

此方法保持 `WebforjServlet` 在 `/*`，并在 `webforJ.conf` 中配置自定义 servlets。`WebforjServlet` 拦截所有请求并将匹配模式代理到您的自定义 servlets。

### 标准 web.xml 配置 {#standard-webxml-configuration}

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

<!-- 具有自己 URL 模式的自定义 servlet -->
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

### webforJ.conf 配置 {#webforjconf-configuration}

```hocon
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

通过此配置：
- `WebforjServlet` 处理所有请求
- 对 `/hello-world` 的请求被代理到 `HelloWorldServlet`
