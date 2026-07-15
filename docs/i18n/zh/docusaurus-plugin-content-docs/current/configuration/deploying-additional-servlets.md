---
sidebar_position: 25
title: Deploying Additional Servlets
description: >-
  Host REST endpoints and third-party servlets alongside a webforJ app by
  remapping WebforjServlet or proxying through webforj.conf.
_i18n_hash: 1e25ddc6c7e56063c26b9f911c0be5d2
---
<!-- vale off -->
# 部署额外的Servlet <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ 将所有请求通过 `WebforjServlet` 进行路由，该Servlet在 web.xml 中默认映射为 `/*`。此Servlet 管理组件生命周期、路由和为您的 webforJ 应用程序提供的 UI 更新。

在某些情况下，您可能需要在 webforJ 应用程序旁边部署额外的Servlet：
- 集成提供自定义Servlet 的第三方库
- 实现 REST API 或 webhooks
- 处理带有自定义处理的文件上传
- 支持基于Servlet的遗留代码

webforJ 提供两种方法在您的应用程序旁边部署自定义Servlet：

<AISkillTip skill="webforj-adding-servlets" />

## 方法 1：重新映射 `WebforjServlet` {#approach-1-remapping-webforjservlet}

该方法将 `WebforjServlet` 从 `/*` 重新映射到特定路径，如 `/ui/*`，为自定义Servlet释放 URL 命名空间。虽然这需要修改 `web.xml`，但它使自定义Servlet 能够直接访问其 URL 模式，而没有任何代理开销。

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

  <!-- 自定义Servlet，具有自己的 URL 模式 -->
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
- webforJ 组件可在 `/ui/` 下访问
- 自定义Servlet 处理对 `/hello-world` 的请求
- 不涉及代理机制 - 直接进行Servlet容器路由

:::tip Spring Boot 配置
当将 webforJ 与 Spring Boot 一起使用时，没有 `web.xml` 文件。相反，请在 `application.properties` 中配置 Servlet 映射：

```Ini
webforj.servlet-mapping=/ui/*
```

此属性将 `WebforjServlet` 从默认的 `/*` 重新映射为 `/ui/*`，为您的自定义Servlet 释放 URL 命名空间。请勿在值周围添加引号 - 它们将被解释为 URL 模式的一部分。
:::

## 方法 2：`WebforjServlet` 代理配置 {#approach-2-webforjservlet-proxy-configuration}

该方法将 `WebforjServlet` 保持在 `/*` 并在 `webforj.conf` 中配置自定义Servlet。`WebforjServlet` 拦截所有请求并将匹配的模式代理到您的自定义Servlet。

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

<!-- 自定义Servlet，具有自己的 URL 模式 -->
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

通过此配置：
- `WebforjServlet` 处理所有请求
- 对 `/hello-world` 的请求被代理到 `HelloWorldServlet`
- 可选的 `config` 键提供名称/值对作为Servlet 的初始化参数
