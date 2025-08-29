---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 95695a68854d595e78a58904d7214208
---
<!-- vale off -->
# 部署额外的 Servlets <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ 将所有请求通过 `WebforjServlet` 路由，该 servlet 默认映射到 `/*`。此 servlet 管理组件生命周期、路由和 UI 更新，为您的 webforJ 应用提供支持。

在某些情况下，您可能需要在 webforJ 应用旁边部署额外的 servlets：
- 集成提供自己 servlet 的第三方库
- 实现 REST API 或 webhook
- 处理带有自定义处理的文件上传
- 支持基于 servlet 的遗留代码

webforJ 提供两种方法用于在您的应用旁边部署自定义 servlets：

## 方法 1：重新映射 `WebforjServlet` {#approach-1-remapping-webforjservlet}

此方法将 `WebforjServlet` 从 `/*` 重新映射到特定路径，如 `/ui/*`，从而释放 URL 命名空间以供自定义 servlets 使用。虽然这需要修改 `web.xml`，但它使自定义 servlets 可以直接访问其 URL 模式，而不会有任何代理开销。

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
  
  <!-- 自定义 servlet 拥有自己的 URL 模式 -->
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

使用此配置：
- webforJ 组件可在 `/ui/` 下访问
- 自定义 servlet 处理对 `/hello-world` 的请求
- 不涉及代理机制 - 直接的 servlet 容器路由

:::tip Spring Boot 配置
在使用 webforJ 与 Spring Boot 时，不存在 `web.xml` 文件。相反，在 `application.properties` 中配置 servlet 映射：

```Ini
webforj.servlet-mapping=/ui/*
```

该属性将 `WebforjServlet` 从默认的 `/*` 重新映射到 `/ui/*`，从而释放自定义 servlets 的 URL 命名空间。值周围不要包含引号 - 否则将被视为 URL 模式的一部分。
:::

## 方法 2：`WebforjServlet` 代理配置 {#approach-2-webforjservlet-proxy-configuration}

此方法将 `WebforjServlet` 保持在 `/*`，并在 `webforj.conf` 中配置自定义 servlets。`WebforjServlet` 拦截所有请求，并将匹配模式代理到您的自定义 servlets。

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

<!-- 自定义 servlet 拥有自己的 URL 模式 -->
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
servlets: [
  {
    class: "com.example.HelloWorldServlet",
    name: "hello-world",
    config: {
      foo: "bar",
      baz: "bang"
    }
  }
]
```

使用此配置：
- `WebforjServlet` 处理所有请求
- 对 `/hello-world` 的请求被代理到 `HelloWorldServlet`
- 可选的 `config` 键提供名称/值对作为 servlet 的初始化参数
