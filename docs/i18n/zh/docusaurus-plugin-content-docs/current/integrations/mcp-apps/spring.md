---
title: Spring Boot MCP Apps
sidebar_position: 5
description: >-
  Build and publish a routed webforJ view as an MCP App with Spring Boot and
  Spring AI.
_i18n_hash: c8aedcd8d0981805ce4fce3b338542c0
---
Spring Boot 集成通过 Spring AI MCP 服务器发布路由的 webforJ 视图。当 Spring Boot 应用需要向 MCP 客户端（连接到服务器的 AI 应用）暴露交互式视图时，请使用它。

:::tip[不使用 Spring Boot？]

对于基于 Servlet 的 webforJ 应用，请使用 [标准 webforJ 设置](./without-spring) 自行组装和注册 MCP 服务器。
:::

## 添加依赖项 {#add-the-dependencies}

导入 webforJ 材料清单，以便 webforJ 模块使用同一版本。然后添加 Spring Boot 启动器、MCP 应用模块和 Spring AI WebMVC MCP 服务器启动器。

```xml
<properties>
  <maven.compiler.release>21</maven.compiler.release>
  <webforj.version>26.02</webforj.version>
  <spring-ai.version>2.0.0</spring-ai.version>
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
    <artifactId>webforj-spring-boot-starter</artifactId>
  </dependency>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-mcp-apps</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
    <version>${spring-ai.version}</version>
  </dependency>
</dependencies>
```

## 配置 MCP 端点 {#configure-the-mcp-endpoint}

在 `src/main/resources/application.properties` 中设置 Spring AI 使用 Streamable HTTP：

```Ini
spring.ai.mcp.server.protocol=STREAMABLE
webforj.origin=http://localhost:8080
```

Spring AI 默认在 `/mcp` 提供传输。webforJ 将该路径和 OAuth 2.0 发现路径留给 Spring，而不是将其视为 UI 路由。本地源可以使生成的应用资源和组件 URL 指向正在运行的应用。有关代理或隧道使应用获得不同公共源时，请参见 [MCP 应用配置](./configuration)。

## 添加路由应用 {#add-a-routed-app}

创建一个普通的路由视图并添加 `@McpApp`。描述有助于 AI 确定何时该工具是有用的。显式名称使工具名称在路由更改时保持稳定。

```java
package com.example.inventory;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.H1;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.mcp.annotation.McpApp;
import com.webforj.router.annotation.Route;

@Route("/inventory")
@McpApp(
    name = "inventory",
    description = "显示仓库的当前库存。",
    displayMode = McpAppDisplayMode.INLINE)
public class InventoryView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public InventoryView() {
    self.add(new H1("库存"));
  }
}
```

该视图在浏览器中仍然可以在 `/inventory` 工作。Spring 自动配置发布了一个 `inventory` MCP 工具和一个打开该路由的 UI 资源，并安装了嵌入 webforJ 所需的 Servlet 过滤器和会话设置。

## 启动服务器 {#start-the-server}

使用项目的正常运行工作流程启动服务器。默认端口下，MCP 端点为：

```text
http://localhost:8080/mcp
```

使用本地或远程客户端 [测试最小发布视图](./testing)。有关结构化参数，参见 [打开输入](./opening-apps)，有关公共源和外部资源，参见 [MCP 应用配置](./configuration)。
