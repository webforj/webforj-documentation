---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: 76bc55d5b841ae3c06bcd2cd9e8b2632
---
将Webswing与webforJ集成涉及两个组件：托管您的Swing应用程序的Webswing服务器，以及在您的webforJ应用中嵌入的`WebswingConnector`组件。

## 先决条件 {#prerequisites}

在开始之前，请确保您具备以下先决条件：

- **Java桌面应用**：打包为JAR文件的Swing、JavaFX或SWT应用
- **Webswing服务器**：从[webswing.org](https://webswing.org)下载
- **webforJ版本`25.10`或更高**：支持`WebswingConnector`

## 架构概览 {#architecture-overview}

集成架构由以下部分组成：

1. **Webswing服务器**：运行您的Swing应用，捕获GUI渲染并处理用户输入
2. **webforJ应用**：托管您的网页应用，嵌入`WebswingConnector`
3. **浏览器客户端**：显示webforJ用户界面和嵌入的Swing应用

:::重要 端口配置
Webswing和webforJ必须在不同的端口上运行以避免冲突。webforJ和Webswing通常在端口`8080`上运行。您应该更改Webswing端口或webforJ端口。
:::

## Webswing服务器设置 {#webswing-server-setup}

### 安装和启动 {#installation-and-startup}

1. **从官方网站下载Webswing** [official website](https://www.webswing.org/en/downloads)
2. **解压归档** 到您选择的位置（例如，`/opt/webswing`或`C:\webswing`）
3. **使用特定于平台的脚本启动服务器：**

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

### 应用配置 {#application-configuration}

服务器启动后，通过访问 `http://localhost:8080/admin` 进入管理控制台，添加并配置您的Swing应用。

在管理控制台中配置：

- **应用名称**：成为URL路径的一部分（例如，`myapp` → `http://localhost:8080/myapp/`）
- **主类**：您的Swing应用的入口点
- **类路径**：应用JAR和依赖项的路径
- **JVM参数**：内存设置、系统属性和其他JVM选项
- **主目录**：应用的工作目录

配置完成后，您的Swing应用将可通过 `http://localhost:8080/[app-name]/` 访问

### CORS配置 {#cors-configuration}

在不同端口或域上运行的webforJ应用中嵌入Webswing时，必须在Webswing中配置跨域资源共享（CORS）。这允许浏览器从您的webforJ页面加载Webswing内容。

在Webswing管理控制台中，导航到您应用的配置并设置：

- **允许的来源**：添加您的webforJ应用的来源（例如，`http://localhost:8090`或`*`用于开发）

此设置对应于Webswing应用配置中的`allowedCorsOrigins`选项。

## webforJ集成 {#webforj-integration}

一旦您的Webswing服务器正常运行，并且您的Swing应用配置好并启用了CORS，您就可以将其集成到您的webforJ应用中。

### 添加依赖 {#add-dependency}

Webswing集成依赖于webforJ的Webswing集成模块，该模块提供`WebswingConnector`组件和相关类。
将以下内容添加到您的`pom.xml`文件中：

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### 基本实现 {#basic-implementation}

创建一个视图，使用`WebswingConnector`嵌入您的Swing应用：

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
    // 用您的Webswing应用程序URL初始化连接器
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // 设置显示尺寸
    connector.setSize("100%", "600px");

    // 添加到视图容器
    getBoundComponent().add(connector);
  }
}
```

添加到DOM时，连接器将自动与Webswing服务器建立连接。然后Swing应用的用户界面将在连接器组件内呈现。

## 配置选项 {#configuration-options}

`WebswingOptions`类允许您自定义连接器的行为。默认情况下，连接器在创建时自动启动，并使用标准连接设置。您可以通过创建`WebswingOptions`实例并将其应用于连接器来修改此行为。

例如，若要在通过webforJ应用管理身份验证的生产环境中隐藏注销按钮：

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // 隐藏注销按钮

connector.setOptions(options);
```

或者如果您需要手动控制连接何时启动：

```java
// 创建无自动启动的连接器
WebswingConnector connector = new WebswingConnector(url, false);

// 配置并在准备好时启动
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

这些选项涵盖连接管理、身份验证、调试和监控。
