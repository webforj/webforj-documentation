---
title: 设置与配置
sidebar_position: 2
_i18n_hash: 5d819b2a84de98748b48e7b3b1c9ab66
---
将 Webswing 与 webforJ 集成涉及两个组件：托管您的 Swing 应用程序的 Webswing 服务器，以及在您的 webforJ 应用程序中嵌入的 `WebswingConnector` 组件。

## 先决条件

在开始之前，请确保您具备以下先决条件：

- **Java 桌面应用程序**：包装为 JAR 文件的 Swing、JavaFX 或 SWT 应用程序
- **Webswing 服务器**：从 [webswing.org](https://webswing.org) 下载
- **webforJ 版本 `25.10` 或更高**：需要支持 `WebswingConnector`

## 架构概述

集成架构由以下部分组成：

1. **Webswing 服务器**：运行您的 Swing 应用程序，捕获 GUI 渲染，并处理用户输入
2. **webforJ 应用程序**：托管您的网络应用程序，并嵌入 `WebswingConnector`
3. **浏览器客户端**：同时显示 webforJ UI 和嵌入的 Swing 应用程序

:::important 端口配置
Webswing 和 webforJ 必须在不同的端口上运行以避免冲突。通常 webforJ 和 Webswing 在端口 `8080` 上运行。您应更改 Webswing 端口或 webforJ 端口之一。
:::

## Webswing 服务器设置

### 安装和启动

1. **下载 Webswing** 从 [官方网站](https://www.webswing.org/en/downloads)
2. **解压缩归档** 到您首选的位置（例如，`/opt/webswing` 或 `C:\webswing`）
3. **使用特定于平台的脚本启动服务器**：

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

4. **通过访问 `http://localhost:8080` 验证服务器是否正在运行**

### 应用程序配置

服务器运行后，通过访问 `http://localhost:8080/admin` 进入管理控制台以添加和配置您的 Swing 应用程序。

在管理控制台中配置：

- **应用程序名称**：成为 URL 路径的一部分（例如，`myapp` → `http://localhost:8080/myapp/`）
- **主类**：您的 Swing 应用程序的入口点
- **类路径**：您的应用 JAR 和依赖项的路径
- **JVM 参数**：内存设置、系统属性和其他 JVM 选项
- **主目录**：应用程序的工作目录

配置完成后，您的 Swing 应用程序将可以在 `http://localhost:8080/[app-name]/` 访问。

## webforJ 集成

一旦您的 Webswing 服务器与配置好的 Swing 应用程序运行，您可以将其集成到您的 webforJ 应用程序中。这涉及添加依赖关系、配置跨域资源共享 (CORS) 以及使用 `WebswingConnector` 组件创建视图。

### 添加依赖关系

将 Webswing 集成模块添加到您的 webforJ 项目中。这提供了 `WebswingConnector` 组件和相关类。

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### CORS 配置

当 Webswing 和 webforJ 在不同的端口或域上运行时，需要进行跨域资源共享 (CORS) 配置。浏览器的同源策略会阻止没有适当 CORS 头的不同来源之间的请求。

创建一个 servlet 过滤器以向您的 webforJ 应用程序添加 CORS 头：

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

在您的 `web.xml` 中注册过滤器：

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

:::important 生产环境访问
对于生产环境，将 `Access-Control-Allow-Origin` 中的通配符 (`*`) 替换为您的特定 Webswing 服务器 URL，以提高安全性。
:::

### 基本实现

创建一个视图，通过 `WebswingConnector` 嵌入您的 Swing 应用程序：

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
    // 用您的 Webswing 应用程序 URL 初始化连接器
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // 设置显示尺寸
    connector.setSize("100%", "600px");

    // 添加到视图容器
    getBoundComponent().add(connector);
  }
}
```

连接器在添加到 DOM 时会自动建立与 Webswing 服务器的连接。然后，Swing 应用程序的 UI 在连接器组件内呈现。

## 配置选项

`WebswingOptions` 类允许您自定义连接器的行为。默认情况下，连接器在创建时会自动启动并使用标准连接设置。您可以通过创建 `WebswingOptions` 实例并将其应用于连接器来修改此行为。

例如，要在通过 webforJ 应用程序管理身份验证的生产环境中隐藏注销按钮：

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // 隐藏注销按钮

connector.setOptions(options);
```

或者，如果您需要手动控制何时启动连接：

```java
// 创建不自动启动的连接器
WebswingConnector connector = new WebswingConnector(url, false);

// 配置并在准备好时启动
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

这些选项涵盖连接管理、身份验证、调试和监控。
