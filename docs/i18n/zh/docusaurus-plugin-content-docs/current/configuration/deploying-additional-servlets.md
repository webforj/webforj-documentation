---
sidebar_position: 35
title: Deploying Additional Servlets
sidebar_class_name: new-content
_i18n_hash: 0717fa071511a4ca3b71dcf0592146e7
---
<!-- vale off -->
# 部署额外的Servlet <DocChip chip='since' label='25.02' />
<!-- vale on -->

webforJ将所有请求通过`WebforjServlet`进行路由，该Servlet在web.xml中默认映射到`/*`。这个Servlet管理组件生命周期、路由和为您的webforJ应用程序提供的UI更新。

在某些情况下，您可能需要在webforJ应用程序旁边部署额外的Servlet：
- 集成提供自己Servlet的第三方库
- 实现REST API或Webhooks
- 处理自定义处理的文件上传
- 支持基于Servlet的遗留代码

webforJ提供了两种方法来在应用程序旁边部署自定义Servlet：

## 方法1：重新映射`WebforjServlet` {#approach-1-remapping-webforjservlet}

这种方法将`WebforjServlet`从`/*`重新映射到特定路径，例如`/ui/*`，为自定义Servlet释放URL名称空间。虽然这需要修改`web.xml`，但它使自定义Servlet直接访问其URL模式，而没有任何代理开销。

```xml
<web-app>
  <!-- WebforjServlet重新映射以仅处理/ui/* -->
  <servlet>
    <servlet-name>WebforjServlet</servlet-name>
    <servlet-class>com.webforj.servlet.WebforjServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>WebforjServlet</servlet-name>
    <url-pattern>/ui/*</url-pattern>
  </servlet-mapping>
  
  <!-- 带有自己URL模式的自定义Servlet -->
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
- webforJ组件可在`/ui/`下访问
- 自定义Servlet处理对`/hello-world`的请求
- 无需代理机制 - 直接在Servlet容器路由

:::tip Spring Boot配置
当在Spring Boot中使用webforJ时，没有`web.xml`文件。相反，请在`application.properties`中配置Servlet映射：

```Ini
webforj.servlet-mapping=/ui/*
```

此属性将`WebforjServlet`从默认的`/*`重新映射到`/ui/*`，为您的自定义Servlet释放URL名称空间。不要在值周围包含引号 - 它们将被解释为URL模式的一部分。
:::

## 方法2：`WebforjServlet`代理配置 {#approach-2-webforjservlet-proxy-configuration}

这种方法将`WebforjServlet`保持在`/*`并在`webforJ.conf`中配置自定义Servlet。`WebforjServlet`拦截所有请求，并将匹配模式代理到您的自定义Servlet。

### 标准web.xml配置 {#standard-webxml-configuration}

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

<!-- 带有自己URL模式的自定义Servlet -->
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

### webforJ.conf配置 {#webforjconf-configuration}

```hocon
servlets = [
  {
    name = "hello-world"
    class = "com.example.HelloWorldServlet"
  }
]
```

使用此配置：
- `WebforjServlet`处理所有请求
- 请求`/hello-world`被代理到`HelloWorldServlet`
