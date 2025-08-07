---
sidebar_position: 50
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 4506bcc85ddfa8698f4f8138fe6b4e33
---
<!-- vale off -->
# 部署额外的 Servlet <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ 会通过 `WebforjServlet` 路由所有请求，默认在 web.xml 中映射为 `/*`。该 servlet 管理组件生命周期、路由和 UI 更新，为您的 webforJ 应用提供支持。

在某些场景中，您可能需要在 webforJ 应用旁边部署额外的 servlet：
- 集成提供自己 servlet 的第三方库
- 实现 REST API 或 webhook
- 处理具有自定义处理的文件上传
- 支持基于 servlet 的遗留代码

webforJ 提供了两种方式来部署自定义 servlet：

## 方法 1：重新映射 `WebforjServlet` {#approach-1-remapping-webforjservlet}

该方法将 `WebforjServlet` 从 `/*` 重新映射到特定路径，如 `/ui/*`，为自定义 servlet 空出 URL 命名空间。虽然这需要修改 web.xml，但它可以让自定义 servlet 直接访问他们的 URL 模式，而没有任何代理开销。

```xml
<web-app>
  <!-- WebforjServlet 被重新映射以仅处理 /ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- 自定义 servlet 及其自己的 URL 模式 -->
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
- webforJ 组件可通过 `/ui/` 访问
- 自定义 servlet 处理访问 `/hello-world` 的请求
- 不涉及代理机制 - 直接的 servlet 容器路由

:::tip Spring Boot 配置
当在 Spring Boot 中使用 webforJ 时，没有 `web.xml` 文件。相反，在 `application.properties` 中配置 servlet 映射：

```Ini
webforj.servlet-mapping=/ui/*
```

此属性将 `WebforjServlet` 从默认的 `/*` 重新映射到 `/ui/*`，为空出自定义 servlet 的 URL 命名空间。请勿在值周围包含引号 - 它们将被解释为 URL 模式的一部分。
:::

## 方法 2：`WebforjServlet` 代理配置 {#approach-2-webforjservlet-proxy-configuration}

该方法保持 `WebforjServlet` 在 `/*`，并在 `webforJ.conf` 中配置自定义 servlet。`WebforjServlet` 拦截所有请求，并将匹配模式代理到您的自定义 servlet。

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

<!-- 自定义 servlet 及其自己的 URL 模式 -->
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
- 对 `/hello-world` 的请求将被代理到 `HelloWorldServlet`
