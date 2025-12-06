---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: e3af6f7983bbd6ed7db57428412466c8
---
将 Webswing 与 webforJ 集成涉及两个组件：承载您的 Swing 应用程序的 Webswing 服务器，以及在您的 webforJ 应用程序中嵌入的 `WebswingConnector` 组件。

## 前提条件 {#prerequisites}

在开始之前，请确保您具备以下前提条件：

- **Java 桌面应用程序**：打包为 JAR 文件的 Swing、JavaFX 或 SWT 应用程序
- **Webswing 服务器**：从 [webswing.org](https://webswing.org) 下载
- **webforJ 版本 `25.10` 或更高**：支持 `WebswingConnector`

## 架构概述 {#architecture-overview}

集成架构包括：

1. **Webswing 服务器**：运行您的 Swing 应用程序，捕获 GUI 渲染并处理用户输入
2. **webforJ 应用程序**：通过嵌入的 `WebswingConnector` 托管您的 Web 应用程序
3. **浏览器客户端**：显示 webforJ 用户界面和嵌入的 Swing 应用程序

:::important 端口配置
Webswing 和 webforJ 必须在不同的端口上运行以避免冲突。webforJ 和 Webswing 通常运行在端口 `8080` 上。您应该更改 Webswing 端口或 webforJ 端口之一。
:::

## Webswing 服务器设置 {#webswing-server-setup}

### 安装和启动 {#installation-and-startup}

1. **下载 Webswing** 从 [官方网站](https://www.webswing.org/en/downloads)
2. **解压缩归档** 到您选择的位置（例如 `/opt/webswing` 或 `C:\webswing`）
3. **启动服务器** 使用特定于平台的脚本：

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

4. **验证服务器是否正在运行** 通过访问 `http://localhost:8080`

### 应用程序配置 {#application-configuration}

服务器运行后，通过访问 `http://localhost:8080/admin` 进入管理控制台，以添加和配置您的 Swing 应用程序。

在管理控制台中配置：

- **应用程序名称**：将成为 URL 路径的一部分（例如 `myapp` → `http://localhost:8080/myapp/`）
- **主类**：您的 Swing 应用程序的入口点
- **类路径**：指向您的应用程序 JAR 和依赖项的路径
- **JVM 参数**：内存设置、系统属性和其他 JVM 选项
- **主目录**：应用程序的工作目录

配置完成后，您的 Swing 应用程序将在 `http://localhost:8080/[app-name]/` 访问。

### CORS 配置 {#cors-configuration}

在将 Webswing 嵌入运行在不同端口或域上的 webforJ 应用程序时，您必须在 Webswing 中配置跨源资源共享 (CORS)。这允许浏览器从您的 webforJ 页面加载 Webswing 内容。

在 Webswing 管理控制台中，导航到您的应用程序配置，并设置：

- **允许的来源**：添加您的 webforJ 应用程序的来源（例如 `http://localhost:8090` 或 `*` 用于开发）

此设置对应于 Webswing 应用程序配置中的 `allowedCorsOrigins` 选项。

## webforJ 集成 {#webforj-integration}

一旦您的 Webswing 服务器启动并且您的 Swing 应用程序已配置且启用 CORS，您可以将其集成到您的 webforJ 应用程序中。

### 添加依赖 {#add-dependency}

将 Webswing 集成模块添加到您的 webforJ 项目中。这提供 `WebswingConnector` 组件和相关类。

```xml
<dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-webswing</artifactId>
</dependency>
```

### 基本实现 {#basic-implementation}

创建一个视图，使用 `WebswingConnector` 嵌入您的 Swing 应用程序：

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
    // 使用您的 Webswing 应用程序 URL 初始化连接器
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // 设置显示尺寸
    connector.setSize("100%", "600px");

    // 添加到视图容器
    getBoundComponent().add(connector);
  }
}
```

连接器在添加到 DOM 时会自动与 Webswing 服务器建立连接。然后，Swing 应用程序的用户界面将在连接器组件内渲染。

## 配置选项 {#configuration-options}

`WebswingOptions` 类允许您自定义连接器的行为。默认情况下，连接器在创建时自动启动，并使用标准连接设置。您可以通过创建 `WebswingOptions` 实例并将其应用于连接器来修改此行为。

例如，在生产环境中隐藏注销按钮，您可以通过 webforJ 应用程序管理身份验证：

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // 隐藏注销按钮

connector.setOptions(options);
```

或者如果您需要手动控制连接何时启动：

```java
// 创建连接器而不自动启动
WebswingConnector connector = new WebswingConnector(url, false);

// 配置并在准备好时启动
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

选项涵盖连接管理、身份验证、调试和监控。
