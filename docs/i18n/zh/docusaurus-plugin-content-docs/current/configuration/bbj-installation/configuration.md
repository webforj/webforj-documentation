---
title: Install Plugin
sidebar_position: 3
displayed_sidebar: documentationSidebar
_i18n_hash: 3f3e4285abb3b23f9427cdd7b9baa282
---
您可以使用项目的 POM 文件配置 webforJ，该文件旨在简化应用程序的部署。以下章节概述了可以更改的各种选项，以实现所需的结果。

## 引擎排除 {#engine-exclusion}

在使用 `BBjServices` 时，应排除 `webforj-engine` 依赖项，因为引擎提供的功能已经可用。

```xml
<dependencies>
  <dependency>
    <groupId>com.webforj</groupId>
    <artifactId>webforj</artifactId>
    <version>${webforj.version}</version>
    <exclusions>
      <exclusion>
        <groupId>com.webforj</groupId>
        <artifactId>webforj-engine</artifactId>
      </exclusion>
    </exclusions> 
  </dependency>
</dependencies>
```

## POM 文件标签 {#pom-file-tags}

`<configuration>` 标签中的标签可以更改以配置您的应用程序。编辑与 [`HelloWorldJava`](https://github.com/webforj/HelloWorldJava) 启动库一起提供的默认 POM 文件中的以下行将导致这些更改：

```xml {13-16} showLineNumbers
<plugin>
    <groupId>com.webforj</groupId>
    <artifactId>webforj-install-maven-plugin</artifactId>
    <version>${webforj.version}</version>
    <executions>
        <execution>
            <goals>
                <goal>install</goal>
            </goals>
    </execution>
    </executions>
    <configuration>
        <deployurl>http://localhost:8888/webforj-install</deployurl>
        <classname>samples.HelloWorldApp</classname>
        <publishname>hello-world</publishname>
        <debug>true</debug>
    </configuration>
</plugin>
```

- **`<deployurl>`** 此标签是可以访问该项目安装的 webforJ 端点的网址。对于在本地运行应用程序的用户，使用默认端口 8888。对于运行 Docker 的用户，应将端口更改为在 [配置 Docker 容器](./docker#2-configuration) 时输入的端口。

- **`<classname>`** 此标签应包含您希望运行的应用程序的包和类名。这将是您的项目中扩展 `App` 类并从基础 URL 运行的单个类。

- **`<publishname>`** 此标签指定发布 URL 中应用程序的名称。通常，要运行您的程序，您将导航到类似 `http://localhost:8888/webapp/<publishname>` 的 URL，将 `<publishname>` 替换为 `<publishname>` 标签中的值。然后，运行由 `<classname>` 标签指定的程序。

- **`<debug>`** 调试标签可以设置为 true 或 false，决定浏览器控制台是否显示您的程序抛出的错误消息。

## 运行特定程序 {#running-a-specific-program}

有两种方法可以在您的应用程序中运行特定程序：

1. 将程序放置在扩展 `App` 类的类的 `run()` 方法中。
2. 在您的 webforJ 应用程序中利用 [路由](../../routing/overview) 给该程序一个专用 URL。

## webforJ 如何选择入口点 {#how-webforj-selects-an-entry-point}

应用程序的入口点由 POM 文件中指定的 `<classname>` 决定。如果 POM 文件中未指定入口点，系统将开始入口点搜索。

### 入口点搜索 {#entry-point-search}

1. 如果有一个扩展 `App` 类的单一类，则该类将成为入口点。
2. 如果多个类扩展 `App`，系统将检查是否有一个类具有 `com.webforj.annotation.AppEntry` 注释。带有 `@AppEntry` 注释的唯一类将成为入口点。
    :::warning
    如果多个类都带有 `@AppEntry` 注释，将抛出异常，列出所有发现的类。
    :::

如果有多个扩展 `App` 的类，并且没有一个带有 `@AppEntry` 注释，则将抛出异常，详细说明每个子类。

## 调试模式 {#debug-mode}

您还可以在调试模式下运行应用程序，这样控制台将打印全面的错误消息。

第一种选择是修改位于 BBj 安装的 `cfg/` 目录中的 `config.bbx` 文件。向文件添加 `SET DEBUG=1` 行并保存更改。

此外，在企业管理器中，您可以将以下内容作为程序参数添加：`DEBUG`

完成其中任何一个操作后，浏览器控制台将打印错误消息。
