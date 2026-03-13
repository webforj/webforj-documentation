---
title: Setup and Configuration
sidebar_position: 2
_i18n_hash: cd2108b15ca8583794ddb366329c3638
---
将Webswing与webforJ集成涉及两个组件：托管您的Swing应用程序的Webswing服务器，以及在您的webforJ应用程序中嵌入的`WebswingConnector`组件。

## 先决条件 {#prerequisites}

开始之前，请确保您具备以下先决条件：

- **Java桌面应用程序**：以JAR文件打包的Swing、JavaFX或SWT应用
- **Webswing服务器**：从[webswing.org](https://webswing.org)下载
- **webforJ版本`25.10`或更高版本**：支持`WebswingConnector`

## 架构概述 {#architecture-overview}

集成架构包括：

1. **Webswing服务器**：运行您的Swing应用，捕获GUI渲染并处理用户输入
2. **webforJ应用程序**：托管带有嵌入式`WebswingConnector`的web应用
3. **浏览器客户端**：显示webforJ UI和嵌入的Swing应用

:::important 端口配置
Webswing和webforJ必须运行在不同的端口上，以避免冲突。webforJ和Webswing通常在端口`8080`上运行。您应该更改Webswing端口或webforJ端口之一。
:::

## Webswing服务器设置 {#webswing-server-setup}

### 安装和启动 {#installation-and-startup}

1. **从[官方网站](https://www.webswing.org/en/downloads)下载Webswing**
2. **将归档文件提取**到您选择的位置（例如，`/opt/webswing`或`C:\webswing`）
3. **使用平台特定的脚本启动服务器：**

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

4. **通过访问`http://localhost:8080`验证服务器是否正在运行**

### 应用程序配置 {#application-configuration}

服务器启动后，访问`http://localhost:8080/admin`的管理控制台，以添加和配置您的Swing应用。

在管理控制台中，配置：

- **应用程序名称**：成为URL路径的一部分（例如，`myapp` → `http://localhost:8080/myapp/`）
- **主类**：您的Swing应用的入口点
- **类路径**：您的应用JAR及其依赖项的路径
- **JVM参数**：内存设置、系统属性和其他JVM选项
- **主目录**：应用的工作目录

配置完成后，您的Swing应用将可以通过`http://localhost:8080/[app-name]/`访问。

### CORS配置 {#cors-configuration}

将Webswing嵌入在运行于不同端口或域的webforJ应用中时，您必须在Webswing中配置跨源资源共享（CORS）。这允许浏览器从您的webforJ页面加载Webswing内容。

在Webswing管理控制台中，导航到您应用的配置并设置：

- **允许的来源**：添加您webforJ应用的来源（例如，`http://localhost:8090`或`*`用于开发）

此设置对应于Webswing应用配置中的`allowedCorsOrigins`选项。

## webforJ集成 {#webforj-integration}

一旦您的Webswing服务器运行并配置了Swing应用且启用了CORS，您可以将其集成到您的webforJ应用中。

### 添加依赖 {#add-dependency}

Webswing集成依赖于webforJ的Webswing集成模块，该模块提供`WebswingConnector`组件及相关类。
将以下内容添加到您的`pom.xml`文件中：

```xml
<dependency>
  <groupId>com.webforj</groupId>
  <artifactId>webforj-webswing</artifactId>
  <version>${webforj.version}</version>
</dependency>
```

### 基本实现 {#basic-implementation}

创建一个视图，通过`WebswingConnector`嵌入您的Swing应用：

```java title="SwingAppView.java"
package com.example.views;

import com.webforj.annotation.Route;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.webswing.WebswingConnector;

@Route
public class SwingAppView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private WebswingConnector connector;

  public SwingAppView() {
    // 使用您的Webswing应用程序URL初始化连接器
    connector = new WebswingConnector("http://localhost:8080/myapp/");

    // 设置显示尺寸
    connector.setSize("100%", "600px");

    // 添加到视图容器
    self.add(connector);
  }
}
```

当连接器添加到DOM时，它会自动与Webswing服务器建立连接。Swing应用的UI随后在连接器组件内呈现。

## 配置选项 {#configuration-options}

`WebswingOptions`类允许您自定义连接器的行为。默认情况下，连接器在创建时自动启动，并使用标准连接设置。您可以通过创建`WebswingOptions`实例并将其应用于连接器来修改此行为。

例如，为了在通过webforJ应用管理身份验证的生产环境中隐藏注销按钮：

```java
WebswingConnector connector = new WebswingConnector("http://localhost:8080/myapp/");

WebswingOptions options = new WebswingOptions()
    .setDisableLogout(true);  // 隐藏注销按钮

connector.setOptions(options);
```

或者，如果您需要手动控制何时启动连接：

```java
// 创建未自动启动的连接器
WebswingConnector connector = new WebswingConnector(url, false);

// 配置并在准备好时启动
WebswingOptions options = new WebswingOptions();
connector.setOptions(options);
connector.start();
```

这些选项涵盖连接管理、身份验证、调试和监控。
