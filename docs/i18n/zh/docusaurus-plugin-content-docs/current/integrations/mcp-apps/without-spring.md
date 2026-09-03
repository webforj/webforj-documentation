---
title: 标准 webforJ 设置
sidebar_position: 35
description: >-
  Assemble an MCP server and register webforJ MCP Apps in a standard
  servlet-based webforJ application.
_i18n_hash: 0d4b2a9da02c480934e5527e0c6d4a44
---
标准设置创建 MCP 服务器，注册其 servlet，并安装 webforJ 支持。

:::tip[何时使用此设置]

当应用程序不使用 Spring Boot 时，使用标准 servlet 设置。对于 Spring Boot 应用程序，请使用 [Spring Boot 设置](./spring)，它通过 Spring AI 自动发布路由视图。
:::


:::warning[不支持 BBj 服务]

MCP 应用程序需要控制应用程序的 servlet 上下文，以安装跨域过滤器、cookie 设置、OAuth 2.0 发现处理以及主机所需的其他嵌入支持。通过 BBj 服务部署的应用程序无法启动该 servlet 设置。请在应用程序控制的 servlet 容器中部署应用程序。
:::

## 添加 MCP 服务器依赖项 {#add-the-dependencies}

在现有的 webforJ 依赖项旁边添加 MCP 应用模块。它提供 webforJ 贡献和用于组装服务器的 MCP SDK。

初始化程序还实现了 `ServletContainerInitializer` 并使用其他 Jakarta Servlet 类型。使用 `provided` 范围添加 Servlet API，以便在编译时可以使用这些类型，而不必打包第二个 servlet 实现。 servlet 容器（例如 Jetty）在运行时提供它们。

```xml
<properties>
  <webforj.version>26.02</webforj.version>
</properties>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.webforj</groupId>
      <artifactId>webforj-bom</artifactId>
      <version>${webforj.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-mcp-apps</artifactId>
  </dependency>
  <dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.1.0</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

## 添加初始化器 {#add-the-initializer}

初始化器扫描包含路由视图的包，将它们的工具和 UI 资源贡献给 MCP 服务器，并在 `/mcp` 挂载可流式传输 HTTP。

```java
package com.example.inventory;

import com.webforj.mcp.McpAppContribution;
import com.webforj.mcp.McpAppOptions;
import com.webforj.mcp.McpAppServletPath;
import io.modelcontextprotocol.common.McpTransportContext;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStreamableServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import java.util.Set;

public class InventoryMcpServerInitializer implements ServletContainerInitializer {

  @Override
  public void onStartup(Set<Class<?>> classes, ServletContext context) {
    McpAppOptions options = McpAppOptions.load();
    McpAppContribution contribution = McpAppContribution.ofPackages(
        new String[] {"com.example.inventory"}, McpAppServletPath.of(context));
    contribution.getOrigin().configure(options.getOrigin());

    HttpServletStreamableServerTransportProvider transport =
        HttpServletStreamableServerTransportProvider.builder().mcpEndpoint("/mcp")
            .contextExtractor(request -> {
              contribution.getOrigin().observe(request);
              return McpTransportContext.EMPTY;
            }).build();

    McpSyncServer server = McpServer.sync(transport)
        .serverInfo("inventory", "1.0.0")
        .capabilities(ServerCapabilities.builder().tools(true).resources(false, true).build())
        .tools(contribution.getToolSpecifications())
        .resources(contribution.getResourceSpecifications())
        .build();

    ServletRegistration.Dynamic registration = context.addServlet("mcpServlet", transport);
    registration.setAsyncSupported(true);
    registration.addMapping("/mcp/*");

    context.addListener(new ServletContextListener() {
      @Override
      public void contextDestroyed(ServletContextEvent event) {
        server.close();
      }
    });

    contribution.install(context, options);
  }
}
```

`McpAppContribution.ofPackages` 创建注册在服务器上的两个集合。`getToolSpecifications()` 包含从路由 `@McpApp` 视图生成的工具。`getResourceSpecifications()` 包含生成的 `ui://webforj/...` 资源，MCP 客户端读取这些资源以呈现这些视图。注册工具而不注册其 UI 资源会暴露客户无法显示的调用。

请求上下文提取器允许 webforJ 在 `webforj.origin` 未设置时观察公共来源。最后的 `install` 调用添加应用程序资源策略、跨域处理、会话 cookie 设置、OAuth 2.0 发现处理和 favicon 支持。发布工具但跳过此调用的服务器可能会暴露客户端无法正确运行的资源。

## 注册初始化器 {#register-the-initializer}

通过 Java 的服务加载器在 `src/main/resources/META-INF/services/jakarta.servlet.ServletContainerInitializer` 中注册初始化器：

```text
com.example.inventory.InventoryMcpServerInitializer
```

Servlet 容器在应用程序启动期间加载此类。请保持服务文件中的完全限定类名与初始化器包同步。

## 配置部署 {#configure-the-deployment}

标准部署从 `webforj.conf` 读取 MCP 应用设置。例如：

```Ini
webforj.origin = "https://app.example.com"
webforj.mcp.allowed-origins = ["https://assistant.example.com"]
```

现在可以使用 [测试 MCP 应用](./testing) 中描述的任何客户端检查最小服务器。[MCP 应用配置](./configuration) 解释了部署需要超过本地默认值时公共和客户端来源。
